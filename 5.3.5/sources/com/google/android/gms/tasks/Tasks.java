package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.zzbq;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class Tasks {

    interface zzb extends OnFailureListener, OnSuccessListener<Object> {
    }

    static final class zza implements zzb {
        private final CountDownLatch zzapc;

        private zza() {
            this.zzapc = new CountDownLatch(1);
        }

        public final void await() throws InterruptedException {
            this.zzapc.await();
        }

        public final boolean await(long j, TimeUnit timeUnit) throws InterruptedException {
            return this.zzapc.await(j, timeUnit);
        }

        public final void onFailure(@NonNull Exception exception) {
            this.zzapc.countDown();
        }

        public final void onSuccess(Object obj) {
            this.zzapc.countDown();
        }
    }

    static final class zzc implements zzb {
        private final Object mLock = new Object();
        private final zzp<Void> zzlel;
        private Exception zzleq;
        private final int zzlet;
        private int zzleu;
        private int zzlev;

        public zzc(int i, zzp<Void> zzp) {
            this.zzlet = i;
            this.zzlel = zzp;
        }

        private final void zzblg() {
            if (this.zzleu + this.zzlev != this.zzlet) {
                return;
            }
            if (this.zzleq == null) {
                this.zzlel.setResult(null);
                return;
            }
            zzp zzp = this.zzlel;
            int i = this.zzlev;
            zzp.setException(new ExecutionException(i + " out of " + this.zzlet + " underlying tasks failed", this.zzleq));
        }

        public final void onFailure(@NonNull Exception exception) {
            synchronized (this.mLock) {
                this.zzlev++;
                this.zzleq = exception;
                zzblg();
            }
        }

        public final void onSuccess(Object obj) {
            synchronized (this.mLock) {
                this.zzleu++;
                zzblg();
            }
        }
    }

    private Tasks() {
    }

    public static <TResult> TResult await(@NonNull Task<TResult> task) throws ExecutionException, InterruptedException {
        zzbq.zzgw("Must not be called on the main application thread");
        zzbq.checkNotNull(task, "Task must not be null");
        if (task.isComplete()) {
            return zzc(task);
        }
        Object zza = new zza();
        zza(task, zza);
        zza.await();
        return zzc(task);
    }

    public static <TResult> TResult await(@NonNull Task<TResult> task, long j, @NonNull TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
        zzbq.zzgw("Must not be called on the main application thread");
        zzbq.checkNotNull(task, "Task must not be null");
        zzbq.checkNotNull(timeUnit, "TimeUnit must not be null");
        if (task.isComplete()) {
            return zzc(task);
        }
        Object zza = new zza();
        zza(task, zza);
        if (zza.await(j, timeUnit)) {
            return zzc(task);
        }
        throw new TimeoutException("Timed out waiting for Task");
    }

    public static <TResult> Task<TResult> call(@NonNull Callable<TResult> callable) {
        return call(TaskExecutors.MAIN_THREAD, callable);
    }

    public static <TResult> Task<TResult> call(@NonNull Executor executor, @NonNull Callable<TResult> callable) {
        zzbq.checkNotNull(executor, "Executor must not be null");
        zzbq.checkNotNull(callable, "Callback must not be null");
        Task zzp = new zzp();
        executor.execute(new zzq(zzp, callable));
        return zzp;
    }

    public static <TResult> Task<TResult> forException(@NonNull Exception exception) {
        Task zzp = new zzp();
        zzp.setException(exception);
        return zzp;
    }

    public static <TResult> Task<TResult> forResult(TResult tResult) {
        Task zzp = new zzp();
        zzp.setResult(tResult);
        return zzp;
    }

    public static Task<Void> whenAll(Collection<? extends Task<?>> collection) {
        if (collection.isEmpty()) {
            return forResult(null);
        }
        for (Task task : collection) {
            if (task == null) {
                throw new NullPointerException("null tasks are not accepted");
            }
        }
        Task zzp = new zzp();
        zzb zzc = new zzc(collection.size(), zzp);
        for (Task task2 : collection) {
            zza(task2, zzc);
        }
        return zzp;
    }

    public static Task<Void> whenAll(Task<?>... taskArr) {
        return taskArr.length == 0 ? forResult(null) : whenAll(Arrays.asList(taskArr));
    }

    public static Task<List<Task<?>>> whenAllComplete(Collection<? extends Task<?>> collection) {
        return whenAll((Collection) collection).continueWith(new zzs(collection));
    }

    public static Task<List<Task<?>>> whenAllComplete(Task<?>... taskArr) {
        return whenAllComplete(Arrays.asList(taskArr));
    }

    public static <TResult> Task<List<TResult>> whenAllSuccess(Collection<? extends Task<?>> collection) {
        return whenAll((Collection) collection).continueWith(new zzr(collection));
    }

    public static <TResult> Task<List<TResult>> whenAllSuccess(Task<?>... taskArr) {
        return whenAllSuccess(Arrays.asList(taskArr));
    }

    private static void zza(Task<?> task, zzb zzb) {
        task.addOnSuccessListener(TaskExecutors.zzlem, (OnSuccessListener) zzb);
        task.addOnFailureListener(TaskExecutors.zzlem, (OnFailureListener) zzb);
    }

    private static <TResult> TResult zzc(Task<TResult> task) throws ExecutionException {
        if (task.isSuccessful()) {
            return task.getResult();
        }
        throw new ExecutionException(task.getException());
    }
}
