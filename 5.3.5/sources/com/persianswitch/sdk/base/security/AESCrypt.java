package com.persianswitch.sdk.base.security;

import android.util.Base64;
import java.security.GeneralSecurityException;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESCrypt {
    private final SecretKeySpec key;
    private AlgorithmParameterSpec spec;

    protected AESCrypt(byte[] rawKey, byte[] iv) throws GeneralSecurityException {
        this.key = new SecretKeySpec(rawKey, "AES");
        this.spec = new IvParameterSpec(iv);
    }

    public String encrypt(String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(1, this.key, this.spec);
        return new String(Base64.encode(cipher.doFinal(plainText.getBytes("UTF-8")), 2), "UTF-8");
    }

    public String decrypt(String cryptedText) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(2, this.key, this.spec);
        return new String(cipher.doFinal(Base64.decode(cryptedText, 2)), "UTF-8");
    }
}
