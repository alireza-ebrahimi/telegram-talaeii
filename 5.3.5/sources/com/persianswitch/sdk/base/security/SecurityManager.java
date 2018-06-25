package com.persianswitch.sdk.base.security;

import android.content.Context;
import android.util.Base64;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Locale;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;

public class SecurityManager {
    private static final int Encryption_Ver = 2;
    private static final String TAG = "SecurityManager";
    private static SecurityManager securityManager = null;
    private final RSACrypt rsaCrypt;

    public static SecurityManager getInstance(Context context) {
        if (securityManager == null) {
            securityManager = new SecurityManager(context);
        }
        return securityManager;
    }

    private SecurityManager(Context context) {
        this.rsaCrypt = new RSACrypt(context);
    }

    public String encodeRequest(String body, byte[] myAESKey) throws Exception {
        byte[] iv = new byte[16];
        SecureRandom.getInstance("SHA1PRNG").nextBytes(iv);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        AESCrypt aesCrypt = new AESCrypt(myAESKey, iv);
        String encryptedAESKey = this.rsaCrypt.rsaEncrypt(myAESKey);
        return "2#" + encryptedAESKey.trim() + "#" + Base64.encodeToString(iv, 2) + "#" + aesCrypt.encrypt(body);
    }

    public String decodeResponse(String response, byte[] aesKey) throws Exception {
        String encodedIV = response.split("#")[1];
        return new AESCrypt(aesKey, Base64.decode(encodedIV, 2)).decrypt(response.split("#")[2]);
    }

    public byte[] generateAesKey() throws NoSuchAlgorithmException, NoSuchProviderException {
        byte[] seed = generateAesSeed();
        KeyGenerator kGen = KeyGenerator.getInstance("AES");
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        sr.setSeed(seed);
        kGen.init(128, sr);
        return kGen.generateKey().getEncoded();
    }

    private static byte[] generateAesSeed() {
        byte[] aesKeySeed = new byte[16];
        new SecureRandom().nextBytes(aesKeySeed);
        Calendar cal = Calendar.getInstance();
        String millisecond = String.format(Locale.US, "%2d", new Object[]{Integer.valueOf(cal.get(14))});
        String second = String.format(Locale.US, "%3d", new Object[]{Integer.valueOf(cal.get(13))});
        byte[] timeByteArray = (second + (millisecond + second)).getBytes();
        System.arraycopy(timeByteArray, 0, aesKeySeed, 0, timeByteArray.length);
        return aesKeySeed;
    }
}
