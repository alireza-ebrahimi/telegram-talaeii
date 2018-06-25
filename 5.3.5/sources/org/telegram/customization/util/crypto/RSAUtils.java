package org.telegram.customization.util.crypto;

import android.util.Base64;
import android.util.Log;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import utils.view.Constants;

public class RSAUtils {
    private static final String algorithm = "RSA";

    /* renamed from: org.telegram.customization.util.crypto.RSAUtils$1 */
    static class C12361 {
        /* renamed from: t */
        int f60t;

        C12361() {
        }

        public String toString() {
            buf = new byte[16];
            this.f60t = 823549496;
            buf[0] = (byte) (this.f60t >>> 14);
            this.f60t = -2071429135;
            buf[1] = (byte) (this.f60t >>> 13);
            this.f60t = 1652217277;
            buf[2] = (byte) (this.f60t >>> 20);
            this.f60t = -151971439;
            buf[3] = (byte) (this.f60t >>> 3);
            this.f60t = 639849382;
            buf[4] = (byte) (this.f60t >>> 20);
            this.f60t = 1634563282;
            buf[5] = (byte) (this.f60t >>> 16);
            this.f60t = -1980852528;
            buf[6] = (byte) (this.f60t >>> 9);
            this.f60t = 652418706;
            buf[7] = (byte) (this.f60t >>> 7);
            this.f60t = -1898768633;
            buf[8] = (byte) (this.f60t >>> 11);
            this.f60t = -292991613;
            buf[9] = (byte) (this.f60t >>> 14);
            this.f60t = 631167559;
            buf[10] = (byte) (this.f60t >>> 1);
            this.f60t = -1209735515;
            buf[11] = (byte) (this.f60t >>> 12);
            this.f60t = 1764270787;
            buf[12] = (byte) (this.f60t >>> 9);
            this.f60t = -1507855466;
            buf[13] = (byte) (this.f60t >>> 15);
            this.f60t = -236355122;
            buf[14] = (byte) (this.f60t >>> 3);
            this.f60t = 1034541696;
            buf[15] = (byte) (this.f60t >>> 18);
            return new String(buf);
        }
    }

    /* renamed from: org.telegram.customization.util.crypto.RSAUtils$2 */
    static class C12372 {
        /* renamed from: t */
        int f61t;

        C12372() {
        }

        public String toString() {
            buf = new byte[16];
            this.f61t = 173905795;
            buf[0] = (byte) (this.f61t >>> 19);
            this.f61t = 1723941651;
            buf[1] = (byte) (this.f61t >>> 8);
            this.f61t = 537019301;
            buf[2] = (byte) (this.f61t >>> 11);
            this.f61t = 1621272603;
            buf[3] = (byte) (this.f61t >>> 5);
            this.f61t = 1074368978;
            buf[4] = (byte) (this.f61t >>> 13);
            this.f61t = -1643999389;
            buf[5] = (byte) (this.f61t >>> 11);
            this.f61t = -2146653359;
            buf[6] = (byte) (this.f61t >>> 3);
            this.f61t = -1526064058;
            buf[7] = (byte) (this.f61t >>> 1);
            this.f61t = 164883336;
            buf[8] = (byte) (this.f61t >>> 12);
            this.f61t = -1943345450;
            buf[9] = (byte) (this.f61t >>> 5);
            this.f61t = 36381826;
            buf[10] = (byte) (this.f61t >>> 15);
            this.f61t = 414258231;
            buf[11] = (byte) (this.f61t >>> 4);
            this.f61t = -2028251598;
            buf[12] = (byte) (this.f61t >>> 20);
            this.f61t = 706048270;
            buf[13] = (byte) (this.f61t >>> 8);
            this.f61t = -1616503273;
            buf[14] = (byte) (this.f61t >>> 15);
            this.f61t = 871205573;
            buf[15] = (byte) (this.f61t >>> 23);
            return new String(buf);
        }
    }

    private RSAUtils() {
    }

    public static boolean generateKey(String publicKeyOutput, String privateKeyOutput) {
        Throwable th;
        Throwable th2 = null;
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance(algorithm);
            keyGen.initialize(2048);
            KeyPair key = keyGen.generateKeyPair();
            DataOutputStream dos = new DataOutputStream(new FileOutputStream(new File(publicKeyOutput)));
            Throwable th3 = null;
            try {
                dos.write(key.getPublic().getEncoded());
                if (dos != null) {
                    if (null != null) {
                        try {
                            dos.close();
                        } catch (Throwable th4) {
                            th3.addSuppressed(th4);
                        }
                    } else {
                        dos.close();
                    }
                }
                dos = new DataOutputStream(new FileOutputStream(new File(privateKeyOutput)));
                th3 = null;
                try {
                    dos.write(key.getPrivate().getEncoded());
                    if (dos != null) {
                        if (null != null) {
                            try {
                                dos.close();
                            } catch (Throwable th22) {
                                th3.addSuppressed(th22);
                            }
                        } else {
                            dos.close();
                        }
                    }
                    return true;
                } catch (Throwable th222) {
                    th = th222;
                    th222 = th3;
                    th3 = th;
                }
                throw th3;
                throw th3;
                if (dos != null) {
                    if (th222 != null) {
                        try {
                            dos.close();
                        } catch (Throwable th42) {
                            th222.addSuppressed(th42);
                        }
                    } else {
                        dos.close();
                    }
                }
                throw th3;
                if (dos != null) {
                    if (th222 != null) {
                        try {
                            dos.close();
                        } catch (Throwable th422) {
                            th222.addSuppressed(th422);
                        }
                    } else {
                        dos.close();
                    }
                }
                throw th3;
            } catch (Throwable th2222) {
                th = th2222;
                th2222 = th3;
                th3 = th;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static byte[] encrypt(PublicKey key, byte[] data) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(1, key);
            return cipher.doFinal(data);
        } catch (Exception e) {
            return null;
        }
    }

    public static byte[] encryptData(String txt, String publicKey) {
        String encoded = "";
        try {
            PublicKey pubKey = KeyFactory.getInstance(algorithm).generatePublic(new X509EncodedKeySpec(Base64.decode(publicKey, 0)));
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
            cipher.init(1, pubKey);
            encoded = Base64.encodeToString(cipher.doFinal(txt.getBytes()), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("LEE", "EncodeRequest:" + encoded);
        return encoded.getBytes();
    }

    public static byte[] decrypt(PrivateKey key, byte[] encryptedData) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(2, key);
            return cipher.doFinal(encryptedData);
        } catch (Exception e) {
            return null;
        }
    }

    public static PublicKey getPublicKey(String publicKeyPath) throws Exception {
        return KeyFactory.getInstance(algorithm).generatePublic(new X509EncodedKeySpec(Files.readAllBytes(Paths.get(publicKeyPath, new String[0]))));
    }

    public static PrivateKey getPrivateKey(String privateKeyPath) throws Exception {
        return KeyFactory.getInstance(algorithm).generatePrivate(new PKCS8EncodedKeySpec(Files.readAllBytes(Paths.get(privateKeyPath, new String[0]))));
    }

    public static PublicKey getPublicKey(byte[] encryptedPublicKey) throws Exception {
        return KeyFactory.getInstance(algorithm).generatePublic(new X509EncodedKeySpec(encryptedPublicKey));
    }

    public static PrivateKey getPrivateKey(byte[] encryptedPrivateKey) throws Exception {
        return KeyFactory.getInstance(algorithm).generatePrivate(new PKCS8EncodedKeySpec(encryptedPrivateKey));
    }

    public static byte[] encryptWithPublicKey(String encrypt) throws Exception {
        byte[] message = encrypt.getBytes("UTF-8");
        PublicKey apiPublicKey = getRSAPublicKeyFromString(Constants.PK);
        Cipher rsaCipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding", "BC");
        rsaCipher.init(1, apiPublicKey);
        return rsaCipher.doFinal(message);
    }

    public static PublicKey getRSAPublicKeyFromString(String publicKey) throws Exception {
        return KeyFactory.getInstance(algorithm, "BC").generatePublic(new X509EncodedKeySpec(Base64.decode(publicKey.getBytes("UTF-8"), 0)));
    }

    private byte[] encryptWithPublicKey(byte[] message, String publicKey) throws Exception {
        PublicKey apiPublicKey = getRSAPublicKeyFromString(Constants.PK);
        Cipher rsaCipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding", "SC");
        rsaCipher.init(1, apiPublicKey);
        return rsaCipher.doFinal(message);
    }

    public static String decryptAES(String str) throws Exception {
        String initVector = new C12361().toString();
        String key = new C12372().toString();
        IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(2, skeySpec, iv);
        return new String(cipher.doFinal(Base64.decode(str, 0)));
    }
}
