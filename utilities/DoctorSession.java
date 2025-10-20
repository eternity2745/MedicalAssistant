package utilities;

public class DoctorSession {
    private static Doctor currentDoctor;

    public static void setCurrentDoctor(Doctor doctor) {
        currentDoctor = doctor;
    }

    public static Doctor getCurrentDoctor() {
        return currentDoctor;
    }

    public static void clearSession() {
        currentDoctor = null;
    }
}
