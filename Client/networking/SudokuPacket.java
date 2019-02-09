package main.java.networking;

import javafx.scene.paint.Color;
import main.java.ui.Square;

import java.io.Serializable;
import java.util.ArrayList;

/**
* Ta klasa jest odpowiedzialna za handel danymi miêdzy serwerem a klientami. Jest to serializowalne i reprezentuje
 * podstawowe dane wysy³ane przez socket. TYLKO SudokuPackets mog¹ byæ wysy³ane.
 */
public class SudokuPacket implements Serializable {
    private Data[] data;
    private String name;
    private double[] color = new double[4];
    private int[][] board;
    private int[][] solnBoard;
    private int spaces;
    private boolean isBoard = false;
    private boolean isPlayer = false;
    private boolean isMessage = false;
    private boolean isStarter = false;
    private boolean isRemove = false;
    private String message;
    private int id;
    private boolean isLast = false;
    private boolean isInput = false;
    private String[] input;
    private boolean isQuery = false;
    private String[] games;
    private String game;
    private boolean isGame = false;
    private boolean isNew = false;

    /**
     * This constructor makes a packet for a new player.
     * @param name The player name.
     * @param color The player color.
     * @param isPlayer An identifier for if it is a player.
     */
    public SudokuPacket(String name, Color color, boolean isPlayer) {
        this.name = name;
        this.color[0] = color.getRed();
        this.color[1] = color.getGreen();
        this.color[2] = color.getBlue();
        this.color[3] = color.getOpacity();
        this.isPlayer = true;
        this.data = null;
    }

    public SudokuPacket(boolean isNew) {
        this.isNew = true;
        this.spaces = 30;
    }

    public SudokuPacket(String gameName, String name, Color color) {
        this(name, color, true);
        this.game = gameName;
        this.isGame = true;
    }

    /**
     * Ten konstruktor tworzy packet dla usunietego gracza.
     * @param name - nazwa gracza.
     * @param color - kolor gracza.
     * @param id - id gracza.
     */
    public SudokuPacket(String name, Color color, int id) {
        this(name, color, true);
        this.id = id;
    }

    /**
     * Ten kosntruktor tworzy plansze.
     * @param board - plansza.
     * @param solnBoard - rozwiazanie.
     * @param name - nazwa gracza.
     * @param color -kolor gracza.
     */
    public SudokuPacket(int[][] board, int[][] solnBoard, String name, Color color) {
        this.name = name;
        this.color[0] = color.getRed();
        this.color[1] = color.getGreen();
        this.color[2] = color.getBlue();
        this.color[3] = color.getOpacity();
        this.board = board;
        this.solnBoard = solnBoard;
        this.isBoard = true;
        this.isPlayer = true;
    }

    /**
     * Ten konstruktor tworzy pierwszy wys³any pakiet.
     * @param spaces - liczba miejsc na planszy.
     * @param name - nazwa gracza.
     * @param color - kolor gracza.
     */
    public SudokuPacket(int spaces, String name, Color color) {
        this.name = name;
        this.color[0] = color.getRed();
        this.color[1] = color.getGreen();
        this.color[2] = color.getBlue();
        this.color[3] = color.getOpacity();
        this.spaces = spaces;
        this.isStarter = true;
    }

    /**
     * @param board 9-elementowa tablica, gdzie kazda linia to wiersz.
     * @param name nazwa gracza.
     * @param color kolor gracza.
     */
    public SudokuPacket(String[] board, String name, Color color) {
        this(0, name, color);
        this.isInput = true;
        this.input = board;
    }

    /**
     * Tworzy pakiet dla planszy i jej rozwiazania.
     * @param board - plansza.
     * @param solnBoard - rozwiazanie.
     */
    public SudokuPacket(int[][] board, int[][] solnBoard) {
        this.board = board;
        this.solnBoard = solnBoard;
        this.isBoard = true;
        this.data = null;
    }

    /**
     * Tworzy pakiet dla wiadomosci.
     * @param message - wiadomosc.
     * @param color - kolor.
     * @param id - id gracza.
     * @param isMessage - czy jest to wiadomosc
     */
    public SudokuPacket(String message, Color color, int id, boolean isMessage) {
        this.message = message;
        this.isMessage = true;
        this.id = id;
        this.data = null;
        this.color[0] = color.getRed();
        this.color[1] = color.getGreen();
        this.color[2] = color.getBlue();
        this.color[3] = color.getOpacity();
    }

    /**
     * Tworzy pakiet sk³adaj¹cy siê z nazw gier.
     * @param games - aktualne nazwy gier
     */
    public SudokuPacket(ArrayList<String> games) {
        this.games = new String[games.size()];
        for (int g = 0; g < games.size(); g++) {
            this.games[g] = games.get(g);
        }
    }

    /**
     * Tworzy pakiet, aby za¿¹daæ informacji o grze.
     */
    public SudokuPacket() {
        this.isQuery = true;
    }

    /**
     * utworzenie pakietu dla kwadratów(pol na planszy).
     * @param squares - zmodyfikowane pola.
     */
    public SudokuPacket(Square... squares) {
        this.data = new Data[squares.length];
        for (int s = 0; s < squares.length; s++) {
            Square sq = squares[s];
            double[] ansColor = new double[4];
            ansColor[0] = ((Color) sq.getAnswer().getFill()).getRed();
            ansColor[1] = ((Color) sq.getAnswer().getFill()).getGreen();
            ansColor[2] = ((Color) sq.getAnswer().getFill()).getBlue();
            ansColor[3] = ((Color) sq.getAnswer().getFill()).getOpacity();
            double[] overColor = new double[4];
            overColor[0] = ((Color) sq.getOverlay().getStroke()).getRed();
            overColor[1] = ((Color) sq.getOverlay().getStroke()).getGreen();
            overColor[2] = ((Color) sq.getOverlay().getStroke()).getBlue();
            overColor[3] = ((Color) sq.getOverlay().getStroke()).getOpacity();
            int ans = sq.getAnswer().getValue();
            int count = 0;
            boolean[] visibility = sq.getNotes().getVisibility();
            for (int i = 0; i < 9; i++) {
                if (visibility[i]) {
                    count++;
                }
            }
            int[] notes = new int[count];
            int k = 0;
            for (int i = 0; i < 9; i++) {
                if (visibility[i]) {
                    notes[k] = i;
                    k++;
                }
            }
            int[] posn = new int[2];
            posn[0] = sq.getRow();
            posn[1] = sq.getCol();
            boolean selected = sq.isSelected();
            this.data[s] = new Data(ansColor, overColor, ans, notes, posn, selected);
        }
    }

    /**
     * Tworzy pakiet do usuniêcia gracza.
     * @param id - id gracza.
     */
    public SudokuPacket(int id) {
        this.id = id;
        this.isRemove = true;
    }

    /**
     * Tworzy pakiet z podanych danych.
     * @param data - dane.
     */
    @Deprecated
    public SudokuPacket(Data...data) {
        this.data = data;
    }


    /**
     * Pobiera dane Square, jeœli s¹.
     * @return Square data.
     */
    public Data[] getData() {
        return this.data;
    }

    /**
     * Informuje, czy pakiet zawiera tablicê/plansze
     * @return jesli zawiera plansze
     */
    public boolean isBoard() {
        return isBoard;
    }

    /**
     * Informuje, czy pakiet zawiera informacje o graczu.
     * @return jesli zawiera info o graczu.
     */
    public boolean isPlayer() {
        return isPlayer;
    }

    /**
     * Pobiera plansze.
     * @return Dwuwymiarowa tablica, 9x9.
     */
    public int[][] getBoard() {
        return board;
    }

    /**
     * Pobiera rozwiazanie
     * @return Dwuwymiarowa tablica, 9x9.
     */
    public int[][] getSolnBoard() {
        return solnBoard;
    }

    /**
     * Pobiera kolor jako tablice double.
     * @return 1x4 tablica double.
     */
    public double[] getColor() {
        return color;
    }

    /**
     * Pobiera nazwe
     * @return name.
     */
    public String getName() {
        return name;
    }

    /**
     * Informuje, czy ten pakiet zawiera informacje o wiadomoœciach.
     * @return jesli pakiet zawiera info o wiadomoœciach.
     */
    public boolean isMessage() {
        return this.isMessage;
    }

    /**
     * Pobiera aktualn¹ wiadomoœæ.
     * @return zawartoœæ wiadomoœci.
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Informuje, czy ten pakiet ma informacje pocz¹tkowe.
     * @return jesli pakiet jest pakietem startowym.
     */
    public boolean isStarter() {
        return this.isStarter;
    }

    /**
     * Pobiera liczbê ¿¹danych miejsc.
     * @return liczba pustych pól na planszy.
     */
    public int getSpaces() {
        return this.spaces;
    }

    /**
     * Zwraca ID gracza.
     * @return ID gracza.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Mówi, czy ten pakiet usuwa gracza.
     * @return jeœli pakiet usuwa gracza.
     */
    public boolean isRemove() {
        return this.isRemove;
    }

    /**
     * Pobiera w³aœciwoœæ isLast();.
     * @return jeœli jest to ostatni pakiet.
     */
    public boolean isLast() {
        return this.isLast;
    }


    /**
     * Ustawia w³aœciwoœæ isLast.
     * @param last jeœli pakiet jest ostatnim.
     */
    public void setLast(boolean last) {
        this.isLast = last;
    }

    /**
     * jeœli ten pakiet reprezentuje wejœcie tablicy.
     */
    public boolean isInput() {
        return this.isInput;
    }

    /**
     * @return board input
     */
    public String[] getInput() {
        return this.input;
    }

    /**
     * @return jeœli ten pakiet ¿¹da informacji o grze..
     */
    public boolean isQuery() {return this.isQuery;}

    /**
     * Pobiera informacje o grze z tego pakietu.
     * @return gry.
     */
    public String[] getGames() {
        return this.games;
    }

    /**
     * Okreœla, czy ten pakiet zawiera informacje o grze.
     */
    public boolean isGame() {
        return this.isGame;
    }

    /**
     * Zwraca grê. Jeœli isGame jest false, to zachowanie jest nieokreœlone!
     * @return nazwa gry
     */
    public String getGame() {
        return this.game;
    }

    public boolean isNew() {
        return this.isNew;
    }

    /**
     * Klasa dla wygody, która przechowuje jeden pe³ny kwadrat(pole w grze).
     */
    public class Data implements Serializable {
        private double[] ansColor;
        private double[] overColor;
        private int ans = 0;
        private int[] notes;
        private int[] posn;
        private boolean selected;

        /**
         * @param ansColor kolor odpowiedzi.
         * @param overColor kolor otoczki(odpowiada kolorowi gracza).
         * @param ans odpowiedz.
         * @param notes notatka.
         * @param posn pozycja na planszy ([Row, Col]).
         * @param selected czy pole jest aktualnie zaznaczone.
         */
        public Data(double[] ansColor, double[] overColor, int ans, int[] notes, int[] posn, boolean selected) {
            this.ansColor = ansColor;
            this.overColor = overColor;
            this.ans = ans;
            this.notes = notes;
            this.posn = posn;
            this.selected = selected;
        }


        /**
         * Pobiera kolor samej odpowiedzi.
         */
        public double[] getAnsColor() {
            return ansColor;
        }

        /**
         * Pobiera wartosc odpowiedzi
         * @return wartosc udzielonej odpowiedzi.
         */
        public int getAns() {
            return ans;
        }

        /**
         * Pobiera notatki..
         */
        public int[] getNotes() {
            return notes;
        }

        /**
         * Pobiera aktualna pozycje na planszy.
         * @return Zwraca int[Row, Col].
         */
        public int[] getPosn() {
            return posn;
        }


        /**
         * Czy pole jest zaznaczone.
         */
        @Deprecated
        public boolean isSelected() {
            return selected;
        }

        /**
         * Zwraca kolor otoczki.
         */
        public double[] getOverColor() {
            return overColor;
        }
    }
}
