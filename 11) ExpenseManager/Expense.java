import java.time.LocalDate; // Импортируем класс для работы с датами без времени (ГГГГ-ММ-ДД)
import java.time.format.DateTimeParseException; // Импортируем исключение для обработки ошибок парсинга даты

/**
 * Класс, представляющий одну запись о расходе.
 * Это модель данных в нашем проекте.
 */
public class Expense {
    private double amount;     // Приватное поле: Сумма расхода. Тип double выбран для хранения дробных значений.
    private String category;   // Приватное поле: Категория расхода (например, 'Еда').
    private LocalDate date;    // Приватное поле: Дата расхода.

    /**
     * Конструктор для создания нового расхода, обычно используется при вводе данных пользователем.
     * @param amount Сумма расхода.
     * @param category Категория расхода.
     * @param dateStr Дата расхода в виде строки, которую нужно преобразовать.
     * @throws DateTimeParseException Пробрасывается, если строка даты не соответствует формату.
     */
    public Expense(double amount, String category, String dateStr) throws DateTimeParseException {
        this.amount = amount; // Присваиваем полю 'amount' значение, переданное в конструктор
        this.category = category; // Присваиваем полю 'category' значение
        this.date = LocalDate.parse(dateStr); // Преобразуем строку 'dateStr' в объект LocalDate
    }

    /**
     * Конструктор для создания расхода при загрузке данных из файла.
     * Здесь дата уже представлена в виде объекта LocalDate.
     */
    public Expense(double amount, String category, LocalDate date) {
        this.amount = amount; // Инициализация суммы
        this.category = category; // Инициализация категории
        this.date = date; // Инициализация объекта даты
    }

    // --- Getters (Методы доступа) ---

    public double getAmount() {
        return amount; // Возвращает сумму расхода
    }

    public String getCategory() {
        return category; // Возвращает категорию
    }

    public LocalDate getDate() {
        return date; // Возвращает дату
    }

    /**
     * Переопределение стандартного метода для форматирования расхода
     * для красивого табличного вывода в консоль.
     * @return Отформатированная строка для консоли.
     */
    @Override
    public String toString() {
        // Форматирует строку, используя фиксированную ширину колонок:
        // %-10s (Дата), %-15s (Категория), %-8.2f р. (Сумма с двумя знаками после запятой)
        return String.format("| %-10s | %-15s | %-8.2f р.",
                date.toString(),
                category,
                amount);
    }

    /**
     * Метод для форматирования расхода перед сохранением в файл.
     * Используется формат, разделенный запятыми (CSV-подобный).
     * @return Строка в формате: СУММА,КАТЕГОРИЯ,ДАТА.
     */
    public String toFileString() {
        return amount + "," + category + "," + date.toString(); // Конкатенация полей с запятыми
    }
}
