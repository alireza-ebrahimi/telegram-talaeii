package com.google.android.gms.internal.clearcut;

import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;
import java.io.IOException;

final class zzal extends zzae<T> {
    private final Object lock = new Object();
    private String zzec;
    private T zzed;
    private final /* synthetic */ zzan zzee;

    zzal(zzao zzao, String str, Object obj, zzan zzan) {
        this.zzee = zzan;
        super(zzao, str, obj);
    }

    protected final T zza(SharedPreferences sharedPreferences) {
        try {
            return zzb(sharedPreferences.getString(this.zzds, TtmlNode.ANONYMOUS_REGION_ID));
        } catch (Throwable e) {
            Throwable th = e;
            String str = "PhenotypeFlag";
            String str2 = "Invalid byte[] value in SharedPreferences for ";
            String valueOf = String.valueOf(this.zzds);
            Log.e(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2), th);
            return null;
        }
    }

    protected final T zzb(String str) {
        try {
            T t;
            synchronized (this.lock) {
                if (!str.equals(this.zzec)) {
                    Object zzb = this.zzee.zzb(Base64.decode(str, 3));
                    this.zzec = str;
                    this.zzed = zzb;
                }
                t = this.zzed;
            }
            return t;
        } catch (IOException e) {
        } catch (IllegalArgumentException e2) {
        }
        String str2 = this.zzds;
        Log.e("PhenotypeFlag", new StringBuilder((String.valueOf(str2).length() + 27) + String.valueOf(str).length()).append("Invalid byte[] value for ").append(str2).append(": ").append(str).toString());
        return null;
    }
}
