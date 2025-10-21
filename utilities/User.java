package utilities;

public interface User {
    String getName();
    String getEmail();
    int getID();
    String getProfilePic();
    
    void setID(int ID);
    void setName(String name);
    void setEmail(String email);
    void setProfilePic(String profilePic);

}
