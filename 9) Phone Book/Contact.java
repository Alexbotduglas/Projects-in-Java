public class Contact {
    private String name;
    private String phoneNumber;

    // Конструктор
    public Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    // Геттеры
    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    // Переопределение toString() для удобного вывода
    @Override
    public String toString() {
        return String.format("Имя: %-20s | Телефон: %s", name, phoneNumber);
    }

    // Метод для подготовки строки контакта для записи в файл
    // Мы используем разделитель ";", чтобы потом легко его считать.
    public String toFileString() {
        return name + ";" + phoneNumber;
    }
}
