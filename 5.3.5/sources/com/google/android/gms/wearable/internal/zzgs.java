package com.google.android.gms.wearable.internal;

import android.os.ParcelFileDescriptor.AutoCloseInputStream;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.wearable.Channel.GetInputStreamResult;
import java.io.InputStream;

final class zzgs extends zzgm<GetInputStreamResult> {
    private final zzbr zzlvj;

    public zzgs(zzn<GetInputStreamResult> zzn, zzbr zzbr) {
        super(zzn);
        this.zzlvj = (zzbr) zzbq.checkNotNull(zzbr);
    }

    public final void zza(zzdm zzdm) {
        InputStream inputStream = null;
        if (zzdm.zzluj != null) {
            inputStream = new zzbj(new AutoCloseInputStream(zzdm.zzluj));
            this.zzlvj.zza(new zzbk(inputStream));
        }
        zzav(new zzbg(new Status(zzdm.statusCode), inputStream));
    }
}
