package com.google.android.gms.internal;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;

final class zzbgi extends ConstantState {
    int mChangingConfigurations;
    int zzgem;

    zzbgi(zzbgi zzbgi) {
        if (zzbgi != null) {
            this.mChangingConfigurations = zzbgi.mChangingConfigurations;
            this.zzgem = zzbgi.zzgem;
        }
    }

    public final int getChangingConfigurations() {
        return this.mChangingConfigurations;
    }

    public final Drawable newDrawable() {
        return new zzbge(this);
    }
}
