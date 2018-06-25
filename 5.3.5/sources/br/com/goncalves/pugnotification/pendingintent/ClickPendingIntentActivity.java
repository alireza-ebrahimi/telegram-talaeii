package br.com.goncalves.pugnotification.pendingintent;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import br.com.goncalves.pugnotification.constants.BroadcastActions;
import br.com.goncalves.pugnotification.interfaces.PendingIntentNotification;
import br.com.goncalves.pugnotification.notification.PugNotification;

public class ClickPendingIntentActivity implements PendingIntentNotification {
    private final Class<?> mActivity;
    private final Bundle mBundle;
    private final int mIdentifier;

    public ClickPendingIntentActivity(Class<?> activity, Bundle bundle, int identifier) {
        this.mActivity = activity;
        this.mBundle = bundle;
        this.mIdentifier = identifier;
    }

    public PendingIntent onSettingPendingIntent() {
        Intent clickIntentActivity = new Intent(PugNotification.mSingleton.mContext, this.mActivity);
        clickIntentActivity.setAction(BroadcastActions.ACTION_PUGNOTIFICATION_CLICK_INTENT);
        clickIntentActivity.addFlags(536870912);
        clickIntentActivity.setPackage(PugNotification.mSingleton.mContext.getPackageName());
        if (this.mBundle != null) {
            clickIntentActivity.putExtras(this.mBundle);
        }
        return PendingIntent.getActivity(PugNotification.mSingleton.mContext, this.mIdentifier, clickIntentActivity, 134217728);
    }
}
