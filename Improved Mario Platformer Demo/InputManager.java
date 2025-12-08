// Новый файл: InputManager.java
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

public class InputManager implements KeyListener {
    private static Map<Integer, Boolean> keyMap = new HashMap<>();

    public InputManager() {
        keyMap.put(KeyEvent.VK_LEFT, false);
        keyMap.put(KeyEvent.VK_RIGHT, false);
        keyMap.put(KeyEvent.VK_UP, false);
    }

    public static boolean isKeyPressed(int keyCode) {
        // Гарантируем, что возвращаем false, если клавиши нет в map
        return keyMap.getOrDefault(keyCode, false);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (keyMap.containsKey(e.getKeyCode())) {
            keyMap.put(e.getKeyCode(), true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (keyMap.containsKey(e.getKeyCode())) {
            keyMap.put(e.getKeyCode(), false);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) { /* Не используется */ }
}
