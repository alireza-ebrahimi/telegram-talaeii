package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.wearable.CapabilityApi.CapabilityListener;
import com.google.android.gms.wearable.ChannelApi.ChannelListener;
import com.google.android.gms.wearable.DataApi.DataListener;
import com.google.android.gms.wearable.MessageApi.MessageListener;
import java.util.List;
import javax.annotation.Nullable;

@VisibleForTesting
public final class zzhk<T> extends zzen {
    private final IntentFilter[] zzba;
    @Nullable
    private final String zzbb;
    private ListenerHolder<Object> zzfj;
    private ListenerHolder<Object> zzfk;
    private ListenerHolder<DataListener> zzfl;
    private ListenerHolder<MessageListener> zzfm;
    private ListenerHolder<Object> zzfn;
    private ListenerHolder<Object> zzfo;
    private ListenerHolder<ChannelListener> zzfp;
    private ListenerHolder<CapabilityListener> zzfq;

    private zzhk(IntentFilter[] intentFilterArr, @Nullable String str) {
        this.zzba = (IntentFilter[]) Preconditions.checkNotNull(intentFilterArr);
        this.zzbb = str;
    }

    public static zzhk<ChannelListener> zza(ListenerHolder<ChannelListener> listenerHolder, String str, IntentFilter[] intentFilterArr) {
        zzhk<ChannelListener> zzhk = new zzhk(intentFilterArr, (String) Preconditions.checkNotNull(str));
        zzhk.zzfp = (ListenerHolder) Preconditions.checkNotNull(listenerHolder);
        return zzhk;
    }

    public static zzhk<DataListener> zza(ListenerHolder<DataListener> listenerHolder, IntentFilter[] intentFilterArr) {
        zzhk<DataListener> zzhk = new zzhk(intentFilterArr, null);
        zzhk.zzfl = (ListenerHolder) Preconditions.checkNotNull(listenerHolder);
        return zzhk;
    }

    private static void zza(ListenerHolder<?> listenerHolder) {
        if (listenerHolder != null) {
            listenerHolder.clear();
        }
    }

    public static zzhk<MessageListener> zzb(ListenerHolder<MessageListener> listenerHolder, IntentFilter[] intentFilterArr) {
        zzhk<MessageListener> zzhk = new zzhk(intentFilterArr, null);
        zzhk.zzfm = (ListenerHolder) Preconditions.checkNotNull(listenerHolder);
        return zzhk;
    }

    public static zzhk<ChannelListener> zzc(ListenerHolder<ChannelListener> listenerHolder, IntentFilter[] intentFilterArr) {
        zzhk<ChannelListener> zzhk = new zzhk(intentFilterArr, null);
        zzhk.zzfp = (ListenerHolder) Preconditions.checkNotNull(listenerHolder);
        return zzhk;
    }

    public static zzhk<CapabilityListener> zzd(ListenerHolder<CapabilityListener> listenerHolder, IntentFilter[] intentFilterArr) {
        zzhk<CapabilityListener> zzhk = new zzhk(intentFilterArr, null);
        zzhk.zzfq = (ListenerHolder) Preconditions.checkNotNull(listenerHolder);
        return zzhk;
    }

    public final void clear() {
        zza(null);
        this.zzfj = null;
        zza(null);
        this.zzfk = null;
        zza(this.zzfl);
        this.zzfl = null;
        zza(this.zzfm);
        this.zzfm = null;
        zza(null);
        this.zzfn = null;
        zza(null);
        this.zzfo = null;
        zza(this.zzfp);
        this.zzfp = null;
        zza(this.zzfq);
        this.zzfq = null;
    }

    public final void onConnectedNodes(List<zzfo> list) {
    }

    public final void zza(DataHolder dataHolder) {
        if (this.zzfl != null) {
            this.zzfl.notifyListener(new zzhl(dataHolder));
        } else {
            dataHolder.close();
        }
    }

    public final void zza(zzah zzah) {
        if (this.zzfq != null) {
            this.zzfq.notifyListener(new zzho(zzah));
        }
    }

    public final void zza(zzaw zzaw) {
        if (this.zzfp != null) {
            this.zzfp.notifyListener(new zzhn(zzaw));
        }
    }

    public final void zza(zzfe zzfe) {
        if (this.zzfm != null) {
            this.zzfm.notifyListener(new zzhm(zzfe));
        }
    }

    public final void zza(zzfo zzfo) {
    }

    public final void zza(zzi zzi) {
    }

    public final void zza(zzl zzl) {
    }

    public final void zzb(zzfo zzfo) {
    }

    public final IntentFilter[] zze() {
        return this.zzba;
    }

    @Nullable
    public final String zzf() {
        return this.zzbb;
    }
}
