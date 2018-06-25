package com.persianswitch.sdk.base.db.phoenix.query;

import android.support.annotation.NonNull;
import com.persianswitch.sdk.base.db.phoenix.Column;
import com.persianswitch.sdk.base.db.phoenix.ColumnType;
import com.persianswitch.sdk.base.utils.PreConditions;

public abstract class WhereCondition implements SQLStatement {

    private static class MixCondition extends WhereCondition {
        private final WhereCondition mLeftCondition;
        private final String mMixKeyword;
        private final WhereCondition mRightCondition;

        public MixCondition(@NonNull WhereCondition left, @NonNull String keyword, @NonNull WhereCondition right) {
            PreConditions.checkNotNull(left, "left condition can not be null");
            PreConditions.checkNotNull(right, "right condition can not be null");
            PreConditions.checkNotNull(keyword, "mixKeyword can not be null");
            this.mLeftCondition = left;
            this.mRightCondition = right;
            this.mMixKeyword = keyword;
        }

        @NonNull
        public String toSQL() {
            return this.mLeftCondition.toSQL() + this.mMixKeyword + this.mRightCondition.toSQL();
        }
    }

    public static class AndMixCondition extends MixCondition {
        @NonNull
        public /* bridge */ /* synthetic */ String toSQL() {
            return super.toSQL();
        }

        public AndMixCondition(@NonNull WhereCondition left, @NonNull WhereCondition right) {
            super(left, " AND ", right);
        }
    }

    public static class OrMixCondition extends MixCondition {
        @NonNull
        public /* bridge */ /* synthetic */ String toSQL() {
            return super.toSQL();
        }

        public OrMixCondition(@NonNull WhereCondition left, @NonNull WhereCondition right) {
            super(left, " OR ", right);
        }
    }

    public static abstract class SimpleCondition extends WhereCondition {
        final Column mColumn;
        final Object mValue;

        SimpleCondition(@NonNull Column column, @NonNull Object value) {
            PreConditions.checkNotNull(column, "column can not be null in SimpleCondition");
            PreConditions.checkNotNull(value, "value can not be null in SimpleCondition");
            if (column.getColumnType() != ColumnType.TEXT || (value instanceof String)) {
                this.mColumn = column;
                this.mValue = value;
                return;
            }
            throw new IllegalStateException("value of column " + column.getColumnName() + " must be String");
        }
    }

    public static class SimpleOperatorCondition extends SimpleCondition {
        private final String mOperator;

        public SimpleOperatorCondition(@NonNull Column column, String operator, @NonNull Object value) {
            super(column, value);
            this.mOperator = operator;
        }

        @NonNull
        public String toSQL() {
            if (this.mValue instanceof String) {
                return this.mColumn.getColumnName() + this.mOperator + "'" + this.mValue + "'";
            }
            return this.mColumn.getColumnName() + this.mOperator + this.mValue;
        }
    }
}
