package org.telegram.customization.util;

import android.content.Context;
import android.util.Base64;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class CrazyRequestSigner {
    static final int SIGN_CERT_ID = 1;
    static final String SIGN_KEY_ALGORITHM = "RSA";
    static final String SIGN_SCHEME = "SHA256withRSA";
    static final int SIGN_VERSION = 1;

    public static String createHostRequestSign(Context context, String hostRequest) {
        try {
            return "1#1#" + Base64.encodeToString(createSign(hostRequest.getBytes(), getPrivateKeyFromAssets(context, "sign/sign.bks")), 2);
        } catch (Exception e) {
            return "";
        }
    }

    public static boolean isResponseValid(Context context, String hostResponse, String hostResponseSign) {
        boolean z = false;
        try {
            PublicKey publicKey = getPublicKeyFromAssets(context, "sign/asanpardakht_sdk_sign_test_certificate.cer");
            String[] parts = hostResponseSign.split("#");
            int signVer = Integer.parseInt(parts[0]);
            int certId = Integer.parseInt(parts[1]);
            byte[] signBytes = Base64.decode(parts[2], 2);
            Signature signature = Signature.getInstance(SIGN_SCHEME);
            signature.initVerify(publicKey);
            signature.update(hostResponse.getBytes());
            z = signature.verify(signBytes);
        } catch (Exception e) {
        }
        return z;
    }

    private static byte[] createSign(byte[] data, PrivateKey privateKey) {
        try {
            Signature signature = Signature.getInstance(SIGN_SCHEME);
            signature.initSign(privateKey);
            signature.update(data);
            return signature.sign();
        } catch (Exception e) {
            return new byte[0];
        }
    }

    private static PublicKey getPublicKeyFromAssets(Context context, String assetPath) throws CertificateException, IOException {
        InputStream inputStream = null;
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            inputStream = context.getAssets().open(assetPath);
            PublicKey publicKey = ((X509Certificate) certificateFactory.generateCertificate(inputStream)).getPublicKey();
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
            return publicKey;
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e2) {
                }
            }
        }
    }

    private static PrivateKey getPrivateKeyFromAssets(Context context, String assetsPath) throws Exception {
        InputStream inputStream = null;
        try {
            KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            inputStream = context.getAssets().open(assetsPath);
            keystore.load(inputStream, "AsdZxc".toCharArray());
            PrivateKey privateKey = (PrivateKey) keystore.getKey("sign", "AsdZxc".toCharArray());
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
            return privateKey;
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e2) {
                }
            }
        }
    }
}
