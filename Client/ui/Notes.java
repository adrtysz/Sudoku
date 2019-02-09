package main.java.ui;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;

/**
 *Ta klasa reprezentuje uwagi/notatki, które u¿ytkownik mo¿e wprowadziæ do jednego kwadratu
 */
public class Notes extends GridPane {
    private Text[] notes = new Text[9];
    private boolean[] visibility = new boolean[9];

    /**
     * Tworzy nowy obiekt Notes, u¿ywaj¹c domyœlnego padding o wartoœci 10 dla lewej i prawej strony. 
     */
    public Notes() {
        this(0, 10, 0, 10);
    }

    /**
    * Tworzy nowy obiekt Notes, u¿ywaj¹c danego padding dla wszystkich czterech boków.
     * @param padding integer, wartosc odstepu jaki chcemy ustawic.
     */
    public Notes(int padding) {
        this(padding, padding, padding, padding);
    }

    /**
     * Tworzy nowy obiekt Notes, u¿ywaj¹c czterech podanych parametrów wype³nienia.
     * @param topPad gorny padding.
     * @param rightPad prawy padding.
     * @param bottomPad dolny padding.
     * @param leftPad lewy padding.
     */
    public Notes(int topPad, int rightPad, int bottomPad, int leftPad) {
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        this.setPadding(new Insets(topPad, rightPad, bottomPad, leftPad));
        for (int c = 0; c < 3; c++) {
            for (int r = 0; r < 3; r++) {
                notes[c + (r * 3)] = new Text(" " + Integer.toString(1 + c + (r * 3)) + " ");
                notes[c + (r * 3)].setFont(new Font(screenHeight / 52));
                this.add(notes[c + (r * 3)], c, r);
                this.visibility[c + (r * 3)] = true;
            }
        }
    }

    /**
     * Pokazuje podan¹ notatkê, indeks od 0-8.
     * @param note - Liczba ca³kowita z zakresu od 0 do 8 w³¹cznie.
     */
    public void show(int note) {
        notes[note - 1].setVisible(true);
        visibility[note - 1] = true;
    }

    /**
     * Ukrywa podan¹ notatkê, indeks od 0-8.
     * @param note - Liczba ca³kowita z zakresu od 0 do 8 w³¹cznie
     */
    public void hide(int note) {
        notes[note - 1].setVisible(false);
        visibility[note - 1] = false;
    }

    /**
     * Prze³¹cza dan¹ notatkê, indeks od 0-8. Jeœli notatka jest obecnie widoczna, jest ukrywana i odwrotnie.
     * @param note - Liczba ca³kowita z zakresu od 0 do 8 w³¹cznie.
     */
    public void toggle(int note) {
        if (visibility[note - 1]) {
            hide(note);
        } else {
            show(note);
        }
    }

    public void clear() {
        for (int k = 0; k < 9; k++) {
            this.hide(k + 1);
        }
    }

    /**
     * Otrzymuje boolowsk¹ tablicê widocznoœci dla ka¿dej notatki. Zauwa¿, ¿e indeksy 0-8 koreluj¹ z liczbami
     * 1-9; to znaczy, indeks 7 daje widocznoœæ dla 8 notatki.
     * @return tablica boolean trzymaj¹ca widocznoœci wszystkich notatek.
     */
    public boolean[] getVisibility() {
        return visibility;
    }

    /**
     * Sprawdza równoœæ dwóch instancji Notes; robi to poprzez porównywanie wszystkich widocznoœci.
     * @param obj obiekt typu Notes.
     * @return boolean stwierdzaj¹cy, czy obiekty s¹ równe.
     * @throws IllegalArgumentException jeœli obiekt nie jest typu Notes.
     */
    @Override
    public boolean equals(Object obj) throws IllegalArgumentException {
        if (!(obj instanceof Notes)) {
            throw new IllegalArgumentException("Object not of Notes class");
        }
        for (int i = 0; i < 9; i++) {
            if (visibility[i] != ((Notes) obj).visibility[i]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int out = 0;
        for (int i = 0; i < 9; i++) {
            if (visibility[i]) {
                out += i * 31;
            }
        }
        return out;
    }
}
