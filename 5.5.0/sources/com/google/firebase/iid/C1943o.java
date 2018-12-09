package com.google.firebase.iid;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.v4.p022f.C0463k;
import android.util.Log;
import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.iid.zzi.C1954a;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.concurrent.GuardedBy;

/* renamed from: com.google.firebase.iid.o */
final class C1943o {
    /* renamed from: a */
    private static int f5735a = 0;
    /* renamed from: b */
    private static PendingIntent f5736b;
    @GuardedBy("responseCallbacks")
    /* renamed from: c */
    private final C0463k<String, TaskCompletionSource<Bundle>> f5737c = new C0463k();
    /* renamed from: d */
    private final Context f5738d;
    /* renamed from: e */
    private final C1938i f5739e;
    /* renamed from: f */
    private Messenger f5740f;
    /* renamed from: g */
    private Messenger f5741g;
    /* renamed from: h */
    private zzi f5742h;

    public C1943o(Context context, C1938i c1938i) {
        this.f5738d = context;
        this.f5739e = c1938i;
        this.f5740f = new Messenger(new C1944p(this, Looper.getMainLooper()));
    }

    /* renamed from: a */
    private static synchronized String m8862a() {
        String num;
        synchronized (C1943o.class) {
            int i = f5735a;
            f5735a = i + 1;
            num = Integer.toString(i);
        }
        return num;
    }

    /* renamed from: a */
    private static synchronized void m8863a(Context context, Intent intent) {
        synchronized (C1943o.class) {
            if (f5736b == null) {
                Intent intent2 = new Intent();
                intent2.setPackage("com.google.example.invalidpackage");
                f5736b = PendingIntent.getBroadcast(context, 0, intent2, 0);
            }
            intent.putExtra("app", f5736b);
        }
    }

    /* renamed from: a */
    private final void m8864a(Message message) {
        if (message == null || !(message.obj instanceof Intent)) {
            Log.w("FirebaseInstanceId", "Dropping invalid message");
            return;
        }
        Intent intent = (Intent) message.obj;
        intent.setExtrasClassLoader(new C1954a());
        if (intent.hasExtra("google.messenger")) {
            Parcelable parcelableExtra = intent.getParcelableExtra("google.messenger");
            if (parcelableExtra instanceof zzi) {
                this.f5742h = (zzi) parcelableExtra;
            }
            if (parcelableExtra instanceof Messenger) {
                this.f5741g = (Messenger) parcelableExtra;
            }
        }
        intent = (Intent) message.obj;
        String action = intent.getAction();
        String stringExtra;
        String valueOf;
        String str;
        if ("com.google.android.c2dm.intent.REGISTRATION".equals(action)) {
            CharSequence stringExtra2 = intent.getStringExtra("registration_id");
            if (stringExtra2 == null) {
                stringExtra2 = intent.getStringExtra("unregistered");
            }
            if (stringExtra2 == null) {
                stringExtra = intent.getStringExtra("error");
                if (stringExtra == null) {
                    valueOf = String.valueOf(intent.getExtras());
                    Log.w("FirebaseInstanceId", new StringBuilder(String.valueOf(valueOf).length() + 49).append("Unexpected response, no error or registration id ").append(valueOf).toString());
                    return;
                }
                if (Log.isLoggable("FirebaseInstanceId", 3)) {
                    str = "FirebaseInstanceId";
                    String str2 = "Received InstanceID error ";
                    action = String.valueOf(stringExtra);
                    Log.d(str, action.length() != 0 ? str2.concat(action) : new String(str2));
                }
                if (stringExtra.startsWith("|")) {
                    String[] split = stringExtra.split("\\|");
                    if (split.length <= 2 || !"ID".equals(split[1])) {
                        action = "FirebaseInstanceId";
                        str = "Unexpected structured response ";
                        valueOf = String.valueOf(stringExtra);
                        Log.w(action, valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
                        return;
                    }
                    stringExtra = split[2];
                    action = split[3];
                    if (action.startsWith(":")) {
                        action = action.substring(1);
                    }
                    m8866a(stringExtra, intent.putExtra("error", action).getExtras());
                    return;
                }
                synchronized (this.f5737c) {
                    for (int i = 0; i < this.f5737c.size(); i++) {
                        m8866a((String) this.f5737c.m1985b(i), intent.getExtras());
                    }
                }
                return;
            }
            Matcher matcher = Pattern.compile("\\|ID\\|([^|]+)\\|:?+(.*)").matcher(stringExtra2);
            if (matcher.matches()) {
                action = matcher.group(1);
                stringExtra = matcher.group(2);
                Bundle extras = intent.getExtras();
                extras.putString("registration_id", stringExtra);
                m8866a(action, extras);
            } else if (Log.isLoggable("FirebaseInstanceId", 3)) {
                stringExtra = "FirebaseInstanceId";
                str = "Unexpected response string: ";
                valueOf = String.valueOf(stringExtra2);
                Log.d(stringExtra, valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
            }
        } else if (Log.isLoggable("FirebaseInstanceId", 3)) {
            stringExtra = "FirebaseInstanceId";
            str = "Unexpected response action: ";
            valueOf = String.valueOf(action);
            Log.d(stringExtra, valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
        }
    }

    /* renamed from: a */
    private final void m8866a(String str, Bundle bundle) {
        synchronized (this.f5737c) {
            TaskCompletionSource taskCompletionSource = (TaskCompletionSource) this.f5737c.remove(str);
            if (taskCompletionSource == null) {
                String str2 = "FirebaseInstanceId";
                String str3 = "Missing callback for ";
                String valueOf = String.valueOf(str);
                Log.w(str2, valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
                return;
            }
            taskCompletionSource.setResult(bundle);
        }
    }

    /* renamed from: b */
    private final Bundle m8867b(Bundle bundle) {
        Bundle c = m8868c(bundle);
        if (c == null || !c.containsKey("google.messenger")) {
            return c;
        }
        c = m8868c(bundle);
        return (c == null || !c.containsKey("google.messenger")) ? c : null;
    }

    /* renamed from: c */
    private final Bundle m8868c(Bundle bundle) {
        String a = C1943o.m8862a();
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        synchronized (this.f5737c) {
            this.f5737c.put(a, taskCompletionSource);
        }
        if (this.f5739e.m8854a() == 0) {
            throw new IOException(InstanceID.ERROR_MISSING_INSTANCEID_SERVICE);
        }
        Intent intent = new Intent();
        intent.setPackage("com.google.android.gms");
        if (this.f5739e.m8854a() == 2) {
            intent.setAction("com.google.iid.TOKEN_REQUEST");
        } else {
            intent.setAction("com.google.android.c2dm.intent.REGISTER");
        }
        intent.putExtras(bundle);
        C1943o.m8863a(this.f5738d, intent);
        intent.putExtra("kid", new StringBuilder(String.valueOf(a).length() + 5).append("|ID|").append(a).append("|").toString());
        if (Log.isLoggable("FirebaseInstanceId", 3)) {
            String valueOf = String.valueOf(intent.getExtras());
            Log.d("FirebaseInstanceId", new StringBuilder(String.valueOf(valueOf).length() + 8).append("Sending ").append(valueOf).toString());
        }
        intent.putExtra("google.messenger", this.f5740f);
        if (!(this.f5741g == null && this.f5742h == null)) {
            Message obtain = Message.obtain();
            obtain.obj = intent;
            try {
                if (this.f5741g != null) {
                    this.f5741g.send(obtain);
                } else {
                    this.f5742h.m8899a(obtain);
                }
            } catch (RemoteException e) {
                if (Log.isLoggable("FirebaseInstanceId", 3)) {
                    Log.d("FirebaseInstanceId", "Messenger failed, fallback to startService");
                }
            }
            Bundle bundle2 = (Bundle) Tasks.await(taskCompletionSource.getTask(), 30000, TimeUnit.MILLISECONDS);
            synchronized (this.f5737c) {
                this.f5737c.remove(a);
            }
            return bundle2;
        }
        if (this.f5739e.m8854a() == 2) {
            this.f5738d.sendBroadcast(intent);
        } else {
            this.f5738d.startService(intent);
        }
        try {
            Bundle bundle22 = (Bundle) Tasks.await(taskCompletionSource.getTask(), 30000, TimeUnit.MILLISECONDS);
            synchronized (this.f5737c) {
                this.f5737c.remove(a);
            }
            return bundle22;
        } catch (InterruptedException e2) {
            Log.w("FirebaseInstanceId", "No response");
            throw new IOException(InstanceID.ERROR_TIMEOUT);
        } catch (TimeoutException e3) {
            Log.w("FirebaseInstanceId", "No response");
            throw new IOException(InstanceID.ERROR_TIMEOUT);
        } catch (Throwable e4) {
            throw new IOException(e4);
        } catch (Throwable th) {
            synchronized (this.f5737c) {
                this.f5737c.remove(a);
            }
        }
    }

    /* renamed from: a */
    final Bundle m8869a(Bundle bundle) {
        Exception e;
        if (this.f5739e.m8857d() < 12000000) {
            return m8867b(bundle);
        }
        try {
            return (Bundle) Tasks.await(ap.m8829a(this.f5738d).m8832b(1, bundle));
        } catch (InterruptedException e2) {
            e = e2;
        } catch (ExecutionException e3) {
            e = e3;
        }
        if (Log.isLoggable("FirebaseInstanceId", 3)) {
            String valueOf = String.valueOf(e);
            Log.d("FirebaseInstanceId", new StringBuilder(String.valueOf(valueOf).length() + 22).append("Error making request: ").append(valueOf).toString());
        }
        return ((e.getCause() instanceof C1936g) && ((C1936g) e.getCause()).m8847a() == 4) ? m8867b(bundle) : null;
    }
}
