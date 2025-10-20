package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import utilities.Patient;

public class PatientDAO {

    public static boolean registerPatient(Patient patient) {
        String query = "INSERT INTO patients (id, name, age, profilePic, email, gender, dob, phone, allergies, medications, bloodGroup, disease, address) "
                     + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        int id = (int)(Math.random() * 70000) + (int)(Math.random() * 10000);
        String profilePic = "D:\\Java\\MedicalAssistant\\Resources\\Images\\patientProfile.jpg";

        patient.setID(id);
        patient.setProfilePic(profilePic);

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.setString(2, patient.getName());
            stmt.setInt(3, patient.getAge());
            stmt.setString(4, profilePic);
            stmt.setString(5, patient.getEmail());
            stmt.setString(6, patient.getGender());
            stmt.setString(7, patient.getDob());
            stmt.setString(8, patient.getPhone());
            stmt.setString(9, patient.getAllergies());
            stmt.setString(10, patient.getMedications());
            stmt.setString(11, patient.getBloodGroup());
            stmt.setString(12, patient.getCondition());
            stmt.setString(13, patient.getAddress());

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
                p.setCondition(rs.getString("disease"));
                patients.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }
   public static boolean updatePatient(Patient patient) {
    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(
             "UPDATE patients SET age=?, medications=?, disease=? WHERE id=?")) {

        ps.setInt(1, patient.getAge());
        ps.setString(2, patient.getMedications());
        ps.setString(3, patient.getCondition());
        ps.setInt(4, patient.getID());

        return ps.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
public static Patient getPatientByID(int id) {
    Patient patient = null;
    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement("SELECT * FROM patients WHERE id=?")) {
        ps.setInt(1, id);
        var rs = ps.executeQuery();
        if(rs.next()) {
            patient = new Patient();
            patient.setID(rs.getInt("id"));
            patient.setName(rs.getString("name"));
            patient.setAge(rs.getInt("age"));
            patient.setEmail(rs.getString("email"));
            patient.setGender(rs.getString("gender"));
            patient.setDob(rs.getString("dob"));
            patient.setPhone(rs.getString("phone"));
            patient.setAllergies(rs.getString("allergies"));
            patient.setMedications(rs.getString("medications"));
            patient.setBloodGroup(rs.getString("blood_group"));
            patient.setCondition(rs.getString("disease"));
            patient.setAddress(rs.getString("address"));
        }
    } catch(Exception e) {
        e.printStackTrace();
    }
    return patient;
}


}


