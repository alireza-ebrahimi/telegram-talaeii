package com.google.android.gms.wearable.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzci;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzj;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.common.zzf;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.CapabilityApi.CapabilityListener;
import com.google.android.gms.wearable.ChannelApi.ChannelListener;
import com.google.android.gms.wearable.DataApi.DataItemResult;
import com.google.android.gms.wearable.DataApi.DataListener;
import com.google.android.gms.wearable.DataApi.GetFdForAssetResult;
import com.google.android.gms.wearable.MessageApi.MessageListener;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.WearableStatusCodes;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public final class zzhg extends zzab<zzep> {
    private final ExecutorService zzimc;
    private final zzer<Object> zzlvl;
    private final zzer<Object> zzlvm;
    private final zzer<ChannelListener> zzlvn;
    private final zzer<DataListener> zzlvo;
    private final zzer<MessageListener> zzlvp;
    private final zzer<Object> zzlvq;
    private final zzer<Object> zzlvr;
    private final zzer<CapabilityListener> zzlvs;
    private final zzhp zzlvt;

    public zzhg(Context context, Looper looper, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener, zzr zzr) {
        this(context, looper, connectionCallbacks, onConnectionFailedListener, zzr, Executors.newCachedThreadPool(), zzhp.zzeu(context));
    }

    private zzhg(Context context, Looper looper, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener, zzr zzr, ExecutorService executorService, zzhp zzhp) {
        super(context, looper, 14, zzr, connectionCallbacks, onConnectionFailedListener);
        this.zzlvl = new zzer();
        this.zzlvm = new zzer();
        this.zzlvn = new zzer();
        this.zzlvo = new zzer();
        this.zzlvp = new zzer();
        this.zzlvq = new zzer();
        this.zzlvr = new zzer();
        this.zzlvs = new zzer();
        this.zzimc = (ExecutorService) zzbq.checkNotNull(executorService);
        this.zzlvt = zzhp;
    }

    protected final void zza(int i, IBinder iBinder, Bundle bundle, int i2) {
        if (Log.isLoggable("WearableClient", 2)) {
            Log.v("WearableClient", "onPostInitHandler: statusCode " + i);
        }
        if (i == 0) {
            this.zzlvl.zzbt(iBinder);
            this.zzlvm.zzbt(iBinder);
            this.zzlvn.zzbt(iBinder);
            this.zzlvo.zzbt(iBinder);
            this.zzlvp.zzbt(iBinder);
            this.zzlvq.zzbt(iBinder);
            this.zzlvr.zzbt(iBinder);
            this.zzlvs.zzbt(iBinder);
        }
        super.zza(i, iBinder, bundle, i2);
    }

    public final void zza(zzn<GetFdForAssetResult> zzn, Asset asset) throws RemoteException {
        ((zzep) zzalw()).zza(new zzgx(zzn), asset);
    }

    public final void zza(zzn<Status> zzn, CapabilityListener capabilityListener) throws RemoteException {
        this.zzlvs.zza(this, zzn, capabilityListener);
    }

    public final void zza(zzn<Status> zzn, CapabilityListener capabilityListener, zzci<CapabilityListener> zzci, IntentFilter[] intentFilterArr) throws RemoteException {
        this.zzlvs.zza(this, zzn, capabilityListener, zzhk.zzd(zzci, intentFilterArr));
    }

    public final void zza(zzn<Status> zzn, ChannelListener channelListener, zzci<ChannelListener> zzci, String str, IntentFilter[] intentFilterArr) throws RemoteException {
        if (str == null) {
            this.zzlvn.zza(this, zzn, channelListener, zzhk.zzc(zzci, intentFilterArr));
            return;
        }
        this.zzlvn.zza(this, zzn, new zzgc(str, channelListener), zzhk.zza(zzci, str, intentFilterArr));
    }

    public final void zza(zzn<Status> zzn, ChannelListener channelListener, String str) throws RemoteException {
        if (str == null) {
            this.zzlvn.zza(this, zzn, channelListener);
            return;
        }
        this.zzlvn.zza(this, zzn, new zzgc(str, channelListener));
    }

    public final void zza(zzn<Status> zzn, DataListener dataListener) throws RemoteException {
        this.zzlvo.zza(this, zzn, dataListener);
    }

    public final void zza(zzn<Status> zzn, DataListener dataListener, zzci<DataListener> zzci, IntentFilter[] intentFilterArr) throws RemoteException {
        this.zzlvo.zza(this, zzn, dataListener, zzhk.zza(zzci, intentFilterArr));
    }

    public final void zza(zzn<Status> zzn, MessageListener messageListener) throws RemoteException {
        this.zzlvp.zza(this, zzn, messageListener);
    }

    public final void zza(zzn<Status> zzn, MessageListener messageListener, zzci<MessageListener> zzci, IntentFilter[] intentFilterArr) throws RemoteException {
        this.zzlvp.zza(this, zzn, messageListener, zzhk.zzb(zzci, intentFilterArr));
    }

    public final void zza(zzn<DataItemResult> zzn, PutDataRequest putDataRequest) throws RemoteException {
        for (Entry value : putDataRequest.getAssets().entrySet()) {
            Asset asset = (Asset) value.getValue();
            if (asset.getData() == null && asset.getDigest() == null && asset.getFd() == null && asset.getUri() == null) {
                String valueOf = String.valueOf(putDataRequest.getUri());
                String valueOf2 = String.valueOf(asset);
                throw new IllegalArgumentException(new StringBuilder((String.valueOf(valueOf).length() + 33) + String.valueOf(valueOf2).length()).append("Put for ").append(valueOf).append(" contains invalid asset: ").append(valueOf2).toString());
            }
        }
        PutDataRequest zzs = PutDataRequest.zzs(putDataRequest.getUri());
        zzs.setData(putDataRequest.getData());
        if (putDataRequest.isUrgent()) {
            zzs.setUrgent();
        }
        List arrayList = new ArrayList();
        for (Entry value2 : putDataRequest.getAssets().entrySet()) {
            Asset asset2 = (Asset) value2.getValue();
            if (asset2.getData() != null) {
                try {
                    ParcelFileDescriptor[] createPipe = ParcelFileDescriptor.createPipe();
                    if (Log.isLoggable("WearableClient", 3)) {
                        String valueOf3 = String.valueOf(asset2);
                        String valueOf4 = String.valueOf(createPipe[0]);
                        String valueOf5 = String.valueOf(createPipe[1]);
                        Log.d("WearableClient", new StringBuilder(((String.valueOf(valueOf3).length() + 61) + String.valueOf(valueOf4).length()) + String.valueOf(valueOf5).length()).append("processAssets: replacing data with FD in asset: ").append(valueOf3).append(" read:").append(valueOf4).append(" write:").append(valueOf5).toString());
                    }
                    zzs.putAsset((String) value2.getKey(), Asset.createFromFd(createPipe[0]));
                    Runnable futureTask = new FutureTask(new zzhh(this, createPipe[1], asset2.getData()));
                    arrayList.add(futureTask);
                    this.zzimc.submit(futureTask);
                } catch (Throwable e) {
                    valueOf = String.valueOf(putDataRequest);
                    throw new IllegalStateException(new StringBuilder(String.valueOf(valueOf).length() + 60).append("Unable to create ParcelFileDescriptor for asset in request: ").append(valueOf).toString(), e);
                }
            } else if (asset2.getUri() != null) {
                try {
                    zzs.putAsset((String) value2.getKey(), Asset.createFromFd(getContext().getContentResolver().openFileDescriptor(asset2.getUri(), "r")));
                } catch (FileNotFoundException e2) {
                    new zzhb(zzn, arrayList).zza(new zzfu(WearableStatusCodes.ASSET_UNAVAILABLE, null));
                    String valueOf6 = String.valueOf(asset2.getUri());
                    Log.w("WearableClient", new StringBuilder(String.valueOf(valueOf6).length() + 28).append("Couldn't resolve asset URI: ").append(valueOf6).toString());
                    return;
                }
            } else {
                zzs.putAsset((String) value2.getKey(), asset2);
            }
        }
        ((zzep) zzalw()).zza(new zzhb(zzn, arrayList), zzs);
    }

    public final void zza(zzn<Status> zzn, String str, Uri uri, long j, long j2) {
        try {
            ExecutorService executorService = this.zzimc;
            zzbq.checkNotNull(zzn);
            zzbq.checkNotNull(str);
            zzbq.checkNotNull(uri);
            zzbq.zzb(j >= 0, "startOffset is negative: %s", Long.valueOf(j));
            zzbq.zzb(j2 >= -1, "invalid length: %s", Long.valueOf(j2));
            executorService.execute(new zzhj(this, uri, zzn, str, j, j2));
        } catch (RuntimeException e) {
            zzn.zzu(new Status(8));
            throw e;
        }
    }

    public final void zza(zzn<Status> zzn, String str, Uri uri, boolean z) {
        try {
            ExecutorService executorService = this.zzimc;
            zzbq.checkNotNull(zzn);
            zzbq.checkNotNull(str);
            zzbq.checkNotNull(uri);
            executorService.execute(new zzhi(this, uri, zzn, z, str));
        } catch (RuntimeException e) {
            zzn.zzu(new Status(8));
            throw e;
        }
    }

    public final void zza(@NonNull zzj zzj) {
        int i = 0;
        if (!zzahn()) {
            try {
                Bundle bundle = getContext().getPackageManager().getApplicationInfo("com.google.android.wearable.app.cn", 128).metaData;
                if (bundle != null) {
                    i = bundle.getInt("com.google.android.wearable.api.version", 0);
                }
                if (i < zzf.GOOGLE_PLAY_SERVICES_VERSION_CODE) {
                    Log.w("WearableClient", "Android Wear out of date. Requires API version " + zzf.GOOGLE_PLAY_SERVICES_VERSION_CODE + " but found " + i);
                    Context context = getContext();
                    Context context2 = getContext();
                    Intent intent = new Intent("com.google.android.wearable.app.cn.UPDATE_ANDROID_WEAR").setPackage("com.google.android.wearable.app.cn");
                    if (context2.getPackageManager().resolveActivity(intent, 65536) == null) {
                        intent = new Intent("android.intent.action.VIEW", Uri.parse("market://details").buildUpon().appendQueryParameter("id", "com.google.android.wearable.app.cn").build());
                    }
                    zza(zzj, 6, PendingIntent.getActivity(context, 0, intent, 0));
                    return;
                }
            } catch (NameNotFoundException e) {
                zza(zzj, 16, null);
                return;
            }
        }
        super.zza(zzj);
    }

    public final boolean zzahn() {
        return !this.zzlvt.zzof("com.google.android.wearable.app.cn");
    }

    protected final String zzalq() {
        return this.zzlvt.zzof("com.google.android.wearable.app.cn") ? "com.google.android.wearable.app.cn" : "com.google.android.gms";
    }

    protected final /* synthetic */ IInterface zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.wearable.internal.IWearableService");
        return queryLocalInterface instanceof zzep ? (zzep) queryLocalInterface : new zzeq(iBinder);
    }

    protected final String zzhm() {
        return "com.google.android.gms.wearable.BIND";
    }

    protected final String zzhn() {
        return "com.google.android.gms.wearable.internal.IWearableService";
    }
}
