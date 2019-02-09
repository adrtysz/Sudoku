package main.java.ui;

import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import main.java.logic.Controller;
import main.java.networking.SudokuSender;


/**
 * GameUI to BorderPane, który obs³uguje wszystkie elementy interfejsu u¿ytkownika. Kontroler jest
 * zsynchronizowany.
 */
public class GameUI extends BorderPane {
    private final Board board = new Board();
    private final ButtonMenu menu = new ButtonMenu();
    private final InfoMenu info;
    private final Controller control;
    private final Chat chat;
    private final Chatter chatter;
    private final ScrollPane chatScroll;

    /**
     * Tworzy kompletn¹ wizualn¹ reprezentacjê obecnej gry Sudoku
     * @param control - kontroler dla gry.
     */
    public GameUI(Controller control) {
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        this.control = control;
        GridPane squares = new GridPane();
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                Rectangle box = new Rectangle(screenHeight / 4.0784, screenHeight / 4.0784, Color.rgb(0, 0, 0, 0.0));
                box.setStroke(Color.BLACK);
                box.setStrokeWidth(2.5);
                squares.add(box, c, r);
            }
        }
        squares.setDisable(true);
        info = new InfoMenu(this, screenHeight / 41.6);
        StackPane center = new StackPane(board, squares);
        chat = new Chat(screenHeight / 3.4667, control);
        chatter = new Chatter(screenHeight / 1.3867);
        chatter.getSender().setOnAction(e -> {
            chat.thisPlayerChat(chatter.getChatter().getText());
            chatter.getSender().setDisable(true);
            SudokuSender sender = new SudokuSender(control, control.getColor(), chatter.getChatter().getText());
            this.scrollToBottom();
            Thread tClient = new Thread(sender);
            tClient.start();
            chatter.getChatter().clear();
        });
        chatter.getChatter().setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                chatter.getSender().fire();
                this.scrollToBottom();
                this.requestFocus();
            }
        });
        chatter.getChatter().setOnKeyReleased(e -> {
            if (chatter.getChatter().getText().isEmpty()) {
                chatter.getSender().setDisable(true);
            } else {
                chatter.getSender().setDisable(false);
            }
        });
        Rectangle overlay = new Rectangle(chat.getChatWidth() + 6, screenHeight / 2.805, Color.color(0, 0, 0, 0));
        overlay.setStroke(Color.color(0, 0, 0, 1));
        overlay.setStrokeWidth(4);
        GridPane scroller = new GridPane();
        scroller.add(chat, 0, 0);
        VBox spacer = new VBox();
        spacer.setMinWidth(Chat.SPACING);
        scroller.add(spacer, 1, 0);
        chatScroll = new ScrollPane(scroller);
        chatScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        chatScroll.setFitToWidth(true);
        chatScroll.setVmin(0);
        chatScroll.setVmax(1);
        chatScroll.setMinWidth(screenHeight / 7.2);

        StackPane chatPane = new StackPane(overlay, chatScroll);
        chatPane.setMaxHeight(screenHeight / 1.3594);
        this.setTop(info);
        this.setCenter(center);
        this.setLeft(menu);
        this.setRight(chatPane);
        this.setBottom(chatter);
    }

    /**
     * Pobiera planszê reprezentuj¹c¹ bie¿¹c¹ grê.
     * @return plansa gry.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Pobiera bie¿¹ce ButtonMenu, które znajduje siê po lewej stronie.
     * @return ButtonMenu.
     */
    public ButtonMenu getMenu() {
        return menu;
    }

    /**
     * Pobiera bie¿¹cy pasek narzêdzi, który jest umieszczony na górze.
     * @return ToolBar z informacjami o u¿ytkowniku.
     */
    public InfoMenu getInfo() {
        return info;
    }

    /**
     * Zsynchronizowana metoda, która otrzymuje tablicê rozwi¹zañ.
     * @return dwuwymiarowa tablica int reprezentuj¹ca rozwi¹zanie.
     */
    public synchronized int[][] getSolnBoard() {
        return this.control.getSolnBoard();
    }

    /**
     * Zsynchronizowana metoda ustawiaj¹ca tablicê rozwi¹zania. .
     * @param solnBoard dwuwymiarowa tablica int reprezentuj¹ca rozwi¹zanie.
     */
    public synchronized void setSolnBoard(int[][] solnBoard) {
        this.control.setSolnBoard(solnBoard);
    }

    /**
     * Pobiera kontroler
     * @return kontroler obecnej gry..
     */
    public Controller getControl() {
        return this.control;
    }

    /**
     * Pobiera czat który znajduje siê po prawej stronie.
     * @return czat obecnej gry.
     */
    public Chat getChat() {
        return chat;
    }

    public void scrollToBottom() {
        this.chatScroll.layout();
        this.chatScroll.setVvalue(Double.MAX_VALUE);
    }
}
