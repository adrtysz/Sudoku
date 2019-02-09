package main.java.ui;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;

/**
 
* Klasa Answer reprezentuje odpowied� w grze Sudoku. Jest to liczba, kt�ra ca�kowicie wype�nia
 * podany kwadrat. Mo�e mie� wiele kolor�w, chocia� trzy s� zarezerwowane: Black jest pocz�tkow� odpowiedzi�, zielony jest
 * podpowiedzi�, a Dark Red to niepoprawna odpowied�. Ka�dy inny kolor reprezentuje gracza, kt�ry poprawnie odpowiedzia� na to pole.
 */
public class Answer extends Text implements Comparable<Answer> {
    private boolean isVisible = false;
    private int value = 0;

    /**
     * Tworzy now� odpowied�, z domy�lnym rozmiarem czcionki 50 i kt�ra nie jest widoczna.
     */
    public Answer() {
        this.clear();
        this.setFont(new Font(Screen.getPrimary().getVisualBounds().getHeight() / 20.8));
        this.setFill(Color.BLACK);
        this.setVisible(false);
    }

    /**
     * Ustawia warto�� odpowiedzi. Je�li nie ma mi�dzy 1 a 9, warto�� jest ustawiona na 0, a odpowied� nie jest widoczna.
     * @param ans integer reprezentuj�cy odpowiedz.
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
     * Metoda kt�ra ustawi tekst na zero, sprawi, �e b�dzie niewidoczny i ustawi warto�� odpowiedzi na 0.
     */
    public void clear() {
        this.setText("");
        this.setVisible(false);
        isVisible = false;
        value = 0;
    }

    /**
     * Metoda, kt�ra okre�la, czy odpowied� jest widoczna.
     */
    public boolean getVisible() {
        return isVisible;
    }

    /**
     * Porownuje odpowiedz z odpowiedz� podana przez gracza.
     */
    @Override
    public int compareTo(Answer answer) {
        return answer.getValue() - this.getValue();
    }

    /**
     * Dwie odpowiedzi s� r�wnowa�ne, je�li ich warto�ci s� takie same, a ich wype�nienia s� takie same. Rzuca
     * IllegalArgumentException, je�li dany obiekt nie nale�y do klasy Answer.
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
     * Zwraca hashcode zdefiniowany jako kombinacja odpowiedzi i jej sk�adnik�w kolor�w.
     * @return hash code.
     */
    @Override
    public int hashCode() {
        return (31 * this.value) + (int) (((Color) this.getFill()).getRed() * 23) +
                (int) (((Color) this.getFill()).getGreen() * 7) + (int) (((Color) this.getFill()).getBlue() * 19);
    }
}
