package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.wearable.Channel.GetInputStreamResult;
import java.io.IOException;
import java.io.InputStream;

final class zzbg implements GetInputStreamResult {
    private final Status mStatus;
    private final InputStream zzltm;

    zzbg(Status status, InputStream inputStream) {
        this.mStatus = (Status) zzbq.checkNotNull(status);
        this.zzltm = inputStream;
    }

    public final InputStream getInputStream() {
        return this.zzltm;
    }

    public final Status getStatus() {
        return this.mStatus;
    }

    public final void release() {
        if (this.zzltm != null) {
            try {
                this.zzltm.close();
            } catch (IOException e) {
            }
        }
    }
}
