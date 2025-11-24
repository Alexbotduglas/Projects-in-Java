import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Создаем менеджер, который автоматически загрузит данные при старте
        PhoneBookManager manager = new PhoneBookManager();
        boolean running = true;

        System.out.println("--- 📞 Добро пожаловать в Телефонную книгу ---");

        // Основной цикл программы
        while (running) {
            displayMenu();

            // Проверка на ввод числа
            if (!scanner.hasNextInt()) {
                System.out.println("🚫 Некорректный ввод. Пожалуйста, введите число от 1 до 4.");
                scanner.next(); // Очистить некорректный ввод
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine(); // Очистка буфера после nextInt()

            switch (choice) {
                case 1:
                    addNewContact(scanner, manager);
                    break;
                case 2:
                    manager.viewAllContacts();
                    break;
                case 3:
                    searchContact(scanner, manager);
                    break;
                case 4:
                    // Сохраняем данные перед выходом!
                    manager.saveContactsToFile();
                    running = false;
                    System.out.println("👋 Программа завершена. До свидания!");
                    break;
                default:
                    System.out.println("🚫 Неизвестная команда. Пожалуйста, выберите пункт от 1 до 4.");
            }
        }
        scanner.close();
    }

    /**
     * Выводит меню в консоль.
     */
    private static void displayMenu() {
        System.out.println("\n----------------------------------------");
        System.out.println("1. ➕ Добавить контакт");
        System.out.println("2. 📄 Просмотреть все контакты");
        System.out.println("3. 🔍 Найти контакт по имени");
        System.out.println("4. 🚪 Выход (и сохранение)");
        System.out.print(">>> Ваш выбор: ");
    }

    /**
     * Логика добавления нового контакта.
     */
    private static void addNewContact(Scanner scanner, PhoneBookManager manager) {
        System.out.print("Введите имя: ");
        String name = scanner.nextLine().trim();

        System.out.print("Введите номер телефона: ");
        String phone = scanner.nextLine().trim();

        if (name.isEmpty() || phone.isEmpty()) {
            System.out.println("🚫 Имя и номер телефона не могут быть пустыми.");
            return;
        }

        Contact newContact = new Contact(name, phone);
        manager.addContact(newContact);
    }

    /**
     * Логика поиска контакта.
     */
    private static void searchContact(Scanner scanner, PhoneBookManager manager) {
        System.out.print("Введите часть имени для поиска: ");
        String searchName = scanner.nextLine().trim();

        List<Contact> foundContacts = manager.findContact(searchName);

        if (foundContacts.isEmpty()) {
            System.out.println("🤷‍♀️ Контакты по запросу '" + searchName + "' не найдены.");
            return;
        }

        System.out.println("\n--- Результаты поиска по '" + searchName + "' (Найдено: " + foundContacts.size() + ") ---");
        for (Contact contact : foundContacts) {
            System.out.println(contact);
        }
        System.out.println("----------------------------------------");
    }
}
