package com.google.android.gms.stats;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.PowerManager;
import android.os.WorkSource;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.providers.PooledExecutorsProvider;
import com.google.android.gms.common.util.Strings;
import com.google.android.gms.common.util.WorkSourceUtil;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class WakeLock {
    private static ScheduledExecutorService zzaeg;
    private static Configuration zzaeh = new zza();
    private final android.os.PowerManager.WakeLock zzadv;
    private WorkSource zzadw;
    private String zzadx;
    private final int zzady;
    private final String zzadz;
    private final String zzaea;
    private final String zzaeb;
    private boolean zzaec;
    private final Map<String, Integer[]> zzaed;
    private int zzaee;
    private AtomicInteger zzaef;
    private final Context zzjp;

    public interface Configuration {
        long getMaximumTimeout(String str, String str2);

        boolean isWorkChainsEnabled();
    }

    public class HeldLock {
        private final /* synthetic */ WakeLock zzaei;
        private boolean zzaek;
        private Future zzael;
        private final String zzaem;

        private HeldLock(WakeLock wakeLock, String str) {
            this.zzaei = wakeLock;
            this.zzaek = true;
            this.zzaem = str;
        }

        public void finalize() {
            if (this.zzaek) {
                String str = "WakeLock";
                String str2 = "HeldLock finalized while still holding the WakeLock! Reason: ";
                String valueOf = String.valueOf(this.zzaem);
                Log.e(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
                release(0);
            }
        }

        public void release() {
            release(0);
        }

        public synchronized void release(int i) {
            if (this.zzaek) {
                this.zzaek = false;
                if (this.zzael != null) {
                    this.zzael.cancel(false);
                    this.zzael = null;
                }
                this.zzaei.zzc(this.zzaem, i);
            }
        }
    }

    public WakeLock(Context context, int i, @Nonnull String str) {
        this(context, i, str, null, context == null ? null : context.getPackageName());
    }

    public WakeLock(Context context, int i, @Nonnull String str, @Nullable String str2) {
        this(context, i, str, str2, context == null ? null : context.getPackageName());
    }

    @SuppressLint({"UnwrappedWakeLock"})
    public WakeLock(Context context, int i, @Nonnull String str, @Nullable String str2, @Nonnull String str3) {
        this(context, i, str, str2, str3, null);
    }

    @SuppressLint({"UnwrappedWakeLock"})
    public WakeLock(Context context, int i, @Nonnull String str, @Nullable String str2, @Nonnull String str3, @Nullable String str4) {
        this.zzaec = true;
        this.zzaed = new HashMap();
        this.zzaef = new AtomicInteger(0);
        Preconditions.checkNotEmpty(str, "Wake lock name can NOT be empty");
        this.zzady = i;
        this.zzaea = str2;
        this.zzaeb = str4;
        this.zzjp = context.getApplicationContext();
        if ("com.google.android.gms".equals(context.getPackageName())) {
            this.zzadz = str;
        } else {
            String valueOf = String.valueOf("*gcore*:");
            String valueOf2 = String.valueOf(str);
            this.zzadz = valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
        }
        this.zzadv = ((PowerManager) context.getSystemService("power")).newWakeLock(i, str);
        if (WorkSourceUtil.hasWorkSourcePermission(context)) {
            if (Strings.isEmptyOrWhitespace(str3)) {
                str3 = context.getPackageName();
            }
            if (!zzaeh.isWorkChainsEnabled() || str3 == null || str4 == null) {
                this.zzadw = WorkSourceUtil.fromPackage(context, str3);
            } else {
                Log.d("WakeLock", new StringBuilder((String.valueOf(str3).length() + 42) + String.valueOf(str4).length()).append("Using experimental Pi WorkSource chains: ").append(str3).append(",").append(str4).toString());
                this.zzadx = str3;
                this.zzadw = WorkSourceUtil.fromPackageAndModuleExperimentalPi(context, str3, str4);
            }
            addWorkSource(this.zzadw);
        }
        if (zzaeg == null) {
            zzaeg = PooledExecutorsProvider.getInstance().newSingleThreadScheduledExecutor();
        }
    }

    public static void setConfiguration(Configuration configuration) {
        zzaeh = configuration;
    }

    private final void zza(WorkSource workSource) {
        RuntimeException e;
        try {
            this.zzadv.setWorkSource(workSource);
            return;
        } catch (IllegalArgumentException e2) {
            e = e2;
        } catch (ArrayIndexOutOfBoundsException e3) {
            e = e3;
        }
        Log.wtf("WakeLock", e.toString());
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    @android.annotation.SuppressLint({"WakelockTimeout"})
    private final void zza(java.lang.String r13, long r14) {
        /*
        r12 = this;
        r1 = 1;
        r2 = 0;
        r6 = r12.zzn(r13);
        monitor-enter(r12);
        r0 = r12.zzaed;	 Catch:{ all -> 0x00bb }
        r0 = r0.isEmpty();	 Catch:{ all -> 0x00bb }
        if (r0 == 0) goto L_0x0013;
    L_0x000f:
        r0 = r12.zzaee;	 Catch:{ all -> 0x00bb }
        if (r0 <= 0) goto L_0x0023;
    L_0x0013:
        r0 = r12.zzadv;	 Catch:{ all -> 0x00bb }
        r0 = r0.isHeld();	 Catch:{ all -> 0x00bb }
        if (r0 != 0) goto L_0x0023;
    L_0x001b:
        r0 = r12.zzaed;	 Catch:{ all -> 0x00bb }
        r0.clear();	 Catch:{ all -> 0x00bb }
        r0 = 0;
        r12.zzaee = r0;	 Catch:{ all -> 0x00bb }
    L_0x0023:
        r0 = r12.zzaec;	 Catch:{ all -> 0x00bb }
        if (r0 == 0) goto L_0x0044;
    L_0x0027:
        r0 = r12.zzaed;	 Catch:{ all -> 0x00bb }
        r0 = r0.get(r6);	 Catch:{ all -> 0x00bb }
        r0 = (java.lang.Integer[]) r0;	 Catch:{ all -> 0x00bb }
        if (r0 != 0) goto L_0x00a9;
    L_0x0031:
        r0 = r12.zzaed;	 Catch:{ all -> 0x00bb }
        r2 = 1;
        r2 = new java.lang.Integer[r2];	 Catch:{ all -> 0x00bb }
        r3 = 0;
        r4 = 1;
        r4 = java.lang.Integer.valueOf(r4);	 Catch:{ all -> 0x00bb }
        r2[r3] = r4;	 Catch:{ all -> 0x00bb }
        r0.put(r6, r2);	 Catch:{ all -> 0x00bb }
        r0 = r1;
    L_0x0042:
        if (r0 != 0) goto L_0x004c;
    L_0x0044:
        r0 = r12.zzaec;	 Catch:{ all -> 0x00bb }
        if (r0 != 0) goto L_0x006d;
    L_0x0048:
        r0 = r12.zzaee;	 Catch:{ all -> 0x00bb }
        if (r0 != 0) goto L_0x006d;
    L_0x004c:
        r1 = com.google.android.gms.common.stats.WakeLockTracker.getInstance();	 Catch:{ all -> 0x00bb }
        r2 = r12.zzjp;	 Catch:{ all -> 0x00bb }
        r0 = r12.zzadv;	 Catch:{ all -> 0x00bb }
        r3 = com.google.android.gms.common.stats.StatsUtils.getEventKey(r0, r6);	 Catch:{ all -> 0x00bb }
        r4 = 7;
        r5 = r12.zzadz;	 Catch:{ all -> 0x00bb }
        r7 = r12.zzaeb;	 Catch:{ all -> 0x00bb }
        r8 = r12.zzady;	 Catch:{ all -> 0x00bb }
        r9 = r12.zzdo();	 Catch:{ all -> 0x00bb }
        r10 = r14;
        r1.registerEvent(r2, r3, r4, r5, r6, r7, r8, r9, r10);	 Catch:{ all -> 0x00bb }
        r0 = r12.zzaee;	 Catch:{ all -> 0x00bb }
        r0 = r0 + 1;
        r12.zzaee = r0;	 Catch:{ all -> 0x00bb }
    L_0x006d:
        monitor-exit(r12);	 Catch:{ all -> 0x00bb }
        r0 = r12.zzadv;
        r0.acquire();
        r0 = 0;
        r0 = (r14 > r0 ? 1 : (r14 == r0 ? 0 : -1));
        if (r0 <= 0) goto L_0x00a8;
    L_0x0079:
        r0 = zzaeg;
        r1 = new com.google.android.gms.stats.zzb;
        r1.<init>(r12);
        r2 = java.util.concurrent.TimeUnit.MILLISECONDS;
        r0.schedule(r1, r14, r2);
        r0 = com.google.android.gms.common.util.PlatformVersion.isAtLeastIceCreamSandwich();
        if (r0 != 0) goto L_0x00a8;
    L_0x008b:
        r0 = r12.zzaec;
        if (r0 == 0) goto L_0x00a8;
    L_0x008f:
        r1 = "WakeLock";
        r2 = "Do not acquire with timeout on reference counted wakeLocks before ICS. wakelock: ";
        r0 = r12.zzadz;
        r0 = java.lang.String.valueOf(r0);
        r3 = r0.length();
        if (r3 == 0) goto L_0x00be;
    L_0x00a1:
        r0 = r2.concat(r0);
    L_0x00a5:
        android.util.Log.wtf(r1, r0);
    L_0x00a8:
        return;
    L_0x00a9:
        r1 = 0;
        r3 = 0;
        r3 = r0[r3];	 Catch:{ all -> 0x00bb }
        r3 = r3.intValue();	 Catch:{ all -> 0x00bb }
        r3 = r3 + 1;
        r3 = java.lang.Integer.valueOf(r3);	 Catch:{ all -> 0x00bb }
        r0[r1] = r3;	 Catch:{ all -> 0x00bb }
        r0 = r2;
        goto L_0x0042;
    L_0x00bb:
        r0 = move-exception;
        monitor-exit(r12);	 Catch:{ all -> 0x00bb }
        throw r0;
    L_0x00be:
        r0 = new java.lang.String;
        r0.<init>(r2);
        goto L_0x00a5;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.stats.WakeLock.zza(java.lang.String, long):void");
    }

    private final void zzb(String str, int i) {
        if (this.zzaef.decrementAndGet() < 0) {
            Log.e("WakeLock", "release without a matched acquire!");
        }
        zzc(str, i);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void zzc(java.lang.String r10, int r11) {
        /*
        r9 = this;
        r2 = 1;
        r1 = 0;
        r5 = r9.zzn(r10);
        monitor-enter(r9);
        r0 = r9.zzaec;	 Catch:{ all -> 0x0068 }
        if (r0 == 0) goto L_0x0018;
    L_0x000b:
        r0 = r9.zzaed;	 Catch:{ all -> 0x0068 }
        r0 = r0.get(r5);	 Catch:{ all -> 0x0068 }
        r0 = (java.lang.Integer[]) r0;	 Catch:{ all -> 0x0068 }
        if (r0 != 0) goto L_0x0046;
    L_0x0015:
        r0 = r1;
    L_0x0016:
        if (r0 != 0) goto L_0x0020;
    L_0x0018:
        r0 = r9.zzaec;	 Catch:{ all -> 0x0068 }
        if (r0 != 0) goto L_0x0041;
    L_0x001c:
        r0 = r9.zzaee;	 Catch:{ all -> 0x0068 }
        if (r0 != r2) goto L_0x0041;
    L_0x0020:
        r0 = com.google.android.gms.common.stats.WakeLockTracker.getInstance();	 Catch:{ all -> 0x0068 }
        r1 = r9.zzjp;	 Catch:{ all -> 0x0068 }
        r2 = r9.zzadv;	 Catch:{ all -> 0x0068 }
        r2 = com.google.android.gms.common.stats.StatsUtils.getEventKey(r2, r5);	 Catch:{ all -> 0x0068 }
        r3 = 8;
        r4 = r9.zzadz;	 Catch:{ all -> 0x0068 }
        r6 = r9.zzaeb;	 Catch:{ all -> 0x0068 }
        r7 = r9.zzady;	 Catch:{ all -> 0x0068 }
        r8 = r9.zzdo();	 Catch:{ all -> 0x0068 }
        r0.registerEvent(r1, r2, r3, r4, r5, r6, r7, r8);	 Catch:{ all -> 0x0068 }
        r0 = r9.zzaee;	 Catch:{ all -> 0x0068 }
        r0 = r0 + -1;
        r9.zzaee = r0;	 Catch:{ all -> 0x0068 }
    L_0x0041:
        monitor-exit(r9);	 Catch:{ all -> 0x0068 }
        r9.zzn(r11);
        return;
    L_0x0046:
        r3 = 0;
        r3 = r0[r3];	 Catch:{ all -> 0x0068 }
        r3 = r3.intValue();	 Catch:{ all -> 0x0068 }
        if (r3 != r2) goto L_0x0056;
    L_0x004f:
        r0 = r9.zzaed;	 Catch:{ all -> 0x0068 }
        r0.remove(r5);	 Catch:{ all -> 0x0068 }
        r0 = r2;
        goto L_0x0016;
    L_0x0056:
        r3 = 0;
        r4 = 0;
        r4 = r0[r4];	 Catch:{ all -> 0x0068 }
        r4 = r4.intValue();	 Catch:{ all -> 0x0068 }
        r4 = r4 + -1;
        r4 = java.lang.Integer.valueOf(r4);	 Catch:{ all -> 0x0068 }
        r0[r3] = r4;	 Catch:{ all -> 0x0068 }
        r0 = r1;
        goto L_0x0016;
    L_0x0068:
        r0 = move-exception;
        monitor-exit(r9);	 Catch:{ all -> 0x0068 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.stats.WakeLock.zzc(java.lang.String, int):void");
    }

    private final List<String> zzdo() {
        Object names = WorkSourceUtil.getNames(this.zzadw);
        if (this.zzadx == null) {
            return names;
        }
        List<String> arrayList = new ArrayList(names);
        arrayList.add(this.zzadx);
        return arrayList;
    }

    private final String zzn(String str) {
        return this.zzaec ? !TextUtils.isEmpty(str) ? str : this.zzaea : this.zzaea;
    }

    private final void zzn(int i) {
        if (this.zzadv.isHeld()) {
            try {
                if (VERSION.SDK_INT < 21 || i <= 0) {
                    this.zzadv.release();
                } else {
                    this.zzadv.release(i);
                }
            } catch (Throwable e) {
                if (e.getClass().equals(RuntimeException.class)) {
                    Log.e("WakeLock", String.valueOf(this.zzadz).concat(" was already released!"), e);
                    return;
                }
                throw e;
            }
        }
    }

    public void acquire() {
        this.zzaef.incrementAndGet();
        zza(null, 0);
    }

    public void acquire(long j) {
        this.zzaef.incrementAndGet();
        zza(null, j);
    }

    public void acquire(String str) {
        this.zzaef.incrementAndGet();
        zza(str, 0);
    }

    public void acquire(String str, long j) {
        this.zzaef.incrementAndGet();
        zza(str, j);
    }

    public HeldLock acquireLock(long j, String str) {
        long min = Math.min(j, zzaeh.getMaximumTimeout(this.zzadz, str));
        HeldLock heldLock = new HeldLock(str);
        zza(str, 0);
        heldLock.zzael = zzaeg.schedule(new zzc(new WeakReference(heldLock)), min, TimeUnit.MILLISECONDS);
        return heldLock;
    }

    public void addWorkSource(WorkSource workSource) {
        if (workSource != null && WorkSourceUtil.hasWorkSourcePermission(this.zzjp)) {
            if (this.zzadw != null) {
                this.zzadw.add(workSource);
            } else {
                this.zzadw = workSource;
            }
            zza(this.zzadw);
        }
    }

    public android.os.PowerManager.WakeLock getWakeLock() {
        return this.zzadv;
    }

    public boolean isHeld() {
        return this.zzadv.isHeld();
    }

    public void release() {
        zzb(null, 0);
    }

    public void release(int i) {
        zzb(null, i);
    }

    public void release(String str) {
        zzb(str, 0);
    }

    public void release(String str, int i) {
        zzb(str, i);
    }

    public void removeWorkSource(WorkSource workSource) {
        if (workSource != null && WorkSourceUtil.hasWorkSourcePermission(this.zzjp)) {
            try {
                if (this.zzadw != null) {
                    this.zzadw.remove(workSource);
                }
                zza(this.zzadw);
            } catch (ArrayIndexOutOfBoundsException e) {
                Log.e("WakeLock", e.toString());
            }
        }
    }

    public void setReferenceCounted(boolean z) {
        this.zzadv.setReferenceCounted(z);
        this.zzaec = z;
    }

    public void setWorkSource(WorkSource workSource) {
        if (WorkSourceUtil.hasWorkSourcePermission(this.zzjp)) {
            zza(workSource);
            this.zzadw = workSource;
            this.zzadx = null;
        }
    }
}
