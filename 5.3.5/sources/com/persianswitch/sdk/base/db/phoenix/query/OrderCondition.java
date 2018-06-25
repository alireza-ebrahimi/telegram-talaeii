package com.persianswitch.sdk.base.db.phoenix.query;

import android.support.annotation.NonNull;
import com.persianswitch.sdk.base.db.phoenix.Column;

public final class OrderCondition implements SQLStatement {
    private final boolean mAscend;
    private final Column mColumn;

    public OrderCondition(Column column) {
        this(column, true);
    }

    public OrderCondition(Column column, boolean ascend) {
        this.mColumn = column;
        this.mAscend = ascend;
    }

    @NonNull
    public String toSQL() {
        return this.mColumn.getColumnName() + (this.mAscend ? " ASC" : " DESC");
    }
}
