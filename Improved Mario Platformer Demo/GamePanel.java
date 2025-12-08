// ИСПРАВЛЕННЫЙ ФАЙЛ: GamePanel.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel implements ActionListener { // KeyListener удален

    private final int GAME_WIDTH = 800;
    private final int GAME_HEIGHT = 600;

    private LevelManager levelManager;
    private InputManager inputManager; // Объявление
    private Timer gameTimer;

    public GamePanel() {
        setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        setBackground(new Color(135, 206, 235));

        levelManager = new LevelManager();
        inputManager = new InputManager(); // Инициализация

        setFocusable(true);
        addKeyListener(inputManager); // Добавляем InputManager как слушателя

        gameTimer = new Timer(16, this);
        gameTimer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        checkInput();
        levelManager.update();
        repaint();
    }

    private void checkInput() {
        int direction = 0;

        if (InputManager.isKeyPressed(KeyEvent.VK_LEFT)) {
            direction = -1;
        }
        if (InputManager.isKeyPressed(KeyEvent.VK_RIGHT)) {
            direction = 1;
        }
        levelManager.getPlayer().setMoveDirection(direction);

        if (InputManager.isKeyPressed(KeyEvent.VK_UP)) {
            levelManager.getPlayer().jump();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Block block : levelManager.getBlocks()) {
            block.draw(g);
        }

        levelManager.getPlayer().draw(g);

        Toolkit.getDefaultToolkit().sync();
    }
}
