package frames;
import java.awt.Point;
import java.awt.Color;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

public class LoginFrame extends JFrame implements ActionListener {
    private JLabel label;
    private JTextField username;
    private JButton playButton = new JButton("Play");

    private JPanel loginPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();

    private GameFrame gameFrame;

    public LoginFrame(GameFrame gameFrame) {
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
        // getContentPane().setBackground(new Color(200, 200, 200));
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
        label = new JLabel("Input your username:");
        label.setBounds(100, 10, 150, 15);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(label, gbc);
        label.setBackground(Color.WHITE);
        label.setForeground(Color.WHITE);
    }

    private void addTextField() {
        username = new JTextField();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        username.setPreferredSize(new Dimension(200, 28));
        username.setForeground(Color.BLACK);
        loginPanel.add(username, gbc);
    }

    private void addButton() {
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        playButton.setBounds(100, 110, 90, 25);
        playButton.setForeground(Color.GREEN);
        playButton.setBackground(Color.WHITE);
        playButton.addActionListener(this);
        loginPanel.add(playButton, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String enteredUsername = username.getText();

        if (enteredUsername.isBlank()) {
            JOptionPane.showMessageDialog(null, "Please, input your username!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            ImageIcon icon = new ImageIcon("assets/images/green_tick_icon.png");
            JOptionPane.showMessageDialog(null, "Login Successful", "Success", JOptionPane.INFORMATION_MESSAGE, icon);
            this.dispose();
            this.gameFrame.setVisible(true);
        }

    }
}