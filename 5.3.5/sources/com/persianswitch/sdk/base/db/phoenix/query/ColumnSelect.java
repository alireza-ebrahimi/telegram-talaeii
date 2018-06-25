package com.persianswitch.sdk.base.db.phoenix.query;

import android.support.annotation.NonNull;
import com.persianswitch.sdk.base.db.phoenix.Column;
import com.persianswitch.sdk.base.utils.strings.StringUtils;

public final class ColumnSelect implements SQLStatement {
    private final Column mColumn;
    private final String mTableName;

    public ColumnSelect(Column column) {
        this(null, column);
    }

    public ColumnSelect(String tableName, Column column) {
        this.mTableName = tableName;
        this.mColumn = column;
    }

    @NonNull
    public String toSQL() {
        if (StringUtils.isEmpty(this.mTableName)) {
            return this.mColumn.getColumnName();
        }
        return this.mTableName + "." + this.mColumn.getColumnName();
    }
}
