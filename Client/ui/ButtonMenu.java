package main.java.ui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;

/**
 * ButtonMenu to klasa, kt�ra definiuje lew� stron� gry Sudoku. Sk�ada si� z przycisku note / answer,
* Przycisk clear, pause / play, Show Answer. Ma r�wnie� numery aby wprowadzi� odpowied� lub notatk�.
 */
public class ButtonMenu extends VBox {
    private final Button note = new Button("Note");
    private final Button clear = new Button("Clear");
    private final Button newGame = new Button("New game");
    private final Button hint = new Button("Hint");
    private final Button send = new Button("Send game");
    private final Numbers numbers = new Numbers();

    /**
     * Konstruktor dla wygody, kt�ry u�ywa padding 10, aby utworzy� menu ButtonMenu.
     */
    public ButtonMenu() {
        this(10);
    }

    /**
     * Konstruktor tworz�cy ButtonMenu z danym paddingiem. Spowoduje to utworzenie domy�lnego menu z przyciskami
     * HINT, CLEAR, PAUSE, SHOW ANSWER, i numery.
     * @param padding integer ktory okresla jaki ma byc padding.
     */
    private ButtonMenu(int padding) {
        numbers.setPadding(new Insets(padding));
        note.setPadding(new Insets(padding));
        clear.setPadding(new Insets(padding));
        newGame.setPadding(new Insets(padding));
        hint.setPadding(new Insets(padding));
        numbers.setPadding(new Insets(padding));
        send.setPadding(new Insets(padding));
        this.getChildren().addAll(note, clear, newGame, hint, numbers, send);
    }

    /**
     * Metoda kt�ra pobiera przycisk Note.
     * @return przycisk Note.
     */
    public Button getNote() {
        return note;
    }

    /**
      * Metoda kt�ra pobiera przycisk Clear.
     * @return przycisk Clear.
     */
    public Button getClear() {
        return clear;
    }

    /**
     * Metoda kt�ra pobiera przycisk New game.
     * @return przycisk New game.
     */
    public Button getNewGame() {
        return newGame;
    }

    /**
      * Metoda kt�ra pobiera przycisk Hint.
     * @return przycisk Hint.
     */
    public Button getHint() {
        return hint;
    }

    /**
      * Metoda kt�ra pobiera przycisk send.
     * @return przycisk send.
     */
    public Button getSend() {
        return send;
    }

    /**
     * Metoda ktora pobiera przycisk z konkretnym numerem.
     * @param num numer, ktory chcemy dostac.
     * @return przycisk z okre�lon� cyfr�.
     */
    public Button getNumber(int num) {
        return numbers.getNumber(num);
    }

    /**
     * Metoda ktora wy��cza wszystkie przyciski z numerami.
     */
    public void disable() {
        note.setDisable(true);
        clear.setDisable(true);
        hint.setDisable(true);
        numbers.disable();
    }

    /**
     * Metoda ktora w��cza wszystkie przyciski z numerami.
     */
    public void enable() {
        note.setDisable(false);
        clear.setDisable(false);
        hint.setDisable(false);
        numbers.enable();
    }

    /**
     * Klasa Numbers to GridPane z siatk� 3x3 z 9 liczb sudoku.
     */
    private class Numbers extends GridPane {
        private final Button[] buttons = new Button[9];

        /**
         * Tworzy nowy obiekt Numbers.
         */
        public Numbers() {
            for (int r = 0; r < 3; r ++) {
                for (int c = 0; c < 3; c++) {
                    double height = Screen.getPrimary().getVisualBounds().getHeight();
                    Button number = new Button(" " + (1 + c + (r * 3)));
                    number.setMinSize(height / 35, height / 35);
                    buttons[c + (r * 3)] = number;
                    this.add(buttons[c + (r * 3)], c, r);
                }
            }
        }

        /**
         *Pobiera konkretny przycisk z liczb�.
         * @param num Liczba(od 0 do 8).
         * @return Przycisk reprezentujacy ta liczbe.
         */
        public Button getNumber(int num) {
            return buttons[num];
        }

        /**
         * Wylacza Number.
         */
        public void disable() {
            for (int i = 0; i < 9; i++) {
                buttons[i].setDisable(true);
            }
        }

        /**
         * Wlacza Number.
         */
        public void enable() {
            for (int i = 0; i < 9; i++) {
                buttons[i].setDisable(false);
            }
        }
    }
}
