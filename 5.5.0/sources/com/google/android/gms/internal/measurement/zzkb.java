package com.google.android.gms.internal.measurement;

import com.google.android.gms.common.internal.Preconditions;

final class zzkb {
    final String name;
    final String origin;
    final Object value;
    final long zzarl;
    final String zzti;

    zzkb(String str, String str2, String str3, long j, Object obj) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str3);
        Preconditions.checkNotNull(obj);
        this.zzti = str;
        this.origin = str2;
        this.name = str3;
        this.zzarl = j;
        this.value = obj;
    }
}
