package com.coremedia.iso;

import java.io.ByteArrayOutputStream;
import org.telegram.messenger.exoplayer2.extractor.ts.PsExtractor;

public class Hex {
    private static final char[] DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static String encodeHex(byte[] data) {
        return encodeHex(data, 0);
    }

    public static String encodeHex(byte[] data, int group) {
        int l = data.length;
        char[] out = new char[((group > 0 ? l / group : 0) + (l << 1))];
        int i = 0;
        int j = 0;
        while (i < l) {
            int i2;
            if (group <= 0 || i % group != 0 || j <= 0) {
                i2 = j;
            } else {
                i2 = j + 1;
                out[j] = '-';
            }
            j = i2 + 1;
            out[i2] = DIGITS[(data[i] & PsExtractor.VIDEO_STREAM_MASK) >>> 4];
            i2 = j + 1;
            out[j] = DIGITS[data[i] & 15];
            i++;
            j = i2;
        }
        return new String(out);
    }

    public static byte[] decodeHex(String hexString) {
        ByteArrayOutputStream bas = new ByteArrayOutputStream();
        for (int i = 0; i < hexString.length(); i += 2) {
            bas.write(Integer.parseInt(hexString.substring(i, i + 2), 16));
        }
        return bas.toByteArray();
    }
}
