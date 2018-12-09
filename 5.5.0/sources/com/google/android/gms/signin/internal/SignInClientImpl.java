package com.google.android.gms.signin.internal;

import android.accounts.Account;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.GmsClient;
import com.google.android.gms.common.internal.IAccountAccessor;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ResolveAccountRequest;
import com.google.android.gms.signin.SignInClient;
import com.google.android.gms.signin.SignInOptions;
import com.google.android.gms.signin.internal.ISignInService.Stub;

public class SignInClientImpl extends GmsClient<ISignInService> implements SignInClient {
    public static final String ACTION_START_SERVICE = "com.google.android.gms.signin.service.START";
    public static final String INTERNAL_ACTION_START_SERVICE = "com.google.android.gms.signin.service.INTERNAL_START";
    public static final String KEY_AUTH_API_SIGN_IN_MODULE_VERSION = "com.google.android.gms.signin.internal.authApiSignInModuleVersion";
    public static final String KEY_CLIENT_REQUESTED_ACCOUNT = "com.google.android.gms.signin.internal.clientRequestedAccount";
    public static final String KEY_FORCE_CODE_FOR_REFRESH_TOKEN = "com.google.android.gms.signin.internal.forceCodeForRefreshToken";
    public static final String KEY_HOSTED_DOMAIN = "com.google.android.gms.signin.internal.hostedDomain";
    public static final String KEY_ID_TOKEN_REQUESTED = "com.google.android.gms.signin.internal.idTokenRequested";
    @Deprecated
    public static final String KEY_OFFLINE_ACCESS_CALLBACKS = "com.google.android.gms.signin.internal.signInCallbacks";
    public static final String KEY_OFFLINE_ACCESS_REQUESTED = "com.google.android.gms.signin.internal.offlineAccessRequested";
    public static final String KEY_REAL_CLIENT_LIBRARY_VERSION = "com.google.android.gms.signin.internal.realClientLibraryVersion";
    public static final String KEY_REAL_CLIENT_PACKAGE_NAME = "com.google.android.gms.signin.internal.realClientPackageName";
    public static final String KEY_SERVER_CLIENT_ID = "com.google.android.gms.signin.internal.serverClientId";
    public static final String KEY_USE_PROMPT_MODE_FOR_AUTH_CODE = "com.google.android.gms.signin.internal.usePromptModeForAuthCode";
    public static final String KEY_WAIT_FOR_ACCESS_TOKEN_REFRESH = "com.google.android.gms.signin.internal.waitForAccessTokenRefresh";
    private final Bundle zzada;
    private final boolean zzads;
    private final ClientSettings zzgf;
    private Integer zzsc;

    public SignInClientImpl(Context context, Looper looper, boolean z, ClientSettings clientSettings, Bundle bundle, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 44, clientSettings, connectionCallbacks, onConnectionFailedListener);
        this.zzads = z;
        this.zzgf = clientSettings;
        this.zzada = bundle;
        this.zzsc = clientSettings.getClientSessionId();
    }

    public SignInClientImpl(Context context, Looper looper, boolean z, ClientSettings clientSettings, SignInOptions signInOptions, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        this(context, looper, z, clientSettings, createBundleFromClientSettings(clientSettings), connectionCallbacks, onConnectionFailedListener);
    }

    public static Bundle createBundleFromClientSettings(ClientSettings clientSettings) {
        SignInOptions signInOptions = clientSettings.getSignInOptions();
        Integer clientSessionId = clientSettings.getClientSessionId();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_CLIENT_REQUESTED_ACCOUNT, clientSettings.getAccount());
        if (clientSessionId != null) {
            bundle.putInt(ClientSettings.KEY_CLIENT_SESSION_ID, clientSessionId.intValue());
        }
        if (signInOptions != null) {
            bundle.putBoolean(KEY_OFFLINE_ACCESS_REQUESTED, signInOptions.isOfflineAccessRequested());
            bundle.putBoolean(KEY_ID_TOKEN_REQUESTED, signInOptions.isIdTokenRequested());
            bundle.putString(KEY_SERVER_CLIENT_ID, signInOptions.getServerClientId());
            bundle.putBoolean(KEY_USE_PROMPT_MODE_FOR_AUTH_CODE, true);
            bundle.putBoolean(KEY_FORCE_CODE_FOR_REFRESH_TOKEN, signInOptions.isForceCodeForRefreshToken());
            bundle.putString(KEY_HOSTED_DOMAIN, signInOptions.getHostedDomain());
            bundle.putBoolean(KEY_WAIT_FOR_ACCESS_TOKEN_REFRESH, signInOptions.waitForAccessTokenRefresh());
            if (signInOptions.getAuthApiSignInModuleVersion() != null) {
                bundle.putLong(KEY_AUTH_API_SIGN_IN_MODULE_VERSION, signInOptions.getAuthApiSignInModuleVersion().longValue());
            }
            if (signInOptions.getRealClientLibraryVersion() != null) {
                bundle.putLong(KEY_REAL_CLIENT_LIBRARY_VERSION, signInOptions.getRealClientLibraryVersion().longValue());
            }
        }
        return bundle;
    }

    public void clearAccountFromSessionStore() {
        try {
            ((ISignInService) getService()).clearAccountFromSessionStore(this.zzsc.intValue());
        } catch (RemoteException e) {
            Log.w("SignInClientImpl", "Remote service probably died when clearAccountFromSessionStore is called");
        }
    }

    public void connect() {
        connect(new LegacyClientCallbackAdapter(this));
    }

    protected ISignInService createServiceInterface(IBinder iBinder) {
        return Stub.asInterface(iBinder);
    }

    protected Bundle getGetServiceRequestExtraArgs() {
        if (!getContext().getPackageName().equals(this.zzgf.getRealClientPackageName())) {
            this.zzada.putString(KEY_REAL_CLIENT_PACKAGE_NAME, this.zzgf.getRealClientPackageName());
        }
        return this.zzada;
    }

    public int getMinApkVersion() {
        return 12451000;
    }

    protected String getServiceDescriptor() {
        return "com.google.android.gms.signin.internal.ISignInService";
    }

    protected String getStartServiceAction() {
        return ACTION_START_SERVICE;
    }

    public boolean requiresSignIn() {
        return this.zzads;
    }

    public void saveDefaultAccount(IAccountAccessor iAccountAccessor, boolean z) {
        try {
            ((ISignInService) getService()).saveDefaultAccountToSharedPref(iAccountAccessor, this.zzsc.intValue(), z);
        } catch (RemoteException e) {
            Log.w("SignInClientImpl", "Remote service probably died when saveDefaultAccount is called");
        }
    }

    public void signIn(ISignInCallbacks iSignInCallbacks) {
        Preconditions.checkNotNull(iSignInCallbacks, "Expecting a valid ISignInCallbacks");
        try {
            Account accountOrDefault = this.zzgf.getAccountOrDefault();
            GoogleSignInAccount googleSignInAccount = null;
            if ("<<default account>>".equals(accountOrDefault.name)) {
                googleSignInAccount = Storage.getInstance(getContext()).getSavedDefaultGoogleSignInAccount();
            }
            ((ISignInService) getService()).signIn(new SignInRequest(new ResolveAccountRequest(accountOrDefault, this.zzsc.intValue(), googleSignInAccount)), iSignInCallbacks);
        } catch (Throwable e) {
            Log.w("SignInClientImpl", "Remote service probably died when signIn is called");
            try {
                iSignInCallbacks.onSignInComplete(new SignInResponse(8));
            } catch (RemoteException e2) {
                Log.wtf("SignInClientImpl", "ISignInCallbacks#onSignInComplete should be executed from the same process, unexpected RemoteException.", e);
            }
        }
    }
}
