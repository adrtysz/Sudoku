package main.java.networking;

import javafx.scene.paint.Color;
import main.java.generator.SudokuBoard;
import main.java.generator.SudokuSolver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

/**
* Ta klasa jest odpowiedzialna za faktyczn¹ rozmowê z klientem. Przekazuje informacje do i od siebie i innych
 * klientów.
 */
class SudokuServerThread extends Thread {
    private final Socket client;
    private final SudokuServer server;
    private SudokuPacket packet;
    private volatile boolean isGoing = true;
    private volatile int id;
    private volatile String name;
    private final int localPort;
    private final String host;

    /**
     * Tworzy w¹tek
     * @param socket zaakceptowane polaczenie.
     * @param server - serwer.
     */
    public SudokuServerThread(Socket socket, SudokuServer server) {
        super("SST - " + socket.getInetAddress().getHostAddress());
        this.client = socket;
        this.server = server;
        this.localPort = socket.getPort();
        this.host = socket.getInetAddress().getHostName();
    }

    /**
     * Rozmawia z graczem i przekazuje informacje do innych w¹tków, w razie potrzeby.
     */
    @Override
    public void run() {
        try (ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream())) {
            out.flush();
            try (ObjectInputStream reader = new ObjectInputStream(client.getInputStream())) {
                SudokuPacket instruct = (SudokuPacket) reader.readObject();
                if (instruct.isStarter()) {
                    if (instruct.isQuery()) {
                        ArrayList<String> games = new ArrayList<>();
                        for (Game game : server.getGames()) {
                            games.add(game.toString());
                        }
                        SudokuPacket packet = new SudokuPacket(games);
                        out.writeObject(packet);
                    } else if (server.isFirstPlayer()) {
                        if (instruct.isInput()) {
                            try {
                                SudokuSolver solver = new SudokuSolver(instruct.getInput());
                                server.setBoards(solver.getBoard(), solver.getSoln());
                                server.setSpaces(instruct.getSpaces());
                            } catch (IllegalArgumentException e) {
                                SudokuBoard generator = new SudokuBoard(instruct.getSpaces());
                                server.setBoards(generator.getBoard(), generator.getSoln());
                                server.setSpaces(instruct.getSpaces());
                            }
                        } else {
                            SudokuBoard generator = new SudokuBoard(server.getSpaces());
                            server.setBoards(generator.getBoard(), generator.getSoln());
                        }
                    }
                    this.name = instruct.getName();
                    id = server.addPlayer(instruct.getName(), Color.color(instruct.getColor()[0], instruct.getColor()[1], instruct.getColor()[2], instruct.getColor()[3]));
                    System.out.println("Connected to player " + id + ": " + instruct.getName() + " (Port: " + localPort + "; Host: " + host+ ")");
                    SudokuPacket response = new SudokuPacket(server.getBoard(), server.getSoln());
                    out.writeObject(response);
                    // send all the player information:
                    SudokuPacket thisPlayer = new SudokuPacket(instruct.getName(), server.getPlayerColor().get(id - 1), id);
                    if (server.getPackets().size() > 0) {
                        out.writeObject(thisPlayer);
                        for (int i = 0; i < server.getPackets().size() - 1; i++) {
                            out.writeObject(server.getPackets().get(i));
                        }
                        if (server.getPackets().size() > 0) {
                            SudokuPacket last = server.getPackets().get(server.getPackets().size() - 1);
                            last.setLast(true);
                            out.writeObject(last);
                            last.setLast(false);
                        }
                    } else {
                        thisPlayer.setLast(true);
                        out.writeObject(thisPlayer);
                    }
                    server.addPacket(thisPlayer);
                    for (SudokuServerThread thread : server.getThreads()) {
                        if (thread != null && thread.isAlive()) {
                            SudokuPacket player = new SudokuPacket(instruct.getName(), server.getPlayerColor().get(id - 1), id);
                            thread.setPacket(player);
                            thread.interrupt();
                        }
                    }
                    server.getThreads().add(this);
                    // Wait to be interrupted!
                    while (isGoing) {
                        try {
                            synchronized (this) {
                                this.wait();
                            }
                        } catch (InterruptedException e) {
                            if (this.isGoing) {
                                out.writeObject(this.packet);
                            } else {
                                throw new SocketException();
                            }
                        }
                    }
                } else {
                    out.writeObject("");
                    if (instruct.isRemove()) {
                        this.id = instruct.getId();
                        server.addPacket(instruct);
                        throw new SocketException("" + instruct.getId());
                    } else if (instruct.isNew()) {
                        SudokuBoard generator = new SudokuBoard(instruct.getSpaces());
                        server.setBoards(generator.getBoard(), generator.getSoln());
                        instruct = new SudokuPacket(server.getBoard(), server.getSoln());
                    }
                    server.addPacket(instruct);
                    for (SudokuServerThread thread : server.getThreads()) {
                        if (thread != null && thread.isAlive()) {
                            thread.setPacket(instruct);
                            thread.interrupt();
                        }
                    }
                }
            }
        } catch (SocketException e) {
            if (server.getThreads().contains(this)) {
                server.getThreads().set(server.getThreads().indexOf(this), null);
                System.out.println("Disconnected from player " + id + ": " + server.getPlayerName().get(id - 1) + " (Port: " + localPort + " Host: " + host + ")");
                server.removePlayer(this.id);
                SudokuPacket instruct = new SudokuPacket(this.id);
                server.addPacket(instruct);
                for (SudokuServerThread thread : server.getThreads()) {
                    if (thread != null && thread.isAlive()) {
                        thread.setPacket(instruct);
                        thread.interrupt();
                    }
                }
            } else {
                server.getThreads().get(id - 1).halt();
                server.getThreads().get(id - 1).interrupt();
            }
        } catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private synchronized void setPacket(SudokuPacket packet) {
        this.packet = packet;
    }

    private synchronized void halt() {
        this.isGoing = false;
    }
}
