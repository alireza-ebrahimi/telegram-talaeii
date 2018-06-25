package net.hockeyapp.android.objects;

import com.persianswitch.sdk.base.log.LogCollector;

public class CrashMetaData {
    private String mUserDescription;
    private String mUserEmail;
    private String mUserID;

    public String getUserDescription() {
        return this.mUserDescription;
    }

    public void setUserDescription(String userDescription) {
        this.mUserDescription = userDescription;
    }

    public String getUserEmail() {
        return this.mUserEmail;
    }

    public void setUserEmail(String userEmail) {
        this.mUserEmail = userEmail;
    }

    public String getUserID() {
        return this.mUserID;
    }

    public void setUserID(String userID) {
        this.mUserID = userID;
    }

    public String toString() {
        return LogCollector.LINE_SEPARATOR + CrashMetaData.class.getSimpleName() + LogCollector.LINE_SEPARATOR + "userDescription " + this.mUserDescription + LogCollector.LINE_SEPARATOR + "userEmail       " + this.mUserEmail + LogCollector.LINE_SEPARATOR + "userID          " + this.mUserID;
    }
}
