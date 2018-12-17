package net.skhu.feederpetedit;

public class ReservationRecord {
    String userID;
    String resTime;
    String resAmount;

    public ReservationRecord(String userID, String resTime, String resAmount) {
        this.userID = userID;
        this.resTime = resTime;
        this.resAmount = resAmount;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getResTime() {
        return resTime;
    }

    public void setResTime(String resTime) {
        this.resTime = resTime;
    }

    public String getResAmount() {
        return resAmount;
    }

    public void setResAmount(String resAmount) {
        this.resAmount = resAmount;
    }
}
