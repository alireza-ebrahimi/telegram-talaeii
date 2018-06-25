package com.google.android.gms.wallet;

import android.app.Activity;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import android.util.SparseArray;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class AutoResolveHelper {
    public static final int RESULT_ERROR = 1;
    private static final long zzliv = TimeUnit.MINUTES.toMillis(10);
    @Hide
    @VisibleForTesting
    static long zzliw = SystemClock.elapsedRealtime();

    @VisibleForTesting
    @Hide
    static class zza<TResult extends AutoResolvableResult> implements OnCompleteListener<TResult>, Runnable {
        @VisibleForTesting
        private static Handler zzlix = new Handler(Looper.getMainLooper());
        @VisibleForTesting
        static final SparseArray<zza<?>> zzliy = new SparseArray(2);
        private static final AtomicInteger zzliz = new AtomicInteger();
        int zzlja;
        private zzb zzljb;
        private Task<TResult> zzljc;

        zza() {
        }

        private final void zzblr() {
            if (this.zzljc != null && this.zzljb != null) {
                zzliy.delete(this.zzlja);
                zzlix.removeCallbacks(this);
                this.zzljb.zze(this.zzljc);
            }
        }

        public static <TResult extends AutoResolvableResult> zza<TResult> zzd(Task<TResult> task) {
            zza<TResult> zza = new zza();
            zza.zzlja = zzliz.incrementAndGet();
            zzliy.put(zza.zzlja, zza);
            zzlix.postDelayed(zza, AutoResolveHelper.zzliv);
            task.addOnCompleteListener(zza);
            return zza;
        }

        public final void onComplete(@NonNull Task<TResult> task) {
            this.zzljc = task;
            zzblr();
        }

        public final void run() {
            zzliy.delete(this.zzlja);
        }

        public final void zza(zzb zzb) {
            this.zzljb = zzb;
            zzblr();
        }

        public final void zzb(zzb zzb) {
            if (this.zzljb == zzb) {
                this.zzljb = null;
            }
        }
    }

    @Hide
    public static class zzb extends Fragment {
        private static String zzljd = "resolveCallId";
        private static String zzlje = "requestCode";
        private static String zzljf = "initializationElapsedRealtime";
        private static String zzljg = "delivered";
        private int zzftn;
        private zza<?> zzljh;
        @VisibleForTesting
        private boolean zzlji;

        private final void zzbls() {
            if (this.zzljh != null) {
                this.zzljh.zzb(this);
            }
        }

        private final void zze(@Nullable Task<? extends AutoResolvableResult> task) {
            if (!this.zzlji) {
                this.zzlji = true;
                Activity activity = getActivity();
                activity.getFragmentManager().beginTransaction().remove(this).commit();
                if (task != null) {
                    AutoResolveHelper.zza(activity, this.zzftn, (Task) task);
                } else {
                    AutoResolveHelper.zza(activity, this.zzftn, 0, new Intent());
                }
            }
        }

        private static Fragment zzp(int i, int i2) {
            Bundle bundle = new Bundle();
            bundle.putInt(zzljd, i);
            bundle.putInt(zzlje, i2);
            bundle.putLong(zzljf, AutoResolveHelper.zzliw);
            Fragment zzb = new zzb();
            zzb.setArguments(bundle);
            return zzb;
        }

        public final void onCreate(@Nullable Bundle bundle) {
            super.onCreate(bundle);
            this.zzftn = getArguments().getInt(zzlje);
            if (AutoResolveHelper.zzliw != getArguments().getLong(zzljf)) {
                this.zzljh = null;
            } else {
                this.zzljh = (zza) zza.zzliy.get(getArguments().getInt(zzljd));
            }
            boolean z = bundle != null && bundle.getBoolean(zzljg);
            this.zzlji = z;
        }

        public final void onPause() {
            super.onPause();
            zzbls();
        }

        public final void onResume() {
            super.onResume();
            if (this.zzljh != null) {
                this.zzljh.zza(this);
                return;
            }
            if (Log.isLoggable("AutoResolveHelper", 5)) {
                Log.w("AutoResolveHelper", "Sending canceled result for garbage collected task!");
            }
            zze(null);
        }

        public final void onSaveInstanceState(Bundle bundle) {
            super.onSaveInstanceState(bundle);
            bundle.putBoolean(zzljg, this.zzlji);
            zzbls();
        }
    }

    private AutoResolveHelper() {
    }

    @Nullable
    public static Status getStatusFromIntent(@Nullable Intent intent) {
        return intent == null ? null : (Status) intent.getParcelableExtra("com.google.android.gms.common.api.AutoResolveHelper.status");
    }

    public static void putStatusIntoIntent(@NonNull Intent intent, @Nullable Status status) {
        if (status == null) {
            intent.removeExtra("com.google.android.gms.common.api.AutoResolveHelper.status");
        } else {
            intent.putExtra("com.google.android.gms.common.api.AutoResolveHelper.status", status);
        }
    }

    @MainThread
    public static <TResult extends AutoResolvableResult> void resolveTask(@NonNull Task<TResult> task, @NonNull Activity activity, int i) {
        zza zzd = zza.zzd(task);
        activity.getFragmentManager().beginTransaction().add(zzb.zzp(zzd.zzlja, i), "com.google.android.gms.wallet.AutoResolveHelper" + zzd.zzlja).commit();
    }

    private static void zza(Activity activity, int i, int i2, Intent intent) {
        PendingIntent createPendingResult = activity.createPendingResult(i, intent, 1073741824);
        if (createPendingResult != null) {
            try {
                createPendingResult.send(i2);
            } catch (Throwable e) {
                if (Log.isLoggable("AutoResolveHelper", 6)) {
                    Log.e("AutoResolveHelper", "Exception sending pending result", e);
                }
            }
        } else if (Log.isLoggable("AutoResolveHelper", 5)) {
            Log.w("AutoResolveHelper", "Null pending result returned when trying to deliver task result!");
        }
    }

    private static void zza(Activity activity, int i, Task<? extends AutoResolvableResult> task) {
        if (activity.isFinishing()) {
            if (Log.isLoggable("AutoResolveHelper", 3)) {
                Log.d("AutoResolveHelper", "Ignoring task result for, Activity is finishing.");
            }
        } else if (task.getException() instanceof ResolvableApiException) {
            try {
                ((ResolvableApiException) task.getException()).startResolutionForResult(activity, i);
            } catch (Throwable e) {
                if (Log.isLoggable("AutoResolveHelper", 6)) {
                    Log.e("AutoResolveHelper", "Error starting pending intent!", e);
                }
            }
        } else {
            int i2;
            Intent intent = new Intent();
            if (task.isSuccessful()) {
                ((AutoResolvableResult) task.getResult()).putIntoIntent(intent);
                i2 = -1;
            } else if (task.getException() instanceof ApiException) {
                ApiException apiException = (ApiException) task.getException();
                putStatusIntoIntent(intent, new Status(apiException.getStatusCode(), apiException.getMessage(), null));
                i2 = 1;
            } else {
                if (Log.isLoggable("AutoResolveHelper", 6)) {
                    Log.e("AutoResolveHelper", "Unexpected non API exception!", task.getException());
                }
                putStatusIntoIntent(intent, new Status(8, "Unexpected non API exception when trying to deliver the task result to an activity!"));
                i2 = 1;
            }
            zza(activity, i, i2, intent);
        }
    }

    @Hide
    public static <TResult> void zza(Status status, TResult tResult, TaskCompletionSource<TResult> taskCompletionSource) {
        if (status.isSuccess()) {
            taskCompletionSource.setResult(tResult);
        } else {
            taskCompletionSource.setException(com.google.android.gms.common.internal.zzb.zzy(status));
        }
    }
}
