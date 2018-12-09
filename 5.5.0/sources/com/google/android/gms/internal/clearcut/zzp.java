package com.google.android.gms.internal.clearcut;

import android.content.Context;
import android.util.Log;
import com.google.android.gms.clearcut.ClearcutLogger.zza;
import com.google.android.gms.clearcut.zze;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.common.wrappers.Wrappers;
import com.google.android.gms.internal.clearcut.zzgw.zza.zzb;
import com.google.android.gms.phenotype.Phenotype;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.telegram.messenger.exoplayer2.C3446C;

public final class zzp implements zza {
    private static final Charset UTF_8 = Charset.forName(C3446C.UTF8_NAME);
    private static final zzao zzaq = new zzao(Phenotype.getContentProviderUri("com.google.android.gms.clearcut.public")).zzc("gms:playlog:service:samplingrules_").zzd("LogSamplingRules__");
    private static final zzao zzar = new zzao(Phenotype.getContentProviderUri("com.google.android.gms.clearcut.public")).zzc("gms:playlog:service:sampling_").zzd("LogSampling__");
    private static final ConcurrentHashMap<String, zzae<zzgw.zza>> zzas = new ConcurrentHashMap();
    private static final HashMap<String, zzae<String>> zzat = new HashMap();
    @VisibleForTesting
    private static Boolean zzau = null;
    @VisibleForTesting
    private static Long zzav = null;
    @VisibleForTesting
    private static final zzae<Boolean> zzaw = zzaq.zzc("enable_log_sampling_rules", false);
    private final Context zzh;

    public zzp(Context context) {
        this.zzh = context;
        if (this.zzh != null) {
            zzae.maybeInit(this.zzh);
        }
    }

    @VisibleForTesting
    private static long zza(String str, long j) {
        if (str == null || str.isEmpty()) {
            return zzk.zza(ByteBuffer.allocate(8).putLong(j).array());
        }
        byte[] bytes = str.getBytes(UTF_8);
        ByteBuffer allocate = ByteBuffer.allocate(bytes.length + 8);
        allocate.put(bytes);
        allocate.putLong(j);
        return zzk.zza(allocate.array());
    }

    @VisibleForTesting
    private static zzb zza(String str) {
        int i = 0;
        if (str == null) {
            return null;
        }
        String str2 = TtmlNode.ANONYMOUS_REGION_ID;
        int indexOf = str.indexOf(44);
        if (indexOf >= 0) {
            str2 = str.substring(0, indexOf);
            i = indexOf + 1;
        }
        indexOf = str.indexOf(47, i);
        if (indexOf <= 0) {
            str2 = "LogSamplerImpl";
            String str3 = "Failed to parse the rule: ";
            String valueOf = String.valueOf(str);
            Log.e(str2, valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
            return null;
        }
        try {
            long parseLong = Long.parseLong(str.substring(i, indexOf));
            long parseLong2 = Long.parseLong(str.substring(indexOf + 1));
            if (parseLong >= 0 && parseLong2 >= 0) {
                return (zzb) zzb.zzfz().zzn(str2).zzr(parseLong).zzs(parseLong2).zzbh();
            }
            Log.e("LogSamplerImpl", "negative values not supported: " + parseLong + "/" + parseLong2);
            return null;
        } catch (Throwable e) {
            Throwable th = e;
            str3 = "LogSamplerImpl";
            String str4 = "parseLong() failed while parsing: ";
            valueOf = String.valueOf(str);
            Log.e(str3, valueOf.length() != 0 ? str4.concat(valueOf) : new String(str4), th);
            return null;
        }
    }

    @VisibleForTesting
    private static boolean zzb(long j, long j2, long j3) {
        if (j2 >= 0 && j3 > 0) {
            if ((j >= 0 ? j % j3 : (((Long.MAX_VALUE % j3) + 1) + ((j & Long.MAX_VALUE) % j3)) % j3) >= j2) {
                return false;
            }
        }
        return true;
    }

    private static boolean zzc(Context context) {
        if (zzau == null) {
            zzau = Boolean.valueOf(Wrappers.packageManager(context).checkCallingOrSelfPermission("com.google.android.providers.gsf.permission.READ_GSERVICES") == 0);
        }
        return zzau.booleanValue();
    }

    @VisibleForTesting
    private static long zzd(Context context) {
        if (zzav == null) {
            if (context == null) {
                return 0;
            }
            if (zzc(context)) {
                zzav = Long.valueOf(zzy.getLong(context.getContentResolver(), "android_id", 0));
            } else {
                zzav = Long.valueOf(0);
            }
        }
        return zzav.longValue();
    }

    public final boolean zza(zze zze) {
        String str = zze.zzag.zzj;
        int i = zze.zzag.zzk;
        if (zze.zzaa != null) {
            int i2 = zze.zzaa.zzbji;
        } else {
            boolean z = false;
        }
        zzae zzae;
        zzb zza;
        if (((Boolean) zzaw.get()).booleanValue()) {
            if (str == null || str.isEmpty()) {
                str = i >= 0 ? String.valueOf(i) : null;
            }
            if (str != null) {
                List emptyList;
                if (this.zzh == null) {
                    emptyList = Collections.emptyList();
                } else {
                    zzae = (zzae) zzas.get(str);
                    if (zzae == null) {
                        zzae zza2 = zzaq.zza(str, zzgw.zza.zzft(), zzq.zzax);
                        zzae = (zzae) zzas.putIfAbsent(str, zza2);
                        if (zzae == null) {
                            zzae = zza2;
                        }
                    }
                    emptyList = ((zzgw.zza) zzae.get()).zzfs();
                }
                for (zzb zza3 : r0) {
                    if ((!zza3.zzfv() || zza3.getEventCode() == 0 || zza3.getEventCode() == r6) && !zzb(zza(zza3.zzfw(), zzd(this.zzh)), zza3.zzfx(), zza3.zzfy())) {
                        return false;
                    }
                }
            }
        } else {
            if (str == null || str.isEmpty()) {
                str = i >= 0 ? String.valueOf(i) : null;
            }
            if (str != null) {
                String str2;
                if (this.zzh == null || !zzc(this.zzh)) {
                    str2 = null;
                } else {
                    zzae = (zzae) zzat.get(str);
                    if (zzae == null) {
                        zzae = zzar.zza(str, null);
                        zzat.put(str, zzae);
                    }
                    str2 = (String) zzae.get();
                }
                zza3 = zza(str2);
                if (zza3 != null) {
                    return zzb(zza(zza3.zzfw(), zzd(this.zzh)), zza3.zzfx(), zza3.zzfy());
                }
            }
        }
        return true;
    }
}
