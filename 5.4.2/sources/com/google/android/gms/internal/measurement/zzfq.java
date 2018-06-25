package com.google.android.gms.internal.measurement;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;

class zzfq extends BroadcastReceiver {
    @VisibleForTesting
    private static final String zzaaw = zzfq.class.getName();
    private boolean zzaax;
    private boolean zzaay;
    private final zzjs zzajy;

    zzfq(zzjs zzjs) {
        Preconditions.checkNotNull(zzjs);
        this.zzajy = zzjs;
    }

    public void onReceive(Context context, Intent intent) {
        this.zzajy.zzkz();
        String action = intent.getAction();
        this.zzajy.zzgf().zziz().zzg("NetworkBroadcastReceiver received action", action);
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(action)) {
            boolean zzex = this.zzajy.zzkw().zzex();
            if (this.zzaay != zzex) {
                this.zzaay = zzex;
                this.zzajy.zzge().zzc(new zzfr(this, zzex));
                return;
            }
            return;
        }
        this.zzajy.zzgf().zziv().zzg("NetworkBroadcastReceiver received unknown action", action);
    }

    public final void unregister() {
        this.zzajy.zzkz();
        this.zzajy.zzge().zzab();
        this.zzajy.zzge().zzab();
        if (this.zzaax) {
            this.zzajy.zzgf().zziz().log("Unregistering connectivity change receiver");
            this.zzaax = false;
            this.zzaay = false;
            try {
                this.zzajy.getContext().unregisterReceiver(this);
            } catch (IllegalArgumentException e) {
                this.zzajy.zzgf().zzis().zzg("Failed to unregister the network broadcast receiver", e);
            }
        }
    }

    public final void zzeu() {
        this.zzajy.zzkz();
        this.zzajy.zzge().zzab();
        if (!this.zzaax) {
            this.zzajy.getContext().registerReceiver(this, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
            this.zzaay = this.zzajy.zzkw().zzex();
            this.zzajy.zzgf().zziz().zzg("Registering connectivity change receiver. Network connected", Boolean.valueOf(this.zzaay));
            this.zzaax = true;
        }
    }
}
