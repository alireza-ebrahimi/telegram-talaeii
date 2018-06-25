package com.google.android.gms.common.internal;

import android.content.Intent;
import android.support.v4.app.Fragment;

final class zzc extends DialogRedirect {
    private final /* synthetic */ Fragment val$fragment;
    private final /* synthetic */ int val$requestCode;
    private final /* synthetic */ Intent zzsh;

    zzc(Intent intent, Fragment fragment, int i) {
        this.zzsh = intent;
        this.val$fragment = fragment;
        this.val$requestCode = i;
    }

    public final void redirect() {
        if (this.zzsh != null) {
            this.val$fragment.startActivityForResult(this.zzsh, this.val$requestCode);
        }
    }
}
