package br.com.goncalves.pugnotification.pendingintent;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import br.com.goncalves.pugnotification.constants.BroadcastActions;
import br.com.goncalves.pugnotification.interfaces.PendingIntentNotification;
import br.com.goncalves.pugnotification.notification.PugNotification;

public class DismissPendingIntentBroadCast implements PendingIntentNotification {
    private final Bundle mBundle;
    private final int mIdentifier;

    public DismissPendingIntentBroadCast(Bundle bundle, int identifier) {
        this.mBundle = bundle;
        this.mIdentifier = identifier;
    }

    public PendingIntent onSettingPendingIntent() {
        Intent clickIntentBroadcast = new Intent(BroadcastActions.ACTION_PUGNOTIFICATION_DIMISS_INTENT);
        clickIntentBroadcast.addFlags(536870912);
        clickIntentBroadcast.setPackage(PugNotification.mSingleton.mContext.getPackageName());
        if (this.mBundle != null) {
            clickIntentBroadcast.putExtras(this.mBundle);
        }
        return PendingIntent.getBroadcast(PugNotification.mSingleton.mContext, this.mIdentifier, clickIntentBroadcast, 134217728);
    }
}
