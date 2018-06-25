package com.google.android.gms.iid;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.annotation.GuardedBy;
import android.support.v4.content.C0235a;
import android.support.v4.p022f.C0464a;
import android.util.Log;
import com.google.android.gms.common.util.VisibleForTesting;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public final class zzak {
    private SharedPreferences zzcz;
    private final zzn zzda;
    @GuardedBy("this")
    private final Map<String, zzo> zzdb;
    private Context zzk;

    public zzak(Context context) {
        this(context, new zzn());
    }

    @VisibleForTesting
    private zzak(Context context, zzn zzn) {
        this.zzdb = new C0464a();
        this.zzk = context;
        this.zzcz = context.getSharedPreferences("com.google.android.gms.appid", 0);
        this.zzda = zzn;
        File file = new File(C0235a.m1074b(this.zzk), "com.google.android.gms.appid-no-backup");
        if (!file.exists()) {
            try {
                if (file.createNewFile() && !isEmpty()) {
                    Log.i("InstanceID/Store", "App restored, clearing state");
                    InstanceIDListenerService.zzd(this.zzk, this);
                }
            } catch (IOException e) {
                if (Log.isLoggable("InstanceID/Store", 3)) {
                    String str = "InstanceID/Store";
                    String str2 = "Error creating file in no backup dir: ";
                    String valueOf = String.valueOf(e.getMessage());
                    Log.d(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
                }
            }
        }
    }

    private static String zzd(String str, String str2, String str3) {
        return new StringBuilder(((String.valueOf(str).length() + 4) + String.valueOf(str2).length()) + String.valueOf(str3).length()).append(str).append("|T|").append(str2).append("|").append(str3).toString();
    }

    static String zzh(String str, String str2) {
        return new StringBuilder((String.valueOf(str).length() + 3) + String.valueOf(str2).length()).append(str).append("|S|").append(str2).toString();
    }

    final synchronized String get(String str) {
        return this.zzcz.getString(str, null);
    }

    public final boolean isEmpty() {
        return this.zzcz.getAll().isEmpty();
    }

    public final synchronized void zzd(String str, String str2, String str3, String str4, String str5) {
        String zzd = zzd(str, str2, str3);
        Editor edit = this.zzcz.edit();
        edit.putString(zzd, str4);
        edit.putString("appVersion", str5);
        edit.putString("lastToken", Long.toString(System.currentTimeMillis() / 1000));
        edit.commit();
    }

    public final synchronized String zze(String str, String str2, String str3) {
        return this.zzcz.getString(zzd(str, str2, str3), null);
    }

    public final synchronized void zzf(String str, String str2, String str3) {
        String zzd = zzd(str, str2, str3);
        Editor edit = this.zzcz.edit();
        edit.remove(zzd);
        edit.commit();
    }

    public final synchronized void zzi(String str) {
        Editor edit = this.zzcz.edit();
        for (String str2 : this.zzcz.getAll().keySet()) {
            if (str2.startsWith(str)) {
                edit.remove(str2);
            }
        }
        edit.commit();
    }

    public final synchronized zzo zzj(String str) {
        zzo zzo;
        zzo = (zzo) this.zzdb.get(str);
        if (zzo == null) {
            try {
                zzo = this.zzda.zze(this.zzk, str);
            } catch (zzp e) {
                Log.w("InstanceID/Store", "Stored data is corrupt, generating new identity");
                InstanceIDListenerService.zzd(this.zzk, this);
                zzo = this.zzda.zzf(this.zzk, str);
            }
            this.zzdb.put(str, zzo);
        }
        return zzo;
    }

    final void zzk(String str) {
        synchronized (this) {
            this.zzdb.remove(str);
        }
        zzn.zzg(this.zzk, str);
        zzi(String.valueOf(str).concat("|"));
    }

    public final synchronized void zzx() {
        this.zzdb.clear();
        zzn.zzi(this.zzk);
        this.zzcz.edit().clear().commit();
    }
}
