package game;

import java.util.Arrays;
import java.util.Map;

public class TicTacToeBoard implements Position, Board {
    private static final Map<Cell, String> CELL_TO_STRING = Map.of(
            Cell.E, ".",
            Cell.X, "X",
            Cell.O, "0",
            Cell.T, "-",
            Cell.L, "|"
    );

    private final Cell[][] field;
    private Cell turn;
    private final int n;
    private final int m;
    private final int k;
    private final int players;
    private int filled = 0;


    public TicTacToeBoard(int n, int m, int k, int players) {
        this.n = n;
        this.m = m;
        this.k = k;
        this.players = players;
        field = new Cell[n][m];
        for (Cell[] row : field) {
            Arrays.fill(row, Cell.E);
        }

        turn = Cell.X;
    }

    @Override
    public Cell getTurn() {
        return turn;
    }

    @Override
    public Position getPosition() {
        return this;
    }

    @Override
    public GameResult makeMove(Move move) {
        if (!isValid(move)) {
            return GameResult.LOOSE;
        }

        field[move.getRow()][move.getCol()] = move.getValue();
        this.filled++;
        if (checkWin(move)) {
            return GameResult.WIN;
        }

        if (checkDraw()) {
            return GameResult.DRAW;
        }

        switch (players) {
            case 2:
                turn = turn == Cell.X ? Cell.O : Cell.X;
                break;
            case 3:
                turn = turn == Cell.X ? Cell.O : turn == Cell.O ? Cell.T : Cell.X;
                break;
            case 4:
                turn = turn == Cell.X ? Cell.O : turn == Cell.O ? Cell.T : turn == Cell.T ? Cell.L : Cell.X;
                break;
            default:
                break;
        }

        return GameResult.UNKNOWN;
    }


    private boolean checkDraw() {
        return this.n * this.m - this.filled == 0;
    }

    private boolean inBoard(int i, int j) {
        return (i < n && i >= 0) && (j < m && j >= 0);
    }

    private int countInWay(int i, int j, Move move) {
        int counter = 0;
        int row = move.getRow();
        int col = move.getCol();
        if (field[row][col] == turn) {
            counter++;
        }
        int cr = row + i;
        int cc = col + j;
        while (inBoard(cr, cc)) {
            if (field[cr][cc] == turn) {
                counter++;
            } else {
                break;
            }
            cr += i;
            cc += j;
        }
        cr = row - i;
        cc = col - j;
        while (inBoard(cr, cc)) {
            if (field[cr][cc] == turn) {
                counter++;
            } else {
                break;
            }
            cr -= i;
            cc -= j;
        }
        return counter;

    }

    private boolean checkWin(Move move) {
        System.out.println(k);
        return (countInWay(1, 1, move) >= k)
                || (countInWay(1, 0, move) >= k)
                || (countInWay(-1, 1, move) >= k)
                || (countInWay(0, 1, move) >= k);
    }

    public boolean isValid(final Move move) {
        return 0 <= move.getRow() && move.getRow() < n
                && 0 <= move.getCol() && move.getCol() < m
                && field[move.getRow()][move.getCol()] == Cell.E
                && turn == move.getValue();
    }

    @Override
    public Cell getCell(int row, int column) {
        return field[row][column];
    }

    @Override
    public int getNumberRows() {
        return this.n;
    }

    @Override
    public int getNumberCols() {
        return this.m;
    }

    @Override
    public String toString() {
        int lenN = (int) Math.floor(Math.log10(this.n)) + 1;
        int lenM = (int) Math.floor(Math.log10(this.m)) + 1;
        final StringBuilder sb = new StringBuilder(" ".repeat(lenM + 1));
        for (int i = 0; i < m; i++) {
            sb.append(i + 1);
            sb.append(" ".repeat(lenN));
        }
        sb.append(System.lineSeparator());
        for (int r = 0; r < n; r++) {
            sb.append((r + 1));
            int offset = lenN - (int) Math.floor(Math.log10(r + 1));
            if (offset > 0) {
                sb.append(" ".repeat(offset));
            }
            for (int c = 0; c < m; c++) {
                sb.append(CELL_TO_STRING.get(field[r][c]));
                sb.append(" ".repeat(lenN + (int) Math.floor(Math.log10(c + 1))));
            }
            sb.append(System.lineSeparator());
        }
        sb.setLength(sb.length() - System.lineSeparator().length());
        return sb.toString();
    }

    public int checker() {
        return 1;
    }
}
