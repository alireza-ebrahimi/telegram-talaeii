package com.google.android.gms.internal.measurement;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

final class zzacd implements Cloneable {
    private Object value;
    private zzacb<?, ?> zzbxo;
    private List<zzaci> zzbxp = new ArrayList();

    zzacd() {
    }

    private final byte[] toByteArray() {
        byte[] bArr = new byte[zza()];
        zza(zzaby.zzj(bArr));
        return bArr;
    }

    private final zzacd zzvp() {
        zzacd zzacd = new zzacd();
        try {
            zzacd.zzbxo = this.zzbxo;
            if (this.zzbxp == null) {
                zzacd.zzbxp = null;
            } else {
                zzacd.zzbxp.addAll(this.zzbxp);
            }
            if (this.value != null) {
                if (this.value instanceof zzacg) {
                    zzacd.value = (zzacg) ((zzacg) this.value).clone();
                } else if (this.value instanceof byte[]) {
                    zzacd.value = ((byte[]) this.value).clone();
                } else if (this.value instanceof byte[][]) {
                    byte[][] bArr = (byte[][]) this.value;
                    r4 = new byte[bArr.length][];
                    zzacd.value = r4;
                    for (r2 = 0; r2 < bArr.length; r2++) {
                        r4[r2] = (byte[]) bArr[r2].clone();
                    }
                } else if (this.value instanceof boolean[]) {
                    zzacd.value = ((boolean[]) this.value).clone();
                } else if (this.value instanceof int[]) {
                    zzacd.value = ((int[]) this.value).clone();
                } else if (this.value instanceof long[]) {
                    zzacd.value = ((long[]) this.value).clone();
                } else if (this.value instanceof float[]) {
                    zzacd.value = ((float[]) this.value).clone();
                } else if (this.value instanceof double[]) {
                    zzacd.value = ((double[]) this.value).clone();
                } else if (this.value instanceof zzacg[]) {
                    zzacg[] zzacgArr = (zzacg[]) this.value;
                    r4 = new zzacg[zzacgArr.length];
                    zzacd.value = r4;
                    for (r2 = 0; r2 < zzacgArr.length; r2++) {
                        r4[r2] = (zzacg) zzacgArr[r2].clone();
                    }
                }
            }
            return zzacd;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final /* synthetic */ Object clone() {
        return zzvp();
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzacd)) {
            return false;
        }
        zzacd zzacd = (zzacd) obj;
        if (this.value != null && zzacd.value != null) {
            return this.zzbxo == zzacd.zzbxo ? !this.zzbxo.zzbxh.isArray() ? this.value.equals(zzacd.value) : this.value instanceof byte[] ? Arrays.equals((byte[]) this.value, (byte[]) zzacd.value) : this.value instanceof int[] ? Arrays.equals((int[]) this.value, (int[]) zzacd.value) : this.value instanceof long[] ? Arrays.equals((long[]) this.value, (long[]) zzacd.value) : this.value instanceof float[] ? Arrays.equals((float[]) this.value, (float[]) zzacd.value) : this.value instanceof double[] ? Arrays.equals((double[]) this.value, (double[]) zzacd.value) : this.value instanceof boolean[] ? Arrays.equals((boolean[]) this.value, (boolean[]) zzacd.value) : Arrays.deepEquals((Object[]) this.value, (Object[]) zzacd.value) : false;
        } else {
            if (this.zzbxp != null && zzacd.zzbxp != null) {
                return this.zzbxp.equals(zzacd.zzbxp);
            }
            try {
                return Arrays.equals(toByteArray(), zzacd.toByteArray());
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

    final int zza() {
        int i = 0;
        int i2;
        if (this.value != null) {
            zzacb zzacb = this.zzbxo;
            Object obj = this.value;
            if (!zzacb.zzbxi) {
                return zzacb.zzv(obj);
            }
            int length = Array.getLength(obj);
            for (i2 = 0; i2 < length; i2++) {
                if (Array.get(obj, i2) != null) {
                    i += zzacb.zzv(Array.get(obj, i2));
                }
            }
            return i;
        }
        i2 = 0;
        for (zzaci zzaci : this.zzbxp) {
            i2 = (zzaci.zzbrm.length + (zzaby.zzas(zzaci.tag) + 0)) + i2;
        }
        return i2;
    }

    final void zza(zzaby zzaby) {
        if (this.value != null) {
            zzacb zzacb = this.zzbxo;
            Object obj = this.value;
            if (zzacb.zzbxi) {
                int length = Array.getLength(obj);
                for (int i = 0; i < length; i++) {
                    Object obj2 = Array.get(obj, i);
                    if (obj2 != null) {
                        zzacb.zza(obj2, zzaby);
                    }
                }
                return;
            }
            zzacb.zza(obj, zzaby);
            return;
        }
        for (zzaci zzaci : this.zzbxp) {
            zzaby.zzar(zzaci.tag);
            zzaby.zzk(zzaci.zzbrm);
        }
    }

    final void zza(zzaci zzaci) {
        if (this.zzbxp != null) {
            this.zzbxp.add(zzaci);
            return;
        }
        Object zzb;
        if (this.value instanceof zzacg) {
            byte[] bArr = zzaci.zzbrm;
            zzabx zza = zzabx.zza(bArr, 0, bArr.length);
            int zzvh = zza.zzvh();
            if (zzvh != bArr.length - zzaby.zzao(zzvh)) {
                throw zzacf.zzvq();
            }
            zzb = ((zzacg) this.value).zzb(zza);
        } else if (this.value instanceof zzacg[]) {
            zzacg[] zzacgArr = (zzacg[]) this.zzbxo.zzi(Collections.singletonList(zzaci));
            zzacg[] zzacgArr2 = (zzacg[]) this.value;
            zzacg[] zzacgArr3 = (zzacg[]) Arrays.copyOf(zzacgArr2, zzacgArr2.length + zzacgArr.length);
            System.arraycopy(zzacgArr, 0, zzacgArr3, zzacgArr2.length, zzacgArr.length);
        } else {
            zzb = this.zzbxo.zzi(Collections.singletonList(zzaci));
        }
        this.zzbxo = this.zzbxo;
        this.value = zzb;
        this.zzbxp = null;
    }

    final <T> T zzb(zzacb<?, T> zzacb) {
        if (this.value == null) {
            this.zzbxo = zzacb;
            this.value = zzacb.zzi(this.zzbxp);
            this.zzbxp = null;
        } else if (!this.zzbxo.equals(zzacb)) {
            throw new IllegalStateException("Tried to getExtension with a different Extension.");
        }
        return this.value;
    }
}
