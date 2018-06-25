package org.telegram.customization.util.crypto;

import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESUtils {
    private static final String algorithm = "AES";

    private AESUtils() {
    }

    public static SecretKey generateKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
            keyGenerator.init(128);
            return keyGenerator.generateKey();
        } catch (Exception e) {
            return null;
        }
    }

    public static SecretKey createKey(String password) {
        try {
            return new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance(CommonUtils.SHA1_INSTANCE).digest(password.getBytes("UTF-8")), 16), algorithm);
        } catch (Exception e) {
            return null;
        }
    }

    public static SecretKey createKey(byte[] salt, String password) {
        try {
            return new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance(CommonUtils.SHA1_INSTANCE).digest((salt + password).getBytes("UTF-8")), 16), algorithm);
        } catch (Exception e) {
            return null;
        }
    }

    public static void writeKey(SecretKey key, File file) throws IOException {
        Throwable th;
        FileOutputStream fis = new FileOutputStream(file);
        Throwable th2 = null;
        try {
            fis.write(key.getEncoded());
            if (fis == null) {
                return;
            }
            if (th2 != null) {
                try {
                    fis.close();
                    return;
                } catch (Throwable th3) {
                    th2.addSuppressed(th3);
                    return;
                }
            }
            fis.close();
            return;
        } catch (Throwable th22) {
            Throwable th4 = th22;
            th22 = th3;
            th3 = th4;
        }
        if (fis != null) {
            if (th22 != null) {
                try {
                    fis.close();
                } catch (Throwable th5) {
                    th22.addSuppressed(th5);
                }
            } else {
                fis.close();
            }
        }
        throw th3;
        throw th3;
    }

    public static SecretKey getSecretKey(File file) throws IOException {
        return new SecretKeySpec(Files.readAllBytes(file.toPath()), algorithm);
    }

    public static byte[] encrypt(SecretKey secretKey, byte[] data) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(1, secretKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            return null;
        }
    }

    public static byte[] decrypt(String password, byte[] encrypted) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(2, createKey(password));
            return cipher.doFinal(encrypted);
        } catch (Exception e) {
            return null;
        }
    }

    public static byte[] decrypt(SecretKey secretKey, byte[] encrypted) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(2, secretKey);
            return cipher.doFinal(encrypted);
        } catch (Exception e) {
            return null;
        }
    }
}
