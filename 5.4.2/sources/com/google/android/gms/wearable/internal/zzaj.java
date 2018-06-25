package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.wearable.ChannelApi;
import com.google.android.gms.wearable.ChannelApi.ChannelListener;
import com.google.android.gms.wearable.ChannelApi.OpenChannelResult;

public final class zzaj implements ChannelApi {
    public final PendingResult<Status> addListener(GoogleApiClient googleApiClient, ChannelListener channelListener) {
        Preconditions.checkNotNull(googleApiClient, "client is null");
        Preconditions.checkNotNull(channelListener, "listener is null");
        return zzb.zza(googleApiClient, new zzal(new IntentFilter[]{zzgj.zzc("com.google.android.gms.wearable.CHANNEL_EVENT")}), channelListener);
    }

    public final PendingResult<OpenChannelResult> openChannel(GoogleApiClient googleApiClient, String str, String str2) {
        Preconditions.checkNotNull(googleApiClient, "client is null");
        Preconditions.checkNotNull(str, "nodeId is null");
        Preconditions.checkNotNull(str2, "path is null");
        return googleApiClient.enqueue(new zzak(this, googleApiClient, str, str2));
    }

    public final PendingResult<Status> removeListener(GoogleApiClient googleApiClient, ChannelListener channelListener) {
        Preconditions.checkNotNull(googleApiClient, "client is null");
        Preconditions.checkNotNull(channelListener, "listener is null");
        return googleApiClient.enqueue(new zzan(googleApiClient, channelListener, null));
    }
}
