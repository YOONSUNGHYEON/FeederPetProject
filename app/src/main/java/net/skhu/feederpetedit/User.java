package net.skhu.feederpetedit;

import java.io.Serializable;

public class User implements Serializable {

    private String userID;
    private String userPassword;
    private String userName;
    private int userAge;
    private int petType;
    private String petName;

    public User(String userID, String userPassword, String userName, int userAge, int petType, String petName) {
        this.userID = userID;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userAge = userAge;
        this.petType = petType;
        this.petName = petName;
    }

    public String getUserID() {
        return userID;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public int getUserAge() {
        return userAge;
    }

    public int getPetType() {
        return petType;
    }

    public String getPetName() {
        return petName;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    public void setPetType(int petType) {
        this.petType = petType;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }
}
