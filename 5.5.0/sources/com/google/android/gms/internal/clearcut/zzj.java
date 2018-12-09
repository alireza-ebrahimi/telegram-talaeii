package com.google.android.gms.internal.clearcut;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.GmsClient;

public final class zzj extends GmsClient<zzn> {
    public zzj(Context context, Looper looper, ClientSettings clientSettings, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 40, clientSettings, connectionCallbacks, onConnectionFailedListener);
    }

    protected final /* synthetic */ IInterface createServiceInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.clearcut.internal.IClearcutLoggerService");
        return queryLocalInterface instanceof zzn ? (zzn) queryLocalInterface : new zzo(iBinder);
    }

    public final int getMinApkVersion() {
        return 11925000;
    }

    protected final String getServiceDescriptor() {
        return "com.google.android.gms.clearcut.internal.IClearcutLoggerService";
    }

    protected final String getStartServiceAction() {
        return "com.google.android.gms.clearcut.service.START";
    }
}
