package com.persianswitch.sdk.base.db.phoenix.query;

import com.persianswitch.sdk.base.db.phoenix.query.WhereCondition.AndMixCondition;

public final class Where implements SQLStatement {
    /* renamed from: a */
    private final WhereCondition f7039a;

    public Where(WhereCondition whereCondition) {
        this.f7039a = whereCondition;
    }

    /* renamed from: b */
    public static Where m10602b() {
        return new Where(null);
    }

    /* renamed from: a */
    public Where m10603a(Where where) {
        return this.f7039a != null ? new Where(new AndMixCondition(this.f7039a, where.f7039a)) : new Where(where.f7039a);
    }

    /* renamed from: a */
    public String mo3244a() {
        return this.f7039a == null ? TtmlNode.ANONYMOUS_REGION_ID : "WHERE " + this.f7039a.mo3244a();
    }

    /* renamed from: c */
    public String m10605c() {
        return this.f7039a == null ? TtmlNode.ANONYMOUS_REGION_ID : this.f7039a.mo3244a();
    }
}
