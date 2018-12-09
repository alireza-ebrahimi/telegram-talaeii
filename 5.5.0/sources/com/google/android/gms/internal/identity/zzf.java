package com.google.android.gms.internal.identity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;

public final class zzf extends zzh {
    private Activity mActivity;
    private final int zzj;

    public zzf(int i, Activity activity) {
        this.zzj = i;
        this.mActivity = activity;
    }

    private final void setActivity(Activity activity) {
        this.mActivity = null;
    }

    public final void zza(int i, Bundle bundle) {
        PendingIntent createPendingResult;
        if (i == 1) {
            Intent intent = new Intent();
            intent.putExtras(bundle);
            createPendingResult = this.mActivity.createPendingResult(this.zzj, intent, 1073741824);
            if (createPendingResult != null) {
                try {
                    createPendingResult.send(1);
                    return;
                } catch (Throwable e) {
                    Log.w("AddressClientImpl", "Exception settng pending result", e);
                    return;
                }
            }
            return;
        }
        createPendingResult = null;
        if (bundle != null) {
            createPendingResult = (PendingIntent) bundle.getParcelable("com.google.android.gms.identity.intents.EXTRA_PENDING_INTENT");
        }
        ConnectionResult connectionResult = new ConnectionResult(i, createPendingResult);
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(this.mActivity, this.zzj);
                return;
            } catch (Throwable e2) {
                Log.w("AddressClientImpl", "Exception starting pending intent", e2);
                return;
            }
        }
        try {
            createPendingResult = this.mActivity.createPendingResult(this.zzj, new Intent(), 1073741824);
            if (createPendingResult != null) {
                createPendingResult.send(1);
            }
        } catch (Throwable e22) {
            Log.w("AddressClientImpl", "Exception setting pending result", e22);
        }
    }
}
