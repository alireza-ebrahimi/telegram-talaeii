package com.persianswitch.sdk.base.db.phoenix.query;

import com.persianswitch.sdk.base.db.phoenix.Column;
import com.persianswitch.sdk.base.db.phoenix.ColumnType;
import com.persianswitch.sdk.base.utils.PreConditions;

public abstract class WhereCondition implements SQLStatement {

    private static class MixCondition extends WhereCondition {
        /* renamed from: a */
        private final WhereCondition f7040a;
        /* renamed from: b */
        private final WhereCondition f7041b;
        /* renamed from: c */
        private final String f7042c;

        public MixCondition(WhereCondition whereCondition, String str, WhereCondition whereCondition2) {
            PreConditions.m10762a(whereCondition, "left condition can not be null");
            PreConditions.m10762a(whereCondition2, "right condition can not be null");
            PreConditions.m10762a(str, "mixKeyword can not be null");
            this.f7040a = whereCondition;
            this.f7041b = whereCondition2;
            this.f7042c = str;
        }

        /* renamed from: a */
        public String mo3244a() {
            return this.f7040a.mo3244a() + this.f7042c + this.f7041b.mo3244a();
        }
    }

    public static class AndMixCondition extends MixCondition {
        public AndMixCondition(WhereCondition whereCondition, WhereCondition whereCondition2) {
            super(whereCondition, " AND ", whereCondition2);
        }

        /* renamed from: a */
        public /* bridge */ /* synthetic */ String mo3244a() {
            return super.mo3244a();
        }
    }

    public static class OrMixCondition extends MixCondition {
        /* renamed from: a */
        public /* bridge */ /* synthetic */ String mo3244a() {
            return super.mo3244a();
        }
    }

    public static abstract class SimpleCondition extends WhereCondition {
        /* renamed from: a */
        final Column f7043a;
        /* renamed from: b */
        final Object f7044b;

        SimpleCondition(Column column, Object obj) {
            PreConditions.m10762a(column, "column can not be null in SimpleCondition");
            PreConditions.m10762a(obj, "value can not be null in SimpleCondition");
            if (column.m10498d() != ColumnType.TEXT || (obj instanceof String)) {
                this.f7043a = column;
                this.f7044b = obj;
                return;
            }
            throw new IllegalStateException("value of column " + column.m10497c() + " must be String");
        }
    }

    public static class SimpleOperatorCondition extends SimpleCondition {
        /* renamed from: c */
        private final String f7045c;

        public SimpleOperatorCondition(Column column, String str, Object obj) {
            super(column, obj);
            this.f7045c = str;
        }

        /* renamed from: a */
        public String mo3244a() {
            return this.b instanceof String ? this.a.m10497c() + this.f7045c + "'" + this.b + "'" : this.a.m10497c() + this.f7045c + this.b;
        }
    }
}
