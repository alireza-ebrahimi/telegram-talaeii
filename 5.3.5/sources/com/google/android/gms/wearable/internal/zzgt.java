package com.google.android.gms.wearable.internal;

import android.os.ParcelFileDescriptor.AutoCloseOutputStream;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.wearable.Channel.GetOutputStreamResult;
import java.io.OutputStream;

final class zzgt extends zzgm<GetOutputStreamResult> {
    private final zzbr zzlvj;

    public zzgt(zzn<GetOutputStreamResult> zzn, zzbr zzbr) {
        super(zzn);
        this.zzlvj = (zzbr) zzbq.checkNotNull(zzbr);
    }

    public final void zza(zzdo zzdo) {
        OutputStream outputStream = null;
        if (zzdo.zzluj != null) {
            outputStream = new zzbl(new AutoCloseOutputStream(zzdo.zzluj));
            this.zzlvj.zza(new zzbm(outputStream));
        }
        zzav(new zzbh(new Status(zzdo.statusCode), outputStream));
    }
}
