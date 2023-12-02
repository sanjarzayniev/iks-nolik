package helpers;

import java.sql.*;
import java.util.HashMap;

public class Database {
    private static Connection connection;
    private static String host, port, db_name;
    private static final int FAIL_STATUS_CODE = -1;

    private static Database instance = null;

    public static synchronized Database getInstance(String keyFileName) {
        if (instance == null)
            instance = new Database(keyFileName);

        return instance;
    }

    public Database(String keyFileName) {
        try {
            HashMap<String, String> credentials = Utils.readCredentials(keyFileName);
            // Read every credential, and construct database connection URL
            String connectionURL = getConnectionURL(credentials);

            connection = DriverManager.getConnection(
                    connectionURL,
                    credentials.get("DATABASE_USERNAME"),
                    credentials.get("DATABASE_PASSWORD"));
        } catch (SQLException exc) {
            Logger.error(exc.toString());
            System.exit(FAIL_STATUS_CODE);

        } catch (Exception exc) {
            Logger.error(exc.toString());
            System.exit(FAIL_STATUS_CODE);
        }

        Logger.success("Database connection is established");
    }

    private static String getConnectionURL(HashMap<String, String> credentials) {
        host = credentials.get("DATABASE_HOST").toString();
        port = credentials.get("DATABASE_PORT").toString();
        db_name = credentials.get("DATABASE_NAME").toString();
        return "jdbc:postgresql://" + host + ":" + port + "/" + db_name;
    }

    public Connection getConnection() {
        return connection;
    }

    public void createResult(String username, String description) {
        try {
            String sqlQuery = "INSERT INTO results(username, description) VALUES(?, ?)";

            PreparedStatement preparedStatement = this.getConnection().prepareStatement(
                    sqlQuery,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, description);
            preparedStatement.executeUpdate();

        } catch (Exception exc) {
            Logger.error("Error during database operation: " + exc.getStackTrace());
        }
    }
}
