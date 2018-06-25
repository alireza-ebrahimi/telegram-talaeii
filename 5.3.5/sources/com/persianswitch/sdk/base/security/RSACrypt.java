package com.persianswitch.sdk.base.security;

import android.content.Context;
import android.util.Base64;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import javax.crypto.Cipher;

public class RSACrypt {
    private static final String TAG = "RSACrypt";
    private final PublicKey publicKey;

    private static PublicKey getPublicKey(Context context) throws IOException, CertificateException {
        DataInputStream dis = new DataInputStream(context.getResources().getAssets().open("public_key.crt"));
        byte[] keyBytes = new byte[dis.available()];
        dis.readFully(keyBytes);
        dis.close();
        return CertificateFactory.getInstance("X509").generateCertificate(new ByteArrayInputStream(keyBytes)).getPublicKey();
    }

    protected RSACrypt(Context context) {
        try {
            this.publicKey = getPublicKey(context);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public String rsaEncrypt(byte[] plain) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(1, this.publicKey);
        return Base64.encodeToString(cipher.doFinal(plain), 2);
    }
}
