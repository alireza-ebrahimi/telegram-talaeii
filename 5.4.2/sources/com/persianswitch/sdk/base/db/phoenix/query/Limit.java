package com.persianswitch.sdk.base.db.phoenix.query;

public final class Limit implements SQLStatement {
    /* renamed from: a */
    private final long f7028a;
    /* renamed from: b */
    private final long f7029b;

    public Limit(long j) {
        this(j, 0);
    }

    public Limit(long j, long j2) {
        this.f7028a = j;
        this.f7029b = j2;
    }

    /* renamed from: a */
    public String mo3244a() {
        return "LIMIT " + this.f7028a + (this.f7029b > 0 ? " OFFSET" + this.f7029b : " ");
    }
}
