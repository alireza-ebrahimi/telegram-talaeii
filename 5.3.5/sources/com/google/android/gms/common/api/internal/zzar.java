package com.google.android.gms.common.api.internal;

import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzj;
import com.google.android.gms.common.zzf;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

final class zzar extends zzay {
    final /* synthetic */ zzao zzfxt;
    private final Map<zze, zzaq> zzfxv;

    public zzar(zzao zzao, Map<zze, zzaq> map) {
        this.zzfxt = zzao;
        super(zzao);
        this.zzfxv = map;
    }

    private final int zza(@NonNull zze zze, @NonNull Map<zze, Integer> map) {
        zzbq.checkNotNull(zze);
        zzbq.checkNotNull(map);
        if (!zze.zzahn()) {
            return 0;
        }
        if (map.containsKey(zze)) {
            return ((Integer) map.get(zze)).intValue();
        }
        int intValue;
        Iterator it = map.keySet().iterator();
        if (it.hasNext()) {
            zze zze2 = (zze) it.next();
            zze2.zzahq();
            zze.zzahq();
            intValue = ((Integer) map.get(zze2)).intValue();
        } else {
            intValue = -1;
        }
        if (intValue == -1) {
            intValue = zzf.zzc(this.zzfxt.mContext, zze.zzahq());
        }
        map.put(zze, Integer.valueOf(intValue));
        return intValue;
    }

    @WorkerThread
    public final void zzajj() {
        int i = 0;
        List arrayList = new ArrayList();
        List arrayList2 = new ArrayList();
        for (zze zze : this.zzfxv.keySet()) {
            if (!zze.zzahn() || ((zzaq) this.zzfxv.get(zze)).zzfvo) {
                arrayList2.add(zze);
            } else {
                arrayList.add(zze);
            }
        }
        Map hashMap = new HashMap(this.zzfxv.size());
        int i2 = -1;
        ArrayList arrayList3;
        int i3;
        if (!arrayList.isEmpty()) {
            arrayList3 = (ArrayList) arrayList;
            int size = arrayList3.size();
            i3 = 0;
            while (i3 < size) {
                Object obj = arrayList3.get(i3);
                i3++;
                i2 = zza((zze) obj, hashMap);
                if (i2 != 0) {
                    break;
                }
            }
        }
        arrayList3 = (ArrayList) arrayList2;
        i3 = arrayList3.size();
        while (i < i3) {
            obj = arrayList3.get(i);
            i++;
            i2 = zza((zze) obj, hashMap);
            if (i2 == 0) {
                break;
            }
        }
        int i4 = i2;
        if (i4 != 0) {
            this.zzfxt.zzfxd.zza(new zzas(this, this.zzfxt, new ConnectionResult(i4, null)));
            return;
        }
        if (this.zzfxt.zzfxn) {
            this.zzfxt.zzfxl.connect();
        }
        for (zze zze2 : this.zzfxv.keySet()) {
            zzj zzj = (zzj) this.zzfxv.get(zze2);
            if (!zze2.zzahn() || zza(zze2, hashMap) == 0) {
                zze2.zza(zzj);
            } else {
                this.zzfxt.zzfxd.zza(new zzat(this, this.zzfxt, zzj));
            }
        }
    }
}
