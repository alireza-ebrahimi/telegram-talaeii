package com.google.android.gms.internal;

import android.text.TextUtils;

public final class zzl {
    private final String mName;
    private final String mValue;

    public zzl(String str, String str2) {
        this.mName = str;
        this.mValue = str2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        zzl zzl = (zzl) obj;
        return TextUtils.equals(this.mName, zzl.mName) && TextUtils.equals(this.mValue, zzl.mValue);
    }

    public final String getName() {
        return this.mName;
    }

    public final String getValue() {
        return this.mValue;
    }

    public final int hashCode() {
        return (this.mName.hashCode() * 31) + this.mValue.hashCode();
    }

    public final String toString() {
        String str = this.mName;
        String str2 = this.mValue;
        return new StringBuilder((String.valueOf(str).length() + 20) + String.valueOf(str2).length()).append("Header[name=").append(str).append(",value=").append(str2).append("]").toString();
    }
}
