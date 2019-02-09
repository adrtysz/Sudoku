package main.java.logic;

import javafx.scene.paint.Color;
import main.java.ui.Square;

/**
 *Klasa Controller odpowiedzialna jest za aktualny stan gry konkretnego gracza. 
 *Trzyma równie¿ informacje tj. host i port na którym po³¹czony jest gracz.
 */
public class Controller {
    private Square lastClicked;
    private final String playerName;
    private final Color playerColor;
    private boolean isNote;
    private boolean isPlay;
    private volatile int[][] board;
    private volatile int[][] solnBoard;
    private final int serverPort;
    private final String serverHost;
    private int spaces;
    private int id;
    private SudokuLoader loader;
    private String[] input = new String[9];

    /**
     * @param name       - nazwa gracza
     * @param color      - kolor wybrany przez gracza
     * @param serverHost - nazwa hosta
     * @param serverPort - numer portu
     */
    public Controller(String name, Color color, String serverHost, int serverPort) {
        this.playerName = name;
        this.playerColor = color;
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    /**
     * @return Square, reprezentacja ostatnio kliknietego pola.
     */
    public Square getLastClicked() {
        return lastClicked;
    }

    /**
     * @return String - nazwa gracza.
     */
    public String getName() {
        return playerName;
    }

    /**
     * @return Color - kolor wybrany przez gracza.
     */
    public Color getColor() {
        return playerColor;
    }

    /**
     * @return boolean reprezentuj¹cy to, czy gracz tworzy na planszy notatki.
     */
    public boolean isNote() {
        return isNote;
    }

    /**
     * @return boolean reprezentuj¹cy to, czy gra jest w toku, czy zostala zawieszona(paused).
     */
    public boolean isPlay() {
        return isPlay;
    }

    /**
     * @param square ustawia Square jako ostatnio klikniete pole.
     */
    public void setLastClicked(Square square) {
        this.lastClicked = square;
    }

    /**
     * @param note ustawia status notowania.
     */
    public void setNote(boolean note) {
        this.isNote = note;
    }

    /**
     * @param play ustawia status gry(w toku lub pauza)
     */
    public void setPlay(boolean play) {
        this.isPlay = play;
    }

    /**
     * Pobiera tablicê z rozwi¹zaniem.
     *
     * @return dwuwymiarowa tablica int, ktora jest rozwiazaniem planszy.
     */
    public synchronized int[][] getSolnBoard() {
        return solnBoard;
    }

    /**
     * Ustawia tablice z rozwiazanie,.
     *
     * @param solnBoard dwuwymiarowa tablica int z rozwiazaniem.
     */
    public synchronized void setSolnBoard(int[][] solnBoard) {
        this.solnBoard = solnBoard;
    }

    /**
     * Pobiera numer portu serwera.
     *
     * @return port serwera jako int
     */
    public int getServerPort() {
        return this.serverPort;
    }

    /**
     * @return nazwa hosta jako string.
     */
    public String getServerHost() {
        return this.serverHost;
    }

    /**
     * Ustawia liczbe miejsc(not-ready).
     *
     * @param spaces liczba wolnych miejsc.
     */
    public void setSpaces(int spaces) {
        this.spaces = spaces;
    }

    /**
     * Pobiera liczbe miejsc.
     *
     * @return liczbe wolnych miejsc.
     */
    public int getSpaces() {
        return this.spaces;
    }

    /**
     * Pobiera ID gracza.
     *
     * @return aktualne ID gracza.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Ustawia ID gracza.
     *
     * @param id to ID aktualnego gracza.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * ustawia aktualn¹ plansze.
     *
     * @param board aktualna plansza
     */
    public void setBoard(int[][] board) {
        this.board = board;
    }

    /**
     * pobiera aktualna plansza.
     *
     * @return aktualna plansza.
     */
    public int[][] getBoard() {
        return this.board;
    }

    public void setLoader(SudokuLoader loader)  {
        this.loader = loader;
    }

    public void ready() {
        loader.ready();
    }

    public void setInput(String[] input) {
        this.input = input;
    }

    public String[] getInput() {
        return this.input;
    }
}
