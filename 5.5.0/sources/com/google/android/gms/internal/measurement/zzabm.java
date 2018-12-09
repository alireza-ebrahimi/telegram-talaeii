package com.google.android.gms.internal.measurement;

public final class zzabm {
    private static final zzabm zzbut = new zzabm(0, new int[0], new Object[0], false);
    private int count;
    private int zzbsj;
    private int[] zzbuu;
    private Object[] zzbuv;
    private boolean zzbuw;

    private zzabm() {
        this(0, new int[8], new Object[8], true);
    }

    private zzabm(int i, int[] iArr, Object[] objArr, boolean z) {
        this.zzbsj = -1;
        this.count = 0;
        this.zzbuu = iArr;
        this.zzbuv = objArr;
        this.zzbuw = z;
    }

    public static zzabm zzuz() {
        return zzbut;
    }

    public final boolean equals(Object obj) {
        return this == obj ? true : obj == null ? false : obj instanceof zzabm;
    }

    public final int hashCode() {
        return 506991;
    }
}
