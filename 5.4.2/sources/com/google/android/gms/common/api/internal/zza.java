package com.google.android.gms.common.api.internal;

import android.app.Activity;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public final class zza extends ActivityLifecycleObserver {
    private final WeakReference<zza> zzds;

    static class zza extends LifecycleCallback {
        private List<Runnable> zzdt = new ArrayList();

        private zza(LifecycleFragment lifecycleFragment) {
            super(lifecycleFragment);
            this.mLifecycleFragment.addCallback("LifecycleObserverOnStop", this);
        }

        private static zza zza(Activity activity) {
            zza zza;
            synchronized (activity) {
                LifecycleFragment fragment = LifecycleCallback.getFragment(activity);
                zza = (zza) fragment.getCallbackOrNull("LifecycleObserverOnStop", zza.class);
                if (zza == null) {
                    zza = new zza(fragment);
                }
            }
            return zza;
        }

        private final synchronized void zza(Runnable runnable) {
            this.zzdt.add(runnable);
        }

        public void onStop() {
            synchronized (this) {
                List<Runnable> list = this.zzdt;
                this.zzdt = new ArrayList();
            }
            for (Runnable run : list) {
                run.run();
            }
        }
    }

    public zza(Activity activity) {
        this(zza.zza(activity));
    }

    private zza(zza zza) {
        this.zzds = new WeakReference(zza);
    }

    public final ActivityLifecycleObserver onStopCallOnce(Runnable runnable) {
        zza zza = (zza) this.zzds.get();
        if (zza == null) {
            throw new IllegalStateException("The target activity has already been GC'd");
        }
        zza.zza(runnable);
        return this;
    }
}
