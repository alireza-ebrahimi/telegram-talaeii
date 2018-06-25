package com.google.android.gms.clearcut;

import android.content.Context;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.AbstractClientBuilder;
import com.google.android.gms.common.api.Api.ApiOptions.NoOptions;
import com.google.android.gms.common.api.Api.ClientKey;
import com.google.android.gms.common.api.PendingResults;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.DefaultClock;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.internal.clearcut.zzaa;
import com.google.android.gms.internal.clearcut.zze;
import com.google.android.gms.internal.clearcut.zzha;
import com.google.android.gms.internal.clearcut.zzj;
import com.google.android.gms.internal.clearcut.zzp;
import com.google.android.gms.internal.clearcut.zzr;
import com.google.android.gms.phenotype.ExperimentTokens;
import java.util.ArrayList;
import java.util.TimeZone;
import javax.annotation.Nullable;

@KeepForSdk
public final class ClearcutLogger {
    @Deprecated
    public static final Api<NoOptions> API = new Api("ClearcutLogger.API", CLIENT_BUILDER, CLIENT_KEY);
    private static final AbstractClientBuilder<zzj, NoOptions> CLIENT_BUILDER = new zza();
    private static final ClientKey<zzj> CLIENT_KEY = new ClientKey();
    private static final ExperimentTokens[] zze = new ExperimentTokens[0];
    private static final String[] zzf = new String[0];
    private static final byte[][] zzg = new byte[0][];
    private final String packageName;
    private final Context zzh;
    private final int zzi;
    private String zzj;
    private int zzk;
    private String zzl;
    private String zzm;
    private final boolean zzn;
    private com.google.android.gms.internal.clearcut.zzge.zzv.zzb zzo;
    private final zzb zzp;
    private final Clock zzq;
    private zzc zzr;
    private final zza zzs;

    public class LogEventBuilder {
        private final zzha zzaa;
        private boolean zzab;
        private final /* synthetic */ ClearcutLogger zzac;
        private String zzj;
        private int zzk;
        private String zzl;
        private String zzm;
        private com.google.android.gms.internal.clearcut.zzge.zzv.zzb zzo;
        private final zzb zzt;
        private ArrayList<Integer> zzu;
        private ArrayList<String> zzv;
        private ArrayList<Integer> zzw;
        private ArrayList<ExperimentTokens> zzx;
        private ArrayList<byte[]> zzy;
        private boolean zzz;

        private LogEventBuilder(ClearcutLogger clearcutLogger, byte[] bArr) {
            this(clearcutLogger, bArr, null);
        }

        private LogEventBuilder(ClearcutLogger clearcutLogger, byte[] bArr, zzb zzb) {
            this.zzac = clearcutLogger;
            this.zzk = this.zzac.zzk;
            this.zzj = this.zzac.zzj;
            this.zzl = this.zzac.zzl;
            ClearcutLogger clearcutLogger2 = this.zzac;
            this.zzm = null;
            this.zzo = this.zzac.zzo;
            this.zzu = null;
            this.zzv = null;
            this.zzw = null;
            this.zzx = null;
            this.zzy = null;
            this.zzz = true;
            this.zzaa = new zzha();
            this.zzab = false;
            this.zzl = clearcutLogger.zzl;
            this.zzm = null;
            this.zzaa.zzbkc = zzaa.zze(clearcutLogger.zzh);
            this.zzaa.zzbjf = clearcutLogger.zzq.currentTimeMillis();
            this.zzaa.zzbjg = clearcutLogger.zzq.elapsedRealtime();
            zzha zzha = this.zzaa;
            clearcutLogger.zzr;
            zzha.zzbju = (long) (TimeZone.getDefault().getOffset(this.zzaa.zzbjf) / 1000);
            if (bArr != null) {
                this.zzaa.zzbjp = bArr;
            }
            this.zzt = null;
        }

        @KeepForSdk
        public void log() {
            if (this.zzab) {
                throw new IllegalStateException("do not reuse LogEventBuilder");
            }
            this.zzab = true;
            zze zze = new zze(new zzr(this.zzac.packageName, this.zzac.zzi, this.zzk, this.zzj, this.zzl, this.zzm, this.zzac.zzn, this.zzo), this.zzaa, null, null, ClearcutLogger.zza(null), null, ClearcutLogger.zza(null), null, null, this.zzz);
            if (this.zzac.zzs.zza(zze)) {
                this.zzac.zzp.zzb(zze);
            } else {
                PendingResults.immediatePendingResult(Status.RESULT_SUCCESS, null);
            }
        }

        @KeepForSdk
        public LogEventBuilder setEventCode(int i) {
            this.zzaa.zzbji = i;
            return this;
        }
    }

    public interface zza {
        boolean zza(zze zze);
    }

    public interface zzb {
        byte[] zza();
    }

    public static class zzc {
    }

    @VisibleForTesting
    private ClearcutLogger(Context context, int i, String str, String str2, String str3, boolean z, zzb zzb, Clock clock, zzc zzc, zza zza) {
        this.zzk = -1;
        this.zzo = com.google.android.gms.internal.clearcut.zzge.zzv.zzb.DEFAULT;
        this.zzh = context;
        this.packageName = context.getPackageName();
        this.zzi = zza(context);
        this.zzk = -1;
        this.zzj = str;
        this.zzl = str2;
        this.zzm = null;
        this.zzn = z;
        this.zzp = zzb;
        this.zzq = clock;
        this.zzr = new zzc();
        this.zzo = com.google.android.gms.internal.clearcut.zzge.zzv.zzb.DEFAULT;
        this.zzs = zza;
        if (z) {
            Preconditions.checkArgument(str2 == null, "can't be anonymous with an upload account");
        }
    }

    @KeepForSdk
    public ClearcutLogger(Context context, String str, @Nullable String str2) {
        this(context, -1, str, str2, null, false, zze.zzb(context), DefaultClock.getInstance(), null, new zzp(context));
    }

    @KeepForSdk
    public static ClearcutLogger anonymousLogger(Context context, String str) {
        return new ClearcutLogger(context, -1, str, null, null, true, zze.zzb(context), DefaultClock.getInstance(), null, new zzp(context));
    }

    private static int zza(Context context) {
        int i = 0;
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (Throwable e) {
            Log.wtf("ClearcutLogger", "This can't happen.", e);
            return i;
        }
    }

    private static int[] zza(ArrayList<Integer> arrayList) {
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

    @KeepForSdk
    public final LogEventBuilder newEvent(@Nullable byte[] bArr) {
        return new LogEventBuilder(bArr);
    }
}
