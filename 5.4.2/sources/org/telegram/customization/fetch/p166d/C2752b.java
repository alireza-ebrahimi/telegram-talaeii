package org.telegram.customization.fetch.p166d;

import android.support.v4.p022f.C0464a;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* renamed from: org.telegram.customization.fetch.d.b */
public final class C2752b {
    /* renamed from: a */
    private final String f9058a;
    /* renamed from: b */
    private final String f9059b;
    /* renamed from: c */
    private final Map<String, String> f9060c = new C0464a();
    /* renamed from: d */
    private int f9061d = 600;

    public C2752b(String str, String str2) {
        this.f9058a = str;
        this.f9059b = str2;
    }

    /* renamed from: a */
    public String m12746a() {
        return this.f9058a;
    }

    /* renamed from: a */
    public C2752b m12747a(int i) {
        this.f9061d = 600;
        if (i == 601) {
            this.f9061d = 601;
        }
        return this;
    }

    /* renamed from: b */
    public String m12748b() {
        return this.f9059b;
    }

    /* renamed from: c */
    public List<C2751a> m12749c() {
        List<C2751a> arrayList = new ArrayList(this.f9060c.size());
        for (String str : this.f9060c.keySet()) {
            arrayList.add(new C2751a(str, (String) this.f9060c.get(str)));
        }
        return arrayList;
    }

    /* renamed from: d */
    public int m12750d() {
        return this.f9061d;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (C2751a c2751a : m12749c()) {
            stringBuilder.append(c2751a.toString()).append(",");
        }
        if (this.f9060c.size() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return "{url:" + this.f9058a + " ,filePath:" + this.f9059b + ",headers:{" + stringBuilder.toString() + "},priority:" + this.f9061d + "}";
    }
}
