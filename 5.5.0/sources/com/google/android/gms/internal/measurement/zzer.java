package com.google.android.gms.internal.measurement;

import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import java.util.Iterator;

public final class zzer {
    final String name;
    private final String origin;
    final long timestamp;
    final long zzafq;
    final zzet zzafr;
    final String zzti;

    zzer(zzgm zzgm, String str, String str2, String str3, long j, long j2, Bundle bundle) {
        zzet zzet;
        Preconditions.checkNotEmpty(str2);
        Preconditions.checkNotEmpty(str3);
        this.zzti = str2;
        this.name = str3;
        if (TextUtils.isEmpty(str)) {
            str = null;
        }
        this.origin = str;
        this.timestamp = j;
        this.zzafq = j2;
        if (this.zzafq != 0 && this.zzafq > this.timestamp) {
            zzgm.zzgf().zziv().zzg("Event created with reverse previous/current timestamps. appId", zzfh.zzbl(str2));
        }
        if (bundle == null || bundle.isEmpty()) {
            zzet = new zzet(new Bundle());
        } else {
            Bundle bundle2 = new Bundle(bundle);
            Iterator it = bundle2.keySet().iterator();
            while (it.hasNext()) {
                String str4 = (String) it.next();
                if (str4 == null) {
                    zzgm.zzgf().zzis().log("Param name can't be null");
                    it.remove();
                } else {
                    Object zzh = zzgm.zzgc().zzh(str4, bundle2.get(str4));
                    if (zzh == null) {
                        zzgm.zzgf().zziv().zzg("Param value can't be null", zzgm.zzgb().zzbj(str4));
                        it.remove();
                    } else {
                        zzgm.zzgc().zza(bundle2, str4, zzh);
                    }
                }
            }
            zzet = new zzet(bundle2);
        }
        this.zzafr = zzet;
    }

    private zzer(zzgm zzgm, String str, String str2, String str3, long j, long j2, zzet zzet) {
        Preconditions.checkNotEmpty(str2);
        Preconditions.checkNotEmpty(str3);
        Preconditions.checkNotNull(zzet);
        this.zzti = str2;
        this.name = str3;
        if (TextUtils.isEmpty(str)) {
            str = null;
        }
        this.origin = str;
        this.timestamp = j;
        this.zzafq = j2;
        if (this.zzafq != 0 && this.zzafq > this.timestamp) {
            zzgm.zzgf().zziv().zze("Event created with reverse previous/current timestamps. appId, name", zzfh.zzbl(str2), zzfh.zzbl(str3));
        }
        this.zzafr = zzet;
    }

    public final String toString() {
        String str = this.zzti;
        String str2 = this.name;
        String valueOf = String.valueOf(this.zzafr);
        return new StringBuilder(((String.valueOf(str).length() + 33) + String.valueOf(str2).length()) + String.valueOf(valueOf).length()).append("Event{appId='").append(str).append("', name='").append(str2).append("', params=").append(valueOf).append('}').toString();
    }

    final zzer zza(zzgm zzgm, long j) {
        return new zzer(zzgm, this.origin, this.zzti, this.name, this.timestamp, j, this.zzafr);
    }
}
