package com.google.android.gms.internal.measurement;

import android.content.SharedPreferences.Editor;
import android.util.Pair;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;

public final class zzfw {
    private final long zzabj;
    private final /* synthetic */ zzfs zzakz;
    @VisibleForTesting
    private final String zzalb;
    private final String zzalc;
    private final String zzald;

    private zzfw(zzfs zzfs, String str, long j) {
        this.zzakz = zzfs;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkArgument(j > 0);
        this.zzalb = String.valueOf(str).concat(":start");
        this.zzalc = String.valueOf(str).concat(":count");
        this.zzald = String.valueOf(str).concat(":value");
        this.zzabj = j;
    }

    private final void zzfh() {
        this.zzakz.zzab();
        long currentTimeMillis = this.zzakz.zzbt().currentTimeMillis();
        Editor edit = this.zzakz.zzjf().edit();
        edit.remove(this.zzalc);
        edit.remove(this.zzald);
        edit.putLong(this.zzalb, currentTimeMillis);
        edit.apply();
    }

    private final long zzfj() {
        return this.zzakz.zzjf().getLong(this.zzalb, 0);
    }

    public final void zzc(String str, long j) {
        this.zzakz.zzab();
        if (zzfj() == 0) {
            zzfh();
        }
        if (str == null) {
            str = TtmlNode.ANONYMOUS_REGION_ID;
        }
        long j2 = this.zzakz.zzjf().getLong(this.zzalc, 0);
        if (j2 <= 0) {
            Editor edit = this.zzakz.zzjf().edit();
            edit.putString(this.zzald, str);
            edit.putLong(this.zzalc, 1);
            edit.apply();
            return;
        }
        Object obj = (this.zzakz.zzgc().zzll().nextLong() & Long.MAX_VALUE) < Long.MAX_VALUE / (j2 + 1) ? 1 : null;
        Editor edit2 = this.zzakz.zzjf().edit();
        if (obj != null) {
            edit2.putString(this.zzald, str);
        }
        edit2.putLong(this.zzalc, j2 + 1);
        edit2.apply();
    }

    public final Pair<String, Long> zzfi() {
        this.zzakz.zzab();
        this.zzakz.zzab();
        long zzfj = zzfj();
        if (zzfj == 0) {
            zzfh();
            zzfj = 0;
        } else {
            zzfj = Math.abs(zzfj - this.zzakz.zzbt().currentTimeMillis());
        }
        if (zzfj < this.zzabj) {
            return null;
        }
        if (zzfj > (this.zzabj << 1)) {
            zzfh();
            return null;
        }
        String string = this.zzakz.zzjf().getString(this.zzald, null);
        long j = this.zzakz.zzjf().getLong(this.zzalc, 0);
        zzfh();
        return (string == null || j <= 0) ? zzfs.zzakb : new Pair(string, Long.valueOf(j));
    }
}
