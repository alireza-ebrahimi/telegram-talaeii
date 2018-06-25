package com.google.android.gms.dynamic;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

final class zze implements OnClickListener {
    private final /* synthetic */ Context val$context;
    private final /* synthetic */ Intent zzabl;

    zze(Context context, Intent intent) {
        this.val$context = context;
        this.zzabl = intent;
    }

    public final void onClick(View view) {
        try {
            this.val$context.startActivity(this.zzabl);
        } catch (Throwable e) {
            Log.e("DeferredLifecycleHelper", "Failed to start resolution intent", e);
        }
    }
}
