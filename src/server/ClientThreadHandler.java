package server;

import java.util.Scanner;

import client.GameFrame;
import helpers.Database;
import helpers.Logger;

public class ClientThreadHandler implements Runnable {
    private Database db;
    private Scanner input;
    private GameFrame frame;
    private String username;

    public static final int FAIL_STATUS_CODE = -1;

    String myMark = "", opponentMark = "";

    public ClientThreadHandler(Scanner input, GameFrame frame, String username) {
        this.input = input;
        this.frame = frame;
        this.username = username;
        this.db = new Database("database.key");

    }

    public void run() {
        while (input.hasNextLine()) {
            String message = input.nextLine();
            Logger.info("Received: " + message);
            invokeHandler(message);

        }
    }

    private void invokeHandler(String message) {
        if (message.startsWith("wait")) {
            handleWait(message);
        }

        if (message.startsWith("your_move")) {
            handleCurrentPlayerMove(message);
        }
        if (message.startsWith("your_mark")) {
            handleCurrentPlayerMark(message);
        }

        if (message.startsWith("opponent_moved")) {
            handleOpponentMove(message);
        }

        if (message.startsWith("valid_move")) {
            handleValidMove(message);
        }

        if (message.startsWith("draw")) {
            handleDraw(message);
        }

        if (message.startsWith("victory")) {
            handleVictory(message);
        }

        if (message.startsWith("defeat")) {
            handleDefeat(message);
        }
    }

    public void handleWait(String message) {
        this.frame.upperTextField.setText("Wait...");
        this.frame.isWaiting = true;
    };

    public void handleCurrentPlayerMove(String message) {
        this.frame.upperTextField.setText("Your Move");
        this.frame.isWaiting = false;

    };

    public void handleCurrentPlayerMark(String message) {
        myMark = message.split(":")[1];
        this.frame.textfield.setText(myMark);
    };

    public void handleOpponentMove(String message) {
        int buttonIndex = Integer.parseInt(message.split(":")[1]);
        opponentMark = myMark.equals("X") ? "O" : "X";
        this.frame.upperTextField.setText("Your move");
        this.frame.updateButton(
                this.frame.buttons[buttonIndex],
                opponentMark, myMark);
        this.frame.isWaiting = false;
    };

    public void handleValidMove(String message) {
        int buttonIndex = Integer.parseInt(message.split(":")[1]);
        this.frame.updateButton(this.frame.buttons[buttonIndex], myMark, myMark);
        this.frame.upperTextField.setText("Wait...");
        this.frame.isWaiting = true;
    };

    public void handleDraw(String message) {
        this.frame.upperTextField.setText("Draw!");
        this.frame.textfield.setText("");
        this.frame.makeButtonsDisabled();
        this.db.createResult(this.username, "Played as " + myMark + " and " + "Draw");
    };

    public void handleVictory(String message) {
        this.frame.upperTextField.setText("You Won!");
        this.frame.textfield.setText("");
        this.frame.makeButtonsDisabled();
        this.db.createResult(this.username, "Played as " + myMark + " and " + "Won");
    };

    public void handleDefeat(String message) {
        this.frame.upperTextField.setText("You lost :(");
        this.frame.textfield.setText("");
        this.frame.makeButtonsDisabled();
        this.db.createResult(this.username, "Played as " + myMark + " and " + "Lost");
    };

}
