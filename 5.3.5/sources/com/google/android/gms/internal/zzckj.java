package com.google.android.gms.internal;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri.Builder;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.support.annotation.NonNull;
import android.support.annotation.Size;
import android.support.annotation.WorkerThread;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.gms.common.api.internal.zzbz;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.util.zzi;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.android.gms.wallet.WalletConstants;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.analytics.FirebaseAnalytics.Event;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.stripe.android.model.Card;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.commons.lang3.time.DateUtils;
import org.telegram.messenger.exoplayer2.C0907C;

public class zzckj {
    private static volatile zzckj zzjnr;
    private boolean initialized = false;
    private final Context zzaiq;
    private final zze zzdir;
    private final long zzjgk;
    private final zzcik zzjns;
    private final zzcju zzjnt;
    private final zzcjj zzjnu;
    private final zzcke zzjnv;
    private final zzcnd zzjnw;
    private final zzckd zzjnx;
    private final AppMeasurement zzjny;
    private final FirebaseAnalytics zzjnz;
    private final zzcno zzjoa;
    private final zzcjh zzjob;
    private final zzcjn zzjoc;
    private final zzcma zzjod;
    private final zzclk zzjoe;
    private final zzcia zzjof;
    private zzcil zzjog;
    private zzcjf zzjoh;
    private zzcme zzjoi;
    private zzcir zzjoj;
    private zzcje zzjok;
    private zzcjs zzjol;
    private zzcnj zzjom;
    private zzcih zzjon;
    private boolean zzjoo;
    private Boolean zzjop;
    private long zzjoq;
    private FileLock zzjor;
    private FileChannel zzjos;
    private List<Long> zzjot;
    private List<Runnable> zzjou;
    private int zzjov;
    private int zzjow;
    private long zzjox;
    private long zzjoy;
    private boolean zzjoz;
    private boolean zzjpa;
    private boolean zzjpb;

    class zza implements zzcin {
        List<zzcob> zzaoz;
        private /* synthetic */ zzckj zzjpd;
        zzcoe zzjpe;
        List<Long> zzjpf;
        private long zzjpg;

        private zza(zzckj zzckj) {
            this.zzjpd = zzckj;
        }

        private static long zza(zzcob zzcob) {
            return ((zzcob.zzjuj.longValue() / 1000) / 60) / 60;
        }

        public final boolean zza(long j, zzcob zzcob) {
            zzbq.checkNotNull(zzcob);
            if (this.zzaoz == null) {
                this.zzaoz = new ArrayList();
            }
            if (this.zzjpf == null) {
                this.zzjpf = new ArrayList();
            }
            if (this.zzaoz.size() > 0 && zza((zzcob) this.zzaoz.get(0)) != zza(zzcob)) {
                return false;
            }
            long zzhs = this.zzjpg + ((long) zzcob.zzhs());
            if (zzhs >= ((long) Math.max(0, ((Integer) zzciz.zzjiv.get()).intValue()))) {
                return false;
            }
            this.zzjpg = zzhs;
            this.zzaoz.add(zzcob);
            this.zzjpf.add(Long.valueOf(j));
            return this.zzaoz.size() < Math.max(1, ((Integer) zzciz.zzjiw.get()).intValue());
        }

        public final void zzb(zzcoe zzcoe) {
            zzbq.checkNotNull(zzcoe);
            this.zzjpe = zzcoe;
        }
    }

    private zzckj(zzclj zzclj) {
        zzbq.checkNotNull(zzclj);
        this.zzaiq = zzclj.zzaiq;
        this.zzjox = -1;
        this.zzdir = zzi.zzanq();
        this.zzjgk = this.zzdir.currentTimeMillis();
        this.zzjns = new zzcik(this);
        zzcli zzcju = new zzcju(this);
        zzcju.initialize();
        this.zzjnt = zzcju;
        zzcju = new zzcjj(this);
        zzcju.initialize();
        this.zzjnu = zzcju;
        zzcju = new zzcno(this);
        zzcju.initialize();
        this.zzjoa = zzcju;
        zzcju = new zzcjh(this);
        zzcju.initialize();
        this.zzjob = zzcju;
        this.zzjof = new zzcia(this);
        zzcju = new zzcjn(this);
        zzcju.initialize();
        this.zzjoc = zzcju;
        zzcju = new zzcma(this);
        zzcju.initialize();
        this.zzjod = zzcju;
        zzcju = new zzclk(this);
        zzcju.initialize();
        this.zzjoe = zzcju;
        this.zzjny = new AppMeasurement(this);
        this.zzjnz = new FirebaseAnalytics(this);
        zzcju = new zzcnd(this);
        zzcju.initialize();
        this.zzjnw = zzcju;
        zzcju = new zzckd(this);
        zzcju.initialize();
        this.zzjnx = zzcju;
        zzcju = new zzcke(this);
        zzcju.initialize();
        this.zzjnv = zzcju;
        if (this.zzaiq.getApplicationContext() instanceof Application) {
            zzclh zzayd = zzayd();
            if (zzayd.getContext().getApplicationContext() instanceof Application) {
                Application application = (Application) zzayd.getContext().getApplicationContext();
                if (zzayd.zzjpt == null) {
                    zzayd.zzjpt = new zzcly(zzayd);
                }
                application.unregisterActivityLifecycleCallbacks(zzayd.zzjpt);
                application.registerActivityLifecycleCallbacks(zzayd.zzjpt);
                zzayd.zzayp().zzbba().log("Registered activity lifecycle callback");
            }
        } else {
            zzayp().zzbaw().log("Application context is not an Application");
        }
        this.zzjnv.zzh(new zzckk(this, zzclj));
    }

    @WorkerThread
    private final int zza(FileChannel fileChannel) {
        int i = 0;
        zzayo().zzwj();
        if (fileChannel == null || !fileChannel.isOpen()) {
            zzayp().zzbau().log("Bad channel to read from");
        } else {
            ByteBuffer allocate = ByteBuffer.allocate(4);
            try {
                fileChannel.position(0);
                int read = fileChannel.read(allocate);
                if (read == 4) {
                    allocate.flip();
                    i = allocate.getInt();
                } else if (read != -1) {
                    zzayp().zzbaw().zzj("Unexpected data length. Bytes read", Integer.valueOf(read));
                }
            } catch (IOException e) {
                zzayp().zzbau().zzj("Failed to read from channel", e);
            }
        }
        return i;
    }

    private final zzcif zza(Context context, String str, String str2, boolean z, boolean z2) {
        Object charSequence;
        String str3 = Card.UNKNOWN;
        String str4 = Card.UNKNOWN;
        int i = Integer.MIN_VALUE;
        String str5 = Card.UNKNOWN;
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            zzayp().zzbau().log("PackageManager is null, can not log app install information");
            return null;
        }
        try {
            str3 = packageManager.getInstallerPackageName(str);
        } catch (IllegalArgumentException e) {
            zzayp().zzbau().zzj("Error retrieving installer package name. appId", zzcjj.zzjs(str));
        }
        if (str3 == null) {
            str3 = "manual_install";
        } else if ("com.android.vending".equals(str3)) {
            str3 = "";
        }
        try {
            PackageInfo packageInfo = zzbih.zzdd(context).getPackageInfo(str, 0);
            if (packageInfo != null) {
                CharSequence zzhc = zzbih.zzdd(context).zzhc(str);
                if (TextUtils.isEmpty(zzhc)) {
                    String str6 = str5;
                } else {
                    charSequence = zzhc.toString();
                }
                try {
                    str4 = packageInfo.versionName;
                    i = packageInfo.versionCode;
                } catch (NameNotFoundException e2) {
                    zzayp().zzbau().zze("Error retrieving newly installed package info. appId, appName", zzcjj.zzjs(str), charSequence);
                    return null;
                }
            }
            return new zzcif(str, str2, str4, (long) i, str3, 12211, zzayl().zzab(context, str), null, z, false, "", 0, 0, 0, z2);
        } catch (NameNotFoundException e3) {
            charSequence = str5;
            zzayp().zzbau().zze("Error retrieving newly installed package info. appId, appName", zzcjj.zzjs(str), charSequence);
            return null;
        }
    }

    private static void zza(zzclh zzclh) {
        if (zzclh == null) {
            throw new IllegalStateException("Component not created");
        }
    }

    private static void zza(zzcli zzcli) {
        if (zzcli == null) {
            throw new IllegalStateException("Component not created");
        } else if (!zzcli.isInitialized()) {
            String valueOf = String.valueOf(zzcli.getClass());
            throw new IllegalStateException(new StringBuilder(String.valueOf(valueOf).length() + 27).append("Component not initialized: ").append(valueOf).toString());
        }
    }

    @WorkerThread
    private final void zza(zzclj zzclj) {
        zzcjl zzbay;
        zzayo().zzwj();
        zzcli zzcir = new zzcir(this);
        zzcir.initialize();
        this.zzjoj = zzcir;
        zzcir = new zzcje(this);
        zzcir.initialize();
        this.zzjok = zzcir;
        zzcli zzcil = new zzcil(this);
        zzcil.initialize();
        this.zzjog = zzcil;
        zzcil = new zzcjf(this);
        zzcil.initialize();
        this.zzjoh = zzcil;
        zzcil = new zzcih(this);
        zzcil.initialize();
        this.zzjon = zzcil;
        zzcil = new zzcme(this);
        zzcil.initialize();
        this.zzjoi = zzcil;
        zzcil = new zzcnj(this);
        zzcil.initialize();
        this.zzjom = zzcil;
        this.zzjol = new zzcjs(this);
        this.zzjoa.zzbcf();
        this.zzjnt.zzbcf();
        this.zzjok.zzbcf();
        zzayp().zzbay().zzj("App measurement is starting up, version", Long.valueOf(12211));
        zzayp().zzbay().log("To enable debug logging run: adb shell setprop log.tag.FA VERBOSE");
        String appId = zzcir.getAppId();
        if (zzayl().zzkq(appId)) {
            zzbay = zzayp().zzbay();
            appId = "Faster debug mode event logging enabled. To disable, run:\n  adb shell setprop debug.firebase.analytics.app .none.";
        } else {
            zzbay = zzayp().zzbay();
            String str = "To enable faster debug mode event logging run:\n  adb shell setprop debug.firebase.analytics.app ";
            appId = String.valueOf(appId);
            appId = appId.length() != 0 ? str.concat(appId) : new String(str);
        }
        zzbay.log(appId);
        zzayp().zzbaz().log("Debug-level message logging enabled");
        if (this.zzjov != this.zzjow) {
            zzayp().zzbau().zze("Not all components initialized", Integer.valueOf(this.zzjov), Integer.valueOf(this.zzjow));
        }
        this.initialized = true;
    }

    @WorkerThread
    private final boolean zza(int i, FileChannel fileChannel) {
        zzayo().zzwj();
        if (fileChannel == null || !fileChannel.isOpen()) {
            zzayp().zzbau().log("Bad channel to read from");
            return false;
        }
        ByteBuffer allocate = ByteBuffer.allocate(4);
        allocate.putInt(i);
        allocate.flip();
        try {
            fileChannel.truncate(0);
            fileChannel.write(allocate);
            fileChannel.force(true);
            if (fileChannel.size() == 4) {
                return true;
            }
            zzayp().zzbau().zzj("Error writing to channel. Bytes written", Long.valueOf(fileChannel.size()));
            return true;
        } catch (IOException e) {
            zzayp().zzbau().zzj("Failed to write to channel", e);
            return false;
        }
    }

    private static boolean zza(zzcob zzcob, String str, Object obj) {
        if (TextUtils.isEmpty(str) || obj == null) {
            return false;
        }
        zzcoc[] zzcocArr = zzcob.zzjui;
        int length = zzcocArr.length;
        int i = 0;
        while (i < length) {
            zzcoc zzcoc = zzcocArr[i];
            if (str.equals(zzcoc.name)) {
                return ((obj instanceof Long) && obj.equals(zzcoc.zzjum)) || (((obj instanceof String) && obj.equals(zzcoc.zzgim)) || ((obj instanceof Double) && obj.equals(zzcoc.zzjsl)));
            } else {
                i++;
            }
        }
        return false;
    }

    private final boolean zza(String str, zzcix zzcix) {
        long round;
        Object string = zzcix.zzjhr.getString(Param.CURRENCY);
        if (Event.ECOMMERCE_PURCHASE.equals(zzcix.name)) {
            double doubleValue = zzcix.zzjhr.getDouble(Param.VALUE).doubleValue() * 1000000.0d;
            if (doubleValue == 0.0d) {
                doubleValue = ((double) zzcix.zzjhr.getLong(Param.VALUE).longValue()) * 1000000.0d;
            }
            if (doubleValue > 9.223372036854776E18d || doubleValue < -9.223372036854776E18d) {
                zzayp().zzbaw().zze("Data lost. Currency value is too big. appId", zzcjj.zzjs(str), Double.valueOf(doubleValue));
                return false;
            }
            round = Math.round(doubleValue);
        } else {
            round = zzcix.zzjhr.getLong(Param.VALUE).longValue();
        }
        if (!TextUtils.isEmpty(string)) {
            String toUpperCase = string.toUpperCase(Locale.US);
            if (toUpperCase.matches("[A-Z]{3}")) {
                String valueOf = String.valueOf("_ltv_");
                toUpperCase = String.valueOf(toUpperCase);
                String concat = toUpperCase.length() != 0 ? valueOf.concat(toUpperCase) : new String(valueOf);
                zzcnn zzag = zzayj().zzag(str, concat);
                if (zzag == null || !(zzag.value instanceof Long)) {
                    zzclh zzayj = zzayj();
                    int zzb = this.zzjns.zzb(str, zzciz.zzjjr) - 1;
                    zzbq.zzgv(str);
                    zzayj.zzwj();
                    zzayj.zzyk();
                    try {
                        zzayj.getWritableDatabase().execSQL("delete from user_attributes where app_id=? and name in (select name from user_attributes where app_id=? and name like '_ltv_%' order by set_timestamp desc limit ?,10);", new String[]{str, str, String.valueOf(zzb)});
                    } catch (SQLiteException e) {
                        zzayj.zzayp().zzbau().zze("Error pruning currencies. appId", zzcjj.zzjs(str), e);
                    }
                    zzag = new zzcnn(str, zzcix.zzjgm, concat, this.zzdir.currentTimeMillis(), Long.valueOf(round));
                } else {
                    zzag = new zzcnn(str, zzcix.zzjgm, concat, this.zzdir.currentTimeMillis(), Long.valueOf(round + ((Long) zzag.value).longValue()));
                }
                if (!zzayj().zza(zzag)) {
                    zzayp().zzbau().zzd("Too many unique user properties are set. Ignoring user property. appId", zzcjj.zzjs(str), zzayk().zzjr(zzag.name), zzag.value);
                    zzayl().zza(str, 9, null, null, 0);
                }
            }
        }
        return true;
    }

    private final zzcoa[] zza(String str, zzcog[] zzcogArr, zzcob[] zzcobArr) {
        zzbq.zzgv(str);
        return zzayc().zza(str, zzcobArr, zzcogArr);
    }

    static void zzaxz() {
        throw new IllegalStateException("Unexpected call on client side");
    }

    @WorkerThread
    private final void zzb(zzcie zzcie) {
        zzayo().zzwj();
        if (TextUtils.isEmpty(zzcie.getGmpAppId())) {
            zzb(zzcie.getAppId(), 204, null, null, null);
            return;
        }
        String gmpAppId = zzcie.getGmpAppId();
        String appInstanceId = zzcie.getAppInstanceId();
        Builder builder = new Builder();
        Builder encodedAuthority = builder.scheme((String) zzciz.zzjir.get()).encodedAuthority((String) zzciz.zzjis.get());
        String str = "config/app/";
        String valueOf = String.valueOf(gmpAppId);
        encodedAuthority.path(valueOf.length() != 0 ? str.concat(valueOf) : new String(str)).appendQueryParameter("app_instance_id", appInstanceId).appendQueryParameter("platform", AbstractSpiCall.ANDROID_CLIENT_TYPE).appendQueryParameter("gmp_version", "12211");
        String uri = builder.build().toString();
        try {
            Map map;
            URL url = new URL(uri);
            zzayp().zzbba().zzj("Fetching remote configuration", zzcie.getAppId());
            zzcny zzka = zzaym().zzka(zzcie.getAppId());
            CharSequence zzkb = zzaym().zzkb(zzcie.getAppId());
            if (zzka == null || TextUtils.isEmpty(zzkb)) {
                map = null;
            } else {
                Map arrayMap = new ArrayMap();
                arrayMap.put("If-Modified-Since", zzkb);
                map = arrayMap;
            }
            this.zzjoz = true;
            zzclh zzbbs = zzbbs();
            appInstanceId = zzcie.getAppId();
            zzcjp zzckn = new zzckn(this);
            zzbbs.zzwj();
            zzbbs.zzyk();
            zzbq.checkNotNull(url);
            zzbq.checkNotNull(zzckn);
            zzbbs.zzayo().zzi(new zzcjr(zzbbs, appInstanceId, url, null, map, zzckn));
        } catch (MalformedURLException e) {
            zzayp().zzbau().zze("Failed to parse config URL. Not fetching. appId", zzcjj.zzjs(zzcie.getAppId()), uri);
        }
    }

    private final zzcjs zzbbt() {
        if (this.zzjol != null) {
            return this.zzjol;
        }
        throw new IllegalStateException("Network broadcast receiver not created");
    }

    private final zzcnj zzbbu() {
        zza(this.zzjom);
        return this.zzjom;
    }

    @WorkerThread
    private final boolean zzbbv() {
        zzayo().zzwj();
        try {
            this.zzjos = new RandomAccessFile(new File(this.zzaiq.getFilesDir(), "google_app_measurement.db"), "rw").getChannel();
            this.zzjor = this.zzjos.tryLock();
            if (this.zzjor != null) {
                zzayp().zzbba().log("Storage concurrent access okay");
                return true;
            }
            zzayp().zzbau().log("Storage concurrent data access panic");
            return false;
        } catch (FileNotFoundException e) {
            zzayp().zzbau().zzj("Failed to acquire storage lock", e);
        } catch (IOException e2) {
            zzayp().zzbau().zzj("Failed to access storage lock file", e2);
        }
    }

    private final long zzbbx() {
        long currentTimeMillis = this.zzdir.currentTimeMillis();
        zzclh zzayq = zzayq();
        zzayq.zzyk();
        zzayq.zzwj();
        long j = zzayq.zzjlr.get();
        if (j == 0) {
            j = (long) (zzayq.zzayl().zzbcr().nextInt(86400000) + 1);
            zzayq.zzjlr.set(j);
        }
        return ((((j + currentTimeMillis) / 1000) / 60) / 60) / 24;
    }

    private final boolean zzbbz() {
        zzayo().zzwj();
        zzyk();
        return zzayj().zzbab() || !TextUtils.isEmpty(zzayj().zzazw());
    }

    @WorkerThread
    private final void zzbca() {
        zzayo().zzwj();
        zzyk();
        if (zzbcd()) {
            long abs;
            if (this.zzjoy > 0) {
                abs = DateUtils.MILLIS_PER_HOUR - Math.abs(this.zzdir.elapsedRealtime() - this.zzjoy);
                if (abs > 0) {
                    zzayp().zzbba().zzj("Upload has been suspended. Will update scheduling later in approximately ms", Long.valueOf(abs));
                    zzbbt().unregister();
                    zzbbu().cancel();
                    return;
                }
                this.zzjoy = 0;
            }
            if (zzbbn() && zzbbz()) {
                long currentTimeMillis = this.zzdir.currentTimeMillis();
                long max = Math.max(0, ((Long) zzciz.zzjjn.get()).longValue());
                Object obj = (zzayj().zzbac() || zzayj().zzazx()) ? 1 : null;
                if (obj != null) {
                    CharSequence zzazu = this.zzjns.zzazu();
                    abs = (TextUtils.isEmpty(zzazu) || ".none.".equals(zzazu)) ? Math.max(0, ((Long) zzciz.zzjjh.get()).longValue()) : Math.max(0, ((Long) zzciz.zzjji.get()).longValue());
                } else {
                    abs = Math.max(0, ((Long) zzciz.zzjjg.get()).longValue());
                }
                long j = zzayq().zzjln.get();
                long j2 = zzayq().zzjlo.get();
                long max2 = Math.max(zzayj().zzazz(), zzayj().zzbaa());
                if (max2 == 0) {
                    currentTimeMillis = 0;
                } else {
                    max2 = currentTimeMillis - Math.abs(max2 - currentTimeMillis);
                    j2 = currentTimeMillis - Math.abs(j2 - currentTimeMillis);
                    j = Math.max(currentTimeMillis - Math.abs(j - currentTimeMillis), j2);
                    currentTimeMillis = max2 + max;
                    if (obj != null && j > 0) {
                        currentTimeMillis = Math.min(max2, j) + abs;
                    }
                    if (!zzayl().zzf(j, abs)) {
                        currentTimeMillis = j + abs;
                    }
                    if (j2 != 0 && j2 >= max2) {
                        for (int i = 0; i < Math.min(20, Math.max(0, ((Integer) zzciz.zzjjp.get()).intValue())); i++) {
                            currentTimeMillis += ((long) (1 << i)) * Math.max(0, ((Long) zzciz.zzjjo.get()).longValue());
                            if (currentTimeMillis > j2) {
                                break;
                            }
                        }
                        currentTimeMillis = 0;
                    }
                }
                if (currentTimeMillis == 0) {
                    zzayp().zzbba().log("Next upload time is 0");
                    zzbbt().unregister();
                    zzbbu().cancel();
                    return;
                } else if (zzbbs().zzaax()) {
                    long j3 = zzayq().zzjlp.get();
                    abs = Math.max(0, ((Long) zzciz.zzjje.get()).longValue());
                    abs = !zzayl().zzf(j3, abs) ? Math.max(currentTimeMillis, abs + j3) : currentTimeMillis;
                    zzbbt().unregister();
                    abs -= this.zzdir.currentTimeMillis();
                    if (abs <= 0) {
                        abs = Math.max(0, ((Long) zzciz.zzjjj.get()).longValue());
                        zzayq().zzjln.set(this.zzdir.currentTimeMillis());
                    }
                    zzayp().zzbba().zzj("Upload scheduled in approximately ms", Long.valueOf(abs));
                    zzbbu().zzs(abs);
                    return;
                } else {
                    zzayp().zzbba().log("No network");
                    zzbbt().zzaau();
                    zzbbu().cancel();
                    return;
                }
            }
            zzayp().zzbba().log("Nothing to upload or uploading impossible");
            zzbbt().unregister();
            zzbbu().cancel();
        }
    }

    @WorkerThread
    private final boolean zzbcd() {
        zzayo().zzwj();
        zzyk();
        return this.zzjoo;
    }

    @WorkerThread
    private final void zzbce() {
        zzayo().zzwj();
        if (this.zzjoz || this.zzjpa || this.zzjpb) {
            zzayp().zzbba().zzd("Not stopping services. fetch, network, upload", Boolean.valueOf(this.zzjoz), Boolean.valueOf(this.zzjpa), Boolean.valueOf(this.zzjpb));
            return;
        }
        zzayp().zzbba().log("Stopping uploading service(s)");
        if (this.zzjou != null) {
            for (Runnable run : this.zzjou) {
                run.run();
            }
            this.zzjou.clear();
        }
    }

    @WorkerThread
    private final Boolean zzc(zzcie zzcie) {
        try {
            if (zzcie.zzayx() != -2147483648L) {
                if (zzcie.zzayx() == ((long) zzbih.zzdd(this.zzaiq).getPackageInfo(zzcie.getAppId(), 0).versionCode)) {
                    return Boolean.valueOf(true);
                }
            }
            String str = zzbih.zzdd(this.zzaiq).getPackageInfo(zzcie.getAppId(), 0).versionName;
            if (zzcie.zzwo() != null && zzcie.zzwo().equals(str)) {
                return Boolean.valueOf(true);
            }
            return Boolean.valueOf(false);
        } catch (NameNotFoundException e) {
            return null;
        }
    }

    @WorkerThread
    private final void zzc(zzcix zzcix, zzcif zzcif) {
        zzbq.checkNotNull(zzcif);
        zzbq.zzgv(zzcif.packageName);
        long nanoTime = System.nanoTime();
        zzayo().zzwj();
        zzyk();
        String str = zzcif.packageName;
        zzayl();
        if (!zzcno.zzd(zzcix, zzcif)) {
            return;
        }
        if (!zzcif.zzjfv) {
            zzg(zzcif);
        } else if (zzaym().zzan(str, zzcix.name)) {
            zzayp().zzbaw().zze("Dropping blacklisted event. appId", zzcjj.zzjs(str), zzayk().zzjp(zzcix.name));
            Object obj = (zzayl().zzks(str) || zzayl().zzkt(str)) ? 1 : null;
            if (obj == null && !"_err".equals(zzcix.name)) {
                zzayl().zza(str, 11, "_ev", zzcix.name, 0);
            }
            if (obj != null) {
                zzcie zzjj = zzayj().zzjj(str);
                if (zzjj != null) {
                    if (Math.abs(this.zzdir.currentTimeMillis() - Math.max(zzjj.zzaze(), zzjj.zzazd())) > ((Long) zzciz.zzjjm.get()).longValue()) {
                        zzayp().zzbaz().log("Fetching config for blacklisted app");
                        zzb(zzjj);
                    }
                }
            }
        } else {
            if (zzayp().zzae(2)) {
                zzayp().zzbba().zzj("Logging event", zzayk().zzb(zzcix));
            }
            zzayj().beginTransaction();
            zzg(zzcif);
            if (("_iap".equals(zzcix.name) || Event.ECOMMERCE_PURCHASE.equals(zzcix.name)) && !zza(str, zzcix)) {
                zzayj().setTransactionSuccessful();
                zzayj().endTransaction();
                return;
            }
            zzcoe zzcoe;
            try {
                boolean zzkh = zzcno.zzkh(zzcix.name);
                boolean equals = "_err".equals(zzcix.name);
                zzcim zza = zzayj().zza(zzbbx(), str, true, zzkh, false, equals, false);
                long intValue = zza.zzjhf - ((long) ((Integer) zzciz.zzjix.get()).intValue());
                if (intValue > 0) {
                    if (intValue % 1000 == 1) {
                        zzayp().zzbau().zze("Data loss. Too many events logged. appId, count", zzcjj.zzjs(str), Long.valueOf(zza.zzjhf));
                    }
                    zzayj().setTransactionSuccessful();
                    zzayj().endTransaction();
                    return;
                }
                zzcit zzbb;
                zzcis zzcis;
                boolean z;
                if (zzkh) {
                    intValue = zza.zzjhe - ((long) ((Integer) zzciz.zzjiz.get()).intValue());
                    if (intValue > 0) {
                        if (intValue % 1000 == 1) {
                            zzayp().zzbau().zze("Data loss. Too many public events logged. appId, count", zzcjj.zzjs(str), Long.valueOf(zza.zzjhe));
                        }
                        zzayl().zza(str, 16, "_ev", zzcix.name, 0);
                        zzayj().setTransactionSuccessful();
                        zzayj().endTransaction();
                        return;
                    }
                }
                if (equals) {
                    intValue = zza.zzjhh - ((long) Math.max(0, Math.min(1000000, this.zzjns.zzb(zzcif.packageName, zzciz.zzjiy))));
                    if (intValue > 0) {
                        if (intValue == 1) {
                            zzayp().zzbau().zze("Too many error events logged. appId, count", zzcjj.zzjs(str), Long.valueOf(zza.zzjhh));
                        }
                        zzayj().setTransactionSuccessful();
                        zzayj().endTransaction();
                        return;
                    }
                }
                Bundle zzbao = zzcix.zzjhr.zzbao();
                zzayl().zza(zzbao, "_o", zzcix.zzjgm);
                if (zzayl().zzkq(str)) {
                    zzayl().zza(zzbao, "_dbg", Long.valueOf(1));
                    zzayl().zza(zzbao, "_r", Long.valueOf(1));
                }
                long zzjk = zzayj().zzjk(str);
                if (zzjk > 0) {
                    zzayp().zzbaw().zze("Data lost. Too many events stored on disk, deleted. appId", zzcjj.zzjs(str), Long.valueOf(zzjk));
                }
                zzcis zzcis2 = new zzcis(this, zzcix.zzjgm, str, zzcix.name, zzcix.zzjib, 0, zzbao);
                zzcit zzae = zzayj().zzae(str, zzcis2.name);
                if (zzae != null) {
                    zzcis2 = zzcis2.zza(this, zzae.zzjhu);
                    zzbb = zzae.zzbb(zzcis2.timestamp);
                    zzcis = zzcis2;
                } else if (zzayj().zzjn(str) < 500 || !zzkh) {
                    zzbb = new zzcit(str, zzcis2.name, 0, 0, zzcis2.timestamp, 0, null, null, null);
                    zzcis = zzcis2;
                } else {
                    zzayp().zzbau().zzd("Too many event names used, ignoring event. appId, name, supported count", zzcjj.zzjs(str), zzayk().zzjp(zzcis2.name), Integer.valueOf(500));
                    zzayl().zza(str, 8, null, null, 0);
                    zzayj().endTransaction();
                    return;
                }
                zzayj().zza(zzbb);
                zzayo().zzwj();
                zzyk();
                zzbq.checkNotNull(zzcis);
                zzbq.checkNotNull(zzcif);
                zzbq.zzgv(zzcis.zzcm);
                zzbq.checkArgument(zzcis.zzcm.equals(zzcif.packageName));
                zzcoe = new zzcoe();
                zzcoe.zzjup = Integer.valueOf(1);
                zzcoe.zzjux = AbstractSpiCall.ANDROID_CLIENT_TYPE;
                zzcoe.zzcm = zzcif.packageName;
                zzcoe.zzjfs = zzcif.zzjfs;
                zzcoe.zzina = zzcif.zzina;
                zzcoe.zzjvi = zzcif.zzjfr == -2147483648L ? null : Integer.valueOf((int) zzcif.zzjfr);
                zzcoe.zzjva = Long.valueOf(zzcif.zzjft);
                zzcoe.zzjfl = zzcif.zzjfl;
                zzcoe.zzjve = zzcif.zzjfu == 0 ? null : Long.valueOf(zzcif.zzjfu);
                Pair zzju = zzayq().zzju(zzcif.packageName);
                if (zzju == null || TextUtils.isEmpty((CharSequence) zzju.first)) {
                    if (!zzayf().zzec(this.zzaiq)) {
                        String string = Secure.getString(this.zzaiq.getContentResolver(), "android_id");
                        if (string == null) {
                            zzayp().zzbaw().zzj("null secure ID. appId", zzcjj.zzjs(zzcoe.zzcm));
                            string = "null";
                        } else if (string.isEmpty()) {
                            zzayp().zzbaw().zzj("empty secure ID. appId", zzcjj.zzjs(zzcoe.zzcm));
                        }
                        zzcoe.zzjvl = string;
                    }
                } else if (zzcif.zzjfx) {
                    zzcoe.zzjvc = (String) zzju.first;
                    zzcoe.zzjvd = (Boolean) zzju.second;
                }
                zzayf().zzyk();
                zzcoe.zzjuy = Build.MODEL;
                zzayf().zzyk();
                zzcoe.zzda = VERSION.RELEASE;
                zzcoe.zzjuz = Integer.valueOf((int) zzayf().zzbal());
                zzcoe.zzjho = zzayf().zzbam();
                zzcoe.zzjvb = null;
                zzcoe.zzjus = null;
                zzcoe.zzjut = null;
                zzcoe.zzjuu = null;
                zzcoe.zzfqm = Long.valueOf(zzcif.zzjfw);
                if (isEnabled() && zzcik.zzazv()) {
                    zzcoe.zzjvn = null;
                }
                zzcie zzjj2 = zzayj().zzjj(zzcif.packageName);
                if (zzjj2 == null) {
                    zzjj2 = new zzcie(this, zzcif.packageName);
                    zzjj2.zziy(zzaye().zzbaq());
                    zzjj2.zzjb(zzcif.zzjfn);
                    zzjj2.zziz(zzcif.zzjfl);
                    zzjj2.zzja(zzayq().zzjv(zzcif.packageName));
                    zzjj2.zzaq(0);
                    zzjj2.zzal(0);
                    zzjj2.zzam(0);
                    zzjj2.setAppVersion(zzcif.zzina);
                    zzjj2.zzan(zzcif.zzjfr);
                    zzjj2.zzjc(zzcif.zzjfs);
                    zzjj2.zzao(zzcif.zzjft);
                    zzjj2.zzap(zzcif.zzjfu);
                    zzjj2.setMeasurementEnabled(zzcif.zzjfv);
                    zzjj2.zzaz(zzcif.zzjfw);
                    zzayj().zza(zzjj2);
                }
                zzcoe.zzjfk = zzjj2.getAppInstanceId();
                zzcoe.zzjfn = zzjj2.zzayu();
                List zzji = zzayj().zzji(zzcif.packageName);
                zzcoe.zzjur = new zzcog[zzji.size()];
                for (int i = 0; i < zzji.size(); i++) {
                    zzcog zzcog = new zzcog();
                    zzcoe.zzjur[i] = zzcog;
                    zzcog.name = ((zzcnn) zzji.get(i)).name;
                    zzcog.zzjvr = Long.valueOf(((zzcnn) zzji.get(i)).zzjsi);
                    zzayl().zza(zzcog, ((zzcnn) zzji.get(i)).value);
                }
                long zza2 = zzayj().zza(zzcoe);
                zzcil zzayj = zzayj();
                if (zzcis.zzjhr != null) {
                    Iterator it = zzcis.zzjhr.iterator();
                    while (it.hasNext()) {
                        if ("_r".equals((String) it.next())) {
                            z = true;
                            break;
                        }
                    }
                    z = zzaym().zzao(zzcis.zzcm, zzcis.name);
                    zzcim zza3 = zzayj().zza(zzbbx(), zzcis.zzcm, false, false, false, false, false);
                    if (z && zza3.zzjhi < ((long) this.zzjns.zzje(zzcis.zzcm))) {
                        z = true;
                        if (zzayj.zza(zzcis, zza2, z)) {
                            this.zzjoy = 0;
                        }
                        zzayj().setTransactionSuccessful();
                        if (zzayp().zzae(2)) {
                            zzayp().zzbba().zzj("Event recorded", zzayk().zza(zzcis));
                        }
                        zzayj().endTransaction();
                        zzbca();
                        zzayp().zzbba().zzj("Background event processing time, ms", Long.valueOf(((System.nanoTime() - nanoTime) + 500000) / C0907C.MICROS_PER_SECOND));
                    }
                }
                z = false;
                if (zzayj.zza(zzcis, zza2, z)) {
                    this.zzjoy = 0;
                }
                zzayj().setTransactionSuccessful();
                if (zzayp().zzae(2)) {
                    zzayp().zzbba().zzj("Event recorded", zzayk().zza(zzcis));
                }
                zzayj().endTransaction();
                zzbca();
                zzayp().zzbba().zzj("Background event processing time, ms", Long.valueOf(((System.nanoTime() - nanoTime) + 500000) / C0907C.MICROS_PER_SECOND));
            } catch (IOException e) {
                zzayp().zzbau().zze("Data loss. Failed to insert raw event metadata. appId", zzcjj.zzjs(zzcoe.zzcm), e);
            } catch (Throwable th) {
                zzayj().endTransaction();
            }
        }
    }

    public static zzckj zzed(Context context) {
        zzbq.checkNotNull(context);
        zzbq.checkNotNull(context.getApplicationContext());
        if (zzjnr == null) {
            synchronized (zzckj.class) {
                if (zzjnr == null) {
                    zzjnr = new zzckj(new zzclj(context));
                }
            }
        }
        return zzjnr;
    }

    @WorkerThread
    private final void zzg(zzcif zzcif) {
        Object obj = 1;
        zzayo().zzwj();
        zzyk();
        zzbq.checkNotNull(zzcif);
        zzbq.zzgv(zzcif.packageName);
        zzcie zzjj = zzayj().zzjj(zzcif.packageName);
        String zzjv = zzayq().zzjv(zzcif.packageName);
        Object obj2 = null;
        if (zzjj == null) {
            zzcie zzcie = new zzcie(this, zzcif.packageName);
            zzcie.zziy(zzaye().zzbaq());
            zzcie.zzja(zzjv);
            zzjj = zzcie;
            obj2 = 1;
        } else if (!zzjv.equals(zzjj.zzayt())) {
            zzjj.zzja(zzjv);
            zzjj.zziy(zzaye().zzbaq());
            int i = 1;
        }
        if (!(TextUtils.isEmpty(zzcif.zzjfl) || zzcif.zzjfl.equals(zzjj.getGmpAppId()))) {
            zzjj.zziz(zzcif.zzjfl);
            obj2 = 1;
        }
        if (!(TextUtils.isEmpty(zzcif.zzjfn) || zzcif.zzjfn.equals(zzjj.zzayu()))) {
            zzjj.zzjb(zzcif.zzjfn);
            obj2 = 1;
        }
        if (!(zzcif.zzjft == 0 || zzcif.zzjft == zzjj.zzayz())) {
            zzjj.zzao(zzcif.zzjft);
            obj2 = 1;
        }
        if (!(TextUtils.isEmpty(zzcif.zzina) || zzcif.zzina.equals(zzjj.zzwo()))) {
            zzjj.setAppVersion(zzcif.zzina);
            obj2 = 1;
        }
        if (zzcif.zzjfr != zzjj.zzayx()) {
            zzjj.zzan(zzcif.zzjfr);
            obj2 = 1;
        }
        if (!(zzcif.zzjfs == null || zzcif.zzjfs.equals(zzjj.zzayy()))) {
            zzjj.zzjc(zzcif.zzjfs);
            obj2 = 1;
        }
        if (zzcif.zzjfu != zzjj.zzaza()) {
            zzjj.zzap(zzcif.zzjfu);
            obj2 = 1;
        }
        if (zzcif.zzjfv != zzjj.zzazb()) {
            zzjj.setMeasurementEnabled(zzcif.zzjfv);
            obj2 = 1;
        }
        if (!(TextUtils.isEmpty(zzcif.zzjgi) || zzcif.zzjgi.equals(zzjj.zzazm()))) {
            zzjj.zzjd(zzcif.zzjgi);
            obj2 = 1;
        }
        if (zzcif.zzjfw != zzjj.zzazo()) {
            zzjj.zzaz(zzcif.zzjfw);
            obj2 = 1;
        }
        if (zzcif.zzjfx != zzjj.zzazp()) {
            zzjj.zzbq(zzcif.zzjfx);
        } else {
            obj = obj2;
        }
        if (obj != null) {
            zzayj().zza(zzjj);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    @android.support.annotation.WorkerThread
    private final boolean zzg(java.lang.String r31, long r32) {
        /*
        r30 = this;
        r2 = r30.zzayj();
        r2.beginTransaction();
        r21 = new com.google.android.gms.internal.zzckj$zza;	 Catch:{ all -> 0x01c5 }
        r2 = 0;
        r0 = r21;
        r1 = r30;
        r0.<init>();	 Catch:{ all -> 0x01c5 }
        r14 = r30.zzayj();	 Catch:{ all -> 0x01c5 }
        r4 = 0;
        r0 = r30;
        r0 = r0.zzjox;	 Catch:{ all -> 0x01c5 }
        r16 = r0;
        com.google.android.gms.common.internal.zzbq.checkNotNull(r21);	 Catch:{ all -> 0x01c5 }
        r14.zzwj();	 Catch:{ all -> 0x01c5 }
        r14.zzyk();	 Catch:{ all -> 0x01c5 }
        r3 = 0;
        r2 = r14.getWritableDatabase();	 Catch:{ SQLiteException -> 0x0b87 }
        r5 = 0;
        r5 = android.text.TextUtils.isEmpty(r5);	 Catch:{ SQLiteException -> 0x0b87 }
        if (r5 == 0) goto L_0x01ce;
    L_0x0031:
        r6 = -1;
        r5 = (r16 > r6 ? 1 : (r16 == r6 ? 0 : -1));
        if (r5 == 0) goto L_0x0160;
    L_0x0037:
        r5 = 2;
        r5 = new java.lang.String[r5];	 Catch:{ SQLiteException -> 0x0b87 }
        r6 = 0;
        r7 = java.lang.String.valueOf(r16);	 Catch:{ SQLiteException -> 0x0b87 }
        r5[r6] = r7;	 Catch:{ SQLiteException -> 0x0b87 }
        r6 = 1;
        r7 = java.lang.String.valueOf(r32);	 Catch:{ SQLiteException -> 0x0b87 }
        r5[r6] = r7;	 Catch:{ SQLiteException -> 0x0b87 }
        r6 = r5;
    L_0x0049:
        r8 = -1;
        r5 = (r16 > r8 ? 1 : (r16 == r8 ? 0 : -1));
        if (r5 == 0) goto L_0x016d;
    L_0x004f:
        r5 = "rowid <= ? and ";
    L_0x0052:
        r7 = java.lang.String.valueOf(r5);	 Catch:{ SQLiteException -> 0x0b87 }
        r7 = r7.length();	 Catch:{ SQLiteException -> 0x0b87 }
        r7 = r7 + 148;
        r8 = new java.lang.StringBuilder;	 Catch:{ SQLiteException -> 0x0b87 }
        r8.<init>(r7);	 Catch:{ SQLiteException -> 0x0b87 }
        r7 = "select app_id, metadata_fingerprint from raw_events where ";
        r7 = r8.append(r7);	 Catch:{ SQLiteException -> 0x0b87 }
        r5 = r7.append(r5);	 Catch:{ SQLiteException -> 0x0b87 }
        r7 = "app_id in (select app_id from apps where config_fetched_time >= ?) order by rowid limit 1;";
        r5 = r5.append(r7);	 Catch:{ SQLiteException -> 0x0b87 }
        r5 = r5.toString();	 Catch:{ SQLiteException -> 0x0b87 }
        r3 = r2.rawQuery(r5, r6);	 Catch:{ SQLiteException -> 0x0b87 }
        r5 = r3.moveToFirst();	 Catch:{ SQLiteException -> 0x0b87 }
        if (r5 != 0) goto L_0x0172;
    L_0x0081:
        if (r3 == 0) goto L_0x0086;
    L_0x0083:
        r3.close();	 Catch:{ all -> 0x01c5 }
    L_0x0086:
        r0 = r21;
        r2 = r0.zzaoz;	 Catch:{ all -> 0x01c5 }
        if (r2 == 0) goto L_0x0096;
    L_0x008c:
        r0 = r21;
        r2 = r0.zzaoz;	 Catch:{ all -> 0x01c5 }
        r2 = r2.isEmpty();	 Catch:{ all -> 0x01c5 }
        if (r2 == 0) goto L_0x0372;
    L_0x0096:
        r2 = 1;
    L_0x0097:
        if (r2 != 0) goto L_0x0b72;
    L_0x0099:
        r17 = 0;
        r0 = r21;
        r0 = r0.zzjpe;	 Catch:{ all -> 0x01c5 }
        r22 = r0;
        r0 = r21;
        r2 = r0.zzaoz;	 Catch:{ all -> 0x01c5 }
        r2 = r2.size();	 Catch:{ all -> 0x01c5 }
        r2 = new com.google.android.gms.internal.zzcob[r2];	 Catch:{ all -> 0x01c5 }
        r0 = r22;
        r0.zzjuq = r2;	 Catch:{ all -> 0x01c5 }
        r13 = 0;
        r14 = 0;
        r0 = r30;
        r2 = r0.zzjns;	 Catch:{ all -> 0x01c5 }
        r0 = r22;
        r3 = r0.zzcm;	 Catch:{ all -> 0x01c5 }
        r18 = r2.zzjh(r3);	 Catch:{ all -> 0x01c5 }
        r2 = 0;
        r16 = r2;
    L_0x00c1:
        r0 = r21;
        r2 = r0.zzaoz;	 Catch:{ all -> 0x01c5 }
        r2 = r2.size();	 Catch:{ all -> 0x01c5 }
        r0 = r16;
        if (r0 >= r2) goto L_0x05ee;
    L_0x00cd:
        r0 = r21;
        r2 = r0.zzaoz;	 Catch:{ all -> 0x01c5 }
        r0 = r16;
        r2 = r2.get(r0);	 Catch:{ all -> 0x01c5 }
        r0 = r2;
        r0 = (com.google.android.gms.internal.zzcob) r0;	 Catch:{ all -> 0x01c5 }
        r12 = r0;
        r2 = r30.zzaym();	 Catch:{ all -> 0x01c5 }
        r0 = r21;
        r3 = r0.zzjpe;	 Catch:{ all -> 0x01c5 }
        r3 = r3.zzcm;	 Catch:{ all -> 0x01c5 }
        r4 = r12.name;	 Catch:{ all -> 0x01c5 }
        r2 = r2.zzan(r3, r4);	 Catch:{ all -> 0x01c5 }
        if (r2 == 0) goto L_0x0378;
    L_0x00ed:
        r2 = r30.zzayp();	 Catch:{ all -> 0x01c5 }
        r2 = r2.zzbaw();	 Catch:{ all -> 0x01c5 }
        r3 = "Dropping blacklisted raw event. appId";
        r0 = r21;
        r4 = r0.zzjpe;	 Catch:{ all -> 0x01c5 }
        r4 = r4.zzcm;	 Catch:{ all -> 0x01c5 }
        r4 = com.google.android.gms.internal.zzcjj.zzjs(r4);	 Catch:{ all -> 0x01c5 }
        r5 = r30.zzayk();	 Catch:{ all -> 0x01c5 }
        r6 = r12.name;	 Catch:{ all -> 0x01c5 }
        r5 = r5.zzjp(r6);	 Catch:{ all -> 0x01c5 }
        r2.zze(r3, r4, r5);	 Catch:{ all -> 0x01c5 }
        r2 = r30.zzayl();	 Catch:{ all -> 0x01c5 }
        r0 = r21;
        r3 = r0.zzjpe;	 Catch:{ all -> 0x01c5 }
        r3 = r3.zzcm;	 Catch:{ all -> 0x01c5 }
        r2 = r2.zzks(r3);	 Catch:{ all -> 0x01c5 }
        if (r2 != 0) goto L_0x012f;
    L_0x011f:
        r2 = r30.zzayl();	 Catch:{ all -> 0x01c5 }
        r0 = r21;
        r3 = r0.zzjpe;	 Catch:{ all -> 0x01c5 }
        r3 = r3.zzcm;	 Catch:{ all -> 0x01c5 }
        r2 = r2.zzkt(r3);	 Catch:{ all -> 0x01c5 }
        if (r2 == 0) goto L_0x0375;
    L_0x012f:
        r2 = 1;
    L_0x0130:
        if (r2 != 0) goto L_0x0ba7;
    L_0x0132:
        r2 = "_err";
        r3 = r12.name;	 Catch:{ all -> 0x01c5 }
        r2 = r2.equals(r3);	 Catch:{ all -> 0x01c5 }
        if (r2 != 0) goto L_0x0ba7;
    L_0x013d:
        r2 = r30.zzayl();	 Catch:{ all -> 0x01c5 }
        r0 = r21;
        r3 = r0.zzjpe;	 Catch:{ all -> 0x01c5 }
        r3 = r3.zzcm;	 Catch:{ all -> 0x01c5 }
        r4 = 11;
        r5 = "_ev";
        r6 = r12.name;	 Catch:{ all -> 0x01c5 }
        r7 = 0;
        r2.zza(r3, r4, r5, r6, r7);	 Catch:{ all -> 0x01c5 }
        r2 = r14;
        r4 = r13;
        r5 = r17;
    L_0x0156:
        r6 = r16 + 1;
        r16 = r6;
        r14 = r2;
        r13 = r4;
        r17 = r5;
        goto L_0x00c1;
    L_0x0160:
        r5 = 1;
        r5 = new java.lang.String[r5];	 Catch:{ SQLiteException -> 0x0b87 }
        r6 = 0;
        r7 = java.lang.String.valueOf(r32);	 Catch:{ SQLiteException -> 0x0b87 }
        r5[r6] = r7;	 Catch:{ SQLiteException -> 0x0b87 }
        r6 = r5;
        goto L_0x0049;
    L_0x016d:
        r5 = "";
        goto L_0x0052;
    L_0x0172:
        r5 = 0;
        r4 = r3.getString(r5);	 Catch:{ SQLiteException -> 0x0b87 }
        r5 = 1;
        r5 = r3.getString(r5);	 Catch:{ SQLiteException -> 0x0b87 }
        r3.close();	 Catch:{ SQLiteException -> 0x0b87 }
        r13 = r5;
        r11 = r3;
        r12 = r4;
    L_0x0182:
        r3 = "raw_events_metadata";
        r4 = 1;
        r4 = new java.lang.String[r4];	 Catch:{ SQLiteException -> 0x02f6, all -> 0x0b83 }
        r5 = 0;
        r6 = "metadata";
        r4[r5] = r6;	 Catch:{ SQLiteException -> 0x02f6, all -> 0x0b83 }
        r5 = "app_id = ? and metadata_fingerprint = ?";
        r6 = 2;
        r6 = new java.lang.String[r6];	 Catch:{ SQLiteException -> 0x02f6, all -> 0x0b83 }
        r7 = 0;
        r6[r7] = r12;	 Catch:{ SQLiteException -> 0x02f6, all -> 0x0b83 }
        r7 = 1;
        r6[r7] = r13;	 Catch:{ SQLiteException -> 0x02f6, all -> 0x0b83 }
        r7 = 0;
        r8 = 0;
        r9 = "rowid";
        r10 = "2";
        r11 = r2.query(r3, r4, r5, r6, r7, r8, r9, r10);	 Catch:{ SQLiteException -> 0x02f6, all -> 0x0b83 }
        r3 = r11.moveToFirst();	 Catch:{ SQLiteException -> 0x02f6, all -> 0x0b83 }
        if (r3 != 0) goto L_0x023c;
    L_0x01ac:
        r2 = r14.zzayp();	 Catch:{ SQLiteException -> 0x02f6, all -> 0x0b83 }
        r2 = r2.zzbau();	 Catch:{ SQLiteException -> 0x02f6, all -> 0x0b83 }
        r3 = "Raw event metadata record is missing. appId";
        r4 = com.google.android.gms.internal.zzcjj.zzjs(r12);	 Catch:{ SQLiteException -> 0x02f6, all -> 0x0b83 }
        r2.zzj(r3, r4);	 Catch:{ SQLiteException -> 0x02f6, all -> 0x0b83 }
        if (r11 == 0) goto L_0x0086;
    L_0x01c0:
        r11.close();	 Catch:{ all -> 0x01c5 }
        goto L_0x0086;
    L_0x01c5:
        r2 = move-exception;
        r3 = r30.zzayj();
        r3.endTransaction();
        throw r2;
    L_0x01ce:
        r6 = -1;
        r5 = (r16 > r6 ? 1 : (r16 == r6 ? 0 : -1));
        if (r5 == 0) goto L_0x0222;
    L_0x01d4:
        r5 = 2;
        r5 = new java.lang.String[r5];	 Catch:{ SQLiteException -> 0x0b87 }
        r6 = 0;
        r7 = 0;
        r5[r6] = r7;	 Catch:{ SQLiteException -> 0x0b87 }
        r6 = 1;
        r7 = java.lang.String.valueOf(r16);	 Catch:{ SQLiteException -> 0x0b87 }
        r5[r6] = r7;	 Catch:{ SQLiteException -> 0x0b87 }
        r6 = r5;
    L_0x01e3:
        r8 = -1;
        r5 = (r16 > r8 ? 1 : (r16 == r8 ? 0 : -1));
        if (r5 == 0) goto L_0x022b;
    L_0x01e9:
        r5 = " and rowid <= ?";
    L_0x01ec:
        r7 = java.lang.String.valueOf(r5);	 Catch:{ SQLiteException -> 0x0b87 }
        r7 = r7.length();	 Catch:{ SQLiteException -> 0x0b87 }
        r7 = r7 + 84;
        r8 = new java.lang.StringBuilder;	 Catch:{ SQLiteException -> 0x0b87 }
        r8.<init>(r7);	 Catch:{ SQLiteException -> 0x0b87 }
        r7 = "select metadata_fingerprint from raw_events where app_id = ?";
        r7 = r8.append(r7);	 Catch:{ SQLiteException -> 0x0b87 }
        r5 = r7.append(r5);	 Catch:{ SQLiteException -> 0x0b87 }
        r7 = " order by rowid limit 1;";
        r5 = r5.append(r7);	 Catch:{ SQLiteException -> 0x0b87 }
        r5 = r5.toString();	 Catch:{ SQLiteException -> 0x0b87 }
        r3 = r2.rawQuery(r5, r6);	 Catch:{ SQLiteException -> 0x0b87 }
        r5 = r3.moveToFirst();	 Catch:{ SQLiteException -> 0x0b87 }
        if (r5 != 0) goto L_0x022f;
    L_0x021b:
        if (r3 == 0) goto L_0x0086;
    L_0x021d:
        r3.close();	 Catch:{ all -> 0x01c5 }
        goto L_0x0086;
    L_0x0222:
        r5 = 1;
        r5 = new java.lang.String[r5];	 Catch:{ SQLiteException -> 0x0b87 }
        r6 = 0;
        r7 = 0;
        r5[r6] = r7;	 Catch:{ SQLiteException -> 0x0b87 }
        r6 = r5;
        goto L_0x01e3;
    L_0x022b:
        r5 = "";
        goto L_0x01ec;
    L_0x022f:
        r5 = 0;
        r5 = r3.getString(r5);	 Catch:{ SQLiteException -> 0x0b87 }
        r3.close();	 Catch:{ SQLiteException -> 0x0b87 }
        r13 = r5;
        r11 = r3;
        r12 = r4;
        goto L_0x0182;
    L_0x023c:
        r3 = 0;
        r3 = r11.getBlob(r3);	 Catch:{ SQLiteException -> 0x02f6, all -> 0x0b83 }
        r4 = 0;
        r5 = r3.length;	 Catch:{ SQLiteException -> 0x02f6, all -> 0x0b83 }
        r3 = com.google.android.gms.internal.zzflj.zzo(r3, r4, r5);	 Catch:{ SQLiteException -> 0x02f6, all -> 0x0b83 }
        r4 = new com.google.android.gms.internal.zzcoe;	 Catch:{ SQLiteException -> 0x02f6, all -> 0x0b83 }
        r4.<init>();	 Catch:{ SQLiteException -> 0x02f6, all -> 0x0b83 }
        r4.zza(r3);	 Catch:{ IOException -> 0x02cf }
        r3 = r11.moveToNext();	 Catch:{ SQLiteException -> 0x02f6, all -> 0x0b83 }
        if (r3 == 0) goto L_0x0267;
    L_0x0255:
        r3 = r14.zzayp();	 Catch:{ SQLiteException -> 0x02f6, all -> 0x0b83 }
        r3 = r3.zzbaw();	 Catch:{ SQLiteException -> 0x02f6, all -> 0x0b83 }
        r5 = "Get multiple raw event metadata records, expected one. appId";
        r6 = com.google.android.gms.internal.zzcjj.zzjs(r12);	 Catch:{ SQLiteException -> 0x02f6, all -> 0x0b83 }
        r3.zzj(r5, r6);	 Catch:{ SQLiteException -> 0x02f6, all -> 0x0b83 }
    L_0x0267:
        r11.close();	 Catch:{ SQLiteException -> 0x02f6, all -> 0x0b83 }
        r0 = r21;
        r0.zzb(r4);	 Catch:{ SQLiteException -> 0x02f6, all -> 0x0b83 }
        r4 = -1;
        r3 = (r16 > r4 ? 1 : (r16 == r4 ? 0 : -1));
        if (r3 == 0) goto L_0x02e9;
    L_0x0275:
        r5 = "app_id = ? and metadata_fingerprint = ? and rowid <= ?";
        r3 = 3;
        r6 = new java.lang.String[r3];	 Catch:{ SQLiteException -> 0x02f6, all -> 0x0b83 }
        r3 = 0;
        r6[r3] = r12;	 Catch:{ SQLiteException -> 0x02f6, all -> 0x0b83 }
        r3 = 1;
        r6[r3] = r13;	 Catch:{ SQLiteException -> 0x02f6, all -> 0x0b83 }
        r3 = 2;
        r4 = java.lang.String.valueOf(r16);	 Catch:{ SQLiteException -> 0x02f6, all -> 0x0b83 }
        r6[r3] = r4;	 Catch:{ SQLiteException -> 0x02f6, all -> 0x0b83 }
    L_0x0288:
        r3 = "raw_events";
        r4 = 4;
        r4 = new java.lang.String[r4];	 Catch:{ SQLiteException -> 0x02f6, all -> 0x0b83 }
        r7 = 0;
        r8 = "rowid";
        r4[r7] = r8;	 Catch:{ SQLiteException -> 0x02f6, all -> 0x0b83 }
        r7 = 1;
        r8 = "name";
        r4[r7] = r8;	 Catch:{ SQLiteException -> 0x02f6, all -> 0x0b83 }
        r7 = 2;
        r8 = "timestamp";
        r4[r7] = r8;	 Catch:{ SQLiteException -> 0x02f6, all -> 0x0b83 }
        r7 = 3;
        r8 = "data";
        r4[r7] = r8;	 Catch:{ SQLiteException -> 0x02f6, all -> 0x0b83 }
        r7 = 0;
        r8 = 0;
        r9 = "rowid";
        r10 = 0;
        r3 = r2.query(r3, r4, r5, r6, r7, r8, r9, r10);	 Catch:{ SQLiteException -> 0x02f6, all -> 0x0b83 }
        r2 = r3.moveToFirst();	 Catch:{ SQLiteException -> 0x0b8a }
        if (r2 != 0) goto L_0x0312;
    L_0x02b6:
        r2 = r14.zzayp();	 Catch:{ SQLiteException -> 0x0b8a }
        r2 = r2.zzbaw();	 Catch:{ SQLiteException -> 0x0b8a }
        r4 = "Raw event data disappeared while in transaction. appId";
        r5 = com.google.android.gms.internal.zzcjj.zzjs(r12);	 Catch:{ SQLiteException -> 0x0b8a }
        r2.zzj(r4, r5);	 Catch:{ SQLiteException -> 0x0b8a }
        if (r3 == 0) goto L_0x0086;
    L_0x02ca:
        r3.close();	 Catch:{ all -> 0x01c5 }
        goto L_0x0086;
    L_0x02cf:
        r2 = move-exception;
        r3 = r14.zzayp();	 Catch:{ SQLiteException -> 0x02f6, all -> 0x0b83 }
        r3 = r3.zzbau();	 Catch:{ SQLiteException -> 0x02f6, all -> 0x0b83 }
        r4 = "Data loss. Failed to merge raw event metadata. appId";
        r5 = com.google.android.gms.internal.zzcjj.zzjs(r12);	 Catch:{ SQLiteException -> 0x02f6, all -> 0x0b83 }
        r3.zze(r4, r5, r2);	 Catch:{ SQLiteException -> 0x02f6, all -> 0x0b83 }
        if (r11 == 0) goto L_0x0086;
    L_0x02e4:
        r11.close();	 Catch:{ all -> 0x01c5 }
        goto L_0x0086;
    L_0x02e9:
        r5 = "app_id = ? and metadata_fingerprint = ?";
        r3 = 2;
        r6 = new java.lang.String[r3];	 Catch:{ SQLiteException -> 0x02f6, all -> 0x0b83 }
        r3 = 0;
        r6[r3] = r12;	 Catch:{ SQLiteException -> 0x02f6, all -> 0x0b83 }
        r3 = 1;
        r6[r3] = r13;	 Catch:{ SQLiteException -> 0x02f6, all -> 0x0b83 }
        goto L_0x0288;
    L_0x02f6:
        r2 = move-exception;
        r3 = r11;
        r4 = r12;
    L_0x02f9:
        r5 = r14.zzayp();	 Catch:{ all -> 0x036b }
        r5 = r5.zzbau();	 Catch:{ all -> 0x036b }
        r6 = "Data loss. Error selecting raw event. appId";
        r4 = com.google.android.gms.internal.zzcjj.zzjs(r4);	 Catch:{ all -> 0x036b }
        r5.zze(r6, r4, r2);	 Catch:{ all -> 0x036b }
        if (r3 == 0) goto L_0x0086;
    L_0x030d:
        r3.close();	 Catch:{ all -> 0x01c5 }
        goto L_0x0086;
    L_0x0312:
        r2 = 0;
        r4 = r3.getLong(r2);	 Catch:{ SQLiteException -> 0x0b8a }
        r2 = 3;
        r2 = r3.getBlob(r2);	 Catch:{ SQLiteException -> 0x0b8a }
        r6 = 0;
        r7 = r2.length;	 Catch:{ SQLiteException -> 0x0b8a }
        r2 = com.google.android.gms.internal.zzflj.zzo(r2, r6, r7);	 Catch:{ SQLiteException -> 0x0b8a }
        r6 = new com.google.android.gms.internal.zzcob;	 Catch:{ SQLiteException -> 0x0b8a }
        r6.<init>();	 Catch:{ SQLiteException -> 0x0b8a }
        r6.zza(r2);	 Catch:{ IOException -> 0x034b }
        r2 = 1;
        r2 = r3.getString(r2);	 Catch:{ SQLiteException -> 0x0b8a }
        r6.name = r2;	 Catch:{ SQLiteException -> 0x0b8a }
        r2 = 2;
        r8 = r3.getLong(r2);	 Catch:{ SQLiteException -> 0x0b8a }
        r2 = java.lang.Long.valueOf(r8);	 Catch:{ SQLiteException -> 0x0b8a }
        r6.zzjuj = r2;	 Catch:{ SQLiteException -> 0x0b8a }
        r0 = r21;
        r2 = r0.zza(r4, r6);	 Catch:{ SQLiteException -> 0x0b8a }
        if (r2 != 0) goto L_0x035e;
    L_0x0344:
        if (r3 == 0) goto L_0x0086;
    L_0x0346:
        r3.close();	 Catch:{ all -> 0x01c5 }
        goto L_0x0086;
    L_0x034b:
        r2 = move-exception;
        r4 = r14.zzayp();	 Catch:{ SQLiteException -> 0x0b8a }
        r4 = r4.zzbau();	 Catch:{ SQLiteException -> 0x0b8a }
        r5 = "Data loss. Failed to merge raw event. appId";
        r6 = com.google.android.gms.internal.zzcjj.zzjs(r12);	 Catch:{ SQLiteException -> 0x0b8a }
        r4.zze(r5, r6, r2);	 Catch:{ SQLiteException -> 0x0b8a }
    L_0x035e:
        r2 = r3.moveToNext();	 Catch:{ SQLiteException -> 0x0b8a }
        if (r2 != 0) goto L_0x0312;
    L_0x0364:
        if (r3 == 0) goto L_0x0086;
    L_0x0366:
        r3.close();	 Catch:{ all -> 0x01c5 }
        goto L_0x0086;
    L_0x036b:
        r2 = move-exception;
    L_0x036c:
        if (r3 == 0) goto L_0x0371;
    L_0x036e:
        r3.close();	 Catch:{ all -> 0x01c5 }
    L_0x0371:
        throw r2;	 Catch:{ all -> 0x01c5 }
    L_0x0372:
        r2 = 0;
        goto L_0x0097;
    L_0x0375:
        r2 = 0;
        goto L_0x0130;
    L_0x0378:
        r2 = r30.zzaym();	 Catch:{ all -> 0x01c5 }
        r0 = r21;
        r3 = r0.zzjpe;	 Catch:{ all -> 0x01c5 }
        r3 = r3.zzcm;	 Catch:{ all -> 0x01c5 }
        r4 = r12.name;	 Catch:{ all -> 0x01c5 }
        r19 = r2.zzao(r3, r4);	 Catch:{ all -> 0x01c5 }
        if (r19 != 0) goto L_0x0395;
    L_0x038a:
        r30.zzayl();	 Catch:{ all -> 0x01c5 }
        r2 = r12.name;	 Catch:{ all -> 0x01c5 }
        r2 = com.google.android.gms.internal.zzcno.zzku(r2);	 Catch:{ all -> 0x01c5 }
        if (r2 == 0) goto L_0x05bc;
    L_0x0395:
        r4 = 0;
        r3 = 0;
        r2 = r12.zzjui;	 Catch:{ all -> 0x01c5 }
        if (r2 != 0) goto L_0x03a0;
    L_0x039b:
        r2 = 0;
        r2 = new com.google.android.gms.internal.zzcoc[r2];	 Catch:{ all -> 0x01c5 }
        r12.zzjui = r2;	 Catch:{ all -> 0x01c5 }
    L_0x03a0:
        r6 = r12.zzjui;	 Catch:{ all -> 0x01c5 }
        r7 = r6.length;	 Catch:{ all -> 0x01c5 }
        r2 = 0;
        r5 = r2;
    L_0x03a5:
        if (r5 >= r7) goto L_0x03de;
    L_0x03a7:
        r2 = r6[r5];	 Catch:{ all -> 0x01c5 }
        r8 = "_c";
        r9 = r2.name;	 Catch:{ all -> 0x01c5 }
        r8 = r8.equals(r9);	 Catch:{ all -> 0x01c5 }
        if (r8 == 0) goto L_0x03c8;
    L_0x03b4:
        r8 = 1;
        r4 = java.lang.Long.valueOf(r8);	 Catch:{ all -> 0x01c5 }
        r2.zzjum = r4;	 Catch:{ all -> 0x01c5 }
        r2 = 1;
        r29 = r3;
        r3 = r2;
        r2 = r29;
    L_0x03c2:
        r4 = r5 + 1;
        r5 = r4;
        r4 = r3;
        r3 = r2;
        goto L_0x03a5;
    L_0x03c8:
        r8 = "_r";
        r9 = r2.name;	 Catch:{ all -> 0x01c5 }
        r8 = r8.equals(r9);	 Catch:{ all -> 0x01c5 }
        if (r8 == 0) goto L_0x0ba3;
    L_0x03d3:
        r8 = 1;
        r3 = java.lang.Long.valueOf(r8);	 Catch:{ all -> 0x01c5 }
        r2.zzjum = r3;	 Catch:{ all -> 0x01c5 }
        r2 = 1;
        r3 = r4;
        goto L_0x03c2;
    L_0x03de:
        if (r4 != 0) goto L_0x0420;
    L_0x03e0:
        if (r19 == 0) goto L_0x0420;
    L_0x03e2:
        r2 = r30.zzayp();	 Catch:{ all -> 0x01c5 }
        r2 = r2.zzbba();	 Catch:{ all -> 0x01c5 }
        r4 = "Marking event as conversion";
        r5 = r30.zzayk();	 Catch:{ all -> 0x01c5 }
        r6 = r12.name;	 Catch:{ all -> 0x01c5 }
        r5 = r5.zzjp(r6);	 Catch:{ all -> 0x01c5 }
        r2.zzj(r4, r5);	 Catch:{ all -> 0x01c5 }
        r2 = r12.zzjui;	 Catch:{ all -> 0x01c5 }
        r4 = r12.zzjui;	 Catch:{ all -> 0x01c5 }
        r4 = r4.length;	 Catch:{ all -> 0x01c5 }
        r4 = r4 + 1;
        r2 = java.util.Arrays.copyOf(r2, r4);	 Catch:{ all -> 0x01c5 }
        r2 = (com.google.android.gms.internal.zzcoc[]) r2;	 Catch:{ all -> 0x01c5 }
        r4 = new com.google.android.gms.internal.zzcoc;	 Catch:{ all -> 0x01c5 }
        r4.<init>();	 Catch:{ all -> 0x01c5 }
        r5 = "_c";
        r4.name = r5;	 Catch:{ all -> 0x01c5 }
        r6 = 1;
        r5 = java.lang.Long.valueOf(r6);	 Catch:{ all -> 0x01c5 }
        r4.zzjum = r5;	 Catch:{ all -> 0x01c5 }
        r5 = r2.length;	 Catch:{ all -> 0x01c5 }
        r5 = r5 + -1;
        r2[r5] = r4;	 Catch:{ all -> 0x01c5 }
        r12.zzjui = r2;	 Catch:{ all -> 0x01c5 }
    L_0x0420:
        if (r3 != 0) goto L_0x0460;
    L_0x0422:
        r2 = r30.zzayp();	 Catch:{ all -> 0x01c5 }
        r2 = r2.zzbba();	 Catch:{ all -> 0x01c5 }
        r3 = "Marking event as real-time";
        r4 = r30.zzayk();	 Catch:{ all -> 0x01c5 }
        r5 = r12.name;	 Catch:{ all -> 0x01c5 }
        r4 = r4.zzjp(r5);	 Catch:{ all -> 0x01c5 }
        r2.zzj(r3, r4);	 Catch:{ all -> 0x01c5 }
        r2 = r12.zzjui;	 Catch:{ all -> 0x01c5 }
        r3 = r12.zzjui;	 Catch:{ all -> 0x01c5 }
        r3 = r3.length;	 Catch:{ all -> 0x01c5 }
        r3 = r3 + 1;
        r2 = java.util.Arrays.copyOf(r2, r3);	 Catch:{ all -> 0x01c5 }
        r2 = (com.google.android.gms.internal.zzcoc[]) r2;	 Catch:{ all -> 0x01c5 }
        r3 = new com.google.android.gms.internal.zzcoc;	 Catch:{ all -> 0x01c5 }
        r3.<init>();	 Catch:{ all -> 0x01c5 }
        r4 = "_r";
        r3.name = r4;	 Catch:{ all -> 0x01c5 }
        r4 = 1;
        r4 = java.lang.Long.valueOf(r4);	 Catch:{ all -> 0x01c5 }
        r3.zzjum = r4;	 Catch:{ all -> 0x01c5 }
        r4 = r2.length;	 Catch:{ all -> 0x01c5 }
        r4 = r4 + -1;
        r2[r4] = r3;	 Catch:{ all -> 0x01c5 }
        r12.zzjui = r2;	 Catch:{ all -> 0x01c5 }
    L_0x0460:
        r2 = 1;
        r3 = r30.zzayj();	 Catch:{ all -> 0x01c5 }
        r4 = r30.zzbbx();	 Catch:{ all -> 0x01c5 }
        r0 = r21;
        r6 = r0.zzjpe;	 Catch:{ all -> 0x01c5 }
        r6 = r6.zzcm;	 Catch:{ all -> 0x01c5 }
        r7 = 0;
        r8 = 0;
        r9 = 0;
        r10 = 0;
        r11 = 1;
        r3 = r3.zza(r4, r6, r7, r8, r9, r10, r11);	 Catch:{ all -> 0x01c5 }
        r4 = r3.zzjhi;	 Catch:{ all -> 0x01c5 }
        r0 = r30;
        r3 = r0.zzjns;	 Catch:{ all -> 0x01c5 }
        r0 = r21;
        r6 = r0.zzjpe;	 Catch:{ all -> 0x01c5 }
        r6 = r6.zzcm;	 Catch:{ all -> 0x01c5 }
        r3 = r3.zzje(r6);	 Catch:{ all -> 0x01c5 }
        r6 = (long) r3;	 Catch:{ all -> 0x01c5 }
        r3 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r3 <= 0) goto L_0x0b9f;
    L_0x048d:
        r2 = 0;
    L_0x048e:
        r3 = r12.zzjui;	 Catch:{ all -> 0x01c5 }
        r3 = r3.length;	 Catch:{ all -> 0x01c5 }
        if (r2 >= r3) goto L_0x04c0;
    L_0x0493:
        r3 = "_r";
        r4 = r12.zzjui;	 Catch:{ all -> 0x01c5 }
        r4 = r4[r2];	 Catch:{ all -> 0x01c5 }
        r4 = r4.name;	 Catch:{ all -> 0x01c5 }
        r3 = r3.equals(r4);	 Catch:{ all -> 0x01c5 }
        if (r3 == 0) goto L_0x052d;
    L_0x04a2:
        r3 = r12.zzjui;	 Catch:{ all -> 0x01c5 }
        r3 = r3.length;	 Catch:{ all -> 0x01c5 }
        r3 = r3 + -1;
        r3 = new com.google.android.gms.internal.zzcoc[r3];	 Catch:{ all -> 0x01c5 }
        if (r2 <= 0) goto L_0x04b2;
    L_0x04ab:
        r4 = r12.zzjui;	 Catch:{ all -> 0x01c5 }
        r5 = 0;
        r6 = 0;
        java.lang.System.arraycopy(r4, r5, r3, r6, r2);	 Catch:{ all -> 0x01c5 }
    L_0x04b2:
        r4 = r3.length;	 Catch:{ all -> 0x01c5 }
        if (r2 >= r4) goto L_0x04be;
    L_0x04b5:
        r4 = r12.zzjui;	 Catch:{ all -> 0x01c5 }
        r5 = r2 + 1;
        r6 = r3.length;	 Catch:{ all -> 0x01c5 }
        r6 = r6 - r2;
        java.lang.System.arraycopy(r4, r5, r3, r2, r6);	 Catch:{ all -> 0x01c5 }
    L_0x04be:
        r12.zzjui = r3;	 Catch:{ all -> 0x01c5 }
    L_0x04c0:
        r2 = r12.name;	 Catch:{ all -> 0x01c5 }
        r2 = com.google.android.gms.internal.zzcno.zzkh(r2);	 Catch:{ all -> 0x01c5 }
        if (r2 == 0) goto L_0x05bc;
    L_0x04c8:
        if (r19 == 0) goto L_0x05bc;
    L_0x04ca:
        r3 = r30.zzayj();	 Catch:{ all -> 0x01c5 }
        r4 = r30.zzbbx();	 Catch:{ all -> 0x01c5 }
        r0 = r21;
        r2 = r0.zzjpe;	 Catch:{ all -> 0x01c5 }
        r6 = r2.zzcm;	 Catch:{ all -> 0x01c5 }
        r7 = 0;
        r8 = 0;
        r9 = 1;
        r10 = 0;
        r11 = 0;
        r2 = r3.zza(r4, r6, r7, r8, r9, r10, r11);	 Catch:{ all -> 0x01c5 }
        r2 = r2.zzjhg;	 Catch:{ all -> 0x01c5 }
        r0 = r30;
        r4 = r0.zzjns;	 Catch:{ all -> 0x01c5 }
        r0 = r21;
        r5 = r0.zzjpe;	 Catch:{ all -> 0x01c5 }
        r5 = r5.zzcm;	 Catch:{ all -> 0x01c5 }
        r6 = com.google.android.gms.internal.zzciz.zzjja;	 Catch:{ all -> 0x01c5 }
        r4 = r4.zzb(r5, r6);	 Catch:{ all -> 0x01c5 }
        r4 = (long) r4;	 Catch:{ all -> 0x01c5 }
        r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r2 <= 0) goto L_0x05bc;
    L_0x04f8:
        r2 = r30.zzayp();	 Catch:{ all -> 0x01c5 }
        r2 = r2.zzbaw();	 Catch:{ all -> 0x01c5 }
        r3 = "Too many conversions. Not logging as conversion. appId";
        r0 = r21;
        r4 = r0.zzjpe;	 Catch:{ all -> 0x01c5 }
        r4 = r4.zzcm;	 Catch:{ all -> 0x01c5 }
        r4 = com.google.android.gms.internal.zzcjj.zzjs(r4);	 Catch:{ all -> 0x01c5 }
        r2.zzj(r3, r4);	 Catch:{ all -> 0x01c5 }
        r4 = 0;
        r3 = 0;
        r6 = r12.zzjui;	 Catch:{ all -> 0x01c5 }
        r7 = r6.length;	 Catch:{ all -> 0x01c5 }
        r2 = 0;
        r5 = r2;
    L_0x0517:
        if (r5 >= r7) goto L_0x0543;
    L_0x0519:
        r2 = r6[r5];	 Catch:{ all -> 0x01c5 }
        r8 = "_c";
        r9 = r2.name;	 Catch:{ all -> 0x01c5 }
        r8 = r8.equals(r9);	 Catch:{ all -> 0x01c5 }
        if (r8 == 0) goto L_0x0531;
    L_0x0526:
        r3 = r4;
    L_0x0527:
        r4 = r5 + 1;
        r5 = r4;
        r4 = r3;
        r3 = r2;
        goto L_0x0517;
    L_0x052d:
        r2 = r2 + 1;
        goto L_0x048e;
    L_0x0531:
        r8 = "_err";
        r2 = r2.name;	 Catch:{ all -> 0x01c5 }
        r2 = r8.equals(r2);	 Catch:{ all -> 0x01c5 }
        if (r2 == 0) goto L_0x0b9b;
    L_0x053c:
        r2 = 1;
        r29 = r3;
        r3 = r2;
        r2 = r29;
        goto L_0x0527;
    L_0x0543:
        if (r4 == 0) goto L_0x0592;
    L_0x0545:
        if (r3 == 0) goto L_0x0592;
    L_0x0547:
        r2 = r12.zzjui;	 Catch:{ all -> 0x01c5 }
        r4 = 1;
        r4 = new com.google.android.gms.internal.zzcoc[r4];	 Catch:{ all -> 0x01c5 }
        r5 = 0;
        r4[r5] = r3;	 Catch:{ all -> 0x01c5 }
        r2 = com.google.android.gms.common.util.zzb.zza(r2, r4);	 Catch:{ all -> 0x01c5 }
        r2 = (com.google.android.gms.internal.zzcoc[]) r2;	 Catch:{ all -> 0x01c5 }
        r12.zzjui = r2;	 Catch:{ all -> 0x01c5 }
        r5 = r17;
    L_0x0559:
        if (r18 == 0) goto L_0x0b98;
    L_0x055b:
        r2 = "_e";
        r3 = r12.name;	 Catch:{ all -> 0x01c5 }
        r2 = r2.equals(r3);	 Catch:{ all -> 0x01c5 }
        if (r2 == 0) goto L_0x0b98;
    L_0x0566:
        r2 = r12.zzjui;	 Catch:{ all -> 0x01c5 }
        if (r2 == 0) goto L_0x056f;
    L_0x056a:
        r2 = r12.zzjui;	 Catch:{ all -> 0x01c5 }
        r2 = r2.length;	 Catch:{ all -> 0x01c5 }
        if (r2 != 0) goto L_0x05bf;
    L_0x056f:
        r2 = r30.zzayp();	 Catch:{ all -> 0x01c5 }
        r2 = r2.zzbaw();	 Catch:{ all -> 0x01c5 }
        r3 = "Engagement event does not contain any parameters. appId";
        r0 = r21;
        r4 = r0.zzjpe;	 Catch:{ all -> 0x01c5 }
        r4 = r4.zzcm;	 Catch:{ all -> 0x01c5 }
        r4 = com.google.android.gms.internal.zzcjj.zzjs(r4);	 Catch:{ all -> 0x01c5 }
        r2.zzj(r3, r4);	 Catch:{ all -> 0x01c5 }
        r2 = r14;
    L_0x0588:
        r0 = r22;
        r6 = r0.zzjuq;	 Catch:{ all -> 0x01c5 }
        r4 = r13 + 1;
        r6[r13] = r12;	 Catch:{ all -> 0x01c5 }
        goto L_0x0156;
    L_0x0592:
        if (r3 == 0) goto L_0x05a4;
    L_0x0594:
        r2 = "_err";
        r3.name = r2;	 Catch:{ all -> 0x01c5 }
        r4 = 10;
        r2 = java.lang.Long.valueOf(r4);	 Catch:{ all -> 0x01c5 }
        r3.zzjum = r2;	 Catch:{ all -> 0x01c5 }
        r5 = r17;
        goto L_0x0559;
    L_0x05a4:
        r2 = r30.zzayp();	 Catch:{ all -> 0x01c5 }
        r2 = r2.zzbau();	 Catch:{ all -> 0x01c5 }
        r3 = "Did not find conversion parameter. appId";
        r0 = r21;
        r4 = r0.zzjpe;	 Catch:{ all -> 0x01c5 }
        r4 = r4.zzcm;	 Catch:{ all -> 0x01c5 }
        r4 = com.google.android.gms.internal.zzcjj.zzjs(r4);	 Catch:{ all -> 0x01c5 }
        r2.zzj(r3, r4);	 Catch:{ all -> 0x01c5 }
    L_0x05bc:
        r5 = r17;
        goto L_0x0559;
    L_0x05bf:
        r30.zzayl();	 Catch:{ all -> 0x01c5 }
        r2 = "_et";
        r2 = com.google.android.gms.internal.zzcno.zzb(r12, r2);	 Catch:{ all -> 0x01c5 }
        r2 = (java.lang.Long) r2;	 Catch:{ all -> 0x01c5 }
        if (r2 != 0) goto L_0x05e7;
    L_0x05cd:
        r2 = r30.zzayp();	 Catch:{ all -> 0x01c5 }
        r2 = r2.zzbaw();	 Catch:{ all -> 0x01c5 }
        r3 = "Engagement event does not include duration. appId";
        r0 = r21;
        r4 = r0.zzjpe;	 Catch:{ all -> 0x01c5 }
        r4 = r4.zzcm;	 Catch:{ all -> 0x01c5 }
        r4 = com.google.android.gms.internal.zzcjj.zzjs(r4);	 Catch:{ all -> 0x01c5 }
        r2.zzj(r3, r4);	 Catch:{ all -> 0x01c5 }
        r2 = r14;
        goto L_0x0588;
    L_0x05e7:
        r2 = r2.longValue();	 Catch:{ all -> 0x01c5 }
        r14 = r14 + r2;
        r2 = r14;
        goto L_0x0588;
    L_0x05ee:
        r0 = r21;
        r2 = r0.zzaoz;	 Catch:{ all -> 0x01c5 }
        r2 = r2.size();	 Catch:{ all -> 0x01c5 }
        if (r13 >= r2) goto L_0x0606;
    L_0x05f8:
        r0 = r22;
        r2 = r0.zzjuq;	 Catch:{ all -> 0x01c5 }
        r2 = java.util.Arrays.copyOf(r2, r13);	 Catch:{ all -> 0x01c5 }
        r2 = (com.google.android.gms.internal.zzcob[]) r2;	 Catch:{ all -> 0x01c5 }
        r0 = r22;
        r0.zzjuq = r2;	 Catch:{ all -> 0x01c5 }
    L_0x0606:
        if (r18 == 0) goto L_0x06bb;
    L_0x0608:
        r2 = r30.zzayj();	 Catch:{ all -> 0x01c5 }
        r0 = r22;
        r3 = r0.zzcm;	 Catch:{ all -> 0x01c5 }
        r4 = "_lte";
        r8 = r2.zzag(r3, r4);	 Catch:{ all -> 0x01c5 }
        if (r8 == 0) goto L_0x061d;
    L_0x0619:
        r2 = r8.value;	 Catch:{ all -> 0x01c5 }
        if (r2 != 0) goto L_0x07b7;
    L_0x061d:
        r2 = new com.google.android.gms.internal.zzcnn;	 Catch:{ all -> 0x01c5 }
        r0 = r22;
        r3 = r0.zzcm;	 Catch:{ all -> 0x01c5 }
        r4 = "auto";
        r5 = "_lte";
        r0 = r30;
        r6 = r0.zzdir;	 Catch:{ all -> 0x01c5 }
        r6 = r6.currentTimeMillis();	 Catch:{ all -> 0x01c5 }
        r8 = java.lang.Long.valueOf(r14);	 Catch:{ all -> 0x01c5 }
        r2.<init>(r3, r4, r5, r6, r8);	 Catch:{ all -> 0x01c5 }
        r4 = r2;
    L_0x0639:
        r5 = new com.google.android.gms.internal.zzcog;	 Catch:{ all -> 0x01c5 }
        r5.<init>();	 Catch:{ all -> 0x01c5 }
        r2 = "_lte";
        r5.name = r2;	 Catch:{ all -> 0x01c5 }
        r0 = r30;
        r2 = r0.zzdir;	 Catch:{ all -> 0x01c5 }
        r2 = r2.currentTimeMillis();	 Catch:{ all -> 0x01c5 }
        r2 = java.lang.Long.valueOf(r2);	 Catch:{ all -> 0x01c5 }
        r5.zzjvr = r2;	 Catch:{ all -> 0x01c5 }
        r2 = r4.value;	 Catch:{ all -> 0x01c5 }
        r2 = (java.lang.Long) r2;	 Catch:{ all -> 0x01c5 }
        r5.zzjum = r2;	 Catch:{ all -> 0x01c5 }
        r2 = 0;
        r3 = 0;
    L_0x0659:
        r0 = r22;
        r6 = r0.zzjur;	 Catch:{ all -> 0x01c5 }
        r6 = r6.length;	 Catch:{ all -> 0x01c5 }
        if (r3 >= r6) goto L_0x0678;
    L_0x0660:
        r6 = "_lte";
        r0 = r22;
        r7 = r0.zzjur;	 Catch:{ all -> 0x01c5 }
        r7 = r7[r3];	 Catch:{ all -> 0x01c5 }
        r7 = r7.name;	 Catch:{ all -> 0x01c5 }
        r6 = r6.equals(r7);	 Catch:{ all -> 0x01c5 }
        if (r6 == 0) goto L_0x07de;
    L_0x0671:
        r0 = r22;
        r2 = r0.zzjur;	 Catch:{ all -> 0x01c5 }
        r2[r3] = r5;	 Catch:{ all -> 0x01c5 }
        r2 = 1;
    L_0x0678:
        if (r2 != 0) goto L_0x069e;
    L_0x067a:
        r0 = r22;
        r2 = r0.zzjur;	 Catch:{ all -> 0x01c5 }
        r0 = r22;
        r3 = r0.zzjur;	 Catch:{ all -> 0x01c5 }
        r3 = r3.length;	 Catch:{ all -> 0x01c5 }
        r3 = r3 + 1;
        r2 = java.util.Arrays.copyOf(r2, r3);	 Catch:{ all -> 0x01c5 }
        r2 = (com.google.android.gms.internal.zzcog[]) r2;	 Catch:{ all -> 0x01c5 }
        r0 = r22;
        r0.zzjur = r2;	 Catch:{ all -> 0x01c5 }
        r0 = r22;
        r2 = r0.zzjur;	 Catch:{ all -> 0x01c5 }
        r0 = r21;
        r3 = r0.zzjpe;	 Catch:{ all -> 0x01c5 }
        r3 = r3.zzjur;	 Catch:{ all -> 0x01c5 }
        r3 = r3.length;	 Catch:{ all -> 0x01c5 }
        r3 = r3 + -1;
        r2[r3] = r5;	 Catch:{ all -> 0x01c5 }
    L_0x069e:
        r2 = 0;
        r2 = (r14 > r2 ? 1 : (r14 == r2 ? 0 : -1));
        if (r2 <= 0) goto L_0x06bb;
    L_0x06a4:
        r2 = r30.zzayj();	 Catch:{ all -> 0x01c5 }
        r2.zza(r4);	 Catch:{ all -> 0x01c5 }
        r2 = r30.zzayp();	 Catch:{ all -> 0x01c5 }
        r2 = r2.zzbaz();	 Catch:{ all -> 0x01c5 }
        r3 = "Updated lifetime engagement user property with value. Value";
        r4 = r4.value;	 Catch:{ all -> 0x01c5 }
        r2.zzj(r3, r4);	 Catch:{ all -> 0x01c5 }
    L_0x06bb:
        r0 = r22;
        r2 = r0.zzcm;	 Catch:{ all -> 0x01c5 }
        r0 = r22;
        r3 = r0.zzjur;	 Catch:{ all -> 0x01c5 }
        r0 = r22;
        r4 = r0.zzjuq;	 Catch:{ all -> 0x01c5 }
        r0 = r30;
        r2 = r0.zza(r2, r3, r4);	 Catch:{ all -> 0x01c5 }
        r0 = r22;
        r0.zzjvh = r2;	 Catch:{ all -> 0x01c5 }
        r2 = com.google.android.gms.internal.zzciz.zzjim;	 Catch:{ all -> 0x01c5 }
        r2 = r2.get();	 Catch:{ all -> 0x01c5 }
        r2 = (java.lang.Boolean) r2;	 Catch:{ all -> 0x01c5 }
        r2 = r2.booleanValue();	 Catch:{ all -> 0x01c5 }
        if (r2 == 0) goto L_0x09e1;
    L_0x06df:
        r0 = r30;
        r2 = r0.zzjns;	 Catch:{ all -> 0x01c5 }
        r0 = r21;
        r3 = r0.zzjpe;	 Catch:{ all -> 0x01c5 }
        r3 = r3.zzcm;	 Catch:{ all -> 0x01c5 }
        r4 = "1";
        r2 = r2.zzaym();	 Catch:{ all -> 0x01c5 }
        r5 = "measurement.event_sampling_enabled";
        r2 = r2.zzam(r3, r5);	 Catch:{ all -> 0x01c5 }
        r2 = r4.equals(r2);	 Catch:{ all -> 0x01c5 }
        if (r2 == 0) goto L_0x09e1;
    L_0x06fd:
        r23 = new java.util.HashMap;	 Catch:{ all -> 0x01c5 }
        r23.<init>();	 Catch:{ all -> 0x01c5 }
        r0 = r22;
        r2 = r0.zzjuq;	 Catch:{ all -> 0x01c5 }
        r2 = r2.length;	 Catch:{ all -> 0x01c5 }
        r0 = new com.google.android.gms.internal.zzcob[r2];	 Catch:{ all -> 0x01c5 }
        r24 = r0;
        r18 = 0;
        r2 = r30.zzayl();	 Catch:{ all -> 0x01c5 }
        r25 = r2.zzbcr();	 Catch:{ all -> 0x01c5 }
        r0 = r22;
        r0 = r0.zzjuq;	 Catch:{ all -> 0x01c5 }
        r26 = r0;
        r0 = r26;
        r0 = r0.length;	 Catch:{ all -> 0x01c5 }
        r27 = r0;
        r2 = 0;
        r20 = r2;
    L_0x0723:
        r0 = r20;
        r1 = r27;
        if (r0 >= r1) goto L_0x09a8;
    L_0x0729:
        r28 = r26[r20];	 Catch:{ all -> 0x01c5 }
        r0 = r28;
        r2 = r0.name;	 Catch:{ all -> 0x01c5 }
        r3 = "_ep";
        r2 = r2.equals(r3);	 Catch:{ all -> 0x01c5 }
        if (r2 == 0) goto L_0x07e2;
    L_0x0738:
        r30.zzayl();	 Catch:{ all -> 0x01c5 }
        r2 = "_en";
        r0 = r28;
        r2 = com.google.android.gms.internal.zzcno.zzb(r0, r2);	 Catch:{ all -> 0x01c5 }
        r2 = (java.lang.String) r2;	 Catch:{ all -> 0x01c5 }
        r0 = r23;
        r3 = r0.get(r2);	 Catch:{ all -> 0x01c5 }
        r3 = (com.google.android.gms.internal.zzcit) r3;	 Catch:{ all -> 0x01c5 }
        if (r3 != 0) goto L_0x0763;
    L_0x0750:
        r3 = r30.zzayj();	 Catch:{ all -> 0x01c5 }
        r0 = r21;
        r4 = r0.zzjpe;	 Catch:{ all -> 0x01c5 }
        r4 = r4.zzcm;	 Catch:{ all -> 0x01c5 }
        r3 = r3.zzae(r4, r2);	 Catch:{ all -> 0x01c5 }
        r0 = r23;
        r0.put(r2, r3);	 Catch:{ all -> 0x01c5 }
    L_0x0763:
        r2 = r3.zzjhw;	 Catch:{ all -> 0x01c5 }
        if (r2 != 0) goto L_0x09a4;
    L_0x0767:
        r2 = r3.zzjhx;	 Catch:{ all -> 0x01c5 }
        r4 = r2.longValue();	 Catch:{ all -> 0x01c5 }
        r6 = 1;
        r2 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r2 <= 0) goto L_0x0787;
    L_0x0773:
        r30.zzayl();	 Catch:{ all -> 0x01c5 }
        r0 = r28;
        r2 = r0.zzjui;	 Catch:{ all -> 0x01c5 }
        r4 = "_sr";
        r5 = r3.zzjhx;	 Catch:{ all -> 0x01c5 }
        r2 = com.google.android.gms.internal.zzcno.zza(r2, r4, r5);	 Catch:{ all -> 0x01c5 }
        r0 = r28;
        r0.zzjui = r2;	 Catch:{ all -> 0x01c5 }
    L_0x0787:
        r2 = r3.zzjhy;	 Catch:{ all -> 0x01c5 }
        if (r2 == 0) goto L_0x07ab;
    L_0x078b:
        r2 = r3.zzjhy;	 Catch:{ all -> 0x01c5 }
        r2 = r2.booleanValue();	 Catch:{ all -> 0x01c5 }
        if (r2 == 0) goto L_0x07ab;
    L_0x0793:
        r30.zzayl();	 Catch:{ all -> 0x01c5 }
        r0 = r28;
        r2 = r0.zzjui;	 Catch:{ all -> 0x01c5 }
        r3 = "_efs";
        r4 = 1;
        r4 = java.lang.Long.valueOf(r4);	 Catch:{ all -> 0x01c5 }
        r2 = com.google.android.gms.internal.zzcno.zza(r2, r3, r4);	 Catch:{ all -> 0x01c5 }
        r0 = r28;
        r0.zzjui = r2;	 Catch:{ all -> 0x01c5 }
    L_0x07ab:
        r2 = r18 + 1;
        r24[r18] = r28;	 Catch:{ all -> 0x01c5 }
    L_0x07af:
        r3 = r20 + 1;
        r20 = r3;
        r18 = r2;
        goto L_0x0723;
    L_0x07b7:
        r2 = new com.google.android.gms.internal.zzcnn;	 Catch:{ all -> 0x01c5 }
        r0 = r22;
        r3 = r0.zzcm;	 Catch:{ all -> 0x01c5 }
        r4 = "auto";
        r5 = "_lte";
        r0 = r30;
        r6 = r0.zzdir;	 Catch:{ all -> 0x01c5 }
        r6 = r6.currentTimeMillis();	 Catch:{ all -> 0x01c5 }
        r8 = r8.value;	 Catch:{ all -> 0x01c5 }
        r8 = (java.lang.Long) r8;	 Catch:{ all -> 0x01c5 }
        r8 = r8.longValue();	 Catch:{ all -> 0x01c5 }
        r8 = r8 + r14;
        r8 = java.lang.Long.valueOf(r8);	 Catch:{ all -> 0x01c5 }
        r2.<init>(r3, r4, r5, r6, r8);	 Catch:{ all -> 0x01c5 }
        r4 = r2;
        goto L_0x0639;
    L_0x07de:
        r3 = r3 + 1;
        goto L_0x0659;
    L_0x07e2:
        r2 = 1;
        r3 = "_dbg";
        r4 = 1;
        r4 = java.lang.Long.valueOf(r4);	 Catch:{ all -> 0x01c5 }
        r0 = r28;
        r3 = zza(r0, r3, r4);	 Catch:{ all -> 0x01c5 }
        if (r3 != 0) goto L_0x0b94;
    L_0x07f4:
        r2 = r30.zzaym();	 Catch:{ all -> 0x01c5 }
        r0 = r21;
        r3 = r0.zzjpe;	 Catch:{ all -> 0x01c5 }
        r3 = r3.zzcm;	 Catch:{ all -> 0x01c5 }
        r0 = r28;
        r4 = r0.name;	 Catch:{ all -> 0x01c5 }
        r2 = r2.zzap(r3, r4);	 Catch:{ all -> 0x01c5 }
        r19 = r2;
    L_0x0808:
        if (r19 > 0) goto L_0x0825;
    L_0x080a:
        r2 = r30.zzayp();	 Catch:{ all -> 0x01c5 }
        r2 = r2.zzbaw();	 Catch:{ all -> 0x01c5 }
        r3 = "Sample rate must be positive. event, rate";
        r0 = r28;
        r4 = r0.name;	 Catch:{ all -> 0x01c5 }
        r5 = java.lang.Integer.valueOf(r19);	 Catch:{ all -> 0x01c5 }
        r2.zze(r3, r4, r5);	 Catch:{ all -> 0x01c5 }
        r2 = r18 + 1;
        r24[r18] = r28;	 Catch:{ all -> 0x01c5 }
        goto L_0x07af;
    L_0x0825:
        r0 = r28;
        r2 = r0.name;	 Catch:{ all -> 0x01c5 }
        r0 = r23;
        r2 = r0.get(r2);	 Catch:{ all -> 0x01c5 }
        r2 = (com.google.android.gms.internal.zzcit) r2;	 Catch:{ all -> 0x01c5 }
        if (r2 != 0) goto L_0x0b91;
    L_0x0833:
        r2 = r30.zzayj();	 Catch:{ all -> 0x01c5 }
        r0 = r21;
        r3 = r0.zzjpe;	 Catch:{ all -> 0x01c5 }
        r3 = r3.zzcm;	 Catch:{ all -> 0x01c5 }
        r0 = r28;
        r4 = r0.name;	 Catch:{ all -> 0x01c5 }
        r3 = r2.zzae(r3, r4);	 Catch:{ all -> 0x01c5 }
        if (r3 != 0) goto L_0x0880;
    L_0x0847:
        r2 = r30.zzayp();	 Catch:{ all -> 0x01c5 }
        r2 = r2.zzbaw();	 Catch:{ all -> 0x01c5 }
        r3 = "Event being bundled has no eventAggregate. appId, eventName";
        r0 = r21;
        r4 = r0.zzjpe;	 Catch:{ all -> 0x01c5 }
        r4 = r4.zzcm;	 Catch:{ all -> 0x01c5 }
        r0 = r28;
        r5 = r0.name;	 Catch:{ all -> 0x01c5 }
        r2.zze(r3, r4, r5);	 Catch:{ all -> 0x01c5 }
        r3 = new com.google.android.gms.internal.zzcit;	 Catch:{ all -> 0x01c5 }
        r0 = r21;
        r2 = r0.zzjpe;	 Catch:{ all -> 0x01c5 }
        r4 = r2.zzcm;	 Catch:{ all -> 0x01c5 }
        r0 = r28;
        r5 = r0.name;	 Catch:{ all -> 0x01c5 }
        r6 = 1;
        r8 = 1;
        r0 = r28;
        r2 = r0.zzjuj;	 Catch:{ all -> 0x01c5 }
        r10 = r2.longValue();	 Catch:{ all -> 0x01c5 }
        r12 = 0;
        r14 = 0;
        r15 = 0;
        r16 = 0;
        r3.<init>(r4, r5, r6, r8, r10, r12, r14, r15, r16);	 Catch:{ all -> 0x01c5 }
    L_0x0880:
        r30.zzayl();	 Catch:{ all -> 0x01c5 }
        r2 = "_eid";
        r0 = r28;
        r2 = com.google.android.gms.internal.zzcno.zzb(r0, r2);	 Catch:{ all -> 0x01c5 }
        r2 = (java.lang.Long) r2;	 Catch:{ all -> 0x01c5 }
        if (r2 == 0) goto L_0x08c2;
    L_0x0890:
        r4 = 1;
    L_0x0891:
        r4 = java.lang.Boolean.valueOf(r4);	 Catch:{ all -> 0x01c5 }
        r5 = 1;
        r0 = r19;
        if (r0 != r5) goto L_0x08c4;
    L_0x089a:
        r2 = r18 + 1;
        r24[r18] = r28;	 Catch:{ all -> 0x01c5 }
        r4 = r4.booleanValue();	 Catch:{ all -> 0x01c5 }
        if (r4 == 0) goto L_0x07af;
    L_0x08a4:
        r4 = r3.zzjhw;	 Catch:{ all -> 0x01c5 }
        if (r4 != 0) goto L_0x08b0;
    L_0x08a8:
        r4 = r3.zzjhx;	 Catch:{ all -> 0x01c5 }
        if (r4 != 0) goto L_0x08b0;
    L_0x08ac:
        r4 = r3.zzjhy;	 Catch:{ all -> 0x01c5 }
        if (r4 == 0) goto L_0x07af;
    L_0x08b0:
        r4 = 0;
        r5 = 0;
        r6 = 0;
        r3 = r3.zza(r4, r5, r6);	 Catch:{ all -> 0x01c5 }
        r0 = r28;
        r4 = r0.name;	 Catch:{ all -> 0x01c5 }
        r0 = r23;
        r0.put(r4, r3);	 Catch:{ all -> 0x01c5 }
        goto L_0x07af;
    L_0x08c2:
        r4 = 0;
        goto L_0x0891;
    L_0x08c4:
        r0 = r25;
        r1 = r19;
        r5 = r0.nextInt(r1);	 Catch:{ all -> 0x01c5 }
        if (r5 != 0) goto L_0x0915;
    L_0x08ce:
        r30.zzayl();	 Catch:{ all -> 0x01c5 }
        r0 = r28;
        r2 = r0.zzjui;	 Catch:{ all -> 0x01c5 }
        r5 = "_sr";
        r0 = r19;
        r6 = (long) r0;	 Catch:{ all -> 0x01c5 }
        r6 = java.lang.Long.valueOf(r6);	 Catch:{ all -> 0x01c5 }
        r2 = com.google.android.gms.internal.zzcno.zza(r2, r5, r6);	 Catch:{ all -> 0x01c5 }
        r0 = r28;
        r0.zzjui = r2;	 Catch:{ all -> 0x01c5 }
        r2 = r18 + 1;
        r24[r18] = r28;	 Catch:{ all -> 0x01c5 }
        r4 = r4.booleanValue();	 Catch:{ all -> 0x01c5 }
        if (r4 == 0) goto L_0x08fe;
    L_0x08f1:
        r4 = 0;
        r0 = r19;
        r6 = (long) r0;	 Catch:{ all -> 0x01c5 }
        r5 = java.lang.Long.valueOf(r6);	 Catch:{ all -> 0x01c5 }
        r6 = 0;
        r3 = r3.zza(r4, r5, r6);	 Catch:{ all -> 0x01c5 }
    L_0x08fe:
        r0 = r28;
        r4 = r0.name;	 Catch:{ all -> 0x01c5 }
        r0 = r28;
        r5 = r0.zzjuj;	 Catch:{ all -> 0x01c5 }
        r6 = r5.longValue();	 Catch:{ all -> 0x01c5 }
        r3 = r3.zzbc(r6);	 Catch:{ all -> 0x01c5 }
        r0 = r23;
        r0.put(r4, r3);	 Catch:{ all -> 0x01c5 }
        goto L_0x07af;
    L_0x0915:
        r6 = r3.zzjhv;	 Catch:{ all -> 0x01c5 }
        r0 = r28;
        r5 = r0.zzjuj;	 Catch:{ all -> 0x01c5 }
        r8 = r5.longValue();	 Catch:{ all -> 0x01c5 }
        r6 = r8 - r6;
        r6 = java.lang.Math.abs(r6);	 Catch:{ all -> 0x01c5 }
        r8 = 86400000; // 0x5265c00 float:7.82218E-36 double:4.2687272E-316;
        r5 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1));
        if (r5 < 0) goto L_0x098f;
    L_0x092c:
        r30.zzayl();	 Catch:{ all -> 0x01c5 }
        r0 = r28;
        r2 = r0.zzjui;	 Catch:{ all -> 0x01c5 }
        r5 = "_efs";
        r6 = 1;
        r6 = java.lang.Long.valueOf(r6);	 Catch:{ all -> 0x01c5 }
        r2 = com.google.android.gms.internal.zzcno.zza(r2, r5, r6);	 Catch:{ all -> 0x01c5 }
        r0 = r28;
        r0.zzjui = r2;	 Catch:{ all -> 0x01c5 }
        r30.zzayl();	 Catch:{ all -> 0x01c5 }
        r0 = r28;
        r2 = r0.zzjui;	 Catch:{ all -> 0x01c5 }
        r5 = "_sr";
        r0 = r19;
        r6 = (long) r0;	 Catch:{ all -> 0x01c5 }
        r6 = java.lang.Long.valueOf(r6);	 Catch:{ all -> 0x01c5 }
        r2 = com.google.android.gms.internal.zzcno.zza(r2, r5, r6);	 Catch:{ all -> 0x01c5 }
        r0 = r28;
        r0.zzjui = r2;	 Catch:{ all -> 0x01c5 }
        r2 = r18 + 1;
        r24[r18] = r28;	 Catch:{ all -> 0x01c5 }
        r4 = r4.booleanValue();	 Catch:{ all -> 0x01c5 }
        if (r4 == 0) goto L_0x0978;
    L_0x0967:
        r4 = 0;
        r0 = r19;
        r6 = (long) r0;	 Catch:{ all -> 0x01c5 }
        r5 = java.lang.Long.valueOf(r6);	 Catch:{ all -> 0x01c5 }
        r6 = 1;
        r6 = java.lang.Boolean.valueOf(r6);	 Catch:{ all -> 0x01c5 }
        r3 = r3.zza(r4, r5, r6);	 Catch:{ all -> 0x01c5 }
    L_0x0978:
        r0 = r28;
        r4 = r0.name;	 Catch:{ all -> 0x01c5 }
        r0 = r28;
        r5 = r0.zzjuj;	 Catch:{ all -> 0x01c5 }
        r6 = r5.longValue();	 Catch:{ all -> 0x01c5 }
        r3 = r3.zzbc(r6);	 Catch:{ all -> 0x01c5 }
        r0 = r23;
        r0.put(r4, r3);	 Catch:{ all -> 0x01c5 }
        goto L_0x07af;
    L_0x098f:
        r4 = r4.booleanValue();	 Catch:{ all -> 0x01c5 }
        if (r4 == 0) goto L_0x09a4;
    L_0x0995:
        r0 = r28;
        r4 = r0.name;	 Catch:{ all -> 0x01c5 }
        r5 = 0;
        r6 = 0;
        r2 = r3.zza(r2, r5, r6);	 Catch:{ all -> 0x01c5 }
        r0 = r23;
        r0.put(r4, r2);	 Catch:{ all -> 0x01c5 }
    L_0x09a4:
        r2 = r18;
        goto L_0x07af;
    L_0x09a8:
        r0 = r22;
        r2 = r0.zzjuq;	 Catch:{ all -> 0x01c5 }
        r2 = r2.length;	 Catch:{ all -> 0x01c5 }
        r0 = r18;
        if (r0 >= r2) goto L_0x09bf;
    L_0x09b1:
        r0 = r24;
        r1 = r18;
        r2 = java.util.Arrays.copyOf(r0, r1);	 Catch:{ all -> 0x01c5 }
        r2 = (com.google.android.gms.internal.zzcob[]) r2;	 Catch:{ all -> 0x01c5 }
        r0 = r22;
        r0.zzjuq = r2;	 Catch:{ all -> 0x01c5 }
    L_0x09bf:
        r2 = r23.entrySet();	 Catch:{ all -> 0x01c5 }
        r3 = r2.iterator();	 Catch:{ all -> 0x01c5 }
    L_0x09c7:
        r2 = r3.hasNext();	 Catch:{ all -> 0x01c5 }
        if (r2 == 0) goto L_0x09e1;
    L_0x09cd:
        r2 = r3.next();	 Catch:{ all -> 0x01c5 }
        r2 = (java.util.Map.Entry) r2;	 Catch:{ all -> 0x01c5 }
        r4 = r30.zzayj();	 Catch:{ all -> 0x01c5 }
        r2 = r2.getValue();	 Catch:{ all -> 0x01c5 }
        r2 = (com.google.android.gms.internal.zzcit) r2;	 Catch:{ all -> 0x01c5 }
        r4.zza(r2);	 Catch:{ all -> 0x01c5 }
        goto L_0x09c7;
    L_0x09e1:
        r2 = 9223372036854775807; // 0x7fffffffffffffff float:NaN double:NaN;
        r2 = java.lang.Long.valueOf(r2);	 Catch:{ all -> 0x01c5 }
        r0 = r22;
        r0.zzjut = r2;	 Catch:{ all -> 0x01c5 }
        r2 = -9223372036854775808;
        r2 = java.lang.Long.valueOf(r2);	 Catch:{ all -> 0x01c5 }
        r0 = r22;
        r0.zzjuu = r2;	 Catch:{ all -> 0x01c5 }
        r2 = 0;
    L_0x09f9:
        r0 = r22;
        r3 = r0.zzjuq;	 Catch:{ all -> 0x01c5 }
        r3 = r3.length;	 Catch:{ all -> 0x01c5 }
        if (r2 >= r3) goto L_0x0a39;
    L_0x0a00:
        r0 = r22;
        r3 = r0.zzjuq;	 Catch:{ all -> 0x01c5 }
        r3 = r3[r2];	 Catch:{ all -> 0x01c5 }
        r4 = r3.zzjuj;	 Catch:{ all -> 0x01c5 }
        r4 = r4.longValue();	 Catch:{ all -> 0x01c5 }
        r0 = r22;
        r6 = r0.zzjut;	 Catch:{ all -> 0x01c5 }
        r6 = r6.longValue();	 Catch:{ all -> 0x01c5 }
        r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r4 >= 0) goto L_0x0a1e;
    L_0x0a18:
        r4 = r3.zzjuj;	 Catch:{ all -> 0x01c5 }
        r0 = r22;
        r0.zzjut = r4;	 Catch:{ all -> 0x01c5 }
    L_0x0a1e:
        r4 = r3.zzjuj;	 Catch:{ all -> 0x01c5 }
        r4 = r4.longValue();	 Catch:{ all -> 0x01c5 }
        r0 = r22;
        r6 = r0.zzjuu;	 Catch:{ all -> 0x01c5 }
        r6 = r6.longValue();	 Catch:{ all -> 0x01c5 }
        r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r4 <= 0) goto L_0x0a36;
    L_0x0a30:
        r3 = r3.zzjuj;	 Catch:{ all -> 0x01c5 }
        r0 = r22;
        r0.zzjuu = r3;	 Catch:{ all -> 0x01c5 }
    L_0x0a36:
        r2 = r2 + 1;
        goto L_0x09f9;
    L_0x0a39:
        r0 = r21;
        r2 = r0.zzjpe;	 Catch:{ all -> 0x01c5 }
        r6 = r2.zzcm;	 Catch:{ all -> 0x01c5 }
        r2 = r30.zzayj();	 Catch:{ all -> 0x01c5 }
        r7 = r2.zzjj(r6);	 Catch:{ all -> 0x01c5 }
        if (r7 != 0) goto L_0x0acf;
    L_0x0a49:
        r2 = r30.zzayp();	 Catch:{ all -> 0x01c5 }
        r2 = r2.zzbau();	 Catch:{ all -> 0x01c5 }
        r3 = "Bundling raw events w/o app info. appId";
        r0 = r21;
        r4 = r0.zzjpe;	 Catch:{ all -> 0x01c5 }
        r4 = r4.zzcm;	 Catch:{ all -> 0x01c5 }
        r4 = com.google.android.gms.internal.zzcjj.zzjs(r4);	 Catch:{ all -> 0x01c5 }
        r2.zzj(r3, r4);	 Catch:{ all -> 0x01c5 }
    L_0x0a61:
        r0 = r22;
        r2 = r0.zzjuq;	 Catch:{ all -> 0x01c5 }
        r2 = r2.length;	 Catch:{ all -> 0x01c5 }
        if (r2 <= 0) goto L_0x0a9d;
    L_0x0a68:
        r2 = r30.zzaym();	 Catch:{ all -> 0x01c5 }
        r0 = r21;
        r3 = r0.zzjpe;	 Catch:{ all -> 0x01c5 }
        r3 = r3.zzcm;	 Catch:{ all -> 0x01c5 }
        r2 = r2.zzka(r3);	 Catch:{ all -> 0x01c5 }
        if (r2 == 0) goto L_0x0a7c;
    L_0x0a78:
        r3 = r2.zzjtx;	 Catch:{ all -> 0x01c5 }
        if (r3 != 0) goto L_0x0b55;
    L_0x0a7c:
        r0 = r21;
        r2 = r0.zzjpe;	 Catch:{ all -> 0x01c5 }
        r2 = r2.zzjfl;	 Catch:{ all -> 0x01c5 }
        r2 = android.text.TextUtils.isEmpty(r2);	 Catch:{ all -> 0x01c5 }
        if (r2 == 0) goto L_0x0b3b;
    L_0x0a88:
        r2 = -1;
        r2 = java.lang.Long.valueOf(r2);	 Catch:{ all -> 0x01c5 }
        r0 = r22;
        r0.zzjvm = r2;	 Catch:{ all -> 0x01c5 }
    L_0x0a92:
        r2 = r30.zzayj();	 Catch:{ all -> 0x01c5 }
        r0 = r22;
        r1 = r17;
        r2.zza(r0, r1);	 Catch:{ all -> 0x01c5 }
    L_0x0a9d:
        r2 = r30.zzayj();	 Catch:{ all -> 0x01c5 }
        r0 = r21;
        r3 = r0.zzjpf;	 Catch:{ all -> 0x01c5 }
        r2.zzai(r3);	 Catch:{ all -> 0x01c5 }
        r3 = r30.zzayj();	 Catch:{ all -> 0x01c5 }
        r2 = r3.getWritableDatabase();	 Catch:{ all -> 0x01c5 }
        r4 = "delete from raw_events_metadata where app_id=? and metadata_fingerprint not in (select distinct metadata_fingerprint from raw_events where app_id=?)";
        r5 = 2;
        r5 = new java.lang.String[r5];	 Catch:{ SQLiteException -> 0x0b5d }
        r7 = 0;
        r5[r7] = r6;	 Catch:{ SQLiteException -> 0x0b5d }
        r7 = 1;
        r5[r7] = r6;	 Catch:{ SQLiteException -> 0x0b5d }
        r2.execSQL(r4, r5);	 Catch:{ SQLiteException -> 0x0b5d }
    L_0x0abf:
        r2 = r30.zzayj();	 Catch:{ all -> 0x01c5 }
        r2.setTransactionSuccessful();	 Catch:{ all -> 0x01c5 }
        r2 = r30.zzayj();
        r2.endTransaction();
        r2 = 1;
    L_0x0ace:
        return r2;
    L_0x0acf:
        r0 = r22;
        r2 = r0.zzjuq;	 Catch:{ all -> 0x01c5 }
        r2 = r2.length;	 Catch:{ all -> 0x01c5 }
        if (r2 <= 0) goto L_0x0a61;
    L_0x0ad6:
        r2 = r7.zzayw();	 Catch:{ all -> 0x01c5 }
        r4 = 0;
        r4 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r4 == 0) goto L_0x0b37;
    L_0x0ae0:
        r4 = java.lang.Long.valueOf(r2);	 Catch:{ all -> 0x01c5 }
    L_0x0ae4:
        r0 = r22;
        r0.zzjuw = r4;	 Catch:{ all -> 0x01c5 }
        r4 = r7.zzayv();	 Catch:{ all -> 0x01c5 }
        r8 = 0;
        r8 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1));
        if (r8 != 0) goto L_0x0b8e;
    L_0x0af2:
        r4 = 0;
        r4 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r4 == 0) goto L_0x0b39;
    L_0x0af8:
        r2 = java.lang.Long.valueOf(r2);	 Catch:{ all -> 0x01c5 }
    L_0x0afc:
        r0 = r22;
        r0.zzjuv = r2;	 Catch:{ all -> 0x01c5 }
        r7.zzazf();	 Catch:{ all -> 0x01c5 }
        r2 = r7.zzazc();	 Catch:{ all -> 0x01c5 }
        r2 = (int) r2;	 Catch:{ all -> 0x01c5 }
        r2 = java.lang.Integer.valueOf(r2);	 Catch:{ all -> 0x01c5 }
        r0 = r22;
        r0.zzjvf = r2;	 Catch:{ all -> 0x01c5 }
        r0 = r22;
        r2 = r0.zzjut;	 Catch:{ all -> 0x01c5 }
        r2 = r2.longValue();	 Catch:{ all -> 0x01c5 }
        r7.zzal(r2);	 Catch:{ all -> 0x01c5 }
        r0 = r22;
        r2 = r0.zzjuu;	 Catch:{ all -> 0x01c5 }
        r2 = r2.longValue();	 Catch:{ all -> 0x01c5 }
        r7.zzam(r2);	 Catch:{ all -> 0x01c5 }
        r2 = r7.zzazn();	 Catch:{ all -> 0x01c5 }
        r0 = r22;
        r0.zzjgi = r2;	 Catch:{ all -> 0x01c5 }
        r2 = r30.zzayj();	 Catch:{ all -> 0x01c5 }
        r2.zza(r7);	 Catch:{ all -> 0x01c5 }
        goto L_0x0a61;
    L_0x0b37:
        r4 = 0;
        goto L_0x0ae4;
    L_0x0b39:
        r2 = 0;
        goto L_0x0afc;
    L_0x0b3b:
        r2 = r30.zzayp();	 Catch:{ all -> 0x01c5 }
        r2 = r2.zzbaw();	 Catch:{ all -> 0x01c5 }
        r3 = "Did not find measurement config or missing version info. appId";
        r0 = r21;
        r4 = r0.zzjpe;	 Catch:{ all -> 0x01c5 }
        r4 = r4.zzcm;	 Catch:{ all -> 0x01c5 }
        r4 = com.google.android.gms.internal.zzcjj.zzjs(r4);	 Catch:{ all -> 0x01c5 }
        r2.zzj(r3, r4);	 Catch:{ all -> 0x01c5 }
        goto L_0x0a92;
    L_0x0b55:
        r2 = r2.zzjtx;	 Catch:{ all -> 0x01c5 }
        r0 = r22;
        r0.zzjvm = r2;	 Catch:{ all -> 0x01c5 }
        goto L_0x0a92;
    L_0x0b5d:
        r2 = move-exception;
        r3 = r3.zzayp();	 Catch:{ all -> 0x01c5 }
        r3 = r3.zzbau();	 Catch:{ all -> 0x01c5 }
        r4 = "Failed to remove unused event metadata. appId";
        r5 = com.google.android.gms.internal.zzcjj.zzjs(r6);	 Catch:{ all -> 0x01c5 }
        r3.zze(r4, r5, r2);	 Catch:{ all -> 0x01c5 }
        goto L_0x0abf;
    L_0x0b72:
        r2 = r30.zzayj();	 Catch:{ all -> 0x01c5 }
        r2.setTransactionSuccessful();	 Catch:{ all -> 0x01c5 }
        r2 = r30.zzayj();
        r2.endTransaction();
        r2 = 0;
        goto L_0x0ace;
    L_0x0b83:
        r2 = move-exception;
        r3 = r11;
        goto L_0x036c;
    L_0x0b87:
        r2 = move-exception;
        goto L_0x02f9;
    L_0x0b8a:
        r2 = move-exception;
        r4 = r12;
        goto L_0x02f9;
    L_0x0b8e:
        r2 = r4;
        goto L_0x0af2;
    L_0x0b91:
        r3 = r2;
        goto L_0x0880;
    L_0x0b94:
        r19 = r2;
        goto L_0x0808;
    L_0x0b98:
        r2 = r14;
        goto L_0x0588;
    L_0x0b9b:
        r2 = r3;
        r3 = r4;
        goto L_0x0527;
    L_0x0b9f:
        r17 = r2;
        goto L_0x04c0;
    L_0x0ba3:
        r2 = r3;
        r3 = r4;
        goto L_0x03c2;
    L_0x0ba7:
        r2 = r14;
        r4 = r13;
        r5 = r17;
        goto L_0x0156;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzckj.zzg(java.lang.String, long):boolean");
    }

    @WorkerThread
    private final zzcif zzke(String str) {
        zzcie zzjj = zzayj().zzjj(str);
        if (zzjj == null || TextUtils.isEmpty(zzjj.zzwo())) {
            zzayp().zzbaz().zzj("No app data available; dropping", str);
            return null;
        }
        Boolean zzc = zzc(zzjj);
        if (zzc == null || zzc.booleanValue()) {
            return new zzcif(str, zzjj.getGmpAppId(), zzjj.zzwo(), zzjj.zzayx(), zzjj.zzayy(), zzjj.zzayz(), zzjj.zzaza(), null, zzjj.zzazb(), false, zzjj.zzayu(), zzjj.zzazo(), 0, 0, zzjj.zzazp());
        }
        zzayp().zzbau().zzj("App version does not match; dropping. appId", zzcjj.zzjs(str));
        return null;
    }

    public final Context getContext() {
        return this.zzaiq;
    }

    @WorkerThread
    public final boolean isEnabled() {
        boolean z = false;
        zzayo().zzwj();
        zzyk();
        if (this.zzjns.zzazr()) {
            return false;
        }
        Boolean zzjf = this.zzjns.zzjf("firebase_analytics_collection_enabled");
        if (zzjf != null) {
            z = zzjf.booleanValue();
        } else if (!zzbz.zzakr()) {
            z = true;
        }
        return zzayq().zzbs(z);
    }

    @WorkerThread
    protected final void start() {
        zzayo().zzwj();
        zzayj().zzazy();
        if (zzayq().zzjln.get() == 0) {
            zzayq().zzjln.set(this.zzdir.currentTimeMillis());
        }
        if (Long.valueOf(zzayq().zzjls.get()).longValue() == 0) {
            zzayp().zzbba().zzj("Persisting first open", Long.valueOf(this.zzjgk));
            zzayq().zzjls.set(this.zzjgk);
        }
        if (zzbbn()) {
            if (!TextUtils.isEmpty(zzaye().getGmpAppId())) {
                String zzbbe = zzayq().zzbbe();
                if (zzbbe == null) {
                    zzayq().zzjw(zzaye().getGmpAppId());
                } else if (!zzbbe.equals(zzaye().getGmpAppId())) {
                    zzayp().zzbay().log("Rechecking which service to use due to a GMP App Id change");
                    zzayq().zzbbh();
                    this.zzjoi.disconnect();
                    this.zzjoi.zzzh();
                    zzayq().zzjw(zzaye().getGmpAppId());
                    zzayq().zzjls.set(this.zzjgk);
                    zzayq().zzjlt.zzjy(null);
                }
            }
            zzayd().zzjx(zzayq().zzjlt.zzbbj());
            if (!TextUtils.isEmpty(zzaye().getGmpAppId())) {
                zzclh zzayd = zzayd();
                zzayd.zzwj();
                zzayd.zzyk();
                if (zzayd.zzjev.zzbbn()) {
                    zzayd.zzayg().zzbcj();
                    String zzbbi = zzayd.zzayq().zzbbi();
                    if (!TextUtils.isEmpty(zzbbi)) {
                        zzayd.zzayf().zzyk();
                        if (!zzbbi.equals(VERSION.RELEASE)) {
                            Bundle bundle = new Bundle();
                            bundle.putString("_po", zzbbi);
                            zzayd.zzd("auto", "_ou", bundle);
                        }
                    }
                }
                zzayg().zza(new AtomicReference());
            }
        } else if (isEnabled()) {
            if (!zzayl().zzeh("android.permission.INTERNET")) {
                zzayp().zzbau().log("App is missing INTERNET permission");
            }
            if (!zzayl().zzeh("android.permission.ACCESS_NETWORK_STATE")) {
                zzayp().zzbau().log("App is missing ACCESS_NETWORK_STATE permission");
            }
            if (!zzbih.zzdd(this.zzaiq).zzaoe()) {
                if (!zzcka.zzbj(this.zzaiq)) {
                    zzayp().zzbau().log("AppMeasurementReceiver not registered/enabled");
                }
                if (!zzcmy.zzg(this.zzaiq, false)) {
                    zzayp().zzbau().log("AppMeasurementService not registered/enabled");
                }
            }
            zzayp().zzbau().log("Uploading is not possible. App measurement disabled");
        }
        zzbca();
    }

    @WorkerThread
    protected final void zza(int i, Throwable th, byte[] bArr) {
        zzayo().zzwj();
        zzyk();
        if (bArr == null) {
            try {
                bArr = new byte[0];
            } catch (Throwable th2) {
                this.zzjpa = false;
                zzbce();
            }
        }
        List<Long> list = this.zzjot;
        this.zzjot = null;
        if ((i == 200 || i == 204) && th == null) {
            try {
                zzayq().zzjln.set(this.zzdir.currentTimeMillis());
                zzayq().zzjlo.set(0);
                zzbca();
                zzayp().zzbba().zze("Successful upload. Got network response. code, size", Integer.valueOf(i), Integer.valueOf(bArr.length));
                zzayj().beginTransaction();
                zzclh zzayj;
                try {
                    for (Long l : list) {
                        zzayj = zzayj();
                        long longValue = l.longValue();
                        zzayj.zzwj();
                        zzayj.zzyk();
                        if (zzayj.getWritableDatabase().delete("queue", "rowid=?", new String[]{String.valueOf(longValue)}) != 1) {
                            throw new SQLiteException("Deleted fewer rows from queue than expected");
                        }
                    }
                    zzayj().setTransactionSuccessful();
                    zzayj().endTransaction();
                    if (zzbbs().zzaax() && zzbbz()) {
                        zzbby();
                    } else {
                        this.zzjox = -1;
                        zzbca();
                    }
                    this.zzjoy = 0;
                } catch (SQLiteException e) {
                    zzayj.zzayp().zzbau().zzj("Failed to delete a bundle in a queue table", e);
                    throw e;
                } catch (Throwable th3) {
                    zzayj().endTransaction();
                }
            } catch (SQLiteException e2) {
                zzayp().zzbau().zzj("Database error while trying to delete uploaded bundles", e2);
                this.zzjoy = this.zzdir.elapsedRealtime();
                zzayp().zzbba().zzj("Disable upload, time", Long.valueOf(this.zzjoy));
            }
        } else {
            zzayp().zzbba().zze("Network upload failed. Will retry later. code, error", Integer.valueOf(i), th);
            zzayq().zzjlo.set(this.zzdir.currentTimeMillis());
            boolean z = i == 503 || i == 429;
            if (z) {
                zzayq().zzjlp.set(this.zzdir.currentTimeMillis());
            }
            zzbca();
        }
        this.zzjpa = false;
        zzbce();
    }

    @WorkerThread
    public final byte[] zza(@NonNull zzcix zzcix, @Size(min = 1) String str) {
        zzyk();
        zzayo().zzwj();
        zzaxz();
        zzbq.checkNotNull(zzcix);
        zzbq.zzgv(str);
        zzfls zzcod = new zzcod();
        zzayj().beginTransaction();
        try {
            zzcie zzjj = zzayj().zzjj(str);
            byte[] bArr;
            if (zzjj == null) {
                zzayp().zzbaz().zzj("Log and bundle not available. package_name", str);
                bArr = new byte[0];
                return bArr;
            } else if (zzjj.zzazb()) {
                zzcog zzcog;
                long j;
                if (("_iap".equals(zzcix.name) || Event.ECOMMERCE_PURCHASE.equals(zzcix.name)) && !zza(str, zzcix)) {
                    zzayp().zzbaw().zzj("Failed to handle purchase event at single event bundle creation. appId", zzcjj.zzjs(str));
                }
                boolean zzjh = this.zzjns.zzjh(str);
                Long valueOf = Long.valueOf(0);
                if (zzjh && "_e".equals(zzcix.name)) {
                    if (zzcix.zzjhr == null || zzcix.zzjhr.size() == 0) {
                        zzayp().zzbaw().zzj("The engagement event does not contain any parameters. appId", zzcjj.zzjs(str));
                    } else if (zzcix.zzjhr.getLong("_et") == null) {
                        zzayp().zzbaw().zzj("The engagement event does not include duration. appId", zzcjj.zzjs(str));
                    } else {
                        valueOf = zzcix.zzjhr.getLong("_et");
                    }
                }
                zzcoe zzcoe = new zzcoe();
                zzcod.zzjun = new zzcoe[]{zzcoe};
                zzcoe.zzjup = Integer.valueOf(1);
                zzcoe.zzjux = AbstractSpiCall.ANDROID_CLIENT_TYPE;
                zzcoe.zzcm = zzjj.getAppId();
                zzcoe.zzjfs = zzjj.zzayy();
                zzcoe.zzina = zzjj.zzwo();
                long zzayx = zzjj.zzayx();
                zzcoe.zzjvi = zzayx == -2147483648L ? null : Integer.valueOf((int) zzayx);
                zzcoe.zzjva = Long.valueOf(zzjj.zzayz());
                zzcoe.zzjfl = zzjj.getGmpAppId();
                zzcoe.zzjve = Long.valueOf(zzjj.zzaza());
                if (isEnabled() && zzcik.zzazv() && this.zzjns.zzjg(zzcoe.zzcm)) {
                    zzcoe.zzjvn = null;
                }
                Pair zzju = zzayq().zzju(zzjj.getAppId());
                if (!(!zzjj.zzazp() || zzju == null || TextUtils.isEmpty((CharSequence) zzju.first))) {
                    zzcoe.zzjvc = (String) zzju.first;
                    zzcoe.zzjvd = (Boolean) zzju.second;
                }
                zzayf().zzyk();
                zzcoe.zzjuy = Build.MODEL;
                zzayf().zzyk();
                zzcoe.zzda = VERSION.RELEASE;
                zzcoe.zzjuz = Integer.valueOf((int) zzayf().zzbal());
                zzcoe.zzjho = zzayf().zzbam();
                zzcoe.zzjfk = zzjj.getAppInstanceId();
                zzcoe.zzjfn = zzjj.zzayu();
                List zzji = zzayj().zzji(zzjj.getAppId());
                zzcoe.zzjur = new zzcog[zzji.size()];
                zzcnn zzcnn = null;
                if (zzjh) {
                    zzcnn zzag = zzayj().zzag(zzcoe.zzcm, "_lte");
                    zzcnn = (zzag == null || zzag.value == null) ? new zzcnn(zzcoe.zzcm, "auto", "_lte", this.zzdir.currentTimeMillis(), valueOf) : valueOf.longValue() > 0 ? new zzcnn(zzcoe.zzcm, "auto", "_lte", this.zzdir.currentTimeMillis(), Long.valueOf(((Long) zzag.value).longValue() + valueOf.longValue())) : zzag;
                }
                zzcog zzcog2 = null;
                int i = 0;
                while (i < zzji.size()) {
                    zzcog zzcog3;
                    zzcog = new zzcog();
                    zzcoe.zzjur[i] = zzcog;
                    zzcog.name = ((zzcnn) zzji.get(i)).name;
                    zzcog.zzjvr = Long.valueOf(((zzcnn) zzji.get(i)).zzjsi);
                    zzayl().zza(zzcog, ((zzcnn) zzji.get(i)).value);
                    if (zzjh && "_lte".equals(zzcog.name)) {
                        zzcog.zzjum = (Long) zzcnn.value;
                        zzcog.zzjvr = Long.valueOf(this.zzdir.currentTimeMillis());
                        zzcog3 = zzcog;
                    } else {
                        zzcog3 = zzcog2;
                    }
                    i++;
                    zzcog2 = zzcog3;
                }
                if (zzjh && zzcog2 == null) {
                    zzcog = new zzcog();
                    zzcog.name = "_lte";
                    zzcog.zzjvr = Long.valueOf(this.zzdir.currentTimeMillis());
                    zzcog.zzjum = (Long) zzcnn.value;
                    zzcoe.zzjur = (zzcog[]) Arrays.copyOf(zzcoe.zzjur, zzcoe.zzjur.length + 1);
                    zzcoe.zzjur[zzcoe.zzjur.length - 1] = zzcog;
                }
                if (valueOf.longValue() > 0) {
                    zzayj().zza(zzcnn);
                }
                Bundle zzbao = zzcix.zzjhr.zzbao();
                if ("_iap".equals(zzcix.name)) {
                    zzbao.putLong("_c", 1);
                    zzayp().zzbaz().log("Marking in-app purchase as real-time");
                    zzbao.putLong("_r", 1);
                }
                zzbao.putString("_o", zzcix.zzjgm);
                if (zzayl().zzkq(zzcoe.zzcm)) {
                    zzayl().zza(zzbao, "_dbg", Long.valueOf(1));
                    zzayl().zza(zzbao, "_r", Long.valueOf(1));
                }
                zzcit zzae = zzayj().zzae(str, zzcix.name);
                if (zzae == null) {
                    zzayj().zza(new zzcit(str, zzcix.name, 1, 0, zzcix.zzjib, 0, null, null, null));
                    j = 0;
                } else {
                    j = zzae.zzjhu;
                    zzayj().zza(zzae.zzbb(zzcix.zzjib).zzban());
                }
                zzcis zzcis = new zzcis(this, zzcix.zzjgm, str, zzcix.name, zzcix.zzjib, j, zzbao);
                zzcob zzcob = new zzcob();
                zzcoe.zzjuq = new zzcob[]{zzcob};
                zzcob.zzjuj = Long.valueOf(zzcis.timestamp);
                zzcob.name = zzcis.name;
                zzcob.zzjuk = Long.valueOf(zzcis.zzjhq);
                zzcob.zzjui = new zzcoc[zzcis.zzjhr.size()];
                Iterator it = zzcis.zzjhr.iterator();
                int i2 = 0;
                while (it.hasNext()) {
                    String str2 = (String) it.next();
                    zzcoc zzcoc = new zzcoc();
                    i = i2 + 1;
                    zzcob.zzjui[i2] = zzcoc;
                    zzcoc.name = str2;
                    zzayl().zza(zzcoc, zzcis.zzjhr.get(str2));
                    i2 = i;
                }
                zzcoe.zzjvh = zza(zzjj.getAppId(), zzcoe.zzjur, zzcoe.zzjuq);
                zzcoe.zzjut = zzcob.zzjuj;
                zzcoe.zzjuu = zzcob.zzjuj;
                zzayx = zzjj.zzayw();
                zzcoe.zzjuw = zzayx != 0 ? Long.valueOf(zzayx) : null;
                long zzayv = zzjj.zzayv();
                if (zzayv != 0) {
                    zzayx = zzayv;
                }
                zzcoe.zzjuv = zzayx != 0 ? Long.valueOf(zzayx) : null;
                zzjj.zzazf();
                zzcoe.zzjvf = Integer.valueOf((int) zzjj.zzazc());
                zzcoe.zzjvb = Long.valueOf(12211);
                zzcoe.zzjus = Long.valueOf(this.zzdir.currentTimeMillis());
                zzcoe.zzjvg = Boolean.TRUE;
                zzjj.zzal(zzcoe.zzjut.longValue());
                zzjj.zzam(zzcoe.zzjuu.longValue());
                zzayj().zza(zzjj);
                zzayj().setTransactionSuccessful();
                zzayj().endTransaction();
                try {
                    bArr = new byte[zzcod.zzhs()];
                    zzflk zzp = zzflk.zzp(bArr, 0, bArr.length);
                    zzcod.zza(zzp);
                    zzp.zzcyx();
                    return zzayl().zzr(bArr);
                } catch (IOException e) {
                    zzayp().zzbau().zze("Data loss. Failed to bundle and serialize. appId", zzcjj.zzjs(str), e);
                    return null;
                }
            } else {
                zzayp().zzbaz().zzj("Log and bundle disabled. package_name", str);
                bArr = new byte[0];
                zzayj().endTransaction();
                return bArr;
            }
        } finally {
            zzayj().endTransaction();
        }
    }

    public final zzcia zzayb() {
        zza(this.zzjof);
        return this.zzjof;
    }

    public final zzcih zzayc() {
        zza(this.zzjon);
        return this.zzjon;
    }

    public final zzclk zzayd() {
        zza(this.zzjoe);
        return this.zzjoe;
    }

    public final zzcje zzaye() {
        zza(this.zzjok);
        return this.zzjok;
    }

    public final zzcir zzayf() {
        zza(this.zzjoj);
        return this.zzjoj;
    }

    public final zzcme zzayg() {
        zza(this.zzjoi);
        return this.zzjoi;
    }

    public final zzcma zzayh() {
        zza(this.zzjod);
        return this.zzjod;
    }

    public final zzcjf zzayi() {
        zza(this.zzjoh);
        return this.zzjoh;
    }

    public final zzcil zzayj() {
        zza(this.zzjog);
        return this.zzjog;
    }

    public final zzcjh zzayk() {
        zza(this.zzjob);
        return this.zzjob;
    }

    public final zzcno zzayl() {
        zza(this.zzjoa);
        return this.zzjoa;
    }

    public final zzckd zzaym() {
        zza(this.zzjnx);
        return this.zzjnx;
    }

    public final zzcnd zzayn() {
        zza(this.zzjnw);
        return this.zzjnw;
    }

    public final zzcke zzayo() {
        zza(this.zzjnv);
        return this.zzjnv;
    }

    public final zzcjj zzayp() {
        zza(this.zzjnu);
        return this.zzjnu;
    }

    public final zzcju zzayq() {
        zza(this.zzjnt);
        return this.zzjnt;
    }

    public final zzcik zzayr() {
        return this.zzjns;
    }

    @WorkerThread
    final void zzb(zzcii zzcii, zzcif zzcif) {
        boolean z = true;
        zzbq.checkNotNull(zzcii);
        zzbq.zzgv(zzcii.packageName);
        zzbq.checkNotNull(zzcii.zzjgm);
        zzbq.checkNotNull(zzcii.zzjgn);
        zzbq.zzgv(zzcii.zzjgn.name);
        zzayo().zzwj();
        zzyk();
        if (!TextUtils.isEmpty(zzcif.zzjfl)) {
            if (zzcif.zzjfv) {
                zzcii zzcii2 = new zzcii(zzcii);
                zzcii2.zzjgp = false;
                zzayj().beginTransaction();
                try {
                    zzcii zzah = zzayj().zzah(zzcii2.packageName, zzcii2.zzjgn.name);
                    if (!(zzah == null || zzah.zzjgm.equals(zzcii2.zzjgm))) {
                        zzayp().zzbaw().zzd("Updating a conditional user property with different origin. name, origin, origin (from DB)", zzayk().zzjr(zzcii2.zzjgn.name), zzcii2.zzjgm, zzah.zzjgm);
                    }
                    if (zzah != null && zzah.zzjgp) {
                        zzcii2.zzjgm = zzah.zzjgm;
                        zzcii2.zzjgo = zzah.zzjgo;
                        zzcii2.zzjgs = zzah.zzjgs;
                        zzcii2.zzjgq = zzah.zzjgq;
                        zzcii2.zzjgt = zzah.zzjgt;
                        zzcii2.zzjgp = zzah.zzjgp;
                        zzcii2.zzjgn = new zzcnl(zzcii2.zzjgn.name, zzah.zzjgn.zzjsi, zzcii2.zzjgn.getValue(), zzah.zzjgn.zzjgm);
                        z = false;
                    } else if (TextUtils.isEmpty(zzcii2.zzjgq)) {
                        zzcii2.zzjgn = new zzcnl(zzcii2.zzjgn.name, zzcii2.zzjgo, zzcii2.zzjgn.getValue(), zzcii2.zzjgn.zzjgm);
                        zzcii2.zzjgp = true;
                    } else {
                        z = false;
                    }
                    if (zzcii2.zzjgp) {
                        zzcnl zzcnl = zzcii2.zzjgn;
                        zzcnn zzcnn = new zzcnn(zzcii2.packageName, zzcii2.zzjgm, zzcnl.name, zzcnl.zzjsi, zzcnl.getValue());
                        if (zzayj().zza(zzcnn)) {
                            zzayp().zzbaz().zzd("User property updated immediately", zzcii2.packageName, zzayk().zzjr(zzcnn.name), zzcnn.value);
                        } else {
                            zzayp().zzbau().zzd("(2)Too many active user properties, ignoring", zzcjj.zzjs(zzcii2.packageName), zzayk().zzjr(zzcnn.name), zzcnn.value);
                        }
                        if (z && zzcii2.zzjgt != null) {
                            zzc(new zzcix(zzcii2.zzjgt, zzcii2.zzjgo), zzcif);
                        }
                    }
                    if (zzayj().zza(zzcii2)) {
                        zzayp().zzbaz().zzd("Conditional property added", zzcii2.packageName, zzayk().zzjr(zzcii2.zzjgn.name), zzcii2.zzjgn.getValue());
                    } else {
                        zzayp().zzbau().zzd("Too many conditional properties, ignoring", zzcjj.zzjs(zzcii2.packageName), zzayk().zzjr(zzcii2.zzjgn.name), zzcii2.zzjgn.getValue());
                    }
                    zzayj().setTransactionSuccessful();
                } finally {
                    zzayj().endTransaction();
                }
            } else {
                zzg(zzcif);
            }
        }
    }

    @WorkerThread
    final void zzb(zzcix zzcix, zzcif zzcif) {
        zzbq.checkNotNull(zzcif);
        zzbq.zzgv(zzcif.packageName);
        zzayo().zzwj();
        zzyk();
        String str = zzcif.packageName;
        long j = zzcix.zzjib;
        zzayl();
        if (!zzcno.zzd(zzcix, zzcif)) {
            return;
        }
        if (zzcif.zzjfv) {
            zzayj().beginTransaction();
            try {
                List emptyList;
                Object obj;
                zzclh zzayj = zzayj();
                zzbq.zzgv(str);
                zzayj.zzwj();
                zzayj.zzyk();
                if (j < 0) {
                    zzayj.zzayp().zzbaw().zze("Invalid time querying timed out conditional properties", zzcjj.zzjs(str), Long.valueOf(j));
                    emptyList = Collections.emptyList();
                } else {
                    emptyList = zzayj.zzd("active=0 and app_id=? and abs(? - creation_timestamp) > trigger_timeout", new String[]{str, String.valueOf(j)});
                }
                for (zzcii zzcii : r2) {
                    if (zzcii != null) {
                        zzayp().zzbaz().zzd("User property timed out", zzcii.packageName, zzayk().zzjr(zzcii.zzjgn.name), zzcii.zzjgn.getValue());
                        if (zzcii.zzjgr != null) {
                            zzc(new zzcix(zzcii.zzjgr, j), zzcif);
                        }
                        zzayj().zzai(str, zzcii.zzjgn.name);
                    }
                }
                zzayj = zzayj();
                zzbq.zzgv(str);
                zzayj.zzwj();
                zzayj.zzyk();
                if (j < 0) {
                    zzayj.zzayp().zzbaw().zze("Invalid time querying expired conditional properties", zzcjj.zzjs(str), Long.valueOf(j));
                    emptyList = Collections.emptyList();
                } else {
                    emptyList = zzayj.zzd("active<>0 and app_id=? and abs(? - triggered_timestamp) > time_to_live", new String[]{str, String.valueOf(j)});
                }
                List arrayList = new ArrayList(r2.size());
                for (zzcii zzcii2 : r2) {
                    if (zzcii2 != null) {
                        zzayp().zzbaz().zzd("User property expired", zzcii2.packageName, zzayk().zzjr(zzcii2.zzjgn.name), zzcii2.zzjgn.getValue());
                        zzayj().zzaf(str, zzcii2.zzjgn.name);
                        if (zzcii2.zzjgv != null) {
                            arrayList.add(zzcii2.zzjgv);
                        }
                        zzayj().zzai(str, zzcii2.zzjgn.name);
                    }
                }
                ArrayList arrayList2 = (ArrayList) arrayList;
                int size = arrayList2.size();
                int i = 0;
                while (i < size) {
                    obj = arrayList2.get(i);
                    i++;
                    zzc(new zzcix((zzcix) obj, j), zzcif);
                }
                zzayj = zzayj();
                String str2 = zzcix.name;
                zzbq.zzgv(str);
                zzbq.zzgv(str2);
                zzayj.zzwj();
                zzayj.zzyk();
                if (j < 0) {
                    zzayj.zzayp().zzbaw().zzd("Invalid time querying triggered conditional properties", zzcjj.zzjs(str), zzayj.zzayk().zzjp(str2), Long.valueOf(j));
                    emptyList = Collections.emptyList();
                } else {
                    emptyList = zzayj.zzd("active=0 and app_id=? and trigger_event_name=? and abs(? - creation_timestamp) <= trigger_timeout", new String[]{str, str2, String.valueOf(j)});
                }
                List arrayList3 = new ArrayList(r2.size());
                for (zzcii zzcii3 : r2) {
                    if (zzcii3 != null) {
                        zzcnl zzcnl = zzcii3.zzjgn;
                        zzcnn zzcnn = new zzcnn(zzcii3.packageName, zzcii3.zzjgm, zzcnl.name, j, zzcnl.getValue());
                        if (zzayj().zza(zzcnn)) {
                            zzayp().zzbaz().zzd("User property triggered", zzcii3.packageName, zzayk().zzjr(zzcnn.name), zzcnn.value);
                        } else {
                            zzayp().zzbau().zzd("Too many active user properties, ignoring", zzcjj.zzjs(zzcii3.packageName), zzayk().zzjr(zzcnn.name), zzcnn.value);
                        }
                        if (zzcii3.zzjgt != null) {
                            arrayList3.add(zzcii3.zzjgt);
                        }
                        zzcii3.zzjgn = new zzcnl(zzcnn);
                        zzcii3.zzjgp = true;
                        zzayj().zza(zzcii3);
                    }
                }
                zzc(zzcix, zzcif);
                arrayList2 = (ArrayList) arrayList3;
                int size2 = arrayList2.size();
                i = 0;
                while (i < size2) {
                    obj = arrayList2.get(i);
                    i++;
                    zzc(new zzcix((zzcix) obj, j), zzcif);
                }
                zzayj().setTransactionSuccessful();
            } finally {
                zzayj().endTransaction();
            }
        } else {
            zzg(zzcif);
        }
    }

    @WorkerThread
    final void zzb(zzcix zzcix, String str) {
        zzcie zzjj = zzayj().zzjj(str);
        if (zzjj == null || TextUtils.isEmpty(zzjj.zzwo())) {
            zzayp().zzbaz().zzj("No app data available; dropping event", str);
            return;
        }
        Boolean zzc = zzc(zzjj);
        if (zzc == null) {
            if (!"_ui".equals(zzcix.name)) {
                zzayp().zzbaw().zzj("Could not find package. appId", zzcjj.zzjs(str));
            }
        } else if (!zzc.booleanValue()) {
            zzayp().zzbau().zzj("App version does not match; dropping event. appId", zzcjj.zzjs(str));
            return;
        }
        zzcix zzcix2 = zzcix;
        zzb(zzcix2, new zzcif(str, zzjj.getGmpAppId(), zzjj.zzwo(), zzjj.zzayx(), zzjj.zzayy(), zzjj.zzayz(), zzjj.zzaza(), null, zzjj.zzazb(), false, zzjj.zzayu(), zzjj.zzazo(), 0, 0, zzjj.zzazp()));
    }

    final void zzb(zzcli zzcli) {
        this.zzjov++;
    }

    @WorkerThread
    final void zzb(zzcnl zzcnl, zzcif zzcif) {
        int i = 0;
        zzayo().zzwj();
        zzyk();
        if (!TextUtils.isEmpty(zzcif.zzjfl)) {
            if (zzcif.zzjfv) {
                int zzkk = zzayl().zzkk(zzcnl.name);
                String zza;
                if (zzkk != 0) {
                    zzayl();
                    zza = zzcno.zza(zzcnl.name, 24, true);
                    if (zzcnl.name != null) {
                        i = zzcnl.name.length();
                    }
                    zzayl().zza(zzcif.packageName, zzkk, "_ev", zza, i);
                    return;
                }
                zzkk = zzayl().zzl(zzcnl.name, zzcnl.getValue());
                if (zzkk != 0) {
                    zzayl();
                    zza = zzcno.zza(zzcnl.name, 24, true);
                    Object value = zzcnl.getValue();
                    if (value != null && ((value instanceof String) || (value instanceof CharSequence))) {
                        i = String.valueOf(value).length();
                    }
                    zzayl().zza(zzcif.packageName, zzkk, "_ev", zza, i);
                    return;
                }
                Object zzm = zzayl().zzm(zzcnl.name, zzcnl.getValue());
                if (zzm != null) {
                    zzcnn zzcnn = new zzcnn(zzcif.packageName, zzcnl.zzjgm, zzcnl.name, zzcnl.zzjsi, zzm);
                    zzayp().zzbaz().zze("Setting user property", zzayk().zzjr(zzcnn.name), zzm);
                    zzayj().beginTransaction();
                    try {
                        zzg(zzcif);
                        boolean zza2 = zzayj().zza(zzcnn);
                        zzayj().setTransactionSuccessful();
                        if (zza2) {
                            zzayp().zzbaz().zze("User property set", zzayk().zzjr(zzcnn.name), zzcnn.value);
                        } else {
                            zzayp().zzbau().zze("Too many unique user properties are set. Ignoring user property", zzayk().zzjr(zzcnn.name), zzcnn.value);
                            zzayl().zza(zzcif.packageName, 9, null, null, 0);
                        }
                        zzayj().endTransaction();
                        return;
                    } catch (Throwable th) {
                        zzayj().endTransaction();
                    }
                } else {
                    return;
                }
            }
            zzg(zzcif);
        }
    }

    @WorkerThread
    final void zzb(String str, int i, Throwable th, byte[] bArr, Map<String, List<String>> map) {
        boolean z = true;
        zzayo().zzwj();
        zzyk();
        zzbq.zzgv(str);
        if (bArr == null) {
            try {
                bArr = new byte[0];
            } catch (Throwable th2) {
                this.zzjoz = false;
                zzbce();
            }
        }
        zzayp().zzbba().zzj("onConfigFetched. Response size", Integer.valueOf(bArr.length));
        zzayj().beginTransaction();
        zzcie zzjj = zzayj().zzjj(str);
        boolean z2 = (i == 200 || i == 204 || i == 304) && th == null;
        if (zzjj == null) {
            zzayp().zzbaw().zzj("App does not exist in onConfigFetched. appId", zzcjj.zzjs(str));
        } else if (z2 || i == WalletConstants.ERROR_CODE_INVALID_PARAMETERS) {
            List list = map != null ? (List) map.get(HttpRequest.HEADER_LAST_MODIFIED) : null;
            String str2 = (list == null || list.size() <= 0) ? null : (String) list.get(0);
            if (i == WalletConstants.ERROR_CODE_INVALID_PARAMETERS || i == 304) {
                if (zzaym().zzka(str) == null && !zzaym().zzb(str, null, null)) {
                    zzayj().endTransaction();
                    this.zzjoz = false;
                    zzbce();
                    return;
                }
            } else if (!zzaym().zzb(str, bArr, str2)) {
                zzayj().endTransaction();
                this.zzjoz = false;
                zzbce();
                return;
            }
            zzjj.zzar(this.zzdir.currentTimeMillis());
            zzayj().zza(zzjj);
            if (i == WalletConstants.ERROR_CODE_INVALID_PARAMETERS) {
                zzayp().zzbax().zzj("Config not found. Using empty config. appId", str);
            } else {
                zzayp().zzbba().zze("Successfully fetched config. Got network response. code, size", Integer.valueOf(i), Integer.valueOf(bArr.length));
            }
            if (zzbbs().zzaax() && zzbbz()) {
                zzbby();
            } else {
                zzbca();
            }
        } else {
            zzjj.zzas(this.zzdir.currentTimeMillis());
            zzayj().zza(zzjj);
            zzayp().zzbba().zze("Fetching config failed. code, error", Integer.valueOf(i), th);
            zzaym().zzkc(str);
            zzayq().zzjlo.set(this.zzdir.currentTimeMillis());
            if (!(i == 503 || i == 429)) {
                z = false;
            }
            if (z) {
                zzayq().zzjlp.set(this.zzdir.currentTimeMillis());
            }
            zzbca();
        }
        zzayj().setTransactionSuccessful();
        zzayj().endTransaction();
        this.zzjoz = false;
        zzbce();
    }

    @WorkerThread
    protected final boolean zzbbn() {
        boolean z = false;
        zzyk();
        zzayo().zzwj();
        if (this.zzjop == null || this.zzjoq == 0 || !(this.zzjop == null || this.zzjop.booleanValue() || Math.abs(this.zzdir.elapsedRealtime() - this.zzjoq) <= 1000)) {
            this.zzjoq = this.zzdir.elapsedRealtime();
            if (zzayl().zzeh("android.permission.INTERNET") && zzayl().zzeh("android.permission.ACCESS_NETWORK_STATE") && (zzbih.zzdd(this.zzaiq).zzaoe() || (zzcka.zzbj(this.zzaiq) && zzcmy.zzg(this.zzaiq, false)))) {
                z = true;
            }
            this.zzjop = Boolean.valueOf(z);
            if (this.zzjop.booleanValue()) {
                this.zzjop = Boolean.valueOf(zzayl().zzkn(zzaye().getGmpAppId()));
            }
        }
        return this.zzjop.booleanValue();
    }

    public final zzcjj zzbbo() {
        return (this.zzjnu == null || !this.zzjnu.isInitialized()) ? null : this.zzjnu;
    }

    final zzcke zzbbp() {
        return this.zzjnv;
    }

    public final AppMeasurement zzbbq() {
        return this.zzjny;
    }

    public final FirebaseAnalytics zzbbr() {
        return this.zzjnz;
    }

    public final zzcjn zzbbs() {
        zza(this.zzjoc);
        return this.zzjoc;
    }

    final long zzbbw() {
        Long valueOf = Long.valueOf(zzayq().zzjls.get());
        return valueOf.longValue() == 0 ? this.zzjgk : Math.min(this.zzjgk, valueOf.longValue());
    }

    @WorkerThread
    public final void zzbby() {
        String str;
        zzayo().zzwj();
        zzyk();
        this.zzjpb = true;
        String zzazw;
        try {
            Boolean zzbck = zzayg().zzbck();
            if (zzbck == null) {
                zzayp().zzbaw().log("Upload data called on the client side before use of service was decided");
                this.zzjpb = false;
                zzbce();
            } else if (zzbck.booleanValue()) {
                zzayp().zzbau().log("Upload called in the client side when service should be used");
                this.zzjpb = false;
                zzbce();
            } else if (this.zzjoy > 0) {
                zzbca();
                this.zzjpb = false;
                zzbce();
            } else {
                zzayo().zzwj();
                if ((this.zzjot != null ? 1 : null) != null) {
                    zzayp().zzbba().log("Uploading requested multiple times");
                    this.zzjpb = false;
                    zzbce();
                } else if (zzbbs().zzaax()) {
                    long currentTimeMillis = this.zzdir.currentTimeMillis();
                    zzg(null, currentTimeMillis - zzcik.zzazt());
                    long j = zzayq().zzjln.get();
                    if (j != 0) {
                        zzayp().zzbaz().zzj("Uploading events. Elapsed time since last upload attempt (ms)", Long.valueOf(Math.abs(currentTimeMillis - j)));
                    }
                    zzazw = zzayj().zzazw();
                    Object zzba;
                    if (TextUtils.isEmpty(zzazw)) {
                        this.zzjox = -1;
                        zzba = zzayj().zzba(currentTimeMillis - zzcik.zzazt());
                        if (!TextUtils.isEmpty(zzba)) {
                            zzcie zzjj = zzayj().zzjj(zzba);
                            if (zzjj != null) {
                                zzb(zzjj);
                            }
                        }
                    } else {
                        if (this.zzjox == -1) {
                            this.zzjox = zzayj().zzbad();
                        }
                        List<Pair> zzl = zzayj().zzl(zzazw, this.zzjns.zzb(zzazw, zzciz.zzjit), Math.max(0, this.zzjns.zzb(zzazw, zzciz.zzjiu)));
                        if (!zzl.isEmpty()) {
                            zzcoe zzcoe;
                            Object obj;
                            int i;
                            List subList;
                            for (Pair pair : zzl) {
                                zzcoe = (zzcoe) pair.first;
                                if (!TextUtils.isEmpty(zzcoe.zzjvc)) {
                                    obj = zzcoe.zzjvc;
                                    break;
                                }
                            }
                            obj = null;
                            if (obj != null) {
                                for (i = 0; i < zzl.size(); i++) {
                                    zzcoe = (zzcoe) ((Pair) zzl.get(i)).first;
                                    if (!TextUtils.isEmpty(zzcoe.zzjvc) && !zzcoe.zzjvc.equals(obj)) {
                                        subList = zzl.subList(0, i);
                                        break;
                                    }
                                }
                            }
                            subList = zzl;
                            zzcod zzcod = new zzcod();
                            zzcod.zzjun = new zzcoe[subList.size()];
                            Collection arrayList = new ArrayList(subList.size());
                            Object obj2 = (zzcik.zzazv() && this.zzjns.zzjg(zzazw)) ? 1 : null;
                            for (i = 0; i < zzcod.zzjun.length; i++) {
                                zzcod.zzjun[i] = (zzcoe) ((Pair) subList.get(i)).first;
                                arrayList.add((Long) ((Pair) subList.get(i)).second);
                                zzcod.zzjun[i].zzjvb = Long.valueOf(12211);
                                zzcod.zzjun[i].zzjus = Long.valueOf(currentTimeMillis);
                                zzcod.zzjun[i].zzjvg = Boolean.valueOf(false);
                                if (obj2 == null) {
                                    zzcod.zzjun[i].zzjvn = null;
                                }
                            }
                            obj2 = zzayp().zzae(2) ? zzayk().zza(zzcod) : null;
                            obj = zzayl().zzb(zzcod);
                            str = (String) zzciz.zzjjd.get();
                            URL url = new URL(str);
                            zzbq.checkArgument(!arrayList.isEmpty());
                            if (this.zzjot != null) {
                                zzayp().zzbau().log("Set uploading progress before finishing the previous upload");
                            } else {
                                this.zzjot = new ArrayList(arrayList);
                            }
                            zzayq().zzjlo.set(currentTimeMillis);
                            zzba = "?";
                            if (zzcod.zzjun.length > 0) {
                                zzba = zzcod.zzjun[0].zzcm;
                            }
                            zzayp().zzbba().zzd("Uploading data. app, uncompressed size, data", zzba, Integer.valueOf(obj.length), obj2);
                            this.zzjpa = true;
                            zzclh zzbbs = zzbbs();
                            zzcjp zzckm = new zzckm(this);
                            zzbbs.zzwj();
                            zzbbs.zzyk();
                            zzbq.checkNotNull(url);
                            zzbq.checkNotNull(obj);
                            zzbq.checkNotNull(zzckm);
                            zzbbs.zzayo().zzi(new zzcjr(zzbbs, zzazw, url, obj, null, zzckm));
                        }
                    }
                    this.zzjpb = false;
                    zzbce();
                } else {
                    zzayp().zzbba().log("Network not connected, ignoring upload request");
                    zzbca();
                    this.zzjpb = false;
                    zzbce();
                }
            }
        } catch (MalformedURLException e) {
            zzayp().zzbau().zze("Failed to parse upload URL. Not uploading. appId", zzcjj.zzjs(zzazw), str);
        } catch (Throwable th) {
            this.zzjpb = false;
            zzbce();
        }
    }

    final void zzbcb() {
        this.zzjow++;
    }

    @WorkerThread
    final void zzbcc() {
        zzayo().zzwj();
        zzyk();
        if (!this.zzjoo) {
            zzayp().zzbay().log("This instance being marked as an uploader");
            zzayo().zzwj();
            zzyk();
            if (zzbcd() && zzbbv()) {
                int zza = zza(this.zzjos);
                int zzbar = zzaye().zzbar();
                zzayo().zzwj();
                if (zza > zzbar) {
                    zzayp().zzbau().zze("Panic: can't downgrade version. Previous, current version", Integer.valueOf(zza), Integer.valueOf(zzbar));
                } else if (zza < zzbar) {
                    if (zza(zzbar, this.zzjos)) {
                        zzayp().zzbba().zze("Storage version upgraded. Previous, current version", Integer.valueOf(zza), Integer.valueOf(zzbar));
                    } else {
                        zzayp().zzbau().zze("Storage version upgrade failed. Previous, current version", Integer.valueOf(zza), Integer.valueOf(zzbar));
                    }
                }
            }
            this.zzjoo = true;
            zzbca();
        }
    }

    public final void zzbt(boolean z) {
        zzbca();
    }

    @WorkerThread
    final void zzc(zzcii zzcii, zzcif zzcif) {
        zzbq.checkNotNull(zzcii);
        zzbq.zzgv(zzcii.packageName);
        zzbq.checkNotNull(zzcii.zzjgn);
        zzbq.zzgv(zzcii.zzjgn.name);
        zzayo().zzwj();
        zzyk();
        if (!TextUtils.isEmpty(zzcif.zzjfl)) {
            if (zzcif.zzjfv) {
                zzayj().beginTransaction();
                try {
                    zzg(zzcif);
                    zzcii zzah = zzayj().zzah(zzcii.packageName, zzcii.zzjgn.name);
                    if (zzah != null) {
                        zzayp().zzbaz().zze("Removing conditional user property", zzcii.packageName, zzayk().zzjr(zzcii.zzjgn.name));
                        zzayj().zzai(zzcii.packageName, zzcii.zzjgn.name);
                        if (zzah.zzjgp) {
                            zzayj().zzaf(zzcii.packageName, zzcii.zzjgn.name);
                        }
                        if (zzcii.zzjgv != null) {
                            Bundle bundle = null;
                            if (zzcii.zzjgv.zzjhr != null) {
                                bundle = zzcii.zzjgv.zzjhr.zzbao();
                            }
                            zzc(zzayl().zza(zzcii.zzjgv.name, bundle, zzah.zzjgm, zzcii.zzjgv.zzjib, true, false), zzcif);
                        }
                    } else {
                        zzayp().zzbaw().zze("Conditional user property doesn't exist", zzcjj.zzjs(zzcii.packageName), zzayk().zzjr(zzcii.zzjgn.name));
                    }
                    zzayj().setTransactionSuccessful();
                } finally {
                    zzayj().endTransaction();
                }
            } else {
                zzg(zzcif);
            }
        }
    }

    @WorkerThread
    final void zzc(zzcnl zzcnl, zzcif zzcif) {
        zzayo().zzwj();
        zzyk();
        if (!TextUtils.isEmpty(zzcif.zzjfl)) {
            if (zzcif.zzjfv) {
                zzayp().zzbaz().zzj("Removing user property", zzayk().zzjr(zzcnl.name));
                zzayj().beginTransaction();
                try {
                    zzg(zzcif);
                    zzayj().zzaf(zzcif.packageName, zzcnl.name);
                    zzayj().setTransactionSuccessful();
                    zzayp().zzbaz().zzj("User property removed", zzayk().zzjr(zzcnl.name));
                } finally {
                    zzayj().endTransaction();
                }
            } else {
                zzg(zzcif);
            }
        }
    }

    final void zzd(zzcif zzcif) {
        zzayj().zzjj(zzcif.packageName);
        zzclh zzayj = zzayj();
        String str = zzcif.packageName;
        zzbq.zzgv(str);
        zzayj.zzwj();
        zzayj.zzyk();
        try {
            SQLiteDatabase writableDatabase = zzayj.getWritableDatabase();
            String[] strArr = new String[]{str};
            int delete = writableDatabase.delete("main_event_params", "app_id=?", strArr) + ((((((((writableDatabase.delete("apps", "app_id=?", strArr) + 0) + writableDatabase.delete("events", "app_id=?", strArr)) + writableDatabase.delete("user_attributes", "app_id=?", strArr)) + writableDatabase.delete("conditional_properties", "app_id=?", strArr)) + writableDatabase.delete("raw_events", "app_id=?", strArr)) + writableDatabase.delete("raw_events_metadata", "app_id=?", strArr)) + writableDatabase.delete("queue", "app_id=?", strArr)) + writableDatabase.delete("audience_filter_values", "app_id=?", strArr));
            if (delete > 0) {
                zzayj.zzayp().zzbba().zze("Reset analytics data. app, records", str, Integer.valueOf(delete));
            }
        } catch (SQLiteException e) {
            zzayj.zzayp().zzbau().zze("Error resetting analytics data. appId, error", zzcjj.zzjs(str), e);
        }
        zzf(zza(this.zzaiq, zzcif.packageName, zzcif.zzjfl, zzcif.zzjfv, zzcif.zzjfx));
    }

    @WorkerThread
    final void zzd(zzcii zzcii) {
        zzcif zzke = zzke(zzcii.packageName);
        if (zzke != null) {
            zzb(zzcii, zzke);
        }
    }

    final void zze(zzcif zzcif) {
        zzayo().zzwj();
        zzyk();
        zzbq.zzgv(zzcif.packageName);
        zzg(zzcif);
    }

    @WorkerThread
    final void zze(zzcii zzcii) {
        zzcif zzke = zzke(zzcii.packageName);
        if (zzke != null) {
            zzc(zzcii, zzke);
        }
    }

    @WorkerThread
    public final void zzf(zzcif zzcif) {
        zzayo().zzwj();
        zzyk();
        zzbq.checkNotNull(zzcif);
        zzbq.zzgv(zzcif.packageName);
        if (!TextUtils.isEmpty(zzcif.zzjfl)) {
            zzcie zzjj = zzayj().zzjj(zzcif.packageName);
            if (!(zzjj == null || !TextUtils.isEmpty(zzjj.getGmpAppId()) || TextUtils.isEmpty(zzcif.zzjfl))) {
                zzjj.zzar(0);
                zzayj().zza(zzjj);
                zzaym().zzkd(zzcif.packageName);
            }
            if (zzcif.zzjfv) {
                int i;
                Bundle bundle;
                long j = zzcif.zzjgk;
                if (j == 0) {
                    j = this.zzdir.currentTimeMillis();
                }
                int i2 = zzcif.zzjgl;
                if (i2 == 0 || i2 == 1) {
                    i = i2;
                } else {
                    zzayp().zzbaw().zze("Incorrect app type, assuming installed app. appId, appType", zzcjj.zzjs(zzcif.packageName), Integer.valueOf(i2));
                    i = 0;
                }
                zzayj().beginTransaction();
                zzclh zzayj;
                String appId;
                try {
                    zzjj = zzayj().zzjj(zzcif.packageName);
                    if (!(zzjj == null || zzjj.getGmpAppId() == null || zzjj.getGmpAppId().equals(zzcif.zzjfl))) {
                        zzayp().zzbaw().zzj("New GMP App Id passed in. Removing cached database data. appId", zzcjj.zzjs(zzjj.getAppId()));
                        zzayj = zzayj();
                        appId = zzjj.getAppId();
                        zzayj.zzyk();
                        zzayj.zzwj();
                        zzbq.zzgv(appId);
                        SQLiteDatabase writableDatabase = zzayj.getWritableDatabase();
                        String[] strArr = new String[]{appId};
                        i2 = writableDatabase.delete("audience_filter_values", "app_id=?", strArr) + ((((((((writableDatabase.delete("events", "app_id=?", strArr) + 0) + writableDatabase.delete("user_attributes", "app_id=?", strArr)) + writableDatabase.delete("conditional_properties", "app_id=?", strArr)) + writableDatabase.delete("apps", "app_id=?", strArr)) + writableDatabase.delete("raw_events", "app_id=?", strArr)) + writableDatabase.delete("raw_events_metadata", "app_id=?", strArr)) + writableDatabase.delete("event_filters", "app_id=?", strArr)) + writableDatabase.delete("property_filters", "app_id=?", strArr));
                        if (i2 > 0) {
                            zzayj.zzayp().zzbba().zze("Deleted application data. app, records", appId, Integer.valueOf(i2));
                        }
                        zzjj = null;
                    }
                } catch (SQLiteException e) {
                    zzayj.zzayp().zzbau().zze("Error deleting application data. appId, error", zzcjj.zzjs(appId), e);
                } catch (Throwable th) {
                    zzayj().endTransaction();
                }
                if (zzjj != null) {
                    if (zzjj.zzayx() != -2147483648L) {
                        if (zzjj.zzayx() != zzcif.zzjfr) {
                            bundle = new Bundle();
                            bundle.putString("_pv", zzjj.zzwo());
                            zzb(new zzcix("_au", new zzciu(bundle), "auto", j), zzcif);
                        }
                    } else if (!(zzjj.zzwo() == null || zzjj.zzwo().equals(zzcif.zzina))) {
                        bundle = new Bundle();
                        bundle.putString("_pv", zzjj.zzwo());
                        zzb(new zzcix("_au", new zzciu(bundle), "auto", j), zzcif);
                    }
                }
                zzg(zzcif);
                zzcit zzcit = null;
                if (i == 0) {
                    zzcit = zzayj().zzae(zzcif.packageName, "_f");
                } else if (i == 1) {
                    zzcit = zzayj().zzae(zzcif.packageName, "_v");
                }
                if (zzcit == null) {
                    long j2 = (1 + (j / DateUtils.MILLIS_PER_HOUR)) * DateUtils.MILLIS_PER_HOUR;
                    if (i == 0) {
                        zzb(new zzcnl("_fot", j, Long.valueOf(j2), "auto"), zzcif);
                        zzayo().zzwj();
                        zzyk();
                        Bundle bundle2 = new Bundle();
                        bundle2.putLong("_c", 1);
                        bundle2.putLong("_r", 1);
                        bundle2.putLong("_uwa", 0);
                        bundle2.putLong("_pfo", 0);
                        bundle2.putLong("_sys", 0);
                        bundle2.putLong("_sysu", 0);
                        if (this.zzaiq.getPackageManager() == null) {
                            zzayp().zzbau().zzj("PackageManager is null, first open report might be inaccurate. appId", zzcjj.zzjs(zzcif.packageName));
                        } else {
                            ApplicationInfo applicationInfo;
                            PackageInfo packageInfo = null;
                            try {
                                packageInfo = zzbih.zzdd(this.zzaiq).getPackageInfo(zzcif.packageName, 0);
                            } catch (NameNotFoundException e2) {
                                zzayp().zzbau().zze("Package info is null, first open report might be inaccurate. appId", zzcjj.zzjs(zzcif.packageName), e2);
                            }
                            if (packageInfo != null) {
                                if (packageInfo.firstInstallTime != 0) {
                                    Object obj = null;
                                    if (packageInfo.firstInstallTime != packageInfo.lastUpdateTime) {
                                        bundle2.putLong("_uwa", 1);
                                    } else {
                                        obj = 1;
                                    }
                                    zzb(new zzcnl("_fi", j, Long.valueOf(obj != null ? 1 : 0), "auto"), zzcif);
                                }
                            }
                            try {
                                applicationInfo = zzbih.zzdd(this.zzaiq).getApplicationInfo(zzcif.packageName, 0);
                            } catch (NameNotFoundException e22) {
                                zzayp().zzbau().zze("Application info is null, first open report might be inaccurate. appId", zzcjj.zzjs(zzcif.packageName), e22);
                                applicationInfo = null;
                            }
                            if (applicationInfo != null) {
                                if ((applicationInfo.flags & 1) != 0) {
                                    bundle2.putLong("_sys", 1);
                                }
                                if ((applicationInfo.flags & 128) != 0) {
                                    bundle2.putLong("_sysu", 1);
                                }
                            }
                        }
                        zzclh zzayj2 = zzayj();
                        String str = zzcif.packageName;
                        zzbq.zzgv(str);
                        zzayj2.zzwj();
                        zzayj2.zzyk();
                        j2 = zzayj2.zzal(str, "first_open_count");
                        if (j2 >= 0) {
                            bundle2.putLong("_pfo", j2);
                        }
                        zzb(new zzcix("_f", new zzciu(bundle2), "auto", j), zzcif);
                    } else if (i == 1) {
                        zzb(new zzcnl("_fvt", j, Long.valueOf(j2), "auto"), zzcif);
                        zzayo().zzwj();
                        zzyk();
                        bundle = new Bundle();
                        bundle.putLong("_c", 1);
                        bundle.putLong("_r", 1);
                        zzb(new zzcix("_v", new zzciu(bundle), "auto", j), zzcif);
                    }
                    bundle = new Bundle();
                    bundle.putLong("_et", 1);
                    zzb(new zzcix("_e", new zzciu(bundle), "auto", j), zzcif);
                } else if (zzcif.zzjgj) {
                    zzb(new zzcix("_cd", new zzciu(new Bundle()), "auto", j), zzcif);
                }
                zzayj().setTransactionSuccessful();
                zzayj().endTransaction();
                return;
            }
            zzg(zzcif);
        }
    }

    @WorkerThread
    final void zzj(Runnable runnable) {
        zzayo().zzwj();
        if (this.zzjou == null) {
            this.zzjou = new ArrayList();
        }
        this.zzjou.add(runnable);
    }

    public final String zzkf(String str) {
        Object e;
        try {
            return (String) zzayo().zzc(new zzckl(this, str)).get(30000, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e2) {
            e = e2;
        } catch (InterruptedException e3) {
            e = e3;
        } catch (ExecutionException e4) {
            e = e4;
        }
        zzayp().zzbau().zze("Failed to get app instance id. appId", zzcjj.zzjs(str), e);
        return null;
    }

    public final zze zzxx() {
        return this.zzdir;
    }

    final void zzyk() {
        if (!this.initialized) {
            throw new IllegalStateException("AppMeasurement is not initialized");
        }
    }
}
