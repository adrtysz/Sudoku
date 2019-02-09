package main.java.ui;

import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Ta klasa jest odpowiedzialna za tworzenie i zarz�dzanie paskiem narz�dzi zawieraj�cego nazwy graczy.
 */
public class InfoMenu extends ToolBar {
    private final Text pause = new Text("Playing...");
    private final GameUI ui;

    /**
     * Tworzy domy�lny pasek narz�dzi ze wska�nikiem pauzy i wysoko�ci� 25
     * @param ui GameUI obecnej gry
     */
    public InfoMenu(GameUI ui) {
        this(ui, 25);
    }

    /**
     * Tworzy pasek narz�dzi ze wskazan� wysoko�ci�.
     * @param ui GameUI obecnej gry.
     * @param height ��dana wysoko��.
     */
    public InfoMenu(GameUI ui, double height) {
        this.setPrefHeight(height);
        this.getItems().add(pause);
        this.ui = ui;
    }

    /**
     * Tworzy pasek narz�dziowy z podanymi nazwami i kolorem gracza.
     * @param name - nazwa gracza.
     * @param color - kolor gracza.
     * @param ui - GameUI obecnej gry.
     */
    public InfoMenu(String[] name, Color[] color, GameUI ui) {
        this(ui);
        this.getItems().add(new HBox(20, pause));
        for (int k = 0; k < name.length; k++) {
            Text item = new Text(name[k]);
            item.setFill(color[k]);
            HBox person = new HBox(20, item);
            this.getItems().add(person);
        }
    }

    /**
     * Dodaje nowego gracza.
     * @param name - nazwa gracza.
     * @param color - kolor gracza.
     */
    public void addPlayer(String name, Color color) {
        Text person = new Text(name);
        person.setFill(color);
        this.getItems().addAll(new HBox(20, person));
    }

    /**
     * Play lub Pause.
     * @param isPause je�li gra jest zastopowana.
     */
    public void setPause(boolean isPause) {
        if (isPause) {
            this.pause.setText("Paused..");
        } else {
            this.pause.setText("Playing...");
        }
    }

    /**
     * Usuwa gracza.
     * @param id - ID gracza do usuni�cia.
     */
    public void removePlayer(int id) {
        if (id > ui.getControl().getId()) {
            this.getItems().set(id, new HBox(20));
        } else {
            this.getItems().set(id + 1, new HBox(20));
        }
    }
}
