/**
 * Класс Book (Книга) — это модель данных.
 * Он описывает, какие свойства есть у каждой книги в нашей системе.
 */
public class Book {
    // Поля класса (атрибуты объекта)
    // private означает, что доступ к ним есть только внутри этого класса (инкапсуляция)
    private String title;  // Название книги
    private String author; // Имя автора
    private int year;      // Год издания

    /**
     * Конструктор класса. Вызывается при создании нового объекта (через new Book(...)).
     * @param title  принимает название
     * @param author принимает автора
     * @param year   принимает год
     */
    public Book(String title, String author, int year) {
        // this указывает, что мы присваиваем значение полю текущего объекта
        this.title = title;
        this.author = author;
        this.year = year;
    }

    /**
     * Метод для формирования строки сохранения.
     * Мы объединяем данные через запятую, чтобы файл .csv понимал, где заканчивается одно поле и начинается другое.
     * @return строка вида "Название,Автор,Год"
     */
    public String toCsvRow() {
        return title + "," + author + "," + year;
    }

    /**
     * Переопределение стандартного метода toString.
     * Нужен для того, чтобы при выводе объекта в консоль (System.out.println)
     * мы видели текст, а не адрес объекта в памяти.
     */
    @Override
    public String toString() {
        // String.format позволяет удобно подставлять переменные в шаблон строки
        return String.format("Книга: %s | Автор: %s | Год: %d", title, author, year);
    }
}
