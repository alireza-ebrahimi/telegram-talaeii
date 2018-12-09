package com.google.firebase.auth.internal;

import android.content.Intent;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelableSerializer;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.google.firebase.auth.internal.i */
public final class C1869i {
    /* renamed from: a */
    private static String f5526a = "com.google.firebase.auth.internal.STATUS";
    /* renamed from: b */
    private static final Map<String, String> f5527b;

    static {
        Map hashMap = new HashMap();
        f5527b = hashMap;
        hashMap.put("auth/no-such-provider", "NO_SUCH_PROVIDER");
        f5527b.put("auth/invalid-cert-hash", "INVALID_CERT_HASH");
        f5527b.put("auth/network-request-failed", "WEB_NETWORK_REQUEST_FAILED");
        f5527b.put("auth/web-storage-unsupported", "WEB_STORAGE_UNSUPPORTED");
    }

    /* renamed from: a */
    public static void m8625a(Intent intent, Status status) {
        SafeParcelableSerializer.serializeToIntentExtra(status, intent, f5526a);
    }

    /* renamed from: a */
    public static boolean m8626a(Intent intent) {
        Preconditions.checkNotNull(intent);
        return intent.hasExtra(f5526a);
    }

    /* renamed from: b */
    public static Status m8627b(Intent intent) {
        Preconditions.checkNotNull(intent);
        Preconditions.checkArgument(C1869i.m8626a(intent));
        return (Status) SafeParcelableSerializer.deserializeFromIntentExtra(intent, f5526a, Status.CREATOR);
    }
}
