package com.google.android.gms.tasks;

final class zzf implements Runnable {
    private final /* synthetic */ Task zzafn;
    private final /* synthetic */ zze zzafp;

    zzf(zze zze, Task task) {
        this.zzafp = zze;
        this.zzafn = task;
    }

    public final void run() {
        try {
            Task task = (Task) this.zzafp.zzafl.then(this.zzafn);
            if (task == null) {
                this.zzafp.onFailure(new NullPointerException("Continuation returned null"));
                return;
            }
            task.addOnSuccessListener(TaskExecutors.zzagd, this.zzafp);
            task.addOnFailureListener(TaskExecutors.zzagd, this.zzafp);
            task.addOnCanceledListener(TaskExecutors.zzagd, this.zzafp);
        } catch (Exception e) {
            if (e.getCause() instanceof Exception) {
                this.zzafp.zzafm.setException((Exception) e.getCause());
            } else {
                this.zzafp.zzafm.setException(e);
            }
        } catch (Exception e2) {
            this.zzafp.zzafm.setException(e2);
        }
    }
}
