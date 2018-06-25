package com.google.android.gms.wearable.internal;

import android.util.Log;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.wearable.ChannelIOException;
import java.io.IOException;
import java.io.OutputStream;

public final class zzbl extends OutputStream {
    private volatile zzav zzltp;
    private final OutputStream zzltr;

    public zzbl(OutputStream outputStream) {
        this.zzltr = (OutputStream) zzbq.checkNotNull(outputStream);
    }

    private final IOException zza(IOException iOException) {
        zzav zzav = this.zzltp;
        if (zzav == null) {
            return iOException;
        }
        if (Log.isLoggable("ChannelOutputStream", 2)) {
            Log.v("ChannelOutputStream", "Caught IOException, but channel has been closed. Translating to ChannelIOException.", iOException);
        }
        return new ChannelIOException("Channel closed unexpectedly before stream was finished", zzav.zzltf, zzav.zzltg);
    }

    public final void close() throws IOException {
        try {
            this.zzltr.close();
        } catch (IOException e) {
            throw zza(e);
        }
    }

    public final void flush() throws IOException {
        try {
            this.zzltr.flush();
        } catch (IOException e) {
            throw zza(e);
        }
    }

    public final void write(int i) throws IOException {
        try {
            this.zzltr.write(i);
        } catch (IOException e) {
            throw zza(e);
        }
    }

    public final void write(byte[] bArr) throws IOException {
        try {
            this.zzltr.write(bArr);
        } catch (IOException e) {
            throw zza(e);
        }
    }

    public final void write(byte[] bArr, int i, int i2) throws IOException {
        try {
            this.zzltr.write(bArr, i, i2);
        } catch (IOException e) {
            throw zza(e);
        }
    }

    final void zzc(zzav zzav) {
        this.zzltp = zzav;
    }
}
