package com.google.android.gms.tasks;

import android.app.Activity;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.api.internal.LifecycleCallback;
import com.google.android.gms.common.api.internal.zzcf;
import com.google.android.gms.common.internal.zzbq;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

final class zzp<TResult> extends Task<TResult> {
    private final Object mLock = new Object();
    private final zzn<TResult> zzlen = new zzn();
    private boolean zzleo;
    private TResult zzlep;
    private Exception zzleq;

    static class zza extends LifecycleCallback {
        private final List<WeakReference<zzm<?>>> zzffp = new ArrayList();

        private zza(zzcf zzcf) {
            super(zzcf);
            this.zzgam.zza("TaskOnStopCallback", (LifecycleCallback) this);
        }

        public static zza zzs(Activity activity) {
            zzcf zzo = LifecycleCallback.zzo(activity);
            zza zza = (zza) zzo.zza("TaskOnStopCallback", zza.class);
            return zza == null ? new zza(zzo) : zza;
        }

        @MainThread
        public final void onStop() {
            synchronized (this.zzffp) {
                for (WeakReference weakReference : this.zzffp) {
                    zzm zzm = (zzm) weakReference.get();
                    if (zzm != null) {
                        zzm.cancel();
                    }
                }
                this.zzffp.clear();
            }
        }

        public final <T> void zzb(zzm<T> zzm) {
            synchronized (this.zzffp) {
                this.zzffp.add(new WeakReference(zzm));
            }
        }
    }

    zzp() {
    }

    private final void zzbld() {
        zzbq.zza(this.zzleo, (Object) "Task is not yet complete");
    }

    private final void zzble() {
        zzbq.zza(!this.zzleo, (Object) "Task is already complete");
    }

    private final void zzblf() {
        synchronized (this.mLock) {
            if (this.zzleo) {
                this.zzlen.zzb(this);
                return;
            }
        }
    }

    @NonNull
    public final Task<TResult> addOnCompleteListener(@NonNull Activity activity, @NonNull OnCompleteListener<TResult> onCompleteListener) {
        zzm zze = new zze(TaskExecutors.MAIN_THREAD, onCompleteListener);
        this.zzlen.zza(zze);
        zza.zzs(activity).zzb(zze);
        zzblf();
        return this;
    }

    @NonNull
    public final Task<TResult> addOnCompleteListener(@NonNull OnCompleteListener<TResult> onCompleteListener) {
        return addOnCompleteListener(TaskExecutors.MAIN_THREAD, (OnCompleteListener) onCompleteListener);
    }

    @NonNull
    public final Task<TResult> addOnCompleteListener(@NonNull Executor executor, @NonNull OnCompleteListener<TResult> onCompleteListener) {
        this.zzlen.zza(new zze(executor, onCompleteListener));
        zzblf();
        return this;
    }

    @NonNull
    public final Task<TResult> addOnFailureListener(@NonNull Activity activity, @NonNull OnFailureListener onFailureListener) {
        zzm zzg = new zzg(TaskExecutors.MAIN_THREAD, onFailureListener);
        this.zzlen.zza(zzg);
        zza.zzs(activity).zzb(zzg);
        zzblf();
        return this;
    }

    @NonNull
    public final Task<TResult> addOnFailureListener(@NonNull OnFailureListener onFailureListener) {
        return addOnFailureListener(TaskExecutors.MAIN_THREAD, onFailureListener);
    }

    @NonNull
    public final Task<TResult> addOnFailureListener(@NonNull Executor executor, @NonNull OnFailureListener onFailureListener) {
        this.zzlen.zza(new zzg(executor, onFailureListener));
        zzblf();
        return this;
    }

    @NonNull
    public final Task<TResult> addOnSuccessListener(@NonNull Activity activity, @NonNull OnSuccessListener<? super TResult> onSuccessListener) {
        zzm zzi = new zzi(TaskExecutors.MAIN_THREAD, onSuccessListener);
        this.zzlen.zza(zzi);
        zza.zzs(activity).zzb(zzi);
        zzblf();
        return this;
    }

    @NonNull
    public final Task<TResult> addOnSuccessListener(@NonNull OnSuccessListener<? super TResult> onSuccessListener) {
        return addOnSuccessListener(TaskExecutors.MAIN_THREAD, (OnSuccessListener) onSuccessListener);
    }

    @NonNull
    public final Task<TResult> addOnSuccessListener(@NonNull Executor executor, @NonNull OnSuccessListener<? super TResult> onSuccessListener) {
        this.zzlen.zza(new zzi(executor, onSuccessListener));
        zzblf();
        return this;
    }

    @NonNull
    public final <TContinuationResult> Task<TContinuationResult> continueWith(@NonNull Continuation<TResult, TContinuationResult> continuation) {
        return continueWith(TaskExecutors.MAIN_THREAD, continuation);
    }

    @NonNull
    public final <TContinuationResult> Task<TContinuationResult> continueWith(@NonNull Executor executor, @NonNull Continuation<TResult, TContinuationResult> continuation) {
        Task zzp = new zzp();
        this.zzlen.zza(new zza(executor, continuation, zzp));
        zzblf();
        return zzp;
    }

    @NonNull
    public final <TContinuationResult> Task<TContinuationResult> continueWithTask(@NonNull Continuation<TResult, Task<TContinuationResult>> continuation) {
        return continueWithTask(TaskExecutors.MAIN_THREAD, continuation);
    }

    @NonNull
    public final <TContinuationResult> Task<TContinuationResult> continueWithTask(@NonNull Executor executor, @NonNull Continuation<TResult, Task<TContinuationResult>> continuation) {
        Task zzp = new zzp();
        this.zzlen.zza(new zzc(executor, continuation, zzp));
        zzblf();
        return zzp;
    }

    @Nullable
    public final Exception getException() {
        Exception exception;
        synchronized (this.mLock) {
            exception = this.zzleq;
        }
        return exception;
    }

    public final TResult getResult() {
        TResult tResult;
        synchronized (this.mLock) {
            zzbld();
            if (this.zzleq != null) {
                throw new RuntimeExecutionException(this.zzleq);
            }
            tResult = this.zzlep;
        }
        return tResult;
    }

    public final <X extends Throwable> TResult getResult(@NonNull Class<X> cls) throws Throwable {
        TResult tResult;
        synchronized (this.mLock) {
            zzbld();
            if (cls.isInstance(this.zzleq)) {
                throw ((Throwable) cls.cast(this.zzleq));
            } else if (this.zzleq != null) {
                throw new RuntimeExecutionException(this.zzleq);
            } else {
                tResult = this.zzlep;
            }
        }
        return tResult;
    }

    public final boolean isComplete() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzleo;
        }
        return z;
    }

    public final boolean isSuccessful() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzleo && this.zzleq == null;
        }
        return z;
    }

    @NonNull
    public final <TContinuationResult> Task<TContinuationResult> onSuccessTask(@NonNull SuccessContinuation<TResult, TContinuationResult> successContinuation) {
        return onSuccessTask(TaskExecutors.MAIN_THREAD, successContinuation);
    }

    @NonNull
    public final <TContinuationResult> Task<TContinuationResult> onSuccessTask(Executor executor, SuccessContinuation<TResult, TContinuationResult> successContinuation) {
        Task zzp = new zzp();
        this.zzlen.zza(new zzk(executor, successContinuation, zzp));
        zzblf();
        return zzp;
    }

    public final void setException(@NonNull Exception exception) {
        zzbq.checkNotNull(exception, "Exception must not be null");
        synchronized (this.mLock) {
            zzble();
            this.zzleo = true;
            this.zzleq = exception;
        }
        this.zzlen.zzb(this);
    }

    public final void setResult(TResult tResult) {
        synchronized (this.mLock) {
            zzble();
            this.zzleo = true;
            this.zzlep = tResult;
        }
        this.zzlen.zzb(this);
    }

    public final boolean trySetException(@NonNull Exception exception) {
        boolean z = true;
        zzbq.checkNotNull(exception, "Exception must not be null");
        synchronized (this.mLock) {
            if (this.zzleo) {
                z = false;
            } else {
                this.zzleo = true;
                this.zzleq = exception;
                this.zzlen.zzb(this);
            }
        }
        return z;
    }

    public final boolean trySetResult(TResult tResult) {
        boolean z = true;
        synchronized (this.mLock) {
            if (this.zzleo) {
                z = false;
            } else {
                this.zzleo = true;
                this.zzlep = tResult;
                this.zzlen.zzb(this);
            }
        }
        return z;
    }
}
