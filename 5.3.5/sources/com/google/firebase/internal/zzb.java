package com.google.firebase.internal;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public final class zzb {
    private static final AtomicReference<zzb> zzmmz = new AtomicReference();

    private zzb(Context context) {
    }

    @Nullable
    public static zzb zzclx() {
        return (zzb) zzmmz.get();
    }

    public static Set<String> zzcly() {
        return Collections.emptySet();
    }

    public static zzb zzfb(Context context) {
        zzmmz.compareAndSet(null, new zzb(context));
        return (zzb) zzmmz.get();
    }

    public static void zzg(@NonNull FirebaseApp firebaseApp) {
    }

    public static FirebaseOptions zzrw(@NonNull String str) {
        return null;
    }
}
