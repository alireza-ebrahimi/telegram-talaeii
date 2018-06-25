package com.google.firebase.iid;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import com.google.android.gms.common.util.zzx;
import java.io.File;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

final class zzaa {
    private Context zzaiq;
    private SharedPreferences zzioc;

    public zzaa(Context context) {
        this(context, "com.google.android.gms.appid");
    }

    private zzaa(Context context, String str) {
        this.zzaiq = context;
        this.zzioc = context.getSharedPreferences(str, 0);
        String valueOf = String.valueOf(str);
        String valueOf2 = String.valueOf("-no-backup");
        File file = new File(zzx.getNoBackupFilesDir(this.zzaiq), valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
        if (!file.exists()) {
            try {
                if (file.createNewFile() && !isEmpty()) {
                    Log.i("FirebaseInstanceId", "App restored, clearing state");
                    zzawz();
                    FirebaseInstanceId.getInstance().zzclg();
                }
            } catch (IOException e) {
                if (Log.isLoggable("FirebaseInstanceId", 3)) {
                    valueOf = "FirebaseInstanceId";
                    String str2 = "Error creating file in no backup dir: ";
                    valueOf2 = String.valueOf(e.getMessage());
                    Log.d(valueOf, valueOf2.length() != 0 ? str2.concat(valueOf2) : new String(str2));
                }
            }
        }
    }

    private final synchronized boolean isEmpty() {
        return this.zzioc.getAll().isEmpty();
    }

    private static String zzbk(String str, String str2) {
        return new StringBuilder((String.valueOf(str).length() + 3) + String.valueOf(str2).length()).append(str).append("|S|").append(str2).toString();
    }

    private static String zzp(String str, String str2, String str3) {
        return new StringBuilder(((String.valueOf(str).length() + 4) + String.valueOf(str2).length()) + String.valueOf(str3).length()).append(str).append("|T|").append(str2).append("|").append(str3).toString();
    }

    public final synchronized void zza(String str, String str2, String str3, String str4, String str5) {
        String zzc = zzab.zzc(str4, str5, System.currentTimeMillis());
        if (zzc != null) {
            Editor edit = this.zzioc.edit();
            edit.putString(zzp(str, str2, str3), zzc);
            edit.commit();
        }
    }

    public final synchronized void zzawz() {
        this.zzioc.edit().clear().commit();
    }

    @Nullable
    public final synchronized String zzcls() {
        String str = null;
        synchronized (this) {
            String string = this.zzioc.getString("topic_operaion_queue", null);
            if (string != null) {
                String[] split = string.split(",");
                if (split.length > 1 && !TextUtils.isEmpty(split[1])) {
                    str = split[1];
                }
            }
        }
        return str;
    }

    public final synchronized void zzg(String str, String str2, String str3) {
        String zzp = zzp(str, str2, str3);
        Editor edit = this.zzioc.edit();
        edit.remove(zzp);
        edit.commit();
    }

    public final synchronized zzab zzq(String str, String str2, String str3) {
        return zzab.zzrt(this.zzioc.getString(zzp(str, str2, str3), null));
    }

    public final synchronized void zzrl(String str) {
        String string = this.zzioc.getString("topic_operaion_queue", "");
        this.zzioc.edit().putString("topic_operaion_queue", new StringBuilder((String.valueOf(string).length() + 1) + String.valueOf(str).length()).append(string).append(",").append(str).toString()).apply();
    }

    public final synchronized boolean zzro(String str) {
        boolean z;
        String string = this.zzioc.getString("topic_operaion_queue", "");
        String valueOf = String.valueOf(",");
        String valueOf2 = String.valueOf(str);
        if (string.startsWith(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf))) {
            valueOf = String.valueOf(",");
            valueOf2 = String.valueOf(str);
            this.zzioc.edit().putString("topic_operaion_queue", string.substring((valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf)).length())).apply();
            z = true;
        } else {
            z = false;
        }
        return z;
    }

    public final synchronized long zzrp(String str) {
        long parseLong;
        String string = this.zzioc.getString(zzbk(str, "cre"), null);
        if (string != null) {
            try {
                parseLong = Long.parseLong(string);
            } catch (NumberFormatException e) {
            }
        }
        parseLong = 0;
        return parseLong;
    }

    final synchronized KeyPair zzrq(String str) {
        KeyPair zzawn;
        zzawn = zza.zzawn();
        long currentTimeMillis = System.currentTimeMillis();
        Editor edit = this.zzioc.edit();
        edit.putString(zzbk(str, "|P|"), Base64.encodeToString(zzawn.getPublic().getEncoded(), 11));
        edit.putString(zzbk(str, "|K|"), Base64.encodeToString(zzawn.getPrivate().getEncoded(), 11));
        edit.putString(zzbk(str, "cre"), Long.toString(currentTimeMillis));
        edit.commit();
        return zzawn;
    }

    public final synchronized void zzrr(String str) {
        String concat = String.valueOf(str).concat("|T|");
        Editor edit = this.zzioc.edit();
        for (String str2 : this.zzioc.getAll().keySet()) {
            if (str2.startsWith(concat)) {
                edit.remove(str2);
            }
        }
        edit.commit();
    }

    public final synchronized KeyPair zzrs(String str) {
        KeyPair keyPair;
        Object e;
        String string = this.zzioc.getString(zzbk(str, "|P|"), null);
        String string2 = this.zzioc.getString(zzbk(str, "|K|"), null);
        if (string == null || string2 == null) {
            keyPair = null;
        } else {
            try {
                byte[] decode = Base64.decode(string, 8);
                byte[] decode2 = Base64.decode(string2, 8);
                KeyFactory instance = KeyFactory.getInstance("RSA");
                keyPair = new KeyPair(instance.generatePublic(new X509EncodedKeySpec(decode)), instance.generatePrivate(new PKCS8EncodedKeySpec(decode2)));
            } catch (InvalidKeySpecException e2) {
                e = e2;
                string = String.valueOf(e);
                Log.w("FirebaseInstanceId", new StringBuilder(String.valueOf(string).length() + 19).append("Invalid key stored ").append(string).toString());
                FirebaseInstanceId.getInstance().zzclg();
                keyPair = null;
                return keyPair;
            } catch (NoSuchAlgorithmException e3) {
                e = e3;
                string = String.valueOf(e);
                Log.w("FirebaseInstanceId", new StringBuilder(String.valueOf(string).length() + 19).append("Invalid key stored ").append(string).toString());
                FirebaseInstanceId.getInstance().zzclg();
                keyPair = null;
                return keyPair;
            }
        }
        return keyPair;
    }
}
