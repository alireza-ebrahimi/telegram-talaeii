package com.google.android.gms.common.config;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Binder;
import android.os.Process;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.os.UserManager;
import android.util.Log;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.internal.stable.zzg;
import com.google.android.gms.internal.stable.zzi;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import javax.annotation.concurrent.GuardedBy;

public abstract class GservicesValue<T> {
    private static final Object sLock = new Object();
    private static zza zzmu = null;
    private static int zzmv = 0;
    private static Context zzmw;
    private static String zzmx = "com.google.android.providers.gsf.permission.READ_GSERVICES";
    @GuardedBy("sLock")
    private static HashSet<String> zzmy;
    protected final T mDefaultValue;
    protected final String mKey;
    private T zzmz = null;

    private interface zza {
        Long getLong(String str, Long l);

        String getString(String str, String str2);

        Boolean zza(String str, Boolean bool);

        Double zza(String str, Double d);

        Float zza(String str, Float f);

        Integer zza(String str, Integer num);

        String zzb(String str, String str2);
    }

    private static class zzb implements zza {
        private static final Collection<GservicesValue<?>> zzna = new HashSet();

        private zzb() {
        }

        public final Long getLong(String str, Long l) {
            return l;
        }

        public final String getString(String str, String str2) {
            return str2;
        }

        public final Boolean zza(String str, Boolean bool) {
            return bool;
        }

        public final Double zza(String str, Double d) {
            return d;
        }

        public final Float zza(String str, Float f) {
            return f;
        }

        public final Integer zza(String str, Integer num) {
            return num;
        }

        public final String zzb(String str, String str2) {
            return str2;
        }
    }

    @Deprecated
    private static class zzc implements zza {
        private final Map<String, ?> values;

        public zzc(Map<String, ?> map) {
            this.values = map;
        }

        private final <T> T zza(String str, T t) {
            return this.values.containsKey(str) ? this.values.get(str) : t;
        }

        public final Long getLong(String str, Long l) {
            return (Long) zza(str, (Object) l);
        }

        public final String getString(String str, String str2) {
            return (String) zza(str, (Object) str2);
        }

        public final Boolean zza(String str, Boolean bool) {
            return (Boolean) zza(str, (Object) bool);
        }

        public final Double zza(String str, Double d) {
            return (Double) zza(str, (Object) d);
        }

        public final Float zza(String str, Float f) {
            return (Float) zza(str, (Object) f);
        }

        public final Integer zza(String str, Integer num) {
            return (Integer) zza(str, (Object) num);
        }

        public final String zzb(String str, String str2) {
            return (String) zza(str, (Object) str2);
        }
    }

    private static class zzd implements zza {
        private final ContentResolver mContentResolver;

        public zzd(ContentResolver contentResolver) {
            this.mContentResolver = contentResolver;
        }

        public final Long getLong(String str, Long l) {
            return Long.valueOf(zzi.getLong(this.mContentResolver, str, l.longValue()));
        }

        public final String getString(String str, String str2) {
            return zzi.zza(this.mContentResolver, str, str2);
        }

        public final Boolean zza(String str, Boolean bool) {
            return Boolean.valueOf(zzi.zza(this.mContentResolver, str, bool.booleanValue()));
        }

        public final Double zza(String str, Double d) {
            String zza = zzi.zza(this.mContentResolver, str, null);
            if (zza != null) {
                try {
                    d = Double.valueOf(Double.parseDouble(zza));
                } catch (NumberFormatException e) {
                }
            }
            return d;
        }

        public final Float zza(String str, Float f) {
            String zza = zzi.zza(this.mContentResolver, str, null);
            if (zza != null) {
                try {
                    f = Float.valueOf(Float.parseFloat(zza));
                } catch (NumberFormatException e) {
                }
            }
            return f;
        }

        public final Integer zza(String str, Integer num) {
            return Integer.valueOf(zzi.getInt(this.mContentResolver, str, num.intValue()));
        }

        public final String zzb(String str, String str2) {
            return zzg.zza(this.mContentResolver, str, str2);
        }
    }

    protected GservicesValue(String str, T t) {
        this.mKey = str;
        this.mDefaultValue = t;
    }

    @Deprecated
    @VisibleForTesting
    public static void forceInit(Context context) {
        forceInit(context, new HashSet());
    }

    @VisibleForTesting
    public static void forceInit(Context context, HashSet<String> hashSet) {
        zza(context, new zzd(context.getContentResolver()), hashSet);
    }

    @TargetApi(24)
    public static SharedPreferences getDirectBootCache(Context context) {
        return context.getApplicationContext().createDeviceProtectedStorageContext().getSharedPreferences("gservices-direboot-cache", 0);
    }

    public static int getSharedUserId() {
        return zzmv;
    }

    @Deprecated
    public static void init(Context context) {
        init(context, zzd(context) ? new HashSet() : null);
    }

    public static void init(Context context, HashSet<String> hashSet) {
        synchronized (sLock) {
            if (zzmu == null) {
                zza(context, new zzd(context.getContentResolver()), hashSet);
            }
            if (zzmv == 0) {
                try {
                    zzmv = context.getPackageManager().getApplicationInfo("com.google.android.gms", 0).uid;
                } catch (NameNotFoundException e) {
                    Log.e("GservicesValue", e.toString());
                }
            }
        }
    }

    @Deprecated
    @VisibleForTesting
    public static void initForTests() {
        zza(null, new zzb(), new HashSet());
    }

    @VisibleForTesting
    public static void initForTests(Context context, HashSet<String> hashSet) {
        zza(context, new zzb(), hashSet);
    }

    @Deprecated
    @VisibleForTesting
    public static void initForTests(String str, Object obj) {
        Map hashMap = new HashMap(1);
        hashMap.put(str, obj);
        initForTests(hashMap);
    }

    @Deprecated
    @VisibleForTesting
    public static void initForTests(Map<String, ?> map) {
        synchronized (sLock) {
            zzmu = new zzc(map);
        }
    }

    public static boolean isInitialized() {
        boolean z;
        synchronized (sLock) {
            z = zzmu != null;
        }
        return z;
    }

    public static GservicesValue<String> partnerSetting(String str, String str2) {
        return new zzg(str, str2);
    }

    @VisibleForTesting
    public static void resetAllOverrides() {
        synchronized (sLock) {
            if (zzcg()) {
                for (GservicesValue resetOverride : zzb.zzna) {
                    resetOverride.resetOverride();
                }
                zzb.zzna.clear();
            }
        }
    }

    public static GservicesValue<Double> value(String str, Double d) {
        return new zzd(str, d);
    }

    public static GservicesValue<Float> value(String str, Float f) {
        return new zze(str, f);
    }

    public static GservicesValue<Integer> value(String str, Integer num) {
        return new zzc(str, num);
    }

    public static GservicesValue<Long> value(String str, Long l) {
        return new zzb(str, l);
    }

    public static GservicesValue<String> value(String str, String str2) {
        return new zzf(str, str2);
    }

    public static GservicesValue<Boolean> value(String str, boolean z) {
        return new zza(str, Boolean.valueOf(z));
    }

    @TargetApi(24)
    private static void zza(Context context, zza zza, HashSet<String> hashSet) {
        synchronized (sLock) {
            zzmu = zza;
            zzmy = null;
            zzmw = null;
            if (context != null && zzd(context)) {
                zzmy = hashSet;
                zzmw = context.getApplicationContext().createDeviceProtectedStorageContext();
            }
        }
    }

    private static boolean zzcg() {
        boolean z;
        synchronized (sLock) {
            z = (zzmu instanceof zzb) || (zzmu instanceof zzc);
        }
        return z;
    }

    @TargetApi(24)
    private static boolean zzd(Context context) {
        if (!PlatformVersion.isAtLeastN()) {
            return false;
        }
        UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
        return userManager.isUserUnlocked() ? false : !userManager.isUserRunning(Process.myUserHandle());
    }

    public final T get() {
        T retrieve;
        long clearCallingIdentity;
        if (this.zzmz != null) {
            return this.zzmz;
        }
        ThreadPolicy allowThreadDiskReads = StrictMode.allowThreadDiskReads();
        synchronized (sLock) {
            Object obj = (zzmw == null || !zzd(zzmw)) ? null : 1;
            HashSet hashSet = zzmy;
            Context context = zzmw;
        }
        if (obj != null) {
            String str;
            String str2;
            String valueOf;
            if (Log.isLoggable("GservicesValue", 3)) {
                str = "GservicesValue";
                str2 = "Gservice value accessed during directboot: ";
                valueOf = String.valueOf(this.mKey);
                Log.d(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
            }
            if (hashSet == null || hashSet.contains(this.mKey)) {
                return retrieveFromDirectBootCache(context, this.mKey, this.mDefaultValue);
            }
            str = "GservicesValue";
            str2 = "Gservices key not whitelisted for directboot access: ";
            valueOf = String.valueOf(this.mKey);
            Log.e(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
            return this.mDefaultValue;
        }
        synchronized (sLock) {
            zzmy = null;
            zzmw = null;
        }
        try {
            retrieve = retrieve(this.mKey);
            StrictMode.setThreadPolicy(allowThreadDiskReads);
            return retrieve;
        } catch (SecurityException e) {
            clearCallingIdentity = Binder.clearCallingIdentity();
            retrieve = retrieve(this.mKey);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            StrictMode.setThreadPolicy(allowThreadDiskReads);
            return retrieve;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Deprecated
    public final T getBinderSafe() {
        return get();
    }

    public String getKey() {
        return this.mKey;
    }

    @VisibleForTesting
    public void override(T t) {
        if (!(zzmu instanceof zzb)) {
            Log.w("GservicesValue", "GservicesValue.override(): test should probably call initForTests() first");
        }
        this.zzmz = t;
        synchronized (sLock) {
            if (zzcg()) {
                zzb.zzna.add(this);
            }
        }
    }

    @VisibleForTesting
    public void resetOverride() {
        this.zzmz = null;
    }

    protected abstract T retrieve(String str);

    @TargetApi(24)
    protected T retrieveFromDirectBootCache(Context context, String str, T t) {
        throw new UnsupportedOperationException("The Gservices classes used does not support direct-boot");
    }
}
