package main.java.ui;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import main.java.logic.Controller;

import java.awt.*;

public class Chat extends GridPane {
    public static final int SPACING = 20;
    private final Controller control;
    private final VBox thisPlayer;
    private final VBox thatPlayer;
    private final double chatWidth;

    /**
     * Tworzy czat z domyœln¹ szerokoœci¹ 300.
     * @param control - kontroler.
     */
    public Chat(Controller control) {
        this(300, control);
    }

    /**
     * Tworzy czat z podan¹ szerokoœci¹
     * @param chatWidth - preferowana szerokoœæ dla czatu
     * @param control - kontroler.
     */
    public Chat(double chatWidth, Controller control) {
        thisPlayer = new VBox(SPACING);
        thisPlayer.setMinWidth(chatWidth / 2);
        thatPlayer = new VBox(SPACING);
        thatPlayer.setMinWidth(chatWidth / 2);
        this.chatWidth = chatWidth;
        this.control = control;
        this.setMinWidth(chatWidth);
        Text log = new Text("Chat Log:");
        this.add(log, 0, 0, 2, 1);
        this.add(thisPlayer, 0, 1);
        this.add(thatPlayer, 1, 1);
    }

    /**
     * Chat u¿ytkownika.
     * @param msg wiadomosc do wyslania.
     */
    public void thisPlayerChat(String msg) {
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        Text message = new Text(msg);
        message.setFill(control.getColor());
        message.setWrappingWidth(thisPlayer.getMinWidth() - 2);
        message.setFont(new Font(screenHeight / 65));
        message.setTextAlignment(TextAlignment.LEFT);
        thisPlayer.getChildren().add(message);
        Text spacer = new Text(msg);
        spacer.setFill(Color.TRANSPARENT);
        spacer.setWrappingWidth(thatPlayer.getMinWidth() - 2);
        spacer.setFont(new Font(screenHeight / 65));
        spacer.setTextAlignment(TextAlignment.RIGHT);
        thatPlayer.getChildren().add(spacer);
    }

    /**
     * Czay innego uzytkownika.
     * @param msg - wiadomosc do odczytania.
     * @param color - kolor wiadomosci.
     */
    public void thatPlayerChat(String msg, Color color) {
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        Text message = new Text(msg);
        message.setFill(color);
        message.setWrappingWidth(thatPlayer.getMinWidth() - 2);
        message.setFont(new Font(screenHeight / 65));
        message.setTextAlignment(TextAlignment.RIGHT);
        thatPlayer.getChildren().add(message);
        Text spacer = new Text(msg);
        spacer.setFill(Color.TRANSPARENT);
        spacer.setWrappingWidth(thisPlayer.getMinWidth() - 2);
        spacer.setFont(new Font(screenHeight / 65));
        spacer.setTextAlignment(TextAlignment.LEFT);
        thisPlayer.getChildren().add(spacer);
        Toolkit.getDefaultToolkit().beep();
    }

    /**
     *
     * @return szerokosc dla czatu.
     */
    public double getChatWidth() {
        return this.chatWidth;
    }
}
