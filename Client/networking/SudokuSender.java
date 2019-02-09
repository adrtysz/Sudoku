package main.java.networking;

import main.java.logic.Controller;
import main.java.ui.Square;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javafx.scene.paint.Color;

/**
 * Ta klasa wysy³a informacje do serwera, które zostan¹ wys³ane do wszystkich innych graczy. Tworzy pakiet przy u¿yciu podanych
 * danych, a nastêpnie wysy³a pakiet do dystrybucji.
 */
public class SudokuSender implements Runnable {
    private Square[] squares;
    private String message;
    private boolean isMessage = false;
    private boolean isRemove = false;
    private boolean isGame = false;
    private boolean isNew = false;
    private Color messageColor;
    private final Controller control;

    /**
     * @param control kontroler
     * @param squares modyfikowane pola.
     */
    public SudokuSender(Controller control, Square...squares) {
        this.control = control;
        this.squares = squares;
    }

    /**
     * Pakiet dla wiadomoœci.
     * @param control konroler.
     * @param color kolor wiadomosci.
     * @param message zawartoœæ wiadomoœci.
     */
    public SudokuSender(Controller control, Color color, String message) {
        this.control = control;
        this.message = message;
        this.isMessage = true;
        this.messageColor = color;
    }

    /**
     * Pakiet dla usuwania wiadomosci.
     * @param control kontroler.
     */
    public SudokuSender(Controller control) {
        this.control = control;
        this.isRemove = true;
    }

    /**
     * Tworzy nadawcê, aby uzyskaæ gry.
     * @param control kontroler.
     * @param isGame 
     */
    public SudokuSender(Controller control, boolean isGame) {
        this.control = control;
        this.isGame = true;
    }

    public SudokuSender(Controller control, String isNew) {
        this.control = control;
        this.isNew = true;
    }

    /**
     * Ta metoda tworzy pakiet, a nastêpnie wysy³a go do serwera w celu dystrybucji.
     */
    @Override
    public void run() {
        try (Socket client = new Socket(control.getServerHost(), control.getServerPort());
            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(client.getInputStream())) {
            SudokuPacket packet;
            if (isGame) {
                packet = new SudokuPacket();
            } else if (isMessage) {
                packet = new SudokuPacket(this.message, this.messageColor, control.getId(), true);
            } else if (isRemove) {
                packet = new SudokuPacket(control.getId());
            } else if (isNew) {
                packet = new SudokuPacket(true);
            } else {
                packet = new SudokuPacket(this.squares);
            }
            out.writeObject(packet);
            out.flush();
            try {
                in.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
