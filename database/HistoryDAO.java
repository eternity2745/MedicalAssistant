package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import utilities.VisitRecord;

public class HistoryDAO {

    // --- Insert new history record when patient is registered ---
    public static void addHistoryRecord(int patientID, int doctorID, String disease, String hospital) {
        String query = "INSERT INTO history (patientID, doctorID, date, disease, hospital, completed) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            System.out.println(Date.valueOf(LocalDate.now()));
            ps.setInt(1, patientID);
            ps.setInt(2, doctorID);
            ps.setDate(3, Date.valueOf(LocalDate.now())); // current date
            ps.setString(4, disease);
            ps.setString(5, hospital);
            ps.setString(6, "F");
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void replaceOrUpdateHistory(int patientID, int doctorID, String disease, String hospital) {
    String checkSql = "SELECT patientID FROM history WHERE patientID= ?";
    String updateSql = "UPDATE history SET doctorID=?, disease=?, hospital=? WHERE patientID=?";
    String insertSql = "INSERT INTO history (patientID, doctorID, disease, hospital) VALUES (?, ?, ?, ?)";

    try (Connection con = DatabaseConnection.getConnection()) {
        var rs = con.prepareStatement(checkSql);
        rs.setInt(1, patientID);
        var resultSet = rs.executeQuery();
        if (resultSet.next()) {
            try (PreparedStatement ps = con.prepareStatement(updateSql)) {
                ps.setInt(1, doctorID);
                ps.setString(2, disease);
                ps.setString(3, hospital);
                ps.setInt(4, patientID);
                ps.executeUpdate();
            }
        } else {
            try (PreparedStatement ps = con.prepareStatement(insertSql)) {
                ps.setInt(1, patientID);
                ps.setInt(2, doctorID);
                ps.setString(3, disease);
                ps.setString(4, hospital);
                ps.executeUpdate();
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }}
    
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
