package com.google.firebase.iid;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.C1897b;
import java.io.IOException;
import java.util.concurrent.Executor;

final class ai implements IRpc {
    /* renamed from: a */
    private final C1897b f5692a;
    /* renamed from: b */
    private final C1938i f5693b;
    /* renamed from: c */
    private final C1943o f5694c;
    /* renamed from: d */
    private final Executor f5695d;

    ai(C1897b c1897b, C1938i c1938i, Executor executor) {
        this(c1897b, c1938i, executor, new C1943o(c1897b.m8690a(), c1938i));
    }

    private ai(C1897b c1897b, C1938i c1938i, Executor executor, C1943o c1943o) {
        this.f5692a = c1897b;
        this.f5693b = c1938i;
        this.f5694c = c1943o;
        this.f5695d = executor;
    }

    /* renamed from: a */
    private final <T> Task<Void> m8799a(Task<T> task) {
        return task.continueWith(this.f5695d, new ak(this));
    }

    /* renamed from: a */
    private final Task<Bundle> m8800a(String str, String str2, String str3, Bundle bundle) {
        bundle.putString("scope", str3);
        bundle.putString("sender", str2);
        bundle.putString("subtype", str2);
        bundle.putString("appid", str);
        bundle.putString("gmp_app_id", this.f5692a.m8696c().m8753b());
        bundle.putString("gmsv", Integer.toString(this.f5693b.m8857d()));
        bundle.putString("osv", Integer.toString(VERSION.SDK_INT));
        bundle.putString("app_ver", this.f5693b.m8855b());
        bundle.putString("app_ver_name", this.f5693b.m8856c());
        bundle.putString("cliv", "fiid-12451000");
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        this.f5695d.execute(new aj(this, bundle, taskCompletionSource));
        return taskCompletionSource.getTask();
    }

    /* renamed from: a */
    private static String m8801a(Bundle bundle) {
        if (bundle == null) {
            throw new IOException("SERVICE_NOT_AVAILABLE");
        }
        String string = bundle.getString("registration_id");
        if (string == null) {
            string = bundle.getString("unregistered");
            if (string == null) {
                string = bundle.getString("error");
                if ("RST".equals(string)) {
                    throw new IOException("INSTANCE_ID_RESET");
                } else if (string != null) {
                    throw new IOException(string);
                } else {
                    String valueOf = String.valueOf(bundle);
                    Log.w("FirebaseInstanceId", new StringBuilder(String.valueOf(valueOf).length() + 21).append("Unexpected response: ").append(valueOf).toString(), new Throwable());
                    throw new IOException("SERVICE_NOT_AVAILABLE");
                }
            }
        }
        return string;
    }

    /* renamed from: b */
    private final Task<String> m8803b(Task<Bundle> task) {
        return task.continueWith(this.f5695d, new al(this));
    }

    /* renamed from: a */
    final /* synthetic */ void m8804a(Bundle bundle, TaskCompletionSource taskCompletionSource) {
        try {
            taskCompletionSource.setResult(this.f5694c.m8869a(bundle));
        } catch (Exception e) {
            taskCompletionSource.setException(e);
        }
    }

    public final Task<Void> ackMessage(String str) {
        return null;
    }

    public final Task<String> buildChannel(String str) {
        return Tasks.forResult(TtmlNode.ANONYMOUS_REGION_ID);
    }

    public final Task<Void> deleteInstanceId(String str) {
        Bundle bundle = new Bundle();
        bundle.putString("iid-operation", "delete");
        bundle.putString("delete", "1");
        return m8799a(m8803b(m8800a(str, "*", "*", bundle)));
    }

    public final Task<Void> deleteToken(String str, String str2, String str3) {
        Bundle bundle = new Bundle();
        bundle.putString("delete", "1");
        return m8799a(m8803b(m8800a(str, str2, str3, bundle)));
    }

    public final Task<String> getToken(String str, String str2, String str3) {
        return m8803b(m8800a(str, str2, str3, new Bundle()));
    }

    public final Task<Void> subscribeToTopic(String str, String str2, String str3) {
        Bundle bundle = new Bundle();
        String str4 = "gcm.topic";
        String valueOf = String.valueOf("/topics/");
        String valueOf2 = String.valueOf(str3);
        bundle.putString(str4, valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
        str4 = String.valueOf("/topics/");
        valueOf2 = String.valueOf(str3);
        return m8799a(m8803b(m8800a(str, str2, valueOf2.length() != 0 ? str4.concat(valueOf2) : new String(str4), bundle)));
    }

    public final Task<Void> unsubscribeFromTopic(String str, String str2, String str3) {
        Bundle bundle = new Bundle();
        String str4 = "gcm.topic";
        String valueOf = String.valueOf("/topics/");
        String valueOf2 = String.valueOf(str3);
        bundle.putString(str4, valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
        bundle.putString("delete", "1");
        str4 = String.valueOf("/topics/");
        valueOf2 = String.valueOf(str3);
        return m8799a(m8803b(m8800a(str, str2, valueOf2.length() != 0 ? str4.concat(valueOf2) : new String(str4), bundle)));
    }
}
