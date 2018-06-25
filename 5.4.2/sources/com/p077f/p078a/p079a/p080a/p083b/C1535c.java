package com.p077f.p078a.p079a.p080a.p083b;

import com.p077f.p078a.p095c.C1602c;
import java.math.BigInteger;
import java.security.MessageDigest;

/* renamed from: com.f.a.a.a.b.c */
public class C1535c implements C1533a {
    /* renamed from: a */
    private byte[] m7633a(byte[] bArr) {
        byte[] bArr2 = null;
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(bArr);
            bArr2 = instance.digest();
        } catch (Throwable e) {
            C1602c.m7937a(e);
        }
        return bArr2;
    }

    /* renamed from: a */
    public String mo1216a(String str) {
        return new BigInteger(m7633a(str.getBytes())).abs().toString(36);
    }
}
