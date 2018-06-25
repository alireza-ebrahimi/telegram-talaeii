package com.google.android.gms.wearable.internal;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.GoogleApi.zza;
import com.google.android.gms.common.internal.zzbj;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.NodeClient;
import java.util.List;

public final class zzfl extends NodeClient {
    private NodeApi zzlvc = new zzfg();

    public zzfl(@NonNull Activity activity, @NonNull zza zza) {
        super(activity, zza);
    }

    public zzfl(@NonNull Context context, @NonNull zza zza) {
        super(context, zza);
    }

    public final Task<List<Node>> getConnectedNodes() {
        return zzbj.zza(this.zzlvc.getConnectedNodes(zzahw()), zzfn.zzgui);
    }

    public final Task<Node> getLocalNode() {
        return zzbj.zza(this.zzlvc.getLocalNode(zzahw()), zzfm.zzgui);
    }
}
