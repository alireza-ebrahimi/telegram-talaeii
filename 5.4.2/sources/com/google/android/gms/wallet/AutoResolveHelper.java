package com.google.android.gms.wallet;

import android.app.Activity;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.util.SparseArray;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.ApiExceptionUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class AutoResolveHelper {
    public static final int RESULT_ERROR = 1;
    private static final long zzo = TimeUnit.MINUTES.toMillis(10);
    static long zzp = SystemClock.elapsedRealtime();

    static class zza<TResult extends AutoResolvableResult> implements OnCompleteListener<TResult>, Runnable {
        private static final Handler zzq = new Handler(Looper.getMainLooper());
        static final SparseArray<zza<?>> zzr = new SparseArray(2);
        private static final AtomicInteger zzs = new AtomicInteger();
        int zzt;
        private zzb zzu;
        private Task<TResult> zzv;

        zza() {
        }

        public static <TResult extends AutoResolvableResult> zza<TResult> zza(Task<TResult> task) {
            zza<TResult> zza = new zza();
            zza.zzt = zzs.incrementAndGet();
            zzr.put(zza.zzt, zza);
            zzq.postDelayed(zza, AutoResolveHelper.zzo);
            task.addOnCompleteListener(zza);
            return zza;
        }

        private final void zzb() {
            if (this.zzv != null && this.zzu != null) {
                zzr.delete(this.zzt);
                zzq.removeCallbacks(this);
                this.zzu.zzb(this.zzv);
            }
        }

        public final void onComplete(Task<TResult> task) {
            this.zzv = task;
            zzb();
        }

        public final void run() {
            zzr.delete(this.zzt);
        }

        public final void zza(zzb zzb) {
            this.zzu = zzb;
            zzb();
        }

        public final void zzb(zzb zzb) {
            if (this.zzu == zzb) {
                this.zzu = null;
            }
        }
    }

    public static class zzb extends Fragment {
        private static String zzw = "resolveCallId";
        private static String zzx = "requestCode";
        private static String zzy = "initializationElapsedRealtime";
        private static String zzz = "delivered";
        private int zzaa;
        private zza<?> zzab;
        private boolean zzac;

        private static Fragment zza(int i, int i2) {
            Bundle bundle = new Bundle();
            bundle.putInt(zzw, i);
            bundle.putInt(zzx, i2);
            bundle.putLong(zzy, AutoResolveHelper.zzp);
            Fragment zzb = new zzb();
            zzb.setArguments(bundle);
            return zzb;
        }

        private final void zzb(Task<? extends AutoResolvableResult> task) {
            if (!this.zzac) {
                this.zzac = true;
                Activity activity = getActivity();
                activity.getFragmentManager().beginTransaction().remove(this).commit();
                if (task != null) {
                    AutoResolveHelper.zza(activity, this.zzaa, (Task) task);
                } else {
                    AutoResolveHelper.zza(activity, this.zzaa, 0, new Intent());
                }
            }
        }

        private final void zzc() {
            if (this.zzab != null) {
                this.zzab.zzb(this);
            }
        }

        public final void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            this.zzaa = getArguments().getInt(zzx);
            if (AutoResolveHelper.zzp != getArguments().getLong(zzy)) {
                this.zzab = null;
            } else {
                this.zzab = (zza) zza.zzr.get(getArguments().getInt(zzw));
            }
            boolean z = bundle != null && bundle.getBoolean(zzz);
            this.zzac = z;
        }

        public final void onPause() {
            super.onPause();
            zzc();
        }

        public final void onResume() {
            super.onResume();
            if (this.zzab != null) {
                this.zzab.zza(this);
                return;
            }
            if (Log.isLoggable("AutoResolveHelper", 5)) {
                Log.w("AutoResolveHelper", "Sending canceled result for garbage collected task!");
            }
            zzb(null);
        }

        public final void onSaveInstanceState(Bundle bundle) {
            super.onSaveInstanceState(bundle);
            bundle.putBoolean(zzz, this.zzac);
            zzc();
        }
    }

    private AutoResolveHelper() {
    }

    public static Status getStatusFromIntent(Intent intent) {
        return intent == null ? null : (Status) intent.getParcelableExtra("com.google.android.gms.common.api.AutoResolveHelper.status");
    }

    public static void putStatusIntoIntent(Intent intent, Status status) {
        if (status == null) {
            intent.removeExtra("com.google.android.gms.common.api.AutoResolveHelper.status");
        } else {
            intent.putExtra("com.google.android.gms.common.api.AutoResolveHelper.status", status);
        }
    }

    public static <TResult extends AutoResolvableResult> void resolveTask(Task<TResult> task, Activity activity, int i) {
        zza zza = zza.zza((Task) task);
        activity.getFragmentManager().beginTransaction().add(zzb.zza(zza.zzt, i), "com.google.android.gms.wallet.AutoResolveHelper" + zza.zzt).commit();
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

    public static <TResult> void zza(Status status, TResult tResult, TaskCompletionSource<TResult> taskCompletionSource) {
        if (status.isSuccess()) {
            taskCompletionSource.setResult(tResult);
        } else {
            taskCompletionSource.setException(ApiExceptionUtil.fromStatus(status));
        }
    }
}
