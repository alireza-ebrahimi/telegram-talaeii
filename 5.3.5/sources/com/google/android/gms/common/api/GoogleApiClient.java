package com.google.android.gms.common.api;

import android.accounts.Account;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.ArrayMap;
import android.view.View;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.Api.ApiOptions.HasOptions;
import com.google.android.gms.common.api.Api.ApiOptions.NotRequiredOptions;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.Api.zzd;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.internal.zzba;
import com.google.android.gms.common.api.internal.zzce;
import com.google.android.gms.common.api.internal.zzci;
import com.google.android.gms.common.api.internal.zzcu;
import com.google.android.gms.common.api.internal.zzdh;
import com.google.android.gms.common.api.internal.zzi;
import com.google.android.gms.common.api.internal.zzm;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.common.internal.zzt;
import com.google.android.gms.internal.zzcyg;
import com.google.android.gms.internal.zzcyj;
import com.google.android.gms.internal.zzcyk;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public abstract class GoogleApiClient {
    public static final int SIGN_IN_MODE_OPTIONAL = 2;
    public static final int SIGN_IN_MODE_REQUIRED = 1;
    private static final Set<GoogleApiClient> zzfsv = Collections.newSetFromMap(new WeakHashMap());

    public static final class Builder {
        private final Context mContext;
        private Looper zzalj;
        private String zzehh;
        private Account zzeho;
        private final Set<Scope> zzfsw;
        private final Set<Scope> zzfsx;
        private int zzfsy;
        private View zzfsz;
        private String zzfta;
        private final Map<Api<?>, zzt> zzftb;
        private final Map<Api<?>, ApiOptions> zzftc;
        private zzce zzftd;
        private int zzfte;
        private OnConnectionFailedListener zzftf;
        private GoogleApiAvailability zzftg;
        private zza<? extends zzcyj, zzcyk> zzfth;
        private final ArrayList<ConnectionCallbacks> zzfti;
        private final ArrayList<OnConnectionFailedListener> zzftj;
        private boolean zzftk;

        public Builder(@NonNull Context context) {
            this.zzfsw = new HashSet();
            this.zzfsx = new HashSet();
            this.zzftb = new ArrayMap();
            this.zzftc = new ArrayMap();
            this.zzfte = -1;
            this.zzftg = GoogleApiAvailability.getInstance();
            this.zzfth = zzcyg.zzegv;
            this.zzfti = new ArrayList();
            this.zzftj = new ArrayList();
            this.zzftk = false;
            this.mContext = context;
            this.zzalj = context.getMainLooper();
            this.zzehh = context.getPackageName();
            this.zzfta = context.getClass().getName();
        }

        public Builder(@NonNull Context context, @NonNull ConnectionCallbacks connectionCallbacks, @NonNull OnConnectionFailedListener onConnectionFailedListener) {
            this(context);
            zzbq.checkNotNull(connectionCallbacks, "Must provide a connected listener");
            this.zzfti.add(connectionCallbacks);
            zzbq.checkNotNull(onConnectionFailedListener, "Must provide a connection failed listener");
            this.zzftj.add(onConnectionFailedListener);
        }

        private final <O extends ApiOptions> void zza(Api<O> api, O o, Scope... scopeArr) {
            Set hashSet = new HashSet(api.zzahk().zzr(o));
            for (Object add : scopeArr) {
                hashSet.add(add);
            }
            this.zzftb.put(api, new zzt(hashSet));
        }

        public final Builder addApi(@NonNull Api<? extends NotRequiredOptions> api) {
            zzbq.checkNotNull(api, "Api must not be null");
            this.zzftc.put(api, null);
            Collection zzr = api.zzahk().zzr(null);
            this.zzfsx.addAll(zzr);
            this.zzfsw.addAll(zzr);
            return this;
        }

        public final <O extends HasOptions> Builder addApi(@NonNull Api<O> api, @NonNull O o) {
            zzbq.checkNotNull(api, "Api must not be null");
            zzbq.checkNotNull(o, "Null options are not permitted for this Api");
            this.zzftc.put(api, o);
            Collection zzr = api.zzahk().zzr(o);
            this.zzfsx.addAll(zzr);
            this.zzfsw.addAll(zzr);
            return this;
        }

        public final <O extends HasOptions> Builder addApiIfAvailable(@NonNull Api<O> api, @NonNull O o, Scope... scopeArr) {
            zzbq.checkNotNull(api, "Api must not be null");
            zzbq.checkNotNull(o, "Null options are not permitted for this Api");
            this.zzftc.put(api, o);
            zza(api, o, scopeArr);
            return this;
        }

        public final Builder addApiIfAvailable(@NonNull Api<? extends NotRequiredOptions> api, Scope... scopeArr) {
            zzbq.checkNotNull(api, "Api must not be null");
            this.zzftc.put(api, null);
            zza(api, null, scopeArr);
            return this;
        }

        public final Builder addConnectionCallbacks(@NonNull ConnectionCallbacks connectionCallbacks) {
            zzbq.checkNotNull(connectionCallbacks, "Listener must not be null");
            this.zzfti.add(connectionCallbacks);
            return this;
        }

        public final Builder addOnConnectionFailedListener(@NonNull OnConnectionFailedListener onConnectionFailedListener) {
            zzbq.checkNotNull(onConnectionFailedListener, "Listener must not be null");
            this.zzftj.add(onConnectionFailedListener);
            return this;
        }

        public final Builder addScope(@NonNull Scope scope) {
            zzbq.checkNotNull(scope, "Scope must not be null");
            this.zzfsw.add(scope);
            return this;
        }

        public final GoogleApiClient build() {
            zzbq.checkArgument(!this.zzftc.isEmpty(), "must call addApi() to add at least one API");
            zzr zzaic = zzaic();
            Api api = null;
            Map zzamh = zzaic.zzamh();
            Map arrayMap = new ArrayMap();
            Map arrayMap2 = new ArrayMap();
            ArrayList arrayList = new ArrayList();
            Object obj = null;
            for (Api api2 : this.zzftc.keySet()) {
                Api api22;
                Object obj2 = this.zzftc.get(api22);
                boolean z = zzamh.get(api22) != null;
                arrayMap.put(api22, Boolean.valueOf(z));
                ConnectionCallbacks zzt = new com.google.android.gms.common.api.internal.zzt(api22, z);
                arrayList.add(zzt);
                zzd zzahl = api22.zzahl();
                zze zza = zzahl.zza(this.mContext, this.zzalj, zzaic, obj2, zzt, zzt);
                arrayMap2.put(api22.zzahm(), zza);
                Object obj3 = zzahl.getPriority() == 1 ? obj2 != null ? 1 : null : obj;
                if (!zza.zzacn()) {
                    api22 = api;
                } else if (api != null) {
                    String name = api22.getName();
                    String name2 = api.getName();
                    throw new IllegalStateException(new StringBuilder((String.valueOf(name).length() + 21) + String.valueOf(name2).length()).append(name).append(" cannot be used with ").append(name2).toString());
                }
                obj = obj3;
                api = api22;
            }
            if (api != null) {
                if (obj != null) {
                    name = api.getName();
                    throw new IllegalStateException(new StringBuilder(String.valueOf(name).length() + 82).append("With using ").append(name).append(", GamesOptions can only be specified within GoogleSignInOptions.Builder").toString());
                }
                zzbq.zza(this.zzeho == null, "Must not set an account in GoogleApiClient.Builder when using %s. Set account in GoogleSignInOptions.Builder instead", api.getName());
                zzbq.zza(this.zzfsw.equals(this.zzfsx), "Must not set scopes in GoogleApiClient.Builder when using %s. Set account in GoogleSignInOptions.Builder instead.", api.getName());
            }
            GoogleApiClient zzba = new zzba(this.mContext, new ReentrantLock(), this.zzalj, zzaic, this.zzftg, this.zzfth, arrayMap, this.zzfti, this.zzftj, arrayMap2, this.zzfte, zzba.zza(arrayMap2.values(), true), arrayList, false);
            synchronized (GoogleApiClient.zzfsv) {
                GoogleApiClient.zzfsv.add(zzba);
            }
            if (this.zzfte >= 0) {
                zzi.zza(this.zzftd).zza(this.zzfte, zzba, this.zzftf);
            }
            return zzba;
        }

        public final Builder enableAutoManage(@NonNull FragmentActivity fragmentActivity, int i, @Nullable OnConnectionFailedListener onConnectionFailedListener) {
            zzce zzce = new zzce(fragmentActivity);
            zzbq.checkArgument(i >= 0, "clientId must be non-negative");
            this.zzfte = i;
            this.zzftf = onConnectionFailedListener;
            this.zzftd = zzce;
            return this;
        }

        public final Builder enableAutoManage(@NonNull FragmentActivity fragmentActivity, @Nullable OnConnectionFailedListener onConnectionFailedListener) {
            return enableAutoManage(fragmentActivity, 0, onConnectionFailedListener);
        }

        public final Builder setAccountName(String str) {
            this.zzeho = str == null ? null : new Account(str, "com.google");
            return this;
        }

        public final Builder setGravityForPopups(int i) {
            this.zzfsy = i;
            return this;
        }

        public final Builder setHandler(@NonNull Handler handler) {
            zzbq.checkNotNull(handler, "Handler must not be null");
            this.zzalj = handler.getLooper();
            return this;
        }

        public final Builder setViewForPopups(@NonNull View view) {
            zzbq.checkNotNull(view, "View must not be null");
            this.zzfsz = view;
            return this;
        }

        public final Builder useDefaultAccount() {
            return setAccountName("<<default account>>");
        }

        @Hide
        public final zzr zzaic() {
            zzcyk zzcyk = zzcyk.zzklp;
            if (this.zzftc.containsKey(zzcyg.API)) {
                zzcyk = (zzcyk) this.zzftc.get(zzcyg.API);
            }
            return new zzr(this.zzeho, this.zzfsw, this.zzftb, this.zzfsy, this.zzfsz, this.zzehh, this.zzfta, zzcyk);
        }
    }

    public interface ConnectionCallbacks {
        public static final int CAUSE_NETWORK_LOST = 2;
        public static final int CAUSE_SERVICE_DISCONNECTED = 1;

        void onConnected(@Nullable Bundle bundle);

        void onConnectionSuspended(int i);
    }

    public interface OnConnectionFailedListener {
        void onConnectionFailed(@NonNull ConnectionResult connectionResult);
    }

    public static void dumpAll(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        synchronized (zzfsv) {
            String concat = String.valueOf(str).concat("  ");
            int i = 0;
            for (GoogleApiClient googleApiClient : zzfsv) {
                int i2 = i + 1;
                printWriter.append(str).append("GoogleApiClient#").println(i);
                googleApiClient.dump(concat, fileDescriptor, printWriter, strArr);
                i = i2;
            }
        }
    }

    @Hide
    public static Set<GoogleApiClient> zzahz() {
        Set<GoogleApiClient> set;
        synchronized (zzfsv) {
            set = zzfsv;
        }
        return set;
    }

    public abstract ConnectionResult blockingConnect();

    public abstract ConnectionResult blockingConnect(long j, @NonNull TimeUnit timeUnit);

    public abstract PendingResult<Status> clearDefaultAccountAndReconnect();

    public abstract void connect();

    public void connect(int i) {
        throw new UnsupportedOperationException();
    }

    public abstract void disconnect();

    public abstract void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr);

    @NonNull
    public abstract ConnectionResult getConnectionResult(@NonNull Api<?> api);

    @Hide
    public Context getContext() {
        throw new UnsupportedOperationException();
    }

    @Hide
    public Looper getLooper() {
        throw new UnsupportedOperationException();
    }

    public abstract boolean hasConnectedApi(@NonNull Api<?> api);

    public abstract boolean isConnected();

    public abstract boolean isConnecting();

    public abstract boolean isConnectionCallbacksRegistered(@NonNull ConnectionCallbacks connectionCallbacks);

    public abstract boolean isConnectionFailedListenerRegistered(@NonNull OnConnectionFailedListener onConnectionFailedListener);

    public abstract void reconnect();

    public abstract void registerConnectionCallbacks(@NonNull ConnectionCallbacks connectionCallbacks);

    public abstract void registerConnectionFailedListener(@NonNull OnConnectionFailedListener onConnectionFailedListener);

    public abstract void stopAutoManage(@NonNull FragmentActivity fragmentActivity);

    public abstract void unregisterConnectionCallbacks(@NonNull ConnectionCallbacks connectionCallbacks);

    public abstract void unregisterConnectionFailedListener(@NonNull OnConnectionFailedListener onConnectionFailedListener);

    @Hide
    @NonNull
    public <C extends zze> C zza(@NonNull zzc<C> zzc) {
        throw new UnsupportedOperationException();
    }

    @Hide
    public void zza(zzdh zzdh) {
        throw new UnsupportedOperationException();
    }

    @Hide
    public boolean zza(@NonNull Api<?> api) {
        throw new UnsupportedOperationException();
    }

    @Hide
    public boolean zza(zzcu zzcu) {
        throw new UnsupportedOperationException();
    }

    @Hide
    public void zzaia() {
        throw new UnsupportedOperationException();
    }

    @Hide
    public void zzb(zzdh zzdh) {
        throw new UnsupportedOperationException();
    }

    @Hide
    public <A extends zzb, R extends Result, T extends zzm<R, A>> T zzd(@NonNull T t) {
        throw new UnsupportedOperationException();
    }

    @Hide
    public <A extends zzb, T extends zzm<? extends Result, A>> T zze(@NonNull T t) {
        throw new UnsupportedOperationException();
    }

    @Hide
    public <L> zzci<L> zzt(@NonNull L l) {
        throw new UnsupportedOperationException();
    }
}
