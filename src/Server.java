import helpers.Logger;
import helpers.Database;

public class Server {
    final static int SERVER_PORT = 2121,
            THREAD_POOL_EXECUTORS = 10;

    final static String DATABASE_KEY_FILE = "database.key";

    static Database db;

    public static void main(String[] args) {
        Logger.info("Running game server on tcp://localhost:" + SERVER_PORT);
        Logger.info("The number of executors on thread pool: " + THREAD_POOL_EXECUTORS);
        initDatabase();
    }
    public static void initServerSocket() {
        
    }
    public static void initDatabase() {
        db = new Database(DATABASE_KEY_FILE);
    }
}
