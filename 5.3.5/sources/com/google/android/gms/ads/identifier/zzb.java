package com.google.android.gms.ads.identifier;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.zzs;

@Hide
public final class zzb {
    private SharedPreferences zzang;

    @Hide
    public zzb(Context context) {
        try {
            Context remoteContext = zzs.getRemoteContext(context);
            this.zzang = remoteContext == null ? null : remoteContext.getSharedPreferences("google_ads_flags", 0);
        } catch (Throwable th) {
            Log.w("GmscoreFlag", "Error while getting SharedPreferences ", th);
            this.zzang = null;
        }
    }

    public final boolean getBoolean(String str, boolean z) {
        boolean z2 = false;
        try {
            if (this.zzang != null) {
                z2 = this.zzang.getBoolean(str, false);
            }
        } catch (Throwable th) {
            Log.w("GmscoreFlag", "Error while reading from SharedPreferences ", th);
        }
        return z2;
    }

    final float getFloat(String str, float f) {
        float f2 = 0.0f;
        try {
            if (this.zzang != null) {
                f2 = this.zzang.getFloat(str, 0.0f);
            }
        } catch (Throwable th) {
            Log.w("GmscoreFlag", "Error while reading from SharedPreferences ", th);
        }
        return f2;
    }

    final String getString(String str, String str2) {
        try {
            if (this.zzang != null) {
                str2 = this.zzang.getString(str, str2);
            }
        } catch (Throwable th) {
            Log.w("GmscoreFlag", "Error while reading from SharedPreferences ", th);
        }
        return str2;
    }
}
