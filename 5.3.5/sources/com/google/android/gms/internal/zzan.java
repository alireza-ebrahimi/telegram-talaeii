package com.google.android.gms.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

final class zzan {
    final String key;
    final String zza;
    final long zzb;
    final long zzc;
    long zzca;
    final long zzd;
    final long zze;
    final List<zzl> zzg;

    zzan(String str, zzc zzc) {
        this(str, zzc.zza, zzc.zzb, zzc.zzc, zzc.zzd, zzc.zze, zzc.zzg != null ? zzc.zzg : zzap.zza(zzc.zzf));
        this.zzca = (long) zzc.data.length;
    }

    private zzan(String str, String str2, long j, long j2, long j3, long j4, List<zzl> list) {
        this.key = str;
        if ("".equals(str2)) {
            str2 = null;
        }
        this.zza = str2;
        this.zzb = j;
        this.zzc = j2;
        this.zzd = j3;
        this.zze = j4;
        this.zzg = list;
    }

    static zzan zzc(zzao zzao) throws IOException {
        if (zzam.zzb((InputStream) zzao) == 538247942) {
            return new zzan(zzam.zza(zzao), zzam.zza(zzao), zzam.zzc(zzao), zzam.zzc(zzao), zzam.zzc(zzao), zzam.zzc(zzao), zzam.zzb(zzao));
        }
        throw new IOException();
    }

    final boolean zza(OutputStream outputStream) {
        try {
            zzam.zza(outputStream, 538247942);
            zzam.zza(outputStream, this.key);
            zzam.zza(outputStream, this.zza == null ? "" : this.zza);
            zzam.zza(outputStream, this.zzb);
            zzam.zza(outputStream, this.zzc);
            zzam.zza(outputStream, this.zzd);
            zzam.zza(outputStream, this.zze);
            List<zzl> list = this.zzg;
            if (list != null) {
                zzam.zza(outputStream, list.size());
                for (zzl zzl : list) {
                    zzam.zza(outputStream, zzl.getName());
                    zzam.zza(outputStream, zzl.getValue());
                }
            } else {
                zzam.zza(outputStream, 0);
            }
            outputStream.flush();
            return true;
        } catch (IOException e) {
            zzaf.zzb("%s", e.toString());
            return false;
        }
    }
}
