import frames.GameFrame;
import frames.LoginFrame;
import helpers.Database;

public class Client {
    public static void main(String[] args) throws Exception {
        new Database("database.key");
        
        GameFrame gameFrame = new GameFrame();
        gameFrame.setVisible(false);
        LoginFrame loginFrame = new LoginFrame(gameFrame);
        loginFrame.setVisible(true);
    }
}