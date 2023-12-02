import frames.GameFrame;
import frames.LoginFrame;

public class Client {
    public static void main(String[] args) throws Exception {
        GameFrame gameFrame = new GameFrame();
        LoginFrame loginFrame = new LoginFrame(gameFrame);
        loginFrame.setVisible(true);
    }
}