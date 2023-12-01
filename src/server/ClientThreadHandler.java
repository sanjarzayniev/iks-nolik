package server;

import java.util.Scanner;

import client.GameFrame;

public class ClientThreadHandler implements Runnable {
    private Scanner input;
    private GameFrame frame;
    public static final int FAIL_STATUS_CODE = -1;

    String myMark = "", opponentMark = "";

    public ClientThreadHandler(Scanner input, GameFrame frame) {
        this.input = input;
        this.frame = frame;

    }

    public void run() {
        while (input.hasNextLine()) {
            String message = input.nextLine();
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
        this.frame.makeButtonsDisabled();
    };

    public void handleCurrentPlayerMove(String message) {
        this.frame.upperTextField.setText("Your Move");
        this.frame.releaseButtons();

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
                opponentMark);
        this.frame.releaseButtons();
    };

    public void handleValidMove(String message) {
        int buttonIndex = Integer.parseInt(message.split(":")[1]);
        this.frame.updateButton(this.frame.buttons[buttonIndex], myMark);
        this.frame.upperTextField.setText("Wait...");
        this.frame.makeButtonsDisabled();
    };

    public void handleDraw(String message) {
        this.frame.upperTextField.setText("Draw!");
        this.frame.textfield.setText("");
        this.frame.makeButtonsDisabled();
    };

    public void handleVictory(String message) {
        this.frame.upperTextField.setText("You Won!");
        this.frame.textfield.setText("");
        this.frame.makeButtonsDisabled();
    };

    public void handleDefeat(String message) {
        this.frame.upperTextField.setText("You lost :(");
        this.frame.textfield.setText("");
        this.frame.makeButtonsDisabled();
    };

}
