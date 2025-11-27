/*
 TeslaArtProject.java

 Complete, refactored generative-art project in one file.
 - Clean architecture: App / RendererPanel / Renderer / LightningGenerator / SceneConfig / Presets
 - UI controls: sliders + presets
 - Motion blur (accumulation) and glow blur (simple box blur)
 - 4K export performed in background (SwingWorker)

 How to use:
 1) Save this file as TeslaArtProject.java
 2) Compile: javac TeslaArtProject.java
 3) Run:     java TeslaArtProject

 This is intentionally a single-file distribution for convenience. For a real project split into multiple files.
*/

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;

public class TeslaArtProject {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> TeslaArtApp.showApp());
    }
}

/* -------------------------- App / UI -------------------------- */
class TeslaArtApp {
    public static void showApp() {
        JFrame frame = new JFrame("Tesla Art — Pro");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SceneConfig config = SceneConfig.defaultConfig();
        RendererPanel rendererPanel = new RendererPanel(config);

        JPanel controls = Controls.createControls(rendererPanel);

        frame.setLayout(new BorderLayout());
        frame.add(controls, BorderLayout.NORTH);
        frame.add(rendererPanel, BorderLayout.CENTER);
        frame.setSize(1200, 820);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

/* -------------------------- SceneConfig / Presets -------------------------- */
record SceneConfig(int bolts, int fractalDepth, double rotationSpeed, float motionFadeAlpha, int glowPasses, float hue) {
    public static SceneConfig defaultConfig() {
        return new SceneConfig(10, 8, 0.02, 0.12f, 4, 0.55f);
    }

    public SceneConfig withBolts(int b) { return new SceneConfig(b, fractalDepth, rotationSpeed, motionFadeAlpha, glowPasses, hue); }
    public SceneConfig withDepth(int d) { return new SceneConfig(bolts, d, rotationSpeed, motionFadeAlpha, glowPasses, hue); }
    public SceneConfig withSpeed(double s) { return new SceneConfig(bolts, fractalDepth, s, motionFadeAlpha, glowPasses, hue); }
    public SceneConfig withMotionFade(float a) { return new SceneConfig(bolts, fractalDepth, rotationSpeed, a, glowPasses, hue); }
    public SceneConfig withGlowPasses(int p) { return new SceneConfig(bolts, fractalDepth, rotationSpeed, motionFadeAlpha, p, hue); }
    public SceneConfig withHue(float h) { return new SceneConfig(bolts, fractalDepth, rotationSpeed, motionFadeAlpha, glowPasses, h); }
}

enum Preset {
    COOL("Cool Plasma", new SceneConfig(10, 8, 0.02, 0.12f, 4, 0.55f)),
    HOT("Hot Lightning", new SceneConfig(12, 9, 0.03, 0.08f, 5, 0.08f)),
    SUBTLE("Subtle Glow", new SceneConfig(6, 6, 0.01, 0.18f, 3, 0.6f));

    public final String name;
    public final SceneConfig config;

    Preset(String name, SceneConfig config) { this.name = name; this.config = config; }
    @Override public String toString(){ return name; }
}

/* -------------------------- Controls (UI) -------------------------- */
class Controls {
    static JPanel createControls(RendererPanel panel) {
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton saveBtn = new JButton("Save 4K PNG");
        saveBtn.addActionListener(e -> panel.save4KAsync());

        JLabel boltsLabel = new JLabel("Bolts:");
        JSlider boltsSlider = new JSlider(1, 30, panel.getConfig().bolts());
        boltsSlider.setPreferredSize(new Dimension(120, 20));
        boltsSlider.addChangeListener(e -> panel.setConfig(panel.getConfig().withBolts(boltsSlider.getValue())));

        JLabel depthLabel = new JLabel("Depth:");
        JSlider depthSlider = new JSlider(1, 12, panel.getConfig().fractalDepth());
        depthSlider.setPreferredSize(new Dimension(120, 20));
        depthSlider.addChangeListener(e -> panel.setConfig(panel.getConfig().withDepth(depthSlider.getValue())));

        JLabel speedLabel = new JLabel("Speed:");
        JSlider speedSlider = new JSlider(1, 100, (int)(panel.getConfig().rotationSpeed()*1000));
        speedSlider.setPreferredSize(new Dimension(120, 20));
        speedSlider.addChangeListener(e -> panel.setConfig(panel.getConfig().withSpeed(speedSlider.getValue()/1000.0)));

        JLabel motionLabel = new JLabel("Motion fade:");
        JSlider motionSlider = new JSlider(0, 50, (int)(panel.getConfig().motionFadeAlpha()*100));
        motionSlider.setPreferredSize(new Dimension(120, 20));
        motionSlider.addChangeListener(e -> panel.setConfig(panel.getConfig().withMotionFade(motionSlider.getValue()/100f)));

        JLabel glowLabel = new JLabel("Glow passes:");
        JSlider glowSlider = new JSlider(1, 8, panel.getConfig().glowPasses());
        glowSlider.setPreferredSize(new Dimension(120, 20));
        glowSlider.addChangeListener(e -> panel.setConfig(panel.getConfig().withGlowPasses(glowSlider.getValue())));

        JLabel hueLabel = new JLabel("Hue:");
        JSlider hueSlider = new JSlider(0, 100, (int)(panel.getConfig().hue()*100));
        hueSlider.setPreferredSize(new Dimension(120, 20));
        hueSlider.addChangeListener(e -> panel.setConfig(panel.getConfig().withHue(hueSlider.getValue()/100f)));

        JComboBox<Preset> presets = new JComboBox<>(Preset.values());
        presets.addActionListener(e -> {
            Preset p = (Preset)presets.getSelectedItem();
            if (p != null) {
                panel.setConfig(p.config);
                boltsSlider.setValue(p.config.bolts());
                depthSlider.setValue(p.config.fractalDepth());
                speedSlider.setValue((int)(p.config.rotationSpeed()*1000));
                motionSlider.setValue((int)(p.config.motionFadeAlpha()*100));
                glowSlider.setValue(p.config.glowPasses());
                hueSlider.setValue((int)(p.config.hue()*100));
            }
        });

        top.add(saveBtn);
        top.add(boltsLabel); top.add(boltsSlider);
        top.add(depthLabel); top.add(depthSlider);
        top.add(speedLabel); top.add(speedSlider);
        top.add(motionLabel); top.add(motionSlider);
        top.add(glowLabel); top.add(glowSlider);
        top.add(hueLabel); top.add(hueSlider);
        top.add(new JLabel("Presets:")); top.add(presets);

        return top;
    }
}

/* -------------------------- RendererPanel -------------------------- */
class RendererPanel extends JPanel {
    private volatile SceneConfig config;
    private final Renderer renderer;

    public RendererPanel(SceneConfig initial) {
        this.config = initial;
        setPreferredSize(new Dimension(1200, 760));
        setBackground(Color.BLACK);
        renderer = new Renderer(initial);

        // Animation timer
        Timer t = new Timer(16, e -> {
            renderer.update(config);
            repaint();
        });
        t.start();
    }

    public SceneConfig getConfig(){ return config; }
    public void setConfig(SceneConfig c){ this.config = c; renderer.setConfig(c); }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        renderer.renderTo((Graphics2D)g, getWidth(), getHeight());
    }

    public void save4KAsync() {
        // Use SwingWorker to avoid freezing UI
        new SwingWorker<Void, Void>(){
            @Override protected Void doInBackground() throws Exception {
                renderer.save4K();
                return null;
            }
            @Override protected void done(){ JOptionPane.showMessageDialog(RendererPanel.this, "4K render finished (tesla_art_4k.png)"); }
        }.execute();
    }
}

/* -------------------------- Renderer (render pipeline) -------------------------- */
class Renderer {
    private SceneConfig config;
    private final LightningGenerator generator = new LightningGenerator();
    private double rotation = 0;

    // Accumulation buffer for motion blur
    private BufferedImage accBuffer = null;

    public Renderer(SceneConfig config) { setConfig(config); }
    public void setConfig(SceneConfig config) { this.config = config; generator.setSeed((long)(config.hue()*100000)); }

    public void update(SceneConfig config) {
        // Update rotation based on config
        rotation += config.rotationSpeed();
    }

    public void renderTo(Graphics2D g2, int w, int h) {
        // Ensure accBuffer size
        if (accBuffer == null || accBuffer.getWidth() != w || accBuffer.getHeight() != h) {
            accBuffer = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = accBuffer.createGraphics();
            g.setColor(Color.BLACK); g.fillRect(0,0,w,h); g.dispose();
        }

        // Fade accumulation buffer (motion blur)
        Graphics2D gAcc = accBuffer.createGraphics();
        gAcc.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f - config.motionFadeAlpha()));
        gAcc.setColor(Color.BLACK);
        gAcc.fillRect(0,0,accBuffer.getWidth(), accBuffer.getHeight());

        // Render current frame into a temporary buffer for post-processing
        BufferedImage frame = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gFrame = frame.createGraphics();
        gFrame.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Background subtle gradient
        paintBackground(gFrame, w, h);

        // Draw glowing coil (center)
        int cx = w/2, cy = h/2;
        drawCoil(gFrame, cx, cy);

        // Draw bolts
        for (int i = 0; i < config.bolts(); i++) {
            double angle = rotation + i * (Math.PI * 2 / config.bolts());
            int len = Math.min(w,h)/2 + generator.random.nextInt(180);
            int ex = cx + (int)(Math.cos(angle)*len);
            int ey = cy + (int)(Math.sin(angle)*len);

            // generate segments (cached per bolt) with a seed influenced by angle
            List<Line2D.Double> segments = generator.generateBolt(cx, cy, ex, ey, config.fractalDepth());

            // draw core then outer strokes
            drawBoltSegments(gFrame, segments, config.hue());
        }

        gFrame.dispose();

        // Glow post-processing: apply a simple iterative box blur onto frame
        BufferedImage glow = applyGlow(frame, config.glowPasses());

        // Composite: draw glow then frame onto accumulation buffer
        gAcc.drawImage(glow, 0, 0, null);
        gAcc.drawImage(frame, 0, 0, null);
        gAcc.dispose();

        // Finally draw accumulation buffer to screen
        g2.drawImage(accBuffer, 0, 0, null);
    }

    private void paintBackground(Graphics2D g, int w, int h) {
        GradientPaint gp = new GradientPaint(0,0, Color.BLACK, w*0.6f, h*0.4f, Color.DARK_GRAY);
        g.setPaint(gp);
        g.fillRect(0,0,w,h);
    }

    private void drawCoil(Graphics2D g, int cx, int cy) {
        for (int i = 6; i >= 1; i--) {
            int r = i*18;
            int alpha = 20 + i*30;
            g.setColor(new Color(80,180,255, Math.min(255, alpha)));
            g.fillOval(cx-r, cy-r, r*2, r*2);
        }
    }

    private void drawBoltSegments(Graphics2D g, List<Line2D.Double> segments, float hue) {
        // draw outer glow strokes
        for (int pass=0; pass<3; pass++) {
            float alpha = (pass==0)?0.12f: (pass==1)?0.22f:0.35f;
            float width = (pass==0)?6f:(pass==1)?3f:1.5f;
            g.setStroke(new BasicStroke(width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            Color c = Color.getHSBColor(hue, 0.8f, 1f);
            g.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), Math.round(alpha*255)));
            for (Line2D.Double s : segments) g.draw(s);
        }
    }

    private BufferedImage applyGlow(BufferedImage src, int passes) {
        BufferedImage tmp = deepCopy(src);
        for (int i=0;i<passes;i++) tmp = boxBlur(tmp);
        return tmp;
    }

    // Simple separable box blur (approximates gaussian after multiple passes)
    private BufferedImage boxBlur(BufferedImage src) {
        int radius = 6; // small kernel — repeat passes for stronger blur
        int w = src.getWidth(), h = src.getHeight();
        BufferedImage tmp = new BufferedImage(w,h, BufferedImage.TYPE_INT_ARGB);

        // horizontal pass
        int[] pixels = src.getRGB(0,0,w,h,null,0,w);
        int[] out = new int[pixels.length];
        int kernel = radius*2+1;
        for (int y=0;y<h;y++){
            long r=0,g=0,b=0,a=0;
            for (int x= -radius; x<=radius; x++) {
                int xx = Math.max(0, Math.min(w-1, x));
                int col = pixels[y*w + xx];
                a += (col>>24)&0xff; r += (col>>16)&0xff; g += (col>>8)&0xff; b += col&0xff;
            }
            for (int x=0;x<w;x++){
                out[y*w + x] = ((int)(a/kernel) <<24) | ((int)(r/kernel)<<16) | ((int)(g/kernel)<<8) | (int)(b/kernel);
                // slide window
                int removeX = x - radius;
                int addX = x + radius + 1;
                int colRem = pixels[y*w + Math.max(0, Math.min(w-1, removeX))];
                int colAdd = pixels[y*w + Math.max(0, Math.min(w-1, addX))];
                a += ((colAdd>>24)&0xff) - ((colRem>>24)&0xff);
                r += ((colAdd>>16)&0xff) - ((colRem>>16)&0xff);
                g += ((colAdd>>8)&0xff) - ((colRem>>8)&0xff);
                b += (colAdd&0xff) - (colRem&0xff);
            }
        }
        tmp.setRGB(0,0,w,h,out,0,w);

        // vertical pass
        int[] pixels2 = tmp.getRGB(0,0,w,h,null,0,w);
        int[] out2 = new int[pixels2.length];
        for (int x=0;x<w;x++){
            long r=0,g=0,b=0,a=0;
            for (int y=-radius;y<=radius;y++){
                int yy = Math.max(0, Math.min(h-1, y));
                int col = pixels2[yy*w + x];
                a += (col>>24)&0xff; r += (col>>16)&0xff; g += (col>>8)&0xff; b += col&0xff;
            }
            for (int y=0;y<h;y++){
                out2[y*w + x] = ((int)(a/kernel) <<24) | ((int)(r/kernel)<<16) | ((int)(g/kernel)<<8) | (int)(b/kernel);
                int removeY = y - radius;
                int addY = y + radius + 1;
                int colRem = pixels2[Math.max(0, Math.min(h-1, removeY))*w + x];
                int colAdd = pixels2[Math.max(0, Math.min(h-1, addY))*w + x];
                a += ((colAdd>>24)&0xff) - ((colRem>>24)&0xff);
                r += ((colAdd>>16)&0xff) - ((colRem>>16)&0xff);
                g += ((colAdd>>8)&0xff) - ((colRem>>8)&0xff);
                b += (colAdd&0xff) - (colRem&0xff);
            }
        }
        BufferedImage outImg = new BufferedImage(w,h, BufferedImage.TYPE_INT_ARGB);
        outImg.setRGB(0,0,w,h,out2,0,w);
        return outImg;
    }

    private static BufferedImage deepCopy(BufferedImage src) {
        BufferedImage copy = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = copy.createGraphics();
        g.drawImage(src,0,0,null); g.dispose();
        return copy;
    }

    public void save4K() {
        int W = 3840, H = 2160;
        BufferedImage img = new BufferedImage(W,H, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        // render using local state but without modifying runtime rotation
        double savedRotation = rotation;
        rotation += 0.123; // small offset for visual variety
        renderTo(g, W, H);
        rotation = savedRotation;
        g.dispose();
        try {
            File out = new File("tesla_art_4k.png");
            ImageIO.write(img, "png", out);
        } catch (Exception ex) { ex.printStackTrace(); }
    }
}

/* -------------------------- LightningGenerator -------------------------- */
class LightningGenerator {
    public final Random random = new Random();

    public void setSeed(long s) { random.setSeed(s); }

    // generates list of segments representing a bolt
    public List<Line2D.Double> generateBolt(int x1, int y1, int x2, int y2, int depth) {
        List<Line2D.Double> list = new ArrayList<>();
        subdivide(list, x1, y1, x2, y2, depth, 1.0);
        return list;
    }

    private void subdivide(List<Line2D.Double> out, double x1, double y1, double x2, double y2, int depth, double disp) {
        double dx = x2 - x1, dy = y2 - y1;
        double dist = Math.hypot(dx, dy);
        if (depth <= 0 || dist < 8) {
            out.add(new Line2D.Double(x1, y1, x2, y2));
            return;
        }

        double mx = (x1 + x2) / 2.0;
        double my = (y1 + y2) / 2.0;

        // perpendicular
        double nx = -dy / dist, ny = dx / dist;
        double maxDisp = disp * Math.min(50, dist * 0.25);
        double displacement = (random.nextDouble()*2 - 1) * maxDisp;
        mx += nx * displacement;
        my += ny * displacement;

        subdivide(out, x1, y1, mx, my, depth-1, disp*0.7);
        subdivide(out, mx, my, x2, y2, depth-1, disp*0.7);

        if (random.nextDouble() < 0.08) {
            double angle = Math.atan2(my - y1, mx - x1) + (random.nextDouble() - 0.5) * Math.PI / 2;
            double len = 20 + random.nextDouble() * 80;
            double bx = mx + Math.cos(angle) * len;
            double by = my + Math.sin(angle) * len;
            subdivide(out, mx, my, bx, by, Math.max(0, depth-2), disp*0.5);
        }
    }
}

/* -------------------------- README (for GitHub) -------------------------- */
/*
README (copy to GitHub)

# Tesla Art Pro (Java)

**Short**: Real-time generative art application in Java (Swing). Creates animated, electric "Tesla-style" bolts with motion blur and glow, includes UI controls and 4K export.

## Features
- Procedural fractal lightning generation
- Motion blur (accumulation buffer)
- Glow post-processing (iterative box blur)
- UI controls: bolts, depth, speed, motion fade, glow passes, hue presets
- 4K export performed in background (SwingWorker)
- Single-file distribution for easy testing; recommended to split into modules for production

## How to run
```
javac TeslaArtProject.java
java TeslaArtProject
```

## Next improvements
- Replace box blur with GPU-accelerated shader (OpenGL)
- Export animated MP4/GIF
- Add presets save/load (JSON)
- Break into Maven project and add unit tests

*/
