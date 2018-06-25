package com.google.android.gms.common;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Hide;

@Hide
@KeepForSdk
final class ProGuardCanary {
    @KeepForSdk
    static final String CANARY = "gms_proguard_canary";

    private ProGuardCanary() {
    }
}
