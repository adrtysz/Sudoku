package main.java.networking;

import javafx.scene.paint.Color;

import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Ta klasa bêdzie odpowiedzialna za oddzielenie gier. Œledzi graczy, kolory, pakiety itp.
 * Pod wieloma wzglêdami przedstawia to, jak wygl¹da³by serwer, gdyby serwer nie móg³ mieæ wielu gier.
 */
public class Game implements Runnable {

    private ArrayList<SudokuServerThread> threads = new ArrayList<>();
    private int[][] board;
    private int[][] soln;
    private ArrayList<SudokuPacket> packets = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<Color> colors = new ArrayList<>();
    private String gameName;
    private SudokuServer server;

    public Game(Socket client, SudokuServer server) {
        this.server = server;
        SudokuServerThread player = new SudokuServerThread(client, server);
        this.gameName = LocalDateTime.now() + ": " + player.getName();
        threads.add(player);
    }

    public void addPlayer(Socket client) {
        SudokuServerThread player = new SudokuServerThread(client, this.server);
        threads.add(player);
    }

    public void removePlayer(int id) {
        names.set(id - 1, null);
        colors.set(id - 1, null);

        for (String name : names) {
            if (name != null) {
                return;
            }
        }
        server.removeGame(this);
    }

    public ArrayList<SudokuPacket> getPackets() {
        return this.packets;
    }

    public ArrayList<String> getNames() {
        return this.names;
    }

    public ArrayList<Color> getColors() {
        return this.colors;
    }

    public int[][] getBoard() {
        return this.board;
    }

    public int[][] getSoln() {
        return this.soln;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public void setSoln(int[][] soln) {
        this.soln = soln;
    }
    @Override
    public String toString() {
        return this.gameName + "'s Game.";
    }

    @Override
    public void run() {

    }
}
