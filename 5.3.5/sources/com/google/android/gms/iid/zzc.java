package com.google.android.gms.iid;

import android.content.Intent;

final class zzc implements Runnable {
    private /* synthetic */ Intent val$intent;
    private /* synthetic */ Intent zzimg;
    private /* synthetic */ zzb zzimh;

    zzc(zzb zzb, Intent intent, Intent intent2) {
        this.zzimh = zzb;
        this.val$intent = intent;
        this.zzimg = intent2;
    }

    public final void run() {
        this.zzimh.handleIntent(this.val$intent);
        this.zzimh.zzh(this.zzimg);
    }
}
