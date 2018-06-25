package com.google.firebase.iid;

import android.os.Bundle;
import com.google.android.gms.common.data.DataBufferSafeParcelable;

/* renamed from: com.google.firebase.iid.h */
final class C1937h extends C1934f<Bundle> {
    C1937h(int i, int i2, Bundle bundle) {
        super(i, 1, bundle);
    }

    /* renamed from: a */
    final void mo3055a(Bundle bundle) {
        Object bundle2 = bundle.getBundle(DataBufferSafeParcelable.DATA_FIELD);
        if (bundle2 == null) {
            bundle2 = Bundle.EMPTY;
        }
        m8843a(bundle2);
    }

    /* renamed from: a */
    final boolean mo3056a() {
        return false;
    }
}
