package main.java.ui;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;


public class Chatter extends HBox {
    private final TextField chatter = new TextField();
    private final Button sender = new Button("Send");

    /**
     * Tworzy rozmowê o domyœlnej szerokoœci 750
     */
    public Chatter() {
        this(750);
    }

    /**
     * Tworzy rozmowê o podanej szerokosci
     * @param sendWidth - szerokosc.
     */
    public Chatter(double sendWidth) {
        chatter.setMinWidth(sendWidth);
        this.getChildren().addAll(chatter, sender);
        this.setTranslateX(sendWidth / 7.5);
        sender.setMinSize(sendWidth / 50, sendWidth / 30);
        sender.setDisable(true);
    }

    /**
     * pobiera przycisk send.
     * @return przycisk send.
     */
    public Button getSender() {
        return sender;
    }

    /**
     * Pobiera pole dla czatu..
     * @return pasek czatu.
     */
    public TextField getChatter() {
        return this.chatter;
    }


}
