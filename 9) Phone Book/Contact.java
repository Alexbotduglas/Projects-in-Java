/**
 * Класс Contact представляет собой одну запись (контакт) в телефонной книге.
 * Реализует принципы ООП (инкапсуляция).
 */
public class Contact {

    // Приватные поля (атрибуты) класса.
    // Приватность обеспечивает инкапсуляцию, доступ к ним только через геттеры.
    private String name;
    private String phoneNumber;

    /**
     * Конструктор класса Contact. Используется для создания нового объекта.
     * @param name Имя контакта.
     * @param phoneNumber Номер телефона контакта.
     */
    public Contact(String name, String phoneNumber) {
        this.name = name; // Присваивание переданного имени полю name
        this.phoneNumber = phoneNumber; // Присваивание переданного номера полю phoneNumber
    }

    // --- Геттеры (Методы доступа) ---

    /**
     * Возвращает имя контакта.
     * @return Имя контакта (String).
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает номер телефона контакта.
     * @return Номер телефона контакта (String).
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Переопределение стандартного метода toString() класса Object.
     * Используется для удобного форматированного вывода контакта в консоль.
     * @return Строковое представление контакта.
     */
    @Override
    public String toString() {
        // Форматирование для красивого выравнивания: %-20s резервирует 20 символов и выравнивает по левому краю
        return String.format("Имя: %-20s | Телефон: %s", name, phoneNumber);
    }

    /**
     * Специальный метод для подготовки данных контакта к записи в файл.
     * Использует разделитель FIELD_SEPARATOR (указанный в PhoneBookManager).
     * @return Строка в формате "Имя;НомерТелефона".
     */
    public String toFileString() {
        return name + ";" + phoneNumber;
    }
}
