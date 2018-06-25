package com.google.firebase.iid;

import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.tasks.TaskCompletionSource;

/* renamed from: com.google.firebase.iid.f */
abstract class C1934f<T> {
    /* renamed from: a */
    final int f5720a;
    /* renamed from: b */
    final TaskCompletionSource<T> f5721b = new TaskCompletionSource();
    /* renamed from: c */
    final int f5722c;
    /* renamed from: d */
    final Bundle f5723d;

    C1934f(int i, int i2, Bundle bundle) {
        this.f5720a = i;
        this.f5722c = i2;
        this.f5723d = bundle;
    }

    /* renamed from: a */
    abstract void mo3055a(Bundle bundle);

    /* renamed from: a */
    final void m8842a(C1936g c1936g) {
        if (Log.isLoggable("MessengerIpcClient", 3)) {
            String valueOf = String.valueOf(this);
            String valueOf2 = String.valueOf(c1936g);
            Log.d("MessengerIpcClient", new StringBuilder((String.valueOf(valueOf).length() + 14) + String.valueOf(valueOf2).length()).append("Failing ").append(valueOf).append(" with ").append(valueOf2).toString());
        }
        this.f5721b.setException(c1936g);
    }

    /* renamed from: a */
    final void m8843a(T t) {
        if (Log.isLoggable("MessengerIpcClient", 3)) {
            String valueOf = String.valueOf(this);
            String valueOf2 = String.valueOf(t);
            Log.d("MessengerIpcClient", new StringBuilder((String.valueOf(valueOf).length() + 16) + String.valueOf(valueOf2).length()).append("Finishing ").append(valueOf).append(" with ").append(valueOf2).toString());
        }
        this.f5721b.setResult(t);
    }

    /* renamed from: a */
    abstract boolean mo3056a();

    public String toString() {
        int i = this.f5722c;
        int i2 = this.f5720a;
        return "Request { what=" + i + " id=" + i2 + " oneWay=" + mo3056a() + "}";
    }
}
