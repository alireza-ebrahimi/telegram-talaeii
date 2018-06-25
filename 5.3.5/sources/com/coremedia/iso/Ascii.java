package com.coremedia.iso;

import java.io.UnsupportedEncodingException;

public final class Ascii {
    public static byte[] convert(String s) {
        if (s == null) {
            return null;
        }
        try {
            return s.getBytes("us-ascii");
        } catch (UnsupportedEncodingException e) {
            throw new Error(e);
        }
    }

    public static String convert(byte[] b) {
        if (b == null) {
            return null;
        }
        try {
            return new String(b, "us-ascii");
        } catch (UnsupportedEncodingException e) {
            throw new Error(e);
        }
    }
}
