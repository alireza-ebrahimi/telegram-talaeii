package com.google.android.gms.phenotype;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Binder;
import android.os.Build.VERSION;
import android.os.UserManager;
import android.support.v4.content.PermissionChecker;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.internal.zzdnm;
import com.google.android.gms.internal.zzdob;

@KeepForSdk
@Deprecated
public abstract class PhenotypeFlag<T> {
    @SuppressLint({"StaticFieldLeak"})
    private static Context zzaiq = null;
    private static boolean zzciw = false;
    private static final Object zzkgs = new Object();
    private static Boolean zzkgt = null;
    private final T zzinq;
    private final Factory zzkgu;
    final String zzkgv;
    private final String zzkgw;
    private T zzkgx;

    @KeepForSdk
    public static class Factory {
        private final String zzkhb;
        private final Uri zzkhc;
        private final String zzkhd;
        private final String zzkhe;
        private final boolean zzkhf;
        private final boolean zzkhg;

        @KeepForSdk
        public Factory(Uri uri) {
            this(null, uri, "", "", false, false);
        }

        private Factory(String str, Uri uri, String str2, String str3, boolean z, boolean z2) {
            this.zzkhb = str;
            this.zzkhc = uri;
            this.zzkhd = str2;
            this.zzkhe = str3;
            this.zzkhf = z;
            this.zzkhg = z2;
        }

        @KeepForSdk
        public PhenotypeFlag<String> createFlag(String str, String str2) {
            return PhenotypeFlag.zza(this, str, str2);
        }

        @KeepForSdk
        public Factory withGservicePrefix(String str) {
            if (this.zzkhf) {
                throw new IllegalStateException("Cannot set GServices prefix and skip GServices");
            }
            return new Factory(this.zzkhb, this.zzkhc, str, this.zzkhe, this.zzkhf, this.zzkhg);
        }

        @KeepForSdk
        public Factory withPhenotypePrefix(String str) {
            return new Factory(this.zzkhb, this.zzkhc, this.zzkhd, str, this.zzkhf, this.zzkhg);
        }
    }

    interface zza<V> {
        V zzbel();
    }

    private PhenotypeFlag(Factory factory, String str, T t) {
        this.zzkgx = null;
        if (factory.zzkhb == null && factory.zzkhc == null) {
            throw new IllegalArgumentException("Must pass a valid SharedPreferences file name or ContentProvider URI");
        } else if (factory.zzkhb == null || factory.zzkhc == null) {
            this.zzkgu = factory;
            String valueOf = String.valueOf(factory.zzkhd);
            String valueOf2 = String.valueOf(str);
            this.zzkgw = valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
            valueOf = String.valueOf(factory.zzkhe);
            valueOf2 = String.valueOf(str);
            this.zzkgv = valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
            this.zzinq = t;
        } else {
            throw new IllegalArgumentException("Must pass one of SharedPreferences file name or ContentProvider URI");
        }
    }

    @KeepForSdk
    public static void maybeInit(Context context) {
        zzdob.maybeInit(context);
        if (zzaiq == null) {
            zzdob.zzch(context);
            synchronized (zzkgs) {
                if (VERSION.SDK_INT < 24 || !context.isDeviceProtectedStorage()) {
                    Context applicationContext = context.getApplicationContext();
                    if (applicationContext != null) {
                        context = applicationContext;
                    }
                }
                if (zzaiq != context) {
                    zzkgt = null;
                }
                zzaiq = context;
            }
            zzciw = false;
        }
    }

    private static PhenotypeFlag<String> zza(Factory factory, String str, String str2) {
        return new zzs(factory, str, str2);
    }

    private static <V> V zza(zza<V> zza) {
        V zzbel;
        long clearCallingIdentity;
        try {
            zzbel = zza.zzbel();
        } catch (SecurityException e) {
            clearCallingIdentity = Binder.clearCallingIdentity();
            zzbel = zza.zzbel();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
        return zzbel;
    }

    @TargetApi(24)
    private final T zzbeh() {
        String valueOf;
        if (zzh("gms:phenotype:phenotype_flag:debug_bypass_phenotype", false)) {
            String str = "PhenotypeFlag";
            String str2 = "Bypass reading Phenotype values for flag: ";
            valueOf = String.valueOf(this.zzkgv);
            Log.w(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
        } else if (this.zzkgu.zzkhc != null) {
            valueOf = (String) zza(new zzo(this, zza.zza(zzaiq.getContentResolver(), this.zzkgu.zzkhc)));
            if (valueOf != null) {
                return zzkz(valueOf);
            }
        } else if (this.zzkgu.zzkhb != null) {
            if (VERSION.SDK_INT >= 24 && !zzaiq.isDeviceProtectedStorage() && !((UserManager) zzaiq.getSystemService(UserManager.class)).isUserUnlocked()) {
                return null;
            }
            SharedPreferences sharedPreferences = zzaiq.getSharedPreferences(this.zzkgu.zzkhb, 0);
            if (sharedPreferences.contains(this.zzkgv)) {
                return zzb(sharedPreferences);
            }
        }
        return null;
    }

    private final T zzbei() {
        if (!this.zzkgu.zzkhf && zzbej()) {
            String str = (String) zza(new zzp(this));
            if (str != null) {
                return zzkz(str);
            }
        }
        return null;
    }

    private static boolean zzbej() {
        boolean z = false;
        if (zzkgt == null) {
            if (zzaiq == null) {
                return false;
            }
            if (PermissionChecker.checkCallingOrSelfPermission(zzaiq, "com.google.android.providers.gsf.permission.READ_GSERVICES") == 0) {
                z = true;
            }
            zzkgt = Boolean.valueOf(z);
        }
        return zzkgt.booleanValue();
    }

    static boolean zzh(String str, boolean z) {
        return zzbej() ? ((Boolean) zza(new zzq(str, false))).booleanValue() : false;
    }

    @KeepForSdk
    public T get() {
        if (zzaiq == null) {
            throw new IllegalStateException("Must call PhenotypeFlag.init() first");
        }
        T zzbei;
        if (this.zzkgu.zzkhg) {
            zzbei = zzbei();
            if (zzbei != null) {
                return zzbei;
            }
            zzbei = zzbeh();
            if (zzbei != null) {
                return zzbei;
            }
        }
        zzbei = zzbeh();
        if (zzbei != null) {
            return zzbei;
        }
        zzbei = zzbei();
        if (zzbei != null) {
            return zzbei;
        }
        return this.zzinq;
    }

    public abstract T zzb(SharedPreferences sharedPreferences);

    final /* synthetic */ String zzbek() {
        return zzdnm.zza(zzaiq.getContentResolver(), this.zzkgw, null);
    }

    public abstract T zzkz(String str);
}
