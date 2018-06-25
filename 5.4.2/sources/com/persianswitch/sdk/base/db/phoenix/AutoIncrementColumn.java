package com.persianswitch.sdk.base.db.phoenix;

import com.persianswitch.sdk.base.utils.strings.StringUtils;

public final class AutoIncrementColumn<T> extends Column<T> {
    /* renamed from: a */
    String mo3233a() {
        return StringUtils.m10802a(" ", super.mo3233a(), "AUTOINCREMENT");
    }
}
