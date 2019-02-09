package main.java.ui;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/** 
* Klasa Answwer reprezentuje odpowiedŸ w grze Sudoku. Mo¿e mieæ wiele kolorów, chocia¿ trzy s¹ zarezerwowane: czarny jest pocz¹tkow¹ odpowiedzi¹, zielony jest danym
 * odpowiedŸ, a Dark Red to niepoprawna odpowiedŸ. Ka¿dy inny kolor reprezentuje gracza, który poprawnie odpowiedzia³ na to pole.
 */
public class Answer extends Text implements Comparable<Answer> {
    private boolean isVisible = false;
    private int value = 0;

    public Answer() {
        this.clear();
        this.setFont(new Font(50));
        this.setFill(Color.BLACK);
        this.setVisible(false);
    }

    public void setValue(int ans) {
        if (ans > 9 || ans < 1) {
            this.clear();
        } else {
            this.setText(Integer.toString(ans));
            this.setVisible(true);
            isVisible = true;
            value = ans;
        }
    }

    public int getValue() {
        return value;
    }

    public void clear() {
        this.setText("");
        this.setVisible(false);
        isVisible = false;
        value = 0;
    }

    public boolean getVisible() {
        return isVisible;
    }

    @Override
    public int compareTo(Answer answer) {
        return answer.getValue() - this.getValue();
    }

    @Override
    public boolean equals(Object object) throws IllegalArgumentException {
        if (object == null || !(object instanceof Answer)) {
            throw new IllegalArgumentException("Valid variable of class Answer not given!");
        } else {
            return (((Answer) object).getValue() == this.getValue()) && ((Answer) object).getFill().equals(this.getFill());
        }
    }

    @Override
    public int hashCode() {
        return (31 * this.value) + (int) (((Color) this.getFill()).getRed() * 23) +
                (int) (((Color) this.getFill()).getGreen() * 7) + (int) (((Color) this.getFill()).getBlue() * 19);
    }
}
