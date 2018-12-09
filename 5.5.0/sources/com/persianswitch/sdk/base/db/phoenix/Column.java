package com.persianswitch.sdk.base.db.phoenix;

import android.content.ContentValues;
import android.database.Cursor;
import com.persianswitch.sdk.base.db.phoenix.query.Where;
import com.persianswitch.sdk.base.db.phoenix.query.WhereCondition.SimpleOperatorCondition;
import com.persianswitch.sdk.base.utils.strings.StringUtils;

public class Column<T> {
    /* renamed from: a */
    private final String f7006a;
    /* renamed from: b */
    private final ColumnType f7007b;
    /* renamed from: c */
    private final FieldConverter<T> f7008c;
    /* renamed from: d */
    private final Class<T> f7009d;
    /* renamed from: e */
    private final boolean f7010e;
    /* renamed from: f */
    private final String f7011f;

    public Column(String str, Class<T> cls) {
        this(str, cls, false, TtmlNode.ANONYMOUS_REGION_ID);
    }

    public Column(String str, Class<T> cls, boolean z) {
        this(str, cls, z, TtmlNode.ANONYMOUS_REGION_ID);
    }

    public Column(String str, Class<T> cls, boolean z, String str2) {
        this.f7006a = str;
        this.f7009d = cls;
        this.f7008c = FieldConverters.m10566a(this.f7009d);
        this.f7007b = ColumnType.m10500a(this.f7009d);
        this.f7010e = z;
        this.f7011f = str2;
    }

    /* renamed from: a */
    public Where m10492a(Object obj) {
        return new Where(new SimpleOperatorCondition(this, " = ", obj));
    }

    /* renamed from: a */
    public T m10493a(Cursor cursor) {
        return this.f7008c.mo3235a(cursor, cursor.getColumnIndexOrThrow(this.f7006a));
    }

    /* renamed from: a */
    String mo3233a() {
        String str = " ";
        Object[] objArr = new Object[3];
        objArr[0] = this.f7006a;
        objArr[1] = this.f7007b;
        objArr[2] = this.f7010e ? "PRIMARY KEY" : TtmlNode.ANONYMOUS_REGION_ID;
        return StringUtils.m10802a(str, objArr);
    }

    /* renamed from: a */
    public void m10495a(T t, ContentValues contentValues) {
        this.f7008c.mo3236a(this.f7006a, t, contentValues);
    }

    /* renamed from: b */
    boolean m10496b() {
        return this.f7010e;
    }

    /* renamed from: c */
    public String m10497c() {
        return this.f7006a;
    }

    /* renamed from: d */
    public ColumnType m10498d() {
        return this.f7007b;
    }
}
