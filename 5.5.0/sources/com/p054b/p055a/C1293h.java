package com.p054b.p055a;

import java.io.UnsupportedEncodingException;
import org.telegram.messenger.exoplayer2.C3446C;

/* renamed from: com.b.a.h */
public final class C1293h {
    /* renamed from: a */
    public static String m6690a(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        try {
            return new String(bArr, C3446C.UTF8_NAME);
        } catch (Throwable e) {
            throw new Error(e);
        }
    }

    /* renamed from: a */
    public static byte[] m6691a(String str) {
        if (str == null) {
            return null;
        }
        try {
            return str.getBytes(C3446C.UTF8_NAME);
        } catch (Throwable e) {
            throw new Error(e);
        }
    }

    /* renamed from: b */
    public static int m6692b(String str) {
        if (str == null) {
            return 0;
        }
        try {
            return str.getBytes(C3446C.UTF8_NAME).length;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException();
        }
    }
}
