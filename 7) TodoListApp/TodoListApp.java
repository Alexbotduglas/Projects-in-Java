// Импортируем класс Scanner для чтения ввода пользователя
import java.util.Scanner;

/**
 * Главный класс приложения TodoList
 * Управляет пользовательским интерфейсом и основным потоком программы
 */
public class TodoListApp {
    // Поля класса
    private TodoList todoList;  // Объект для управления задачами
    private Scanner scanner;    // Объект для чтения ввода пользователя

    /**
     * Конструктор - инициализирует необходимые объекты
     */
    public TodoListApp() {
        this.todoList = new TodoList();  // Создаем новый список задач
        this.scanner = new Scanner(System.in);  // Создаем сканер для чтения ввода
    }

    /**
     * Главный метод, который запускает приложение
     * Содержит основной цикл программы
     */
    public void run() {
        // Выводим приветственное сообщение
        System.out.println("=== TODO LIST ===");

        // Основной цикл программы - работает пока пользователь не выберет выход
        while (true) {
            showMenu();  // Показываем меню пользователю
            String choice = scanner.nextLine();  // Читаем выбор пользователя

            // Обрабатываем выбор пользователя с помощью switch
            switch (choice) {
                case "1":
                    addTask();  // Вызываем метод добавления задачи
                    break;
                case "2":
                    todoList.showAllTasks();  // Показываем все задачи
                    break;
                case "3":
                    markTaskCompleted();  // Отмечаем задачу выполненной
                    break;
                case "4":
                    deleteTask();  // Удаляем задачу
                    break;
                case "0":
                    System.out.println("До свидания!");  // Прощальное сообщение
                    return;  // Выходим из метода run() и завершаем программу
                default:
                    System.out.println("Неверный выбор!");  // Сообщение об ошибке
            }
        }
    }

    /**
     * Показывает главное меню пользователю
     * Выводит все доступные опции
     */
    private void showMenu() {
        System.out.println("\n1. Добавить задачу");
        System.out.println("2. Показать все задачи");
        System.out.println("3. Отметить задачу выполненной");
        System.out.println("4. Удалить задачу");
        System.out.println("0. Выход");
        System.out.print("Выберите: ");  // Приглашение для ввода
    }

    /**
     * Метод для добавления новой задачи
     * Запрашивает у пользователя описание задачи и добавляет её в список
     */
    private void addTask() {
        System.out.print("Введите задачу: ");
        String task = scanner.nextLine();  // Читаем описание задачи
        todoList.addTask(task);  // Добавляем задачу через TodoList
    }

    /**
     * Метод для отметки задачи как выполненной
     * Запрашивает ID задачи и отмечает её выполненной
     */
    private void markTaskCompleted() {
        System.out.print("Введите ID задачи: ");
        try {
            // Пытаемся преобразовать ввод пользователя в число
            int id = Integer.parseInt(scanner.nextLine());
            todoList.markTaskAsCompleted(id);  // Отмечаем задачу выполненной
        } catch (NumberFormatException e) {
            // Если введено не число - выводим сообщение об ошибке
            System.out.println("Ошибка: введите число!");
        }
    }

    /**
     * Метод для удаления задачи
     * Запрашивает ID задачи и удаляет её из списка
     */
    private void deleteTask() {
        System.out.print("Введите ID задачи для удаления: ");
        try {
            // Пытаемся преобразовать ввод пользователя в число
            int id = Integer.parseInt(scanner.nextLine());
            todoList.deleteTask(id);  // Удаляем задачу через TodoList
        } catch (NumberFormatException e) {
            // Если введено не число - выводим сообщение об ошибке
            System.out.println("Ошибка: введите число!");
        }
    }

    /**
     * Точка входа в программу
     * Создает экземпляр приложения и запускает его
     * @param args - аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        // Создаем экземпляр приложения
        TodoListApp app = new TodoListApp();
        // Запускаем приложение
        app.run();
    }
}
