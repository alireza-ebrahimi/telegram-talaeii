package com.google.android.gms.internal;

import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzbq;
import java.util.Iterator;

public final class zzcis {
    final String name;
    final long timestamp;
    final String zzcm;
    private String zzjgm;
    final long zzjhq;
    final zzciu zzjhr;

    zzcis(zzckj zzckj, String str, String str2, String str3, long j, long j2, Bundle bundle) {
        zzbq.zzgv(str2);
        zzbq.zzgv(str3);
        this.zzcm = str2;
        this.name = str3;
        if (TextUtils.isEmpty(str)) {
            str = null;
        }
        this.zzjgm = str;
        this.timestamp = j;
        this.zzjhq = j2;
        if (this.zzjhq != 0 && this.zzjhq > this.timestamp) {
            zzckj.zzayp().zzbaw().zzj("Event created with reverse previous/current timestamps. appId", zzcjj.zzjs(str2));
        }
        this.zzjhr = zza(zzckj, bundle);
    }

    private zzcis(zzckj zzckj, String str, String str2, String str3, long j, long j2, zzciu zzciu) {
        zzbq.zzgv(str2);
        zzbq.zzgv(str3);
        zzbq.checkNotNull(zzciu);
        this.zzcm = str2;
        this.name = str3;
        if (TextUtils.isEmpty(str)) {
            str = null;
        }
        this.zzjgm = str;
        this.timestamp = j;
        this.zzjhq = j2;
        if (this.zzjhq != 0 && this.zzjhq > this.timestamp) {
            zzckj.zzayp().zzbaw().zzj("Event created with reverse previous/current timestamps. appId", zzcjj.zzjs(str2));
        }
        this.zzjhr = zzciu;
    }

    private static zzciu zza(zzckj zzckj, Bundle bundle) {
        if (bundle == null || bundle.isEmpty()) {
            return new zzciu(new Bundle());
        }
        Bundle bundle2 = new Bundle(bundle);
        Iterator it = bundle2.keySet().iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (str == null) {
                zzckj.zzayp().zzbau().log("Param name can't be null");
                it.remove();
            } else {
                Object zzk = zzckj.zzayl().zzk(str, bundle2.get(str));
                if (zzk == null) {
                    zzckj.zzayp().zzbaw().zzj("Param value can't be null", zzckj.zzayk().zzjq(str));
                    it.remove();
                } else {
                    zzckj.zzayl().zza(bundle2, str, zzk);
                }
            }
        }
        return new zzciu(bundle2);
    }

    public final String toString() {
        String str = this.zzcm;
        String str2 = this.name;
        String valueOf = String.valueOf(this.zzjhr);
        return new StringBuilder(((String.valueOf(str).length() + 33) + String.valueOf(str2).length()) + String.valueOf(valueOf).length()).append("Event{appId='").append(str).append("', name='").append(str2).append("', params=").append(valueOf).append('}').toString();
    }

    final zzcis zza(zzckj zzckj, long j) {
        return new zzcis(zzckj, this.zzjgm, this.zzcm, this.name, this.timestamp, j, this.zzjhr);
    }
}
