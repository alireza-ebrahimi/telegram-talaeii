package com.persianswitch.sdk.base.security;

import android.content.Context;
import android.util.Base64;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import javax.crypto.Cipher;

public class RSACrypt {
    /* renamed from: a */
    private final PublicKey f7097a;

    protected RSACrypt(Context context) {
        try {
            this.f7097a = m10726a(context);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    /* renamed from: a */
    private static PublicKey m10726a(Context context) {
        DataInputStream dataInputStream = new DataInputStream(context.getResources().getAssets().open("public_key.crt"));
        byte[] bArr = new byte[dataInputStream.available()];
        dataInputStream.readFully(bArr);
        dataInputStream.close();
        return CertificateFactory.getInstance("X509").generateCertificate(new ByteArrayInputStream(bArr)).getPublicKey();
    }

    /* renamed from: a */
    public String m10727a(byte[] bArr) {
        Cipher instance = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        instance.init(1, this.f7097a);
        return Base64.encodeToString(instance.doFinal(bArr), 2);
    }
}
