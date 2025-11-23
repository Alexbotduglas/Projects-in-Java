import java.util.Random;
import java.util.Scanner;

public class Main  {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        // Массивы для удобного отображения ходов
        String[] choices = {"Камень", "Ножницы", "Бумага"};

        // Счетчики побед
        int playerWins = 0;
        int computerWins = 0;
        int ties = 0;

        boolean playAgain = true;

        System.out.println("Добро пожаловать в игру 'Камень, Ножницы, Бумага'!");
        System.out.println("Правила просты:");
        System.out.println(" - Камень бьет Ножницы");
        System.out.println(" - Ножницы бьют Бумагу");
        System.out.println(" - Бумага бьет Камень");
        System.out.println();

        // Главный цикл игры
        while (playAgain) {
            // Ход пользователя с проверкой ввода
            int playerChoice = -1;
            boolean validInput = false;

            while (!validInput) {
                System.out.println("Сделайте ваш выбор:");
                System.out.println("0 - Камень, 1 - Ножницы, 2 - Бумага");
                System.out.print("Ваш выбор: ");

                if (scanner.hasNextInt()) {
                    playerChoice = scanner.nextInt();

                    // Проверка на корректный диапазон
                    if (playerChoice >= 0 && playerChoice <= 2) {
                        validInput = true;
                    } else {
                        System.out.println("Неверный ввод! Пожалуйста, введите число от 0 до 2.");
                        System.out.println();
                    }
                } else {
                    System.out.println("Ошибка! Пожалуйста, введите число (0, 1 или 2).");
                    System.out.println();
                    scanner.next(); // Очищаем некорректный ввод
                }
            }

            // Ход компьютера
            int computerChoice = random.nextInt(3); // Генерирует 0, 1 или 2

            // Вывод ходов
            System.out.println("\n=== Результат раунда ===");
            System.out.println("Ваш ход: " + choices[playerChoice]);
            System.out.println("Ход компьютера: " + choices[computerChoice]);

            // Определение победителя и обновление счета
            if (playerChoice == computerChoice) {
                System.out.println(">> Ничья! <<");
                ties++;
            } else if ((playerChoice == 0 && computerChoice == 1) || // Камень vs Ножницы
                    (playerChoice == 1 && computerChoice == 2) || // Ножницы vs Бумага
                    (playerChoice == 2 && computerChoice == 0)) { // Бумага vs Камень
                System.out.println(">> Вы победили! <<");
                playerWins++;
            } else {
                System.out.println(">> Компьютер победил! <<");
                computerWins++;
            }

            // Вывод текущего счета
            System.out.println("\n--- Текущий счет ---");
            System.out.println("Ваши победы: " + playerWins);
            System.out.println("Победы компьютера: " + computerWins);
            System.out.println("Ничьи: " + ties);
            System.out.println("-------------------");

            // Запрос на повторную игру с проверкой ввода
            boolean validResponse = false;
            while (!validResponse) {
                System.out.print("\nХотите сыграть еще раз? (да/нет): ");
                String response = scanner.next();

                // Проверяем ответ пользователя
                if (response.equalsIgnoreCase("да") ||
                        response.equalsIgnoreCase("yes") ||
                        response.equalsIgnoreCase("y") ||
                        response.equalsIgnoreCase("д")) {
                    playAgain = true;
                    validResponse = true;
                    System.out.println();
                } else if (response.equalsIgnoreCase("нет") ||
                        response.equalsIgnoreCase("no") ||
                        response.equalsIgnoreCase("n") ||
                        response.equalsIgnoreCase("н")) {
                    playAgain = false;
                    validResponse = true;
                } else {
                    System.out.println("Пожалуйста, введите 'да' или 'нет'.");
                }
            }
        }

        // Финальные результаты
        System.out.println("\n=== ФИНАЛЬНЫЙ СЧЕТ ===");
        System.out.println("Ваши победы: " + playerWins);
        System.out.println("Победы компьютера: " + computerWins);
        System.out.println("Ничьи: " + ties);

        // Определение общего победителя
        if (playerWins > computerWins) {
            System.out.println("Вы выиграли эту серию игр! Поздравляем!");
        } else if (computerWins > playerWins) {
            System.out.println("Компьютер выиграл эту серию игр. Попробуйте еще раз!");
        } else {
            System.out.println("Серия игр закончилась вничью!");
        }

        System.out.println("\nСпасибо за игру! До свидания!");
        scanner.close();
    }
}
