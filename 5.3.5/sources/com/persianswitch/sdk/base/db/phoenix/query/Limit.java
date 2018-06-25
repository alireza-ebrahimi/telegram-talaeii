package com.persianswitch.sdk.base.db.phoenix.query;

import android.support.annotation.NonNull;

public final class Limit implements SQLStatement {
    private final long mRecordNumber;
    private final long mRecordOffset;

    public Limit(long recordNumber) {
        this(recordNumber, 0);
    }

    public Limit(long recordNumber, long recordOffset) {
        this.mRecordNumber = recordNumber;
        this.mRecordOffset = recordOffset;
    }

    @NonNull
    public String toSQL() {
        return "LIMIT " + this.mRecordNumber + (this.mRecordOffset > 0 ? " OFFSET" + this.mRecordOffset : " ");
    }
}
