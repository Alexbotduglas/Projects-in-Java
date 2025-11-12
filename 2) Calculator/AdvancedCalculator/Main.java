// Импортируем класс ArrayList для хранения истории вычислений
import java.util.ArrayList;
// Импортируем класс Scanner для ввода данных пользователем
import java.util.Scanner;

// Объявляем главный класс программы
public class Main {

    // Точка входа в программу
    public static void main(String[] args) {

        // Создаём объект Scanner для чтения пользовательского ввода с консоли
        Scanner scanner = new Scanner(System.in);

        // Создаём список (историю) для хранения всех вычисленных выражений
        ArrayList<String> history = new ArrayList<>();

        // Приветственное сообщение
        System.out.println("=== Расширенный консольный калькулятор ===");

        // Основной бесконечный цикл программы
        while (true) {

            // Вывод меню выбора действия
            System.out.println("\nВыберите действие:");
            System.out.println("1. Вычислить выражение");
            System.out.println("2. Показать историю");
            System.out.println("3. Выход");
            System.out.print("Ваш выбор: ");

            // Считываем выбор пользователя (число)
            int choice = scanner.nextInt();
            // Читаем оставшийся перевод строки (чтобы избежать проблем со nextLine)
            scanner.nextLine();

            // --- Если пользователь выбрал пункт 1 (вычислить выражение) ---
            if (choice == 1) {
                System.out.print("Введите выражение (например: 5 + 3 или sqrt 16): ");

                // Считываем всю строку с выражением
                String input = scanner.nextLine().trim();

                // Разделяем строку по пробелам (для выделения чисел и операторов)
                String[] tokens = input.split(" ");

                // Переменные для результата и записи в историю
                double result = 0;
                String record = "";

                // Блок обработки возможных ошибок (например, неверный формат)
                try {

                    // --- Обработка квадратного корня ---
                    if (tokens.length == 2 && tokens[0].equalsIgnoreCase("sqrt")) {
                        // Преобразуем второй элемент (число) в тип double
                        double num = Double.parseDouble(tokens[1]);

                        // Проверяем, что число не отрицательное
                        if (num < 0) {
                            System.out.println("Ошибка: корень из отрицательного числа!");
                            continue; // Переход к следующему циклу
                        }

                        // Вычисляем квадратный корень
                        result = Math.sqrt(num);

                        // Формируем запись для истории
                        record = "sqrt " + num + " = " + result;

                    // --- Обработка стандартных арифметических операций ---
                    } else if (tokens.length == 3) {
                        // Преобразуем первый и третий элемент в числа
                        double num1 = Double.parseDouble(tokens[0]);
                        double num2 = Double.parseDouble(tokens[2]);
                        // Сохраняем оператор
                        String operator = tokens[1];

                        // Выбираем действие в зависимости от оператора
                        switch (operator) {
                            case "+":
                                result = num1 + num2;
                                break;
                            case "-":
                                result = num1 - num2;
                                break;
                            case "*":
                                result = num1 * num2;
                                break;
                            case "/":
                                // Проверяем деление на ноль
                                if (num2 == 0) {
                                    System.out.println("Ошибка: деление на ноль!");
                                    continue;
                                }
                                result = num1 / num2;
                                break;
                            case "^":
                                result = Math.pow(num1, num2);
                                break;
                            case "%":
                                result = num1 % num2;
                                break;
                            default:
                                // Если введён неизвестный оператор
                                System.out.println("Ошибка: неизвестный оператор.");
                                continue;
                        }

                        // Формируем строку для истории
                        record = input + " = " + result;

                    } else {
                        // Если выражение не соответствует допустимому формату
                        System.out.println("Ошибка: неправильный формат выражения.");
                        continue;
                    }

                    // Выводим результат вычисления
                    System.out.println("Результат: " + result);

                    // Добавляем запись в историю
                    history.add(record);

                } catch (NumberFormatException e) {
                    // Обрабатываем ошибку, если пользователь ввёл нечисловое значение
                    System.out.println("Ошибка: неверный ввод чисел.");
                }

            // --- Если пользователь выбрал пункт 2 (показать историю) ---
            } else if (choice == 2) {
                // Проверяем, пуста ли история
                if (history.isEmpty()) {
                    System.out.println("История пуста.");
                } else {
                    System.out.println("История вычислений:");
                    // Проходим по всем записям и выводим их
                    for (String entry : history) {
                        System.out.println(" - " + entry);
                    }
                }

            // --- Если пользователь выбрал пункт 3 (выход) ---
            } else if (choice == 3) {
                // Сообщение о завершении работы
                System.out.println("Выход из программы. Спасибо!");
                break; // Прерываем бесконечный цикл

            // --- Если введено что-то другое ---
            } else {
                System.out.println("Ошибка: выберите 1, 2 или 3.");
            }
        }

        // Закрываем сканер для предотвращения утечки ресурсов
        scanner.close();
    }
}
