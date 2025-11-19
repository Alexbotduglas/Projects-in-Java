/**
 * Класс Task представляет одну задачу в списке дел
 * Содержит основную информацию о задаче и методы для работы с ней
 */
public class Task {
    // Поля класса (переменные для хранения данных)
    private int id;              // Уникальный идентификатор задачи
    private String description;  // Описание задачи (текст)
    private boolean isCompleted; // Статус выполнения (true - выполнена, false - не выполнена)

    /**
     * Конструктор класса Task - создает новую задачу
     * @param id - уникальный номер задачи
     * @param description - текст задачи
     */
    public Task(int id, String description) {
        this.id = id;                    // Устанавливаем ID
        this.description = description;  // Устанавливаем описание
        this.isCompleted = false;        // Новая задача всегда не выполнена
    }

    // Методы доступа (геттеры) - позволяют получить значения полей

    /**
     * @return ID задачи
     */
    public int getId() { return id; }

    /**
     * @return описание задачи
     */
    public String getDescription() { return description; }

    /**
     * @return статус выполнения задачи
     */
    public boolean isCompleted() { return isCompleted; }

    // Методы изменения (сеттеры) - позволяют изменить значения полей

    /**
     * Изменяет описание задачи
     * @param description - новое описание
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Отмечает задачу как выполненную
     * Меняет статус isCompleted на true
     */
    public void markAsCompleted() {
        this.isCompleted = true;
    }

    /**
     * Преобразует задачу в строку для красивого вывода
     * @return строка в формате "[✓] 1. Текст задачи" или "[ ] 1. Текст задачи"
     */
    @Override
    public String toString() {
        // Выбираем символ в зависимости от статуса: ✓ для выполненных, пробел для невыполненных
        String status = isCompleted ? "✓" : " ";
        // Форматируем строку: [статус] номер. описание
        return String.format("[%s] %d. %s", status, id, description);
    }
}
