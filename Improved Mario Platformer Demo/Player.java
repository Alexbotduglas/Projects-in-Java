// ИСПРАВЛЕННЫЙ ФАЙЛ: Player.java
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.geom.Point2D; // !!! ВАЖНО: Добавлен импорт Point2D
import java.util.List;

public class Player {
    // Используем Point2D.Double для хранения точных координат и скорости
    private Point2D.Double position;
    private Point2D.Double velocity;

    private final int width = 50, height = 50;

    private final double GRAVITY = 1.0;
    private final double JUMP_STRENGTH = -18.0;
    private final int MAX_SPEED = 5;

    private boolean onGround = false;

    public Player(int startX, int startY) {
        this.position = new Point2D.Double(startX, startY);
        this.velocity = new Point2D.Double(0, 0);
    }

    public void setMoveDirection(int direction) {
        velocity.x = direction * MAX_SPEED;
    }

    public void jump() {
        if (onGround) {
            velocity.y = JUMP_STRENGTH;
            onGround = false;
        }
    }

    public void update(List<Block> blocks) {
        // 1. Применяем гравитацию
        velocity.y += GRAVITY;

        // 2. Движение по X с проверкой коллизий
        position.x += velocity.x;
        handleHorizontalCollision(blocks);

        // 3. Движение по Y с проверкой коллизий
        position.y += velocity.y;
        handleVerticalCollision(blocks);
    }

    private void handleHorizontalCollision(List<Block> blocks) {
        Rectangle bounds = getBounds();
        for (Block block : blocks) {
            if (bounds.intersects(block.getBounds())) {

                if (velocity.x > 0) { // Движение вправо
                    // Откатываем X
                    position.x = block.getBounds().getX() - width;
                } else if (velocity.x < 0) { // Движение влево
                    // Откатываем X
                    position.x = block.getBounds().getX() + block.getBounds().getWidth();
                }
                velocity.x = 0;
            }
        }
    }

    private void handleVerticalCollision(List<Block> blocks) {
        Rectangle bounds = getBounds();
        onGround = false;

        for (Block block : blocks) {
            if (bounds.intersects(block.getBounds())) {

                if (velocity.y > 0) { // Падение
                    position.y = block.getBounds().getY() - height;
                    onGround = true;
                } else if (velocity.y < 0) { // Удар головой
                    position.y = block.getBounds().getY() + block.getBounds().getHeight();
                }
                velocity.y = 0;
                return; // Коллизия обработана
            }
        }

        // Проверка на падение за пределы экрана
        if (position.y > 700) {
            position.x = 50;
            position.y = 50;
            velocity.y = 0;
            onGround = true;
        }
    }

    // --- Вспомогательные методы ---
    public Rectangle getBounds() {
        return new Rectangle((int) position.x, (int) position.y, width, height);
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect((int) position.x, (int) position.y, width, height);
    }
}
