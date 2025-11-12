// Импортируем нужные классы
import java.util.ArrayList;
import java.util.Scanner;

// Главный класс программы
public class Main {

    // Точка входа в программу
    public static void main(String[] args) {

        // Объект для ввода данных
        Scanner scanner = new Scanner(System.in);

        // Список для хранения истории вычислений
        ArrayList<String> history = new ArrayList<>();

        // Приветственное сообщение
        System.out.println("=== Инженерный консольный калькулятор ===");

        // Главный цикл программы
        while (true) {

            // Меню действий
            System.out.println("\nВыберите действие:");
            System.out.println("1. Вычислить выражение");
            System.out.println("2. Показать историю");
            System.out.println("3. Выход");
            System.out.print("Ваш выбор: ");

            // Читаем выбор пользователя
            int choice = scanner.nextInt();
            scanner.nextLine(); // убрать перевод строки

            // Если пользователь выбрал "1 — Вычислить выражение"
            if (choice == 1) {
                // Просим ввести выражение
                System.out.print("Введите выражение (например: 5 + 3, sqrt 16, sin 30, log 100): ");
                String input = scanner.nextLine().trim(); // считываем строку без лишних пробелов
                String[] tokens = input.split(" "); // разбиваем строку по пробелам

                double result = 0; // переменная для результата
                String record = ""; // строка для записи в историю

                try {
                    // --- Тригонометрические и специальные функции ---
                    if (tokens.length == 2) { // если пользователь ввёл 2 элемента (например, sin 30)
                        String func = tokens[0].toLowerCase(); // имя функции
                        double num = Double.parseDouble(tokens[1]); // число

                        switch (func) {
                            case "sqrt": // квадратный корень
                                if (num < 0) {
                                    System.out.println("Ошибка: корень из отрицательного числа!");
                                    continue; // пропускаем итерацию
                                }
                                result = Math.sqrt(num);
                                record = "sqrt " + num + " = " + result;
                                break;

                            case "sin": // синус (в градусах)
                                result = Math.sin(Math.toRadians(num));
                                record = "sin(" + num + "°) = " + result;
                                break;

                            case "cos": // косинус (в градусах)
                                result = Math.cos(Math.toRadians(num));
                                record = "cos(" + num + "°) = " + result;
                                break;

                            case "tan": // тангенс (в градусах)
                                result = Math.tan(Math.toRadians(num));
                                record = "tan(" + num + "°) = " + result;
                                break;

                            case "log": // десятичный логарифм
                                if (num <= 0) {
                                    System.out.println("Ошибка: логарифм не определён для неположительных чисел!");
                                    continue;
                                }
                                result = Math.log10(num);
                                record = "log(" + num + ") = " + result;
                                break;

                            case "ln": // натуральный логарифм
                                if (num <= 0) {
                                    System.out.println("Ошибка: логарифм не определён для неположительных чисел!");
                                    continue;
                                }
                                result = Math.log(num);
                                record = "ln(" + num + ") = " + result;
                                break;

                            default: // если функция неизвестна
                                System.out.println("Ошибка: неизвестная функция.");
                                continue;
                        }

                    // --- Арифметические операции ---
                    } else if (tokens.length == 3) { // если три элемента (например: 5 + 3)
                        double num1 = Double.parseDouble(tokens[0]); // первое число
                        String operator = tokens[1]; // оператор
                        double num2 = Double.parseDouble(tokens[2]); // второе число

                        switch (operator) {
                            case "+": // сложение
                                result = num1 + num2;
                                break;
                            case "-": // вычитание
                                result = num1 - num2;
                                break;
                            case "*": // умножение
                                result = num1 * num2;
                                break;
                            case "/": // деление
                                if (num2 == 0) {
                                    System.out.println("Ошибка: деление на ноль!");
                                    continue;
                                }
                                result = num1 / num2;
                                break;
                            case "^": // возведение в степень
                                result = Math.pow(num1, num2);
                                break;
                            case "%": // остаток от деления
                                result = num1 % num2;
                                break;
                            default: // неизвестный оператор
                                System.out.println("Ошибка: неизвестный оператор.");
                                continue;
                        }

                        // сохраняем запись в историю
                        record = input + " = " + result;

                    } else {
                        // если введён неправильный формат выражения
                        System.out.println("Ошибка: неправильный формат выражения.");
                        continue;
                    }

                    // Выводим результат вычислений
                    System.out.println("Результат: " + result);

                    // Добавляем запись в историю
                    history.add(record);

                } catch (NumberFormatException e) {
                    // обработка ошибки, если пользователь ввёл не число
                    System.out.println("Ошибка: неверный ввод чисел.");
                }

            // Если пользователь выбрал "2 — Показать историю"
            } else if (choice == 2) {
                if (history.isEmpty()) {
                    // если история пуста
                    System.out.println("История пуста.");
                } else {
                    // выводим все записи
                    System.out.println("История вычислений:");
                    for (String entry : history) {
                        System.out.println(" - " + entry);
                    }
                }

            // Если пользователь выбрал "3 — Выход"
            } else if (choice == 3) {
                System.out.println("Выход из программы. Спасибо!");
                break; // выходим из цикла

            // Если пользователь ввёл неверный номер меню
            } else {
                System.out.println("Ошибка: выберите 1, 2 или 3.");
            }
        }

        // Закрываем Scanner после выхода
        scanner.close();
    }
}
