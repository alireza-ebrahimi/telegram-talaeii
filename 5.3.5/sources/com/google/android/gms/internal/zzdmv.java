package com.google.android.gms.internal;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.api.BooleanResult;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.wallet.AutoResolvableVoidResult;
import com.google.android.gms.wallet.CreateWalletObjectsRequest;
import com.google.android.gms.wallet.FullWalletRequest;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.MaskedWalletRequest;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentDataRequest;

@Hide
public final class zzdmv extends zzab<zzdmi> {
    private final Context mContext;
    private final int mTheme;
    private final String zzehk;
    private final int zzlod;
    private final boolean zzloe;

    public zzdmv(Context context, Looper looper, zzr zzr, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener, int i, int i2, boolean z) {
        super(context, looper, 4, zzr, connectionCallbacks, onConnectionFailedListener);
        this.mContext = context;
        this.zzlod = i;
        this.zzehk = zzr.getAccountName();
        this.mTheme = i2;
        this.zzloe = z;
    }

    private final Bundle zzblt() {
        int i = this.zzlod;
        String packageName = this.mContext.getPackageName();
        Object obj = this.zzehk;
        int i2 = this.mTheme;
        boolean z = this.zzloe;
        Bundle bundle = new Bundle();
        bundle.putInt("com.google.android.gms.wallet.EXTRA_ENVIRONMENT", i);
        bundle.putBoolean("com.google.android.gms.wallet.EXTRA_USING_ANDROID_PAY_BRAND", z);
        bundle.putString("androidPackageName", packageName);
        if (!TextUtils.isEmpty(obj)) {
            bundle.putParcelable("com.google.android.gms.wallet.EXTRA_BUYER_ACCOUNT", new Account(obj, "com.google"));
        }
        bundle.putInt("com.google.android.gms.wallet.EXTRA_THEME", i2);
        return bundle;
    }

    public final void zza(CreateWalletObjectsRequest createWalletObjectsRequest, int i) {
        zzdmm zzdmw = new zzdmw((Activity) this.mContext, i);
        try {
            ((zzdmi) zzalw()).zza(createWalletObjectsRequest, zzblt(), zzdmw);
        } catch (Throwable e) {
            Log.e("WalletClientImpl", "RemoteException creating wallet objects", e);
            zzdmw.zzl(8, Bundle.EMPTY);
        }
    }

    public final void zza(CreateWalletObjectsRequest createWalletObjectsRequest, TaskCompletionSource<AutoResolvableVoidResult> taskCompletionSource) {
        Bundle zzblt = zzblt();
        zzblt.putBoolean("com.google.android.gms.wallet.EXTRA_USING_AUTO_RESOLVABLE_RESULT", true);
        zzdmm zzdmz = new zzdmz(taskCompletionSource);
        try {
            ((zzdmi) zzalw()).zza(createWalletObjectsRequest, zzblt, zzdmz);
        } catch (Throwable e) {
            Log.e("WalletClientImpl", "RemoteException creating wallet objects", e);
            zzdmz.zzl(8, Bundle.EMPTY);
        }
    }

    public final void zza(FullWalletRequest fullWalletRequest, int i) {
        zzdmm zzdmw = new zzdmw((Activity) this.mContext, i);
        try {
            ((zzdmi) zzalw()).zza(fullWalletRequest, zzblt(), zzdmw);
        } catch (Throwable e) {
            Log.e("WalletClientImpl", "RemoteException getting full wallet", e);
            zzdmw.zza(8, null, Bundle.EMPTY);
        }
    }

    public final void zza(IsReadyToPayRequest isReadyToPayRequest, zzn<BooleanResult> zzn) {
        zzdmm zzdna = new zzdna(zzn);
        try {
            ((zzdmi) zzalw()).zza(isReadyToPayRequest, zzblt(), zzdna);
        } catch (Throwable e) {
            Log.e("WalletClientImpl", "RemoteException during isReadyToPay", e);
            zzdna.zza(Status.zzfts, false, Bundle.EMPTY);
        }
    }

    public final void zza(IsReadyToPayRequest isReadyToPayRequest, TaskCompletionSource<Boolean> taskCompletionSource) throws RemoteException {
        zzdmm zzdmy = new zzdmy(taskCompletionSource);
        try {
            ((zzdmi) zzalw()).zza(isReadyToPayRequest, zzblt(), zzdmy);
        } catch (Throwable e) {
            Log.e("WalletClientImpl", "RemoteException during isReadyToPay", e);
            zzdmy.zza(Status.zzfts, false, Bundle.EMPTY);
        }
    }

    public final void zza(MaskedWalletRequest maskedWalletRequest, int i) {
        Activity activity = (Activity) this.mContext;
        Bundle zzblt = zzblt();
        zzdmm zzdmw = new zzdmw(activity, i);
        try {
            ((zzdmi) zzalw()).zza(maskedWalletRequest, zzblt, zzdmw);
        } catch (Throwable e) {
            Log.e("WalletClientImpl", "RemoteException getting masked wallet", e);
            zzdmw.zza(8, null, Bundle.EMPTY);
        }
    }

    public final void zza(PaymentDataRequest paymentDataRequest, TaskCompletionSource<PaymentData> taskCompletionSource) {
        Bundle zzblt = zzblt();
        zzblt.putBoolean("com.google.android.gms.wallet.EXTRA_USING_AUTO_RESOLVABLE_RESULT", true);
        zzdmm zzdnb = new zzdnb(taskCompletionSource);
        try {
            ((zzdmi) zzalw()).zza(paymentDataRequest, zzblt, zzdnb);
        } catch (Throwable e) {
            Log.e("WalletClientImpl", "RemoteException getting payment data", e);
            zzdnb.zza(Status.zzfts, null, Bundle.EMPTY);
        }
    }

    public final boolean zzalx() {
        return true;
    }

    public final void zzc(String str, String str2, int i) {
        Activity activity = (Activity) this.mContext;
        Bundle zzblt = zzblt();
        Object zzdmw = new zzdmw(activity, i);
        try {
            ((zzdmi) zzalw()).zza(str, str2, zzblt, zzdmw);
        } catch (Throwable e) {
            Log.e("WalletClientImpl", "RemoteException changing masked wallet", e);
            zzdmw.zza(8, null, Bundle.EMPTY);
        }
    }

    protected final /* synthetic */ IInterface zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.wallet.internal.IOwService");
        return queryLocalInterface instanceof zzdmi ? (zzdmi) queryLocalInterface : new zzdmj(iBinder);
    }

    public final void zzfq(int i) {
        Bundle zzblt = zzblt();
        Object zzdmw = new zzdmw((Activity) this.mContext, i);
        try {
            ((zzdmi) zzalw()).zza(zzblt, zzdmw);
        } catch (Throwable e) {
            Log.e("WalletClientImpl", "RemoteException during checkForPreAuthorization", e);
            zzdmw.zza(8, false, Bundle.EMPTY);
        }
    }

    protected final String zzhm() {
        return "com.google.android.gms.wallet.service.BIND";
    }

    protected final String zzhn() {
        return "com.google.android.gms.wallet.internal.IOwService";
    }
}
