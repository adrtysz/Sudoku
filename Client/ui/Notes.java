package main.java.ui;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;

/**
 *Ta klasa reprezentuje uwagi/notatki, kt�re u�ytkownik mo�e wprowadzi� do jednego kwadratu
 */
public class Notes extends GridPane {
    private Text[] notes = new Text[9];
    private boolean[] visibility = new boolean[9];

    /**
     * Tworzy nowy obiekt Notes, u�ywaj�c domy�lnego padding o warto�ci 10 dla lewej i prawej strony. 
     */
    public Notes() {
        this(0, 10, 0, 10);
    }

    /**
    * Tworzy nowy obiekt Notes, u�ywaj�c danego padding dla wszystkich czterech bok�w.
     * @param padding integer, wartosc odstepu jaki chcemy ustawic.
     */
    public Notes(int padding) {
        this(padding, padding, padding, padding);
    }

    /**
     * Tworzy nowy obiekt Notes, u�ywaj�c czterech podanych parametr�w wype�nienia.
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
     * Pokazuje podan� notatk�, indeks od 0-8.
     * @param note - Liczba ca�kowita z zakresu od 0 do 8 w��cznie.
     */
    public void show(int note) {
        notes[note - 1].setVisible(true);
        visibility[note - 1] = true;
    }

    /**
     * Ukrywa podan� notatk�, indeks od 0-8.
     * @param note - Liczba ca�kowita z zakresu od 0 do 8 w��cznie
     */
    public void hide(int note) {
        notes[note - 1].setVisible(false);
        visibility[note - 1] = false;
    }

    /**
     * Prze��cza dan� notatk�, indeks od 0-8. Je�li notatka jest obecnie widoczna, jest ukrywana i odwrotnie.
     * @param note - Liczba ca�kowita z zakresu od 0 do 8 w��cznie.
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
     * Otrzymuje boolowsk� tablic� widoczno�ci dla ka�dej notatki. Zauwa�, �e indeksy 0-8 koreluj� z liczbami
     * 1-9; to znaczy, indeks 7 daje widoczno�� dla 8 notatki.
     * @return tablica boolean trzymaj�ca widoczno�ci wszystkich notatek.
     */
    public boolean[] getVisibility() {
        return visibility;
    }

    /**
     * Sprawdza r�wno�� dw�ch instancji Notes; robi to poprzez por�wnywanie wszystkich widoczno�ci.
     * @param obj obiekt typu Notes.
     * @return boolean stwierdzaj�cy, czy obiekty s� r�wne.
     * @throws IllegalArgumentException je�li obiekt nie jest typu Notes.
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
