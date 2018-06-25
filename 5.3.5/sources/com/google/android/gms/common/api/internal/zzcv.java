package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.BinderThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzbt;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.internal.zzcyg;
import com.google.android.gms.internal.zzcyj;
import com.google.android.gms.internal.zzcyk;
import com.google.android.gms.internal.zzcyo;
import com.google.android.gms.internal.zzcyw;
import java.util.Set;

public final class zzcv extends zzcyo implements ConnectionCallbacks, OnConnectionFailedListener {
    private static zza<? extends zzcyj, zzcyk> zzgbc = zzcyg.zzegv;
    private final Context mContext;
    private final Handler mHandler;
    private Set<Scope> zzenh;
    private final zza<? extends zzcyj, zzcyk> zzfsa;
    private zzr zzfwf;
    private zzcyj zzfxl;
    private zzcy zzgbd;

    @WorkerThread
    public zzcv(Context context, Handler handler, @NonNull zzr zzr) {
        this(context, handler, zzr, zzgbc);
    }

    @WorkerThread
    public zzcv(Context context, Handler handler, @NonNull zzr zzr, zza<? extends zzcyj, zzcyk> zza) {
        this.mContext = context;
        this.mHandler = handler;
        this.zzfwf = (zzr) zzbq.checkNotNull(zzr, "ClientSettings must not be null");
        this.zzenh = zzr.zzamf();
        this.zzfsa = zza;
    }

    @WorkerThread
    private final void zzc(zzcyw zzcyw) {
        ConnectionResult zzain = zzcyw.zzain();
        if (zzain.isSuccess()) {
            zzbt zzbfa = zzcyw.zzbfa();
            ConnectionResult zzain2 = zzbfa.zzain();
            if (zzain2.isSuccess()) {
                this.zzgbd.zzb(zzbfa.zzamy(), this.zzenh);
            } else {
                String valueOf = String.valueOf(zzain2);
                Log.wtf("SignInCoordinator", new StringBuilder(String.valueOf(valueOf).length() + 48).append("Sign-in succeeded with resolve account failure: ").append(valueOf).toString(), new Exception());
                this.zzgbd.zzh(zzain2);
                this.zzfxl.disconnect();
                return;
            }
        }
        this.zzgbd.zzh(zzain);
        this.zzfxl.disconnect();
    }

    @WorkerThread
    public final void onConnected(@Nullable Bundle bundle) {
        this.zzfxl.zza(this);
    }

    @WorkerThread
    public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        this.zzgbd.zzh(connectionResult);
    }

    @WorkerThread
    public final void onConnectionSuspended(int i) {
        this.zzfxl.disconnect();
    }

    @WorkerThread
    public final void zza(zzcy zzcy) {
        if (this.zzfxl != null) {
            this.zzfxl.disconnect();
        }
        this.zzfwf.zzc(Integer.valueOf(System.identityHashCode(this)));
        this.zzfxl = (zzcyj) this.zzfsa.zza(this.mContext, this.mHandler.getLooper(), this.zzfwf, this.zzfwf.zzaml(), this, this);
        this.zzgbd = zzcy;
        if (this.zzenh == null || this.zzenh.isEmpty()) {
            this.mHandler.post(new zzcw(this));
        } else {
            this.zzfxl.connect();
        }
    }

    public final zzcyj zzakn() {
        return this.zzfxl;
    }

    public final void zzakz() {
        if (this.zzfxl != null) {
            this.zzfxl.disconnect();
        }
    }

    @BinderThread
    public final void zzb(zzcyw zzcyw) {
        this.mHandler.post(new zzcx(this, zzcyw));
    }
}
