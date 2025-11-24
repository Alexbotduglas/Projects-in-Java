import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PhoneBookManager {
    // Список для хранения контактов в памяти
    private List<Contact> contacts;
    private static final String FILENAME = "phonebook.txt";
    private static final String FIELD_SEPARATOR = ";";

    public PhoneBookManager() {
        this.contacts = new ArrayList<>();
        // Загружаем контакты из файла сразу при создании менеджера
        loadContactsFromFile();
    }

    /**
     * Добавляет новый контакт в список.
     */
    public void addContact(Contact contact) {
        contacts.add(contact);
        System.out.println("✅ Контакт '" + contact.getName() + "' добавлен.");
    }

    /**
     * Выводит все контакты в консоль.
     */
    public void viewAllContacts() {
        if (contacts.isEmpty()) {
            System.out.println("🤷‍♀️ Телефонная книга пуста.");
            return;
        }

        System.out.println("\n--- Список контактов (Всего: " + contacts.size() + ") ---");
        for (Contact contact : contacts) {
            System.out.println(contact);
        }
        System.out.println("----------------------------------------");
    }

    /**
     * Ищет контакты по имени (частичное совпадение, без учета регистра).
     */
    public List<Contact> findContact(String searchName) {
        String lowerCaseSearchName = searchName.toLowerCase();

        // Используем Stream API для фильтрации (современный Java)
        return contacts.stream()
                .filter(c -> c.getName().toLowerCase().contains(lowerCaseSearchName))
                .collect(Collectors.toList());
    }

    /**
     * Сохраняет текущий список контактов в файл (использует FileWriter).
     */
    public void saveContactsToFile() {
        // Используем try-with-resources для автоматического закрытия ресурсов
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILENAME))) {
            for (Contact contact : contacts) {
                // Используем метод toFileString() класса Contact
                writer.println(contact.toFileString());
            }
            System.out.println("\n💾 Данные успешно сохранены в файл: " + FILENAME);
        } catch (IOException e) {
            System.err.println("❌ Ошибка при сохранении данных: " + e.getMessage());
        }
    }

    /**
     * Загружает контакты из файла в память (использует FileReader).
     */
    public void loadContactsFromFile() {
        File file = new File(FILENAME);
        if (!file.exists()) {
            System.out.println("ℹ️ Файл данных не найден. Создана пустая книга.");
            return;
        }

        // Используем try-with-resources с BufferedReader для построчного чтения
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int loadedCount = 0;
            // Читаем файл построчно до конца
            while ((line = reader.readLine()) != null) {
                // Разделяем строку на поля по разделителю ";"
                String[] parts = line.split(FIELD_SEPARATOR);

                // Проверка на корректность формата (должно быть 2 части: имя и номер)
                if (parts.length == 2) {
                    String name = parts[0];
                    String phoneNumber = parts[1];
                    contacts.add(new Contact(name, phoneNumber));
                    loadedCount++;
                } else {
                    System.err.println("⚠️ Пропущена некорректная строка в файле: " + line);
                }
            }
            System.out.println("✅ Загружено " + loadedCount + " контактов из файла: " + FILENAME);
        } catch (IOException e) {
            System.err.println("❌ Ошибка при загрузке данных: " + e.getMessage());
        }
    }
}
