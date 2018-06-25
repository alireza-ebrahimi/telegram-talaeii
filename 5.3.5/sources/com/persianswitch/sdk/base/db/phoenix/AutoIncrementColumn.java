package com.persianswitch.sdk.base.db.phoenix;

import com.persianswitch.sdk.base.utils.strings.StringUtils;

public final class AutoIncrementColumn<T> extends Column<T> {
    public AutoIncrementColumn(String columnName, Class<T> javaTypeClass) {
        super(columnName, (Class) javaTypeClass);
    }

    String toDefineStatement() {
        return StringUtils.trimJoin(" ", super.toDefineStatement(), "AUTOINCREMENT");
    }
}
