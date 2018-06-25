package com.google.android.gms.internal.wearable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

final class zzq implements Cloneable {
    private Object value;
    private zzo<?, ?> zzhi;
    private List<zzv> zzhj = new ArrayList();

    zzq() {
    }

    private final byte[] toByteArray() {
        byte[] bArr = new byte[zzg()];
        zza(zzl.zzb(bArr));
        return bArr;
    }

    private final zzq zzt() {
        zzq zzq = new zzq();
        try {
            zzq.zzhi = this.zzhi;
            if (this.zzhj == null) {
                zzq.zzhj = null;
            } else {
                zzq.zzhj.addAll(this.zzhj);
            }
            if (this.value != null) {
                if (this.value instanceof zzt) {
                    zzq.value = (zzt) ((zzt) this.value).clone();
                } else if (this.value instanceof byte[]) {
                    zzq.value = ((byte[]) this.value).clone();
                } else if (this.value instanceof byte[][]) {
                    byte[][] bArr = (byte[][]) this.value;
                    r4 = new byte[bArr.length][];
                    zzq.value = r4;
                    for (r2 = 0; r2 < bArr.length; r2++) {
                        r4[r2] = (byte[]) bArr[r2].clone();
                    }
                } else if (this.value instanceof boolean[]) {
                    zzq.value = ((boolean[]) this.value).clone();
                } else if (this.value instanceof int[]) {
                    zzq.value = ((int[]) this.value).clone();
                } else if (this.value instanceof long[]) {
                    zzq.value = ((long[]) this.value).clone();
                } else if (this.value instanceof float[]) {
                    zzq.value = ((float[]) this.value).clone();
                } else if (this.value instanceof double[]) {
                    zzq.value = ((double[]) this.value).clone();
                } else if (this.value instanceof zzt[]) {
                    zzt[] zztArr = (zzt[]) this.value;
                    r4 = new zzt[zztArr.length];
                    zzq.value = r4;
                    for (r2 = 0; r2 < zztArr.length; r2++) {
                        r4[r2] = (zzt) zztArr[r2].clone();
                    }
                }
            }
            return zzq;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final /* synthetic */ Object clone() {
        return zzt();
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzq)) {
            return false;
        }
        zzq zzq = (zzq) obj;
        if (this.value != null && zzq.value != null) {
            return this.zzhi == zzq.zzhi ? !this.zzhi.zzhd.isArray() ? this.value.equals(zzq.value) : this.value instanceof byte[] ? Arrays.equals((byte[]) this.value, (byte[]) zzq.value) : this.value instanceof int[] ? Arrays.equals((int[]) this.value, (int[]) zzq.value) : this.value instanceof long[] ? Arrays.equals((long[]) this.value, (long[]) zzq.value) : this.value instanceof float[] ? Arrays.equals((float[]) this.value, (float[]) zzq.value) : this.value instanceof double[] ? Arrays.equals((double[]) this.value, (double[]) zzq.value) : this.value instanceof boolean[] ? Arrays.equals((boolean[]) this.value, (boolean[]) zzq.value) : Arrays.deepEquals((Object[]) this.value, (Object[]) zzq.value) : false;
        } else {
            if (this.zzhj != null && zzq.zzhj != null) {
                return this.zzhj.equals(zzq.zzhj);
            }
            try {
                return Arrays.equals(toByteArray(), zzq.toByteArray());
            } catch (Throwable e) {
                throw new IllegalStateException(e);
            }
        }
    }

    public final int hashCode() {
        try {
            return Arrays.hashCode(toByteArray()) + 527;
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    final void zza(zzl zzl) {
        if (this.value != null) {
            throw new NoSuchMethodError();
        }
        for (zzv zzv : this.zzhj) {
            zzl.zzl(zzv.tag);
            zzl.zzc(zzv.zzhm);
        }
    }

    final void zza(zzv zzv) {
        if (this.zzhj != null) {
            this.zzhj.add(zzv);
        } else if (this.value instanceof zzt) {
            byte[] bArr = zzv.zzhm;
            zzk zza = zzk.zza(bArr, 0, bArr.length);
            int zzk = zza.zzk();
            if (zzk != bArr.length - zzl.zzi(zzk)) {
                throw zzs.zzu();
            }
            zzt zza2 = ((zzt) this.value).zza(zza);
            this.zzhi = this.zzhi;
            this.value = zza2;
            this.zzhj = null;
        } else if (this.value instanceof zzt[]) {
            Collections.singletonList(zzv);
            throw new NoSuchMethodError();
        } else {
            Collections.singletonList(zzv);
            throw new NoSuchMethodError();
        }
    }

    final int zzg() {
        if (this.value != null) {
            throw new NoSuchMethodError();
        }
        int i = 0;
        for (zzv zzv : this.zzhj) {
            i = (zzv.zzhm.length + (zzl.zzm(zzv.tag) + 0)) + i;
        }
        return i;
    }
}
