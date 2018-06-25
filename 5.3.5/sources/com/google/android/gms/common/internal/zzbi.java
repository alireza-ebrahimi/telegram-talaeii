package com.google.android.gms.common.internal;

import java.util.ArrayList;
import java.util.List;

public final class zzbi {
    private final Object zzdht;
    private final List<String> zzgho;

    private zzbi(Object obj) {
        this.zzdht = zzbq.checkNotNull(obj);
        this.zzgho = new ArrayList();
    }

    public final String toString() {
        StringBuilder append = new StringBuilder(100).append(this.zzdht.getClass().getSimpleName()).append('{');
        int size = this.zzgho.size();
        for (int i = 0; i < size; i++) {
            append.append((String) this.zzgho.get(i));
            if (i < size - 1) {
                append.append(", ");
            }
        }
        return append.append('}').toString();
    }

    public final zzbi zzg(String str, Object obj) {
        List list = this.zzgho;
        String str2 = (String) zzbq.checkNotNull(str);
        String valueOf = String.valueOf(obj);
        list.add(new StringBuilder((String.valueOf(str2).length() + 1) + String.valueOf(valueOf).length()).append(str2).append("=").append(valueOf).toString());
        return this;
    }
}
