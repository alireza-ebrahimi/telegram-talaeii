package com.google.android.gms.internal.measurement;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.Build.VERSION;
import android.support.v4.content.C0428o;
import android.util.Log;
import javax.annotation.Nullable;

public abstract class zzwu<T> {
    private static final Object zzbno = new Object();
    private static boolean zzbnp = false;
    private static volatile Boolean zzbnq = null;
    @SuppressLint({"StaticFieldLeak"})
    private static Context zzqx = null;
    private final zzxe zzbnr;
    final String zzbns;
    private final String zzbnt;
    private final T zzbnu;
    private T zzbnv;
    private volatile zzwr zzbnw;
    private volatile SharedPreferences zzbnx;

    private zzwu(zzxe zzxe, String str, T t) {
        this.zzbnv = null;
        this.zzbnw = null;
        this.zzbnx = null;
        if (zzxe.zzbod == null) {
            throw new IllegalArgumentException("Must pass a valid SharedPreferences file name or ContentProvider URI");
        }
        this.zzbnr = zzxe;
        String valueOf = String.valueOf(zzxe.zzboe);
        String valueOf2 = String.valueOf(str);
        this.zzbnt = valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
        valueOf = String.valueOf(zzxe.zzbof);
        valueOf2 = String.valueOf(str);
        this.zzbns = valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
        this.zzbnu = t;
    }

    public static void init(Context context) {
        synchronized (zzbno) {
            if (VERSION.SDK_INT < 24 || !context.isDeviceProtectedStorage()) {
                Context applicationContext = context.getApplicationContext();
                if (applicationContext != null) {
                    context = applicationContext;
                }
            }
            if (zzqx != context) {
                zzbnq = null;
            }
            zzqx = context;
        }
        zzbnp = false;
    }

    private static zzwu<Double> zza(zzxe zzxe, String str, double d) {
        return new zzxb(zzxe, str, Double.valueOf(d));
    }

    private static zzwu<Integer> zza(zzxe zzxe, String str, int i) {
        return new zzwz(zzxe, str, Integer.valueOf(i));
    }

    private static zzwu<Long> zza(zzxe zzxe, String str, long j) {
        return new zzwy(zzxe, str, Long.valueOf(j));
    }

    private static zzwu<String> zza(zzxe zzxe, String str, String str2) {
        return new zzxc(zzxe, str, str2);
    }

    private static zzwu<Boolean> zza(zzxe zzxe, String str, boolean z) {
        return new zzxa(zzxe, str, Boolean.valueOf(z));
    }

    private static <V> V zza(zzxd<V> zzxd) {
        V zzsl;
        long clearCallingIdentity;
        try {
            zzsl = zzxd.zzsl();
        } catch (SecurityException e) {
            clearCallingIdentity = Binder.clearCallingIdentity();
            zzsl = zzxd.zzsl();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
        return zzsl;
    }

    static boolean zzd(String str, boolean z) {
        try {
            return zzsj() ? ((Boolean) zza(new zzwx(str, false))).booleanValue() : false;
        } catch (Throwable e) {
            Log.e("PhenotypeFlag", "Unable to read GServices, returning default value.", e);
            return false;
        }
    }

    @TargetApi(24)
    @Nullable
    private final T zzsh() {
        String valueOf;
        if (zzd("gms:phenotype:phenotype_flag:debug_bypass_phenotype", false)) {
            String str = "PhenotypeFlag";
            String str2 = "Bypass reading Phenotype values for flag: ";
            valueOf = String.valueOf(this.zzbns);
            Log.w(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
        } else if (this.zzbnr.zzbod != null) {
            if (this.zzbnw == null) {
                this.zzbnw = zzwr.zza(zzqx.getContentResolver(), this.zzbnr.zzbod);
            }
            valueOf = (String) zza(new zzwv(this, this.zzbnw));
            if (valueOf != null) {
                return zzex(valueOf);
            }
        } else {
            zzxe zzxe = this.zzbnr;
        }
        return null;
    }

    @Nullable
    private final T zzsi() {
        zzxe zzxe = this.zzbnr;
        if (zzsj()) {
            String str;
            try {
                str = (String) zza(new zzww(this));
                if (str != null) {
                    return zzex(str);
                }
            } catch (Throwable e) {
                Throwable th = e;
                String str2 = "PhenotypeFlag";
                String str3 = "Unable to read GServices for flag: ";
                str = String.valueOf(this.zzbns);
                Log.e(str2, str.length() != 0 ? str3.concat(str) : new String(str3), th);
            }
        }
        return null;
    }

    private static boolean zzsj() {
        boolean z = false;
        if (zzbnq == null) {
            if (zzqx == null) {
                return false;
            }
            if (C0428o.m1909b(zzqx, "com.google.android.providers.gsf.permission.READ_GSERVICES") == 0) {
                z = true;
            }
            zzbnq = Boolean.valueOf(z);
        }
        return zzbnq.booleanValue();
    }

    public final T get() {
        if (zzqx == null) {
            throw new IllegalStateException("Must call PhenotypeFlag.init() first");
        }
        zzxe zzxe = this.zzbnr;
        T zzsh = zzsh();
        if (zzsh != null) {
            return zzsh;
        }
        zzsh = zzsi();
        return zzsh == null ? this.zzbnu : zzsh;
    }

    protected abstract T zzex(String str);

    final /* synthetic */ String zzsk() {
        return zzwp.zza(zzqx.getContentResolver(), this.zzbnt, null);
    }
}
