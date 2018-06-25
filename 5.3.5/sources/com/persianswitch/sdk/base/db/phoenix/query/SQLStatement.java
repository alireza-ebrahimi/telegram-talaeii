package com.persianswitch.sdk.base.db.phoenix.query;

import android.support.annotation.NonNull;

public interface SQLStatement {
    @NonNull
    String toSQL();
}
