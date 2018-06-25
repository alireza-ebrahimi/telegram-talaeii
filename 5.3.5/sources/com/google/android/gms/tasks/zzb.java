package com.google.android.gms.tasks;

final class zzb implements Runnable {
    private /* synthetic */ Task zzldy;
    private /* synthetic */ zza zzldz;

    zzb(zza zza, Task task) {
        this.zzldz = zza;
        this.zzldy = task;
    }

    public final void run() {
        try {
            this.zzldz.zzldx.setResult(this.zzldz.zzldw.then(this.zzldy));
        } catch (Exception e) {
            if (e.getCause() instanceof Exception) {
                this.zzldz.zzldx.setException((Exception) e.getCause());
            } else {
                this.zzldz.zzldx.setException(e);
            }
        } catch (Exception e2) {
            this.zzldz.zzldx.setException(e2);
        }
    }
}
