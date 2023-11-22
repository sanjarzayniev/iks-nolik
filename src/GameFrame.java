import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

class GameFrame extends JFrame {
    boolean firstPlayerTurn;

    final int FRAME_SIZE = 600, SLEEP_TIME = 2000;

    Random random = new Random();
    JPanel title_panel = new JPanel(), button_panel = new JPanel();

    JLabel textfield = new JLabel();

    JButton login = new JButton();
    JButton[] buttons = new JButton[9];

    GameFrame() {
        initFrame();
        addTextField();
        initPanels();
        initGameBoard();

        startGame();
    }

    void initFrame() {
        setVisible(true);
        setSize(FRAME_SIZE, FRAME_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(50, 50, 50));
        setLayout(new BorderLayout());
    }

    void initPanels() {
        title_panel.setLayout(new BorderLayout());
        title_panel.setBounds(0, 0, 800, 100);

        button_panel.setLayout(new GridLayout(3, 3));
        button_panel.setBackground(new Color(150, 150, 150));
    }

    void addTextField() {
        textfield.setOpaque(true);
        textfield.setBackground(new Color(25, 25, 25));
        textfield.setForeground(new Color(25, 255, 0));
        textfield.setFont(new Font("Ink Free", Font.BOLD, 30));
        textfield.setHorizontalAlignment(JLabel.CENTER);
        textfield.setText("Tic-Tac-Toe");
    }

    void initGameBoard() {
        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            button_panel.add(buttons[i]);
            buttons[i].setFont(new Font("MV Boli", Font.BOLD, 100));
            buttons[i].setFocusable(false);
            buttons[i].addActionListener(new ButtonActionListener());
        }

        title_panel.add(textfield);

        add(title_panel, BorderLayout.NORTH);
        add(button_panel);

        startGame();

    }

    public void startGame() {
        try {
            Thread.sleep(SLEEP_TIME);
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
        buttons[a].setBackground(Color.GREEN);
        buttons[b].setBackground(Color.GREEN);
        buttons[c].setBackground(Color.GREEN);

        for (int i = 0; i < 9; i++) {
            buttons[i].setEnabled(false);
        }
        textfield.setText("X wins");
    }

    public void oWins(int a, int b, int c) {
        buttons[a].setBackground(Color.GREEN);
        buttons[b].setBackground(Color.GREEN);
        buttons[c].setBackground(Color.GREEN);

        for (int i = 0; i < 9; i++) {
            buttons[i].setEnabled(false);
        }
        textfield.setText("O wins");
    }

    class ButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            for (int i = 0; i < 9; i++) {
                if (event.getSource() == buttons[i]) {
                    if (firstPlayerTurn) {
                        if (buttons[i].getText() == "") {
                            buttons[i].setForeground(new Color(255, 0, 0));
                            buttons[i].setText("X");
                            firstPlayerTurn = false;
                            textfield.setText("O turn");
                            check();
                        } else {
                            for (int j = 0; j < 9; j++) {
                                buttons[j].setEnabled(false);
                            }
                            textfield.setText("draw");
                        }
                    }

                    else {
                        if (buttons[i].getText() == "") {
                            buttons[i].setForeground(new Color(0, 0, 255));
                            buttons[i].setText("O");
                            firstPlayerTurn = true;
                            textfield.setText("X turn");
                            check();
                        } else {
                            for (int j = 0; j < 9; j++) {
                                buttons[j].setEnabled(false);
                            }
                            textfield.setText("draw");
                        }
                    }
                }
            }
        }
    }
}
