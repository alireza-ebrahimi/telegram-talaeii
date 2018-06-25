package com.google.android.gms.internal;

import android.accounts.Account;
import android.app.Activity;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.identity.intents.AddressConstants.ErrorCodes;
import com.google.android.gms.identity.intents.AddressConstants.Extras;
import com.google.android.gms.identity.intents.UserAddressRequest;

@Hide
public final class zzcdo extends zzab<zzcds> {
    private Activity mActivity;
    private final int mTheme;
    private final String zzehk;
    private zzcdp zzilp;

    public zzcdo(Activity activity, Looper looper, zzr zzr, int i, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        super(activity, looper, 12, zzr, connectionCallbacks, onConnectionFailedListener);
        this.zzehk = zzr.getAccountName();
        this.mActivity = activity;
        this.mTheme = i;
    }

    public final void disconnect() {
        super.disconnect();
        if (this.zzilp != null) {
            this.zzilp.setActivity(null);
            this.zzilp = null;
        }
    }

    public final void zza(UserAddressRequest userAddressRequest, int i) {
        super.zzalv();
        this.zzilp = new zzcdp(i, this.mActivity);
        try {
            Bundle bundle = new Bundle();
            bundle.putString("com.google.android.gms.identity.intents.EXTRA_CALLING_PACKAGE_NAME", getContext().getPackageName());
            if (!TextUtils.isEmpty(this.zzehk)) {
                bundle.putParcelable("com.google.android.gms.identity.intents.EXTRA_ACCOUNT", new Account(this.zzehk, "com.google"));
            }
            bundle.putInt("com.google.android.gms.identity.intents.EXTRA_THEME", this.mTheme);
            ((zzcds) super.zzalw()).zza(this.zzilp, userAddressRequest, bundle);
        } catch (Throwable e) {
            Log.e("AddressClientImpl", "Exception requesting user address", e);
            Bundle bundle2 = new Bundle();
            bundle2.putInt(Extras.EXTRA_ERROR_CODE, ErrorCodes.ERROR_CODE_NO_APPLICABLE_ADDRESSES);
            this.zzilp.zzi(1, bundle2);
        }
    }

    public final boolean zzalx() {
        return true;
    }

    protected final /* synthetic */ IInterface zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.identity.intents.internal.IAddressService");
        return queryLocalInterface instanceof zzcds ? (zzcds) queryLocalInterface : new zzcdt(iBinder);
    }

    protected final String zzhm() {
        return "com.google.android.gms.identity.service.BIND";
    }

    protected final String zzhn() {
        return "com.google.android.gms.identity.intents.internal.IAddressService";
    }
}
