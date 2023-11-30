package frames;

import java.awt.*;
import java.util.*;
import javax.swing.*;

import java.awt.event.*;

public class GameFrame extends JFrame {
    boolean firstPlayerTurn;
    boolean winnerExists = false;

    Random random = new Random();
    JPanel title_panel = new JPanel(), button_panel = new JPanel();
    JLabel textfield = new JLabel(), upperTextField = new JLabel(), usernameLabel = new JLabel();

    JButton[] buttons = new JButton[Settings.TOTAL_BUTTONS];

    public GameFrame() {
        initFrame();
        addTextField();
        initPanels();
        initGameBoard();

        startGame();
    }

    void initFrame() {
        setVisible(true);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Settings.FRAME_SIZE, Settings.FRAME_SIZE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Settings.panel_background);
    }

    void initPanels() {
        title_panel.setLayout(new GridLayout(1, 3));
        title_panel.setBounds(0, 0, 800, 100);
        button_panel.setLayout(new GridLayout(3, 3));
        button_panel.setBackground(Settings.button_background_color);
    }

    void addTextField() {
        textfield.setOpaque(true);
        textfield.setFont(Settings.textFieldFont);
        textfield.setHorizontalAlignment(JLabel.CENTER);
        textfield.setBackground(Settings.text_field_background_color);
        textfield.setForeground(Settings.text_field_foreground_color);

        upperTextField.setOpaque(true);
        upperTextField.setText("TicTacToe");
        upperTextField.setFont(Settings.upperTextFieldFont);
        upperTextField.setHorizontalAlignment(JLabel.CENTER);
        upperTextField.setBackground(Settings.upper_text_field_background_color);
        upperTextField.setForeground(Settings.upper_text_field_foreground_color);

        usernameLabel.setOpaque(true);
        usernameLabel.setFont(Settings.usernameTextFieldFont);
        usernameLabel.setBackground(Settings.upper_text_field_background_color);
        usernameLabel.setForeground(Settings.upper_text_field_foreground_color);
        usernameLabel.setHorizontalAlignment(JLabel.CENTER);
    }

    void initGameBoard() {
        for (int index = 0; index < Settings.TOTAL_BUTTONS; index++) {
            buttons[index] = new JButton();
            button_panel.add(buttons[index]);
            buttons[index].setFocusable(false);
            buttons[index].setFont(Settings.buttonFont);
            buttons[index].addActionListener(new ButtonActionListener());
            buttons[index].setBackground(Color.WHITE);
        }

        title_panel.add(textfield);
        title_panel.add(upperTextField);
        title_panel.add(usernameLabel);

        add(title_panel, BorderLayout.NORTH);
        add(button_panel);

        startGame();
    }

    public void startGame() {
        if (random.nextInt(2) == 0) {
            firstPlayerTurn = true;
            textfield.setText("X turn");
        } else {
            firstPlayerTurn = false;
            textfield.setText("O turn");
        }
    }

    public void check() {
        Integer[][] winPositions = {
                { 0, 1, 2 },
                { 3, 4, 5 },
                { 6, 7, 8 },
                { 0, 3, 6 },
                { 1, 4, 7 },
                { 2, 5, 8 },
                { 0, 4, 8 },
                { 2, 4, 6 }
        };

        for (Integer[] condition : winPositions) {
            if (checkText("X", condition[0], condition[1], condition[2])) {
                xWins(condition[0], condition[1], condition[2]);
                winnerExists = true;
            }
            if (checkText("O", condition[0], condition[1], condition[2])) {
                oWins(condition[0], condition[1], condition[2]);
                winnerExists = true;
            }
        }

    }

    private boolean checkText(String text, int firstIndex, int secondIndex, int thirdIndex) {
        return  (buttons[firstIndex].getText() == text)  &&
                (buttons[secondIndex].getText() == text) &&
                (buttons[thirdIndex].getText() == text);
    }

    public void xWins(int a, int b, int c) {
        buttons[a].setBackground(Settings.button_green_color);
        buttons[b].setBackground(Settings.button_green_color);
        buttons[c].setBackground(Settings.button_green_color);

        this.makeButtonsDisabled();
        textfield.setText("X wins");
    }

    public void oWins(int a, int b, int c) {
        buttons[a].setBackground(Settings.button_green_color);
        buttons[b].setBackground(Settings.button_green_color);
        buttons[c].setBackground(Settings.button_green_color);

        this.makeButtonsDisabled();
        textfield.setText("O wins");
    }

    private void makeButtonsDisabled() {
        for (JButton button : buttons) {
            button.setEnabled(false);
        }
    }

    public void setUsername(String username) {
        this.usernameLabel.setText(username);
    }

    class ButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            for (JButton button : buttons) {
                if (event.getSource() == button) {
                    if (firstPlayerTurn) {
                        if (button.getText().equals("")) {
                            updateButton(
                                    button,
                                    Settings.button_red_color,
                                    "X",
                                    "O turn");
                            firstPlayerTurn = false;
                        }
                    }

                    else {
                        if (button.getText().equals("")) {
                            updateButton(
                                    button,
                                    Settings.button_blue_color,
                                    "O",
                                    "X turn");
                            firstPlayerTurn = true;
                        }
                    }
                }
            }
            // Consider draw case
            if (isBoardFull() && !winnerExists) {
                textfield.setText("Draw");
                makeButtonsDisabled();
            }
        }

        private void updateButton(JButton button, Color color, String buttonText, String textFieldText) {
            button.setForeground(color);
            button.setText(buttonText);
            textfield.setText(textFieldText);
            check();

        }

        private boolean isBoardFull() {
            for (JButton button : buttons) {
                if (button.getText().equals("")) {
                    return false;
                }
            }
            return true;
        }
    }

}
