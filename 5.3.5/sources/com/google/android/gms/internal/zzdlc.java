package com.google.android.gms.internal;

import android.content.Context;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.dynamite.DynamiteModule;
import com.google.android.gms.dynamite.DynamiteModule.zzc;

@Hide
public abstract class zzdlc<T> {
    private final Context mContext;
    private final Object mLock = new Object();
    private final String mTag;
    private boolean zzlhu = false;
    private T zzlhv;

    public zzdlc(Context context, String str) {
        this.mContext = context;
        this.mTag = str;
    }

    public final boolean isOperational() {
        return zzblo() != null;
    }

    protected abstract T zza(DynamiteModule dynamiteModule, Context context) throws RemoteException, zzc;

    protected abstract void zzbll() throws RemoteException;

    public final void zzbln() {
        synchronized (this.mLock) {
            if (this.zzlhv == null) {
                return;
            }
            try {
                zzbll();
            } catch (Throwable e) {
                Log.e(this.mTag, "Could not finalize native handle", e);
            }
        }
    }

    protected final T zzblo() {
        T t;
        Throwable e;
        synchronized (this.mLock) {
            if (this.zzlhv != null) {
                t = this.zzlhv;
            } else {
                try {
                    this.zzlhv = zza(DynamiteModule.zza(this.mContext, DynamiteModule.zzhdm, "com.google.android.gms.vision.dynamite"), this.mContext);
                } catch (zzc e2) {
                    e = e2;
                    Log.e(this.mTag, "Error creating remote native handle", e);
                    if (!!this.zzlhu) {
                    }
                    Log.w(this.mTag, "Native handle is now available.");
                    t = this.zzlhv;
                    return t;
                } catch (RemoteException e3) {
                    e = e3;
                    Log.e(this.mTag, "Error creating remote native handle", e);
                    if (!this.zzlhu) {
                    }
                    Log.w(this.mTag, "Native handle is now available.");
                    t = this.zzlhv;
                    return t;
                }
                if (!this.zzlhu && this.zzlhv == null) {
                    Log.w(this.mTag, "Native handle not yet available. Reverting to no-op handle.");
                    this.zzlhu = true;
                } else if (this.zzlhu && this.zzlhv != null) {
                    Log.w(this.mTag, "Native handle is now available.");
                }
                t = this.zzlhv;
            }
        }
        return t;
    }
}
