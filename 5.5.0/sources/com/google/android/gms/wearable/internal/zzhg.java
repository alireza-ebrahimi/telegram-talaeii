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
import android.util.Log;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation.ResultHolder;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.internal.BaseGmsClient.ConnectionProgressReportCallbacks;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.GmsClient;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;
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
import javax.annotation.Nullable;
import org.telegram.messenger.exoplayer2.C3446C;

public final class zzhg extends GmsClient<zzep> {
    private final ExecutorService zzew;
    private final zzer<Object> zzex;
    private final zzer<Object> zzey;
    private final zzer<ChannelListener> zzez;
    private final zzer<DataListener> zzfa;
    private final zzer<MessageListener> zzfb;
    private final zzer<Object> zzfc;
    private final zzer<Object> zzfd;
    private final zzer<CapabilityListener> zzfe;
    private final zzhp zzff;

    public zzhg(Context context, Looper looper, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener, ClientSettings clientSettings) {
        this(context, looper, connectionCallbacks, onConnectionFailedListener, clientSettings, Executors.newCachedThreadPool(), zzhp.zza(context));
    }

    @VisibleForTesting
    private zzhg(Context context, Looper looper, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener, ClientSettings clientSettings, ExecutorService executorService, zzhp zzhp) {
        super(context, looper, 14, clientSettings, connectionCallbacks, onConnectionFailedListener);
        this.zzex = new zzer();
        this.zzey = new zzer();
        this.zzez = new zzer();
        this.zzfa = new zzer();
        this.zzfb = new zzer();
        this.zzfc = new zzer();
        this.zzfd = new zzer();
        this.zzfe = new zzer();
        this.zzew = (ExecutorService) Preconditions.checkNotNull(executorService);
        this.zzff = zzhp;
    }

    public final void connect(ConnectionProgressReportCallbacks connectionProgressReportCallbacks) {
        int i = 0;
        if (!requiresGooglePlayServices()) {
            try {
                Bundle bundle = getContext().getPackageManager().getApplicationInfo("com.google.android.wearable.app.cn", 128).metaData;
                if (bundle != null) {
                    i = bundle.getInt("com.google.android.wearable.api.version", 0);
                }
                if (i < GoogleApiAvailabilityLight.GOOGLE_PLAY_SERVICES_VERSION_CODE) {
                    Log.w("WearableClient", "The Wear OS app is out of date. Requires API version " + GoogleApiAvailabilityLight.GOOGLE_PLAY_SERVICES_VERSION_CODE + " but found " + i);
                    Context context = getContext();
                    Context context2 = getContext();
                    Intent intent = new Intent("com.google.android.wearable.app.cn.UPDATE_ANDROID_WEAR").setPackage("com.google.android.wearable.app.cn");
                    if (context2.getPackageManager().resolveActivity(intent, C3446C.DEFAULT_BUFFER_SEGMENT_SIZE) == null) {
                        intent = new Intent("android.intent.action.VIEW", Uri.parse("market://details").buildUpon().appendQueryParameter(TtmlNode.ATTR_ID, "com.google.android.wearable.app.cn").build());
                    }
                    triggerNotAvailable(connectionProgressReportCallbacks, 6, PendingIntent.getActivity(context, 0, intent, 0));
                    return;
                }
            } catch (NameNotFoundException e) {
                triggerNotAvailable(connectionProgressReportCallbacks, 16, null);
                return;
            }
        }
        super.connect(connectionProgressReportCallbacks);
    }

    protected final /* synthetic */ IInterface createServiceInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.wearable.internal.IWearableService");
        return queryLocalInterface instanceof zzep ? (zzep) queryLocalInterface : new zzeq(iBinder);
    }

    public final int getMinApkVersion() {
        return 12451000;
    }

    protected final String getServiceDescriptor() {
        return "com.google.android.gms.wearable.internal.IWearableService";
    }

    protected final String getStartServiceAction() {
        return "com.google.android.gms.wearable.BIND";
    }

    protected final String getStartServicePackage() {
        return this.zzff.zze("com.google.android.wearable.app.cn") ? "com.google.android.wearable.app.cn" : "com.google.android.gms";
    }

    protected final void onPostInitHandler(int i, IBinder iBinder, Bundle bundle, int i2) {
        if (Log.isLoggable("WearableClient", 2)) {
            Log.v("WearableClient", "onPostInitHandler: statusCode " + i);
        }
        if (i == 0) {
            this.zzex.zza(iBinder);
            this.zzey.zza(iBinder);
            this.zzez.zza(iBinder);
            this.zzfa.zza(iBinder);
            this.zzfb.zza(iBinder);
            this.zzfc.zza(iBinder);
            this.zzfd.zza(iBinder);
            this.zzfe.zza(iBinder);
        }
        super.onPostInitHandler(i, iBinder, bundle, i2);
    }

    public final boolean requiresGooglePlayServices() {
        return !this.zzff.zze("com.google.android.wearable.app.cn");
    }

    public final void zza(ResultHolder<GetFdForAssetResult> resultHolder, Asset asset) {
        ((zzep) getService()).zza(new zzgx(resultHolder), asset);
    }

    public final void zza(ResultHolder<Status> resultHolder, CapabilityListener capabilityListener) {
        this.zzfe.zza(this, resultHolder, capabilityListener);
    }

    public final void zza(ResultHolder<Status> resultHolder, CapabilityListener capabilityListener, ListenerHolder<CapabilityListener> listenerHolder, IntentFilter[] intentFilterArr) {
        this.zzfe.zza(this, resultHolder, capabilityListener, zzhk.zzd(listenerHolder, intentFilterArr));
    }

    public final void zza(ResultHolder<Status> resultHolder, ChannelListener channelListener, ListenerHolder<ChannelListener> listenerHolder, @Nullable String str, IntentFilter[] intentFilterArr) {
        if (str == null) {
            this.zzez.zza(this, resultHolder, channelListener, zzhk.zzc(listenerHolder, intentFilterArr));
            return;
        }
        this.zzez.zza(this, resultHolder, new zzgc(str, channelListener), zzhk.zza(listenerHolder, str, intentFilterArr));
    }

    public final void zza(ResultHolder<Status> resultHolder, ChannelListener channelListener, String str) {
        if (str == null) {
            this.zzez.zza(this, resultHolder, channelListener);
            return;
        }
        this.zzez.zza(this, resultHolder, new zzgc(str, channelListener));
    }

    public final void zza(ResultHolder<Status> resultHolder, DataListener dataListener) {
        this.zzfa.zza(this, resultHolder, dataListener);
    }

    public final void zza(ResultHolder<Status> resultHolder, DataListener dataListener, ListenerHolder<DataListener> listenerHolder, IntentFilter[] intentFilterArr) {
        this.zzfa.zza(this, resultHolder, dataListener, zzhk.zza(listenerHolder, intentFilterArr));
    }

    public final void zza(ResultHolder<Status> resultHolder, MessageListener messageListener) {
        this.zzfb.zza(this, resultHolder, messageListener);
    }

    public final void zza(ResultHolder<Status> resultHolder, MessageListener messageListener, ListenerHolder<MessageListener> listenerHolder, IntentFilter[] intentFilterArr) {
        this.zzfb.zza(this, resultHolder, messageListener, zzhk.zzb(listenerHolder, intentFilterArr));
    }

    public final void zza(ResultHolder<DataItemResult> resultHolder, PutDataRequest putDataRequest) {
        for (Entry value : putDataRequest.getAssets().entrySet()) {
            Asset asset = (Asset) value.getValue();
            if (asset.getData() == null && asset.getDigest() == null && asset.getFd() == null && asset.getUri() == null) {
                String valueOf = String.valueOf(putDataRequest.getUri());
                String valueOf2 = String.valueOf(asset);
                throw new IllegalArgumentException(new StringBuilder((String.valueOf(valueOf).length() + 33) + String.valueOf(valueOf2).length()).append("Put for ").append(valueOf).append(" contains invalid asset: ").append(valueOf2).toString());
            }
        }
        PutDataRequest zza = PutDataRequest.zza(putDataRequest.getUri());
        zza.setData(putDataRequest.getData());
        if (putDataRequest.isUrgent()) {
            zza.setUrgent();
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
                    zza.putAsset((String) value2.getKey(), Asset.createFromFd(createPipe[0]));
                    Runnable futureTask = new FutureTask(new zzhh(this, createPipe[1], asset2.getData()));
                    arrayList.add(futureTask);
                    this.zzew.submit(futureTask);
                } catch (Throwable e) {
                    valueOf = String.valueOf(putDataRequest);
                    throw new IllegalStateException(new StringBuilder(String.valueOf(valueOf).length() + 60).append("Unable to create ParcelFileDescriptor for asset in request: ").append(valueOf).toString(), e);
                }
            } else if (asset2.getUri() != null) {
                try {
                    zza.putAsset((String) value2.getKey(), Asset.createFromFd(getContext().getContentResolver().openFileDescriptor(asset2.getUri(), "r")));
                } catch (FileNotFoundException e2) {
                    new zzhb(resultHolder, arrayList).zza(new zzfu(WearableStatusCodes.ASSET_UNAVAILABLE, null));
                    String valueOf6 = String.valueOf(asset2.getUri());
                    Log.w("WearableClient", new StringBuilder(String.valueOf(valueOf6).length() + 28).append("Couldn't resolve asset URI: ").append(valueOf6).toString());
                    return;
                }
            } else {
                zza.putAsset((String) value2.getKey(), asset2);
            }
        }
        ((zzep) getService()).zza(new zzhb(resultHolder, arrayList), zza);
    }

    public final void zza(ResultHolder<Status> resultHolder, String str, Uri uri, long j, long j2) {
        try {
            ExecutorService executorService = this.zzew;
            Preconditions.checkNotNull(resultHolder);
            Preconditions.checkNotNull(str);
            Preconditions.checkNotNull(uri);
            Preconditions.checkArgument(j >= 0, "startOffset is negative: %s", Long.valueOf(j));
            Preconditions.checkArgument(j2 >= -1, "invalid length: %s", Long.valueOf(j2));
            executorService.execute(new zzhj(this, uri, resultHolder, str, j, j2));
        } catch (RuntimeException e) {
            resultHolder.setFailedResult(new Status(8));
            throw e;
        }
    }

    public final void zza(ResultHolder<Status> resultHolder, String str, Uri uri, boolean z) {
        try {
            ExecutorService executorService = this.zzew;
            Preconditions.checkNotNull(resultHolder);
            Preconditions.checkNotNull(str);
            Preconditions.checkNotNull(uri);
            executorService.execute(new zzhi(this, uri, resultHolder, z, str));
        } catch (RuntimeException e) {
            resultHolder.setFailedResult(new Status(8));
            throw e;
        }
    }
}
