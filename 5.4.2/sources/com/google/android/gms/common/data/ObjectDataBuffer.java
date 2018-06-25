package com.google.android.gms.common.data;

import android.os.Bundle;
import com.google.android.gms.common.data.DataBufferObserver.Observable;
import com.google.android.gms.common.internal.Asserts;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

public final class ObjectDataBuffer<T> extends AbstractDataBuffer<T> implements Observable, ObjectExclusionFilterable<T> {
    private final ArrayList<Integer> zzob;
    private final HashSet<Integer> zzoe;
    private DataBufferObserverSet zzof;
    private final ArrayList<T> zzog;

    public ObjectDataBuffer() {
        super(null);
        this.zzoe = new HashSet();
        this.zzob = new ArrayList();
        this.zzog = new ArrayList();
        this.zzof = new DataBufferObserverSet();
        zzcl();
    }

    public ObjectDataBuffer(ArrayList<T> arrayList) {
        super(null);
        this.zzoe = new HashSet();
        this.zzob = new ArrayList();
        this.zzog = arrayList;
        this.zzof = new DataBufferObserverSet();
        zzcl();
    }

    public ObjectDataBuffer(T... tArr) {
        super(null);
        this.zzoe = new HashSet();
        this.zzob = new ArrayList();
        this.zzog = new ArrayList(Arrays.asList(tArr));
        this.zzof = new DataBufferObserverSet();
        zzcl();
    }

    private final void zzcl() {
        this.zzob.clear();
        int size = this.zzog.size();
        for (int i = 0; i < size; i++) {
            if (!this.zzoe.contains(Integer.valueOf(i))) {
                this.zzob.add(Integer.valueOf(i));
            }
        }
    }

    public final void add(T t) {
        boolean z = false;
        int size = this.zzog.size();
        this.zzog.add(t);
        zzcl();
        if (this.zzof.hasObservers()) {
            Asserts.checkState(!this.zzoe.contains(Integer.valueOf(size)));
            int size2 = this.zzob.size();
            Asserts.checkState(size2 > 0);
            if (((Integer) this.zzob.get(size2 - 1)).intValue() == size) {
                z = true;
            }
            Asserts.checkState(z);
            this.zzof.onDataRangeInserted(size2 - 1, 1);
        }
    }

    public final void addObserver(DataBufferObserver dataBufferObserver) {
        this.zzof.addObserver(dataBufferObserver);
    }

    public final void filterOut(T t) {
        int size = this.zzog.size();
        int i = 0;
        int i2 = -1;
        int i3 = -1;
        Object obj = null;
        int i4 = -1;
        while (i < size) {
            int i5;
            Object obj2;
            int i6;
            if (!this.zzoe.contains(Integer.valueOf(i))) {
                i4++;
                if (t.equals(this.zzog.get(i))) {
                    this.zzoe.add(Integer.valueOf(i));
                    if (!this.zzof.hasObservers()) {
                        i5 = i4;
                        i4 = i3;
                        i3 = 1;
                    } else if (i3 < 0) {
                        i2 = 1;
                        obj2 = 1;
                        i5 = i4;
                    } else {
                        i2++;
                        i5 = i4;
                        i4 = i3;
                        i3 = 1;
                    }
                } else if (i3 >= 0) {
                    zzcl();
                    this.zzof.onDataRangeRemoved(i3, i2);
                    i4 -= i2;
                    i2 = -1;
                    obj2 = null;
                    i5 = i4;
                    i4 = -1;
                }
                i++;
                i6 = i4;
                i4 = i5;
                obj = obj2;
                i3 = i6;
            }
            i6 = i3;
            obj2 = obj;
            i5 = i4;
            i4 = i6;
            i++;
            i6 = i4;
            i4 = i5;
            obj = obj2;
            i3 = i6;
        }
        if (obj != null) {
            zzcl();
        }
        if (i3 >= 0) {
            this.zzof.onDataRangeRemoved(i3, i2);
        }
    }

    public final void filterOutRaw(int i) {
        int i2;
        boolean add = this.zzoe.add(Integer.valueOf(i));
        if (this.zzof.hasObservers() && add) {
            int size = this.zzob.size();
            for (int i3 = 0; i3 < size; i3++) {
                if (((Integer) this.zzob.get(i3)).intValue() == i) {
                    i2 = i3;
                    break;
                }
            }
        }
        i2 = -1;
        zzcl();
        if (i2 >= 0) {
            this.zzof.onDataRangeRemoved(i2, 1);
        }
    }

    public final T get(int i) {
        return this.zzog.get(getRawPosition(i));
    }

    public final int getCount() {
        return this.zzog.size() - this.zzoe.size();
    }

    public final Bundle getMetadata() {
        return null;
    }

    public final int getPositionFromRawPosition(int i) {
        int i2 = -1;
        for (int i3 = 0; i3 <= i; i3++) {
            if (!this.zzoe.contains(Integer.valueOf(i3))) {
                i2++;
            }
        }
        return i2;
    }

    public final T getRaw(int i) {
        return this.zzog.get(i);
    }

    public final int getRawCount() {
        return this.zzog.size();
    }

    public final int getRawPosition(int i) {
        if (i >= 0 && i < getCount()) {
            return ((Integer) this.zzob.get(i)).intValue();
        }
        throw new IllegalArgumentException("Position " + i + " is out of bounds for this buffer");
    }

    public final void insertRaw(int i, T t) {
        this.zzog.add(i, t);
        HashSet hashSet = new HashSet(this.zzoe.size());
        Iterator it = this.zzoe.iterator();
        int i2 = i;
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            if (num.intValue() < i) {
                i2--;
            } else {
                hashSet.add(Integer.valueOf(num.intValue() + 1));
                it.remove();
            }
        }
        Iterator it2 = hashSet.iterator();
        while (it2.hasNext()) {
            this.zzoe.add((Integer) it2.next());
        }
        zzcl();
        if (this.zzof.hasObservers()) {
            this.zzof.onDataRangeInserted(i2, 1);
        }
    }

    public final boolean isRawPositionFiltered(int i) {
        return this.zzoe.contains(Integer.valueOf(i));
    }

    public final void notifyChanged(T t) {
        if (this.zzof.hasObservers()) {
            int size = this.zzob.size();
            for (int i = 0; i < size; i++) {
                if (t.equals(this.zzog.get(((Integer) this.zzob.get(i)).intValue()))) {
                    this.zzof.onDataRangeChanged(i, 1);
                }
            }
        }
    }

    public final void release() {
        this.zzof.clear();
    }

    public final void removeObserver(DataBufferObserver dataBufferObserver) {
        this.zzof.removeObserver(dataBufferObserver);
    }

    public final void removeRaw(int i) {
        this.zzog.remove(i);
        boolean remove = this.zzoe.remove(Integer.valueOf(i));
        HashSet hashSet = new HashSet(this.zzoe.size());
        Iterator it = this.zzoe.iterator();
        int i2 = i;
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            if (num.intValue() < i) {
                i2--;
            } else {
                it.remove();
                hashSet.add(Integer.valueOf(num.intValue() - 1));
            }
        }
        Iterator it2 = hashSet.iterator();
        while (it2.hasNext()) {
            this.zzoe.add((Integer) it2.next());
        }
        zzcl();
        if (!remove && this.zzof.hasObservers()) {
            this.zzof.onDataRangeRemoved(i2, 1);
        }
    }

    public final boolean setRaw(int i, T t) {
        this.zzog.set(i, t);
        boolean z = !this.zzoe.contains(Integer.valueOf(i));
        if (z && this.zzof.hasObservers()) {
            int size = this.zzob.size();
            for (int i2 = 0; i2 < size; i2++) {
                if (((Integer) this.zzob.get(i2)).intValue() == i) {
                    this.zzof.onDataRangeChanged(i2, 1);
                    break;
                }
            }
        }
        return z;
    }

    public final void unfilter(T t) {
        int size = this.zzog.size();
        int i = 0;
        int i2 = -1;
        int i3 = -1;
        Object obj = null;
        int i4 = 0;
        while (i < size) {
            int i5;
            Object obj2;
            Object obj3;
            if (!this.zzoe.contains(Integer.valueOf(i))) {
                i4++;
                if (i3 >= 0) {
                    zzcl();
                    this.zzof.onDataRangeInserted(i3, i2);
                    i4 += i2;
                    i2 = -1;
                    i3 = -1;
                    i5 = i4;
                    obj2 = null;
                }
                obj3 = obj;
                i5 = i4;
                obj2 = obj3;
            } else if (t.equals(this.zzog.get(i))) {
                this.zzoe.remove(Integer.valueOf(i));
                if (!this.zzof.hasObservers()) {
                    i5 = i4;
                    i4 = 1;
                } else if (i3 < 0) {
                    i2 = 1;
                    i3 = i4;
                    i5 = i4;
                    i4 = 1;
                } else {
                    i2++;
                    i5 = i4;
                    i4 = 1;
                }
            } else {
                if (this.zzof.hasObservers() && i3 >= 0) {
                    zzcl();
                    this.zzof.onDataRangeInserted(i3, i2);
                    i4 += i2;
                    i2 = -1;
                    i3 = -1;
                    i5 = i4;
                    obj2 = null;
                }
                obj3 = obj;
                i5 = i4;
                obj2 = obj3;
            }
            i++;
            obj3 = obj2;
            i4 = i5;
            obj = obj3;
        }
        if (obj != null) {
            zzcl();
        }
        if (i3 >= 0) {
            this.zzof.onDataRangeInserted(i3, i2);
        }
    }

    public final void unfilterRaw(int i) {
        boolean remove = this.zzoe.remove(Integer.valueOf(i));
        zzcl();
        if (this.zzof.hasObservers() && remove) {
            int i2;
            int size = this.zzob.size();
            for (int i3 = 0; i3 < size; i3++) {
                if (((Integer) this.zzob.get(i3)).intValue() == i) {
                    i2 = i3;
                    break;
                }
            }
            i2 = -1;
            if (i2 >= 0) {
                this.zzof.onDataRangeInserted(i2, 1);
            }
        }
    }
}
