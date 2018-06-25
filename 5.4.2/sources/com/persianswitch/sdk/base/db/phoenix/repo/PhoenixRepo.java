package com.persianswitch.sdk.base.db.phoenix.repo;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.persianswitch.sdk.base.db.phoenix.Column;
import com.persianswitch.sdk.base.db.phoenix.Table;
import com.persianswitch.sdk.base.db.phoenix.query.Query;
import com.persianswitch.sdk.base.db.phoenix.query.Where;
import com.persianswitch.sdk.base.utils.PreConditions;
import java.util.List;

public abstract class PhoenixRepo<I, T extends IPhoenixModel<I>> implements IPhoenixRepo<I, T> {
    /* renamed from: a */
    private final Table<T> f7046a;
    /* renamed from: b */
    private final SQLiteOpenHelper f7047b;

    public PhoenixRepo(SQLiteOpenHelper sQLiteOpenHelper, Table<T> table) {
        this.f7047b = sQLiteOpenHelper;
        this.f7046a = table;
    }

    /* renamed from: a */
    public T m10610a(I i) {
        Column b = m10614b().mo3240b();
        PreConditions.m10762a(b, "you must specify id column in your entity schema");
        SQLiteDatabase d = m10618d();
        try {
            IPhoenixModel iPhoenixModel = (IPhoenixModel) m10616c().m10596a(b.m10492a((Object) i)).m10597a(d);
            return iPhoenixModel;
        } finally {
            d.close();
        }
    }

    /* renamed from: a */
    public List<T> m10611a() {
        SQLiteDatabase d = m10618d();
        try {
            List<T> b = m10616c().m10600b(d);
            return b;
        } finally {
            d.close();
        }
    }

    /* renamed from: a */
    public void m10612a(Where where) {
        SQLiteDatabase e = m10620e();
        try {
            e.delete(m10614b().mo3239a(), where.m10605c(), null);
        } finally {
            e.close();
        }
    }

    /* renamed from: a */
    public void mo3369a(T t) {
        if (m10610a(t.mo3243c()) == null) {
            m10615b(t);
        } else {
            m10617c(t);
        }
    }

    /* renamed from: b */
    public Table<T> m10614b() {
        return this.f7046a;
    }

    /* renamed from: b */
    public void m10615b(T t) {
        PreConditions.m10762a(t, "can not create null entity");
        ContentValues contentValues = new ContentValues();
        m10614b().mo3242d().mo3238a(t, contentValues);
        SQLiteDatabase e = m10620e();
        try {
            e.insert(m10614b().mo3239a(), null, contentValues);
        } finally {
            e.close();
        }
    }

    /* renamed from: c */
    public Query<T> m10616c() {
        return new Query(m10614b());
    }

    /* renamed from: c */
    public void m10617c(T t) {
        PreConditions.m10762a(t, "can not update null entity");
        ContentValues contentValues = new ContentValues();
        m10614b().mo3242d().mo3238a(t, contentValues);
        String c = m10614b().mo3240b().m10492a(t.mo3243c()).m10605c();
        SQLiteDatabase e = m10620e();
        try {
            e.update(m10614b().mo3239a(), contentValues, c, null);
        } finally {
            e.close();
        }
    }

    /* renamed from: d */
    public SQLiteDatabase m10618d() {
        return this.f7047b.getReadableDatabase();
    }

    /* renamed from: d */
    public void m10619d(T t) {
        m10612a(m10614b().mo3240b().m10492a(t.mo3243c()));
    }

    /* renamed from: e */
    public SQLiteDatabase m10620e() {
        return this.f7047b.getWritableDatabase();
    }
}
