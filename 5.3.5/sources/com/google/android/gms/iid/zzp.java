package com.google.android.gms.iid;

import android.os.Handler.Callback;
import android.os.Message;

final /* synthetic */ class zzp implements Callback {
    private final zzo zzinl;

    zzp(zzo zzo) {
        this.zzinl = zzo;
    }

    public final boolean handleMessage(Message message) {
        return this.zzinl.zzc(message);
    }
}
