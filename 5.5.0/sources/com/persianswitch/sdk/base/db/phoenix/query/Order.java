package com.persianswitch.sdk.base.db.phoenix.query;

import java.util.List;

public final class Order implements SQLStatement {
    /* renamed from: a */
    private List<OrderCondition> f7030a;

    /* renamed from: a */
    public String mo3244a() {
        StringBuilder stringBuilder = new StringBuilder(30);
        stringBuilder.append("ORDER BY ");
        for (int i = 0; i < this.f7030a.size(); i++) {
            stringBuilder.append(((OrderCondition) this.f7030a.get(i)).mo3244a());
            if (i < this.f7030a.size() - 1) {
                stringBuilder.append(", ");
            }
        }
        return stringBuilder.toString();
    }
}
