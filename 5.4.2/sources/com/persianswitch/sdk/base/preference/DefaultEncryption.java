package com.persianswitch.sdk.base.preference;

import android.util.Base64;
import com.persianswitch.sdk.base.log.SDKLog;
import com.persianswitch.sdk.base.utils.strings.StringUtils;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.telegram.messenger.exoplayer2.C3446C;

class DefaultEncryption implements Encryption {
    /* renamed from: a */
    private final byte[] f7078a;
    /* renamed from: b */
    private final int f7079b = 10000;
    /* renamed from: c */
    private final int f7080c = 128;

    DefaultEncryption(String str, String str2) {
        if (StringUtils.m10803a(str) || StringUtils.m10803a(str2)) {
            throw new RuntimeException("secure token & salt can not be empty!");
        }
        byte[] decode = Base64.decode(str2, 2);
        byte[] bArr = new byte[128];
        try {
            bArr = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(new PBEKeySpec(str.toCharArray(), decode, 10000, 128)).getEncoded();
        } catch (Exception e) {
            SDKLog.m10661c("DefaultEncryption", "PBKDF2WithHmacSHA1 not found use sha1 instead", new Object[0]);
            try {
                MessageDigest instance = MessageDigest.getInstance("SHA-1");
                instance.update(decode);
                bArr = Arrays.copyOf(instance.digest(str.getBytes(C3446C.UTF8_NAME)), 16);
            } catch (Exception e2) {
            }
        }
        this.f7078a = bArr;
    }

    /* renamed from: a */
    public String mo3250a(String str) {
        try {
            Key secretKeySpec = new SecretKeySpec(this.f7078a, "AES");
            Cipher instance = Cipher.getInstance("AES/ECB/PKCS5Padding");
            instance.init(1, secretKeySpec);
            str = Base64.encodeToString(instance.doFinal(str.getBytes(C3446C.UTF8_NAME)), 2);
        } catch (Exception e) {
        }
        return str;
    }

    /* renamed from: b */
    public String mo3251b(String str) {
        try {
            Key secretKeySpec = new SecretKeySpec(this.f7078a, "AES");
            Cipher instance = Cipher.getInstance("AES/ECB/PKCS5Padding");
            instance.init(2, secretKeySpec);
            return new String(instance.doFinal(Base64.decode(str.getBytes(C3446C.UTF8_NAME), 2)), C3446C.UTF8_NAME);
        } catch (Exception e) {
            return str;
        }
    }
}
