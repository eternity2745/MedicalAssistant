package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import utilities.Doctor;
import utilities.Patient;

public class PatientDAO {
    

    // ---------------- Register Patient ----------------
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

    // --- Overloaded version (doctor + patient) ---
    // --- Overloaded version (doctor + patient) ---
    public static boolean registerPatientHistory(Patient patient, int doctorID) {
        boolean registered = registerPatient(patient);
        if (registered) {
            Doctor doctor = DoctorDAO.getDoctorByID(doctorID); // fetch the doctor
            String hospital = (doctor != null && doctor.getHospital() != null) ? doctor.getHospital() : "Unknown";
            
            HistoryDAO.addHistoryRecord(
                patient.getID(),
                doctorID,
                patient.getCondition(),
                hospital
            );
        }
        return registered;
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
                p.setAge(rs.getInt("age"));
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
//     public static List<Patient> searchPatients(String keyword) {
//     List<Patient> patients = new ArrayList<>();
//     String sql = "SELECT p.id, p.name, p.age, p.gender, p.medications, h.disease, h.timestamp " +
//                  "FROM patients p " +
//                  "LEFT JOIN patient_history h ON p.id = h.patient_id " +
//                  "WHERE (p.name LIKE ? OR p.email LIKE ?) " +
//                  "AND h.id = (SELECT MAX(id) FROM patient_history WHERE patient_id = p.id)";

//     try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
//         ps.setString(1, "%" + keyword + "%");
//         ps.setString(2, "%" + keyword + "%");
//         ResultSet rs = ps.executeQuery();

//         while (rs.next()) {
//             Patient p = new Patient();
//             p.setID(rs.getInt("id"));
//             p.setName(rs.getString("name"));
//             p.setAge(rs.getInt("age"));
//             p.setGender(rs.getString("gender"));
//             p.setMedications(rs.getString("medications"));
//             p.setCondition(rs.getString("disease"));  // only current disease
//              // optional: store timestamp
//             patients.add(p);
//         }

//     } catch (Exception e) {
//         e.printStackTrace();
//     }

//     return patients;
// }


    public static List<Patient> getAllPatients() {
        List<Patient> list = new ArrayList<>();
        String query = "SELECT * FROM patients ORDER BY name LIMIT 10";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(mapPatient(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Patient> searchPatientsScreen(String query) {
        List<Patient> list = new ArrayList<>();
        String sql = "SELECT * FROM patients WHERE LOWER(name) LIKE ? OR LOWER(id) LIKE ? OR phone LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String like = "%" + query + "%";
            stmt.setString(1, like);
            stmt.setString(2, like);
            stmt.setString(3, like);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapPatient(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static boolean doesPatientExist(int patientId) {
        String query = "SELECT 1 FROM patients WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }



    private static Patient mapPatient(ResultSet rs) throws SQLException {
        Patient patient = new Patient();
        patient.setID(rs.getInt("id"));
        patient.setAge(rs.getInt("age"));
        patient.setName(rs.getString("name"));
        patient.setDob(rs.getString("dob"));
        patient.setPhone(rs.getString("phone"));
        patient.setAllergies(rs.getString("allergies"));
        patient.setAddress(rs.getString("address"));
        patient.setBloodGroup(rs.getString("bloodGroup"));
        patient.setCondition(rs.getString("disease"));
        patient.setGender(rs.getString("gender"));
        patient.setProfilePic(rs.getString("profilePic"));
        patient.setEmail(rs.getString("email"));
        patient.setMedications(rs.getString("medications"));
        return patient;
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
    public static boolean updatePatientWithHistory(Patient patient, int doctorID) {
    boolean updated = updatePatient(patient);
    if (updated) {
        // Add new history entry for this doctor and patient
        Doctor doctor = DoctorDAO.getDoctorByID(doctorID);
        String hospital = (doctor != null && doctor.getHospital() != null) ? doctor.getHospital() : "Unknown";

        //HistoryDAO.addHistoryRecord(patient.getID(), doctorID, patient.getCondition(), hospital);
        HistoryDAO.replaceOrUpdateHistory(patient.getID(), doctorID, patient.getCondition(), hospital);
    }
    return updated;
}

    

    // ---------------- Get Patient By ID ----------------
    public static Patient getPatientByID(int id) {
        Patient patient = null;
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM patients WHERE id=?")) {
            ps.setInt(1, id);
            var rs = ps.executeQuery();
            if (rs.next()) {
                patient = new Patient();
                patient.setID(rs.getInt("id"));
                patient.setName(rs.getString("name"));
                patient.setAge(rs.getInt("age"));
                patient.setEmail(rs.getString("email"));
                patient.setGender(rs.getString("gender"));
                patient.setDob(rs.getDate("dob").toString());
                patient.setPhone(rs.getString("phone"));
                patient.setAllergies(rs.getString("allergies"));
                patient.setMedications(rs.getString("medications"));
                patient.setBloodGroup(rs.getString("bloodGroup"));
                patient.setCondition(rs.getString("disease"));
                patient.setAddress(rs.getString("address"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return patient;
    }
}




