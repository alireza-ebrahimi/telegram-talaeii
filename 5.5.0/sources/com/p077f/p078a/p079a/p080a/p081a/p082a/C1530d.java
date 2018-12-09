package com.p077f.p078a.p079a.p080a.p081a.p082a;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import org.telegram.messenger.exoplayer2.C3446C;

/* renamed from: com.f.a.a.a.a.a.d */
final class C1530d {
    /* renamed from: a */
    static final Charset f4658a = Charset.forName("US-ASCII");
    /* renamed from: b */
    static final Charset f4659b = Charset.forName(C3446C.UTF8_NAME);

    /* renamed from: a */
    static void m7625a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e2) {
            }
        }
    }

    /* renamed from: a */
    static void m7626a(File file) {
        File[] listFiles = file.listFiles();
        if (listFiles == null) {
            throw new IOException("not a readable directory: " + file);
        }
        int length = listFiles.length;
        int i = 0;
        while (i < length) {
            File file2 = listFiles[i];
            if (file2.isDirectory()) {
                C1530d.m7626a(file2);
            }
            if (file2.delete()) {
                i++;
            } else {
                throw new IOException("failed to delete file: " + file2);
            }
        }
    }
}
