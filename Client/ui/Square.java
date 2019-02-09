package main.java.ui;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;

/**
 * Ta klasa reprezentuje pojedynczy kwadrat na planszy Sudoku. Ma wszystkie elementy interfejsu powi¹zane z kwadratem, takie jak
 * answer i notes.
 */
public class Square extends StackPane {
    private final Notes notes = new Notes();
    private final Answer answer = new Answer();
    private final Rectangle overlay = new Rectangle(
            Screen.getPrimary().getVisualBounds().getHeight() / 12.2353,
            Screen.getPrimary().getVisualBounds().getHeight() / 12.2353,
            Color.TRANSPARENT);
    private boolean selected = false;
    private final int row;
    private final int col;

    /**
     * Tworzy kwadrat, który ma okreœlony wiersz i kolumnê.
     * @param row - wybrany wiersz.
     * @param col - wybrana kolumna.
     */
    public Square(int row, int col) {
        this.row = row;
        this.col = col;
        overlay.setStroke(Color.BLACK);
        this.setMinSize(1 + (Screen.getPrimary().getVisualBounds().getHeight() / 12.2353),
                1 + (Screen.getPrimary().getVisualBounds().getHeight() / 12.2353));
        this.setMaxSize(1 + (Screen.getPrimary().getVisualBounds().getHeight() / 12.2353),
                1 + (Screen.getPrimary().getVisualBounds().getHeight() / 12.2353));
        this.getChildren().addAll(overlay, answer, notes);
    }

    /**
     * Pobiera odpowiedz zawart¹ w konkretnym kwadracie.
     * @return obiekt Answer.
     */
    public Answer getAnswer() {
        return answer;
    }

    /**
     * Pobiera notatkê zawart¹ w wadracie.
     * @return obiekt Notes.
     */
    public Notes getNotes() {
        return notes;
    }

    /**
     * Pobiera nak³adkê(kolor) dla kwadratu.
     */
    public Rectangle getOverlay() {
        return overlay;
    }

    /**
     * Pobiera wiersz.
     * @return wiersz wybranego kwadratu.
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Pobiera kolumne.
     * @return kolumnê wybranego kwadratu.
     */
    public int getCol() {
        return this.col;
    }

    /**
     *Pobiera, czy ten kwadrat jest zaznaczony.
     * @return czy jest zaznaczony czy nie.
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Ustawia kwadrat jako zaznaczony.
     * @param selected - status kwadratu, ktory jest zaznaczony.
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * Czyœci kwadrat z odpowiedzi i notatek.
     */
    public void clear() {
        notes.clear();
        answer.clear();
        this.setColor(Color.color(1, 1, 1, 1));
    }

    private void setColor(Color color) {
        this.overlay.setFill(color);
    }


    /**
     * Sprawdza równowa¿noœæ miêdzy tym kwadratem a innym. S¹ takie same, jeœli ich odpowiedŸ i notatki s¹ takie same,
     * i ich nak³adki, wiersze i kolumny równie¿ s¹ równe.
     * @param obj obiekt Square.
     * @return czy obiekty s¹ równe.
     * @throws IllegalArgumentException jeœli obiekt nie jest typu Square.
     */
    @Override
    public boolean equals(Object obj) throws IllegalArgumentException {
        Square sq;
        if (!(obj instanceof Square)) {
            throw new IllegalArgumentException("Non-Square Object Given");
        } else {
            sq = (Square) obj;
        }
        boolean isNotes = this.notes.equals(sq.notes);
        boolean isAnswer = this.answer.equals(sq.answer);
        boolean isSquare = this.overlay.equals(sq.overlay) && (this.row == sq.row) && (this.col == sq.col);
        return isNotes && isAnswer && isSquare;
    }
}
