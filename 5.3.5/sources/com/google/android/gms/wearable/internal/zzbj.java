package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.wearable.ChannelIOException;
import java.io.IOException;
import java.io.InputStream;

public final class zzbj extends InputStream {
    private final InputStream zzlto;
    private volatile zzav zzltp;

    public zzbj(InputStream inputStream) {
        this.zzlto = (InputStream) zzbq.checkNotNull(inputStream);
    }

    private final int zzfs(int i) throws ChannelIOException {
        if (i == -1) {
            zzav zzav = this.zzltp;
            if (zzav != null) {
                throw new ChannelIOException("Channel closed unexpectedly before stream was finished", zzav.zzltf, zzav.zzltg);
            }
        }
        return i;
    }

    public final int available() throws IOException {
        return this.zzlto.available();
    }

    public final void close() throws IOException {
        this.zzlto.close();
    }

    public final void mark(int i) {
        this.zzlto.mark(i);
    }

    public final boolean markSupported() {
        return this.zzlto.markSupported();
    }

    public final int read() throws IOException {
        return zzfs(this.zzlto.read());
    }

    public final int read(byte[] bArr) throws IOException {
        return zzfs(this.zzlto.read(bArr));
    }

    public final int read(byte[] bArr, int i, int i2) throws IOException {
        return zzfs(this.zzlto.read(bArr, i, i2));
    }

    public final void reset() throws IOException {
        this.zzlto.reset();
    }

    public final long skip(long j) throws IOException {
        return this.zzlto.skip(j);
    }

    final void zza(zzav zzav) {
        this.zzltp = (zzav) zzbq.checkNotNull(zzav);
    }
}
