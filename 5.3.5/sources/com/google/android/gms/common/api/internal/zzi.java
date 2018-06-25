package com.google.android.gms.common.api.internal;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.SparseArray;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzbq;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public class zzi extends zzo {
    private final SparseArray<zza> zzfuf = new SparseArray();

    class zza implements OnConnectionFailedListener {
        public final int zzfug;
        public final GoogleApiClient zzfuh;
        public final OnConnectionFailedListener zzfui;
        private /* synthetic */ zzi zzfuj;

        public zza(zzi zzi, int i, GoogleApiClient googleApiClient, OnConnectionFailedListener onConnectionFailedListener) {
            this.zzfuj = zzi;
            this.zzfug = i;
            this.zzfuh = googleApiClient;
            this.zzfui = onConnectionFailedListener;
            googleApiClient.registerConnectionFailedListener(this);
        }

        public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            String valueOf = String.valueOf(connectionResult);
            Log.d("AutoManageHelper", new StringBuilder(String.valueOf(valueOf).length() + 27).append("beginFailureResolution for ").append(valueOf).toString());
            this.zzfuj.zzb(connectionResult, this.zzfug);
        }
    }

    private zzi(zzcf zzcf) {
        super(zzcf);
        this.zzgam.zza("AutoManageHelper", (LifecycleCallback) this);
    }

    public static zzi zza(zzce zzce) {
        zzcf zzb = LifecycleCallback.zzb(zzce);
        zzi zzi = (zzi) zzb.zza("AutoManageHelper", zzi.class);
        return zzi != null ? zzi : new zzi(zzb);
    }

    @Nullable
    private final zza zzbr(int i) {
        return this.zzfuf.size() <= i ? null : (zza) this.zzfuf.get(this.zzfuf.keyAt(i));
    }

    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        for (int i = 0; i < this.zzfuf.size(); i++) {
            zza zzbr = zzbr(i);
            if (zzbr != null) {
                printWriter.append(str).append("GoogleApiClient #").print(zzbr.zzfug);
                printWriter.println(":");
                zzbr.zzfuh.dump(String.valueOf(str).concat("  "), fileDescriptor, printWriter, strArr);
            }
        }
    }

    public final void onStart() {
        super.onStart();
        boolean z = this.mStarted;
        String valueOf = String.valueOf(this.zzfuf);
        Log.d("AutoManageHelper", new StringBuilder(String.valueOf(valueOf).length() + 14).append("onStart ").append(z).append(" ").append(valueOf).toString());
        if (this.zzfut.get() == null) {
            for (int i = 0; i < this.zzfuf.size(); i++) {
                zza zzbr = zzbr(i);
                if (zzbr != null) {
                    zzbr.zzfuh.connect();
                }
            }
        }
    }

    public final void onStop() {
        super.onStop();
        for (int i = 0; i < this.zzfuf.size(); i++) {
            zza zzbr = zzbr(i);
            if (zzbr != null) {
                zzbr.zzfuh.disconnect();
            }
        }
    }

    public final void zza(int i, GoogleApiClient googleApiClient, OnConnectionFailedListener onConnectionFailedListener) {
        zzbq.checkNotNull(googleApiClient, "GoogleApiClient instance cannot be null");
        zzbq.zza(this.zzfuf.indexOfKey(i) < 0, "Already managing a GoogleApiClient with id " + i);
        zzp zzp = (zzp) this.zzfut.get();
        boolean z = this.mStarted;
        String valueOf = String.valueOf(zzp);
        Log.d("AutoManageHelper", new StringBuilder(String.valueOf(valueOf).length() + 49).append("starting AutoManage for client ").append(i).append(" ").append(z).append(" ").append(valueOf).toString());
        this.zzfuf.put(i, new zza(this, i, googleApiClient, onConnectionFailedListener));
        if (this.mStarted && zzp == null) {
            String valueOf2 = String.valueOf(googleApiClient);
            Log.d("AutoManageHelper", new StringBuilder(String.valueOf(valueOf2).length() + 11).append("connecting ").append(valueOf2).toString());
            googleApiClient.connect();
        }
    }

    protected final void zza(ConnectionResult connectionResult, int i) {
        Log.w("AutoManageHelper", "Unresolved error while connecting client. Stopping auto-manage.");
        if (i < 0) {
            Log.wtf("AutoManageHelper", "AutoManageLifecycleHelper received onErrorResolutionFailed callback but no failing client ID is set", new Exception());
            return;
        }
        zza zza = (zza) this.zzfuf.get(i);
        if (zza != null) {
            zzbq(i);
            OnConnectionFailedListener onConnectionFailedListener = zza.zzfui;
            if (onConnectionFailedListener != null) {
                onConnectionFailedListener.onConnectionFailed(connectionResult);
            }
        }
    }

    protected final void zzaih() {
        for (int i = 0; i < this.zzfuf.size(); i++) {
            zza zzbr = zzbr(i);
            if (zzbr != null) {
                zzbr.zzfuh.connect();
            }
        }
    }

    public final void zzbq(int i) {
        zza zza = (zza) this.zzfuf.get(i);
        this.zzfuf.remove(i);
        if (zza != null) {
            zza.zzfuh.unregisterConnectionFailedListener(zza);
            zza.zzfuh.disconnect();
        }
    }
}
