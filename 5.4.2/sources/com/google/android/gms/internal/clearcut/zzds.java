package com.google.android.gms.internal.clearcut;

import com.google.android.gms.common.util.CrashUtils.ErrorDialogData;
import com.google.android.gms.internal.clearcut.zzcg.zzg;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import sun.misc.Unsafe;

final class zzds<T> implements zzef<T> {
    private static final Unsafe zzmh = zzfd.zzef();
    private final int[] zzmi;
    private final Object[] zzmj;
    private final int zzmk;
    private final int zzml;
    private final int zzmm;
    private final zzdo zzmn;
    private final boolean zzmo;
    private final boolean zzmp;
    private final boolean zzmq;
    private final boolean zzmr;
    private final int[] zzms;
    private final int[] zzmt;
    private final int[] zzmu;
    private final zzdw zzmv;
    private final zzcy zzmw;
    private final zzex<?, ?> zzmx;
    private final zzbu<?> zzmy;
    private final zzdj zzmz;

    private zzds(int[] iArr, Object[] objArr, int i, int i2, int i3, zzdo zzdo, boolean z, boolean z2, int[] iArr2, int[] iArr3, int[] iArr4, zzdw zzdw, zzcy zzcy, zzex<?, ?> zzex, zzbu<?> zzbu, zzdj zzdj) {
        this.zzmi = iArr;
        this.zzmj = objArr;
        this.zzmk = i;
        this.zzml = i2;
        this.zzmm = i3;
        this.zzmp = zzdo instanceof zzcg;
        this.zzmq = z;
        boolean z3 = zzbu != null && zzbu.zze(zzdo);
        this.zzmo = z3;
        this.zzmr = false;
        this.zzms = iArr2;
        this.zzmt = iArr3;
        this.zzmu = iArr4;
        this.zzmv = zzdw;
        this.zzmw = zzcy;
        this.zzmx = zzex;
        this.zzmy = zzbu;
        this.zzmn = zzdo;
        this.zzmz = zzdj;
    }

    private static int zza(int i, byte[] bArr, int i2, int i3, Object obj, zzay zzay) {
        return zzax.zza(i, bArr, i2, i3, zzn(obj), zzay);
    }

    private static int zza(zzef<?> zzef, int i, byte[] bArr, int i2, int i3, zzcn<?> zzcn, zzay zzay) {
        int zza = zza((zzef) zzef, bArr, i2, i3, zzay);
        zzcn.add(zzay.zzff);
        while (zza < i3) {
            int zza2 = zzax.zza(bArr, zza, zzay);
            if (i != zzay.zzfd) {
                break;
            }
            zza = zza((zzef) zzef, bArr, zza2, i3, zzay);
            zzcn.add(zzay.zzff);
        }
        return zza;
    }

    private static int zza(zzef zzef, byte[] bArr, int i, int i2, int i3, zzay zzay) {
        zzds zzds = (zzds) zzef;
        Object newInstance = zzds.newInstance();
        int zza = zzds.zza(newInstance, bArr, i, i2, i3, zzay);
        zzds.zzc(newInstance);
        zzay.zzff = newInstance;
        return zza;
    }

    private static int zza(zzef zzef, byte[] bArr, int i, int i2, zzay zzay) {
        int i3;
        int i4 = i + 1;
        int i5 = bArr[i];
        if (i5 < (byte) 0) {
            i4 = zzax.zza(i5, bArr, i4, zzay);
            i3 = zzay.zzfd;
        } else {
            i3 = i5;
        }
        if (i3 < 0 || i3 > i2 - i4) {
            throw zzco.zzbl();
        }
        Object newInstance = zzef.newInstance();
        zzef.zza(newInstance, bArr, i4, i4 + i3, zzay);
        zzef.zzc(newInstance);
        zzay.zzff = newInstance;
        return i4 + i3;
    }

    private static <UT, UB> int zza(zzex<UT, UB> zzex, T t) {
        return zzex.zzm(zzex.zzq(t));
    }

    private final int zza(T t, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, int i7, long j, int i8, zzay zzay) {
        int i9;
        Unsafe unsafe = zzmh;
        long j2 = (long) (this.zzmi[i8 + 2] & 1048575);
        int zzb;
        switch (i7) {
            case 51:
                if (i5 == 1) {
                    unsafe.putObject(t, j, Double.valueOf(zzax.zze(bArr, i)));
                    i9 = i + 8;
                    break;
                }
                return i;
            case 52:
                if (i5 == 5) {
                    unsafe.putObject(t, j, Float.valueOf(zzax.zzf(bArr, i)));
                    i9 = i + 4;
                    break;
                }
                return i;
            case 53:
            case 54:
                if (i5 == 0) {
                    i9 = zzax.zzb(bArr, i, zzay);
                    unsafe.putObject(t, j, Long.valueOf(zzay.zzfe));
                    break;
                }
                return i;
            case 55:
            case 62:
                if (i5 == 0) {
                    i9 = zzax.zza(bArr, i, zzay);
                    unsafe.putObject(t, j, Integer.valueOf(zzay.zzfd));
                    break;
                }
                return i;
            case 56:
            case 65:
                if (i5 == 1) {
                    unsafe.putObject(t, j, Long.valueOf(zzax.zzd(bArr, i)));
                    i9 = i + 8;
                    break;
                }
                return i;
            case 57:
            case 64:
                if (i5 == 5) {
                    unsafe.putObject(t, j, Integer.valueOf(zzax.zzc(bArr, i)));
                    i9 = i + 4;
                    break;
                }
                return i;
            case 58:
                if (i5 == 0) {
                    zzb = zzax.zzb(bArr, i, zzay);
                    unsafe.putObject(t, j, Boolean.valueOf(zzay.zzfe != 0));
                    i9 = zzb;
                    break;
                }
                return i;
            case 59:
                if (i5 != 2) {
                    return i;
                }
                i9 = zzax.zza(bArr, i, zzay);
                zzb = zzay.zzfd;
                if (zzb == 0) {
                    unsafe.putObject(t, j, TtmlNode.ANONYMOUS_REGION_ID);
                } else if ((ErrorDialogData.DYNAMITE_CRASH & i6) == 0 || zzff.zze(bArr, i9, i9 + zzb)) {
                    unsafe.putObject(t, j, new String(bArr, i9, zzb, zzci.UTF_8));
                    i9 += zzb;
                } else {
                    throw zzco.zzbp();
                }
                unsafe.putInt(t, j2, i4);
                return i9;
            case 60:
                if (i5 != 2) {
                    return i;
                }
                i = zza(zzad(i8), bArr, i, i2, zzay);
                Object object = unsafe.getInt(t, j2) == i4 ? unsafe.getObject(t, j) : null;
                if (object == null) {
                    unsafe.putObject(t, j, zzay.zzff);
                } else {
                    unsafe.putObject(t, j, zzci.zza(object, zzay.zzff));
                }
                unsafe.putInt(t, j2, i4);
                return i;
            case 61:
                if (i5 != 2) {
                    return i;
                }
                i9 = zzax.zza(bArr, i, zzay);
                zzb = zzay.zzfd;
                if (zzb == 0) {
                    unsafe.putObject(t, j, zzbb.zzfi);
                } else {
                    unsafe.putObject(t, j, zzbb.zzb(bArr, i9, zzb));
                    i9 += zzb;
                }
                unsafe.putInt(t, j2, i4);
                return i9;
            case 63:
                if (i5 != 0) {
                    return i;
                }
                i9 = zzax.zza(bArr, i, zzay);
                zzb = zzay.zzfd;
                zzck zzaf = zzaf(i8);
                if (zzaf == null || zzaf.zzb(zzb) != null) {
                    unsafe.putObject(t, j, Integer.valueOf(zzb));
                    break;
                }
                zzn(t).zzb(i3, Long.valueOf((long) zzb));
                return i9;
            case 66:
                if (i5 == 0) {
                    i9 = zzax.zza(bArr, i, zzay);
                    unsafe.putObject(t, j, Integer.valueOf(zzbk.zzm(zzay.zzfd)));
                    break;
                }
                return i;
            case 67:
                if (i5 == 0) {
                    i9 = zzax.zzb(bArr, i, zzay);
                    unsafe.putObject(t, j, Long.valueOf(zzbk.zza(zzay.zzfe)));
                    break;
                }
                return i;
            case 68:
                if (i5 == 3) {
                    i9 = zza(zzad(i8), bArr, i, i2, (i3 & -8) | 4, zzay);
                    Object object2 = unsafe.getInt(t, j2) == i4 ? unsafe.getObject(t, j) : null;
                    if (object2 != null) {
                        unsafe.putObject(t, j, zzci.zza(object2, zzay.zzff));
                        break;
                    }
                    unsafe.putObject(t, j, zzay.zzff);
                    break;
                }
                return i;
            default:
                return i;
        }
        unsafe.putInt(t, j2, i4);
        return i9;
    }

    private final int zza(T t, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, long j, int i7, long j2, zzay zzay) {
        zzcn zzcn;
        zzcn zzcn2 = (zzcn) zzmh.getObject(t, j2);
        if (zzcn2.zzu()) {
            zzcn = zzcn2;
        } else {
            int size = zzcn2.size();
            zzcn = zzcn2.zzi(size == 0 ? 10 : size << 1);
            zzmh.putObject(t, j2, zzcn);
        }
        int i8;
        zzdc zzdc;
        zzch zzch;
        switch (i7) {
            case 18:
            case 35:
                zzbq zzbq;
                if (i5 == 2) {
                    zzbq = (zzbq) zzcn;
                    i = zzax.zza(bArr, i, zzay);
                    i8 = zzay.zzfd + i;
                    while (i < i8) {
                        zzbq.zzc(zzax.zze(bArr, i));
                        i += 8;
                    }
                    if (i == i8) {
                        return i;
                    }
                    throw zzco.zzbl();
                } else if (i5 != 1) {
                    return i;
                } else {
                    zzbq = (zzbq) zzcn;
                    zzbq.zzc(zzax.zze(bArr, i));
                    i += 8;
                    while (i < i2) {
                        i8 = zzax.zza(bArr, i, zzay);
                        if (i3 != zzay.zzfd) {
                            return i;
                        }
                        zzbq.zzc(zzax.zze(bArr, i8));
                        i = i8 + 8;
                    }
                    return i;
                }
            case 19:
            case 36:
                zzce zzce;
                if (i5 == 2) {
                    zzce = (zzce) zzcn;
                    i = zzax.zza(bArr, i, zzay);
                    i8 = zzay.zzfd + i;
                    while (i < i8) {
                        zzce.zzc(zzax.zzf(bArr, i));
                        i += 4;
                    }
                    if (i == i8) {
                        return i;
                    }
                    throw zzco.zzbl();
                } else if (i5 != 5) {
                    return i;
                } else {
                    zzce = (zzce) zzcn;
                    zzce.zzc(zzax.zzf(bArr, i));
                    i += 4;
                    while (i < i2) {
                        i8 = zzax.zza(bArr, i, zzay);
                        if (i3 != zzay.zzfd) {
                            return i;
                        }
                        zzce.zzc(zzax.zzf(bArr, i8));
                        i = i8 + 4;
                    }
                    return i;
                }
            case 20:
            case 21:
            case 37:
            case 38:
                if (i5 == 2) {
                    zzdc = (zzdc) zzcn;
                    i = zzax.zza(bArr, i, zzay);
                    i8 = zzay.zzfd + i;
                    while (i < i8) {
                        i = zzax.zzb(bArr, i, zzay);
                        zzdc.zzm(zzay.zzfe);
                    }
                    if (i == i8) {
                        return i;
                    }
                    throw zzco.zzbl();
                } else if (i5 != 0) {
                    return i;
                } else {
                    zzdc = (zzdc) zzcn;
                    i = zzax.zzb(bArr, i, zzay);
                    zzdc.zzm(zzay.zzfe);
                    while (i < i2) {
                        i8 = zzax.zza(bArr, i, zzay);
                        if (i3 != zzay.zzfd) {
                            return i;
                        }
                        i = zzax.zzb(bArr, i8, zzay);
                        zzdc.zzm(zzay.zzfe);
                    }
                    return i;
                }
            case 22:
            case 29:
            case 39:
            case 43:
                return i5 == 2 ? zzax.zza(bArr, i, zzcn, zzay) : i5 == 0 ? zzax.zza(i3, bArr, i, i2, zzcn, zzay) : i;
            case 23:
            case 32:
            case 40:
            case 46:
                if (i5 == 2) {
                    zzdc = (zzdc) zzcn;
                    i = zzax.zza(bArr, i, zzay);
                    i8 = zzay.zzfd + i;
                    while (i < i8) {
                        zzdc.zzm(zzax.zzd(bArr, i));
                        i += 8;
                    }
                    if (i == i8) {
                        return i;
                    }
                    throw zzco.zzbl();
                } else if (i5 != 1) {
                    return i;
                } else {
                    zzdc = (zzdc) zzcn;
                    zzdc.zzm(zzax.zzd(bArr, i));
                    i += 8;
                    while (i < i2) {
                        i8 = zzax.zza(bArr, i, zzay);
                        if (i3 != zzay.zzfd) {
                            return i;
                        }
                        zzdc.zzm(zzax.zzd(bArr, i8));
                        i = i8 + 8;
                    }
                    return i;
                }
            case 24:
            case 31:
            case 41:
            case 45:
                if (i5 == 2) {
                    zzch = (zzch) zzcn;
                    i = zzax.zza(bArr, i, zzay);
                    i8 = zzay.zzfd + i;
                    while (i < i8) {
                        zzch.zzac(zzax.zzc(bArr, i));
                        i += 4;
                    }
                    if (i == i8) {
                        return i;
                    }
                    throw zzco.zzbl();
                } else if (i5 != 5) {
                    return i;
                } else {
                    zzch = (zzch) zzcn;
                    zzch.zzac(zzax.zzc(bArr, i));
                    i += 4;
                    while (i < i2) {
                        i8 = zzax.zza(bArr, i, zzay);
                        if (i3 != zzay.zzfd) {
                            return i;
                        }
                        zzch.zzac(zzax.zzc(bArr, i8));
                        i = i8 + 4;
                    }
                    return i;
                }
            case 25:
            case 42:
                zzaz zzaz;
                if (i5 == 2) {
                    zzaz = (zzaz) zzcn;
                    i = zzax.zza(bArr, i, zzay);
                    size = i + zzay.zzfd;
                    while (i < size) {
                        i = zzax.zzb(bArr, i, zzay);
                        zzaz.addBoolean(zzay.zzfe != 0);
                    }
                    if (i == size) {
                        return i;
                    }
                    throw zzco.zzbl();
                } else if (i5 != 0) {
                    return i;
                } else {
                    zzaz = (zzaz) zzcn;
                    i = zzax.zzb(bArr, i, zzay);
                    zzaz.addBoolean(zzay.zzfe != 0);
                    while (i < i2) {
                        i8 = zzax.zza(bArr, i, zzay);
                        if (i3 != zzay.zzfd) {
                            return i;
                        }
                        i = zzax.zzb(bArr, i8, zzay);
                        zzaz.addBoolean(zzay.zzfe != 0);
                    }
                    return i;
                }
            case 26:
                if (i5 != 2) {
                    return i;
                }
                if ((536870912 & j) == 0) {
                    i = zzax.zza(bArr, i, zzay);
                    i8 = zzay.zzfd;
                    if (i8 == 0) {
                        zzcn.add(TtmlNode.ANONYMOUS_REGION_ID);
                    } else {
                        zzcn.add(new String(bArr, i, i8, zzci.UTF_8));
                        i += i8;
                    }
                    while (i < i2) {
                        i8 = zzax.zza(bArr, i, zzay);
                        if (i3 != zzay.zzfd) {
                            return i;
                        }
                        i = zzax.zza(bArr, i8, zzay);
                        i8 = zzay.zzfd;
                        if (i8 == 0) {
                            zzcn.add(TtmlNode.ANONYMOUS_REGION_ID);
                        } else {
                            zzcn.add(new String(bArr, i, i8, zzci.UTF_8));
                            i += i8;
                        }
                    }
                    return i;
                }
                i = zzax.zza(bArr, i, zzay);
                i8 = zzay.zzfd;
                if (i8 == 0) {
                    zzcn.add(TtmlNode.ANONYMOUS_REGION_ID);
                } else {
                    if (zzff.zze(bArr, i, i + i8)) {
                        zzcn.add(new String(bArr, i, i8, zzci.UTF_8));
                        i += i8;
                    } else {
                        throw zzco.zzbp();
                    }
                }
                while (i < i2) {
                    i8 = zzax.zza(bArr, i, zzay);
                    if (i3 != zzay.zzfd) {
                        return i;
                    }
                    i = zzax.zza(bArr, i8, zzay);
                    i8 = zzay.zzfd;
                    if (i8 == 0) {
                        zzcn.add(TtmlNode.ANONYMOUS_REGION_ID);
                    } else {
                        if (zzff.zze(bArr, i, i + i8)) {
                            zzcn.add(new String(bArr, i, i8, zzci.UTF_8));
                            i += i8;
                        } else {
                            throw zzco.zzbp();
                        }
                    }
                }
                return i;
            case 27:
                return i5 == 2 ? zza(zzad(i6), i3, bArr, i, i2, zzcn, zzay) : i;
            case 28:
                if (i5 != 2) {
                    return i;
                }
                i = zzax.zza(bArr, i, zzay);
                i8 = zzay.zzfd;
                if (i8 == 0) {
                    zzcn.add(zzbb.zzfi);
                } else {
                    zzcn.add(zzbb.zzb(bArr, i, i8));
                    i += i8;
                }
                while (i < i2) {
                    i8 = zzax.zza(bArr, i, zzay);
                    if (i3 != zzay.zzfd) {
                        return i;
                    }
                    i = zzax.zza(bArr, i8, zzay);
                    i8 = zzay.zzfd;
                    if (i8 == 0) {
                        zzcn.add(zzbb.zzfi);
                    } else {
                        zzcn.add(zzbb.zzb(bArr, i, i8));
                        i += i8;
                    }
                }
                return i;
            case 30:
            case 44:
                if (i5 == 2) {
                    size = zzax.zza(bArr, i, zzcn, zzay);
                } else if (i5 != 0) {
                    return i;
                } else {
                    size = zzax.zza(i3, bArr, i, i2, zzcn, zzay);
                }
                Object obj = ((zzcg) t).zzjp;
                if (obj == zzey.zzea()) {
                    obj = null;
                }
                zzey zzey = (zzey) zzeh.zza(i4, zzcn, zzaf(i6), obj, this.zzmx);
                if (zzey == null) {
                    return size;
                }
                ((zzcg) t).zzjp = zzey;
                return size;
            case 33:
            case 47:
                if (i5 == 2) {
                    zzch = (zzch) zzcn;
                    i = zzax.zza(bArr, i, zzay);
                    i8 = zzay.zzfd + i;
                    while (i < i8) {
                        i = zzax.zza(bArr, i, zzay);
                        zzch.zzac(zzbk.zzm(zzay.zzfd));
                    }
                    if (i == i8) {
                        return i;
                    }
                    throw zzco.zzbl();
                } else if (i5 != 0) {
                    return i;
                } else {
                    zzch = (zzch) zzcn;
                    i = zzax.zza(bArr, i, zzay);
                    zzch.zzac(zzbk.zzm(zzay.zzfd));
                    while (i < i2) {
                        i8 = zzax.zza(bArr, i, zzay);
                        if (i3 != zzay.zzfd) {
                            return i;
                        }
                        i = zzax.zza(bArr, i8, zzay);
                        zzch.zzac(zzbk.zzm(zzay.zzfd));
                    }
                    return i;
                }
            case 34:
            case 48:
                if (i5 == 2) {
                    zzdc = (zzdc) zzcn;
                    i = zzax.zza(bArr, i, zzay);
                    i8 = zzay.zzfd + i;
                    while (i < i8) {
                        i = zzax.zzb(bArr, i, zzay);
                        zzdc.zzm(zzbk.zza(zzay.zzfe));
                    }
                    if (i == i8) {
                        return i;
                    }
                    throw zzco.zzbl();
                } else if (i5 != 0) {
                    return i;
                } else {
                    zzdc = (zzdc) zzcn;
                    i = zzax.zzb(bArr, i, zzay);
                    zzdc.zzm(zzbk.zza(zzay.zzfe));
                    while (i < i2) {
                        i8 = zzax.zza(bArr, i, zzay);
                        if (i3 != zzay.zzfd) {
                            return i;
                        }
                        i = zzax.zzb(bArr, i8, zzay);
                        zzdc.zzm(zzbk.zza(zzay.zzfe));
                    }
                    return i;
                }
            case 49:
                if (i5 != 3) {
                    return i;
                }
                zzef zzad = zzad(i6);
                int i9 = (i3 & -8) | 4;
                i = zza(zzad, bArr, i, i2, i9, zzay);
                zzcn.add(zzay.zzff);
                while (i < i2) {
                    int zza = zzax.zza(bArr, i, zzay);
                    if (i3 != zzay.zzfd) {
                        return i;
                    }
                    i = zza(zzad, bArr, zza, i2, i9, zzay);
                    zzcn.add(zzay.zzff);
                }
                return i;
            default:
                return i;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final <K, V> int zza(T r14, byte[] r15, int r16, int r17, int r18, int r19, long r20, com.google.android.gms.internal.clearcut.zzay r22) {
        /*
        r13 = this;
        r4 = zzmh;
        r0 = r18;
        r5 = r13.zzae(r0);
        r0 = r20;
        r3 = r4.getObject(r14, r0);
        r2 = r13.zzmz;
        r2 = r2.zzi(r3);
        if (r2 == 0) goto L_0x00ba;
    L_0x0016:
        r2 = r13.zzmz;
        r2 = r2.zzk(r5);
        r6 = r13.zzmz;
        r6.zzb(r2, r3);
        r0 = r20;
        r4.putObject(r14, r0, r2);
    L_0x0026:
        r3 = r13.zzmz;
        r10 = r3.zzl(r5);
        r3 = r13.zzmz;
        r11 = r3.zzg(r2);
        r0 = r16;
        r1 = r22;
        r4 = com.google.android.gms.internal.clearcut.zzax.zza(r15, r0, r1);
        r0 = r22;
        r2 = r0.zzfd;
        if (r2 < 0) goto L_0x0044;
    L_0x0040:
        r3 = r17 - r4;
        if (r2 <= r3) goto L_0x0049;
    L_0x0044:
        r2 = com.google.android.gms.internal.clearcut.zzco.zzbl();
        throw r2;
    L_0x0049:
        r12 = r4 + r2;
        r3 = r10.zzmc;
        r2 = r10.zzdu;
        r8 = r2;
        r9 = r3;
        r2 = r4;
    L_0x0052:
        if (r2 >= r12) goto L_0x00af;
    L_0x0054:
        r3 = r2 + 1;
        r2 = r15[r2];
        if (r2 >= 0) goto L_0x0064;
    L_0x005a:
        r0 = r22;
        r3 = com.google.android.gms.internal.clearcut.zzax.zza(r2, r15, r3, r0);
        r0 = r22;
        r2 = r0.zzfd;
    L_0x0064:
        r4 = r2 >>> 3;
        r5 = r2 & 7;
        switch(r4) {
            case 1: goto L_0x0074;
            case 2: goto L_0x008f;
            default: goto L_0x006b;
        };
    L_0x006b:
        r0 = r17;
        r1 = r22;
        r2 = com.google.android.gms.internal.clearcut.zzax.zza(r2, r15, r3, r0, r1);
        goto L_0x0052;
    L_0x0074:
        r4 = r10.zzmb;
        r4 = r4.zzel();
        if (r5 != r4) goto L_0x006b;
    L_0x007c:
        r5 = r10.zzmb;
        r6 = 0;
        r2 = r15;
        r4 = r17;
        r7 = r22;
        r3 = zza(r2, r3, r4, r5, r6, r7);
        r0 = r22;
        r2 = r0.zzff;
        r9 = r2;
        r2 = r3;
        goto L_0x0052;
    L_0x008f:
        r4 = r10.zzmd;
        r4 = r4.zzel();
        if (r5 != r4) goto L_0x006b;
    L_0x0097:
        r5 = r10.zzmd;
        r2 = r10.zzdu;
        r6 = r2.getClass();
        r2 = r15;
        r4 = r17;
        r7 = r22;
        r3 = zza(r2, r3, r4, r5, r6, r7);
        r0 = r22;
        r2 = r0.zzff;
        r8 = r2;
        r2 = r3;
        goto L_0x0052;
    L_0x00af:
        if (r2 == r12) goto L_0x00b6;
    L_0x00b1:
        r2 = com.google.android.gms.internal.clearcut.zzco.zzbo();
        throw r2;
    L_0x00b6:
        r11.put(r9, r8);
        return r12;
    L_0x00ba:
        r2 = r3;
        goto L_0x0026;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.clearcut.zzds.zza(java.lang.Object, byte[], int, int, int, int, long, com.google.android.gms.internal.clearcut.zzay):int");
    }

    private final int zza(T t, byte[] bArr, int i, int i2, int i3, zzay zzay) {
        int i4;
        int i5;
        int length;
        int i6;
        Unsafe unsafe = zzmh;
        int i7 = -1;
        int i8 = 0;
        int i9 = 0;
        int i10 = i;
        while (i10 < i2) {
            zzck zzaf;
            Object obj;
            int[] iArr;
            zzex zzex;
            Object zzo;
            int i11 = i10 + 1;
            i9 = bArr[i10];
            if (i9 < 0) {
                i11 = zzax.zza(i9, bArr, i11, zzay);
                i9 = zzay.zzfd;
            }
            int i12 = i9 >>> 3;
            i10 = i9 & 7;
            int zzai = zzai(i12);
            if (zzai != -1) {
                int i13 = this.zzmi[zzai + 1];
                int i14 = (267386880 & i13) >>> 20;
                long j = (long) (1048575 & i13);
                if (i14 <= 17) {
                    i4 = this.zzmi[zzai + 2];
                    int i15 = 1 << (i4 >>> 20);
                    i4 &= 1048575;
                    if (i4 != i7) {
                        if (i7 != -1) {
                            unsafe.putInt(t, (long) i7, i8);
                        }
                        i8 = unsafe.getInt(t, (long) i4);
                        i7 = i4;
                    }
                    switch (i14) {
                        case 0:
                            if (i10 == 1) {
                                zzfd.zza((Object) t, j, zzax.zze(bArr, i11));
                                i10 = i11 + 8;
                                i8 |= i15;
                                continue;
                            }
                            break;
                        case 1:
                            if (i10 == 5) {
                                zzfd.zza((Object) t, j, zzax.zzf(bArr, i11));
                                i10 = i11 + 4;
                                i8 |= i15;
                                continue;
                            }
                            break;
                        case 2:
                        case 3:
                            if (i10 == 0) {
                                i10 = zzax.zzb(bArr, i11, zzay);
                                unsafe.putLong(t, j, zzay.zzfe);
                                i8 |= i15;
                                continue;
                            }
                            break;
                        case 4:
                        case 11:
                            if (i10 == 0) {
                                i10 = zzax.zza(bArr, i11, zzay);
                                unsafe.putInt(t, j, zzay.zzfd);
                                i8 |= i15;
                                continue;
                            }
                            break;
                        case 5:
                        case 14:
                            if (i10 == 1) {
                                unsafe.putLong(t, j, zzax.zzd(bArr, i11));
                                i10 = i11 + 8;
                                i8 |= i15;
                                continue;
                            }
                            break;
                        case 6:
                        case 13:
                            if (i10 == 5) {
                                unsafe.putInt(t, j, zzax.zzc(bArr, i11));
                                i10 = i11 + 4;
                                i8 |= i15;
                                continue;
                            }
                            break;
                        case 7:
                            if (i10 == 0) {
                                i10 = zzax.zzb(bArr, i11, zzay);
                                zzfd.zza((Object) t, j, zzay.zzfe != 0);
                                i8 |= i15;
                                continue;
                            }
                            break;
                        case 8:
                            if (i10 == 2) {
                                i4 = (ErrorDialogData.DYNAMITE_CRASH & i13) == 0 ? zzax.zzc(bArr, i11, zzay) : zzax.zzd(bArr, i11, zzay);
                                unsafe.putObject(t, j, zzay.zzff);
                                i8 |= i15;
                                i10 = i4;
                                continue;
                            }
                            break;
                        case 9:
                            if (i10 == 2) {
                                i10 = zza(zzad(zzai), bArr, i11, i2, zzay);
                                if ((i8 & i15) == 0) {
                                    unsafe.putObject(t, j, zzay.zzff);
                                } else {
                                    unsafe.putObject(t, j, zzci.zza(unsafe.getObject(t, j), zzay.zzff));
                                }
                                i8 |= i15;
                                continue;
                            }
                            break;
                        case 10:
                            if (i10 == 2) {
                                i10 = zzax.zze(bArr, i11, zzay);
                                unsafe.putObject(t, j, zzay.zzff);
                                i8 |= i15;
                                continue;
                            }
                            break;
                        case 12:
                            if (i10 == 0) {
                                i10 = zzax.zza(bArr, i11, zzay);
                                i4 = zzay.zzfd;
                                zzaf = zzaf(zzai);
                                if (zzaf != null && zzaf.zzb(i4) == null) {
                                    zzn(t).zzb(i9, Long.valueOf((long) i4));
                                    break;
                                }
                                unsafe.putInt(t, j, i4);
                                i8 |= i15;
                                continue;
                            }
                            break;
                        case 15:
                            if (i10 == 0) {
                                i10 = zzax.zza(bArr, i11, zzay);
                                unsafe.putInt(t, j, zzbk.zzm(zzay.zzfd));
                                i8 |= i15;
                                continue;
                            }
                            break;
                        case 16:
                            if (i10 == 0) {
                                i10 = zzax.zzb(bArr, i11, zzay);
                                unsafe.putLong(t, j, zzbk.zza(zzay.zzfe));
                                i8 |= i15;
                                continue;
                            }
                            break;
                        case 17:
                            if (i10 == 3) {
                                i10 = zza(zzad(zzai), bArr, i11, i2, (i12 << 3) | 4, zzay);
                                if ((i8 & i15) == 0) {
                                    unsafe.putObject(t, j, zzay.zzff);
                                } else {
                                    unsafe.putObject(t, j, zzci.zza(unsafe.getObject(t, j), zzay.zzff));
                                }
                                i8 |= i15;
                                continue;
                            }
                            break;
                    }
                    i4 = i8;
                    i5 = i7;
                    i10 = i11;
                    if (i9 == i3 || i3 == 0) {
                        i10 = zza(i9, bArr, i10, i2, (Object) t, zzay);
                        i8 = i4;
                        i7 = i5;
                    } else {
                        if (i5 != -1) {
                            unsafe.putInt(t, (long) i5, i4);
                        }
                        if (this.zzmt != null) {
                            obj = null;
                            iArr = this.zzmt;
                            length = iArr.length;
                            i6 = 0;
                            while (i6 < length) {
                                i4 = iArr[i6];
                                zzex = this.zzmx;
                                i5 = this.zzmi[i4];
                                zzo = zzfd.zzo(t, (long) (zzag(i4) & 1048575));
                                if (zzo != null) {
                                    zzo = obj;
                                } else {
                                    zzaf = zzaf(i4);
                                    zzo = zzaf != null ? obj : zza(i4, i5, this.zzmz.zzg(zzo), zzaf, obj, zzex);
                                }
                                i6++;
                                zzey zzey = (zzey) zzo;
                            }
                            if (obj != null) {
                                this.zzmx.zzf(t, obj);
                            }
                        }
                        if (i3 != 0) {
                            if (i10 != i2) {
                                throw zzco.zzbo();
                            }
                        } else if (i10 > i2 || r17 != i3) {
                            throw zzco.zzbo();
                        }
                        return i10;
                    }
                } else if (i14 == 27) {
                    if (i10 == 2) {
                        zzcn zzcn;
                        zzcn zzcn2 = (zzcn) unsafe.getObject(t, j);
                        if (zzcn2.zzu()) {
                            zzcn = zzcn2;
                        } else {
                            int size = zzcn2.size();
                            zzcn = zzcn2.zzi(size == 0 ? 10 : size << 1);
                            unsafe.putObject(t, j, zzcn);
                        }
                        i10 = zza(zzad(zzai), i9, bArr, i11, i2, zzcn, zzay);
                    }
                } else if (i14 <= 49) {
                    i10 = zza((Object) t, bArr, i11, i2, i9, i12, i10, zzai, (long) i13, i14, j, zzay);
                    if (i10 == i11) {
                        i4 = i8;
                        i5 = i7;
                        if (i9 == i3) {
                        }
                        i10 = zza(i9, bArr, i10, i2, (Object) t, zzay);
                        i8 = i4;
                        i7 = i5;
                    } else {
                        continue;
                    }
                } else if (i14 != 50) {
                    i10 = zza((Object) t, bArr, i11, i2, i9, i12, i10, i13, i14, j, zzai, zzay);
                    if (i10 == i11) {
                        i4 = i8;
                        i5 = i7;
                        if (i9 == i3) {
                        }
                        i10 = zza(i9, bArr, i10, i2, (Object) t, zzay);
                        i8 = i4;
                        i7 = i5;
                    } else {
                        continue;
                    }
                } else if (i10 == 2) {
                    i10 = zza(t, bArr, i11, i2, zzai, i12, j, zzay);
                    if (i10 == i11) {
                        i4 = i8;
                        i5 = i7;
                        if (i9 == i3) {
                        }
                        i10 = zza(i9, bArr, i10, i2, (Object) t, zzay);
                        i8 = i4;
                        i7 = i5;
                    } else {
                        continue;
                    }
                }
            }
            i4 = i8;
            i5 = i7;
            i10 = i11;
            if (i9 == i3) {
            }
            i10 = zza(i9, bArr, i10, i2, (Object) t, zzay);
            i8 = i4;
            i7 = i5;
        }
        i4 = i8;
        i5 = i7;
        if (i5 != -1) {
            unsafe.putInt(t, (long) i5, i4);
        }
        if (this.zzmt != null) {
            obj = null;
            iArr = this.zzmt;
            length = iArr.length;
            i6 = 0;
            while (i6 < length) {
                i4 = iArr[i6];
                zzex = this.zzmx;
                i5 = this.zzmi[i4];
                zzo = zzfd.zzo(t, (long) (zzag(i4) & 1048575));
                if (zzo != null) {
                    zzaf = zzaf(i4);
                    if (zzaf != null) {
                    }
                } else {
                    zzo = obj;
                }
                i6++;
                zzey zzey2 = (zzey) zzo;
            }
            if (obj != null) {
                this.zzmx.zzf(t, obj);
            }
        }
        if (i3 != 0) {
            throw zzco.zzbo();
        } else if (i10 != i2) {
            throw zzco.zzbo();
        }
        return i10;
    }

    private static int zza(byte[] bArr, int i, int i2, zzfl zzfl, Class<?> cls, zzay zzay) {
        int zza;
        switch (zzdt.zzgq[zzfl.ordinal()]) {
            case 1:
                int zzb = zzax.zzb(bArr, i, zzay);
                zzay.zzff = Boolean.valueOf(zzay.zzfe != 0);
                return zzb;
            case 2:
                return zzax.zze(bArr, i, zzay);
            case 3:
                zzay.zzff = Double.valueOf(zzax.zze(bArr, i));
                return i + 8;
            case 4:
            case 5:
                zzay.zzff = Integer.valueOf(zzax.zzc(bArr, i));
                return i + 4;
            case 6:
            case 7:
                zzay.zzff = Long.valueOf(zzax.zzd(bArr, i));
                return i + 8;
            case 8:
                zzay.zzff = Float.valueOf(zzax.zzf(bArr, i));
                return i + 4;
            case 9:
            case 10:
            case 11:
                zza = zzax.zza(bArr, i, zzay);
                zzay.zzff = Integer.valueOf(zzay.zzfd);
                return zza;
            case 12:
            case 13:
                zza = zzax.zzb(bArr, i, zzay);
                zzay.zzff = Long.valueOf(zzay.zzfe);
                return zza;
            case 14:
                return zza(zzea.zzcm().zze(cls), bArr, i, i2, zzay);
            case 15:
                zza = zzax.zza(bArr, i, zzay);
                zzay.zzff = Integer.valueOf(zzbk.zzm(zzay.zzfd));
                return zza;
            case 16:
                zza = zzax.zzb(bArr, i, zzay);
                zzay.zzff = Long.valueOf(zzbk.zza(zzay.zzfe));
                return zza;
            case 17:
                return zzax.zzd(bArr, i, zzay);
            default:
                throw new RuntimeException("unsupported field type.");
        }
    }

    static <T> zzds<T> zza(Class<T> cls, zzdm zzdm, zzdw zzdw, zzcy zzcy, zzex<?, ?> zzex, zzbu<?> zzbu, zzdj zzdj) {
        if (zzdm instanceof zzec) {
            int i;
            int i2;
            int i3;
            zzec zzec = (zzec) zzdm;
            boolean z = zzec.zzcf() == zzg.zzkm;
            if (zzec.getFieldCount() == 0) {
                i = 0;
                i2 = 0;
                i3 = 0;
            } else {
                i = zzec.zzcp();
                i2 = zzec.zzcq();
                i3 = zzec.zzcu();
            }
            int[] iArr = new int[(i3 << 2)];
            Object[] objArr = new Object[(i3 << 1)];
            int[] iArr2 = zzec.zzcr() > 0 ? new int[zzec.zzcr()] : null;
            int[] iArr3 = zzec.zzcs() > 0 ? new int[zzec.zzcs()] : null;
            int i4 = 0;
            int i5 = 0;
            zzed zzco = zzec.zzco();
            if (zzco.next()) {
                int zzcx = zzco.zzcx();
                i3 = 0;
                while (true) {
                    int i6;
                    if (zzcx >= zzec.zzcv() || i3 >= ((zzcx - i) << 2)) {
                        int zza;
                        if (zzco.zzda()) {
                            zza = (int) zzfd.zza(zzco.zzdb());
                            zzcx = (int) zzfd.zza(zzco.zzdc());
                            i6 = 0;
                        } else {
                            zza = (int) zzfd.zza(zzco.zzdd());
                            if (zzco.zzde()) {
                                zzcx = (int) zzfd.zza(zzco.zzdf());
                                i6 = zzco.zzdg();
                            } else {
                                zzcx = 0;
                                i6 = 0;
                            }
                        }
                        iArr[i3] = zzco.zzcx();
                        iArr[i3 + 1] = zza | (((zzco.zzdi() ? ErrorDialogData.DYNAMITE_CRASH : 0) | (zzco.zzdh() ? ErrorDialogData.BINDER_CRASH : 0)) | (zzco.zzcy() << 20));
                        iArr[i3 + 2] = zzcx | (i6 << 20);
                        if (zzco.zzdl() != null) {
                            objArr[(i3 / 4) << 1] = zzco.zzdl();
                            if (zzco.zzdj() != null) {
                                objArr[((i3 / 4) << 1) + 1] = zzco.zzdj();
                            } else if (zzco.zzdk() != null) {
                                objArr[((i3 / 4) << 1) + 1] = zzco.zzdk();
                            }
                        } else if (zzco.zzdj() != null) {
                            objArr[((i3 / 4) << 1) + 1] = zzco.zzdj();
                        } else if (zzco.zzdk() != null) {
                            objArr[((i3 / 4) << 1) + 1] = zzco.zzdk();
                        }
                        zzcx = zzco.zzcy();
                        if (zzcx == zzcb.MAP.ordinal()) {
                            zzcx = i4 + 1;
                            iArr2[i4] = i3;
                            i4 = zzcx;
                        } else if (zzcx >= 18 && zzcx <= 49) {
                            zzcx = i5 + 1;
                            iArr3[i5] = iArr[i3 + 1] & 1048575;
                            i5 = zzcx;
                        }
                        if (!zzco.next()) {
                            break;
                        }
                        zzcx = zzco.zzcx();
                    } else {
                        for (i6 = 0; i6 < 4; i6++) {
                            iArr[i3 + i6] = -1;
                        }
                    }
                    i3 += 4;
                }
            }
            return new zzds(iArr, objArr, i, i2, zzec.zzcv(), zzec.zzch(), z, false, zzec.zzct(), iArr2, iArr3, zzdw, zzcy, zzex, zzbu, zzdj);
        }
        ((zzes) zzdm).zzcf();
        throw new NoSuchMethodError();
    }

    private final <K, V, UT, UB> UB zza(int i, int i2, Map<K, V> map, zzck<?> zzck, UB ub, zzex<UT, UB> zzex) {
        zzdh zzl = this.zzmz.zzl(zzae(i));
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            if (zzck.zzb(((Integer) entry.getValue()).intValue()) == null) {
                if (ub == null) {
                    ub = zzex.zzdz();
                }
                zzbg zzk = zzbb.zzk(zzdg.zza(zzl, entry.getKey(), entry.getValue()));
                try {
                    zzdg.zza(zzk.zzae(), zzl, entry.getKey(), entry.getValue());
                    zzex.zza((Object) ub, i2, zzk.zzad());
                    it.remove();
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return ub;
    }

    private static void zza(int i, Object obj, zzfr zzfr) {
        if (obj instanceof String) {
            zzfr.zza(i, (String) obj);
        } else {
            zzfr.zza(i, (zzbb) obj);
        }
    }

    private static <UT, UB> void zza(zzex<UT, UB> zzex, T t, zzfr zzfr) {
        zzex.zza(zzex.zzq(t), zzfr);
    }

    private final <K, V> void zza(zzfr zzfr, int i, Object obj, int i2) {
        if (obj != null) {
            zzfr.zza(i, this.zzmz.zzl(zzae(i2)), this.zzmz.zzh(obj));
        }
    }

    private final void zza(T t, T t2, int i) {
        long zzag = (long) (zzag(i) & 1048575);
        if (zza((Object) t2, i)) {
            Object zzo = zzfd.zzo(t, zzag);
            Object zzo2 = zzfd.zzo(t2, zzag);
            if (zzo != null && zzo2 != null) {
                zzfd.zza((Object) t, zzag, zzci.zza(zzo, zzo2));
                zzb((Object) t, i);
            } else if (zzo2 != null) {
                zzfd.zza((Object) t, zzag, zzo2);
                zzb((Object) t, i);
            }
        }
    }

    private final boolean zza(T t, int i) {
        if (this.zzmq) {
            int zzag;
            zzag = zzag(i);
            long j = (long) (zzag & 1048575);
            switch ((zzag & 267386880) >>> 20) {
                case 0:
                    return zzfd.zzn(t, j) != 0.0d;
                case 1:
                    return zzfd.zzm(t, j) != BitmapDescriptorFactory.HUE_RED;
                case 2:
                    return zzfd.zzk(t, j) != 0;
                case 3:
                    return zzfd.zzk(t, j) != 0;
                case 4:
                    return zzfd.zzj(t, j) != 0;
                case 5:
                    return zzfd.zzk(t, j) != 0;
                case 6:
                    return zzfd.zzj(t, j) != 0;
                case 7:
                    return zzfd.zzl(t, j);
                case 8:
                    Object zzo = zzfd.zzo(t, j);
                    if (zzo instanceof String) {
                        return !((String) zzo).isEmpty();
                    } else {
                        if (zzo instanceof zzbb) {
                            return !zzbb.zzfi.equals(zzo);
                        } else {
                            throw new IllegalArgumentException();
                        }
                    }
                case 9:
                    return zzfd.zzo(t, j) != null;
                case 10:
                    return !zzbb.zzfi.equals(zzfd.zzo(t, j));
                case 11:
                    return zzfd.zzj(t, j) != 0;
                case 12:
                    return zzfd.zzj(t, j) != 0;
                case 13:
                    return zzfd.zzj(t, j) != 0;
                case 14:
                    return zzfd.zzk(t, j) != 0;
                case 15:
                    return zzfd.zzj(t, j) != 0;
                case 16:
                    return zzfd.zzk(t, j) != 0;
                case 17:
                    return zzfd.zzo(t, j) != null;
                default:
                    throw new IllegalArgumentException();
            }
        }
        zzag = zzah(i);
        return (zzfd.zzj(t, (long) (zzag & 1048575)) & (1 << (zzag >>> 20))) != 0;
    }

    private final boolean zza(T t, int i, int i2) {
        return zzfd.zzj(t, (long) (zzah(i2) & 1048575)) == i;
    }

    private final boolean zza(T t, int i, int i2, int i3) {
        return this.zzmq ? zza((Object) t, i) : (i2 & i3) != 0;
    }

    private static boolean zza(Object obj, int i, zzef zzef) {
        return zzef.zzo(zzfd.zzo(obj, (long) (1048575 & i)));
    }

    private final zzef zzad(int i) {
        int i2 = (i / 4) << 1;
        zzef zzef = (zzef) this.zzmj[i2];
        if (zzef != null) {
            return zzef;
        }
        zzef = zzea.zzcm().zze((Class) this.zzmj[i2 + 1]);
        this.zzmj[i2] = zzef;
        return zzef;
    }

    private final Object zzae(int i) {
        return this.zzmj[(i / 4) << 1];
    }

    private final zzck<?> zzaf(int i) {
        return (zzck) this.zzmj[((i / 4) << 1) + 1];
    }

    private final int zzag(int i) {
        return this.zzmi[i + 1];
    }

    private final int zzah(int i) {
        return this.zzmi[i + 2];
    }

    private final int zzai(int i) {
        if (i >= this.zzmk) {
            int i2;
            if (i < this.zzmm) {
                i2 = (i - this.zzmk) << 2;
                return this.zzmi[i2] == i ? i2 : -1;
            } else if (i <= this.zzml) {
                int i3 = this.zzmm - this.zzmk;
                int length = (this.zzmi.length / 4) - 1;
                while (i3 <= length) {
                    int i4 = (length + i3) >>> 1;
                    i2 = i4 << 2;
                    int i5 = this.zzmi[i2];
                    if (i == i5) {
                        return i2;
                    }
                    if (i < i5) {
                        length = i4 - 1;
                    } else {
                        i3 = i4 + 1;
                    }
                }
                return -1;
            }
        }
        return -1;
    }

    private final void zzb(T t, int i) {
        if (!this.zzmq) {
            int zzah = zzah(i);
            long j = (long) (zzah & 1048575);
            zzfd.zza((Object) t, j, zzfd.zzj(t, j) | (1 << (zzah >>> 20)));
        }
    }

    private final void zzb(T t, int i, int i2) {
        zzfd.zza((Object) t, (long) (zzah(i2) & 1048575), i);
    }

    private final void zzb(T t, zzfr zzfr) {
        Iterator it = null;
        Entry entry = null;
        if (this.zzmo) {
            zzby zza = this.zzmy.zza((Object) t);
            if (!zza.isEmpty()) {
                it = zza.iterator();
                entry = (Entry) it.next();
            }
        }
        int length = this.zzmi.length;
        Unsafe unsafe = zzmh;
        int i = 0;
        int i2 = -1;
        Entry entry2 = entry;
        int i3 = 0;
        while (i < length) {
            Entry entry3;
            int i4;
            int zzag = zzag(i);
            int i5 = this.zzmi[i];
            int i6 = (267386880 & zzag) >>> 20;
            int i7 = 0;
            if (this.zzmq || i6 > 17) {
                entry3 = entry2;
                i4 = i2;
                i2 = i3;
            } else {
                int i8 = this.zzmi[i + 2];
                i7 = 1048575 & i8;
                if (i7 != i2) {
                    i3 = unsafe.getInt(t, (long) i7);
                } else {
                    i7 = i2;
                }
                i2 = 1 << (i8 >>> 20);
                entry3 = entry2;
                i4 = i7;
                i7 = i2;
                i2 = i3;
            }
            while (entry3 != null && this.zzmy.zza(entry3) <= i5) {
                this.zzmy.zza(zzfr, entry3);
                entry3 = it.hasNext() ? (Entry) it.next() : null;
            }
            long j = (long) (1048575 & zzag);
            switch (i6) {
                case 0:
                    if ((i2 & i7) == 0) {
                        break;
                    }
                    zzfr.zza(i5, zzfd.zzn(t, j));
                    break;
                case 1:
                    if ((i2 & i7) == 0) {
                        break;
                    }
                    zzfr.zza(i5, zzfd.zzm(t, j));
                    break;
                case 2:
                    if ((i2 & i7) == 0) {
                        break;
                    }
                    zzfr.zzi(i5, unsafe.getLong(t, j));
                    break;
                case 3:
                    if ((i2 & i7) == 0) {
                        break;
                    }
                    zzfr.zza(i5, unsafe.getLong(t, j));
                    break;
                case 4:
                    if ((i2 & i7) == 0) {
                        break;
                    }
                    zzfr.zzc(i5, unsafe.getInt(t, j));
                    break;
                case 5:
                    if ((i2 & i7) == 0) {
                        break;
                    }
                    zzfr.zzc(i5, unsafe.getLong(t, j));
                    break;
                case 6:
                    if ((i2 & i7) == 0) {
                        break;
                    }
                    zzfr.zzf(i5, unsafe.getInt(t, j));
                    break;
                case 7:
                    if ((i2 & i7) == 0) {
                        break;
                    }
                    zzfr.zzb(i5, zzfd.zzl(t, j));
                    break;
                case 8:
                    if ((i2 & i7) == 0) {
                        break;
                    }
                    zza(i5, unsafe.getObject(t, j), zzfr);
                    break;
                case 9:
                    if ((i2 & i7) == 0) {
                        break;
                    }
                    zzfr.zza(i5, unsafe.getObject(t, j), zzad(i));
                    break;
                case 10:
                    if ((i2 & i7) == 0) {
                        break;
                    }
                    zzfr.zza(i5, (zzbb) unsafe.getObject(t, j));
                    break;
                case 11:
                    if ((i2 & i7) == 0) {
                        break;
                    }
                    zzfr.zzd(i5, unsafe.getInt(t, j));
                    break;
                case 12:
                    if ((i2 & i7) == 0) {
                        break;
                    }
                    zzfr.zzn(i5, unsafe.getInt(t, j));
                    break;
                case 13:
                    if ((i2 & i7) == 0) {
                        break;
                    }
                    zzfr.zzm(i5, unsafe.getInt(t, j));
                    break;
                case 14:
                    if ((i2 & i7) == 0) {
                        break;
                    }
                    zzfr.zzj(i5, unsafe.getLong(t, j));
                    break;
                case 15:
                    if ((i2 & i7) == 0) {
                        break;
                    }
                    zzfr.zze(i5, unsafe.getInt(t, j));
                    break;
                case 16:
                    if ((i2 & i7) == 0) {
                        break;
                    }
                    zzfr.zzb(i5, unsafe.getLong(t, j));
                    break;
                case 17:
                    if ((i2 & i7) == 0) {
                        break;
                    }
                    zzfr.zzb(i5, unsafe.getObject(t, j), zzad(i));
                    break;
                case 18:
                    zzeh.zza(this.zzmi[i], (List) unsafe.getObject(t, j), zzfr, false);
                    break;
                case 19:
                    zzeh.zzb(this.zzmi[i], (List) unsafe.getObject(t, j), zzfr, false);
                    break;
                case 20:
                    zzeh.zzc(this.zzmi[i], (List) unsafe.getObject(t, j), zzfr, false);
                    break;
                case 21:
                    zzeh.zzd(this.zzmi[i], (List) unsafe.getObject(t, j), zzfr, false);
                    break;
                case 22:
                    zzeh.zzh(this.zzmi[i], (List) unsafe.getObject(t, j), zzfr, false);
                    break;
                case 23:
                    zzeh.zzf(this.zzmi[i], (List) unsafe.getObject(t, j), zzfr, false);
                    break;
                case 24:
                    zzeh.zzk(this.zzmi[i], (List) unsafe.getObject(t, j), zzfr, false);
                    break;
                case 25:
                    zzeh.zzn(this.zzmi[i], (List) unsafe.getObject(t, j), zzfr, false);
                    break;
                case 26:
                    zzeh.zza(this.zzmi[i], (List) unsafe.getObject(t, j), zzfr);
                    break;
                case 27:
                    zzeh.zza(this.zzmi[i], (List) unsafe.getObject(t, j), zzfr, zzad(i));
                    break;
                case 28:
                    zzeh.zzb(this.zzmi[i], (List) unsafe.getObject(t, j), zzfr);
                    break;
                case 29:
                    zzeh.zzi(this.zzmi[i], (List) unsafe.getObject(t, j), zzfr, false);
                    break;
                case 30:
                    zzeh.zzm(this.zzmi[i], (List) unsafe.getObject(t, j), zzfr, false);
                    break;
                case 31:
                    zzeh.zzl(this.zzmi[i], (List) unsafe.getObject(t, j), zzfr, false);
                    break;
                case 32:
                    zzeh.zzg(this.zzmi[i], (List) unsafe.getObject(t, j), zzfr, false);
                    break;
                case 33:
                    zzeh.zzj(this.zzmi[i], (List) unsafe.getObject(t, j), zzfr, false);
                    break;
                case 34:
                    zzeh.zze(this.zzmi[i], (List) unsafe.getObject(t, j), zzfr, false);
                    break;
                case 35:
                    zzeh.zza(this.zzmi[i], (List) unsafe.getObject(t, j), zzfr, true);
                    break;
                case 36:
                    zzeh.zzb(this.zzmi[i], (List) unsafe.getObject(t, j), zzfr, true);
                    break;
                case 37:
                    zzeh.zzc(this.zzmi[i], (List) unsafe.getObject(t, j), zzfr, true);
                    break;
                case 38:
                    zzeh.zzd(this.zzmi[i], (List) unsafe.getObject(t, j), zzfr, true);
                    break;
                case 39:
                    zzeh.zzh(this.zzmi[i], (List) unsafe.getObject(t, j), zzfr, true);
                    break;
                case 40:
                    zzeh.zzf(this.zzmi[i], (List) unsafe.getObject(t, j), zzfr, true);
                    break;
                case 41:
                    zzeh.zzk(this.zzmi[i], (List) unsafe.getObject(t, j), zzfr, true);
                    break;
                case 42:
                    zzeh.zzn(this.zzmi[i], (List) unsafe.getObject(t, j), zzfr, true);
                    break;
                case 43:
                    zzeh.zzi(this.zzmi[i], (List) unsafe.getObject(t, j), zzfr, true);
                    break;
                case 44:
                    zzeh.zzm(this.zzmi[i], (List) unsafe.getObject(t, j), zzfr, true);
                    break;
                case 45:
                    zzeh.zzl(this.zzmi[i], (List) unsafe.getObject(t, j), zzfr, true);
                    break;
                case 46:
                    zzeh.zzg(this.zzmi[i], (List) unsafe.getObject(t, j), zzfr, true);
                    break;
                case 47:
                    zzeh.zzj(this.zzmi[i], (List) unsafe.getObject(t, j), zzfr, true);
                    break;
                case 48:
                    zzeh.zze(this.zzmi[i], (List) unsafe.getObject(t, j), zzfr, true);
                    break;
                case 49:
                    zzeh.zzb(this.zzmi[i], (List) unsafe.getObject(t, j), zzfr, zzad(i));
                    break;
                case 50:
                    zza(zzfr, i5, unsafe.getObject(t, j), i);
                    break;
                case 51:
                    if (!zza((Object) t, i5, i)) {
                        break;
                    }
                    zzfr.zza(i5, zze(t, j));
                    break;
                case 52:
                    if (!zza((Object) t, i5, i)) {
                        break;
                    }
                    zzfr.zza(i5, zzf(t, j));
                    break;
                case 53:
                    if (!zza((Object) t, i5, i)) {
                        break;
                    }
                    zzfr.zzi(i5, zzh(t, j));
                    break;
                case 54:
                    if (!zza((Object) t, i5, i)) {
                        break;
                    }
                    zzfr.zza(i5, zzh(t, j));
                    break;
                case 55:
                    if (!zza((Object) t, i5, i)) {
                        break;
                    }
                    zzfr.zzc(i5, zzg(t, j));
                    break;
                case 56:
                    if (!zza((Object) t, i5, i)) {
                        break;
                    }
                    zzfr.zzc(i5, zzh(t, j));
                    break;
                case 57:
                    if (!zza((Object) t, i5, i)) {
                        break;
                    }
                    zzfr.zzf(i5, zzg(t, j));
                    break;
                case 58:
                    if (!zza((Object) t, i5, i)) {
                        break;
                    }
                    zzfr.zzb(i5, zzi(t, j));
                    break;
                case 59:
                    if (!zza((Object) t, i5, i)) {
                        break;
                    }
                    zza(i5, unsafe.getObject(t, j), zzfr);
                    break;
                case 60:
                    if (!zza((Object) t, i5, i)) {
                        break;
                    }
                    zzfr.zza(i5, unsafe.getObject(t, j), zzad(i));
                    break;
                case 61:
                    if (!zza((Object) t, i5, i)) {
                        break;
                    }
                    zzfr.zza(i5, (zzbb) unsafe.getObject(t, j));
                    break;
                case 62:
                    if (!zza((Object) t, i5, i)) {
                        break;
                    }
                    zzfr.zzd(i5, zzg(t, j));
                    break;
                case 63:
                    if (!zza((Object) t, i5, i)) {
                        break;
                    }
                    zzfr.zzn(i5, zzg(t, j));
                    break;
                case 64:
                    if (!zza((Object) t, i5, i)) {
                        break;
                    }
                    zzfr.zzm(i5, zzg(t, j));
                    break;
                case 65:
                    if (!zza((Object) t, i5, i)) {
                        break;
                    }
                    zzfr.zzj(i5, zzh(t, j));
                    break;
                case 66:
                    if (!zza((Object) t, i5, i)) {
                        break;
                    }
                    zzfr.zze(i5, zzg(t, j));
                    break;
                case 67:
                    if (!zza((Object) t, i5, i)) {
                        break;
                    }
                    zzfr.zzb(i5, zzh(t, j));
                    break;
                case 68:
                    if (!zza((Object) t, i5, i)) {
                        break;
                    }
                    zzfr.zzb(i5, unsafe.getObject(t, j), zzad(i));
                    break;
                default:
                    break;
            }
            i += 4;
            i3 = i2;
            i2 = i4;
            entry2 = entry3;
        }
        for (entry = entry2; entry != null; entry = it.hasNext() ? (Entry) it.next() : null) {
            this.zzmy.zza(zzfr, entry);
        }
        zza(this.zzmx, (Object) t, zzfr);
    }

    private final void zzb(T t, T t2, int i) {
        int zzag = zzag(i);
        int i2 = this.zzmi[i];
        long j = (long) (zzag & 1048575);
        if (zza((Object) t2, i2, i)) {
            Object zzo = zzfd.zzo(t, j);
            Object zzo2 = zzfd.zzo(t2, j);
            if (zzo != null && zzo2 != null) {
                zzfd.zza((Object) t, j, zzci.zza(zzo, zzo2));
                zzb((Object) t, i2, i);
            } else if (zzo2 != null) {
                zzfd.zza((Object) t, j, zzo2);
                zzb((Object) t, i2, i);
            }
        }
    }

    private final boolean zzc(T t, T t2, int i) {
        return zza((Object) t, i) == zza((Object) t2, i);
    }

    private static <E> List<E> zzd(Object obj, long j) {
        return (List) zzfd.zzo(obj, j);
    }

    private static <T> double zze(T t, long j) {
        return ((Double) zzfd.zzo(t, j)).doubleValue();
    }

    private static <T> float zzf(T t, long j) {
        return ((Float) zzfd.zzo(t, j)).floatValue();
    }

    private static <T> int zzg(T t, long j) {
        return ((Integer) zzfd.zzo(t, j)).intValue();
    }

    private static <T> long zzh(T t, long j) {
        return ((Long) zzfd.zzo(t, j)).longValue();
    }

    private static <T> boolean zzi(T t, long j) {
        return ((Boolean) zzfd.zzo(t, j)).booleanValue();
    }

    private static zzey zzn(Object obj) {
        zzey zzey = ((zzcg) obj).zzjp;
        if (zzey != zzey.zzea()) {
            return zzey;
        }
        zzey = zzey.zzeb();
        ((zzcg) obj).zzjp = zzey;
        return zzey;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean equals(T r12, T r13) {
        /*
        r11 = this;
        r1 = 1;
        r10 = 1048575; // 0xfffff float:1.469367E-39 double:5.18065E-318;
        r0 = 0;
        r2 = r11.zzmi;
        r4 = r2.length;
        r3 = r0;
    L_0x0009:
        if (r3 >= r4) goto L_0x01cf;
    L_0x000b:
        r2 = r11.zzag(r3);
        r5 = r2 & r10;
        r6 = (long) r5;
        r5 = 267386880; // 0xff00000 float:2.3665827E-29 double:1.321066716E-315;
        r2 = r2 & r5;
        r2 = r2 >>> 20;
        switch(r2) {
            case 0: goto L_0x001e;
            case 1: goto L_0x0032;
            case 2: goto L_0x0044;
            case 3: goto L_0x0058;
            case 4: goto L_0x006c;
            case 5: goto L_0x007e;
            case 6: goto L_0x0092;
            case 7: goto L_0x00a5;
            case 8: goto L_0x00b8;
            case 9: goto L_0x00cf;
            case 10: goto L_0x00e6;
            case 11: goto L_0x00fd;
            case 12: goto L_0x0110;
            case 13: goto L_0x0123;
            case 14: goto L_0x0136;
            case 15: goto L_0x014b;
            case 16: goto L_0x015e;
            case 17: goto L_0x0173;
            case 18: goto L_0x018a;
            case 19: goto L_0x018a;
            case 20: goto L_0x018a;
            case 21: goto L_0x018a;
            case 22: goto L_0x018a;
            case 23: goto L_0x018a;
            case 24: goto L_0x018a;
            case 25: goto L_0x018a;
            case 26: goto L_0x018a;
            case 27: goto L_0x018a;
            case 28: goto L_0x018a;
            case 29: goto L_0x018a;
            case 30: goto L_0x018a;
            case 31: goto L_0x018a;
            case 32: goto L_0x018a;
            case 33: goto L_0x018a;
            case 34: goto L_0x018a;
            case 35: goto L_0x018a;
            case 36: goto L_0x018a;
            case 37: goto L_0x018a;
            case 38: goto L_0x018a;
            case 39: goto L_0x018a;
            case 40: goto L_0x018a;
            case 41: goto L_0x018a;
            case 42: goto L_0x018a;
            case 43: goto L_0x018a;
            case 44: goto L_0x018a;
            case 45: goto L_0x018a;
            case 46: goto L_0x018a;
            case 47: goto L_0x018a;
            case 48: goto L_0x018a;
            case 49: goto L_0x018a;
            case 50: goto L_0x0198;
            case 51: goto L_0x01a6;
            case 52: goto L_0x01a6;
            case 53: goto L_0x01a6;
            case 54: goto L_0x01a6;
            case 55: goto L_0x01a6;
            case 56: goto L_0x01a6;
            case 57: goto L_0x01a6;
            case 58: goto L_0x01a6;
            case 59: goto L_0x01a6;
            case 60: goto L_0x01a6;
            case 61: goto L_0x01a6;
            case 62: goto L_0x01a6;
            case 63: goto L_0x01a6;
            case 64: goto L_0x01a6;
            case 65: goto L_0x01a6;
            case 66: goto L_0x01a6;
            case 67: goto L_0x01a6;
            case 68: goto L_0x01a6;
            default: goto L_0x001a;
        };
    L_0x001a:
        r2 = r1;
    L_0x001b:
        if (r2 != 0) goto L_0x01ca;
    L_0x001d:
        return r0;
    L_0x001e:
        r2 = r11.zzc(r12, r13, r3);
        if (r2 == 0) goto L_0x0030;
    L_0x0024:
        r8 = com.google.android.gms.internal.clearcut.zzfd.zzk(r12, r6);
        r6 = com.google.android.gms.internal.clearcut.zzfd.zzk(r13, r6);
        r2 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1));
        if (r2 == 0) goto L_0x001a;
    L_0x0030:
        r2 = r0;
        goto L_0x001b;
    L_0x0032:
        r2 = r11.zzc(r12, r13, r3);
        if (r2 == 0) goto L_0x0042;
    L_0x0038:
        r2 = com.google.android.gms.internal.clearcut.zzfd.zzj(r12, r6);
        r5 = com.google.android.gms.internal.clearcut.zzfd.zzj(r13, r6);
        if (r2 == r5) goto L_0x001a;
    L_0x0042:
        r2 = r0;
        goto L_0x001b;
    L_0x0044:
        r2 = r11.zzc(r12, r13, r3);
        if (r2 == 0) goto L_0x0056;
    L_0x004a:
        r8 = com.google.android.gms.internal.clearcut.zzfd.zzk(r12, r6);
        r6 = com.google.android.gms.internal.clearcut.zzfd.zzk(r13, r6);
        r2 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1));
        if (r2 == 0) goto L_0x001a;
    L_0x0056:
        r2 = r0;
        goto L_0x001b;
    L_0x0058:
        r2 = r11.zzc(r12, r13, r3);
        if (r2 == 0) goto L_0x006a;
    L_0x005e:
        r8 = com.google.android.gms.internal.clearcut.zzfd.zzk(r12, r6);
        r6 = com.google.android.gms.internal.clearcut.zzfd.zzk(r13, r6);
        r2 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1));
        if (r2 == 0) goto L_0x001a;
    L_0x006a:
        r2 = r0;
        goto L_0x001b;
    L_0x006c:
        r2 = r11.zzc(r12, r13, r3);
        if (r2 == 0) goto L_0x007c;
    L_0x0072:
        r2 = com.google.android.gms.internal.clearcut.zzfd.zzj(r12, r6);
        r5 = com.google.android.gms.internal.clearcut.zzfd.zzj(r13, r6);
        if (r2 == r5) goto L_0x001a;
    L_0x007c:
        r2 = r0;
        goto L_0x001b;
    L_0x007e:
        r2 = r11.zzc(r12, r13, r3);
        if (r2 == 0) goto L_0x0090;
    L_0x0084:
        r8 = com.google.android.gms.internal.clearcut.zzfd.zzk(r12, r6);
        r6 = com.google.android.gms.internal.clearcut.zzfd.zzk(r13, r6);
        r2 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1));
        if (r2 == 0) goto L_0x001a;
    L_0x0090:
        r2 = r0;
        goto L_0x001b;
    L_0x0092:
        r2 = r11.zzc(r12, r13, r3);
        if (r2 == 0) goto L_0x00a2;
    L_0x0098:
        r2 = com.google.android.gms.internal.clearcut.zzfd.zzj(r12, r6);
        r5 = com.google.android.gms.internal.clearcut.zzfd.zzj(r13, r6);
        if (r2 == r5) goto L_0x001a;
    L_0x00a2:
        r2 = r0;
        goto L_0x001b;
    L_0x00a5:
        r2 = r11.zzc(r12, r13, r3);
        if (r2 == 0) goto L_0x00b5;
    L_0x00ab:
        r2 = com.google.android.gms.internal.clearcut.zzfd.zzl(r12, r6);
        r5 = com.google.android.gms.internal.clearcut.zzfd.zzl(r13, r6);
        if (r2 == r5) goto L_0x001a;
    L_0x00b5:
        r2 = r0;
        goto L_0x001b;
    L_0x00b8:
        r2 = r11.zzc(r12, r13, r3);
        if (r2 == 0) goto L_0x00cc;
    L_0x00be:
        r2 = com.google.android.gms.internal.clearcut.zzfd.zzo(r12, r6);
        r5 = com.google.android.gms.internal.clearcut.zzfd.zzo(r13, r6);
        r2 = com.google.android.gms.internal.clearcut.zzeh.zzd(r2, r5);
        if (r2 != 0) goto L_0x001a;
    L_0x00cc:
        r2 = r0;
        goto L_0x001b;
    L_0x00cf:
        r2 = r11.zzc(r12, r13, r3);
        if (r2 == 0) goto L_0x00e3;
    L_0x00d5:
        r2 = com.google.android.gms.internal.clearcut.zzfd.zzo(r12, r6);
        r5 = com.google.android.gms.internal.clearcut.zzfd.zzo(r13, r6);
        r2 = com.google.android.gms.internal.clearcut.zzeh.zzd(r2, r5);
        if (r2 != 0) goto L_0x001a;
    L_0x00e3:
        r2 = r0;
        goto L_0x001b;
    L_0x00e6:
        r2 = r11.zzc(r12, r13, r3);
        if (r2 == 0) goto L_0x00fa;
    L_0x00ec:
        r2 = com.google.android.gms.internal.clearcut.zzfd.zzo(r12, r6);
        r5 = com.google.android.gms.internal.clearcut.zzfd.zzo(r13, r6);
        r2 = com.google.android.gms.internal.clearcut.zzeh.zzd(r2, r5);
        if (r2 != 0) goto L_0x001a;
    L_0x00fa:
        r2 = r0;
        goto L_0x001b;
    L_0x00fd:
        r2 = r11.zzc(r12, r13, r3);
        if (r2 == 0) goto L_0x010d;
    L_0x0103:
        r2 = com.google.android.gms.internal.clearcut.zzfd.zzj(r12, r6);
        r5 = com.google.android.gms.internal.clearcut.zzfd.zzj(r13, r6);
        if (r2 == r5) goto L_0x001a;
    L_0x010d:
        r2 = r0;
        goto L_0x001b;
    L_0x0110:
        r2 = r11.zzc(r12, r13, r3);
        if (r2 == 0) goto L_0x0120;
    L_0x0116:
        r2 = com.google.android.gms.internal.clearcut.zzfd.zzj(r12, r6);
        r5 = com.google.android.gms.internal.clearcut.zzfd.zzj(r13, r6);
        if (r2 == r5) goto L_0x001a;
    L_0x0120:
        r2 = r0;
        goto L_0x001b;
    L_0x0123:
        r2 = r11.zzc(r12, r13, r3);
        if (r2 == 0) goto L_0x0133;
    L_0x0129:
        r2 = com.google.android.gms.internal.clearcut.zzfd.zzj(r12, r6);
        r5 = com.google.android.gms.internal.clearcut.zzfd.zzj(r13, r6);
        if (r2 == r5) goto L_0x001a;
    L_0x0133:
        r2 = r0;
        goto L_0x001b;
    L_0x0136:
        r2 = r11.zzc(r12, r13, r3);
        if (r2 == 0) goto L_0x0148;
    L_0x013c:
        r8 = com.google.android.gms.internal.clearcut.zzfd.zzk(r12, r6);
        r6 = com.google.android.gms.internal.clearcut.zzfd.zzk(r13, r6);
        r2 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1));
        if (r2 == 0) goto L_0x001a;
    L_0x0148:
        r2 = r0;
        goto L_0x001b;
    L_0x014b:
        r2 = r11.zzc(r12, r13, r3);
        if (r2 == 0) goto L_0x015b;
    L_0x0151:
        r2 = com.google.android.gms.internal.clearcut.zzfd.zzj(r12, r6);
        r5 = com.google.android.gms.internal.clearcut.zzfd.zzj(r13, r6);
        if (r2 == r5) goto L_0x001a;
    L_0x015b:
        r2 = r0;
        goto L_0x001b;
    L_0x015e:
        r2 = r11.zzc(r12, r13, r3);
        if (r2 == 0) goto L_0x0170;
    L_0x0164:
        r8 = com.google.android.gms.internal.clearcut.zzfd.zzk(r12, r6);
        r6 = com.google.android.gms.internal.clearcut.zzfd.zzk(r13, r6);
        r2 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1));
        if (r2 == 0) goto L_0x001a;
    L_0x0170:
        r2 = r0;
        goto L_0x001b;
    L_0x0173:
        r2 = r11.zzc(r12, r13, r3);
        if (r2 == 0) goto L_0x0187;
    L_0x0179:
        r2 = com.google.android.gms.internal.clearcut.zzfd.zzo(r12, r6);
        r5 = com.google.android.gms.internal.clearcut.zzfd.zzo(r13, r6);
        r2 = com.google.android.gms.internal.clearcut.zzeh.zzd(r2, r5);
        if (r2 != 0) goto L_0x001a;
    L_0x0187:
        r2 = r0;
        goto L_0x001b;
    L_0x018a:
        r2 = com.google.android.gms.internal.clearcut.zzfd.zzo(r12, r6);
        r5 = com.google.android.gms.internal.clearcut.zzfd.zzo(r13, r6);
        r2 = com.google.android.gms.internal.clearcut.zzeh.zzd(r2, r5);
        goto L_0x001b;
    L_0x0198:
        r2 = com.google.android.gms.internal.clearcut.zzfd.zzo(r12, r6);
        r5 = com.google.android.gms.internal.clearcut.zzfd.zzo(r13, r6);
        r2 = com.google.android.gms.internal.clearcut.zzeh.zzd(r2, r5);
        goto L_0x001b;
    L_0x01a6:
        r2 = r11.zzah(r3);
        r5 = r2 & r10;
        r8 = (long) r5;
        r5 = com.google.android.gms.internal.clearcut.zzfd.zzj(r12, r8);
        r2 = r2 & r10;
        r8 = (long) r2;
        r2 = com.google.android.gms.internal.clearcut.zzfd.zzj(r13, r8);
        if (r5 != r2) goto L_0x01c7;
    L_0x01b9:
        r2 = com.google.android.gms.internal.clearcut.zzfd.zzo(r12, r6);
        r5 = com.google.android.gms.internal.clearcut.zzfd.zzo(r13, r6);
        r2 = com.google.android.gms.internal.clearcut.zzeh.zzd(r2, r5);
        if (r2 != 0) goto L_0x001a;
    L_0x01c7:
        r2 = r0;
        goto L_0x001b;
    L_0x01ca:
        r2 = r3 + 4;
        r3 = r2;
        goto L_0x0009;
    L_0x01cf:
        r2 = r11.zzmx;
        r2 = r2.zzq(r12);
        r3 = r11.zzmx;
        r3 = r3.zzq(r13);
        r2 = r2.equals(r3);
        if (r2 == 0) goto L_0x001d;
    L_0x01e1:
        r0 = r11.zzmo;
        if (r0 == 0) goto L_0x01f7;
    L_0x01e5:
        r0 = r11.zzmy;
        r0 = r0.zza(r12);
        r1 = r11.zzmy;
        r1 = r1.zza(r13);
        r0 = r0.equals(r1);
        goto L_0x001d;
    L_0x01f7:
        r0 = r1;
        goto L_0x001d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.clearcut.zzds.equals(java.lang.Object, java.lang.Object):boolean");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int hashCode(T r10) {
        /*
        r9 = this;
        r1 = 37;
        r0 = 0;
        r2 = r9.zzmi;
        r4 = r2.length;
        r3 = r0;
        r2 = r0;
    L_0x0008:
        if (r3 >= r4) goto L_0x0255;
    L_0x000a:
        r0 = r9.zzag(r3);
        r5 = r9.zzmi;
        r5 = r5[r3];
        r6 = 1048575; // 0xfffff float:1.469367E-39 double:5.18065E-318;
        r6 = r6 & r0;
        r6 = (long) r6;
        r8 = 267386880; // 0xff00000 float:2.3665827E-29 double:1.321066716E-315;
        r0 = r0 & r8;
        r0 = r0 >>> 20;
        switch(r0) {
            case 0: goto L_0x0025;
            case 1: goto L_0x0035;
            case 2: goto L_0x0041;
            case 3: goto L_0x004d;
            case 4: goto L_0x0059;
            case 5: goto L_0x0061;
            case 6: goto L_0x006d;
            case 7: goto L_0x0075;
            case 8: goto L_0x0081;
            case 9: goto L_0x008f;
            case 10: goto L_0x009d;
            case 11: goto L_0x00aa;
            case 12: goto L_0x00b3;
            case 13: goto L_0x00bc;
            case 14: goto L_0x00c5;
            case 15: goto L_0x00d2;
            case 16: goto L_0x00db;
            case 17: goto L_0x00e8;
            case 18: goto L_0x00f7;
            case 19: goto L_0x00f7;
            case 20: goto L_0x00f7;
            case 21: goto L_0x00f7;
            case 22: goto L_0x00f7;
            case 23: goto L_0x00f7;
            case 24: goto L_0x00f7;
            case 25: goto L_0x00f7;
            case 26: goto L_0x00f7;
            case 27: goto L_0x00f7;
            case 28: goto L_0x00f7;
            case 29: goto L_0x00f7;
            case 30: goto L_0x00f7;
            case 31: goto L_0x00f7;
            case 32: goto L_0x00f7;
            case 33: goto L_0x00f7;
            case 34: goto L_0x00f7;
            case 35: goto L_0x00f7;
            case 36: goto L_0x00f7;
            case 37: goto L_0x00f7;
            case 38: goto L_0x00f7;
            case 39: goto L_0x00f7;
            case 40: goto L_0x00f7;
            case 41: goto L_0x00f7;
            case 42: goto L_0x00f7;
            case 43: goto L_0x00f7;
            case 44: goto L_0x00f7;
            case 45: goto L_0x00f7;
            case 46: goto L_0x00f7;
            case 47: goto L_0x00f7;
            case 48: goto L_0x00f7;
            case 49: goto L_0x00f7;
            case 50: goto L_0x0104;
            case 51: goto L_0x0111;
            case 52: goto L_0x0128;
            case 53: goto L_0x013b;
            case 54: goto L_0x014e;
            case 55: goto L_0x0161;
            case 56: goto L_0x0170;
            case 57: goto L_0x0183;
            case 58: goto L_0x0192;
            case 59: goto L_0x01a5;
            case 60: goto L_0x01ba;
            case 61: goto L_0x01cd;
            case 62: goto L_0x01e0;
            case 63: goto L_0x01ef;
            case 64: goto L_0x01fe;
            case 65: goto L_0x020d;
            case 66: goto L_0x0220;
            case 67: goto L_0x022f;
            case 68: goto L_0x0242;
            default: goto L_0x001f;
        };
    L_0x001f:
        r0 = r2;
    L_0x0020:
        r2 = r3 + 4;
        r3 = r2;
        r2 = r0;
        goto L_0x0008;
    L_0x0025:
        r0 = r2 * 53;
        r6 = com.google.android.gms.internal.clearcut.zzfd.zzn(r10, r6);
        r6 = java.lang.Double.doubleToLongBits(r6);
        r2 = com.google.android.gms.internal.clearcut.zzci.zzl(r6);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x0035:
        r0 = r2 * 53;
        r2 = com.google.android.gms.internal.clearcut.zzfd.zzm(r10, r6);
        r2 = java.lang.Float.floatToIntBits(r2);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x0041:
        r0 = r2 * 53;
        r6 = com.google.android.gms.internal.clearcut.zzfd.zzk(r10, r6);
        r2 = com.google.android.gms.internal.clearcut.zzci.zzl(r6);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x004d:
        r0 = r2 * 53;
        r6 = com.google.android.gms.internal.clearcut.zzfd.zzk(r10, r6);
        r2 = com.google.android.gms.internal.clearcut.zzci.zzl(r6);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x0059:
        r0 = r2 * 53;
        r2 = com.google.android.gms.internal.clearcut.zzfd.zzj(r10, r6);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x0061:
        r0 = r2 * 53;
        r6 = com.google.android.gms.internal.clearcut.zzfd.zzk(r10, r6);
        r2 = com.google.android.gms.internal.clearcut.zzci.zzl(r6);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x006d:
        r0 = r2 * 53;
        r2 = com.google.android.gms.internal.clearcut.zzfd.zzj(r10, r6);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x0075:
        r0 = r2 * 53;
        r2 = com.google.android.gms.internal.clearcut.zzfd.zzl(r10, r6);
        r2 = com.google.android.gms.internal.clearcut.zzci.zzc(r2);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x0081:
        r2 = r2 * 53;
        r0 = com.google.android.gms.internal.clearcut.zzfd.zzo(r10, r6);
        r0 = (java.lang.String) r0;
        r0 = r0.hashCode();
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x008f:
        r0 = com.google.android.gms.internal.clearcut.zzfd.zzo(r10, r6);
        if (r0 == 0) goto L_0x0277;
    L_0x0095:
        r0 = r0.hashCode();
    L_0x0099:
        r2 = r2 * 53;
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x009d:
        r0 = r2 * 53;
        r2 = com.google.android.gms.internal.clearcut.zzfd.zzo(r10, r6);
        r2 = r2.hashCode();
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x00aa:
        r0 = r2 * 53;
        r2 = com.google.android.gms.internal.clearcut.zzfd.zzj(r10, r6);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x00b3:
        r0 = r2 * 53;
        r2 = com.google.android.gms.internal.clearcut.zzfd.zzj(r10, r6);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x00bc:
        r0 = r2 * 53;
        r2 = com.google.android.gms.internal.clearcut.zzfd.zzj(r10, r6);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x00c5:
        r0 = r2 * 53;
        r6 = com.google.android.gms.internal.clearcut.zzfd.zzk(r10, r6);
        r2 = com.google.android.gms.internal.clearcut.zzci.zzl(r6);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x00d2:
        r0 = r2 * 53;
        r2 = com.google.android.gms.internal.clearcut.zzfd.zzj(r10, r6);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x00db:
        r0 = r2 * 53;
        r6 = com.google.android.gms.internal.clearcut.zzfd.zzk(r10, r6);
        r2 = com.google.android.gms.internal.clearcut.zzci.zzl(r6);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x00e8:
        r0 = com.google.android.gms.internal.clearcut.zzfd.zzo(r10, r6);
        if (r0 == 0) goto L_0x0274;
    L_0x00ee:
        r0 = r0.hashCode();
    L_0x00f2:
        r2 = r2 * 53;
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x00f7:
        r0 = r2 * 53;
        r2 = com.google.android.gms.internal.clearcut.zzfd.zzo(r10, r6);
        r2 = r2.hashCode();
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x0104:
        r0 = r2 * 53;
        r2 = com.google.android.gms.internal.clearcut.zzfd.zzo(r10, r6);
        r2 = r2.hashCode();
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x0111:
        r0 = r9.zza(r10, r5, r3);
        if (r0 == 0) goto L_0x001f;
    L_0x0117:
        r0 = r2 * 53;
        r6 = zze(r10, r6);
        r6 = java.lang.Double.doubleToLongBits(r6);
        r2 = com.google.android.gms.internal.clearcut.zzci.zzl(r6);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x0128:
        r0 = r9.zza(r10, r5, r3);
        if (r0 == 0) goto L_0x001f;
    L_0x012e:
        r0 = r2 * 53;
        r2 = zzf(r10, r6);
        r2 = java.lang.Float.floatToIntBits(r2);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x013b:
        r0 = r9.zza(r10, r5, r3);
        if (r0 == 0) goto L_0x001f;
    L_0x0141:
        r0 = r2 * 53;
        r6 = zzh(r10, r6);
        r2 = com.google.android.gms.internal.clearcut.zzci.zzl(r6);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x014e:
        r0 = r9.zza(r10, r5, r3);
        if (r0 == 0) goto L_0x001f;
    L_0x0154:
        r0 = r2 * 53;
        r6 = zzh(r10, r6);
        r2 = com.google.android.gms.internal.clearcut.zzci.zzl(r6);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x0161:
        r0 = r9.zza(r10, r5, r3);
        if (r0 == 0) goto L_0x001f;
    L_0x0167:
        r0 = r2 * 53;
        r2 = zzg(r10, r6);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x0170:
        r0 = r9.zza(r10, r5, r3);
        if (r0 == 0) goto L_0x001f;
    L_0x0176:
        r0 = r2 * 53;
        r6 = zzh(r10, r6);
        r2 = com.google.android.gms.internal.clearcut.zzci.zzl(r6);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x0183:
        r0 = r9.zza(r10, r5, r3);
        if (r0 == 0) goto L_0x001f;
    L_0x0189:
        r0 = r2 * 53;
        r2 = zzg(r10, r6);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x0192:
        r0 = r9.zza(r10, r5, r3);
        if (r0 == 0) goto L_0x001f;
    L_0x0198:
        r0 = r2 * 53;
        r2 = zzi(r10, r6);
        r2 = com.google.android.gms.internal.clearcut.zzci.zzc(r2);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x01a5:
        r0 = r9.zza(r10, r5, r3);
        if (r0 == 0) goto L_0x001f;
    L_0x01ab:
        r2 = r2 * 53;
        r0 = com.google.android.gms.internal.clearcut.zzfd.zzo(r10, r6);
        r0 = (java.lang.String) r0;
        r0 = r0.hashCode();
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x01ba:
        r0 = r9.zza(r10, r5, r3);
        if (r0 == 0) goto L_0x001f;
    L_0x01c0:
        r0 = com.google.android.gms.internal.clearcut.zzfd.zzo(r10, r6);
        r2 = r2 * 53;
        r0 = r0.hashCode();
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x01cd:
        r0 = r9.zza(r10, r5, r3);
        if (r0 == 0) goto L_0x001f;
    L_0x01d3:
        r0 = r2 * 53;
        r2 = com.google.android.gms.internal.clearcut.zzfd.zzo(r10, r6);
        r2 = r2.hashCode();
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x01e0:
        r0 = r9.zza(r10, r5, r3);
        if (r0 == 0) goto L_0x001f;
    L_0x01e6:
        r0 = r2 * 53;
        r2 = zzg(r10, r6);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x01ef:
        r0 = r9.zza(r10, r5, r3);
        if (r0 == 0) goto L_0x001f;
    L_0x01f5:
        r0 = r2 * 53;
        r2 = zzg(r10, r6);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x01fe:
        r0 = r9.zza(r10, r5, r3);
        if (r0 == 0) goto L_0x001f;
    L_0x0204:
        r0 = r2 * 53;
        r2 = zzg(r10, r6);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x020d:
        r0 = r9.zza(r10, r5, r3);
        if (r0 == 0) goto L_0x001f;
    L_0x0213:
        r0 = r2 * 53;
        r6 = zzh(r10, r6);
        r2 = com.google.android.gms.internal.clearcut.zzci.zzl(r6);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x0220:
        r0 = r9.zza(r10, r5, r3);
        if (r0 == 0) goto L_0x001f;
    L_0x0226:
        r0 = r2 * 53;
        r2 = zzg(r10, r6);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x022f:
        r0 = r9.zza(r10, r5, r3);
        if (r0 == 0) goto L_0x001f;
    L_0x0235:
        r0 = r2 * 53;
        r6 = zzh(r10, r6);
        r2 = com.google.android.gms.internal.clearcut.zzci.zzl(r6);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x0242:
        r0 = r9.zza(r10, r5, r3);
        if (r0 == 0) goto L_0x001f;
    L_0x0248:
        r0 = com.google.android.gms.internal.clearcut.zzfd.zzo(r10, r6);
        r2 = r2 * 53;
        r0 = r0.hashCode();
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x0255:
        r0 = r2 * 53;
        r1 = r9.zzmx;
        r1 = r1.zzq(r10);
        r1 = r1.hashCode();
        r0 = r0 + r1;
        r1 = r9.zzmo;
        if (r1 == 0) goto L_0x0273;
    L_0x0266:
        r0 = r0 * 53;
        r1 = r9.zzmy;
        r1 = r1.zza(r10);
        r1 = r1.hashCode();
        r0 = r0 + r1;
    L_0x0273:
        return r0;
    L_0x0274:
        r0 = r1;
        goto L_0x00f2;
    L_0x0277:
        r0 = r1;
        goto L_0x0099;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.clearcut.zzds.hashCode(java.lang.Object):int");
    }

    public final T newInstance() {
        return this.zzmv.newInstance(this.zzmn);
    }

    public final void zza(T t, zzfr zzfr) {
        Iterator it;
        Entry entry;
        zzby zza;
        int length;
        int zzag;
        int i;
        Entry entry2;
        if (zzfr.zzaj() == zzg.zzkp) {
            zza(this.zzmx, (Object) t, zzfr);
            it = null;
            entry = null;
            if (this.zzmo) {
                zza = this.zzmy.zza((Object) t);
                if (!zza.isEmpty()) {
                    it = zza.descendingIterator();
                    entry = (Entry) it.next();
                }
            }
            length = this.zzmi.length - 4;
            while (length >= 0) {
                zzag = zzag(length);
                i = this.zzmi[length];
                entry2 = entry;
                while (entry2 != null && this.zzmy.zza(entry2) > i) {
                    this.zzmy.zza(zzfr, entry2);
                    entry2 = it.hasNext() ? (Entry) it.next() : null;
                }
                switch ((267386880 & zzag) >>> 20) {
                    case 0:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzfr.zza(i, zzfd.zzn(t, (long) (1048575 & zzag)));
                        break;
                    case 1:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzfr.zza(i, zzfd.zzm(t, (long) (1048575 & zzag)));
                        break;
                    case 2:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzfr.zzi(i, zzfd.zzk(t, (long) (1048575 & zzag)));
                        break;
                    case 3:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzfr.zza(i, zzfd.zzk(t, (long) (1048575 & zzag)));
                        break;
                    case 4:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzfr.zzc(i, zzfd.zzj(t, (long) (1048575 & zzag)));
                        break;
                    case 5:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzfr.zzc(i, zzfd.zzk(t, (long) (1048575 & zzag)));
                        break;
                    case 6:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzfr.zzf(i, zzfd.zzj(t, (long) (1048575 & zzag)));
                        break;
                    case 7:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzfr.zzb(i, zzfd.zzl(t, (long) (1048575 & zzag)));
                        break;
                    case 8:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zza(i, zzfd.zzo(t, (long) (1048575 & zzag)), zzfr);
                        break;
                    case 9:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzfr.zza(i, zzfd.zzo(t, (long) (1048575 & zzag)), zzad(length));
                        break;
                    case 10:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzfr.zza(i, (zzbb) zzfd.zzo(t, (long) (1048575 & zzag)));
                        break;
                    case 11:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzfr.zzd(i, zzfd.zzj(t, (long) (1048575 & zzag)));
                        break;
                    case 12:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzfr.zzn(i, zzfd.zzj(t, (long) (1048575 & zzag)));
                        break;
                    case 13:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzfr.zzm(i, zzfd.zzj(t, (long) (1048575 & zzag)));
                        break;
                    case 14:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzfr.zzj(i, zzfd.zzk(t, (long) (1048575 & zzag)));
                        break;
                    case 15:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzfr.zze(i, zzfd.zzj(t, (long) (1048575 & zzag)));
                        break;
                    case 16:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzfr.zzb(i, zzfd.zzk(t, (long) (1048575 & zzag)));
                        break;
                    case 17:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzfr.zzb(i, zzfd.zzo(t, (long) (1048575 & zzag)), zzad(length));
                        break;
                    case 18:
                        zzeh.zza(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & zzag)), zzfr, false);
                        break;
                    case 19:
                        zzeh.zzb(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & zzag)), zzfr, false);
                        break;
                    case 20:
                        zzeh.zzc(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & zzag)), zzfr, false);
                        break;
                    case 21:
                        zzeh.zzd(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & zzag)), zzfr, false);
                        break;
                    case 22:
                        zzeh.zzh(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & zzag)), zzfr, false);
                        break;
                    case 23:
                        zzeh.zzf(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & zzag)), zzfr, false);
                        break;
                    case 24:
                        zzeh.zzk(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & zzag)), zzfr, false);
                        break;
                    case 25:
                        zzeh.zzn(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & zzag)), zzfr, false);
                        break;
                    case 26:
                        zzeh.zza(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & zzag)), zzfr);
                        break;
                    case 27:
                        zzeh.zza(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & zzag)), zzfr, zzad(length));
                        break;
                    case 28:
                        zzeh.zzb(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & zzag)), zzfr);
                        break;
                    case 29:
                        zzeh.zzi(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & zzag)), zzfr, false);
                        break;
                    case 30:
                        zzeh.zzm(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & zzag)), zzfr, false);
                        break;
                    case 31:
                        zzeh.zzl(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & zzag)), zzfr, false);
                        break;
                    case 32:
                        zzeh.zzg(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & zzag)), zzfr, false);
                        break;
                    case 33:
                        zzeh.zzj(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & zzag)), zzfr, false);
                        break;
                    case 34:
                        zzeh.zze(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & zzag)), zzfr, false);
                        break;
                    case 35:
                        zzeh.zza(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & zzag)), zzfr, true);
                        break;
                    case 36:
                        zzeh.zzb(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & zzag)), zzfr, true);
                        break;
                    case 37:
                        zzeh.zzc(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & zzag)), zzfr, true);
                        break;
                    case 38:
                        zzeh.zzd(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & zzag)), zzfr, true);
                        break;
                    case 39:
                        zzeh.zzh(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & zzag)), zzfr, true);
                        break;
                    case 40:
                        zzeh.zzf(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & zzag)), zzfr, true);
                        break;
                    case 41:
                        zzeh.zzk(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & zzag)), zzfr, true);
                        break;
                    case 42:
                        zzeh.zzn(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & zzag)), zzfr, true);
                        break;
                    case 43:
                        zzeh.zzi(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & zzag)), zzfr, true);
                        break;
                    case 44:
                        zzeh.zzm(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & zzag)), zzfr, true);
                        break;
                    case 45:
                        zzeh.zzl(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & zzag)), zzfr, true);
                        break;
                    case 46:
                        zzeh.zzg(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & zzag)), zzfr, true);
                        break;
                    case 47:
                        zzeh.zzj(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & zzag)), zzfr, true);
                        break;
                    case 48:
                        zzeh.zze(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & zzag)), zzfr, true);
                        break;
                    case 49:
                        zzeh.zzb(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & zzag)), zzfr, zzad(length));
                        break;
                    case 50:
                        zza(zzfr, i, zzfd.zzo(t, (long) (1048575 & zzag)), length);
                        break;
                    case 51:
                        if (!zza((Object) t, i, length)) {
                            break;
                        }
                        zzfr.zza(i, zze(t, (long) (1048575 & zzag)));
                        break;
                    case 52:
                        if (!zza((Object) t, i, length)) {
                            break;
                        }
                        zzfr.zza(i, zzf(t, (long) (1048575 & zzag)));
                        break;
                    case 53:
                        if (!zza((Object) t, i, length)) {
                            break;
                        }
                        zzfr.zzi(i, zzh(t, (long) (1048575 & zzag)));
                        break;
                    case 54:
                        if (!zza((Object) t, i, length)) {
                            break;
                        }
                        zzfr.zza(i, zzh(t, (long) (1048575 & zzag)));
                        break;
                    case 55:
                        if (!zza((Object) t, i, length)) {
                            break;
                        }
                        zzfr.zzc(i, zzg(t, (long) (1048575 & zzag)));
                        break;
                    case 56:
                        if (!zza((Object) t, i, length)) {
                            break;
                        }
                        zzfr.zzc(i, zzh(t, (long) (1048575 & zzag)));
                        break;
                    case 57:
                        if (!zza((Object) t, i, length)) {
                            break;
                        }
                        zzfr.zzf(i, zzg(t, (long) (1048575 & zzag)));
                        break;
                    case 58:
                        if (!zza((Object) t, i, length)) {
                            break;
                        }
                        zzfr.zzb(i, zzi(t, (long) (1048575 & zzag)));
                        break;
                    case 59:
                        if (!zza((Object) t, i, length)) {
                            break;
                        }
                        zza(i, zzfd.zzo(t, (long) (1048575 & zzag)), zzfr);
                        break;
                    case 60:
                        if (!zza((Object) t, i, length)) {
                            break;
                        }
                        zzfr.zza(i, zzfd.zzo(t, (long) (1048575 & zzag)), zzad(length));
                        break;
                    case 61:
                        if (!zza((Object) t, i, length)) {
                            break;
                        }
                        zzfr.zza(i, (zzbb) zzfd.zzo(t, (long) (1048575 & zzag)));
                        break;
                    case 62:
                        if (!zza((Object) t, i, length)) {
                            break;
                        }
                        zzfr.zzd(i, zzg(t, (long) (1048575 & zzag)));
                        break;
                    case 63:
                        if (!zza((Object) t, i, length)) {
                            break;
                        }
                        zzfr.zzn(i, zzg(t, (long) (1048575 & zzag)));
                        break;
                    case 64:
                        if (!zza((Object) t, i, length)) {
                            break;
                        }
                        zzfr.zzm(i, zzg(t, (long) (1048575 & zzag)));
                        break;
                    case 65:
                        if (!zza((Object) t, i, length)) {
                            break;
                        }
                        zzfr.zzj(i, zzh(t, (long) (1048575 & zzag)));
                        break;
                    case 66:
                        if (!zza((Object) t, i, length)) {
                            break;
                        }
                        zzfr.zze(i, zzg(t, (long) (1048575 & zzag)));
                        break;
                    case 67:
                        if (!zza((Object) t, i, length)) {
                            break;
                        }
                        zzfr.zzb(i, zzh(t, (long) (1048575 & zzag)));
                        break;
                    case 68:
                        if (!zza((Object) t, i, length)) {
                            break;
                        }
                        zzfr.zzb(i, zzfd.zzo(t, (long) (1048575 & zzag)), zzad(length));
                        break;
                    default:
                        break;
                }
                length -= 4;
                entry = entry2;
            }
            while (entry != null) {
                this.zzmy.zza(zzfr, entry);
                entry = it.hasNext() ? (Entry) it.next() : null;
            }
        } else if (this.zzmq) {
            it = null;
            entry = null;
            if (this.zzmo) {
                zza = this.zzmy.zza((Object) t);
                if (!zza.isEmpty()) {
                    it = zza.iterator();
                    entry = (Entry) it.next();
                }
            }
            zzag = this.zzmi.length;
            length = 0;
            while (length < zzag) {
                i = zzag(length);
                int i2 = this.zzmi[length];
                entry2 = entry;
                while (entry2 != null && this.zzmy.zza(entry2) <= i2) {
                    this.zzmy.zza(zzfr, entry2);
                    entry2 = it.hasNext() ? (Entry) it.next() : null;
                }
                switch ((267386880 & i) >>> 20) {
                    case 0:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzfr.zza(i2, zzfd.zzn(t, (long) (1048575 & i)));
                        break;
                    case 1:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzfr.zza(i2, zzfd.zzm(t, (long) (1048575 & i)));
                        break;
                    case 2:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzfr.zzi(i2, zzfd.zzk(t, (long) (1048575 & i)));
                        break;
                    case 3:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzfr.zza(i2, zzfd.zzk(t, (long) (1048575 & i)));
                        break;
                    case 4:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzfr.zzc(i2, zzfd.zzj(t, (long) (1048575 & i)));
                        break;
                    case 5:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzfr.zzc(i2, zzfd.zzk(t, (long) (1048575 & i)));
                        break;
                    case 6:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzfr.zzf(i2, zzfd.zzj(t, (long) (1048575 & i)));
                        break;
                    case 7:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzfr.zzb(i2, zzfd.zzl(t, (long) (1048575 & i)));
                        break;
                    case 8:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zza(i2, zzfd.zzo(t, (long) (1048575 & i)), zzfr);
                        break;
                    case 9:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzfr.zza(i2, zzfd.zzo(t, (long) (1048575 & i)), zzad(length));
                        break;
                    case 10:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzfr.zza(i2, (zzbb) zzfd.zzo(t, (long) (1048575 & i)));
                        break;
                    case 11:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzfr.zzd(i2, zzfd.zzj(t, (long) (1048575 & i)));
                        break;
                    case 12:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzfr.zzn(i2, zzfd.zzj(t, (long) (1048575 & i)));
                        break;
                    case 13:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzfr.zzm(i2, zzfd.zzj(t, (long) (1048575 & i)));
                        break;
                    case 14:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzfr.zzj(i2, zzfd.zzk(t, (long) (1048575 & i)));
                        break;
                    case 15:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzfr.zze(i2, zzfd.zzj(t, (long) (1048575 & i)));
                        break;
                    case 16:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzfr.zzb(i2, zzfd.zzk(t, (long) (1048575 & i)));
                        break;
                    case 17:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzfr.zzb(i2, zzfd.zzo(t, (long) (1048575 & i)), zzad(length));
                        break;
                    case 18:
                        zzeh.zza(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & i)), zzfr, false);
                        break;
                    case 19:
                        zzeh.zzb(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & i)), zzfr, false);
                        break;
                    case 20:
                        zzeh.zzc(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & i)), zzfr, false);
                        break;
                    case 21:
                        zzeh.zzd(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & i)), zzfr, false);
                        break;
                    case 22:
                        zzeh.zzh(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & i)), zzfr, false);
                        break;
                    case 23:
                        zzeh.zzf(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & i)), zzfr, false);
                        break;
                    case 24:
                        zzeh.zzk(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & i)), zzfr, false);
                        break;
                    case 25:
                        zzeh.zzn(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & i)), zzfr, false);
                        break;
                    case 26:
                        zzeh.zza(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & i)), zzfr);
                        break;
                    case 27:
                        zzeh.zza(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & i)), zzfr, zzad(length));
                        break;
                    case 28:
                        zzeh.zzb(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & i)), zzfr);
                        break;
                    case 29:
                        zzeh.zzi(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & i)), zzfr, false);
                        break;
                    case 30:
                        zzeh.zzm(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & i)), zzfr, false);
                        break;
                    case 31:
                        zzeh.zzl(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & i)), zzfr, false);
                        break;
                    case 32:
                        zzeh.zzg(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & i)), zzfr, false);
                        break;
                    case 33:
                        zzeh.zzj(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & i)), zzfr, false);
                        break;
                    case 34:
                        zzeh.zze(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & i)), zzfr, false);
                        break;
                    case 35:
                        zzeh.zza(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & i)), zzfr, true);
                        break;
                    case 36:
                        zzeh.zzb(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & i)), zzfr, true);
                        break;
                    case 37:
                        zzeh.zzc(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & i)), zzfr, true);
                        break;
                    case 38:
                        zzeh.zzd(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & i)), zzfr, true);
                        break;
                    case 39:
                        zzeh.zzh(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & i)), zzfr, true);
                        break;
                    case 40:
                        zzeh.zzf(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & i)), zzfr, true);
                        break;
                    case 41:
                        zzeh.zzk(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & i)), zzfr, true);
                        break;
                    case 42:
                        zzeh.zzn(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & i)), zzfr, true);
                        break;
                    case 43:
                        zzeh.zzi(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & i)), zzfr, true);
                        break;
                    case 44:
                        zzeh.zzm(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & i)), zzfr, true);
                        break;
                    case 45:
                        zzeh.zzl(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & i)), zzfr, true);
                        break;
                    case 46:
                        zzeh.zzg(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & i)), zzfr, true);
                        break;
                    case 47:
                        zzeh.zzj(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & i)), zzfr, true);
                        break;
                    case 48:
                        zzeh.zze(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & i)), zzfr, true);
                        break;
                    case 49:
                        zzeh.zzb(this.zzmi[length], (List) zzfd.zzo(t, (long) (1048575 & i)), zzfr, zzad(length));
                        break;
                    case 50:
                        zza(zzfr, i2, zzfd.zzo(t, (long) (1048575 & i)), length);
                        break;
                    case 51:
                        if (!zza((Object) t, i2, length)) {
                            break;
                        }
                        zzfr.zza(i2, zze(t, (long) (1048575 & i)));
                        break;
                    case 52:
                        if (!zza((Object) t, i2, length)) {
                            break;
                        }
                        zzfr.zza(i2, zzf(t, (long) (1048575 & i)));
                        break;
                    case 53:
                        if (!zza((Object) t, i2, length)) {
                            break;
                        }
                        zzfr.zzi(i2, zzh(t, (long) (1048575 & i)));
                        break;
                    case 54:
                        if (!zza((Object) t, i2, length)) {
                            break;
                        }
                        zzfr.zza(i2, zzh(t, (long) (1048575 & i)));
                        break;
                    case 55:
                        if (!zza((Object) t, i2, length)) {
                            break;
                        }
                        zzfr.zzc(i2, zzg(t, (long) (1048575 & i)));
                        break;
                    case 56:
                        if (!zza((Object) t, i2, length)) {
                            break;
                        }
                        zzfr.zzc(i2, zzh(t, (long) (1048575 & i)));
                        break;
                    case 57:
                        if (!zza((Object) t, i2, length)) {
                            break;
                        }
                        zzfr.zzf(i2, zzg(t, (long) (1048575 & i)));
                        break;
                    case 58:
                        if (!zza((Object) t, i2, length)) {
                            break;
                        }
                        zzfr.zzb(i2, zzi(t, (long) (1048575 & i)));
                        break;
                    case 59:
                        if (!zza((Object) t, i2, length)) {
                            break;
                        }
                        zza(i2, zzfd.zzo(t, (long) (1048575 & i)), zzfr);
                        break;
                    case 60:
                        if (!zza((Object) t, i2, length)) {
                            break;
                        }
                        zzfr.zza(i2, zzfd.zzo(t, (long) (1048575 & i)), zzad(length));
                        break;
                    case 61:
                        if (!zza((Object) t, i2, length)) {
                            break;
                        }
                        zzfr.zza(i2, (zzbb) zzfd.zzo(t, (long) (1048575 & i)));
                        break;
                    case 62:
                        if (!zza((Object) t, i2, length)) {
                            break;
                        }
                        zzfr.zzd(i2, zzg(t, (long) (1048575 & i)));
                        break;
                    case 63:
                        if (!zza((Object) t, i2, length)) {
                            break;
                        }
                        zzfr.zzn(i2, zzg(t, (long) (1048575 & i)));
                        break;
                    case 64:
                        if (!zza((Object) t, i2, length)) {
                            break;
                        }
                        zzfr.zzm(i2, zzg(t, (long) (1048575 & i)));
                        break;
                    case 65:
                        if (!zza((Object) t, i2, length)) {
                            break;
                        }
                        zzfr.zzj(i2, zzh(t, (long) (1048575 & i)));
                        break;
                    case 66:
                        if (!zza((Object) t, i2, length)) {
                            break;
                        }
                        zzfr.zze(i2, zzg(t, (long) (1048575 & i)));
                        break;
                    case 67:
                        if (!zza((Object) t, i2, length)) {
                            break;
                        }
                        zzfr.zzb(i2, zzh(t, (long) (1048575 & i)));
                        break;
                    case 68:
                        if (!zza((Object) t, i2, length)) {
                            break;
                        }
                        zzfr.zzb(i2, zzfd.zzo(t, (long) (1048575 & i)), zzad(length));
                        break;
                    default:
                        break;
                }
                length += 4;
                entry = entry2;
            }
            while (entry != null) {
                this.zzmy.zza(zzfr, entry);
                entry = it.hasNext() ? (Entry) it.next() : null;
            }
            zza(this.zzmx, (Object) t, zzfr);
        } else {
            zzb((Object) t, zzfr);
        }
    }

    public final void zza(T t, byte[] bArr, int i, int i2, zzay zzay) {
        if (this.zzmq) {
            Unsafe unsafe = zzmh;
            int i3 = i;
            while (i3 < i2) {
                int i4 = i3 + 1;
                int i5 = bArr[i3];
                if (i5 < 0) {
                    i4 = zzax.zza(i5, bArr, i4, zzay);
                    i5 = zzay.zzfd;
                }
                int i6 = i5 >>> 3;
                i3 = i5 & 7;
                int zzai = zzai(i6);
                if (zzai >= 0) {
                    int i7 = this.zzmi[zzai + 1];
                    int i8 = (267386880 & i7) >>> 20;
                    long j = (long) (1048575 & i7);
                    if (i8 <= 17) {
                        switch (i8) {
                            case 0:
                                if (i3 == 1) {
                                    zzfd.zza((Object) t, j, zzax.zze(bArr, i4));
                                    i3 = i4 + 8;
                                    continue;
                                }
                                break;
                            case 1:
                                if (i3 == 5) {
                                    zzfd.zza((Object) t, j, zzax.zzf(bArr, i4));
                                    i3 = i4 + 4;
                                    continue;
                                }
                                break;
                            case 2:
                            case 3:
                                if (i3 == 0) {
                                    i3 = zzax.zzb(bArr, i4, zzay);
                                    unsafe.putLong(t, j, zzay.zzfe);
                                    continue;
                                }
                                break;
                            case 4:
                            case 11:
                                if (i3 == 0) {
                                    i3 = zzax.zza(bArr, i4, zzay);
                                    unsafe.putInt(t, j, zzay.zzfd);
                                    continue;
                                }
                                break;
                            case 5:
                            case 14:
                                if (i3 == 1) {
                                    unsafe.putLong(t, j, zzax.zzd(bArr, i4));
                                    i3 = i4 + 8;
                                    continue;
                                }
                                break;
                            case 6:
                            case 13:
                                if (i3 == 5) {
                                    unsafe.putInt(t, j, zzax.zzc(bArr, i4));
                                    i3 = i4 + 4;
                                    continue;
                                }
                                break;
                            case 7:
                                if (i3 == 0) {
                                    i3 = zzax.zzb(bArr, i4, zzay);
                                    zzfd.zza((Object) t, j, zzay.zzfe != 0);
                                    continue;
                                }
                                break;
                            case 8:
                                if (i3 == 2) {
                                    int zzc = (ErrorDialogData.DYNAMITE_CRASH & i7) == 0 ? zzax.zzc(bArr, i4, zzay) : zzax.zzd(bArr, i4, zzay);
                                    unsafe.putObject(t, j, zzay.zzff);
                                    i3 = zzc;
                                    continue;
                                }
                                break;
                            case 9:
                                if (i3 == 2) {
                                    i3 = zza(zzad(zzai), bArr, i4, i2, zzay);
                                    Object object = unsafe.getObject(t, j);
                                    if (object != null) {
                                        unsafe.putObject(t, j, zzci.zza(object, zzay.zzff));
                                        break;
                                    } else {
                                        unsafe.putObject(t, j, zzay.zzff);
                                        continue;
                                    }
                                }
                                break;
                            case 10:
                                if (i3 == 2) {
                                    i3 = zzax.zze(bArr, i4, zzay);
                                    unsafe.putObject(t, j, zzay.zzff);
                                    continue;
                                }
                                break;
                            case 12:
                                if (i3 == 0) {
                                    i3 = zzax.zza(bArr, i4, zzay);
                                    unsafe.putInt(t, j, zzay.zzfd);
                                    continue;
                                }
                                break;
                            case 15:
                                if (i3 == 0) {
                                    i3 = zzax.zza(bArr, i4, zzay);
                                    unsafe.putInt(t, j, zzbk.zzm(zzay.zzfd));
                                    continue;
                                }
                                break;
                            case 16:
                                if (i3 == 0) {
                                    i3 = zzax.zzb(bArr, i4, zzay);
                                    unsafe.putLong(t, j, zzbk.zza(zzay.zzfe));
                                    continue;
                                }
                                break;
                            default:
                                i3 = i4;
                                break;
                        }
                    } else if (i8 == 27) {
                        if (i3 == 2) {
                            zzcn zzcn;
                            zzcn zzcn2 = (zzcn) unsafe.getObject(t, j);
                            if (zzcn2.zzu()) {
                                zzcn = zzcn2;
                            } else {
                                int size = zzcn2.size();
                                zzcn = zzcn2.zzi(size == 0 ? 10 : size << 1);
                                unsafe.putObject(t, j, zzcn);
                            }
                            i3 = zza(zzad(zzai), i5, bArr, i4, i2, zzcn, zzay);
                        }
                    } else if (i8 <= 49) {
                        i3 = zza((Object) t, bArr, i4, i2, i5, i6, i3, zzai, (long) i7, i8, j, zzay);
                        if (i3 == i4) {
                            i3 = zza(i5, bArr, i3, i2, (Object) t, zzay);
                        }
                    } else if (i8 != 50) {
                        i3 = zza((Object) t, bArr, i4, i2, i5, i6, i3, i7, i8, j, zzai, zzay);
                        if (i3 == i4) {
                            i3 = zza(i5, bArr, i3, i2, (Object) t, zzay);
                        }
                    } else if (i3 == 2) {
                        i3 = zza(t, bArr, i4, i2, zzai, i6, j, zzay);
                        if (i3 == i4) {
                            i3 = zza(i5, bArr, i3, i2, (Object) t, zzay);
                        }
                    }
                }
                i3 = i4;
                i3 = zza(i5, bArr, i3, i2, (Object) t, zzay);
            }
            if (i3 != i2) {
                throw zzco.zzbo();
            }
            return;
        }
        zza((Object) t, bArr, i, i2, 0, zzay);
    }

    public final void zzc(T t) {
        int i = 0;
        if (this.zzmt != null) {
            for (int zzag : this.zzmt) {
                long zzag2 = (long) (zzag(zzag) & 1048575);
                Object zzo = zzfd.zzo(t, zzag2);
                if (zzo != null) {
                    zzfd.zza((Object) t, zzag2, this.zzmz.zzj(zzo));
                }
            }
        }
        if (this.zzmu != null) {
            int[] iArr = this.zzmu;
            int length = iArr.length;
            while (i < length) {
                this.zzmw.zza(t, (long) iArr[i]);
                i++;
            }
        }
        this.zzmx.zzc(t);
        if (this.zzmo) {
            this.zzmy.zzc(t);
        }
    }

    public final void zzc(T t, T t2) {
        if (t2 == null) {
            throw new NullPointerException();
        }
        for (int i = 0; i < this.zzmi.length; i += 4) {
            int zzag = zzag(i);
            long j = (long) (1048575 & zzag);
            int i2 = this.zzmi[i];
            switch ((zzag & 267386880) >>> 20) {
                case 0:
                    if (!zza((Object) t2, i)) {
                        break;
                    }
                    zzfd.zza((Object) t, j, zzfd.zzn(t2, j));
                    zzb((Object) t, i);
                    break;
                case 1:
                    if (!zza((Object) t2, i)) {
                        break;
                    }
                    zzfd.zza((Object) t, j, zzfd.zzm(t2, j));
                    zzb((Object) t, i);
                    break;
                case 2:
                    if (!zza((Object) t2, i)) {
                        break;
                    }
                    zzfd.zza((Object) t, j, zzfd.zzk(t2, j));
                    zzb((Object) t, i);
                    break;
                case 3:
                    if (!zza((Object) t2, i)) {
                        break;
                    }
                    zzfd.zza((Object) t, j, zzfd.zzk(t2, j));
                    zzb((Object) t, i);
                    break;
                case 4:
                    if (!zza((Object) t2, i)) {
                        break;
                    }
                    zzfd.zza((Object) t, j, zzfd.zzj(t2, j));
                    zzb((Object) t, i);
                    break;
                case 5:
                    if (!zza((Object) t2, i)) {
                        break;
                    }
                    zzfd.zza((Object) t, j, zzfd.zzk(t2, j));
                    zzb((Object) t, i);
                    break;
                case 6:
                    if (!zza((Object) t2, i)) {
                        break;
                    }
                    zzfd.zza((Object) t, j, zzfd.zzj(t2, j));
                    zzb((Object) t, i);
                    break;
                case 7:
                    if (!zza((Object) t2, i)) {
                        break;
                    }
                    zzfd.zza((Object) t, j, zzfd.zzl(t2, j));
                    zzb((Object) t, i);
                    break;
                case 8:
                    if (!zza((Object) t2, i)) {
                        break;
                    }
                    zzfd.zza((Object) t, j, zzfd.zzo(t2, j));
                    zzb((Object) t, i);
                    break;
                case 9:
                    zza((Object) t, (Object) t2, i);
                    break;
                case 10:
                    if (!zza((Object) t2, i)) {
                        break;
                    }
                    zzfd.zza((Object) t, j, zzfd.zzo(t2, j));
                    zzb((Object) t, i);
                    break;
                case 11:
                    if (!zza((Object) t2, i)) {
                        break;
                    }
                    zzfd.zza((Object) t, j, zzfd.zzj(t2, j));
                    zzb((Object) t, i);
                    break;
                case 12:
                    if (!zza((Object) t2, i)) {
                        break;
                    }
                    zzfd.zza((Object) t, j, zzfd.zzj(t2, j));
                    zzb((Object) t, i);
                    break;
                case 13:
                    if (!zza((Object) t2, i)) {
                        break;
                    }
                    zzfd.zza((Object) t, j, zzfd.zzj(t2, j));
                    zzb((Object) t, i);
                    break;
                case 14:
                    if (!zza((Object) t2, i)) {
                        break;
                    }
                    zzfd.zza((Object) t, j, zzfd.zzk(t2, j));
                    zzb((Object) t, i);
                    break;
                case 15:
                    if (!zza((Object) t2, i)) {
                        break;
                    }
                    zzfd.zza((Object) t, j, zzfd.zzj(t2, j));
                    zzb((Object) t, i);
                    break;
                case 16:
                    if (!zza((Object) t2, i)) {
                        break;
                    }
                    zzfd.zza((Object) t, j, zzfd.zzk(t2, j));
                    zzb((Object) t, i);
                    break;
                case 17:
                    zza((Object) t, (Object) t2, i);
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    this.zzmw.zza(t, t2, j);
                    break;
                case 50:
                    zzeh.zza(this.zzmz, (Object) t, (Object) t2, j);
                    break;
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                case 58:
                case 59:
                    if (!zza((Object) t2, i2, i)) {
                        break;
                    }
                    zzfd.zza((Object) t, j, zzfd.zzo(t2, j));
                    zzb((Object) t, i2, i);
                    break;
                case 60:
                    zzb((Object) t, (Object) t2, i);
                    break;
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case 66:
                case 67:
                    if (!zza((Object) t2, i2, i)) {
                        break;
                    }
                    zzfd.zza((Object) t, j, zzfd.zzo(t2, j));
                    zzb((Object) t, i2, i);
                    break;
                case 68:
                    zzb((Object) t, (Object) t2, i);
                    break;
                default:
                    break;
            }
        }
        if (!this.zzmq) {
            zzeh.zza(this.zzmx, (Object) t, (Object) t2);
            if (this.zzmo) {
                zzeh.zza(this.zzmy, (Object) t, (Object) t2);
            }
        }
    }

    public final int zzm(T t) {
        int i;
        int i2;
        int zzag;
        int i3;
        int i4;
        int i5;
        Object zzo;
        if (this.zzmq) {
            Unsafe unsafe = zzmh;
            i = 0;
            for (i2 = 0; i2 < this.zzmi.length; i2 += 4) {
                zzag = zzag(i2);
                i3 = (267386880 & zzag) >>> 20;
                i4 = this.zzmi[i2];
                long j = (long) (zzag & 1048575);
                i5 = (i3 < zzcb.DOUBLE_LIST_PACKED.id() || i3 > zzcb.SINT64_LIST_PACKED.id()) ? 0 : this.zzmi[i2 + 2] & 1048575;
                switch (i3) {
                    case 0:
                        if (!zza((Object) t, i2)) {
                            break;
                        }
                        i += zzbn.zzb(i4, 0.0d);
                        break;
                    case 1:
                        if (!zza((Object) t, i2)) {
                            break;
                        }
                        i += zzbn.zzb(i4, (float) BitmapDescriptorFactory.HUE_RED);
                        break;
                    case 2:
                        if (!zza((Object) t, i2)) {
                            break;
                        }
                        i += zzbn.zzd(i4, zzfd.zzk(t, j));
                        break;
                    case 3:
                        if (!zza((Object) t, i2)) {
                            break;
                        }
                        i += zzbn.zze(i4, zzfd.zzk(t, j));
                        break;
                    case 4:
                        if (!zza((Object) t, i2)) {
                            break;
                        }
                        i += zzbn.zzg(i4, zzfd.zzj(t, j));
                        break;
                    case 5:
                        if (!zza((Object) t, i2)) {
                            break;
                        }
                        i += zzbn.zzg(i4, 0);
                        break;
                    case 6:
                        if (!zza((Object) t, i2)) {
                            break;
                        }
                        i += zzbn.zzj(i4, 0);
                        break;
                    case 7:
                        if (!zza((Object) t, i2)) {
                            break;
                        }
                        i += zzbn.zzc(i4, true);
                        break;
                    case 8:
                        if (!zza((Object) t, i2)) {
                            break;
                        }
                        zzo = zzfd.zzo(t, j);
                        if (!(zzo instanceof zzbb)) {
                            i += zzbn.zzb(i4, (String) zzo);
                            break;
                        }
                        i += zzbn.zzc(i4, (zzbb) zzo);
                        break;
                    case 9:
                        if (!zza((Object) t, i2)) {
                            break;
                        }
                        i += zzeh.zzc(i4, zzfd.zzo(t, j), zzad(i2));
                        break;
                    case 10:
                        if (!zza((Object) t, i2)) {
                            break;
                        }
                        i += zzbn.zzc(i4, (zzbb) zzfd.zzo(t, j));
                        break;
                    case 11:
                        if (!zza((Object) t, i2)) {
                            break;
                        }
                        i += zzbn.zzh(i4, zzfd.zzj(t, j));
                        break;
                    case 12:
                        if (!zza((Object) t, i2)) {
                            break;
                        }
                        i += zzbn.zzl(i4, zzfd.zzj(t, j));
                        break;
                    case 13:
                        if (!zza((Object) t, i2)) {
                            break;
                        }
                        i += zzbn.zzk(i4, 0);
                        break;
                    case 14:
                        if (!zza((Object) t, i2)) {
                            break;
                        }
                        i += zzbn.zzh(i4, 0);
                        break;
                    case 15:
                        if (!zza((Object) t, i2)) {
                            break;
                        }
                        i += zzbn.zzi(i4, zzfd.zzj(t, j));
                        break;
                    case 16:
                        if (!zza((Object) t, i2)) {
                            break;
                        }
                        i += zzbn.zzf(i4, zzfd.zzk(t, j));
                        break;
                    case 17:
                        if (!zza((Object) t, i2)) {
                            break;
                        }
                        i += zzbn.zzc(i4, (zzdo) zzfd.zzo(t, j), zzad(i2));
                        break;
                    case 18:
                        i += zzeh.zzw(i4, zzd(t, j), false);
                        break;
                    case 19:
                        i += zzeh.zzv(i4, zzd(t, j), false);
                        break;
                    case 20:
                        i += zzeh.zzo(i4, zzd(t, j), false);
                        break;
                    case 21:
                        i += zzeh.zzp(i4, zzd(t, j), false);
                        break;
                    case 22:
                        i += zzeh.zzs(i4, zzd(t, j), false);
                        break;
                    case 23:
                        i += zzeh.zzw(i4, zzd(t, j), false);
                        break;
                    case 24:
                        i += zzeh.zzv(i4, zzd(t, j), false);
                        break;
                    case 25:
                        i += zzeh.zzx(i4, zzd(t, j), false);
                        break;
                    case 26:
                        i += zzeh.zzc(i4, zzd(t, j));
                        break;
                    case 27:
                        i += zzeh.zzc(i4, zzd(t, j), zzad(i2));
                        break;
                    case 28:
                        i += zzeh.zzd(i4, zzd(t, j));
                        break;
                    case 29:
                        i += zzeh.zzt(i4, zzd(t, j), false);
                        break;
                    case 30:
                        i += zzeh.zzr(i4, zzd(t, j), false);
                        break;
                    case 31:
                        i += zzeh.zzv(i4, zzd(t, j), false);
                        break;
                    case 32:
                        i += zzeh.zzw(i4, zzd(t, j), false);
                        break;
                    case 33:
                        i += zzeh.zzu(i4, zzd(t, j), false);
                        break;
                    case 34:
                        i += zzeh.zzq(i4, zzd(t, j), false);
                        break;
                    case 35:
                        zzag = zzeh.zzi((List) unsafe.getObject(t, j));
                        if (zzag > 0) {
                            if (this.zzmr) {
                                unsafe.putInt(t, (long) i5, zzag);
                            }
                            i += zzag + (zzbn.zzr(i4) + zzbn.zzt(zzag));
                            break;
                        }
                        break;
                    case 36:
                        zzag = zzeh.zzh((List) unsafe.getObject(t, j));
                        if (zzag > 0) {
                            if (this.zzmr) {
                                unsafe.putInt(t, (long) i5, zzag);
                            }
                            i += zzag + (zzbn.zzr(i4) + zzbn.zzt(zzag));
                            break;
                        }
                        break;
                    case 37:
                        zzag = zzeh.zza((List) unsafe.getObject(t, j));
                        if (zzag > 0) {
                            if (this.zzmr) {
                                unsafe.putInt(t, (long) i5, zzag);
                            }
                            i += zzag + (zzbn.zzr(i4) + zzbn.zzt(zzag));
                            break;
                        }
                        break;
                    case 38:
                        zzag = zzeh.zzb((List) unsafe.getObject(t, j));
                        if (zzag > 0) {
                            if (this.zzmr) {
                                unsafe.putInt(t, (long) i5, zzag);
                            }
                            i += zzag + (zzbn.zzr(i4) + zzbn.zzt(zzag));
                            break;
                        }
                        break;
                    case 39:
                        zzag = zzeh.zze((List) unsafe.getObject(t, j));
                        if (zzag > 0) {
                            if (this.zzmr) {
                                unsafe.putInt(t, (long) i5, zzag);
                            }
                            i += zzag + (zzbn.zzr(i4) + zzbn.zzt(zzag));
                            break;
                        }
                        break;
                    case 40:
                        zzag = zzeh.zzi((List) unsafe.getObject(t, j));
                        if (zzag > 0) {
                            if (this.zzmr) {
                                unsafe.putInt(t, (long) i5, zzag);
                            }
                            i += zzag + (zzbn.zzr(i4) + zzbn.zzt(zzag));
                            break;
                        }
                        break;
                    case 41:
                        zzag = zzeh.zzh((List) unsafe.getObject(t, j));
                        if (zzag > 0) {
                            if (this.zzmr) {
                                unsafe.putInt(t, (long) i5, zzag);
                            }
                            i += zzag + (zzbn.zzr(i4) + zzbn.zzt(zzag));
                            break;
                        }
                        break;
                    case 42:
                        zzag = zzeh.zzj((List) unsafe.getObject(t, j));
                        if (zzag > 0) {
                            if (this.zzmr) {
                                unsafe.putInt(t, (long) i5, zzag);
                            }
                            i += zzag + (zzbn.zzr(i4) + zzbn.zzt(zzag));
                            break;
                        }
                        break;
                    case 43:
                        zzag = zzeh.zzf((List) unsafe.getObject(t, j));
                        if (zzag > 0) {
                            if (this.zzmr) {
                                unsafe.putInt(t, (long) i5, zzag);
                            }
                            i += zzag + (zzbn.zzr(i4) + zzbn.zzt(zzag));
                            break;
                        }
                        break;
                    case 44:
                        zzag = zzeh.zzd((List) unsafe.getObject(t, j));
                        if (zzag > 0) {
                            if (this.zzmr) {
                                unsafe.putInt(t, (long) i5, zzag);
                            }
                            i += zzag + (zzbn.zzr(i4) + zzbn.zzt(zzag));
                            break;
                        }
                        break;
                    case 45:
                        zzag = zzeh.zzh((List) unsafe.getObject(t, j));
                        if (zzag > 0) {
                            if (this.zzmr) {
                                unsafe.putInt(t, (long) i5, zzag);
                            }
                            i += zzag + (zzbn.zzr(i4) + zzbn.zzt(zzag));
                            break;
                        }
                        break;
                    case 46:
                        zzag = zzeh.zzi((List) unsafe.getObject(t, j));
                        if (zzag > 0) {
                            if (this.zzmr) {
                                unsafe.putInt(t, (long) i5, zzag);
                            }
                            i += zzag + (zzbn.zzr(i4) + zzbn.zzt(zzag));
                            break;
                        }
                        break;
                    case 47:
                        zzag = zzeh.zzg((List) unsafe.getObject(t, j));
                        if (zzag > 0) {
                            if (this.zzmr) {
                                unsafe.putInt(t, (long) i5, zzag);
                            }
                            i += zzag + (zzbn.zzr(i4) + zzbn.zzt(zzag));
                            break;
                        }
                        break;
                    case 48:
                        zzag = zzeh.zzc((List) unsafe.getObject(t, j));
                        if (zzag > 0) {
                            if (this.zzmr) {
                                unsafe.putInt(t, (long) i5, zzag);
                            }
                            i += zzag + (zzbn.zzr(i4) + zzbn.zzt(zzag));
                            break;
                        }
                        break;
                    case 49:
                        i += zzeh.zzd(i4, zzd(t, j), zzad(i2));
                        break;
                    case 50:
                        i += this.zzmz.zzb(i4, zzfd.zzo(t, j), zzae(i2));
                        break;
                    case 51:
                        if (!zza((Object) t, i4, i2)) {
                            break;
                        }
                        i += zzbn.zzb(i4, 0.0d);
                        break;
                    case 52:
                        if (!zza((Object) t, i4, i2)) {
                            break;
                        }
                        i += zzbn.zzb(i4, (float) BitmapDescriptorFactory.HUE_RED);
                        break;
                    case 53:
                        if (!zza((Object) t, i4, i2)) {
                            break;
                        }
                        i += zzbn.zzd(i4, zzh(t, j));
                        break;
                    case 54:
                        if (!zza((Object) t, i4, i2)) {
                            break;
                        }
                        i += zzbn.zze(i4, zzh(t, j));
                        break;
                    case 55:
                        if (!zza((Object) t, i4, i2)) {
                            break;
                        }
                        i += zzbn.zzg(i4, zzg(t, j));
                        break;
                    case 56:
                        if (!zza((Object) t, i4, i2)) {
                            break;
                        }
                        i += zzbn.zzg(i4, 0);
                        break;
                    case 57:
                        if (!zza((Object) t, i4, i2)) {
                            break;
                        }
                        i += zzbn.zzj(i4, 0);
                        break;
                    case 58:
                        if (!zza((Object) t, i4, i2)) {
                            break;
                        }
                        i += zzbn.zzc(i4, true);
                        break;
                    case 59:
                        if (!zza((Object) t, i4, i2)) {
                            break;
                        }
                        zzo = zzfd.zzo(t, j);
                        if (!(zzo instanceof zzbb)) {
                            i += zzbn.zzb(i4, (String) zzo);
                            break;
                        }
                        i += zzbn.zzc(i4, (zzbb) zzo);
                        break;
                    case 60:
                        if (!zza((Object) t, i4, i2)) {
                            break;
                        }
                        i += zzeh.zzc(i4, zzfd.zzo(t, j), zzad(i2));
                        break;
                    case 61:
                        if (!zza((Object) t, i4, i2)) {
                            break;
                        }
                        i += zzbn.zzc(i4, (zzbb) zzfd.zzo(t, j));
                        break;
                    case 62:
                        if (!zza((Object) t, i4, i2)) {
                            break;
                        }
                        i += zzbn.zzh(i4, zzg(t, j));
                        break;
                    case 63:
                        if (!zza((Object) t, i4, i2)) {
                            break;
                        }
                        i += zzbn.zzl(i4, zzg(t, j));
                        break;
                    case 64:
                        if (!zza((Object) t, i4, i2)) {
                            break;
                        }
                        i += zzbn.zzk(i4, 0);
                        break;
                    case 65:
                        if (!zza((Object) t, i4, i2)) {
                            break;
                        }
                        i += zzbn.zzh(i4, 0);
                        break;
                    case 66:
                        if (!zza((Object) t, i4, i2)) {
                            break;
                        }
                        i += zzbn.zzi(i4, zzg(t, j));
                        break;
                    case 67:
                        if (!zza((Object) t, i4, i2)) {
                            break;
                        }
                        i += zzbn.zzf(i4, zzh(t, j));
                        break;
                    case 68:
                        if (!zza((Object) t, i4, i2)) {
                            break;
                        }
                        i += zzbn.zzc(i4, (zzdo) zzfd.zzo(t, j), zzad(i2));
                        break;
                    default:
                        break;
                }
            }
            return zza(this.zzmx, (Object) t) + i;
        }
        Unsafe unsafe2 = zzmh;
        i = -1;
        int i6 = 0;
        i2 = 0;
        zzag = 0;
        while (i2 < this.zzmi.length) {
            int zzag2 = zzag(i2);
            int i7 = this.zzmi[i2];
            int i8 = (267386880 & zzag2) >>> 20;
            i3 = 0;
            if (i8 <= 17) {
                i4 = this.zzmi[i2 + 2];
                i5 = 1048575 & i4;
                i3 = 1 << (i4 >>> 20);
                if (i5 != i) {
                    zzag = unsafe2.getInt(t, (long) i5);
                    i = i5;
                }
                i5 = i;
                i = zzag;
                zzag = i3;
                i3 = i4;
            } else if (!this.zzmr || i8 < zzcb.DOUBLE_LIST_PACKED.id() || i8 > zzcb.SINT64_LIST_PACKED.id()) {
                i5 = i;
                i = zzag;
                zzag = 0;
            } else {
                i3 = this.zzmi[i2 + 2] & 1048575;
                i5 = i;
                i = zzag;
                zzag = 0;
            }
            long j2 = (long) (1048575 & zzag2);
            switch (i8) {
                case 0:
                    if ((zzag & i) == 0) {
                        break;
                    }
                    i6 += zzbn.zzb(i7, 0.0d);
                    break;
                case 1:
                    if ((zzag & i) == 0) {
                        break;
                    }
                    i6 += zzbn.zzb(i7, (float) BitmapDescriptorFactory.HUE_RED);
                    break;
                case 2:
                    if ((zzag & i) == 0) {
                        break;
                    }
                    i6 += zzbn.zzd(i7, unsafe2.getLong(t, j2));
                    break;
                case 3:
                    if ((zzag & i) == 0) {
                        break;
                    }
                    i6 += zzbn.zze(i7, unsafe2.getLong(t, j2));
                    break;
                case 4:
                    if ((zzag & i) == 0) {
                        break;
                    }
                    i6 += zzbn.zzg(i7, unsafe2.getInt(t, j2));
                    break;
                case 5:
                    if ((zzag & i) == 0) {
                        break;
                    }
                    i6 += zzbn.zzg(i7, 0);
                    break;
                case 6:
                    if ((zzag & i) == 0) {
                        break;
                    }
                    i6 += zzbn.zzj(i7, 0);
                    break;
                case 7:
                    if ((zzag & i) == 0) {
                        break;
                    }
                    i6 += zzbn.zzc(i7, true);
                    break;
                case 8:
                    if ((zzag & i) == 0) {
                        break;
                    }
                    zzo = unsafe2.getObject(t, j2);
                    if (!(zzo instanceof zzbb)) {
                        i6 += zzbn.zzb(i7, (String) zzo);
                        break;
                    }
                    i6 += zzbn.zzc(i7, (zzbb) zzo);
                    break;
                case 9:
                    if ((zzag & i) == 0) {
                        break;
                    }
                    i6 += zzeh.zzc(i7, unsafe2.getObject(t, j2), zzad(i2));
                    break;
                case 10:
                    if ((zzag & i) == 0) {
                        break;
                    }
                    i6 += zzbn.zzc(i7, (zzbb) unsafe2.getObject(t, j2));
                    break;
                case 11:
                    if ((zzag & i) == 0) {
                        break;
                    }
                    i6 += zzbn.zzh(i7, unsafe2.getInt(t, j2));
                    break;
                case 12:
                    if ((zzag & i) == 0) {
                        break;
                    }
                    i6 += zzbn.zzl(i7, unsafe2.getInt(t, j2));
                    break;
                case 13:
                    if ((zzag & i) == 0) {
                        break;
                    }
                    i6 += zzbn.zzk(i7, 0);
                    break;
                case 14:
                    if ((zzag & i) == 0) {
                        break;
                    }
                    i6 += zzbn.zzh(i7, 0);
                    break;
                case 15:
                    if ((zzag & i) == 0) {
                        break;
                    }
                    i6 += zzbn.zzi(i7, unsafe2.getInt(t, j2));
                    break;
                case 16:
                    if ((zzag & i) == 0) {
                        break;
                    }
                    i6 += zzbn.zzf(i7, unsafe2.getLong(t, j2));
                    break;
                case 17:
                    if ((zzag & i) == 0) {
                        break;
                    }
                    i6 += zzbn.zzc(i7, (zzdo) unsafe2.getObject(t, j2), zzad(i2));
                    break;
                case 18:
                    i6 += zzeh.zzw(i7, (List) unsafe2.getObject(t, j2), false);
                    break;
                case 19:
                    i6 += zzeh.zzv(i7, (List) unsafe2.getObject(t, j2), false);
                    break;
                case 20:
                    i6 += zzeh.zzo(i7, (List) unsafe2.getObject(t, j2), false);
                    break;
                case 21:
                    i6 += zzeh.zzp(i7, (List) unsafe2.getObject(t, j2), false);
                    break;
                case 22:
                    i6 += zzeh.zzs(i7, (List) unsafe2.getObject(t, j2), false);
                    break;
                case 23:
                    i6 += zzeh.zzw(i7, (List) unsafe2.getObject(t, j2), false);
                    break;
                case 24:
                    i6 += zzeh.zzv(i7, (List) unsafe2.getObject(t, j2), false);
                    break;
                case 25:
                    i6 += zzeh.zzx(i7, (List) unsafe2.getObject(t, j2), false);
                    break;
                case 26:
                    i6 += zzeh.zzc(i7, (List) unsafe2.getObject(t, j2));
                    break;
                case 27:
                    i6 += zzeh.zzc(i7, (List) unsafe2.getObject(t, j2), zzad(i2));
                    break;
                case 28:
                    i6 += zzeh.zzd(i7, (List) unsafe2.getObject(t, j2));
                    break;
                case 29:
                    i6 += zzeh.zzt(i7, (List) unsafe2.getObject(t, j2), false);
                    break;
                case 30:
                    i6 += zzeh.zzr(i7, (List) unsafe2.getObject(t, j2), false);
                    break;
                case 31:
                    i6 += zzeh.zzv(i7, (List) unsafe2.getObject(t, j2), false);
                    break;
                case 32:
                    i6 += zzeh.zzw(i7, (List) unsafe2.getObject(t, j2), false);
                    break;
                case 33:
                    i6 += zzeh.zzu(i7, (List) unsafe2.getObject(t, j2), false);
                    break;
                case 34:
                    i6 += zzeh.zzq(i7, (List) unsafe2.getObject(t, j2), false);
                    break;
                case 35:
                    zzag = zzeh.zzi((List) unsafe2.getObject(t, j2));
                    if (zzag > 0) {
                        if (this.zzmr) {
                            unsafe2.putInt(t, (long) i3, zzag);
                        }
                        i6 += zzag + (zzbn.zzr(i7) + zzbn.zzt(zzag));
                        break;
                    }
                    break;
                case 36:
                    zzag = zzeh.zzh((List) unsafe2.getObject(t, j2));
                    if (zzag > 0) {
                        if (this.zzmr) {
                            unsafe2.putInt(t, (long) i3, zzag);
                        }
                        i6 += zzag + (zzbn.zzr(i7) + zzbn.zzt(zzag));
                        break;
                    }
                    break;
                case 37:
                    zzag = zzeh.zza((List) unsafe2.getObject(t, j2));
                    if (zzag > 0) {
                        if (this.zzmr) {
                            unsafe2.putInt(t, (long) i3, zzag);
                        }
                        i6 += zzag + (zzbn.zzr(i7) + zzbn.zzt(zzag));
                        break;
                    }
                    break;
                case 38:
                    zzag = zzeh.zzb((List) unsafe2.getObject(t, j2));
                    if (zzag > 0) {
                        if (this.zzmr) {
                            unsafe2.putInt(t, (long) i3, zzag);
                        }
                        i6 += zzag + (zzbn.zzr(i7) + zzbn.zzt(zzag));
                        break;
                    }
                    break;
                case 39:
                    zzag = zzeh.zze((List) unsafe2.getObject(t, j2));
                    if (zzag > 0) {
                        if (this.zzmr) {
                            unsafe2.putInt(t, (long) i3, zzag);
                        }
                        i6 += zzag + (zzbn.zzr(i7) + zzbn.zzt(zzag));
                        break;
                    }
                    break;
                case 40:
                    zzag = zzeh.zzi((List) unsafe2.getObject(t, j2));
                    if (zzag > 0) {
                        if (this.zzmr) {
                            unsafe2.putInt(t, (long) i3, zzag);
                        }
                        i6 += zzag + (zzbn.zzr(i7) + zzbn.zzt(zzag));
                        break;
                    }
                    break;
                case 41:
                    zzag = zzeh.zzh((List) unsafe2.getObject(t, j2));
                    if (zzag > 0) {
                        if (this.zzmr) {
                            unsafe2.putInt(t, (long) i3, zzag);
                        }
                        i6 += zzag + (zzbn.zzr(i7) + zzbn.zzt(zzag));
                        break;
                    }
                    break;
                case 42:
                    zzag = zzeh.zzj((List) unsafe2.getObject(t, j2));
                    if (zzag > 0) {
                        if (this.zzmr) {
                            unsafe2.putInt(t, (long) i3, zzag);
                        }
                        i6 += zzag + (zzbn.zzr(i7) + zzbn.zzt(zzag));
                        break;
                    }
                    break;
                case 43:
                    zzag = zzeh.zzf((List) unsafe2.getObject(t, j2));
                    if (zzag > 0) {
                        if (this.zzmr) {
                            unsafe2.putInt(t, (long) i3, zzag);
                        }
                        i6 += zzag + (zzbn.zzr(i7) + zzbn.zzt(zzag));
                        break;
                    }
                    break;
                case 44:
                    zzag = zzeh.zzd((List) unsafe2.getObject(t, j2));
                    if (zzag > 0) {
                        if (this.zzmr) {
                            unsafe2.putInt(t, (long) i3, zzag);
                        }
                        i6 += zzag + (zzbn.zzr(i7) + zzbn.zzt(zzag));
                        break;
                    }
                    break;
                case 45:
                    zzag = zzeh.zzh((List) unsafe2.getObject(t, j2));
                    if (zzag > 0) {
                        if (this.zzmr) {
                            unsafe2.putInt(t, (long) i3, zzag);
                        }
                        i6 += zzag + (zzbn.zzr(i7) + zzbn.zzt(zzag));
                        break;
                    }
                    break;
                case 46:
                    zzag = zzeh.zzi((List) unsafe2.getObject(t, j2));
                    if (zzag > 0) {
                        if (this.zzmr) {
                            unsafe2.putInt(t, (long) i3, zzag);
                        }
                        i6 += zzag + (zzbn.zzr(i7) + zzbn.zzt(zzag));
                        break;
                    }
                    break;
                case 47:
                    zzag = zzeh.zzg((List) unsafe2.getObject(t, j2));
                    if (zzag > 0) {
                        if (this.zzmr) {
                            unsafe2.putInt(t, (long) i3, zzag);
                        }
                        i6 += zzag + (zzbn.zzr(i7) + zzbn.zzt(zzag));
                        break;
                    }
                    break;
                case 48:
                    zzag = zzeh.zzc((List) unsafe2.getObject(t, j2));
                    if (zzag > 0) {
                        if (this.zzmr) {
                            unsafe2.putInt(t, (long) i3, zzag);
                        }
                        i6 += zzag + (zzbn.zzr(i7) + zzbn.zzt(zzag));
                        break;
                    }
                    break;
                case 49:
                    i6 += zzeh.zzd(i7, (List) unsafe2.getObject(t, j2), zzad(i2));
                    break;
                case 50:
                    i6 += this.zzmz.zzb(i7, unsafe2.getObject(t, j2), zzae(i2));
                    break;
                case 51:
                    if (!zza((Object) t, i7, i2)) {
                        break;
                    }
                    i6 += zzbn.zzb(i7, 0.0d);
                    break;
                case 52:
                    if (!zza((Object) t, i7, i2)) {
                        break;
                    }
                    i6 += zzbn.zzb(i7, (float) BitmapDescriptorFactory.HUE_RED);
                    break;
                case 53:
                    if (!zza((Object) t, i7, i2)) {
                        break;
                    }
                    i6 += zzbn.zzd(i7, zzh(t, j2));
                    break;
                case 54:
                    if (!zza((Object) t, i7, i2)) {
                        break;
                    }
                    i6 += zzbn.zze(i7, zzh(t, j2));
                    break;
                case 55:
                    if (!zza((Object) t, i7, i2)) {
                        break;
                    }
                    i6 += zzbn.zzg(i7, zzg(t, j2));
                    break;
                case 56:
                    if (!zza((Object) t, i7, i2)) {
                        break;
                    }
                    i6 += zzbn.zzg(i7, 0);
                    break;
                case 57:
                    if (!zza((Object) t, i7, i2)) {
                        break;
                    }
                    i6 += zzbn.zzj(i7, 0);
                    break;
                case 58:
                    if (!zza((Object) t, i7, i2)) {
                        break;
                    }
                    i6 += zzbn.zzc(i7, true);
                    break;
                case 59:
                    if (!zza((Object) t, i7, i2)) {
                        break;
                    }
                    zzo = unsafe2.getObject(t, j2);
                    if (!(zzo instanceof zzbb)) {
                        i6 += zzbn.zzb(i7, (String) zzo);
                        break;
                    }
                    i6 += zzbn.zzc(i7, (zzbb) zzo);
                    break;
                case 60:
                    if (!zza((Object) t, i7, i2)) {
                        break;
                    }
                    i6 += zzeh.zzc(i7, unsafe2.getObject(t, j2), zzad(i2));
                    break;
                case 61:
                    if (!zza((Object) t, i7, i2)) {
                        break;
                    }
                    i6 += zzbn.zzc(i7, (zzbb) unsafe2.getObject(t, j2));
                    break;
                case 62:
                    if (!zza((Object) t, i7, i2)) {
                        break;
                    }
                    i6 += zzbn.zzh(i7, zzg(t, j2));
                    break;
                case 63:
                    if (!zza((Object) t, i7, i2)) {
                        break;
                    }
                    i6 += zzbn.zzl(i7, zzg(t, j2));
                    break;
                case 64:
                    if (!zza((Object) t, i7, i2)) {
                        break;
                    }
                    i6 += zzbn.zzk(i7, 0);
                    break;
                case 65:
                    if (!zza((Object) t, i7, i2)) {
                        break;
                    }
                    i6 += zzbn.zzh(i7, 0);
                    break;
                case 66:
                    if (!zza((Object) t, i7, i2)) {
                        break;
                    }
                    i6 += zzbn.zzi(i7, zzg(t, j2));
                    break;
                case 67:
                    if (!zza((Object) t, i7, i2)) {
                        break;
                    }
                    i6 += zzbn.zzf(i7, zzh(t, j2));
                    break;
                case 68:
                    if (!zza((Object) t, i7, i2)) {
                        break;
                    }
                    i6 += zzbn.zzc(i7, (zzdo) unsafe2.getObject(t, j2), zzad(i2));
                    break;
                default:
                    break;
            }
            i2 += 4;
            zzag = i;
            i = i5;
        }
        zzag = zza(this.zzmx, (Object) t) + i6;
        return this.zzmo ? zzag + this.zzmy.zza((Object) t).zzas() : zzag;
    }

    public final boolean zzo(T t) {
        if (this.zzms == null || this.zzms.length == 0) {
            return true;
        }
        int i = -1;
        int i2 = 0;
        int[] iArr = this.zzms;
        int length = iArr.length;
        int i3 = 0;
        while (i3 < length) {
            int i4;
            List list;
            zzef zzad;
            Object obj;
            Map zzh;
            zzef zzef;
            int i5 = iArr[i3];
            int zzai = zzai(i5);
            int zzag = zzag(zzai);
            int i6 = 0;
            if (!this.zzmq) {
                i6 = this.zzmi[zzai + 2];
                i4 = 1048575 & i6;
                i6 = 1 << (i6 >>> 20);
                if (i4 != i) {
                    i = i4;
                    i4 = zzmh.getInt(t, (long) i4);
                    i2 = i6;
                    if (((ErrorDialogData.BINDER_CRASH & zzag) == 0 ? 1 : null) == null && !zza((Object) t, zzai, i4, i2)) {
                        return false;
                    }
                    switch ((267386880 & zzag) >>> 20) {
                        case 9:
                        case 17:
                            if (zza((Object) t, zzai, i4, i2) && !zza((Object) t, zzag, zzad(zzai))) {
                                return false;
                            }
                        case 27:
                        case 49:
                            list = (List) zzfd.zzo(t, (long) (1048575 & zzag));
                            if (!list.isEmpty()) {
                                zzad = zzad(zzai);
                                for (i2 = 0; i2 < list.size(); i2++) {
                                    if (!zzad.zzo(list.get(i2))) {
                                        obj = null;
                                        if (obj != null) {
                                            break;
                                        }
                                        return false;
                                    }
                                }
                            }
                            obj = 1;
                            if (obj != null) {
                                return false;
                            }
                        case 50:
                            zzh = this.zzmz.zzh(zzfd.zzo(t, (long) (1048575 & zzag)));
                            if (!zzh.isEmpty()) {
                                if (this.zzmz.zzl(zzae(zzai)).zzmd.zzek() == zzfq.MESSAGE) {
                                    zzef = null;
                                    for (Object next : zzh.values()) {
                                        if (zzef == null) {
                                            zzef = zzea.zzcm().zze(next.getClass());
                                        }
                                        if (!zzef.zzo(next)) {
                                            obj = null;
                                            if (obj != null) {
                                                break;
                                            }
                                            return false;
                                        }
                                    }
                                }
                            }
                            obj = 1;
                            if (obj != null) {
                                return false;
                            }
                        case 60:
                        case 68:
                            if (zza((Object) t, i5, zzai) && !zza((Object) t, zzag, zzad(zzai))) {
                                return false;
                            }
                        default:
                            break;
                    }
                    i3++;
                    i2 = i4;
                }
            }
            i4 = i2;
            i2 = i6;
            if ((ErrorDialogData.BINDER_CRASH & zzag) == 0) {
            }
            if (((ErrorDialogData.BINDER_CRASH & zzag) == 0 ? 1 : null) == null) {
            }
            switch ((267386880 & zzag) >>> 20) {
                case 9:
                case 17:
                    return false;
                case 27:
                case 49:
                    list = (List) zzfd.zzo(t, (long) (1048575 & zzag));
                    if (list.isEmpty()) {
                        zzad = zzad(zzai);
                        for (i2 = 0; i2 < list.size(); i2++) {
                            if (!zzad.zzo(list.get(i2))) {
                                obj = null;
                                if (obj != null) {
                                    break;
                                }
                                return false;
                            }
                        }
                    }
                    obj = 1;
                    if (obj != null) {
                        return false;
                    }
                case 50:
                    zzh = this.zzmz.zzh(zzfd.zzo(t, (long) (1048575 & zzag)));
                    if (zzh.isEmpty()) {
                        if (this.zzmz.zzl(zzae(zzai)).zzmd.zzek() == zzfq.MESSAGE) {
                            zzef = null;
                            for (Object next2 : zzh.values()) {
                                if (zzef == null) {
                                    zzef = zzea.zzcm().zze(next2.getClass());
                                }
                                if (zzef.zzo(next2)) {
                                    obj = null;
                                    if (obj != null) {
                                        break;
                                    }
                                    return false;
                                }
                            }
                        }
                    }
                    obj = 1;
                    if (obj != null) {
                        return false;
                    }
                case 60:
                case 68:
                    return false;
                default:
                    break;
            }
            i3++;
            i2 = i4;
        }
        return !this.zzmo || this.zzmy.zza((Object) t).isInitialized();
    }
}
