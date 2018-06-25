package com.google.android.gms.internal;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

final class zzao extends FilterInputStream {
    private long bytesRead;
    private final long zzcb;

    zzao(InputStream inputStream, long j) {
        super(inputStream);
        this.zzcb = j;
    }

    public final int read() throws IOException {
        int read = super.read();
        if (read != -1) {
            this.bytesRead++;
        }
        return read;
    }

    public final int read(byte[] bArr, int i, int i2) throws IOException {
        int read = super.read(bArr, i, i2);
        if (read != -1) {
            this.bytesRead += (long) read;
        }
        return read;
    }

    final long zzn() {
        return this.zzcb - this.bytesRead;
    }
}
