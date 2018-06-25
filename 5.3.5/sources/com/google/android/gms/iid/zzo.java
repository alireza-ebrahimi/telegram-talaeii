package com.google.android.gms.iid;

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
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.stats.zza;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

final class zzo implements ServiceConnection {
    int state;
    final Messenger zzing;
    zzt zzinh;
    final Queue<zzu<?>> zzini;
    final SparseArray<zzu<?>> zzinj;
    final /* synthetic */ zzm zzink;

    private zzo(zzm zzm) {
        this.zzink = zzm;
        this.state = 0;
        this.zzing = new Messenger(new Handler(Looper.getMainLooper(), new zzp(this)));
        this.zzini = new ArrayDeque();
        this.zzinj = new SparseArray();
    }

    private final void zza(zzv zzv) {
        for (zzu zzb : this.zzini) {
            zzb.zzb(zzv);
        }
        this.zzini.clear();
        for (int i = 0; i < this.zzinj.size(); i++) {
            ((zzu) this.zzinj.valueAt(i)).zzb(zzv);
        }
        this.zzinj.clear();
    }

    private final void zzawt() {
        this.zzink.zzind.execute(new zzr(this));
    }

    public final synchronized void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        if (Log.isLoggable("MessengerIpcClient", 2)) {
            Log.v("MessengerIpcClient", "Service connected");
        }
        if (iBinder == null) {
            zzl(0, "Null service connection");
        } else {
            try {
                this.zzinh = new zzt(iBinder);
                this.state = 2;
                zzawt();
            } catch (RemoteException e) {
                zzl(0, e.getMessage());
            }
        }
    }

    public final synchronized void onServiceDisconnected(ComponentName componentName) {
        if (Log.isLoggable("MessengerIpcClient", 2)) {
            Log.v("MessengerIpcClient", "Service disconnected");
        }
        zzl(2, "Service disconnected");
    }

    final synchronized void zzawu() {
        if (this.state == 2 && this.zzini.isEmpty() && this.zzinj.size() == 0) {
            if (Log.isLoggable("MessengerIpcClient", 2)) {
                Log.v("MessengerIpcClient", "Finished handling requests, unbinding");
            }
            this.state = 3;
            zza.zzanm();
            this.zzink.zzaiq.unbindService(this);
        }
    }

    final synchronized void zzawv() {
        if (this.state == 1) {
            zzl(1, "Timed out while binding");
        }
    }

    final synchronized boolean zzb(zzu zzu) {
        boolean z = false;
        boolean z2 = true;
        synchronized (this) {
            switch (this.state) {
                case 0:
                    this.zzini.add(zzu);
                    if (this.state == 0) {
                        z = true;
                    }
                    zzbq.checkState(z);
                    if (Log.isLoggable("MessengerIpcClient", 2)) {
                        Log.v("MessengerIpcClient", "Starting bind to GmsCore");
                    }
                    this.state = 1;
                    Intent intent = new Intent("com.google.android.c2dm.intent.REGISTER");
                    intent.setPackage("com.google.android.gms");
                    if (!zza.zzanm().zza(this.zzink.zzaiq, intent, this, 1)) {
                        zzl(0, "Unable to bind to service");
                        break;
                    }
                    this.zzink.zzind.schedule(new zzq(this), 30, TimeUnit.SECONDS);
                    break;
                case 1:
                    this.zzini.add(zzu);
                    break;
                case 2:
                    this.zzini.add(zzu);
                    zzawt();
                    break;
                case 3:
                case 4:
                    z2 = false;
                    break;
                default:
                    throw new IllegalStateException("Unknown state: " + this.state);
            }
        }
        return z2;
    }

    final boolean zzc(Message message) {
        int i = message.arg1;
        if (Log.isLoggable("MessengerIpcClient", 3)) {
            Log.d("MessengerIpcClient", "Received response to request: " + i);
        }
        synchronized (this) {
            zzu zzu = (zzu) this.zzinj.get(i);
            if (zzu == null) {
                Log.w("MessengerIpcClient", "Received response for unknown request: " + i);
            } else {
                this.zzinj.remove(i);
                zzawu();
                Bundle data = message.getData();
                if (data.getBoolean("unsupported", false)) {
                    zzu.zzb(new zzv(4, "Not supported by GmsCore"));
                } else {
                    zzu.zzx(data);
                }
            }
        }
        return true;
    }

    final synchronized void zzec(int i) {
        zzu zzu = (zzu) this.zzinj.get(i);
        if (zzu != null) {
            Log.w("MessengerIpcClient", "Timing out request: " + i);
            this.zzinj.remove(i);
            zzu.zzb(new zzv(3, "Timed out waiting for response"));
            zzawu();
        }
    }

    final synchronized void zzl(int i, String str) {
        if (Log.isLoggable("MessengerIpcClient", 3)) {
            String str2 = "MessengerIpcClient";
            String str3 = "Disconnected: ";
            String valueOf = String.valueOf(str);
            Log.d(str2, valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
        }
        switch (this.state) {
            case 0:
                throw new IllegalStateException();
            case 1:
            case 2:
                if (Log.isLoggable("MessengerIpcClient", 2)) {
                    Log.v("MessengerIpcClient", "Unbinding service");
                }
                this.state = 4;
                zza.zzanm();
                this.zzink.zzaiq.unbindService(this);
                zza(new zzv(i, str));
                break;
            case 3:
                this.state = 4;
                break;
            case 4:
                break;
            default:
                throw new IllegalStateException("Unknown state: " + this.state);
        }
    }
}
