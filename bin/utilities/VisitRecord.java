package utilities;

public class VisitRecord {
    private String date;
    private String disease;
    private String doctor;
    private String hospital;

    public VisitRecord(String date, String disease, String doctor, String hospital) {
        this.date = date;
        this.disease = disease;
        this.doctor = doctor;
        this.hospital = hospital;
    }

    public String getDate() { return date; }
    public String getDisease() { return disease; }
    public String getDoctor() { return doctor; }
    public String getHospital() { return hospital; }
}
