package main.java.generator;

import com.sun.media.sound.InvalidFormatException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* Ta klasa rozwi¹zuje dan¹ ³amig³ówkê sudoku, podan¹ jako tablica int lub tablica string.
 */
public class SudokuSolver {
    private int[][] board;
    private int[][] soln;

    /**
     * Rozwi¹¿e tablicê podan¹ jako tablica string.
     * @param board plansza. MUSI przestrzegaæ standardowego formatu CSV, z przecinkiem!
     * @throws IllegalArgumentException  Jeœli dane wejœciowe nie maj¹ poprawnego formatu lub jeœli plansza jest nierozwi¹zywalna.
     */
    public SudokuSolver(String[] board) throws IllegalArgumentException {
        parseProblem(board);
        this.soln = new int[9][9];
        for (int r = 0; r < 9; r++) {
            System.arraycopy(this.board[r], 0, soln[r], 0, 9);
        }
        if (!solve(0, 0)) {
            throw new IllegalArgumentException("Invalid board given.");
        }
    }

    /**
     * Rozwi¹zuje planszê podan¹ jako tablicê int
     * @param board plansza.
     * @throws IllegalArgumentException Jeœli dane wejœciowe nie maj¹ poprawnego formatu lub jeœli p³yta jest nierozwi¹zywalna..
     */
    public SudokuSolver(int[][] board) throws IllegalArgumentException {
        this.board = board;
        this.soln = new int[9][9];
        for (int r = 0; r < 9; r++) {
            System.arraycopy(board[r], 0, soln[r], 0, 9);
        }
        if (!solve(0, 0)) {
            throw new IllegalArgumentException("Invalid board given.");
        }
    }

    /**
     * Pobiera plansze.
     * @return tablica int reprezentujaca plansze, nigdy null.
     */
    public int[][] getBoard() {
        return this.board;
    }

    /**
     * Pobiera rozwiazanie planszy
     * @return tablica int reprezentuj¹ca rozwi¹zanie
     */
    public int[][] getSoln() {
        return soln;
    }

    public boolean solve(int row, int col) {
        if (row == 9) {
            row = 0;
            if (++col == 9)
                return true;
        }
        if (soln[row][col] != 0)  // pomija wype³nione komórki
            return solve(row+1, col);

        for (int num = 1; num <= 9; num++) {
            if (this.legal(row, col, num)) {
                soln[row][col] = num;
                if (solve(row+1, col))
                    return true;
            }
        }
        soln[row][col] = 0;
        return false;
    }

    private boolean legal(int row, int col, int num) {
        for (int r = 0; r < 9; r++) {
            if (num == soln[r][col]) {
                return false;
            }
        }
        for (int c = 0; c < 9; c++) {
            if (num == soln[row][c]) {
                return false;
            }
        }
        int rOff = (row / 3) * 3;
        int cOff = (col / 3) * 3;
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (num == soln[rOff + r][cOff + c]) {
                    return false;
                }
            }
        }
        return true;
    }

    private void parseProblem(String[] input) throws IllegalArgumentException {
        try {
            board = new int[9][9];
            Pattern exp = Pattern.compile("\\d");
            Matcher res = exp.matcher(String.join(", ", input));
            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {
                    if (!res.find()) {
                        throw new InvalidFormatException("Not enough integers!");
                    }
                    board[r][c] = Integer.parseInt(res.group(0));
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}