package main.java.ui;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;

/**
* Klasa ta reprezentuje notatki, kt�re u�ytkownik mo�e wprowadzi� do jednego kwadratu. 
 */
public class Notes extends GridPane {
    private Text[] notes = new Text[9];
    private boolean[] visibility = new boolean[9];

    public Notes() {
        this(0, 10, 0, 10);
    }

    public Notes(int padding) {
        this(padding, padding, padding, padding);
    }

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

    public void show(int note) {
        notes[note].setVisible(true);
        visibility[note] = true;
    }


    public void hide(int note) {
        notes[note].setVisible(false);
        visibility[note] = false;
    }

    public void toggle(int note) {
        if (visibility[note]) {
            hide(note);
        } else {
            show(note);
        }
    }

    public void clear() {
        for (int k = 0; k < 9; k++) {
            this.hide(k);
        }
    }

    public boolean[] getVisibility() {
        return visibility;
    }

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