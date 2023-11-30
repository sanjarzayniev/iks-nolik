package frames;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class LoginFrame extends JFrame implements ActionListener {
    private JLabel label;
    private JTextField username;
    private JButton playButton = new JButton(Settings.PLAY_BUTTON_TEXT);

    private JPanel loginPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();

    private GameFrame gameFrame;

    public static String enteredUsername;

    public LoginFrame(GameFrame gameFrame) {
        setVisible(true);
        addJpanel();
        addJFrame();
        addLabel();
        addTextField();
        addButton();
        this.gameFrame = gameFrame;
    }

    private void addJpanel() {
        loginPanel.setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        loginPanel.setBackground(Color.BLACK);
    }

    private void addJFrame() {
        setTitle("Login");
        setLocation(new Point(400, 300));
        add(loginPanel);
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Settings.panel_background);
    }

    private void addLabel() {
        label = new JLabel(Settings.USERNAME_LABEL);
        label.setBounds(100, 10, 150, 15);
        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(label, gbc);
        label.setBackground(Color.WHITE);
        label.setForeground(Color.WHITE);
        gbc.anchor = GridBagConstraints.CENTER;
    }

    private void addTextField() {
        username = new JTextField();
        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(username, gbc);
        gbc.anchor = GridBagConstraints.CENTER;
        username.setForeground(Color.BLACK);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        username.setPreferredSize(new Dimension(200, 28));
    }

    private void addButton() {
        gbc.gridx = 0;
        gbc.gridy = 2;
        loginPanel.add(playButton, gbc);
        playButton.addActionListener(this);
        playButton.setForeground(Color.GREEN);
        playButton.setBackground(Color.WHITE);
        playButton.setBounds(100, 110, 90, 25);
        gbc.anchor = GridBagConstraints.CENTER;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        enteredUsername = username.getText();

        if (enteredUsername.isBlank()) {
            JOptionPane.showMessageDialog(null, Settings.USERNAME_WARNING_TEXT, "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            ImageIcon icon = new ImageIcon(Settings.TICK_ICON_PATH);
            JOptionPane.showMessageDialog(
                    null,
                    Settings.LOGIN_SUCCESS_TEXT,
                    "SUCCESS",
                    JOptionPane.INFORMATION_MESSAGE,
                    icon);
            this.dispose();
            this.gameFrame.setVisible(true);
            this.gameFrame.setUsername(enteredUsername);
            this.gameFrame.initSockets();
        }

    }

}