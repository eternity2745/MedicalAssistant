package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import utilities.Patient;

public class PatientDAO {

    public static boolean registerPatient(Patient patient) {
        String query = "INSERT INTO patients (id, name, profilePic, email, gender, dob, phone, allergies, medications, bloodGroup, condition, address) "
                     + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        int id = (int)(Math.random() * 70000) + (int)(Math.random() * 10000);
        String profilePic = "D:\\Java\\MedicalAssistant\\Resources\\Images\\patientProfile.jpg";

        patient.setID(id);
        patient.setProfilePic(profilePic);

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.setString(2, patient.getName());
            stmt.setString(3, profilePic);
            stmt.setString(4, patient.getEmail());
            stmt.setString(5, patient.getGender());
            stmt.setString(6, patient.getDob());
            stmt.setString(7, patient.getPhone());
            stmt.setString(8, patient.getAllergies());
            stmt.setString(9, patient.getMedications());
            stmt.setString(10, patient.getBloodGroup());
            stmt.setString(11, patient.getCondition());
            stmt.setString(12, patient.getAddress());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("❌ Email or ID already exists!");
            return false;
        } catch (SQLException e) {
            System.err.println("SQL Error while inserting patient: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // ---------------- Search Patients ----------------
    public static java.util.List<Patient> searchPatients(String keyword) {
        java.util.List<Patient> patients = new java.util.ArrayList<>();
        String query = "SELECT * FROM patients WHERE name LIKE ? OR email LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            String likeKeyword = "%" + keyword + "%";
            stmt.setString(1, likeKeyword);
            stmt.setString(2, likeKeyword);

            var rs = stmt.executeQuery();
            while (rs.next()) {
                Patient p = new Patient();
                p.setID(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setEmail(rs.getString("email"));
                p.setGender(rs.getString("gender"));
                p.setCondition(rs.getString("condition"));
                patients.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }
}


