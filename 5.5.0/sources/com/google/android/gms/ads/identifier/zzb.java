package com.google.android.gms.ads.identifier;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesUtilLight;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public final class zzb {
    private SharedPreferences zzs;

    public zzb(Context context) {
        try {
            Context remoteContext = GooglePlayServicesUtilLight.getRemoteContext(context);
            this.zzs = remoteContext == null ? null : remoteContext.getSharedPreferences("google_ads_flags", 0);
        } catch (Throwable th) {
            Log.w("GmscoreFlag", "Error while getting SharedPreferences ", th);
            this.zzs = null;
        }
    }

    public final boolean getBoolean(String str, boolean z) {
        boolean z2 = false;
        try {
            if (this.zzs != null) {
                z2 = this.zzs.getBoolean(str, false);
            }
        } catch (Throwable th) {
            Log.w("GmscoreFlag", "Error while reading from SharedPreferences ", th);
        }
        return z2;
    }

    final float getFloat(String str, float f) {
        float f2 = BitmapDescriptorFactory.HUE_RED;
        try {
            if (this.zzs != null) {
                f2 = this.zzs.getFloat(str, BitmapDescriptorFactory.HUE_RED);
            }
        } catch (Throwable th) {
            Log.w("GmscoreFlag", "Error while reading from SharedPreferences ", th);
        }
        return f2;
    }

    final String getString(String str, String str2) {
        try {
            if (this.zzs != null) {
                str2 = this.zzs.getString(str, str2);
            }
        } catch (Throwable th) {
            Log.w("GmscoreFlag", "Error while reading from SharedPreferences ", th);
        }
        return str2;
    }
}
