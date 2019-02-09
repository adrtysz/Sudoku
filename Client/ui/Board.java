package main.java.ui;

import com.sun.istack.internal.NotNull;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 * Board jest klas¹ odpowiedzialn¹ za renderowanie rzeczywistej planszy Sudoku. 
 * To GridPane sk³adaj¹cy siê z 81 kwadratów.
 */
public class Board extends GridPane implements Comparable<Board> {
    private final Square[][] squares = new Square[9][9];

    /**
     * Tworzy pust¹ planszê.
     */
    public Board() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                this.squares[row][col] = new Square(row, col);
                this.add(this.squares[row][col], col, row);
            }
        }
    }

    /**
     * Zwraca konkretne pole.
     * @param row .
     * @param col .
     * @return konkretne pole(Square).
     */
    public Square getSquare(int row, int col) {
        return squares[row][col];
    }

    /**
     * Porównaj tê tablicê z drug¹. Porównuj¹ jako liczbê widocznych miejsc.
     * @return Pozytywny, jeœli ta tablica jest wiêksza ni¿ podana  w odniesieniu do liczby widocznych miejsc.
     */
    @Override
    public int compareTo(@NotNull Board board) {
        int thisVis = 0;
        int thatVis = 0;
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (this.squares[r][c].isVisible()) {
                    thisVis++;
                }
                if (board.squares[r][c].isVisible()) {
                    thatVis++;
                }
            }
        }
        return thisVis - thatVis;
    }

    /**
     * Sprawdza równoœæ dla dwóch plansz. Równoœæ jest zdefiniowana jako wszystkie pola s¹ równe.
     * @return true jesli s¹ rowne
     * @throws IllegalArgumentException jesli argument nie jest plansza
     */
    @Override
    public boolean equals(@NotNull Object obj) throws IllegalArgumentException {
        if (!(obj instanceof Board)) {
            throw new IllegalArgumentException("Object not of class Board");
        }
        Board board = (Board) obj;

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (!(board.squares[r][c].equals(this.squares[r][c]))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int thisVis = 0;
        int thisRight = 0;
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (this.squares[r][c].isVisible()) {
                    thisVis++;
                }
                if (this.squares[r][c].getOverlay().getFill() != Color.DARKRED) {
                    thisRight++;
                } else {
                    thisRight--;
                }
            }
        }
        return (thisVis * 11) + (thisRight * 17);
    }

}
