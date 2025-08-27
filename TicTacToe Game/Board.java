public class Board { //
    private final char[][] board;      // Двумерный массив для хранения состояния поля
    private static final int SIZE = 3; // Размер поля (3x3)

    // Конструктор класса Board - вызывается при создании нового объекта Board
    public Board() {
        board = new char[SIZE][SIZE];  // Создает массив 3 на 3 для хранения символов X, O и пробелов
        initializeBoard();             // Вызывает метод для заполнения поля пустыми клетками
    }

    // Инициализация поля пустыми клетками
    private void initializeBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = '-';
            }
        }
    }

    // Отображение текущего состояния поля
    public void display() {
        System.out.println("\nТекущее поле:");
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // Проверка, свободна ли клетка
    public boolean isCellEmpty(int row, int col) {
        return board[row][col] == '-';
    }

    // Установка символа в клетку
    public void setCell(int row, int col, char symbol) {
        board[row][col] = symbol;
    }

    // Проверка победы
    public boolean checkWin(char symbol) {
        // Проверка строк и столбцов
        for (int i = 0; i < SIZE; i++) {
            if ((board[i][0] == symbol && board[i][1] == symbol && board[i][2] == symbol) ||
                    (board[0][i] == symbol && board[1][i] == symbol && board[2][i] == symbol)) {
                return true;
            }
        }

        // Проверка диагоналей
        if ((board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol) ||
                (board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol)) {
            return true;
        }

        return false;
    }

    // Проверка на ничью (все клетки заполнены)
    public boolean isFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }
}
