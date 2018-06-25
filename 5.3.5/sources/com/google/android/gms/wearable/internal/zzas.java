package com.google.android.gms.wearable.internal;

import com.google.android.gms.wearable.Channel;
import com.google.android.gms.wearable.ChannelApi.ChannelListener;
import com.google.android.gms.wearable.ChannelClient.ChannelCallback;

public final class zzas implements ChannelListener {
    private final ChannelCallback zzltc;

    public zzas(ChannelCallback channelCallback) {
        this.zzltc = channelCallback;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.zzltc.equals(((zzas) obj).zzltc);
    }

    public final int hashCode() {
        return this.zzltc.hashCode();
    }

    public final void onChannelClosed(Channel channel, int i, int i2) {
        this.zzltc.onChannelClosed(zzao.zza(channel), i, i2);
    }

    public final void onChannelOpened(Channel channel) {
        this.zzltc.onChannelOpened(zzao.zza(channel));
    }

    public final void onInputClosed(Channel channel, int i, int i2) {
        this.zzltc.onInputClosed(zzao.zza(channel), i, i2);
    }

    public final void onOutputClosed(Channel channel, int i, int i2) {
        this.zzltc.onOutputClosed(zzao.zza(channel), i, i2);
    }
}
