public class App {
    public static void main(String[] args) throws Exception {
        boolean visibility = true;
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setVisible(visibility);
        // loginFrame.dispose();
        GameFrame gameFrame = new GameFrame();
        visibility = false;
        gameFrame.setVisible(true);

    }
}
