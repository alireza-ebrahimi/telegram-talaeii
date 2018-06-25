package com.google.android.gms.clearcut;

import android.content.Context;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.NoOptions;
import com.google.android.gms.common.api.Api.zzf;
import com.google.android.gms.common.api.PendingResults;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.util.zzi;
import com.google.android.gms.internal.zzbfi;
import com.google.android.gms.internal.zzbfn;
import com.google.android.gms.internal.zzbft;
import com.google.android.gms.internal.zzbfv;
import com.google.android.gms.internal.zzfmr;
import com.google.android.gms.phenotype.ExperimentTokens;
import java.util.ArrayList;
import java.util.TimeZone;

@KeepForSdk
public final class ClearcutLogger {
    @Deprecated
    public static final Api<NoOptions> API = new Api("ClearcutLogger.API", zzegv, zzegu);
    @Hide
    private static zzf<zzbfn> zzegu = new zzf();
    @Hide
    private static com.google.android.gms.common.api.Api.zza<zzbfn, NoOptions> zzegv = new zza();
    private static final ExperimentTokens[] zzfoz = new ExperimentTokens[0];
    private static final String[] zzfpa = new String[0];
    private static final byte[][] zzfpb = new byte[0][];
    private final String packageName;
    private final zze zzdir;
    private final int zzfpc;
    private String zzfpd;
    private int zzfpe = -1;
    private String zzfpf;
    private String zzfpg;
    private final boolean zzfph;
    private int zzfpi = 0;
    private final zzb zzfpj;
    private zzc zzfpk;
    private final zza zzfpl;

    public class LogEventBuilder {
        private String zzfpd;
        private int zzfpe;
        private String zzfpf;
        private String zzfpg;
        private int zzfpi;
        private final zzb zzfpm;
        private ArrayList<Integer> zzfpn;
        private ArrayList<String> zzfpo;
        private ArrayList<Integer> zzfpp;
        private ArrayList<ExperimentTokens> zzfpq;
        private ArrayList<byte[]> zzfpr;
        private boolean zzfps;
        private final zzfmr zzfpt;
        private boolean zzfpu;
        private /* synthetic */ ClearcutLogger zzfpv;

        private LogEventBuilder(ClearcutLogger clearcutLogger, byte[] bArr) {
            this(clearcutLogger, bArr, null);
        }

        private LogEventBuilder(ClearcutLogger clearcutLogger, byte[] bArr, zzb zzb) {
            this.zzfpv = clearcutLogger;
            this.zzfpe = this.zzfpv.zzfpe;
            this.zzfpd = this.zzfpv.zzfpd;
            ClearcutLogger clearcutLogger2 = this.zzfpv;
            this.zzfpf = null;
            clearcutLogger2 = this.zzfpv;
            this.zzfpg = null;
            this.zzfpi = 0;
            this.zzfpn = null;
            this.zzfpo = null;
            this.zzfpp = null;
            this.zzfpq = null;
            this.zzfpr = null;
            this.zzfps = true;
            this.zzfpt = new zzfmr();
            this.zzfpu = false;
            this.zzfpf = null;
            this.zzfpg = null;
            this.zzfpt.zzpyu = clearcutLogger.zzdir.currentTimeMillis();
            this.zzfpt.zzpyv = clearcutLogger.zzdir.elapsedRealtime();
            zzfmr zzfmr = this.zzfpt;
            clearcutLogger.zzfpk;
            zzfmr.zzpzg = (long) (TimeZone.getDefault().getOffset(this.zzfpt.zzpyu) / 1000);
            if (bArr != null) {
                this.zzfpt.zzpzb = bArr;
            }
            this.zzfpm = null;
        }

        @KeepForSdk
        public void log() {
            if (this.zzfpu) {
                throw new IllegalStateException("do not reuse LogEventBuilder");
            }
            this.zzfpu = true;
            zze zze = new zze(new zzbfv(this.zzfpv.packageName, this.zzfpv.zzfpc, this.zzfpe, this.zzfpd, this.zzfpf, this.zzfpg, this.zzfpv.zzfph, 0), this.zzfpt, null, null, ClearcutLogger.zzb(null), null, ClearcutLogger.zzb(null), null, null, this.zzfps);
            zzbfv zzbfv = zze.zzfpz;
            if (this.zzfpv.zzfpl.zzg(zzbfv.zzfpd, zzbfv.zzfpe)) {
                this.zzfpv.zzfpj.zza(zze);
            } else {
                PendingResults.zza(Status.zzftq, null);
            }
        }
    }

    public interface zza {
        boolean zzg(String str, int i);
    }

    public interface zzb {
        byte[] zzahc();
    }

    public static class zzc {
    }

    private ClearcutLogger(Context context, int i, String str, String str2, String str3, boolean z, zzb zzb, zze zze, zzc zzc, zza zza) {
        this.packageName = context.getPackageName();
        this.zzfpc = zzca(context);
        this.zzfpe = -1;
        this.zzfpd = str;
        this.zzfpf = null;
        this.zzfpg = null;
        this.zzfph = true;
        this.zzfpj = zzb;
        this.zzdir = zze;
        this.zzfpk = new zzc();
        this.zzfpi = 0;
        this.zzfpl = zza;
        zzbq.checkArgument(true, "can't be anonymous with an upload account");
    }

    @KeepForSdk
    public static ClearcutLogger anonymousLogger(Context context, String str) {
        return new ClearcutLogger(context, -1, str, null, null, true, zzbfi.zzcb(context), zzi.zzanq(), null, new zzbft(context));
    }

    private static int[] zzb(ArrayList<Integer> arrayList) {
        if (arrayList == null) {
            return null;
        }
        int[] iArr = new int[arrayList.size()];
        ArrayList arrayList2 = arrayList;
        int size = arrayList2.size();
        int i = 0;
        int i2 = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            int intValue = ((Integer) obj).intValue();
            int i3 = i2 + 1;
            iArr[i2] = intValue;
            i2 = i3;
        }
        return iArr;
    }

    private static int zzca(Context context) {
        int i = 0;
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (Throwable e) {
            Log.wtf("ClearcutLogger", "This can't happen.", e);
            return i;
        }
    }

    @KeepForSdk
    public final LogEventBuilder newEvent(byte[] bArr) {
        return new LogEventBuilder(bArr);
    }
}
