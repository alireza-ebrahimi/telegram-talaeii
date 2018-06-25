package com.google.firebase.iid;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.content.C0235a;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import com.google.android.gms.internal.firebase_messaging.zzh;
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

final class am {
    am() {
    }

    /* renamed from: a */
    private static an m8805a(SharedPreferences sharedPreferences, String str) {
        String string = sharedPreferences.getString(C1946r.m8875a(str, "|P|"), null);
        String string2 = sharedPreferences.getString(C1946r.m8875a(str, "|K|"), null);
        return (string == null || string2 == null) ? null : new an(m8807a(string, string2), m8812b(sharedPreferences, str));
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    /* renamed from: a */
    private static com.google.firebase.iid.an m8806a(java.io.File r7) {
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
        m8810a(r1, r2);
        r0 = r1;
    L_0x0024:
        return r0;
    L_0x0025:
        r3 = m8807a(r3, r4);	 Catch:{ Throwable -> 0x0044, all -> 0x004e }
        r4 = "cre";
        r0 = r0.getProperty(r4);	 Catch:{ NumberFormatException -> 0x003d }
        r4 = java.lang.Long.parseLong(r0);	 Catch:{ NumberFormatException -> 0x003d }
        r0 = new com.google.firebase.iid.an;	 Catch:{ Throwable -> 0x0044, all -> 0x004e }
        r0.<init>(r3, r4);	 Catch:{ Throwable -> 0x0044, all -> 0x004e }
        m8810a(r1, r2);
        goto L_0x0024;
    L_0x003d:
        r0 = move-exception;
        r3 = new com.google.firebase.iid.ao;	 Catch:{ Throwable -> 0x0044, all -> 0x004e }
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
        m8810a(r1, r2);
        throw r0;
    L_0x004e:
        r0 = move-exception;
        goto L_0x004a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.iid.am.a(java.io.File):com.google.firebase.iid.an");
    }

    /* renamed from: a */
    private static KeyPair m8807a(String str, String str2) {
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
                Log.w("FirebaseInstanceId", new StringBuilder(String.valueOf(valueOf).length() + 19).append("Invalid key stored ").append(valueOf).toString());
                throw new ao(e);
            } catch (NoSuchAlgorithmException e3) {
                e = e3;
                valueOf = String.valueOf(e);
                Log.w("FirebaseInstanceId", new StringBuilder(String.valueOf(valueOf).length() + 19).append("Invalid key stored ").append(valueOf).toString());
                throw new ao(e);
            }
        } catch (Exception e4) {
            throw new ao(e4);
        }
    }

    /* renamed from: a */
    static void m8808a(Context context) {
        for (File file : m8813b(context).listFiles()) {
            if (file.getName().startsWith("com.google.InstanceId")) {
                file.delete();
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    /* renamed from: a */
    private static void m8809a(android.content.Context r6, java.lang.String r7, com.google.firebase.iid.an r8) {
        /*
        r1 = 0;
        r0 = "FirebaseInstanceId";
        r2 = 3;
        r0 = android.util.Log.isLoggable(r0, r2);	 Catch:{ IOException -> 0x0057 }
        if (r0 == 0) goto L_0x0014;
    L_0x000b:
        r0 = "FirebaseInstanceId";
        r2 = "Writing key to properties file";
        android.util.Log.d(r0, r2);	 Catch:{ IOException -> 0x0057 }
    L_0x0014:
        r0 = m8817e(r6, r7);	 Catch:{ IOException -> 0x0057 }
        r0.createNewFile();	 Catch:{ IOException -> 0x0057 }
        r2 = new java.util.Properties;	 Catch:{ IOException -> 0x0057 }
        r2.<init>();	 Catch:{ IOException -> 0x0057 }
        r3 = "pub";
        r4 = r8.m8821b();	 Catch:{ IOException -> 0x0057 }
        r2.setProperty(r3, r4);	 Catch:{ IOException -> 0x0057 }
        r3 = "pri";
        r4 = r8.m8824c();	 Catch:{ IOException -> 0x0057 }
        r2.setProperty(r3, r4);	 Catch:{ IOException -> 0x0057 }
        r3 = "cre";
        r4 = r8.f5701b;	 Catch:{ IOException -> 0x0057 }
        r4 = java.lang.String.valueOf(r4);	 Catch:{ IOException -> 0x0057 }
        r2.setProperty(r3, r4);	 Catch:{ IOException -> 0x0057 }
        r3 = new java.io.FileOutputStream;	 Catch:{ IOException -> 0x0057 }
        r3.<init>(r0);	 Catch:{ IOException -> 0x0057 }
        r0 = 0;
        r2.store(r3, r0);	 Catch:{ Throwable -> 0x0050 }
        r0 = 0;
        m8811a(r0, r3);	 Catch:{ IOException -> 0x0057 }
    L_0x004f:
        return;
    L_0x0050:
        r1 = move-exception;
        throw r1;	 Catch:{ all -> 0x0052 }
    L_0x0052:
        r0 = move-exception;
        m8811a(r1, r3);	 Catch:{ IOException -> 0x0057 }
        throw r0;	 Catch:{ IOException -> 0x0057 }
    L_0x0057:
        r0 = move-exception;
        r1 = "FirebaseInstanceId";
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
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.iid.am.a(android.content.Context, java.lang.String, com.google.firebase.iid.an):void");
    }

    /* renamed from: a */
    private static /* synthetic */ void m8810a(Throwable th, FileInputStream fileInputStream) {
        if (th != null) {
            try {
                fileInputStream.close();
                return;
            } catch (Throwable th2) {
                zzh.zza(th, th2);
                return;
            }
        }
        fileInputStream.close();
    }

    /* renamed from: a */
    private static /* synthetic */ void m8811a(Throwable th, FileOutputStream fileOutputStream) {
        if (th != null) {
            try {
                fileOutputStream.close();
                return;
            } catch (Throwable th2) {
                zzh.zza(th, th2);
                return;
            }
        }
        fileOutputStream.close();
    }

    /* renamed from: b */
    private static long m8812b(SharedPreferences sharedPreferences, String str) {
        String string = sharedPreferences.getString(C1946r.m8875a(str, "cre"), null);
        if (string != null) {
            try {
                return Long.parseLong(string);
            } catch (NumberFormatException e) {
            }
        }
        return 0;
    }

    /* renamed from: b */
    private static File m8813b(Context context) {
        File b = C0235a.m1074b(context);
        if (b != null && b.isDirectory()) {
            return b;
        }
        Log.w("FirebaseInstanceId", "noBackupFilesDir doesn't exist, using regular files directory instead");
        return context.getFilesDir();
    }

    /* renamed from: b */
    private final void m8814b(Context context, String str, an anVar) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.google.android.gms.appid", 0);
        try {
            if (anVar.equals(m8805a(sharedPreferences, str))) {
                return;
            }
        } catch (ao e) {
        }
        if (Log.isLoggable("FirebaseInstanceId", 3)) {
            Log.d("FirebaseInstanceId", "Writing key to shared preferences");
        }
        Editor edit = sharedPreferences.edit();
        edit.putString(C1946r.m8875a(str, "|P|"), anVar.m8821b());
        edit.putString(C1946r.m8875a(str, "|K|"), anVar.m8824c());
        edit.putString(C1946r.m8875a(str, "cre"), String.valueOf(anVar.f5701b));
        edit.commit();
    }

    /* renamed from: c */
    private final an m8815c(Context context, String str) {
        ao aoVar;
        ao aoVar2;
        try {
            an d = m8816d(context, str);
            if (d != null) {
                m8814b(context, str, d);
                return d;
            }
            aoVar = null;
            try {
                d = m8805a(context.getSharedPreferences("com.google.android.gms.appid", 0), str);
                if (d != null) {
                    m8809a(context, str, d);
                    return d;
                }
                aoVar2 = aoVar;
                if (aoVar2 == null) {
                    return null;
                }
                throw aoVar2;
            } catch (ao e) {
                aoVar2 = e;
            }
        } catch (ao aoVar22) {
            aoVar = aoVar22;
        }
    }

    /* renamed from: d */
    private final an m8816d(Context context, String str) {
        File e = m8817e(context, str);
        if (!e.exists()) {
            return null;
        }
        try {
            return m8806a(e);
        } catch (IOException e2) {
            if (Log.isLoggable("FirebaseInstanceId", 3)) {
                String valueOf = String.valueOf(e2);
                Log.d("FirebaseInstanceId", new StringBuilder(String.valueOf(valueOf).length() + 40).append("Failed to read key from file, retrying: ").append(valueOf).toString());
            }
            try {
                return m8806a(e);
            } catch (Exception e3) {
                String valueOf2 = String.valueOf(e3);
                Log.w("FirebaseInstanceId", new StringBuilder(String.valueOf(valueOf2).length() + 45).append("IID file exists, but failed to read from it: ").append(valueOf2).toString());
                throw new ao(e3);
            }
        }
    }

    /* renamed from: e */
    private static File m8817e(Context context, String str) {
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
        return new File(m8813b(context), str2);
    }

    /* renamed from: a */
    final an m8818a(Context context, String str) {
        an c = m8815c(context, str);
        return c != null ? c : m8819b(context, str);
    }

    /* renamed from: b */
    final an m8819b(Context context, String str) {
        an anVar = new an(C1929a.m8792a(), System.currentTimeMillis());
        try {
            an c = m8815c(context, str);
            if (c != null) {
                if (!Log.isLoggable("FirebaseInstanceId", 3)) {
                    return c;
                }
                Log.d("FirebaseInstanceId", "Loaded key after generating new one, using loaded one");
                return c;
            }
        } catch (ao e) {
        }
        if (Log.isLoggable("FirebaseInstanceId", 3)) {
            Log.d("FirebaseInstanceId", "Generated new key");
        }
        m8809a(context, str, anVar);
        m8814b(context, str, anVar);
        return anVar;
    }
}
