import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Color;

public class Block {
    private final int x, y, width, height;

    public Block(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    // Возвращает ограничивающий прямоугольник для проверки коллизий
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void draw(Graphics g) {
        g.setColor(Color.GREEN.darker());
        g.fillRect(x, y, width, height);
    }
}
