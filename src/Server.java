import java.net.*;
import java.util.*;
import javax.swing.*;

import settings.ServerSettings;

import java.io.IOException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class Server extends JFrame {
    // Player constants
    private final static int PLAYER_X = 0;
    private final static int PLAYER_O = 1;
    private final static String[] MARKS = { "X", "O" };

    // Server socket
    private ServerSocket server;

    private int currentPlayer;
    // Store two opponnest
    private final Player[] players;

    // Store server logs
    private JTextArea outputArea;

    // Players threadpool
    private final ExecutorService runGame;

    // Abstract board ;D
    private final String[] board = new String[ServerSettings.TOTAL_BUTTONS];

    private final Lock gameLock;
    private final Condition otherPlayerTurn;
    private final Condition otherPlayerConnected;

    public static void main(String[] args) {
        new Server().start();
    }

    public Server() {
        super(ServerSettings.FRAME_TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        runGame = Executors.newFixedThreadPool(ServerSettings.PLAYERS_COUNT);
        gameLock = new ReentrantLock(); // create lock for game

        // condition variable for the other player's turn
        otherPlayerTurn = gameLock.newCondition();
        // condition variable for both players being connected
        otherPlayerConnected = gameLock.newCondition();

        players = new Player[ServerSettings.PLAYERS_COUNT];
        currentPlayer = PLAYER_X; // set current player to first player

        initServerSocket();
        initAbstractBoard();
        setSize(ServerSettings.FRAME_SIZE, ServerSettings.FRAME_SIZE);
        setUpOutputArea();
    }

    private void setUpOutputArea() {
        // Create and add styles to text area (for logs)
        outputArea = new JTextArea();
        outputArea.setBackground(Color.BLACK);
        outputArea.setForeground(Color.WHITE);
        outputArea.setFont(new Font("Ink Free", Font.PLAIN, 12));
        String serverAddress = String.format(
                "Server: %s:%s\n",
                ServerSettings.SERVER_HOST,
                ServerSettings.SERVER_PORT);

        outputArea.setText(serverAddress);

        setVisible(true);
        add(outputArea, BorderLayout.CENTER);
    }

    private void initServerSocket() {
        try {
            server = new ServerSocket(ServerSettings.SERVER_PORT, ServerSettings.PLAYERS_COUNT);
        } catch (IOException exc) {
            System.out.println(exc.toString());
            System.exit(ServerSettings.FAIL_STATUS_CODE);
        }
    }

    private void initAbstractBoard() {
        // Init tic tac toe board
        for (int index = 0; index < ServerSettings.TOTAL_BUTTONS; index++) {
            board[index] = "";
        }
    }

    public void start() {
        for (int index = 0; index < ServerSettings.PLAYERS_COUNT; index++) {
            // wait for connection
            try {
                players[index] = new Player(server.accept(), index);
                runGame.execute(players[index]); // execute player thread
            } catch (IOException ioException) {
                System.out.println(ioException.toString());
                System.exit(ServerSettings.FAIL_STATUS_CODE);
            }
        }

        gameLock.lock(); // lock game

        try {
            players[PLAYER_X].setSuspended(false); // resume player x
            otherPlayerConnected.signal(); // wake up player x
        } finally {
            gameLock.unlock();
        }
    }

    private void displayMessage(final String messageToDisplay) {
        SwingUtilities.invokeLater(() -> {
            outputArea.append(messageToDisplay);
        });
    }

    public boolean validateAndMove(int location, int player) {
        // wait for turn
        while (player != currentPlayer) {
            gameLock.lock(); // lock game to wait for other player to go

            try {
                otherPlayerTurn.await(); // wait for player's turn
            } catch (InterruptedException exception) {
                System.out.println(exception.toString());
            } finally {
                gameLock.unlock(); // unlock game after waiting
            }
        }

        if (!isOccupied(location)) {
            board[location] = MARKS[currentPlayer]; // set move on board
            currentPlayer = (currentPlayer + 1) % 2; // change player 1->0, 0->1

            // synchronize move
            players[currentPlayer].otherPlayerMoved(location);

            gameLock.lock();

            try {
                otherPlayerTurn.signal(); // signal other player to continue
            } finally {
                gameLock.unlock(); // unlock game after signaling
            }
            return true;
        }
        return false;
    }

    public boolean isOccupied(int location) {
        return board[location].equals(MARKS[PLAYER_X]) || board[location].equals(MARKS[PLAYER_O]);
    }

    public boolean hasWinner() {
        return (!board[0].isEmpty() && board[0].equals(board[1]) && board[0].equals(board[2]))
                || (!board[3].isEmpty() && board[3].equals(board[4]) && board[3].equals(board[5]))
                || (!board[6].isEmpty() && board[6].equals(board[7]) && board[6].equals(board[8]))
                || (!board[0].isEmpty() && board[0].equals(board[3]) && board[0].equals(board[6]))
                || (!board[1].isEmpty() && board[1].equals(board[4]) && board[1].equals(board[7]))
                || (!board[2].isEmpty() && board[2].equals(board[5]) && board[2].equals(board[8]))
                || (!board[0].isEmpty() && board[0].equals(board[4]) && board[0].equals(board[8]))
                || (!board[2].isEmpty() && board[2].equals(board[4]) && board[2].equals(board[6]));
    }

    public boolean isBoardFull() {
        for (String boardElement : board) {
            if (boardElement.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public boolean isGameOver() {
        return hasWinner() || isBoardFull();
    }

    private class Player implements Runnable {
        private final Socket connection;
        private Scanner input; // input from client
        private Formatter output; // output to client
        private final int playerNumber;
        private final String mark; // mark for this player
        private boolean suspended = true; // whether thread is suspended

        public Player(Socket socket, int number) {
            playerNumber = number; // store this player number
            mark = MARKS[playerNumber];
            connection = socket; // store socket for client

            try {
                input = new Scanner(connection.getInputStream());
                output = new Formatter(connection.getOutputStream());
            } catch (IOException ioException) {
                System.out.println(ioException.toString());
                System.exit(ServerSettings.FAIL_STATUS_CODE);
            }
        }

        public void otherPlayerMoved(int location) {
            output.format("opponent_moved:%s\n", location);
            output.flush();
            output.format(hasWinner() ? "defeat\n" : isBoardFull() ? "draw\n" : "");
            output.flush();
        }

        @Override
        public void run() {
            try {
                displayMessage("" + mark + " connected\n");
                output.format("your_mark:%s\n", mark); // send player's mark
                output.flush(); // flush output

                // if player X, wait for another player to arrive
                if (playerNumber == PLAYER_X) {
                    output.format("wait\n");
                    output.flush(); // flush output
                    gameLock.lock(); // lock game to wait for second player

                    try {
                        while (suspended) {
                            otherPlayerConnected.await();
                        }
                    } catch (InterruptedException exception) {
                        System.out.println(exception.toString());
                    } finally {
                        gameLock.unlock(); // unlock game after second player
                    }

                    output.format("your_move\n");
                    output.flush();
                } else {
                    output.format("wait\n");
                    output.flush();
                }

                while (!isGameOver()) {
                    int location = 0;

                    if (input.hasNext()) {
                        location = input.nextInt();
                    }
                    if (location >= board.length) {
                        output.format("invalid_move\n");
                        output.flush();
                    } else {
                        if (validateAndMove(location, playerNumber)) {
                            displayMessage("\nlocation: " + location + "->" + players[playerNumber].mark);
                            output.format("valid_move:%s\n", location); // notify client
                            output.flush(); // flush output
                            output.format(hasWinner() ? "victory\n" : isBoardFull() ? "draw\n" : "");
                            output.flush();
                        } else {
                            output.format("invalid_move\n");
                            output.flush(); // flush output
                        }
                    }

                }
            } finally {
                try {
                    connection.close();
                } catch (IOException ioException) {
                    System.out.println(ioException.toString());
                    System.exit(ServerSettings.FAIL_STATUS_CODE);
                }
            }
        }

        public void setSuspended(boolean status) {
            suspended = status;
        }
    }

}