package com.persianswitch.sdk.base.db.phoenix.query;

import android.support.annotation.NonNull;
import com.persianswitch.sdk.base.db.phoenix.query.WhereCondition.AndMixCondition;
import com.persianswitch.sdk.base.db.phoenix.query.WhereCondition.OrMixCondition;

public final class Where implements SQLStatement {
    private final WhereCondition mWhereCondition;

    public Where(WhereCondition whereCondition) {
        this.mWhereCondition = whereCondition;
    }

    public static Where any() {
        return new Where(null);
    }

    public Where or(Where right) {
        if (this.mWhereCondition != null) {
            return new Where(new OrMixCondition(this.mWhereCondition, right.mWhereCondition));
        }
        return new Where(right.mWhereCondition);
    }

    public Where and(Where right) {
        if (this.mWhereCondition != null) {
            return new Where(new AndMixCondition(this.mWhereCondition, right.mWhereCondition));
        }
        return new Where(right.mWhereCondition);
    }

    @NonNull
    public String toSQL() {
        if (this.mWhereCondition == null) {
            return "";
        }
        return "WHERE " + this.mWhereCondition.toSQL();
    }

    public String toSQLConditionOnly() {
        if (this.mWhereCondition == null) {
            return "";
        }
        return this.mWhereCondition.toSQL();
    }
}
