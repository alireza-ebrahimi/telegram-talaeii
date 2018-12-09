package com.google.android.gms.internal.measurement;

import com.google.android.gms.common.internal.Preconditions;

final class zzes {
    final String name;
    final long zzafs;
    final long zzaft;
    final long zzafu;
    final long zzafv;
    final Long zzafw;
    final Long zzafx;
    final Boolean zzafy;
    final String zzti;

    zzes(String str, String str2, long j, long j2, long j3, long j4, Long l, Long l2, Boolean bool) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        Preconditions.checkArgument(j >= 0);
        Preconditions.checkArgument(j2 >= 0);
        Preconditions.checkArgument(j4 >= 0);
        this.zzti = str;
        this.name = str2;
        this.zzafs = j;
        this.zzaft = j2;
        this.zzafu = j3;
        this.zzafv = j4;
        this.zzafw = l;
        this.zzafx = l2;
        this.zzafy = bool;
    }

    final zzes zza(Long l, Long l2, Boolean bool) {
        Boolean bool2 = (bool == null || bool.booleanValue()) ? bool : null;
        return new zzes(this.zzti, this.name, this.zzafs, this.zzaft, this.zzafu, this.zzafv, l, l2, bool2);
    }

    final zzes zzac(long j) {
        return new zzes(this.zzti, this.name, this.zzafs, this.zzaft, j, this.zzafv, this.zzafw, this.zzafx, this.zzafy);
    }

    final zzes zzad(long j) {
        return new zzes(this.zzti, this.name, this.zzafs, this.zzaft, this.zzafu, j, this.zzafw, this.zzafx, this.zzafy);
    }

    final zzes zzii() {
        return new zzes(this.zzti, this.name, this.zzafs + 1, this.zzaft + 1, this.zzafu, this.zzafv, this.zzafw, this.zzafx, this.zzafy);
    }
}
