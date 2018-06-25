package com.google.android.gms.internal;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zze;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public final class zzcke extends zzcli {
    private static final AtomicLong zzjnk = new AtomicLong(Long.MIN_VALUE);
    private ExecutorService executor;
    private zzcki zzjnb;
    private zzcki zzjnc;
    private final PriorityBlockingQueue<zzckh<?>> zzjnd = new PriorityBlockingQueue();
    private final BlockingQueue<zzckh<?>> zzjne = new LinkedBlockingQueue();
    private final UncaughtExceptionHandler zzjnf = new zzckg(this, "Thread death: Uncaught exception on worker thread");
    private final UncaughtExceptionHandler zzjng = new zzckg(this, "Thread death: Uncaught exception on network thread");
    private final Object zzjnh = new Object();
    private final Semaphore zzjni = new Semaphore(2);
    private volatile boolean zzjnj;

    zzcke(zzckj zzckj) {
        super(zzckj);
    }

    private final void zza(zzckh<?> zzckh) {
        synchronized (this.zzjnh) {
            this.zzjnd.add(zzckh);
            if (this.zzjnb == null) {
                this.zzjnb = new zzcki(this, "Measurement Worker", this.zzjnd);
                this.zzjnb.setUncaughtExceptionHandler(this.zzjnf);
                this.zzjnb.start();
            } else {
                this.zzjnb.zzsl();
            }
        }
    }

    public static boolean zzas() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    public final /* bridge */ /* synthetic */ void zzaxz() {
        super.zzaxz();
    }

    public final void zzaya() {
        if (Thread.currentThread() != this.zzjnc) {
            throw new IllegalStateException("Call expected from network thread");
        }
    }

    public final /* bridge */ /* synthetic */ zzcia zzayb() {
        return super.zzayb();
    }

    public final /* bridge */ /* synthetic */ zzcih zzayc() {
        return super.zzayc();
    }

    public final /* bridge */ /* synthetic */ zzclk zzayd() {
        return super.zzayd();
    }

    public final /* bridge */ /* synthetic */ zzcje zzaye() {
        return super.zzaye();
    }

    public final /* bridge */ /* synthetic */ zzcir zzayf() {
        return super.zzayf();
    }

    public final /* bridge */ /* synthetic */ zzcme zzayg() {
        return super.zzayg();
    }

    public final /* bridge */ /* synthetic */ zzcma zzayh() {
        return super.zzayh();
    }

    public final /* bridge */ /* synthetic */ zzcjf zzayi() {
        return super.zzayi();
    }

    public final /* bridge */ /* synthetic */ zzcil zzayj() {
        return super.zzayj();
    }

    public final /* bridge */ /* synthetic */ zzcjh zzayk() {
        return super.zzayk();
    }

    public final /* bridge */ /* synthetic */ zzcno zzayl() {
        return super.zzayl();
    }

    public final /* bridge */ /* synthetic */ zzckd zzaym() {
        return super.zzaym();
    }

    public final /* bridge */ /* synthetic */ zzcnd zzayn() {
        return super.zzayn();
    }

    public final /* bridge */ /* synthetic */ zzcke zzayo() {
        return super.zzayo();
    }

    public final /* bridge */ /* synthetic */ zzcjj zzayp() {
        return super.zzayp();
    }

    public final /* bridge */ /* synthetic */ zzcju zzayq() {
        return super.zzayq();
    }

    public final /* bridge */ /* synthetic */ zzcik zzayr() {
        return super.zzayr();
    }

    protected final boolean zzazq() {
        return false;
    }

    public final boolean zzbbk() {
        return Thread.currentThread() == this.zzjnb;
    }

    final ExecutorService zzbbl() {
        ExecutorService executorService;
        synchronized (this.zzjnh) {
            if (this.executor == null) {
                this.executor = new ThreadPoolExecutor(0, 1, 30, TimeUnit.SECONDS, new ArrayBlockingQueue(100));
            }
            executorService = this.executor;
        }
        return executorService;
    }

    public final <V> Future<V> zzc(Callable<V> callable) throws IllegalStateException {
        zzyk();
        zzbq.checkNotNull(callable);
        zzckh zzckh = new zzckh(this, (Callable) callable, false, "Task exception on worker thread");
        if (Thread.currentThread() == this.zzjnb) {
            if (!this.zzjnd.isEmpty()) {
                zzayp().zzbaw().log("Callable skipped the worker queue.");
            }
            zzckh.run();
        } else {
            zza(zzckh);
        }
        return zzckh;
    }

    public final <V> Future<V> zzd(Callable<V> callable) throws IllegalStateException {
        zzyk();
        zzbq.checkNotNull(callable);
        zzckh zzckh = new zzckh(this, (Callable) callable, true, "Task exception on worker thread");
        if (Thread.currentThread() == this.zzjnb) {
            zzckh.run();
        } else {
            zza(zzckh);
        }
        return zzckh;
    }

    public final void zzh(Runnable runnable) throws IllegalStateException {
        zzyk();
        zzbq.checkNotNull(runnable);
        zza(new zzckh(this, runnable, false, "Task exception on worker thread"));
    }

    public final void zzi(Runnable runnable) throws IllegalStateException {
        zzyk();
        zzbq.checkNotNull(runnable);
        zzckh zzckh = new zzckh(this, runnable, false, "Task exception on network thread");
        synchronized (this.zzjnh) {
            this.zzjne.add(zzckh);
            if (this.zzjnc == null) {
                this.zzjnc = new zzcki(this, "Measurement Network", this.zzjne);
                this.zzjnc.setUncaughtExceptionHandler(this.zzjng);
                this.zzjnc.start();
            } else {
                this.zzjnc.zzsl();
            }
        }
    }

    public final void zzwj() {
        if (Thread.currentThread() != this.zzjnb) {
            throw new IllegalStateException("Call expected from worker thread");
        }
    }

    public final /* bridge */ /* synthetic */ zze zzxx() {
        return super.zzxx();
    }
}
