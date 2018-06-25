package com.persianswitch.sdk.base.db.phoenix.query;

import com.persianswitch.sdk.base.db.phoenix.Column;
import com.persianswitch.sdk.base.utils.strings.StringUtils;

public final class ColumnSelect implements SQLStatement {
    /* renamed from: a */
    private final String f7026a;
    /* renamed from: b */
    private final Column f7027b;

    /* renamed from: a */
    public String mo3244a() {
        return StringUtils.m10803a(this.f7026a) ? this.f7027b.m10497c() : this.f7026a + "." + this.f7027b.m10497c();
    }
}
