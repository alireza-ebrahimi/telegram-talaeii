package com.google.android.gms.common.data;

import java.util.ArrayList;
import java.util.HashSet;

public final class PositionFilteredDataBuffer<T> extends FilteredDataBuffer<T> {
    private final ArrayList<Integer> zzob = new ArrayList();
    private final HashSet<Integer> zzoe = new HashSet();

    public PositionFilteredDataBuffer(AbstractDataBuffer<T> abstractDataBuffer) {
        super(abstractDataBuffer);
        zzcl();
    }

    private final void zzcl() {
        this.zzob.clear();
        int count = this.mDataBuffer.getCount();
        for (int i = 0; i < count; i++) {
            if (!this.zzoe.contains(Integer.valueOf(i))) {
                this.zzob.add(Integer.valueOf(i));
            }
        }
    }

    public final void clearFilters() {
        this.zzoe.clear();
        zzcl();
    }

    public final int computeRealPosition(int i) {
        if (i >= 0 && i < getCount()) {
            return ((Integer) this.zzob.get(i)).intValue();
        }
        throw new IllegalArgumentException("Position " + i + " is out of bounds for this buffer");
    }

    public final void filterOut(int i) {
        if (i >= 0 && i <= this.mDataBuffer.getCount()) {
            this.zzoe.add(Integer.valueOf(i));
            zzcl();
        }
    }

    public final int getCount() {
        return this.mDataBuffer.getCount() - this.zzoe.size();
    }

    public final void unfilter(int i) {
        this.zzoe.remove(Integer.valueOf(i));
        zzcl();
    }
}
