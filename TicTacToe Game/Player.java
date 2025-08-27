// Класс Player представляет игрока в игре
public class Player {
    // Поля класса:
    private final String name;   // Имя игрока (только для чтения)
    private final char symbol;   // Символ игрока: 'X' или 'O' (только для чтения)

    // Конструктор класса - создает нового игрока
    public Player(String name, char symbol) {
        this.name = name;       // Сохраняет переданное имя
        this.symbol = symbol;   // Сохраняет переданный символ
    }

    // Метод для получения имени игрока
    public String getName() {
        return name;  // Возвращает имя игрока
    }

    // Метод для получения символа игрока
    public char getSymbol() {
        return symbol;  // Возвращает символ игрока ('X' или 'O')
    }
}
