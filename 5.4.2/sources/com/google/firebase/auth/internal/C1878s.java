package com.google.firebase.auth.internal;

import android.content.Context;
import com.google.android.gms.flags.Flag;
import com.google.android.gms.flags.FlagRegistry;
import com.google.android.gms.flags.Singletons;

/* renamed from: com.google.firebase.auth.internal.s */
public final class C1878s {
    /* renamed from: a */
    public static final Flag<Boolean> f5539a = Flag.define(0, "firebase_auth_proactive_token_refresh_enabled", Boolean.valueOf(true));

    /* renamed from: a */
    public static final void m8632a(Context context) {
        Singletons.flagRegistry();
        FlagRegistry.initialize(context);
    }
}
