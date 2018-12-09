package com.google.android.gms.common.internal;

import android.content.Intent;
import com.google.android.gms.common.api.internal.LifecycleFragment;

final class zzd extends DialogRedirect {
    private final /* synthetic */ int val$requestCode;
    private final /* synthetic */ Intent zzsh;
    private final /* synthetic */ LifecycleFragment zzsi;

    zzd(Intent intent, LifecycleFragment lifecycleFragment, int i) {
        this.zzsh = intent;
        this.zzsi = lifecycleFragment;
        this.val$requestCode = i;
    }

    public final void redirect() {
        if (this.zzsh != null) {
            this.zzsi.startActivityForResult(this.zzsh, this.val$requestCode);
        }
    }
}
