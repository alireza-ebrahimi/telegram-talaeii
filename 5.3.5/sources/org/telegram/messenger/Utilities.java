package org.telegram.messenger;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utilities {
    public static volatile DispatchQueue globalQueue = new DispatchQueue("globalQueue");
    protected static final char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static Pattern pattern = Pattern.compile("[\\-0-9]+");
    public static volatile DispatchQueue phoneBookQueue = new DispatchQueue("photoBookQueue");
    public static SecureRandom random = new SecureRandom();
    public static volatile DispatchQueue searchQueue = new DispatchQueue("searchQueue");
    public static volatile DispatchQueue stageQueue = new DispatchQueue("stageQueue");

    public static native void aesCtrDecryption(ByteBuffer byteBuffer, byte[] bArr, byte[] bArr2, int i, int i2);

    public static native void aesCtrDecryptionByteArray(byte[] bArr, byte[] bArr2, byte[] bArr3, int i, int i2, int i3);

    private static native void aesIgeEncryption(ByteBuffer byteBuffer, byte[] bArr, byte[] bArr2, boolean z, int i, int i2);

    public static native void blurBitmap(Object obj, int i, int i2, int i3, int i4, int i5);

    public static native void calcCDT(ByteBuffer byteBuffer, int i, int i2, ByteBuffer byteBuffer2);

    public static native int convertVideoFrame(ByteBuffer byteBuffer, ByteBuffer byteBuffer2, int i, int i2, int i3, int i4, int i5);

    public static native boolean loadWebpImage(Bitmap bitmap, ByteBuffer byteBuffer, int i, Options options, boolean z);

    public static native int pinBitmap(Bitmap bitmap);

    public static native String readlink(String str);

    public static native void unpinBitmap(Bitmap bitmap);

    static {
        try {
            FileInputStream sUrandomIn = new FileInputStream(new File("/dev/urandom"));
            byte[] buffer = new byte[1024];
            sUrandomIn.read(buffer);
            sUrandomIn.close();
            random.setSeed(buffer);
        } catch (Throwable e) {
            FileLog.m94e(e);
        }
    }

    public static void aesIgeEncryption(ByteBuffer buffer, byte[] key, byte[] iv, boolean encrypt, boolean changeIv, int offset, int length) {
        aesIgeEncryption(buffer, key, changeIv ? iv : (byte[]) iv.clone(), encrypt, offset, length);
    }

    public static Integer parseInt(String value) {
        if (value == null) {
            return Integer.valueOf(0);
        }
        Integer val = Integer.valueOf(0);
        try {
            Matcher matcher = pattern.matcher(value);
            if (matcher.find()) {
                return Integer.valueOf(Integer.parseInt(matcher.group(0)));
            }
            return val;
        } catch (Throwable e) {
            FileLog.m94e(e);
            return val;
        }
    }

    public static Long parseLong(String value) {
        if (value == null) {
            return Long.valueOf(0);
        }
        Long val = Long.valueOf(0);
        try {
            Matcher matcher = pattern.matcher(value);
            if (matcher.find()) {
                return Long.valueOf(Long.parseLong(matcher.group(0)));
            }
            return val;
        } catch (Throwable e) {
            FileLog.m94e(e);
            return val;
        }
    }

    public static String parseIntToString(String value) {
        Matcher matcher = pattern.matcher(value);
        if (matcher.find()) {
            return matcher.group(0);
        }
        return null;
    }

    public static String bytesToHex(byte[] bytes) {
        if (bytes == null) {
            return "";
        }
        char[] hexChars = new char[(bytes.length * 2)];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 255;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[(j * 2) + 1] = hexArray[v & 15];
        }
        return new String(hexChars);
    }

    public static byte[] hexToBytes(String hex) {
        if (hex == null) {
            return null;
        }
        int len = hex.length();
        byte[] data = new byte[(len / 2)];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4) + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }

    public static boolean isGoodPrime(byte[] prime, int g) {
        boolean z = true;
        if (g < 2 || g > 7 || prime.length != 256 || prime[0] >= (byte) 0) {
            return false;
        }
        BigInteger dhBI = new BigInteger(1, prime);
        if (g == 2) {
            if (dhBI.mod(BigInteger.valueOf(8)).intValue() != 7) {
                return false;
            }
        } else if (g == 3) {
            if (dhBI.mod(BigInteger.valueOf(3)).intValue() != 2) {
                return false;
            }
        } else if (g == 5) {
            val = dhBI.mod(BigInteger.valueOf(5)).intValue();
            if (!(val == 1 || val == 4)) {
                return false;
            }
        } else if (g == 6) {
            val = dhBI.mod(BigInteger.valueOf(24)).intValue();
            if (!(val == 19 || val == 23)) {
                return false;
            }
        } else if (g == 7) {
            val = dhBI.mod(BigInteger.valueOf(7)).intValue();
            if (!(val == 3 || val == 5 || val == 6)) {
                return false;
            }
        }
        if (bytesToHex(prime).equals("C71CAEB9C6B1C9048E6C522F70F13F73980D40238E3E21C14934D037563D930F48198A0AA7C14058229493D22530F4DBFA336F6E0AC925139543AED44CCE7C3720FD51F69458705AC68CD4FE6B6B13ABDC9746512969328454F18FAF8C595F642477FE96BB2A941D5BCD1D4AC8CC49880708FA9B378E3C4F3A9060BEE67CF9A4A4A695811051907E162753B56B0F6B410DBA74D8A84B2A14B3144E0EF1284754FD17ED950D5965B4B9DD46582DB1178D169C6BC465B0D6FF9CA3928FEF5B9AE4E418FC15E83EBEA0F87FA9FF5EED70050DED2849F47BF959D956850CE929851F0D8115F635B105EE2E4E15D04B2454BF6F4FADF034B10403119CD8E3B92FCC5B")) {
            return true;
        }
        BigInteger dhBI2 = dhBI.subtract(BigInteger.valueOf(1)).divide(BigInteger.valueOf(2));
        if (!(dhBI.isProbablePrime(30) && dhBI2.isProbablePrime(30))) {
            z = false;
        }
        return z;
    }

    public static boolean isGoodGaAndGb(BigInteger g_a, BigInteger p) {
        return g_a.compareTo(BigInteger.valueOf(1)) == 1 && g_a.compareTo(p.subtract(BigInteger.valueOf(1))) == -1;
    }

    public static boolean arraysEquals(byte[] arr1, int offset1, byte[] arr2, int offset2) {
        if (arr1 == null || arr2 == null || offset1 < 0 || offset2 < 0 || arr1.length - offset1 > arr2.length - offset2 || arr1.length - offset1 < 0 || arr2.length - offset2 < 0) {
            return false;
        }
        boolean result = true;
        for (int a = offset1; a < arr1.length; a++) {
            if (arr1[a + offset1] != arr2[a + offset2]) {
                result = false;
            }
        }
        return result;
    }

    public static byte[] computeSHA1(byte[] convertme, int offset, int len) {
        try {
            MessageDigest md = MessageDigest.getInstance(CommonUtils.SHA1_INSTANCE);
            md.update(convertme, offset, len);
            return md.digest();
        } catch (Throwable e) {
            FileLog.m94e(e);
            return new byte[20];
        }
    }

    public static byte[] computeSHA1(ByteBuffer convertme, int offset, int len) {
        int oldp = convertme.position();
        int oldl = convertme.limit();
        try {
            MessageDigest md = MessageDigest.getInstance(CommonUtils.SHA1_INSTANCE);
            convertme.position(offset);
            convertme.limit(len);
            md.update(convertme);
            byte[] digest = md.digest();
            return digest;
        } catch (Throwable e) {
            FileLog.m94e(e);
            return new byte[20];
        } finally {
            convertme.limit(oldl);
            convertme.position(oldp);
        }
    }

    public static byte[] computeSHA1(ByteBuffer convertme) {
        return computeSHA1(convertme, 0, convertme.limit());
    }

    public static byte[] computeSHA1(byte[] convertme) {
        return computeSHA1(convertme, 0, convertme.length);
    }

    public static byte[] computeSHA256(byte[] convertme) {
        return computeSHA256(convertme, 0, convertme.length);
    }

    public static byte[] computeSHA256(byte[] convertme, int offset, int len) {
        try {
            MessageDigest md = MessageDigest.getInstance(CommonUtils.SHA256_INSTANCE);
            md.update(convertme, offset, len);
            return md.digest();
        } catch (Throwable e) {
            FileLog.m94e(e);
            return new byte[32];
        }
    }

    public static byte[] computeSHA256(byte[] b1, int o1, int l1, ByteBuffer b2, int o2, int l2) {
        int oldp = b2.position();
        int oldl = b2.limit();
        try {
            MessageDigest md = MessageDigest.getInstance(CommonUtils.SHA256_INSTANCE);
            md.update(b1, o1, l1);
            b2.position(o2);
            b2.limit(l2);
            md.update(b2);
            byte[] digest = md.digest();
            return digest;
        } catch (Throwable e) {
            FileLog.m94e(e);
            return new byte[32];
        } finally {
            b2.limit(oldl);
            b2.position(oldp);
        }
    }

    public static long bytesToLong(byte[] bytes) {
        return (((((((((long) bytes[7]) << 56) + ((((long) bytes[6]) & 255) << 48)) + ((((long) bytes[5]) & 255) << 40)) + ((((long) bytes[4]) & 255) << 32)) + ((((long) bytes[3]) & 255) << 24)) + ((((long) bytes[2]) & 255) << 16)) + ((((long) bytes[1]) & 255) << 8)) + (((long) bytes[0]) & 255);
    }

    public static String MD5(String md5) {
        String str = null;
        if (md5 != null) {
            try {
                byte[] array = MessageDigest.getInstance("MD5").digest(md5.getBytes());
                StringBuilder sb = new StringBuilder();
                for (byte b : array) {
                    sb.append(Integer.toHexString((b & 255) | 256).substring(1, 3));
                }
                str = sb.toString();
            } catch (Throwable e) {
                FileLog.m94e(e);
            }
        }
        return str;
    }
}
