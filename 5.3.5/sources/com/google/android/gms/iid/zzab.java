package com.google.android.gms.iid;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

final class zzab extends Handler {
    private /* synthetic */ zzaa zzioa;

    zzab(zzaa zzaa, Looper looper) {
        this.zzioa = zzaa;
        super(looper);
    }

    public final void handleMessage(Message message) {
        this.zzioa.zzd(message);
    }
}
