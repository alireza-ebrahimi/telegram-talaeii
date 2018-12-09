package com.google.android.gms.tasks;

import java.util.concurrent.CancellationException;

final class zzp implements Runnable {
    private final /* synthetic */ Task zzafn;
    private final /* synthetic */ zzo zzafz;

    zzp(zzo zzo, Task task) {
        this.zzafz = zzo;
        this.zzafn = task;
    }

    public final void run() {
        try {
            Task then = this.zzafz.zzafy.then(this.zzafn.getResult());
            if (then == null) {
                this.zzafz.onFailure(new NullPointerException("Continuation returned null"));
                return;
            }
            then.addOnSuccessListener(TaskExecutors.zzagd, this.zzafz);
            then.addOnFailureListener(TaskExecutors.zzagd, this.zzafz);
            then.addOnCanceledListener(TaskExecutors.zzagd, this.zzafz);
        } catch (Exception e) {
            if (e.getCause() instanceof Exception) {
                this.zzafz.onFailure((Exception) e.getCause());
            } else {
                this.zzafz.onFailure(e);
            }
        } catch (CancellationException e2) {
            this.zzafz.onCanceled();
        } catch (Exception e3) {
            this.zzafz.onFailure(e3);
        }
    }
}
