package com.google.android.gms.tasks;

final class zzd implements Runnable {
    private /* synthetic */ Task zzldy;
    private /* synthetic */ zzc zzlea;

    zzd(zzc zzc, Task task) {
        this.zzlea = zzc;
        this.zzldy = task;
    }

    public final void run() {
        try {
            Task task = (Task) this.zzlea.zzldw.then(this.zzldy);
            if (task == null) {
                this.zzlea.onFailure(new NullPointerException("Continuation returned null"));
                return;
            }
            task.addOnSuccessListener(TaskExecutors.zzlem, this.zzlea);
            task.addOnFailureListener(TaskExecutors.zzlem, this.zzlea);
        } catch (Exception e) {
            if (e.getCause() instanceof Exception) {
                this.zzlea.zzldx.setException((Exception) e.getCause());
            } else {
                this.zzlea.zzldx.setException(e);
            }
        } catch (Exception e2) {
            this.zzlea.zzldx.setException(e2);
        }
    }
}
