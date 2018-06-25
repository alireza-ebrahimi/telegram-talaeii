package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbq;

final class zzcit {
    final String name;
    final String zzcm;
    final long zzjhs;
    final long zzjht;
    final long zzjhu;
    final long zzjhv;
    final Long zzjhw;
    final Long zzjhx;
    final Boolean zzjhy;

    zzcit(String str, String str2, long j, long j2, long j3, long j4, Long l, Long l2, Boolean bool) {
        zzbq.zzgv(str);
        zzbq.zzgv(str2);
        zzbq.checkArgument(j >= 0);
        zzbq.checkArgument(j2 >= 0);
        zzbq.checkArgument(j4 >= 0);
        this.zzcm = str;
        this.name = str2;
        this.zzjhs = j;
        this.zzjht = j2;
        this.zzjhu = j3;
        this.zzjhv = j4;
        this.zzjhw = l;
        this.zzjhx = l2;
        this.zzjhy = bool;
    }

    final zzcit zza(Long l, Long l2, Boolean bool) {
        Boolean bool2 = (bool == null || bool.booleanValue()) ? bool : null;
        return new zzcit(this.zzcm, this.name, this.zzjhs, this.zzjht, this.zzjhu, this.zzjhv, l, l2, bool2);
    }

    final zzcit zzban() {
        return new zzcit(this.zzcm, this.name, this.zzjhs + 1, this.zzjht + 1, this.zzjhu, this.zzjhv, this.zzjhw, this.zzjhx, this.zzjhy);
    }

    final zzcit zzbb(long j) {
        return new zzcit(this.zzcm, this.name, this.zzjhs, this.zzjht, j, this.zzjhv, this.zzjhw, this.zzjhx, this.zzjhy);
    }

    final zzcit zzbc(long j) {
        return new zzcit(this.zzcm, this.name, this.zzjhs, this.zzjht, this.zzjhu, j, this.zzjhw, this.zzjhx, this.zzjhy);
    }
}
