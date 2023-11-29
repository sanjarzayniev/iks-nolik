import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

import server.Player;
import helpers.Logger;
import helpers.Database;
import server.ClientHandler;
import server.ServerSettings;

public class Server {
    static Database db;
    static ServerSocket serverSocket;
    private static Player firstPlayer, secondPlayer;
    private static ExecutorService pool = Executors.newFixedThreadPool(ServerSettings.PLAYERS_COUNT);

    public static void main(String[] args) {
        Logger.info("Running game server on tcp://localhost:" + ServerSettings.SERVER_PORT);
        startServer();
    }

    public static void startServer() {
        initServer();
        initDatabase();

        // We can pass by reference
        boolean[] isRunning = { true };

        try {
            while (isRunning[0]) {
                Socket clientSocket = serverSocket.accept();
                Logger.info("Received connection: " + getClientAddress(clientSocket));

                // Create new player object
                InputStream stream = clientSocket.getInputStream();
                Scanner scanner = new Scanner(stream);
                PrintWriter outputWriter = new PrintWriter(clientSocket.getOutputStream(), true);
                Player player = new Player(scanner.nextLine(), outputWriter, scanner);

                // Check room capacity and Allocate & start new thread
                checkRoomCapacity(clientSocket, player, isRunning);
                Logger.info(firstPlayer + " vs " + secondPlayer);
            }
        } catch (Exception exc) {
            Logger.error(exc.toString());
        } finally {
            pool.shutdown();
            try {
                serverSocket.close();
            } catch (IOException exc) {
                // skip
            }
        }
    }

    private static void checkRoomCapacity(Socket clientSocket, Player player, boolean[] isRunning) {
        
        // Assign to player object
        if (firstPlayer == null) {
            firstPlayer = player;
        } else if (secondPlayer == null) {
            secondPlayer = player;
        }
        // Run them independly
        pool.execute(new ClientHandler(player));

        if (firstPlayer != null && secondPlayer != null) {
            isRunning[0] = false; // stops main server loop
            Logger.info("Room is full. Closing server socket.");
            return;
        }
    }

    private static void initServer() {
        try {
            serverSocket = new ServerSocket(ServerSettings.SERVER_PORT);
        } catch (IOException exc) {
            Logger.error("Server cannot create socket: " + exc.toString());
        }
    }

    private static void initDatabase() {
        db = new Database(ServerSettings.DATABASE_KEY_FILE);
    }

    public static void sendAll(String message) {
        firstPlayer.writer.println(message);
        secondPlayer.writer.println(message);
    }

    public static String getClientAddress(Socket anySocket) {
        return anySocket.getRemoteSocketAddress().toString().replace("/", "");
    }
}