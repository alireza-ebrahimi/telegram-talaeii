package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.common.internal.Preconditions;
import com.google.firebase.auth.ActionCodeSettings;

public final class zzan {
    private String zzaf;
    private String zzah;
    private ActionCodeSettings zzhb;
    private String zzjw;

    public zzan(int i) {
        String str;
        switch (i) {
            case 1:
                str = "PASSWORD_RESET";
                break;
            case 4:
                str = "VERIFY_EMAIL";
                break;
            case 6:
                str = "EMAIL_SIGNIN";
                break;
            default:
                str = "REQUEST_TYPE_UNSET_ENUM_VALUE";
                break;
        }
        this.zzjw = str;
    }

    public final zzan zza(ActionCodeSettings actionCodeSettings) {
        this.zzhb = (ActionCodeSettings) Preconditions.checkNotNull(actionCodeSettings);
        return this;
    }

    public final /* synthetic */ zzgt zzao() {
        int i = 1;
        zzgt zzl = new zzl();
        String str = this.zzjw;
        int i2 = -1;
        switch (str.hashCode()) {
            case -1452371317:
                if (str.equals("PASSWORD_RESET")) {
                    i2 = 0;
                    break;
                }
                break;
            case -1341836234:
                if (str.equals("VERIFY_EMAIL")) {
                    i2 = 1;
                    break;
                }
                break;
            case 870738373:
                if (str.equals("EMAIL_SIGNIN")) {
                    i2 = 2;
                    break;
                }
                break;
        }
        switch (i2) {
            case 0:
                break;
            case 1:
                i = 4;
                break;
            case 2:
                i = 6;
                break;
            default:
                i = Integer.MIN_VALUE;
                break;
        }
        zzl.zzao = i;
        zzl.zzah = this.zzah;
        zzl.zzaf = this.zzaf;
        if (this.zzhb != null) {
            zzl.zzat = this.zzhb.m8480a();
            zzl.zzau = this.zzhb.m8481b();
            zzl.zzav = this.zzhb.m8482c();
            zzl.zzaw = this.zzhb.m8483d();
            zzl.zzax = this.zzhb.m8484e();
            zzl.zzay = this.zzhb.m8485f();
            zzl.zzaz = this.zzhb.m8486g();
        }
        return zzl;
    }

    public final zzan zzp(String str) {
        this.zzah = Preconditions.checkNotEmpty(str);
        return this;
    }

    public final zzan zzq(String str) {
        this.zzaf = Preconditions.checkNotEmpty(str);
        return this;
    }
}
