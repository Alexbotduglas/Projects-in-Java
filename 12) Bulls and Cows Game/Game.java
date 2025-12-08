public class Game {
    private String secretNumber;
    private final int NUM_DIGITS = 4; // Обычно 4-значное число

    public Game() {
        this.secretNumber = generateSecretNumber();
    }

    private String generateSecretNumber() {
        // Логика генерации уникального 4-значного числа без повторяющихся цифр
        // Например: 7205
        // 
        // ... (реализация)
        return "7205"; // Пример для тестирования
    }

    /**
     * Возвращает строку вида: "X быков, Y коров"
     */
    public String checkGuess(String guess) {
        int bulls = 0;
        int cows = 0;

        for (int i = 0; i < NUM_DIGITS; i++) {
            char guessDigit = guess.charAt(i);

            if (guessDigit == secretNumber.charAt(i)) {
                // Цифра на правильной позиции
                bulls++;
            } else if (secretNumber.contains(String.valueOf(guessDigit))) {
                // Цифра есть в числе, но на неверной позиции
                cows++;
            }
        }
        return bulls + " быков, " + cows + " коров";
    }

    public boolean isGameOver(int bulls) {
        return bulls == NUM_DIGITS;
    }

    public String getSecretNumber() {
        return secretNumber;
    }
}
