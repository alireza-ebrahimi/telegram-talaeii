package com.google.android.gms.internal.measurement;

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
import android.support.v4.p022f.C0464a;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.common.wrappers.Wrappers;
import com.google.android.gms.wallet.WalletConstants;
import com.google.firebase.analytics.FirebaseAnalytics.C1796a;
import com.google.firebase.analytics.FirebaseAnalytics.C1797b;
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
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;
import org.telegram.ui.ChatActivity;

public class zzjs implements zzed {
    private static volatile zzjs zzaqj;
    private final zzgm zzacw;
    private zzgg zzaqk;
    private zzfl zzaql;
    private zzej zzaqm;
    private zzfq zzaqn;
    private zzjo zzaqo;
    private zzeb zzaqp;
    private final zzjy zzaqq;
    private boolean zzaqr;
    @VisibleForTesting
    private long zzaqs;
    private List<Runnable> zzaqt;
    private int zzaqu;
    private int zzaqv;
    private boolean zzaqw;
    private boolean zzaqx;
    private boolean zzaqy;
    private FileLock zzaqz;
    private FileChannel zzara;
    private List<Long> zzarb;
    private List<Long> zzarc;
    private long zzard;
    private boolean zzvo;

    class zza implements zzel {
        private final /* synthetic */ zzjs zzarf;
        zzks zzarh;
        List<Long> zzari;
        List<zzkp> zzarj;
        private long zzark;

        private zza(zzjs zzjs) {
            this.zzarf = zzjs;
        }

        private static long zza(zzkp zzkp) {
            return ((zzkp.zzatn.longValue() / 1000) / 60) / 60;
        }

        public final boolean zza(long j, zzkp zzkp) {
            Preconditions.checkNotNull(zzkp);
            if (this.zzarj == null) {
                this.zzarj = new ArrayList();
            }
            if (this.zzari == null) {
                this.zzari = new ArrayList();
            }
            if (this.zzarj.size() > 0 && zza((zzkp) this.zzarj.get(0)) != zza(zzkp)) {
                return false;
            }
            long zzvv = this.zzark + ((long) zzkp.zzvv());
            if (zzvv >= ((long) Math.max(0, ((Integer) zzey.zzagx.get()).intValue()))) {
                return false;
            }
            this.zzark = zzvv;
            this.zzarj.add(zzkp);
            this.zzari.add(Long.valueOf(j));
            return this.zzarj.size() < Math.max(1, ((Integer) zzey.zzagy.get()).intValue());
        }

        public final void zzb(zzks zzks) {
            Preconditions.checkNotNull(zzks);
            this.zzarh = zzks;
        }
    }

    private zzjs(zzjx zzjx) {
        this(zzjx, null);
    }

    private zzjs(zzjx zzjx, zzgm zzgm) {
        this.zzvo = false;
        Preconditions.checkNotNull(zzjx);
        this.zzacw = zzgm.zza(zzjx.zzqx, null, null);
        this.zzard = -1;
        zzjr zzjy = new zzjy(this);
        zzjy.zzm();
        this.zzaqq = zzjy;
        zzjy = new zzfl(this);
        zzjy.zzm();
        this.zzaql = zzjy;
        zzjy = new zzgg(this);
        zzjy.zzm();
        this.zzaqk = zzjy;
        this.zzacw.zzge().zzc(new zzjt(this, zzjx));
    }

    @VisibleForTesting
    private final int zza(FileChannel fileChannel) {
        int i = 0;
        zzab();
        if (fileChannel == null || !fileChannel.isOpen()) {
            this.zzacw.zzgf().zzis().log("Bad channel to read from");
        } else {
            ByteBuffer allocate = ByteBuffer.allocate(4);
            try {
                fileChannel.position(0);
                int read = fileChannel.read(allocate);
                if (read == 4) {
                    allocate.flip();
                    i = allocate.getInt();
                } else if (read != -1) {
                    this.zzacw.zzgf().zziv().zzg("Unexpected data length. Bytes read", Integer.valueOf(read));
                }
            } catch (IOException e) {
                this.zzacw.zzgf().zzis().zzg("Failed to read from channel", e);
            }
        }
        return i;
    }

    private final zzdz zza(Context context, String str, String str2, boolean z, boolean z2, boolean z3, long j) {
        Object charSequence;
        String str3 = "Unknown";
        String str4 = "Unknown";
        int i = Integer.MIN_VALUE;
        String str5 = "Unknown";
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            this.zzacw.zzgf().zzis().log("PackageManager is null, can not log app install information");
            return null;
        }
        try {
            str3 = packageManager.getInstallerPackageName(str);
        } catch (IllegalArgumentException e) {
            this.zzacw.zzgf().zzis().zzg("Error retrieving installer package name. appId", zzfh.zzbl(str));
        }
        if (str3 == null) {
            str3 = "manual_install";
        } else if ("com.android.vending".equals(str3)) {
            str3 = TtmlNode.ANONYMOUS_REGION_ID;
        }
        try {
            PackageInfo packageInfo = Wrappers.packageManager(context).getPackageInfo(str, 0);
            if (packageInfo != null) {
                CharSequence applicationLabel = Wrappers.packageManager(context).getApplicationLabel(str);
                if (TextUtils.isEmpty(applicationLabel)) {
                    String str6 = str5;
                } else {
                    charSequence = applicationLabel.toString();
                }
                try {
                    str4 = packageInfo.versionName;
                    i = packageInfo.versionCode;
                } catch (NameNotFoundException e2) {
                    this.zzacw.zzgf().zzis().zze("Error retrieving newly installed package info. appId, appName", zzfh.zzbl(str), charSequence);
                    return null;
                }
            }
            this.zzacw.zzgi();
            long j2 = 0;
            if (this.zzacw.zzgh().zzaz(str)) {
                j2 = j;
            }
            return new zzdz(str, str2, str4, (long) i, str3, 12451, this.zzacw.zzgc().zzd(context, str), null, z, false, TtmlNode.ANONYMOUS_REGION_ID, 0, j2, 0, z2, z3, false);
        } catch (NameNotFoundException e3) {
            charSequence = str5;
            this.zzacw.zzgf().zzis().zze("Error retrieving newly installed package info. appId, appName", zzfh.zzbl(str), charSequence);
            return null;
        }
    }

    private static void zza(zzjr zzjr) {
        if (zzjr == null) {
            throw new IllegalStateException("Upload Component not created");
        } else if (!zzjr.isInitialized()) {
            String valueOf = String.valueOf(zzjr.getClass());
            throw new IllegalStateException(new StringBuilder(String.valueOf(valueOf).length() + 27).append("Component not initialized: ").append(valueOf).toString());
        }
    }

    private final void zza(zzjx zzjx) {
        this.zzacw.zzge().zzab();
        zzjr zzej = new zzej(this);
        zzej.zzm();
        this.zzaqm = zzej;
        this.zzacw.zzgh().zza(this.zzaqk);
        zzej = new zzeb(this);
        zzej.zzm();
        this.zzaqp = zzej;
        zzej = new zzjo(this);
        zzej.zzm();
        this.zzaqo = zzej;
        this.zzaqn = new zzfq(this);
        if (this.zzaqu != this.zzaqv) {
            this.zzacw.zzgf().zzis().zze("Not all upload components initialized", Integer.valueOf(this.zzaqu), Integer.valueOf(this.zzaqv));
        }
        this.zzvo = true;
    }

    @VisibleForTesting
    private final boolean zza(int i, FileChannel fileChannel) {
        zzab();
        if (fileChannel == null || !fileChannel.isOpen()) {
            this.zzacw.zzgf().zzis().log("Bad channel to read from");
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
            this.zzacw.zzgf().zzis().zzg("Error writing to channel. Bytes written", Long.valueOf(fileChannel.size()));
            return true;
        } catch (IOException e) {
            this.zzacw.zzgf().zzis().zzg("Failed to write to channel", e);
            return false;
        }
    }

    private final boolean zza(String str, zzew zzew) {
        long round;
        Object string = zzew.zzafr.getString(C1797b.CURRENCY);
        if (C1796a.ECOMMERCE_PURCHASE.equals(zzew.name)) {
            double doubleValue = zzew.zzafr.zzbg(C1797b.VALUE).doubleValue() * 1000000.0d;
            if (doubleValue == 0.0d) {
                doubleValue = ((double) zzew.zzafr.getLong(C1797b.VALUE).longValue()) * 1000000.0d;
            }
            if (doubleValue > 9.223372036854776E18d || doubleValue < -9.223372036854776E18d) {
                this.zzacw.zzgf().zziv().zze("Data lost. Currency value is too big. appId", zzfh.zzbl(str), Double.valueOf(doubleValue));
                return false;
            }
            round = Math.round(doubleValue);
        } else {
            round = zzew.zzafr.getLong(C1797b.VALUE).longValue();
        }
        if (!TextUtils.isEmpty(string)) {
            String toUpperCase = string.toUpperCase(Locale.US);
            if (toUpperCase.matches("[A-Z]{3}")) {
                String valueOf = String.valueOf("_ltv_");
                toUpperCase = String.valueOf(toUpperCase);
                String concat = toUpperCase.length() != 0 ? valueOf.concat(toUpperCase) : new String(valueOf);
                zzkb zzh = zzje().zzh(str, concat);
                if (zzh == null || !(zzh.value instanceof Long)) {
                    zzhh zzje = zzje();
                    int zzb = this.zzacw.zzgh().zzb(str, zzey.zzaht) - 1;
                    Preconditions.checkNotEmpty(str);
                    zzje.zzab();
                    zzje.zzch();
                    try {
                        zzje.getWritableDatabase().execSQL("delete from user_attributes where app_id=? and name in (select name from user_attributes where app_id=? and name like '_ltv_%' order by set_timestamp desc limit ?,10);", new String[]{str, str, String.valueOf(zzb)});
                    } catch (SQLiteException e) {
                        zzje.zzgf().zzis().zze("Error pruning currencies. appId", zzfh.zzbl(str), e);
                    }
                    zzh = new zzkb(str, zzew.origin, concat, this.zzacw.zzbt().currentTimeMillis(), Long.valueOf(round));
                } else {
                    zzh = new zzkb(str, zzew.origin, concat, this.zzacw.zzbt().currentTimeMillis(), Long.valueOf(round + ((Long) zzh.value).longValue()));
                }
                if (!zzje().zza(zzh)) {
                    this.zzacw.zzgf().zzis().zzd("Too many unique user properties are set. Ignoring user property. appId", zzfh.zzbl(str), this.zzacw.zzgb().zzbk(zzh.name), zzh.value);
                    this.zzacw.zzgc().zza(str, 9, null, null, 0);
                }
            }
        }
        return true;
    }

    private final zzko[] zza(String str, zzku[] zzkuArr, zzkp[] zzkpArr) {
        Preconditions.checkNotEmpty(str);
        return zzjd().zza(str, zzkpArr, zzkuArr);
    }

    private final void zzab() {
        this.zzacw.zzge().zzab();
    }

    private final void zzb(zzdy zzdy) {
        zzab();
        if (TextUtils.isEmpty(zzdy.getGmpAppId())) {
            zzb(zzdy.zzah(), 204, null, null, null);
            return;
        }
        String gmpAppId = zzdy.getGmpAppId();
        String appInstanceId = zzdy.getAppInstanceId();
        Builder builder = new Builder();
        Builder encodedAuthority = builder.scheme((String) zzey.zzagt.get()).encodedAuthority((String) zzey.zzagu.get());
        String str = "config/app/";
        String valueOf = String.valueOf(gmpAppId);
        encodedAuthority.path(valueOf.length() != 0 ? str.concat(valueOf) : new String(str)).appendQueryParameter("app_instance_id", appInstanceId).appendQueryParameter("platform", "android").appendQueryParameter("gmp_version", "12451");
        String uri = builder.build().toString();
        try {
            Map map;
            URL url = new URL(uri);
            this.zzacw.zzgf().zziz().zzg("Fetching remote configuration", zzdy.zzah());
            zzkm zzbt = zzkv().zzbt(zzdy.zzah());
            CharSequence zzbu = zzkv().zzbu(zzdy.zzah());
            if (zzbt == null || TextUtils.isEmpty(zzbu)) {
                map = null;
            } else {
                Map c0464a = new C0464a();
                c0464a.put("If-Modified-Since", zzbu);
                map = c0464a;
            }
            this.zzaqw = true;
            zzhh zzkw = zzkw();
            appInstanceId = zzdy.zzah();
            zzfn zzjv = new zzjv(this);
            zzkw.zzab();
            zzkw.zzch();
            Preconditions.checkNotNull(url);
            Preconditions.checkNotNull(zzjv);
            zzkw.zzge().zzd(new zzfp(zzkw, appInstanceId, url, null, map, zzjv));
        } catch (MalformedURLException e) {
            this.zzacw.zzgf().zzis().zze("Failed to parse config URL. Not fetching. appId", zzfh.zzbl(zzdy.zzah()), uri);
        }
    }

    private final Boolean zzc(zzdy zzdy) {
        try {
            if (zzdy.zzgo() != -2147483648L) {
                if (zzdy.zzgo() == ((long) Wrappers.packageManager(this.zzacw.getContext()).getPackageInfo(zzdy.zzah(), 0).versionCode)) {
                    return Boolean.valueOf(true);
                }
            }
            String str = Wrappers.packageManager(this.zzacw.getContext()).getPackageInfo(zzdy.zzah(), 0).versionName;
            if (zzdy.zzag() != null && zzdy.zzag().equals(str)) {
                return Boolean.valueOf(true);
            }
            return Boolean.valueOf(false);
        } catch (NameNotFoundException e) {
            return null;
        }
    }

    private final void zzc(zzew zzew, zzdz zzdz) {
        Preconditions.checkNotNull(zzdz);
        Preconditions.checkNotEmpty(zzdz.packageName);
        long nanoTime = System.nanoTime();
        zzab();
        zzkz();
        String str = zzdz.packageName;
        if (!this.zzacw.zzgc().zzd(zzew, zzdz)) {
            return;
        }
        if (!zzdz.zzadw) {
            zzg(zzdz);
        } else if (zzkv().zzn(str, zzew.name)) {
            this.zzacw.zzgf().zziv().zze("Dropping blacklisted event. appId", zzfh.zzbl(str), this.zzacw.zzgb().zzbi(zzew.name));
            Object obj = (zzkv().zzbx(str) || zzkv().zzby(str)) ? 1 : null;
            if (obj == null && !"_err".equals(zzew.name)) {
                this.zzacw.zzgc().zza(str, 11, "_ev", zzew.name, 0);
            }
            if (obj != null) {
                zzdy zzbb = zzje().zzbb(str);
                if (zzbb != null) {
                    if (Math.abs(this.zzacw.zzbt().currentTimeMillis() - Math.max(zzbb.zzgu(), zzbb.zzgt())) > ((Long) zzey.zzaho.get()).longValue()) {
                        this.zzacw.zzgf().zziy().log("Fetching config for blacklisted app");
                        zzb(zzbb);
                    }
                }
            }
        } else {
            if (this.zzacw.zzgf().isLoggable(2)) {
                this.zzacw.zzgf().zziz().zzg("Logging event", this.zzacw.zzgb().zzb(zzew));
            }
            zzje().beginTransaction();
            zzg(zzdz);
            if (("_iap".equals(zzew.name) || C1796a.ECOMMERCE_PURCHASE.equals(zzew.name)) && !zza(str, zzew)) {
                zzje().setTransactionSuccessful();
                zzje().endTransaction();
                return;
            }
            zzks zzks;
            try {
                boolean zzcb = zzkc.zzcb(zzew.name);
                boolean equals = "_err".equals(zzew.name);
                zzek zza = zzje().zza(zzla(), str, true, zzcb, false, equals, false);
                long intValue = zza.zzaff - ((long) ((Integer) zzey.zzagz.get()).intValue());
                if (intValue > 0) {
                    if (intValue % 1000 == 1) {
                        this.zzacw.zzgf().zzis().zze("Data loss. Too many events logged. appId, count", zzfh.zzbl(str), Long.valueOf(zza.zzaff));
                    }
                    zzje().setTransactionSuccessful();
                    zzje().endTransaction();
                    return;
                }
                zzes zzac;
                zzer zzer;
                boolean z;
                if (zzcb) {
                    intValue = zza.zzafe - ((long) ((Integer) zzey.zzahb.get()).intValue());
                    if (intValue > 0) {
                        if (intValue % 1000 == 1) {
                            this.zzacw.zzgf().zzis().zze("Data loss. Too many public events logged. appId, count", zzfh.zzbl(str), Long.valueOf(zza.zzafe));
                        }
                        this.zzacw.zzgc().zza(str, 16, "_ev", zzew.name, 0);
                        zzje().setTransactionSuccessful();
                        zzje().endTransaction();
                        return;
                    }
                }
                if (equals) {
                    intValue = zza.zzafh - ((long) Math.max(0, Math.min(1000000, this.zzacw.zzgh().zzb(zzdz.packageName, zzey.zzaha))));
                    if (intValue > 0) {
                        if (intValue == 1) {
                            this.zzacw.zzgf().zzis().zze("Too many error events logged. appId, count", zzfh.zzbl(str), Long.valueOf(zza.zzafh));
                        }
                        zzje().setTransactionSuccessful();
                        zzje().endTransaction();
                        return;
                    }
                }
                Bundle zzij = zzew.zzafr.zzij();
                this.zzacw.zzgc().zza(zzij, "_o", zzew.origin);
                if (this.zzacw.zzgc().zzci(str)) {
                    this.zzacw.zzgc().zza(zzij, "_dbg", Long.valueOf(1));
                    this.zzacw.zzgc().zza(zzij, "_r", Long.valueOf(1));
                }
                long zzbc = zzje().zzbc(str);
                if (zzbc > 0) {
                    this.zzacw.zzgf().zziv().zze("Data lost. Too many events stored on disk, deleted. appId", zzfh.zzbl(str), Long.valueOf(zzbc));
                }
                zzer zzer2 = new zzer(this.zzacw, zzew.origin, str, zzew.name, zzew.zzagc, 0, zzij);
                zzes zzf = zzje().zzf(str, zzer2.name);
                if (zzf != null) {
                    zzer2 = zzer2.zza(this.zzacw, zzf.zzafu);
                    zzac = zzf.zzac(zzer2.timestamp);
                    zzer = zzer2;
                } else if (zzje().zzbf(str) < 500 || !zzcb) {
                    zzac = new zzes(str, zzer2.name, 0, 0, zzer2.timestamp, 0, null, null, null);
                    zzer = zzer2;
                } else {
                    this.zzacw.zzgf().zzis().zzd("Too many event names used, ignoring event. appId, name, supported count", zzfh.zzbl(str), this.zzacw.zzgb().zzbi(zzer2.name), Integer.valueOf(ChatActivity.startAllServices));
                    this.zzacw.zzgc().zza(str, 8, null, null, 0);
                    zzje().endTransaction();
                    return;
                }
                zzje().zza(zzac);
                zzab();
                zzkz();
                Preconditions.checkNotNull(zzer);
                Preconditions.checkNotNull(zzdz);
                Preconditions.checkNotEmpty(zzer.zzti);
                Preconditions.checkArgument(zzer.zzti.equals(zzdz.packageName));
                zzks = new zzks();
                zzks.zzatt = Integer.valueOf(1);
                zzks.zzaub = "android";
                zzks.zzti = zzdz.packageName;
                zzks.zzadt = zzdz.zzadt;
                zzks.zzth = zzdz.zzth;
                zzks.zzaun = zzdz.zzads == -2147483648L ? null : Integer.valueOf((int) zzdz.zzads);
                zzks.zzauf = Long.valueOf(zzdz.zzadu);
                zzks.zzadm = zzdz.zzadm;
                zzks.zzauj = zzdz.zzadv == 0 ? null : Long.valueOf(zzdz.zzadv);
                Pair zzbn = this.zzacw.zzgg().zzbn(zzdz.packageName);
                if (zzbn == null || TextUtils.isEmpty((CharSequence) zzbn.first)) {
                    if (!this.zzacw.zzfx().zzf(this.zzacw.getContext()) && zzdz.zzadz) {
                        String string = Secure.getString(this.zzacw.getContext().getContentResolver(), "android_id");
                        if (string == null) {
                            this.zzacw.zzgf().zziv().zzg("null secure ID. appId", zzfh.zzbl(zzks.zzti));
                            string = "null";
                        } else if (string.isEmpty()) {
                            this.zzacw.zzgf().zziv().zzg("empty secure ID. appId", zzfh.zzbl(zzks.zzti));
                        }
                        zzks.zzauq = string;
                    }
                } else if (zzdz.zzady) {
                    zzks.zzauh = (String) zzbn.first;
                    zzks.zzaui = (Boolean) zzbn.second;
                }
                this.zzacw.zzfx().zzch();
                zzks.zzaud = Build.MODEL;
                this.zzacw.zzfx().zzch();
                zzks.zzauc = VERSION.RELEASE;
                zzks.zzaue = Integer.valueOf((int) this.zzacw.zzfx().zzig());
                zzks.zzafo = this.zzacw.zzfx().zzih();
                zzks.zzaug = null;
                zzks.zzatw = null;
                zzks.zzatx = null;
                zzks.zzaty = null;
                zzks.zzaus = Long.valueOf(zzdz.zzadx);
                if (this.zzacw.isEnabled() && zzeg.zzho()) {
                    zzks.zzaut = null;
                }
                zzdy zzbb2 = zzje().zzbb(zzdz.packageName);
                if (zzbb2 == null) {
                    zzbb2 = new zzdy(this.zzacw, zzdz.packageName);
                    zzbb2.zzak(this.zzacw.zzfw().zzio());
                    zzbb2.zzan(zzdz.zzado);
                    zzbb2.zzal(zzdz.zzadm);
                    zzbb2.zzam(this.zzacw.zzgg().zzbo(zzdz.packageName));
                    zzbb2.zzr(0);
                    zzbb2.zzm(0);
                    zzbb2.zzn(0);
                    zzbb2.setAppVersion(zzdz.zzth);
                    zzbb2.zzo(zzdz.zzads);
                    zzbb2.zzao(zzdz.zzadt);
                    zzbb2.zzp(zzdz.zzadu);
                    zzbb2.zzq(zzdz.zzadv);
                    zzbb2.setMeasurementEnabled(zzdz.zzadw);
                    zzbb2.zzaa(zzdz.zzadx);
                    zzje().zza(zzbb2);
                }
                zzks.zzadl = zzbb2.getAppInstanceId();
                zzks.zzado = zzbb2.zzgl();
                List zzba = zzje().zzba(zzdz.packageName);
                zzks.zzatv = new zzku[zzba.size()];
                for (int i = 0; i < zzba.size(); i++) {
                    zzku zzku = new zzku();
                    zzks.zzatv[i] = zzku;
                    zzku.name = ((zzkb) zzba.get(i)).name;
                    zzku.zzauz = Long.valueOf(((zzkb) zzba.get(i)).zzarl);
                    zzjc().zza(zzku, ((zzkb) zzba.get(i)).value);
                }
                long zza2 = zzje().zza(zzks);
                zzej zzje = zzje();
                if (zzer.zzafr != null) {
                    Iterator it = zzer.zzafr.iterator();
                    while (it.hasNext()) {
                        if ("_r".equals((String) it.next())) {
                            z = true;
                            break;
                        }
                    }
                    z = zzkv().zzo(zzer.zzti, zzer.name);
                    zzek zza3 = zzje().zza(zzla(), zzer.zzti, false, false, false, false, false);
                    if (z && zza3.zzafi < ((long) this.zzacw.zzgh().zzaq(zzer.zzti))) {
                        z = true;
                        if (zzje.zza(zzer, zza2, z)) {
                            this.zzaqs = 0;
                        }
                        zzje().setTransactionSuccessful();
                        if (this.zzacw.zzgf().isLoggable(2)) {
                            this.zzacw.zzgf().zziz().zzg("Event recorded", this.zzacw.zzgb().zza(zzer));
                        }
                        zzje().endTransaction();
                        zzld();
                        this.zzacw.zzgf().zziz().zzg("Background event processing time, ms", Long.valueOf(((System.nanoTime() - nanoTime) + 500000) / C3446C.MICROS_PER_SECOND));
                    }
                }
                z = false;
                if (zzje.zza(zzer, zza2, z)) {
                    this.zzaqs = 0;
                }
                zzje().setTransactionSuccessful();
                if (this.zzacw.zzgf().isLoggable(2)) {
                    this.zzacw.zzgf().zziz().zzg("Event recorded", this.zzacw.zzgb().zza(zzer));
                }
                zzje().endTransaction();
                zzld();
                this.zzacw.zzgf().zziz().zzg("Background event processing time, ms", Long.valueOf(((System.nanoTime() - nanoTime) + 500000) / C3446C.MICROS_PER_SECOND));
            } catch (IOException e) {
                this.zzacw.zzgf().zzis().zze("Data loss. Failed to insert raw event metadata. appId", zzfh.zzbl(zzks.zzti), e);
            } catch (Throwable th) {
                zzje().endTransaction();
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final boolean zzd(java.lang.String r31, long r32) {
        /*
        r30 = this;
        r2 = r30.zzje();
        r2.beginTransaction();
        r21 = new com.google.android.gms.internal.measurement.zzjs$zza;	 Catch:{ all -> 0x01d5 }
        r2 = 0;
        r0 = r21;
        r1 = r30;
        r0.<init>();	 Catch:{ all -> 0x01d5 }
        r14 = r30.zzje();	 Catch:{ all -> 0x01d5 }
        r4 = 0;
        r0 = r30;
        r0 = r0.zzard;	 Catch:{ all -> 0x01d5 }
        r16 = r0;
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r21);	 Catch:{ all -> 0x01d5 }
        r14.zzab();	 Catch:{ all -> 0x01d5 }
        r14.zzch();	 Catch:{ all -> 0x01d5 }
        r3 = 0;
        r2 = r14.getWritableDatabase();	 Catch:{ SQLiteException -> 0x0c7b }
        r5 = 0;
        r5 = android.text.TextUtils.isEmpty(r5);	 Catch:{ SQLiteException -> 0x0c7b }
        if (r5 == 0) goto L_0x01de;
    L_0x0031:
        r6 = -1;
        r5 = (r16 > r6 ? 1 : (r16 == r6 ? 0 : -1));
        if (r5 == 0) goto L_0x0170;
    L_0x0037:
        r5 = 2;
        r5 = new java.lang.String[r5];	 Catch:{ SQLiteException -> 0x0c7b }
        r6 = 0;
        r7 = java.lang.String.valueOf(r16);	 Catch:{ SQLiteException -> 0x0c7b }
        r5[r6] = r7;	 Catch:{ SQLiteException -> 0x0c7b }
        r6 = 1;
        r7 = java.lang.String.valueOf(r32);	 Catch:{ SQLiteException -> 0x0c7b }
        r5[r6] = r7;	 Catch:{ SQLiteException -> 0x0c7b }
        r6 = r5;
    L_0x0049:
        r8 = -1;
        r5 = (r16 > r8 ? 1 : (r16 == r8 ? 0 : -1));
        if (r5 == 0) goto L_0x017d;
    L_0x004f:
        r5 = "rowid <= ? and ";
    L_0x0052:
        r7 = java.lang.String.valueOf(r5);	 Catch:{ SQLiteException -> 0x0c7b }
        r7 = r7.length();	 Catch:{ SQLiteException -> 0x0c7b }
        r7 = r7 + 148;
        r8 = new java.lang.StringBuilder;	 Catch:{ SQLiteException -> 0x0c7b }
        r8.<init>(r7);	 Catch:{ SQLiteException -> 0x0c7b }
        r7 = "select app_id, metadata_fingerprint from raw_events where ";
        r7 = r8.append(r7);	 Catch:{ SQLiteException -> 0x0c7b }
        r5 = r7.append(r5);	 Catch:{ SQLiteException -> 0x0c7b }
        r7 = "app_id in (select app_id from apps where config_fetched_time >= ?) order by rowid limit 1;";
        r5 = r5.append(r7);	 Catch:{ SQLiteException -> 0x0c7b }
        r5 = r5.toString();	 Catch:{ SQLiteException -> 0x0c7b }
        r3 = r2.rawQuery(r5, r6);	 Catch:{ SQLiteException -> 0x0c7b }
        r5 = r3.moveToFirst();	 Catch:{ SQLiteException -> 0x0c7b }
        if (r5 != 0) goto L_0x0182;
    L_0x0081:
        if (r3 == 0) goto L_0x0086;
    L_0x0083:
        r3.close();	 Catch:{ all -> 0x01d5 }
    L_0x0086:
        r0 = r21;
        r2 = r0.zzarj;	 Catch:{ all -> 0x01d5 }
        if (r2 == 0) goto L_0x0096;
    L_0x008c:
        r0 = r21;
        r2 = r0.zzarj;	 Catch:{ all -> 0x01d5 }
        r2 = r2.isEmpty();	 Catch:{ all -> 0x01d5 }
        if (r2 == 0) goto L_0x0382;
    L_0x0096:
        r2 = 1;
    L_0x0097:
        if (r2 != 0) goto L_0x0c67;
    L_0x0099:
        r17 = 0;
        r0 = r21;
        r0 = r0.zzarh;	 Catch:{ all -> 0x01d5 }
        r22 = r0;
        r0 = r21;
        r2 = r0.zzarj;	 Catch:{ all -> 0x01d5 }
        r2 = r2.size();	 Catch:{ all -> 0x01d5 }
        r2 = new com.google.android.gms.internal.measurement.zzkp[r2];	 Catch:{ all -> 0x01d5 }
        r0 = r22;
        r0.zzatu = r2;	 Catch:{ all -> 0x01d5 }
        r13 = 0;
        r14 = 0;
        r0 = r30;
        r2 = r0.zzacw;	 Catch:{ all -> 0x01d5 }
        r2 = r2.zzgh();	 Catch:{ all -> 0x01d5 }
        r0 = r22;
        r3 = r0.zzti;	 Catch:{ all -> 0x01d5 }
        r18 = r2.zzau(r3);	 Catch:{ all -> 0x01d5 }
        r2 = 0;
        r16 = r2;
    L_0x00c5:
        r0 = r21;
        r2 = r0.zzarj;	 Catch:{ all -> 0x01d5 }
        r2 = r2.size();	 Catch:{ all -> 0x01d5 }
        r0 = r16;
        if (r0 >= r2) goto L_0x062a;
    L_0x00d1:
        r0 = r21;
        r2 = r0.zzarj;	 Catch:{ all -> 0x01d5 }
        r0 = r16;
        r2 = r2.get(r0);	 Catch:{ all -> 0x01d5 }
        r0 = r2;
        r0 = (com.google.android.gms.internal.measurement.zzkp) r0;	 Catch:{ all -> 0x01d5 }
        r12 = r0;
        r2 = r30.zzkv();	 Catch:{ all -> 0x01d5 }
        r0 = r21;
        r3 = r0.zzarh;	 Catch:{ all -> 0x01d5 }
        r3 = r3.zzti;	 Catch:{ all -> 0x01d5 }
        r4 = r12.name;	 Catch:{ all -> 0x01d5 }
        r2 = r2.zzn(r3, r4);	 Catch:{ all -> 0x01d5 }
        if (r2 == 0) goto L_0x0388;
    L_0x00f1:
        r0 = r30;
        r2 = r0.zzacw;	 Catch:{ all -> 0x01d5 }
        r2 = r2.zzgf();	 Catch:{ all -> 0x01d5 }
        r2 = r2.zziv();	 Catch:{ all -> 0x01d5 }
        r3 = "Dropping blacklisted raw event. appId";
        r0 = r21;
        r4 = r0.zzarh;	 Catch:{ all -> 0x01d5 }
        r4 = r4.zzti;	 Catch:{ all -> 0x01d5 }
        r4 = com.google.android.gms.internal.measurement.zzfh.zzbl(r4);	 Catch:{ all -> 0x01d5 }
        r0 = r30;
        r5 = r0.zzacw;	 Catch:{ all -> 0x01d5 }
        r5 = r5.zzgb();	 Catch:{ all -> 0x01d5 }
        r6 = r12.name;	 Catch:{ all -> 0x01d5 }
        r5 = r5.zzbi(r6);	 Catch:{ all -> 0x01d5 }
        r2.zze(r3, r4, r5);	 Catch:{ all -> 0x01d5 }
        r2 = r30.zzkv();	 Catch:{ all -> 0x01d5 }
        r0 = r21;
        r3 = r0.zzarh;	 Catch:{ all -> 0x01d5 }
        r3 = r3.zzti;	 Catch:{ all -> 0x01d5 }
        r2 = r2.zzbx(r3);	 Catch:{ all -> 0x01d5 }
        if (r2 != 0) goto L_0x013b;
    L_0x012b:
        r2 = r30.zzkv();	 Catch:{ all -> 0x01d5 }
        r0 = r21;
        r3 = r0.zzarh;	 Catch:{ all -> 0x01d5 }
        r3 = r3.zzti;	 Catch:{ all -> 0x01d5 }
        r2 = r2.zzby(r3);	 Catch:{ all -> 0x01d5 }
        if (r2 == 0) goto L_0x0385;
    L_0x013b:
        r2 = 1;
    L_0x013c:
        if (r2 != 0) goto L_0x0c9b;
    L_0x013e:
        r2 = "_err";
        r3 = r12.name;	 Catch:{ all -> 0x01d5 }
        r2 = r2.equals(r3);	 Catch:{ all -> 0x01d5 }
        if (r2 != 0) goto L_0x0c9b;
    L_0x0149:
        r0 = r30;
        r2 = r0.zzacw;	 Catch:{ all -> 0x01d5 }
        r2 = r2.zzgc();	 Catch:{ all -> 0x01d5 }
        r0 = r21;
        r3 = r0.zzarh;	 Catch:{ all -> 0x01d5 }
        r3 = r3.zzti;	 Catch:{ all -> 0x01d5 }
        r4 = 11;
        r5 = "_ev";
        r6 = r12.name;	 Catch:{ all -> 0x01d5 }
        r7 = 0;
        r2.zza(r3, r4, r5, r6, r7);	 Catch:{ all -> 0x01d5 }
        r2 = r14;
        r4 = r13;
        r5 = r17;
    L_0x0166:
        r6 = r16 + 1;
        r16 = r6;
        r14 = r2;
        r13 = r4;
        r17 = r5;
        goto L_0x00c5;
    L_0x0170:
        r5 = 1;
        r5 = new java.lang.String[r5];	 Catch:{ SQLiteException -> 0x0c7b }
        r6 = 0;
        r7 = java.lang.String.valueOf(r32);	 Catch:{ SQLiteException -> 0x0c7b }
        r5[r6] = r7;	 Catch:{ SQLiteException -> 0x0c7b }
        r6 = r5;
        goto L_0x0049;
    L_0x017d:
        r5 = "";
        goto L_0x0052;
    L_0x0182:
        r5 = 0;
        r4 = r3.getString(r5);	 Catch:{ SQLiteException -> 0x0c7b }
        r5 = 1;
        r5 = r3.getString(r5);	 Catch:{ SQLiteException -> 0x0c7b }
        r3.close();	 Catch:{ SQLiteException -> 0x0c7b }
        r13 = r5;
        r11 = r3;
        r12 = r4;
    L_0x0192:
        r3 = "raw_events_metadata";
        r4 = 1;
        r4 = new java.lang.String[r4];	 Catch:{ SQLiteException -> 0x0306, all -> 0x0c77 }
        r5 = 0;
        r6 = "metadata";
        r4[r5] = r6;	 Catch:{ SQLiteException -> 0x0306, all -> 0x0c77 }
        r5 = "app_id = ? and metadata_fingerprint = ?";
        r6 = 2;
        r6 = new java.lang.String[r6];	 Catch:{ SQLiteException -> 0x0306, all -> 0x0c77 }
        r7 = 0;
        r6[r7] = r12;	 Catch:{ SQLiteException -> 0x0306, all -> 0x0c77 }
        r7 = 1;
        r6[r7] = r13;	 Catch:{ SQLiteException -> 0x0306, all -> 0x0c77 }
        r7 = 0;
        r8 = 0;
        r9 = "rowid";
        r10 = "2";
        r11 = r2.query(r3, r4, r5, r6, r7, r8, r9, r10);	 Catch:{ SQLiteException -> 0x0306, all -> 0x0c77 }
        r3 = r11.moveToFirst();	 Catch:{ SQLiteException -> 0x0306, all -> 0x0c77 }
        if (r3 != 0) goto L_0x024c;
    L_0x01bc:
        r2 = r14.zzgf();	 Catch:{ SQLiteException -> 0x0306, all -> 0x0c77 }
        r2 = r2.zzis();	 Catch:{ SQLiteException -> 0x0306, all -> 0x0c77 }
        r3 = "Raw event metadata record is missing. appId";
        r4 = com.google.android.gms.internal.measurement.zzfh.zzbl(r12);	 Catch:{ SQLiteException -> 0x0306, all -> 0x0c77 }
        r2.zzg(r3, r4);	 Catch:{ SQLiteException -> 0x0306, all -> 0x0c77 }
        if (r11 == 0) goto L_0x0086;
    L_0x01d0:
        r11.close();	 Catch:{ all -> 0x01d5 }
        goto L_0x0086;
    L_0x01d5:
        r2 = move-exception;
        r3 = r30.zzje();
        r3.endTransaction();
        throw r2;
    L_0x01de:
        r6 = -1;
        r5 = (r16 > r6 ? 1 : (r16 == r6 ? 0 : -1));
        if (r5 == 0) goto L_0x0232;
    L_0x01e4:
        r5 = 2;
        r5 = new java.lang.String[r5];	 Catch:{ SQLiteException -> 0x0c7b }
        r6 = 0;
        r7 = 0;
        r5[r6] = r7;	 Catch:{ SQLiteException -> 0x0c7b }
        r6 = 1;
        r7 = java.lang.String.valueOf(r16);	 Catch:{ SQLiteException -> 0x0c7b }
        r5[r6] = r7;	 Catch:{ SQLiteException -> 0x0c7b }
        r6 = r5;
    L_0x01f3:
        r8 = -1;
        r5 = (r16 > r8 ? 1 : (r16 == r8 ? 0 : -1));
        if (r5 == 0) goto L_0x023b;
    L_0x01f9:
        r5 = " and rowid <= ?";
    L_0x01fc:
        r7 = java.lang.String.valueOf(r5);	 Catch:{ SQLiteException -> 0x0c7b }
        r7 = r7.length();	 Catch:{ SQLiteException -> 0x0c7b }
        r7 = r7 + 84;
        r8 = new java.lang.StringBuilder;	 Catch:{ SQLiteException -> 0x0c7b }
        r8.<init>(r7);	 Catch:{ SQLiteException -> 0x0c7b }
        r7 = "select metadata_fingerprint from raw_events where app_id = ?";
        r7 = r8.append(r7);	 Catch:{ SQLiteException -> 0x0c7b }
        r5 = r7.append(r5);	 Catch:{ SQLiteException -> 0x0c7b }
        r7 = " order by rowid limit 1;";
        r5 = r5.append(r7);	 Catch:{ SQLiteException -> 0x0c7b }
        r5 = r5.toString();	 Catch:{ SQLiteException -> 0x0c7b }
        r3 = r2.rawQuery(r5, r6);	 Catch:{ SQLiteException -> 0x0c7b }
        r5 = r3.moveToFirst();	 Catch:{ SQLiteException -> 0x0c7b }
        if (r5 != 0) goto L_0x023f;
    L_0x022b:
        if (r3 == 0) goto L_0x0086;
    L_0x022d:
        r3.close();	 Catch:{ all -> 0x01d5 }
        goto L_0x0086;
    L_0x0232:
        r5 = 1;
        r5 = new java.lang.String[r5];	 Catch:{ SQLiteException -> 0x0c7b }
        r6 = 0;
        r7 = 0;
        r5[r6] = r7;	 Catch:{ SQLiteException -> 0x0c7b }
        r6 = r5;
        goto L_0x01f3;
    L_0x023b:
        r5 = "";
        goto L_0x01fc;
    L_0x023f:
        r5 = 0;
        r5 = r3.getString(r5);	 Catch:{ SQLiteException -> 0x0c7b }
        r3.close();	 Catch:{ SQLiteException -> 0x0c7b }
        r13 = r5;
        r11 = r3;
        r12 = r4;
        goto L_0x0192;
    L_0x024c:
        r3 = 0;
        r3 = r11.getBlob(r3);	 Catch:{ SQLiteException -> 0x0306, all -> 0x0c77 }
        r4 = 0;
        r5 = r3.length;	 Catch:{ SQLiteException -> 0x0306, all -> 0x0c77 }
        r3 = com.google.android.gms.internal.measurement.zzabx.zza(r3, r4, r5);	 Catch:{ SQLiteException -> 0x0306, all -> 0x0c77 }
        r4 = new com.google.android.gms.internal.measurement.zzks;	 Catch:{ SQLiteException -> 0x0306, all -> 0x0c77 }
        r4.<init>();	 Catch:{ SQLiteException -> 0x0306, all -> 0x0c77 }
        r4.zzb(r3);	 Catch:{ IOException -> 0x02df }
        r3 = r11.moveToNext();	 Catch:{ SQLiteException -> 0x0306, all -> 0x0c77 }
        if (r3 == 0) goto L_0x0277;
    L_0x0265:
        r3 = r14.zzgf();	 Catch:{ SQLiteException -> 0x0306, all -> 0x0c77 }
        r3 = r3.zziv();	 Catch:{ SQLiteException -> 0x0306, all -> 0x0c77 }
        r5 = "Get multiple raw event metadata records, expected one. appId";
        r6 = com.google.android.gms.internal.measurement.zzfh.zzbl(r12);	 Catch:{ SQLiteException -> 0x0306, all -> 0x0c77 }
        r3.zzg(r5, r6);	 Catch:{ SQLiteException -> 0x0306, all -> 0x0c77 }
    L_0x0277:
        r11.close();	 Catch:{ SQLiteException -> 0x0306, all -> 0x0c77 }
        r0 = r21;
        r0.zzb(r4);	 Catch:{ SQLiteException -> 0x0306, all -> 0x0c77 }
        r4 = -1;
        r3 = (r16 > r4 ? 1 : (r16 == r4 ? 0 : -1));
        if (r3 == 0) goto L_0x02f9;
    L_0x0285:
        r5 = "app_id = ? and metadata_fingerprint = ? and rowid <= ?";
        r3 = 3;
        r6 = new java.lang.String[r3];	 Catch:{ SQLiteException -> 0x0306, all -> 0x0c77 }
        r3 = 0;
        r6[r3] = r12;	 Catch:{ SQLiteException -> 0x0306, all -> 0x0c77 }
        r3 = 1;
        r6[r3] = r13;	 Catch:{ SQLiteException -> 0x0306, all -> 0x0c77 }
        r3 = 2;
        r4 = java.lang.String.valueOf(r16);	 Catch:{ SQLiteException -> 0x0306, all -> 0x0c77 }
        r6[r3] = r4;	 Catch:{ SQLiteException -> 0x0306, all -> 0x0c77 }
    L_0x0298:
        r3 = "raw_events";
        r4 = 4;
        r4 = new java.lang.String[r4];	 Catch:{ SQLiteException -> 0x0306, all -> 0x0c77 }
        r7 = 0;
        r8 = "rowid";
        r4[r7] = r8;	 Catch:{ SQLiteException -> 0x0306, all -> 0x0c77 }
        r7 = 1;
        r8 = "name";
        r4[r7] = r8;	 Catch:{ SQLiteException -> 0x0306, all -> 0x0c77 }
        r7 = 2;
        r8 = "timestamp";
        r4[r7] = r8;	 Catch:{ SQLiteException -> 0x0306, all -> 0x0c77 }
        r7 = 3;
        r8 = "data";
        r4[r7] = r8;	 Catch:{ SQLiteException -> 0x0306, all -> 0x0c77 }
        r7 = 0;
        r8 = 0;
        r9 = "rowid";
        r10 = 0;
        r3 = r2.query(r3, r4, r5, r6, r7, r8, r9, r10);	 Catch:{ SQLiteException -> 0x0306, all -> 0x0c77 }
        r2 = r3.moveToFirst();	 Catch:{ SQLiteException -> 0x0c7e }
        if (r2 != 0) goto L_0x0322;
    L_0x02c6:
        r2 = r14.zzgf();	 Catch:{ SQLiteException -> 0x0c7e }
        r2 = r2.zziv();	 Catch:{ SQLiteException -> 0x0c7e }
        r4 = "Raw event data disappeared while in transaction. appId";
        r5 = com.google.android.gms.internal.measurement.zzfh.zzbl(r12);	 Catch:{ SQLiteException -> 0x0c7e }
        r2.zzg(r4, r5);	 Catch:{ SQLiteException -> 0x0c7e }
        if (r3 == 0) goto L_0x0086;
    L_0x02da:
        r3.close();	 Catch:{ all -> 0x01d5 }
        goto L_0x0086;
    L_0x02df:
        r2 = move-exception;
        r3 = r14.zzgf();	 Catch:{ SQLiteException -> 0x0306, all -> 0x0c77 }
        r3 = r3.zzis();	 Catch:{ SQLiteException -> 0x0306, all -> 0x0c77 }
        r4 = "Data loss. Failed to merge raw event metadata. appId";
        r5 = com.google.android.gms.internal.measurement.zzfh.zzbl(r12);	 Catch:{ SQLiteException -> 0x0306, all -> 0x0c77 }
        r3.zze(r4, r5, r2);	 Catch:{ SQLiteException -> 0x0306, all -> 0x0c77 }
        if (r11 == 0) goto L_0x0086;
    L_0x02f4:
        r11.close();	 Catch:{ all -> 0x01d5 }
        goto L_0x0086;
    L_0x02f9:
        r5 = "app_id = ? and metadata_fingerprint = ?";
        r3 = 2;
        r6 = new java.lang.String[r3];	 Catch:{ SQLiteException -> 0x0306, all -> 0x0c77 }
        r3 = 0;
        r6[r3] = r12;	 Catch:{ SQLiteException -> 0x0306, all -> 0x0c77 }
        r3 = 1;
        r6[r3] = r13;	 Catch:{ SQLiteException -> 0x0306, all -> 0x0c77 }
        goto L_0x0298;
    L_0x0306:
        r2 = move-exception;
        r3 = r11;
        r4 = r12;
    L_0x0309:
        r5 = r14.zzgf();	 Catch:{ all -> 0x037b }
        r5 = r5.zzis();	 Catch:{ all -> 0x037b }
        r6 = "Data loss. Error selecting raw event. appId";
        r4 = com.google.android.gms.internal.measurement.zzfh.zzbl(r4);	 Catch:{ all -> 0x037b }
        r5.zze(r6, r4, r2);	 Catch:{ all -> 0x037b }
        if (r3 == 0) goto L_0x0086;
    L_0x031d:
        r3.close();	 Catch:{ all -> 0x01d5 }
        goto L_0x0086;
    L_0x0322:
        r2 = 0;
        r4 = r3.getLong(r2);	 Catch:{ SQLiteException -> 0x0c7e }
        r2 = 3;
        r2 = r3.getBlob(r2);	 Catch:{ SQLiteException -> 0x0c7e }
        r6 = 0;
        r7 = r2.length;	 Catch:{ SQLiteException -> 0x0c7e }
        r2 = com.google.android.gms.internal.measurement.zzabx.zza(r2, r6, r7);	 Catch:{ SQLiteException -> 0x0c7e }
        r6 = new com.google.android.gms.internal.measurement.zzkp;	 Catch:{ SQLiteException -> 0x0c7e }
        r6.<init>();	 Catch:{ SQLiteException -> 0x0c7e }
        r6.zzb(r2);	 Catch:{ IOException -> 0x035b }
        r2 = 1;
        r2 = r3.getString(r2);	 Catch:{ SQLiteException -> 0x0c7e }
        r6.name = r2;	 Catch:{ SQLiteException -> 0x0c7e }
        r2 = 2;
        r8 = r3.getLong(r2);	 Catch:{ SQLiteException -> 0x0c7e }
        r2 = java.lang.Long.valueOf(r8);	 Catch:{ SQLiteException -> 0x0c7e }
        r6.zzatn = r2;	 Catch:{ SQLiteException -> 0x0c7e }
        r0 = r21;
        r2 = r0.zza(r4, r6);	 Catch:{ SQLiteException -> 0x0c7e }
        if (r2 != 0) goto L_0x036e;
    L_0x0354:
        if (r3 == 0) goto L_0x0086;
    L_0x0356:
        r3.close();	 Catch:{ all -> 0x01d5 }
        goto L_0x0086;
    L_0x035b:
        r2 = move-exception;
        r4 = r14.zzgf();	 Catch:{ SQLiteException -> 0x0c7e }
        r4 = r4.zzis();	 Catch:{ SQLiteException -> 0x0c7e }
        r5 = "Data loss. Failed to merge raw event. appId";
        r6 = com.google.android.gms.internal.measurement.zzfh.zzbl(r12);	 Catch:{ SQLiteException -> 0x0c7e }
        r4.zze(r5, r6, r2);	 Catch:{ SQLiteException -> 0x0c7e }
    L_0x036e:
        r2 = r3.moveToNext();	 Catch:{ SQLiteException -> 0x0c7e }
        if (r2 != 0) goto L_0x0322;
    L_0x0374:
        if (r3 == 0) goto L_0x0086;
    L_0x0376:
        r3.close();	 Catch:{ all -> 0x01d5 }
        goto L_0x0086;
    L_0x037b:
        r2 = move-exception;
    L_0x037c:
        if (r3 == 0) goto L_0x0381;
    L_0x037e:
        r3.close();	 Catch:{ all -> 0x01d5 }
    L_0x0381:
        throw r2;	 Catch:{ all -> 0x01d5 }
    L_0x0382:
        r2 = 0;
        goto L_0x0097;
    L_0x0385:
        r2 = 0;
        goto L_0x013c;
    L_0x0388:
        r2 = r30.zzkv();	 Catch:{ all -> 0x01d5 }
        r0 = r21;
        r3 = r0.zzarh;	 Catch:{ all -> 0x01d5 }
        r3 = r3.zzti;	 Catch:{ all -> 0x01d5 }
        r4 = r12.name;	 Catch:{ all -> 0x01d5 }
        r19 = r2.zzo(r3, r4);	 Catch:{ all -> 0x01d5 }
        if (r19 != 0) goto L_0x03a9;
    L_0x039a:
        r0 = r30;
        r2 = r0.zzacw;	 Catch:{ all -> 0x01d5 }
        r2.zzgc();	 Catch:{ all -> 0x01d5 }
        r2 = r12.name;	 Catch:{ all -> 0x01d5 }
        r2 = com.google.android.gms.internal.measurement.zzkc.zzck(r2);	 Catch:{ all -> 0x01d5 }
        if (r2 == 0) goto L_0x05f4;
    L_0x03a9:
        r4 = 0;
        r3 = 0;
        r2 = r12.zzatm;	 Catch:{ all -> 0x01d5 }
        if (r2 != 0) goto L_0x03b4;
    L_0x03af:
        r2 = 0;
        r2 = new com.google.android.gms.internal.measurement.zzkq[r2];	 Catch:{ all -> 0x01d5 }
        r12.zzatm = r2;	 Catch:{ all -> 0x01d5 }
    L_0x03b4:
        r6 = r12.zzatm;	 Catch:{ all -> 0x01d5 }
        r7 = r6.length;	 Catch:{ all -> 0x01d5 }
        r2 = 0;
        r5 = r2;
    L_0x03b9:
        if (r5 >= r7) goto L_0x03f2;
    L_0x03bb:
        r2 = r6[r5];	 Catch:{ all -> 0x01d5 }
        r8 = "_c";
        r9 = r2.name;	 Catch:{ all -> 0x01d5 }
        r8 = r8.equals(r9);	 Catch:{ all -> 0x01d5 }
        if (r8 == 0) goto L_0x03dc;
    L_0x03c8:
        r8 = 1;
        r4 = java.lang.Long.valueOf(r8);	 Catch:{ all -> 0x01d5 }
        r2.zzatq = r4;	 Catch:{ all -> 0x01d5 }
        r2 = 1;
        r29 = r3;
        r3 = r2;
        r2 = r29;
    L_0x03d6:
        r4 = r5 + 1;
        r5 = r4;
        r4 = r3;
        r3 = r2;
        goto L_0x03b9;
    L_0x03dc:
        r8 = "_r";
        r9 = r2.name;	 Catch:{ all -> 0x01d5 }
        r8 = r8.equals(r9);	 Catch:{ all -> 0x01d5 }
        if (r8 == 0) goto L_0x0c97;
    L_0x03e7:
        r8 = 1;
        r3 = java.lang.Long.valueOf(r8);	 Catch:{ all -> 0x01d5 }
        r2.zzatq = r3;	 Catch:{ all -> 0x01d5 }
        r2 = 1;
        r3 = r4;
        goto L_0x03d6;
    L_0x03f2:
        if (r4 != 0) goto L_0x043c;
    L_0x03f4:
        if (r19 == 0) goto L_0x043c;
    L_0x03f6:
        r0 = r30;
        r2 = r0.zzacw;	 Catch:{ all -> 0x01d5 }
        r2 = r2.zzgf();	 Catch:{ all -> 0x01d5 }
        r2 = r2.zziz();	 Catch:{ all -> 0x01d5 }
        r4 = "Marking event as conversion";
        r0 = r30;
        r5 = r0.zzacw;	 Catch:{ all -> 0x01d5 }
        r5 = r5.zzgb();	 Catch:{ all -> 0x01d5 }
        r6 = r12.name;	 Catch:{ all -> 0x01d5 }
        r5 = r5.zzbi(r6);	 Catch:{ all -> 0x01d5 }
        r2.zzg(r4, r5);	 Catch:{ all -> 0x01d5 }
        r2 = r12.zzatm;	 Catch:{ all -> 0x01d5 }
        r4 = r12.zzatm;	 Catch:{ all -> 0x01d5 }
        r4 = r4.length;	 Catch:{ all -> 0x01d5 }
        r4 = r4 + 1;
        r2 = java.util.Arrays.copyOf(r2, r4);	 Catch:{ all -> 0x01d5 }
        r2 = (com.google.android.gms.internal.measurement.zzkq[]) r2;	 Catch:{ all -> 0x01d5 }
        r4 = new com.google.android.gms.internal.measurement.zzkq;	 Catch:{ all -> 0x01d5 }
        r4.<init>();	 Catch:{ all -> 0x01d5 }
        r5 = "_c";
        r4.name = r5;	 Catch:{ all -> 0x01d5 }
        r6 = 1;
        r5 = java.lang.Long.valueOf(r6);	 Catch:{ all -> 0x01d5 }
        r4.zzatq = r5;	 Catch:{ all -> 0x01d5 }
        r5 = r2.length;	 Catch:{ all -> 0x01d5 }
        r5 = r5 + -1;
        r2[r5] = r4;	 Catch:{ all -> 0x01d5 }
        r12.zzatm = r2;	 Catch:{ all -> 0x01d5 }
    L_0x043c:
        if (r3 != 0) goto L_0x0484;
    L_0x043e:
        r0 = r30;
        r2 = r0.zzacw;	 Catch:{ all -> 0x01d5 }
        r2 = r2.zzgf();	 Catch:{ all -> 0x01d5 }
        r2 = r2.zziz();	 Catch:{ all -> 0x01d5 }
        r3 = "Marking event as real-time";
        r0 = r30;
        r4 = r0.zzacw;	 Catch:{ all -> 0x01d5 }
        r4 = r4.zzgb();	 Catch:{ all -> 0x01d5 }
        r5 = r12.name;	 Catch:{ all -> 0x01d5 }
        r4 = r4.zzbi(r5);	 Catch:{ all -> 0x01d5 }
        r2.zzg(r3, r4);	 Catch:{ all -> 0x01d5 }
        r2 = r12.zzatm;	 Catch:{ all -> 0x01d5 }
        r3 = r12.zzatm;	 Catch:{ all -> 0x01d5 }
        r3 = r3.length;	 Catch:{ all -> 0x01d5 }
        r3 = r3 + 1;
        r2 = java.util.Arrays.copyOf(r2, r3);	 Catch:{ all -> 0x01d5 }
        r2 = (com.google.android.gms.internal.measurement.zzkq[]) r2;	 Catch:{ all -> 0x01d5 }
        r3 = new com.google.android.gms.internal.measurement.zzkq;	 Catch:{ all -> 0x01d5 }
        r3.<init>();	 Catch:{ all -> 0x01d5 }
        r4 = "_r";
        r3.name = r4;	 Catch:{ all -> 0x01d5 }
        r4 = 1;
        r4 = java.lang.Long.valueOf(r4);	 Catch:{ all -> 0x01d5 }
        r3.zzatq = r4;	 Catch:{ all -> 0x01d5 }
        r4 = r2.length;	 Catch:{ all -> 0x01d5 }
        r4 = r4 + -1;
        r2[r4] = r3;	 Catch:{ all -> 0x01d5 }
        r12.zzatm = r2;	 Catch:{ all -> 0x01d5 }
    L_0x0484:
        r2 = 1;
        r3 = r30.zzje();	 Catch:{ all -> 0x01d5 }
        r4 = r30.zzla();	 Catch:{ all -> 0x01d5 }
        r0 = r21;
        r6 = r0.zzarh;	 Catch:{ all -> 0x01d5 }
        r6 = r6.zzti;	 Catch:{ all -> 0x01d5 }
        r7 = 0;
        r8 = 0;
        r9 = 0;
        r10 = 0;
        r11 = 1;
        r3 = r3.zza(r4, r6, r7, r8, r9, r10, r11);	 Catch:{ all -> 0x01d5 }
        r4 = r3.zzafi;	 Catch:{ all -> 0x01d5 }
        r0 = r30;
        r3 = r0.zzacw;	 Catch:{ all -> 0x01d5 }
        r3 = r3.zzgh();	 Catch:{ all -> 0x01d5 }
        r0 = r21;
        r6 = r0.zzarh;	 Catch:{ all -> 0x01d5 }
        r6 = r6.zzti;	 Catch:{ all -> 0x01d5 }
        r3 = r3.zzaq(r6);	 Catch:{ all -> 0x01d5 }
        r6 = (long) r3;	 Catch:{ all -> 0x01d5 }
        r3 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r3 <= 0) goto L_0x0c93;
    L_0x04b5:
        r2 = 0;
    L_0x04b6:
        r3 = r12.zzatm;	 Catch:{ all -> 0x01d5 }
        r3 = r3.length;	 Catch:{ all -> 0x01d5 }
        if (r2 >= r3) goto L_0x04e8;
    L_0x04bb:
        r3 = "_r";
        r4 = r12.zzatm;	 Catch:{ all -> 0x01d5 }
        r4 = r4[r2];	 Catch:{ all -> 0x01d5 }
        r4 = r4.name;	 Catch:{ all -> 0x01d5 }
        r3 = r3.equals(r4);	 Catch:{ all -> 0x01d5 }
        if (r3 == 0) goto L_0x055d;
    L_0x04ca:
        r3 = r12.zzatm;	 Catch:{ all -> 0x01d5 }
        r3 = r3.length;	 Catch:{ all -> 0x01d5 }
        r3 = r3 + -1;
        r3 = new com.google.android.gms.internal.measurement.zzkq[r3];	 Catch:{ all -> 0x01d5 }
        if (r2 <= 0) goto L_0x04da;
    L_0x04d3:
        r4 = r12.zzatm;	 Catch:{ all -> 0x01d5 }
        r5 = 0;
        r6 = 0;
        java.lang.System.arraycopy(r4, r5, r3, r6, r2);	 Catch:{ all -> 0x01d5 }
    L_0x04da:
        r4 = r3.length;	 Catch:{ all -> 0x01d5 }
        if (r2 >= r4) goto L_0x04e6;
    L_0x04dd:
        r4 = r12.zzatm;	 Catch:{ all -> 0x01d5 }
        r5 = r2 + 1;
        r6 = r3.length;	 Catch:{ all -> 0x01d5 }
        r6 = r6 - r2;
        java.lang.System.arraycopy(r4, r5, r3, r2, r6);	 Catch:{ all -> 0x01d5 }
    L_0x04e6:
        r12.zzatm = r3;	 Catch:{ all -> 0x01d5 }
    L_0x04e8:
        r2 = r12.name;	 Catch:{ all -> 0x01d5 }
        r2 = com.google.android.gms.internal.measurement.zzkc.zzcb(r2);	 Catch:{ all -> 0x01d5 }
        if (r2 == 0) goto L_0x05f4;
    L_0x04f0:
        if (r19 == 0) goto L_0x05f4;
    L_0x04f2:
        r3 = r30.zzje();	 Catch:{ all -> 0x01d5 }
        r4 = r30.zzla();	 Catch:{ all -> 0x01d5 }
        r0 = r21;
        r2 = r0.zzarh;	 Catch:{ all -> 0x01d5 }
        r6 = r2.zzti;	 Catch:{ all -> 0x01d5 }
        r7 = 0;
        r8 = 0;
        r9 = 1;
        r10 = 0;
        r11 = 0;
        r2 = r3.zza(r4, r6, r7, r8, r9, r10, r11);	 Catch:{ all -> 0x01d5 }
        r2 = r2.zzafg;	 Catch:{ all -> 0x01d5 }
        r0 = r30;
        r4 = r0.zzacw;	 Catch:{ all -> 0x01d5 }
        r4 = r4.zzgh();	 Catch:{ all -> 0x01d5 }
        r0 = r21;
        r5 = r0.zzarh;	 Catch:{ all -> 0x01d5 }
        r5 = r5.zzti;	 Catch:{ all -> 0x01d5 }
        r6 = com.google.android.gms.internal.measurement.zzey.zzahc;	 Catch:{ all -> 0x01d5 }
        r4 = r4.zzb(r5, r6);	 Catch:{ all -> 0x01d5 }
        r4 = (long) r4;	 Catch:{ all -> 0x01d5 }
        r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r2 <= 0) goto L_0x05f4;
    L_0x0524:
        r0 = r30;
        r2 = r0.zzacw;	 Catch:{ all -> 0x01d5 }
        r2 = r2.zzgf();	 Catch:{ all -> 0x01d5 }
        r2 = r2.zziv();	 Catch:{ all -> 0x01d5 }
        r3 = "Too many conversions. Not logging as conversion. appId";
        r0 = r21;
        r4 = r0.zzarh;	 Catch:{ all -> 0x01d5 }
        r4 = r4.zzti;	 Catch:{ all -> 0x01d5 }
        r4 = com.google.android.gms.internal.measurement.zzfh.zzbl(r4);	 Catch:{ all -> 0x01d5 }
        r2.zzg(r3, r4);	 Catch:{ all -> 0x01d5 }
        r4 = 0;
        r3 = 0;
        r6 = r12.zzatm;	 Catch:{ all -> 0x01d5 }
        r7 = r6.length;	 Catch:{ all -> 0x01d5 }
        r2 = 0;
        r5 = r2;
    L_0x0547:
        if (r5 >= r7) goto L_0x0573;
    L_0x0549:
        r2 = r6[r5];	 Catch:{ all -> 0x01d5 }
        r8 = "_c";
        r9 = r2.name;	 Catch:{ all -> 0x01d5 }
        r8 = r8.equals(r9);	 Catch:{ all -> 0x01d5 }
        if (r8 == 0) goto L_0x0561;
    L_0x0556:
        r3 = r4;
    L_0x0557:
        r4 = r5 + 1;
        r5 = r4;
        r4 = r3;
        r3 = r2;
        goto L_0x0547;
    L_0x055d:
        r2 = r2 + 1;
        goto L_0x04b6;
    L_0x0561:
        r8 = "_err";
        r2 = r2.name;	 Catch:{ all -> 0x01d5 }
        r2 = r8.equals(r2);	 Catch:{ all -> 0x01d5 }
        if (r2 == 0) goto L_0x0c8f;
    L_0x056c:
        r2 = 1;
        r29 = r3;
        r3 = r2;
        r2 = r29;
        goto L_0x0557;
    L_0x0573:
        if (r4 == 0) goto L_0x05c6;
    L_0x0575:
        if (r3 == 0) goto L_0x05c6;
    L_0x0577:
        r2 = r12.zzatm;	 Catch:{ all -> 0x01d5 }
        r4 = 1;
        r4 = new com.google.android.gms.internal.measurement.zzkq[r4];	 Catch:{ all -> 0x01d5 }
        r5 = 0;
        r4[r5] = r3;	 Catch:{ all -> 0x01d5 }
        r2 = com.google.android.gms.common.util.ArrayUtils.removeAll(r2, r4);	 Catch:{ all -> 0x01d5 }
        r2 = (com.google.android.gms.internal.measurement.zzkq[]) r2;	 Catch:{ all -> 0x01d5 }
        r12.zzatm = r2;	 Catch:{ all -> 0x01d5 }
        r5 = r17;
    L_0x0589:
        if (r18 == 0) goto L_0x0c8c;
    L_0x058b:
        r2 = "_e";
        r3 = r12.name;	 Catch:{ all -> 0x01d5 }
        r2 = r2.equals(r3);	 Catch:{ all -> 0x01d5 }
        if (r2 == 0) goto L_0x0c8c;
    L_0x0596:
        r2 = r12.zzatm;	 Catch:{ all -> 0x01d5 }
        if (r2 == 0) goto L_0x059f;
    L_0x059a:
        r2 = r12.zzatm;	 Catch:{ all -> 0x01d5 }
        r2 = r2.length;	 Catch:{ all -> 0x01d5 }
        if (r2 != 0) goto L_0x05f7;
    L_0x059f:
        r0 = r30;
        r2 = r0.zzacw;	 Catch:{ all -> 0x01d5 }
        r2 = r2.zzgf();	 Catch:{ all -> 0x01d5 }
        r2 = r2.zziv();	 Catch:{ all -> 0x01d5 }
        r3 = "Engagement event does not contain any parameters. appId";
        r0 = r21;
        r4 = r0.zzarh;	 Catch:{ all -> 0x01d5 }
        r4 = r4.zzti;	 Catch:{ all -> 0x01d5 }
        r4 = com.google.android.gms.internal.measurement.zzfh.zzbl(r4);	 Catch:{ all -> 0x01d5 }
        r2.zzg(r3, r4);	 Catch:{ all -> 0x01d5 }
        r2 = r14;
    L_0x05bc:
        r0 = r22;
        r6 = r0.zzatu;	 Catch:{ all -> 0x01d5 }
        r4 = r13 + 1;
        r6[r13] = r12;	 Catch:{ all -> 0x01d5 }
        goto L_0x0166;
    L_0x05c6:
        if (r3 == 0) goto L_0x05d8;
    L_0x05c8:
        r2 = "_err";
        r3.name = r2;	 Catch:{ all -> 0x01d5 }
        r4 = 10;
        r2 = java.lang.Long.valueOf(r4);	 Catch:{ all -> 0x01d5 }
        r3.zzatq = r2;	 Catch:{ all -> 0x01d5 }
        r5 = r17;
        goto L_0x0589;
    L_0x05d8:
        r0 = r30;
        r2 = r0.zzacw;	 Catch:{ all -> 0x01d5 }
        r2 = r2.zzgf();	 Catch:{ all -> 0x01d5 }
        r2 = r2.zzis();	 Catch:{ all -> 0x01d5 }
        r3 = "Did not find conversion parameter. appId";
        r0 = r21;
        r4 = r0.zzarh;	 Catch:{ all -> 0x01d5 }
        r4 = r4.zzti;	 Catch:{ all -> 0x01d5 }
        r4 = com.google.android.gms.internal.measurement.zzfh.zzbl(r4);	 Catch:{ all -> 0x01d5 }
        r2.zzg(r3, r4);	 Catch:{ all -> 0x01d5 }
    L_0x05f4:
        r5 = r17;
        goto L_0x0589;
    L_0x05f7:
        r30.zzjc();	 Catch:{ all -> 0x01d5 }
        r2 = "_et";
        r2 = com.google.android.gms.internal.measurement.zzjy.zzb(r12, r2);	 Catch:{ all -> 0x01d5 }
        r2 = (java.lang.Long) r2;	 Catch:{ all -> 0x01d5 }
        if (r2 != 0) goto L_0x0623;
    L_0x0605:
        r0 = r30;
        r2 = r0.zzacw;	 Catch:{ all -> 0x01d5 }
        r2 = r2.zzgf();	 Catch:{ all -> 0x01d5 }
        r2 = r2.zziv();	 Catch:{ all -> 0x01d5 }
        r3 = "Engagement event does not include duration. appId";
        r0 = r21;
        r4 = r0.zzarh;	 Catch:{ all -> 0x01d5 }
        r4 = r4.zzti;	 Catch:{ all -> 0x01d5 }
        r4 = com.google.android.gms.internal.measurement.zzfh.zzbl(r4);	 Catch:{ all -> 0x01d5 }
        r2.zzg(r3, r4);	 Catch:{ all -> 0x01d5 }
        r2 = r14;
        goto L_0x05bc;
    L_0x0623:
        r2 = r2.longValue();	 Catch:{ all -> 0x01d5 }
        r14 = r14 + r2;
        r2 = r14;
        goto L_0x05bc;
    L_0x062a:
        r0 = r21;
        r2 = r0.zzarj;	 Catch:{ all -> 0x01d5 }
        r2 = r2.size();	 Catch:{ all -> 0x01d5 }
        if (r13 >= r2) goto L_0x0642;
    L_0x0634:
        r0 = r22;
        r2 = r0.zzatu;	 Catch:{ all -> 0x01d5 }
        r2 = java.util.Arrays.copyOf(r2, r13);	 Catch:{ all -> 0x01d5 }
        r2 = (com.google.android.gms.internal.measurement.zzkp[]) r2;	 Catch:{ all -> 0x01d5 }
        r0 = r22;
        r0.zzatu = r2;	 Catch:{ all -> 0x01d5 }
    L_0x0642:
        if (r18 == 0) goto L_0x0703;
    L_0x0644:
        r2 = r30.zzje();	 Catch:{ all -> 0x01d5 }
        r0 = r22;
        r3 = r0.zzti;	 Catch:{ all -> 0x01d5 }
        r4 = "_lte";
        r8 = r2.zzh(r3, r4);	 Catch:{ all -> 0x01d5 }
        if (r8 == 0) goto L_0x0659;
    L_0x0655:
        r2 = r8.value;	 Catch:{ all -> 0x01d5 }
        if (r2 != 0) goto L_0x07eb;
    L_0x0659:
        r2 = new com.google.android.gms.internal.measurement.zzkb;	 Catch:{ all -> 0x01d5 }
        r0 = r22;
        r3 = r0.zzti;	 Catch:{ all -> 0x01d5 }
        r4 = "auto";
        r5 = "_lte";
        r0 = r30;
        r6 = r0.zzacw;	 Catch:{ all -> 0x01d5 }
        r6 = r6.zzbt();	 Catch:{ all -> 0x01d5 }
        r6 = r6.currentTimeMillis();	 Catch:{ all -> 0x01d5 }
        r8 = java.lang.Long.valueOf(r14);	 Catch:{ all -> 0x01d5 }
        r2.<init>(r3, r4, r5, r6, r8);	 Catch:{ all -> 0x01d5 }
        r4 = r2;
    L_0x0679:
        r5 = new com.google.android.gms.internal.measurement.zzku;	 Catch:{ all -> 0x01d5 }
        r5.<init>();	 Catch:{ all -> 0x01d5 }
        r2 = "_lte";
        r5.name = r2;	 Catch:{ all -> 0x01d5 }
        r0 = r30;
        r2 = r0.zzacw;	 Catch:{ all -> 0x01d5 }
        r2 = r2.zzbt();	 Catch:{ all -> 0x01d5 }
        r2 = r2.currentTimeMillis();	 Catch:{ all -> 0x01d5 }
        r2 = java.lang.Long.valueOf(r2);	 Catch:{ all -> 0x01d5 }
        r5.zzauz = r2;	 Catch:{ all -> 0x01d5 }
        r2 = r4.value;	 Catch:{ all -> 0x01d5 }
        r2 = (java.lang.Long) r2;	 Catch:{ all -> 0x01d5 }
        r5.zzatq = r2;	 Catch:{ all -> 0x01d5 }
        r2 = 0;
        r3 = 0;
    L_0x069d:
        r0 = r22;
        r6 = r0.zzatv;	 Catch:{ all -> 0x01d5 }
        r6 = r6.length;	 Catch:{ all -> 0x01d5 }
        if (r3 >= r6) goto L_0x06bc;
    L_0x06a4:
        r6 = "_lte";
        r0 = r22;
        r7 = r0.zzatv;	 Catch:{ all -> 0x01d5 }
        r7 = r7[r3];	 Catch:{ all -> 0x01d5 }
        r7 = r7.name;	 Catch:{ all -> 0x01d5 }
        r6 = r6.equals(r7);	 Catch:{ all -> 0x01d5 }
        if (r6 == 0) goto L_0x0816;
    L_0x06b5:
        r0 = r22;
        r2 = r0.zzatv;	 Catch:{ all -> 0x01d5 }
        r2[r3] = r5;	 Catch:{ all -> 0x01d5 }
        r2 = 1;
    L_0x06bc:
        if (r2 != 0) goto L_0x06e2;
    L_0x06be:
        r0 = r22;
        r2 = r0.zzatv;	 Catch:{ all -> 0x01d5 }
        r0 = r22;
        r3 = r0.zzatv;	 Catch:{ all -> 0x01d5 }
        r3 = r3.length;	 Catch:{ all -> 0x01d5 }
        r3 = r3 + 1;
        r2 = java.util.Arrays.copyOf(r2, r3);	 Catch:{ all -> 0x01d5 }
        r2 = (com.google.android.gms.internal.measurement.zzku[]) r2;	 Catch:{ all -> 0x01d5 }
        r0 = r22;
        r0.zzatv = r2;	 Catch:{ all -> 0x01d5 }
        r0 = r22;
        r2 = r0.zzatv;	 Catch:{ all -> 0x01d5 }
        r0 = r21;
        r3 = r0.zzarh;	 Catch:{ all -> 0x01d5 }
        r3 = r3.zzatv;	 Catch:{ all -> 0x01d5 }
        r3 = r3.length;	 Catch:{ all -> 0x01d5 }
        r3 = r3 + -1;
        r2[r3] = r5;	 Catch:{ all -> 0x01d5 }
    L_0x06e2:
        r2 = 0;
        r2 = (r14 > r2 ? 1 : (r14 == r2 ? 0 : -1));
        if (r2 <= 0) goto L_0x0703;
    L_0x06e8:
        r2 = r30.zzje();	 Catch:{ all -> 0x01d5 }
        r2.zza(r4);	 Catch:{ all -> 0x01d5 }
        r0 = r30;
        r2 = r0.zzacw;	 Catch:{ all -> 0x01d5 }
        r2 = r2.zzgf();	 Catch:{ all -> 0x01d5 }
        r2 = r2.zziy();	 Catch:{ all -> 0x01d5 }
        r3 = "Updated lifetime engagement user property with value. Value";
        r4 = r4.value;	 Catch:{ all -> 0x01d5 }
        r2.zzg(r3, r4);	 Catch:{ all -> 0x01d5 }
    L_0x0703:
        r0 = r22;
        r2 = r0.zzti;	 Catch:{ all -> 0x01d5 }
        r0 = r22;
        r3 = r0.zzatv;	 Catch:{ all -> 0x01d5 }
        r0 = r22;
        r4 = r0.zzatu;	 Catch:{ all -> 0x01d5 }
        r0 = r30;
        r2 = r0.zza(r2, r3, r4);	 Catch:{ all -> 0x01d5 }
        r0 = r22;
        r0.zzaum = r2;	 Catch:{ all -> 0x01d5 }
        r0 = r30;
        r2 = r0.zzacw;	 Catch:{ all -> 0x01d5 }
        r2 = r2.zzgh();	 Catch:{ all -> 0x01d5 }
        r0 = r21;
        r3 = r0.zzarh;	 Catch:{ all -> 0x01d5 }
        r3 = r3.zzti;	 Catch:{ all -> 0x01d5 }
        r2 = r2.zzat(r3);	 Catch:{ all -> 0x01d5 }
        if (r2 == 0) goto L_0x0a63;
    L_0x072d:
        r23 = new java.util.HashMap;	 Catch:{ all -> 0x01d5 }
        r23.<init>();	 Catch:{ all -> 0x01d5 }
        r0 = r22;
        r2 = r0.zzatu;	 Catch:{ all -> 0x01d5 }
        r2 = r2.length;	 Catch:{ all -> 0x01d5 }
        r0 = new com.google.android.gms.internal.measurement.zzkp[r2];	 Catch:{ all -> 0x01d5 }
        r24 = r0;
        r18 = 0;
        r0 = r30;
        r2 = r0.zzacw;	 Catch:{ all -> 0x01d5 }
        r2 = r2.zzgc();	 Catch:{ all -> 0x01d5 }
        r25 = r2.zzll();	 Catch:{ all -> 0x01d5 }
        r0 = r22;
        r0 = r0.zzatu;	 Catch:{ all -> 0x01d5 }
        r26 = r0;
        r0 = r26;
        r0 = r0.length;	 Catch:{ all -> 0x01d5 }
        r27 = r0;
        r2 = 0;
        r19 = r2;
    L_0x0757:
        r0 = r19;
        r1 = r27;
        if (r0 >= r1) goto L_0x0a2a;
    L_0x075d:
        r28 = r26[r19];	 Catch:{ all -> 0x01d5 }
        r0 = r28;
        r2 = r0.name;	 Catch:{ all -> 0x01d5 }
        r3 = "_ep";
        r2 = r2.equals(r3);	 Catch:{ all -> 0x01d5 }
        if (r2 == 0) goto L_0x081a;
    L_0x076c:
        r30.zzjc();	 Catch:{ all -> 0x01d5 }
        r2 = "_en";
        r0 = r28;
        r2 = com.google.android.gms.internal.measurement.zzjy.zzb(r0, r2);	 Catch:{ all -> 0x01d5 }
        r2 = (java.lang.String) r2;	 Catch:{ all -> 0x01d5 }
        r0 = r23;
        r3 = r0.get(r2);	 Catch:{ all -> 0x01d5 }
        r3 = (com.google.android.gms.internal.measurement.zzes) r3;	 Catch:{ all -> 0x01d5 }
        if (r3 != 0) goto L_0x0797;
    L_0x0784:
        r3 = r30.zzje();	 Catch:{ all -> 0x01d5 }
        r0 = r21;
        r4 = r0.zzarh;	 Catch:{ all -> 0x01d5 }
        r4 = r4.zzti;	 Catch:{ all -> 0x01d5 }
        r3 = r3.zzf(r4, r2);	 Catch:{ all -> 0x01d5 }
        r0 = r23;
        r0.put(r2, r3);	 Catch:{ all -> 0x01d5 }
    L_0x0797:
        r2 = r3.zzafw;	 Catch:{ all -> 0x01d5 }
        if (r2 != 0) goto L_0x0a26;
    L_0x079b:
        r2 = r3.zzafx;	 Catch:{ all -> 0x01d5 }
        r4 = r2.longValue();	 Catch:{ all -> 0x01d5 }
        r6 = 1;
        r2 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r2 <= 0) goto L_0x07bb;
    L_0x07a7:
        r30.zzjc();	 Catch:{ all -> 0x01d5 }
        r0 = r28;
        r2 = r0.zzatm;	 Catch:{ all -> 0x01d5 }
        r4 = "_sr";
        r5 = r3.zzafx;	 Catch:{ all -> 0x01d5 }
        r2 = com.google.android.gms.internal.measurement.zzjy.zza(r2, r4, r5);	 Catch:{ all -> 0x01d5 }
        r0 = r28;
        r0.zzatm = r2;	 Catch:{ all -> 0x01d5 }
    L_0x07bb:
        r2 = r3.zzafy;	 Catch:{ all -> 0x01d5 }
        if (r2 == 0) goto L_0x07df;
    L_0x07bf:
        r2 = r3.zzafy;	 Catch:{ all -> 0x01d5 }
        r2 = r2.booleanValue();	 Catch:{ all -> 0x01d5 }
        if (r2 == 0) goto L_0x07df;
    L_0x07c7:
        r30.zzjc();	 Catch:{ all -> 0x01d5 }
        r0 = r28;
        r2 = r0.zzatm;	 Catch:{ all -> 0x01d5 }
        r3 = "_efs";
        r4 = 1;
        r4 = java.lang.Long.valueOf(r4);	 Catch:{ all -> 0x01d5 }
        r2 = com.google.android.gms.internal.measurement.zzjy.zza(r2, r3, r4);	 Catch:{ all -> 0x01d5 }
        r0 = r28;
        r0.zzatm = r2;	 Catch:{ all -> 0x01d5 }
    L_0x07df:
        r2 = r18 + 1;
        r24[r18] = r28;	 Catch:{ all -> 0x01d5 }
    L_0x07e3:
        r3 = r19 + 1;
        r19 = r3;
        r18 = r2;
        goto L_0x0757;
    L_0x07eb:
        r2 = new com.google.android.gms.internal.measurement.zzkb;	 Catch:{ all -> 0x01d5 }
        r0 = r22;
        r3 = r0.zzti;	 Catch:{ all -> 0x01d5 }
        r4 = "auto";
        r5 = "_lte";
        r0 = r30;
        r6 = r0.zzacw;	 Catch:{ all -> 0x01d5 }
        r6 = r6.zzbt();	 Catch:{ all -> 0x01d5 }
        r6 = r6.currentTimeMillis();	 Catch:{ all -> 0x01d5 }
        r8 = r8.value;	 Catch:{ all -> 0x01d5 }
        r8 = (java.lang.Long) r8;	 Catch:{ all -> 0x01d5 }
        r8 = r8.longValue();	 Catch:{ all -> 0x01d5 }
        r8 = r8 + r14;
        r8 = java.lang.Long.valueOf(r8);	 Catch:{ all -> 0x01d5 }
        r2.<init>(r3, r4, r5, r6, r8);	 Catch:{ all -> 0x01d5 }
        r4 = r2;
        goto L_0x0679;
    L_0x0816:
        r3 = r3 + 1;
        goto L_0x069d;
    L_0x081a:
        r2 = 1;
        r4 = "_dbg";
        r6 = 1;
        r5 = java.lang.Long.valueOf(r6);	 Catch:{ all -> 0x01d5 }
        r3 = android.text.TextUtils.isEmpty(r4);	 Catch:{ all -> 0x01d5 }
        if (r3 != 0) goto L_0x082c;
    L_0x082a:
        if (r5 != 0) goto L_0x0864;
    L_0x082c:
        r3 = 0;
    L_0x082d:
        if (r3 != 0) goto L_0x0c88;
    L_0x082f:
        r2 = r30.zzkv();	 Catch:{ all -> 0x01d5 }
        r0 = r21;
        r3 = r0.zzarh;	 Catch:{ all -> 0x01d5 }
        r3 = r3.zzti;	 Catch:{ all -> 0x01d5 }
        r0 = r28;
        r4 = r0.name;	 Catch:{ all -> 0x01d5 }
        r2 = r2.zzp(r3, r4);	 Catch:{ all -> 0x01d5 }
        r20 = r2;
    L_0x0843:
        if (r20 > 0) goto L_0x08a3;
    L_0x0845:
        r0 = r30;
        r2 = r0.zzacw;	 Catch:{ all -> 0x01d5 }
        r2 = r2.zzgf();	 Catch:{ all -> 0x01d5 }
        r2 = r2.zziv();	 Catch:{ all -> 0x01d5 }
        r3 = "Sample rate must be positive. event, rate";
        r0 = r28;
        r4 = r0.name;	 Catch:{ all -> 0x01d5 }
        r5 = java.lang.Integer.valueOf(r20);	 Catch:{ all -> 0x01d5 }
        r2.zze(r3, r4, r5);	 Catch:{ all -> 0x01d5 }
        r2 = r18 + 1;
        r24[r18] = r28;	 Catch:{ all -> 0x01d5 }
        goto L_0x07e3;
    L_0x0864:
        r0 = r28;
        r6 = r0.zzatm;	 Catch:{ all -> 0x01d5 }
        r7 = r6.length;	 Catch:{ all -> 0x01d5 }
        r3 = 0;
    L_0x086a:
        if (r3 >= r7) goto L_0x08a1;
    L_0x086c:
        r8 = r6[r3];	 Catch:{ all -> 0x01d5 }
        r9 = r8.name;	 Catch:{ all -> 0x01d5 }
        r9 = r4.equals(r9);	 Catch:{ all -> 0x01d5 }
        if (r9 == 0) goto L_0x089e;
    L_0x0876:
        r3 = r5 instanceof java.lang.Long;	 Catch:{ all -> 0x01d5 }
        if (r3 == 0) goto L_0x0882;
    L_0x087a:
        r3 = r8.zzatq;	 Catch:{ all -> 0x01d5 }
        r3 = r5.equals(r3);	 Catch:{ all -> 0x01d5 }
        if (r3 != 0) goto L_0x089a;
    L_0x0882:
        r3 = r5 instanceof java.lang.String;	 Catch:{ all -> 0x01d5 }
        if (r3 == 0) goto L_0x088e;
    L_0x0886:
        r3 = r8.zzajo;	 Catch:{ all -> 0x01d5 }
        r3 = r5.equals(r3);	 Catch:{ all -> 0x01d5 }
        if (r3 != 0) goto L_0x089a;
    L_0x088e:
        r3 = r5 instanceof java.lang.Double;	 Catch:{ all -> 0x01d5 }
        if (r3 == 0) goto L_0x089c;
    L_0x0892:
        r3 = r8.zzaro;	 Catch:{ all -> 0x01d5 }
        r3 = r5.equals(r3);	 Catch:{ all -> 0x01d5 }
        if (r3 == 0) goto L_0x089c;
    L_0x089a:
        r3 = 1;
        goto L_0x082d;
    L_0x089c:
        r3 = 0;
        goto L_0x082d;
    L_0x089e:
        r3 = r3 + 1;
        goto L_0x086a;
    L_0x08a1:
        r3 = 0;
        goto L_0x082d;
    L_0x08a3:
        r0 = r28;
        r2 = r0.name;	 Catch:{ all -> 0x01d5 }
        r0 = r23;
        r2 = r0.get(r2);	 Catch:{ all -> 0x01d5 }
        r2 = (com.google.android.gms.internal.measurement.zzes) r2;	 Catch:{ all -> 0x01d5 }
        if (r2 != 0) goto L_0x0c85;
    L_0x08b1:
        r2 = r30.zzje();	 Catch:{ all -> 0x01d5 }
        r0 = r21;
        r3 = r0.zzarh;	 Catch:{ all -> 0x01d5 }
        r3 = r3.zzti;	 Catch:{ all -> 0x01d5 }
        r0 = r28;
        r4 = r0.name;	 Catch:{ all -> 0x01d5 }
        r3 = r2.zzf(r3, r4);	 Catch:{ all -> 0x01d5 }
        if (r3 != 0) goto L_0x0902;
    L_0x08c5:
        r0 = r30;
        r2 = r0.zzacw;	 Catch:{ all -> 0x01d5 }
        r2 = r2.zzgf();	 Catch:{ all -> 0x01d5 }
        r2 = r2.zziv();	 Catch:{ all -> 0x01d5 }
        r3 = "Event being bundled has no eventAggregate. appId, eventName";
        r0 = r21;
        r4 = r0.zzarh;	 Catch:{ all -> 0x01d5 }
        r4 = r4.zzti;	 Catch:{ all -> 0x01d5 }
        r0 = r28;
        r5 = r0.name;	 Catch:{ all -> 0x01d5 }
        r2.zze(r3, r4, r5);	 Catch:{ all -> 0x01d5 }
        r3 = new com.google.android.gms.internal.measurement.zzes;	 Catch:{ all -> 0x01d5 }
        r0 = r21;
        r2 = r0.zzarh;	 Catch:{ all -> 0x01d5 }
        r4 = r2.zzti;	 Catch:{ all -> 0x01d5 }
        r0 = r28;
        r5 = r0.name;	 Catch:{ all -> 0x01d5 }
        r6 = 1;
        r8 = 1;
        r0 = r28;
        r2 = r0.zzatn;	 Catch:{ all -> 0x01d5 }
        r10 = r2.longValue();	 Catch:{ all -> 0x01d5 }
        r12 = 0;
        r14 = 0;
        r15 = 0;
        r16 = 0;
        r3.<init>(r4, r5, r6, r8, r10, r12, r14, r15, r16);	 Catch:{ all -> 0x01d5 }
    L_0x0902:
        r30.zzjc();	 Catch:{ all -> 0x01d5 }
        r2 = "_eid";
        r0 = r28;
        r2 = com.google.android.gms.internal.measurement.zzjy.zzb(r0, r2);	 Catch:{ all -> 0x01d5 }
        r2 = (java.lang.Long) r2;	 Catch:{ all -> 0x01d5 }
        if (r2 == 0) goto L_0x0944;
    L_0x0912:
        r4 = 1;
    L_0x0913:
        r4 = java.lang.Boolean.valueOf(r4);	 Catch:{ all -> 0x01d5 }
        r5 = 1;
        r0 = r20;
        if (r0 != r5) goto L_0x0946;
    L_0x091c:
        r2 = r18 + 1;
        r24[r18] = r28;	 Catch:{ all -> 0x01d5 }
        r4 = r4.booleanValue();	 Catch:{ all -> 0x01d5 }
        if (r4 == 0) goto L_0x07e3;
    L_0x0926:
        r4 = r3.zzafw;	 Catch:{ all -> 0x01d5 }
        if (r4 != 0) goto L_0x0932;
    L_0x092a:
        r4 = r3.zzafx;	 Catch:{ all -> 0x01d5 }
        if (r4 != 0) goto L_0x0932;
    L_0x092e:
        r4 = r3.zzafy;	 Catch:{ all -> 0x01d5 }
        if (r4 == 0) goto L_0x07e3;
    L_0x0932:
        r4 = 0;
        r5 = 0;
        r6 = 0;
        r3 = r3.zza(r4, r5, r6);	 Catch:{ all -> 0x01d5 }
        r0 = r28;
        r4 = r0.name;	 Catch:{ all -> 0x01d5 }
        r0 = r23;
        r0.put(r4, r3);	 Catch:{ all -> 0x01d5 }
        goto L_0x07e3;
    L_0x0944:
        r4 = 0;
        goto L_0x0913;
    L_0x0946:
        r0 = r25;
        r1 = r20;
        r5 = r0.nextInt(r1);	 Catch:{ all -> 0x01d5 }
        if (r5 != 0) goto L_0x0997;
    L_0x0950:
        r30.zzjc();	 Catch:{ all -> 0x01d5 }
        r0 = r28;
        r2 = r0.zzatm;	 Catch:{ all -> 0x01d5 }
        r5 = "_sr";
        r0 = r20;
        r6 = (long) r0;	 Catch:{ all -> 0x01d5 }
        r6 = java.lang.Long.valueOf(r6);	 Catch:{ all -> 0x01d5 }
        r2 = com.google.android.gms.internal.measurement.zzjy.zza(r2, r5, r6);	 Catch:{ all -> 0x01d5 }
        r0 = r28;
        r0.zzatm = r2;	 Catch:{ all -> 0x01d5 }
        r2 = r18 + 1;
        r24[r18] = r28;	 Catch:{ all -> 0x01d5 }
        r4 = r4.booleanValue();	 Catch:{ all -> 0x01d5 }
        if (r4 == 0) goto L_0x0980;
    L_0x0973:
        r4 = 0;
        r0 = r20;
        r6 = (long) r0;	 Catch:{ all -> 0x01d5 }
        r5 = java.lang.Long.valueOf(r6);	 Catch:{ all -> 0x01d5 }
        r6 = 0;
        r3 = r3.zza(r4, r5, r6);	 Catch:{ all -> 0x01d5 }
    L_0x0980:
        r0 = r28;
        r4 = r0.name;	 Catch:{ all -> 0x01d5 }
        r0 = r28;
        r5 = r0.zzatn;	 Catch:{ all -> 0x01d5 }
        r6 = r5.longValue();	 Catch:{ all -> 0x01d5 }
        r3 = r3.zzad(r6);	 Catch:{ all -> 0x01d5 }
        r0 = r23;
        r0.put(r4, r3);	 Catch:{ all -> 0x01d5 }
        goto L_0x07e3;
    L_0x0997:
        r6 = r3.zzafv;	 Catch:{ all -> 0x01d5 }
        r0 = r28;
        r5 = r0.zzatn;	 Catch:{ all -> 0x01d5 }
        r8 = r5.longValue();	 Catch:{ all -> 0x01d5 }
        r6 = r8 - r6;
        r6 = java.lang.Math.abs(r6);	 Catch:{ all -> 0x01d5 }
        r8 = 86400000; // 0x5265c00 float:7.82218E-36 double:4.2687272E-316;
        r5 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1));
        if (r5 < 0) goto L_0x0a11;
    L_0x09ae:
        r30.zzjc();	 Catch:{ all -> 0x01d5 }
        r0 = r28;
        r2 = r0.zzatm;	 Catch:{ all -> 0x01d5 }
        r5 = "_efs";
        r6 = 1;
        r6 = java.lang.Long.valueOf(r6);	 Catch:{ all -> 0x01d5 }
        r2 = com.google.android.gms.internal.measurement.zzjy.zza(r2, r5, r6);	 Catch:{ all -> 0x01d5 }
        r0 = r28;
        r0.zzatm = r2;	 Catch:{ all -> 0x01d5 }
        r30.zzjc();	 Catch:{ all -> 0x01d5 }
        r0 = r28;
        r2 = r0.zzatm;	 Catch:{ all -> 0x01d5 }
        r5 = "_sr";
        r0 = r20;
        r6 = (long) r0;	 Catch:{ all -> 0x01d5 }
        r6 = java.lang.Long.valueOf(r6);	 Catch:{ all -> 0x01d5 }
        r2 = com.google.android.gms.internal.measurement.zzjy.zza(r2, r5, r6);	 Catch:{ all -> 0x01d5 }
        r0 = r28;
        r0.zzatm = r2;	 Catch:{ all -> 0x01d5 }
        r2 = r18 + 1;
        r24[r18] = r28;	 Catch:{ all -> 0x01d5 }
        r4 = r4.booleanValue();	 Catch:{ all -> 0x01d5 }
        if (r4 == 0) goto L_0x09fa;
    L_0x09e9:
        r4 = 0;
        r0 = r20;
        r6 = (long) r0;	 Catch:{ all -> 0x01d5 }
        r5 = java.lang.Long.valueOf(r6);	 Catch:{ all -> 0x01d5 }
        r6 = 1;
        r6 = java.lang.Boolean.valueOf(r6);	 Catch:{ all -> 0x01d5 }
        r3 = r3.zza(r4, r5, r6);	 Catch:{ all -> 0x01d5 }
    L_0x09fa:
        r0 = r28;
        r4 = r0.name;	 Catch:{ all -> 0x01d5 }
        r0 = r28;
        r5 = r0.zzatn;	 Catch:{ all -> 0x01d5 }
        r6 = r5.longValue();	 Catch:{ all -> 0x01d5 }
        r3 = r3.zzad(r6);	 Catch:{ all -> 0x01d5 }
        r0 = r23;
        r0.put(r4, r3);	 Catch:{ all -> 0x01d5 }
        goto L_0x07e3;
    L_0x0a11:
        r4 = r4.booleanValue();	 Catch:{ all -> 0x01d5 }
        if (r4 == 0) goto L_0x0a26;
    L_0x0a17:
        r0 = r28;
        r4 = r0.name;	 Catch:{ all -> 0x01d5 }
        r5 = 0;
        r6 = 0;
        r2 = r3.zza(r2, r5, r6);	 Catch:{ all -> 0x01d5 }
        r0 = r23;
        r0.put(r4, r2);	 Catch:{ all -> 0x01d5 }
    L_0x0a26:
        r2 = r18;
        goto L_0x07e3;
    L_0x0a2a:
        r0 = r22;
        r2 = r0.zzatu;	 Catch:{ all -> 0x01d5 }
        r2 = r2.length;	 Catch:{ all -> 0x01d5 }
        r0 = r18;
        if (r0 >= r2) goto L_0x0a41;
    L_0x0a33:
        r0 = r24;
        r1 = r18;
        r2 = java.util.Arrays.copyOf(r0, r1);	 Catch:{ all -> 0x01d5 }
        r2 = (com.google.android.gms.internal.measurement.zzkp[]) r2;	 Catch:{ all -> 0x01d5 }
        r0 = r22;
        r0.zzatu = r2;	 Catch:{ all -> 0x01d5 }
    L_0x0a41:
        r2 = r23.entrySet();	 Catch:{ all -> 0x01d5 }
        r3 = r2.iterator();	 Catch:{ all -> 0x01d5 }
    L_0x0a49:
        r2 = r3.hasNext();	 Catch:{ all -> 0x01d5 }
        if (r2 == 0) goto L_0x0a63;
    L_0x0a4f:
        r2 = r3.next();	 Catch:{ all -> 0x01d5 }
        r2 = (java.util.Map.Entry) r2;	 Catch:{ all -> 0x01d5 }
        r4 = r30.zzje();	 Catch:{ all -> 0x01d5 }
        r2 = r2.getValue();	 Catch:{ all -> 0x01d5 }
        r2 = (com.google.android.gms.internal.measurement.zzes) r2;	 Catch:{ all -> 0x01d5 }
        r4.zza(r2);	 Catch:{ all -> 0x01d5 }
        goto L_0x0a49;
    L_0x0a63:
        r2 = 9223372036854775807; // 0x7fffffffffffffff float:NaN double:NaN;
        r2 = java.lang.Long.valueOf(r2);	 Catch:{ all -> 0x01d5 }
        r0 = r22;
        r0.zzatx = r2;	 Catch:{ all -> 0x01d5 }
        r2 = -9223372036854775808;
        r2 = java.lang.Long.valueOf(r2);	 Catch:{ all -> 0x01d5 }
        r0 = r22;
        r0.zzaty = r2;	 Catch:{ all -> 0x01d5 }
        r2 = 0;
    L_0x0a7b:
        r0 = r22;
        r3 = r0.zzatu;	 Catch:{ all -> 0x01d5 }
        r3 = r3.length;	 Catch:{ all -> 0x01d5 }
        if (r2 >= r3) goto L_0x0abb;
    L_0x0a82:
        r0 = r22;
        r3 = r0.zzatu;	 Catch:{ all -> 0x01d5 }
        r3 = r3[r2];	 Catch:{ all -> 0x01d5 }
        r4 = r3.zzatn;	 Catch:{ all -> 0x01d5 }
        r4 = r4.longValue();	 Catch:{ all -> 0x01d5 }
        r0 = r22;
        r6 = r0.zzatx;	 Catch:{ all -> 0x01d5 }
        r6 = r6.longValue();	 Catch:{ all -> 0x01d5 }
        r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r4 >= 0) goto L_0x0aa0;
    L_0x0a9a:
        r4 = r3.zzatn;	 Catch:{ all -> 0x01d5 }
        r0 = r22;
        r0.zzatx = r4;	 Catch:{ all -> 0x01d5 }
    L_0x0aa0:
        r4 = r3.zzatn;	 Catch:{ all -> 0x01d5 }
        r4 = r4.longValue();	 Catch:{ all -> 0x01d5 }
        r0 = r22;
        r6 = r0.zzaty;	 Catch:{ all -> 0x01d5 }
        r6 = r6.longValue();	 Catch:{ all -> 0x01d5 }
        r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r4 <= 0) goto L_0x0ab8;
    L_0x0ab2:
        r3 = r3.zzatn;	 Catch:{ all -> 0x01d5 }
        r0 = r22;
        r0.zzaty = r3;	 Catch:{ all -> 0x01d5 }
    L_0x0ab8:
        r2 = r2 + 1;
        goto L_0x0a7b;
    L_0x0abb:
        r0 = r21;
        r2 = r0.zzarh;	 Catch:{ all -> 0x01d5 }
        r6 = r2.zzti;	 Catch:{ all -> 0x01d5 }
        r2 = r30.zzje();	 Catch:{ all -> 0x01d5 }
        r7 = r2.zzbb(r6);	 Catch:{ all -> 0x01d5 }
        if (r7 != 0) goto L_0x0b64;
    L_0x0acb:
        r0 = r30;
        r2 = r0.zzacw;	 Catch:{ all -> 0x01d5 }
        r2 = r2.zzgf();	 Catch:{ all -> 0x01d5 }
        r2 = r2.zzis();	 Catch:{ all -> 0x01d5 }
        r3 = "Bundling raw events w/o app info. appId";
        r0 = r21;
        r4 = r0.zzarh;	 Catch:{ all -> 0x01d5 }
        r4 = r4.zzti;	 Catch:{ all -> 0x01d5 }
        r4 = com.google.android.gms.internal.measurement.zzfh.zzbl(r4);	 Catch:{ all -> 0x01d5 }
        r2.zzg(r3, r4);	 Catch:{ all -> 0x01d5 }
    L_0x0ae7:
        r0 = r22;
        r2 = r0.zzatu;	 Catch:{ all -> 0x01d5 }
        r2 = r2.length;	 Catch:{ all -> 0x01d5 }
        if (r2 <= 0) goto L_0x0b2a;
    L_0x0aee:
        r0 = r30;
        r2 = r0.zzacw;	 Catch:{ all -> 0x01d5 }
        r2.zzgi();	 Catch:{ all -> 0x01d5 }
        r2 = r30.zzkv();	 Catch:{ all -> 0x01d5 }
        r0 = r21;
        r3 = r0.zzarh;	 Catch:{ all -> 0x01d5 }
        r3 = r3.zzti;	 Catch:{ all -> 0x01d5 }
        r2 = r2.zzbt(r3);	 Catch:{ all -> 0x01d5 }
        if (r2 == 0) goto L_0x0b09;
    L_0x0b05:
        r3 = r2.zzatb;	 Catch:{ all -> 0x01d5 }
        if (r3 != 0) goto L_0x0bee;
    L_0x0b09:
        r0 = r21;
        r2 = r0.zzarh;	 Catch:{ all -> 0x01d5 }
        r2 = r2.zzadm;	 Catch:{ all -> 0x01d5 }
        r2 = android.text.TextUtils.isEmpty(r2);	 Catch:{ all -> 0x01d5 }
        if (r2 == 0) goto L_0x0bd0;
    L_0x0b15:
        r2 = -1;
        r2 = java.lang.Long.valueOf(r2);	 Catch:{ all -> 0x01d5 }
        r0 = r22;
        r0.zzaur = r2;	 Catch:{ all -> 0x01d5 }
    L_0x0b1f:
        r2 = r30.zzje();	 Catch:{ all -> 0x01d5 }
        r0 = r22;
        r1 = r17;
        r2.zza(r0, r1);	 Catch:{ all -> 0x01d5 }
    L_0x0b2a:
        r4 = r30.zzje();	 Catch:{ all -> 0x01d5 }
        r0 = r21;
        r5 = r0.zzari;	 Catch:{ all -> 0x01d5 }
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r5);	 Catch:{ all -> 0x01d5 }
        r4.zzab();	 Catch:{ all -> 0x01d5 }
        r4.zzch();	 Catch:{ all -> 0x01d5 }
        r7 = new java.lang.StringBuilder;	 Catch:{ all -> 0x01d5 }
        r2 = "rowid in (";
        r7.<init>(r2);	 Catch:{ all -> 0x01d5 }
        r2 = 0;
        r3 = r2;
    L_0x0b45:
        r2 = r5.size();	 Catch:{ all -> 0x01d5 }
        if (r3 >= r2) goto L_0x0bf6;
    L_0x0b4b:
        if (r3 == 0) goto L_0x0b53;
    L_0x0b4d:
        r2 = ",";
        r7.append(r2);	 Catch:{ all -> 0x01d5 }
    L_0x0b53:
        r2 = r5.get(r3);	 Catch:{ all -> 0x01d5 }
        r2 = (java.lang.Long) r2;	 Catch:{ all -> 0x01d5 }
        r8 = r2.longValue();	 Catch:{ all -> 0x01d5 }
        r7.append(r8);	 Catch:{ all -> 0x01d5 }
        r2 = r3 + 1;
        r3 = r2;
        goto L_0x0b45;
    L_0x0b64:
        r0 = r22;
        r2 = r0.zzatu;	 Catch:{ all -> 0x01d5 }
        r2 = r2.length;	 Catch:{ all -> 0x01d5 }
        if (r2 <= 0) goto L_0x0ae7;
    L_0x0b6b:
        r2 = r7.zzgn();	 Catch:{ all -> 0x01d5 }
        r4 = 0;
        r4 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r4 == 0) goto L_0x0bcc;
    L_0x0b75:
        r4 = java.lang.Long.valueOf(r2);	 Catch:{ all -> 0x01d5 }
    L_0x0b79:
        r0 = r22;
        r0.zzaua = r4;	 Catch:{ all -> 0x01d5 }
        r4 = r7.zzgm();	 Catch:{ all -> 0x01d5 }
        r8 = 0;
        r8 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1));
        if (r8 != 0) goto L_0x0c82;
    L_0x0b87:
        r4 = 0;
        r4 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r4 == 0) goto L_0x0bce;
    L_0x0b8d:
        r2 = java.lang.Long.valueOf(r2);	 Catch:{ all -> 0x01d5 }
    L_0x0b91:
        r0 = r22;
        r0.zzatz = r2;	 Catch:{ all -> 0x01d5 }
        r7.zzgv();	 Catch:{ all -> 0x01d5 }
        r2 = r7.zzgs();	 Catch:{ all -> 0x01d5 }
        r2 = (int) r2;	 Catch:{ all -> 0x01d5 }
        r2 = java.lang.Integer.valueOf(r2);	 Catch:{ all -> 0x01d5 }
        r0 = r22;
        r0.zzauk = r2;	 Catch:{ all -> 0x01d5 }
        r0 = r22;
        r2 = r0.zzatx;	 Catch:{ all -> 0x01d5 }
        r2 = r2.longValue();	 Catch:{ all -> 0x01d5 }
        r7.zzm(r2);	 Catch:{ all -> 0x01d5 }
        r0 = r22;
        r2 = r0.zzaty;	 Catch:{ all -> 0x01d5 }
        r2 = r2.longValue();	 Catch:{ all -> 0x01d5 }
        r7.zzn(r2);	 Catch:{ all -> 0x01d5 }
        r2 = r7.zzhd();	 Catch:{ all -> 0x01d5 }
        r0 = r22;
        r0.zzaek = r2;	 Catch:{ all -> 0x01d5 }
        r2 = r30.zzje();	 Catch:{ all -> 0x01d5 }
        r2.zza(r7);	 Catch:{ all -> 0x01d5 }
        goto L_0x0ae7;
    L_0x0bcc:
        r4 = 0;
        goto L_0x0b79;
    L_0x0bce:
        r2 = 0;
        goto L_0x0b91;
    L_0x0bd0:
        r0 = r30;
        r2 = r0.zzacw;	 Catch:{ all -> 0x01d5 }
        r2 = r2.zzgf();	 Catch:{ all -> 0x01d5 }
        r2 = r2.zziv();	 Catch:{ all -> 0x01d5 }
        r3 = "Did not find measurement config or missing version info. appId";
        r0 = r21;
        r4 = r0.zzarh;	 Catch:{ all -> 0x01d5 }
        r4 = r4.zzti;	 Catch:{ all -> 0x01d5 }
        r4 = com.google.android.gms.internal.measurement.zzfh.zzbl(r4);	 Catch:{ all -> 0x01d5 }
        r2.zzg(r3, r4);	 Catch:{ all -> 0x01d5 }
        goto L_0x0b1f;
    L_0x0bee:
        r2 = r2.zzatb;	 Catch:{ all -> 0x01d5 }
        r0 = r22;
        r0.zzaur = r2;	 Catch:{ all -> 0x01d5 }
        goto L_0x0b1f;
    L_0x0bf6:
        r2 = ")";
        r7.append(r2);	 Catch:{ all -> 0x01d5 }
        r2 = r4.getWritableDatabase();	 Catch:{ all -> 0x01d5 }
        r3 = "raw_events";
        r7 = r7.toString();	 Catch:{ all -> 0x01d5 }
        r8 = 0;
        r2 = r2.delete(r3, r7, r8);	 Catch:{ all -> 0x01d5 }
        r3 = r5.size();	 Catch:{ all -> 0x01d5 }
        if (r2 == r3) goto L_0x0c2c;
    L_0x0c12:
        r3 = r4.zzgf();	 Catch:{ all -> 0x01d5 }
        r3 = r3.zzis();	 Catch:{ all -> 0x01d5 }
        r4 = "Deleted fewer rows from raw events table than expected";
        r2 = java.lang.Integer.valueOf(r2);	 Catch:{ all -> 0x01d5 }
        r5 = r5.size();	 Catch:{ all -> 0x01d5 }
        r5 = java.lang.Integer.valueOf(r5);	 Catch:{ all -> 0x01d5 }
        r3.zze(r4, r2, r5);	 Catch:{ all -> 0x01d5 }
    L_0x0c2c:
        r3 = r30.zzje();	 Catch:{ all -> 0x01d5 }
        r2 = r3.getWritableDatabase();	 Catch:{ all -> 0x01d5 }
        r4 = "delete from raw_events_metadata where app_id=? and metadata_fingerprint not in (select distinct metadata_fingerprint from raw_events where app_id=?)";
        r5 = 2;
        r5 = new java.lang.String[r5];	 Catch:{ SQLiteException -> 0x0c53 }
        r7 = 0;
        r5[r7] = r6;	 Catch:{ SQLiteException -> 0x0c53 }
        r7 = 1;
        r5[r7] = r6;	 Catch:{ SQLiteException -> 0x0c53 }
        r2.execSQL(r4, r5);	 Catch:{ SQLiteException -> 0x0c53 }
    L_0x0c43:
        r2 = r30.zzje();	 Catch:{ all -> 0x01d5 }
        r2.setTransactionSuccessful();	 Catch:{ all -> 0x01d5 }
        r2 = r30.zzje();
        r2.endTransaction();
        r2 = 1;
    L_0x0c52:
        return r2;
    L_0x0c53:
        r2 = move-exception;
        r3 = r3.zzgf();	 Catch:{ all -> 0x01d5 }
        r3 = r3.zzis();	 Catch:{ all -> 0x01d5 }
        r4 = "Failed to remove unused event metadata. appId";
        r5 = com.google.android.gms.internal.measurement.zzfh.zzbl(r6);	 Catch:{ all -> 0x01d5 }
        r3.zze(r4, r5, r2);	 Catch:{ all -> 0x01d5 }
        goto L_0x0c43;
    L_0x0c67:
        r2 = r30.zzje();	 Catch:{ all -> 0x01d5 }
        r2.setTransactionSuccessful();	 Catch:{ all -> 0x01d5 }
        r2 = r30.zzje();
        r2.endTransaction();
        r2 = 0;
        goto L_0x0c52;
    L_0x0c77:
        r2 = move-exception;
        r3 = r11;
        goto L_0x037c;
    L_0x0c7b:
        r2 = move-exception;
        goto L_0x0309;
    L_0x0c7e:
        r2 = move-exception;
        r4 = r12;
        goto L_0x0309;
    L_0x0c82:
        r2 = r4;
        goto L_0x0b87;
    L_0x0c85:
        r3 = r2;
        goto L_0x0902;
    L_0x0c88:
        r20 = r2;
        goto L_0x0843;
    L_0x0c8c:
        r2 = r14;
        goto L_0x05bc;
    L_0x0c8f:
        r2 = r3;
        r3 = r4;
        goto L_0x0557;
    L_0x0c93:
        r17 = r2;
        goto L_0x04e8;
    L_0x0c97:
        r2 = r3;
        r3 = r4;
        goto L_0x03d6;
    L_0x0c9b:
        r2 = r14;
        r4 = r13;
        r5 = r17;
        goto L_0x0166;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzjs.zzd(java.lang.String, long):boolean");
    }

    private final zzdy zzg(zzdz zzdz) {
        Object obj = 1;
        zzab();
        zzkz();
        Preconditions.checkNotNull(zzdz);
        Preconditions.checkNotEmpty(zzdz.packageName);
        zzdy zzbb = zzje().zzbb(zzdz.packageName);
        String zzbo = this.zzacw.zzgg().zzbo(zzdz.packageName);
        Object obj2 = null;
        if (zzbb == null) {
            zzdy zzdy = new zzdy(this.zzacw, zzdz.packageName);
            zzdy.zzak(this.zzacw.zzfw().zzio());
            zzdy.zzam(zzbo);
            zzbb = zzdy;
            obj2 = 1;
        } else if (!zzbo.equals(zzbb.zzgk())) {
            zzbb.zzam(zzbo);
            zzbb.zzak(this.zzacw.zzfw().zzio());
            int i = 1;
        }
        if (!(TextUtils.isEmpty(zzdz.zzadm) || zzdz.zzadm.equals(zzbb.getGmpAppId()))) {
            zzbb.zzal(zzdz.zzadm);
            obj2 = 1;
        }
        if (!(TextUtils.isEmpty(zzdz.zzado) || zzdz.zzado.equals(zzbb.zzgl()))) {
            zzbb.zzan(zzdz.zzado);
            obj2 = 1;
        }
        if (!(zzdz.zzadu == 0 || zzdz.zzadu == zzbb.zzgq())) {
            zzbb.zzp(zzdz.zzadu);
            obj2 = 1;
        }
        if (!(TextUtils.isEmpty(zzdz.zzth) || zzdz.zzth.equals(zzbb.zzag()))) {
            zzbb.setAppVersion(zzdz.zzth);
            obj2 = 1;
        }
        if (zzdz.zzads != zzbb.zzgo()) {
            zzbb.zzo(zzdz.zzads);
            obj2 = 1;
        }
        if (!(zzdz.zzadt == null || zzdz.zzadt.equals(zzbb.zzgp()))) {
            zzbb.zzao(zzdz.zzadt);
            obj2 = 1;
        }
        if (zzdz.zzadv != zzbb.zzgr()) {
            zzbb.zzq(zzdz.zzadv);
            obj2 = 1;
        }
        if (zzdz.zzadw != zzbb.isMeasurementEnabled()) {
            zzbb.setMeasurementEnabled(zzdz.zzadw);
            obj2 = 1;
        }
        if (!(TextUtils.isEmpty(zzdz.zzaek) || zzdz.zzaek.equals(zzbb.zzhc()))) {
            zzbb.zzap(zzdz.zzaek);
            obj2 = 1;
        }
        if (zzdz.zzadx != zzbb.zzhe()) {
            zzbb.zzaa(zzdz.zzadx);
            obj2 = 1;
        }
        if (zzdz.zzady != zzbb.zzhf()) {
            zzbb.zzd(zzdz.zzady);
            obj2 = 1;
        }
        if (zzdz.zzadz != zzbb.zzhg()) {
            zzbb.zze(zzdz.zzadz);
        } else {
            obj = obj2;
        }
        if (obj != null) {
            zzje().zza(zzbb);
        }
        return zzbb;
    }

    public static zzjs zzg(Context context) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(context.getApplicationContext());
        if (zzaqj == null) {
            synchronized (zzjs.class) {
                if (zzaqj == null) {
                    zzaqj = new zzjs(new zzjx(context));
                }
            }
        }
        return zzaqj;
    }

    private final zzgg zzkv() {
        zza(this.zzaqk);
        return this.zzaqk;
    }

    private final zzfq zzkx() {
        if (this.zzaqn != null) {
            return this.zzaqn;
        }
        throw new IllegalStateException("Network broadcast receiver not created");
    }

    private final zzjo zzky() {
        zza(this.zzaqo);
        return this.zzaqo;
    }

    private final long zzla() {
        long currentTimeMillis = this.zzacw.zzbt().currentTimeMillis();
        zzhh zzgg = this.zzacw.zzgg();
        zzgg.zzch();
        zzgg.zzab();
        long j = zzgg.zzakh.get();
        if (j == 0) {
            j = 1 + ((long) zzgg.zzgc().zzll().nextInt(86400000));
            zzgg.zzakh.set(j);
        }
        return ((((j + currentTimeMillis) / 1000) / 60) / 60) / 24;
    }

    private final boolean zzlc() {
        zzab();
        zzkz();
        return zzje().zzhw() || !TextUtils.isEmpty(zzje().zzhr());
    }

    private final void zzld() {
        zzab();
        zzkz();
        if (zzlh()) {
            long abs;
            if (this.zzaqs > 0) {
                abs = 3600000 - Math.abs(this.zzacw.zzbt().elapsedRealtime() - this.zzaqs);
                if (abs > 0) {
                    this.zzacw.zzgf().zziz().zzg("Upload has been suspended. Will update scheduling later in approximately ms", Long.valueOf(abs));
                    zzkx().unregister();
                    zzky().cancel();
                    return;
                }
                this.zzaqs = 0;
            }
            if (this.zzacw.zzkd() && zzlc()) {
                long currentTimeMillis = this.zzacw.zzbt().currentTimeMillis();
                long max = Math.max(0, ((Long) zzey.zzahp.get()).longValue());
                Object obj = (zzje().zzhx() || zzje().zzhs()) ? 1 : null;
                if (obj != null) {
                    CharSequence zzhn = this.zzacw.zzgh().zzhn();
                    abs = (TextUtils.isEmpty(zzhn) || ".none.".equals(zzhn)) ? Math.max(0, ((Long) zzey.zzahj.get()).longValue()) : Math.max(0, ((Long) zzey.zzahk.get()).longValue());
                } else {
                    abs = Math.max(0, ((Long) zzey.zzahi.get()).longValue());
                }
                long j = this.zzacw.zzgg().zzakd.get();
                long j2 = this.zzacw.zzgg().zzake.get();
                long max2 = Math.max(zzje().zzhu(), zzje().zzhv());
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
                    if (!this.zzacw.zzgc().zza(j, abs)) {
                        currentTimeMillis = j + abs;
                    }
                    if (j2 != 0 && j2 >= max2) {
                        for (int i = 0; i < Math.min(20, Math.max(0, ((Integer) zzey.zzahr.get()).intValue())); i++) {
                            currentTimeMillis += (1 << i) * Math.max(0, ((Long) zzey.zzahq.get()).longValue());
                            if (currentTimeMillis > j2) {
                                break;
                            }
                        }
                        currentTimeMillis = 0;
                    }
                }
                if (currentTimeMillis == 0) {
                    this.zzacw.zzgf().zziz().log("Next upload time is 0");
                    zzkx().unregister();
                    zzky().cancel();
                    return;
                } else if (zzkw().zzex()) {
                    long j3 = this.zzacw.zzgg().zzakf.get();
                    abs = Math.max(0, ((Long) zzey.zzahg.get()).longValue());
                    abs = !this.zzacw.zzgc().zza(j3, abs) ? Math.max(currentTimeMillis, abs + j3) : currentTimeMillis;
                    zzkx().unregister();
                    abs -= this.zzacw.zzbt().currentTimeMillis();
                    if (abs <= 0) {
                        abs = Math.max(0, ((Long) zzey.zzahl.get()).longValue());
                        this.zzacw.zzgg().zzakd.set(this.zzacw.zzbt().currentTimeMillis());
                    }
                    this.zzacw.zzgf().zziz().zzg("Upload scheduled in approximately ms", Long.valueOf(abs));
                    zzky().zzh(abs);
                    return;
                } else {
                    this.zzacw.zzgf().zziz().log("No network");
                    zzkx().zzeu();
                    zzky().cancel();
                    return;
                }
            }
            this.zzacw.zzgf().zziz().log("Nothing to upload or uploading impossible");
            zzkx().unregister();
            zzky().cancel();
        }
    }

    private final void zzle() {
        zzab();
        if (this.zzaqw || this.zzaqx || this.zzaqy) {
            this.zzacw.zzgf().zziz().zzd("Not stopping services. fetch, network, upload", Boolean.valueOf(this.zzaqw), Boolean.valueOf(this.zzaqx), Boolean.valueOf(this.zzaqy));
            return;
        }
        this.zzacw.zzgf().zziz().log("Stopping uploading service(s)");
        if (this.zzaqt != null) {
            for (Runnable run : this.zzaqt) {
                run.run();
            }
            this.zzaqt.clear();
        }
    }

    @VisibleForTesting
    private final boolean zzlf() {
        zzab();
        try {
            this.zzara = new RandomAccessFile(new File(this.zzacw.getContext().getFilesDir(), "google_app_measurement.db"), "rw").getChannel();
            this.zzaqz = this.zzara.tryLock();
            if (this.zzaqz != null) {
                this.zzacw.zzgf().zziz().log("Storage concurrent access okay");
                return true;
            }
            this.zzacw.zzgf().zzis().log("Storage concurrent data access panic");
            return false;
        } catch (FileNotFoundException e) {
            this.zzacw.zzgf().zzis().zzg("Failed to acquire storage lock", e);
        } catch (IOException e2) {
            this.zzacw.zzgf().zzis().zzg("Failed to access storage lock file", e2);
        }
    }

    private final boolean zzlh() {
        zzab();
        zzkz();
        return this.zzaqr;
    }

    public final Context getContext() {
        return this.zzacw.getContext();
    }

    protected final void start() {
        this.zzacw.zzge().zzab();
        zzje().zzht();
        if (this.zzacw.zzgg().zzakd.get() == 0) {
            this.zzacw.zzgg().zzakd.set(this.zzacw.zzbt().currentTimeMillis());
        }
        zzld();
    }

    @VisibleForTesting
    protected final void zza(int i, Throwable th, byte[] bArr, String str) {
        zzhh zzje;
        zzab();
        zzkz();
        if (bArr == null) {
            try {
                bArr = new byte[0];
            } catch (Throwable th2) {
                this.zzaqx = false;
                zzle();
            }
        }
        List<Long> list = this.zzarb;
        this.zzarb = null;
        if ((i == Callback.DEFAULT_DRAG_ANIMATION_DURATION || i == 204) && th == null) {
            try {
                this.zzacw.zzgg().zzakd.set(this.zzacw.zzbt().currentTimeMillis());
                this.zzacw.zzgg().zzake.set(0);
                zzld();
                this.zzacw.zzgf().zziz().zze("Successful upload. Got network response. code, size", Integer.valueOf(i), Integer.valueOf(bArr.length));
                zzje().beginTransaction();
                try {
                    for (Long l : list) {
                        try {
                            zzje = zzje();
                            long longValue = l.longValue();
                            zzje.zzab();
                            zzje.zzch();
                            if (zzje.getWritableDatabase().delete("queue", "rowid=?", new String[]{String.valueOf(longValue)}) != 1) {
                                throw new SQLiteException("Deleted fewer rows from queue than expected");
                            }
                        } catch (SQLiteException e) {
                            zzje.zzgf().zzis().zzg("Failed to delete a bundle in a queue table", e);
                            throw e;
                        } catch (SQLiteException e2) {
                            if (this.zzarc == null || !this.zzarc.contains(l)) {
                                throw e2;
                            }
                        }
                    }
                    zzje().setTransactionSuccessful();
                    this.zzarc = null;
                    if (zzkw().zzex() && zzlc()) {
                        zzlb();
                    } else {
                        this.zzard = -1;
                        zzld();
                    }
                    this.zzaqs = 0;
                } finally {
                    zzje().endTransaction();
                }
            } catch (SQLiteException e3) {
                this.zzacw.zzgf().zzis().zzg("Database error while trying to delete uploaded bundles", e3);
                this.zzaqs = this.zzacw.zzbt().elapsedRealtime();
                this.zzacw.zzgf().zziz().zzg("Disable upload, time", Long.valueOf(this.zzaqs));
            }
        } else {
            this.zzacw.zzgf().zziz().zze("Network upload failed. Will retry later. code, error", Integer.valueOf(i), th);
            this.zzacw.zzgg().zzake.set(this.zzacw.zzbt().currentTimeMillis());
            boolean z = i == 503 || i == 429;
            if (z) {
                this.zzacw.zzgg().zzakf.set(this.zzacw.zzbt().currentTimeMillis());
            }
            if (this.zzacw.zzgh().zzaw(str)) {
                zzje().zzc(list);
            }
            zzld();
        }
        this.zzaqx = false;
        zzle();
    }

    public final byte[] zza(zzew zzew, String str) {
        zzkz();
        zzab();
        this.zzacw.zzfr();
        Preconditions.checkNotNull(zzew);
        Preconditions.checkNotEmpty(str);
        zzacg zzkr = new zzkr();
        zzje().beginTransaction();
        try {
            zzdy zzbb = zzje().zzbb(str);
            byte[] bArr;
            if (zzbb == null) {
                this.zzacw.zzgf().zziy().zzg("Log and bundle not available. package_name", str);
                bArr = new byte[0];
                return bArr;
            } else if (zzbb.isMeasurementEnabled()) {
                zzku zzku;
                long j;
                if (("_iap".equals(zzew.name) || C1796a.ECOMMERCE_PURCHASE.equals(zzew.name)) && !zza(str, zzew)) {
                    this.zzacw.zzgf().zziv().zzg("Failed to handle purchase event at single event bundle creation. appId", zzfh.zzbl(str));
                }
                boolean zzau = this.zzacw.zzgh().zzau(str);
                Long valueOf = Long.valueOf(0);
                if (zzau && "_e".equals(zzew.name)) {
                    if (zzew.zzafr == null || zzew.zzafr.size() == 0) {
                        this.zzacw.zzgf().zziv().zzg("The engagement event does not contain any parameters. appId", zzfh.zzbl(str));
                    } else if (zzew.zzafr.getLong("_et") == null) {
                        this.zzacw.zzgf().zziv().zzg("The engagement event does not include duration. appId", zzfh.zzbl(str));
                    } else {
                        valueOf = zzew.zzafr.getLong("_et");
                    }
                }
                zzks zzks = new zzks();
                zzkr.zzatr = new zzks[]{zzks};
                zzks.zzatt = Integer.valueOf(1);
                zzks.zzaub = "android";
                zzks.zzti = zzbb.zzah();
                zzks.zzadt = zzbb.zzgp();
                zzks.zzth = zzbb.zzag();
                long zzgo = zzbb.zzgo();
                zzks.zzaun = zzgo == -2147483648L ? null : Integer.valueOf((int) zzgo);
                zzks.zzauf = Long.valueOf(zzbb.zzgq());
                zzks.zzadm = zzbb.getGmpAppId();
                zzks.zzauj = Long.valueOf(zzbb.zzgr());
                if (this.zzacw.isEnabled() && zzeg.zzho() && this.zzacw.zzgh().zzas(zzks.zzti)) {
                    zzks.zzaut = null;
                }
                Pair zzbn = this.zzacw.zzgg().zzbn(zzbb.zzah());
                if (!(!zzbb.zzhf() || zzbn == null || TextUtils.isEmpty((CharSequence) zzbn.first))) {
                    zzks.zzauh = (String) zzbn.first;
                    zzks.zzaui = (Boolean) zzbn.second;
                }
                this.zzacw.zzfx().zzch();
                zzks.zzaud = Build.MODEL;
                this.zzacw.zzfx().zzch();
                zzks.zzauc = VERSION.RELEASE;
                zzks.zzaue = Integer.valueOf((int) this.zzacw.zzfx().zzig());
                zzks.zzafo = this.zzacw.zzfx().zzih();
                zzks.zzadl = zzbb.getAppInstanceId();
                zzks.zzado = zzbb.zzgl();
                List zzba = zzje().zzba(zzbb.zzah());
                zzks.zzatv = new zzku[zzba.size()];
                zzkb zzkb = null;
                if (zzau) {
                    zzkb zzh = zzje().zzh(zzks.zzti, "_lte");
                    zzkb = (zzh == null || zzh.value == null) ? new zzkb(zzks.zzti, "auto", "_lte", this.zzacw.zzbt().currentTimeMillis(), valueOf) : valueOf.longValue() > 0 ? new zzkb(zzks.zzti, "auto", "_lte", this.zzacw.zzbt().currentTimeMillis(), Long.valueOf(((Long) zzh.value).longValue() + valueOf.longValue())) : zzh;
                }
                zzku zzku2 = null;
                int i = 0;
                while (i < zzba.size()) {
                    zzku zzku3;
                    zzku = new zzku();
                    zzks.zzatv[i] = zzku;
                    zzku.name = ((zzkb) zzba.get(i)).name;
                    zzku.zzauz = Long.valueOf(((zzkb) zzba.get(i)).zzarl);
                    zzjc().zza(zzku, ((zzkb) zzba.get(i)).value);
                    if (zzau && "_lte".equals(zzku.name)) {
                        zzku.zzatq = (Long) zzkb.value;
                        zzku.zzauz = Long.valueOf(this.zzacw.zzbt().currentTimeMillis());
                        zzku3 = zzku;
                    } else {
                        zzku3 = zzku2;
                    }
                    i++;
                    zzku2 = zzku3;
                }
                if (zzau && zzku2 == null) {
                    zzku = new zzku();
                    zzku.name = "_lte";
                    zzku.zzauz = Long.valueOf(this.zzacw.zzbt().currentTimeMillis());
                    zzku.zzatq = (Long) zzkb.value;
                    zzks.zzatv = (zzku[]) Arrays.copyOf(zzks.zzatv, zzks.zzatv.length + 1);
                    zzks.zzatv[zzks.zzatv.length - 1] = zzku;
                }
                if (valueOf.longValue() > 0) {
                    zzje().zza(zzkb);
                }
                Bundle zzij = zzew.zzafr.zzij();
                if ("_iap".equals(zzew.name)) {
                    zzij.putLong("_c", 1);
                    this.zzacw.zzgf().zziy().log("Marking in-app purchase as real-time");
                    zzij.putLong("_r", 1);
                }
                zzij.putString("_o", zzew.origin);
                if (this.zzacw.zzgc().zzci(zzks.zzti)) {
                    this.zzacw.zzgc().zza(zzij, "_dbg", Long.valueOf(1));
                    this.zzacw.zzgc().zza(zzij, "_r", Long.valueOf(1));
                }
                zzes zzf = zzje().zzf(str, zzew.name);
                if (zzf == null) {
                    zzje().zza(new zzes(str, zzew.name, 1, 0, zzew.zzagc, 0, null, null, null));
                    j = 0;
                } else {
                    j = zzf.zzafu;
                    zzje().zza(zzf.zzac(zzew.zzagc).zzii());
                }
                zzer zzer = new zzer(this.zzacw, zzew.origin, str, zzew.name, zzew.zzagc, j, zzij);
                zzkp zzkp = new zzkp();
                zzks.zzatu = new zzkp[]{zzkp};
                zzkp.zzatn = Long.valueOf(zzer.timestamp);
                zzkp.name = zzer.name;
                zzkp.zzato = Long.valueOf(zzer.zzafq);
                zzkp.zzatm = new zzkq[zzer.zzafr.size()];
                Iterator it = zzer.zzafr.iterator();
                int i2 = 0;
                while (it.hasNext()) {
                    String str2 = (String) it.next();
                    zzkq zzkq = new zzkq();
                    i = i2 + 1;
                    zzkp.zzatm[i2] = zzkq;
                    zzkq.name = str2;
                    zzjc().zza(zzkq, zzer.zzafr.get(str2));
                    i2 = i;
                }
                zzks.zzaum = zza(zzbb.zzah(), zzks.zzatv, zzks.zzatu);
                zzks.zzatx = zzkp.zzatn;
                zzks.zzaty = zzkp.zzatn;
                zzgo = zzbb.zzgn();
                zzks.zzaua = zzgo != 0 ? Long.valueOf(zzgo) : null;
                long zzgm = zzbb.zzgm();
                if (zzgm != 0) {
                    zzgo = zzgm;
                }
                zzks.zzatz = zzgo != 0 ? Long.valueOf(zzgo) : null;
                zzbb.zzgv();
                zzks.zzauk = Integer.valueOf((int) zzbb.zzgs());
                zzks.zzaug = Long.valueOf(12451);
                zzks.zzatw = Long.valueOf(this.zzacw.zzbt().currentTimeMillis());
                zzks.zzaul = Boolean.TRUE;
                zzbb.zzm(zzks.zzatx.longValue());
                zzbb.zzn(zzks.zzaty.longValue());
                zzje().zza(zzbb);
                zzje().setTransactionSuccessful();
                zzje().endTransaction();
                try {
                    bArr = new byte[zzkr.zzvv()];
                    zzaby zzb = zzaby.zzb(bArr, 0, bArr.length);
                    zzkr.zza(zzb);
                    zzb.zzvn();
                    return this.zzacw.zzgc().zza(bArr);
                } catch (IOException e) {
                    this.zzacw.zzgf().zzis().zze("Data loss. Failed to bundle and serialize. appId", zzfh.zzbl(str), e);
                    return null;
                }
            } else {
                this.zzacw.zzgf().zziy().zzg("Log and bundle disabled. package_name", str);
                bArr = new byte[0];
                zzje().endTransaction();
                return bArr;
            }
        } finally {
            zzje().endTransaction();
        }
    }

    final void zzb(zzee zzee, zzdz zzdz) {
        boolean z = true;
        Preconditions.checkNotNull(zzee);
        Preconditions.checkNotEmpty(zzee.packageName);
        Preconditions.checkNotNull(zzee.origin);
        Preconditions.checkNotNull(zzee.zzaeq);
        Preconditions.checkNotEmpty(zzee.zzaeq.name);
        zzab();
        zzkz();
        if (!TextUtils.isEmpty(zzdz.zzadm)) {
            if (zzdz.zzadw) {
                zzee zzee2 = new zzee(zzee);
                zzee2.active = false;
                zzje().beginTransaction();
                try {
                    zzee zzi = zzje().zzi(zzee2.packageName, zzee2.zzaeq.name);
                    if (!(zzi == null || zzi.origin.equals(zzee2.origin))) {
                        this.zzacw.zzgf().zziv().zzd("Updating a conditional user property with different origin. name, origin, origin (from DB)", this.zzacw.zzgb().zzbk(zzee2.zzaeq.name), zzee2.origin, zzi.origin);
                    }
                    if (zzi != null && zzi.active) {
                        zzee2.origin = zzi.origin;
                        zzee2.creationTimestamp = zzi.creationTimestamp;
                        zzee2.triggerTimeout = zzi.triggerTimeout;
                        zzee2.triggerEventName = zzi.triggerEventName;
                        zzee2.zzaes = zzi.zzaes;
                        zzee2.active = zzi.active;
                        zzee2.zzaeq = new zzjz(zzee2.zzaeq.name, zzi.zzaeq.zzarl, zzee2.zzaeq.getValue(), zzi.zzaeq.origin);
                        z = false;
                    } else if (TextUtils.isEmpty(zzee2.triggerEventName)) {
                        zzee2.zzaeq = new zzjz(zzee2.zzaeq.name, zzee2.creationTimestamp, zzee2.zzaeq.getValue(), zzee2.zzaeq.origin);
                        zzee2.active = true;
                    } else {
                        z = false;
                    }
                    if (zzee2.active) {
                        zzjz zzjz = zzee2.zzaeq;
                        zzkb zzkb = new zzkb(zzee2.packageName, zzee2.origin, zzjz.name, zzjz.zzarl, zzjz.getValue());
                        if (zzje().zza(zzkb)) {
                            this.zzacw.zzgf().zziy().zzd("User property updated immediately", zzee2.packageName, this.zzacw.zzgb().zzbk(zzkb.name), zzkb.value);
                        } else {
                            this.zzacw.zzgf().zzis().zzd("(2)Too many active user properties, ignoring", zzfh.zzbl(zzee2.packageName), this.zzacw.zzgb().zzbk(zzkb.name), zzkb.value);
                        }
                        if (z && zzee2.zzaes != null) {
                            zzc(new zzew(zzee2.zzaes, zzee2.creationTimestamp), zzdz);
                        }
                    }
                    if (zzje().zza(zzee2)) {
                        this.zzacw.zzgf().zziy().zzd("Conditional property added", zzee2.packageName, this.zzacw.zzgb().zzbk(zzee2.zzaeq.name), zzee2.zzaeq.getValue());
                    } else {
                        this.zzacw.zzgf().zzis().zzd("Too many conditional properties, ignoring", zzfh.zzbl(zzee2.packageName), this.zzacw.zzgb().zzbk(zzee2.zzaeq.name), zzee2.zzaeq.getValue());
                    }
                    zzje().setTransactionSuccessful();
                } finally {
                    zzje().endTransaction();
                }
            } else {
                zzg(zzdz);
            }
        }
    }

    final void zzb(zzew zzew, zzdz zzdz) {
        Preconditions.checkNotNull(zzdz);
        Preconditions.checkNotEmpty(zzdz.packageName);
        zzab();
        zzkz();
        String str = zzdz.packageName;
        long j = zzew.zzagc;
        if (!this.zzacw.zzgc().zzd(zzew, zzdz)) {
            return;
        }
        if (zzdz.zzadw) {
            zzje().beginTransaction();
            try {
                List emptyList;
                Object obj;
                zzhh zzje = zzje();
                Preconditions.checkNotEmpty(str);
                zzje.zzab();
                zzje.zzch();
                if (j < 0) {
                    zzje.zzgf().zziv().zze("Invalid time querying timed out conditional properties", zzfh.zzbl(str), Long.valueOf(j));
                    emptyList = Collections.emptyList();
                } else {
                    emptyList = zzje.zzb("active=0 and app_id=? and abs(? - creation_timestamp) > trigger_timeout", new String[]{str, String.valueOf(j)});
                }
                for (zzee zzee : r2) {
                    if (zzee != null) {
                        this.zzacw.zzgf().zziy().zzd("User property timed out", zzee.packageName, this.zzacw.zzgb().zzbk(zzee.zzaeq.name), zzee.zzaeq.getValue());
                        if (zzee.zzaer != null) {
                            zzc(new zzew(zzee.zzaer, j), zzdz);
                        }
                        zzje().zzj(str, zzee.zzaeq.name);
                    }
                }
                zzje = zzje();
                Preconditions.checkNotEmpty(str);
                zzje.zzab();
                zzje.zzch();
                if (j < 0) {
                    zzje.zzgf().zziv().zze("Invalid time querying expired conditional properties", zzfh.zzbl(str), Long.valueOf(j));
                    emptyList = Collections.emptyList();
                } else {
                    emptyList = zzje.zzb("active<>0 and app_id=? and abs(? - triggered_timestamp) > time_to_live", new String[]{str, String.valueOf(j)});
                }
                List arrayList = new ArrayList(r2.size());
                for (zzee zzee2 : r2) {
                    if (zzee2 != null) {
                        this.zzacw.zzgf().zziy().zzd("User property expired", zzee2.packageName, this.zzacw.zzgb().zzbk(zzee2.zzaeq.name), zzee2.zzaeq.getValue());
                        zzje().zzg(str, zzee2.zzaeq.name);
                        if (zzee2.zzaet != null) {
                            arrayList.add(zzee2.zzaet);
                        }
                        zzje().zzj(str, zzee2.zzaeq.name);
                    }
                }
                ArrayList arrayList2 = (ArrayList) arrayList;
                int size = arrayList2.size();
                int i = 0;
                while (i < size) {
                    obj = arrayList2.get(i);
                    i++;
                    zzc(new zzew((zzew) obj, j), zzdz);
                }
                zzje = zzje();
                String str2 = zzew.name;
                Preconditions.checkNotEmpty(str);
                Preconditions.checkNotEmpty(str2);
                zzje.zzab();
                zzje.zzch();
                if (j < 0) {
                    zzje.zzgf().zziv().zzd("Invalid time querying triggered conditional properties", zzfh.zzbl(str), zzje.zzgb().zzbi(str2), Long.valueOf(j));
                    emptyList = Collections.emptyList();
                } else {
                    emptyList = zzje.zzb("active=0 and app_id=? and trigger_event_name=? and abs(? - creation_timestamp) <= trigger_timeout", new String[]{str, str2, String.valueOf(j)});
                }
                List arrayList3 = new ArrayList(r2.size());
                for (zzee zzee3 : r2) {
                    if (zzee3 != null) {
                        zzjz zzjz = zzee3.zzaeq;
                        zzkb zzkb = new zzkb(zzee3.packageName, zzee3.origin, zzjz.name, j, zzjz.getValue());
                        if (zzje().zza(zzkb)) {
                            this.zzacw.zzgf().zziy().zzd("User property triggered", zzee3.packageName, this.zzacw.zzgb().zzbk(zzkb.name), zzkb.value);
                        } else {
                            this.zzacw.zzgf().zzis().zzd("Too many active user properties, ignoring", zzfh.zzbl(zzee3.packageName), this.zzacw.zzgb().zzbk(zzkb.name), zzkb.value);
                        }
                        if (zzee3.zzaes != null) {
                            arrayList3.add(zzee3.zzaes);
                        }
                        zzee3.zzaeq = new zzjz(zzkb);
                        zzee3.active = true;
                        zzje().zza(zzee3);
                    }
                }
                zzc(zzew, zzdz);
                arrayList2 = (ArrayList) arrayList3;
                int size2 = arrayList2.size();
                i = 0;
                while (i < size2) {
                    obj = arrayList2.get(i);
                    i++;
                    zzc(new zzew((zzew) obj, j), zzdz);
                }
                zzje().setTransactionSuccessful();
            } finally {
                zzje().endTransaction();
            }
        } else {
            zzg(zzdz);
        }
    }

    final void zzb(zzjr zzjr) {
        this.zzaqu++;
    }

    final void zzb(zzjz zzjz, zzdz zzdz) {
        int i = 0;
        zzab();
        zzkz();
        if (!TextUtils.isEmpty(zzdz.zzadm)) {
            if (zzdz.zzadw) {
                int zzce = this.zzacw.zzgc().zzce(zzjz.name);
                String zza;
                if (zzce != 0) {
                    this.zzacw.zzgc();
                    zza = zzkc.zza(zzjz.name, 24, true);
                    if (zzjz.name != null) {
                        i = zzjz.name.length();
                    }
                    this.zzacw.zzgc().zza(zzdz.packageName, zzce, "_ev", zza, i);
                    return;
                }
                zzce = this.zzacw.zzgc().zzi(zzjz.name, zzjz.getValue());
                if (zzce != 0) {
                    this.zzacw.zzgc();
                    zza = zzkc.zza(zzjz.name, 24, true);
                    Object value = zzjz.getValue();
                    if (value != null && ((value instanceof String) || (value instanceof CharSequence))) {
                        i = String.valueOf(value).length();
                    }
                    this.zzacw.zzgc().zza(zzdz.packageName, zzce, "_ev", zza, i);
                    return;
                }
                Object zzj = this.zzacw.zzgc().zzj(zzjz.name, zzjz.getValue());
                if (zzj != null) {
                    zzkb zzkb = new zzkb(zzdz.packageName, zzjz.origin, zzjz.name, zzjz.zzarl, zzj);
                    this.zzacw.zzgf().zziy().zze("Setting user property", this.zzacw.zzgb().zzbk(zzkb.name), zzj);
                    zzje().beginTransaction();
                    try {
                        zzg(zzdz);
                        boolean zza2 = zzje().zza(zzkb);
                        zzje().setTransactionSuccessful();
                        if (zza2) {
                            this.zzacw.zzgf().zziy().zze("User property set", this.zzacw.zzgb().zzbk(zzkb.name), zzkb.value);
                        } else {
                            this.zzacw.zzgf().zzis().zze("Too many unique user properties are set. Ignoring user property", this.zzacw.zzgb().zzbk(zzkb.name), zzkb.value);
                            this.zzacw.zzgc().zza(zzdz.packageName, 9, null, null, 0);
                        }
                        zzje().endTransaction();
                        return;
                    } catch (Throwable th) {
                        zzje().endTransaction();
                    }
                } else {
                    return;
                }
            }
            zzg(zzdz);
        }
    }

    @VisibleForTesting
    final void zzb(String str, int i, Throwable th, byte[] bArr, Map<String, List<String>> map) {
        boolean z = true;
        zzab();
        zzkz();
        Preconditions.checkNotEmpty(str);
        if (bArr == null) {
            try {
                bArr = new byte[0];
            } catch (Throwable th2) {
                this.zzaqw = false;
                zzle();
            }
        }
        this.zzacw.zzgf().zziz().zzg("onConfigFetched. Response size", Integer.valueOf(bArr.length));
        zzje().beginTransaction();
        zzdy zzbb = zzje().zzbb(str);
        boolean z2 = (i == Callback.DEFAULT_DRAG_ANIMATION_DURATION || i == 204 || i == 304) && th == null;
        if (zzbb == null) {
            this.zzacw.zzgf().zziv().zzg("App does not exist in onConfigFetched. appId", zzfh.zzbl(str));
        } else if (z2 || i == WalletConstants.ERROR_CODE_INVALID_PARAMETERS) {
            List list = map != null ? (List) map.get("Last-Modified") : null;
            String str2 = (list == null || list.size() <= 0) ? null : (String) list.get(0);
            if (i == WalletConstants.ERROR_CODE_INVALID_PARAMETERS || i == 304) {
                if (zzkv().zzbt(str) == null && !zzkv().zza(str, null, null)) {
                    zzje().endTransaction();
                    this.zzaqw = false;
                    zzle();
                    return;
                }
            } else if (!zzkv().zza(str, bArr, str2)) {
                zzje().endTransaction();
                this.zzaqw = false;
                zzle();
                return;
            }
            zzbb.zzs(this.zzacw.zzbt().currentTimeMillis());
            zzje().zza(zzbb);
            if (i == WalletConstants.ERROR_CODE_INVALID_PARAMETERS) {
                this.zzacw.zzgf().zziw().zzg("Config not found. Using empty config. appId", str);
            } else {
                this.zzacw.zzgf().zziz().zze("Successfully fetched config. Got network response. code, size", Integer.valueOf(i), Integer.valueOf(bArr.length));
            }
            if (zzkw().zzex() && zzlc()) {
                zzlb();
            } else {
                zzld();
            }
        } else {
            zzbb.zzt(this.zzacw.zzbt().currentTimeMillis());
            zzje().zza(zzbb);
            this.zzacw.zzgf().zziz().zze("Fetching config failed. code, error", Integer.valueOf(i), th);
            zzkv().zzbv(str);
            this.zzacw.zzgg().zzake.set(this.zzacw.zzbt().currentTimeMillis());
            if (!(i == 503 || i == 429)) {
                z = false;
            }
            if (z) {
                this.zzacw.zzgg().zzakf.set(this.zzacw.zzbt().currentTimeMillis());
            }
            zzld();
        }
        zzje().setTransactionSuccessful();
        zzje().endTransaction();
        this.zzaqw = false;
        zzle();
    }

    public final Clock zzbt() {
        return this.zzacw.zzbt();
    }

    final void zzc(zzee zzee, zzdz zzdz) {
        Preconditions.checkNotNull(zzee);
        Preconditions.checkNotEmpty(zzee.packageName);
        Preconditions.checkNotNull(zzee.zzaeq);
        Preconditions.checkNotEmpty(zzee.zzaeq.name);
        zzab();
        zzkz();
        if (!TextUtils.isEmpty(zzdz.zzadm)) {
            if (zzdz.zzadw) {
                zzje().beginTransaction();
                try {
                    zzg(zzdz);
                    zzee zzi = zzje().zzi(zzee.packageName, zzee.zzaeq.name);
                    if (zzi != null) {
                        this.zzacw.zzgf().zziy().zze("Removing conditional user property", zzee.packageName, this.zzacw.zzgb().zzbk(zzee.zzaeq.name));
                        zzje().zzj(zzee.packageName, zzee.zzaeq.name);
                        if (zzi.active) {
                            zzje().zzg(zzee.packageName, zzee.zzaeq.name);
                        }
                        if (zzee.zzaet != null) {
                            Bundle bundle = null;
                            if (zzee.zzaet.zzafr != null) {
                                bundle = zzee.zzaet.zzafr.zzij();
                            }
                            zzc(this.zzacw.zzgc().zza(zzee.zzaet.name, bundle, zzi.origin, zzee.zzaet.zzagc, true, false), zzdz);
                        }
                    } else {
                        this.zzacw.zzgf().zziv().zze("Conditional user property doesn't exist", zzfh.zzbl(zzee.packageName), this.zzacw.zzgb().zzbk(zzee.zzaeq.name));
                    }
                    zzje().setTransactionSuccessful();
                } finally {
                    zzje().endTransaction();
                }
            } else {
                zzg(zzdz);
            }
        }
    }

    final void zzc(zzew zzew, String str) {
        zzdy zzbb = zzje().zzbb(str);
        if (zzbb == null || TextUtils.isEmpty(zzbb.zzag())) {
            this.zzacw.zzgf().zziy().zzg("No app data available; dropping event", str);
            return;
        }
        Boolean zzc = zzc(zzbb);
        if (zzc == null) {
            if (!"_ui".equals(zzew.name)) {
                this.zzacw.zzgf().zziv().zzg("Could not find package. appId", zzfh.zzbl(str));
            }
        } else if (!zzc.booleanValue()) {
            this.zzacw.zzgf().zzis().zzg("App version does not match; dropping event. appId", zzfh.zzbl(str));
            return;
        }
        zzew zzew2 = zzew;
        zzb(zzew2, new zzdz(str, zzbb.getGmpAppId(), zzbb.zzag(), zzbb.zzgo(), zzbb.zzgp(), zzbb.zzgq(), zzbb.zzgr(), null, zzbb.isMeasurementEnabled(), false, zzbb.zzgl(), zzbb.zzhe(), 0, 0, zzbb.zzhf(), zzbb.zzhg(), false));
    }

    final void zzc(zzjz zzjz, zzdz zzdz) {
        zzab();
        zzkz();
        if (!TextUtils.isEmpty(zzdz.zzadm)) {
            if (zzdz.zzadw) {
                this.zzacw.zzgf().zziy().zzg("Removing user property", this.zzacw.zzgb().zzbk(zzjz.name));
                zzje().beginTransaction();
                try {
                    zzg(zzdz);
                    zzje().zzg(zzdz.packageName, zzjz.name);
                    zzje().setTransactionSuccessful();
                    this.zzacw.zzgf().zziy().zzg("User property removed", this.zzacw.zzgb().zzbk(zzjz.name));
                } finally {
                    zzje().endTransaction();
                }
            } else {
                zzg(zzdz);
            }
        }
    }

    final zzdz zzca(String str) {
        zzdy zzbb = zzje().zzbb(str);
        if (zzbb == null || TextUtils.isEmpty(zzbb.zzag())) {
            this.zzacw.zzgf().zziy().zzg("No app data available; dropping", str);
            return null;
        }
        Boolean zzc = zzc(zzbb);
        if (zzc == null || zzc.booleanValue()) {
            return new zzdz(str, zzbb.getGmpAppId(), zzbb.zzag(), zzbb.zzgo(), zzbb.zzgp(), zzbb.zzgq(), zzbb.zzgr(), null, zzbb.isMeasurementEnabled(), false, zzbb.zzgl(), zzbb.zzhe(), 0, 0, zzbb.zzhf(), zzbb.zzhg(), false);
        }
        this.zzacw.zzgf().zzis().zzg("App version does not match; dropping. appId", zzfh.zzbl(str));
        return null;
    }

    @VisibleForTesting
    final void zzd(zzdz zzdz) {
        if (this.zzarb != null) {
            this.zzarc = new ArrayList();
            this.zzarc.addAll(this.zzarb);
        }
        zzhh zzje = zzje();
        String str = zzdz.packageName;
        Preconditions.checkNotEmpty(str);
        zzje.zzab();
        zzje.zzch();
        try {
            SQLiteDatabase writableDatabase = zzje.getWritableDatabase();
            String[] strArr = new String[]{str};
            int delete = writableDatabase.delete("main_event_params", "app_id=?", strArr) + ((((((((writableDatabase.delete("apps", "app_id=?", strArr) + 0) + writableDatabase.delete("events", "app_id=?", strArr)) + writableDatabase.delete("user_attributes", "app_id=?", strArr)) + writableDatabase.delete("conditional_properties", "app_id=?", strArr)) + writableDatabase.delete("raw_events", "app_id=?", strArr)) + writableDatabase.delete("raw_events_metadata", "app_id=?", strArr)) + writableDatabase.delete("queue", "app_id=?", strArr)) + writableDatabase.delete("audience_filter_values", "app_id=?", strArr));
            if (delete > 0) {
                zzje.zzgf().zziz().zze("Reset analytics data. app, records", str, Integer.valueOf(delete));
            }
        } catch (SQLiteException e) {
            zzje.zzgf().zzis().zze("Error resetting analytics data. appId, error", zzfh.zzbl(str), e);
        }
        zzdz zza = zza(this.zzacw.getContext(), zzdz.packageName, zzdz.zzadm, zzdz.zzadw, zzdz.zzady, zzdz.zzadz, zzdz.zzaem);
        if (!this.zzacw.zzgh().zzay(zzdz.packageName) || zzdz.zzadw) {
            zzf(zza);
        }
    }

    final void zze(zzdz zzdz) {
        zzab();
        zzkz();
        Preconditions.checkNotEmpty(zzdz.packageName);
        zzg(zzdz);
    }

    public final void zzf(zzdz zzdz) {
        zzhh zzje;
        String zzah;
        zzab();
        zzkz();
        Preconditions.checkNotNull(zzdz);
        Preconditions.checkNotEmpty(zzdz.packageName);
        if (!TextUtils.isEmpty(zzdz.zzadm)) {
            zzdy zzbb = zzje().zzbb(zzdz.packageName);
            if (!(zzbb == null || !TextUtils.isEmpty(zzbb.getGmpAppId()) || TextUtils.isEmpty(zzdz.zzadm))) {
                zzbb.zzs(0);
                zzje().zza(zzbb);
                zzkv().zzbw(zzdz.packageName);
            }
            if (zzdz.zzadw) {
                int i;
                Bundle bundle;
                long j = zzdz.zzaem;
                if (j == 0) {
                    j = this.zzacw.zzbt().currentTimeMillis();
                }
                int i2 = zzdz.zzaen;
                if (i2 == 0 || i2 == 1) {
                    i = i2;
                } else {
                    this.zzacw.zzgf().zziv().zze("Incorrect app type, assuming installed app. appId, appType", zzfh.zzbl(zzdz.packageName), Integer.valueOf(i2));
                    i = 0;
                }
                zzje().beginTransaction();
                try {
                    zzbb = zzje().zzbb(zzdz.packageName);
                    if (!(zzbb == null || zzbb.getGmpAppId() == null || zzbb.getGmpAppId().equals(zzdz.zzadm))) {
                        this.zzacw.zzgf().zziv().zzg("New GMP App Id passed in. Removing cached database data. appId", zzfh.zzbl(zzbb.zzah()));
                        zzje = zzje();
                        zzah = zzbb.zzah();
                        zzje.zzch();
                        zzje.zzab();
                        Preconditions.checkNotEmpty(zzah);
                        SQLiteDatabase writableDatabase = zzje.getWritableDatabase();
                        String[] strArr = new String[]{zzah};
                        i2 = writableDatabase.delete("audience_filter_values", "app_id=?", strArr) + ((((((((writableDatabase.delete("events", "app_id=?", strArr) + 0) + writableDatabase.delete("user_attributes", "app_id=?", strArr)) + writableDatabase.delete("conditional_properties", "app_id=?", strArr)) + writableDatabase.delete("apps", "app_id=?", strArr)) + writableDatabase.delete("raw_events", "app_id=?", strArr)) + writableDatabase.delete("raw_events_metadata", "app_id=?", strArr)) + writableDatabase.delete("event_filters", "app_id=?", strArr)) + writableDatabase.delete("property_filters", "app_id=?", strArr));
                        if (i2 > 0) {
                            zzje.zzgf().zziz().zze("Deleted application data. app, records", zzah, Integer.valueOf(i2));
                        }
                        zzbb = null;
                    }
                } catch (SQLiteException e) {
                    zzje.zzgf().zzis().zze("Error deleting application data. appId, error", zzfh.zzbl(zzah), e);
                } catch (Throwable th) {
                    zzje().endTransaction();
                }
                if (zzbb != null) {
                    if (zzbb.zzgo() != -2147483648L) {
                        if (zzbb.zzgo() != zzdz.zzads) {
                            bundle = new Bundle();
                            bundle.putString("_pv", zzbb.zzag());
                            zzb(new zzew("_au", new zzet(bundle), "auto", j), zzdz);
                        }
                    } else if (!(zzbb.zzag() == null || zzbb.zzag().equals(zzdz.zzth))) {
                        bundle = new Bundle();
                        bundle.putString("_pv", zzbb.zzag());
                        zzb(new zzew("_au", new zzet(bundle), "auto", j), zzdz);
                    }
                }
                zzg(zzdz);
                zzes zzes = null;
                if (i == 0) {
                    zzes = zzje().zzf(zzdz.packageName, "_f");
                } else if (i == 1) {
                    zzes = zzje().zzf(zzdz.packageName, "_v");
                }
                if (zzes == null) {
                    long j2 = (1 + (j / 3600000)) * 3600000;
                    if (i == 0) {
                        zzb(new zzjz("_fot", j, Long.valueOf(j2), "auto"), zzdz);
                        zzab();
                        zzkz();
                        Bundle bundle2 = new Bundle();
                        bundle2.putLong("_c", 1);
                        bundle2.putLong("_r", 1);
                        bundle2.putLong("_uwa", 0);
                        bundle2.putLong("_pfo", 0);
                        bundle2.putLong("_sys", 0);
                        bundle2.putLong("_sysu", 0);
                        if (this.zzacw.zzgh().zzay(zzdz.packageName) && zzdz.zzaeo) {
                            bundle2.putLong("_dac", 1);
                        }
                        if (this.zzacw.getContext().getPackageManager() == null) {
                            this.zzacw.zzgf().zzis().zzg("PackageManager is null, first open report might be inaccurate. appId", zzfh.zzbl(zzdz.packageName));
                        } else {
                            ApplicationInfo applicationInfo;
                            PackageInfo packageInfo = null;
                            try {
                                packageInfo = Wrappers.packageManager(this.zzacw.getContext()).getPackageInfo(zzdz.packageName, 0);
                            } catch (NameNotFoundException e2) {
                                this.zzacw.zzgf().zzis().zze("Package info is null, first open report might be inaccurate. appId", zzfh.zzbl(zzdz.packageName), e2);
                            }
                            if (packageInfo != null) {
                                if (packageInfo.firstInstallTime != 0) {
                                    Object obj = null;
                                    if (packageInfo.firstInstallTime != packageInfo.lastUpdateTime) {
                                        bundle2.putLong("_uwa", 1);
                                    } else {
                                        obj = 1;
                                    }
                                    zzb(new zzjz("_fi", j, Long.valueOf(obj != null ? 1 : 0), "auto"), zzdz);
                                }
                            }
                            try {
                                applicationInfo = Wrappers.packageManager(this.zzacw.getContext()).getApplicationInfo(zzdz.packageName, 0);
                            } catch (NameNotFoundException e22) {
                                this.zzacw.zzgf().zzis().zze("Application info is null, first open report might be inaccurate. appId", zzfh.zzbl(zzdz.packageName), e22);
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
                        zzhh zzje2 = zzje();
                        String str = zzdz.packageName;
                        Preconditions.checkNotEmpty(str);
                        zzje2.zzab();
                        zzje2.zzch();
                        j2 = zzje2.zzm(str, "first_open_count");
                        if (j2 >= 0) {
                            bundle2.putLong("_pfo", j2);
                        }
                        zzb(new zzew("_f", new zzet(bundle2), "auto", j), zzdz);
                    } else if (i == 1) {
                        zzb(new zzjz("_fvt", j, Long.valueOf(j2), "auto"), zzdz);
                        zzab();
                        zzkz();
                        bundle = new Bundle();
                        bundle.putLong("_c", 1);
                        bundle.putLong("_r", 1);
                        if (this.zzacw.zzgh().zzay(zzdz.packageName) && zzdz.zzaeo) {
                            bundle.putLong("_dac", 1);
                        }
                        zzb(new zzew("_v", new zzet(bundle), "auto", j), zzdz);
                    }
                    bundle = new Bundle();
                    bundle.putLong("_et", 1);
                    zzb(new zzew("_e", new zzet(bundle), "auto", j), zzdz);
                } else if (zzdz.zzael) {
                    zzb(new zzew("_cd", new zzet(new Bundle()), "auto", j), zzdz);
                }
                zzje().setTransactionSuccessful();
                zzje().endTransaction();
                return;
            }
            zzg(zzdz);
        }
    }

    final void zzg(Runnable runnable) {
        zzab();
        if (this.zzaqt == null) {
            this.zzaqt = new ArrayList();
        }
        this.zzaqt.add(runnable);
    }

    public final zzff zzgb() {
        return this.zzacw.zzgb();
    }

    public final zzkc zzgc() {
        return this.zzacw.zzgc();
    }

    public final zzgh zzge() {
        return this.zzacw.zzge();
    }

    public final zzfh zzgf() {
        return this.zzacw.zzgf();
    }

    public final zzeg zzgh() {
        return this.zzacw.zzgh();
    }

    public final zzec zzgi() {
        return this.zzacw.zzgi();
    }

    public final String zzh(zzdz zzdz) {
        Object e;
        try {
            return (String) this.zzacw.zzge().zzb(new zzjw(this, zzdz)).get(30000, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e2) {
            e = e2;
        } catch (InterruptedException e3) {
            e = e3;
        } catch (ExecutionException e4) {
            e = e4;
        }
        this.zzacw.zzgf().zzis().zze("Failed to get app instance id. appId", zzfh.zzbl(zzdz.packageName), e);
        return null;
    }

    public final zzjy zzjc() {
        zza(this.zzaqq);
        return this.zzaqq;
    }

    public final zzeb zzjd() {
        zza(this.zzaqp);
        return this.zzaqp;
    }

    public final zzej zzje() {
        zza(this.zzaqm);
        return this.zzaqm;
    }

    public final zzfl zzkw() {
        zza(this.zzaql);
        return this.zzaql;
    }

    final void zzkz() {
        if (!this.zzvo) {
            throw new IllegalStateException("UploadController is not initialized");
        }
    }

    public final void zzlb() {
        String zzhr;
        String str;
        zzab();
        zzkz();
        this.zzaqy = true;
        try {
            this.zzacw.zzgi();
            Boolean zzko = this.zzacw.zzfy().zzko();
            if (zzko == null) {
                this.zzacw.zzgf().zziv().log("Upload data called on the client side before use of service was decided");
                this.zzaqy = false;
                zzle();
            } else if (zzko.booleanValue()) {
                this.zzacw.zzgf().zzis().log("Upload called in the client side when service should be used");
                this.zzaqy = false;
                zzle();
            } else if (this.zzaqs > 0) {
                zzld();
                this.zzaqy = false;
                zzle();
            } else {
                zzab();
                if ((this.zzarb != null ? 1 : null) != null) {
                    this.zzacw.zzgf().zziz().log("Uploading requested multiple times");
                    this.zzaqy = false;
                    zzle();
                } else if (zzkw().zzex()) {
                    long currentTimeMillis = this.zzacw.zzbt().currentTimeMillis();
                    zzd(null, currentTimeMillis - zzeg.zzhm());
                    long j = this.zzacw.zzgg().zzakd.get();
                    if (j != 0) {
                        this.zzacw.zzgf().zziy().zzg("Uploading events. Elapsed time since last upload attempt (ms)", Long.valueOf(Math.abs(currentTimeMillis - j)));
                    }
                    zzhr = zzje().zzhr();
                    Object zzab;
                    if (TextUtils.isEmpty(zzhr)) {
                        this.zzard = -1;
                        zzab = zzje().zzab(currentTimeMillis - zzeg.zzhm());
                        if (!TextUtils.isEmpty(zzab)) {
                            zzdy zzbb = zzje().zzbb(zzab);
                            if (zzbb != null) {
                                zzb(zzbb);
                            }
                        }
                    } else {
                        if (this.zzard == -1) {
                            this.zzard = zzje().zzhy();
                        }
                        List<Pair> zzb = zzje().zzb(zzhr, this.zzacw.zzgh().zzb(zzhr, zzey.zzagv), Math.max(0, this.zzacw.zzgh().zzb(zzhr, zzey.zzagw)));
                        if (!zzb.isEmpty()) {
                            zzks zzks;
                            Object obj;
                            int i;
                            List subList;
                            for (Pair pair : zzb) {
                                zzks = (zzks) pair.first;
                                if (!TextUtils.isEmpty(zzks.zzauh)) {
                                    obj = zzks.zzauh;
                                    break;
                                }
                            }
                            obj = null;
                            if (obj != null) {
                                for (i = 0; i < zzb.size(); i++) {
                                    zzks = (zzks) ((Pair) zzb.get(i)).first;
                                    if (!TextUtils.isEmpty(zzks.zzauh) && !zzks.zzauh.equals(obj)) {
                                        subList = zzb.subList(0, i);
                                        break;
                                    }
                                }
                            }
                            subList = zzb;
                            zzkr zzkr = new zzkr();
                            zzkr.zzatr = new zzks[subList.size()];
                            Collection arrayList = new ArrayList(subList.size());
                            Object obj2 = (zzeg.zzho() && this.zzacw.zzgh().zzas(zzhr)) ? 1 : null;
                            for (i = 0; i < zzkr.zzatr.length; i++) {
                                zzkr.zzatr[i] = (zzks) ((Pair) subList.get(i)).first;
                                arrayList.add((Long) ((Pair) subList.get(i)).second);
                                zzkr.zzatr[i].zzaug = Long.valueOf(12451);
                                zzkr.zzatr[i].zzatw = Long.valueOf(currentTimeMillis);
                                zzks = zzkr.zzatr[i];
                                this.zzacw.zzgi();
                                zzks.zzaul = Boolean.valueOf(false);
                                if (obj2 == null) {
                                    zzkr.zzatr[i].zzaut = null;
                                }
                            }
                            obj2 = this.zzacw.zzgf().isLoggable(2) ? zzjc().zzb(zzkr) : null;
                            obj = zzjc().zza(zzkr);
                            str = (String) zzey.zzahf.get();
                            URL url = new URL(str);
                            Preconditions.checkArgument(!arrayList.isEmpty());
                            if (this.zzarb != null) {
                                this.zzacw.zzgf().zzis().log("Set uploading progress before finishing the previous upload");
                            } else {
                                this.zzarb = new ArrayList(arrayList);
                            }
                            this.zzacw.zzgg().zzake.set(currentTimeMillis);
                            zzab = "?";
                            if (zzkr.zzatr.length > 0) {
                                zzab = zzkr.zzatr[0].zzti;
                            }
                            this.zzacw.zzgf().zziz().zzd("Uploading data. app, uncompressed size, data", zzab, Integer.valueOf(obj.length), obj2);
                            this.zzaqx = true;
                            zzhh zzkw = zzkw();
                            zzfn zzju = new zzju(this, zzhr);
                            zzkw.zzab();
                            zzkw.zzch();
                            Preconditions.checkNotNull(url);
                            Preconditions.checkNotNull(obj);
                            Preconditions.checkNotNull(zzju);
                            zzkw.zzge().zzd(new zzfp(zzkw, zzhr, url, obj, null, zzju));
                        }
                    }
                    this.zzaqy = false;
                    zzle();
                } else {
                    this.zzacw.zzgf().zziz().log("Network not connected, ignoring upload request");
                    zzld();
                    this.zzaqy = false;
                    zzle();
                }
            }
        } catch (MalformedURLException e) {
            this.zzacw.zzgf().zzis().zze("Failed to parse upload URL. Not uploading. appId", zzfh.zzbl(zzhr), str);
        } catch (Throwable th) {
            this.zzaqy = false;
            zzle();
        }
    }

    final void zzlg() {
        zzab();
        zzkz();
        if (!this.zzaqr) {
            this.zzacw.zzgf().zzix().log("This instance being marked as an uploader");
            zzab();
            zzkz();
            if (zzlh() && zzlf()) {
                int zza = zza(this.zzara);
                int zzip = this.zzacw.zzfw().zzip();
                zzab();
                if (zza > zzip) {
                    this.zzacw.zzgf().zzis().zze("Panic: can't downgrade version. Previous, current version", Integer.valueOf(zza), Integer.valueOf(zzip));
                } else if (zza < zzip) {
                    if (zza(zzip, this.zzara)) {
                        this.zzacw.zzgf().zziz().zze("Storage version upgraded. Previous, current version", Integer.valueOf(zza), Integer.valueOf(zzip));
                    } else {
                        this.zzacw.zzgf().zzis().zze("Storage version upgrade failed. Previous, current version", Integer.valueOf(zza), Integer.valueOf(zzip));
                    }
                }
            }
            this.zzaqr = true;
            zzld();
        }
    }

    final void zzli() {
        this.zzaqv++;
    }

    final zzgm zzlj() {
        return this.zzacw;
    }

    public final void zzm(boolean z) {
        zzld();
    }
}
