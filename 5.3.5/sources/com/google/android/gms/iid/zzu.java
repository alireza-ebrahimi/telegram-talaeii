package com.google.android.gms.iid;

import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.tasks.TaskCompletionSource;

abstract class zzu<T> {
    final int what;
    final TaskCompletionSource<T> zzgyc = new TaskCompletionSource();
    final int zzino;
    final Bundle zzinp;

    zzu(int i, int i2, Bundle bundle) {
        this.zzino = i;
        this.what = i2;
        this.zzinp = bundle;
    }

    public String toString() {
        int i = this.what;
        int i2 = this.zzino;
        zzaww();
        return "Request { what=" + i + " id=" + i2 + " oneWay=false}";
    }

    abstract boolean zzaww();

    final void zzb(zzv zzv) {
        if (Log.isLoggable("MessengerIpcClient", 3)) {
            String valueOf = String.valueOf(this);
            String valueOf2 = String.valueOf(zzv);
            Log.d("MessengerIpcClient", new StringBuilder((String.valueOf(valueOf).length() + 14) + String.valueOf(valueOf2).length()).append("Failing ").append(valueOf).append(" with ").append(valueOf2).toString());
        }
        this.zzgyc.setException(zzv);
    }

    abstract void zzx(Bundle bundle);
}
