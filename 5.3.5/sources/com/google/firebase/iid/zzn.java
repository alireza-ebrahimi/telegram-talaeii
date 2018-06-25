package com.google.firebase.iid;

import android.os.Handler.Callback;
import android.os.Message;

final /* synthetic */ class zzn implements Callback {
    private final zzm zzola;

    zzn(zzm zzm) {
        this.zzola = zzm;
    }

    public final boolean handleMessage(Message message) {
        return this.zzola.zzc(message);
    }
}
