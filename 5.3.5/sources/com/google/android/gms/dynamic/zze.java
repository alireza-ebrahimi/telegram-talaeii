package com.google.android.gms.dynamic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;

final class zze implements zzi {
    private /* synthetic */ ViewGroup val$container;
    private /* synthetic */ Bundle zzaik;
    private /* synthetic */ zza zzhct;
    private /* synthetic */ FrameLayout zzhcv;
    private /* synthetic */ LayoutInflater zzhcw;

    zze(zza zza, FrameLayout frameLayout, LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.zzhct = zza;
        this.zzhcv = frameLayout;
        this.zzhcw = layoutInflater;
        this.val$container = viewGroup;
        this.zzaik = bundle;
    }

    public final int getState() {
        return 2;
    }

    public final void zzb(LifecycleDelegate lifecycleDelegate) {
        this.zzhcv.removeAllViews();
        this.zzhcv.addView(this.zzhct.zzhcp.onCreateView(this.zzhcw, this.val$container, this.zzaik));
    }
}
