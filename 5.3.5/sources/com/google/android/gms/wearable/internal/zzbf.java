package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import android.os.RemoteException;
import com.google.android.gms.common.api.internal.zzci;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.wearable.ChannelApi.ChannelListener;

final class zzbf implements zzc<ChannelListener> {
    private /* synthetic */ String zzehu;
    private /* synthetic */ IntentFilter[] zzlso;

    zzbf(String str, IntentFilter[] intentFilterArr) {
        this.zzehu = str;
        this.zzlso = intentFilterArr;
    }

    public final /* synthetic */ void zza(zzhg zzhg, zzn zzn, Object obj, zzci zzci) throws RemoteException {
        zzhg.zza(zzn, (ChannelListener) obj, zzci, this.zzehu, this.zzlso);
    }
}
