package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import utilities.Doctor;

public class DoctorDAO {

    public static boolean registerDoctor(Doctor doctor) {
        String query = "INSERT INTO doctors (id, name, profilePic, email, password, phone, hospital, specialization, AI) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int id = (int)(Math.random() * 90000) + new Random().nextInt(10000);
        String profilePic = "D:\\Java\\MedicalAssistant\\Resources\\Images\\doctorProfile.jpg";

        doctor.setID(id);
        doctor.setProfilePic(profilePic);
        doctor.setAIAnalysis(0);

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.setString(2, doctor.getName());
            stmt.setString(3, profilePic);
            stmt.setString(4, doctor.getEmail());
            stmt.setString(5, doctor.getPassword());
            stmt.setString(6, doctor.getPhone());
            stmt.setString(7, doctor.getHospital());
            stmt.setString(8, doctor.getSpecialization());
            stmt.setInt(9, 0);

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("❌ Email already exists!");
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Doctor validateLogin(String email, String password) {
        String query = "SELECT * FROM doctors WHERE email = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            stmt.setString(2, password);

            var rs = stmt.executeQuery();
            if (rs.next()) {
                // Create and fill Doctor object
                Doctor doctor = new Doctor(rs.getString("name"), rs.getString("email"), rs.getString("phone"), rs.getString("password"), rs.getString("hospital"), rs.getString("specialization"));
                doctor.setID(rs.getInt("id"));
                doctor.setProfilePic(rs.getString("profilePic"));
                doctor.setAIAnalysis(rs.getInt("AI"));
                doctor.setTotalPatients(getTotalPatients(conn, rs.getInt("id")));
                doctor.setPending(getPendingReports(conn, rs.getInt("id")));
                doctor.setAppointments(getTodaysAppointments(conn, rs.getInt("id")));
                // Add other fields if any
                return doctor;
            }
            return null; // returns true if a match is found

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Object[]> getRecentPatients(int doctorId) {
        List<Object[]> recentPatients = new ArrayList<>();

        String query = """
            SELECT p.name, p.age, p.gender, h.disease, h.date
            FROM history h
            JOIN patients p ON h.patientID = p.id
            WHERE h.doctorID = ? AND h.completed = 'T'
            ORDER BY h.date DESC
            LIMIT 10
        """;

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, doctorId);
            var rs = stmt.executeQuery();

            while (rs.next()) {
                Object[] row = {
                    rs.getString("name"),
                    rs.getInt("age"),
                    rs.getString("gender"),
                    rs.getString("disease"),
                    // rs.getString("date")
                };
                recentPatients.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return recentPatients;
    }


    private static int getTodaysAppointments(Connection conn, int doctorId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM history WHERE doctorID = ? AND date = CURDATE()";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, doctorId);
            var rs = stmt.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    private static int getTotalPatients(Connection conn, int doctorId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM history WHERE doctorID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, doctorId);
            var rs = stmt.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    private static int getPendingReports(Connection conn, int doctorId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM history WHERE doctorID = ? AND completed = 'F'";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, doctorId);
            var rs = stmt.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        }
    }
    public static Doctor getDoctorByID(int id) {
    String query = "SELECT * FROM doctors WHERE id = ?";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setInt(1, id);
        var rs = stmt.executeQuery();
        if (rs.next()) {
            Doctor doctor = new Doctor(
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("phone"),
                rs.getString("password"),
                rs.getString("hospital"),
                rs.getString("specialization")
            );
            doctor.setID(rs.getInt("id"));
            doctor.setProfilePic(rs.getString("profilePic"));
            doctor.setAIAnalysis(rs.getInt("AI"));
            return doctor;
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}



}
