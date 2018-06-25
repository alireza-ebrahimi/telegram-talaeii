package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.wearable.Channel.GetOutputStreamResult;
import java.io.IOException;
import java.io.OutputStream;

final class zzbh implements GetOutputStreamResult {
    private final Status mStatus;
    private final OutputStream zzltn;

    zzbh(Status status, OutputStream outputStream) {
        this.mStatus = (Status) zzbq.checkNotNull(status);
        this.zzltn = outputStream;
    }

    public final OutputStream getOutputStream() {
        return this.zzltn;
    }

    public final Status getStatus() {
        return this.mStatus;
    }

    public final void release() {
        if (this.zzltn != null) {
            try {
                this.zzltn.close();
            } catch (IOException e) {
            }
        }
    }
}
