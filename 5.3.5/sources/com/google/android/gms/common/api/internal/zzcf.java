package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

public interface zzcf {
    void startActivityForResult(Intent intent, int i);

    <T extends LifecycleCallback> T zza(String str, Class<T> cls);

    void zza(String str, @NonNull LifecycleCallback lifecycleCallback);

    Activity zzakw();
}
