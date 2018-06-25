package net.hockeyapp.android;

import java.util.Date;
import org.json.JSONArray;

public abstract class UpdateManagerListener {
    public Class<? extends UpdateActivity> getUpdateActivityClass() {
        return UpdateActivity.class;
    }

    public Class<? extends UpdateFragment> getUpdateFragmentClass() {
        return UpdateFragment.class;
    }

    public void onNoUpdateAvailable() {
    }

    public void onUpdateAvailable() {
    }

    public void onCancel() {
    }

    public void onUpdateAvailable(JSONArray data, String url) {
        onUpdateAvailable();
    }

    public Date getExpiryDate() {
        return null;
    }

    public boolean onBuildExpired() {
        return true;
    }

    public boolean canUpdateInMarket() {
        return false;
    }

    public void onUpdatePermissionsNotGranted() {
    }
}
