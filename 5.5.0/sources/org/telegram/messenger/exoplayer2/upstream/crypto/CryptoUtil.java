package org.telegram.messenger.exoplayer2.upstream.crypto;

final class CryptoUtil {
    private CryptoUtil() {
    }

    public static long getFNV64Hash(String str) {
        long j = 0;
        if (str != null) {
            for (int i = 0; i < str.length(); i++) {
                j ^= (long) str.charAt(i);
                j += (((((j << 1) + (j << 4)) + (j << 5)) + (j << 7)) + (j << 8)) + (j << 40);
            }
        }
        return j;
    }
}
