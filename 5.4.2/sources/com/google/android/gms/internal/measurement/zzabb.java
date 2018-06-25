package com.google.android.gms.internal.measurement;

import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

final class zzabb extends zzaba<FieldDescriptorType, Object> {
    zzabb(int i) {
        super(i);
    }

    public final void zzrp() {
        if (!isImmutable()) {
            for (int i = 0; i < zzus(); i++) {
                Entry zzah = zzah(i);
                if (((zzzq) zzah.getKey()).zztt()) {
                    zzah.setValue(Collections.unmodifiableList((List) zzah.getValue()));
                }
            }
            for (Entry entry : zzut()) {
                if (((zzzq) entry.getKey()).zztt()) {
                    entry.setValue(Collections.unmodifiableList((List) entry.getValue()));
                }
            }
        }
        super.zzrp();
    }
}
