package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.wearable.Channel;
import com.google.android.gms.wearable.ChannelApi.ChannelListener;

final class zzgc implements ChannelListener {
    private final String zzeia;
    private final ChannelListener zzlvg;

    zzgc(String str, ChannelListener channelListener) {
        this.zzeia = (String) zzbq.checkNotNull(str);
        this.zzlvg = (ChannelListener) zzbq.checkNotNull(channelListener);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzgc)) {
            return false;
        }
        zzgc zzgc = (zzgc) obj;
        return this.zzlvg.equals(zzgc.zzlvg) && this.zzeia.equals(zzgc.zzeia);
    }

    public final int hashCode() {
        return (this.zzeia.hashCode() * 31) + this.zzlvg.hashCode();
    }

    public final void onChannelClosed(Channel channel, int i, int i2) {
        this.zzlvg.onChannelClosed(channel, i, i2);
    }

    public final void onChannelOpened(Channel channel) {
        this.zzlvg.onChannelOpened(channel);
    }

    public final void onInputClosed(Channel channel, int i, int i2) {
        this.zzlvg.onInputClosed(channel, i, i2);
    }

    public final void onOutputClosed(Channel channel, int i, int i2) {
        this.zzlvg.onOutputClosed(channel, i, i2);
    }
}
