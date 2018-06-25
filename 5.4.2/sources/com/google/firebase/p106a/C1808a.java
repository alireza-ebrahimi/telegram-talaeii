package com.google.firebase.p106a;

import com.google.android.gms.common.annotation.KeepForSdk;

@KeepForSdk
/* renamed from: com.google.firebase.a.a */
public class C1808a<T> {
    /* renamed from: a */
    private final Class<T> f5412a;
    /* renamed from: b */
    private final T f5413b;

    @KeepForSdk
    /* renamed from: a */
    public Class<T> m8467a() {
        return this.f5412a;
    }

    public String toString() {
        return String.format("Event{type: %s, payload: %s}", new Object[]{this.f5412a, this.f5413b});
    }
}
