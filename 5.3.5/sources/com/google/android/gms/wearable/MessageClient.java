package com.google.android.gms.wearable;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApi.zza;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.MessageApi.MessageListener;
import com.google.android.gms.wearable.Wearable.WearableOptions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class MessageClient extends GoogleApi<WearableOptions> {
    public static final String ACTION_MESSAGE_RECEIVED = "com.google.android.gms.wearable.MESSAGE_RECEIVED";
    public static final int FILTER_LITERAL = 0;
    public static final int FILTER_PREFIX = 1;

    @Retention(RetentionPolicy.SOURCE)
    public @interface FilterType {
    }

    public interface OnMessageReceivedListener extends MessageListener {
        void onMessageReceived(@NonNull MessageEvent messageEvent);
    }

    @Hide
    public MessageClient(@NonNull Activity activity, @NonNull zza zza) {
        super(activity, Wearable.API, null, zza);
    }

    @Hide
    public MessageClient(@NonNull Context context, @NonNull zza zza) {
        super(context, Wearable.API, null, zza);
    }

    public abstract Task<Void> addListener(@NonNull OnMessageReceivedListener onMessageReceivedListener);

    public abstract Task<Void> addListener(@NonNull OnMessageReceivedListener onMessageReceivedListener, @NonNull Uri uri, int i);

    public abstract Task<Boolean> removeListener(@NonNull OnMessageReceivedListener onMessageReceivedListener);

    public abstract Task<Integer> sendMessage(@NonNull String str, @NonNull String str2, @Nullable byte[] bArr);
}
