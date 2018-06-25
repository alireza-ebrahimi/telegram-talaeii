package com.persianswitch.sdk.base.db.phoenix.query;

import com.persianswitch.sdk.base.db.phoenix.Column;

public final class OrderCondition implements SQLStatement {
    /* renamed from: a */
    private final Column f7031a;
    /* renamed from: b */
    private final boolean f7032b;

    /* renamed from: a */
    public String mo3244a() {
        return this.f7031a.m10497c() + (this.f7032b ? " ASC" : " DESC");
    }
}
