package com.google.android.gms.internal.measurement;

import android.content.ComponentName;

final class zziz implements Runnable {
    private final /* synthetic */ ComponentName val$name;
    private final /* synthetic */ zzix zzapw;

    zziz(zzix zzix, ComponentName componentName) {
        this.zzapw = zzix;
        this.val$name = componentName;
    }

    public final void run() {
        this.zzapw.zzapn.onServiceDisconnected(this.val$name);
    }
}
