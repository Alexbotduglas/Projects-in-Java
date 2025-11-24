import java.util.Random;
import java.util.Scanner;

// Главный класс программы
public class Main  {

    // Главный метод, с которого начинается выполнение программы
    public static void main(String[] args) {
        // Создание объекта Scanner для чтения ввода от пользователя с консоли
        Scanner scanner = new Scanner(System.in);
        // Создание объекта Random для генерации случайных чисел (для хода компьютера)
        Random random = new Random();

        // Массивы для удобного отображения ходов: 0 -> Камень, 1 -> Ножницы, 2 -> Бумага
        String[] choices = {"Камень", "Ножницы", "Бумага"};

        // Счетчики побед и ничьих
        int playerWins = 0;   // Победы игрока
        int computerWins = 0; // Победы компьютера
        int ties = 0;         // Ничьи

        // Флаг для управления основным циклом игры (продолжать ли играть)
        boolean playAgain = true;

        // Приветствие и вывод правил игры
        System.out.println("Добро пожаловать в игру 'Камень, Ножницы, Бумага'!");
        System.out.println("Правила просты:");
        System.out.println(" - Камень бьет Ножницы");
        System.out.println(" - Ножницы бьют Бумагу");
        System.out.println(" - Бумага бьет Камень");
        System.out.println();

        // Главный цикл игры. Продолжается, пока 'playAgain' равно 'true'
        while (playAgain) {
            // Ход пользователя с проверкой ввода
            int playerChoice = -1;      // Переменная для хранения выбора игрока (0, 1 или 2)
            boolean validInput = false; // Флаг для контроля корректности ввода

            // Цикл для обеспечения корректного ввода от пользователя
            while (!validInput) {
                System.out.println("Сделайте ваш выбор:");
                System.out.println("0 - Камень, 1 - Ножницы, 2 - Бумага");
                System.out.print("Ваш выбор: ");

                // Проверяем, ввел ли пользователь целое число
                if (scanner.hasNextInt()) {
                    playerChoice = scanner.nextInt(); // Считываем целое число

                    // Проверка на корректный диапазон (0, 1 или 2)
                    if (playerChoice >= 0 && playerChoice <= 2) {
                        validInput = true; // Ввод корректен, выходим из цикла
                    } else {
                        // Сообщение об ошибке, если число вне диапазона
                        System.out.println("Неверный ввод! Пожалуйста, введите число от 0 до 2.");
                        System.out.println();
                    }
                } else {
                    // Сообщение об ошибке, если введен не числовой символ
                    System.out.println("Ошибка! Пожалуйста, введите число (0, 1 или 2).");
                    System.out.println();
                    scanner.next(); // Очищаем некорректный ввод из буфера сканера
                }
            }

            // Ход компьютера
            // random.nextInt(3) генерирует случайное целое число: 0, 1 или 2
            int computerChoice = random.nextInt(3);

            // Вывод ходов
            System.out.println("\n=== Результат раунда ===");
            // Отображаем выбор игрока, используя массив choices
            System.out.println("Ваш ход: " + choices[playerChoice]);
            // Отображаем выбор компьютера, используя массив choices
            System.out.println("Ход компьютера: " + choices[computerChoice]);

            // Определение победителя и обновление счета
            if (playerChoice == computerChoice) {
                // Условие ничьи: ходы совпадают
                System.out.println(">> Ничья! <<");
                ties++;
            } else if ((playerChoice == 0 && computerChoice == 1) || // Камень (0) бьет Ножницы (1)
                    (playerChoice == 1 && computerChoice == 2) || // Ножницы (1) бьют Бумагу (2)
                    (playerChoice == 2 && computerChoice == 0)) { // Бумага (2) бьет Камень (0)
                // Условия победы игрока
                System.out.println(">> Вы победили! <<");
                playerWins++;
            } else {
                // Все остальные случаи - победа компьютера
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
                String response = scanner.next(); // Считываем ответ

                // Проверяем, хочет ли пользователь продолжить (принимаем разные варианты "да")
                if (response.equalsIgnoreCase("да") ||
                        response.equalsIgnoreCase("yes") ||
                        response.equalsIgnoreCase("y") ||
                        response.equalsIgnoreCase("д")) {
                    playAgain = true;      // Устанавливаем флаг в true
                    validResponse = true; // Ввод корректен, выходим из цикла проверки ответа
                    System.out.println();
                }
                // Проверяем, хочет ли пользователь закончить (принимаем разные варианты "нет")
                else if (response.equalsIgnoreCase("нет") ||
                        response.equalsIgnoreCase("no") ||
                        response.equalsIgnoreCase("n") ||
                        response.equalsIgnoreCase("н")) {
                    playAgain = false;     // Устанавливаем флаг в false
                    validResponse = true; // Ввод корректен, выходим из цикла проверки ответа
                }
                // Некорректный ввод
                else {
                    System.out.println("Пожалуйста, введите 'да' или 'нет'.");
                }
            }
        } // Конец главного цикла 'while (playAgain)'

        // Финальные результаты после завершения серии игр
        System.out.println("\n=== ФИНАЛЬНЫЙ СЧЕТ ===");
        System.out.println("Ваши победы: " + playerWins);
        System.out.println("Победы компьютера: " + computerWins);
        System.out.println("Ничьи: " + ties);

        // Определение общего победителя серии игр
        if (playerWins > computerWins) {
            System.out.println("Вы выиграли эту серию игр! Поздравляем!");
        } else if (computerWins > playerWins) {
            System.out.println("Компьютер выиграл эту серию игр. Попробуйте еще раз!");
        } else {
            // Если счеты равны
            System.out.println("Серия игр закончилась вничью!");
        }

        System.out.println("\nСпасибо за игру! До свидания!");
        // Закрытие объекта Scanner для освобождения системных ресурсов
        scanner.close();
    } // Конец метода main
} // Конец класса Main
