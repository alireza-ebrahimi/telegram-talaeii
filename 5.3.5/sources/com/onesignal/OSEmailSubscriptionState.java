package com.onesignal;

import android.support.annotation.NonNull;
import org.json.JSONObject;

public class OSEmailSubscriptionState implements Cloneable {
    private String emailAddress;
    private String emailUserId;
    OSObservable<Object, OSEmailSubscriptionState> observable = new OSObservable("changed", false);

    OSEmailSubscriptionState(boolean asFrom) {
        if (asFrom) {
            this.emailUserId = OneSignalPrefs.getString(OneSignalPrefs.PREFS_ONESIGNAL, "PREFS_ONESIGNAL_EMAIL_ID_LAST", null);
            this.emailAddress = OneSignalPrefs.getString(OneSignalPrefs.PREFS_ONESIGNAL, "PREFS_ONESIGNAL_EMAIL_ADDRESS_LAST", null);
            return;
        }
        this.emailUserId = OneSignal.getEmailId();
        this.emailAddress = OneSignalStateSynchronizer.getEmailStateSynchronizer().getRegistrationId();
    }

    void clearEmailAndId() {
        boolean changed = (this.emailUserId == null && this.emailAddress == null) ? false : true;
        this.emailUserId = null;
        this.emailAddress = null;
        if (changed) {
            this.observable.notifyChange(this);
        }
    }

    void setEmailUserId(@NonNull String id) {
        boolean changed = !id.equals(this.emailUserId);
        this.emailUserId = id;
        if (changed) {
            this.observable.notifyChange(this);
        }
    }

    public String getEmailUserId() {
        return this.emailUserId;
    }

    void setEmailAddress(@NonNull String email) {
        boolean changed = !email.equals(this.emailAddress);
        this.emailAddress = email;
        if (changed) {
            this.observable.notifyChange(this);
        }
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public boolean getSubscribed() {
        return (this.emailUserId == null || this.emailAddress == null) ? false : true;
    }

    void persistAsFrom() {
        OneSignalPrefs.saveString(OneSignalPrefs.PREFS_ONESIGNAL, "PREFS_ONESIGNAL_EMAIL_ID_LAST", this.emailUserId);
        OneSignalPrefs.saveString(OneSignalPrefs.PREFS_ONESIGNAL, "PREFS_ONESIGNAL_EMAIL_ADDRESS_LAST", this.emailAddress);
    }

    boolean compare(OSEmailSubscriptionState from) {
        if ((this.emailUserId != null ? this.emailUserId : "").equals(from.emailUserId != null ? from.emailUserId : "")) {
            if ((this.emailAddress != null ? this.emailAddress : "").equals(from.emailAddress != null ? from.emailAddress : "")) {
                return false;
            }
        }
        return true;
    }

    protected Object clone() {
        try {
            return super.clone();
        } catch (Throwable th) {
            return null;
        }
    }

    public JSONObject toJSONObject() {
        JSONObject mainObj = new JSONObject();
        try {
            if (this.emailUserId != null) {
                mainObj.put("emailUserId", this.emailUserId);
            } else {
                mainObj.put("emailUserId", JSONObject.NULL);
            }
            if (this.emailAddress != null) {
                mainObj.put("emailAddress", this.emailAddress);
            } else {
                mainObj.put("emailAddress", JSONObject.NULL);
            }
            mainObj.put("subscribed", getSubscribed());
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return mainObj;
    }

    public String toString() {
        return toJSONObject().toString();
    }
}
