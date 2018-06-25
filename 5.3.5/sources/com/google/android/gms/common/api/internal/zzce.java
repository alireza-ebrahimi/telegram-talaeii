package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.common.internal.zzbq;

public final class zzce {
    private final Object zzgal;

    public zzce(Activity activity) {
        zzbq.checkNotNull(activity, "Activity must not be null");
        this.zzgal = activity;
    }

    public final boolean zzaks() {
        return this.zzgal instanceof FragmentActivity;
    }

    public final boolean zzakt() {
        return this.zzgal instanceof Activity;
    }

    public final Activity zzaku() {
        return (Activity) this.zzgal;
    }

    public final FragmentActivity zzakv() {
        return (FragmentActivity) this.zzgal;
    }
}
