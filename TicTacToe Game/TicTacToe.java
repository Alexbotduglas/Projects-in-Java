import java.util.Scanner;

public class TicTacToe {
    private Board board;
    private final Player player1;
    private final Player player2;
    private Player currentPlayer;
    private final Scanner scanner;

    public TicTacToe() {
        board = new Board();
        scanner = new Scanner(System.in);

        System.out.println("Добро пожаловать в Крестики-Нолики!");
        System.out.print("Введите имя первого игрока (X): ");
        player1 = new Player(scanner.nextLine(), 'X');

        System.out.print("Введите имя второго игрока (O): ");
        player2 = new Player(scanner.nextLine(), 'O');

        currentPlayer = player1;
    }

    // Основной игровой цикл
    public void startGame() {
        boolean gameRunning = true;

        while (gameRunning) {
            board.display();
            makeMove();

            if (board.checkWin(currentPlayer.getSymbol())) {
                board.display();
                System.out.println("Поздравляем! " + currentPlayer.getName() + " победил!");
                gameRunning = false;
            } else if (board.isFull()) {
                board.display();
                System.out.println("Ничья! Поле полностью заполнено.");
                gameRunning = false;
            } else {
                // Смена игрока
                switchPlayer();
            }
        }

        askForReplay();
    }

    // Обработка хода игрока
    private void makeMove() {
        int row, col;
        boolean validMove = false;

        while (!validMove) {
            System.out.println(currentPlayer.getName() + " (" + currentPlayer.getSymbol() + "), ваш ход!");

            try {
                System.out.print("Введите номер строки (0-2): ");
                row = Integer.parseInt(scanner.nextLine());

                System.out.print("Введите номер столбца (0-2): ");
                col = Integer.parseInt(scanner.nextLine());

                if (row >= 0 && row < 3 && col >= 0 && col < 3) {
                    if (board.isCellEmpty(row, col)) {
                        board.setCell(row, col, currentPlayer.getSymbol());
                        validMove = true;
                    } else {
                        System.out.println("Эта клетка уже занята! Попробуйте другую.");
                    }
                } else {
                    System.out.println("Неверные координаты! Используйте числа от 0 до 2.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Пожалуйста, вводите только числа!");
            }
        }
    }

    // Смена текущего игрока
    private void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }

    // Предложение сыграть еще раз
    private void askForReplay() {
        System.out.print("Хотите сыграть еще раз? (да/нет): ");
        String answer = scanner.nextLine().toLowerCase();

        if (answer.equals("да") || answer.equals("yes") || answer.equals("д")) {
            // Создаем новую игру с чистым полем
            board = new Board();
            currentPlayer = player1;
            startGame();
        } else {
            System.out.println("Спасибо за игру!");
            scanner.close();
        }
    }
}
