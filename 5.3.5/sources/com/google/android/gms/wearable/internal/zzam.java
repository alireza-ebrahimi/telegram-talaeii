package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.wearable.Channel;
import com.google.android.gms.wearable.ChannelApi.OpenChannelResult;

final class zzam implements OpenChannelResult {
    private final Status mStatus;
    private final Channel zzlsz;

    zzam(Status status, Channel channel) {
        this.mStatus = (Status) zzbq.checkNotNull(status);
        this.zzlsz = channel;
    }

    public final Channel getChannel() {
        return this.zzlsz;
    }

    public final Status getStatus() {
        return this.mStatus;
    }
}
