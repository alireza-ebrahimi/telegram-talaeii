package com.google.android.gms.internal;

import android.content.ComponentName;

final class zzcmu implements Runnable {
    private /* synthetic */ ComponentName val$name;
    private /* synthetic */ zzcms zzjrs;

    zzcmu(zzcms zzcms, ComponentName componentName) {
        this.zzjrs = zzcms;
        this.val$name = componentName;
    }

    public final void run() {
        this.zzjrs.zzjri.onServiceDisconnected(this.val$name);
    }
}
