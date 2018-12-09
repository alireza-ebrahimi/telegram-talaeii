package com.google.android.gms.internal.clearcut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

final class zzfx implements Cloneable {
    private Object value;
    private zzfv<?, ?> zzrp;
    private List<Object> zzrq = new ArrayList();

    zzfx() {
    }

    private final byte[] toByteArray() {
        byte[] bArr = new byte[zzen()];
        zza(zzfs.zzg(bArr));
        return bArr;
    }

    private final zzfx zzeq() {
        zzfx zzfx = new zzfx();
        try {
            zzfx.zzrp = this.zzrp;
            if (this.zzrq == null) {
                zzfx.zzrq = null;
            } else {
                zzfx.zzrq.addAll(this.zzrq);
            }
            if (this.value != null) {
                if (this.value instanceof zzfz) {
                    zzfx.value = (zzfz) ((zzfz) this.value).clone();
                } else if (this.value instanceof byte[]) {
                    zzfx.value = ((byte[]) this.value).clone();
                } else if (this.value instanceof byte[][]) {
                    byte[][] bArr = (byte[][]) this.value;
                    r4 = new byte[bArr.length][];
                    zzfx.value = r4;
                    for (r2 = 0; r2 < bArr.length; r2++) {
                        r4[r2] = (byte[]) bArr[r2].clone();
                    }
                } else if (this.value instanceof boolean[]) {
                    zzfx.value = ((boolean[]) this.value).clone();
                } else if (this.value instanceof int[]) {
                    zzfx.value = ((int[]) this.value).clone();
                } else if (this.value instanceof long[]) {
                    zzfx.value = ((long[]) this.value).clone();
                } else if (this.value instanceof float[]) {
                    zzfx.value = ((float[]) this.value).clone();
                } else if (this.value instanceof double[]) {
                    zzfx.value = ((double[]) this.value).clone();
                } else if (this.value instanceof zzfz[]) {
                    zzfz[] zzfzArr = (zzfz[]) this.value;
                    r4 = new zzfz[zzfzArr.length];
                    zzfx.value = r4;
                    for (r2 = 0; r2 < zzfzArr.length; r2++) {
                        r4[r2] = (zzfz) zzfzArr[r2].clone();
                    }
                }
            }
            return zzfx;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final /* synthetic */ Object clone() {
        return zzeq();
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzfx)) {
            return false;
        }
        zzfx zzfx = (zzfx) obj;
        if (this.value != null && zzfx.value != null) {
            return this.zzrp == zzfx.zzrp ? !this.zzrp.zzrk.isArray() ? this.value.equals(zzfx.value) : this.value instanceof byte[] ? Arrays.equals((byte[]) this.value, (byte[]) zzfx.value) : this.value instanceof int[] ? Arrays.equals((int[]) this.value, (int[]) zzfx.value) : this.value instanceof long[] ? Arrays.equals((long[]) this.value, (long[]) zzfx.value) : this.value instanceof float[] ? Arrays.equals((float[]) this.value, (float[]) zzfx.value) : this.value instanceof double[] ? Arrays.equals((double[]) this.value, (double[]) zzfx.value) : this.value instanceof boolean[] ? Arrays.equals((boolean[]) this.value, (boolean[]) zzfx.value) : Arrays.deepEquals((Object[]) this.value, (Object[]) zzfx.value) : false;
        } else {
            if (this.zzrq != null && zzfx.zzrq != null) {
                return this.zzrq.equals(zzfx.zzrq);
            }
            try {
                return Arrays.equals(toByteArray(), zzfx.toByteArray());
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

    final void zza(zzfs zzfs) {
        if (this.value != null) {
            throw new NoSuchMethodError();
        }
        Iterator it = this.zzrq.iterator();
        if (it.hasNext()) {
            it.next();
            throw new NoSuchMethodError();
        }
    }

    final int zzen() {
        if (this.value != null) {
            throw new NoSuchMethodError();
        }
        Iterator it = this.zzrq.iterator();
        if (!it.hasNext()) {
            return 0;
        }
        it.next();
        throw new NoSuchMethodError();
    }
}
