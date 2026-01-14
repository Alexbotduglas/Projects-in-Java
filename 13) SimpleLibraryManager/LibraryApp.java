import java.io.*;    // Библиотеки для ввода-вывода (работа с файлами)
import java.util.*;  // Библиотеки для коллекций (списки, сканер)

/**
 * Основной класс приложения.
 * Реализует консольный интерфейс и управление хранилищем.
 */
public class LibraryApp {
    // Константа: имя файла. Изменив её здесь, вы измените имя во всей программе.
    private static final String FILE_NAME = "library.csv";

    // Список в оперативной памяти, где хранятся все созданные объекты Book.
    // Используем List (интерфейс) и ArrayList (реализация).
    private static List<Book> library = new ArrayList<>();

    /**
     * Главный метод (точка входа). С него начинается выполнение программы.
     */
    public static void main(String[] args) {
        // 1. При старте пытаемся загрузить данные из файла в список library
        loadFromFile();

        // Объект Scanner для считывания ввода пользователя из консоли
        Scanner scanner = new Scanner(System.in);

        // Бесконечный цикл: программа работает, пока пользователь не выберет "Выход"
        while (true) {
            System.out.println("\n--- МИНИ-БИБЛИОТЕКА ---");
            System.out.println("1. Показать список книг");
            System.out.println("2. Добавить новую книгу");
            System.out.println("3. Сохранить изменения и выйти");
            System.out.print("Выберите пункт меню (1-3): ");

            // Проверка: ввел ли пользователь число
            if (!scanner.hasNextInt()) {
                System.out.println("Ошибка! Введите число.");
                scanner.next(); // Пропускаем некорректный ввод
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine(); // Очистка "хвоста" строки после считывания числа

            switch (choice) {
                case 1:
                    showBooks();
                    break;
                case 2:
                    addBook(scanner);
                    break;
                case 3:
                    saveToFile(); // Сохраняем перед закрытием
                    System.out.println("Программа завершена. Данные в безопасности!");
                    return; // Выход из метода main (и всей программы)
                default:
                    System.out.println("Такого пункта нет. Попробуйте снова.");
            }
        }
    }

    /**
     * Запрашивает данные у пользователя и создает объект Book.
     */
    private static void addBook(Scanner scanner) {
        System.out.print("Введите название книги: ");
        String title = scanner.nextLine();
        System.out.print("Введите автора: ");
        String author = scanner.nextLine();
        System.out.print("Введите год издания: ");
        int year = scanner.nextInt();

        // Добавляем созданный объект в наш список (в оперативную память)
        library.add(new Book(title, author, year));
        System.out.println("Готово! Книга временно сохранена в памяти.");
    }

    /**
     * Выводит содержимое списка в консоль.
     */
    private static void showBooks() {
        if (library.isEmpty()) {
            System.out.println("Библиотека пуста. Сначала добавьте книги.");
        } else {
            // Перебираем список и печатаем каждый объект
            for (Book b : library) {
                System.out.println(b); // Здесь автоматически сработает метод toString()
            }
        }
    }

    /**
     * Записывает данные из списка library в текстовый файл library.csv.
     */
    private static void saveToFile() {
        // try-with-resources: автоматически закроет файл даже при ошибке
        // FileWriter(FILE_NAME) открывает файл для записи
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Book book : library) {
                // Превращаем объект в CSV-строку и пишем в файл
                writer.println(book.toCsvRow());
            }
        } catch (IOException e) {
            System.out.println("Ошибка записи в файл: " + e.getMessage());
        }
    }

    /**
     * Читает файл построчно и превращает текст обратно в объекты Java.
     */
    private static void loadFromFile() {
        File file = new File(FILE_NAME);
        // Если файла еще нет (первый запуск), просто выходим из метода
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            // Читаем файл, пока строки не закончатся
            while ((line = reader.readLine()) != null) {
                // Разбиваем строку по запятой: "Название,Автор,Год" -> ["Название", "Автор", "Год"]
                String[] data = line.split(",");
                if (data.length == 3) {
                    // Создаем объект на основе данных из файла
                    String title = data[0];
                    String author = data[1];
                    int year = Integer.parseInt(data[2]); // Превращаем текст в число

                    library.add(new Book(title, author, year));
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }
    }
}
