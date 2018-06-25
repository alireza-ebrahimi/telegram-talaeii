package com.persianswitch.sdk.base.db.phoenix.query;

import java.util.List;

public final class Project implements SQLStatement {
    /* renamed from: a */
    private final List<ColumnSelect> f7033a;

    /* renamed from: a */
    public String mo3244a() {
        StringBuilder stringBuilder = new StringBuilder(20);
        for (int i = 0; i < this.f7033a.size(); i++) {
            stringBuilder.append(((ColumnSelect) this.f7033a.get(i)).mo3244a());
            if (i < this.f7033a.size() - 1) {
                stringBuilder.append(", ");
            }
        }
        return stringBuilder.toString();
    }
}
