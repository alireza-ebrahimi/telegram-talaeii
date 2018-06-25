package com.google.firebase.iid;

import android.util.Base64;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.util.VisibleForTesting;
import java.security.KeyPair;

final class an {
    /* renamed from: a */
    private final KeyPair f5700a;
    /* renamed from: b */
    private final long f5701b;

    @VisibleForTesting
    an(KeyPair keyPair, long j) {
        this.f5700a = keyPair;
        this.f5701b = j;
    }

    /* renamed from: b */
    private final String m8821b() {
        return Base64.encodeToString(this.f5700a.getPublic().getEncoded(), 11);
    }

    /* renamed from: c */
    private final String m8824c() {
        return Base64.encodeToString(this.f5700a.getPrivate().getEncoded(), 11);
    }

    /* renamed from: a */
    final KeyPair m8825a() {
        return this.f5700a;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof an)) {
            return false;
        }
        an anVar = (an) obj;
        return this.f5701b == anVar.f5701b && this.f5700a.getPublic().equals(anVar.f5700a.getPublic()) && this.f5700a.getPrivate().equals(anVar.f5700a.getPrivate());
    }

    public final int hashCode() {
        return Objects.hashCode(this.f5700a.getPublic(), this.f5700a.getPrivate(), Long.valueOf(this.f5701b));
    }
}
