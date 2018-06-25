package com.google.android.gms.wearable.internal;

import android.os.ParcelFileDescriptor;
import android.os.ParcelFileDescriptor.AutoCloseInputStream;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.DataApi.GetFdForAssetResult;
import java.io.IOException;
import java.io.InputStream;

public final class zzci implements GetFdForAssetResult {
    private volatile boolean mClosed = false;
    private final Status mStatus;
    private volatile InputStream zzltm;
    private volatile ParcelFileDescriptor zzlub;

    public zzci(Status status, ParcelFileDescriptor parcelFileDescriptor) {
        this.mStatus = status;
        this.zzlub = parcelFileDescriptor;
    }

    public final ParcelFileDescriptor getFd() {
        if (!this.mClosed) {
            return this.zzlub;
        }
        throw new IllegalStateException("Cannot access the file descriptor after release().");
    }

    public final InputStream getInputStream() {
        if (this.mClosed) {
            throw new IllegalStateException("Cannot access the input stream after release().");
        } else if (this.zzlub == null) {
            return null;
        } else {
            if (this.zzltm == null) {
                this.zzltm = new AutoCloseInputStream(this.zzlub);
            }
            return this.zzltm;
        }
    }

    public final Status getStatus() {
        return this.mStatus;
    }

    public final void release() {
        if (this.zzlub != null) {
            if (this.mClosed) {
                throw new IllegalStateException("releasing an already released result.");
            }
            try {
                if (this.zzltm != null) {
                    this.zzltm.close();
                } else {
                    this.zzlub.close();
                }
                this.mClosed = true;
                this.zzlub = null;
                this.zzltm = null;
            } catch (IOException e) {
            }
        }
    }
}
