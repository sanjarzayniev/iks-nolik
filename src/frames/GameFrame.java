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

    JLabel textfield = new JLabel();
    JLabel upperTextField = new JLabel();
    JLabel usernameLabel = new JLabel(LoginFrame.enteredUsername);

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

        // usernameLabel.setOpaque(true);
        usernameLabel.setFont(Settings.upperTextFieldFont);
        usernameLabel.setBackground(Color.BLACK);
        usernameLabel.setForeground(Settings.upper_text_field_foreground_color);
        usernameLabel.setHorizontalAlignment(JLabel.CENTER);
    }

    void initGameBoard() {
        for (int i = 0; i < Settings.TOTAL_BUTTONS; i++) {
            buttons[i] = new JButton();
            button_panel.add(buttons[i]);
            buttons[i].setFocusable(false);
            buttons[i].setFont(Settings.buttonFont);
            buttons[i].addActionListener(new ButtonActionListener());
        }

        // title_panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        title_panel.add(upperTextField);
        title_panel.add(textfield);
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
        // Check X win conditions
        if ((buttons[0].getText() == "X") &&
                (buttons[1].getText() == "X") &&
                (buttons[2].getText() == "X")) {
            xWins(0, 1, 2);
            winnerExists = true;
        }
        if ((buttons[3].getText() == "X") &&
                (buttons[4].getText() == "X") &&
                (buttons[5].getText() == "X")) {
            xWins(3, 4, 5);
            winnerExists = true;
        }
        if ((buttons[6].getText() == "X") &&
                (buttons[7].getText() == "X") &&
                (buttons[8].getText() == "X")) {
            xWins(6, 7, 8);
            winnerExists = true;
        }
        if ((buttons[0].getText() == "X") &&
                (buttons[3].getText() == "X") &&
                (buttons[6].getText() == "X")) {
            xWins(0, 3, 6);
            winnerExists = true;
        }
        if ((buttons[1].getText() == "X") &&
                (buttons[4].getText() == "X") &&
                (buttons[7].getText() == "X")) {
            xWins(1, 4, 7);
            winnerExists = true;
        }
        if ((buttons[2].getText() == "X") &&
                (buttons[5].getText() == "X") &&
                (buttons[8].getText() == "X")) {
            xWins(2, 5, 8);
            winnerExists = true;
        }
        if ((buttons[0].getText() == "X") &&
                (buttons[4].getText() == "X") &&
                (buttons[8].getText() == "X")) {
            xWins(0, 4, 8);
            winnerExists = true;
        }
        if ((buttons[2].getText() == "X") &&
                (buttons[4].getText() == "X") &&
                (buttons[6].getText() == "X")) {
            xWins(2, 4, 6);
            winnerExists = true;
        }

        // check O win conditions
        if ((buttons[0].getText() == "O") &&
                (buttons[1].getText() == "O") &&
                (buttons[2].getText() == "O")) {
            oWins(0, 1, 2);
            winnerExists = true;
        }
        if ((buttons[3].getText() == "O") &&
                (buttons[4].getText() == "O") &&
                (buttons[5].getText() == "O")) {
            oWins(3, 4, 5);
            winnerExists = true;
        }
        if ((buttons[6].getText() == "O") &&
                (buttons[7].getText() == "O") &&
                (buttons[8].getText() == "O")) {
            oWins(6, 7, 8);
            winnerExists = true;
        }
        if ((buttons[0].getText() == "O") &&
                (buttons[3].getText() == "O") &&
                (buttons[6].getText() == "O")) {
            oWins(0, 3, 6);
            winnerExists = true;
        }
        if ((buttons[1].getText() == "O") &&
                (buttons[4].getText() == "O") &&
                (buttons[7].getText() == "O")) {
            oWins(1, 4, 7);
            winnerExists = true;
        }
        if ((buttons[2].getText() == "O") &&
                (buttons[5].getText() == "O") &&
                (buttons[8].getText() == "O")) {
            oWins(2, 5, 8);
            winnerExists = true;
        }
        if ((buttons[0].getText() == "O") &&
                (buttons[4].getText() == "O") &&
                (buttons[8].getText() == "O")) {
            oWins(0, 4, 8);
            winnerExists = true;
        }
        if ((buttons[2].getText() == "O") &&
                (buttons[4].getText() == "O") &&
                (buttons[6].getText() == "O")) {
            oWins(2, 4, 6);
            winnerExists = true;
        }
    }

    public void xWins(int a, int b, int c) {
        buttons[a].setBackground(Settings.button_green_color);
        buttons[b].setBackground(Settings.button_green_color);
        buttons[c].setBackground(Settings.button_green_color);

        for (int i = 0; i < Settings.TOTAL_BUTTONS; i++) {
            buttons[i].setEnabled(false);
        }
        textfield.setText("X wins");
    }

    public void oWins(int a, int b, int c) {
        buttons[a].setBackground(Settings.button_green_color);
        buttons[b].setBackground(Settings.button_green_color);
        buttons[c].setBackground(Settings.button_green_color);

        for (int i = 0; i < Settings.TOTAL_BUTTONS; i++) {
            buttons[i].setEnabled(false);
        }
        textfield.setText("O wins");
    }

    class ButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            for (int i = 0; i < Settings.TOTAL_BUTTONS; i++) {
                if (event.getSource() == buttons[i]) {
                    if (firstPlayerTurn) {
                        if (buttons[i].getText() == "") {
                            buttons[i].setForeground(Settings.button_red_color);
                            buttons[i].setText("X");
                            textfield.setText("O turn");
                            check();
                            firstPlayerTurn = false;
                        }
                    }

                    else {
                        if (buttons[i].getText() == "") {
                            buttons[i].setForeground(Settings.button_blue_color);
                            buttons[i].setText("O");
                            textfield.setText("X turn");
                            check();
                            firstPlayerTurn = true;
                        }
                    }
                }
            }
            if (isBoardFull() && !winnerExists) {
                for (int i = 0; i < Settings.TOTAL_BUTTONS; i++) {
                    buttons[i].setEnabled(false);
                }
                textfield.setText("Draw");
            }
        }

        private boolean isBoardFull() {
            for (int i = 0; i < Settings.TOTAL_BUTTONS; i++) {
                if (buttons[i].getText().equals("")) {
                    return false;
                }
            }
            return true;
        }
    }
}
