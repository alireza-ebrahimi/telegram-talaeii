package com.google.android.gms.common.api.internal;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.concurrent.atomic.AtomicReference;

public abstract class zzk extends LifecycleCallback implements OnCancelListener {
    protected volatile boolean mStarted;
    protected final GoogleApiAvailability zzdg;
    protected final AtomicReference<zzl> zzer;
    private final Handler zzes;

    protected zzk(LifecycleFragment lifecycleFragment) {
        this(lifecycleFragment, GoogleApiAvailability.getInstance());
    }

    @VisibleForTesting
    private zzk(LifecycleFragment lifecycleFragment, GoogleApiAvailability googleApiAvailability) {
        super(lifecycleFragment);
        this.zzer = new AtomicReference(null);
        this.zzes = new Handler(Looper.getMainLooper());
        this.zzdg = googleApiAvailability;
    }

    private static int zza(zzl zzl) {
        return zzl == null ? -1 : zzl.zzu();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onActivityResult(int r7, int r8, android.content.Intent r9) {
        /*
        r6 = this;
        r5 = 18;
        r1 = 13;
        r2 = 1;
        r3 = 0;
        r0 = r6.zzer;
        r0 = r0.get();
        r0 = (com.google.android.gms.common.api.internal.zzl) r0;
        switch(r7) {
            case 1: goto L_0x0034;
            case 2: goto L_0x0018;
            default: goto L_0x0011;
        };
    L_0x0011:
        r1 = r3;
    L_0x0012:
        if (r1 == 0) goto L_0x005b;
    L_0x0014:
        r6.zzt();
    L_0x0017:
        return;
    L_0x0018:
        r1 = r6.zzdg;
        r4 = r6.getActivity();
        r4 = r1.isGooglePlayServicesAvailable(r4);
        if (r4 != 0) goto L_0x0069;
    L_0x0024:
        r1 = r2;
    L_0x0025:
        if (r0 == 0) goto L_0x0017;
    L_0x0027:
        r2 = r0.getConnectionResult();
        r2 = r2.getErrorCode();
        if (r2 != r5) goto L_0x0012;
    L_0x0031:
        if (r4 != r5) goto L_0x0012;
    L_0x0033:
        goto L_0x0017;
    L_0x0034:
        r4 = -1;
        if (r8 != r4) goto L_0x0039;
    L_0x0037:
        r1 = r2;
        goto L_0x0012;
    L_0x0039:
        if (r8 != 0) goto L_0x0011;
    L_0x003b:
        if (r9 == 0) goto L_0x0044;
    L_0x003d:
        r2 = "<<ResolutionFailureErrorDetail>>";
        r1 = r9.getIntExtra(r2, r1);
    L_0x0044:
        r2 = new com.google.android.gms.common.api.internal.zzl;
        r4 = new com.google.android.gms.common.ConnectionResult;
        r5 = 0;
        r4.<init>(r1, r5);
        r0 = zza(r0);
        r2.<init>(r4, r0);
        r0 = r6.zzer;
        r0.set(r2);
        r0 = r2;
        r1 = r3;
        goto L_0x0012;
    L_0x005b:
        if (r0 == 0) goto L_0x0017;
    L_0x005d:
        r1 = r0.getConnectionResult();
        r0 = r0.zzu();
        r6.zza(r1, r0);
        goto L_0x0017;
    L_0x0069:
        r1 = r3;
        goto L_0x0025;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.zzk.onActivityResult(int, int, android.content.Intent):void");
    }

    public void onCancel(DialogInterface dialogInterface) {
        zza(new ConnectionResult(13, null), zza((zzl) this.zzer.get()));
        zzt();
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.zzer.set(bundle.getBoolean("resolving_error", false) ? new zzl(new ConnectionResult(bundle.getInt("failed_status"), (PendingIntent) bundle.getParcelable("failed_resolution")), bundle.getInt("failed_client_id", -1)) : null);
        }
    }

    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        zzl zzl = (zzl) this.zzer.get();
        if (zzl != null) {
            bundle.putBoolean("resolving_error", true);
            bundle.putInt("failed_client_id", zzl.zzu());
            bundle.putInt("failed_status", zzl.getConnectionResult().getErrorCode());
            bundle.putParcelable("failed_resolution", zzl.getConnectionResult().getResolution());
        }
    }

    public void onStart() {
        super.onStart();
        this.mStarted = true;
    }

    public void onStop() {
        super.onStop();
        this.mStarted = false;
    }

    protected abstract void zza(ConnectionResult connectionResult, int i);

    public final void zzb(ConnectionResult connectionResult, int i) {
        zzl zzl = new zzl(connectionResult, i);
        if (this.zzer.compareAndSet(null, zzl)) {
            this.zzes.post(new zzm(this, zzl));
        }
    }

    protected abstract void zzr();

    protected final void zzt() {
        this.zzer.set(null);
        zzr();
    }
}
