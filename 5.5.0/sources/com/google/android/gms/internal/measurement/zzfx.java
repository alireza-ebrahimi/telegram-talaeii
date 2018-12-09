package com.google.android.gms.internal.measurement;

import android.content.SharedPreferences.Editor;
import com.google.android.gms.common.internal.Preconditions;

public final class zzfx {
    private String value;
    private boolean zzaky;
    private final /* synthetic */ zzfs zzakz;
    private final String zzale = null;
    private final String zzny;

    public zzfx(zzfs zzfs, String str, String str2) {
        this.zzakz = zzfs;
        Preconditions.checkNotEmpty(str);
        this.zzny = str;
    }

    public final void zzbr(String str) {
        if (!zzkc.zzs(str, this.value)) {
            Editor edit = this.zzakz.zzjf().edit();
            edit.putString(this.zzny, str);
            edit.apply();
            this.value = str;
        }
    }

    public final String zzjn() {
        if (!this.zzaky) {
            this.zzaky = true;
            this.value = this.zzakz.zzjf().getString(this.zzny, null);
        }
        return this.value;
    }
}
