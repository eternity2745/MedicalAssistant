package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
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
}
