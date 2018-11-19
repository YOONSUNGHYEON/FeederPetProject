package net.skhu.feederpetedit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Date;

public class FeedRecord {

    String feedID;
    String userID;
    String feedAmount;
    String feedRegdate;

    public FeedRecord(String feedID, String userID, String feedAmount, String feedRegdate) {
        this.feedID = feedID;
        this.userID = userID;
        this.feedAmount = feedAmount;
        this.feedRegdate = feedRegdate;
    }

    public String getFeedID() {
        return feedID;
    }

    public String getFeedAmount() {
        return feedAmount;
    }

    public String getFeedRegdate() {
        return feedRegdate;
    }

    public String getUserID() {
        return userID;
    }

    public void setFeedID(String feedID) {
        this.feedID = feedID;
    }

    public void setFeedAmount(String feedAmount) {
        this.feedAmount = feedAmount;
    }

    public void setFeedRegdate(String feedRegdate) {
        this.feedRegdate = feedRegdate;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }


}

