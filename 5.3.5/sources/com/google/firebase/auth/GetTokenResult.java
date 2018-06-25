package com.google.firebase.auth;

import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.Hide;

public class GetTokenResult {
    private String zzeia;

    @Hide
    public GetTokenResult(String str) {
        this.zzeia = str;
    }

    @Nullable
    public String getToken() {
        return this.zzeia;
    }
}
