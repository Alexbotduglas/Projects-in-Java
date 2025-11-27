import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;
import javax.imageio.ImageIO;

public class TeslaArt extends JPanel {

    // ===== НАСТРОЙКИ =====
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 800;

    // 4K для сохранения
    private static final int RENDER_4K_W = 3840;
    private static final int RENDER_4K_H = 2160;

    private final Random random = new Random();
    private double rotation = 0;

    private Timer timer;

    public TeslaArt() {
        setBackground(Color.BLACK);

        // АНИМАЦИЯ (≈60 FPS)
        timer = new Timer(16, e -> {
            rotation += 0.02;
            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawScene((Graphics2D) g, getWidth(), getHeight());
    }

    // ===== ОСНОВНАЯ СЦЕНА =====
    private void drawScene(Graphics2D g2, int w, int h) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int cx = w / 2;
        int cy = h / 2;

        drawGlow(g2, cx, cy);

        int bolts = 8;

        for (int i = 0; i < bolts; i++) {
            double angle = rotation + i * (Math.PI * 2 / bolts);
            int length = Math.min(w, h) / 2 + random.nextInt(200);

            int ex = cx + (int) (Math.cos(angle) * length);
            int ey = cy + (int) (Math.sin(angle) * length);

            g2.setStroke(new BasicStroke(2f));
            g2.setColor(new Color(120, 200, 255, 180));
            drawLightning(g2, cx, cy, ex, ey, 8);
        }
    }

    // ===== ФРАКТАЛЬНАЯ МОЛНИЯ =====
    private void drawLightning(Graphics2D g, int x1, int y1, int x2, int y2, int depth) {
        if (depth <= 0) {
            g.drawLine(x1, y1, x2, y2);
            return;
        }

        int mx = (x1 + x2) / 2 + random.nextInt(40) - 20;
        int my = (y1 + y2) / 2 + random.nextInt(40) - 20;

        drawLightning(g, x1, y1, mx, my, depth - 1);
        drawLightning(g, mx, my, x2, y2, depth - 1);

        if (random.nextDouble() < 0.2) {
            int bx = mx + random.nextInt(60) - 30;
            int by = my + random.nextInt(60) - 30;
            drawLightning(g, mx, my, bx, by, depth - 1);
        }
    }

    // ===== СВЕЧЕНИЕ =====
    private void drawGlow(Graphics2D g, int x, int y) {
        for (int r = 120; r > 0; r -= 10) {
            int alpha = Math.max(8, r);
            g.setColor(new Color(80, 180, 255, alpha));
            g.fillOval(x - r, y - r, r * 2, r * 2);
        }
    }

    // ===== СОХРАНЕНИЕ 4K =====
    private void save4K() {
        BufferedImage img = new BufferedImage(RENDER_4K_W, RENDER_4K_H, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, RENDER_4K_W, RENDER_4K_H);

        drawScene(g2, RENDER_4K_W, RENDER_4K_H);
        g2.dispose();

        try {
            ImageIO.write(img, "png", new File("tesla_art_4k.png"));
            JOptionPane.showMessageDialog(this, "Saved: tesla_art_4k.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===== MAIN =====
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Tesla Art Pro — Generative Java");

            TeslaArt panel = new TeslaArt();

            JButton saveBtn = new JButton("Save 4K PNG");
            saveBtn.addActionListener((ActionEvent e) -> panel.save4K());

            JPanel top = new JPanel();
            top.add(saveBtn);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(WIDTH, HEIGHT);
            frame.setLayout(new BorderLayout());
            frame.add(top, BorderLayout.NORTH);
            frame.add(panel, BorderLayout.CENTER);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
