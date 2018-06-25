package com.google.android.gms.internal.measurement;

import android.content.Context;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import java.io.IOException;
import org.telegram.messenger.NotificationBadge.NewHtcHomeBadger;

public final class zzjy extends zzjr {
    zzjy(zzjs zzjs) {
        super(zzjs);
    }

    public static zzkq zza(zzkp zzkp, String str) {
        for (zzkq zzkq : zzkp.zzatm) {
            if (zzkq.name.equals(str)) {
                return zzkq;
            }
        }
        return null;
    }

    private static void zza(StringBuilder stringBuilder, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            stringBuilder.append("  ");
        }
    }

    private final void zza(StringBuilder stringBuilder, int i, zzkh zzkh) {
        if (zzkh != null) {
            zza(stringBuilder, i);
            stringBuilder.append("filter {\n");
            zza(stringBuilder, i, "complement", zzkh.zzasj);
            zza(stringBuilder, i, "param_name", zzgb().zzbj(zzkh.zzask));
            int i2 = i + 1;
            String str = "string_filter";
            zzkk zzkk = zzkh.zzash;
            if (zzkk != null) {
                zza(stringBuilder, i2);
                stringBuilder.append(str);
                stringBuilder.append(" {\n");
                if (zzkk.zzast != null) {
                    Object obj = "UNKNOWN_MATCH_TYPE";
                    switch (zzkk.zzast.intValue()) {
                        case 1:
                            obj = "REGEXP";
                            break;
                        case 2:
                            obj = "BEGINS_WITH";
                            break;
                        case 3:
                            obj = "ENDS_WITH";
                            break;
                        case 4:
                            obj = "PARTIAL";
                            break;
                        case 5:
                            obj = "EXACT";
                            break;
                        case 6:
                            obj = "IN_LIST";
                            break;
                    }
                    zza(stringBuilder, i2, "match_type", obj);
                }
                zza(stringBuilder, i2, "expression", zzkk.zzasu);
                zza(stringBuilder, i2, "case_sensitive", zzkk.zzasv);
                if (zzkk.zzasw.length > 0) {
                    zza(stringBuilder, i2 + 1);
                    stringBuilder.append("expression_list {\n");
                    for (String str2 : zzkk.zzasw) {
                        zza(stringBuilder, i2 + 2);
                        stringBuilder.append(str2);
                        stringBuilder.append("\n");
                    }
                    stringBuilder.append("}\n");
                }
                zza(stringBuilder, i2);
                stringBuilder.append("}\n");
            }
            zza(stringBuilder, i + 1, "number_filter", zzkh.zzasi);
            zza(stringBuilder, i);
            stringBuilder.append("}\n");
        }
    }

    private final void zza(StringBuilder stringBuilder, int i, String str, zzki zzki) {
        if (zzki != null) {
            zza(stringBuilder, i);
            stringBuilder.append(str);
            stringBuilder.append(" {\n");
            if (zzki.zzasl != null) {
                Object obj = "UNKNOWN_COMPARISON_TYPE";
                switch (zzki.zzasl.intValue()) {
                    case 1:
                        obj = "LESS_THAN";
                        break;
                    case 2:
                        obj = "GREATER_THAN";
                        break;
                    case 3:
                        obj = "EQUAL";
                        break;
                    case 4:
                        obj = "BETWEEN";
                        break;
                }
                zza(stringBuilder, i, "comparison_type", obj);
            }
            zza(stringBuilder, i, "match_as_float", zzki.zzasm);
            zza(stringBuilder, i, "comparison_value", zzki.zzasn);
            zza(stringBuilder, i, "min_comparison_value", zzki.zzaso);
            zza(stringBuilder, i, "max_comparison_value", zzki.zzasp);
            zza(stringBuilder, i);
            stringBuilder.append("}\n");
        }
    }

    private static void zza(StringBuilder stringBuilder, int i, String str, zzkt zzkt) {
        int i2 = 0;
        if (zzkt != null) {
            int i3;
            int i4;
            zza(stringBuilder, 3);
            stringBuilder.append(str);
            stringBuilder.append(" {\n");
            if (zzkt.zzaux != null) {
                zza(stringBuilder, 4);
                stringBuilder.append("results: ");
                long[] jArr = zzkt.zzaux;
                int length = jArr.length;
                i3 = 0;
                i4 = 0;
                while (i3 < length) {
                    Long valueOf = Long.valueOf(jArr[i3]);
                    int i5 = i4 + 1;
                    if (i4 != 0) {
                        stringBuilder.append(", ");
                    }
                    stringBuilder.append(valueOf);
                    i3++;
                    i4 = i5;
                }
                stringBuilder.append('\n');
            }
            if (zzkt.zzauw != null) {
                zza(stringBuilder, 4);
                stringBuilder.append("status: ");
                long[] jArr2 = zzkt.zzauw;
                int length2 = jArr2.length;
                i3 = 0;
                while (i2 < length2) {
                    Long valueOf2 = Long.valueOf(jArr2[i2]);
                    i4 = i3 + 1;
                    if (i3 != 0) {
                        stringBuilder.append(", ");
                    }
                    stringBuilder.append(valueOf2);
                    i2++;
                    i3 = i4;
                }
                stringBuilder.append('\n');
            }
            zza(stringBuilder, 3);
            stringBuilder.append("}\n");
        }
    }

    private static void zza(StringBuilder stringBuilder, int i, String str, Object obj) {
        if (obj != null) {
            zza(stringBuilder, i + 1);
            stringBuilder.append(str);
            stringBuilder.append(": ");
            stringBuilder.append(obj);
            stringBuilder.append('\n');
        }
    }

    static zzkq[] zza(zzkq[] zzkqArr, String str, Object obj) {
        for (zzkq zzkq : zzkqArr) {
            if (str.equals(zzkq.name)) {
                zzkq.zzatq = null;
                zzkq.zzajo = null;
                zzkq.zzaro = null;
                if (obj instanceof Long) {
                    zzkq.zzatq = (Long) obj;
                    return zzkqArr;
                } else if (obj instanceof String) {
                    zzkq.zzajo = (String) obj;
                    return zzkqArr;
                } else if (!(obj instanceof Double)) {
                    return zzkqArr;
                } else {
                    zzkq.zzaro = (Double) obj;
                    return zzkqArr;
                }
            }
        }
        Object obj2 = new zzkq[(zzkqArr.length + 1)];
        System.arraycopy(zzkqArr, 0, obj2, 0, zzkqArr.length);
        zzkq zzkq2 = new zzkq();
        zzkq2.name = str;
        if (obj instanceof Long) {
            zzkq2.zzatq = (Long) obj;
        } else if (obj instanceof String) {
            zzkq2.zzajo = (String) obj;
        } else if (obj instanceof Double) {
            zzkq2.zzaro = (Double) obj;
        }
        obj2[zzkqArr.length] = zzkq2;
        return obj2;
    }

    public static Object zzb(zzkp zzkp, String str) {
        zzkq zza = zza(zzkp, str);
        if (zza != null) {
            if (zza.zzajo != null) {
                return zza.zzajo;
            }
            if (zza.zzatq != null) {
                return zza.zzatq;
            }
            if (zza.zzaro != null) {
                return zza.zzaro;
            }
        }
        return null;
    }

    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    protected final String zza(zzkg zzkg) {
        int i = 0;
        if (zzkg == null) {
            return "null";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nevent_filter {\n");
        zza(stringBuilder, 0, "filter_id", zzkg.zzasb);
        zza(stringBuilder, 0, "event_name", zzgb().zzbi(zzkg.zzasc));
        zza(stringBuilder, 1, "event_count_filter", zzkg.zzasf);
        stringBuilder.append("  filters {\n");
        zzkh[] zzkhArr = zzkg.zzasd;
        int length = zzkhArr.length;
        while (i < length) {
            zza(stringBuilder, 2, zzkhArr[i]);
            i++;
        }
        zza(stringBuilder, 1);
        stringBuilder.append("}\n}\n");
        return stringBuilder.toString();
    }

    protected final String zza(zzkj zzkj) {
        if (zzkj == null) {
            return "null";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nproperty_filter {\n");
        zza(stringBuilder, 0, "filter_id", zzkj.zzasb);
        zza(stringBuilder, 0, "property_name", zzgb().zzbk(zzkj.zzasr));
        zza(stringBuilder, 1, zzkj.zzass);
        stringBuilder.append("}\n");
        return stringBuilder.toString();
    }

    public final void zza(zzkq zzkq, Object obj) {
        Preconditions.checkNotNull(obj);
        zzkq.zzajo = null;
        zzkq.zzatq = null;
        zzkq.zzaro = null;
        if (obj instanceof String) {
            zzkq.zzajo = (String) obj;
        } else if (obj instanceof Long) {
            zzkq.zzatq = (Long) obj;
        } else if (obj instanceof Double) {
            zzkq.zzaro = (Double) obj;
        } else {
            zzgf().zzis().zzg("Ignoring invalid (type) event param value", obj);
        }
    }

    public final void zza(zzku zzku, Object obj) {
        Preconditions.checkNotNull(obj);
        zzku.zzajo = null;
        zzku.zzatq = null;
        zzku.zzaro = null;
        if (obj instanceof String) {
            zzku.zzajo = (String) obj;
        } else if (obj instanceof Long) {
            zzku.zzatq = (Long) obj;
        } else if (obj instanceof Double) {
            zzku.zzaro = (Double) obj;
        } else {
            zzgf().zzis().zzg("Ignoring invalid (type) user attribute value", obj);
        }
    }

    public final byte[] zza(zzkr zzkr) {
        try {
            byte[] bArr = new byte[zzkr.zzvv()];
            zzaby zzb = zzaby.zzb(bArr, 0, bArr.length);
            zzkr.zza(zzb);
            zzb.zzvn();
            return bArr;
        } catch (IOException e) {
            zzgf().zzis().zzg("Data loss. Failed to serialize batch", e);
            return null;
        }
    }

    public final /* bridge */ /* synthetic */ void zzab() {
        super.zzab();
    }

    protected final String zzb(zzkr zzkr) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nbatch {\n");
        if (zzkr.zzatr != null) {
            for (zzks zzks : zzkr.zzatr) {
                if (!(zzks == null || zzks == null)) {
                    zza(stringBuilder, 1);
                    stringBuilder.append("bundle {\n");
                    zza(stringBuilder, 1, "protocol_version", zzks.zzatt);
                    zza(stringBuilder, 1, "platform", zzks.zzaub);
                    zza(stringBuilder, 1, "gmp_version", zzks.zzauf);
                    zza(stringBuilder, 1, "uploading_gmp_version", zzks.zzaug);
                    zza(stringBuilder, 1, "config_version", zzks.zzaur);
                    zza(stringBuilder, 1, "gmp_app_id", zzks.zzadm);
                    zza(stringBuilder, 1, "app_id", zzks.zzti);
                    zza(stringBuilder, 1, "app_version", zzks.zzth);
                    zza(stringBuilder, 1, "app_version_major", zzks.zzaun);
                    zza(stringBuilder, 1, "firebase_instance_id", zzks.zzado);
                    zza(stringBuilder, 1, "dev_cert_hash", zzks.zzauj);
                    zza(stringBuilder, 1, "app_store", zzks.zzadt);
                    zza(stringBuilder, 1, "upload_timestamp_millis", zzks.zzatw);
                    zza(stringBuilder, 1, "start_timestamp_millis", zzks.zzatx);
                    zza(stringBuilder, 1, "end_timestamp_millis", zzks.zzaty);
                    zza(stringBuilder, 1, "previous_bundle_start_timestamp_millis", zzks.zzatz);
                    zza(stringBuilder, 1, "previous_bundle_end_timestamp_millis", zzks.zzaua);
                    zza(stringBuilder, 1, "app_instance_id", zzks.zzadl);
                    zza(stringBuilder, 1, "resettable_device_id", zzks.zzauh);
                    zza(stringBuilder, 1, "device_id", zzks.zzauq);
                    zza(stringBuilder, 1, "ds_id", zzks.zzaut);
                    zza(stringBuilder, 1, "limited_ad_tracking", zzks.zzaui);
                    zza(stringBuilder, 1, "os_version", zzks.zzauc);
                    zza(stringBuilder, 1, "device_model", zzks.zzaud);
                    zza(stringBuilder, 1, "user_default_language", zzks.zzafo);
                    zza(stringBuilder, 1, "time_zone_offset_minutes", zzks.zzaue);
                    zza(stringBuilder, 1, "bundle_sequential_index", zzks.zzauk);
                    zza(stringBuilder, 1, "service_upload", zzks.zzaul);
                    zza(stringBuilder, 1, "health_monitor", zzks.zzaek);
                    if (!(zzks.zzaus == null || zzks.zzaus.longValue() == 0)) {
                        zza(stringBuilder, 1, "android_id", zzks.zzaus);
                    }
                    if (zzks.zzauv != null) {
                        zza(stringBuilder, 1, "retry_counter", zzks.zzauv);
                    }
                    zzku[] zzkuArr = zzks.zzatv;
                    if (zzkuArr != null) {
                        for (zzku zzku : zzkuArr) {
                            if (zzku != null) {
                                zza(stringBuilder, 2);
                                stringBuilder.append("user_property {\n");
                                zza(stringBuilder, 2, "set_timestamp_millis", zzku.zzauz);
                                zza(stringBuilder, 2, "name", zzgb().zzbk(zzku.name));
                                zza(stringBuilder, 2, "string_value", zzku.zzajo);
                                zza(stringBuilder, 2, "int_value", zzku.zzatq);
                                zza(stringBuilder, 2, "double_value", zzku.zzaro);
                                zza(stringBuilder, 2);
                                stringBuilder.append("}\n");
                            }
                        }
                    }
                    zzko[] zzkoArr = zzks.zzaum;
                    if (zzkoArr != null) {
                        for (zzko zzko : zzkoArr) {
                            if (zzko != null) {
                                zza(stringBuilder, 2);
                                stringBuilder.append("audience_membership {\n");
                                zza(stringBuilder, 2, "audience_id", zzko.zzarx);
                                zza(stringBuilder, 2, "new_audience", zzko.zzatk);
                                zza(stringBuilder, 2, "current_data", zzko.zzati);
                                zza(stringBuilder, 2, "previous_data", zzko.zzatj);
                                zza(stringBuilder, 2);
                                stringBuilder.append("}\n");
                            }
                        }
                    }
                    zzkp[] zzkpArr = zzks.zzatu;
                    if (zzkpArr != null) {
                        for (zzkp zzkp : zzkpArr) {
                            if (zzkp != null) {
                                zza(stringBuilder, 2);
                                stringBuilder.append("event {\n");
                                zza(stringBuilder, 2, "name", zzgb().zzbi(zzkp.name));
                                zza(stringBuilder, 2, "timestamp_millis", zzkp.zzatn);
                                zza(stringBuilder, 2, "previous_timestamp_millis", zzkp.zzato);
                                zza(stringBuilder, 2, NewHtcHomeBadger.COUNT, zzkp.count);
                                zzkq[] zzkqArr = zzkp.zzatm;
                                if (zzkqArr != null) {
                                    for (zzkq zzkq : zzkqArr) {
                                        if (zzkq != null) {
                                            zza(stringBuilder, 3);
                                            stringBuilder.append("param {\n");
                                            zza(stringBuilder, 3, "name", zzgb().zzbj(zzkq.name));
                                            zza(stringBuilder, 3, "string_value", zzkq.zzajo);
                                            zza(stringBuilder, 3, "int_value", zzkq.zzatq);
                                            zza(stringBuilder, 3, "double_value", zzkq.zzaro);
                                            zza(stringBuilder, 3);
                                            stringBuilder.append("}\n");
                                        }
                                    }
                                }
                                zza(stringBuilder, 2);
                                stringBuilder.append("}\n");
                            }
                        }
                    }
                    zza(stringBuilder, 1);
                    stringBuilder.append("}\n");
                }
            }
        }
        stringBuilder.append("}\n");
        return stringBuilder.toString();
    }

    public final /* bridge */ /* synthetic */ Clock zzbt() {
        return super.zzbt();
    }

    public final /* bridge */ /* synthetic */ void zzfr() {
        super.zzfr();
    }

    public final /* bridge */ /* synthetic */ void zzfs() {
        super.zzfs();
    }

    public final /* bridge */ /* synthetic */ void zzft() {
        super.zzft();
    }

    public final /* bridge */ /* synthetic */ zzdu zzfu() {
        return super.zzfu();
    }

    public final /* bridge */ /* synthetic */ zzhl zzfv() {
        return super.zzfv();
    }

    public final /* bridge */ /* synthetic */ zzfc zzfw() {
        return super.zzfw();
    }

    public final /* bridge */ /* synthetic */ zzeq zzfx() {
        return super.zzfx();
    }

    public final /* bridge */ /* synthetic */ zzij zzfy() {
        return super.zzfy();
    }

    public final /* bridge */ /* synthetic */ zzig zzfz() {
        return super.zzfz();
    }

    public final /* bridge */ /* synthetic */ zzfd zzga() {
        return super.zzga();
    }

    public final /* bridge */ /* synthetic */ zzff zzgb() {
        return super.zzgb();
    }

    public final /* bridge */ /* synthetic */ zzkc zzgc() {
        return super.zzgc();
    }

    public final /* bridge */ /* synthetic */ zzji zzgd() {
        return super.zzgd();
    }

    public final /* bridge */ /* synthetic */ zzgh zzge() {
        return super.zzge();
    }

    public final /* bridge */ /* synthetic */ zzfh zzgf() {
        return super.zzgf();
    }

    public final /* bridge */ /* synthetic */ zzfs zzgg() {
        return super.zzgg();
    }

    public final /* bridge */ /* synthetic */ zzeg zzgh() {
        return super.zzgh();
    }

    public final /* bridge */ /* synthetic */ zzec zzgi() {
        return super.zzgi();
    }

    protected final boolean zzhh() {
        return false;
    }

    public final /* bridge */ /* synthetic */ zzjy zzjc() {
        return super.zzjc();
    }

    public final /* bridge */ /* synthetic */ zzeb zzjd() {
        return super.zzjd();
    }

    public final /* bridge */ /* synthetic */ zzej zzje() {
        return super.zzje();
    }
}
