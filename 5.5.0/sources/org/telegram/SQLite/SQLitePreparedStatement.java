package org.telegram.SQLite;

import java.nio.ByteBuffer;
import org.telegram.messenger.FileLog;
import org.telegram.tgnet.NativeByteBuffer;

public class SQLitePreparedStatement {
    /* renamed from: a */
    private boolean f8304a = false;
    /* renamed from: b */
    private int f8305b;
    /* renamed from: c */
    private boolean f8306c = false;

    public SQLitePreparedStatement(SQLiteDatabase sQLiteDatabase, String str, boolean z) {
        this.f8306c = z;
        this.f8305b = prepare(sQLiteDatabase.m12162a(), str);
    }

    /* renamed from: a */
    public int m12170a() {
        return this.f8305b;
    }

    /* renamed from: a */
    public SQLiteCursor m12171a(Object[] objArr) {
        if (objArr == null) {
            throw new IllegalArgumentException();
        }
        m12182f();
        reset(this.f8305b);
        int i = 1;
        for (Object obj : objArr) {
            if (obj == null) {
                bindNull(this.f8305b, i);
            } else if (obj instanceof Integer) {
                bindInt(this.f8305b, i, ((Integer) obj).intValue());
            } else if (obj instanceof Double) {
                bindDouble(this.f8305b, i, ((Double) obj).doubleValue());
            } else if (obj instanceof String) {
                bindString(this.f8305b, i, (String) obj);
            } else {
                throw new IllegalArgumentException();
            }
            i++;
        }
        return new SQLiteCursor(this);
    }

    /* renamed from: a */
    public void m12172a(int i) {
        bindNull(this.f8305b, i);
    }

    /* renamed from: a */
    public void m12173a(int i, double d) {
        bindDouble(this.f8305b, i, d);
    }

    /* renamed from: a */
    public void m12174a(int i, int i2) {
        bindInt(this.f8305b, i, i2);
    }

    /* renamed from: a */
    public void m12175a(int i, long j) {
        bindLong(this.f8305b, i, j);
    }

    /* renamed from: a */
    public void m12176a(int i, String str) {
        bindString(this.f8305b, i, str);
    }

    /* renamed from: a */
    public void m12177a(int i, NativeByteBuffer nativeByteBuffer) {
        bindByteBuffer(this.f8305b, i, nativeByteBuffer.buffer, nativeByteBuffer.limit());
    }

    /* renamed from: b */
    public int m12178b() {
        return step(this.f8305b);
    }

    native void bindByteBuffer(int i, int i2, ByteBuffer byteBuffer, int i3);

    native void bindDouble(int i, int i2, double d);

    native void bindInt(int i, int i2, int i3);

    native void bindLong(int i, int i2, long j);

    native void bindNull(int i, int i2);

    native void bindString(int i, int i2, String str);

    /* renamed from: c */
    public SQLitePreparedStatement m12179c() {
        step(this.f8305b);
        return this;
    }

    /* renamed from: d */
    public void m12180d() {
        m12182f();
        reset(this.f8305b);
    }

    /* renamed from: e */
    public void m12181e() {
        if (this.f8306c) {
            m12183g();
        }
    }

    /* renamed from: f */
    void m12182f() {
        if (this.f8304a) {
            throw new C2486a("Prepared query finalized");
        }
    }

    native void finalize(int i);

    /* renamed from: g */
    public void m12183g() {
        if (!this.f8304a) {
            try {
                this.f8304a = true;
                finalize(this.f8305b);
            } catch (Throwable e) {
                FileLog.m13727e(e.getMessage(), e);
            }
        }
    }

    native int prepare(int i, String str);

    native void reset(int i);

    native int step(int i);
}
