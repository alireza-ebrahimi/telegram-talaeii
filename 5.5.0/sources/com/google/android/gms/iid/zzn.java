package com.google.android.gms.iid;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.content.C0235a;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import com.google.android.gms.internal.gcm.zzf;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import org.telegram.messenger.exoplayer2.C3446C;

final class zzn {
    zzn() {
    }

    private static zzo zzd(SharedPreferences sharedPreferences, String str) {
        String string = sharedPreferences.getString(zzak.zzh(str, "|P|"), null);
        String string2 = sharedPreferences.getString(zzak.zzh(str, "|K|"), null);
        return (string == null || string2 == null) ? null : new zzo(zzg(string, string2), zze(sharedPreferences, str));
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.google.android.gms.iid.zzo zzd(java.io.File r7) {
        /*
        r1 = 0;
        r2 = new java.io.FileInputStream;
        r2.<init>(r7);
        r0 = new java.util.Properties;	 Catch:{ Throwable -> 0x0044, all -> 0x004e }
        r0.<init>();	 Catch:{ Throwable -> 0x0044, all -> 0x004e }
        r0.load(r2);	 Catch:{ Throwable -> 0x0044, all -> 0x004e }
        r3 = "pub";
        r3 = r0.getProperty(r3);	 Catch:{ Throwable -> 0x0044, all -> 0x004e }
        r4 = "pri";
        r4 = r0.getProperty(r4);	 Catch:{ Throwable -> 0x0044, all -> 0x004e }
        if (r3 == 0) goto L_0x0020;
    L_0x001e:
        if (r4 != 0) goto L_0x0025;
    L_0x0020:
        zzd(r1, r2);
        r0 = r1;
    L_0x0024:
        return r0;
    L_0x0025:
        r3 = zzg(r3, r4);	 Catch:{ Throwable -> 0x0044, all -> 0x004e }
        r4 = "cre";
        r0 = r0.getProperty(r4);	 Catch:{ NumberFormatException -> 0x003d }
        r4 = java.lang.Long.parseLong(r0);	 Catch:{ NumberFormatException -> 0x003d }
        r0 = new com.google.android.gms.iid.zzo;	 Catch:{ Throwable -> 0x0044, all -> 0x004e }
        r0.<init>(r3, r4);	 Catch:{ Throwable -> 0x0044, all -> 0x004e }
        zzd(r1, r2);
        goto L_0x0024;
    L_0x003d:
        r0 = move-exception;
        r3 = new com.google.android.gms.iid.zzp;	 Catch:{ Throwable -> 0x0044, all -> 0x004e }
        r3.<init>(r0);	 Catch:{ Throwable -> 0x0044, all -> 0x004e }
        throw r3;	 Catch:{ Throwable -> 0x0044, all -> 0x004e }
    L_0x0044:
        r0 = move-exception;
        throw r0;	 Catch:{ all -> 0x0046 }
    L_0x0046:
        r1 = move-exception;
        r6 = r1;
        r1 = r0;
        r0 = r6;
    L_0x004a:
        zzd(r1, r2);
        throw r0;
    L_0x004e:
        r0 = move-exception;
        goto L_0x004a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.iid.zzn.zzd(java.io.File):com.google.android.gms.iid.zzo");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void zzd(android.content.Context r6, java.lang.String r7, com.google.android.gms.iid.zzo r8) {
        /*
        r1 = 0;
        r0 = "InstanceID";
        r2 = 3;
        r0 = android.util.Log.isLoggable(r0, r2);	 Catch:{ IOException -> 0x0057 }
        if (r0 == 0) goto L_0x0014;
    L_0x000b:
        r0 = "InstanceID";
        r2 = "Writing key to properties file";
        android.util.Log.d(r0, r2);	 Catch:{ IOException -> 0x0057 }
    L_0x0014:
        r0 = zzj(r6, r7);	 Catch:{ IOException -> 0x0057 }
        r0.createNewFile();	 Catch:{ IOException -> 0x0057 }
        r2 = new java.util.Properties;	 Catch:{ IOException -> 0x0057 }
        r2.<init>();	 Catch:{ IOException -> 0x0057 }
        r3 = "pub";
        r4 = r8.zzo();	 Catch:{ IOException -> 0x0057 }
        r2.setProperty(r3, r4);	 Catch:{ IOException -> 0x0057 }
        r3 = "pri";
        r4 = r8.zzp();	 Catch:{ IOException -> 0x0057 }
        r2.setProperty(r3, r4);	 Catch:{ IOException -> 0x0057 }
        r3 = "cre";
        r4 = r8.zzbx;	 Catch:{ IOException -> 0x0057 }
        r4 = java.lang.String.valueOf(r4);	 Catch:{ IOException -> 0x0057 }
        r2.setProperty(r3, r4);	 Catch:{ IOException -> 0x0057 }
        r3 = new java.io.FileOutputStream;	 Catch:{ IOException -> 0x0057 }
        r3.<init>(r0);	 Catch:{ IOException -> 0x0057 }
        r0 = 0;
        r2.store(r3, r0);	 Catch:{ Throwable -> 0x0050 }
        r0 = 0;
        zzd(r0, r3);	 Catch:{ IOException -> 0x0057 }
    L_0x004f:
        return;
    L_0x0050:
        r1 = move-exception;
        throw r1;	 Catch:{ all -> 0x0052 }
    L_0x0052:
        r0 = move-exception;
        zzd(r1, r3);	 Catch:{ IOException -> 0x0057 }
        throw r0;	 Catch:{ IOException -> 0x0057 }
    L_0x0057:
        r0 = move-exception;
        r1 = "InstanceID";
        r0 = java.lang.String.valueOf(r0);
        r2 = java.lang.String.valueOf(r0);
        r2 = r2.length();
        r2 = r2 + 21;
        r3 = new java.lang.StringBuilder;
        r3.<init>(r2);
        r2 = "Failed to write key: ";
        r2 = r3.append(r2);
        r0 = r2.append(r0);
        r0 = r0.toString();
        android.util.Log.w(r1, r0);
        goto L_0x004f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.iid.zzn.zzd(android.content.Context, java.lang.String, com.google.android.gms.iid.zzo):void");
    }

    private static /* synthetic */ void zzd(Throwable th, FileInputStream fileInputStream) {
        if (th != null) {
            try {
                fileInputStream.close();
                return;
            } catch (Throwable th2) {
                zzf.zzd(th, th2);
                return;
            }
        }
        fileInputStream.close();
    }

    private static /* synthetic */ void zzd(Throwable th, FileOutputStream fileOutputStream) {
        if (th != null) {
            try {
                fileOutputStream.close();
                return;
            } catch (Throwable th2) {
                zzf.zzd(th, th2);
                return;
            }
        }
        fileOutputStream.close();
    }

    private static long zze(SharedPreferences sharedPreferences, String str) {
        String string = sharedPreferences.getString(zzak.zzh(str, "cre"), null);
        if (string != null) {
            try {
                return Long.parseLong(string);
            } catch (NumberFormatException e) {
            }
        }
        return 0;
    }

    private final void zze(Context context, String str, zzo zzo) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.google.android.gms.appid", 0);
        try {
            if (zzo.equals(zzd(sharedPreferences, str))) {
                return;
            }
        } catch (zzp e) {
        }
        if (Log.isLoggable("InstanceID", 3)) {
            Log.d("InstanceID", "Writing key to shared preferences");
        }
        Editor edit = sharedPreferences.edit();
        edit.putString(zzak.zzh(str, "|P|"), zzo.zzo());
        edit.putString(zzak.zzh(str, "|K|"), zzo.zzp());
        edit.putString(zzak.zzh(str, "cre"), String.valueOf(zzo.zzbx));
        edit.commit();
    }

    private static KeyPair zzg(String str, String str2) {
        Exception e;
        String valueOf;
        try {
            byte[] decode = Base64.decode(str, 8);
            byte[] decode2 = Base64.decode(str2, 8);
            try {
                KeyFactory instance = KeyFactory.getInstance("RSA");
                return new KeyPair(instance.generatePublic(new X509EncodedKeySpec(decode)), instance.generatePrivate(new PKCS8EncodedKeySpec(decode2)));
            } catch (InvalidKeySpecException e2) {
                e = e2;
                valueOf = String.valueOf(e);
                Log.w("InstanceID", new StringBuilder(String.valueOf(valueOf).length() + 19).append("Invalid key stored ").append(valueOf).toString());
                throw new zzp(e);
            } catch (NoSuchAlgorithmException e3) {
                e = e3;
                valueOf = String.valueOf(e);
                Log.w("InstanceID", new StringBuilder(String.valueOf(valueOf).length() + 19).append("Invalid key stored ").append(valueOf).toString());
                throw new zzp(e);
            }
        } catch (Exception e4) {
            throw new zzp(e4);
        }
    }

    static void zzg(Context context, String str) {
        File zzj = zzj(context, str);
        if (zzj.exists()) {
            zzj.delete();
        }
    }

    private final zzo zzh(Context context, String str) {
        zzp zzp;
        zzp zzp2;
        try {
            zzo zzi = zzi(context, str);
            if (zzi != null) {
                zze(context, str, zzi);
                return zzi;
            }
            zzp = null;
            try {
                zzi = zzd(context.getSharedPreferences("com.google.android.gms.appid", 0), str);
                if (zzi != null) {
                    zzd(context, str, zzi);
                    return zzi;
                }
                zzp2 = zzp;
                if (zzp2 == null) {
                    return null;
                }
                throw zzp2;
            } catch (zzp e) {
                zzp2 = e;
            }
        } catch (zzp zzp22) {
            zzp = zzp22;
        }
    }

    private final zzo zzi(Context context, String str) {
        File zzj = zzj(context, str);
        if (!zzj.exists()) {
            return null;
        }
        try {
            return zzd(zzj);
        } catch (IOException e) {
            if (Log.isLoggable("InstanceID", 3)) {
                String valueOf = String.valueOf(e);
                Log.d("InstanceID", new StringBuilder(String.valueOf(valueOf).length() + 40).append("Failed to read key from file, retrying: ").append(valueOf).toString());
            }
            try {
                return zzd(zzj);
            } catch (Exception e2) {
                String valueOf2 = String.valueOf(e2);
                Log.w("InstanceID", new StringBuilder(String.valueOf(valueOf2).length() + 45).append("IID file exists, but failed to read from it: ").append(valueOf2).toString());
                throw new zzp(e2);
            }
        }
    }

    static void zzi(Context context) {
        for (File file : zzj(context).listFiles()) {
            if (file.getName().startsWith("com.google.InstanceId")) {
                file.delete();
            }
        }
    }

    private static File zzj(Context context) {
        File b = C0235a.m1074b(context);
        if (b != null && b.isDirectory()) {
            return b;
        }
        Log.w("InstanceID", "noBackupFilesDir doesn't exist, using regular files directory instead");
        return context.getFilesDir();
    }

    private static File zzj(Context context, String str) {
        String str2;
        if (TextUtils.isEmpty(str)) {
            str2 = "com.google.InstanceId.properties";
        } else {
            try {
                str2 = Base64.encodeToString(str.getBytes(C3446C.UTF8_NAME), 11);
                str2 = new StringBuilder(String.valueOf(str2).length() + 33).append("com.google.InstanceId_").append(str2).append(".properties").toString();
            } catch (UnsupportedEncodingException e) {
                throw new AssertionError(e);
            }
        }
        return new File(zzj(context), str2);
    }

    final zzo zze(Context context, String str) {
        zzo zzh = zzh(context, str);
        return zzh != null ? zzh : zzf(context, str);
    }

    final zzo zzf(Context context, String str) {
        zzo zzo = new zzo(zzd.zzk(), System.currentTimeMillis());
        try {
            zzo zzh = zzh(context, str);
            if (zzh != null) {
                if (!Log.isLoggable("InstanceID", 3)) {
                    return zzh;
                }
                Log.d("InstanceID", "Loaded key after generating new one, using loaded one");
                return zzh;
            }
        } catch (zzp e) {
        }
        if (Log.isLoggable("InstanceID", 3)) {
            Log.d("InstanceID", "Generated new key");
        }
        zzd(context, str, zzo);
        zze(context, str, zzo);
        return zzo;
    }
}
