package utilities;

public interface User {
    String getName();
    String getEmail();

    void viewProfile();
    void updateProfile(String newName, String newEmail);
}
