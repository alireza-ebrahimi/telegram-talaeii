package com.google.android.gms.wearable.internal;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v4.util.Preconditions;
import com.google.android.gms.common.api.GoogleApi.Settings;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.ListenerHolders;
import com.google.android.gms.common.internal.PendingResultUtil;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageClient;
import com.google.android.gms.wearable.MessageClient.OnMessageReceivedListener;

public final class zzez extends MessageClient {
    @VisibleForTesting
    private final MessageApi zzei = new zzeu();

    public zzez(Activity activity, Settings settings) {
        super(activity, settings);
    }

    public zzez(Context context, Settings settings) {
        super(context, settings);
    }

    private final Task<Void> zza(OnMessageReceivedListener onMessageReceivedListener, IntentFilter[] intentFilterArr) {
        ListenerHolder createListenerHolder = ListenerHolders.createListenerHolder(onMessageReceivedListener, getLooper(), "MessageListener");
        return doRegisterEventListener(new zzfc(onMessageReceivedListener, intentFilterArr, createListenerHolder, null), new zzfd(onMessageReceivedListener, createListenerHolder.getListenerKey(), null));
    }

    public final Task<Void> addListener(OnMessageReceivedListener onMessageReceivedListener) {
        return zza(onMessageReceivedListener, new IntentFilter[]{zzgj.zzc("com.google.android.gms.wearable.MESSAGE_RECEIVED")});
    }

    public final Task<Void> addListener(OnMessageReceivedListener onMessageReceivedListener, Uri uri, int i) {
        Preconditions.checkNotNull(uri, "uri must not be null");
        boolean z = i == 0 || i == 1;
        com.google.android.gms.common.internal.Preconditions.checkArgument(z, "invalid filter type");
        return zza(onMessageReceivedListener, new IntentFilter[]{zzgj.zza("com.google.android.gms.wearable.MESSAGE_RECEIVED", uri, i)});
    }

    public final Task<Boolean> removeListener(OnMessageReceivedListener onMessageReceivedListener) {
        return doUnregisterEventListener(ListenerHolders.createListenerHolder(onMessageReceivedListener, getLooper(), "MessageListener").getListenerKey());
    }

    public final Task<Integer> sendMessage(String str, String str2, byte[] bArr) {
        return PendingResultUtil.toTask(this.zzei.sendMessage(asGoogleApiClient(), str, str2, bArr), zzfa.zzbx);
    }
}
