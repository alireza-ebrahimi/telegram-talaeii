package com.google.android.gms.internal;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

final class zzflp implements Cloneable {
    private Object value;
    private zzfln<?, ?> zzpvr;
    private List<zzflu> zzpvs = new ArrayList();

    zzflp() {
    }

    private final byte[] toByteArray() throws IOException {
        byte[] bArr = new byte[zzq()];
        zza(zzflk.zzbf(bArr));
        return bArr;
    }

    private zzflp zzdcm() {
        zzflp zzflp = new zzflp();
        try {
            zzflp.zzpvr = this.zzpvr;
            if (this.zzpvs == null) {
                zzflp.zzpvs = null;
            } else {
                zzflp.zzpvs.addAll(this.zzpvs);
            }
            if (this.value != null) {
                if (this.value instanceof zzfls) {
                    zzflp.value = (zzfls) ((zzfls) this.value).clone();
                } else if (this.value instanceof byte[]) {
                    zzflp.value = ((byte[]) this.value).clone();
                } else if (this.value instanceof byte[][]) {
                    byte[][] bArr = (byte[][]) this.value;
                    r4 = new byte[bArr.length][];
                    zzflp.value = r4;
                    for (r2 = 0; r2 < bArr.length; r2++) {
                        r4[r2] = (byte[]) bArr[r2].clone();
                    }
                } else if (this.value instanceof boolean[]) {
                    zzflp.value = ((boolean[]) this.value).clone();
                } else if (this.value instanceof int[]) {
                    zzflp.value = ((int[]) this.value).clone();
                } else if (this.value instanceof long[]) {
                    zzflp.value = ((long[]) this.value).clone();
                } else if (this.value instanceof float[]) {
                    zzflp.value = ((float[]) this.value).clone();
                } else if (this.value instanceof double[]) {
                    zzflp.value = ((double[]) this.value).clone();
                } else if (this.value instanceof zzfls[]) {
                    zzfls[] zzflsArr = (zzfls[]) this.value;
                    r4 = new zzfls[zzflsArr.length];
                    zzflp.value = r4;
                    for (r2 = 0; r2 < zzflsArr.length; r2++) {
                        r4[r2] = (zzfls) zzflsArr[r2].clone();
                    }
                }
            }
            return zzflp;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        return zzdcm();
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzflp)) {
            return false;
        }
        zzflp zzflp = (zzflp) obj;
        if (this.value != null && zzflp.value != null) {
            return this.zzpvr == zzflp.zzpvr ? !this.zzpvr.zznro.isArray() ? this.value.equals(zzflp.value) : this.value instanceof byte[] ? Arrays.equals((byte[]) this.value, (byte[]) zzflp.value) : this.value instanceof int[] ? Arrays.equals((int[]) this.value, (int[]) zzflp.value) : this.value instanceof long[] ? Arrays.equals((long[]) this.value, (long[]) zzflp.value) : this.value instanceof float[] ? Arrays.equals((float[]) this.value, (float[]) zzflp.value) : this.value instanceof double[] ? Arrays.equals((double[]) this.value, (double[]) zzflp.value) : this.value instanceof boolean[] ? Arrays.equals((boolean[]) this.value, (boolean[]) zzflp.value) : Arrays.deepEquals((Object[]) this.value, (Object[]) zzflp.value) : false;
        } else {
            if (this.zzpvs != null && zzflp.zzpvs != null) {
                return this.zzpvs.equals(zzflp.zzpvs);
            }
            try {
                return Arrays.equals(toByteArray(), zzflp.toByteArray());
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

    final void zza(zzflk zzflk) throws IOException {
        if (this.value != null) {
            zzfln zzfln = this.zzpvr;
            Object obj = this.value;
            if (zzfln.zzpvm) {
                int length = Array.getLength(obj);
                for (int i = 0; i < length; i++) {
                    Object obj2 = Array.get(obj, i);
                    if (obj2 != null) {
                        zzfln.zza(obj2, zzflk);
                    }
                }
                return;
            }
            zzfln.zza(obj, zzflk);
            return;
        }
        for (zzflu zzflu : this.zzpvs) {
            zzflk.zzmy(zzflu.tag);
            zzflk.zzbh(zzflu.zzjwl);
        }
    }

    final void zza(zzflu zzflu) {
        this.zzpvs.add(zzflu);
    }

    final <T> T zzb(zzfln<?, T> zzfln) {
        if (this.value == null) {
            this.zzpvr = zzfln;
            this.value = zzfln.zzbq(this.zzpvs);
            this.zzpvs = null;
        } else if (!this.zzpvr.equals(zzfln)) {
            throw new IllegalStateException("Tried to getExtension with a different Extension.");
        }
        return this.value;
    }

    final int zzq() {
        int i = 0;
        int i2;
        if (this.value != null) {
            zzfln zzfln = this.zzpvr;
            Object obj = this.value;
            if (!zzfln.zzpvm) {
                return zzfln.zzcw(obj);
            }
            int length = Array.getLength(obj);
            for (i2 = 0; i2 < length; i2++) {
                if (Array.get(obj, i2) != null) {
                    i += zzfln.zzcw(Array.get(obj, i2));
                }
            }
            return i;
        }
        i2 = 0;
        for (zzflu zzflu : this.zzpvs) {
            i2 = (zzflu.zzjwl.length + (zzflk.zzmf(zzflu.tag) + 0)) + i2;
        }
        return i2;
    }
}
