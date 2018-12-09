package org.telegram.customization.fetch.p166d;

import java.util.List;

/* renamed from: org.telegram.customization.fetch.d.c */
public final class C2753c {
    /* renamed from: a */
    private final long f9062a;
    /* renamed from: b */
    private final int f9063b;
    /* renamed from: c */
    private final String f9064c;
    /* renamed from: d */
    private final String f9065d;
    /* renamed from: e */
    private final int f9066e;
    /* renamed from: f */
    private final long f9067f;
    /* renamed from: g */
    private final long f9068g;
    /* renamed from: h */
    private final int f9069h;
    /* renamed from: i */
    private final List<C2751a> f9070i;
    /* renamed from: j */
    private final int f9071j;

    public C2753c(long j, int i, String str, String str2, int i2, long j2, long j3, int i3, List<C2751a> list, int i4) {
        if (str == null) {
            throw new NullPointerException("Url cannot be null");
        } else if (str2 == null) {
            throw new NullPointerException("FilePath cannot be null");
        } else if (list == null) {
            throw new NullPointerException("Headers cannot be null");
        } else {
            this.f9062a = j;
            this.f9063b = i;
            this.f9064c = str;
            this.f9065d = str2;
            this.f9066e = i2;
            this.f9067f = j2;
            this.f9068g = j3;
            this.f9069h = i3;
            this.f9070i = list;
            this.f9071j = i4;
        }
    }

    /* renamed from: a */
    public long m12751a() {
        return this.f9062a;
    }

    /* renamed from: b */
    public int m12752b() {
        return this.f9063b;
    }

    /* renamed from: c */
    public String m12753c() {
        return this.f9064c;
    }

    /* renamed from: d */
    public String m12754d() {
        return this.f9065d;
    }

    /* renamed from: e */
    public int m12755e() {
        return this.f9066e;
    }

    /* renamed from: f */
    public long m12756f() {
        return this.f9067f;
    }

    /* renamed from: g */
    public long m12757g() {
        return this.f9068g;
    }

    /* renamed from: h */
    public int m12758h() {
        return this.f9069h;
    }

    /* renamed from: i */
    public List<C2751a> m12759i() {
        return this.f9070i;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (C2751a c2751a : this.f9070i) {
            stringBuilder.append(c2751a.toString()).append(",");
        }
        if (this.f9070i.size() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return "{id:" + this.f9062a + ",status:" + this.f9063b + ",url:" + this.f9064c + ",filePath:" + this.f9065d + ",progress:" + this.f9066e + ",fileSize:" + this.f9068g + ",error:" + this.f9069h + ",headers:{" + stringBuilder.toString() + "},priority:" + this.f9071j + "}";
    }
}
