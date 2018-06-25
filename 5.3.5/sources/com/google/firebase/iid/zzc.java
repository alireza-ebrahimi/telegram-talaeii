package com.google.firebase.iid;

import android.content.Intent;

final class zzc implements Runnable {
    private /* synthetic */ Intent val$intent;
    private /* synthetic */ Intent zzimg;
    private /* synthetic */ zzb zzokh;

    zzc(zzb zzb, Intent intent, Intent intent2) {
        this.zzokh = zzb;
        this.val$intent = intent;
        this.zzimg = intent2;
    }

    public final void run() {
        this.zzokh.handleIntent(this.val$intent);
        this.zzokh.zzh(this.zzimg);
    }
}
