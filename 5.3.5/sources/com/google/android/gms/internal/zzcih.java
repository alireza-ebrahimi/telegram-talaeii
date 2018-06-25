package com.google.android.gms.internal;

import android.content.ContentValues;
import android.database.sqlite.SQLiteException;
import android.support.annotation.WorkerThread;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.gms.common.internal.zzbq;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

final class zzcih extends zzcli {
    zzcih(zzckj zzckj) {
        super(zzckj);
    }

    private final Boolean zza(double d, zzcnu zzcnu) {
        try {
            return zza(new BigDecimal(d), zzcnu, Math.ulp(d));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private final Boolean zza(long j, zzcnu zzcnu) {
        try {
            return zza(new BigDecimal(j), zzcnu, 0.0d);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private final Boolean zza(zzcns zzcns, String str, zzcoc[] zzcocArr, long j) {
        Boolean zza;
        if (zzcns.zzjtb != null) {
            zza = zza(j, zzcns.zzjtb);
            if (zza == null) {
                return null;
            }
            if (!zza.booleanValue()) {
                return Boolean.valueOf(false);
            }
        }
        Set hashSet = new HashSet();
        for (zzcnt zzcnt : zzcns.zzjsz) {
            if (TextUtils.isEmpty(zzcnt.zzjtg)) {
                zzayp().zzbaw().zzj("null or empty param name in filter. event", zzayk().zzjp(str));
                return null;
            }
            hashSet.add(zzcnt.zzjtg);
        }
        Map arrayMap = new ArrayMap();
        for (zzcoc zzcoc : zzcocArr) {
            if (hashSet.contains(zzcoc.name)) {
                if (zzcoc.zzjum != null) {
                    arrayMap.put(zzcoc.name, zzcoc.zzjum);
                } else if (zzcoc.zzjsl != null) {
                    arrayMap.put(zzcoc.name, zzcoc.zzjsl);
                } else if (zzcoc.zzgim != null) {
                    arrayMap.put(zzcoc.name, zzcoc.zzgim);
                } else {
                    zzayp().zzbaw().zze("Unknown value for param. event, param", zzayk().zzjp(str), zzayk().zzjq(zzcoc.name));
                    return null;
                }
            }
        }
        for (zzcnt zzcnt2 : zzcns.zzjsz) {
            boolean equals = Boolean.TRUE.equals(zzcnt2.zzjtf);
            String str2 = zzcnt2.zzjtg;
            if (TextUtils.isEmpty(str2)) {
                zzayp().zzbaw().zzj("Event has empty param name. event", zzayk().zzjp(str));
                return null;
            }
            Object obj = arrayMap.get(str2);
            if (obj instanceof Long) {
                if (zzcnt2.zzjte == null) {
                    zzayp().zzbaw().zze("No number filter for long param. event, param", zzayk().zzjp(str), zzayk().zzjq(str2));
                    return null;
                }
                zza = zza(((Long) obj).longValue(), zzcnt2.zzjte);
                if (zza == null) {
                    return null;
                }
                if (((!zza.booleanValue() ? 1 : 0) ^ equals) != 0) {
                    return Boolean.valueOf(false);
                }
            } else if (obj instanceof Double) {
                if (zzcnt2.zzjte == null) {
                    zzayp().zzbaw().zze("No number filter for double param. event, param", zzayk().zzjp(str), zzayk().zzjq(str2));
                    return null;
                }
                zza = zza(((Double) obj).doubleValue(), zzcnt2.zzjte);
                if (zza == null) {
                    return null;
                }
                if (((!zza.booleanValue() ? 1 : 0) ^ equals) != 0) {
                    return Boolean.valueOf(false);
                }
            } else if (obj instanceof String) {
                if (zzcnt2.zzjtd != null) {
                    zza = zza((String) obj, zzcnt2.zzjtd);
                } else if (zzcnt2.zzjte == null) {
                    zzayp().zzbaw().zze("No filter for String param. event, param", zzayk().zzjp(str), zzayk().zzjq(str2));
                    return null;
                } else if (zzcno.zzkr((String) obj)) {
                    zza = zza((String) obj, zzcnt2.zzjte);
                } else {
                    zzayp().zzbaw().zze("Invalid param value for number filter. event, param", zzayk().zzjp(str), zzayk().zzjq(str2));
                    return null;
                }
                if (zza == null) {
                    return null;
                }
                if (((!zza.booleanValue() ? 1 : 0) ^ equals) != 0) {
                    return Boolean.valueOf(false);
                }
            } else if (obj == null) {
                zzayp().zzbba().zze("Missing param for filter. event, param", zzayk().zzjp(str), zzayk().zzjq(str2));
                return Boolean.valueOf(false);
            } else {
                zzayp().zzbaw().zze("Unknown param type. event, param", zzayk().zzjp(str), zzayk().zzjq(str2));
                return null;
            }
        }
        return Boolean.valueOf(true);
    }

    private static Boolean zza(Boolean bool, boolean z) {
        return bool == null ? null : Boolean.valueOf(bool.booleanValue() ^ z);
    }

    private final Boolean zza(String str, int i, boolean z, String str2, List<String> list, String str3) {
        if (str == null) {
            return null;
        }
        if (i == 6) {
            if (list == null || list.size() == 0) {
                return null;
            }
        } else if (str2 == null) {
            return null;
        }
        if (!(z || i == 1)) {
            str = str.toUpperCase(Locale.ENGLISH);
        }
        switch (i) {
            case 1:
                try {
                    return Boolean.valueOf(Pattern.compile(str3, z ? 0 : 66).matcher(str).matches());
                } catch (PatternSyntaxException e) {
                    zzayp().zzbaw().zzj("Invalid regular expression in REGEXP audience filter. expression", str3);
                    return null;
                }
            case 2:
                return Boolean.valueOf(str.startsWith(str2));
            case 3:
                return Boolean.valueOf(str.endsWith(str2));
            case 4:
                return Boolean.valueOf(str.contains(str2));
            case 5:
                return Boolean.valueOf(str.equals(str2));
            case 6:
                return Boolean.valueOf(list.contains(str));
            default:
                return null;
        }
    }

    private final Boolean zza(String str, zzcnu zzcnu) {
        Boolean bool = null;
        if (zzcno.zzkr(str)) {
            try {
                bool = zza(new BigDecimal(str), zzcnu, 0.0d);
            } catch (NumberFormatException e) {
            }
        }
        return bool;
    }

    private final Boolean zza(String str, zzcnw zzcnw) {
        int i = 0;
        String str2 = null;
        zzbq.checkNotNull(zzcnw);
        if (str == null || zzcnw.zzjtp == null || zzcnw.zzjtp.intValue() == 0) {
            return null;
        }
        List list;
        if (zzcnw.zzjtp.intValue() == 6) {
            if (zzcnw.zzjts == null || zzcnw.zzjts.length == 0) {
                return null;
            }
        } else if (zzcnw.zzjtq == null) {
            return null;
        }
        int intValue = zzcnw.zzjtp.intValue();
        boolean z = zzcnw.zzjtr != null && zzcnw.zzjtr.booleanValue();
        String toUpperCase = (z || intValue == 1 || intValue == 6) ? zzcnw.zzjtq : zzcnw.zzjtq.toUpperCase(Locale.ENGLISH);
        if (zzcnw.zzjts == null) {
            list = null;
        } else {
            String[] strArr = zzcnw.zzjts;
            if (z) {
                list = Arrays.asList(strArr);
            } else {
                list = new ArrayList();
                int length = strArr.length;
                while (i < length) {
                    list.add(strArr[i].toUpperCase(Locale.ENGLISH));
                    i++;
                }
            }
        }
        if (intValue == 1) {
            str2 = toUpperCase;
        }
        return zza(str, intValue, z, toUpperCase, list, str2);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.Boolean zza(java.math.BigDecimal r10, com.google.android.gms.internal.zzcnu r11, double r12) {
        /*
        r8 = 4;
        r7 = -1;
        r1 = 0;
        r0 = 1;
        r2 = 0;
        com.google.android.gms.common.internal.zzbq.checkNotNull(r11);
        r3 = r11.zzjth;
        if (r3 == 0) goto L_0x0014;
    L_0x000c:
        r3 = r11.zzjth;
        r3 = r3.intValue();
        if (r3 != 0) goto L_0x0016;
    L_0x0014:
        r0 = r2;
    L_0x0015:
        return r0;
    L_0x0016:
        r3 = r11.zzjth;
        r3 = r3.intValue();
        if (r3 != r8) goto L_0x0028;
    L_0x001e:
        r3 = r11.zzjtk;
        if (r3 == 0) goto L_0x0026;
    L_0x0022:
        r3 = r11.zzjtl;
        if (r3 != 0) goto L_0x002e;
    L_0x0026:
        r0 = r2;
        goto L_0x0015;
    L_0x0028:
        r3 = r11.zzjtj;
        if (r3 != 0) goto L_0x002e;
    L_0x002c:
        r0 = r2;
        goto L_0x0015;
    L_0x002e:
        r3 = r11.zzjth;
        r6 = r3.intValue();
        r3 = r11.zzjth;
        r3 = r3.intValue();
        if (r3 != r8) goto L_0x0066;
    L_0x003c:
        r3 = r11.zzjtk;
        r3 = com.google.android.gms.internal.zzcno.zzkr(r3);
        if (r3 == 0) goto L_0x004c;
    L_0x0044:
        r3 = r11.zzjtl;
        r3 = com.google.android.gms.internal.zzcno.zzkr(r3);
        if (r3 != 0) goto L_0x004e;
    L_0x004c:
        r0 = r2;
        goto L_0x0015;
    L_0x004e:
        r4 = new java.math.BigDecimal;	 Catch:{ NumberFormatException -> 0x0063 }
        r3 = r11.zzjtk;	 Catch:{ NumberFormatException -> 0x0063 }
        r4.<init>(r3);	 Catch:{ NumberFormatException -> 0x0063 }
        r3 = new java.math.BigDecimal;	 Catch:{ NumberFormatException -> 0x0063 }
        r5 = r11.zzjtl;	 Catch:{ NumberFormatException -> 0x0063 }
        r3.<init>(r5);	 Catch:{ NumberFormatException -> 0x0063 }
        r5 = r2;
    L_0x005d:
        if (r6 != r8) goto L_0x007e;
    L_0x005f:
        if (r4 != 0) goto L_0x0080;
    L_0x0061:
        r0 = r2;
        goto L_0x0015;
    L_0x0063:
        r0 = move-exception;
        r0 = r2;
        goto L_0x0015;
    L_0x0066:
        r3 = r11.zzjtj;
        r3 = com.google.android.gms.internal.zzcno.zzkr(r3);
        if (r3 != 0) goto L_0x0070;
    L_0x006e:
        r0 = r2;
        goto L_0x0015;
    L_0x0070:
        r3 = new java.math.BigDecimal;	 Catch:{ NumberFormatException -> 0x007b }
        r4 = r11.zzjtj;	 Catch:{ NumberFormatException -> 0x007b }
        r3.<init>(r4);	 Catch:{ NumberFormatException -> 0x007b }
        r4 = r2;
        r5 = r3;
        r3 = r2;
        goto L_0x005d;
    L_0x007b:
        r0 = move-exception;
        r0 = r2;
        goto L_0x0015;
    L_0x007e:
        if (r5 == 0) goto L_0x0083;
    L_0x0080:
        switch(r6) {
            case 1: goto L_0x0085;
            case 2: goto L_0x0092;
            case 3: goto L_0x00a0;
            case 4: goto L_0x00ee;
            default: goto L_0x0083;
        };
    L_0x0083:
        r0 = r2;
        goto L_0x0015;
    L_0x0085:
        r2 = r10.compareTo(r5);
        if (r2 != r7) goto L_0x0090;
    L_0x008b:
        r0 = java.lang.Boolean.valueOf(r0);
        goto L_0x0015;
    L_0x0090:
        r0 = r1;
        goto L_0x008b;
    L_0x0092:
        r2 = r10.compareTo(r5);
        if (r2 != r0) goto L_0x009e;
    L_0x0098:
        r0 = java.lang.Boolean.valueOf(r0);
        goto L_0x0015;
    L_0x009e:
        r0 = r1;
        goto L_0x0098;
    L_0x00a0:
        r2 = 0;
        r2 = (r12 > r2 ? 1 : (r12 == r2 ? 0 : -1));
        if (r2 == 0) goto L_0x00e0;
    L_0x00a6:
        r2 = new java.math.BigDecimal;
        r2.<init>(r12);
        r3 = new java.math.BigDecimal;
        r4 = 2;
        r3.<init>(r4);
        r2 = r2.multiply(r3);
        r2 = r5.subtract(r2);
        r2 = r10.compareTo(r2);
        if (r2 != r0) goto L_0x00de;
    L_0x00bf:
        r2 = new java.math.BigDecimal;
        r2.<init>(r12);
        r3 = new java.math.BigDecimal;
        r4 = 2;
        r3.<init>(r4);
        r2 = r2.multiply(r3);
        r2 = r5.add(r2);
        r2 = r10.compareTo(r2);
        if (r2 != r7) goto L_0x00de;
    L_0x00d8:
        r0 = java.lang.Boolean.valueOf(r0);
        goto L_0x0015;
    L_0x00de:
        r0 = r1;
        goto L_0x00d8;
    L_0x00e0:
        r2 = r10.compareTo(r5);
        if (r2 != 0) goto L_0x00ec;
    L_0x00e6:
        r0 = java.lang.Boolean.valueOf(r0);
        goto L_0x0015;
    L_0x00ec:
        r0 = r1;
        goto L_0x00e6;
    L_0x00ee:
        r2 = r10.compareTo(r4);
        if (r2 == r7) goto L_0x0100;
    L_0x00f4:
        r2 = r10.compareTo(r3);
        if (r2 == r0) goto L_0x0100;
    L_0x00fa:
        r0 = java.lang.Boolean.valueOf(r0);
        goto L_0x0015;
    L_0x0100:
        r0 = r1;
        goto L_0x00fa;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcih.zza(java.math.BigDecimal, com.google.android.gms.internal.zzcnu, double):java.lang.Boolean");
    }

    @WorkerThread
    final zzcoa[] zza(String str, zzcob[] zzcobArr, zzcog[] zzcogArr) {
        int intValue;
        BitSet bitSet;
        BitSet bitSet2;
        int i;
        zzclh zzayj;
        int i2;
        int length;
        Map map;
        zzcoa zzcoa;
        zzbq.zzgv(str);
        HashSet hashSet = new HashSet();
        ArrayMap arrayMap = new ArrayMap();
        Map arrayMap2 = new ArrayMap();
        ArrayMap arrayMap3 = new ArrayMap();
        Map zzjm = zzayj().zzjm(str);
        if (zzjm != null) {
            for (Integer intValue2 : zzjm.keySet()) {
                intValue = intValue2.intValue();
                zzcof zzcof = (zzcof) zzjm.get(Integer.valueOf(intValue));
                bitSet = (BitSet) arrayMap2.get(Integer.valueOf(intValue));
                bitSet2 = (BitSet) arrayMap3.get(Integer.valueOf(intValue));
                if (bitSet == null) {
                    bitSet = new BitSet();
                    arrayMap2.put(Integer.valueOf(intValue), bitSet);
                    bitSet2 = new BitSet();
                    arrayMap3.put(Integer.valueOf(intValue), bitSet2);
                }
                for (i = 0; i < (zzcof.zzjvo.length << 6); i++) {
                    if (zzcno.zza(zzcof.zzjvo, i)) {
                        zzayp().zzbba().zze("Filter already evaluated. audience ID, filter ID", Integer.valueOf(intValue), Integer.valueOf(i));
                        bitSet2.set(i);
                        if (zzcno.zza(zzcof.zzjvp, i)) {
                            bitSet.set(i);
                        }
                    }
                }
                zzcoa zzcoa2 = new zzcoa();
                arrayMap.put(Integer.valueOf(intValue), zzcoa2);
                zzcoa2.zzjug = Boolean.valueOf(false);
                zzcoa2.zzjuf = zzcof;
                zzcoa2.zzjue = new zzcof();
                zzcoa2.zzjue.zzjvp = zzcno.zza(bitSet);
                zzcoa2.zzjue.zzjvo = zzcno.zza(bitSet2);
            }
        }
        if (zzcobArr != null) {
            zzcob zzcob = null;
            long j = 0;
            Long l = null;
            ArrayMap arrayMap4 = new ArrayMap();
            for (zzcob zzcob2 : zzcobArr) {
                zzcoc[] zzcocArr;
                String str2;
                Long l2;
                long j2;
                zzcob zzcob3;
                zzcit zzae;
                zzcit zzcit;
                Map map2;
                int intValue3;
                BitSet bitSet3;
                BitSet bitSet4;
                String str3 = zzcob2.name;
                zzcoc[] zzcocArr2 = zzcob2.zzjui;
                if (zzayr().zzc(str, zzciz.zzjjv)) {
                    zzayl();
                    Long l3 = (Long) zzcno.zzb(zzcob2, "_eid");
                    Object obj = l3 != null ? 1 : null;
                    Object obj2 = (obj == null || !str3.equals("_ep")) ? null : 1;
                    if (obj2 != null) {
                        zzayl();
                        str3 = (String) zzcno.zzb(zzcob2, "_en");
                        if (TextUtils.isEmpty(str3)) {
                            zzayp().zzbau().zzj("Extra parameter without an event name. eventId", l3);
                        } else {
                            Long l4;
                            int i3;
                            if (zzcob == null || l == null || l3.longValue() != l.longValue()) {
                                Pair zzb = zzayj().zzb(str, l3);
                                if (zzb == null || zzb.first == null) {
                                    zzayp().zzbau().zze("Extra parameter without existing main event. eventName, eventId", str3, l3);
                                } else {
                                    zzcob zzcob4 = (zzcob) zzb.first;
                                    j = ((Long) zzb.second).longValue();
                                    zzayl();
                                    l4 = (Long) zzcno.zzb(zzcob4, "_eid");
                                    zzcob = zzcob4;
                                }
                            } else {
                                l4 = l;
                            }
                            j--;
                            if (j <= 0) {
                                zzayj = zzayj();
                                zzayj.zzwj();
                                zzayj.zzayp().zzbba().zzj("Clearing complex main event info. appId", str);
                                try {
                                    zzayj.getWritableDatabase().execSQL("delete from main_event_params where app_id=?", new String[]{str});
                                } catch (SQLiteException e) {
                                    zzayj.zzayp().zzbau().zzj("Error clearing complex main event", e);
                                }
                            } else {
                                zzayj().zza(str, l3, j, zzcob);
                            }
                            zzcoc[] zzcocArr3 = new zzcoc[(zzcob.zzjui.length + zzcocArr2.length)];
                            i2 = 0;
                            zzcoc[] zzcocArr4 = zzcob.zzjui;
                            int length2 = zzcocArr4.length;
                            i = 0;
                            while (i < length2) {
                                zzcoc zzcoc = zzcocArr4[i];
                                zzayl();
                                if (zzcno.zza(zzcob2, zzcoc.name) == null) {
                                    i3 = i2 + 1;
                                    zzcocArr3[i2] = zzcoc;
                                } else {
                                    i3 = i2;
                                }
                                i++;
                                i2 = i3;
                            }
                            if (i2 > 0) {
                                length = zzcocArr2.length;
                                i3 = 0;
                                while (i3 < length) {
                                    i = i2 + 1;
                                    zzcocArr3[i2] = zzcocArr2[i3];
                                    i3++;
                                    i2 = i;
                                }
                                zzcocArr = i2 == zzcocArr3.length ? zzcocArr3 : (zzcoc[]) Arrays.copyOf(zzcocArr3, i2);
                                str2 = str3;
                                l2 = l4;
                                j2 = j;
                                zzcob3 = zzcob;
                            } else {
                                zzayp().zzbaw().zzj("No unique parameters in main event. eventName", str3);
                                zzcocArr = zzcocArr2;
                                str2 = str3;
                                l2 = l4;
                                j2 = j;
                                zzcob3 = zzcob;
                            }
                        }
                    } else if (obj != null) {
                        zzayl();
                        Long valueOf = Long.valueOf(0);
                        l = zzcno.zzb(zzcob2, "_epc");
                        if (l != null) {
                            valueOf = l;
                        }
                        j = valueOf.longValue();
                        if (j <= 0) {
                            zzayp().zzbaw().zzj("Complex event with zero extra param count. eventName", str3);
                            zzcocArr = zzcocArr2;
                            str2 = str3;
                            l2 = l3;
                            j2 = j;
                            zzcob3 = zzcob2;
                        } else {
                            zzayj().zza(str, l3, j, zzcob2);
                            zzcocArr = zzcocArr2;
                            str2 = str3;
                            l2 = l3;
                            j2 = j;
                            zzcob3 = zzcob2;
                        }
                    }
                    zzae = zzayj().zzae(str, zzcob2.name);
                    if (zzae != null) {
                        zzayp().zzbaw().zze("Event aggregate wasn't created during raw event logging. appId, event", zzcjj.zzjs(str), zzayk().zzjp(str2));
                        zzcit = new zzcit(str, zzcob2.name, 1, 1, zzcob2.zzjuj.longValue(), 0, null, null, null);
                    } else {
                        zzcit = zzae.zzban();
                    }
                    zzayj().zza(zzcit);
                    j = zzcit.zzjhs;
                    map = (Map) arrayMap4.get(str2);
                    if (map != null) {
                        map = zzayj().zzaj(str, str2);
                        if (map == null) {
                            map = new ArrayMap();
                        }
                        arrayMap4.put(str2, map);
                        map2 = map;
                    } else {
                        map2 = map;
                    }
                    for (Integer intValue22 : r10.keySet()) {
                        intValue3 = intValue22.intValue();
                        if (hashSet.contains(Integer.valueOf(intValue3))) {
                            bitSet = (BitSet) arrayMap2.get(Integer.valueOf(intValue3));
                            bitSet2 = (BitSet) arrayMap3.get(Integer.valueOf(intValue3));
                            if (((zzcoa) arrayMap.get(Integer.valueOf(intValue3))) != null) {
                                zzcoa = new zzcoa();
                                arrayMap.put(Integer.valueOf(intValue3), zzcoa);
                                zzcoa.zzjug = Boolean.valueOf(true);
                                bitSet = new BitSet();
                                arrayMap2.put(Integer.valueOf(intValue3), bitSet);
                                bitSet2 = new BitSet();
                                arrayMap3.put(Integer.valueOf(intValue3), bitSet2);
                                bitSet3 = bitSet2;
                                bitSet4 = bitSet;
                            } else {
                                bitSet3 = bitSet2;
                                bitSet4 = bitSet;
                            }
                            for (zzcns zzcns : (List) r10.get(Integer.valueOf(intValue3))) {
                                if (zzayp().zzae(2)) {
                                    zzayp().zzbba().zzd("Evaluating filter. audience, filter, event", Integer.valueOf(intValue3), zzcns.zzjsx, zzayk().zzjp(zzcns.zzjsy));
                                    zzayp().zzbba().zzj("Filter definition", zzayk().zza(zzcns));
                                }
                                if (zzcns.zzjsx != null || zzcns.zzjsx.intValue() > 256) {
                                    zzayp().zzbaw().zze("Invalid event filter ID. appId, id", zzcjj.zzjs(str), String.valueOf(zzcns.zzjsx));
                                } else if (bitSet4.get(zzcns.zzjsx.intValue())) {
                                    zzayp().zzbba().zze("Event filter already evaluated true. audience ID, filter ID", Integer.valueOf(intValue3), zzcns.zzjsx);
                                } else {
                                    Boolean zza = zza(zzcns, str2, zzcocArr, j);
                                    zzcjl zzbba = zzayp().zzbba();
                                    String str4 = "Event filter result";
                                    if (zza == null) {
                                        obj2 = "null";
                                    } else {
                                        Boolean bool = zza;
                                    }
                                    zzbba.zzj(str4, obj2);
                                    if (zza == null) {
                                        hashSet.add(Integer.valueOf(intValue3));
                                    } else {
                                        bitSet3.set(zzcns.zzjsx.intValue());
                                        if (zza.booleanValue()) {
                                            bitSet4.set(zzcns.zzjsx.intValue());
                                        }
                                    }
                                }
                            }
                        } else {
                            zzayp().zzbba().zzj("Skipping failed audience ID", Integer.valueOf(intValue3));
                        }
                    }
                    l = l2;
                    j = j2;
                    zzcob = zzcob3;
                }
                zzcocArr = zzcocArr2;
                str2 = str3;
                l2 = l;
                j2 = j;
                zzcob3 = zzcob;
                zzae = zzayj().zzae(str, zzcob2.name);
                if (zzae != null) {
                    zzcit = zzae.zzban();
                } else {
                    zzayp().zzbaw().zze("Event aggregate wasn't created during raw event logging. appId, event", zzcjj.zzjs(str), zzayk().zzjp(str2));
                    zzcit = new zzcit(str, zzcob2.name, 1, 1, zzcob2.zzjuj.longValue(), 0, null, null, null);
                }
                zzayj().zza(zzcit);
                j = zzcit.zzjhs;
                map = (Map) arrayMap4.get(str2);
                if (map != null) {
                    map2 = map;
                } else {
                    map = zzayj().zzaj(str, str2);
                    if (map == null) {
                        map = new ArrayMap();
                    }
                    arrayMap4.put(str2, map);
                    map2 = map;
                }
                while (r13.hasNext()) {
                    intValue3 = intValue22.intValue();
                    if (hashSet.contains(Integer.valueOf(intValue3))) {
                        bitSet = (BitSet) arrayMap2.get(Integer.valueOf(intValue3));
                        bitSet2 = (BitSet) arrayMap3.get(Integer.valueOf(intValue3));
                        if (((zzcoa) arrayMap.get(Integer.valueOf(intValue3))) != null) {
                            bitSet3 = bitSet2;
                            bitSet4 = bitSet;
                        } else {
                            zzcoa = new zzcoa();
                            arrayMap.put(Integer.valueOf(intValue3), zzcoa);
                            zzcoa.zzjug = Boolean.valueOf(true);
                            bitSet = new BitSet();
                            arrayMap2.put(Integer.valueOf(intValue3), bitSet);
                            bitSet2 = new BitSet();
                            arrayMap3.put(Integer.valueOf(intValue3), bitSet2);
                            bitSet3 = bitSet2;
                            bitSet4 = bitSet;
                        }
                        for (zzcns zzcns2 : (List) r10.get(Integer.valueOf(intValue3))) {
                            if (zzayp().zzae(2)) {
                                zzayp().zzbba().zzd("Evaluating filter. audience, filter, event", Integer.valueOf(intValue3), zzcns2.zzjsx, zzayk().zzjp(zzcns2.zzjsy));
                                zzayp().zzbba().zzj("Filter definition", zzayk().zza(zzcns2));
                            }
                            if (zzcns2.zzjsx != null) {
                            }
                            zzayp().zzbaw().zze("Invalid event filter ID. appId, id", zzcjj.zzjs(str), String.valueOf(zzcns2.zzjsx));
                        }
                    } else {
                        zzayp().zzbba().zzj("Skipping failed audience ID", Integer.valueOf(intValue3));
                    }
                }
                l = l2;
                j = j2;
                zzcob = zzcob3;
            }
        }
        if (zzcogArr != null) {
            Map arrayMap5 = new ArrayMap();
            for (zzcog zzcog : zzcogArr) {
                map = (Map) arrayMap5.get(zzcog.name);
                Map map3;
                if (map == null) {
                    map = zzayj().zzak(str, zzcog.name);
                    if (map == null) {
                        map = new ArrayMap();
                    }
                    arrayMap5.put(zzcog.name, map);
                    map3 = map;
                } else {
                    map3 = map;
                }
                for (Integer intValue222 : r7.keySet()) {
                    length = intValue222.intValue();
                    if (hashSet.contains(Integer.valueOf(length))) {
                        zzayp().zzbba().zzj("Skipping failed audience ID", Integer.valueOf(length));
                    } else {
                        bitSet = (BitSet) arrayMap2.get(Integer.valueOf(length));
                        bitSet2 = (BitSet) arrayMap3.get(Integer.valueOf(length));
                        if (((zzcoa) arrayMap.get(Integer.valueOf(length))) == null) {
                            zzcoa = new zzcoa();
                            arrayMap.put(Integer.valueOf(length), zzcoa);
                            zzcoa.zzjug = Boolean.valueOf(true);
                            bitSet = new BitSet();
                            arrayMap2.put(Integer.valueOf(length), bitSet);
                            bitSet2 = new BitSet();
                            arrayMap3.put(Integer.valueOf(length), bitSet2);
                        }
                        for (zzcnv zzcnv : (List) r7.get(Integer.valueOf(length))) {
                            if (zzayp().zzae(2)) {
                                zzayp().zzbba().zzd("Evaluating filter. audience, filter, property", Integer.valueOf(length), zzcnv.zzjsx, zzayk().zzjr(zzcnv.zzjtn));
                                zzayp().zzbba().zzj("Filter definition", zzayk().zza(zzcnv));
                            }
                            if (zzcnv.zzjsx == null || zzcnv.zzjsx.intValue() > 256) {
                                zzayp().zzbaw().zze("Invalid property filter ID. appId, id", zzcjj.zzjs(str), String.valueOf(zzcnv.zzjsx));
                                hashSet.add(Integer.valueOf(length));
                                break;
                            } else if (bitSet.get(zzcnv.zzjsx.intValue())) {
                                zzayp().zzbba().zze("Property filter already evaluated true. audience ID, filter ID", Integer.valueOf(length), zzcnv.zzjsx);
                            } else {
                                Boolean bool2;
                                Object obj3;
                                zzcnt zzcnt = zzcnv.zzjto;
                                if (zzcnt == null) {
                                    zzayp().zzbaw().zzj("Missing property filter. property", zzayk().zzjr(zzcog.name));
                                    bool2 = null;
                                } else {
                                    boolean equals = Boolean.TRUE.equals(zzcnt.zzjtf);
                                    if (zzcog.zzjum != null) {
                                        if (zzcnt.zzjte == null) {
                                            zzayp().zzbaw().zzj("No number filter for long property. property", zzayk().zzjr(zzcog.name));
                                            bool2 = null;
                                        } else {
                                            bool2 = zza(zza(zzcog.zzjum.longValue(), zzcnt.zzjte), equals);
                                        }
                                    } else if (zzcog.zzjsl != null) {
                                        if (zzcnt.zzjte == null) {
                                            zzayp().zzbaw().zzj("No number filter for double property. property", zzayk().zzjr(zzcog.name));
                                            bool2 = null;
                                        } else {
                                            bool2 = zza(zza(zzcog.zzjsl.doubleValue(), zzcnt.zzjte), equals);
                                        }
                                    } else if (zzcog.zzgim == null) {
                                        zzayp().zzbaw().zzj("User property has no value, property", zzayk().zzjr(zzcog.name));
                                        bool2 = null;
                                    } else if (zzcnt.zzjtd == null) {
                                        if (zzcnt.zzjte == null) {
                                            zzayp().zzbaw().zzj("No string or number filter defined. property", zzayk().zzjr(zzcog.name));
                                        } else if (zzcno.zzkr(zzcog.zzgim)) {
                                            bool2 = zza(zza(zzcog.zzgim, zzcnt.zzjte), equals);
                                        } else {
                                            zzayp().zzbaw().zze("Invalid user property value for Numeric number filter. property, value", zzayk().zzjr(zzcog.name), zzcog.zzgim);
                                        }
                                        bool2 = null;
                                    } else {
                                        bool2 = zza(zza(zzcog.zzgim, zzcnt.zzjtd), equals);
                                    }
                                }
                                zzcjl zzbba2 = zzayp().zzbba();
                                String str5 = "Property filter result";
                                if (bool2 == null) {
                                    obj3 = "null";
                                } else {
                                    Boolean bool3 = bool2;
                                }
                                zzbba2.zzj(str5, obj3);
                                if (bool2 == null) {
                                    hashSet.add(Integer.valueOf(length));
                                } else {
                                    bitSet2.set(zzcnv.zzjsx.intValue());
                                    if (bool2.booleanValue()) {
                                        bitSet.set(zzcnv.zzjsx.intValue());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        zzcoa[] zzcoaArr = new zzcoa[arrayMap2.size()];
        i2 = 0;
        for (Integer intValue2222 : arrayMap2.keySet()) {
            intValue = intValue2222.intValue();
            if (!hashSet.contains(Integer.valueOf(intValue))) {
                zzcoa = (zzcoa) arrayMap.get(Integer.valueOf(intValue));
                zzcoa2 = zzcoa == null ? new zzcoa() : zzcoa;
                int i4 = i2 + 1;
                zzcoaArr[i2] = zzcoa2;
                zzcoa2.zzjst = Integer.valueOf(intValue);
                zzcoa2.zzjue = new zzcof();
                zzcoa2.zzjue.zzjvp = zzcno.zza((BitSet) arrayMap2.get(Integer.valueOf(intValue)));
                zzcoa2.zzjue.zzjvo = zzcno.zza((BitSet) arrayMap3.get(Integer.valueOf(intValue)));
                zzayj = zzayj();
                zzfls zzfls = zzcoa2.zzjue;
                zzayj.zzyk();
                zzayj.zzwj();
                zzbq.zzgv(str);
                zzbq.checkNotNull(zzfls);
                try {
                    byte[] bArr = new byte[zzfls.zzhs()];
                    zzflk zzp = zzflk.zzp(bArr, 0, bArr.length);
                    zzfls.zza(zzp);
                    zzp.zzcyx();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("app_id", str);
                    contentValues.put("audience_id", Integer.valueOf(intValue));
                    contentValues.put("current_results", bArr);
                    try {
                        if (zzayj.getWritableDatabase().insertWithOnConflict("audience_filter_values", null, contentValues, 5) == -1) {
                            zzayj.zzayp().zzbau().zzj("Failed to insert filter results (got -1). appId", zzcjj.zzjs(str));
                        }
                        i2 = i4;
                    } catch (SQLiteException e2) {
                        zzayj.zzayp().zzbau().zze("Error storing filter results. appId", zzcjj.zzjs(str), e2);
                        i2 = i4;
                    }
                } catch (IOException e3) {
                    zzayj.zzayp().zzbau().zze("Configuration loss. Failed to serialize filter results. appId", zzcjj.zzjs(str), e3);
                    i2 = i4;
                }
            }
        }
        return (zzcoa[]) Arrays.copyOf(zzcoaArr, i2);
    }

    protected final boolean zzazq() {
        return false;
    }
}
