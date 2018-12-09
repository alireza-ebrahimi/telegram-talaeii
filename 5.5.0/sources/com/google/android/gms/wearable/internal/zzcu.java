package com.google.android.gms.wearable.internal;

import android.os.ParcelFileDescriptor;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.wearable.DataApi.GetFdForAssetResult;
import com.google.android.gms.wearable.DataClient.GetFdForAssetResponse;
import java.io.InputStream;

final class zzcu extends GetFdForAssetResponse implements Releasable {
    private final GetFdForAssetResult zzdj;

    zzcu(GetFdForAssetResult getFdForAssetResult) {
        this.zzdj = getFdForAssetResult;
    }

    public final ParcelFileDescriptor getFdForAsset() {
        return this.zzdj.getFd();
    }

    public final InputStream getInputStream() {
        return this.zzdj.getInputStream();
    }

    public final void release() {
        this.zzdj.release();
    }
}
