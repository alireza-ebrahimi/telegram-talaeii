package org.telegram.SQLite;

import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.FileLog;

public class SQLiteDatabase {
    /* renamed from: a */
    private final int f8301a;
    /* renamed from: b */
    private boolean f8302b = false;
    /* renamed from: c */
    private boolean f8303c = false;

    public SQLiteDatabase(String str) {
        this.f8301a = opendb(str, ApplicationLoader.getFilesDirFixed().getPath());
        this.f8302b = true;
    }

    /* renamed from: a */
    public int m12162a() {
        return this.f8301a;
    }

    /* renamed from: a */
    public Integer m12163a(String str, Object... objArr) {
        m12167c();
        SQLiteCursor b = m12165b(str, objArr);
        try {
            if (!b.m12152a()) {
                return null;
            }
            Integer valueOf = Integer.valueOf(b.m12154b(0));
            b.m12155b();
            return valueOf;
        } finally {
            b.m12155b();
        }
    }

    /* renamed from: a */
    public SQLitePreparedStatement m12164a(String str) {
        return new SQLitePreparedStatement(this, str, true);
    }

    /* renamed from: b */
    public SQLiteCursor m12165b(String str, Object... objArr) {
        m12167c();
        return new SQLitePreparedStatement(this, str, true).m12171a(objArr);
    }

    /* renamed from: b */
    public void m12166b() {
        if (this.f8302b) {
            try {
                m12169e();
                closedb(this.f8301a);
            } catch (Throwable e) {
                FileLog.m13727e(e.getMessage(), e);
            }
            this.f8302b = false;
        }
    }

    native void beginTransaction(int i);

    /* renamed from: c */
    void m12167c() {
        if (!this.f8302b) {
            throw new C2486a("Database closed");
        }
    }

    native void closedb(int i);

    native void commitTransaction(int i);

    /* renamed from: d */
    public void m12168d() {
        if (this.f8303c) {
            throw new C2486a("database already in transaction");
        }
        this.f8303c = true;
        beginTransaction(this.f8301a);
    }

    /* renamed from: e */
    public void m12169e() {
        if (this.f8303c) {
            this.f8303c = false;
            commitTransaction(this.f8301a);
        }
    }

    public void finalize() {
        super.finalize();
        m12166b();
    }

    native int opendb(String str, String str2);
}
