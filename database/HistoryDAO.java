package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;

public class HistoryDAO {

    // --- Insert new history record when patient is registered ---
    public static void addHistoryRecord(int patientID, int doctorID, String disease, String hospital) {
        String query = "INSERT INTO history (patientID, doctorID, date, disease, hospital, completed) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, patientID);
            ps.setInt(2, doctorID);
            ps.setDate(3, Date.valueOf(LocalDate.now())); // current date
            ps.setString(4, disease);
            ps.setString(5, hospital);
            ps.setString(6, "N"); // N = not completed yet
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- Update completion status ---
    public static void updateHistoryCompletion(int patientID, int doctorID, boolean completed) {
        String query = "UPDATE history SET completed = ? WHERE patientID = ? AND doctorID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, completed ? "Y" : "N");
            ps.setInt(2, patientID);
            ps.setInt(3, doctorID);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


