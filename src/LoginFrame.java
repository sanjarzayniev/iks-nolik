import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginFrame extends JFrame implements ActionListener {
    private JLabel label;
    private JTextField username;
    private JButton playButton = new JButton("Play");

    private JPanel loginPanel = new JPanel();

    public LoginFrame() {
        addJpanel();
        addJFrame();
        addLabel();
        addTextField();
        addButton();
    }

    private void addJpanel() {
        loginPanel.setLayout(null);
    }

    private void addJFrame() {
        setTitle("Login Page");
        setLocation(new Point(500, 300));
        add(loginPanel);
        setSize(new Dimension(600, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void addLabel() {
        label = new JLabel("Username");
        label.setBounds(100, 8, 70, 20);
        loginPanel.add(label);
    }

    private void addTextField() {
        username = new JTextField();
        username.setBounds(100, 27, 193, 28);
        loginPanel.add(username);
    }

    private void addButton() {
        playButton.setBounds(100, 110, 90, 25);
        playButton.setForeground(Color.WHITE);
        playButton.setBackground(Color.BLACK);
        playButton.addActionListener(this);
        loginPanel.add(playButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String enteredUsername = username.getText();

        JOptionPane.showMessageDialog(null, "Login Successful\nUsername: " + enteredUsername);
    }
}