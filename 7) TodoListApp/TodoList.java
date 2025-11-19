// Импортируем необходимые классы для работы с коллекциями
import java.util.ArrayList;
import java.util.List;

/**
 * Класс TodoList управляет коллекцией задач
 * Содержит методы для добавления, удаления, изменения и отображения задач
 */
public class TodoList {
    // Поля класса
    private List<Task> tasks;  // Список для хранения всех задач
    private int nextId;        // Счетчик для генерации следующих ID

    /**
     * Конструктор - создает новый пустой список задач
     */
    public TodoList() {
        this.tasks = new ArrayList<>();  // Инициализируем пустой список
        this.nextId = 1;                 // Начинаем нумерацию с 1
    }

    /**
     * Добавляет новую задачу в список
     * @param description - текст новой задачи
     */
    public void addTask(String description) {
        // Создаем новую задачу с текущим nextId
        Task task = new Task(nextId++, description);
        // Добавляем задачу в список
        tasks.add(task);
        // Выводим сообщение об успешном добавлении
        System.out.println("Задача добавлена: " + description);
    }

    /**
     * Показывает все задачи в списке
     * Выводит в консоль все задачи с их статусами
     */
    public void showAllTasks() {
        // Проверяем, есть ли задачи в списке
        if (tasks.isEmpty()) {
            System.out.println("Список задач пуст!");
            return;  // Выходим из метода, если список пуст
        }

        // Выводим заголовок
        System.out.println("\n=== ВАШИ ЗАДАЧИ ===");
        // Проходим по всем задачам и выводим каждую
        for (Task task : tasks) {
            System.out.println(task);  // Используем метод toString() класса Task
        }
        // Выводим разделитель
        System.out.println("===================\n");
    }

    /**
     * Отмечает задачу как выполненную по ID
     * @param taskId - ID задачи, которую нужно отметить выполненной
     */
    public void markTaskAsCompleted(int taskId) {
        // Ищем задачу с указанным ID
        for (Task task : tasks) {
            if (task.getId() == taskId) {
                // Если нашли - отмечаем как выполненную
                task.markAsCompleted();
                System.out.println("Задача выполнена: " + task.getDescription());
                return;  // Выходим из метода после успешного выполнения
            }
        }
        // Если задача не найдена - выводим сообщение об ошибке
        System.out.println("Задача с ID " + taskId + " не найдена!");
    }

    /**
     * Удаляет задачу из списка по ID
     * @param taskId - ID задачи для удаления
     */
    public void deleteTask(int taskId) {
        Task taskToRemove = null;  // Переменная для хранения задачи, которую нужно удалить

        // Ищем задачу с указанным ID
        for (Task task : tasks) {
            if (task.getId() == taskId) {
                taskToRemove = task;  // Сохраняем найденную задачу
                break;  // Прерываем цикл, так как задача найдена
            }
        }

        // Проверяем, нашли ли мы задачу для удаления
        if (taskToRemove != null) {
            tasks.remove(taskToRemove);  // Удаляем задачу из списка
            System.out.println("Задача удалена: " + taskToRemove.getDescription());
        } else {
            // Если задача не найдена - выводим сообщение об ошибке
            System.out.println("Задача с ID " + taskId + " не найдена!");
        }
    }
}
