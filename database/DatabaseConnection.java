package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public class DatabaseConnection {
    private static Connection connection;
    private static Dotenv dotenv;

    private DatabaseConnection() {}

    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Load environment variables from .env file
                dotenv = Dotenv.load();

                String host = dotenv.get("SQL_HOST");
                String dbName = dotenv.get("SQL_NAME");
                String password = dotenv.get("SQL_PASS");
                String port = dotenv.get("SQL_PORT");

                // Construct JDBC URL
                String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName;
                String user = dbName; // In FreeSQLDatabase, username = dbName

                connection = DriverManager.getConnection(url, user, password);
                System.out.println("✅ Connected to database successfully at " + host);

            } catch (SQLException e) {
                System.err.println("❌ Database connection failed!");
                e.printStackTrace();
            } catch (Exception e) {
                System.err.println("❌ Error loading .env file!");
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("🔒 Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
