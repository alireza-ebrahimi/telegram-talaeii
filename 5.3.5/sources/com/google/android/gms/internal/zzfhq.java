package com.google.android.gms.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

final class zzfhq<FieldDescriptorType extends zzfhs<FieldDescriptorType>> {
    private static final zzfhq zzppc = new zzfhq(true);
    private boolean zzldh;
    private final zzfjy<FieldDescriptorType, Object> zzppa = zzfjy.zzmq(16);
    private boolean zzppb = false;

    private zzfhq() {
    }

    private zzfhq(boolean z) {
        if (!this.zzldh) {
            this.zzppa.zzbkr();
            this.zzldh = true;
        }
    }

    static int zza(zzfky zzfky, int i, Object obj) {
        int i2;
        int zzlw = zzfhg.zzlw(i);
        if (zzfky == zzfky.GROUP) {
            zzfhz.zzh((zzfjc) obj);
            i2 = zzlw << 1;
        } else {
            i2 = zzlw;
        }
        return i2 + zzb(zzfky, obj);
    }

    public static Object zza(zzfhb zzfhb, zzfky zzfky, boolean z) throws IOException {
        zzfle zzfle = zzfle.STRICT;
        switch (zzfkx.zzppe[zzfky.ordinal()]) {
            case 1:
                return Double.valueOf(zzfhb.readDouble());
            case 2:
                return Float.valueOf(zzfhb.readFloat());
            case 3:
                return Long.valueOf(zzfhb.zzcxz());
            case 4:
                return Long.valueOf(zzfhb.zzcxy());
            case 5:
                return Integer.valueOf(zzfhb.zzcya());
            case 6:
                return Long.valueOf(zzfhb.zzcyb());
            case 7:
                return Integer.valueOf(zzfhb.zzcyc());
            case 8:
                return Boolean.valueOf(zzfhb.zzcyd());
            case 9:
                return zzfhb.zzcyf();
            case 10:
                return Integer.valueOf(zzfhb.zzcyg());
            case 11:
                return Integer.valueOf(zzfhb.zzcyi());
            case 12:
                return Long.valueOf(zzfhb.zzcyj());
            case 13:
                return Integer.valueOf(zzfhb.zzcyk());
            case 14:
                return Long.valueOf(zzfhb.zzcyl());
            case 15:
                return zzfle.zza(zzfhb);
            case 16:
                throw new IllegalArgumentException("readPrimitiveField() cannot handle nested groups.");
            case 17:
                throw new IllegalArgumentException("readPrimitiveField() cannot handle embedded messages.");
            case 18:
                throw new IllegalArgumentException("readPrimitiveField() cannot handle enums.");
            default:
                throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
        }
    }

    static void zza(zzfhg zzfhg, zzfky zzfky, int i, Object obj) throws IOException {
        if (zzfky == zzfky.GROUP) {
            zzfhz.zzh((zzfjc) obj);
            zzfhg.zze(i, (zzfjc) obj);
            return;
        }
        zzfhg.zzac(i, zzfky.zzdcj());
        switch (zzfhr.zzppe[zzfky.ordinal()]) {
            case 1:
                zzfhg.zzn(((Double) obj).doubleValue());
                return;
            case 2:
                zzfhg.zzf(((Float) obj).floatValue());
                return;
            case 3:
                zzfhg.zzcu(((Long) obj).longValue());
                return;
            case 4:
                zzfhg.zzcu(((Long) obj).longValue());
                return;
            case 5:
                zzfhg.zzls(((Integer) obj).intValue());
                return;
            case 6:
                zzfhg.zzcw(((Long) obj).longValue());
                return;
            case 7:
                zzfhg.zzlv(((Integer) obj).intValue());
                return;
            case 8:
                zzfhg.zzdl(((Boolean) obj).booleanValue());
                return;
            case 9:
                ((zzfjc) obj).zza(zzfhg);
                return;
            case 10:
                zzfhg.zze((zzfjc) obj);
                return;
            case 11:
                if (obj instanceof zzfgs) {
                    zzfhg.zzay((zzfgs) obj);
                    return;
                } else {
                    zzfhg.zztw((String) obj);
                    return;
                }
            case 12:
                if (obj instanceof zzfgs) {
                    zzfhg.zzay((zzfgs) obj);
                    return;
                }
                byte[] bArr = (byte[]) obj;
                zzfhg.zzj(bArr, 0, bArr.length);
                return;
            case 13:
                zzfhg.zzlt(((Integer) obj).intValue());
                return;
            case 14:
                zzfhg.zzlv(((Integer) obj).intValue());
                return;
            case 15:
                zzfhg.zzcw(((Long) obj).longValue());
                return;
            case 16:
                zzfhg.zzlu(((Integer) obj).intValue());
                return;
            case 17:
                zzfhg.zzcv(((Long) obj).longValue());
                return;
            case 18:
                if (obj instanceof zzfia) {
                    zzfhg.zzls(((zzfia) obj).zzhu());
                    return;
                } else {
                    zzfhg.zzls(((Integer) obj).intValue());
                    return;
                }
            default:
                return;
        }
    }

    private void zza(FieldDescriptorType fieldDescriptorType, Object obj) {
        Object obj2;
        if (!fieldDescriptorType.zzczn()) {
            zza(fieldDescriptorType.zzczl(), obj);
            obj2 = obj;
        } else if (obj instanceof List) {
            obj2 = new ArrayList();
            obj2.addAll((List) obj);
            ArrayList arrayList = (ArrayList) obj2;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj3 = arrayList.get(i);
                i++;
                zza(fieldDescriptorType.zzczl(), obj3);
            }
        } else {
            throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
        }
        if (obj2 instanceof zzfig) {
            this.zzppb = true;
        }
        this.zzppa.zza((Comparable) fieldDescriptorType, obj2);
    }

    private static void zza(zzfky zzfky, Object obj) {
        boolean z = false;
        zzfhz.checkNotNull(obj);
        switch (zzfhr.zzppd[zzfky.zzdci().ordinal()]) {
            case 1:
                z = obj instanceof Integer;
                break;
            case 2:
                z = obj instanceof Long;
                break;
            case 3:
                z = obj instanceof Float;
                break;
            case 4:
                z = obj instanceof Double;
                break;
            case 5:
                z = obj instanceof Boolean;
                break;
            case 6:
                z = obj instanceof String;
                break;
            case 7:
                if ((obj instanceof zzfgs) || (obj instanceof byte[])) {
                    z = true;
                    break;
                }
            case 8:
                if ((obj instanceof Integer) || (obj instanceof zzfia)) {
                    z = true;
                    break;
                }
            case 9:
                if ((obj instanceof zzfjc) || (obj instanceof zzfig)) {
                    z = true;
                    break;
                }
        }
        if (!z) {
            throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
        }
    }

    private static int zzb(zzfhs<?> zzfhs, Object obj) {
        int i = 0;
        zzfky zzczl = zzfhs.zzczl();
        int zzhu = zzfhs.zzhu();
        if (!zzfhs.zzczn()) {
            return zza(zzczl, zzhu, obj);
        }
        if (zzfhs.zzczo()) {
            for (Object zzb : (List) obj) {
                i += zzb(zzczl, zzb);
            }
            return zzfhg.zzmf(i) + (zzfhg.zzlw(zzhu) + i);
        }
        for (Object zzb2 : (List) obj) {
            i += zza(zzczl, zzhu, zzb2);
        }
        return i;
    }

    private static int zzb(zzfky zzfky, Object obj) {
        switch (zzfhr.zzppe[zzfky.ordinal()]) {
            case 1:
                return zzfhg.zzo(((Double) obj).doubleValue());
            case 2:
                return zzfhg.zzg(((Float) obj).floatValue());
            case 3:
                return zzfhg.zzcx(((Long) obj).longValue());
            case 4:
                return zzfhg.zzcy(((Long) obj).longValue());
            case 5:
                return zzfhg.zzlx(((Integer) obj).intValue());
            case 6:
                return zzfhg.zzda(((Long) obj).longValue());
            case 7:
                return zzfhg.zzma(((Integer) obj).intValue());
            case 8:
                return zzfhg.zzdm(((Boolean) obj).booleanValue());
            case 9:
                return zzfhg.zzg((zzfjc) obj);
            case 10:
                return obj instanceof zzfig ? zzfhg.zza((zzfig) obj) : zzfhg.zzf((zzfjc) obj);
            case 11:
                return obj instanceof zzfgs ? zzfhg.zzaz((zzfgs) obj) : zzfhg.zztx((String) obj);
            case 12:
                return obj instanceof zzfgs ? zzfhg.zzaz((zzfgs) obj) : zzfhg.zzbd((byte[]) obj);
            case 13:
                return zzfhg.zzly(((Integer) obj).intValue());
            case 14:
                return zzfhg.zzmb(((Integer) obj).intValue());
            case 15:
                return zzfhg.zzdb(((Long) obj).longValue());
            case 16:
                return zzfhg.zzlz(((Integer) obj).intValue());
            case 17:
                return zzfhg.zzcz(((Long) obj).longValue());
            case 18:
                return obj instanceof zzfia ? zzfhg.zzmc(((zzfia) obj).zzhu()) : zzfhg.zzmc(((Integer) obj).intValue());
            default:
                throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
        }
    }

    private static int zzb(Entry<FieldDescriptorType, Object> entry) {
        zzfhs zzfhs = (zzfhs) entry.getKey();
        Object value = entry.getValue();
        return (zzfhs.zzczm() != zzfld.MESSAGE || zzfhs.zzczn() || zzfhs.zzczo()) ? zzb(zzfhs, value) : value instanceof zzfig ? zzfhg.zzb(((zzfhs) entry.getKey()).zzhu(), (zzfig) value) : zzfhg.zzd(((zzfhs) entry.getKey()).zzhu(), (zzfjc) value);
    }

    public static <T extends zzfhs<T>> zzfhq<T> zzczj() {
        return zzppc;
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        zzfhq zzfhq = new zzfhq();
        for (int i = 0; i < this.zzppa.zzdbp(); i++) {
            Entry zzmr = this.zzppa.zzmr(i);
            zzfhq.zza((zzfhs) zzmr.getKey(), zzmr.getValue());
        }
        for (Entry entry : this.zzppa.zzdbq()) {
            zzfhq.zza((zzfhs) entry.getKey(), entry.getValue());
        }
        zzfhq.zzppb = this.zzppb;
        return zzfhq;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzfhq)) {
            return false;
        }
        return this.zzppa.equals(((zzfhq) obj).zzppa);
    }

    public final int hashCode() {
        return this.zzppa.hashCode();
    }

    public final Iterator<Entry<FieldDescriptorType, Object>> iterator() {
        return this.zzppb ? new zzfij(this.zzppa.entrySet().iterator()) : this.zzppa.entrySet().iterator();
    }

    public final int zzczk() {
        int i = 0;
        int i2 = 0;
        while (i < this.zzppa.zzdbp()) {
            i2 += zzb(this.zzppa.zzmr(i));
            i++;
        }
        for (Entry zzb : this.zzppa.zzdbq()) {
            i2 += zzb(zzb);
        }
        return i2;
    }
}
