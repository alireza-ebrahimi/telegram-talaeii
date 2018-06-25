package com.google.android.gms.common.data;

import com.google.android.gms.common.data.TextFilterable.StringFilter;

final class zzd implements StringFilter {
    zzd() {
    }

    public final boolean matches(String str, String str2) {
        return str.startsWith(str2);
    }
}
