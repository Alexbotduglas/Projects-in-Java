import java.util.Scanner;

public class BullsAndCowsApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Game game = new Game();
        int attempts = 0;
        boolean isRunning = true;

        System.out.println("🐂🐄 Добро пожаловать в игру 'Быки и коровы'! 🐄🐂");
        System.out.println("Я загадал " + game.NUM_DIGITS + "-значное число с неповторяющимися цифрами.");
        System.out.println("Попробуйте его угадать.");

        while (isRunning) {
            System.out.print("\nВведите вашу догадку (" + game.NUM_DIGITS + " цифры): ");
            String guess = scanner.next();

            // ⚠️ TODO: Добавить проверку ввода (длина, только цифры, отсутствие повторов)

            attempts++;
            String result = game.checkGuess(guess);
            System.out.println("Результат: " + result);

            // Проверка на победу
            if (game.isGameOver(result.charAt(0) - '0')) { // Хитрое получение числа быков
                System.out.println("\n🎉 Поздравляем! Вы угадали число " + game.getSecretNumber() + " за " + attempts + " попыток!");
                isRunning = false;
            }
        }

        scanner.close();
    }
}
