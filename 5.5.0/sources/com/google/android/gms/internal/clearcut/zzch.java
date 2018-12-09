package com.google.android.gms.internal.clearcut;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class zzch extends zzav<Integer> implements zzcn<Integer>, RandomAccess {
    private static final zzch zzkr;
    private int size;
    private int[] zzks;

    static {
        zzav zzch = new zzch();
        zzkr = zzch;
        zzch.zzv();
    }

    zzch() {
        this(new int[10], 0);
    }

    private zzch(int[] iArr, int i) {
        this.zzks = iArr;
        this.size = i;
    }

    public static zzch zzbk() {
        return zzkr;
    }

    private final void zzg(int i) {
        if (i < 0 || i >= this.size) {
            throw new IndexOutOfBoundsException(zzh(i));
        }
    }

    private final String zzh(int i) {
        return "Index:" + i + ", Size:" + this.size;
    }

    private final void zzo(int i, int i2) {
        zzw();
        if (i < 0 || i > this.size) {
            throw new IndexOutOfBoundsException(zzh(i));
        }
        if (this.size < this.zzks.length) {
            System.arraycopy(this.zzks, i, this.zzks, i + 1, this.size - i);
        } else {
            Object obj = new int[(((this.size * 3) / 2) + 1)];
            System.arraycopy(this.zzks, 0, obj, 0, i);
            System.arraycopy(this.zzks, i, obj, i + 1, this.size - i);
            this.zzks = obj;
        }
        this.zzks[i] = i2;
        this.size++;
        this.modCount++;
    }

    public final /* synthetic */ void add(int i, Object obj) {
        zzo(i, ((Integer) obj).intValue());
    }

    public final boolean addAll(Collection<? extends Integer> collection) {
        zzw();
        zzci.checkNotNull(collection);
        if (!(collection instanceof zzch)) {
            return super.addAll(collection);
        }
        zzch zzch = (zzch) collection;
        if (zzch.size == 0) {
            return false;
        }
        if (Integer.MAX_VALUE - this.size < zzch.size) {
            throw new OutOfMemoryError();
        }
        int i = this.size + zzch.size;
        if (i > this.zzks.length) {
            this.zzks = Arrays.copyOf(this.zzks, i);
        }
        System.arraycopy(zzch.zzks, 0, this.zzks, this.size, zzch.size);
        this.size = i;
        this.modCount++;
        return true;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzch)) {
            return super.equals(obj);
        }
        zzch zzch = (zzch) obj;
        if (this.size != zzch.size) {
            return false;
        }
        int[] iArr = zzch.zzks;
        for (int i = 0; i < this.size; i++) {
            if (this.zzks[i] != iArr[i]) {
                return false;
            }
        }
        return true;
    }

    public final /* synthetic */ Object get(int i) {
        return Integer.valueOf(getInt(i));
    }

    public final int getInt(int i) {
        zzg(i);
        return this.zzks[i];
    }

    public final int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.size; i2++) {
            i = (i * 31) + this.zzks[i2];
        }
        return i;
    }

    public final /* synthetic */ Object remove(int i) {
        zzw();
        zzg(i);
        int i2 = this.zzks[i];
        if (i < this.size - 1) {
            System.arraycopy(this.zzks, i + 1, this.zzks, i, this.size - i);
        }
        this.size--;
        this.modCount++;
        return Integer.valueOf(i2);
    }

    public final boolean remove(Object obj) {
        zzw();
        for (int i = 0; i < this.size; i++) {
            if (obj.equals(Integer.valueOf(this.zzks[i]))) {
                System.arraycopy(this.zzks, i + 1, this.zzks, i, this.size - i);
                this.size--;
                this.modCount++;
                return true;
            }
        }
        return false;
    }

    protected final void removeRange(int i, int i2) {
        zzw();
        if (i2 < i) {
            throw new IndexOutOfBoundsException("toIndex < fromIndex");
        }
        System.arraycopy(this.zzks, i2, this.zzks, i, this.size - i2);
        this.size -= i2 - i;
        this.modCount++;
    }

    public final /* synthetic */ Object set(int i, Object obj) {
        int intValue = ((Integer) obj).intValue();
        zzw();
        zzg(i);
        int i2 = this.zzks[i];
        this.zzks[i] = intValue;
        return Integer.valueOf(i2);
    }

    public final int size() {
        return this.size;
    }

    public final void zzac(int i) {
        zzo(this.size, i);
    }

    public final /* synthetic */ zzcn zzi(int i) {
        if (i >= this.size) {
            return new zzch(Arrays.copyOf(this.zzks, i), this.size);
        }
        throw new IllegalArgumentException();
    }
}
