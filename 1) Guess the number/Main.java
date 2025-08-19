import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        boolean playAgain = true;

        int wins = 0;
        int losses = 0;
        int bestScore = Integer.MAX_VALUE;

        while (playAgain) {
            // --- Выбор уровня сложности ---
            System.out.println("\nВыбери уровень сложности:");
            System.out.println("1 - Лёгкий (1-50, 10 попыток)");
            System.out.println("2 - Средний (1-100, 7 попыток)");
            System.out.println("3 - Сложный (1-200, 5 попыток)");
            System.out.print("Твой выбор: ");
            int difficulty = scanner.nextInt();

            int maxAttempts;
            int maxRange;
            switch (difficulty) {
                case 1:
                    maxAttempts = 10;
                    maxRange = 50;
                    break;
                case 2:
                    maxAttempts = 7;
                    maxRange = 100;
                    break;
                case 3:
                    maxAttempts = 5;
                    maxRange = 200;
                    break;
                default:
                    System.out.println("Некорректный выбор. Установлен уровень 'Средний'.");
                    maxAttempts = 7;
                    maxRange = 100;
            }

            int numberToGuess = random.nextInt(maxRange) + 1;
            int guess;
            int attempts = 0;
            boolean guessed = false;

            System.out.println("\nЯ загадал число от 1 до " + maxRange +
                    ". У тебя есть " + maxAttempts + " попыток!");

            // --- Игровой цикл ---
            while (attempts < maxAttempts) {
                System.out.print("Введи число: ");
                guess = scanner.nextInt();
                attempts++;

                if (guess < numberToGuess) {
                    System.out.println("Моё число больше.");
                } else if (guess > numberToGuess) {
                    System.out.println("Моё число меньше.");
                } else {
                    System.out.println("🎉 Поздравляю! Ты угадал число за " + attempts + " попыток.");
                    guessed = true;
                    wins++;
                    if (attempts < bestScore) {
                        bestScore = attempts;
                        System.out.println("🔥 Новый рекорд: " + attempts + " попыток!");
                    }
                    break;
                }

                // --- Умные подсказки ---
                if (attempts == 3) {
                    System.out.println("Подсказка: попробуй использовать стратегию деления пополам.");
                } else if (attempts == 5 && !guessed) {
                    if (numberToGuess % 2 == 0) {
                        System.out.println("Подсказка: число чётное.");
                    } else {
                        System.out.println("Подсказка: число нечётное.");
                    }
                }
            }

            if (!guessed) {
                System.out.println("😢 Ты не угадал! Загаданное число было: " + numberToGuess);
                losses++;
            }

            // --- Советы после игры ---
            String[] tips = {
                    "Совет: начинай угадывать с середины диапазона.",
                    "Совет: не трать попытки на повтор чисел.",
                    "Совет: сокращай диапазон шаг за шагом.",
                    "Совет: обращай внимание на чётность числа.",
                    "Совет: думай стратегически, а не наугад."
            };
            int randomTipIndex = random.nextInt(tips.length);
            System.out.println("💡 " + tips[randomTipIndex]);

            // --- Показ статистики ---
            System.out.println("\n📊 Статистика:");
            System.out.println("Победы: " + wins + " | Поражения: " + losses);
            if (bestScore != Integer.MAX_VALUE) {
                System.out.println("Лучший результат: " + bestScore + " попыток.");
            }

            // --- Снова играть ---
            System.out.print("\nХочешь сыграть ещё раз? (да/нет): ");
            String answer = scanner.next();
            playAgain = answer.equalsIgnoreCase("да");
        }

        System.out.println("\nСпасибо за игру! До встречи 👋");
        scanner.close();
    }
}
