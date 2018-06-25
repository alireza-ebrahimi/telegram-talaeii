package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import android.net.Uri;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageApi.MessageListener;
import com.google.android.gms.wearable.MessageApi.SendMessageResult;

public final class zzeu implements MessageApi {
    private static PendingResult<Status> zza(GoogleApiClient googleApiClient, MessageListener messageListener, IntentFilter[] intentFilterArr) {
        return googleApiClient.enqueue(new zzex(googleApiClient, messageListener, googleApiClient.registerListener(messageListener), intentFilterArr, null));
    }

    public final PendingResult<Status> addListener(GoogleApiClient googleApiClient, MessageListener messageListener) {
        return zza(googleApiClient, messageListener, new IntentFilter[]{zzgj.zzc("com.google.android.gms.wearable.MESSAGE_RECEIVED")});
    }

    public final PendingResult<Status> addListener(GoogleApiClient googleApiClient, MessageListener messageListener, Uri uri, int i) {
        Preconditions.checkNotNull(uri, "uri must not be null");
        boolean z = i == 0 || i == 1;
        Preconditions.checkArgument(z, "invalid filter type");
        return zza(googleApiClient, messageListener, new IntentFilter[]{zzgj.zza("com.google.android.gms.wearable.MESSAGE_RECEIVED", uri, i)});
    }

    public final PendingResult<Status> removeListener(GoogleApiClient googleApiClient, MessageListener messageListener) {
        return googleApiClient.enqueue(new zzew(this, googleApiClient, messageListener));
    }

    public final PendingResult<SendMessageResult> sendMessage(GoogleApiClient googleApiClient, String str, String str2, byte[] bArr) {
        return googleApiClient.enqueue(new zzev(this, googleApiClient, str, str2, bArr));
    }
}
