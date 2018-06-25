package com.google.android.gms.common.data;

import java.util.Comparator;

final class zzb implements Comparator<Integer> {
    private final /* synthetic */ Comparator zzom;
    private final /* synthetic */ SortedDataBuffer zzon;

    zzb(SortedDataBuffer sortedDataBuffer, Comparator comparator) {
        this.zzon = sortedDataBuffer;
        this.zzom = comparator;
    }

    public final /* synthetic */ int compare(Object obj, Object obj2) {
        return this.zzom.compare(this.zzon.zzok.get(((Integer) obj).intValue()), this.zzon.zzok.get(((Integer) obj2).intValue()));
    }
}
