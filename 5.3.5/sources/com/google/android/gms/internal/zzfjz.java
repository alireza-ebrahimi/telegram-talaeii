package com.google.android.gms.internal;

import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

final class zzfjz extends zzfjy<FieldDescriptorType, Object> {
    zzfjz(int i) {
        super(i);
    }

    public final void zzbkr() {
        if (!isImmutable()) {
            for (int i = 0; i < zzdbp(); i++) {
                Entry zzmr = zzmr(i);
                if (((zzfhs) zzmr.getKey()).zzczn()) {
                    zzmr.setValue(Collections.unmodifiableList((List) zzmr.getValue()));
                }
            }
            for (Entry entry : zzdbq()) {
                if (((zzfhs) entry.getKey()).zzczn()) {
                    entry.setValue(Collections.unmodifiableList((List) entry.getValue()));
                }
            }
        }
        super.zzbkr();
    }
}
