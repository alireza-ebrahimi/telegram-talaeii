package com.persianswitch.sdk.base.security;

import android.util.Base64;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.telegram.messenger.exoplayer2.C3446C;

public class AESCrypt {
    /* renamed from: a */
    private final SecretKeySpec f7082a;
    /* renamed from: b */
    private AlgorithmParameterSpec f7083b;

    protected AESCrypt(byte[] bArr, byte[] bArr2) {
        this.f7082a = new SecretKeySpec(bArr, "AES");
        this.f7083b = new IvParameterSpec(bArr2);
    }

    /* renamed from: a */
    public String m10708a(String str) {
        Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
        instance.init(1, this.f7082a, this.f7083b);
        return new String(Base64.encode(instance.doFinal(str.getBytes(C3446C.UTF8_NAME)), 2), C3446C.UTF8_NAME);
    }

    /* renamed from: b */
    public String m10709b(String str) {
        Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
        instance.init(2, this.f7082a, this.f7083b);
        return new String(instance.doFinal(Base64.decode(str, 2)), C3446C.UTF8_NAME);
    }
}
