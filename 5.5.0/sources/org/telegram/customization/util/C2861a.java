package org.telegram.customization.util;

import android.util.Base64;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.telegram.messenger.exoplayer2.C3446C;

/* renamed from: org.telegram.customization.util.a */
public class C2861a {
    /* renamed from: a */
    private Cipher f9468a;
    /* renamed from: b */
    private Cipher f9469b;

    public C2861a(String str, String str2, String str3) {
        this.f9468a = m13321a(1, str, str2, str3);
        this.f9469b = m13321a(2, str, str2, str3);
    }

    /* renamed from: a */
    private Key m13320a(String str, String str2) {
        return new SecretKeySpec(SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(new PBEKeySpec(str2.toCharArray(), m13322c(str), 1000, 128)).getEncoded(), "AES");
    }

    /* renamed from: a */
    private Cipher m13321a(int i, String str, String str2, String str3) {
        Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
        instance.init(i, m13320a(str2, str3), new IvParameterSpec(m13322c(str)));
        return instance;
    }

    /* renamed from: c */
    private byte[] m13322c(String str) {
        return str.getBytes(C3446C.UTF8_NAME);
    }

    /* renamed from: a */
    public byte[] m13323a(String str) {
        try {
            return this.f9468a.doFinal(m13322c(str));
        } catch (Throwable th) {
            RuntimeException runtimeException = new RuntimeException(th);
        }
    }

    /* renamed from: b */
    public String m13324b(String str) {
        return new String(this.f9469b.doFinal(Base64.decode(m13322c(str), 0)));
    }
}
