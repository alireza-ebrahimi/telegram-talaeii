package com.google.firebase.iid;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.util.SparseArray;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.stats.ConnectionTracker;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import javax.annotation.concurrent.GuardedBy;

final class ar implements ServiceConnection {
    @GuardedBy("this")
    /* renamed from: a */
    int f5707a;
    /* renamed from: b */
    final Messenger f5708b;
    /* renamed from: c */
    C1933d f5709c;
    @GuardedBy("this")
    /* renamed from: d */
    final Queue<C1934f<?>> f5710d;
    @GuardedBy("this")
    /* renamed from: e */
    final SparseArray<C1934f<?>> f5711e;
    /* renamed from: f */
    final /* synthetic */ ap f5712f;

    private ar(ap apVar) {
        this.f5712f = apVar;
        this.f5707a = 0;
        this.f5708b = new Messenger(new Handler(Looper.getMainLooper(), new as(this)));
        this.f5710d = new ArrayDeque();
        this.f5711e = new SparseArray();
    }

    /* renamed from: c */
    private final void m8833c() {
        this.f5712f.f5704c.execute(new C1931b(this));
    }

    /* renamed from: a */
    final synchronized void m8834a() {
        if (this.f5707a == 2 && this.f5710d.isEmpty() && this.f5711e.size() == 0) {
            if (Log.isLoggable("MessengerIpcClient", 2)) {
                Log.v("MessengerIpcClient", "Finished handling requests, unbinding");
            }
            this.f5707a = 3;
            ConnectionTracker.getInstance().unbindService(this.f5712f.f5703b, this);
        }
    }

    /* renamed from: a */
    final synchronized void m8835a(int i) {
        C1934f c1934f = (C1934f) this.f5711e.get(i);
        if (c1934f != null) {
            Log.w("MessengerIpcClient", "Timing out request: " + i);
            this.f5711e.remove(i);
            c1934f.m8842a(new C1936g(3, "Timed out waiting for response"));
            m8834a();
        }
    }

    /* renamed from: a */
    final synchronized void m8836a(int i, String str) {
        if (Log.isLoggable("MessengerIpcClient", 3)) {
            String str2 = "MessengerIpcClient";
            String str3 = "Disconnected: ";
            String valueOf = String.valueOf(str);
            Log.d(str2, valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
        }
        switch (this.f5707a) {
            case 0:
                throw new IllegalStateException();
            case 1:
            case 2:
                if (Log.isLoggable("MessengerIpcClient", 2)) {
                    Log.v("MessengerIpcClient", "Unbinding service");
                }
                this.f5707a = 4;
                ConnectionTracker.getInstance().unbindService(this.f5712f.f5703b, this);
                C1936g c1936g = new C1936g(i, str);
                for (C1934f a : this.f5710d) {
                    a.m8842a(c1936g);
                }
                this.f5710d.clear();
                for (int i2 = 0; i2 < this.f5711e.size(); i2++) {
                    ((C1934f) this.f5711e.valueAt(i2)).m8842a(c1936g);
                }
                this.f5711e.clear();
                break;
            case 3:
                this.f5707a = 4;
                break;
            case 4:
                break;
            default:
                throw new IllegalStateException("Unknown state: " + this.f5707a);
        }
    }

    /* renamed from: a */
    final boolean m8837a(Message message) {
        int i = message.arg1;
        if (Log.isLoggable("MessengerIpcClient", 3)) {
            Log.d("MessengerIpcClient", "Received response to request: " + i);
        }
        synchronized (this) {
            C1934f c1934f = (C1934f) this.f5711e.get(i);
            if (c1934f == null) {
                Log.w("MessengerIpcClient", "Received response for unknown request: " + i);
            } else {
                this.f5711e.remove(i);
                m8834a();
                Bundle data = message.getData();
                if (data.getBoolean("unsupported", false)) {
                    c1934f.m8842a(new C1936g(4, "Not supported by GmsCore"));
                } else {
                    c1934f.mo3055a(data);
                }
            }
        }
        return true;
    }

    /* renamed from: a */
    final synchronized boolean m8838a(C1934f c1934f) {
        boolean z = false;
        boolean z2 = true;
        synchronized (this) {
            switch (this.f5707a) {
                case 0:
                    this.f5710d.add(c1934f);
                    if (this.f5707a == 0) {
                        z = true;
                    }
                    Preconditions.checkState(z);
                    if (Log.isLoggable("MessengerIpcClient", 2)) {
                        Log.v("MessengerIpcClient", "Starting bind to GmsCore");
                    }
                    this.f5707a = 1;
                    Intent intent = new Intent("com.google.android.c2dm.intent.REGISTER");
                    intent.setPackage("com.google.android.gms");
                    if (!ConnectionTracker.getInstance().bindService(this.f5712f.f5703b, intent, this, 1)) {
                        m8836a(0, "Unable to bind to service");
                        break;
                    }
                    this.f5712f.f5704c.schedule(new at(this), 30, TimeUnit.SECONDS);
                    break;
                case 1:
                    this.f5710d.add(c1934f);
                    break;
                case 2:
                    this.f5710d.add(c1934f);
                    m8833c();
                    break;
                case 3:
                case 4:
                    z2 = false;
                    break;
                default:
                    throw new IllegalStateException("Unknown state: " + this.f5707a);
            }
        }
        return z2;
    }

    /* renamed from: b */
    final synchronized void m8839b() {
        if (this.f5707a == 1) {
            m8836a(1, "Timed out while binding");
        }
    }

    public final synchronized void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        if (Log.isLoggable("MessengerIpcClient", 2)) {
            Log.v("MessengerIpcClient", "Service connected");
        }
        if (iBinder == null) {
            m8836a(0, "Null service connection");
        } else {
            try {
                this.f5709c = new C1933d(iBinder);
                this.f5707a = 2;
                m8833c();
            } catch (RemoteException e) {
                m8836a(0, e.getMessage());
            }
        }
    }

    public final synchronized void onServiceDisconnected(ComponentName componentName) {
        if (Log.isLoggable("MessengerIpcClient", 2)) {
            Log.v("MessengerIpcClient", "Service disconnected");
        }
        m8836a(2, "Service disconnected");
    }
}
