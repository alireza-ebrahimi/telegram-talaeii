package org.telegram.SQLite;

import org.telegram.tgnet.NativeByteBuffer;

public class SQLiteCursor {
    /* renamed from: a */
    SQLitePreparedStatement f8299a;
    /* renamed from: b */
    boolean f8300b = false;

    public SQLiteCursor(SQLitePreparedStatement sQLitePreparedStatement) {
        this.f8299a = sQLitePreparedStatement;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    /* renamed from: a */
    public boolean m12152a() {
        /*
        r7 = this;
        r3 = -1;
        r0 = r7.f8299a;
        r1 = r7.f8299a;
        r1 = r1.m12170a();
        r0 = r0.step(r1);
        if (r0 != r3) goto L_0x003e;
    L_0x000f:
        r1 = 6;
        r6 = r1;
        r1 = r0;
        r0 = r6;
    L_0x0013:
        r2 = r0 + -1;
        if (r0 == 0) goto L_0x0048;
    L_0x0017:
        r0 = "sqlite busy, waiting...";
        org.telegram.messenger.FileLog.m13726e(r0);	 Catch:{ Exception -> 0x0038 }
        r4 = 500; // 0x1f4 float:7.0E-43 double:2.47E-321;
        java.lang.Thread.sleep(r4);	 Catch:{ Exception -> 0x0038 }
        r0 = r7.f8299a;	 Catch:{ Exception -> 0x0038 }
        r0 = r0.m12178b();	 Catch:{ Exception -> 0x0038 }
        if (r0 != 0) goto L_0x0035;
    L_0x002a:
        if (r0 != r3) goto L_0x003e;
    L_0x002c:
        r0 = new org.telegram.SQLite.a;
        r1 = "sqlite busy";
        r0.<init>(r1);
        throw r0;
    L_0x0035:
        r1 = r0;
        r0 = r2;
        goto L_0x0013;
    L_0x0038:
        r0 = move-exception;
        org.telegram.messenger.FileLog.m13728e(r0);
        r0 = r2;
        goto L_0x0013;
    L_0x003e:
        if (r0 != 0) goto L_0x0046;
    L_0x0040:
        r0 = 1;
    L_0x0041:
        r7.f8300b = r0;
        r0 = r7.f8300b;
        return r0;
    L_0x0046:
        r0 = 0;
        goto L_0x0041;
    L_0x0048:
        r0 = r1;
        goto L_0x002a;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.SQLite.SQLiteCursor.a():boolean");
    }

    /* renamed from: a */
    public boolean m12153a(int i) {
        m12157c();
        return columnIsNull(this.f8299a.m12170a(), i) == 1;
    }

    /* renamed from: b */
    public int m12154b(int i) {
        m12157c();
        return columnIntValue(this.f8299a.m12170a(), i);
    }

    /* renamed from: b */
    public void m12155b() {
        this.f8299a.m12181e();
    }

    /* renamed from: c */
    public double m12156c(int i) {
        m12157c();
        return columnDoubleValue(this.f8299a.m12170a(), i);
    }

    /* renamed from: c */
    void m12157c() {
        if (!this.f8300b) {
            throw new C2486a("You must call next before");
        }
    }

    native byte[] columnByteArrayValue(int i, int i2);

    native int columnByteBufferValue(int i, int i2);

    native double columnDoubleValue(int i, int i2);

    native int columnIntValue(int i, int i2);

    native int columnIsNull(int i, int i2);

    native long columnLongValue(int i, int i2);

    native String columnStringValue(int i, int i2);

    native int columnType(int i, int i2);

    /* renamed from: d */
    public long m12158d(int i) {
        m12157c();
        return columnLongValue(this.f8299a.m12170a(), i);
    }

    /* renamed from: e */
    public String m12159e(int i) {
        m12157c();
        return columnStringValue(this.f8299a.m12170a(), i);
    }

    /* renamed from: f */
    public byte[] m12160f(int i) {
        m12157c();
        return columnByteArrayValue(this.f8299a.m12170a(), i);
    }

    /* renamed from: g */
    public NativeByteBuffer m12161g(int i) {
        m12157c();
        int columnByteBufferValue = columnByteBufferValue(this.f8299a.m12170a(), i);
        return columnByteBufferValue != 0 ? NativeByteBuffer.wrap(columnByteBufferValue) : null;
    }
}
