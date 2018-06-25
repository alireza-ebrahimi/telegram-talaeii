package com.persianswitch.sdk.base.db.phoenix.query;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.persianswitch.sdk.base.db.phoenix.EntityConverter;
import com.persianswitch.sdk.base.db.phoenix.Table;
import com.persianswitch.sdk.base.utils.PreConditions;
import java.util.LinkedList;
import java.util.List;

public final class Query<T> implements SQLStatement {
    /* renamed from: a */
    private final Table<T> f7034a;
    /* renamed from: b */
    private Project f7035b;
    /* renamed from: c */
    private Where f7036c;
    /* renamed from: d */
    private Order f7037d;
    /* renamed from: e */
    private Limit f7038e;

    public Query(Table table) {
        PreConditions.m10762a(table, "table can not be null");
        PreConditions.m10762a(table.mo3241c(), "getName can not be null");
        this.f7034a = table;
    }

    /* renamed from: a */
    private Query<T> m10594a(Limit limit) {
        this.f7038e = limit;
        return this;
    }

    /* renamed from: a */
    public Query<T> m10595a(long j) {
        return m10594a(new Limit(j));
    }

    /* renamed from: a */
    public Query<T> m10596a(Where where) {
        this.f7036c = where;
        return this;
    }

    /* renamed from: a */
    public synchronized T m10597a(SQLiteDatabase sQLiteDatabase) {
        return m10598a(sQLiteDatabase, this.f7034a.mo3242d());
    }

    /* renamed from: a */
    public synchronized <E> E m10598a(SQLiteDatabase sQLiteDatabase, EntityConverter<E> entityConverter) {
        E e;
        m10595a(1);
        List b = m10601b(sQLiteDatabase, entityConverter);
        e = (b == null || b.isEmpty()) ? null : b.get(0);
        return e;
    }

    /* renamed from: a */
    public synchronized String mo3244a() {
        StringBuilder stringBuilder;
        stringBuilder = new StringBuilder(100);
        stringBuilder.append("SELECT ");
        if (this.f7035b == null) {
            stringBuilder.append("* ");
        } else {
            stringBuilder.append(this.f7035b.mo3244a()).append(" ");
        }
        stringBuilder.append("FROM ");
        stringBuilder.append(this.f7034a.mo3239a()).append(" ");
        if (this.f7036c != null) {
            stringBuilder.append(this.f7036c.mo3244a()).append(" ");
        }
        if (this.f7038e != null) {
            stringBuilder.append(this.f7038e.mo3244a());
        }
        if (this.f7037d != null) {
            stringBuilder.append(this.f7037d.mo3244a());
        }
        stringBuilder.append(";");
        return stringBuilder.toString().trim();
    }

    /* renamed from: b */
    public synchronized List<T> m10600b(SQLiteDatabase sQLiteDatabase) {
        return m10601b(sQLiteDatabase, this.f7034a.mo3242d());
    }

    /* renamed from: b */
    public synchronized <E> List<E> m10601b(SQLiteDatabase sQLiteDatabase, EntityConverter<E> entityConverter) {
        List<E> linkedList;
        PreConditions.m10762a(entityConverter, "entity converter can not be null");
        linkedList = new LinkedList();
        Cursor rawQuery = sQLiteDatabase.rawQuery(mo3244a(), new String[0]);
        while (rawQuery.moveToNext()) {
            try {
                linkedList.add(entityConverter.mo3237a(rawQuery));
            } catch (Throwable th) {
                if (rawQuery != null) {
                    rawQuery.close();
                }
            }
        }
        if (rawQuery != null) {
            rawQuery.close();
        }
        return linkedList;
    }
}
