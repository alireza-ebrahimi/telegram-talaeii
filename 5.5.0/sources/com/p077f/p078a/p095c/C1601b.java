package com.p077f.p078a.p095c;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tgnet.TLRPC;

/* renamed from: com.f.a.c.b */
public final class C1601b {

    /* renamed from: com.f.a.c.b$a */
    public interface C1596a {
        /* renamed from: a */
        boolean mo1242a(int i, int i2);
    }

    /* renamed from: a */
    public static void m7930a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
            }
        }
    }

    /* renamed from: a */
    public static void m7931a(InputStream inputStream) {
        while (true) {
            try {
                if (inputStream.read(new byte[TLRPC.MESSAGE_FLAG_EDITED], 0, TLRPC.MESSAGE_FLAG_EDITED) == -1) {
                    break;
                }
            } catch (IOException e) {
            } finally {
                C1601b.m7930a((Closeable) inputStream);
            }
        }
    }

    /* renamed from: a */
    private static boolean m7932a(C1596a c1596a, int i, int i2) {
        return (c1596a == null || c1596a.mo1242a(i, i2) || (i * 100) / i2 >= 75) ? false : true;
    }

    /* renamed from: a */
    public static boolean m7933a(InputStream inputStream, OutputStream outputStream, C1596a c1596a, int i) {
        int available = inputStream.available();
        if (available <= 0) {
            available = 512000;
        }
        byte[] bArr = new byte[i];
        if (C1601b.m7932a(c1596a, 0, available)) {
            return false;
        }
        int i2 = 0;
        do {
            int read = inputStream.read(bArr, 0, i);
            if (read != -1) {
                outputStream.write(bArr, 0, read);
                i2 += read;
            } else {
                outputStream.flush();
                return true;
            }
        } while (!C1601b.m7932a(c1596a, i2, available));
        return false;
    }
}
