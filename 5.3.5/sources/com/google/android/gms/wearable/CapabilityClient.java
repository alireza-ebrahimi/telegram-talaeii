package com.google.android.gms.wearable;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApi.zza;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.CapabilityApi.CapabilityListener;
import com.google.android.gms.wearable.Wearable.WearableOptions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;

public abstract class CapabilityClient extends GoogleApi<WearableOptions> {
    public static final String ACTION_CAPABILITY_CHANGED = "com.google.android.gms.wearable.CAPABILITY_CHANGED";
    public static final int FILTER_ALL = 0;
    public static final int FILTER_LITERAL = 0;
    public static final int FILTER_PREFIX = 1;
    public static final int FILTER_REACHABLE = 1;

    @Retention(RetentionPolicy.SOURCE)
    public @interface CapabilityFilterType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface NodeFilterType {
    }

    public interface OnCapabilityChangedListener extends CapabilityListener {
        void onCapabilityChanged(@NonNull CapabilityInfo capabilityInfo);
    }

    @Hide
    public CapabilityClient(@NonNull Activity activity, @NonNull zza zza) {
        super(activity, Wearable.API, null, zza);
    }

    @Hide
    public CapabilityClient(@NonNull Context context, @NonNull zza zza) {
        super(context, Wearable.API, null, zza);
    }

    public abstract Task<Void> addListener(@NonNull OnCapabilityChangedListener onCapabilityChangedListener, @NonNull Uri uri, int i);

    public abstract Task<Void> addListener(@NonNull OnCapabilityChangedListener onCapabilityChangedListener, @NonNull String str);

    public abstract Task<Void> addLocalCapability(@NonNull String str);

    public abstract Task<Map<String, CapabilityInfo>> getAllCapabilities(int i);

    public abstract Task<CapabilityInfo> getCapability(@NonNull String str, int i);

    public abstract Task<Boolean> removeListener(@NonNull OnCapabilityChangedListener onCapabilityChangedListener);

    public abstract Task<Boolean> removeListener(@NonNull OnCapabilityChangedListener onCapabilityChangedListener, @NonNull String str);

    public abstract Task<Void> removeLocalCapability(@NonNull String str);
}
