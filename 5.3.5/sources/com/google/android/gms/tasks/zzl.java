package com.google.android.gms.tasks;

final class zzl implements Runnable {
    private /* synthetic */ Task zzldy;
    private /* synthetic */ zzk zzlei;

    zzl(zzk zzk, Task task) {
        this.zzlei = zzk;
        this.zzldy = task;
    }

    public final void run() {
        try {
            Task then = this.zzlei.zzleh.then(this.zzldy.getResult());
            if (then == null) {
                this.zzlei.onFailure(new NullPointerException("Continuation returned null"));
                return;
            }
            then.addOnSuccessListener(TaskExecutors.zzlem, this.zzlei);
            then.addOnFailureListener(TaskExecutors.zzlem, this.zzlei);
        } catch (Exception e) {
            if (e.getCause() instanceof Exception) {
                this.zzlei.onFailure((Exception) e.getCause());
            } else {
                this.zzlei.onFailure(e);
            }
        } catch (Exception e2) {
            this.zzlei.onFailure(e2);
        }
    }
}
