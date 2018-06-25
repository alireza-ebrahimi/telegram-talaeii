package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import com.google.android.gms.C0489R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbf;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzca;

@Deprecated
public final class zzbz {
    private static final Object sLock = new Object();
    private static zzbz zzgah;
    private final String mAppId;
    private final Status zzgai;
    private final boolean zzgaj;
    private final boolean zzgak;

    private zzbz(Context context) {
        boolean z = true;
        Resources resources = context.getResources();
        int identifier = resources.getIdentifier("google_app_measurement_enable", "integer", resources.getResourcePackageName(C0489R.string.common_google_play_services_unknown_issue));
        if (identifier != 0) {
            boolean z2 = resources.getInteger(identifier) != 0;
            if (z2) {
                z = false;
            }
            this.zzgak = z;
            z = z2;
        } else {
            this.zzgak = false;
        }
        this.zzgaj = z;
        Object zzcr = zzbf.zzcr(context);
        if (zzcr == null) {
            zzcr = new zzca(context).getString("google_app_id");
        }
        if (TextUtils.isEmpty(zzcr)) {
            this.zzgai = new Status(10, "Missing google app id value from from string resources with name google_app_id.");
            this.mAppId = null;
            return;
        }
        this.mAppId = zzcr;
        this.zzgai = Status.zzftq;
    }

    public static String zzakq() {
        return zzgi("getGoogleAppId").mAppId;
    }

    public static boolean zzakr() {
        return zzgi("isMeasurementExplicitlyDisabled").zzgak;
    }

    public static Status zzcl(Context context) {
        Status status;
        zzbq.checkNotNull(context, "Context must not be null.");
        synchronized (sLock) {
            if (zzgah == null) {
                zzgah = new zzbz(context);
            }
            status = zzgah.zzgai;
        }
        return status;
    }

    private static zzbz zzgi(String str) {
        zzbz zzbz;
        synchronized (sLock) {
            if (zzgah == null) {
                throw new IllegalStateException(new StringBuilder(String.valueOf(str).length() + 34).append("Initialize must be called before ").append(str).append(".").toString());
            }
            zzbz = zzgah;
        }
        return zzbz;
    }
}
