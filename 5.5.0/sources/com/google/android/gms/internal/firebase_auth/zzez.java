package com.google.android.gms.internal.firebase_auth;

import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

final class zzez extends zzey<FieldDescriptorType, Object> {
    zzez(int i) {
        super(i);
    }

    public final void zzbs() {
        if (!isImmutable()) {
            for (int i = 0; i < zzfo(); i++) {
                Entry zzau = zzau(i);
                if (((zzcu) zzau.getKey()).zzdv()) {
                    zzau.setValue(Collections.unmodifiableList((List) zzau.getValue()));
                }
            }
            for (Entry entry : zzfp()) {
                if (((zzcu) entry.getKey()).zzdv()) {
                    entry.setValue(Collections.unmodifiableList((List) entry.getValue()));
                }
            }
        }
        super.zzbs();
    }
}
