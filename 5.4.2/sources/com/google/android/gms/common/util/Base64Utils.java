package com.google.android.gms.common.util;

import android.util.Base64;

public final class Base64Utils {
    public static byte[] decode(String str) {
        return str == null ? null : Base64.decode(str, 0);
    }

    public static byte[] decodeUrlSafe(String str) {
        return str == null ? null : Base64.decode(str, 10);
    }

    public static byte[] decodeUrlSafeNoPadding(String str) {
        return str == null ? null : Base64.decode(str, 11);
    }

    public static byte[] decodeUrlSafeNoPadding(byte[] bArr) {
        return bArr == null ? null : Base64.decode(bArr, 11);
    }

    public static String encode(byte[] bArr) {
        return bArr == null ? null : Base64.encodeToString(bArr, 0);
    }

    public static String encodeUrlSafe(byte[] bArr) {
        return bArr == null ? null : Base64.encodeToString(bArr, 10);
    }

    public static String encodeUrlSafeNoPadding(byte[] bArr) {
        return bArr == null ? null : Base64.encodeToString(bArr, 11);
    }
}
