package com.google.android.gms.internal.measurement;

import android.content.ContentValues;
import android.database.sqlite.SQLiteException;
import android.support.v4.p022f.C0464a;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;
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

final class zzeb extends zzjr {
    zzeb(zzjs zzjs) {
        super(zzjs);
    }

    private final Boolean zza(double d, zzki zzki) {
        try {
            return zza(new BigDecimal(d), zzki, Math.ulp(d));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private final Boolean zza(long j, zzki zzki) {
        try {
            return zza(new BigDecimal(j), zzki, 0.0d);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @VisibleForTesting
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
                    zzgf().zziv().zzg("Invalid regular expression in REGEXP audience filter. expression", str3);
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

    private final Boolean zza(String str, zzki zzki) {
        Boolean bool = null;
        if (zzkc.zzcj(str)) {
            try {
                bool = zza(new BigDecimal(str), zzki, 0.0d);
            } catch (NumberFormatException e) {
            }
        }
        return bool;
    }

    @VisibleForTesting
    private final Boolean zza(String str, zzkk zzkk) {
        int i = 0;
        String str2 = null;
        Preconditions.checkNotNull(zzkk);
        if (str == null || zzkk.zzast == null || zzkk.zzast.intValue() == 0) {
            return null;
        }
        List list;
        if (zzkk.zzast.intValue() == 6) {
            if (zzkk.zzasw == null || zzkk.zzasw.length == 0) {
                return null;
            }
        } else if (zzkk.zzasu == null) {
            return null;
        }
        int intValue = zzkk.zzast.intValue();
        boolean z = zzkk.zzasv != null && zzkk.zzasv.booleanValue();
        String toUpperCase = (z || intValue == 1 || intValue == 6) ? zzkk.zzasu : zzkk.zzasu.toUpperCase(Locale.ENGLISH);
        if (zzkk.zzasw == null) {
            list = null;
        } else {
            String[] strArr = zzkk.zzasw;
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
    @com.google.android.gms.common.util.VisibleForTesting
    private static java.lang.Boolean zza(java.math.BigDecimal r10, com.google.android.gms.internal.measurement.zzki r11, double r12) {
        /*
        r8 = 4;
        r7 = -1;
        r1 = 0;
        r0 = 1;
        r2 = 0;
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r11);
        r3 = r11.zzasl;
        if (r3 == 0) goto L_0x0014;
    L_0x000c:
        r3 = r11.zzasl;
        r3 = r3.intValue();
        if (r3 != 0) goto L_0x0016;
    L_0x0014:
        r0 = r2;
    L_0x0015:
        return r0;
    L_0x0016:
        r3 = r11.zzasl;
        r3 = r3.intValue();
        if (r3 != r8) goto L_0x0028;
    L_0x001e:
        r3 = r11.zzaso;
        if (r3 == 0) goto L_0x0026;
    L_0x0022:
        r3 = r11.zzasp;
        if (r3 != 0) goto L_0x002e;
    L_0x0026:
        r0 = r2;
        goto L_0x0015;
    L_0x0028:
        r3 = r11.zzasn;
        if (r3 != 0) goto L_0x002e;
    L_0x002c:
        r0 = r2;
        goto L_0x0015;
    L_0x002e:
        r3 = r11.zzasl;
        r6 = r3.intValue();
        r3 = r11.zzasl;
        r3 = r3.intValue();
        if (r3 != r8) goto L_0x0066;
    L_0x003c:
        r3 = r11.zzaso;
        r3 = com.google.android.gms.internal.measurement.zzkc.zzcj(r3);
        if (r3 == 0) goto L_0x004c;
    L_0x0044:
        r3 = r11.zzasp;
        r3 = com.google.android.gms.internal.measurement.zzkc.zzcj(r3);
        if (r3 != 0) goto L_0x004e;
    L_0x004c:
        r0 = r2;
        goto L_0x0015;
    L_0x004e:
        r4 = new java.math.BigDecimal;	 Catch:{ NumberFormatException -> 0x0063 }
        r3 = r11.zzaso;	 Catch:{ NumberFormatException -> 0x0063 }
        r4.<init>(r3);	 Catch:{ NumberFormatException -> 0x0063 }
        r3 = new java.math.BigDecimal;	 Catch:{ NumberFormatException -> 0x0063 }
        r5 = r11.zzasp;	 Catch:{ NumberFormatException -> 0x0063 }
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
        r3 = r11.zzasn;
        r3 = com.google.android.gms.internal.measurement.zzkc.zzcj(r3);
        if (r3 != 0) goto L_0x0070;
    L_0x006e:
        r0 = r2;
        goto L_0x0015;
    L_0x0070:
        r3 = new java.math.BigDecimal;	 Catch:{ NumberFormatException -> 0x007b }
        r4 = r11.zzasn;	 Catch:{ NumberFormatException -> 0x007b }
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
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzeb.zza(java.math.BigDecimal, com.google.android.gms.internal.measurement.zzki, double):java.lang.Boolean");
    }

    final zzko[] zza(String str, zzkp[] zzkpArr, zzku[] zzkuArr) {
        int intValue;
        BitSet bitSet;
        BitSet bitSet2;
        int i;
        zzhh zzje;
        int i2;
        int length;
        Map map;
        Map map2;
        zzko zzko;
        Preconditions.checkNotEmpty(str);
        HashSet hashSet = new HashSet();
        C0464a c0464a = new C0464a();
        Map c0464a2 = new C0464a();
        C0464a c0464a3 = new C0464a();
        Map zzbe = zzje().zzbe(str);
        if (zzbe != null) {
            for (Integer intValue2 : zzbe.keySet()) {
                intValue = intValue2.intValue();
                zzkt zzkt = (zzkt) zzbe.get(Integer.valueOf(intValue));
                bitSet = (BitSet) c0464a2.get(Integer.valueOf(intValue));
                bitSet2 = (BitSet) c0464a3.get(Integer.valueOf(intValue));
                if (bitSet == null) {
                    bitSet = new BitSet();
                    c0464a2.put(Integer.valueOf(intValue), bitSet);
                    bitSet2 = new BitSet();
                    c0464a3.put(Integer.valueOf(intValue), bitSet2);
                }
                for (i = 0; i < (zzkt.zzauw.length << 6); i++) {
                    if (zzkc.zza(zzkt.zzauw, i)) {
                        zzgf().zziz().zze("Filter already evaluated. audience ID, filter ID", Integer.valueOf(intValue), Integer.valueOf(i));
                        bitSet2.set(i);
                        if (zzkc.zza(zzkt.zzaux, i)) {
                            bitSet.set(i);
                        }
                    }
                }
                zzko zzko2 = new zzko();
                c0464a.put(Integer.valueOf(intValue), zzko2);
                zzko2.zzatk = Boolean.valueOf(false);
                zzko2.zzatj = zzkt;
                zzko2.zzati = new zzkt();
                zzko2.zzati.zzaux = zzkc.zza(bitSet);
                zzko2.zzati.zzauw = zzkc.zza(bitSet2);
            }
        }
        if (zzkpArr != null) {
            zzkp zzkp = null;
            long j = 0;
            Long l = null;
            C0464a c0464a4 = new C0464a();
            for (zzkp zzkp2 : zzkpArr) {
                zzkq[] zzkqArr;
                String str2;
                Long l2;
                long j2;
                zzkp zzkp3;
                zzes zzf;
                zzes zzes;
                long j3;
                int intValue3;
                BitSet bitSet3;
                BitSet bitSet4;
                String str3 = zzkp2.name;
                zzkq[] zzkqArr2 = zzkp2.zzatm;
                if (zzgh().zzd(str, zzey.zzaic)) {
                    int length2;
                    zzkq zzkq;
                    zzjc();
                    Long l3 = (Long) zzjy.zzb(zzkp2, "_eid");
                    Object obj = l3 != null ? 1 : null;
                    Object obj2 = (obj == null || !str3.equals("_ep")) ? null : 1;
                    if (obj2 != null) {
                        zzjc();
                        str3 = (String) zzjy.zzb(zzkp2, "_en");
                        if (TextUtils.isEmpty(str3)) {
                            zzgf().zzis().zzg("Extra parameter without an event name. eventId", l3);
                        } else {
                            Long l4;
                            int i3;
                            if (zzkp == null || l == null || l3.longValue() != l.longValue()) {
                                Pair zza = zzje().zza(str, l3);
                                if (zza == null || zza.first == null) {
                                    zzgf().zzis().zze("Extra parameter without existing main event. eventName, eventId", str3, l3);
                                } else {
                                    zzkp zzkp4 = (zzkp) zza.first;
                                    j = ((Long) zza.second).longValue();
                                    zzjc();
                                    l4 = (Long) zzjy.zzb(zzkp4, "_eid");
                                    zzkp = zzkp4;
                                }
                            } else {
                                l4 = l;
                            }
                            j--;
                            if (j <= 0) {
                                zzje = zzje();
                                zzje.zzab();
                                zzje.zzgf().zziz().zzg("Clearing complex main event info. appId", str);
                                try {
                                    zzje.getWritableDatabase().execSQL("delete from main_event_params where app_id=?", new String[]{str});
                                } catch (SQLiteException e) {
                                    zzje.zzgf().zzis().zzg("Error clearing complex main event", e);
                                }
                            } else {
                                zzje().zza(str, l3, j, zzkp);
                            }
                            zzkq[] zzkqArr3 = new zzkq[(zzkp.zzatm.length + zzkqArr2.length)];
                            i2 = 0;
                            zzkq[] zzkqArr4 = zzkp.zzatm;
                            length2 = zzkqArr4.length;
                            i = 0;
                            while (i < length2) {
                                zzkq = zzkqArr4[i];
                                zzjc();
                                if (zzjy.zza(zzkp2, zzkq.name) == null) {
                                    i3 = i2 + 1;
                                    zzkqArr3[i2] = zzkq;
                                } else {
                                    i3 = i2;
                                }
                                i++;
                                i2 = i3;
                            }
                            if (i2 > 0) {
                                length = zzkqArr2.length;
                                i3 = 0;
                                while (i3 < length) {
                                    i = i2 + 1;
                                    zzkqArr3[i2] = zzkqArr2[i3];
                                    i3++;
                                    i2 = i;
                                }
                                zzkqArr = i2 == zzkqArr3.length ? zzkqArr3 : (zzkq[]) Arrays.copyOf(zzkqArr3, i2);
                                str2 = str3;
                                l2 = l4;
                                j2 = j;
                                zzkp3 = zzkp;
                            } else {
                                zzgf().zziv().zzg("No unique parameters in main event. eventName", str3);
                                zzkqArr = zzkqArr2;
                                str2 = str3;
                                l2 = l4;
                                j2 = j;
                                zzkp3 = zzkp;
                            }
                        }
                    } else if (obj != null) {
                        zzjc();
                        Long valueOf = Long.valueOf(0);
                        l = zzjy.zzb(zzkp2, "_epc");
                        if (l != null) {
                            valueOf = l;
                        }
                        j = valueOf.longValue();
                        if (j <= 0) {
                            zzgf().zziv().zzg("Complex event with zero extra param count. eventName", str3);
                            zzkqArr = zzkqArr2;
                            str2 = str3;
                            l2 = l3;
                            j2 = j;
                            zzkp3 = zzkp2;
                        } else {
                            zzje().zza(str, l3, j, zzkp2);
                            zzkqArr = zzkqArr2;
                            str2 = str3;
                            l2 = l3;
                            j2 = j;
                            zzkp3 = zzkp2;
                        }
                    }
                    zzf = zzje().zzf(str, zzkp2.name);
                    if (zzf != null) {
                        zzgf().zziv().zze("Event aggregate wasn't created during raw event logging. appId, event", zzfh.zzbl(str), zzgb().zzbi(str2));
                        zzes = new zzes(str, zzkp2.name, 1, 1, zzkp2.zzatn.longValue(), 0, null, null, null);
                    } else {
                        zzes = zzf.zzii();
                    }
                    zzje().zza(zzes);
                    j3 = zzes.zzafs;
                    map = (Map) c0464a4.get(str2);
                    if (map != null) {
                        map = zzje().zzk(str, str2);
                        if (map == null) {
                            map = new C0464a();
                        }
                        c0464a4.put(str2, map);
                        map2 = map;
                    } else {
                        map2 = map;
                    }
                    for (Integer intValue22 : r7.keySet()) {
                        intValue3 = intValue22.intValue();
                        if (hashSet.contains(Integer.valueOf(intValue3))) {
                            bitSet = (BitSet) c0464a2.get(Integer.valueOf(intValue3));
                            bitSet2 = (BitSet) c0464a3.get(Integer.valueOf(intValue3));
                            if (((zzko) c0464a.get(Integer.valueOf(intValue3))) != null) {
                                zzko = new zzko();
                                c0464a.put(Integer.valueOf(intValue3), zzko);
                                zzko.zzatk = Boolean.valueOf(true);
                                bitSet = new BitSet();
                                c0464a2.put(Integer.valueOf(intValue3), bitSet);
                                bitSet2 = new BitSet();
                                c0464a3.put(Integer.valueOf(intValue3), bitSet2);
                                bitSet3 = bitSet2;
                                bitSet4 = bitSet;
                            } else {
                                bitSet3 = bitSet2;
                                bitSet4 = bitSet;
                            }
                            for (zzkg zzkg : (List) r7.get(Integer.valueOf(intValue3))) {
                                if (zzgf().isLoggable(2)) {
                                    zzgf().zziz().zzd("Evaluating filter. audience, filter, event", Integer.valueOf(intValue3), zzkg.zzasb, zzgb().zzbi(zzkg.zzasc));
                                    zzgf().zziz().zzg("Filter definition", zzjc().zza(zzkg));
                                }
                                if (zzkg.zzasb != null || zzkg.zzasb.intValue() > 256) {
                                    zzgf().zziv().zze("Invalid event filter ID. appId, id", zzfh.zzbl(str), String.valueOf(zzkg.zzasb));
                                } else if (bitSet4.get(zzkg.zzasb.intValue())) {
                                    zzgf().zziz().zze("Event filter already evaluated true. audience ID, filter ID", Integer.valueOf(intValue3), zzkg.zzasb);
                                } else {
                                    Boolean zza2;
                                    zzfj zziz;
                                    String str4;
                                    Boolean bool;
                                    if (zzkg.zzasf != null) {
                                        zza2 = zza(j3, zzkg.zzasf);
                                        if (zza2 == null) {
                                            zza2 = null;
                                        } else if (!zza2.booleanValue()) {
                                            zza2 = Boolean.valueOf(false);
                                        }
                                        zziz = zzgf().zziz();
                                        str4 = "Event filter result";
                                        if (zza2 != null) {
                                            obj = "null";
                                        } else {
                                            bool = zza2;
                                        }
                                        zziz.zzg(str4, obj);
                                        if (zza2 != null) {
                                            hashSet.add(Integer.valueOf(intValue3));
                                        } else {
                                            bitSet3.set(zzkg.zzasb.intValue());
                                            if (zza2.booleanValue()) {
                                                bitSet4.set(zzkg.zzasb.intValue());
                                            }
                                        }
                                    }
                                    Set hashSet2 = new HashSet();
                                    for (zzkh zzkh : zzkg.zzasd) {
                                        if (TextUtils.isEmpty(zzkh.zzask)) {
                                            zzgf().zziv().zzg("null or empty param name in filter. event", zzgb().zzbi(str2));
                                            zza2 = null;
                                            break;
                                        }
                                        hashSet2.add(zzkh.zzask);
                                    }
                                    C0464a c0464a5 = new C0464a();
                                    for (zzkq zzkq2 : r19) {
                                        if (hashSet2.contains(zzkq2.name)) {
                                            if (zzkq2.zzatq == null) {
                                                if (zzkq2.zzaro == null) {
                                                    if (zzkq2.zzajo == null) {
                                                        zzgf().zziv().zze("Unknown value for param. event, param", zzgb().zzbi(str2), zzgb().zzbj(zzkq2.name));
                                                        zza2 = null;
                                                        break;
                                                    }
                                                    c0464a5.put(zzkq2.name, zzkq2.zzajo);
                                                } else {
                                                    c0464a5.put(zzkq2.name, zzkq2.zzaro);
                                                }
                                            } else {
                                                c0464a5.put(zzkq2.name, zzkq2.zzatq);
                                            }
                                        }
                                    }
                                    for (zzkh zzkh2 : zzkg.zzasd) {
                                        boolean equals = Boolean.TRUE.equals(zzkh2.zzasj);
                                        String str5 = zzkh2.zzask;
                                        if (TextUtils.isEmpty(str5)) {
                                            zzgf().zziv().zzg("Event has empty param name. event", zzgb().zzbi(str2));
                                            zza2 = null;
                                            break;
                                        }
                                        Object obj3 = c0464a5.get(str5);
                                        if (obj3 instanceof Long) {
                                            if (zzkh2.zzasi == null) {
                                                zzgf().zziv().zze("No number filter for long param. event, param", zzgb().zzbi(str2), zzgb().zzbj(str5));
                                                zza2 = null;
                                                break;
                                            }
                                            zza2 = zza(((Long) obj3).longValue(), zzkh2.zzasi);
                                            if (zza2 == null) {
                                                zza2 = null;
                                                break;
                                            }
                                            if (((!zza2.booleanValue() ? 1 : 0) ^ equals) != 0) {
                                                zza2 = Boolean.valueOf(false);
                                                break;
                                            }
                                        } else if (obj3 instanceof Double) {
                                            if (zzkh2.zzasi == null) {
                                                zzgf().zziv().zze("No number filter for double param. event, param", zzgb().zzbi(str2), zzgb().zzbj(str5));
                                                zza2 = null;
                                                break;
                                            }
                                            zza2 = zza(((Double) obj3).doubleValue(), zzkh2.zzasi);
                                            if (zza2 == null) {
                                                zza2 = null;
                                                break;
                                            }
                                            if (((!zza2.booleanValue() ? 1 : 0) ^ equals) != 0) {
                                                zza2 = Boolean.valueOf(false);
                                                break;
                                            }
                                        } else if (obj3 instanceof String) {
                                            if (zzkh2.zzash == null) {
                                                if (zzkh2.zzasi != null) {
                                                    if (!zzkc.zzcj((String) obj3)) {
                                                        zzgf().zziv().zze("Invalid param value for number filter. event, param", zzgb().zzbi(str2), zzgb().zzbj(str5));
                                                        zza2 = null;
                                                        break;
                                                    }
                                                    zza2 = zza((String) obj3, zzkh2.zzasi);
                                                } else {
                                                    zzgf().zziv().zze("No filter for String param. event, param", zzgb().zzbi(str2), zzgb().zzbj(str5));
                                                    zza2 = null;
                                                    break;
                                                }
                                            }
                                            zza2 = zza((String) obj3, zzkh2.zzash);
                                            if (zza2 == null) {
                                                zza2 = null;
                                                break;
                                            }
                                            if (((!zza2.booleanValue() ? 1 : 0) ^ equals) != 0) {
                                                zza2 = Boolean.valueOf(false);
                                                break;
                                            }
                                        } else if (obj3 == null) {
                                            zzgf().zziz().zze("Missing param for filter. event, param", zzgb().zzbi(str2), zzgb().zzbj(str5));
                                            zza2 = Boolean.valueOf(false);
                                        } else {
                                            zzgf().zziv().zze("Unknown param type. event, param", zzgb().zzbi(str2), zzgb().zzbj(str5));
                                            zza2 = null;
                                        }
                                    }
                                    zza2 = Boolean.valueOf(true);
                                    zziz = zzgf().zziz();
                                    str4 = "Event filter result";
                                    if (zza2 != null) {
                                        bool = zza2;
                                    } else {
                                        obj = "null";
                                    }
                                    zziz.zzg(str4, obj);
                                    if (zza2 != null) {
                                        bitSet3.set(zzkg.zzasb.intValue());
                                        if (zza2.booleanValue()) {
                                            bitSet4.set(zzkg.zzasb.intValue());
                                        }
                                    } else {
                                        hashSet.add(Integer.valueOf(intValue3));
                                    }
                                }
                            }
                        } else {
                            zzgf().zziz().zzg("Skipping failed audience ID", Integer.valueOf(intValue3));
                        }
                    }
                    l = l2;
                    j = j2;
                    zzkp = zzkp3;
                }
                zzkqArr = zzkqArr2;
                str2 = str3;
                l2 = l;
                j2 = j;
                zzkp3 = zzkp;
                zzf = zzje().zzf(str, zzkp2.name);
                if (zzf != null) {
                    zzes = zzf.zzii();
                } else {
                    zzgf().zziv().zze("Event aggregate wasn't created during raw event logging. appId, event", zzfh.zzbl(str), zzgb().zzbi(str2));
                    zzes = new zzes(str, zzkp2.name, 1, 1, zzkp2.zzatn.longValue(), 0, null, null, null);
                }
                zzje().zza(zzes);
                j3 = zzes.zzafs;
                map = (Map) c0464a4.get(str2);
                if (map != null) {
                    map2 = map;
                } else {
                    map = zzje().zzk(str, str2);
                    if (map == null) {
                        map = new C0464a();
                    }
                    c0464a4.put(str2, map);
                    map2 = map;
                }
                while (r11.hasNext()) {
                    intValue3 = intValue22.intValue();
                    if (hashSet.contains(Integer.valueOf(intValue3))) {
                        bitSet = (BitSet) c0464a2.get(Integer.valueOf(intValue3));
                        bitSet2 = (BitSet) c0464a3.get(Integer.valueOf(intValue3));
                        if (((zzko) c0464a.get(Integer.valueOf(intValue3))) != null) {
                            bitSet3 = bitSet2;
                            bitSet4 = bitSet;
                        } else {
                            zzko = new zzko();
                            c0464a.put(Integer.valueOf(intValue3), zzko);
                            zzko.zzatk = Boolean.valueOf(true);
                            bitSet = new BitSet();
                            c0464a2.put(Integer.valueOf(intValue3), bitSet);
                            bitSet2 = new BitSet();
                            c0464a3.put(Integer.valueOf(intValue3), bitSet2);
                            bitSet3 = bitSet2;
                            bitSet4 = bitSet;
                        }
                        for (zzkg zzkg2 : (List) r7.get(Integer.valueOf(intValue3))) {
                            if (zzgf().isLoggable(2)) {
                                zzgf().zziz().zzd("Evaluating filter. audience, filter, event", Integer.valueOf(intValue3), zzkg2.zzasb, zzgb().zzbi(zzkg2.zzasc));
                                zzgf().zziz().zzg("Filter definition", zzjc().zza(zzkg2));
                            }
                            if (zzkg2.zzasb != null) {
                            }
                            zzgf().zziv().zze("Invalid event filter ID. appId, id", zzfh.zzbl(str), String.valueOf(zzkg2.zzasb));
                        }
                    } else {
                        zzgf().zziz().zzg("Skipping failed audience ID", Integer.valueOf(intValue3));
                    }
                }
                l = l2;
                j = j2;
                zzkp = zzkp3;
            }
        }
        if (zzkuArr != null) {
            Map c0464a6 = new C0464a();
            for (zzku zzku : zzkuArr) {
                map = (Map) c0464a6.get(zzku.name);
                if (map == null) {
                    map = zzje().zzl(str, zzku.name);
                    if (map == null) {
                        map = new C0464a();
                    }
                    c0464a6.put(zzku.name, map);
                    map2 = map;
                } else {
                    map2 = map;
                }
                for (Integer intValue222 : r7.keySet()) {
                    length = intValue222.intValue();
                    if (hashSet.contains(Integer.valueOf(length))) {
                        zzgf().zziz().zzg("Skipping failed audience ID", Integer.valueOf(length));
                    } else {
                        bitSet = (BitSet) c0464a2.get(Integer.valueOf(length));
                        bitSet2 = (BitSet) c0464a3.get(Integer.valueOf(length));
                        if (((zzko) c0464a.get(Integer.valueOf(length))) == null) {
                            zzko = new zzko();
                            c0464a.put(Integer.valueOf(length), zzko);
                            zzko.zzatk = Boolean.valueOf(true);
                            bitSet = new BitSet();
                            c0464a2.put(Integer.valueOf(length), bitSet);
                            bitSet2 = new BitSet();
                            c0464a3.put(Integer.valueOf(length), bitSet2);
                        }
                        for (zzkj zzkj : (List) r7.get(Integer.valueOf(length))) {
                            if (zzgf().isLoggable(2)) {
                                zzgf().zziz().zzd("Evaluating filter. audience, filter, property", Integer.valueOf(length), zzkj.zzasb, zzgb().zzbk(zzkj.zzasr));
                                zzgf().zziz().zzg("Filter definition", zzjc().zza(zzkj));
                            }
                            if (zzkj.zzasb == null || zzkj.zzasb.intValue() > 256) {
                                zzgf().zziv().zze("Invalid property filter ID. appId, id", zzfh.zzbl(str), String.valueOf(zzkj.zzasb));
                                hashSet.add(Integer.valueOf(length));
                                break;
                            } else if (bitSet.get(zzkj.zzasb.intValue())) {
                                zzgf().zziz().zze("Property filter already evaluated true. audience ID, filter ID", Integer.valueOf(length), zzkj.zzasb);
                            } else {
                                Boolean bool2;
                                Object obj4;
                                zzkh zzkh3 = zzkj.zzass;
                                if (zzkh3 == null) {
                                    zzgf().zziv().zzg("Missing property filter. property", zzgb().zzbk(zzku.name));
                                    bool2 = null;
                                } else {
                                    boolean equals2 = Boolean.TRUE.equals(zzkh3.zzasj);
                                    if (zzku.zzatq != null) {
                                        if (zzkh3.zzasi == null) {
                                            zzgf().zziv().zzg("No number filter for long property. property", zzgb().zzbk(zzku.name));
                                            bool2 = null;
                                        } else {
                                            bool2 = zza(zza(zzku.zzatq.longValue(), zzkh3.zzasi), equals2);
                                        }
                                    } else if (zzku.zzaro != null) {
                                        if (zzkh3.zzasi == null) {
                                            zzgf().zziv().zzg("No number filter for double property. property", zzgb().zzbk(zzku.name));
                                            bool2 = null;
                                        } else {
                                            bool2 = zza(zza(zzku.zzaro.doubleValue(), zzkh3.zzasi), equals2);
                                        }
                                    } else if (zzku.zzajo == null) {
                                        zzgf().zziv().zzg("User property has no value, property", zzgb().zzbk(zzku.name));
                                        bool2 = null;
                                    } else if (zzkh3.zzash == null) {
                                        if (zzkh3.zzasi == null) {
                                            zzgf().zziv().zzg("No string or number filter defined. property", zzgb().zzbk(zzku.name));
                                        } else if (zzkc.zzcj(zzku.zzajo)) {
                                            bool2 = zza(zza(zzku.zzajo, zzkh3.zzasi), equals2);
                                        } else {
                                            zzgf().zziv().zze("Invalid user property value for Numeric number filter. property, value", zzgb().zzbk(zzku.name), zzku.zzajo);
                                        }
                                        bool2 = null;
                                    } else {
                                        bool2 = zza(zza(zzku.zzajo, zzkh3.zzash), equals2);
                                    }
                                }
                                zzfj zziz2 = zzgf().zziz();
                                String str6 = "Property filter result";
                                if (bool2 == null) {
                                    obj4 = "null";
                                } else {
                                    Boolean bool3 = bool2;
                                }
                                zziz2.zzg(str6, obj4);
                                if (bool2 == null) {
                                    hashSet.add(Integer.valueOf(length));
                                } else {
                                    bitSet2.set(zzkj.zzasb.intValue());
                                    if (bool2.booleanValue()) {
                                        bitSet.set(zzkj.zzasb.intValue());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        zzko[] zzkoArr = new zzko[c0464a2.size()];
        i2 = 0;
        for (Integer intValue2222 : c0464a2.keySet()) {
            intValue = intValue2222.intValue();
            if (!hashSet.contains(Integer.valueOf(intValue))) {
                zzko = (zzko) c0464a.get(Integer.valueOf(intValue));
                zzko2 = zzko == null ? new zzko() : zzko;
                int i4 = i2 + 1;
                zzkoArr[i2] = zzko2;
                zzko2.zzarx = Integer.valueOf(intValue);
                zzko2.zzati = new zzkt();
                zzko2.zzati.zzaux = zzkc.zza((BitSet) c0464a2.get(Integer.valueOf(intValue)));
                zzko2.zzati.zzauw = zzkc.zza((BitSet) c0464a3.get(Integer.valueOf(intValue)));
                zzje = zzje();
                zzacg zzacg = zzko2.zzati;
                zzje.zzch();
                zzje.zzab();
                Preconditions.checkNotEmpty(str);
                Preconditions.checkNotNull(zzacg);
                try {
                    byte[] bArr = new byte[zzacg.zzvv()];
                    zzaby zzb = zzaby.zzb(bArr, 0, bArr.length);
                    zzacg.zza(zzb);
                    zzb.zzvn();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("app_id", str);
                    contentValues.put("audience_id", Integer.valueOf(intValue));
                    contentValues.put("current_results", bArr);
                    try {
                        if (zzje.getWritableDatabase().insertWithOnConflict("audience_filter_values", null, contentValues, 5) == -1) {
                            zzje.zzgf().zzis().zzg("Failed to insert filter results (got -1). appId", zzfh.zzbl(str));
                        }
                        i2 = i4;
                    } catch (SQLiteException e2) {
                        zzje.zzgf().zzis().zze("Error storing filter results. appId", zzfh.zzbl(str), e2);
                        i2 = i4;
                    }
                } catch (IOException e3) {
                    zzje.zzgf().zzis().zze("Configuration loss. Failed to serialize filter results. appId", zzfh.zzbl(str), e3);
                    i2 = i4;
                }
            }
        }
        return (zzko[]) Arrays.copyOf(zzkoArr, i2);
    }

    protected final boolean zzhh() {
        return false;
    }
}
