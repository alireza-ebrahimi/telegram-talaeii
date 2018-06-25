package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.wearable.NodeApi.GetConnectedNodesResult;
import java.util.ArrayList;
import java.util.List;

final class zzgu extends zzgm<GetConnectedNodesResult> {
    public zzgu(zzn<GetConnectedNodesResult> zzn) {
        super(zzn);
    }

    public final void zza(zzea zzea) {
        List arrayList = new ArrayList();
        if (zzea.zzlup != null) {
            arrayList.addAll(zzea.zzlup);
        }
        zzav(new zzfj(zzgd.zzdg(zzea.statusCode), arrayList));
    }
}
