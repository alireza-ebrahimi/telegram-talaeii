package com.google.android.gms.common;

import com.google.android.gms.common.util.zza;
import com.google.android.gms.common.util.zzm;
import io.fabric.sdk.android.services.common.CommonUtils;

final class zzr extends zzp {
    private final String packageName;
    private final zzh zzfro;
    private final boolean zzfrp;
    private final boolean zzfrq;

    private zzr(String str, zzh zzh, boolean z, boolean z2) {
        super(false, null, null);
        this.packageName = str;
        this.zzfro = zzh;
        this.zzfrp = z;
        this.zzfrq = z2;
    }

    final String getErrorMessage() {
        String str = this.zzfrq ? "debug cert rejected" : "not whitelisted";
        String str2 = this.packageName;
        String zzn = zzm.zzn(zza.zzeq(CommonUtils.SHA1_INSTANCE).digest(this.zzfro.getBytes()));
        return new StringBuilder(((String.valueOf(str).length() + 44) + String.valueOf(str2).length()) + String.valueOf(zzn).length()).append(str).append(": pkg=").append(str2).append(", sha1=").append(zzn).append(", atk=").append(this.zzfrp).append(", ver=12211278.false").toString();
    }
}
