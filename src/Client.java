import frames.GameFrame;
import frames.LoginFrame;

public class Client {
    public static void main(String[] args) throws Exception {
        GameFrame gameFrame = new GameFrame();
        gameFrame.setVisible(false);
        LoginFrame loginFrame = new LoginFrame(gameFrame);
        loginFrame.setVisible(true);
    }
}