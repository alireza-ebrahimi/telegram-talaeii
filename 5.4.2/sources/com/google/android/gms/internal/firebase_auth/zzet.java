package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.internal.firebase_auth.zzdb.zze;

final class zzet implements zzef {
    private final int flags;
    private final String info;
    private final Object[] zztl;
    private final zzeh zzto;

    zzet(zzeh zzeh, String str, Object[] objArr) {
        this.zzto = zzeh;
        this.info = str;
        this.zztl = objArr;
        int i = 1;
        char charAt = str.charAt(0);
        if (charAt < '?') {
            this.flags = charAt;
            return;
        }
        int i2 = charAt & 8191;
        int i3 = 13;
        while (true) {
            int i4 = i + 1;
            char charAt2 = str.charAt(i);
            if (charAt2 >= '?') {
                i2 |= (charAt2 & 8191) << i3;
                i3 += 13;
                i = i4;
            } else {
                this.flags = (charAt2 << i3) | i2;
                return;
            }
        }
    }

    public final int zzez() {
        return (this.flags & 1) == 1 ? zze.zzrm : zze.zzrn;
    }

    public final boolean zzfa() {
        return (this.flags & 2) == 2;
    }

    public final zzeh zzfb() {
        return this.zzto;
    }

    final String zzfh() {
        return this.info;
    }

    final Object[] zzfi() {
        return this.zztl;
    }
}
