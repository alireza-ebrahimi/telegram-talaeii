package com.google.android.gms.wearable.internal;

import android.os.ParcelFileDescriptor.AutoCloseOutputStream;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation.ResultHolder;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.wearable.Channel.GetOutputStreamResult;
import java.io.OutputStream;

final class zzgt extends zzgm<GetOutputStreamResult> {
    private final zzbr zzeu;

    public zzgt(ResultHolder<GetOutputStreamResult> resultHolder, zzbr zzbr) {
        super(resultHolder);
        this.zzeu = (zzbr) Preconditions.checkNotNull(zzbr);
    }

    public final void zza(zzdo zzdo) {
        OutputStream outputStream = null;
        if (zzdo.zzdr != null) {
            outputStream = new zzbl(new AutoCloseOutputStream(zzdo.zzdr));
            this.zzeu.zza(new zzbm(outputStream));
        }
        zza(new zzbh(new Status(zzdo.statusCode), outputStream));
    }
}
