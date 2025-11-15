import java.security.SecureRandom;
import java.util.Scanner;

// Основной класс, содержащий всю логику генератора паролей и интерфейс командной строки (CLI).
public class Main {

    // --- Константы для определения наборов символов ---

    // Строчные буквы
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    // Прописные (заглавные) буквы
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    // Цифры
    private static final String DIGITS = "0123456789";
    // Специальные символы
    private static final String SPECIAL_CHARS = "!@#$%^&*()-_+=<>?";

    // Используем SecureRandom для более криптографически надежной генерации случайных чисел.
    private static final SecureRandom random = new SecureRandom();

    // --- Основной метод (Точка входа) ---

    public static void main(String[] args) {
        // Вывод приветственного сообщения
        System.out.println("🚀 Генератор Случайных Паролей 🚀");

        // Используем try-with-resources для автоматического закрытия объекта Scanner
        try (Scanner scanner = new Scanner(System.in)) {

            // 1. Получение длины пароля от пользователя
            int length = getPasswordLength(scanner);

            // 2. Получение настроек включения типов символов через подтверждение (да/нет)
            boolean useLower = getConfirmation(scanner, "Включить строчные буквы (a-z)? (да/нет): ");
            boolean useUpper = getConfirmation(scanner, "Включить прописные буквы (A-Z)? (да/нет): ");
            boolean useDigits = getConfirmation(scanner, "Включить цифры (0-9)? (да/нет): ");
            boolean useSpecial = getConfirmation(scanner, "Включить специальные символы (!@#$...) ? (да/нет): ");

            // Проверка, что хотя бы один тип символов был выбран.
            if (!useLower && !useUpper && !useDigits && !useSpecial) {
                System.out.println("❌ Ошибка: Вы должны выбрать хотя бы один тип символов!");
                return; // Завершение программы
            }

            // 3. Генерация пароля с учетом всех выбранных параметров
            String generatedPassword = generatePassword(length, useLower, useUpper, useDigits, useSpecial);

            // 4. Вывод результата
            System.out.println("\n✅ Сгенерированный пароль:");
            System.out.println(generatedPassword);

        } catch (Exception e) {
            // Базовая обработка исключений, например, при проблемах с вводом/выводом
            System.out.println("Произошла ошибка: " + e.getMessage());
        }
    }

    // --- Вспомогательные методы для работы с вводом пользователя ---

    /**
     * Получает от пользователя желаемую длину пароля.
     * Реализует цикл для запроса ввода до тех пор, пока не будет получено валидное число
     * в заданном диапазоне (4-128).
     * @param scanner Объект Scanner для чтения ввода
     * @return Валидная длина пароля (int)
     */
    private static int getPasswordLength(Scanner scanner) {
        int length = 0;
        while (length < 4 || length > 128) {
            System.out.print("Введите желаемую длину пароля (от 4 до 128): ");
            try {
                if (scanner.hasNextInt()) {
                    length = scanner.nextInt();
                    if (length < 4 || length > 128) {
                        System.out.println("⚠️ Длина должна быть в диапазоне от 4 до 128.");
                    }
                } else {
                    System.out.println("⚠️ Некорректный ввод. Пожалуйста, введите число.");
                    scanner.next(); // Очистка некорректного ввода (не-числа)
                }
            } catch (Exception e) {
                System.out.println("Произошла ошибка ввода.");
                scanner.next();
            }
        }
        // Очистка буфера ввода: необходимо после nextInt(), чтобы nextLine() в getConfirmation работал корректно
        scanner.nextLine();
        return length;
    }

    /**
     * Запрашивает у пользователя подтверждение (да/нет) для включения категории символов.
     * Зацикливается, пока не получит один из валидных ответов ('да', 'нет', 'yes', 'no').
     * @param scanner Объект Scanner для чтения ввода
     * @param prompt Сообщение для пользователя
     * @return true, если пользователь ответил 'да', false, если 'нет'
     */
    private static boolean getConfirmation(Scanner scanner, String prompt) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine().trim().toLowerCase(); // Считываем строку, удаляем пробелы, переводим в нижний регистр
            if (input.equals("да") || input.equals("yes")) {
                return true;
            } else if (input.equals("нет") || input.equals("no")) {
                return false;
            } else {
                System.out.println("⚠️ Некорректный ввод. Введите 'да' или 'нет'.");
            }
        }
    }

    // --- Методы генерации пароля ---

    /**
     * Генерирует случайный пароль на основе выбранных параметров.
     * @param length Желаемая длина пароля
     * @param useLower Использовать ли строчные буквы
     * @param useUpper Использовать ли прописные буквы
     * @param useDigits Использовать ли цифры
     * @param useSpecial Использовать ли специальные символы
     * @return Сгенерированный и перемешанный пароль (String)
     */
    private static String generatePassword(int length, boolean useLower, boolean useUpper, boolean useDigits, boolean useSpecial) {

        // Объединяем все разрешенные наборы символов в один пул
        StringBuilder charPool = new StringBuilder();
        if (useLower) charPool.append(LOWERCASE);
        if (useUpper) charPool.append(UPPERCASE);
        if (useDigits) charPool.append(DIGITS);
        if (useSpecial) charPool.append(SPECIAL_CHARS);

        String availableChars = charPool.toString();
        StringBuilder password = new StringBuilder(length);

        // --- Шаг 1: Гарантированное включение ---
        // Добавляем по одному случайному символу из каждого ВЫБРАННОГО набора.
        // Это гарантирует, что пароль будет соответствовать всем требованиям пользователя.
        if (useLower) password.append(LOWERCASE.charAt(random.nextInt(LOWERCASE.length())));
        if (useUpper) password.append(UPPERCASE.charAt(random.nextInt(UPPERCASE.length())));
        if (useDigits) password.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
        if (useSpecial) password.append(SPECIAL_CHARS.charAt(random.nextInt(SPECIAL_CHARS.length())));

        // --- Шаг 2: Дополнение пароля ---
        // Заполняем оставшуюся часть пароля случайными символами из ОБЩЕГО пула.
        for (int i = password.length(); i < length; i++) {
            int randomIndex = random.nextInt(availableChars.length());
            password.append(availableChars.charAt(randomIndex));
        }

        // --- Шаг 3: Перемешивание ---
        // Перемешиваем символы, чтобы гарантированные символы не всегда стояли в начале пароля.
        return shuffleString(password.toString());
    }

    /**
     * Перемешивает символы в строке (алгоритм Фишера-Йетса, реализованный вручную).
     * @param input Исходная строка для перемешивания
     * @return Перемешанная строка (String)
     */
    private static String shuffleString(String input) {
        char[] characters = input.toCharArray(); // Преобразуем строку в массив символов для удобства перемешивания
        for (int i = 0; i < characters.length; i++) {
            // Выбираем случайный индекс
            int randomIndex = random.nextInt(characters.length);
            // Меняем текущий символ местами со случайно выбранным
            char temp = characters[i];
            characters[i] = characters[randomIndex];
            characters[randomIndex] = temp;
        }
        // Возвращаем обратно в виде строки
        return new String(characters);
    }
}
