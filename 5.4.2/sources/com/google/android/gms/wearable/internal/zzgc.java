package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.wearable.Channel;
import com.google.android.gms.wearable.ChannelApi.ChannelListener;

final class zzgc implements ChannelListener {
    private final String zzce;
    private final ChannelListener zzeq;

    zzgc(String str, ChannelListener channelListener) {
        this.zzce = (String) Preconditions.checkNotNull(str);
        this.zzeq = (ChannelListener) Preconditions.checkNotNull(channelListener);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzgc)) {
            return false;
        }
        zzgc zzgc = (zzgc) obj;
        return this.zzeq.equals(zzgc.zzeq) && this.zzce.equals(zzgc.zzce);
    }

    public final int hashCode() {
        return (this.zzce.hashCode() * 31) + this.zzeq.hashCode();
    }

    public final void onChannelClosed(Channel channel, int i, int i2) {
        this.zzeq.onChannelClosed(channel, i, i2);
    }

    public final void onChannelOpened(Channel channel) {
        this.zzeq.onChannelOpened(channel);
    }

    public final void onInputClosed(Channel channel, int i, int i2) {
        this.zzeq.onInputClosed(channel, i, i2);
    }

    public final void onOutputClosed(Channel channel, int i, int i2) {
        this.zzeq.onOutputClosed(channel, i, i2);
    }
}
