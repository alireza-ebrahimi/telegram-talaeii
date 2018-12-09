package com.google.android.gms.wearable.internal;

import android.os.ParcelFileDescriptor;
import android.os.ParcelFileDescriptor.AutoCloseInputStream;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.DataApi.GetFdForAssetResult;
import java.io.IOException;
import java.io.InputStream;

public final class zzci implements GetFdForAssetResult {
    private volatile boolean closed = false;
    private volatile InputStream zzct;
    private volatile ParcelFileDescriptor zzf;
    private final Status zzp;

    public zzci(Status status, ParcelFileDescriptor parcelFileDescriptor) {
        this.zzp = status;
        this.zzf = parcelFileDescriptor;
    }

    public final ParcelFileDescriptor getFd() {
        if (!this.closed) {
            return this.zzf;
        }
        throw new IllegalStateException("Cannot access the file descriptor after release().");
    }

    public final InputStream getInputStream() {
        if (this.closed) {
            throw new IllegalStateException("Cannot access the input stream after release().");
        } else if (this.zzf == null) {
            return null;
        } else {
            if (this.zzct == null) {
                this.zzct = new AutoCloseInputStream(this.zzf);
            }
            return this.zzct;
        }
    }

    public final Status getStatus() {
        return this.zzp;
    }

    public final void release() {
        if (this.zzf != null) {
            if (this.closed) {
                throw new IllegalStateException("releasing an already released result.");
            }
            try {
                if (this.zzct != null) {
                    this.zzct.close();
                } else {
                    this.zzf.close();
                }
                this.closed = true;
                this.zzf = null;
                this.zzct = null;
            } catch (IOException e) {
            }
        }
    }
}
