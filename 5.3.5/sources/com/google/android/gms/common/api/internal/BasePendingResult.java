package com.google.android.gms.common.api.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Pair;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultTransform;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.TransformedResult;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzaq;
import com.google.android.gms.common.internal.zzbq;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@KeepName
@Hide
public abstract class BasePendingResult<R extends Result> extends PendingResult<R> {
    static final ThreadLocal<Boolean> zzfvb = new zzs();
    @KeepName
    private zzb mResultGuardian;
    private Status mStatus;
    private boolean zzam;
    private final CountDownLatch zzapc;
    private R zzftm;
    private final Object zzfvc;
    @Hide
    private zza<R> zzfvd;
    private WeakReference<GoogleApiClient> zzfve;
    private final ArrayList<com.google.android.gms.common.api.PendingResult.zza> zzfvf;
    private ResultCallback<? super R> zzfvg;
    private final AtomicReference<zzdn> zzfvh;
    private volatile boolean zzfvi;
    private boolean zzfvj;
    private zzaq zzfvk;
    private volatile zzdh<R> zzfvl;
    private boolean zzfvm;

    @Hide
    public static class zza<R extends Result> extends Handler {
        public zza() {
            this(Looper.getMainLooper());
        }

        public zza(Looper looper) {
            super(looper);
        }

        public final void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    Pair pair = (Pair) message.obj;
                    ResultCallback resultCallback = (ResultCallback) pair.first;
                    Result result = (Result) pair.second;
                    try {
                        resultCallback.onResult(result);
                        return;
                    } catch (RuntimeException e) {
                        BasePendingResult.zzd(result);
                        throw e;
                    }
                case 2:
                    ((BasePendingResult) message.obj).zzv(Status.zzftt);
                    return;
                default:
                    Log.wtf("BasePendingResult", "Don't know how to handle message: " + message.what, new Exception());
                    return;
            }
        }

        public final void zza(ResultCallback<? super R> resultCallback, R r) {
            sendMessage(obtainMessage(1, new Pair(resultCallback, r)));
        }
    }

    final class zzb {
        private /* synthetic */ BasePendingResult zzfvn;

        private zzb(BasePendingResult basePendingResult) {
            this.zzfvn = basePendingResult;
        }

        protected final void finalize() throws Throwable {
            BasePendingResult.zzd(this.zzfvn.zzftm);
            super.finalize();
        }
    }

    @Deprecated
    BasePendingResult() {
        this.zzfvc = new Object();
        this.zzapc = new CountDownLatch(1);
        this.zzfvf = new ArrayList();
        this.zzfvh = new AtomicReference();
        this.zzfvm = false;
        this.zzfvd = new zza(Looper.getMainLooper());
        this.zzfve = new WeakReference(null);
    }

    @Deprecated
    protected BasePendingResult(Looper looper) {
        this.zzfvc = new Object();
        this.zzapc = new CountDownLatch(1);
        this.zzfvf = new ArrayList();
        this.zzfvh = new AtomicReference();
        this.zzfvm = false;
        this.zzfvd = new zza(looper);
        this.zzfve = new WeakReference(null);
    }

    protected BasePendingResult(GoogleApiClient googleApiClient) {
        this.zzfvc = new Object();
        this.zzapc = new CountDownLatch(1);
        this.zzfvf = new ArrayList();
        this.zzfvh = new AtomicReference();
        this.zzfvm = false;
        this.zzfvd = new zza(googleApiClient != null ? googleApiClient.getLooper() : Looper.getMainLooper());
        this.zzfve = new WeakReference(googleApiClient);
    }

    private final R get() {
        R r;
        boolean z = true;
        synchronized (this.zzfvc) {
            if (this.zzfvi) {
                z = false;
            }
            zzbq.zza(z, (Object) "Result has already been consumed.");
            zzbq.zza(isReady(), (Object) "Result is not ready.");
            r = this.zzftm;
            this.zzftm = null;
            this.zzfvg = null;
            this.zzfvi = true;
        }
        zzdn zzdn = (zzdn) this.zzfvh.getAndSet(null);
        if (zzdn != null) {
            zzdn.zzc(this);
        }
        return r;
    }

    private final void zzc(R r) {
        this.zzftm = r;
        this.zzfvk = null;
        this.zzapc.countDown();
        this.mStatus = this.zzftm.getStatus();
        if (this.zzam) {
            this.zzfvg = null;
        } else if (this.zzfvg != null) {
            this.zzfvd.removeMessages(2);
            this.zzfvd.zza(this.zzfvg, get());
        } else if (this.zzftm instanceof Releasable) {
            this.mResultGuardian = new zzb();
        }
        ArrayList arrayList = this.zzfvf;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((com.google.android.gms.common.api.PendingResult.zza) obj).zzr(this.mStatus);
        }
        this.zzfvf.clear();
    }

    @Hide
    public static void zzd(Result result) {
        if (result instanceof Releasable) {
            try {
                ((Releasable) result).release();
            } catch (Throwable e) {
                String valueOf = String.valueOf(result);
                Log.w("BasePendingResult", new StringBuilder(String.valueOf(valueOf).length() + 18).append("Unable to release ").append(valueOf).toString(), e);
            }
        }
    }

    public final R await() {
        boolean z = true;
        zzbq.zzgw("await must not be called on the UI thread");
        zzbq.zza(!this.zzfvi, (Object) "Result has already been consumed");
        if (this.zzfvl != null) {
            z = false;
        }
        zzbq.zza(z, (Object) "Cannot await if then() has been called.");
        try {
            this.zzapc.await();
        } catch (InterruptedException e) {
            zzv(Status.zzftr);
        }
        zzbq.zza(isReady(), (Object) "Result is not ready.");
        return get();
    }

    public final R await(long j, TimeUnit timeUnit) {
        boolean z = true;
        if (j > 0) {
            zzbq.zzgw("await must not be called on the UI thread when time is greater than zero.");
        }
        zzbq.zza(!this.zzfvi, (Object) "Result has already been consumed.");
        if (this.zzfvl != null) {
            z = false;
        }
        zzbq.zza(z, (Object) "Cannot await if then() has been called.");
        try {
            if (!this.zzapc.await(j, timeUnit)) {
                zzv(Status.zzftt);
            }
        } catch (InterruptedException e) {
            zzv(Status.zzftr);
        }
        zzbq.zza(isReady(), (Object) "Result is not ready.");
        return get();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void cancel() {
        /*
        r2 = this;
        r1 = r2.zzfvc;
        monitor-enter(r1);
        r0 = r2.zzam;	 Catch:{ all -> 0x0029 }
        if (r0 != 0) goto L_0x000b;
    L_0x0007:
        r0 = r2.zzfvi;	 Catch:{ all -> 0x0029 }
        if (r0 == 0) goto L_0x000d;
    L_0x000b:
        monitor-exit(r1);	 Catch:{ all -> 0x0029 }
    L_0x000c:
        return;
    L_0x000d:
        r0 = r2.zzfvk;	 Catch:{ all -> 0x0029 }
        if (r0 == 0) goto L_0x0016;
    L_0x0011:
        r0 = r2.zzfvk;	 Catch:{ RemoteException -> 0x002c }
        r0.cancel();	 Catch:{ RemoteException -> 0x002c }
    L_0x0016:
        r0 = r2.zzftm;	 Catch:{ all -> 0x0029 }
        zzd(r0);	 Catch:{ all -> 0x0029 }
        r0 = 1;
        r2.zzam = r0;	 Catch:{ all -> 0x0029 }
        r0 = com.google.android.gms.common.api.Status.zzftu;	 Catch:{ all -> 0x0029 }
        r0 = r2.zzb(r0);	 Catch:{ all -> 0x0029 }
        r2.zzc(r0);	 Catch:{ all -> 0x0029 }
        monitor-exit(r1);	 Catch:{ all -> 0x0029 }
        goto L_0x000c;
    L_0x0029:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0029 }
        throw r0;
    L_0x002c:
        r0 = move-exception;
        goto L_0x0016;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.BasePendingResult.cancel():void");
    }

    public boolean isCanceled() {
        boolean z;
        synchronized (this.zzfvc) {
            z = this.zzam;
        }
        return z;
    }

    @Hide
    public final boolean isReady() {
        return this.zzapc.getCount() == 0;
    }

    @Hide
    public final void setResult(R r) {
        boolean z = true;
        synchronized (this.zzfvc) {
            if (this.zzfvj || this.zzam) {
                zzd(r);
                return;
            }
            if (isReady()) {
            }
            zzbq.zza(!isReady(), (Object) "Results have already been set");
            if (this.zzfvi) {
                z = false;
            }
            zzbq.zza(z, (Object) "Result has already been consumed");
            zzc(r);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void setResultCallback(com.google.android.gms.common.api.ResultCallback<? super R> r6) {
        /*
        r5 = this;
        r0 = 1;
        r1 = 0;
        r3 = r5.zzfvc;
        monitor-enter(r3);
        if (r6 != 0) goto L_0x000c;
    L_0x0007:
        r0 = 0;
        r5.zzfvg = r0;	 Catch:{ all -> 0x0029 }
        monitor-exit(r3);	 Catch:{ all -> 0x0029 }
    L_0x000b:
        return;
    L_0x000c:
        r2 = r5.zzfvi;	 Catch:{ all -> 0x0029 }
        if (r2 != 0) goto L_0x002c;
    L_0x0010:
        r2 = r0;
    L_0x0011:
        r4 = "Result has already been consumed.";
        com.google.android.gms.common.internal.zzbq.zza(r2, r4);	 Catch:{ all -> 0x0029 }
        r2 = r5.zzfvl;	 Catch:{ all -> 0x0029 }
        if (r2 != 0) goto L_0x002e;
    L_0x001b:
        r1 = "Cannot set callbacks if then() has been called.";
        com.google.android.gms.common.internal.zzbq.zza(r0, r1);	 Catch:{ all -> 0x0029 }
        r0 = r5.isCanceled();	 Catch:{ all -> 0x0029 }
        if (r0 == 0) goto L_0x0030;
    L_0x0027:
        monitor-exit(r3);	 Catch:{ all -> 0x0029 }
        goto L_0x000b;
    L_0x0029:
        r0 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x0029 }
        throw r0;
    L_0x002c:
        r2 = r1;
        goto L_0x0011;
    L_0x002e:
        r0 = r1;
        goto L_0x001b;
    L_0x0030:
        r0 = r5.isReady();	 Catch:{ all -> 0x0029 }
        if (r0 == 0) goto L_0x0041;
    L_0x0036:
        r0 = r5.zzfvd;	 Catch:{ all -> 0x0029 }
        r1 = r5.get();	 Catch:{ all -> 0x0029 }
        r0.zza(r6, r1);	 Catch:{ all -> 0x0029 }
    L_0x003f:
        monitor-exit(r3);	 Catch:{ all -> 0x0029 }
        goto L_0x000b;
    L_0x0041:
        r5.zzfvg = r6;	 Catch:{ all -> 0x0029 }
        goto L_0x003f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.BasePendingResult.setResultCallback(com.google.android.gms.common.api.ResultCallback):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void setResultCallback(com.google.android.gms.common.api.ResultCallback<? super R> r7, long r8, java.util.concurrent.TimeUnit r10) {
        /*
        r6 = this;
        r0 = 1;
        r1 = 0;
        r3 = r6.zzfvc;
        monitor-enter(r3);
        if (r7 != 0) goto L_0x000c;
    L_0x0007:
        r0 = 0;
        r6.zzfvg = r0;	 Catch:{ all -> 0x0029 }
        monitor-exit(r3);	 Catch:{ all -> 0x0029 }
    L_0x000b:
        return;
    L_0x000c:
        r2 = r6.zzfvi;	 Catch:{ all -> 0x0029 }
        if (r2 != 0) goto L_0x002c;
    L_0x0010:
        r2 = r0;
    L_0x0011:
        r4 = "Result has already been consumed.";
        com.google.android.gms.common.internal.zzbq.zza(r2, r4);	 Catch:{ all -> 0x0029 }
        r2 = r6.zzfvl;	 Catch:{ all -> 0x0029 }
        if (r2 != 0) goto L_0x002e;
    L_0x001b:
        r1 = "Cannot set callbacks if then() has been called.";
        com.google.android.gms.common.internal.zzbq.zza(r0, r1);	 Catch:{ all -> 0x0029 }
        r0 = r6.isCanceled();	 Catch:{ all -> 0x0029 }
        if (r0 == 0) goto L_0x0030;
    L_0x0027:
        monitor-exit(r3);	 Catch:{ all -> 0x0029 }
        goto L_0x000b;
    L_0x0029:
        r0 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x0029 }
        throw r0;
    L_0x002c:
        r2 = r1;
        goto L_0x0011;
    L_0x002e:
        r0 = r1;
        goto L_0x001b;
    L_0x0030:
        r0 = r6.isReady();	 Catch:{ all -> 0x0029 }
        if (r0 == 0) goto L_0x0041;
    L_0x0036:
        r0 = r6.zzfvd;	 Catch:{ all -> 0x0029 }
        r1 = r6.get();	 Catch:{ all -> 0x0029 }
        r0.zza(r7, r1);	 Catch:{ all -> 0x0029 }
    L_0x003f:
        monitor-exit(r3);	 Catch:{ all -> 0x0029 }
        goto L_0x000b;
    L_0x0041:
        r6.zzfvg = r7;	 Catch:{ all -> 0x0029 }
        r0 = r6.zzfvd;	 Catch:{ all -> 0x0029 }
        r4 = r10.toMillis(r8);	 Catch:{ all -> 0x0029 }
        r1 = 2;
        r1 = r0.obtainMessage(r1, r6);	 Catch:{ all -> 0x0029 }
        r0.sendMessageDelayed(r1, r4);	 Catch:{ all -> 0x0029 }
        goto L_0x003f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.BasePendingResult.setResultCallback(com.google.android.gms.common.api.ResultCallback, long, java.util.concurrent.TimeUnit):void");
    }

    @Hide
    public <S extends Result> TransformedResult<S> then(ResultTransform<? super R, ? extends S> resultTransform) {
        TransformedResult<S> then;
        boolean z = true;
        zzbq.zza(!this.zzfvi, (Object) "Result has already been consumed.");
        synchronized (this.zzfvc) {
            zzbq.zza(this.zzfvl == null, (Object) "Cannot call then() twice.");
            zzbq.zza(this.zzfvg == null, (Object) "Cannot call then() if callbacks are set.");
            if (this.zzam) {
                z = false;
            }
            zzbq.zza(z, (Object) "Cannot call then() if result was canceled.");
            this.zzfvm = true;
            this.zzfvl = new zzdh(this.zzfve);
            then = this.zzfvl.then(resultTransform);
            if (isReady()) {
                this.zzfvd.zza(this.zzfvl, get());
            } else {
                this.zzfvg = this.zzfvl;
            }
        }
        return then;
    }

    @Hide
    public final void zza(com.google.android.gms.common.api.PendingResult.zza zza) {
        zzbq.checkArgument(zza != null, "Callback cannot be null.");
        synchronized (this.zzfvc) {
            if (isReady()) {
                zza.zzr(this.mStatus);
            } else {
                this.zzfvf.add(zza);
            }
        }
    }

    @Hide
    public final void zza(zzdn zzdn) {
        this.zzfvh.set(zzdn);
    }

    @Hide
    protected final void zza(zzaq zzaq) {
        synchronized (this.zzfvc) {
            this.zzfvk = zzaq;
        }
    }

    @Hide
    public final Integer zzaid() {
        return null;
    }

    @Hide
    public final boolean zzaip() {
        boolean isCanceled;
        synchronized (this.zzfvc) {
            if (((GoogleApiClient) this.zzfve.get()) == null || !this.zzfvm) {
                cancel();
            }
            isCanceled = isCanceled();
        }
        return isCanceled;
    }

    @Hide
    public final void zzaiq() {
        boolean z = this.zzfvm || ((Boolean) zzfvb.get()).booleanValue();
        this.zzfvm = z;
    }

    @Hide
    @NonNull
    protected abstract R zzb(Status status);

    @Hide
    public final void zzv(Status status) {
        synchronized (this.zzfvc) {
            if (!isReady()) {
                setResult(zzb(status));
                this.zzfvj = true;
            }
        }
    }
}
