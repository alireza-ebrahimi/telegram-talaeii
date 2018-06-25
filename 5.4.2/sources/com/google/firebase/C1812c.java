package com.google.firebase;

import com.google.android.gms.common.internal.Preconditions;

/* renamed from: com.google.firebase.c */
public class C1812c extends Exception {
    @Deprecated
    protected C1812c() {
    }

    public C1812c(String str) {
        super(Preconditions.checkNotEmpty(str, "Detail message must not be empty"));
    }
}
