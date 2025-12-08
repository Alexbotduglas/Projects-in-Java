import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// Главный класс игры, наследуется от JPanel для рисования
public class SimplePlatformer extends JPanel implements ActionListener, KeyListener {

    private final int TILE_SIZE = 50; // Размер персонажа и блоков
    private final int GAME_WIDTH = 800;
    private final int GAME_HEIGHT = 600;

    // --- Параметры игрока (Марио) ---
    private int playerX = 50;
    private int playerY = 400;
    private double velY = 0; // Вертикальная скорость (для гравитации/прыжка)
    private int velX = 0;   // Горизонтальная скорость
    private boolean isJumping = false;
    private boolean canJump = true;

    // Константы физики
    private final double GRAVITY = 1.0;
    private final double JUMP_STRENGTH = -18.0;
    private final int MAX_SPEED = 5;

    // Таймер для игрового цикла
    private Timer gameTimer;

    public SimplePlatformer() {
        setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        setBackground(Color.CYAN); // Небо
        setFocusable(true);
        addKeyListener(this);

        // Игровой цикл: обновляем игру 60 раз в секунду (1000 мс / 60 кадров ≈ 16 мс)
        gameTimer = new Timer(16, this);
        gameTimer.start();
    }

    // --- ГЛАВНЫЙ ИГРОВОЙ ЦИКЛ (Вызывается таймером) ---
    @Override
    public void actionPerformed(ActionEvent e) {
        updateGame();
        repaint(); // Перерисовка экрана
    }

    // Обновление физики и позиции
    private void updateGame() {
        // 1. ПРИМЕНЕНИЕ ГРАВИТАЦИИ
        velY += GRAVITY;

        // 2. ДВИЖЕНИЕ ПО X
        playerX += velX;

        // 3. ДВИЖЕНИЕ ПО Y И КОЛЛИЗИИ
        playerY += velY;

        // Простейшая коллизия с полом (уровень земли)
        int groundY = GAME_HEIGHT - TILE_SIZE - 50; // Высота земли

        if (playerY >= groundY) {
            playerY = groundY; // Устанавливаем на землю
            velY = 0;          // Обнуляем вертикальную скорость
            canJump = true;    // Разрешаем новый прыжок
            isJumping = false;
        }

        // Ограничение движения по горизонтали
        if (playerX < 0) playerX = 0;
        if (playerX > GAME_WIDTH - TILE_SIZE) playerX = GAME_WIDTH - TILE_SIZE;
    }

    // --- ОТРИСОВКА (RENDER) ---
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 1. Рисуем землю/пол (зеленый прямоугольник)
        g.setColor(Color.GREEN.darker());
        g.fillRect(0, GAME_HEIGHT - 50, GAME_WIDTH, 50);

        // 2. Рисуем Марио (красный квадрат)
        g.setColor(Color.RED);
        g.fillRect(playerX, playerY, TILE_SIZE, TILE_SIZE);

        // 3. Дополнительная информация
        g.setColor(Color.BLACK);
        g.drawString("X: " + playerX + ", Y: " + playerY, 10, 20);
        g.drawString("Используйте ← и → для движения, ↑ для прыжка.", 10, 40);

        Toolkit.getDefaultToolkit().sync();
    }

    // --- ОБРАБОТКА ВВОДА С КЛАВИАТУРЫ ---

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            velX = -MAX_SPEED;
        }
        if (key == KeyEvent.VK_RIGHT) {
            velX = MAX_SPEED;
        }
        if (key == KeyEvent.VK_UP && canJump) {
            velY = JUMP_STRENGTH; // Начальный импульс вверх
            canJump = false;       // Запрещаем прыжок в воздухе
            isJumping = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        // Останавливаем горизонтальное движение при отпускании клавиш
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
            velX = 0;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Не используется
    }

    // --- МЕТОД ЗАПУСКА ПРОГРАММЫ ---
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Simple Platformer (Mario Demo)");
            frame.add(new SimplePlatformer());
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            frame.setVisible(true);
        });
    }
}
