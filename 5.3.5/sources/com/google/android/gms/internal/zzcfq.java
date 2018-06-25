package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzr;

@Hide
public class zzcfq extends zzab<zzcgw> {
    private final String zzitj;
    protected final zzchr<zzcgw> zzitk = new zzcfr(this);

    public zzcfq(Context context, Looper looper, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener, String str, zzr zzr) {
        super(context, looper, 23, zzr, connectionCallbacks, onConnectionFailedListener);
        this.zzitj = str;
    }

    protected final Bundle zzabt() {
        Bundle bundle = new Bundle();
        bundle.putString("client_name", this.zzitj);
        return bundle;
    }

    protected final /* synthetic */ IInterface zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
        return queryLocalInterface instanceof zzcgw ? (zzcgw) queryLocalInterface : new zzcgx(iBinder);
    }

    protected final String zzhm() {
        return "com.google.android.location.internal.GoogleLocationManagerService.START";
    }

    protected final String zzhn() {
        return "com.google.android.gms.location.internal.IGoogleLocationManagerService";
    }
}
