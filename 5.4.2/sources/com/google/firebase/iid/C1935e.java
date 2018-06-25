package com.google.firebase.iid;

import android.os.Bundle;

/* renamed from: com.google.firebase.iid.e */
final class C1935e extends C1934f<Void> {
    C1935e(int i, int i2, Bundle bundle) {
        super(i, 2, bundle);
    }

    /* renamed from: a */
    final void mo3055a(Bundle bundle) {
        if (bundle.getBoolean("ack", false)) {
            m8843a(null);
        } else {
            m8842a(new C1936g(4, "Invalid response to one way request"));
        }
    }

    /* renamed from: a */
    final boolean mo3056a() {
        return true;
    }
}
