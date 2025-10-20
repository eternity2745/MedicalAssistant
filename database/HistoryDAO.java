package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utilities.VisitRecord;

public class HistoryDAO {

    public static List<VisitRecord> getVisitHistoryByPatientID(int patientID) {
        List<VisitRecord> visits = new ArrayList<>();

        String query = "SELECT h.date, h.disease, d.name AS doctors, h.hospital " +
                       "FROM history h " +
                       "JOIN doctors d ON h.doctorID = d.id " +
                       "WHERE h.patientID = ? AND h.completed = 'T' " +
                       "ORDER BY h.date DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, patientID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                VisitRecord record = new VisitRecord(
                    rs.getString("date"),
                    rs.getString("disease"),
                    rs.getString("doctor"),
                    rs.getString("hospital")
                );
                visits.add(record);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return visits;
    }
}
