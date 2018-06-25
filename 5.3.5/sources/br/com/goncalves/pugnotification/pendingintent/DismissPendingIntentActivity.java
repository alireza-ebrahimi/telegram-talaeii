package br.com.goncalves.pugnotification.pendingintent;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import br.com.goncalves.pugnotification.constants.BroadcastActions;
import br.com.goncalves.pugnotification.interfaces.PendingIntentNotification;
import br.com.goncalves.pugnotification.notification.PugNotification;

public class DismissPendingIntentActivity implements PendingIntentNotification {
    private final Class<?> mActivity;
    private final Bundle mBundle;
    private final int mIdentifier;

    public DismissPendingIntentActivity(Class<?> activity, Bundle bundle, int identifier) {
        this.mActivity = activity;
        this.mBundle = bundle;
        this.mIdentifier = identifier;
    }

    public PendingIntent onSettingPendingIntent() {
        Intent dismissIntentActivity = new Intent(PugNotification.mSingleton.mContext, this.mActivity);
        dismissIntentActivity.setAction(BroadcastActions.ACTION_PUGNOTIFICATION_DIMISS_INTENT);
        dismissIntentActivity.addFlags(536870912);
        dismissIntentActivity.setPackage(PugNotification.mSingleton.mContext.getPackageName());
        if (this.mBundle != null) {
            dismissIntentActivity.putExtras(this.mBundle);
        }
        return PendingIntent.getActivity(PugNotification.mSingleton.mContext, this.mIdentifier, dismissIntentActivity, 134217728);
    }
}
