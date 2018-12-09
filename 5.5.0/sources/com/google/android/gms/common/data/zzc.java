package com.google.android.gms.common.data;

import com.google.android.gms.common.data.TextFilterable.StringFilter;

final class zzc implements StringFilter {
    zzc() {
    }

    public final boolean matches(String str, String str2) {
        return str.contains(str2);
    }
}
