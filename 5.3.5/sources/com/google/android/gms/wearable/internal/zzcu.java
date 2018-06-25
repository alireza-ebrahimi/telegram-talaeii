package com.google.android.gms.wearable.internal;

import android.os.ParcelFileDescriptor;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.wearable.DataApi.GetFdForAssetResult;
import com.google.android.gms.wearable.DataClient.GetFdForAssetResponse;
import java.io.InputStream;

final class zzcu extends GetFdForAssetResponse implements Releasable {
    private final GetFdForAssetResult zzlud;

    zzcu(GetFdForAssetResult getFdForAssetResult) {
        this.zzlud = getFdForAssetResult;
    }

    public final ParcelFileDescriptor getFdForAsset() {
        return this.zzlud.getFd();
    }

    public final InputStream getInputStream() {
        return this.zzlud.getInputStream();
    }

    public final void release() {
        this.zzlud.release();
    }
}
