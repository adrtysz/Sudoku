package main.java.networking;

import javafx.scene.paint.Color;
import main.java.ui.Square;

import java.io.Serializable;
import java.util.ArrayList;

/**
* Ta klasa jest odpowiedzialna za handel danymi miêdzy serwerem a klientami. Jest to serializowalne i reprezentuje
 * podstawowe dane wysy³ane przez socket. Mog¹ byæ TYLKO wysy³ane SudokuPackets.
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
     * Pakiet dla nowego gracza.
     * @param name nazwa.
     * @param color kolor.
     * @param isPlayer Identyfikator, jeœli jest graczem.
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
     * Pakiet dla usunietego gracza.
     * @param name nazwa.
     * @param color kolor.
     * @param id id gracza.
     */
    public SudokuPacket(String name, Color color, int id) {
        this(name, color, true);
        this.id = id;
    }

    /**
     * Ten konstruktor tworzy planszê 
     * @param board plansza.
     * @param solnBoard rozwi¹zanie.
     * @param name knazwa gracza.
     * @param color kolor.
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
     * @param spaces liczba miejsc na plansz.
     * @param name nazwa gracza.
     * @param color kolor gracza.
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

    public SudokuPacket(String[] board, String name, Color color) {
        this(0, name, color);
        this.isInput = true;
        this.input = board;
    }

    /**
     * Tworzy pakiet dla planszy i rozwi¹zania
     * @param board plansza
     * @param solnBoard rozwi¹zanie
     */
    public SudokuPacket(int[][] board, int[][] solnBoard) {
        this.board = board;
        this.solnBoard = solnBoard;
        this.isBoard = true;
        this.data = null;
    }

    /**
     * Tworzy pakiet dla wiadomoœci
     * @param message - wiadomoœæ
     * @param color - kolor.
     * @param id - id gracza
     * @param isMessage - czy jest to wiadomoœæ.
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
   * Tworzy pakiet dla kwadratów.
     * @param squares zmodyfikowane kwadraty
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
     * @param id id gracza.
     */
    public SudokuPacket(int id) {
        this.id = id;
        this.isRemove = true;
    }

    @Deprecated
    public SudokuPacket(Data...data) {
        this.data = data;
    }

    public Data[] getData() {
        return this.data;
    }

    public boolean isBoard() {
        return isBoard;
    }


    public boolean isPlayer() {
        return isPlayer;
    }

    public int[][] getBoard() {
        return board;
    }

    public int[][] getSolnBoard() {
        return solnBoard;
    }

    public double[] getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public boolean isMessage() {
        return this.isMessage;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean isStarter() {
        return this.isStarter;
    }

    public int getSpaces() {
        return this.spaces;
    }

    public int getId() {
        return this.id;
    }

    public boolean isRemove() {
        return this.isRemove;
    }

    public boolean isLast() {
        return this.isLast;
    }

    public void setLast(boolean last) {
        this.isLast = last;
    }

    public boolean isInput() {
        return this.isInput;
    }

    public String[] getInput() {
        return this.input;
    }

    public boolean isQuery() {
    	return this.isQuery;
    }

    public String[] getGames() {
        return this.games;
    }

    public boolean isGame() {
        return this.isGame;
    }

    public String getGame() {
        return this.game;
    }

    public boolean isNew() {
        return this.isNew;
    }

    public class Data implements Serializable {
        private double[] ansColor;
        private double[] overColor;
        private int ans = 0;
        private int[] notes;
        private int[] posn;
        private boolean selected;

        public Data(double[] ansColor, double[] overColor, int ans, int[] notes, int[] posn, boolean selected) {
            this.ansColor = ansColor;
            this.overColor = overColor;
            this.ans = ans;
            this.notes = notes;
            this.posn = posn;
            this.selected = selected;
        }

        public double[] getAnsColor() {
            return ansColor;
        }
        
        public int getAns() {
            return ans;
        }

        public int[] getNotes() {
            return notes;
        }

        public int[] getPosn() {
            return posn;
        }

        @Deprecated
        public boolean isSelected() {
            return selected;
        }

        public double[] getOverColor() {
            return overColor;
        }
    }
}
