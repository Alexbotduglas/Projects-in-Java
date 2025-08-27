import java.util.Scanner;

public class TicTacToe {
    private Board board;            // Игровое поле
    private final Player player1;   // Первый игрок (играет за X)
    private final Player player2;   // Второй игрок (играет за O)
    private Player currentPlayer;   // Текущий игрок, который делает ход
    private final Scanner scanner;  // Объект для чтения ввода пользователя

    // Конструктор класса - инициализирует игру
    public TicTacToe() {
        board = new Board();        // Создаем новое игровое поле
        scanner = new Scanner(System.in); // Инициализируем сканер для чтения ввода

        // Приветствие и создание игроков
        System.out.println("Добро пожаловать в Крестики-Нолики!");
        
        // Создание первого игрока
        System.out.print("Введите имя первого игрока (X): ");
        player1 = new Player(scanner.nextLine(), 'X'); // Читаем имя и назначаем символ X

        // Создание второго игрока
        System.out.print("Введите имя второго игрока (O): ");
        player2 = new Player(scanner.nextLine(), 'O'); // Читаем имя и назначаем символ O

        currentPlayer = player1; // Первым ходит первый игрок
    }

    // Основной игровой цикл - управляет всем процессом игры
    public void startGame() {
        boolean gameRunning = true; // Флаг для продолжения игры

        // Главный игровой цикл
        while (gameRunning) {
            board.display();    // Показываем текущее состояние поля
            makeMove();         // Текущий игрок делает ход

            // Проверяем условия окончания игры
            if (board.checkWin(currentPlayer.getSymbol())) {
                // Если текущий игрок выиграл
                board.display(); // Показываем финальное поле
                System.out.println("Поздравляем! " + currentPlayer.getName() + " победил!");
                gameRunning = false; // Завершаем игру
            } else if (board.isFull()) {
                // Если поле заполнено (ничья)
                board.display(); // Показываем финальное поле
                System.out.println("Ничья! Поле полностью заполнено.");
                gameRunning = false; // Завершаем игру
            } else {
                // Если игра продолжается - переключаем игрока
                switchPlayer();
            }
        }

        // После завершения игры предлагаем сыграть еще раз
        askForReplay();
    }

    // Обработка хода игрока - запрашивает и проверяет корректность хода
    private void makeMove() {
        int row, col; // Координаты для хода
        boolean validMove = false; // Флаг корректности хода

        // Цикл продолжается до тех пор, пока не будет сделан корректный ход
        while (!validMove) {
            System.out.println(currentPlayer.getName() + " (" + currentPlayer.getSymbol() + "), ваш ход!");

            try {
                // Запрос номера строки
                System.out.print("Введите номер строки (0-2): ");
                row = Integer.parseInt(scanner.nextLine()); // Читаем и преобразуем в число

                // Запрос номера столбца
                System.out.print("Введите номер столбца (0-2): ");
                col = Integer.parseInt(scanner.nextLine()); // Читаем и преобразуем в число

                // Проверка корректности координат
                if (row >= 0 && row < 3 && col >= 0 && col < 3) {
                    // Проверка, свободна ли клетка
                    if (board.isCellEmpty(row, col)) {
                        board.setCell(row, col, currentPlayer.getSymbol()); // Делаем ход
                        validMove = true; // Ход успешен, выходим из цикла
                    } else {
                        System.out.println("Эта клетка уже занята! Попробуйте другую.");
                    }
                } else {
                    System.out.println("Неверные координаты! Используйте числа от 0 до 2.");
                }
            } catch (NumberFormatException e) {
                // Обработка ошибки ввода (если введено не число)
                System.out.println("Пожалуйста, вводите только числа!");
            }
        }
    }

    // Смена текущего игрока - переключает активного игрока
    private void switchPlayer() {
        // Тернарный оператор: если текущий игрок - player1, то меняем на player2, иначе на player1
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }

    // Предложение сыграть еще раз - обрабатывает рестарт игры
    private void askForReplay() {
        System.out.print("Хотите сыграть еще раз? (да/нет): ");
        String answer = scanner.nextLine().toLowerCase(); // Читаем ответ и приводим к нижнему регистру

        // Проверяем положительные ответы (поддерживаем русский и английский варианты)
        if (answer.equals("да") || answer.equals("yes") || answer.equals("д")) {
            // Перезапуск игры: создаем новое поле и сбрасываем текущего игрока
            board = new Board();
            currentPlayer = player1;
            startGame(); // Запускаем игру заново
        } else {
            // Завершение программы
            System.out.println("Спасибо за игру!");
            scanner.close(); // Закрываем сканер для освобождения ресурсов
        }
    }
}
