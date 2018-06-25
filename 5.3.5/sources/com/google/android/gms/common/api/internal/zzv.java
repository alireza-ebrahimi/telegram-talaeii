package com.google.android.gms.common.api.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.common.zzf;
import com.google.android.gms.internal.zzcyj;
import com.google.android.gms.internal.zzcyk;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

final class zzv implements zzcc {
    private final Context mContext;
    private final Looper zzalj;
    private final zzba zzfvq;
    private final zzbi zzfvr;
    private final zzbi zzfvs;
    private final Map<zzc<?>, zzbi> zzfvt;
    private final Set<zzcu> zzfvu = Collections.newSetFromMap(new WeakHashMap());
    private final zze zzfvv;
    private Bundle zzfvw;
    private ConnectionResult zzfvx = null;
    private ConnectionResult zzfvy = null;
    private boolean zzfvz = false;
    private final Lock zzfwa;
    private int zzfwb = 0;

    private zzv(Context context, zzba zzba, Lock lock, Looper looper, zzf zzf, Map<zzc<?>, zze> map, Map<zzc<?>, zze> map2, zzr zzr, zza<? extends zzcyj, zzcyk> zza, zze zze, ArrayList<zzt> arrayList, ArrayList<zzt> arrayList2, Map<Api<?>, Boolean> map3, Map<Api<?>, Boolean> map4) {
        this.mContext = context;
        this.zzfvq = zzba;
        this.zzfwa = lock;
        this.zzalj = looper;
        this.zzfvv = zze;
        this.zzfvr = new zzbi(context, this.zzfvq, lock, looper, zzf, map2, null, map4, null, arrayList2, new zzx());
        this.zzfvs = new zzbi(context, this.zzfvq, lock, looper, zzf, map, zzr, map3, zza, arrayList, new zzy());
        Map arrayMap = new ArrayMap();
        for (zzc put : map2.keySet()) {
            arrayMap.put(put, this.zzfvr);
        }
        for (zzc put2 : map.keySet()) {
            arrayMap.put(put2, this.zzfvs);
        }
        this.zzfvt = Collections.unmodifiableMap(arrayMap);
    }

    public static zzv zza(Context context, zzba zzba, Lock lock, Looper looper, zzf zzf, Map<zzc<?>, zze> map, zzr zzr, Map<Api<?>, Boolean> map2, zza<? extends zzcyj, zzcyk> zza, ArrayList<zzt> arrayList) {
        zze zze = null;
        Map arrayMap = new ArrayMap();
        Map arrayMap2 = new ArrayMap();
        for (Entry entry : map.entrySet()) {
            zze zze2 = (zze) entry.getValue();
            if (zze2.zzacn()) {
                zze = zze2;
            }
            if (zze2.zzacc()) {
                arrayMap.put((zzc) entry.getKey(), zze2);
            } else {
                arrayMap2.put((zzc) entry.getKey(), zze2);
            }
        }
        zzbq.zza(!arrayMap.isEmpty(), (Object) "CompositeGoogleApiClient should not be used without any APIs that require sign-in.");
        Map arrayMap3 = new ArrayMap();
        Map arrayMap4 = new ArrayMap();
        for (Api api : map2.keySet()) {
            zzc zzahm = api.zzahm();
            if (arrayMap.containsKey(zzahm)) {
                arrayMap3.put(api, (Boolean) map2.get(api));
            } else if (arrayMap2.containsKey(zzahm)) {
                arrayMap4.put(api, (Boolean) map2.get(api));
            } else {
                throw new IllegalStateException("Each API in the isOptionalMap must have a corresponding client in the clients map.");
            }
        }
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = arrayList;
        int size = arrayList4.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList4.get(i);
            i++;
            zzt zzt = (zzt) obj;
            if (arrayMap3.containsKey(zzt.zzfop)) {
                arrayList2.add(zzt);
            } else if (arrayMap4.containsKey(zzt.zzfop)) {
                arrayList3.add(zzt);
            } else {
                throw new IllegalStateException("Each ClientCallbacks must have a corresponding API in the isOptionalMap");
            }
        }
        return new zzv(context, zzba, lock, looper, zzf, arrayMap, arrayMap2, zzr, zza, zze, arrayList2, arrayList3, arrayMap3, arrayMap4);
    }

    private final void zza(ConnectionResult connectionResult) {
        switch (this.zzfwb) {
            case 1:
                break;
            case 2:
                this.zzfvq.zzc(connectionResult);
                break;
            default:
                Log.wtf("CompositeGAC", "Attempted to call failure callbacks in CONNECTION_MODE_NONE. Callbacks should be disabled via GmsClientSupervisor", new Exception());
                break;
        }
        zzaiu();
        this.zzfwb = 0;
    }

    private final void zzait() {
        if (zzb(this.zzfvx)) {
            if (zzb(this.zzfvy) || zzaiv()) {
                switch (this.zzfwb) {
                    case 1:
                        break;
                    case 2:
                        this.zzfvq.zzk(this.zzfvw);
                        break;
                    default:
                        Log.wtf("CompositeGAC", "Attempted to call success callbacks in CONNECTION_MODE_NONE. Callbacks should be disabled via GmsClientSupervisor", new AssertionError());
                        break;
                }
                zzaiu();
                this.zzfwb = 0;
            } else if (this.zzfvy == null) {
            } else {
                if (this.zzfwb == 1) {
                    zzaiu();
                    return;
                }
                zza(this.zzfvy);
                this.zzfvr.disconnect();
            }
        } else if (this.zzfvx != null && zzb(this.zzfvy)) {
            this.zzfvs.disconnect();
            zza(this.zzfvx);
        } else if (this.zzfvx != null && this.zzfvy != null) {
            ConnectionResult connectionResult = this.zzfvx;
            if (this.zzfvs.zzfzb < this.zzfvr.zzfzb) {
                connectionResult = this.zzfvy;
            }
            zza(connectionResult);
        }
    }

    private final void zzaiu() {
        for (zzcu zzacm : this.zzfvu) {
            zzacm.zzacm();
        }
        this.zzfvu.clear();
    }

    private final boolean zzaiv() {
        return this.zzfvy != null && this.zzfvy.getErrorCode() == 4;
    }

    @Nullable
    private final PendingIntent zzaiw() {
        return this.zzfvv == null ? null : PendingIntent.getActivity(this.mContext, System.identityHashCode(this.zzfvq), this.zzfvv.getSignInIntent(), 134217728);
    }

    private static boolean zzb(ConnectionResult connectionResult) {
        return connectionResult != null && connectionResult.isSuccess();
    }

    private final void zze(int i, boolean z) {
        this.zzfvq.zzf(i, z);
        this.zzfvy = null;
        this.zzfvx = null;
    }

    private final boolean zzf(zzm<? extends Result, ? extends zzb> zzm) {
        zzc zzahm = zzm.zzahm();
        zzbq.checkArgument(this.zzfvt.containsKey(zzahm), "GoogleApiClient is not configured to use the API required for this call.");
        return ((zzbi) this.zzfvt.get(zzahm)).equals(this.zzfvs);
    }

    private final void zzj(Bundle bundle) {
        if (this.zzfvw == null) {
            this.zzfvw = bundle;
        } else if (bundle != null) {
            this.zzfvw.putAll(bundle);
        }
    }

    public final ConnectionResult blockingConnect() {
        throw new UnsupportedOperationException();
    }

    public final ConnectionResult blockingConnect(long j, @NonNull TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }

    public final void connect() {
        this.zzfwb = 2;
        this.zzfvz = false;
        this.zzfvy = null;
        this.zzfvx = null;
        this.zzfvr.connect();
        this.zzfvs.connect();
    }

    public final void disconnect() {
        this.zzfvy = null;
        this.zzfvx = null;
        this.zzfwb = 0;
        this.zzfvr.disconnect();
        this.zzfvs.disconnect();
        zzaiu();
    }

    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.append(str).append("authClient").println(":");
        this.zzfvs.dump(String.valueOf(str).concat("  "), fileDescriptor, printWriter, strArr);
        printWriter.append(str).append("anonClient").println(":");
        this.zzfvr.dump(String.valueOf(str).concat("  "), fileDescriptor, printWriter, strArr);
    }

    @Nullable
    public final ConnectionResult getConnectionResult(@NonNull Api<?> api) {
        return ((zzbi) this.zzfvt.get(api.zzahm())).equals(this.zzfvs) ? zzaiv() ? new ConnectionResult(4, zzaiw()) : this.zzfvs.getConnectionResult(api) : this.zzfvr.getConnectionResult(api);
    }

    public final boolean isConnected() {
        boolean z = true;
        this.zzfwa.lock();
        try {
            if (!(this.zzfvr.isConnected() && (this.zzfvs.isConnected() || zzaiv() || this.zzfwb == 1))) {
                z = false;
            }
            this.zzfwa.unlock();
            return z;
        } catch (Throwable th) {
            this.zzfwa.unlock();
        }
    }

    public final boolean isConnecting() {
        this.zzfwa.lock();
        try {
            boolean z = this.zzfwb == 2;
            this.zzfwa.unlock();
            return z;
        } catch (Throwable th) {
            this.zzfwa.unlock();
        }
    }

    public final boolean zza(zzcu zzcu) {
        this.zzfwa.lock();
        try {
            if ((isConnecting() || isConnected()) && !this.zzfvs.isConnected()) {
                this.zzfvu.add(zzcu);
                if (this.zzfwb == 0) {
                    this.zzfwb = 1;
                }
                this.zzfvy = null;
                this.zzfvs.connect();
                return true;
            }
            this.zzfwa.unlock();
            return false;
        } finally {
            this.zzfwa.unlock();
        }
    }

    public final void zzaia() {
        this.zzfwa.lock();
        try {
            boolean isConnecting = isConnecting();
            this.zzfvs.disconnect();
            this.zzfvy = new ConnectionResult(4);
            if (isConnecting) {
                new Handler(this.zzalj).post(new zzw(this));
            } else {
                zzaiu();
            }
            this.zzfwa.unlock();
        } catch (Throwable th) {
            this.zzfwa.unlock();
        }
    }

    public final void zzais() {
        this.zzfvr.zzais();
        this.zzfvs.zzais();
    }

    public final <A extends zzb, R extends Result, T extends zzm<R, A>> T zzd(@NonNull T t) {
        if (!zzf((zzm) t)) {
            return this.zzfvr.zzd(t);
        }
        if (!zzaiv()) {
            return this.zzfvs.zzd(t);
        }
        t.zzu(new Status(4, null, zzaiw()));
        return t;
    }

    public final <A extends zzb, T extends zzm<? extends Result, A>> T zze(@NonNull T t) {
        if (!zzf((zzm) t)) {
            return this.zzfvr.zze(t);
        }
        if (!zzaiv()) {
            return this.zzfvs.zze(t);
        }
        t.zzu(new Status(4, null, zzaiw()));
        return t;
    }
}
