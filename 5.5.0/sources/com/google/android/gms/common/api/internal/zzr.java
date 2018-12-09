package com.google.android.gms.common.api.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.p022f.C0464a;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.AbstractClientBuilder;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.Api.AnyClientKey;
import com.google.android.gms.common.api.Api.Client;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.signin.SignInClient;
import com.google.android.gms.signin.SignInOptions;
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
import javax.annotation.concurrent.GuardedBy;

final class zzr implements zzbp {
    private final Context mContext;
    private final Looper zzcn;
    private final zzav zzfq;
    private final zzbd zzfr;
    private final zzbd zzfs;
    private final Map<AnyClientKey<?>, zzbd> zzft;
    private final Set<SignInConnectionListener> zzfu = Collections.newSetFromMap(new WeakHashMap());
    private final Client zzfv;
    private Bundle zzfw;
    private ConnectionResult zzfx = null;
    private ConnectionResult zzfy = null;
    private boolean zzfz = false;
    private final Lock zzga;
    @GuardedBy("mLock")
    private int zzgb = 0;

    private zzr(Context context, zzav zzav, Lock lock, Looper looper, GoogleApiAvailabilityLight googleApiAvailabilityLight, Map<AnyClientKey<?>, Client> map, Map<AnyClientKey<?>, Client> map2, ClientSettings clientSettings, AbstractClientBuilder<? extends SignInClient, SignInOptions> abstractClientBuilder, Client client, ArrayList<zzp> arrayList, ArrayList<zzp> arrayList2, Map<Api<?>, Boolean> map3, Map<Api<?>, Boolean> map4) {
        this.mContext = context;
        this.zzfq = zzav;
        this.zzga = lock;
        this.zzcn = looper;
        this.zzfv = client;
        this.zzfr = new zzbd(context, this.zzfq, lock, looper, googleApiAvailabilityLight, map2, null, map4, null, arrayList2, new zzt());
        this.zzfs = new zzbd(context, this.zzfq, lock, looper, googleApiAvailabilityLight, map, clientSettings, map3, abstractClientBuilder, arrayList, new zzu());
        Map c0464a = new C0464a();
        for (AnyClientKey put : map2.keySet()) {
            c0464a.put(put, this.zzfr);
        }
        for (AnyClientKey put2 : map.keySet()) {
            c0464a.put(put2, this.zzfs);
        }
        this.zzft = Collections.unmodifiableMap(c0464a);
    }

    public static zzr zza(Context context, zzav zzav, Lock lock, Looper looper, GoogleApiAvailabilityLight googleApiAvailabilityLight, Map<AnyClientKey<?>, Client> map, ClientSettings clientSettings, Map<Api<?>, Boolean> map2, AbstractClientBuilder<? extends SignInClient, SignInOptions> abstractClientBuilder, ArrayList<zzp> arrayList) {
        Client client = null;
        Map c0464a = new C0464a();
        Map c0464a2 = new C0464a();
        for (Entry entry : map.entrySet()) {
            Client client2 = (Client) entry.getValue();
            if (client2.providesSignIn()) {
                client = client2;
            }
            if (client2.requiresSignIn()) {
                c0464a.put((AnyClientKey) entry.getKey(), client2);
            } else {
                c0464a2.put((AnyClientKey) entry.getKey(), client2);
            }
        }
        Preconditions.checkState(!c0464a.isEmpty(), "CompositeGoogleApiClient should not be used without any APIs that require sign-in.");
        Map c0464a3 = new C0464a();
        Map c0464a4 = new C0464a();
        for (Api api : map2.keySet()) {
            AnyClientKey clientKey = api.getClientKey();
            if (c0464a.containsKey(clientKey)) {
                c0464a3.put(api, (Boolean) map2.get(api));
            } else if (c0464a2.containsKey(clientKey)) {
                c0464a4.put(api, (Boolean) map2.get(api));
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
            zzp zzp = (zzp) obj;
            if (c0464a3.containsKey(zzp.mApi)) {
                arrayList2.add(zzp);
            } else if (c0464a4.containsKey(zzp.mApi)) {
                arrayList3.add(zzp);
            } else {
                throw new IllegalStateException("Each ClientCallbacks must have a corresponding API in the isOptionalMap");
            }
        }
        return new zzr(context, zzav, lock, looper, googleApiAvailabilityLight, c0464a, c0464a2, clientSettings, abstractClientBuilder, client, arrayList2, arrayList3, c0464a3, c0464a4);
    }

    @GuardedBy("mLock")
    private final void zza(int i, boolean z) {
        this.zzfq.zzb(i, z);
        this.zzfy = null;
        this.zzfx = null;
    }

    private final void zza(Bundle bundle) {
        if (this.zzfw == null) {
            this.zzfw = bundle;
        } else if (bundle != null) {
            this.zzfw.putAll(bundle);
        }
    }

    @GuardedBy("mLock")
    private final void zza(ConnectionResult connectionResult) {
        switch (this.zzgb) {
            case 1:
                break;
            case 2:
                this.zzfq.zzc(connectionResult);
                break;
            default:
                Log.wtf("CompositeGAC", "Attempted to call failure callbacks in CONNECTION_MODE_NONE. Callbacks should be disabled via GmsClientSupervisor", new Exception());
                break;
        }
        zzab();
        this.zzgb = 0;
    }

    private final boolean zza(ApiMethodImpl<? extends Result, ? extends AnyClient> apiMethodImpl) {
        AnyClientKey clientKey = apiMethodImpl.getClientKey();
        Preconditions.checkArgument(this.zzft.containsKey(clientKey), "GoogleApiClient is not configured to use the API required for this call.");
        return ((zzbd) this.zzft.get(clientKey)).equals(this.zzfs);
    }

    @GuardedBy("mLock")
    private final void zzaa() {
        if (zzb(this.zzfx)) {
            if (zzb(this.zzfy) || zzac()) {
                switch (this.zzgb) {
                    case 1:
                        break;
                    case 2:
                        this.zzfq.zzb(this.zzfw);
                        break;
                    default:
                        Log.wtf("CompositeGAC", "Attempted to call success callbacks in CONNECTION_MODE_NONE. Callbacks should be disabled via GmsClientSupervisor", new AssertionError());
                        break;
                }
                zzab();
                this.zzgb = 0;
            } else if (this.zzfy == null) {
            } else {
                if (this.zzgb == 1) {
                    zzab();
                    return;
                }
                zza(this.zzfy);
                this.zzfr.disconnect();
            }
        } else if (this.zzfx != null && zzb(this.zzfy)) {
            this.zzfs.disconnect();
            zza(this.zzfx);
        } else if (this.zzfx != null && this.zzfy != null) {
            ConnectionResult connectionResult = this.zzfx;
            if (this.zzfs.zzje < this.zzfr.zzje) {
                connectionResult = this.zzfy;
            }
            zza(connectionResult);
        }
    }

    @GuardedBy("mLock")
    private final void zzab() {
        for (SignInConnectionListener onComplete : this.zzfu) {
            onComplete.onComplete();
        }
        this.zzfu.clear();
    }

    @GuardedBy("mLock")
    private final boolean zzac() {
        return this.zzfy != null && this.zzfy.getErrorCode() == 4;
    }

    private final PendingIntent zzad() {
        return this.zzfv == null ? null : PendingIntent.getActivity(this.mContext, System.identityHashCode(this.zzfq), this.zzfv.getSignInIntent(), 134217728);
    }

    private static boolean zzb(ConnectionResult connectionResult) {
        return connectionResult != null && connectionResult.isSuccess();
    }

    @GuardedBy("mLock")
    public final ConnectionResult blockingConnect() {
        throw new UnsupportedOperationException();
    }

    @GuardedBy("mLock")
    public final ConnectionResult blockingConnect(long j, TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }

    @GuardedBy("mLock")
    public final void connect() {
        this.zzgb = 2;
        this.zzfz = false;
        this.zzfy = null;
        this.zzfx = null;
        this.zzfr.connect();
        this.zzfs.connect();
    }

    @GuardedBy("mLock")
    public final void disconnect() {
        this.zzfy = null;
        this.zzfx = null;
        this.zzgb = 0;
        this.zzfr.disconnect();
        this.zzfs.disconnect();
        zzab();
    }

    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.append(str).append("authClient").println(":");
        this.zzfs.dump(String.valueOf(str).concat("  "), fileDescriptor, printWriter, strArr);
        printWriter.append(str).append("anonClient").println(":");
        this.zzfr.dump(String.valueOf(str).concat("  "), fileDescriptor, printWriter, strArr);
    }

    @GuardedBy("mLock")
    public final <A extends AnyClient, R extends Result, T extends ApiMethodImpl<R, A>> T enqueue(T t) {
        if (!zza((ApiMethodImpl) t)) {
            return this.zzfr.enqueue(t);
        }
        if (!zzac()) {
            return this.zzfs.enqueue(t);
        }
        t.setFailedResult(new Status(4, null, zzad()));
        return t;
    }

    @GuardedBy("mLock")
    public final <A extends AnyClient, T extends ApiMethodImpl<? extends Result, A>> T execute(T t) {
        if (!zza((ApiMethodImpl) t)) {
            return this.zzfr.execute(t);
        }
        if (!zzac()) {
            return this.zzfs.execute(t);
        }
        t.setFailedResult(new Status(4, null, zzad()));
        return t;
    }

    @GuardedBy("mLock")
    public final ConnectionResult getConnectionResult(Api<?> api) {
        return ((zzbd) this.zzft.get(api.getClientKey())).equals(this.zzfs) ? zzac() ? new ConnectionResult(4, zzad()) : this.zzfs.getConnectionResult(api) : this.zzfr.getConnectionResult(api);
    }

    public final boolean isConnected() {
        boolean z = true;
        this.zzga.lock();
        try {
            if (!(this.zzfr.isConnected() && (this.zzfs.isConnected() || zzac() || this.zzgb == 1))) {
                z = false;
            }
            this.zzga.unlock();
            return z;
        } catch (Throwable th) {
            this.zzga.unlock();
        }
    }

    public final boolean isConnecting() {
        this.zzga.lock();
        try {
            boolean z = this.zzgb == 2;
            this.zzga.unlock();
            return z;
        } catch (Throwable th) {
            this.zzga.unlock();
        }
    }

    public final boolean maybeSignIn(SignInConnectionListener signInConnectionListener) {
        this.zzga.lock();
        try {
            if ((isConnecting() || isConnected()) && !this.zzfs.isConnected()) {
                this.zzfu.add(signInConnectionListener);
                if (this.zzgb == 0) {
                    this.zzgb = 1;
                }
                this.zzfy = null;
                this.zzfs.connect();
                return true;
            }
            this.zzga.unlock();
            return false;
        } finally {
            this.zzga.unlock();
        }
    }

    public final void maybeSignOut() {
        this.zzga.lock();
        try {
            boolean isConnecting = isConnecting();
            this.zzfs.disconnect();
            this.zzfy = new ConnectionResult(4);
            if (isConnecting) {
                new Handler(this.zzcn).post(new zzs(this));
            } else {
                zzab();
            }
            this.zzga.unlock();
        } catch (Throwable th) {
            this.zzga.unlock();
        }
    }

    @GuardedBy("mLock")
    public final void zzz() {
        this.zzfr.zzz();
        this.zzfs.zzz();
    }
}
