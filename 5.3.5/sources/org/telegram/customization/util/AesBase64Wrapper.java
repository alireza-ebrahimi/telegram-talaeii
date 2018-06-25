package org.telegram.customization.util;

import android.util.Base64;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class AesBase64Wrapper {
    private Cipher decryptor;
    private Cipher encryptor;

    public AesBase64Wrapper(String iv, String salt, String password) throws Exception {
        this.encryptor = getCipher(1, iv, salt, password);
        this.decryptor = getCipher(2, iv, salt, password);
    }

    public byte[] encryptAndEncode(String raw) {
        try {
            return this.encryptor.doFinal(getBytes(raw));
        } catch (Throwable t) {
            RuntimeException runtimeException = new RuntimeException(t);
        }
    }

    public String decodeAndDecrypt(String encrypted) throws Exception {
        return new String(this.decryptor.doFinal(Base64.decode(getBytes(encrypted), 0)));
    }

    private String getString(byte[] bytes) throws UnsupportedEncodingException {
        return new String(bytes, "UTF-8");
    }

    private byte[] getBytes(String str) throws UnsupportedEncodingException {
        return str.getBytes("UTF-8");
    }

    private Cipher getCipher(int mode, String IV, String salt, String password) throws Exception {
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(mode, generateKey(salt, password), new IvParameterSpec(getBytes(IV)));
        return c;
    }

    private Key generateKey(String SALT, String PASSWORD) throws Exception {
        return new SecretKeySpec(SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(new PBEKeySpec(PASSWORD.toCharArray(), getBytes(SALT), 1000, 128)).getEncoded(), "AES");
    }
}
