package main.java.networking;

import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.ArrayList;

/**
* Ta klasa jest odpowiedzialna za tworzenie i utrzymywanie gry. G³ównie polega na przekazywaniu socketow do w¹tkow.
 */
public class SudokuServer implements Runnable {
    private final int port;
    private volatile boolean isGoing = true;
    private volatile boolean firstPlayer = true;
    private ServerSocket server;
    private volatile ArrayList<SudokuServerThread> connections = new ArrayList<>();
    private volatile ArrayList<String> playerName = new ArrayList<>();
    private volatile ArrayList<Color> playerColor = new ArrayList<>();
    private volatile ArrayList<Integer> playerId = new ArrayList<>();
    private volatile ArrayList<Game> games = new ArrayList<>();
    private volatile ArrayList<SudokuPacket> packets = new ArrayList<>();
    private volatile int[][] board;
    private volatile int[][] soln;
    private volatile int spaces = 30;
    private static final int PORT = 60000;

    /**
     * Tworzy serwer Sudoku z domyœlnym portem zdefiniowanym w PORT.
     */
    public SudokuServer() {
        this(PORT);
    }
    /**
     * Tworzy Serwer Sudoku z danym hostem i portem.
     * @param port - port serwera.
     */
    public SudokuServer(int port) {
        this.port = port;
    }

    /**
     *S³ucha po³¹czeñ, przekazuj¹c te po³¹czenia do nowego w¹tku.
     */
    @Override
    public void run() {
        try (ServerSocket server = new ServerSocket(port)) {
            this.server = server;
            while (isGoing) {
                try {
                    SudokuServerThread thread = new SudokuServerThread(server.accept(), this);
                    thread.start();
                } catch (SocketException e) {
                    return;
                }
            }
        } catch (SocketException e) {
            System.out.println("Server Port " + port + " was not available.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
    * Tworzy serwer. Opcjonalne wejœcie portu; jeœli nie podano argumentu, u¿ywana jest wartoœæ 60000.
     */
    public static synchronized void main(String[] args) {
        System.out.println("Starting Server...");
        int port;
        if (args.length > 0) {

            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                port = SudokuServer.PORT;
            }
        } else {
            port = SudokuServer.PORT;
        }
        SudokuServer sudokuServer = new SudokuServer(port);
        Thread server = new Thread(sudokuServer);
        System.out.println("Server Started. Listening for connections on port " + port + "...");
        server.start();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
            in.readLine();
            sudokuServer.getServer().close();
            System.out.println("Server has stopped.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Pobiera serverSocket.
     */
    private synchronized ServerSocket getServer() {
        return server;
    }

    /**
     * Pobiera wszystkie w¹tki graczy.
     * @return wszystkie po³¹czenia.
     */
    public synchronized ArrayList<SudokuServerThread> getThreads() {
        return this.connections;
    }

    /**
     * Sprawdza, czy gracz jest pierwszym.
     */
    public synchronized boolean isFirstPlayer() {
        return this.firstPlayer;
    }

    /**
     * Pobiera nazwê graczy.
     * @return nazwy graczy(w kolejnosc).
     */
    public synchronized ArrayList<String> getPlayerName() {
        return this.playerName;
    }

    /**
     * Pobiera kolory graczy.
     * @return Kolory w kolejnoœci.
     */
    public synchronized ArrayList<Color> getPlayerColor() {
        return this.playerColor;
    }

    /**
     * Pobiera plansze.
     * @return plansza.
     */
    public synchronized int[][] getBoard() {
        return this.board;
    }

    /**
     * Pobiera rozwi¹zanie.
     * @return rozwi¹zanie.
     */
    public synchronized int[][] getSoln() {
        return this.soln;
    }

    public synchronized void setBoards(int[][] board, int[][] soln) {
        this.board = board;
        this.soln = soln;
        this.firstPlayer = false;
    }

    public synchronized int getSpaces() {
        return this.spaces;
    }

    public synchronized void setSpaces(int spaces) {
        this.spaces = spaces;
    }

    /**
     * Dodaje gracza.
     * @param name - nazwa.
     * @param color - kolor.
     * @return Id gracza.
     */
    public int addPlayer(String name, Color color) {
        this.playerName.add(name);
        this.playerColor.add(color);
        this.playerId.add(this.playerColor.size());
        return this.playerColor.size();
    }

    /**
     * Usuwa gracza.
     * @param id gracz do usuniecia.
     */
    public void removePlayer(int id) {
        this.playerName.set(id - 1, null);
        this.playerColor.set(id - 1, null);
        this.playerId.set(id - 1, null);
        for (String name : this.playerName) {
            if (name != null) {
                return;
            }
        }
        this.board = new int[9][9];
        this.soln = this.board;
        this.firstPlayer = true;
        this.packets.clear();
        this.playerColor.clear();
        this.playerId.clear();
        this.playerName.clear();
        this.connections.clear();
        System.out.println("No Active Players - Game reset.");
    }

    /**
     * Dodaje pakiet na stos.
     * @param packet pakiet do dodania.
     */
    public synchronized void addPacket(SudokuPacket packet) {
        this.packets.add(packet);
    }

    public synchronized ArrayList<SudokuPacket> getPackets() {
        return this.packets;
    }

    public synchronized void removeGame(Game game) {
        games.remove(game);
    }

    public synchronized ArrayList<Game> getGames() {
        return this.games;
    }
}
