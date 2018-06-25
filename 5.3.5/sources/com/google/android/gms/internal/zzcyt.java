package com.google.android.gms.internal;

import android.accounts.Account;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.internal.zzaa;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzan;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.common.internal.zzm;
import com.google.android.gms.common.internal.zzr;

public final class zzcyt extends zzab<zzcyr> implements zzcyj {
    private final zzr zzfwf;
    private Integer zzgft;
    private final boolean zzklw;
    private final Bundle zzklx;

    private zzcyt(Context context, Looper looper, boolean z, zzr zzr, Bundle bundle, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 44, zzr, connectionCallbacks, onConnectionFailedListener);
        this.zzklw = true;
        this.zzfwf = zzr;
        this.zzklx = bundle;
        this.zzgft = zzr.zzamm();
    }

    public zzcyt(Context context, Looper looper, boolean z, zzr zzr, zzcyk zzcyk, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        this(context, looper, true, zzr, zza(zzr), connectionCallbacks, onConnectionFailedListener);
    }

    public static Bundle zza(zzr zzr) {
        zzcyk zzaml = zzr.zzaml();
        Integer zzamm = zzr.zzamm();
        Bundle bundle = new Bundle();
        bundle.putParcelable("com.google.android.gms.signin.internal.clientRequestedAccount", zzr.getAccount());
        if (zzamm != null) {
            bundle.putInt("com.google.android.gms.common.internal.ClientSettings.sessionId", zzamm.intValue());
        }
        if (zzaml != null) {
            bundle.putBoolean("com.google.android.gms.signin.internal.offlineAccessRequested", zzaml.zzbeu());
            bundle.putBoolean("com.google.android.gms.signin.internal.idTokenRequested", zzaml.isIdTokenRequested());
            bundle.putString("com.google.android.gms.signin.internal.serverClientId", zzaml.getServerClientId());
            bundle.putBoolean("com.google.android.gms.signin.internal.usePromptModeForAuthCode", true);
            bundle.putBoolean("com.google.android.gms.signin.internal.forceCodeForRefreshToken", zzaml.zzbev());
            bundle.putString("com.google.android.gms.signin.internal.hostedDomain", zzaml.zzbew());
            bundle.putBoolean("com.google.android.gms.signin.internal.waitForAccessTokenRefresh", zzaml.zzbex());
            if (zzaml.zzbey() != null) {
                bundle.putLong("com.google.android.gms.signin.internal.authApiSignInModuleVersion", zzaml.zzbey().longValue());
            }
            if (zzaml.zzbez() != null) {
                bundle.putLong("com.google.android.gms.signin.internal.realClientLibraryVersion", zzaml.zzbez().longValue());
            }
        }
        return bundle;
    }

    public final void connect() {
        zza(new zzm(this));
    }

    public final void zza(zzan zzan, boolean z) {
        try {
            ((zzcyr) zzalw()).zza(zzan, this.zzgft.intValue(), z);
        } catch (RemoteException e) {
            Log.w("SignInClientImpl", "Remote service probably died when saveDefaultAccount is called");
        }
    }

    public final void zza(zzcyp zzcyp) {
        zzbq.checkNotNull(zzcyp, "Expecting a valid ISignInCallbacks");
        try {
            Account zzamd = this.zzfwf.zzamd();
            GoogleSignInAccount googleSignInAccount = null;
            if ("<<default account>>".equals(zzamd.name)) {
                googleSignInAccount = zzaa.zzbs(getContext()).zzacx();
            }
            ((zzcyr) zzalw()).zza(new zzcyu(new zzbr(zzamd, this.zzgft.intValue(), googleSignInAccount)), zzcyp);
        } catch (Throwable e) {
            Log.w("SignInClientImpl", "Remote service probably died when signIn is called");
            try {
                zzcyp.zzb(new zzcyw(8));
            } catch (RemoteException e2) {
                Log.wtf("SignInClientImpl", "ISignInCallbacks#onSignInComplete should be executed from the same process, unexpected RemoteException.", e);
            }
        }
    }

    protected final Bundle zzabt() {
        if (!getContext().getPackageName().equals(this.zzfwf.zzami())) {
            this.zzklx.putString("com.google.android.gms.signin.internal.realClientPackageName", this.zzfwf.zzami());
        }
        return this.zzklx;
    }

    public final boolean zzacc() {
        return this.zzklw;
    }

    public final void zzbet() {
        try {
            ((zzcyr) zzalw()).zzev(this.zzgft.intValue());
        } catch (RemoteException e) {
            Log.w("SignInClientImpl", "Remote service probably died when clearAccountFromSessionStore is called");
        }
    }

    protected final /* synthetic */ IInterface zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.signin.internal.ISignInService");
        return queryLocalInterface instanceof zzcyr ? (zzcyr) queryLocalInterface : new zzcys(iBinder);
    }

    protected final String zzhm() {
        return "com.google.android.gms.signin.service.START";
    }

    protected final String zzhn() {
        return "com.google.android.gms.signin.internal.ISignInService";
    }
}
