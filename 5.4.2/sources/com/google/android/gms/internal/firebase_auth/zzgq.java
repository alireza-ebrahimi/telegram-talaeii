package com.google.android.gms.internal.firebase_auth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

final class zzgq implements Cloneable {
    private Object value;
    private zzgo<?, ?> zzxx;
    private List<zzgv> zzxy = new ArrayList();

    zzgq() {
    }

    private final byte[] toByteArray() {
        byte[] bArr = new byte[zzb()];
        zza(zzgl.zzf(bArr));
        return bArr;
    }

    private final zzgq zzgo() {
        zzgq zzgq = new zzgq();
        try {
            zzgq.zzxx = this.zzxx;
            if (this.zzxy == null) {
                zzgq.zzxy = null;
            } else {
                zzgq.zzxy.addAll(this.zzxy);
            }
            if (this.value != null) {
                if (this.value instanceof zzgt) {
                    zzgq.value = (zzgt) ((zzgt) this.value).clone();
                } else if (this.value instanceof byte[]) {
                    zzgq.value = ((byte[]) this.value).clone();
                } else if (this.value instanceof byte[][]) {
                    byte[][] bArr = (byte[][]) this.value;
                    r4 = new byte[bArr.length][];
                    zzgq.value = r4;
                    for (r2 = 0; r2 < bArr.length; r2++) {
                        r4[r2] = (byte[]) bArr[r2].clone();
                    }
                } else if (this.value instanceof boolean[]) {
                    zzgq.value = ((boolean[]) this.value).clone();
                } else if (this.value instanceof int[]) {
                    zzgq.value = ((int[]) this.value).clone();
                } else if (this.value instanceof long[]) {
                    zzgq.value = ((long[]) this.value).clone();
                } else if (this.value instanceof float[]) {
                    zzgq.value = ((float[]) this.value).clone();
                } else if (this.value instanceof double[]) {
                    zzgq.value = ((double[]) this.value).clone();
                } else if (this.value instanceof zzgt[]) {
                    zzgt[] zzgtArr = (zzgt[]) this.value;
                    r4 = new zzgt[zzgtArr.length];
                    zzgq.value = r4;
                    for (r2 = 0; r2 < zzgtArr.length; r2++) {
                        r4[r2] = (zzgt) zzgtArr[r2].clone();
                    }
                }
            }
            return zzgq;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final /* synthetic */ Object clone() {
        return zzgo();
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzgq)) {
            return false;
        }
        zzgq zzgq = (zzgq) obj;
        if (this.value != null && zzgq.value != null) {
            return this.zzxx == zzgq.zzxx ? !this.zzxx.zzxs.isArray() ? this.value.equals(zzgq.value) : this.value instanceof byte[] ? Arrays.equals((byte[]) this.value, (byte[]) zzgq.value) : this.value instanceof int[] ? Arrays.equals((int[]) this.value, (int[]) zzgq.value) : this.value instanceof long[] ? Arrays.equals((long[]) this.value, (long[]) zzgq.value) : this.value instanceof float[] ? Arrays.equals((float[]) this.value, (float[]) zzgq.value) : this.value instanceof double[] ? Arrays.equals((double[]) this.value, (double[]) zzgq.value) : this.value instanceof boolean[] ? Arrays.equals((boolean[]) this.value, (boolean[]) zzgq.value) : Arrays.deepEquals((Object[]) this.value, (Object[]) zzgq.value) : false;
        } else {
            if (this.zzxy != null && zzgq.zzxy != null) {
                return this.zzxy.equals(zzgq.zzxy);
            }
            try {
                return Arrays.equals(toByteArray(), zzgq.toByteArray());
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

    final void zza(zzgl zzgl) {
        if (this.value != null) {
            throw new NoSuchMethodError();
        }
        for (zzgv zzgv : this.zzxy) {
            zzgl.zzba(zzgv.tag);
            zzgl.zzg(zzgv.zzmp);
        }
    }

    final void zza(zzgv zzgv) {
        if (this.zzxy != null) {
            this.zzxy.add(zzgv);
        } else if (this.value instanceof zzgt) {
            byte[] bArr = zzgv.zzmp;
            zzgk zzi = zzgk.zzi(bArr, 0, bArr.length);
            int zzcu = zzi.zzcu();
            if (zzcu != bArr.length - zzgl.zzab(zzcu)) {
                throw zzgs.zzgp();
            }
            zzgt zza = ((zzgt) this.value).zza(zzi);
            this.zzxx = this.zzxx;
            this.value = zza;
            this.zzxy = null;
        } else if (this.value instanceof zzgt[]) {
            Collections.singletonList(zzgv);
            throw new NoSuchMethodError();
        } else {
            Collections.singletonList(zzgv);
            throw new NoSuchMethodError();
        }
    }

    final int zzb() {
        if (this.value != null) {
            throw new NoSuchMethodError();
        }
        int i = 0;
        for (zzgv zzgv : this.zzxy) {
            i = (zzgv.zzmp.length + (zzgl.zzai(zzgv.tag) + 0)) + i;
        }
        return i;
    }
}
