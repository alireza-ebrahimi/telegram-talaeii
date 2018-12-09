package com.google.firebase.components;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;

@KeepForSdk
/* renamed from: com.google.firebase.components.f */
public final class C1905f {
    /* renamed from: a */
    private final Class<?> f5611a;
    /* renamed from: b */
    private final int f5612b;
    /* renamed from: c */
    private final int f5613c;

    private C1905f(Class<?> cls, int i, int i2) {
        this.f5611a = (Class) Preconditions.checkNotNull(cls, "Null dependency anInterface.");
        this.f5612b = i;
        this.f5613c = i2;
    }

    @KeepForSdk
    /* renamed from: a */
    public static C1905f m8718a(Class<?> cls) {
        return new C1905f(cls, 1, 0);
    }

    /* renamed from: a */
    public final Class<?> m8719a() {
        return this.f5611a;
    }

    /* renamed from: b */
    public final boolean m8720b() {
        return this.f5612b == 1;
    }

    /* renamed from: c */
    public final boolean m8721c() {
        return this.f5613c == 0;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof C1905f)) {
            return false;
        }
        C1905f c1905f = (C1905f) obj;
        return this.f5611a == c1905f.f5611a && this.f5612b == c1905f.f5612b && this.f5613c == c1905f.f5613c;
    }

    public final int hashCode() {
        return ((((this.f5611a.hashCode() ^ 1000003) * 1000003) ^ this.f5612b) * 1000003) ^ this.f5613c;
    }

    public final String toString() {
        boolean z = true;
        StringBuilder append = new StringBuilder("Dependency{anInterface=").append(this.f5611a).append(", required=").append(this.f5612b == 1).append(", direct=");
        if (this.f5613c != 0) {
            z = false;
        }
        return append.append(z).append("}").toString();
    }
}
