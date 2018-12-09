package com.google.p098a.p102d;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.Writer;

/* renamed from: com.google.a.d.c */
public class C1681c implements Closeable, Flushable {
    /* renamed from: a */
    private static final String[] f5153a = new String[128];
    /* renamed from: b */
    private static final String[] f5154b = ((String[]) f5153a.clone());
    /* renamed from: c */
    private final Writer f5155c;
    /* renamed from: d */
    private int[] f5156d = new int[32];
    /* renamed from: e */
    private int f5157e = 0;
    /* renamed from: f */
    private String f5158f;
    /* renamed from: g */
    private String f5159g;
    /* renamed from: h */
    private boolean f5160h;
    /* renamed from: i */
    private boolean f5161i;
    /* renamed from: j */
    private String f5162j;
    /* renamed from: k */
    private boolean f5163k;

    static {
        for (int i = 0; i <= 31; i++) {
            f5153a[i] = String.format("\\u%04x", new Object[]{Integer.valueOf(i)});
        }
        f5153a[34] = "\\\"";
        f5153a[92] = "\\\\";
        f5153a[9] = "\\t";
        f5153a[8] = "\\b";
        f5153a[10] = "\\n";
        f5153a[13] = "\\r";
        f5153a[12] = "\\f";
        f5154b[60] = "\\u003c";
        f5154b[62] = "\\u003e";
        f5154b[38] = "\\u0026";
        f5154b[61] = "\\u003d";
        f5154b[39] = "\\u0027";
    }

    public C1681c(Writer writer) {
        m8161a(6);
        this.f5159g = ":";
        this.f5163k = true;
        if (writer == null) {
            throw new NullPointerException("out == null");
        }
        this.f5155c = writer;
    }

    /* renamed from: a */
    private int mo1277a() {
        if (this.f5157e != 0) {
            return this.f5156d[this.f5157e - 1];
        }
        throw new IllegalStateException("JsonWriter is closed.");
    }

    /* renamed from: a */
    private C1681c m8159a(int i, int i2, String str) {
        int a = mo1277a();
        if (a != i2 && a != i) {
            throw new IllegalStateException("Nesting problem.");
        } else if (this.f5162j != null) {
            throw new IllegalStateException("Dangling name: " + this.f5162j);
        } else {
            this.f5157e--;
            if (a == i2) {
                m8166k();
            }
            this.f5155c.write(str);
            return this;
        }
    }

    /* renamed from: a */
    private C1681c m8160a(int i, String str) {
        m8164e(true);
        m8161a(i);
        this.f5155c.write(str);
        return this;
    }

    /* renamed from: a */
    private void m8161a(int i) {
        if (this.f5157e == this.f5156d.length) {
            Object obj = new int[(this.f5157e * 2)];
            System.arraycopy(this.f5156d, 0, obj, 0, this.f5157e);
            this.f5156d = obj;
        }
        int[] iArr = this.f5156d;
        int i2 = this.f5157e;
        this.f5157e = i2 + 1;
        iArr[i2] = i;
    }

    /* renamed from: b */
    private void m8162b(int i) {
        this.f5156d[this.f5157e - 1] = i;
    }

    /* renamed from: d */
    private void m8163d(String str) {
        int i = 0;
        String[] strArr = this.f5161i ? f5154b : f5153a;
        this.f5155c.write("\"");
        int length = str.length();
        for (int i2 = 0; i2 < length; i2++) {
            char charAt = str.charAt(i2);
            String str2;
            if (charAt < '') {
                str2 = strArr[charAt];
                if (str2 == null) {
                }
                if (i < i2) {
                    this.f5155c.write(str, i, i2 - i);
                }
                this.f5155c.write(str2);
                i = i2 + 1;
            } else {
                if (charAt == ' ') {
                    str2 = "\\u2028";
                } else if (charAt == ' ') {
                    str2 = "\\u2029";
                }
                if (i < i2) {
                    this.f5155c.write(str, i, i2 - i);
                }
                this.f5155c.write(str2);
                i = i2 + 1;
            }
        }
        if (i < length) {
            this.f5155c.write(str, i, length - i);
        }
        this.f5155c.write("\"");
    }

    /* renamed from: e */
    private void m8164e(boolean z) {
        switch (mo1277a()) {
            case 1:
                m8162b(2);
                m8166k();
                return;
            case 2:
                this.f5155c.append(',');
                m8166k();
                return;
            case 4:
                this.f5155c.append(this.f5159g);
                m8162b(5);
                return;
            case 6:
                break;
            case 7:
                if (!this.f5160h) {
                    throw new IllegalStateException("JSON must have only one top-level value.");
                }
                break;
            default:
                throw new IllegalStateException("Nesting problem.");
        }
        if (this.f5160h || z) {
            m8162b(7);
            return;
        }
        throw new IllegalStateException("JSON must start with an array or an object.");
    }

    /* renamed from: j */
    private void m8165j() {
        if (this.f5162j != null) {
            m8167l();
            m8163d(this.f5162j);
            this.f5162j = null;
        }
    }

    /* renamed from: k */
    private void m8166k() {
        if (this.f5158f != null) {
            this.f5155c.write("\n");
            int i = this.f5157e;
            for (int i2 = 1; i2 < i; i2++) {
                this.f5155c.write(this.f5158f);
            }
        }
    }

    /* renamed from: l */
    private void m8167l() {
        int a = mo1277a();
        if (a == 5) {
            this.f5155c.write(44);
        } else if (a != 3) {
            throw new IllegalStateException("Nesting problem.");
        }
        m8166k();
        m8162b(4);
    }

    /* renamed from: a */
    public C1681c mo1273a(long j) {
        m8165j();
        m8164e(false);
        this.f5155c.write(Long.toString(j));
        return this;
    }

    /* renamed from: a */
    public C1681c mo1274a(Number number) {
        if (number == null) {
            return mo1284f();
        }
        m8165j();
        CharSequence obj = number.toString();
        if (this.f5160h || !(obj.equals("-Infinity") || obj.equals("Infinity") || obj.equals("NaN"))) {
            m8164e(false);
            this.f5155c.append(obj);
            return this;
        }
        throw new IllegalArgumentException("Numeric values must be finite, but was " + number);
    }

    /* renamed from: a */
    public C1681c mo1275a(String str) {
        if (str == null) {
            throw new NullPointerException("name == null");
        } else if (this.f5162j != null) {
            throw new IllegalStateException();
        } else if (this.f5157e == 0) {
            throw new IllegalStateException("JsonWriter is closed.");
        } else {
            this.f5162j = str;
            return this;
        }
    }

    /* renamed from: a */
    public C1681c mo1276a(boolean z) {
        m8165j();
        m8164e(false);
        this.f5155c.write(z ? "true" : "false");
        return this;
    }

    /* renamed from: b */
    public C1681c mo1278b() {
        m8165j();
        return m8160a(1, "[");
    }

    /* renamed from: b */
    public C1681c mo1279b(String str) {
        if (str == null) {
            return mo1284f();
        }
        m8165j();
        m8164e(false);
        m8163d(str);
        return this;
    }

    /* renamed from: b */
    public final void m8174b(boolean z) {
        this.f5160h = z;
    }

    /* renamed from: c */
    public C1681c mo1280c() {
        return m8159a(1, 2, "]");
    }

    /* renamed from: c */
    public final void m8176c(String str) {
        if (str.length() == 0) {
            this.f5158f = null;
            this.f5159g = ":";
            return;
        }
        this.f5158f = str;
        this.f5159g = ": ";
    }

    /* renamed from: c */
    public final void m8177c(boolean z) {
        this.f5161i = z;
    }

    public void close() {
        this.f5155c.close();
        int i = this.f5157e;
        if (i > 1 || (i == 1 && this.f5156d[i - 1] != 7)) {
            throw new IOException("Incomplete document");
        }
        this.f5157e = 0;
    }

    /* renamed from: d */
    public C1681c mo1282d() {
        m8165j();
        return m8160a(3, "{");
    }

    /* renamed from: d */
    public final void m8179d(boolean z) {
        this.f5163k = z;
    }

    /* renamed from: e */
    public C1681c mo1283e() {
        return m8159a(3, 5, "}");
    }

    /* renamed from: f */
    public C1681c mo1284f() {
        if (this.f5162j != null) {
            if (this.f5163k) {
                m8165j();
            } else {
                this.f5162j = null;
                return this;
            }
        }
        m8164e(false);
        this.f5155c.write("null");
        return this;
    }

    public void flush() {
        if (this.f5157e == 0) {
            throw new IllegalStateException("JsonWriter is closed.");
        }
        this.f5155c.flush();
    }

    /* renamed from: g */
    public boolean m8182g() {
        return this.f5160h;
    }

    /* renamed from: h */
    public final boolean m8183h() {
        return this.f5161i;
    }

    /* renamed from: i */
    public final boolean m8184i() {
        return this.f5163k;
    }
}
