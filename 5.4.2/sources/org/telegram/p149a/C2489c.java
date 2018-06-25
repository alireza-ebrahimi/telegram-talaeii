package org.telegram.p149a;

/* renamed from: org.telegram.a.c */
public class C2489c {
    /* renamed from: a */
    public int f8323a;
    /* renamed from: b */
    public int f8324b;
    /* renamed from: c */
    public int f8325c;
    /* renamed from: d */
    public int f8326d;
    /* renamed from: e */
    public int f8327e;
    /* renamed from: f */
    public int f8328f;
    /* renamed from: g */
    public int f8329g;
    /* renamed from: h */
    public int f8330h;
    /* renamed from: i */
    public String f8331i;
    /* renamed from: j */
    public boolean f8332j;
    /* renamed from: k */
    public boolean f8333k;

    /* renamed from: a */
    String m12199a(String str, String str2, String str3) {
        StringBuilder stringBuilder = new StringBuilder(20);
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        while (i < this.f8331i.length()) {
            char charAt = this.f8331i.charAt(i);
            switch (charAt) {
                case '#':
                    if (i2 >= str.length()) {
                        if (i3 == 0) {
                            break;
                        }
                        stringBuilder.append(" ");
                        break;
                    }
                    stringBuilder.append(str.substring(i2, i2 + 1));
                    i2++;
                    continue;
                case '(':
                    if (i2 < str.length()) {
                        i3 = 1;
                        break;
                    }
                    break;
                case 'c':
                    if (str2 == null) {
                        i5 = 1;
                        break;
                    }
                    stringBuilder.append(str2);
                    i5 = 1;
                    continue;
                case 'n':
                    if (str3 == null) {
                        i4 = 1;
                        break;
                    }
                    stringBuilder.append(str3);
                    i4 = 1;
                    continue;
            }
            if (!(charAt == ' ' && i > 0 && ((this.f8331i.charAt(i - 1) == 'n' && str3 == null) || (this.f8331i.charAt(i - 1) == 'c' && str2 == null))) && (i2 < str.length() || (r3 != 0 && charAt == ')'))) {
                stringBuilder.append(this.f8331i.substring(i, i + 1));
                if (charAt == ')') {
                    i3 = 0;
                }
            }
            i++;
        }
        if (str2 != null && r5 == 0) {
            stringBuilder.insert(0, String.format("%s ", new Object[]{str2}));
        } else if (str3 != null && r4 == 0) {
            stringBuilder.insert(0, str3);
        }
        return stringBuilder.toString();
    }
}
