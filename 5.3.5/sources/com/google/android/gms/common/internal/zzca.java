package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.res.Resources;
import com.google.android.gms.C0489R;

@Hide
public final class zzca {
    private final Resources zzgid;
    private final String zzgie = this.zzgid.getResourcePackageName(C0489R.string.common_google_play_services_unknown_issue);

    public zzca(Context context) {
        zzbq.checkNotNull(context);
        this.zzgid = context.getResources();
    }

    public final String getString(String str) {
        int identifier = this.zzgid.getIdentifier(str, "string", this.zzgie);
        return identifier == 0 ? null : this.zzgid.getString(identifier);
    }
}
