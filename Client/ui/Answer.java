package main.java.ui;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;

/**
 
* Klasa Answer reprezentuje odpowiedŸ w grze Sudoku. Jest to liczba, która ca³kowicie wype³nia
 * podany kwadrat. Mo¿e mieæ wiele kolorów, chocia¿ trzy s¹ zarezerwowane: Black jest pocz¹tkow¹ odpowiedzi¹, zielony jest
 * podpowiedzi¹, a Dark Red to niepoprawna odpowiedŸ. Ka¿dy inny kolor reprezentuje gracza, który poprawnie odpowiedzia³ na to pole.
 */
public class Answer extends Text implements Comparable<Answer> {
    private boolean isVisible = false;
    private int value = 0;

    /**
     * Tworzy now¹ odpowiedŸ, z domyœlnym rozmiarem czcionki 50 i która nie jest widoczna.
     */
    public Answer() {
        this.clear();
        this.setFont(new Font(Screen.getPrimary().getVisualBounds().getHeight() / 20.8));
        this.setFill(Color.BLACK);
        this.setVisible(false);
    }

    /**
     * Ustawia wartoœæ odpowiedzi. Jeœli nie ma miêdzy 1 a 9, wartoœæ jest ustawiona na 0, a odpowiedŸ nie jest widoczna.
     * @param ans integer reprezentuj¹cy odpowiedz.
     */
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

    /**
     * Pobiera wartosc odpowiedzi jako integer.
     * @return wartosc integer.
     */
    public int getValue() {
        return value;
    }

    /**
     * Metoda która ustawi tekst na zero, sprawi, ¿e bêdzie niewidoczny i ustawi wartoœæ odpowiedzi na 0.
     */
    public void clear() {
        this.setText("");
        this.setVisible(false);
        isVisible = false;
        value = 0;
    }

    /**
     * Metoda, która okreœla, czy odpowiedŸ jest widoczna.
     */
    public boolean getVisible() {
        return isVisible;
    }

    /**
     * Porownuje odpowiedz z odpowiedz¹ podana przez gracza.
     */
    @Override
    public int compareTo(Answer answer) {
        return answer.getValue() - this.getValue();
    }

    /**
     * Dwie odpowiedzi s¹ równowa¿ne, jeœli ich wartoœci s¹ takie same, a ich wype³nienia s¹ takie same. Rzuca
     * IllegalArgumentException, jeœli dany obiekt nie nale¿y do klasy Answer.
     */
    @Override
    public boolean equals(Object object) throws IllegalArgumentException {
        if (object == null || !(object instanceof Answer)) {
            throw new IllegalArgumentException("Valid variable of class Answer not given!");
        } else {
            return (((Answer) object).getValue() == this.getValue()) && ((Answer) object).getFill().equals(this.getFill());
        }
    }

    /**
     * Zwraca hashcode zdefiniowany jako kombinacja odpowiedzi i jej sk³adników kolorów.
     * @return hash code.
     */
    @Override
    public int hashCode() {
        return (31 * this.value) + (int) (((Color) this.getFill()).getRed() * 23) +
                (int) (((Color) this.getFill()).getGreen() * 7) + (int) (((Color) this.getFill()).getBlue() * 19);
    }
}
