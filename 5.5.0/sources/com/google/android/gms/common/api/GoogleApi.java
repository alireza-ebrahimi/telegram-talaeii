package com.google.android.gms.common.api;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.Api.ApiOptions.HasAccountOptions;
import com.google.android.gms.common.api.Api.ApiOptions.HasGoogleSignInAccountOptions;
import com.google.android.gms.common.api.Api.Client;
import com.google.android.gms.common.api.internal.ApiExceptionMapper;
import com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl;
import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.android.gms.common.api.internal.GoogleApiManager.zza;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.ListenerHolder.ListenerKey;
import com.google.android.gms.common.api.internal.ListenerHolders;
import com.google.android.gms.common.api.internal.RegisterListenerMethod;
import com.google.android.gms.common.api.internal.StatusExceptionMapper;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.api.internal.UnregisterListenerMethod;
import com.google.android.gms.common.api.internal.zzad;
import com.google.android.gms.common.api.internal.zzbo;
import com.google.android.gms.common.api.internal.zzby;
import com.google.android.gms.common.api.internal.zzh;
import com.google.android.gms.common.internal.ClientSettings.Builder;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.Collection;
import java.util.Collections;

@KeepForSdk
public class GoogleApi<O extends ApiOptions> {
    private final Api<O> mApi;
    private final Context mContext;
    private final int mId;
    private final O zzcl;
    private final zzh<O> zzcm;
    private final Looper zzcn;
    private final GoogleApiClient zzco;
    private final StatusExceptionMapper zzcp;
    protected final GoogleApiManager zzcq;

    @KeepForSdk
    public static class Settings {
        @KeepForSdk
        public static final Settings DEFAULT_SETTINGS = new Builder().build();
        public final StatusExceptionMapper zzcr;
        public final Looper zzcs;

        @KeepForSdk
        public static class Builder {
            private Looper zzcn;
            private StatusExceptionMapper zzcp;

            @KeepForSdk
            public Settings build() {
                if (this.zzcp == null) {
                    this.zzcp = new ApiExceptionMapper();
                }
                if (this.zzcn == null) {
                    this.zzcn = Looper.getMainLooper();
                }
                return new Settings(this.zzcp, this.zzcn);
            }

            @KeepForSdk
            public Builder setLooper(Looper looper) {
                Preconditions.checkNotNull(looper, "Looper must not be null.");
                this.zzcn = looper;
                return this;
            }

            @KeepForSdk
            public Builder setMapper(StatusExceptionMapper statusExceptionMapper) {
                Preconditions.checkNotNull(statusExceptionMapper, "StatusExceptionMapper must not be null.");
                this.zzcp = statusExceptionMapper;
                return this;
            }
        }

        @KeepForSdk
        private Settings(StatusExceptionMapper statusExceptionMapper, Account account, Looper looper) {
            this.zzcr = statusExceptionMapper;
            this.zzcs = looper;
        }
    }

    @KeepForSdk
    public GoogleApi(Activity activity, Api<O> api, O o, Settings settings) {
        Preconditions.checkNotNull(activity, "Null activity is not permitted.");
        Preconditions.checkNotNull(api, "Api must not be null.");
        Preconditions.checkNotNull(settings, "Settings must not be null; use Settings.DEFAULT_SETTINGS instead.");
        this.mContext = activity.getApplicationContext();
        this.mApi = api;
        this.zzcl = o;
        this.zzcn = settings.zzcs;
        this.zzcm = zzh.zza(this.mApi, this.zzcl);
        this.zzco = new zzbo(this);
        this.zzcq = GoogleApiManager.zzb(this.mContext);
        this.mId = this.zzcq.zzbg();
        this.zzcp = settings.zzcr;
        zzad.zza(activity, this.zzcq, this.zzcm);
        this.zzcq.zza(this);
    }

    @KeepForSdk
    @Deprecated
    public GoogleApi(Activity activity, Api<O> api, O o, StatusExceptionMapper statusExceptionMapper) {
        this(activity, (Api) api, (ApiOptions) o, new Builder().setMapper(statusExceptionMapper).setLooper(activity.getMainLooper()).build());
    }

    @KeepForSdk
    protected GoogleApi(Context context, Api<O> api, Looper looper) {
        Preconditions.checkNotNull(context, "Null context is not permitted.");
        Preconditions.checkNotNull(api, "Api must not be null.");
        Preconditions.checkNotNull(looper, "Looper must not be null.");
        this.mContext = context.getApplicationContext();
        this.mApi = api;
        this.zzcl = null;
        this.zzcn = looper;
        this.zzcm = zzh.zza(api);
        this.zzco = new zzbo(this);
        this.zzcq = GoogleApiManager.zzb(this.mContext);
        this.mId = this.zzcq.zzbg();
        this.zzcp = new ApiExceptionMapper();
    }

    @KeepForSdk
    @Deprecated
    public GoogleApi(Context context, Api<O> api, O o, Looper looper, StatusExceptionMapper statusExceptionMapper) {
        this(context, (Api) api, (ApiOptions) o, new Builder().setLooper(looper).setMapper(statusExceptionMapper).build());
    }

    @KeepForSdk
    public GoogleApi(Context context, Api<O> api, O o, Settings settings) {
        Preconditions.checkNotNull(context, "Null context is not permitted.");
        Preconditions.checkNotNull(api, "Api must not be null.");
        Preconditions.checkNotNull(settings, "Settings must not be null; use Settings.DEFAULT_SETTINGS instead.");
        this.mContext = context.getApplicationContext();
        this.mApi = api;
        this.zzcl = o;
        this.zzcn = settings.zzcs;
        this.zzcm = zzh.zza(this.mApi, this.zzcl);
        this.zzco = new zzbo(this);
        this.zzcq = GoogleApiManager.zzb(this.mContext);
        this.mId = this.zzcq.zzbg();
        this.zzcp = settings.zzcr;
        this.zzcq.zza(this);
    }

    @KeepForSdk
    @Deprecated
    public GoogleApi(Context context, Api<O> api, O o, StatusExceptionMapper statusExceptionMapper) {
        this(context, (Api) api, (ApiOptions) o, new Builder().setMapper(statusExceptionMapper).build());
    }

    private final <A extends AnyClient, T extends ApiMethodImpl<? extends Result, A>> T zza(int i, T t) {
        t.zzx();
        this.zzcq.zza(this, i, (ApiMethodImpl) t);
        return t;
    }

    private final <TResult, A extends AnyClient> Task<TResult> zza(int i, TaskApiCall<A, TResult> taskApiCall) {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        this.zzcq.zza(this, i, taskApiCall, taskCompletionSource, this.zzcp);
        return taskCompletionSource.getTask();
    }

    @KeepForSdk
    public GoogleApiClient asGoogleApiClient() {
        return this.zzco;
    }

    @KeepForSdk
    protected Builder createClientSettingsBuilder() {
        GoogleSignInAccount googleSignInAccount;
        Account account;
        Collection requestedScopes;
        Builder builder = new Builder();
        if (this.zzcl instanceof HasGoogleSignInAccountOptions) {
            googleSignInAccount = ((HasGoogleSignInAccountOptions) this.zzcl).getGoogleSignInAccount();
            if (googleSignInAccount != null) {
                account = googleSignInAccount.getAccount();
                builder = builder.setAccount(account);
                if (this.zzcl instanceof HasGoogleSignInAccountOptions) {
                    googleSignInAccount = ((HasGoogleSignInAccountOptions) this.zzcl).getGoogleSignInAccount();
                    if (googleSignInAccount != null) {
                        requestedScopes = googleSignInAccount.getRequestedScopes();
                        return builder.addAllRequiredScopes(requestedScopes).setRealClientClassName(this.mContext.getClass().getName()).setRealClientPackageName(this.mContext.getPackageName());
                    }
                }
                requestedScopes = Collections.emptySet();
                return builder.addAllRequiredScopes(requestedScopes).setRealClientClassName(this.mContext.getClass().getName()).setRealClientPackageName(this.mContext.getPackageName());
            }
        }
        account = this.zzcl instanceof HasAccountOptions ? ((HasAccountOptions) this.zzcl).getAccount() : null;
        builder = builder.setAccount(account);
        if (this.zzcl instanceof HasGoogleSignInAccountOptions) {
            googleSignInAccount = ((HasGoogleSignInAccountOptions) this.zzcl).getGoogleSignInAccount();
            if (googleSignInAccount != null) {
                requestedScopes = googleSignInAccount.getRequestedScopes();
                return builder.addAllRequiredScopes(requestedScopes).setRealClientClassName(this.mContext.getClass().getName()).setRealClientPackageName(this.mContext.getPackageName());
            }
        }
        requestedScopes = Collections.emptySet();
        return builder.addAllRequiredScopes(requestedScopes).setRealClientClassName(this.mContext.getClass().getName()).setRealClientPackageName(this.mContext.getPackageName());
    }

    @KeepForSdk
    protected Task<Boolean> disconnectService() {
        return this.zzcq.zzc(this);
    }

    @KeepForSdk
    public <A extends AnyClient, T extends ApiMethodImpl<? extends Result, A>> T doBestEffortWrite(T t) {
        return zza(2, (ApiMethodImpl) t);
    }

    @KeepForSdk
    public <TResult, A extends AnyClient> Task<TResult> doBestEffortWrite(TaskApiCall<A, TResult> taskApiCall) {
        return zza(2, (TaskApiCall) taskApiCall);
    }

    @KeepForSdk
    public <A extends AnyClient, T extends ApiMethodImpl<? extends Result, A>> T doRead(T t) {
        return zza(0, (ApiMethodImpl) t);
    }

    @KeepForSdk
    public <TResult, A extends AnyClient> Task<TResult> doRead(TaskApiCall<A, TResult> taskApiCall) {
        return zza(0, (TaskApiCall) taskApiCall);
    }

    @KeepForSdk
    public <A extends AnyClient, T extends RegisterListenerMethod<A, ?>, U extends UnregisterListenerMethod<A, ?>> Task<Void> doRegisterEventListener(T t, U u) {
        Preconditions.checkNotNull(t);
        Preconditions.checkNotNull(u);
        Preconditions.checkNotNull(t.getListenerKey(), "Listener has already been released.");
        Preconditions.checkNotNull(u.getListenerKey(), "Listener has already been released.");
        Preconditions.checkArgument(t.getListenerKey().equals(u.getListenerKey()), "Listener registration and unregistration methods must be constructed with the same ListenerHolder.");
        return this.zzcq.zza(this, (RegisterListenerMethod) t, (UnregisterListenerMethod) u);
    }

    @KeepForSdk
    public Task<Boolean> doUnregisterEventListener(ListenerKey<?> listenerKey) {
        Preconditions.checkNotNull(listenerKey, "Listener key cannot be null.");
        return this.zzcq.zza(this, (ListenerKey) listenerKey);
    }

    @KeepForSdk
    public <A extends AnyClient, T extends ApiMethodImpl<? extends Result, A>> T doWrite(T t) {
        return zza(1, (ApiMethodImpl) t);
    }

    @KeepForSdk
    public <TResult, A extends AnyClient> Task<TResult> doWrite(TaskApiCall<A, TResult> taskApiCall) {
        return zza(1, (TaskApiCall) taskApiCall);
    }

    public final Api<O> getApi() {
        return this.mApi;
    }

    @KeepForSdk
    public O getApiOptions() {
        return this.zzcl;
    }

    @KeepForSdk
    public Context getApplicationContext() {
        return this.mContext;
    }

    public final int getInstanceId() {
        return this.mId;
    }

    @KeepForSdk
    public Looper getLooper() {
        return this.zzcn;
    }

    @KeepForSdk
    public <L> ListenerHolder<L> registerListener(L l, String str) {
        return ListenerHolders.createListenerHolder(l, this.zzcn, str);
    }

    public Client zza(Looper looper, zza<O> zza) {
        return this.mApi.zzk().buildClient(this.mContext, looper, createClientSettingsBuilder().build(), this.zzcl, zza, zza);
    }

    public zzby zza(Context context, Handler handler) {
        return new zzby(context, handler, createClientSettingsBuilder().build());
    }

    public final zzh<O> zzm() {
        return this.zzcm;
    }
}
