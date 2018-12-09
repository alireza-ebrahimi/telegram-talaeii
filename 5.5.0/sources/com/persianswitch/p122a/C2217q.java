package com.persianswitch.p122a;

import com.persianswitch.p122a.p123a.C2187l;
import com.persianswitch.p122a.p123a.p127b.C2148g;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/* renamed from: com.persianswitch.a.q */
public final class C2217q {
    /* renamed from: a */
    private final String[] f6791a;

    /* renamed from: com.persianswitch.a.q$a */
    public static final class C2216a {
        /* renamed from: a */
        private final List<String> f6790a = new ArrayList(20);

        /* renamed from: d */
        private void m10015d(String str, String str2) {
            if (str == null) {
                throw new NullPointerException("name == null");
            } else if (str.isEmpty()) {
                throw new IllegalArgumentException("name is empty");
            } else {
                int i;
                char charAt;
                int length = str.length();
                for (i = 0; i < length; i++) {
                    charAt = str.charAt(i);
                    if (charAt <= '\u001f' || charAt >= '') {
                        throw new IllegalArgumentException(C2187l.m9892a("Unexpected char %#04x at %d in header name: %s", Integer.valueOf(charAt), Integer.valueOf(i), str));
                    }
                }
                if (str2 == null) {
                    throw new NullPointerException("value == null");
                }
                length = str2.length();
                for (i = 0; i < length; i++) {
                    charAt = str2.charAt(i);
                    if (charAt <= '\u001f' || charAt >= '') {
                        throw new IllegalArgumentException(C2187l.m9892a("Unexpected char %#04x at %d in %s value: %s", Integer.valueOf(charAt), Integer.valueOf(i), str, str2));
                    }
                }
            }
        }

        /* renamed from: a */
        C2216a m10016a(String str) {
            int indexOf = str.indexOf(":", 1);
            return indexOf != -1 ? m10020b(str.substring(0, indexOf), str.substring(indexOf + 1)) : str.startsWith(":") ? m10020b(TtmlNode.ANONYMOUS_REGION_ID, str.substring(1)) : m10020b(TtmlNode.ANONYMOUS_REGION_ID, str);
        }

        /* renamed from: a */
        public C2216a m10017a(String str, String str2) {
            m10015d(str, str2);
            return m10020b(str, str2);
        }

        /* renamed from: a */
        public C2217q m10018a() {
            return new C2217q();
        }

        /* renamed from: b */
        public C2216a m10019b(String str) {
            int i = 0;
            while (i < this.f6790a.size()) {
                if (str.equalsIgnoreCase((String) this.f6790a.get(i))) {
                    this.f6790a.remove(i);
                    this.f6790a.remove(i);
                    i -= 2;
                }
                i += 2;
            }
            return this;
        }

        /* renamed from: b */
        C2216a m10020b(String str, String str2) {
            this.f6790a.add(str);
            this.f6790a.add(str2.trim());
            return this;
        }

        /* renamed from: c */
        public C2216a m10021c(String str, String str2) {
            m10015d(str, str2);
            m10019b(str);
            m10020b(str, str2);
            return this;
        }
    }

    private C2217q(C2216a c2216a) {
        this.f6791a = (String[]) c2216a.f6790a.toArray(new String[c2216a.f6790a.size()]);
    }

    /* renamed from: a */
    private static String m10022a(String[] strArr, String str) {
        for (int length = strArr.length - 2; length >= 0; length -= 2) {
            if (str.equalsIgnoreCase(strArr[length])) {
                return strArr[length + 1];
            }
        }
        return null;
    }

    /* renamed from: a */
    public int m10023a() {
        return this.f6791a.length / 2;
    }

    /* renamed from: a */
    public String m10024a(int i) {
        return this.f6791a[i * 2];
    }

    /* renamed from: a */
    public String m10025a(String str) {
        return C2217q.m10022a(this.f6791a, str);
    }

    /* renamed from: b */
    public C2216a m10026b() {
        C2216a c2216a = new C2216a();
        Collections.addAll(c2216a.f6790a, this.f6791a);
        return c2216a;
    }

    /* renamed from: b */
    public String m10027b(int i) {
        return this.f6791a[(i * 2) + 1];
    }

    /* renamed from: b */
    public Date m10028b(String str) {
        String a = m10025a(str);
        return a != null ? C2148g.m9694a(a) : null;
    }

    /* renamed from: c */
    public List<String> m10029c(String str) {
        int a = m10023a();
        List list = null;
        for (int i = 0; i < a; i++) {
            if (str.equalsIgnoreCase(m10024a(i))) {
                if (list == null) {
                    list = new ArrayList(2);
                }
                list.add(m10027b(i));
            }
        }
        return list != null ? Collections.unmodifiableList(list) : Collections.emptyList();
    }

    public boolean equals(Object obj) {
        return (obj instanceof C2217q) && Arrays.equals(((C2217q) obj).f6791a, this.f6791a);
    }

    public int hashCode() {
        return Arrays.hashCode(this.f6791a);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        int a = m10023a();
        for (int i = 0; i < a; i++) {
            stringBuilder.append(m10024a(i)).append(": ").append(m10027b(i)).append("\n");
        }
        return stringBuilder.toString();
    }
}
