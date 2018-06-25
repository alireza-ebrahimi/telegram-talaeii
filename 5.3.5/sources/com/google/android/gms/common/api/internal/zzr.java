package com.google.android.gms.common.api.internal;

import android.app.Dialog;

final class zzr extends zzby {
    private /* synthetic */ Dialog zzfuz;
    private /* synthetic */ zzq zzfva;

    zzr(zzq zzq, Dialog dialog) {
        this.zzfva = zzq;
        this.zzfuz = dialog;
    }

    public final void zzaio() {
        this.zzfva.zzfuy.zzail();
        if (this.zzfuz.isShowing()) {
            this.zzfuz.dismiss();
        }
    }
}
