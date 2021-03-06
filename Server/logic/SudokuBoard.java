package main.java.generator;

import java.util.Random;

/**
 * Ta klasa jest odpowiedzialna za generowanie tablicy od zera. Nie wymaga �adnej innej klasy do dzia�ania, w tym
 * klasy Solver.

 */
public class SudokuBoard {
    private int[][] board;
    private int[][] soln;

    /**
     * Tworzy domyslna plansza.
     */
    public SudokuBoard() {
        this(30);
    }
    
    

    /**
     * Tworzy tablice odpowiedniej wielkosci.
     * @param spaces ��dana liczba wolnych miejsc.
     */
    public SudokuBoard(int spaces) {
        this.board = new int[9][9];
        this.soln = new int[9][9];
        do {
            this.generate();
        } while (!this.verify());
    }

    private boolean verify() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (soln[r][c] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private void generate() {
        int k;
        int n = 1;
        for (int r = 0; r < 9; r++) {
            k = n;
            for (int c = 0; c < 9; c++) {
                if (k > 9) {
                    k = 1;
                }
                this.soln[r][c] = k;
                k++;
            }
            n = k + 3;
            if (k == 10) {
                n = 4;
            }
            if (n > 9) {
                n = (n % 9) + 1;
            }
        }
        randomRow();
        randomCol();
        Random random = new Random();
        int[] i = {0, 3, 6};
        for (int j = 0; j < 2; j++) {
            int k1 = i[random.nextInt(i.length)];
            int k2;
            do {
                k2 = i[random.nextInt(i.length)];
            } while (k1 == k2);
            if (j == 1) {
                rowGroupChange(k1, k2);
            } else {
                colGroupChange(k1, k2);
            }
        }
        for (int r = 0; r < 9; r++) {
            System.arraycopy(soln[r], 0, board[r], 0, 9);
        }
        int[] rowVec = new int[9];
        int[] colVec = new int[9];
        for (int ind = 0; ind < 9; ind++) {
            rowVec[ind] = -1;
            colVec[ind] = -1;
        }
        for (int ind = 0; ind < 9; ind++) {
            int num;
            do {
                num = random.nextInt(9);
            } while (contains(rowVec, num));
            rowVec[ind] = num;
            do {
                num = random.nextInt(9);
            } while (contains(colVec, num));
            colVec[ind] = num;
        }
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                strikeOut(rowVec[r], colVec[c]);
            }
        }
    }

    private boolean contains(int[] vec, int item) {
        for (int i : vec) {
            if (i == item) {
                return true;
            }
        }
        return false;
    }

    private void strikeOut(int row, int col) {
        int count = 9;
        for (int num = 1; num <= 9; num++) {
            boolean flag = true;
            for (int c = 0; c < 9; c++) {
                if (c != col) {
                    if (num == board[row][c]) {
                        flag = false;
                        break;
                    }
                }
            }
            if (flag) {
                for (int r = 0; r < 9; r++) {
                    if (r != row) {
                        if (num == board[r][col]) {
                            flag = false;
                            break;
                        }
                    }
                }
            }
            if (flag) {
                int rowRem = row % 3;
                int colRem = col % 3;
                int rowFrom = row - rowRem;
                int rowTo = row + (2 - rowRem);
                int colFrom = col - colRem;
                int colTo = col + (2 - colRem);
                for (int r = rowFrom; r < rowTo; r++) {
                    for (int c = colFrom; c < colTo; c++) {
                        if (r != row && c != col) {
                            if (num == board[r][c]) {
                                flag = false;
                                break;
                            }
                        }
                    }
                }
            }
            if (!flag) {
                count--;
            }
            if (count == 1) {
                board[row][col] = 0;
            }
        }
    }

    private void randomRow() {
        int k1;
        int k2;
        int max = 2;
        int min = 0;
        Random random = new Random();
        for (int k = 0; k < 3; k++) {
            k1 = random.nextInt(max - min + 1) + min;
            do {
                k2 = random.nextInt(max - min + 1) + min;
            } while (k1 == k2);
            max += 3;
            min += 3;
            permuteRow(k1, k2);
        }
    }

    private void randomCol() {
        int k1;
        int k2;
        int max = 2;
        int min = 0;
        Random random = new Random();
        for (int k = 0; k < 3; k++) {
            k1 = random.nextInt(max - min + 1) + min;
            do {
                k2 = random.nextInt(max - min + 1) + min;
            } while (k1 == k2);
            max += 3;
            min += 3;
            permuteCol(k1, k2);
        }
    }
    private void permuteRow(int row1, int row2) {
        for (int c = 0; c < 9; c++) {
            int temp;
            temp = this.soln[row1][c];
            soln[row1][c] = soln[row2][c];
            soln[row2][c] = temp;
        }
    }

    private void permuteCol(int col1, int col2) {
        for (int r = 0; r < 9; r++) {
            int temp;
            temp = soln[r][col1];
            soln[r][col1] = soln[r][col2];
            soln[r][col2] = temp;
        }
    }

    private void rowGroupChange(int row1, int row2) {
        for (int i = 0; i < 3; i++) {
            for (int c = 0; c < 9; c++) {
                int temp;
                temp = soln[row1 + i][c];
                soln[row1 + i][c] = soln[row2 + i][c];
                soln[row2 + i][c] = temp;
            }
        }
    }

    private void colGroupChange(int col1, int col2) {
        for (int i = 0; i < 3; i++) {
            for (int r = 0; r < 9; r++) {
                int temp;
                temp = soln[r][col1 + i];
                soln[r][col1 + i] = soln[r][col2 + i];
                soln[r][col2 + i] = temp;
            }
        }
    }

    /**
     * Pobiera plansze.
     * @return Orginalna plansza.
     */
    public int[][] getBoard() {
        return this.board;
    }

    /**
     * Pobiera rozwiazanie.
     * @return rozwiazanie do planszy.
     */
    public int[][] getSoln() {
        return this.soln;
    }
}