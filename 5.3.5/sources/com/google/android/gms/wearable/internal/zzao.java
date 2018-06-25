package com.google.android.gms.wearable.internal;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Looper;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.GoogleApi.zza;
import com.google.android.gms.common.api.internal.zzci;
import com.google.android.gms.common.api.internal.zzcm;
import com.google.android.gms.common.internal.zzbj;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.Channel;
import com.google.android.gms.wearable.ChannelApi.ChannelListener;
import com.google.android.gms.wearable.ChannelClient;
import com.google.android.gms.wearable.ChannelClient.ChannelCallback;
import java.io.InputStream;
import java.io.OutputStream;

public final class zzao extends ChannelClient {
    private final zzaj zzltb = new zzaj();

    public zzao(@NonNull Activity activity, @NonNull zza zza) {
        super(activity, zza);
    }

    public zzao(@NonNull Context context, @NonNull zza zza) {
        super(context, zza);
    }

    private static zzay zza(@NonNull Channel channel) {
        zzbq.checkNotNull(channel, "channel must not be null");
        return (zzay) channel;
    }

    private static zzay zza(@NonNull ChannelClient.Channel channel) {
        zzbq.checkNotNull(channel, "channel must not be null");
        return (zzay) channel;
    }

    public final Task<Void> close(@NonNull ChannelClient.Channel channel) {
        return zzbj.zzb(zza(channel).close(zzahw()));
    }

    public final Task<Void> close(@NonNull ChannelClient.Channel channel, int i) {
        return zzbj.zzb(zza(channel).close(zzahw(), i));
    }

    public final Task<InputStream> getInputStream(@NonNull ChannelClient.Channel channel) {
        return zzbj.zza(zza(channel).getInputStream(zzahw()), zzaq.zzgui);
    }

    public final Task<OutputStream> getOutputStream(@NonNull ChannelClient.Channel channel) {
        return zzbj.zza(zza(channel).getOutputStream(zzahw()), zzar.zzgui);
    }

    public final Task<ChannelClient.Channel> openChannel(@NonNull String str, @NonNull String str2) {
        return zzbj.zza(this.zzltb.openChannel(zzahw(), str, str2), zzap.zzgui);
    }

    public final Task<Void> receiveFile(@NonNull ChannelClient.Channel channel, @NonNull Uri uri, boolean z) {
        return zzbj.zzb(zza(channel).receiveFile(zzahw(), uri, z));
    }

    public final Task<Void> registerChannelCallback(@NonNull ChannelClient.Channel channel, @NonNull ChannelCallback channelCallback) {
        String token = ((zzay) channel).getToken();
        zzbq.checkNotNull(channelCallback, "listener is null");
        Looper looper = getLooper();
        String str = "ChannelListener:";
        String valueOf = String.valueOf(token);
        zzci zzb = zzcm.zzb(channelCallback, looper, valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
        IntentFilter[] intentFilterArr = new IntentFilter[]{zzgj.zzoe("com.google.android.gms.wearable.CHANNEL_EVENT")};
        ChannelListener zzas = new zzas(channelCallback);
        return zza(new zzat(zzas, token, intentFilterArr, zzb, zzcm.zzb(zzas, getLooper(), "ChannelListener")), new zzau(zzas, token, zzb.zzakx()));
    }

    public final Task<Void> registerChannelCallback(@NonNull ChannelCallback channelCallback) {
        zzbq.checkNotNull(channelCallback, "listener is null");
        zzci zzb = zzcm.zzb(channelCallback, getLooper(), "ChannelListener");
        IntentFilter[] intentFilterArr = new IntentFilter[]{zzgj.zzoe("com.google.android.gms.wearable.CHANNEL_EVENT")};
        ChannelListener zzas = new zzas(channelCallback);
        return zza(new zzat(zzas, null, intentFilterArr, zzb, zzcm.zzb(zzas, getLooper(), "ChannelListener")), new zzau(zzas, null, zzb.zzakx()));
    }

    public final Task<Void> sendFile(@NonNull ChannelClient.Channel channel, @NonNull Uri uri) {
        return zzbj.zzb(zza(channel).sendFile(zzahw(), uri));
    }

    public final Task<Void> sendFile(@NonNull ChannelClient.Channel channel, @NonNull Uri uri, long j, long j2) {
        return zzbj.zzb(zza(channel).sendFile(zzahw(), uri, j, j2));
    }

    public final Task<Boolean> unregisterChannelCallback(@NonNull ChannelClient.Channel channel, @NonNull ChannelCallback channelCallback) {
        String token = zza(channel).getToken();
        Looper looper = getLooper();
        String str = "ChannelListener:";
        token = String.valueOf(token);
        return zza(zzcm.zzb(channelCallback, looper, token.length() != 0 ? str.concat(token) : new String(str)).zzakx());
    }

    public final Task<Boolean> unregisterChannelCallback(@NonNull ChannelCallback channelCallback) {
        return zza(zzcm.zzb(channelCallback, getLooper(), "ChannelListener").zzakx());
    }
}
