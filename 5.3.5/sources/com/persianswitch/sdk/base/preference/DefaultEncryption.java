package com.persianswitch.sdk.base.preference;

import android.util.Base64;
import com.persianswitch.sdk.base.log.SDKLog;
import com.persianswitch.sdk.base.utils.strings.StringUtils;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

class DefaultEncryption implements Encryption {
    private static final String TAG = "DefaultEncryption";
    private final byte[] mAESKey;
    private final int mIterationTimes = 10000;
    private final int mKeyLength = 128;

    DefaultEncryption(String password, String saltAsBase64) {
        if (StringUtils.isEmpty(password) || StringUtils.isEmpty(saltAsBase64)) {
            throw new RuntimeException("secure token & salt can not be empty!");
        }
        byte[] saltBytes = Base64.decode(saltAsBase64, 2);
        byte[] aesKey = new byte[128];
        try {
            aesKey = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(new PBEKeySpec(password.toCharArray(), saltBytes, 10000, 128)).getEncoded();
        } catch (Exception e) {
            SDKLog.m37e(TAG, "PBKDF2WithHmacSHA1 not found use sha1 instead", new Object[0]);
            try {
                MessageDigest md = MessageDigest.getInstance(CommonUtils.SHA1_INSTANCE);
                md.update(saltBytes);
                aesKey = Arrays.copyOf(md.digest(password.getBytes("UTF-8")), 16);
            } catch (Exception e2) {
            }
        }
        this.mAESKey = aesKey;
    }

    String generateSalt() {
        byte[] salt = new byte[16];
        new SecureRandom(new SecureRandom().generateSeed(16)).nextBytes(salt);
        return Base64.encodeToString(salt, 2);
    }

    public String encrypt(String plain) {
        try {
            SecretKeySpec key = new SecretKeySpec(this.mAESKey, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(1, key);
            plain = Base64.encodeToString(cipher.doFinal(plain.getBytes("UTF-8")), 2);
        } catch (Exception e) {
        }
        return plain;
    }

    public String decrypt(String encrypted) {
        try {
            SecretKeySpec key = new SecretKeySpec(this.mAESKey, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(2, key);
            return new String(cipher.doFinal(Base64.decode(encrypted.getBytes("UTF-8"), 2)), "UTF-8");
        } catch (Exception e) {
            return encrypted;
        }
    }
}
