package com.google.android.gms.common.api.internal;

import android.app.Activity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzb;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.concurrent.CancellationException;

public class zzcn extends zzo {
    private TaskCompletionSource<Void> zzejm = new TaskCompletionSource();

    private zzcn(zzcf zzcf) {
        super(zzcf);
        this.zzgam.zza("GmsAvailabilityHelper", (LifecycleCallback) this);
    }

    public static zzcn zzq(Activity activity) {
        zzcf zzo = LifecycleCallback.zzo(activity);
        zzcn zzcn = (zzcn) zzo.zza("GmsAvailabilityHelper", zzcn.class);
        if (zzcn == null) {
            return new zzcn(zzo);
        }
        if (!zzcn.zzejm.getTask().isComplete()) {
            return zzcn;
        }
        zzcn.zzejm = new TaskCompletionSource();
        return zzcn;
    }

    public final Task<Void> getTask() {
        return this.zzejm.getTask();
    }

    public final void onDestroy() {
        super.onDestroy();
        this.zzejm.trySetException(new CancellationException("Host activity was destroyed before Google Play services could be made available."));
    }

    protected final void zza(ConnectionResult connectionResult, int i) {
        this.zzejm.setException(zzb.zzy(new Status(connectionResult.getErrorCode(), connectionResult.getErrorMessage(), connectionResult.getResolution())));
    }

    protected final void zzaih() {
        int isGooglePlayServicesAvailable = this.zzftg.isGooglePlayServicesAvailable(this.zzgam.zzakw());
        if (isGooglePlayServicesAvailable == 0) {
            this.zzejm.setResult(null);
        } else if (!this.zzejm.getTask().isComplete()) {
            zzb(new ConnectionResult(isGooglePlayServicesAvailable, null), 0);
        }
    }
}
