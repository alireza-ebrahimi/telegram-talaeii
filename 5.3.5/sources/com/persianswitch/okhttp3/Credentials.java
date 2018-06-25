package com.persianswitch.okhttp3;

import com.persianswitch.okio.ByteString;
import java.io.UnsupportedEncodingException;
import org.apache.commons.lang3.CharEncoding;

public final class Credentials {
    private Credentials() {
    }

    public static String basic(String userName, String password) {
        try {
            return "Basic " + ByteString.of((userName + ":" + password).getBytes(CharEncoding.ISO_8859_1)).base64();
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError();
        }
    }
}
