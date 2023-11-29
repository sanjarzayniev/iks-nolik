package server;

import helpers.Logger;

public class ClientHandler implements Runnable {
    public String name;
    private Player currentPlayer;

    public ClientHandler(Player player) {
        this.currentPlayer = player;
    }

    @SuppressWarnings({ "resource" })
    public void run() {
        try {
            Logger.info("Connected: " + currentPlayer);

            while (true) {
                if (currentPlayer.scanner.hasNextLine()) {
                    String message = currentPlayer.scanner.nextLine();
                    Logger.info("Received: " + message);
                }
            }
        } catch (Exception exc) {
            Logger.error("Error occurred: " + exc.getMessage());
        }
    }
}