package com.google.firebase.iid;

import android.support.v4.p022f.C0464a;
import android.util.Log;
import android.util.Pair;
import com.google.android.gms.tasks.Task;
import java.util.Map;
import javax.annotation.concurrent.GuardedBy;

/* renamed from: com.google.firebase.iid.l */
final class C1941l {
    @GuardedBy("this")
    /* renamed from: a */
    private final Map<Pair<String, String>, Task<String>> f5732a = new C0464a();

    C1941l() {
    }

    /* renamed from: a */
    final /* synthetic */ Task m8860a(Pair pair, Task task) {
        synchronized (this) {
            this.f5732a.remove(pair);
        }
        return task;
    }

    /* renamed from: a */
    final synchronized Task<String> m8861a(String str, String str2, C1930n c1930n) {
        Task<String> task;
        Pair pair = new Pair(str, str2);
        task = (Task) this.f5732a.get(pair);
        if (task == null) {
            if (Log.isLoggable("FirebaseInstanceId", 3)) {
                String valueOf = String.valueOf(pair);
                Log.d("FirebaseInstanceId", new StringBuilder(String.valueOf(valueOf).length() + 24).append("Making new request for: ").append(valueOf).toString());
            }
            task = c1930n.mo3047a().continueWithTask(FirebaseInstanceId.f5647a, new C1942m(this, pair));
            this.f5732a.put(pair, task);
        } else if (Log.isLoggable("FirebaseInstanceId", 3)) {
            String valueOf2 = String.valueOf(pair);
            Log.d("FirebaseInstanceId", new StringBuilder(String.valueOf(valueOf2).length() + 29).append("Joining ongoing request for: ").append(valueOf2).toString());
        }
        return task;
    }
}
