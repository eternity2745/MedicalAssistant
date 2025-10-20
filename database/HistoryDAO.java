package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import utilities.History;

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
    
    public static List<History> getVisitHistoryByPatientID(int patientID) {
        List<History> visits = new ArrayList<>();

        String query = "SELECT h.date, h.disease, d.name AS doctor, h.hospital " +
                       "FROM history h " +
                       "JOIN doctors d ON h.doctorID = d.id " +
                       "WHERE h.patientID = ? AND h.completed = 'T' " +
                       "ORDER BY h.date DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, patientID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                History record = new History();
                record.setDate(rs.getString("date"));
                record.setDisease(rs.getString("disease"));
                record.setHospital(rs.getString("hospital"));
                record.setDoctorName(rs.getString("doctor"));
                visits.add(record);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
         return visits;
    }

    public static List<History> getPendingReports() {
    List<History> reports = new ArrayList<>();
    String query = "SELECT * FROM history WHERE completed = 'F'";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(query);
         ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            History history = new History();
            history.setPatientID(rs.getInt("patientID"));
            history.setDoctorID(rs.getInt("doctorID"));
            history.setDate(rs.getString("date"));
            history.setHospital(rs.getString("hospital"));
            history.setCompleted(rs.getString("completed"));
            history.setDisease(rs.getString("disease"));
            reports.add(history);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return reports;
}

    public static List<History> searchPendingReports(String keyword) {
        List<History> reports = new ArrayList<>();
        String query = "SELECT * FROM history WHERE completed = 'F' AND (patientID LIKE ?)";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    History history = new History();
                    history.setPatientID(rs.getInt("patientID"));
                    history.setDoctorID(rs.getInt("doctorID"));
                    history.setDate(rs.getString("date"));
                    history.setHospital(rs.getString("hospital"));
                    history.setCompleted(rs.getString("completed"));
                    history.setDisease(rs.getString("disease"));
                    reports.add(history);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reports;
    }

    public static void markReportCompleted(int patientID, int doctorID) {
        String query = "UPDATE history SET completed = 'T' WHERE completed = 'F' AND patientID = ? AND doctorID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, patientID);
            ps.setInt(2, doctorID);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
