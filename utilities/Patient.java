package utilities;

public class Patient implements User {

    private int ID;
    private String name;
    private int age;
    private String profilePic;
    private String email;
    private String gender;
    private String dob; 
    private String phone;
    private String allergies;
    private String medications;
    private String bloodGroup;
    private String condition;
    private String address;

    @Override
    public int getID() { return ID; }
    @Override
    public void setID(int ID) { this.ID = ID; }

    @Override
    public String getName() { return name; }
    @Override
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    @Override
    public String getProfilePic() { return profilePic; }
    @Override
    public void setProfilePic(String profilePic) { this.profilePic = profilePic; }

    @Override
    public String getEmail() { return email; }
    @Override
    public void setEmail(String email) { this.email = email; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getDob() { return dob; }
    public void setDob(String dob) { this.dob = dob; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAllergies() { return allergies; }
    public void setAllergies(String allergies) { this.allergies = allergies; }

    public String getMedications() { return medications; }
    public void setMedications(String medications) { this.medications = medications; }

    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }

    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}

