package com.google.android.gms.tasks;

import java.util.concurrent.Callable;

final class zzq implements Runnable {
    private /* synthetic */ Callable val$callable;
    private /* synthetic */ zzp zzler;

    zzq(zzp zzp, Callable callable) {
        this.zzler = zzp;
        this.val$callable = callable;
    }

    public final void run() {
        try {
            this.zzler.setResult(this.val$callable.call());
        } catch (Exception e) {
            this.zzler.setException(e);
        }
    }
}
