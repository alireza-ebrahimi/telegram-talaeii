package com.google.android.gms.wearable;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApi.zza;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.Wearable.WearableOptions;
import java.util.List;

public abstract class NodeClient extends GoogleApi<WearableOptions> {
    @Hide
    public NodeClient(@NonNull Activity activity, @NonNull zza zza) {
        super(activity, Wearable.API, null, zza);
    }

    @Hide
    public NodeClient(@NonNull Context context, @NonNull zza zza) {
        super(context, Wearable.API, null, zza);
    }

    public abstract Task<List<Node>> getConnectedNodes();

    public abstract Task<Node> getLocalNode();
}
