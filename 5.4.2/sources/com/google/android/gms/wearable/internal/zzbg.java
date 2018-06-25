package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.wearable.Channel.GetInputStreamResult;
import java.io.IOException;
import java.io.InputStream;
import javax.annotation.Nullable;

final class zzbg implements GetInputStreamResult {
    private final InputStream zzct;
    private final Status zzp;

    zzbg(Status status, @Nullable InputStream inputStream) {
        this.zzp = (Status) Preconditions.checkNotNull(status);
        this.zzct = inputStream;
    }

    @Nullable
    public final InputStream getInputStream() {
        return this.zzct;
    }

    public final Status getStatus() {
        return this.zzp;
    }

    public final void release() {
        if (this.zzct != null) {
            try {
                this.zzct.close();
            } catch (IOException e) {
            }
        }
    }
}
