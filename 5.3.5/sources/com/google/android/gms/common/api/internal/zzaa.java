package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.common.internal.zzt;
import com.google.android.gms.common.zzf;
import com.google.android.gms.internal.zzbic;
import com.google.android.gms.internal.zzcyj;
import com.google.android.gms.internal.zzcyk;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public final class zzaa implements zzcc {
    private final Looper zzalj;
    private final zzbm zzfsq;
    private final Lock zzfwa;
    private final zzr zzfwf;
    private final Map<zzc<?>, zzz<?>> zzfwg = new HashMap();
    private final Map<zzc<?>, zzz<?>> zzfwh = new HashMap();
    private final Map<Api<?>, Boolean> zzfwi;
    private final zzba zzfwj;
    private final zzf zzfwk;
    private final Condition zzfwl;
    private final boolean zzfwm;
    private final boolean zzfwn;
    private final Queue<zzm<?, ?>> zzfwo = new LinkedList();
    private boolean zzfwp;
    private Map<zzh<?>, ConnectionResult> zzfwq;
    private Map<zzh<?>, ConnectionResult> zzfwr;
    private zzad zzfws;
    private ConnectionResult zzfwt;

    public zzaa(Context context, Lock lock, Looper looper, zzf zzf, Map<zzc<?>, zze> map, zzr zzr, Map<Api<?>, Boolean> map2, zza<? extends zzcyj, zzcyk> zza, ArrayList<zzt> arrayList, zzba zzba, boolean z) {
        this.zzfwa = lock;
        this.zzalj = looper;
        this.zzfwl = lock.newCondition();
        this.zzfwk = zzf;
        this.zzfwj = zzba;
        this.zzfwi = map2;
        this.zzfwf = zzr;
        this.zzfwm = z;
        Map hashMap = new HashMap();
        for (Api api : map2.keySet()) {
            hashMap.put(api.zzahm(), api);
        }
        Map hashMap2 = new HashMap();
        ArrayList arrayList2 = arrayList;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            zzt zzt = (zzt) obj;
            hashMap2.put(zzt.zzfop, zzt);
        }
        Object obj2 = 1;
        Object obj3 = null;
        Object obj4 = null;
        for (Entry entry : map.entrySet()) {
            Object obj5;
            Object obj6;
            Object obj7;
            Api api2 = (Api) hashMap.get(entry.getKey());
            zze zze = (zze) entry.getValue();
            if (zze.zzahn()) {
                obj5 = 1;
                if (((Boolean) this.zzfwi.get(api2)).booleanValue()) {
                    obj6 = obj2;
                    obj7 = obj3;
                } else {
                    obj6 = obj2;
                    obj7 = 1;
                }
            } else {
                obj5 = obj4;
                obj6 = null;
                obj7 = obj3;
            }
            zzz zzz = new zzz(context, api2, looper, zze, (zzt) hashMap2.get(api2), zzr, zza);
            this.zzfwg.put((zzc) entry.getKey(), zzz);
            if (zze.zzacc()) {
                this.zzfwh.put((zzc) entry.getKey(), zzz);
            }
            obj4 = obj5;
            obj2 = obj6;
            obj3 = obj7;
        }
        boolean z2 = obj4 != null && obj2 == null && obj3 == null;
        this.zzfwn = z2;
        this.zzfsq = zzbm.zzajy();
    }

    private final boolean zza(zzz<?> zzz, ConnectionResult connectionResult) {
        return !connectionResult.isSuccess() && !connectionResult.hasResolution() && ((Boolean) this.zzfwi.get(zzz.zzaht())).booleanValue() && zzz.zzaix().zzahn() && this.zzfwk.isUserResolvableError(connectionResult.getErrorCode());
    }

    private final boolean zzaiy() {
        this.zzfwa.lock();
        try {
            if (this.zzfwp && this.zzfwm) {
                for (zzc zzb : this.zzfwh.keySet()) {
                    ConnectionResult zzb2 = zzb(zzb);
                    if (zzb2 != null) {
                        if (!zzb2.isSuccess()) {
                        }
                    }
                    this.zzfwa.unlock();
                    return false;
                }
                this.zzfwa.unlock();
                return true;
            }
            this.zzfwa.unlock();
            return false;
        } catch (Throwable th) {
            this.zzfwa.unlock();
        }
    }

    private final void zzaiz() {
        if (this.zzfwf == null) {
            this.zzfwj.zzfyk = Collections.emptySet();
            return;
        }
        Set hashSet = new HashSet(this.zzfwf.zzamf());
        Map zzamh = this.zzfwf.zzamh();
        for (Api api : zzamh.keySet()) {
            ConnectionResult connectionResult = getConnectionResult(api);
            if (connectionResult != null && connectionResult.isSuccess()) {
                hashSet.addAll(((zzt) zzamh.get(api)).zzenh);
            }
        }
        this.zzfwj.zzfyk = hashSet;
    }

    private final void zzaja() {
        while (!this.zzfwo.isEmpty()) {
            zze((zzm) this.zzfwo.remove());
        }
        this.zzfwj.zzk(null);
    }

    @Nullable
    private final ConnectionResult zzajb() {
        int i = 0;
        ConnectionResult connectionResult = null;
        int i2 = 0;
        ConnectionResult connectionResult2 = null;
        for (zzz zzz : this.zzfwg.values()) {
            Api zzaht = zzz.zzaht();
            ConnectionResult connectionResult3 = (ConnectionResult) this.zzfwq.get(zzz.zzahv());
            if (!connectionResult3.isSuccess() && (!((Boolean) this.zzfwi.get(zzaht)).booleanValue() || connectionResult3.hasResolution() || this.zzfwk.isUserResolvableError(connectionResult3.getErrorCode()))) {
                int priority;
                if (connectionResult3.getErrorCode() == 4 && this.zzfwm) {
                    priority = zzaht.zzahk().getPriority();
                    if (connectionResult == null || i > priority) {
                        i = priority;
                        connectionResult = connectionResult3;
                    }
                } else {
                    ConnectionResult connectionResult4;
                    int i3;
                    priority = zzaht.zzahk().getPriority();
                    if (connectionResult2 == null || i2 > priority) {
                        int i4 = priority;
                        connectionResult4 = connectionResult3;
                        i3 = i4;
                    } else {
                        i3 = i2;
                        connectionResult4 = connectionResult2;
                    }
                    i2 = i3;
                    connectionResult2 = connectionResult4;
                }
            }
        }
        return (connectionResult2 == null || connectionResult == null || i2 <= i) ? connectionResult2 : connectionResult;
    }

    @Nullable
    private final ConnectionResult zzb(@NonNull zzc<?> zzc) {
        this.zzfwa.lock();
        try {
            zzz zzz = (zzz) this.zzfwg.get(zzc);
            if (this.zzfwq == null || zzz == null) {
                this.zzfwa.unlock();
                return null;
            }
            ConnectionResult connectionResult = (ConnectionResult) this.zzfwq.get(zzz.zzahv());
            return connectionResult;
        } finally {
            this.zzfwa.unlock();
        }
    }

    private final <T extends zzm<? extends Result, ? extends zzb>> boolean zzg(@NonNull T t) {
        zzc zzahm = t.zzahm();
        ConnectionResult zzb = zzb(zzahm);
        if (zzb == null || zzb.getErrorCode() != 4) {
            return false;
        }
        t.zzu(new Status(4, null, this.zzfsq.zza(((zzz) this.zzfwg.get(zzahm)).zzahv(), System.identityHashCode(this.zzfwj))));
        return true;
    }

    public final ConnectionResult blockingConnect() {
        connect();
        while (isConnecting()) {
            try {
                this.zzfwl.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return new ConnectionResult(15, null);
            }
        }
        return isConnected() ? ConnectionResult.zzfqt : this.zzfwt != null ? this.zzfwt : new ConnectionResult(13, null);
    }

    public final ConnectionResult blockingConnect(long j, TimeUnit timeUnit) {
        connect();
        long toNanos = timeUnit.toNanos(j);
        while (isConnecting()) {
            if (toNanos <= 0) {
                try {
                    disconnect();
                    return new ConnectionResult(14, null);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return new ConnectionResult(15, null);
                }
            }
            toNanos = this.zzfwl.awaitNanos(toNanos);
        }
        return isConnected() ? ConnectionResult.zzfqt : this.zzfwt != null ? this.zzfwt : new ConnectionResult(13, null);
    }

    public final void connect() {
        this.zzfwa.lock();
        try {
            if (!this.zzfwp) {
                this.zzfwp = true;
                this.zzfwq = null;
                this.zzfwr = null;
                this.zzfws = null;
                this.zzfwt = null;
                this.zzfsq.zzaih();
                this.zzfsq.zza(this.zzfwg.values()).addOnCompleteListener(new zzbic(this.zzalj), new zzac());
                this.zzfwa.unlock();
            }
        } finally {
            this.zzfwa.unlock();
        }
    }

    public final void disconnect() {
        this.zzfwa.lock();
        try {
            this.zzfwp = false;
            this.zzfwq = null;
            this.zzfwr = null;
            if (this.zzfws != null) {
                this.zzfws.cancel();
                this.zzfws = null;
            }
            this.zzfwt = null;
            while (!this.zzfwo.isEmpty()) {
                zzm zzm = (zzm) this.zzfwo.remove();
                zzm.zza(null);
                zzm.cancel();
            }
            this.zzfwl.signalAll();
        } finally {
            this.zzfwa.unlock();
        }
    }

    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    }

    @Nullable
    public final ConnectionResult getConnectionResult(@NonNull Api<?> api) {
        return zzb(api.zzahm());
    }

    public final boolean isConnected() {
        this.zzfwa.lock();
        try {
            boolean z = this.zzfwq != null && this.zzfwt == null;
            this.zzfwa.unlock();
            return z;
        } catch (Throwable th) {
            this.zzfwa.unlock();
        }
    }

    public final boolean isConnecting() {
        this.zzfwa.lock();
        try {
            boolean z = this.zzfwq == null && this.zzfwp;
            this.zzfwa.unlock();
            return z;
        } catch (Throwable th) {
            this.zzfwa.unlock();
        }
    }

    public final boolean zza(zzcu zzcu) {
        this.zzfwa.lock();
        try {
            if (!this.zzfwp || zzaiy()) {
                this.zzfwa.unlock();
                return false;
            }
            this.zzfsq.zzaih();
            this.zzfws = new zzad(this, zzcu);
            this.zzfsq.zza(this.zzfwh.values()).addOnCompleteListener(new zzbic(this.zzalj), this.zzfws);
            return true;
        } finally {
            this.zzfwa.unlock();
        }
    }

    public final void zzaia() {
        this.zzfwa.lock();
        try {
            this.zzfsq.zzaia();
            if (this.zzfws != null) {
                this.zzfws.cancel();
                this.zzfws = null;
            }
            if (this.zzfwr == null) {
                this.zzfwr = new ArrayMap(this.zzfwh.size());
            }
            ConnectionResult connectionResult = new ConnectionResult(4);
            for (zzz zzahv : this.zzfwh.values()) {
                this.zzfwr.put(zzahv.zzahv(), connectionResult);
            }
            if (this.zzfwq != null) {
                this.zzfwq.putAll(this.zzfwr);
            }
            this.zzfwa.unlock();
        } catch (Throwable th) {
            this.zzfwa.unlock();
        }
    }

    public final void zzais() {
    }

    public final <A extends zzb, R extends Result, T extends zzm<R, A>> T zzd(@NonNull T t) {
        if (this.zzfwm && zzg((zzm) t)) {
            return t;
        }
        if (isConnected()) {
            this.zzfwj.zzfyp.zzb(t);
            return ((zzz) this.zzfwg.get(t.zzahm())).zza((zzm) t);
        }
        this.zzfwo.add(t);
        return t;
    }

    public final <A extends zzb, T extends zzm<? extends Result, A>> T zze(@NonNull T t) {
        zzc zzahm = t.zzahm();
        if (this.zzfwm && zzg((zzm) t)) {
            return t;
        }
        this.zzfwj.zzfyp.zzb(t);
        return ((zzz) this.zzfwg.get(zzahm)).zzb((zzm) t);
    }
}
