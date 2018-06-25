package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.internal.BaseImplementation.ResultHolder;
import com.google.android.gms.wearable.NodeApi.GetConnectedNodesResult;
import java.util.ArrayList;
import java.util.List;

final class zzgu extends zzgm<GetConnectedNodesResult> {
    public zzgu(ResultHolder<GetConnectedNodesResult> resultHolder) {
        super(resultHolder);
    }

    public final void zza(zzea zzea) {
        List arrayList = new ArrayList();
        if (zzea.zzdx != null) {
            arrayList.addAll(zzea.zzdx);
        }
        zza(new zzfj(zzgd.zzb(zzea.statusCode), arrayList));
    }
}
