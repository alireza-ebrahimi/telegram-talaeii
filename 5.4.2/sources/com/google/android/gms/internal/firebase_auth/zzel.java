package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.common.util.CrashUtils.ErrorDialogData;
import com.google.android.gms.internal.firebase_auth.zzdb.zze;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import sun.misc.Unsafe;

final class zzel<T> implements zzev<T> {
    private static final int[] zzti = new int[0];
    private static final Unsafe zztj = zzfv.zzge();
    private final int[] zztk;
    private final Object[] zztl;
    private final int zztm;
    private final int zztn;
    private final zzeh zzto;
    private final boolean zztp;
    private final boolean zztq;
    private final boolean zztr;
    private final boolean zzts;
    private final int[] zztt;
    private final int zztu;
    private final int zztv;
    private final zzeo zztw;
    private final zzdr zztx;
    private final zzfp<?, ?> zzty;
    private final zzcp<?> zztz;
    private final zzec zzua;

    private zzel(int[] iArr, Object[] objArr, int i, int i2, zzeh zzeh, boolean z, boolean z2, int[] iArr2, int i3, int i4, zzeo zzeo, zzdr zzdr, zzfp<?, ?> zzfp, zzcp<?> zzcp, zzec zzec) {
        this.zztk = iArr;
        this.zztl = objArr;
        this.zztm = i;
        this.zztn = i2;
        this.zztq = zzeh instanceof zzdb;
        this.zztr = z;
        boolean z3 = zzcp != null && zzcp.zze(zzeh);
        this.zztp = z3;
        this.zzts = false;
        this.zztt = iArr2;
        this.zztu = i3;
        this.zztv = i4;
        this.zztw = zzeo;
        this.zztx = zzdr;
        this.zzty = zzfp;
        this.zztz = zzcp;
        this.zzto = zzeh;
        this.zzua = zzec;
    }

    private static <UT, UB> int zza(zzfp<UT, UB> zzfp, T t) {
        return zzfp.zzo(zzfp.zzr(t));
    }

    static <T> zzel<T> zza(Class<T> cls, zzef zzef, zzeo zzeo, zzdr zzdr, zzfp<?, ?> zzfp, zzcp<?> zzcp, zzec zzec) {
        if (zzef instanceof zzet) {
            int i;
            int i2;
            int i3;
            char charAt;
            int i4;
            int i5;
            int i6;
            int[] iArr;
            int i7;
            int i8;
            int i9;
            int i10;
            char charAt2;
            zzet zzet = (zzet) zzef;
            boolean z = zzet.zzez() == zze.zzrn;
            String zzfh = zzet.zzfh();
            int length = zzfh.length();
            int i11 = 1;
            char charAt3 = zzfh.charAt(0);
            if (charAt3 >= '?') {
                i = charAt3 & 8191;
                i2 = 13;
                while (true) {
                    i3 = i11 + 1;
                    charAt = zzfh.charAt(i11);
                    if (charAt < '?') {
                        break;
                    }
                    i |= (charAt & 8191) << i2;
                    i2 += 13;
                    i11 = i3;
                }
                i4 = (charAt << i2) | i;
            } else {
                char c = charAt3;
                i3 = 1;
            }
            i11 = i3 + 1;
            i2 = zzfh.charAt(i3);
            if (i2 >= 55296) {
                i = i2 & 8191;
                i2 = 13;
                while (true) {
                    i3 = i11 + 1;
                    charAt = zzfh.charAt(i11);
                    if (charAt < '?') {
                        break;
                    }
                    i |= (charAt & 8191) << i2;
                    i2 += 13;
                    i11 = i3;
                }
                i2 = (charAt << i2) | i;
                i5 = i3;
            } else {
                i5 = i11;
            }
            if (i2 == 0) {
                i6 = 0;
                iArr = zzti;
                i2 = 0;
                i7 = 0;
                i8 = 0;
                i3 = 0;
                i11 = 0;
                i9 = 0;
            } else {
                char charAt4;
                char charAt5;
                i11 = i5 + 1;
                i2 = zzfh.charAt(i5);
                if (i2 >= 55296) {
                    i = i2 & 8191;
                    i2 = 13;
                    while (true) {
                        i3 = i11 + 1;
                        charAt = zzfh.charAt(i11);
                        if (charAt < '?') {
                            break;
                        }
                        i |= (charAt & 8191) << i2;
                        i2 += 13;
                        i11 = i3;
                    }
                    i2 = (charAt << i2) | i;
                } else {
                    i3 = i11;
                }
                i9 = i3 + 1;
                i = zzfh.charAt(i3);
                if (i >= 55296) {
                    i11 = i & 8191;
                    i = 13;
                    i3 = i9;
                    while (true) {
                        i9 = i3 + 1;
                        charAt4 = zzfh.charAt(i3);
                        if (charAt4 < '?') {
                            break;
                        }
                        i11 |= (charAt4 & 8191) << i;
                        i += 13;
                        i3 = i9;
                    }
                    i = (charAt4 << i) | i11;
                }
                i8 = i9 + 1;
                charAt = zzfh.charAt(i9);
                if (charAt >= '?') {
                    i3 = charAt & 8191;
                    i11 = 13;
                    i9 = i8;
                    while (true) {
                        i8 = i9 + 1;
                        charAt5 = zzfh.charAt(i9);
                        if (charAt5 < '?') {
                            break;
                        }
                        i3 |= (charAt5 & 8191) << i11;
                        i11 += 13;
                        i9 = i8;
                    }
                    charAt = (charAt5 << i11) | i3;
                }
                int i12 = i8 + 1;
                charAt4 = zzfh.charAt(i8);
                if (charAt4 >= '?') {
                    char charAt6;
                    i9 = charAt4 & 8191;
                    i3 = 13;
                    i8 = i12;
                    while (true) {
                        i12 = i8 + 1;
                        charAt6 = zzfh.charAt(i8);
                        if (charAt6 < '?') {
                            break;
                        }
                        i9 |= (charAt6 & 8191) << i3;
                        i3 += 13;
                        i8 = i12;
                    }
                    charAt4 = (charAt6 << i3) | i9;
                }
                i6 = i12 + 1;
                charAt5 = zzfh.charAt(i12);
                if (charAt5 >= '?') {
                    char charAt7;
                    i8 = charAt5 & 8191;
                    i9 = 13;
                    i12 = i6;
                    while (true) {
                        i6 = i12 + 1;
                        charAt7 = zzfh.charAt(i12);
                        if (charAt7 < '?') {
                            break;
                        }
                        i8 |= (charAt7 & 8191) << i9;
                        i9 += 13;
                        i12 = i6;
                    }
                    charAt5 = (charAt7 << i9) | i8;
                }
                i7 = i6 + 1;
                i8 = zzfh.charAt(i6);
                if (i8 >= 55296) {
                    char charAt8;
                    i12 = i8 & 8191;
                    i8 = 13;
                    i6 = i7;
                    while (true) {
                        i7 = i6 + 1;
                        charAt8 = zzfh.charAt(i6);
                        if (charAt8 < '?') {
                            break;
                        }
                        i12 |= (charAt8 & 8191) << i8;
                        i8 += 13;
                        i6 = i7;
                    }
                    i8 = (charAt8 << i8) | i12;
                }
                i10 = i7 + 1;
                i12 = zzfh.charAt(i7);
                if (i12 >= 55296) {
                    char charAt9;
                    i6 = i12 & 8191;
                    i12 = 13;
                    i7 = i10;
                    while (true) {
                        i10 = i7 + 1;
                        charAt9 = zzfh.charAt(i7);
                        if (charAt9 < '?') {
                            break;
                        }
                        i6 |= (charAt9 & 8191) << i12;
                        i12 += 13;
                        i7 = i10;
                    }
                    i12 = (charAt9 << i12) | i6;
                }
                i5 = i10 + 1;
                i6 = zzfh.charAt(i10);
                if (i6 >= 55296) {
                    i7 = i6 & 8191;
                    i6 = 13;
                    i10 = i5;
                    while (true) {
                        i5 = i10 + 1;
                        charAt2 = zzfh.charAt(i10);
                        if (charAt2 < '?') {
                            break;
                        }
                        i7 |= (charAt2 & 8191) << i6;
                        i6 += 13;
                        i10 = i5;
                    }
                    i6 = (charAt2 << i6) | i7;
                }
                iArr = new int[(i12 + (i6 + i8))];
                i7 = i8;
                i8 = i2;
                i2 = i + (i2 << 1);
                char c2 = charAt4;
                charAt4 = charAt;
                charAt = charAt5;
                charAt5 = c2;
            }
            Unsafe unsafe = zztj;
            Object[] zzfi = zzet.zzfi();
            int i13 = 0;
            Class cls2 = zzet.zzfb().getClass();
            int[] iArr2 = new int[(i11 * 3)];
            Object[] objArr = new Object[(i11 << 1)];
            int i14 = i6 + i7;
            int i15 = 0;
            int i16 = i6;
            int i17 = i2;
            int i18 = i5;
            while (i18 < length) {
                int i19;
                int i20;
                int i21;
                i5 = i18 + 1;
                charAt3 = zzfh.charAt(i18);
                if (charAt3 >= '?') {
                    char charAt10;
                    i10 = charAt3 & 8191;
                    i2 = 13;
                    while (true) {
                        i19 = i5 + 1;
                        charAt10 = zzfh.charAt(i5);
                        if (charAt10 < '?') {
                            break;
                        }
                        i10 |= (charAt10 & 8191) << i2;
                        i2 += 13;
                        i5 = i19;
                    }
                    i10 = (charAt10 << i2) | i10;
                } else {
                    charAt2 = charAt3;
                    i19 = i5;
                }
                int i22 = i19 + 1;
                charAt3 = zzfh.charAt(i19);
                if (charAt3 >= '?') {
                    char charAt11;
                    i5 = charAt3 & 8191;
                    i2 = 13;
                    i19 = i22;
                    while (true) {
                        i22 = i19 + 1;
                        charAt11 = zzfh.charAt(i19);
                        if (charAt11 < '?') {
                            break;
                        }
                        i5 |= (charAt11 & 8191) << i2;
                        i2 += 13;
                        i19 = i22;
                    }
                    i20 = (charAt11 << i2) | i5;
                    i18 = i22;
                } else {
                    char c3 = charAt3;
                    i18 = i22;
                }
                int i23 = i20 & 255;
                if ((i20 & 1024) != 0) {
                    i2 = i13 + 1;
                    iArr[i13] = i15;
                    i5 = i2;
                } else {
                    i5 = i13;
                }
                Object obj;
                Field field;
                if (i23 > zzcv.MAP.id()) {
                    i22 = i18 + 1;
                    i2 = zzfh.charAt(i18);
                    if (i2 >= 55296) {
                        char charAt12;
                        i19 = i2 & 8191;
                        i2 = 13;
                        while (true) {
                            i13 = i22 + 1;
                            charAt12 = zzfh.charAt(i22);
                            if (charAt12 < '?') {
                                break;
                            }
                            i19 |= (charAt12 & 8191) << i2;
                            i2 += 13;
                            i22 = i13;
                        }
                        i2 = (charAt12 << i2) | i19;
                    } else {
                        i13 = i22;
                    }
                    if (i23 == zzcv.MESSAGE.id() + 51 || i23 == zzcv.GROUP.id() + 51) {
                        i19 = i17 + 1;
                        objArr[((i15 / 3) << 1) + 1] = zzfi[i17];
                    } else if (i23 == zzcv.ENUM.id() + 51 && (i4 & 1) == 1) {
                        i19 = i17 + 1;
                        objArr[((i15 / 3) << 1) + 1] = zzfi[i17];
                    } else {
                        i19 = i17;
                    }
                    i22 = i2 << 1;
                    obj = zzfi[i22];
                    if (obj instanceof Field) {
                        field = (Field) obj;
                    } else {
                        field = zza(cls2, (String) obj);
                        zzfi[i22] = field;
                    }
                    i17 = (int) unsafe.objectFieldOffset(field);
                    i22++;
                    obj = zzfi[i22];
                    if (obj instanceof Field) {
                        field = (Field) obj;
                    } else {
                        field = zza(cls2, (String) obj);
                        zzfi[i22] = field;
                    }
                    i21 = i19;
                    i18 = i13;
                    i13 = 0;
                    i22 = (int) unsafe.objectFieldOffset(field);
                } else {
                    i19 = i17 + 1;
                    Field zza = zza(cls2, (String) zzfi[i17]);
                    if (i23 == zzcv.MESSAGE.id() || i23 == zzcv.GROUP.id()) {
                        objArr[((i15 / 3) << 1) + 1] = zza.getType();
                        i22 = i19;
                        i19 = i16;
                    } else if (i23 == zzcv.MESSAGE_LIST.id() || i23 == zzcv.GROUP_LIST.id()) {
                        i2 = i19 + 1;
                        objArr[((i15 / 3) << 1) + 1] = zzfi[i19];
                        i19 = i16;
                        i22 = i2;
                    } else {
                        if (i23 == zzcv.ENUM.id() || i23 == zzcv.ENUM_LIST.id() || i23 == zzcv.ENUM_LIST_PACKED.id()) {
                            if ((i4 & 1) == 1) {
                                i2 = i19 + 1;
                                objArr[((i15 / 3) << 1) + 1] = zzfi[i19];
                                i19 = i16;
                                i22 = i2;
                            }
                        } else if (i23 == zzcv.MAP.id()) {
                            i2 = i16 + 1;
                            iArr[i16] = i15;
                            i22 = i19 + 1;
                            objArr[(i15 / 3) << 1] = zzfi[i19];
                            if ((i20 & 2048) != 0) {
                                i19 = i22 + 1;
                                objArr[((i15 / 3) << 1) + 1] = zzfi[i22];
                                i22 = i19;
                                i19 = i2;
                            } else {
                                i19 = i2;
                            }
                        }
                        i22 = i19;
                        i19 = i16;
                    }
                    i21 = (int) unsafe.objectFieldOffset(zza);
                    if ((i4 & 1) != 1 || i23 > zzcv.GROUP.id()) {
                        i13 = 0;
                        i17 = i21;
                        i16 = i19;
                        i21 = i22;
                        i22 = 0;
                    } else {
                        i17 = i18 + 1;
                        charAt3 = zzfh.charAt(i18);
                        if (charAt3 >= '?') {
                            char charAt13;
                            i13 = charAt3 & 8191;
                            i2 = 13;
                            while (true) {
                                i16 = i17 + 1;
                                charAt13 = zzfh.charAt(i17);
                                if (charAt13 < '?') {
                                    break;
                                }
                                i13 |= (charAt13 & 8191) << i2;
                                i2 += 13;
                                i17 = i16;
                            }
                            i13 = (charAt13 << i2) | i13;
                        } else {
                            char c4 = charAt3;
                            i16 = i17;
                        }
                        i17 = (i13 / 32) + (i8 << 1);
                        obj = zzfi[i17];
                        if (obj instanceof Field) {
                            field = (Field) obj;
                        } else {
                            field = zza(cls2, (String) obj);
                            zzfi[i17] = field;
                        }
                        i13 %= 32;
                        i17 = i21;
                        i18 = i16;
                        i16 = i19;
                        i21 = i22;
                        i22 = (int) unsafe.objectFieldOffset(field);
                    }
                }
                if (i23 < 18 || i23 > 49) {
                    i2 = i14;
                } else {
                    i2 = i14 + 1;
                    iArr[i14] = i17;
                }
                i14 = i15 + 1;
                iArr2[i15] = i10;
                i15 = i14 + 1;
                iArr2[i14] = ((((i20 & 256) != 0 ? ErrorDialogData.BINDER_CRASH : 0) | ((i20 & 512) != 0 ? ErrorDialogData.DYNAMITE_CRASH : 0)) | (i23 << 20)) | i17;
                i10 = i15 + 1;
                iArr2[i15] = (i13 << 20) | i22;
                i15 = i10;
                i14 = i2;
                i13 = i5;
                i17 = i21;
            }
            return new zzel(iArr2, objArr, i3, i9, zzet.zzfb(), z, false, iArr, i6, i7 + i6, zzeo, zzdr, zzfp, zzcp, zzec);
        }
        ((zzfi) zzef).zzez();
        throw new NoSuchMethodError();
    }

    private final <K, V, UT, UB> UB zza(int i, int i2, Map<K, V> map, zzdf<?> zzdf, UB ub, zzfp<UT, UB> zzfp) {
        zzea zzn = this.zzua.zzn(zzao(i));
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            if (zzdf.zzam(((Integer) entry.getValue()).intValue()) == null) {
                if (ub == null) {
                    ub = zzfp.zzfy();
                }
                zzbz zzl = zzbu.zzl(zzdz.zza(zzn, entry.getKey(), entry.getValue()));
                try {
                    zzdz.zza(zzl.zzcb(), zzn, entry.getKey(), entry.getValue());
                    zzfp.zza((Object) ub, i2, zzl.zzca());
                    it.remove();
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return ub;
    }

    private final <UT, UB> UB zza(Object obj, int i, UB ub, zzfp<UT, UB> zzfp) {
        int i2 = this.zztk[i];
        Object zzp = zzfv.zzp(obj, (long) (zzaq(i) & 1048575));
        if (zzp == null) {
            return ub;
        }
        zzdf zzap = zzap(i);
        if (zzap == null) {
            return ub;
        }
        return zza(i, i2, this.zzua.zzi(zzp), zzap, ub, zzfp);
    }

    private static Field zza(Class<?> cls, String str) {
        Field declaredField;
        try {
            declaredField = cls.getDeclaredField(str);
        } catch (NoSuchFieldException e) {
            Field[] declaredFields = cls.getDeclaredFields();
            int length = declaredFields.length;
            int i = 0;
            while (i < length) {
                declaredField = declaredFields[i];
                if (!str.equals(declaredField.getName())) {
                    i++;
                }
            }
            String name = cls.getName();
            String arrays = Arrays.toString(declaredFields);
            throw new RuntimeException(new StringBuilder(((String.valueOf(str).length() + 40) + String.valueOf(name).length()) + String.valueOf(arrays).length()).append("Field ").append(str).append(" for ").append(name).append(" not found. Known fields are ").append(arrays).toString());
        }
        return declaredField;
    }

    private static void zza(int i, Object obj, zzgj zzgj) {
        if (obj instanceof String) {
            zzgj.zza(i, (String) obj);
        } else {
            zzgj.zza(i, (zzbu) obj);
        }
    }

    private static <UT, UB> void zza(zzfp<UT, UB> zzfp, T t, zzgj zzgj) {
        zzfp.zza(zzfp.zzr(t), zzgj);
    }

    private final <K, V> void zza(zzgj zzgj, int i, Object obj, int i2) {
        if (obj != null) {
            zzgj.zza(i, this.zzua.zzn(zzao(i2)), this.zzua.zzj(obj));
        }
    }

    private final void zza(Object obj, int i, zzeu zzeu) {
        if (zzas(i)) {
            zzfv.zza(obj, (long) (i & 1048575), zzeu.zzcj());
        } else if (this.zztq) {
            zzfv.zza(obj, (long) (i & 1048575), zzeu.readString());
        } else {
            zzfv.zza(obj, (long) (i & 1048575), zzeu.zzck());
        }
    }

    private final void zza(T t, T t2, int i) {
        long zzaq = (long) (zzaq(i) & 1048575);
        if (zza((Object) t2, i)) {
            Object zzp = zzfv.zzp(t, zzaq);
            Object zzp2 = zzfv.zzp(t2, zzaq);
            if (zzp != null && zzp2 != null) {
                zzfv.zza((Object) t, zzaq, zzdd.zza(zzp, zzp2));
                zzb((Object) t, i);
            } else if (zzp2 != null) {
                zzfv.zza((Object) t, zzaq, zzp2);
                zzb((Object) t, i);
            }
        }
    }

    private final boolean zza(T t, int i) {
        int zzaq;
        if (this.zztr) {
            zzaq = zzaq(i);
            long j = (long) (zzaq & 1048575);
            switch ((zzaq & 267386880) >>> 20) {
                case 0:
                    return zzfv.zzo(t, j) != 0.0d;
                case 1:
                    return zzfv.zzn(t, j) != BitmapDescriptorFactory.HUE_RED;
                case 2:
                    return zzfv.zzl(t, j) != 0;
                case 3:
                    return zzfv.zzl(t, j) != 0;
                case 4:
                    return zzfv.zzk(t, j) != 0;
                case 5:
                    return zzfv.zzl(t, j) != 0;
                case 6:
                    return zzfv.zzk(t, j) != 0;
                case 7:
                    return zzfv.zzm(t, j);
                case 8:
                    Object zzp = zzfv.zzp(t, j);
                    if (zzp instanceof String) {
                        return !((String) zzp).isEmpty();
                    } else {
                        if (zzp instanceof zzbu) {
                            return !zzbu.zzmi.equals(zzp);
                        } else {
                            throw new IllegalArgumentException();
                        }
                    }
                case 9:
                    return zzfv.zzp(t, j) != null;
                case 10:
                    return !zzbu.zzmi.equals(zzfv.zzp(t, j));
                case 11:
                    return zzfv.zzk(t, j) != 0;
                case 12:
                    return zzfv.zzk(t, j) != 0;
                case 13:
                    return zzfv.zzk(t, j) != 0;
                case 14:
                    return zzfv.zzl(t, j) != 0;
                case 15:
                    return zzfv.zzk(t, j) != 0;
                case 16:
                    return zzfv.zzl(t, j) != 0;
                case 17:
                    return zzfv.zzp(t, j) != null;
                default:
                    throw new IllegalArgumentException();
            }
        }
        zzaq = zzar(i);
        return (zzfv.zzk(t, (long) (zzaq & 1048575)) & (1 << (zzaq >>> 20))) != 0;
    }

    private final boolean zza(T t, int i, int i2) {
        return zzfv.zzk(t, (long) (zzar(i2) & 1048575)) == i;
    }

    private final boolean zza(T t, int i, int i2, int i3) {
        return this.zztr ? zza((Object) t, i) : (i2 & i3) != 0;
    }

    private static boolean zza(Object obj, int i, zzev zzev) {
        return zzev.zzp(zzfv.zzp(obj, (long) (1048575 & i)));
    }

    private final zzev zzan(int i) {
        int i2 = (i / 3) << 1;
        zzev zzev = (zzev) this.zztl[i2];
        if (zzev != null) {
            return zzev;
        }
        zzev = zzes.zzfg().zzf((Class) this.zztl[i2 + 1]);
        this.zztl[i2] = zzev;
        return zzev;
    }

    private final Object zzao(int i) {
        return this.zztl[(i / 3) << 1];
    }

    private final zzdf<?> zzap(int i) {
        return (zzdf) this.zztl[((i / 3) << 1) + 1];
    }

    private final int zzaq(int i) {
        return this.zztk[i + 1];
    }

    private final int zzar(int i) {
        return this.zztk[i + 2];
    }

    private static boolean zzas(int i) {
        return (ErrorDialogData.DYNAMITE_CRASH & i) != 0;
    }

    private final void zzb(T t, int i) {
        if (!this.zztr) {
            int zzar = zzar(i);
            long j = (long) (zzar & 1048575);
            zzfv.zzb((Object) t, j, zzfv.zzk(t, j) | (1 << (zzar >>> 20)));
        }
    }

    private final void zzb(T t, int i, int i2) {
        zzfv.zzb((Object) t, (long) (zzar(i2) & 1048575), i);
    }

    private final void zzb(T t, zzgj zzgj) {
        Iterator it = null;
        Entry entry = null;
        if (this.zztp) {
            zzcs zzc = this.zztz.zzc(t);
            if (!zzc.isEmpty()) {
                it = zzc.iterator();
                entry = (Entry) it.next();
            }
        }
        int length = this.zztk.length;
        Unsafe unsafe = zztj;
        int i = 0;
        int i2 = -1;
        Entry entry2 = entry;
        int i3 = 0;
        while (i < length) {
            Entry entry3;
            int i4;
            int zzaq = zzaq(i);
            int i5 = this.zztk[i];
            int i6 = (267386880 & zzaq) >>> 20;
            int i7 = 0;
            if (this.zztr || i6 > 17) {
                entry3 = entry2;
                i4 = i2;
                i2 = i3;
            } else {
                int i8 = this.zztk[i + 2];
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
            while (entry3 != null && this.zztz.zza(entry3) <= i5) {
                this.zztz.zza(zzgj, entry3);
                entry3 = it.hasNext() ? (Entry) it.next() : null;
            }
            long j = (long) (1048575 & zzaq);
            switch (i6) {
                case 0:
                    if ((i2 & i7) == 0) {
                        break;
                    }
                    zzgj.zza(i5, zzfv.zzo(t, j));
                    break;
                case 1:
                    if ((i2 & i7) == 0) {
                        break;
                    }
                    zzgj.zza(i5, zzfv.zzn(t, j));
                    break;
                case 2:
                    if ((i2 & i7) == 0) {
                        break;
                    }
                    zzgj.zzi(i5, unsafe.getLong(t, j));
                    break;
                case 3:
                    if ((i2 & i7) == 0) {
                        break;
                    }
                    zzgj.zza(i5, unsafe.getLong(t, j));
                    break;
                case 4:
                    if ((i2 & i7) == 0) {
                        break;
                    }
                    zzgj.zzc(i5, unsafe.getInt(t, j));
                    break;
                case 5:
                    if ((i2 & i7) == 0) {
                        break;
                    }
                    zzgj.zzc(i5, unsafe.getLong(t, j));
                    break;
                case 6:
                    if ((i2 & i7) == 0) {
                        break;
                    }
                    zzgj.zzf(i5, unsafe.getInt(t, j));
                    break;
                case 7:
                    if ((i2 & i7) == 0) {
                        break;
                    }
                    zzgj.zzb(i5, zzfv.zzm(t, j));
                    break;
                case 8:
                    if ((i2 & i7) == 0) {
                        break;
                    }
                    zza(i5, unsafe.getObject(t, j), zzgj);
                    break;
                case 9:
                    if ((i2 & i7) == 0) {
                        break;
                    }
                    zzgj.zza(i5, unsafe.getObject(t, j), zzan(i));
                    break;
                case 10:
                    if ((i2 & i7) == 0) {
                        break;
                    }
                    zzgj.zza(i5, (zzbu) unsafe.getObject(t, j));
                    break;
                case 11:
                    if ((i2 & i7) == 0) {
                        break;
                    }
                    zzgj.zzd(i5, unsafe.getInt(t, j));
                    break;
                case 12:
                    if ((i2 & i7) == 0) {
                        break;
                    }
                    zzgj.zzn(i5, unsafe.getInt(t, j));
                    break;
                case 13:
                    if ((i2 & i7) == 0) {
                        break;
                    }
                    zzgj.zzm(i5, unsafe.getInt(t, j));
                    break;
                case 14:
                    if ((i2 & i7) == 0) {
                        break;
                    }
                    zzgj.zzj(i5, unsafe.getLong(t, j));
                    break;
                case 15:
                    if ((i2 & i7) == 0) {
                        break;
                    }
                    zzgj.zze(i5, unsafe.getInt(t, j));
                    break;
                case 16:
                    if ((i2 & i7) == 0) {
                        break;
                    }
                    zzgj.zzb(i5, unsafe.getLong(t, j));
                    break;
                case 17:
                    if ((i2 & i7) == 0) {
                        break;
                    }
                    zzgj.zzb(i5, unsafe.getObject(t, j), zzan(i));
                    break;
                case 18:
                    zzex.zza(this.zztk[i], (List) unsafe.getObject(t, j), zzgj, false);
                    break;
                case 19:
                    zzex.zzb(this.zztk[i], (List) unsafe.getObject(t, j), zzgj, false);
                    break;
                case 20:
                    zzex.zzc(this.zztk[i], (List) unsafe.getObject(t, j), zzgj, false);
                    break;
                case 21:
                    zzex.zzd(this.zztk[i], (List) unsafe.getObject(t, j), zzgj, false);
                    break;
                case 22:
                    zzex.zzh(this.zztk[i], (List) unsafe.getObject(t, j), zzgj, false);
                    break;
                case 23:
                    zzex.zzf(this.zztk[i], (List) unsafe.getObject(t, j), zzgj, false);
                    break;
                case 24:
                    zzex.zzk(this.zztk[i], (List) unsafe.getObject(t, j), zzgj, false);
                    break;
                case 25:
                    zzex.zzn(this.zztk[i], (List) unsafe.getObject(t, j), zzgj, false);
                    break;
                case 26:
                    zzex.zza(this.zztk[i], (List) unsafe.getObject(t, j), zzgj);
                    break;
                case 27:
                    zzex.zza(this.zztk[i], (List) unsafe.getObject(t, j), zzgj, zzan(i));
                    break;
                case 28:
                    zzex.zzb(this.zztk[i], (List) unsafe.getObject(t, j), zzgj);
                    break;
                case 29:
                    zzex.zzi(this.zztk[i], (List) unsafe.getObject(t, j), zzgj, false);
                    break;
                case 30:
                    zzex.zzm(this.zztk[i], (List) unsafe.getObject(t, j), zzgj, false);
                    break;
                case 31:
                    zzex.zzl(this.zztk[i], (List) unsafe.getObject(t, j), zzgj, false);
                    break;
                case 32:
                    zzex.zzg(this.zztk[i], (List) unsafe.getObject(t, j), zzgj, false);
                    break;
                case 33:
                    zzex.zzj(this.zztk[i], (List) unsafe.getObject(t, j), zzgj, false);
                    break;
                case 34:
                    zzex.zze(this.zztk[i], (List) unsafe.getObject(t, j), zzgj, false);
                    break;
                case 35:
                    zzex.zza(this.zztk[i], (List) unsafe.getObject(t, j), zzgj, true);
                    break;
                case 36:
                    zzex.zzb(this.zztk[i], (List) unsafe.getObject(t, j), zzgj, true);
                    break;
                case 37:
                    zzex.zzc(this.zztk[i], (List) unsafe.getObject(t, j), zzgj, true);
                    break;
                case 38:
                    zzex.zzd(this.zztk[i], (List) unsafe.getObject(t, j), zzgj, true);
                    break;
                case 39:
                    zzex.zzh(this.zztk[i], (List) unsafe.getObject(t, j), zzgj, true);
                    break;
                case 40:
                    zzex.zzf(this.zztk[i], (List) unsafe.getObject(t, j), zzgj, true);
                    break;
                case 41:
                    zzex.zzk(this.zztk[i], (List) unsafe.getObject(t, j), zzgj, true);
                    break;
                case 42:
                    zzex.zzn(this.zztk[i], (List) unsafe.getObject(t, j), zzgj, true);
                    break;
                case 43:
                    zzex.zzi(this.zztk[i], (List) unsafe.getObject(t, j), zzgj, true);
                    break;
                case 44:
                    zzex.zzm(this.zztk[i], (List) unsafe.getObject(t, j), zzgj, true);
                    break;
                case 45:
                    zzex.zzl(this.zztk[i], (List) unsafe.getObject(t, j), zzgj, true);
                    break;
                case 46:
                    zzex.zzg(this.zztk[i], (List) unsafe.getObject(t, j), zzgj, true);
                    break;
                case 47:
                    zzex.zzj(this.zztk[i], (List) unsafe.getObject(t, j), zzgj, true);
                    break;
                case 48:
                    zzex.zze(this.zztk[i], (List) unsafe.getObject(t, j), zzgj, true);
                    break;
                case 49:
                    zzex.zzb(this.zztk[i], (List) unsafe.getObject(t, j), zzgj, zzan(i));
                    break;
                case 50:
                    zza(zzgj, i5, unsafe.getObject(t, j), i);
                    break;
                case 51:
                    if (!zza((Object) t, i5, i)) {
                        break;
                    }
                    zzgj.zza(i5, zzf(t, j));
                    break;
                case 52:
                    if (!zza((Object) t, i5, i)) {
                        break;
                    }
                    zzgj.zza(i5, zzg(t, j));
                    break;
                case 53:
                    if (!zza((Object) t, i5, i)) {
                        break;
                    }
                    zzgj.zzi(i5, zzi(t, j));
                    break;
                case 54:
                    if (!zza((Object) t, i5, i)) {
                        break;
                    }
                    zzgj.zza(i5, zzi(t, j));
                    break;
                case 55:
                    if (!zza((Object) t, i5, i)) {
                        break;
                    }
                    zzgj.zzc(i5, zzh(t, j));
                    break;
                case 56:
                    if (!zza((Object) t, i5, i)) {
                        break;
                    }
                    zzgj.zzc(i5, zzi(t, j));
                    break;
                case 57:
                    if (!zza((Object) t, i5, i)) {
                        break;
                    }
                    zzgj.zzf(i5, zzh(t, j));
                    break;
                case 58:
                    if (!zza((Object) t, i5, i)) {
                        break;
                    }
                    zzgj.zzb(i5, zzj(t, j));
                    break;
                case 59:
                    if (!zza((Object) t, i5, i)) {
                        break;
                    }
                    zza(i5, unsafe.getObject(t, j), zzgj);
                    break;
                case 60:
                    if (!zza((Object) t, i5, i)) {
                        break;
                    }
                    zzgj.zza(i5, unsafe.getObject(t, j), zzan(i));
                    break;
                case 61:
                    if (!zza((Object) t, i5, i)) {
                        break;
                    }
                    zzgj.zza(i5, (zzbu) unsafe.getObject(t, j));
                    break;
                case 62:
                    if (!zza((Object) t, i5, i)) {
                        break;
                    }
                    zzgj.zzd(i5, zzh(t, j));
                    break;
                case 63:
                    if (!zza((Object) t, i5, i)) {
                        break;
                    }
                    zzgj.zzn(i5, zzh(t, j));
                    break;
                case 64:
                    if (!zza((Object) t, i5, i)) {
                        break;
                    }
                    zzgj.zzm(i5, zzh(t, j));
                    break;
                case 65:
                    if (!zza((Object) t, i5, i)) {
                        break;
                    }
                    zzgj.zzj(i5, zzi(t, j));
                    break;
                case 66:
                    if (!zza((Object) t, i5, i)) {
                        break;
                    }
                    zzgj.zze(i5, zzh(t, j));
                    break;
                case 67:
                    if (!zza((Object) t, i5, i)) {
                        break;
                    }
                    zzgj.zzb(i5, zzi(t, j));
                    break;
                case 68:
                    if (!zza((Object) t, i5, i)) {
                        break;
                    }
                    zzgj.zzb(i5, unsafe.getObject(t, j), zzan(i));
                    break;
                default:
                    break;
            }
            i += 3;
            i3 = i2;
            i2 = i4;
            entry2 = entry3;
        }
        for (entry = entry2; entry != null; entry = it.hasNext() ? (Entry) it.next() : null) {
            this.zztz.zza(zzgj, entry);
        }
        zza(this.zzty, (Object) t, zzgj);
    }

    private final void zzb(T t, T t2, int i) {
        int zzaq = zzaq(i);
        int i2 = this.zztk[i];
        long j = (long) (zzaq & 1048575);
        if (zza((Object) t2, i2, i)) {
            Object zzp = zzfv.zzp(t, j);
            Object zzp2 = zzfv.zzp(t2, j);
            if (zzp != null && zzp2 != null) {
                zzfv.zza((Object) t, j, zzdd.zza(zzp, zzp2));
                zzb((Object) t, i2, i);
            } else if (zzp2 != null) {
                zzfv.zza((Object) t, j, zzp2);
                zzb((Object) t, i2, i);
            }
        }
    }

    private final boolean zzc(T t, T t2, int i) {
        return zza((Object) t, i) == zza((Object) t2, i);
    }

    private static <E> List<E> zze(Object obj, long j) {
        return (List) zzfv.zzp(obj, j);
    }

    private static <T> double zzf(T t, long j) {
        return ((Double) zzfv.zzp(t, j)).doubleValue();
    }

    private static <T> float zzg(T t, long j) {
        return ((Float) zzfv.zzp(t, j)).floatValue();
    }

    private static <T> int zzh(T t, long j) {
        return ((Integer) zzfv.zzp(t, j)).intValue();
    }

    private static <T> long zzi(T t, long j) {
        return ((Long) zzfv.zzp(t, j)).longValue();
    }

    private static <T> boolean zzj(T t, long j) {
        return ((Boolean) zzfv.zzp(t, j)).booleanValue();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean equals(T r12, T r13) {
        /*
        r11 = this;
        r1 = 1;
        r10 = 1048575; // 0xfffff float:1.469367E-39 double:5.18065E-318;
        r0 = 0;
        r2 = r11.zztk;
        r4 = r2.length;
        r3 = r0;
    L_0x0009:
        if (r3 >= r4) goto L_0x01cf;
    L_0x000b:
        r2 = r11.zzaq(r3);
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
        r8 = com.google.android.gms.internal.firebase_auth.zzfv.zzl(r12, r6);
        r6 = com.google.android.gms.internal.firebase_auth.zzfv.zzl(r13, r6);
        r2 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1));
        if (r2 == 0) goto L_0x001a;
    L_0x0030:
        r2 = r0;
        goto L_0x001b;
    L_0x0032:
        r2 = r11.zzc(r12, r13, r3);
        if (r2 == 0) goto L_0x0042;
    L_0x0038:
        r2 = com.google.android.gms.internal.firebase_auth.zzfv.zzk(r12, r6);
        r5 = com.google.android.gms.internal.firebase_auth.zzfv.zzk(r13, r6);
        if (r2 == r5) goto L_0x001a;
    L_0x0042:
        r2 = r0;
        goto L_0x001b;
    L_0x0044:
        r2 = r11.zzc(r12, r13, r3);
        if (r2 == 0) goto L_0x0056;
    L_0x004a:
        r8 = com.google.android.gms.internal.firebase_auth.zzfv.zzl(r12, r6);
        r6 = com.google.android.gms.internal.firebase_auth.zzfv.zzl(r13, r6);
        r2 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1));
        if (r2 == 0) goto L_0x001a;
    L_0x0056:
        r2 = r0;
        goto L_0x001b;
    L_0x0058:
        r2 = r11.zzc(r12, r13, r3);
        if (r2 == 0) goto L_0x006a;
    L_0x005e:
        r8 = com.google.android.gms.internal.firebase_auth.zzfv.zzl(r12, r6);
        r6 = com.google.android.gms.internal.firebase_auth.zzfv.zzl(r13, r6);
        r2 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1));
        if (r2 == 0) goto L_0x001a;
    L_0x006a:
        r2 = r0;
        goto L_0x001b;
    L_0x006c:
        r2 = r11.zzc(r12, r13, r3);
        if (r2 == 0) goto L_0x007c;
    L_0x0072:
        r2 = com.google.android.gms.internal.firebase_auth.zzfv.zzk(r12, r6);
        r5 = com.google.android.gms.internal.firebase_auth.zzfv.zzk(r13, r6);
        if (r2 == r5) goto L_0x001a;
    L_0x007c:
        r2 = r0;
        goto L_0x001b;
    L_0x007e:
        r2 = r11.zzc(r12, r13, r3);
        if (r2 == 0) goto L_0x0090;
    L_0x0084:
        r8 = com.google.android.gms.internal.firebase_auth.zzfv.zzl(r12, r6);
        r6 = com.google.android.gms.internal.firebase_auth.zzfv.zzl(r13, r6);
        r2 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1));
        if (r2 == 0) goto L_0x001a;
    L_0x0090:
        r2 = r0;
        goto L_0x001b;
    L_0x0092:
        r2 = r11.zzc(r12, r13, r3);
        if (r2 == 0) goto L_0x00a2;
    L_0x0098:
        r2 = com.google.android.gms.internal.firebase_auth.zzfv.zzk(r12, r6);
        r5 = com.google.android.gms.internal.firebase_auth.zzfv.zzk(r13, r6);
        if (r2 == r5) goto L_0x001a;
    L_0x00a2:
        r2 = r0;
        goto L_0x001b;
    L_0x00a5:
        r2 = r11.zzc(r12, r13, r3);
        if (r2 == 0) goto L_0x00b5;
    L_0x00ab:
        r2 = com.google.android.gms.internal.firebase_auth.zzfv.zzm(r12, r6);
        r5 = com.google.android.gms.internal.firebase_auth.zzfv.zzm(r13, r6);
        if (r2 == r5) goto L_0x001a;
    L_0x00b5:
        r2 = r0;
        goto L_0x001b;
    L_0x00b8:
        r2 = r11.zzc(r12, r13, r3);
        if (r2 == 0) goto L_0x00cc;
    L_0x00be:
        r2 = com.google.android.gms.internal.firebase_auth.zzfv.zzp(r12, r6);
        r5 = com.google.android.gms.internal.firebase_auth.zzfv.zzp(r13, r6);
        r2 = com.google.android.gms.internal.firebase_auth.zzex.zzd(r2, r5);
        if (r2 != 0) goto L_0x001a;
    L_0x00cc:
        r2 = r0;
        goto L_0x001b;
    L_0x00cf:
        r2 = r11.zzc(r12, r13, r3);
        if (r2 == 0) goto L_0x00e3;
    L_0x00d5:
        r2 = com.google.android.gms.internal.firebase_auth.zzfv.zzp(r12, r6);
        r5 = com.google.android.gms.internal.firebase_auth.zzfv.zzp(r13, r6);
        r2 = com.google.android.gms.internal.firebase_auth.zzex.zzd(r2, r5);
        if (r2 != 0) goto L_0x001a;
    L_0x00e3:
        r2 = r0;
        goto L_0x001b;
    L_0x00e6:
        r2 = r11.zzc(r12, r13, r3);
        if (r2 == 0) goto L_0x00fa;
    L_0x00ec:
        r2 = com.google.android.gms.internal.firebase_auth.zzfv.zzp(r12, r6);
        r5 = com.google.android.gms.internal.firebase_auth.zzfv.zzp(r13, r6);
        r2 = com.google.android.gms.internal.firebase_auth.zzex.zzd(r2, r5);
        if (r2 != 0) goto L_0x001a;
    L_0x00fa:
        r2 = r0;
        goto L_0x001b;
    L_0x00fd:
        r2 = r11.zzc(r12, r13, r3);
        if (r2 == 0) goto L_0x010d;
    L_0x0103:
        r2 = com.google.android.gms.internal.firebase_auth.zzfv.zzk(r12, r6);
        r5 = com.google.android.gms.internal.firebase_auth.zzfv.zzk(r13, r6);
        if (r2 == r5) goto L_0x001a;
    L_0x010d:
        r2 = r0;
        goto L_0x001b;
    L_0x0110:
        r2 = r11.zzc(r12, r13, r3);
        if (r2 == 0) goto L_0x0120;
    L_0x0116:
        r2 = com.google.android.gms.internal.firebase_auth.zzfv.zzk(r12, r6);
        r5 = com.google.android.gms.internal.firebase_auth.zzfv.zzk(r13, r6);
        if (r2 == r5) goto L_0x001a;
    L_0x0120:
        r2 = r0;
        goto L_0x001b;
    L_0x0123:
        r2 = r11.zzc(r12, r13, r3);
        if (r2 == 0) goto L_0x0133;
    L_0x0129:
        r2 = com.google.android.gms.internal.firebase_auth.zzfv.zzk(r12, r6);
        r5 = com.google.android.gms.internal.firebase_auth.zzfv.zzk(r13, r6);
        if (r2 == r5) goto L_0x001a;
    L_0x0133:
        r2 = r0;
        goto L_0x001b;
    L_0x0136:
        r2 = r11.zzc(r12, r13, r3);
        if (r2 == 0) goto L_0x0148;
    L_0x013c:
        r8 = com.google.android.gms.internal.firebase_auth.zzfv.zzl(r12, r6);
        r6 = com.google.android.gms.internal.firebase_auth.zzfv.zzl(r13, r6);
        r2 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1));
        if (r2 == 0) goto L_0x001a;
    L_0x0148:
        r2 = r0;
        goto L_0x001b;
    L_0x014b:
        r2 = r11.zzc(r12, r13, r3);
        if (r2 == 0) goto L_0x015b;
    L_0x0151:
        r2 = com.google.android.gms.internal.firebase_auth.zzfv.zzk(r12, r6);
        r5 = com.google.android.gms.internal.firebase_auth.zzfv.zzk(r13, r6);
        if (r2 == r5) goto L_0x001a;
    L_0x015b:
        r2 = r0;
        goto L_0x001b;
    L_0x015e:
        r2 = r11.zzc(r12, r13, r3);
        if (r2 == 0) goto L_0x0170;
    L_0x0164:
        r8 = com.google.android.gms.internal.firebase_auth.zzfv.zzl(r12, r6);
        r6 = com.google.android.gms.internal.firebase_auth.zzfv.zzl(r13, r6);
        r2 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1));
        if (r2 == 0) goto L_0x001a;
    L_0x0170:
        r2 = r0;
        goto L_0x001b;
    L_0x0173:
        r2 = r11.zzc(r12, r13, r3);
        if (r2 == 0) goto L_0x0187;
    L_0x0179:
        r2 = com.google.android.gms.internal.firebase_auth.zzfv.zzp(r12, r6);
        r5 = com.google.android.gms.internal.firebase_auth.zzfv.zzp(r13, r6);
        r2 = com.google.android.gms.internal.firebase_auth.zzex.zzd(r2, r5);
        if (r2 != 0) goto L_0x001a;
    L_0x0187:
        r2 = r0;
        goto L_0x001b;
    L_0x018a:
        r2 = com.google.android.gms.internal.firebase_auth.zzfv.zzp(r12, r6);
        r5 = com.google.android.gms.internal.firebase_auth.zzfv.zzp(r13, r6);
        r2 = com.google.android.gms.internal.firebase_auth.zzex.zzd(r2, r5);
        goto L_0x001b;
    L_0x0198:
        r2 = com.google.android.gms.internal.firebase_auth.zzfv.zzp(r12, r6);
        r5 = com.google.android.gms.internal.firebase_auth.zzfv.zzp(r13, r6);
        r2 = com.google.android.gms.internal.firebase_auth.zzex.zzd(r2, r5);
        goto L_0x001b;
    L_0x01a6:
        r2 = r11.zzar(r3);
        r5 = r2 & r10;
        r8 = (long) r5;
        r5 = com.google.android.gms.internal.firebase_auth.zzfv.zzk(r12, r8);
        r2 = r2 & r10;
        r8 = (long) r2;
        r2 = com.google.android.gms.internal.firebase_auth.zzfv.zzk(r13, r8);
        if (r5 != r2) goto L_0x01c7;
    L_0x01b9:
        r2 = com.google.android.gms.internal.firebase_auth.zzfv.zzp(r12, r6);
        r5 = com.google.android.gms.internal.firebase_auth.zzfv.zzp(r13, r6);
        r2 = com.google.android.gms.internal.firebase_auth.zzex.zzd(r2, r5);
        if (r2 != 0) goto L_0x001a;
    L_0x01c7:
        r2 = r0;
        goto L_0x001b;
    L_0x01ca:
        r2 = r3 + 3;
        r3 = r2;
        goto L_0x0009;
    L_0x01cf:
        r2 = r11.zzty;
        r2 = r2.zzr(r12);
        r3 = r11.zzty;
        r3 = r3.zzr(r13);
        r2 = r2.equals(r3);
        if (r2 == 0) goto L_0x001d;
    L_0x01e1:
        r0 = r11.zztp;
        if (r0 == 0) goto L_0x01f7;
    L_0x01e5:
        r0 = r11.zztz;
        r0 = r0.zzc(r12);
        r1 = r11.zztz;
        r1 = r1.zzc(r13);
        r0 = r0.equals(r1);
        goto L_0x001d;
    L_0x01f7:
        r0 = r1;
        goto L_0x001d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_auth.zzel.equals(java.lang.Object, java.lang.Object):boolean");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int hashCode(T r10) {
        /*
        r9 = this;
        r1 = 37;
        r0 = 0;
        r2 = r9.zztk;
        r4 = r2.length;
        r3 = r0;
        r2 = r0;
    L_0x0008:
        if (r3 >= r4) goto L_0x0255;
    L_0x000a:
        r0 = r9.zzaq(r3);
        r5 = r9.zztk;
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
        r2 = r3 + 3;
        r3 = r2;
        r2 = r0;
        goto L_0x0008;
    L_0x0025:
        r0 = r2 * 53;
        r6 = com.google.android.gms.internal.firebase_auth.zzfv.zzo(r10, r6);
        r6 = java.lang.Double.doubleToLongBits(r6);
        r2 = com.google.android.gms.internal.firebase_auth.zzdd.zzk(r6);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x0035:
        r0 = r2 * 53;
        r2 = com.google.android.gms.internal.firebase_auth.zzfv.zzn(r10, r6);
        r2 = java.lang.Float.floatToIntBits(r2);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x0041:
        r0 = r2 * 53;
        r6 = com.google.android.gms.internal.firebase_auth.zzfv.zzl(r10, r6);
        r2 = com.google.android.gms.internal.firebase_auth.zzdd.zzk(r6);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x004d:
        r0 = r2 * 53;
        r6 = com.google.android.gms.internal.firebase_auth.zzfv.zzl(r10, r6);
        r2 = com.google.android.gms.internal.firebase_auth.zzdd.zzk(r6);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x0059:
        r0 = r2 * 53;
        r2 = com.google.android.gms.internal.firebase_auth.zzfv.zzk(r10, r6);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x0061:
        r0 = r2 * 53;
        r6 = com.google.android.gms.internal.firebase_auth.zzfv.zzl(r10, r6);
        r2 = com.google.android.gms.internal.firebase_auth.zzdd.zzk(r6);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x006d:
        r0 = r2 * 53;
        r2 = com.google.android.gms.internal.firebase_auth.zzfv.zzk(r10, r6);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x0075:
        r0 = r2 * 53;
        r2 = com.google.android.gms.internal.firebase_auth.zzfv.zzm(r10, r6);
        r2 = com.google.android.gms.internal.firebase_auth.zzdd.zzh(r2);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x0081:
        r2 = r2 * 53;
        r0 = com.google.android.gms.internal.firebase_auth.zzfv.zzp(r10, r6);
        r0 = (java.lang.String) r0;
        r0 = r0.hashCode();
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x008f:
        r0 = com.google.android.gms.internal.firebase_auth.zzfv.zzp(r10, r6);
        if (r0 == 0) goto L_0x0277;
    L_0x0095:
        r0 = r0.hashCode();
    L_0x0099:
        r2 = r2 * 53;
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x009d:
        r0 = r2 * 53;
        r2 = com.google.android.gms.internal.firebase_auth.zzfv.zzp(r10, r6);
        r2 = r2.hashCode();
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x00aa:
        r0 = r2 * 53;
        r2 = com.google.android.gms.internal.firebase_auth.zzfv.zzk(r10, r6);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x00b3:
        r0 = r2 * 53;
        r2 = com.google.android.gms.internal.firebase_auth.zzfv.zzk(r10, r6);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x00bc:
        r0 = r2 * 53;
        r2 = com.google.android.gms.internal.firebase_auth.zzfv.zzk(r10, r6);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x00c5:
        r0 = r2 * 53;
        r6 = com.google.android.gms.internal.firebase_auth.zzfv.zzl(r10, r6);
        r2 = com.google.android.gms.internal.firebase_auth.zzdd.zzk(r6);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x00d2:
        r0 = r2 * 53;
        r2 = com.google.android.gms.internal.firebase_auth.zzfv.zzk(r10, r6);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x00db:
        r0 = r2 * 53;
        r6 = com.google.android.gms.internal.firebase_auth.zzfv.zzl(r10, r6);
        r2 = com.google.android.gms.internal.firebase_auth.zzdd.zzk(r6);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x00e8:
        r0 = com.google.android.gms.internal.firebase_auth.zzfv.zzp(r10, r6);
        if (r0 == 0) goto L_0x0274;
    L_0x00ee:
        r0 = r0.hashCode();
    L_0x00f2:
        r2 = r2 * 53;
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x00f7:
        r0 = r2 * 53;
        r2 = com.google.android.gms.internal.firebase_auth.zzfv.zzp(r10, r6);
        r2 = r2.hashCode();
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x0104:
        r0 = r2 * 53;
        r2 = com.google.android.gms.internal.firebase_auth.zzfv.zzp(r10, r6);
        r2 = r2.hashCode();
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x0111:
        r0 = r9.zza(r10, r5, r3);
        if (r0 == 0) goto L_0x001f;
    L_0x0117:
        r0 = r2 * 53;
        r6 = zzf(r10, r6);
        r6 = java.lang.Double.doubleToLongBits(r6);
        r2 = com.google.android.gms.internal.firebase_auth.zzdd.zzk(r6);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x0128:
        r0 = r9.zza(r10, r5, r3);
        if (r0 == 0) goto L_0x001f;
    L_0x012e:
        r0 = r2 * 53;
        r2 = zzg(r10, r6);
        r2 = java.lang.Float.floatToIntBits(r2);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x013b:
        r0 = r9.zza(r10, r5, r3);
        if (r0 == 0) goto L_0x001f;
    L_0x0141:
        r0 = r2 * 53;
        r6 = zzi(r10, r6);
        r2 = com.google.android.gms.internal.firebase_auth.zzdd.zzk(r6);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x014e:
        r0 = r9.zza(r10, r5, r3);
        if (r0 == 0) goto L_0x001f;
    L_0x0154:
        r0 = r2 * 53;
        r6 = zzi(r10, r6);
        r2 = com.google.android.gms.internal.firebase_auth.zzdd.zzk(r6);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x0161:
        r0 = r9.zza(r10, r5, r3);
        if (r0 == 0) goto L_0x001f;
    L_0x0167:
        r0 = r2 * 53;
        r2 = zzh(r10, r6);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x0170:
        r0 = r9.zza(r10, r5, r3);
        if (r0 == 0) goto L_0x001f;
    L_0x0176:
        r0 = r2 * 53;
        r6 = zzi(r10, r6);
        r2 = com.google.android.gms.internal.firebase_auth.zzdd.zzk(r6);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x0183:
        r0 = r9.zza(r10, r5, r3);
        if (r0 == 0) goto L_0x001f;
    L_0x0189:
        r0 = r2 * 53;
        r2 = zzh(r10, r6);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x0192:
        r0 = r9.zza(r10, r5, r3);
        if (r0 == 0) goto L_0x001f;
    L_0x0198:
        r0 = r2 * 53;
        r2 = zzj(r10, r6);
        r2 = com.google.android.gms.internal.firebase_auth.zzdd.zzh(r2);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x01a5:
        r0 = r9.zza(r10, r5, r3);
        if (r0 == 0) goto L_0x001f;
    L_0x01ab:
        r2 = r2 * 53;
        r0 = com.google.android.gms.internal.firebase_auth.zzfv.zzp(r10, r6);
        r0 = (java.lang.String) r0;
        r0 = r0.hashCode();
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x01ba:
        r0 = r9.zza(r10, r5, r3);
        if (r0 == 0) goto L_0x001f;
    L_0x01c0:
        r0 = com.google.android.gms.internal.firebase_auth.zzfv.zzp(r10, r6);
        r2 = r2 * 53;
        r0 = r0.hashCode();
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x01cd:
        r0 = r9.zza(r10, r5, r3);
        if (r0 == 0) goto L_0x001f;
    L_0x01d3:
        r0 = r2 * 53;
        r2 = com.google.android.gms.internal.firebase_auth.zzfv.zzp(r10, r6);
        r2 = r2.hashCode();
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x01e0:
        r0 = r9.zza(r10, r5, r3);
        if (r0 == 0) goto L_0x001f;
    L_0x01e6:
        r0 = r2 * 53;
        r2 = zzh(r10, r6);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x01ef:
        r0 = r9.zza(r10, r5, r3);
        if (r0 == 0) goto L_0x001f;
    L_0x01f5:
        r0 = r2 * 53;
        r2 = zzh(r10, r6);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x01fe:
        r0 = r9.zza(r10, r5, r3);
        if (r0 == 0) goto L_0x001f;
    L_0x0204:
        r0 = r2 * 53;
        r2 = zzh(r10, r6);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x020d:
        r0 = r9.zza(r10, r5, r3);
        if (r0 == 0) goto L_0x001f;
    L_0x0213:
        r0 = r2 * 53;
        r6 = zzi(r10, r6);
        r2 = com.google.android.gms.internal.firebase_auth.zzdd.zzk(r6);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x0220:
        r0 = r9.zza(r10, r5, r3);
        if (r0 == 0) goto L_0x001f;
    L_0x0226:
        r0 = r2 * 53;
        r2 = zzh(r10, r6);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x022f:
        r0 = r9.zza(r10, r5, r3);
        if (r0 == 0) goto L_0x001f;
    L_0x0235:
        r0 = r2 * 53;
        r6 = zzi(r10, r6);
        r2 = com.google.android.gms.internal.firebase_auth.zzdd.zzk(r6);
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x0242:
        r0 = r9.zza(r10, r5, r3);
        if (r0 == 0) goto L_0x001f;
    L_0x0248:
        r0 = com.google.android.gms.internal.firebase_auth.zzfv.zzp(r10, r6);
        r2 = r2 * 53;
        r0 = r0.hashCode();
        r0 = r0 + r2;
        goto L_0x0020;
    L_0x0255:
        r0 = r2 * 53;
        r1 = r9.zzty;
        r1 = r1.zzr(r10);
        r1 = r1.hashCode();
        r0 = r0 + r1;
        r1 = r9.zztp;
        if (r1 == 0) goto L_0x0273;
    L_0x0266:
        r0 = r0 * 53;
        r1 = r9.zztz;
        r1 = r1.zzc(r10);
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
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_auth.zzel.hashCode(java.lang.Object):int");
    }

    public final T newInstance() {
        return this.zztw.newInstance(this.zzto);
    }

    public final void zza(T t, zzeu zzeu, zzco zzco) {
        Throwable th;
        if (zzco == null) {
            throw new NullPointerException();
        }
        zzfp zzfp = this.zzty;
        zzcp zzcp = this.zztz;
        Object obj = null;
        zzcs zzcs = null;
        while (true) {
            int i;
            int i2;
            int length;
            Object zzm;
            int i3;
            int zzda = zzeu.zzda();
            if (zzda < this.zztm || zzda > this.zztn) {
                i = -1;
            } else {
                i2 = 0;
                length = (this.zztk.length / 3) - 1;
                while (i2 <= length) {
                    int i4 = (length + i2) >>> 1;
                    i = i4 * 3;
                    int i5 = this.zztk[i];
                    if (zzda != i5) {
                        if (zzda < i5) {
                            length = i4 - 1;
                        } else {
                            i2 = i4 + 1;
                        }
                    }
                }
                i = -1;
            }
            Object zzp;
            if (i >= 0) {
                length = zzaq(i);
                zzdf zzap;
                List zza;
                switch ((267386880 & length) >>> 20) {
                    case 0:
                        zzfv.zza((Object) t, (long) (length & 1048575), zzeu.readDouble());
                        zzb((Object) t, i);
                        continue;
                    case 1:
                        zzfv.zza((Object) t, (long) (length & 1048575), zzeu.readFloat());
                        zzb((Object) t, i);
                        continue;
                    case 2:
                        zzfv.zza((Object) t, (long) (length & 1048575), zzeu.zzce());
                        zzb((Object) t, i);
                        continue;
                    case 3:
                        zzfv.zza((Object) t, (long) (length & 1048575), zzeu.zzcd());
                        zzb((Object) t, i);
                        continue;
                    case 4:
                        zzfv.zzb((Object) t, (long) (length & 1048575), zzeu.zzcf());
                        zzb((Object) t, i);
                        continue;
                    case 5:
                        zzfv.zza((Object) t, (long) (length & 1048575), zzeu.zzcg());
                        zzb((Object) t, i);
                        continue;
                    case 6:
                        zzfv.zzb((Object) t, (long) (length & 1048575), zzeu.zzch());
                        zzb((Object) t, i);
                        continue;
                    case 7:
                        zzfv.zza((Object) t, (long) (length & 1048575), zzeu.zzci());
                        zzb((Object) t, i);
                        continue;
                    case 8:
                        zza((Object) t, length, zzeu);
                        zzb((Object) t, i);
                        continue;
                    case 9:
                        if (!zza((Object) t, i)) {
                            zzfv.zza((Object) t, (long) (length & 1048575), zzeu.zza(zzan(i), zzco));
                            zzb((Object) t, i);
                            break;
                        }
                        zzfv.zza((Object) t, (long) (length & 1048575), zzdd.zza(zzfv.zzp(t, (long) (1048575 & length)), zzeu.zza(zzan(i), zzco)));
                        continue;
                    case 10:
                        zzfv.zza((Object) t, (long) (length & 1048575), zzeu.zzck());
                        zzb((Object) t, i);
                        continue;
                    case 11:
                        zzfv.zzb((Object) t, (long) (length & 1048575), zzeu.zzcl());
                        zzb((Object) t, i);
                        continue;
                    case 12:
                        i2 = zzeu.zzcm();
                        zzap = zzap(i);
                        if (zzap != null && zzap.zzam(i2) == null) {
                            obj = zzex.zza(zzda, i2, obj, zzfp);
                            break;
                        }
                        zzfv.zzb((Object) t, (long) (length & 1048575), i2);
                        zzb((Object) t, i);
                        continue;
                        break;
                    case 13:
                        zzfv.zzb((Object) t, (long) (length & 1048575), zzeu.zzcn());
                        zzb((Object) t, i);
                        continue;
                    case 14:
                        zzfv.zza((Object) t, (long) (length & 1048575), zzeu.zzco());
                        zzb((Object) t, i);
                        continue;
                    case 15:
                        zzfv.zzb((Object) t, (long) (length & 1048575), zzeu.zzcp());
                        zzb((Object) t, i);
                        continue;
                    case 16:
                        zzfv.zza((Object) t, (long) (length & 1048575), zzeu.zzcq());
                        zzb((Object) t, i);
                        continue;
                    case 17:
                        if (!zza((Object) t, i)) {
                            zzfv.zza((Object) t, (long) (length & 1048575), zzeu.zzb(zzan(i), zzco));
                            zzb((Object) t, i);
                            break;
                        }
                        zzfv.zza((Object) t, (long) (length & 1048575), zzdd.zza(zzfv.zzp(t, (long) (1048575 & length)), zzeu.zzb(zzan(i), zzco)));
                        continue;
                    case 18:
                        zzeu.zzc(this.zztx.zza(t, (long) (length & 1048575)));
                        continue;
                    case 19:
                        zzeu.zzd(this.zztx.zza(t, (long) (length & 1048575)));
                        continue;
                    case 20:
                        zzeu.zzf(this.zztx.zza(t, (long) (length & 1048575)));
                        continue;
                    case 21:
                        zzeu.zze(this.zztx.zza(t, (long) (length & 1048575)));
                        continue;
                    case 22:
                        zzeu.zzg(this.zztx.zza(t, (long) (length & 1048575)));
                        continue;
                    case 23:
                        zzeu.zzh(this.zztx.zza(t, (long) (length & 1048575)));
                        continue;
                    case 24:
                        zzeu.zzi(this.zztx.zza(t, (long) (length & 1048575)));
                        continue;
                    case 25:
                        zzeu.zzj(this.zztx.zza(t, (long) (length & 1048575)));
                        continue;
                    case 26:
                        if (!zzas(length)) {
                            zzeu.readStringList(this.zztx.zza(t, (long) (length & 1048575)));
                            break;
                        } else {
                            zzeu.zzk(this.zztx.zza(t, (long) (length & 1048575)));
                            continue;
                        }
                    case 27:
                        zzeu.zza(this.zztx.zza(t, (long) (length & 1048575)), zzan(i), zzco);
                        continue;
                    case 28:
                        zzeu.zzl(this.zztx.zza(t, (long) (length & 1048575)));
                        continue;
                    case 29:
                        zzeu.zzm(this.zztx.zza(t, (long) (length & 1048575)));
                        continue;
                    case 30:
                        zza = this.zztx.zza(t, (long) (length & 1048575));
                        zzeu.zzn(zza);
                        obj = zzex.zza(zzda, zza, zzap(i), obj, zzfp);
                        continue;
                    case 31:
                        zzeu.zzo(this.zztx.zza(t, (long) (length & 1048575)));
                        continue;
                    case 32:
                        zzeu.zzp(this.zztx.zza(t, (long) (length & 1048575)));
                        continue;
                    case 33:
                        zzeu.zzq(this.zztx.zza(t, (long) (length & 1048575)));
                        continue;
                    case 34:
                        zzeu.zzr(this.zztx.zza(t, (long) (length & 1048575)));
                        continue;
                    case 35:
                        zzeu.zzc(this.zztx.zza(t, (long) (length & 1048575)));
                        continue;
                    case 36:
                        zzeu.zzd(this.zztx.zza(t, (long) (length & 1048575)));
                        continue;
                    case 37:
                        zzeu.zzf(this.zztx.zza(t, (long) (length & 1048575)));
                        continue;
                    case 38:
                        zzeu.zze(this.zztx.zza(t, (long) (length & 1048575)));
                        continue;
                    case 39:
                        zzeu.zzg(this.zztx.zza(t, (long) (length & 1048575)));
                        continue;
                    case 40:
                        zzeu.zzh(this.zztx.zza(t, (long) (length & 1048575)));
                        continue;
                    case 41:
                        zzeu.zzi(this.zztx.zza(t, (long) (length & 1048575)));
                        continue;
                    case 42:
                        zzeu.zzj(this.zztx.zza(t, (long) (length & 1048575)));
                        continue;
                    case 43:
                        zzeu.zzm(this.zztx.zza(t, (long) (length & 1048575)));
                        continue;
                    case 44:
                        zza = this.zztx.zza(t, (long) (length & 1048575));
                        zzeu.zzn(zza);
                        obj = zzex.zza(zzda, zza, zzap(i), obj, zzfp);
                        continue;
                    case 45:
                        zzeu.zzo(this.zztx.zza(t, (long) (length & 1048575)));
                        continue;
                    case 46:
                        zzeu.zzp(this.zztx.zza(t, (long) (length & 1048575)));
                        continue;
                    case 47:
                        zzeu.zzq(this.zztx.zza(t, (long) (length & 1048575)));
                        continue;
                    case 48:
                        zzeu.zzr(this.zztx.zza(t, (long) (length & 1048575)));
                        continue;
                    case 49:
                        zzeu.zzb(this.zztx.zza(t, (long) (length & 1048575)), zzan(i), zzco);
                        continue;
                    case 50:
                        Object zzao = zzao(i);
                        long zzaq = (long) (zzaq(i) & 1048575);
                        zzp = zzfv.zzp(t, zzaq);
                        if (zzp == null) {
                            zzm = this.zzua.zzm(zzao);
                            zzfv.zza((Object) t, zzaq, zzm);
                        } else if (this.zzua.zzk(zzp)) {
                            zzm = this.zzua.zzm(zzao);
                            this.zzua.zzb(zzm, zzp);
                            zzfv.zza((Object) t, zzaq, zzm);
                        } else {
                            zzm = zzp;
                        }
                        zzeu.zza(this.zzua.zzi(zzm), this.zzua.zzn(zzao), zzco);
                        continue;
                    case 51:
                        zzfv.zza((Object) t, (long) (length & 1048575), Double.valueOf(zzeu.readDouble()));
                        zzb((Object) t, zzda, i);
                        continue;
                    case 52:
                        zzfv.zza((Object) t, (long) (length & 1048575), Float.valueOf(zzeu.readFloat()));
                        zzb((Object) t, zzda, i);
                        continue;
                    case 53:
                        zzfv.zza((Object) t, (long) (length & 1048575), Long.valueOf(zzeu.zzce()));
                        zzb((Object) t, zzda, i);
                        continue;
                    case 54:
                        zzfv.zza((Object) t, (long) (length & 1048575), Long.valueOf(zzeu.zzcd()));
                        zzb((Object) t, zzda, i);
                        continue;
                    case 55:
                        zzfv.zza((Object) t, (long) (length & 1048575), Integer.valueOf(zzeu.zzcf()));
                        zzb((Object) t, zzda, i);
                        continue;
                    case 56:
                        zzfv.zza((Object) t, (long) (length & 1048575), Long.valueOf(zzeu.zzcg()));
                        zzb((Object) t, zzda, i);
                        continue;
                    case 57:
                        zzfv.zza((Object) t, (long) (length & 1048575), Integer.valueOf(zzeu.zzch()));
                        zzb((Object) t, zzda, i);
                        continue;
                    case 58:
                        zzfv.zza((Object) t, (long) (length & 1048575), Boolean.valueOf(zzeu.zzci()));
                        zzb((Object) t, zzda, i);
                        continue;
                    case 59:
                        zza((Object) t, length, zzeu);
                        zzb((Object) t, zzda, i);
                        continue;
                    case 60:
                        if (zza((Object) t, zzda, i)) {
                            zzfv.zza((Object) t, (long) (length & 1048575), zzdd.zza(zzfv.zzp(t, (long) (1048575 & length)), zzeu.zza(zzan(i), zzco)));
                        } else {
                            zzfv.zza((Object) t, (long) (length & 1048575), zzeu.zza(zzan(i), zzco));
                            zzb((Object) t, i);
                        }
                        zzb((Object) t, zzda, i);
                        continue;
                    case 61:
                        zzfv.zza((Object) t, (long) (length & 1048575), zzeu.zzck());
                        zzb((Object) t, zzda, i);
                        continue;
                    case 62:
                        zzfv.zza((Object) t, (long) (length & 1048575), Integer.valueOf(zzeu.zzcl()));
                        zzb((Object) t, zzda, i);
                        continue;
                    case 63:
                        i2 = zzeu.zzcm();
                        zzap = zzap(i);
                        if (zzap != null && zzap.zzam(i2) == null) {
                            obj = zzex.zza(zzda, i2, obj, zzfp);
                            break;
                        }
                        zzfv.zza((Object) t, (long) (length & 1048575), Integer.valueOf(i2));
                        zzb((Object) t, zzda, i);
                        continue;
                        break;
                    case 64:
                        zzfv.zza((Object) t, (long) (length & 1048575), Integer.valueOf(zzeu.zzcn()));
                        zzb((Object) t, zzda, i);
                        continue;
                    case 65:
                        zzfv.zza((Object) t, (long) (length & 1048575), Long.valueOf(zzeu.zzco()));
                        zzb((Object) t, zzda, i);
                        continue;
                    case 66:
                        zzfv.zza((Object) t, (long) (length & 1048575), Integer.valueOf(zzeu.zzcp()));
                        zzb((Object) t, zzda, i);
                        continue;
                    case 67:
                        zzfv.zza((Object) t, (long) (length & 1048575), Long.valueOf(zzeu.zzcq()));
                        zzb((Object) t, zzda, i);
                        continue;
                    case 68:
                        zzfv.zza((Object) t, (long) (length & 1048575), zzeu.zzb(zzan(i), zzco));
                        zzb((Object) t, zzda, i);
                        continue;
                    default:
                        if (obj == null) {
                            try {
                                zzm = zzfp.zzfy();
                            } catch (zzdi e) {
                                break;
                            }
                        }
                        zzm = obj;
                        try {
                            if (zzfp.zza(zzm, zzeu)) {
                                obj = zzm;
                                continue;
                            } else {
                                for (i3 = this.zztu; i3 < this.zztv; i3++) {
                                    zzm = zza((Object) t, this.zztt[i3], zzm, zzfp);
                                }
                                if (zzm != null) {
                                    zzfp.zzf(t, zzm);
                                    return;
                                }
                                return;
                            }
                        } catch (zzdi e2) {
                            obj = zzm;
                            break;
                        }
                }
                try {
                    zzfp.zza(zzeu);
                    zzm = obj == null ? zzfp.zzs(t) : obj;
                    if (zzfp.zza(zzm, zzeu)) {
                        obj = zzm;
                    } else {
                        for (i3 = this.zztu; i3 < this.zztv; i3++) {
                            zzm = zza((Object) t, this.zztt[i3], zzm, zzfp);
                        }
                        if (zzm != null) {
                            zzfp.zzf(t, zzm);
                            return;
                        }
                        return;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    zzm = obj;
                }
            } else if (zzda == Integer.MAX_VALUE) {
                for (i3 = this.zztu; i3 < this.zztv; i3++) {
                    obj = zza((Object) t, this.zztt[i3], obj, zzfp);
                }
                if (obj != null) {
                    zzfp.zzf(t, obj);
                    return;
                }
                return;
            } else {
                zzp = !this.zztp ? null : zzcp.zza(zzco, this.zzto, zzda);
                if (zzp != null) {
                    if (zzcs == null) {
                        zzcs = zzcp.zzd(t);
                    }
                    obj = zzcp.zza(zzeu, zzp, zzco, zzcs, obj, zzfp);
                } else {
                    zzfp.zza(zzeu);
                    zzm = obj == null ? zzfp.zzs(t) : obj;
                    try {
                        if (zzfp.zza(zzm, zzeu)) {
                            obj = zzm;
                        } else {
                            for (i3 = this.zztu; i3 < this.zztv; i3++) {
                                zzm = zza((Object) t, this.zztt[i3], zzm, zzfp);
                            }
                            if (zzm != null) {
                                zzfp.zzf(t, zzm);
                                return;
                            }
                            return;
                        }
                    } catch (Throwable th22) {
                        th = th22;
                    }
                }
            }
        }
        for (i3 = this.zztu; i3 < this.zztv; i3++) {
            zzm = zza((Object) t, this.zztt[i3], zzm, zzfp);
        }
        if (zzm != null) {
            zzfp.zzf(t, zzm);
        }
        throw th;
    }

    public final void zza(T t, zzgj zzgj) {
        Iterator it;
        Entry entry;
        zzcs zzc;
        int length;
        int zzaq;
        int i;
        Entry entry2;
        if (zzgj.zzdf() == zze.zzrq) {
            zza(this.zzty, (Object) t, zzgj);
            it = null;
            entry = null;
            if (this.zztp) {
                zzc = this.zztz.zzc(t);
                if (!zzc.isEmpty()) {
                    it = zzc.descendingIterator();
                    entry = (Entry) it.next();
                }
            }
            length = this.zztk.length - 3;
            while (length >= 0) {
                zzaq = zzaq(length);
                i = this.zztk[length];
                entry2 = entry;
                while (entry2 != null && this.zztz.zza(entry2) > i) {
                    this.zztz.zza(zzgj, entry2);
                    entry2 = it.hasNext() ? (Entry) it.next() : null;
                }
                switch ((267386880 & zzaq) >>> 20) {
                    case 0:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzgj.zza(i, zzfv.zzo(t, (long) (1048575 & zzaq)));
                        break;
                    case 1:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzgj.zza(i, zzfv.zzn(t, (long) (1048575 & zzaq)));
                        break;
                    case 2:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzgj.zzi(i, zzfv.zzl(t, (long) (1048575 & zzaq)));
                        break;
                    case 3:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzgj.zza(i, zzfv.zzl(t, (long) (1048575 & zzaq)));
                        break;
                    case 4:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzgj.zzc(i, zzfv.zzk(t, (long) (1048575 & zzaq)));
                        break;
                    case 5:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzgj.zzc(i, zzfv.zzl(t, (long) (1048575 & zzaq)));
                        break;
                    case 6:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzgj.zzf(i, zzfv.zzk(t, (long) (1048575 & zzaq)));
                        break;
                    case 7:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzgj.zzb(i, zzfv.zzm(t, (long) (1048575 & zzaq)));
                        break;
                    case 8:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zza(i, zzfv.zzp(t, (long) (1048575 & zzaq)), zzgj);
                        break;
                    case 9:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzgj.zza(i, zzfv.zzp(t, (long) (1048575 & zzaq)), zzan(length));
                        break;
                    case 10:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzgj.zza(i, (zzbu) zzfv.zzp(t, (long) (1048575 & zzaq)));
                        break;
                    case 11:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzgj.zzd(i, zzfv.zzk(t, (long) (1048575 & zzaq)));
                        break;
                    case 12:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzgj.zzn(i, zzfv.zzk(t, (long) (1048575 & zzaq)));
                        break;
                    case 13:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzgj.zzm(i, zzfv.zzk(t, (long) (1048575 & zzaq)));
                        break;
                    case 14:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzgj.zzj(i, zzfv.zzl(t, (long) (1048575 & zzaq)));
                        break;
                    case 15:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzgj.zze(i, zzfv.zzk(t, (long) (1048575 & zzaq)));
                        break;
                    case 16:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzgj.zzb(i, zzfv.zzl(t, (long) (1048575 & zzaq)));
                        break;
                    case 17:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzgj.zzb(i, zzfv.zzp(t, (long) (1048575 & zzaq)), zzan(length));
                        break;
                    case 18:
                        zzex.zza(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & zzaq)), zzgj, false);
                        break;
                    case 19:
                        zzex.zzb(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & zzaq)), zzgj, false);
                        break;
                    case 20:
                        zzex.zzc(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & zzaq)), zzgj, false);
                        break;
                    case 21:
                        zzex.zzd(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & zzaq)), zzgj, false);
                        break;
                    case 22:
                        zzex.zzh(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & zzaq)), zzgj, false);
                        break;
                    case 23:
                        zzex.zzf(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & zzaq)), zzgj, false);
                        break;
                    case 24:
                        zzex.zzk(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & zzaq)), zzgj, false);
                        break;
                    case 25:
                        zzex.zzn(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & zzaq)), zzgj, false);
                        break;
                    case 26:
                        zzex.zza(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & zzaq)), zzgj);
                        break;
                    case 27:
                        zzex.zza(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & zzaq)), zzgj, zzan(length));
                        break;
                    case 28:
                        zzex.zzb(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & zzaq)), zzgj);
                        break;
                    case 29:
                        zzex.zzi(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & zzaq)), zzgj, false);
                        break;
                    case 30:
                        zzex.zzm(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & zzaq)), zzgj, false);
                        break;
                    case 31:
                        zzex.zzl(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & zzaq)), zzgj, false);
                        break;
                    case 32:
                        zzex.zzg(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & zzaq)), zzgj, false);
                        break;
                    case 33:
                        zzex.zzj(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & zzaq)), zzgj, false);
                        break;
                    case 34:
                        zzex.zze(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & zzaq)), zzgj, false);
                        break;
                    case 35:
                        zzex.zza(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & zzaq)), zzgj, true);
                        break;
                    case 36:
                        zzex.zzb(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & zzaq)), zzgj, true);
                        break;
                    case 37:
                        zzex.zzc(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & zzaq)), zzgj, true);
                        break;
                    case 38:
                        zzex.zzd(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & zzaq)), zzgj, true);
                        break;
                    case 39:
                        zzex.zzh(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & zzaq)), zzgj, true);
                        break;
                    case 40:
                        zzex.zzf(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & zzaq)), zzgj, true);
                        break;
                    case 41:
                        zzex.zzk(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & zzaq)), zzgj, true);
                        break;
                    case 42:
                        zzex.zzn(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & zzaq)), zzgj, true);
                        break;
                    case 43:
                        zzex.zzi(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & zzaq)), zzgj, true);
                        break;
                    case 44:
                        zzex.zzm(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & zzaq)), zzgj, true);
                        break;
                    case 45:
                        zzex.zzl(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & zzaq)), zzgj, true);
                        break;
                    case 46:
                        zzex.zzg(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & zzaq)), zzgj, true);
                        break;
                    case 47:
                        zzex.zzj(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & zzaq)), zzgj, true);
                        break;
                    case 48:
                        zzex.zze(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & zzaq)), zzgj, true);
                        break;
                    case 49:
                        zzex.zzb(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & zzaq)), zzgj, zzan(length));
                        break;
                    case 50:
                        zza(zzgj, i, zzfv.zzp(t, (long) (1048575 & zzaq)), length);
                        break;
                    case 51:
                        if (!zza((Object) t, i, length)) {
                            break;
                        }
                        zzgj.zza(i, zzf(t, (long) (1048575 & zzaq)));
                        break;
                    case 52:
                        if (!zza((Object) t, i, length)) {
                            break;
                        }
                        zzgj.zza(i, zzg(t, (long) (1048575 & zzaq)));
                        break;
                    case 53:
                        if (!zza((Object) t, i, length)) {
                            break;
                        }
                        zzgj.zzi(i, zzi(t, (long) (1048575 & zzaq)));
                        break;
                    case 54:
                        if (!zza((Object) t, i, length)) {
                            break;
                        }
                        zzgj.zza(i, zzi(t, (long) (1048575 & zzaq)));
                        break;
                    case 55:
                        if (!zza((Object) t, i, length)) {
                            break;
                        }
                        zzgj.zzc(i, zzh(t, (long) (1048575 & zzaq)));
                        break;
                    case 56:
                        if (!zza((Object) t, i, length)) {
                            break;
                        }
                        zzgj.zzc(i, zzi(t, (long) (1048575 & zzaq)));
                        break;
                    case 57:
                        if (!zza((Object) t, i, length)) {
                            break;
                        }
                        zzgj.zzf(i, zzh(t, (long) (1048575 & zzaq)));
                        break;
                    case 58:
                        if (!zza((Object) t, i, length)) {
                            break;
                        }
                        zzgj.zzb(i, zzj(t, (long) (1048575 & zzaq)));
                        break;
                    case 59:
                        if (!zza((Object) t, i, length)) {
                            break;
                        }
                        zza(i, zzfv.zzp(t, (long) (1048575 & zzaq)), zzgj);
                        break;
                    case 60:
                        if (!zza((Object) t, i, length)) {
                            break;
                        }
                        zzgj.zza(i, zzfv.zzp(t, (long) (1048575 & zzaq)), zzan(length));
                        break;
                    case 61:
                        if (!zza((Object) t, i, length)) {
                            break;
                        }
                        zzgj.zza(i, (zzbu) zzfv.zzp(t, (long) (1048575 & zzaq)));
                        break;
                    case 62:
                        if (!zza((Object) t, i, length)) {
                            break;
                        }
                        zzgj.zzd(i, zzh(t, (long) (1048575 & zzaq)));
                        break;
                    case 63:
                        if (!zza((Object) t, i, length)) {
                            break;
                        }
                        zzgj.zzn(i, zzh(t, (long) (1048575 & zzaq)));
                        break;
                    case 64:
                        if (!zza((Object) t, i, length)) {
                            break;
                        }
                        zzgj.zzm(i, zzh(t, (long) (1048575 & zzaq)));
                        break;
                    case 65:
                        if (!zza((Object) t, i, length)) {
                            break;
                        }
                        zzgj.zzj(i, zzi(t, (long) (1048575 & zzaq)));
                        break;
                    case 66:
                        if (!zza((Object) t, i, length)) {
                            break;
                        }
                        zzgj.zze(i, zzh(t, (long) (1048575 & zzaq)));
                        break;
                    case 67:
                        if (!zza((Object) t, i, length)) {
                            break;
                        }
                        zzgj.zzb(i, zzi(t, (long) (1048575 & zzaq)));
                        break;
                    case 68:
                        if (!zza((Object) t, i, length)) {
                            break;
                        }
                        zzgj.zzb(i, zzfv.zzp(t, (long) (1048575 & zzaq)), zzan(length));
                        break;
                    default:
                        break;
                }
                length -= 3;
                entry = entry2;
            }
            while (entry != null) {
                this.zztz.zza(zzgj, entry);
                entry = it.hasNext() ? (Entry) it.next() : null;
            }
        } else if (this.zztr) {
            it = null;
            entry = null;
            if (this.zztp) {
                zzc = this.zztz.zzc(t);
                if (!zzc.isEmpty()) {
                    it = zzc.iterator();
                    entry = (Entry) it.next();
                }
            }
            zzaq = this.zztk.length;
            length = 0;
            while (length < zzaq) {
                i = zzaq(length);
                int i2 = this.zztk[length];
                entry2 = entry;
                while (entry2 != null && this.zztz.zza(entry2) <= i2) {
                    this.zztz.zza(zzgj, entry2);
                    entry2 = it.hasNext() ? (Entry) it.next() : null;
                }
                switch ((267386880 & i) >>> 20) {
                    case 0:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzgj.zza(i2, zzfv.zzo(t, (long) (1048575 & i)));
                        break;
                    case 1:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzgj.zza(i2, zzfv.zzn(t, (long) (1048575 & i)));
                        break;
                    case 2:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzgj.zzi(i2, zzfv.zzl(t, (long) (1048575 & i)));
                        break;
                    case 3:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzgj.zza(i2, zzfv.zzl(t, (long) (1048575 & i)));
                        break;
                    case 4:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzgj.zzc(i2, zzfv.zzk(t, (long) (1048575 & i)));
                        break;
                    case 5:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzgj.zzc(i2, zzfv.zzl(t, (long) (1048575 & i)));
                        break;
                    case 6:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzgj.zzf(i2, zzfv.zzk(t, (long) (1048575 & i)));
                        break;
                    case 7:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzgj.zzb(i2, zzfv.zzm(t, (long) (1048575 & i)));
                        break;
                    case 8:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zza(i2, zzfv.zzp(t, (long) (1048575 & i)), zzgj);
                        break;
                    case 9:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzgj.zza(i2, zzfv.zzp(t, (long) (1048575 & i)), zzan(length));
                        break;
                    case 10:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzgj.zza(i2, (zzbu) zzfv.zzp(t, (long) (1048575 & i)));
                        break;
                    case 11:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzgj.zzd(i2, zzfv.zzk(t, (long) (1048575 & i)));
                        break;
                    case 12:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzgj.zzn(i2, zzfv.zzk(t, (long) (1048575 & i)));
                        break;
                    case 13:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzgj.zzm(i2, zzfv.zzk(t, (long) (1048575 & i)));
                        break;
                    case 14:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzgj.zzj(i2, zzfv.zzl(t, (long) (1048575 & i)));
                        break;
                    case 15:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzgj.zze(i2, zzfv.zzk(t, (long) (1048575 & i)));
                        break;
                    case 16:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzgj.zzb(i2, zzfv.zzl(t, (long) (1048575 & i)));
                        break;
                    case 17:
                        if (!zza((Object) t, length)) {
                            break;
                        }
                        zzgj.zzb(i2, zzfv.zzp(t, (long) (1048575 & i)), zzan(length));
                        break;
                    case 18:
                        zzex.zza(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & i)), zzgj, false);
                        break;
                    case 19:
                        zzex.zzb(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & i)), zzgj, false);
                        break;
                    case 20:
                        zzex.zzc(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & i)), zzgj, false);
                        break;
                    case 21:
                        zzex.zzd(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & i)), zzgj, false);
                        break;
                    case 22:
                        zzex.zzh(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & i)), zzgj, false);
                        break;
                    case 23:
                        zzex.zzf(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & i)), zzgj, false);
                        break;
                    case 24:
                        zzex.zzk(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & i)), zzgj, false);
                        break;
                    case 25:
                        zzex.zzn(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & i)), zzgj, false);
                        break;
                    case 26:
                        zzex.zza(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & i)), zzgj);
                        break;
                    case 27:
                        zzex.zza(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & i)), zzgj, zzan(length));
                        break;
                    case 28:
                        zzex.zzb(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & i)), zzgj);
                        break;
                    case 29:
                        zzex.zzi(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & i)), zzgj, false);
                        break;
                    case 30:
                        zzex.zzm(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & i)), zzgj, false);
                        break;
                    case 31:
                        zzex.zzl(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & i)), zzgj, false);
                        break;
                    case 32:
                        zzex.zzg(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & i)), zzgj, false);
                        break;
                    case 33:
                        zzex.zzj(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & i)), zzgj, false);
                        break;
                    case 34:
                        zzex.zze(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & i)), zzgj, false);
                        break;
                    case 35:
                        zzex.zza(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & i)), zzgj, true);
                        break;
                    case 36:
                        zzex.zzb(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & i)), zzgj, true);
                        break;
                    case 37:
                        zzex.zzc(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & i)), zzgj, true);
                        break;
                    case 38:
                        zzex.zzd(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & i)), zzgj, true);
                        break;
                    case 39:
                        zzex.zzh(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & i)), zzgj, true);
                        break;
                    case 40:
                        zzex.zzf(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & i)), zzgj, true);
                        break;
                    case 41:
                        zzex.zzk(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & i)), zzgj, true);
                        break;
                    case 42:
                        zzex.zzn(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & i)), zzgj, true);
                        break;
                    case 43:
                        zzex.zzi(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & i)), zzgj, true);
                        break;
                    case 44:
                        zzex.zzm(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & i)), zzgj, true);
                        break;
                    case 45:
                        zzex.zzl(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & i)), zzgj, true);
                        break;
                    case 46:
                        zzex.zzg(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & i)), zzgj, true);
                        break;
                    case 47:
                        zzex.zzj(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & i)), zzgj, true);
                        break;
                    case 48:
                        zzex.zze(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & i)), zzgj, true);
                        break;
                    case 49:
                        zzex.zzb(this.zztk[length], (List) zzfv.zzp(t, (long) (1048575 & i)), zzgj, zzan(length));
                        break;
                    case 50:
                        zza(zzgj, i2, zzfv.zzp(t, (long) (1048575 & i)), length);
                        break;
                    case 51:
                        if (!zza((Object) t, i2, length)) {
                            break;
                        }
                        zzgj.zza(i2, zzf(t, (long) (1048575 & i)));
                        break;
                    case 52:
                        if (!zza((Object) t, i2, length)) {
                            break;
                        }
                        zzgj.zza(i2, zzg(t, (long) (1048575 & i)));
                        break;
                    case 53:
                        if (!zza((Object) t, i2, length)) {
                            break;
                        }
                        zzgj.zzi(i2, zzi(t, (long) (1048575 & i)));
                        break;
                    case 54:
                        if (!zza((Object) t, i2, length)) {
                            break;
                        }
                        zzgj.zza(i2, zzi(t, (long) (1048575 & i)));
                        break;
                    case 55:
                        if (!zza((Object) t, i2, length)) {
                            break;
                        }
                        zzgj.zzc(i2, zzh(t, (long) (1048575 & i)));
                        break;
                    case 56:
                        if (!zza((Object) t, i2, length)) {
                            break;
                        }
                        zzgj.zzc(i2, zzi(t, (long) (1048575 & i)));
                        break;
                    case 57:
                        if (!zza((Object) t, i2, length)) {
                            break;
                        }
                        zzgj.zzf(i2, zzh(t, (long) (1048575 & i)));
                        break;
                    case 58:
                        if (!zza((Object) t, i2, length)) {
                            break;
                        }
                        zzgj.zzb(i2, zzj(t, (long) (1048575 & i)));
                        break;
                    case 59:
                        if (!zza((Object) t, i2, length)) {
                            break;
                        }
                        zza(i2, zzfv.zzp(t, (long) (1048575 & i)), zzgj);
                        break;
                    case 60:
                        if (!zza((Object) t, i2, length)) {
                            break;
                        }
                        zzgj.zza(i2, zzfv.zzp(t, (long) (1048575 & i)), zzan(length));
                        break;
                    case 61:
                        if (!zza((Object) t, i2, length)) {
                            break;
                        }
                        zzgj.zza(i2, (zzbu) zzfv.zzp(t, (long) (1048575 & i)));
                        break;
                    case 62:
                        if (!zza((Object) t, i2, length)) {
                            break;
                        }
                        zzgj.zzd(i2, zzh(t, (long) (1048575 & i)));
                        break;
                    case 63:
                        if (!zza((Object) t, i2, length)) {
                            break;
                        }
                        zzgj.zzn(i2, zzh(t, (long) (1048575 & i)));
                        break;
                    case 64:
                        if (!zza((Object) t, i2, length)) {
                            break;
                        }
                        zzgj.zzm(i2, zzh(t, (long) (1048575 & i)));
                        break;
                    case 65:
                        if (!zza((Object) t, i2, length)) {
                            break;
                        }
                        zzgj.zzj(i2, zzi(t, (long) (1048575 & i)));
                        break;
                    case 66:
                        if (!zza((Object) t, i2, length)) {
                            break;
                        }
                        zzgj.zze(i2, zzh(t, (long) (1048575 & i)));
                        break;
                    case 67:
                        if (!zza((Object) t, i2, length)) {
                            break;
                        }
                        zzgj.zzb(i2, zzi(t, (long) (1048575 & i)));
                        break;
                    case 68:
                        if (!zza((Object) t, i2, length)) {
                            break;
                        }
                        zzgj.zzb(i2, zzfv.zzp(t, (long) (1048575 & i)), zzan(length));
                        break;
                    default:
                        break;
                }
                length += 3;
                entry = entry2;
            }
            while (entry != null) {
                this.zztz.zza(zzgj, entry);
                entry = it.hasNext() ? (Entry) it.next() : null;
            }
            zza(this.zzty, (Object) t, zzgj);
        } else {
            zzb((Object) t, zzgj);
        }
    }

    public final void zzc(T t, T t2) {
        if (t2 == null) {
            throw new NullPointerException();
        }
        for (int i = 0; i < this.zztk.length; i += 3) {
            int zzaq = zzaq(i);
            long j = (long) (1048575 & zzaq);
            int i2 = this.zztk[i];
            switch ((zzaq & 267386880) >>> 20) {
                case 0:
                    if (!zza((Object) t2, i)) {
                        break;
                    }
                    zzfv.zza((Object) t, j, zzfv.zzo(t2, j));
                    zzb((Object) t, i);
                    break;
                case 1:
                    if (!zza((Object) t2, i)) {
                        break;
                    }
                    zzfv.zza((Object) t, j, zzfv.zzn(t2, j));
                    zzb((Object) t, i);
                    break;
                case 2:
                    if (!zza((Object) t2, i)) {
                        break;
                    }
                    zzfv.zza((Object) t, j, zzfv.zzl(t2, j));
                    zzb((Object) t, i);
                    break;
                case 3:
                    if (!zza((Object) t2, i)) {
                        break;
                    }
                    zzfv.zza((Object) t, j, zzfv.zzl(t2, j));
                    zzb((Object) t, i);
                    break;
                case 4:
                    if (!zza((Object) t2, i)) {
                        break;
                    }
                    zzfv.zzb((Object) t, j, zzfv.zzk(t2, j));
                    zzb((Object) t, i);
                    break;
                case 5:
                    if (!zza((Object) t2, i)) {
                        break;
                    }
                    zzfv.zza((Object) t, j, zzfv.zzl(t2, j));
                    zzb((Object) t, i);
                    break;
                case 6:
                    if (!zza((Object) t2, i)) {
                        break;
                    }
                    zzfv.zzb((Object) t, j, zzfv.zzk(t2, j));
                    zzb((Object) t, i);
                    break;
                case 7:
                    if (!zza((Object) t2, i)) {
                        break;
                    }
                    zzfv.zza((Object) t, j, zzfv.zzm(t2, j));
                    zzb((Object) t, i);
                    break;
                case 8:
                    if (!zza((Object) t2, i)) {
                        break;
                    }
                    zzfv.zza((Object) t, j, zzfv.zzp(t2, j));
                    zzb((Object) t, i);
                    break;
                case 9:
                    zza((Object) t, (Object) t2, i);
                    break;
                case 10:
                    if (!zza((Object) t2, i)) {
                        break;
                    }
                    zzfv.zza((Object) t, j, zzfv.zzp(t2, j));
                    zzb((Object) t, i);
                    break;
                case 11:
                    if (!zza((Object) t2, i)) {
                        break;
                    }
                    zzfv.zzb((Object) t, j, zzfv.zzk(t2, j));
                    zzb((Object) t, i);
                    break;
                case 12:
                    if (!zza((Object) t2, i)) {
                        break;
                    }
                    zzfv.zzb((Object) t, j, zzfv.zzk(t2, j));
                    zzb((Object) t, i);
                    break;
                case 13:
                    if (!zza((Object) t2, i)) {
                        break;
                    }
                    zzfv.zzb((Object) t, j, zzfv.zzk(t2, j));
                    zzb((Object) t, i);
                    break;
                case 14:
                    if (!zza((Object) t2, i)) {
                        break;
                    }
                    zzfv.zza((Object) t, j, zzfv.zzl(t2, j));
                    zzb((Object) t, i);
                    break;
                case 15:
                    if (!zza((Object) t2, i)) {
                        break;
                    }
                    zzfv.zzb((Object) t, j, zzfv.zzk(t2, j));
                    zzb((Object) t, i);
                    break;
                case 16:
                    if (!zza((Object) t2, i)) {
                        break;
                    }
                    zzfv.zza((Object) t, j, zzfv.zzl(t2, j));
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
                    this.zztx.zza(t, t2, j);
                    break;
                case 50:
                    zzex.zza(this.zzua, (Object) t, (Object) t2, j);
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
                    zzfv.zza((Object) t, j, zzfv.zzp(t2, j));
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
                    zzfv.zza((Object) t, j, zzfv.zzp(t2, j));
                    zzb((Object) t, i2, i);
                    break;
                case 68:
                    zzb((Object) t, (Object) t2, i);
                    break;
                default:
                    break;
            }
        }
        if (!this.zztr) {
            zzex.zza(this.zzty, (Object) t, (Object) t2);
            if (this.zztp) {
                zzex.zza(this.zztz, (Object) t, (Object) t2);
            }
        }
    }

    public final void zze(T t) {
        int i;
        for (i = this.zztu; i < this.zztv; i++) {
            long zzaq = (long) (zzaq(this.zztt[i]) & 1048575);
            Object zzp = zzfv.zzp(t, zzaq);
            if (zzp != null) {
                zzfv.zza((Object) t, zzaq, this.zzua.zzl(zzp));
            }
        }
        int length = this.zztt.length;
        for (i = this.zztv; i < length; i++) {
            this.zztx.zzb(t, (long) this.zztt[i]);
        }
        this.zzty.zze(t);
        if (this.zztp) {
            this.zztz.zze((Object) t);
        }
    }

    public final int zzo(T t) {
        int i;
        int i2;
        int zzaq;
        int i3;
        int i4;
        int i5;
        Object zzp;
        if (this.zztr) {
            Unsafe unsafe = zztj;
            i = 0;
            for (i2 = 0; i2 < this.zztk.length; i2 += 3) {
                zzaq = zzaq(i2);
                i3 = (267386880 & zzaq) >>> 20;
                i4 = this.zztk[i2];
                long j = (long) (zzaq & 1048575);
                i5 = (i3 < zzcv.DOUBLE_LIST_PACKED.id() || i3 > zzcv.SINT64_LIST_PACKED.id()) ? 0 : this.zztk[i2 + 2] & 1048575;
                switch (i3) {
                    case 0:
                        if (!zza((Object) t, i2)) {
                            break;
                        }
                        i += zzci.zzb(i4, 0.0d);
                        break;
                    case 1:
                        if (!zza((Object) t, i2)) {
                            break;
                        }
                        i += zzci.zzb(i4, (float) BitmapDescriptorFactory.HUE_RED);
                        break;
                    case 2:
                        if (!zza((Object) t, i2)) {
                            break;
                        }
                        i += zzci.zzd(i4, zzfv.zzl(t, j));
                        break;
                    case 3:
                        if (!zza((Object) t, i2)) {
                            break;
                        }
                        i += zzci.zze(i4, zzfv.zzl(t, j));
                        break;
                    case 4:
                        if (!zza((Object) t, i2)) {
                            break;
                        }
                        i += zzci.zzg(i4, zzfv.zzk(t, j));
                        break;
                    case 5:
                        if (!zza((Object) t, i2)) {
                            break;
                        }
                        i += zzci.zzg(i4, 0);
                        break;
                    case 6:
                        if (!zza((Object) t, i2)) {
                            break;
                        }
                        i += zzci.zzj(i4, 0);
                        break;
                    case 7:
                        if (!zza((Object) t, i2)) {
                            break;
                        }
                        i += zzci.zzc(i4, true);
                        break;
                    case 8:
                        if (!zza((Object) t, i2)) {
                            break;
                        }
                        zzp = zzfv.zzp(t, j);
                        if (!(zzp instanceof zzbu)) {
                            i += zzci.zzb(i4, (String) zzp);
                            break;
                        }
                        i += zzci.zzc(i4, (zzbu) zzp);
                        break;
                    case 9:
                        if (!zza((Object) t, i2)) {
                            break;
                        }
                        i += zzex.zzc(i4, zzfv.zzp(t, j), zzan(i2));
                        break;
                    case 10:
                        if (!zza((Object) t, i2)) {
                            break;
                        }
                        i += zzci.zzc(i4, (zzbu) zzfv.zzp(t, j));
                        break;
                    case 11:
                        if (!zza((Object) t, i2)) {
                            break;
                        }
                        i += zzci.zzh(i4, zzfv.zzk(t, j));
                        break;
                    case 12:
                        if (!zza((Object) t, i2)) {
                            break;
                        }
                        i += zzci.zzl(i4, zzfv.zzk(t, j));
                        break;
                    case 13:
                        if (!zza((Object) t, i2)) {
                            break;
                        }
                        i += zzci.zzk(i4, 0);
                        break;
                    case 14:
                        if (!zza((Object) t, i2)) {
                            break;
                        }
                        i += zzci.zzh(i4, 0);
                        break;
                    case 15:
                        if (!zza((Object) t, i2)) {
                            break;
                        }
                        i += zzci.zzi(i4, zzfv.zzk(t, j));
                        break;
                    case 16:
                        if (!zza((Object) t, i2)) {
                            break;
                        }
                        i += zzci.zzf(i4, zzfv.zzl(t, j));
                        break;
                    case 17:
                        if (!zza((Object) t, i2)) {
                            break;
                        }
                        i += zzci.zzc(i4, (zzeh) zzfv.zzp(t, j), zzan(i2));
                        break;
                    case 18:
                        i += zzex.zzw(i4, zze(t, j), false);
                        break;
                    case 19:
                        i += zzex.zzv(i4, zze(t, j), false);
                        break;
                    case 20:
                        i += zzex.zzo(i4, zze(t, j), false);
                        break;
                    case 21:
                        i += zzex.zzp(i4, zze(t, j), false);
                        break;
                    case 22:
                        i += zzex.zzs(i4, zze(t, j), false);
                        break;
                    case 23:
                        i += zzex.zzw(i4, zze(t, j), false);
                        break;
                    case 24:
                        i += zzex.zzv(i4, zze(t, j), false);
                        break;
                    case 25:
                        i += zzex.zzx(i4, zze(t, j), false);
                        break;
                    case 26:
                        i += zzex.zzc(i4, zze(t, j));
                        break;
                    case 27:
                        i += zzex.zzc(i4, zze(t, j), zzan(i2));
                        break;
                    case 28:
                        i += zzex.zzd(i4, zze(t, j));
                        break;
                    case 29:
                        i += zzex.zzt(i4, zze(t, j), false);
                        break;
                    case 30:
                        i += zzex.zzr(i4, zze(t, j), false);
                        break;
                    case 31:
                        i += zzex.zzv(i4, zze(t, j), false);
                        break;
                    case 32:
                        i += zzex.zzw(i4, zze(t, j), false);
                        break;
                    case 33:
                        i += zzex.zzu(i4, zze(t, j), false);
                        break;
                    case 34:
                        i += zzex.zzq(i4, zze(t, j), false);
                        break;
                    case 35:
                        zzaq = zzex.zzaa((List) unsafe.getObject(t, j));
                        if (zzaq > 0) {
                            if (this.zzts) {
                                unsafe.putInt(t, (long) i5, zzaq);
                            }
                            i += zzaq + (zzci.zzaa(i4) + zzci.zzac(zzaq));
                            break;
                        }
                        break;
                    case 36:
                        zzaq = zzex.zzz((List) unsafe.getObject(t, j));
                        if (zzaq > 0) {
                            if (this.zzts) {
                                unsafe.putInt(t, (long) i5, zzaq);
                            }
                            i += zzaq + (zzci.zzaa(i4) + zzci.zzac(zzaq));
                            break;
                        }
                        break;
                    case 37:
                        zzaq = zzex.zzs((List) unsafe.getObject(t, j));
                        if (zzaq > 0) {
                            if (this.zzts) {
                                unsafe.putInt(t, (long) i5, zzaq);
                            }
                            i += zzaq + (zzci.zzaa(i4) + zzci.zzac(zzaq));
                            break;
                        }
                        break;
                    case 38:
                        zzaq = zzex.zzt((List) unsafe.getObject(t, j));
                        if (zzaq > 0) {
                            if (this.zzts) {
                                unsafe.putInt(t, (long) i5, zzaq);
                            }
                            i += zzaq + (zzci.zzaa(i4) + zzci.zzac(zzaq));
                            break;
                        }
                        break;
                    case 39:
                        zzaq = zzex.zzw((List) unsafe.getObject(t, j));
                        if (zzaq > 0) {
                            if (this.zzts) {
                                unsafe.putInt(t, (long) i5, zzaq);
                            }
                            i += zzaq + (zzci.zzaa(i4) + zzci.zzac(zzaq));
                            break;
                        }
                        break;
                    case 40:
                        zzaq = zzex.zzaa((List) unsafe.getObject(t, j));
                        if (zzaq > 0) {
                            if (this.zzts) {
                                unsafe.putInt(t, (long) i5, zzaq);
                            }
                            i += zzaq + (zzci.zzaa(i4) + zzci.zzac(zzaq));
                            break;
                        }
                        break;
                    case 41:
                        zzaq = zzex.zzz((List) unsafe.getObject(t, j));
                        if (zzaq > 0) {
                            if (this.zzts) {
                                unsafe.putInt(t, (long) i5, zzaq);
                            }
                            i += zzaq + (zzci.zzaa(i4) + zzci.zzac(zzaq));
                            break;
                        }
                        break;
                    case 42:
                        zzaq = zzex.zzab((List) unsafe.getObject(t, j));
                        if (zzaq > 0) {
                            if (this.zzts) {
                                unsafe.putInt(t, (long) i5, zzaq);
                            }
                            i += zzaq + (zzci.zzaa(i4) + zzci.zzac(zzaq));
                            break;
                        }
                        break;
                    case 43:
                        zzaq = zzex.zzx((List) unsafe.getObject(t, j));
                        if (zzaq > 0) {
                            if (this.zzts) {
                                unsafe.putInt(t, (long) i5, zzaq);
                            }
                            i += zzaq + (zzci.zzaa(i4) + zzci.zzac(zzaq));
                            break;
                        }
                        break;
                    case 44:
                        zzaq = zzex.zzv((List) unsafe.getObject(t, j));
                        if (zzaq > 0) {
                            if (this.zzts) {
                                unsafe.putInt(t, (long) i5, zzaq);
                            }
                            i += zzaq + (zzci.zzaa(i4) + zzci.zzac(zzaq));
                            break;
                        }
                        break;
                    case 45:
                        zzaq = zzex.zzz((List) unsafe.getObject(t, j));
                        if (zzaq > 0) {
                            if (this.zzts) {
                                unsafe.putInt(t, (long) i5, zzaq);
                            }
                            i += zzaq + (zzci.zzaa(i4) + zzci.zzac(zzaq));
                            break;
                        }
                        break;
                    case 46:
                        zzaq = zzex.zzaa((List) unsafe.getObject(t, j));
                        if (zzaq > 0) {
                            if (this.zzts) {
                                unsafe.putInt(t, (long) i5, zzaq);
                            }
                            i += zzaq + (zzci.zzaa(i4) + zzci.zzac(zzaq));
                            break;
                        }
                        break;
                    case 47:
                        zzaq = zzex.zzy((List) unsafe.getObject(t, j));
                        if (zzaq > 0) {
                            if (this.zzts) {
                                unsafe.putInt(t, (long) i5, zzaq);
                            }
                            i += zzaq + (zzci.zzaa(i4) + zzci.zzac(zzaq));
                            break;
                        }
                        break;
                    case 48:
                        zzaq = zzex.zzu((List) unsafe.getObject(t, j));
                        if (zzaq > 0) {
                            if (this.zzts) {
                                unsafe.putInt(t, (long) i5, zzaq);
                            }
                            i += zzaq + (zzci.zzaa(i4) + zzci.zzac(zzaq));
                            break;
                        }
                        break;
                    case 49:
                        i += zzex.zzd(i4, zze(t, j), zzan(i2));
                        break;
                    case 50:
                        i += this.zzua.zzb(i4, zzfv.zzp(t, j), zzao(i2));
                        break;
                    case 51:
                        if (!zza((Object) t, i4, i2)) {
                            break;
                        }
                        i += zzci.zzb(i4, 0.0d);
                        break;
                    case 52:
                        if (!zza((Object) t, i4, i2)) {
                            break;
                        }
                        i += zzci.zzb(i4, (float) BitmapDescriptorFactory.HUE_RED);
                        break;
                    case 53:
                        if (!zza((Object) t, i4, i2)) {
                            break;
                        }
                        i += zzci.zzd(i4, zzi(t, j));
                        break;
                    case 54:
                        if (!zza((Object) t, i4, i2)) {
                            break;
                        }
                        i += zzci.zze(i4, zzi(t, j));
                        break;
                    case 55:
                        if (!zza((Object) t, i4, i2)) {
                            break;
                        }
                        i += zzci.zzg(i4, zzh(t, j));
                        break;
                    case 56:
                        if (!zza((Object) t, i4, i2)) {
                            break;
                        }
                        i += zzci.zzg(i4, 0);
                        break;
                    case 57:
                        if (!zza((Object) t, i4, i2)) {
                            break;
                        }
                        i += zzci.zzj(i4, 0);
                        break;
                    case 58:
                        if (!zza((Object) t, i4, i2)) {
                            break;
                        }
                        i += zzci.zzc(i4, true);
                        break;
                    case 59:
                        if (!zza((Object) t, i4, i2)) {
                            break;
                        }
                        zzp = zzfv.zzp(t, j);
                        if (!(zzp instanceof zzbu)) {
                            i += zzci.zzb(i4, (String) zzp);
                            break;
                        }
                        i += zzci.zzc(i4, (zzbu) zzp);
                        break;
                    case 60:
                        if (!zza((Object) t, i4, i2)) {
                            break;
                        }
                        i += zzex.zzc(i4, zzfv.zzp(t, j), zzan(i2));
                        break;
                    case 61:
                        if (!zza((Object) t, i4, i2)) {
                            break;
                        }
                        i += zzci.zzc(i4, (zzbu) zzfv.zzp(t, j));
                        break;
                    case 62:
                        if (!zza((Object) t, i4, i2)) {
                            break;
                        }
                        i += zzci.zzh(i4, zzh(t, j));
                        break;
                    case 63:
                        if (!zza((Object) t, i4, i2)) {
                            break;
                        }
                        i += zzci.zzl(i4, zzh(t, j));
                        break;
                    case 64:
                        if (!zza((Object) t, i4, i2)) {
                            break;
                        }
                        i += zzci.zzk(i4, 0);
                        break;
                    case 65:
                        if (!zza((Object) t, i4, i2)) {
                            break;
                        }
                        i += zzci.zzh(i4, 0);
                        break;
                    case 66:
                        if (!zza((Object) t, i4, i2)) {
                            break;
                        }
                        i += zzci.zzi(i4, zzh(t, j));
                        break;
                    case 67:
                        if (!zza((Object) t, i4, i2)) {
                            break;
                        }
                        i += zzci.zzf(i4, zzi(t, j));
                        break;
                    case 68:
                        if (!zza((Object) t, i4, i2)) {
                            break;
                        }
                        i += zzci.zzc(i4, (zzeh) zzfv.zzp(t, j), zzan(i2));
                        break;
                    default:
                        break;
                }
            }
            return zza(this.zzty, (Object) t) + i;
        }
        Unsafe unsafe2 = zztj;
        i = -1;
        int i6 = 0;
        i2 = 0;
        zzaq = 0;
        while (i2 < this.zztk.length) {
            int zzaq2 = zzaq(i2);
            int i7 = this.zztk[i2];
            int i8 = (267386880 & zzaq2) >>> 20;
            i3 = 0;
            if (i8 <= 17) {
                i4 = this.zztk[i2 + 2];
                i5 = 1048575 & i4;
                i3 = 1 << (i4 >>> 20);
                if (i5 != i) {
                    zzaq = unsafe2.getInt(t, (long) i5);
                    i = i5;
                }
                i5 = i;
                i = zzaq;
                zzaq = i3;
                i3 = i4;
            } else if (!this.zzts || i8 < zzcv.DOUBLE_LIST_PACKED.id() || i8 > zzcv.SINT64_LIST_PACKED.id()) {
                i5 = i;
                i = zzaq;
                zzaq = 0;
            } else {
                i3 = this.zztk[i2 + 2] & 1048575;
                i5 = i;
                i = zzaq;
                zzaq = 0;
            }
            long j2 = (long) (1048575 & zzaq2);
            switch (i8) {
                case 0:
                    if ((zzaq & i) == 0) {
                        break;
                    }
                    i6 += zzci.zzb(i7, 0.0d);
                    break;
                case 1:
                    if ((zzaq & i) == 0) {
                        break;
                    }
                    i6 += zzci.zzb(i7, (float) BitmapDescriptorFactory.HUE_RED);
                    break;
                case 2:
                    if ((zzaq & i) == 0) {
                        break;
                    }
                    i6 += zzci.zzd(i7, unsafe2.getLong(t, j2));
                    break;
                case 3:
                    if ((zzaq & i) == 0) {
                        break;
                    }
                    i6 += zzci.zze(i7, unsafe2.getLong(t, j2));
                    break;
                case 4:
                    if ((zzaq & i) == 0) {
                        break;
                    }
                    i6 += zzci.zzg(i7, unsafe2.getInt(t, j2));
                    break;
                case 5:
                    if ((zzaq & i) == 0) {
                        break;
                    }
                    i6 += zzci.zzg(i7, 0);
                    break;
                case 6:
                    if ((zzaq & i) == 0) {
                        break;
                    }
                    i6 += zzci.zzj(i7, 0);
                    break;
                case 7:
                    if ((zzaq & i) == 0) {
                        break;
                    }
                    i6 += zzci.zzc(i7, true);
                    break;
                case 8:
                    if ((zzaq & i) == 0) {
                        break;
                    }
                    zzp = unsafe2.getObject(t, j2);
                    if (!(zzp instanceof zzbu)) {
                        i6 += zzci.zzb(i7, (String) zzp);
                        break;
                    }
                    i6 += zzci.zzc(i7, (zzbu) zzp);
                    break;
                case 9:
                    if ((zzaq & i) == 0) {
                        break;
                    }
                    i6 += zzex.zzc(i7, unsafe2.getObject(t, j2), zzan(i2));
                    break;
                case 10:
                    if ((zzaq & i) == 0) {
                        break;
                    }
                    i6 += zzci.zzc(i7, (zzbu) unsafe2.getObject(t, j2));
                    break;
                case 11:
                    if ((zzaq & i) == 0) {
                        break;
                    }
                    i6 += zzci.zzh(i7, unsafe2.getInt(t, j2));
                    break;
                case 12:
                    if ((zzaq & i) == 0) {
                        break;
                    }
                    i6 += zzci.zzl(i7, unsafe2.getInt(t, j2));
                    break;
                case 13:
                    if ((zzaq & i) == 0) {
                        break;
                    }
                    i6 += zzci.zzk(i7, 0);
                    break;
                case 14:
                    if ((zzaq & i) == 0) {
                        break;
                    }
                    i6 += zzci.zzh(i7, 0);
                    break;
                case 15:
                    if ((zzaq & i) == 0) {
                        break;
                    }
                    i6 += zzci.zzi(i7, unsafe2.getInt(t, j2));
                    break;
                case 16:
                    if ((zzaq & i) == 0) {
                        break;
                    }
                    i6 += zzci.zzf(i7, unsafe2.getLong(t, j2));
                    break;
                case 17:
                    if ((zzaq & i) == 0) {
                        break;
                    }
                    i6 += zzci.zzc(i7, (zzeh) unsafe2.getObject(t, j2), zzan(i2));
                    break;
                case 18:
                    i6 += zzex.zzw(i7, (List) unsafe2.getObject(t, j2), false);
                    break;
                case 19:
                    i6 += zzex.zzv(i7, (List) unsafe2.getObject(t, j2), false);
                    break;
                case 20:
                    i6 += zzex.zzo(i7, (List) unsafe2.getObject(t, j2), false);
                    break;
                case 21:
                    i6 += zzex.zzp(i7, (List) unsafe2.getObject(t, j2), false);
                    break;
                case 22:
                    i6 += zzex.zzs(i7, (List) unsafe2.getObject(t, j2), false);
                    break;
                case 23:
                    i6 += zzex.zzw(i7, (List) unsafe2.getObject(t, j2), false);
                    break;
                case 24:
                    i6 += zzex.zzv(i7, (List) unsafe2.getObject(t, j2), false);
                    break;
                case 25:
                    i6 += zzex.zzx(i7, (List) unsafe2.getObject(t, j2), false);
                    break;
                case 26:
                    i6 += zzex.zzc(i7, (List) unsafe2.getObject(t, j2));
                    break;
                case 27:
                    i6 += zzex.zzc(i7, (List) unsafe2.getObject(t, j2), zzan(i2));
                    break;
                case 28:
                    i6 += zzex.zzd(i7, (List) unsafe2.getObject(t, j2));
                    break;
                case 29:
                    i6 += zzex.zzt(i7, (List) unsafe2.getObject(t, j2), false);
                    break;
                case 30:
                    i6 += zzex.zzr(i7, (List) unsafe2.getObject(t, j2), false);
                    break;
                case 31:
                    i6 += zzex.zzv(i7, (List) unsafe2.getObject(t, j2), false);
                    break;
                case 32:
                    i6 += zzex.zzw(i7, (List) unsafe2.getObject(t, j2), false);
                    break;
                case 33:
                    i6 += zzex.zzu(i7, (List) unsafe2.getObject(t, j2), false);
                    break;
                case 34:
                    i6 += zzex.zzq(i7, (List) unsafe2.getObject(t, j2), false);
                    break;
                case 35:
                    zzaq = zzex.zzaa((List) unsafe2.getObject(t, j2));
                    if (zzaq > 0) {
                        if (this.zzts) {
                            unsafe2.putInt(t, (long) i3, zzaq);
                        }
                        i6 += zzaq + (zzci.zzaa(i7) + zzci.zzac(zzaq));
                        break;
                    }
                    break;
                case 36:
                    zzaq = zzex.zzz((List) unsafe2.getObject(t, j2));
                    if (zzaq > 0) {
                        if (this.zzts) {
                            unsafe2.putInt(t, (long) i3, zzaq);
                        }
                        i6 += zzaq + (zzci.zzaa(i7) + zzci.zzac(zzaq));
                        break;
                    }
                    break;
                case 37:
                    zzaq = zzex.zzs((List) unsafe2.getObject(t, j2));
                    if (zzaq > 0) {
                        if (this.zzts) {
                            unsafe2.putInt(t, (long) i3, zzaq);
                        }
                        i6 += zzaq + (zzci.zzaa(i7) + zzci.zzac(zzaq));
                        break;
                    }
                    break;
                case 38:
                    zzaq = zzex.zzt((List) unsafe2.getObject(t, j2));
                    if (zzaq > 0) {
                        if (this.zzts) {
                            unsafe2.putInt(t, (long) i3, zzaq);
                        }
                        i6 += zzaq + (zzci.zzaa(i7) + zzci.zzac(zzaq));
                        break;
                    }
                    break;
                case 39:
                    zzaq = zzex.zzw((List) unsafe2.getObject(t, j2));
                    if (zzaq > 0) {
                        if (this.zzts) {
                            unsafe2.putInt(t, (long) i3, zzaq);
                        }
                        i6 += zzaq + (zzci.zzaa(i7) + zzci.zzac(zzaq));
                        break;
                    }
                    break;
                case 40:
                    zzaq = zzex.zzaa((List) unsafe2.getObject(t, j2));
                    if (zzaq > 0) {
                        if (this.zzts) {
                            unsafe2.putInt(t, (long) i3, zzaq);
                        }
                        i6 += zzaq + (zzci.zzaa(i7) + zzci.zzac(zzaq));
                        break;
                    }
                    break;
                case 41:
                    zzaq = zzex.zzz((List) unsafe2.getObject(t, j2));
                    if (zzaq > 0) {
                        if (this.zzts) {
                            unsafe2.putInt(t, (long) i3, zzaq);
                        }
                        i6 += zzaq + (zzci.zzaa(i7) + zzci.zzac(zzaq));
                        break;
                    }
                    break;
                case 42:
                    zzaq = zzex.zzab((List) unsafe2.getObject(t, j2));
                    if (zzaq > 0) {
                        if (this.zzts) {
                            unsafe2.putInt(t, (long) i3, zzaq);
                        }
                        i6 += zzaq + (zzci.zzaa(i7) + zzci.zzac(zzaq));
                        break;
                    }
                    break;
                case 43:
                    zzaq = zzex.zzx((List) unsafe2.getObject(t, j2));
                    if (zzaq > 0) {
                        if (this.zzts) {
                            unsafe2.putInt(t, (long) i3, zzaq);
                        }
                        i6 += zzaq + (zzci.zzaa(i7) + zzci.zzac(zzaq));
                        break;
                    }
                    break;
                case 44:
                    zzaq = zzex.zzv((List) unsafe2.getObject(t, j2));
                    if (zzaq > 0) {
                        if (this.zzts) {
                            unsafe2.putInt(t, (long) i3, zzaq);
                        }
                        i6 += zzaq + (zzci.zzaa(i7) + zzci.zzac(zzaq));
                        break;
                    }
                    break;
                case 45:
                    zzaq = zzex.zzz((List) unsafe2.getObject(t, j2));
                    if (zzaq > 0) {
                        if (this.zzts) {
                            unsafe2.putInt(t, (long) i3, zzaq);
                        }
                        i6 += zzaq + (zzci.zzaa(i7) + zzci.zzac(zzaq));
                        break;
                    }
                    break;
                case 46:
                    zzaq = zzex.zzaa((List) unsafe2.getObject(t, j2));
                    if (zzaq > 0) {
                        if (this.zzts) {
                            unsafe2.putInt(t, (long) i3, zzaq);
                        }
                        i6 += zzaq + (zzci.zzaa(i7) + zzci.zzac(zzaq));
                        break;
                    }
                    break;
                case 47:
                    zzaq = zzex.zzy((List) unsafe2.getObject(t, j2));
                    if (zzaq > 0) {
                        if (this.zzts) {
                            unsafe2.putInt(t, (long) i3, zzaq);
                        }
                        i6 += zzaq + (zzci.zzaa(i7) + zzci.zzac(zzaq));
                        break;
                    }
                    break;
                case 48:
                    zzaq = zzex.zzu((List) unsafe2.getObject(t, j2));
                    if (zzaq > 0) {
                        if (this.zzts) {
                            unsafe2.putInt(t, (long) i3, zzaq);
                        }
                        i6 += zzaq + (zzci.zzaa(i7) + zzci.zzac(zzaq));
                        break;
                    }
                    break;
                case 49:
                    i6 += zzex.zzd(i7, (List) unsafe2.getObject(t, j2), zzan(i2));
                    break;
                case 50:
                    i6 += this.zzua.zzb(i7, unsafe2.getObject(t, j2), zzao(i2));
                    break;
                case 51:
                    if (!zza((Object) t, i7, i2)) {
                        break;
                    }
                    i6 += zzci.zzb(i7, 0.0d);
                    break;
                case 52:
                    if (!zza((Object) t, i7, i2)) {
                        break;
                    }
                    i6 += zzci.zzb(i7, (float) BitmapDescriptorFactory.HUE_RED);
                    break;
                case 53:
                    if (!zza((Object) t, i7, i2)) {
                        break;
                    }
                    i6 += zzci.zzd(i7, zzi(t, j2));
                    break;
                case 54:
                    if (!zza((Object) t, i7, i2)) {
                        break;
                    }
                    i6 += zzci.zze(i7, zzi(t, j2));
                    break;
                case 55:
                    if (!zza((Object) t, i7, i2)) {
                        break;
                    }
                    i6 += zzci.zzg(i7, zzh(t, j2));
                    break;
                case 56:
                    if (!zza((Object) t, i7, i2)) {
                        break;
                    }
                    i6 += zzci.zzg(i7, 0);
                    break;
                case 57:
                    if (!zza((Object) t, i7, i2)) {
                        break;
                    }
                    i6 += zzci.zzj(i7, 0);
                    break;
                case 58:
                    if (!zza((Object) t, i7, i2)) {
                        break;
                    }
                    i6 += zzci.zzc(i7, true);
                    break;
                case 59:
                    if (!zza((Object) t, i7, i2)) {
                        break;
                    }
                    zzp = unsafe2.getObject(t, j2);
                    if (!(zzp instanceof zzbu)) {
                        i6 += zzci.zzb(i7, (String) zzp);
                        break;
                    }
                    i6 += zzci.zzc(i7, (zzbu) zzp);
                    break;
                case 60:
                    if (!zza((Object) t, i7, i2)) {
                        break;
                    }
                    i6 += zzex.zzc(i7, unsafe2.getObject(t, j2), zzan(i2));
                    break;
                case 61:
                    if (!zza((Object) t, i7, i2)) {
                        break;
                    }
                    i6 += zzci.zzc(i7, (zzbu) unsafe2.getObject(t, j2));
                    break;
                case 62:
                    if (!zza((Object) t, i7, i2)) {
                        break;
                    }
                    i6 += zzci.zzh(i7, zzh(t, j2));
                    break;
                case 63:
                    if (!zza((Object) t, i7, i2)) {
                        break;
                    }
                    i6 += zzci.zzl(i7, zzh(t, j2));
                    break;
                case 64:
                    if (!zza((Object) t, i7, i2)) {
                        break;
                    }
                    i6 += zzci.zzk(i7, 0);
                    break;
                case 65:
                    if (!zza((Object) t, i7, i2)) {
                        break;
                    }
                    i6 += zzci.zzh(i7, 0);
                    break;
                case 66:
                    if (!zza((Object) t, i7, i2)) {
                        break;
                    }
                    i6 += zzci.zzi(i7, zzh(t, j2));
                    break;
                case 67:
                    if (!zza((Object) t, i7, i2)) {
                        break;
                    }
                    i6 += zzci.zzf(i7, zzi(t, j2));
                    break;
                case 68:
                    if (!zza((Object) t, i7, i2)) {
                        break;
                    }
                    i6 += zzci.zzc(i7, (zzeh) unsafe2.getObject(t, j2), zzan(i2));
                    break;
                default:
                    break;
            }
            i2 += 3;
            zzaq = i;
            i = i5;
        }
        zzaq = zza(this.zzty, (Object) t) + i6;
        return this.zztp ? zzaq + this.zztz.zzc(t).zzdq() : zzaq;
    }

    public final boolean zzp(T t) {
        int i = 0;
        int i2 = -1;
        boolean z = false;
        while (i < this.zztu) {
            int i3;
            int i4;
            int i5 = this.zztt[i];
            int i6 = this.zztk[i5];
            int zzaq = zzaq(i5);
            boolean z2;
            if (this.zztr) {
                i3 = 0;
                i4 = i2;
                z2 = z;
            } else {
                i4 = this.zztk[i5 + 2];
                i3 = i4 & 1048575;
                i4 = 1 << (i4 >>> 20);
                if (i3 != i2) {
                    i2 = zztj.getInt(t, (long) i3);
                    int i7 = i4;
                    i4 = i3;
                    i3 = i7;
                } else {
                    i3 = i4;
                    i4 = i2;
                    z2 = z;
                }
            }
            if ((ErrorDialogData.BINDER_CRASH & zzaq) != 0) {
                int i8 = 1;
            } else {
                z = false;
            }
            if (i8 != 0 && !zza((Object) t, i5, i2, i3)) {
                return false;
            }
            switch ((267386880 & zzaq) >>> 20) {
                case 9:
                case 17:
                    if (zza((Object) t, i5, i2, i3) && !zza((Object) t, zzaq, zzan(i5))) {
                        return false;
                    }
                case 27:
                case 49:
                    List list = (List) zzfv.zzp(t, (long) (zzaq & 1048575));
                    if (!list.isEmpty()) {
                        zzev zzan = zzan(i5);
                        for (i3 = 0; i3 < list.size(); i3++) {
                            if (!zzan.zzp(list.get(i3))) {
                                z = false;
                                if (z) {
                                    break;
                                }
                                return false;
                            }
                        }
                    }
                    z = true;
                    if (z) {
                        return false;
                    }
                case 50:
                    Map zzj = this.zzua.zzj(zzfv.zzp(t, (long) (zzaq & 1048575)));
                    if (!zzj.isEmpty()) {
                        if (this.zzua.zzn(zzao(i5)).zztd.zzgj() == zzgi.MESSAGE) {
                            zzev zzev = null;
                            for (Object next : zzj.values()) {
                                if (zzev == null) {
                                    zzev = zzes.zzfg().zzf(next.getClass());
                                }
                                if (!zzev.zzp(next)) {
                                    z = false;
                                    if (z) {
                                        break;
                                    }
                                    return false;
                                }
                            }
                        }
                    }
                    z = true;
                    if (z) {
                        return false;
                    }
                case 60:
                case 68:
                    if (zza((Object) t, i6, i5) && !zza((Object) t, zzaq, zzan(i5))) {
                        return false;
                    }
                default:
                    break;
            }
            i++;
            z = i2;
            i2 = i4;
        }
        return !this.zztp || this.zztz.zzc(t).isInitialized();
    }
}
