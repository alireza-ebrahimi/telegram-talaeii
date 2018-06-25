package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.wearable.ChannelApi;
import com.google.android.gms.wearable.ChannelApi.ChannelListener;
import com.google.android.gms.wearable.ChannelApi.OpenChannelResult;

@Hide
public final class zzaj implements ChannelApi {
    public final PendingResult<Status> addListener(GoogleApiClient googleApiClient, ChannelListener channelListener) {
        zzbq.checkNotNull(googleApiClient, "client is null");
        zzbq.checkNotNull(channelListener, "listener is null");
        return zzb.zza(googleApiClient, new zzal(new IntentFilter[]{zzgj.zzoe("com.google.android.gms.wearable.CHANNEL_EVENT")}), channelListener);
    }

    public final PendingResult<OpenChannelResult> openChannel(GoogleApiClient googleApiClient, String str, String str2) {
        zzbq.checkNotNull(googleApiClient, "client is null");
        zzbq.checkNotNull(str, "nodeId is null");
        zzbq.checkNotNull(str2, "path is null");
        return googleApiClient.zzd(new zzak(this, googleApiClient, str, str2));
    }

    public final PendingResult<Status> removeListener(GoogleApiClient googleApiClient, ChannelListener channelListener) {
        zzbq.checkNotNull(googleApiClient, "client is null");
        zzbq.checkNotNull(channelListener, "listener is null");
        return googleApiClient.zzd(new zzan(googleApiClient, channelListener, null));
    }
}
