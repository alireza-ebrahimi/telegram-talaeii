package com.p077f.p078a.p086b.p087a;

import java.io.FilterInputStream;
import java.io.InputStream;

/* renamed from: com.f.a.b.a.c */
public class C1551c extends FilterInputStream {
    public C1551c(InputStream inputStream) {
        super(inputStream);
    }

    public long skip(long j) {
        long j2 = 0;
        while (j2 < j) {
            long skip = this.in.skip(j - j2);
            if (skip == 0) {
                if (read() < 0) {
                    break;
                }
                skip = 1;
            }
            j2 = skip + j2;
        }
        return j2;
    }
}
