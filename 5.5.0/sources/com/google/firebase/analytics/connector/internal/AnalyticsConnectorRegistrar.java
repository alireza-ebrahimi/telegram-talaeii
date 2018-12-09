package com.google.firebase.analytics.connector.internal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Keep;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.firebase.C1897b;
import com.google.firebase.analytics.connector.C1814a;
import com.google.firebase.components.C1816e;
import com.google.firebase.components.C1902a;
import com.google.firebase.components.C1905f;
import java.util.Collections;
import java.util.List;

@Keep
@KeepForSdk
public class AnalyticsConnectorRegistrar implements C1816e {
    @Keep
    @SuppressLint({"MissingPermission"})
    @KeepForSdk
    public List<C1902a<?>> getComponents() {
        return Collections.singletonList(C1902a.m8705a(C1814a.class).m8703a(C1905f.m8718a(C1897b.class)).m8703a(C1905f.m8718a(Context.class)).m8702a(C1818a.f5417a).m8704b());
    }
}
