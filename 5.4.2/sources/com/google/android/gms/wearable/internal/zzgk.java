package com.google.android.gms.wearable.internal;

import com.google.android.gms.wearable.CapabilityInfo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class zzgk {
    private static Map<String, CapabilityInfo> zza(List<zzah> list) {
        Map hashMap = new HashMap();
        if (list != null) {
            for (zzah zzah : list) {
                hashMap.put(zzah.getName(), new zzw(zzah));
            }
        }
        return hashMap;
    }
}
