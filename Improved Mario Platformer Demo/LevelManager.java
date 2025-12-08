// Новый файл: LevelManager.java
import java.util.ArrayList;
import java.util.List;

public class LevelManager {
    private Player player;
    private List<Block> blocks;
    private final int GAME_WIDTH = 800;
    private final int GAME_HEIGHT = 600;
    private final int TILE_SIZE = 50;

    public LevelManager() {
        initLevel();
    }

    private void initLevel() {
        player = new Player(50, 400);
        blocks = new ArrayList<>();

        // Создание пола
        blocks.add(new Block(0, GAME_HEIGHT - TILE_SIZE, GAME_WIDTH, TILE_SIZE));

        // Добавление платформ
        blocks.add(new Block(200, 450, 150, 20));
        blocks.add(new Block(450, 300, 200, 20));
        blocks.add(new Block(50, 200, 100, 20));

        // Дополнительный блок для теста боковой коллизии
        blocks.add(new Block(300, 350, 50, 100));
    }

    public void update() {
        player.update(blocks);
    }

    // Геттеры для доступа к объектам из GamePanel
    public Player getPlayer() { return player; }
    public List<Block> getBlocks() { return blocks; }
}
