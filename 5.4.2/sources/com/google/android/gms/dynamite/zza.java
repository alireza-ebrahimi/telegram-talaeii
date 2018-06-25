package com.google.android.gms.dynamite;

import android.content.Context;
import com.google.android.gms.dynamite.DynamiteModule.VersionPolicy.IVersions;

final class zza implements IVersions {
    zza() {
    }

    public final int getLocalVersion(Context context, String str) {
        return DynamiteModule.getLocalVersion(context, str);
    }

    public final int getRemoteVersion(Context context, String str, boolean z) {
        return DynamiteModule.getRemoteVersion(context, str, z);
    }
}
