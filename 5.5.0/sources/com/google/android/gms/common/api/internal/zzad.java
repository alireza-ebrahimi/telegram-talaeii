package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.support.v4.p022f.C0466b;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.Preconditions;

public class zzad extends zzk {
    private GoogleApiManager zzcq;
    private final C0466b<zzh<?>> zzhb = new C0466b();

    private zzad(LifecycleFragment lifecycleFragment) {
        super(lifecycleFragment);
        this.mLifecycleFragment.addCallback("ConnectionlessLifecycleHelper", this);
    }

    public static void zza(Activity activity, GoogleApiManager googleApiManager, zzh<?> zzh) {
        LifecycleFragment fragment = LifecycleCallback.getFragment(activity);
        zzad zzad = (zzad) fragment.getCallbackOrNull("ConnectionlessLifecycleHelper", zzad.class);
        if (zzad == null) {
            zzad = new zzad(fragment);
        }
        zzad.zzcq = googleApiManager;
        Preconditions.checkNotNull(zzh, "ApiKey cannot be null");
        zzad.zzhb.add(zzh);
        googleApiManager.zza(zzad);
    }

    private final void zzan() {
        if (!this.zzhb.isEmpty()) {
            this.zzcq.zza(this);
        }
    }

    public final void onResume() {
        super.onResume();
        zzan();
    }

    public final void onStart() {
        super.onStart();
        zzan();
    }

    public void onStop() {
        super.onStop();
        this.zzcq.zzb(this);
    }

    protected final void zza(ConnectionResult connectionResult, int i) {
        this.zzcq.zza(connectionResult, i);
    }

    final C0466b<zzh<?>> zzam() {
        return this.zzhb;
    }

    protected final void zzr() {
        this.zzcq.zzr();
    }
}
