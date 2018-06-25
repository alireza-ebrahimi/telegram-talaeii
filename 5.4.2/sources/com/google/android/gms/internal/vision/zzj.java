package com.google.android.gms.internal.vision;

import android.content.Context;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.dynamite.DynamiteModule;
import com.google.android.gms.dynamite.DynamiteModule.LoadingException;
import javax.annotation.concurrent.GuardedBy;

public abstract class zzj<T> {
    private final Context mContext;
    private final Object mLock = new Object();
    private final String mTag;
    private boolean zzci = false;
    @GuardedBy("mLock")
    private T zzcj;

    public zzj(Context context, String str) {
        this.mContext = context;
        this.mTag = str;
    }

    public final boolean isOperational() {
        return zzh() != null;
    }

    protected abstract T zza(DynamiteModule dynamiteModule, Context context);

    protected abstract void zze();

    public final void zzg() {
        synchronized (this.mLock) {
            if (this.zzcj == null) {
                return;
            }
            try {
                zze();
            } catch (Throwable e) {
                Log.e(this.mTag, "Could not finalize native handle", e);
            }
        }
    }

    protected final T zzh() {
        T t;
        Throwable e;
        synchronized (this.mLock) {
            if (this.zzcj != null) {
                t = this.zzcj;
            } else {
                try {
                    this.zzcj = zza(DynamiteModule.load(this.mContext, DynamiteModule.PREFER_HIGHEST_OR_REMOTE_VERSION, "com.google.android.gms.vision.dynamite"), this.mContext);
                } catch (LoadingException e2) {
                    e = e2;
                    Log.e(this.mTag, "Error creating remote native handle", e);
                    if (!!this.zzci) {
                    }
                    Log.w(this.mTag, "Native handle is now available.");
                    t = this.zzcj;
                    return t;
                } catch (RemoteException e3) {
                    e = e3;
                    Log.e(this.mTag, "Error creating remote native handle", e);
                    if (!this.zzci) {
                    }
                    Log.w(this.mTag, "Native handle is now available.");
                    t = this.zzcj;
                    return t;
                }
                if (!this.zzci && this.zzcj == null) {
                    Log.w(this.mTag, "Native handle not yet available. Reverting to no-op handle.");
                    this.zzci = true;
                } else if (this.zzci && this.zzcj != null) {
                    Log.w(this.mTag, "Native handle is now available.");
                }
                t = this.zzcj;
            }
        }
        return t;
    }
}
