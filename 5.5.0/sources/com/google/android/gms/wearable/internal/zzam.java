package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.wearable.Channel;
import com.google.android.gms.wearable.ChannelApi.OpenChannelResult;
import javax.annotation.Nullable;

final class zzam implements OpenChannelResult {
    private final Channel zzcd;
    private final Status zzp;

    zzam(Status status, @Nullable Channel channel) {
        this.zzp = (Status) Preconditions.checkNotNull(status);
        this.zzcd = channel;
    }

    @Nullable
    public final Channel getChannel() {
        return this.zzcd;
    }

    public final Status getStatus() {
        return this.zzp;
    }
}
