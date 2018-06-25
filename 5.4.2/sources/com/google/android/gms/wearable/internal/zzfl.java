package com.google.android.gms.wearable.internal;

import android.app.Activity;
import android.content.Context;
import com.google.android.gms.common.api.GoogleApi.Settings;
import com.google.android.gms.common.internal.PendingResultUtil;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.NodeClient;
import java.util.List;

public final class zzfl extends NodeClient {
    private final NodeApi zzem = new zzfg();

    public zzfl(Activity activity, Settings settings) {
        super(activity, settings);
    }

    public zzfl(Context context, Settings settings) {
        super(context, settings);
    }

    public final Task<List<Node>> getConnectedNodes() {
        return PendingResultUtil.toTask(this.zzem.getConnectedNodes(asGoogleApiClient()), zzfn.zzbx);
    }

    public final Task<Node> getLocalNode() {
        return PendingResultUtil.toTask(this.zzem.getLocalNode(asGoogleApiClient()), zzfm.zzbx);
    }
}
