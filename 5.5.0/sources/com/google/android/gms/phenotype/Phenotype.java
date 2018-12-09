package com.google.android.gms.phenotype;

import android.net.Uri;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.AbstractClientBuilder;
import com.google.android.gms.common.api.Api.ApiOptions.NoOptions;
import com.google.android.gms.common.api.Api.ClientKey;
import com.google.android.gms.internal.phenotype.zzd;
import com.google.android.gms.internal.phenotype.zze;

@KeepForSdk
public final class Phenotype {
    @Deprecated
    private static final Api<NoOptions> API = new Api("Phenotype.API", CLIENT_BUILDER, CLIENT_KEY);
    private static final AbstractClientBuilder<zze, NoOptions> CLIENT_BUILDER = new zzl();
    private static final ClientKey<zze> CLIENT_KEY = new ClientKey();
    @Deprecated
    private static final zzm zzaj = new zzd();

    private Phenotype() {
    }

    @KeepForSdk
    public static Uri getContentProviderUri(String str) {
        String str2 = "content://com.google.android.gms.phenotype/";
        String valueOf = String.valueOf(Uri.encode(str));
        return Uri.parse(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
    }
}
