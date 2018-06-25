package com.google.firebase.iid;

import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.tasks.TaskCompletionSource;

abstract class zzt<T> {
    final int what;
    final TaskCompletionSource<T> zzgyc = new TaskCompletionSource();
    final int zzino;
    final Bundle zzinp;

    zzt(int i, int i2, Bundle bundle) {
        this.zzino = i;
        this.what = i2;
        this.zzinp = bundle;
    }

    final void finish(T t) {
        if (Log.isLoggable("MessengerIpcClient", 3)) {
            String valueOf = String.valueOf(this);
            String valueOf2 = String.valueOf(t);
            Log.d("MessengerIpcClient", new StringBuilder((String.valueOf(valueOf).length() + 16) + String.valueOf(valueOf2).length()).append("Finishing ").append(valueOf).append(" with ").append(valueOf2).toString());
        }
        this.zzgyc.setResult(t);
    }

    public String toString() {
        int i = this.what;
        int i2 = this.zzino;
        return "Request { what=" + i + " id=" + i2 + " oneWay=" + zzaww() + "}";
    }

    abstract boolean zzaww();

    final void zzb(zzu zzu) {
        if (Log.isLoggable("MessengerIpcClient", 3)) {
            String valueOf = String.valueOf(this);
            String valueOf2 = String.valueOf(zzu);
            Log.d("MessengerIpcClient", new StringBuilder((String.valueOf(valueOf).length() + 14) + String.valueOf(valueOf2).length()).append("Failing ").append(valueOf).append(" with ").append(valueOf2).toString());
        }
        this.zzgyc.setException(zzu);
    }

    abstract void zzx(Bundle bundle);
}
