import java.util.*;

public class Main {
    private long startTime;
    private long pausedTime;
    private boolean running;
    private final List<Long> laps = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new Main().run();
    }

    public void run() {
        System.out.println("⏱️  СЕКУНДОМЕР 2024");
        System.out.println("-------------------");

        while (true) {
            showDisplay();

            System.out.print("\nВыберите действие: ");
            String input = scanner.nextLine().trim();

            if (handleInput(input)) break;
        }
        scanner.close();
    }

    private boolean handleInput(String input) {
        switch (input.toLowerCase()) {
            case "1", "старт", "start", "s" -> startTimer();
            case "2", "пауза", "pause", "p" -> pauseTimer();
            case "3", "сброс", "reset", "r" -> resetTimer();
            case "4", "круг", "lap", "l" -> addLap();
            case "0", "выход", "exit", "q" -> {
                System.out.println("До свидания!");
                return true;
            }
            case "" -> { /* пустой ввод - обновить экран */ }
            default -> System.out.println("❌ Неизвестная команда");
        }
        return false;
    }

    private void startTimer() {
        if (!running) {
            if (pausedTime > 0) {
                // Продолжение после паузы
                startTime = System.currentTimeMillis() - pausedTime;
            } else {
                // Новый старт
                startTime = System.currentTimeMillis();
                if (laps.isEmpty()) {
                    System.out.println("▶ Секундомер запущен");
                } else {
                    System.out.println("▶ Продолжение работы");
                }
            }
            running = true;
        } else {
            System.out.println("⚠ Уже работает");
        }
    }

    private void pauseTimer() {
        if (running) {
            pausedTime = getCurrentTime();
            running = false;
            System.out.println("⏸ Пауза");
        } else {
            System.out.println("⚠ Секундомер не запущен");
        }
    }

    private void resetTimer() {
        running = false;
        pausedTime = 0;
        laps.clear();
        System.out.println("🔄 Сброс");
    }

    private void addLap() {
        if (running) {
            long currentTime = getCurrentTime();
            laps.add(currentTime);

            long lapTime = getLastLapTime();
            System.out.printf("⏱ Круг %d: %s%n", laps.size(), formatTime(lapTime));
        } else {
            System.out.println("❌ Запустите секундомер сначала");
        }
    }

    private void showDisplay() {
        clearScreen();

        // Заголовок
        System.out.println("⏱️  СЕКУНДОМЕР 2024");
        System.out.println("===================");

        // Основное время
        long current = getCurrentTime();
        System.out.printf("╔═══════════════════╗%n");
        System.out.printf("║    %s    ║%n", formatTime(current));
        System.out.printf("╠═══════════════════╣%n");

        // Статус
        String status = running ? "▶ РАБОТАЕТ" : "⏸ ОСТАНОВЛЕН";
        System.out.printf("║   %-12s  ║%n", status);
        System.out.printf("╚═══════════════════╝%n");

        // Статистика
        System.out.printf("Кругов: %d", laps.size());
        if (!laps.isEmpty()) {
            long best = laps.stream().mapToLong(this::getLapTime).min().orElse(0);
            long worst = laps.stream().mapToLong(this::getLapTime).max().orElse(0);
            System.out.printf("  |  Лучший: %s  |  Худший: %s%n",
                    formatShortTime(best), formatShortTime(worst));
        } else {
            System.out.println();
        }

        // Последние круги
        if (!laps.isEmpty()) {
            System.out.println("\nПоследние круги:");
            int start = Math.max(0, laps.size() - 5);
            for (int i = start; i < laps.size(); i++) {
                long lapTime = getLapTime(laps.get(i));
                System.out.printf("%2d. %s%n", i + 1, formatShortTime(lapTime));
            }
        }

        // Меню
        System.out.println("\n" + "=".repeat(30));
        System.out.println("1️⃣  Старт   2️⃣  Пауза   3️⃣  Сброс");
        System.out.println("4️⃣  Круг    0️⃣  Выход");
        System.out.println("(или используйте слова: старт/пауза/сброс/круг/выход)");
    }

    private long getCurrentTime() {
        return running ? System.currentTimeMillis() - startTime : pausedTime;
    }

    private long getLapTime(long lapTime) {
        int index = laps.indexOf(lapTime);
        return index == 0 ? lapTime : lapTime - laps.get(index - 1);
    }

    private long getLastLapTime() {
        if (laps.size() < 2) return laps.get(0);
        return laps.get(laps.size() - 1) - laps.get(laps.size() - 2);
    }

    private String formatTime(long millis) {
        long hours = millis / 3600000;
        long minutes = (millis % 3600000) / 60000;
        long seconds = (millis % 60000) / 1000;
        long ms = millis % 1000;

        if (hours > 0) {
            return String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, ms);
        } else {
            return String.format("%02d:%02d.%03d", minutes, seconds, ms);
        }
    }

    private String formatShortTime(long millis) {
        long minutes = millis / 60000;
        long seconds = (millis % 60000) / 1000;
        long ms = millis % 1000;
        return String.format("%02d:%02d.%03d", minutes, seconds, ms);
    }

    private void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            // Альтернативная очистка для других ОС
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    }
}

// ⏱️  СЕКУНДОМЕР 2024
//-------------------
//⏱️  СЕКУНДОМЕР 2024
//===================
//╔═══════════════════╗
//║    00:00.000    ║
//╠═══════════════════╣
//║   ⏸ ОСТАНОВЛЕН  ║
//╚═══════════════════╝
//Кругов: 0
//
//==============================
//1️⃣  Старт   2️⃣  Пауза   3️⃣  Сброс
//4️⃣  Круг    0️⃣  Выход
//(или используйте слова: старт/пауза/сброс/круг/выход)
//
//Выберите действие: 1
//▶ Секундомер запущен
//⏱️  СЕКУНДОМЕР 2024
//===================
//╔═══════════════════╗
//║    00:00.023    ║
//╠═══════════════════╣
//║   ▶ РАБОТАЕТ    ║
//╚═══════════════════╝
//Кругов: 0
//
//==============================
//1️⃣  Старт   2️⃣  Пауза   3️⃣  Сброс
//4️⃣  Круг    0️⃣  Выход
//(или используйте слова: старт/пауза/сброс/круг/выход)
//
//Выберите действие: 3
//🔄 Сброс
//⏱️  СЕКУНДОМЕР 2024
//===================
//╔═══════════════════╗
//║    00:00.000    ║
//╠═══════════════════╣
//║   ⏸ ОСТАНОВЛЕН  ║
//╚═══════════════════╝
//Кругов: 0
//
//==============================
//1️⃣  Старт   2️⃣  Пауза   3️⃣  Сброс
//4️⃣  Круг    0️⃣  Выход
//(или используйте слова: старт/пауза/сброс/круг/выход)
//
//Выберите действие: 4
//❌ Запустите секундомер сначала
//⏱️  СЕКУНДОМЕР 2024
//===================
//╔═══════════════════╗
//║    00:00.000    ║
//╠═══════════════════╣
//║   ⏸ ОСТАНОВЛЕН  ║
//╚═══════════════════╝
//Кругов: 0
//
//==============================
//1️⃣  Старт   2️⃣  Пауза   3️⃣  Сброс
//4️⃣  Круг    0️⃣  Выход
//(или используйте слова: старт/пауза/сброс/круг/выход)
//
//Выберите действие: 3
//🔄 Сброс
//⏱️  СЕКУНДОМЕР 2024
//===================
//╔═══════════════════╗
//║    00:00.000    ║
//╠═══════════════════╣
//║   ⏸ ОСТАНОВЛЕН  ║
//╚═══════════════════╝
//Кругов: 0
//
//==============================
//1️⃣  Старт   2️⃣  Пауза   3️⃣  Сброс
//4️⃣  Круг    0️⃣  Выход
//(или используйте слова: старт/пауза/сброс/круг/выход)
//
//Выберите действие: 0
//До свидания!
