package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import com.google.android.gms.common.api.internal.zzci;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.wearable.CapabilityApi.CapabilityListener;
import com.google.android.gms.wearable.ChannelApi.ChannelListener;
import com.google.android.gms.wearable.DataApi.DataListener;
import com.google.android.gms.wearable.MessageApi.MessageListener;
import java.util.List;

public final class zzhk<T> extends zzen {
    private final IntentFilter[] zzlux;
    private zzci<Object> zzlvx;
    private zzci<Object> zzlvy;
    private zzci<DataListener> zzlvz;
    private zzci<MessageListener> zzlwa;
    private zzci<Object> zzlwb;
    private zzci<Object> zzlwc;
    private zzci<ChannelListener> zzlwd;
    private zzci<CapabilityListener> zzlwe;
    private final String zzlwf;

    private zzhk(IntentFilter[] intentFilterArr, String str) {
        this.zzlux = (IntentFilter[]) zzbq.checkNotNull(intentFilterArr);
        this.zzlwf = str;
    }

    public static zzhk<ChannelListener> zza(zzci<ChannelListener> zzci, String str, IntentFilter[] intentFilterArr) {
        zzhk<ChannelListener> zzhk = new zzhk(intentFilterArr, (String) zzbq.checkNotNull(str));
        zzhk.zzlwd = (zzci) zzbq.checkNotNull(zzci);
        return zzhk;
    }

    public static zzhk<DataListener> zza(zzci<DataListener> zzci, IntentFilter[] intentFilterArr) {
        zzhk<DataListener> zzhk = new zzhk(intentFilterArr, null);
        zzhk.zzlvz = (zzci) zzbq.checkNotNull(zzci);
        return zzhk;
    }

    public static zzhk<MessageListener> zzb(zzci<MessageListener> zzci, IntentFilter[] intentFilterArr) {
        zzhk<MessageListener> zzhk = new zzhk(intentFilterArr, null);
        zzhk.zzlwa = (zzci) zzbq.checkNotNull(zzci);
        return zzhk;
    }

    public static zzhk<ChannelListener> zzc(zzci<ChannelListener> zzci, IntentFilter[] intentFilterArr) {
        zzhk<ChannelListener> zzhk = new zzhk(intentFilterArr, null);
        zzhk.zzlwd = (zzci) zzbq.checkNotNull(zzci);
        return zzhk;
    }

    public static zzhk<CapabilityListener> zzd(zzci<CapabilityListener> zzci, IntentFilter[] intentFilterArr) {
        zzhk<CapabilityListener> zzhk = new zzhk(intentFilterArr, null);
        zzhk.zzlwe = (zzci) zzbq.checkNotNull(zzci);
        return zzhk;
    }

    private static void zzo(zzci<?> zzci) {
        if (zzci != null) {
            zzci.clear();
        }
    }

    public final void clear() {
        zzo(null);
        this.zzlvx = null;
        zzo(null);
        this.zzlvy = null;
        zzo(this.zzlvz);
        this.zzlvz = null;
        zzo(this.zzlwa);
        this.zzlwa = null;
        zzo(null);
        this.zzlwb = null;
        zzo(null);
        this.zzlwc = null;
        zzo(this.zzlwd);
        this.zzlwd = null;
        zzo(this.zzlwe);
        this.zzlwe = null;
    }

    public final void onConnectedNodes(List<zzfo> list) {
    }

    public final void zza(zzah zzah) {
        if (this.zzlwe != null) {
            this.zzlwe.zza(new zzho(zzah));
        }
    }

    public final void zza(zzaw zzaw) {
        if (this.zzlwd != null) {
            this.zzlwd.zza(new zzhn(zzaw));
        }
    }

    public final void zza(zzfe zzfe) {
        if (this.zzlwa != null) {
            this.zzlwa.zza(new zzhm(zzfe));
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

    public final IntentFilter[] zzblz() {
        return this.zzlux;
    }

    public final String zzbma() {
        return this.zzlwf;
    }

    public final void zzbo(DataHolder dataHolder) {
        if (this.zzlvz != null) {
            this.zzlvz.zza(new zzhl(dataHolder));
        } else {
            dataHolder.close();
        }
    }
}
