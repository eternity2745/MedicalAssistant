package utilities;

public class Doctor implements User {
    private int id;
    private String name;
    private String email;
    private String profilePic;
    private String phoneNumber;
    private String password;
    private String hospital;
    private String specialization;
    private int AIAnalysis;
    private int totalPatients;
    private int appointments;
    private int pending;

    public Doctor(String name, String email, String phoneNumber, String password, String hospital ,String specialization) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.hospital = hospital;
        this.specialization = specialization;
    }

    public int getID() {return id ;}

    public void setID(int id) {this.id = id;}

    public void setTotalPatients(int totalPatients) {this.totalPatients = totalPatients;}

    public void setAppointments(int appointments) {this.appointments = appointments;}

    public void setPending(int pending) {this.pending = pending;}

    public void setProfilePic(String profilePic) {this.profilePic = profilePic;}

    public void setAIAnalysis(int AIAnalysis) { this.AIAnalysis = AIAnalysis; }

    public int getTotalPatients() {return totalPatients ;}

    public int getAppointments() {return appointments ;}

    public int getPending() {return pending ;}

    @Override
    public String getName() { return name; }

    @Override
    public String getEmail() { return email; }

    public String getProfilePic() { return profilePic; }

    public String getPhone() {return phoneNumber ;}

    public String getPassword() { return password; }

    public String getHospital() { return hospital; }

    public String getSpecialization() { return specialization; }

    public int getAIAnalysis() { return AIAnalysis; }

    @Override
    public void viewProfile() {
        System.out.println("Doctor Profile");
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Specialization: " + specialization);
    }

    @Override
    public void updateProfile(String newName, String newEmail) {
        this.name = newName;
        this.email = newEmail;
        System.out.println("Doctor profile updated successfully.");
    }

    public void viewPatients() {
        System.out.println("Viewing patients assigned to Dr. " + name);
    }
}
