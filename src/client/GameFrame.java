package client;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import helpers.Logger;
import server.ClientThreadHandler;
import settings.ServerSettings;
import settings.Settings;

import java.awt.event.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class GameFrame extends JFrame {
    boolean firstPlayerTurn;
    boolean winnerExists = false;

    Random random = new Random();
    JPanel title_panel = new JPanel(), button_panel = new JPanel();
    public JLabel textfield = new JLabel(), upperTextField = new JLabel(), usernameLabel = new JLabel();

    public JButton[] buttons = new JButton[Settings.TOTAL_BUTTONS];

    // Socket handlers
    Scanner input;
    PrintWriter output;

    public boolean isWaiting;

    public GameFrame() {
        initFrame();
        addTextField();
        initPanels();
        initGameBoard();
    }

    void initFrame() {
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Settings.FRAME_SIZE, Settings.FRAME_SIZE);
        getContentPane().setBackground(Settings.panel_background);
    }

    void initPanels() {
        title_panel.setBounds(0, 0, 800, 100);
        title_panel.setLayout(new GridLayout(1, 3));
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
            buttons[index].setBackground(Color.WHITE);
            buttons[index].setFont(Settings.buttonFont);
            buttons[index].addActionListener(new ButtonActionListener());
        }

        title_panel.add(textfield);
        title_panel.add(upperTextField);
        title_panel.add(usernameLabel);

        add(title_panel, BorderLayout.NORTH);
        add(button_panel);
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

    public void makeButtonsDisabled() {
        for (JButton button : buttons) {
            button.setEnabled(false);
        }
    }

    public void releaseButtons() {
        for (JButton button : buttons) {
            button.setEnabled(true);
        }
    }

    public void setUsername(String username) {
        this.usernameLabel.setText(username);
    }

    public void initBackgroundEvents() {
        Socket socket;
        try {
            socket = new Socket(ServerSettings.SERVER_HOST, ServerSettings.SERVER_PORT);
            input = new Scanner(socket.getInputStream());
            output = new PrintWriter(socket.getOutputStream(), true);

            new Thread(new ClientThreadHandler(input, this, usernameLabel.getText())).start();

        } catch (IOException exc) {
            Logger.error(exc.toString());
        }
    }

    public void updateButton(JButton button, String buttonText, String myMark) {
        Color color = myMark == buttonText ? Settings.button_blue_color : Settings.button_red_color;
        button.setForeground(color);
        button.setText(buttonText);

    }

    class ButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            for (int index = 0; index < Settings.TOTAL_BUTTONS; index++) {
                if (event.getSource() == buttons[index]) {
                    if (!isWaiting) {
                        output.println(index);
                    }
                }
            }
        }
    }

}
