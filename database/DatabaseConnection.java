package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public class DatabaseConnection {
    private static Connection connection = null;
    private static Dotenv dotenv;

    private DatabaseConnection() {}

    public static Connection getConnection() {
        try {
            
            if (connection == null || connection.isClosed()) {

                if (dotenv == null) {
                    dotenv = Dotenv.load();
                }

                String host = dotenv.get("SQL_HOST");
                String dbName = dotenv.get("SQL_NAME");
                String password = dotenv.get("SQL_PASS");
                String port = dotenv.get("SQL_PORT");

                String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName;
                String user = dbName;

                connection = DriverManager.getConnection(url, user, password);
                System.out.println("✅ Connected to database successfully at " + host);
            }
        } catch (SQLException e) {
            System.err.println("❌ Database connection failed!");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("❌ Error loading .env file!");
            e.printStackTrace();
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




