package com.google.android.gms.internal.measurement;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import com.google.android.gms.common.api.internal.GoogleServices;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.DefaultClock;
import com.google.android.gms.common.wrappers.Wrappers;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.concurrent.atomic.AtomicReference;

public class zzgm implements zzhj {
    private static volatile zzgm zzamn;
    private final long zzaem;
    private final zzec zzagd;
    private final String zzamo;
    private final zzeg zzamp;
    private final zzfs zzamq;
    private final zzfh zzamr;
    private final zzgh zzams;
    private final zzji zzamt;
    private final AppMeasurement zzamu;
    private final FirebaseAnalytics zzamv;
    private final zzkc zzamw;
    private final zzff zzamx;
    private final zzig zzamy;
    private final zzhl zzamz;
    private final zzdu zzana;
    private zzfd zzanb;
    private zzij zzanc;
    private zzeq zzand;
    private zzfc zzane;
    private zzfy zzanf;
    private Boolean zzang;
    private long zzanh;
    private int zzani;
    private int zzanj;
    private final Context zzqx;
    private final Clock zzro;
    private boolean zzvo = false;

    private zzgm(zzhk zzhk) {
        Preconditions.checkNotNull(zzhk);
        this.zzagd = new zzec(zzhk.zzqx);
        zzey.zza(this.zzagd);
        this.zzqx = zzhk.zzqx;
        this.zzamo = zzhk.zzamo;
        zzwu.init(this.zzqx);
        this.zzro = DefaultClock.getInstance();
        this.zzaem = this.zzro.currentTimeMillis();
        this.zzamp = new zzeg(this);
        zzhi zzfs = new zzfs(this);
        zzfs.zzm();
        this.zzamq = zzfs;
        zzfs = new zzfh(this);
        zzfs.zzm();
        this.zzamr = zzfs;
        zzfs = new zzkc(this);
        zzfs.zzm();
        this.zzamw = zzfs;
        zzfs = new zzff(this);
        zzfs.zzm();
        this.zzamx = zzfs;
        this.zzana = new zzdu(this);
        zzfs = new zzig(this);
        zzfs.zzm();
        this.zzamy = zzfs;
        zzfs = new zzhl(this);
        zzfs.zzm();
        this.zzamz = zzfs;
        this.zzamu = new AppMeasurement(this);
        this.zzamv = new FirebaseAnalytics(this);
        zzfs = new zzji(this);
        zzfs.zzm();
        this.zzamt = zzfs;
        zzfs = new zzgh(this);
        zzfs.zzm();
        this.zzams = zzfs;
        zzec zzec = this.zzagd;
        if (this.zzqx.getApplicationContext() instanceof Application) {
            zzhh zzfv = zzfv();
            if (zzfv.getContext().getApplicationContext() instanceof Application) {
                Application application = (Application) zzfv.getContext().getApplicationContext();
                if (zzfv.zzanz == null) {
                    zzfv.zzanz = new zzie(zzfv);
                }
                application.unregisterActivityLifecycleCallbacks(zzfv.zzanz);
                application.registerActivityLifecycleCallbacks(zzfv.zzanz);
                zzfv.zzgf().zziz().log("Registered activity lifecycle callback");
            }
        } else {
            zzgf().zziv().log("Application context is not an Application");
        }
        this.zzams.zzc(new zzgn(this, zzhk));
    }

    public static zzgm zza(Context context, String str, String str2) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(context.getApplicationContext());
        if (zzamn == null) {
            synchronized (zzgm.class) {
                if (zzamn == null) {
                    zzamn = new zzgm(new zzhk(context, null));
                }
            }
        }
        return zzamn;
    }

    private static void zza(zzhh zzhh) {
        if (zzhh == null) {
            throw new IllegalStateException("Component not created");
        }
    }

    private static void zza(zzhi zzhi) {
        if (zzhi == null) {
            throw new IllegalStateException("Component not created");
        } else if (!zzhi.isInitialized()) {
            String valueOf = String.valueOf(zzhi.getClass());
            throw new IllegalStateException(new StringBuilder(String.valueOf(valueOf).length() + 27).append("Component not initialized: ").append(valueOf).toString());
        }
    }

    private final void zza(zzhk zzhk) {
        zzfj zzix;
        zzge().zzab();
        zzeg.zzhi();
        zzhi zzeq = new zzeq(this);
        zzeq.zzm();
        this.zzand = zzeq;
        zzeq = new zzfc(this);
        zzeq.zzm();
        this.zzane = zzeq;
        zzhi zzfd = new zzfd(this);
        zzfd.zzm();
        this.zzanb = zzfd;
        zzfd = new zzij(this);
        zzfd.zzm();
        this.zzanc = zzfd;
        this.zzamw.zzke();
        this.zzamq.zzke();
        this.zzanf = new zzfy(this);
        this.zzane.zzke();
        zzgf().zzix().zzg("App measurement is starting up, version", Long.valueOf(12451));
        zzec zzec = this.zzagd;
        zzgf().zzix().log("To enable debug logging run: adb shell setprop log.tag.FA VERBOSE");
        zzec = this.zzagd;
        String zzah = zzeq.zzah();
        if (zzgc().zzci(zzah)) {
            zzix = zzgf().zzix();
            zzah = "Faster debug mode event logging enabled. To disable, run:\n  adb shell setprop debug.firebase.analytics.app .none.";
        } else {
            zzix = zzgf().zzix();
            String str = "To enable faster debug mode event logging run:\n  adb shell setprop debug.firebase.analytics.app ";
            zzah = String.valueOf(zzah);
            zzah = zzah.length() != 0 ? str.concat(zzah) : new String(str);
        }
        zzix.log(zzah);
        zzgf().zziy().log("Debug-level message logging enabled");
        if (this.zzani != this.zzanj) {
            zzgf().zzis().zze("Not all components initialized", Integer.valueOf(this.zzani), Integer.valueOf(this.zzanj));
        }
        this.zzvo = true;
    }

    private final void zzch() {
        if (!this.zzvo) {
            throw new IllegalStateException("AppMeasurement is not initialized");
        }
    }

    public final Context getContext() {
        return this.zzqx;
    }

    public final boolean isEnabled() {
        boolean z = false;
        zzge().zzab();
        zzch();
        if (this.zzamp.zzhj()) {
            return false;
        }
        Boolean zzhk = this.zzamp.zzhk();
        if (zzhk != null) {
            z = zzhk.booleanValue();
        } else if (!GoogleServices.isMeasurementExplicitlyDisabled()) {
            z = true;
        }
        return zzgg().zzg(z);
    }

    protected final void start() {
        boolean z = false;
        zzge().zzab();
        if (zzgg().zzakd.get() == 0) {
            zzgg().zzakd.set(this.zzro.currentTimeMillis());
        }
        if (Long.valueOf(zzgg().zzaki.get()).longValue() == 0) {
            zzgf().zziz().zzg("Persisting first open", Long.valueOf(this.zzaem));
            zzgg().zzaki.set(this.zzaem);
        }
        zzec zzec;
        if (zzkd()) {
            zzec = this.zzagd;
            if (!TextUtils.isEmpty(zzfw().getGmpAppId())) {
                String zzjg = zzgg().zzjg();
                if (zzjg == null) {
                    zzgg().zzbp(zzfw().getGmpAppId());
                } else if (!zzjg.equals(zzfw().getGmpAppId())) {
                    zzgf().zzix().log("Rechecking which service to use due to a GMP App Id change");
                    zzgg().zzjj();
                    this.zzanc.disconnect();
                    this.zzanc.zzdf();
                    zzgg().zzbp(zzfw().getGmpAppId());
                    zzgg().zzaki.set(this.zzaem);
                    zzgg().zzakk.zzbr(null);
                }
            }
            zzfv().zzbq(zzgg().zzakk.zzjn());
            zzec = this.zzagd;
            if (!TextUtils.isEmpty(zzfw().getGmpAppId())) {
                boolean isEnabled = isEnabled();
                if (!(zzgg().zzjm() || this.zzamp.zzhj())) {
                    zzfs zzgg = zzgg();
                    if (!isEnabled) {
                        z = true;
                    }
                    zzgg.zzh(z);
                }
                if (!this.zzamp.zzay(zzfw().zzah()) || isEnabled) {
                    zzfv().zzkj();
                }
                zzfy().zza(new AtomicReference());
            }
        } else if (isEnabled()) {
            if (!zzgc().zzw("android.permission.INTERNET")) {
                zzgf().zzis().log("App is missing INTERNET permission");
            }
            if (!zzgc().zzw("android.permission.ACCESS_NETWORK_STATE")) {
                zzgf().zzis().log("App is missing ACCESS_NETWORK_STATE permission");
            }
            zzec = this.zzagd;
            if (!Wrappers.packageManager(this.zzqx).isCallerInstantApp()) {
                if (!zzgc.zza(this.zzqx)) {
                    zzgf().zzis().log("AppMeasurementReceiver not registered/enabled");
                }
                if (!zzkc.zza(this.zzqx, false)) {
                    zzgf().zzis().log("AppMeasurementService not registered/enabled");
                }
            }
            zzgf().zzis().log("Uploading is not possible. App measurement disabled");
        }
    }

    final void zzb(zzhi zzhi) {
        this.zzani++;
    }

    public final Clock zzbt() {
        return this.zzro;
    }

    final void zzfr() {
        zzec zzec = this.zzagd;
        throw new IllegalStateException("Unexpected call on client side");
    }

    final void zzfs() {
        zzec zzec = this.zzagd;
    }

    public final zzdu zzfu() {
        zza(this.zzana);
        return this.zzana;
    }

    public final zzhl zzfv() {
        zza(this.zzamz);
        return this.zzamz;
    }

    public final zzfc zzfw() {
        zza(this.zzane);
        return this.zzane;
    }

    public final zzeq zzfx() {
        zza(this.zzand);
        return this.zzand;
    }

    public final zzij zzfy() {
        zza(this.zzanc);
        return this.zzanc;
    }

    public final zzig zzfz() {
        zza(this.zzamy);
        return this.zzamy;
    }

    public final zzfd zzga() {
        zza(this.zzanb);
        return this.zzanb;
    }

    public final zzff zzgb() {
        zza(this.zzamx);
        return this.zzamx;
    }

    public final zzkc zzgc() {
        zza(this.zzamw);
        return this.zzamw;
    }

    public final zzji zzgd() {
        zza(this.zzamt);
        return this.zzamt;
    }

    public final zzgh zzge() {
        zza(this.zzams);
        return this.zzams;
    }

    public final zzfh zzgf() {
        zza(this.zzamr);
        return this.zzamr;
    }

    public final zzfs zzgg() {
        zza(this.zzamq);
        return this.zzamq;
    }

    public final zzeg zzgh() {
        return this.zzamp;
    }

    public final zzec zzgi() {
        return this.zzagd;
    }

    public final zzfh zzjv() {
        return (this.zzamr == null || !this.zzamr.isInitialized()) ? null : this.zzamr;
    }

    public final zzfy zzjw() {
        return this.zzanf;
    }

    final zzgh zzjx() {
        return this.zzams;
    }

    public final AppMeasurement zzjy() {
        return this.zzamu;
    }

    public final FirebaseAnalytics zzjz() {
        return this.zzamv;
    }

    public final String zzka() {
        return this.zzamo;
    }

    final long zzkb() {
        Long valueOf = Long.valueOf(zzgg().zzaki.get());
        return valueOf.longValue() == 0 ? this.zzaem : Math.min(this.zzaem, valueOf.longValue());
    }

    final void zzkc() {
        this.zzanj++;
    }

    protected final boolean zzkd() {
        boolean z = false;
        zzch();
        zzge().zzab();
        if (this.zzang == null || this.zzanh == 0 || !(this.zzang == null || this.zzang.booleanValue() || Math.abs(this.zzro.elapsedRealtime() - this.zzanh) <= 1000)) {
            this.zzanh = this.zzro.elapsedRealtime();
            zzec zzec = this.zzagd;
            if (zzgc().zzw("android.permission.INTERNET") && zzgc().zzw("android.permission.ACCESS_NETWORK_STATE") && (Wrappers.packageManager(this.zzqx).isCallerInstantApp() || (zzgc.zza(this.zzqx) && zzkc.zza(this.zzqx, false)))) {
                z = true;
            }
            this.zzang = Boolean.valueOf(z);
            if (this.zzang.booleanValue()) {
                this.zzang = Boolean.valueOf(zzgc().zzcf(zzfw().getGmpAppId()));
            }
        }
        return this.zzang.booleanValue();
    }
}
