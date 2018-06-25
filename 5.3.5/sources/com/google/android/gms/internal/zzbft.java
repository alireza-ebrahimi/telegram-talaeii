package com.google.android.gms.internal;

import android.content.Context;
import android.util.Log;
import com.google.android.gms.clearcut.ClearcutLogger.zza;
import com.google.android.gms.phenotype.Phenotype;
import com.google.android.gms.phenotype.PhenotypeFlag;
import com.google.android.gms.phenotype.PhenotypeFlag.Factory;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public final class zzbft implements zza {
    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private static final Factory zzfqj = new Factory(Phenotype.getContentProviderUri("com.google.android.gms.clearcut.public")).withGservicePrefix("gms:playlog:service:sampling_").withPhenotypePrefix("LogSampling__");
    private static Map<String, PhenotypeFlag<String>> zzfqk = null;
    private static Boolean zzfql = null;
    private static Long zzfqm = null;
    private final Context zzaiq;

    public zzbft(Context context) {
        this.zzaiq = context;
        if (zzfqk == null) {
            zzfqk = new HashMap();
        }
        if (this.zzaiq != null) {
            PhenotypeFlag.maybeInit(this.zzaiq);
        }
    }

    private static boolean zzcc(Context context) {
        if (zzfql == null) {
            zzfql = Boolean.valueOf(zzbih.zzdd(context).checkCallingOrSelfPermission("com.google.android.providers.gsf.permission.READ_GSERVICES") == 0);
        }
        return zzfql.booleanValue();
    }

    private static zzbfu zzge(String str) {
        int i = 0;
        if (str == null) {
            return null;
        }
        String str2 = "";
        int indexOf = str.indexOf(44);
        if (indexOf >= 0) {
            str2 = str.substring(0, indexOf);
            i = indexOf + 1;
        }
        int indexOf2 = str.indexOf(47, i);
        if (indexOf2 <= 0) {
            str2 = "LogSamplerImpl";
            String str3 = "Failed to parse the rule: ";
            String valueOf = String.valueOf(str);
            Log.e(str2, valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
            return null;
        }
        try {
            long parseLong = Long.parseLong(str.substring(i, indexOf2));
            long parseLong2 = Long.parseLong(str.substring(indexOf2 + 1));
            if (parseLong >= 0 && parseLong2 >= 0) {
                return new zzbfu(str2, parseLong, parseLong2);
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

    public final boolean zzg(String str, int i) {
        if (str == null || str.isEmpty()) {
            str = i >= 0 ? String.valueOf(i) : null;
        }
        if (str == null) {
            return true;
        }
        String str2;
        if (this.zzaiq == null || !zzcc(this.zzaiq)) {
            str2 = null;
        } else {
            PhenotypeFlag phenotypeFlag = (PhenotypeFlag) zzfqk.get(str);
            if (phenotypeFlag == null) {
                phenotypeFlag = zzfqj.createFlag(str, null);
                zzfqk.put(str, phenotypeFlag);
            }
            str2 = (String) phenotypeFlag.get();
        }
        zzbfu zzge = zzge(str2);
        if (zzge == null) {
            return true;
        }
        long j;
        long j2;
        long j3;
        String str3 = zzge.zzfqn;
        Context context = this.zzaiq;
        if (zzfqm == null) {
            if (context == null) {
                j = 0;
                if (str3 != null || str3.isEmpty()) {
                    j = zzbfo.zzi(ByteBuffer.allocate(8).putLong(j).array());
                } else {
                    byte[] bytes = str3.getBytes(UTF_8);
                    ByteBuffer allocate = ByteBuffer.allocate(bytes.length + 8);
                    allocate.put(bytes);
                    allocate.putLong(j);
                    j = zzbfo.zzi(allocate.array());
                }
                j2 = zzge.zzfqo;
                j3 = zzge.zzfqp;
                if (j2 >= 0 || j3 < 0) {
                    throw new IllegalArgumentException("negative values not supported: " + j2 + "/" + j3);
                }
                if (j3 > 0) {
                    if ((j >= 0 ? j % j3 : (((j & Long.MAX_VALUE) % j3) + ((Long.MAX_VALUE % j3) + 1)) % j3) < j2) {
                        return true;
                    }
                }
                return false;
            } else if (zzcc(context)) {
                zzfqm = Long.valueOf(zzdnm.getLong(context.getContentResolver(), "android_id", 0));
            } else {
                zzfqm = Long.valueOf(0);
            }
        }
        j = zzfqm.longValue();
        if (str3 != null) {
        }
        j = zzbfo.zzi(ByteBuffer.allocate(8).putLong(j).array());
        j2 = zzge.zzfqo;
        j3 = zzge.zzfqp;
        if (j2 >= 0) {
        }
        throw new IllegalArgumentException("negative values not supported: " + j2 + "/" + j3);
    }
}
