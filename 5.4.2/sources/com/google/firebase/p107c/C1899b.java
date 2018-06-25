package com.google.firebase.p107c;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Objects;

@KeepForSdk
/* renamed from: com.google.firebase.c.b */
public class C1899b {
    /* renamed from: a */
    private String f5600a;

    @KeepForSdk
    public C1899b(String str) {
        this.f5600a = str;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof C1899b)) {
            return false;
        }
        return Objects.equal(this.f5600a, ((C1899b) obj).f5600a);
    }

    public int hashCode() {
        return Objects.hashCode(this.f5600a);
    }

    public String toString() {
        return Objects.toStringHelper(this).add("token", this.f5600a).toString();
    }
}
