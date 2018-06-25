package com.google.android.gms.wearable.internal;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.util.Preconditions;
import com.google.android.gms.common.api.GoogleApi.zza;
import com.google.android.gms.common.api.internal.zzci;
import com.google.android.gms.common.api.internal.zzcm;
import com.google.android.gms.common.internal.zzbj;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageClient;
import com.google.android.gms.wearable.MessageClient.OnMessageReceivedListener;

public final class zzez extends MessageClient {
    private MessageApi zzluy = new zzeu();

    public zzez(@NonNull Activity activity, @NonNull zza zza) {
        super(activity, zza);
    }

    public zzez(@NonNull Context context, @NonNull zza zza) {
        super(context, zza);
    }

    private final Task<Void> zza(OnMessageReceivedListener onMessageReceivedListener, IntentFilter[] intentFilterArr) {
        zzci zzb = zzcm.zzb(onMessageReceivedListener, getLooper(), "MessageListener");
        return zza(new zzfc(onMessageReceivedListener, intentFilterArr, zzb, null), new zzfd(onMessageReceivedListener, zzb.zzakx(), null));
    }

    public final Task<Void> addListener(OnMessageReceivedListener onMessageReceivedListener) {
        return zza(onMessageReceivedListener, new IntentFilter[]{zzgj.zzoe("com.google.android.gms.wearable.MESSAGE_RECEIVED")});
    }

    public final Task<Void> addListener(OnMessageReceivedListener onMessageReceivedListener, Uri uri, int i) {
        Preconditions.checkNotNull(uri, "uri must not be null");
        boolean z = i == 0 || i == 1;
        zzbq.checkArgument(z, "invalid filter type");
        return zza(onMessageReceivedListener, new IntentFilter[]{zzgj.zza("com.google.android.gms.wearable.MESSAGE_RECEIVED", uri, i)});
    }

    public final Task<Boolean> removeListener(@NonNull OnMessageReceivedListener onMessageReceivedListener) {
        return zza(zzcm.zzb(onMessageReceivedListener, getLooper(), "MessageListener").zzakx());
    }

    public final Task<Integer> sendMessage(String str, String str2, byte[] bArr) {
        return zzbj.zza(this.zzluy.sendMessage(zzahw(), str, str2, bArr), zzfa.zzgui);
    }
}
