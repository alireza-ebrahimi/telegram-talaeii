package com.google.android.gms.common.api.internal;

import android.support.v4.p022f.C0464a;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.AvailabilityException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.util.Collections;
import java.util.Map;

final class zzy implements OnCompleteListener<Map<zzh<?>, String>> {
    private final /* synthetic */ zzw zzgu;

    private zzy(zzw zzw) {
        this.zzgu = zzw;
    }

    public final void onComplete(Task<Map<zzh<?>, String>> task) {
        this.zzgu.zzga.lock();
        try {
            if (this.zzgu.zzgp) {
                if (task.isSuccessful()) {
                    this.zzgu.zzgq = new C0464a(this.zzgu.zzgg.size());
                    for (zzv zzm : this.zzgu.zzgg.values()) {
                        this.zzgu.zzgq.put(zzm.zzm(), ConnectionResult.RESULT_SUCCESS);
                    }
                } else if (task.getException() instanceof AvailabilityException) {
                    AvailabilityException availabilityException = (AvailabilityException) task.getException();
                    if (this.zzgu.zzgn) {
                        this.zzgu.zzgq = new C0464a(this.zzgu.zzgg.size());
                        for (zzv zzv : this.zzgu.zzgg.values()) {
                            zzh zzm2 = zzv.zzm();
                            ConnectionResult connectionResult = availabilityException.getConnectionResult(zzv);
                            if (this.zzgu.zza(zzv, connectionResult)) {
                                this.zzgu.zzgq.put(zzm2, new ConnectionResult(16));
                            } else {
                                this.zzgu.zzgq.put(zzm2, connectionResult);
                            }
                        }
                    } else {
                        this.zzgu.zzgq = availabilityException.zzl();
                    }
                    this.zzgu.zzgt = this.zzgu.zzai();
                } else {
                    Log.e("ConnectionlessGAC", "Unexpected availability exception", task.getException());
                    this.zzgu.zzgq = Collections.emptyMap();
                    this.zzgu.zzgt = new ConnectionResult(8);
                }
                if (this.zzgu.zzgr != null) {
                    this.zzgu.zzgq.putAll(this.zzgu.zzgr);
                    this.zzgu.zzgt = this.zzgu.zzai();
                }
                if (this.zzgu.zzgt == null) {
                    this.zzgu.zzag();
                    this.zzgu.zzah();
                } else {
                    this.zzgu.zzgp = false;
                    this.zzgu.zzgj.zzc(this.zzgu.zzgt);
                }
                this.zzgu.zzgl.signalAll();
                this.zzgu.zzga.unlock();
            }
        } finally {
            this.zzgu.zzga.unlock();
        }
    }
}
