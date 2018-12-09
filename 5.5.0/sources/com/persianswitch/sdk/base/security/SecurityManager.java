package com.persianswitch.sdk.base.security;

import android.content.Context;
import android.util.Base64;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Locale;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;

public class SecurityManager {
    /* renamed from: a */
    private static SecurityManager f7098a = null;
    /* renamed from: b */
    private final RSACrypt f7099b;

    private SecurityManager(Context context) {
        this.f7099b = new RSACrypt(context);
    }

    /* renamed from: a */
    public static SecurityManager m10728a(Context context) {
        if (f7098a == null) {
            f7098a = new SecurityManager(context);
        }
        return f7098a;
    }

    /* renamed from: b */
    private static byte[] m10729b() {
        Object obj = new byte[16];
        new SecureRandom().nextBytes(obj);
        Calendar instance = Calendar.getInstance();
        String format = String.format(Locale.US, "%2d", new Object[]{Integer.valueOf(instance.get(14))});
        String format2 = String.format(Locale.US, "%3d", new Object[]{Integer.valueOf(instance.get(13))});
        Object bytes = (format2 + (format + format2)).getBytes();
        System.arraycopy(bytes, 0, obj, 0, bytes.length);
        return obj;
    }

    /* renamed from: a */
    public String m10730a(String str, byte[] bArr) {
        byte[] bArr2 = new byte[16];
        SecureRandom.getInstance("SHA1PRNG").nextBytes(bArr2);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(bArr2);
        AESCrypt aESCrypt = new AESCrypt(bArr, bArr2);
        String a = this.f7099b.m10727a(bArr);
        return "2#" + a.trim() + "#" + Base64.encodeToString(bArr2, 2) + "#" + aESCrypt.m10708a(str);
    }

    /* renamed from: a */
    public byte[] m10731a() {
        byte[] b = m10729b();
        KeyGenerator instance = KeyGenerator.getInstance("AES");
        SecureRandom instance2 = SecureRandom.getInstance("SHA1PRNG");
        instance2.setSeed(b);
        instance.init(128, instance2);
        return instance.generateKey().getEncoded();
    }

    /* renamed from: b */
    public String m10732b(String str, byte[] bArr) {
        String str2 = str.split("#")[1];
        return new AESCrypt(bArr, Base64.decode(str2, 2)).m10709b(str.split("#")[2]);
    }
}
