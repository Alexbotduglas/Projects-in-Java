// === Расширенный консольный калькулятор ===
//
//Выберите действие:
//1. Вычислить выражение
//2. Показать историю
//3. Выход
//Ваш выбор: 1
//Введите выражение (например: 5 + 3 или sqrt 16): 5 + 12
//Результат: 17.0
//
//Выберите действие:
//1. Вычислить выражение
//2. Показать историю
//3. Выход
//Ваш выбор: 2
//История вычислений:
// - 5 + 12 = 17.0
//
//Выберите действие:
//1. Вычислить выражение
//2. Показать историю
//3. Выход
//Ваш выбор: 1
//Введите выражение (например: 5 + 3 или sqrt 16): sqrt 16
//Результат: 4.0
//
//Выберите действие:
//1. Вычислить выражение
//2. Показать историю
//3. Выход
//Ваш выбор: 2
//История вычислений:
// - 5 + 12 = 17.0
// - sqrt 16.0 = 4.0
//
//Выберите действие:
//1. Вычислить выражение
//2. Показать историю
//3. Выход
//Ваш выбор: 3
//Выход из программы. Спасибо!

import java.util.ArrayList;
import java.util.Scanner;

public class AdvancedCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> history = new ArrayList<>();

        System.out.println("=== Расширенный консольный калькулятор ===");

        while (true) {
            System.out.println("\nВыберите действие:");
            System.out.println("1. Вычислить выражение");
            System.out.println("2. Показать историю");
            System.out.println("3. Выход");
            System.out.print("Ваш выбор: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (choice == 1) {
                System.out.print("Введите выражение (например: 5 + 3 или sqrt 16): ");
                String input = scanner.nextLine().trim();

                String[] tokens = input.split(" ");
                double result = 0;
                String record = "";

                try {
                    if (tokens.length == 2 && tokens[0].equalsIgnoreCase("sqrt")) {
                        double num = Double.parseDouble(tokens[1]);
                        if (num < 0) {
                            System.out.println("Ошибка: корень из отрицательного числа!");
                            continue;
                        }
                        result = Math.sqrt(num);
                        record = "sqrt " + num + " = " + result;
                    } else if (tokens.length == 3) {
                        double num1 = Double.parseDouble(tokens[0]);
                        String operator = tokens[1];
                        double num2 = Double.parseDouble(tokens[2]);

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
                                System.out.println("Ошибка: неизвестный оператор.");
                                continue;
                        }
                        record = input + " = " + result;
                    } else {
                        System.out.println("Ошибка: неправильный формат выражения.");
                        continue;
                    }

                    System.out.println("Результат: " + result);
                    history.add(record);

                } catch (NumberFormatException e) {
                    System.out.println("Ошибка: неверный ввод чисел.");
                }
            } else if (choice == 2) {
                if (history.isEmpty()) {
                    System.out.println("История пуста.");
                } else {
                    System.out.println("История вычислений:");
                    for (String entry : history) {
                        System.out.println(" - " + entry);
                    }
                }
            } else if (choice == 3) {
                System.out.println("Выход из программы. Спасибо!");
                break;
            } else {
                System.out.println("Ошибка: выберите 1, 2 или 3.");
            }
        }

        scanner.close();
    }
}
