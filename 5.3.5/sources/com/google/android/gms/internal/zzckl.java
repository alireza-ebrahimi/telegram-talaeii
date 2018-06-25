package com.google.android.gms.internal;

import java.util.concurrent.Callable;

final class zzckl implements Callable<String> {
    private /* synthetic */ String zziuw;
    private /* synthetic */ zzckj zzjpd;

    zzckl(zzckj zzckj, String str) {
        this.zzjpd = zzckj;
        this.zziuw = str;
    }

    public final /* synthetic */ Object call() throws Exception {
        zzcie zzjj = this.zzjpd.zzayj().zzjj(this.zziuw);
        if (zzjj != null) {
            return zzjj.getAppInstanceId();
        }
        this.zzjpd.zzayp().zzbaw().log("App info was null when attempting to get app instance id");
        return null;
    }
}
