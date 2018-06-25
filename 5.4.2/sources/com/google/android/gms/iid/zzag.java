package com.google.android.gms.iid;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

final class zzag extends Handler {
    private final /* synthetic */ zzaf zzcx;

    zzag(zzaf zzaf, Looper looper) {
        this.zzcx = zzaf;
        super(looper);
    }

    public final void handleMessage(Message message) {
        this.zzcx.zze(message);
    }
}
