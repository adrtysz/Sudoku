package main.java.ui;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Ta klasa reprezentuje pojedynczy kwadrat na planszy Sudoku. Ma wszystkie elementy interfejsu powi¹zane z kwadratem, takie jak
 * answer i notes.
 */
public class Square extends StackPane {
    private final Notes notes = new Notes();
    private final Answer answer = new Answer();
    private final Rectangle overlay = new Rectangle(85, 85, Color.TRANSPARENT);
    private boolean selected = false;
    private final int row;
    private final int col;
    
    public Square(int row, int col) {
        this.row = row;
        this.col = col;
        overlay.setStroke(Color.BLACK);
        this.setMinSize(86, 86);
        this.setMaxSize(86, 86);
        this.getChildren().addAll(overlay, answer, notes);
    }

    public Answer getAnswer() {
        return answer;
    }

    public Notes getNotes() {
        return notes;
    }

    public Rectangle getOverlay() {
        return overlay;
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public boolean isSelected() {
        return selected;
    }

  
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void clear() {
        notes.clear();
        answer.clear();
        this.setColor(Color.color(1, 1, 1, 1));
    }

    private void setColor(Color color) {
        this.overlay.setFill(color);
    }

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
