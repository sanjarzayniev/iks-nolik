import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

class GameFrame extends JFrame {
    boolean firstPlayerTurn;

    Random random = new Random();
    JPanel title_panel = new JPanel(), button_panel = new JPanel();

    JLabel textfield = new JLabel();

    JButton[] buttons = new JButton[Settings.TOTAL_BUTTONS];

    GameFrame() {
        initFrame();
        addTextField();
        initPanels();
        initGameBoard();

        startGame();
    }

    void initFrame() {
        setVisible(true);
        setSize(Settings.FRAME_SIZE, Settings.FRAME_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Settings.black);
        setLayout(new BorderLayout());
    }

    void initPanels() {
        title_panel.setLayout(new BorderLayout());
        title_panel.setBounds(0, 0, 800, 100);

        button_panel.setLayout(new GridLayout(3, 3));
        button_panel.setBackground(Settings.button_background_color);
    }

    void addTextField() {
        textfield.setOpaque(true);
        textfield.setBackground(Settings.text_field_background_color);
        textfield.setForeground(Settings.text_field_foreground_color);
        textfield.setFont(Settings.textFieldFont);
        textfield.setHorizontalAlignment(JLabel.CENTER);
        textfield.setText("Tic-Tac-Toe");
    }

    void initGameBoard() {
        for (int i = 0; i < Settings.TOTAL_BUTTONS; i++) {
            buttons[i] = new JButton();
            button_panel.add(buttons[i]);
            buttons[i].setFocusable(false);
            buttons[i].setFont(Settings.buttonFont);
            buttons[i].addActionListener(new ButtonActionListener());
        }

        title_panel.add(textfield);

        add(title_panel, BorderLayout.NORTH);
        add(button_panel);

        startGame();
    }

    public void startGame() {
        try {
            Thread.sleep(Settings.SLEEP_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        }
        if ((buttons[3].getText() == "X") &&
                (buttons[4].getText() == "X") &&
                (buttons[5].getText() == "X")) {
            xWins(3, 4, 5);
        }
        if ((buttons[6].getText() == "X") &&
                (buttons[7].getText() == "X") &&
                (buttons[8].getText() == "X")) {
            xWins(6, 7, 8);
        }
        if ((buttons[0].getText() == "X") &&
                (buttons[3].getText() == "X") &&
                (buttons[6].getText() == "X")) {
            xWins(0, 3, 6);
        }
        if ((buttons[1].getText() == "X") &&
                (buttons[4].getText() == "X") &&
                (buttons[7].getText() == "X")) {
            xWins(1, 4, 7);
        }
        if ((buttons[2].getText() == "X") &&
                (buttons[5].getText() == "X") &&
                (buttons[8].getText() == "X")) {
            xWins(2, 5, 8);
        }
        if ((buttons[0].getText() == "X") &&
                (buttons[4].getText() == "X") &&
                (buttons[8].getText() == "X")) {
            xWins(0, 4, 8);
        }
        if ((buttons[2].getText() == "X") &&
                (buttons[4].getText() == "X") &&
                (buttons[6].getText() == "X")) {
            xWins(2, 4, 6);
        }

        // check O win conditions
        if ((buttons[0].getText() == "O") &&
                (buttons[1].getText() == "O") &&
                (buttons[2].getText() == "O")) {
            oWins(0, 1, 2);
        }
        if ((buttons[3].getText() == "O") &&
                (buttons[4].getText() == "O") &&
                (buttons[5].getText() == "O")) {
            oWins(3, 4, 5);
        }
        if ((buttons[6].getText() == "O") &&
                (buttons[7].getText() == "O") &&
                (buttons[8].getText() == "O")) {
            oWins(6, 7, 8);
        }
        if ((buttons[0].getText() == "O") &&
                (buttons[3].getText() == "O") &&
                (buttons[6].getText() == "O")) {
            oWins(0, 3, 6);
        }
        if ((buttons[1].getText() == "O") &&
                (buttons[4].getText() == "O") &&
                (buttons[7].getText() == "O")) {
            oWins(1, 4, 7);
        }
        if ((buttons[2].getText() == "O") &&
                (buttons[5].getText() == "O") &&
                (buttons[8].getText() == "O")) {
            oWins(2, 5, 8);
        }
        if ((buttons[0].getText() == "O") &&
                (buttons[4].getText() == "O") &&
                (buttons[8].getText() == "O")) {
            oWins(0, 4, 8);
        }
        if ((buttons[2].getText() == "O") &&
                (buttons[4].getText() == "O") &&
                (buttons[6].getText() == "O")) {
            oWins(2, 4, 6);
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
            if (isBoardFull()) {
                for (int i = 0; i < Settings.TOTAL_BUTTONS; i++) {
                    buttons[i].setEnabled(false);
                }
                textfield.setText("draw");
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
