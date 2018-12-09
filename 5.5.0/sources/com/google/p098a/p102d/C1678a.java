package com.google.p098a.p102d;

import com.google.p098a.p100b.C1727e;
import com.google.p098a.p100b.p101a.C1679e;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;

/* renamed from: com.google.a.d.a */
public class C1678a implements Closeable {
    /* renamed from: a */
    private static final char[] f5134a = ")]}'\n".toCharArray();
    /* renamed from: b */
    private final Reader f5135b;
    /* renamed from: c */
    private boolean f5136c = false;
    /* renamed from: d */
    private final char[] f5137d = new char[1024];
    /* renamed from: e */
    private int f5138e = 0;
    /* renamed from: f */
    private int f5139f = 0;
    /* renamed from: g */
    private int f5140g = 0;
    /* renamed from: h */
    private int f5141h = 0;
    /* renamed from: i */
    private int f5142i = 0;
    /* renamed from: j */
    private long f5143j;
    /* renamed from: k */
    private int f5144k;
    /* renamed from: l */
    private String f5145l;
    /* renamed from: m */
    private int[] f5146m = new int[32];
    /* renamed from: n */
    private int f5147n = 0;
    /* renamed from: o */
    private String[] f5148o;
    /* renamed from: p */
    private int[] f5149p;

    /* renamed from: com.google.a.d.a$1 */
    static class C17571 extends C1727e {
        C17571() {
        }

        /* renamed from: a */
        public void mo1291a(C1678a c1678a) {
            if (c1678a instanceof C1679e) {
                ((C1679e) c1678a).mo1271o();
                return;
            }
            int a = c1678a.f5142i;
            if (a == 0) {
                a = c1678a.mo1271o();
            }
            if (a == 13) {
                c1678a.f5142i = 9;
            } else if (a == 12) {
                c1678a.f5142i = 8;
            } else if (a == 14) {
                c1678a.f5142i = 10;
            } else {
                throw new IllegalStateException("Expected a name but was " + c1678a.mo1262f() + " " + " at line " + c1678a.m8118v() + " column " + c1678a.m8119w() + " path " + c1678a.m8139q());
            }
        }
    }

    static {
        C1727e.f5300a = new C17571();
    }

    public C1678a(Reader reader) {
        int[] iArr = this.f5146m;
        int i = this.f5147n;
        this.f5147n = i + 1;
        iArr[i] = 6;
        this.f5148o = new String[32];
        this.f5149p = new int[32];
        if (reader == null) {
            throw new NullPointerException("in == null");
        }
        this.f5135b = reader;
    }

    /* renamed from: A */
    private void m8099A() {
        m8106b(true);
        this.f5138e--;
        if (this.f5138e + f5134a.length <= this.f5139f || m8109b(f5134a.length)) {
            int i = 0;
            while (i < f5134a.length) {
                if (this.f5137d[this.f5138e + i] == f5134a[i]) {
                    i++;
                } else {
                    return;
                }
            }
            this.f5138e += f5134a.length;
        }
    }

    /* renamed from: a */
    private void m8102a(int i) {
        if (this.f5147n == this.f5146m.length) {
            Object obj = new int[(this.f5147n * 2)];
            Object obj2 = new int[(this.f5147n * 2)];
            Object obj3 = new String[(this.f5147n * 2)];
            System.arraycopy(this.f5146m, 0, obj, 0, this.f5147n);
            System.arraycopy(this.f5149p, 0, obj2, 0, this.f5147n);
            System.arraycopy(this.f5148o, 0, obj3, 0, this.f5147n);
            this.f5146m = obj;
            this.f5149p = obj2;
            this.f5148o = obj3;
        }
        int[] iArr = this.f5146m;
        int i2 = this.f5147n;
        this.f5147n = i2 + 1;
        iArr[i2] = i;
    }

    /* renamed from: a */
    private boolean m8103a(char c) {
        switch (c) {
            case '\t':
            case '\n':
            case '\f':
            case '\r':
            case ' ':
            case ',':
            case ':':
            case '[':
            case ']':
            case '{':
            case '}':
                break;
            case '#':
            case '/':
            case ';':
            case '=':
            case '\\':
                m8120x();
                break;
            default:
                return true;
        }
        return false;
    }

    /* renamed from: a */
    private boolean m8104a(String str) {
        while (true) {
            if (this.f5138e + str.length() > this.f5139f && !m8109b(str.length())) {
                return false;
            }
            if (this.f5137d[this.f5138e] == '\n') {
                this.f5140g++;
                this.f5141h = this.f5138e + 1;
            } else {
                int i = 0;
                while (i < str.length()) {
                    if (this.f5137d[this.f5138e + i] == str.charAt(i)) {
                        i++;
                    }
                }
                return true;
            }
            this.f5138e++;
        }
    }

    /* renamed from: b */
    private int m8106b(boolean z) {
        char[] cArr = this.f5137d;
        int i = this.f5138e;
        int i2 = this.f5139f;
        while (true) {
            if (i == i2) {
                this.f5138e = i;
                if (m8109b(1)) {
                    i = this.f5138e;
                    i2 = this.f5139f;
                } else if (!z) {
                    return -1;
                } else {
                    throw new EOFException("End of input at line " + m8118v() + " column " + m8119w());
                }
            }
            int i3 = i + 1;
            char c = cArr[i];
            if (c == '\n') {
                this.f5140g++;
                this.f5141h = i3;
                i = i3;
            } else if (c == ' ' || c == '\r') {
                i = i3;
            } else if (c == '\t') {
                i = i3;
            } else if (c == '/') {
                this.f5138e = i3;
                if (i3 == i2) {
                    this.f5138e--;
                    boolean b = m8109b(2);
                    this.f5138e++;
                    if (!b) {
                        return c;
                    }
                }
                m8120x();
                switch (cArr[this.f5138e]) {
                    case '*':
                        this.f5138e++;
                        if (m8104a("*/")) {
                            i = this.f5138e + 2;
                            i2 = this.f5139f;
                            break;
                        }
                        throw m8107b("Unterminated comment");
                    case '/':
                        this.f5138e++;
                        m8121y();
                        i = this.f5138e;
                        i2 = this.f5139f;
                        break;
                    default:
                        return c;
                }
            } else if (c == '#') {
                this.f5138e = i3;
                m8120x();
                m8121y();
                i = this.f5138e;
                i2 = this.f5139f;
            } else {
                this.f5138e = i3;
                return c;
            }
        }
    }

    /* renamed from: b */
    private IOException m8107b(String str) {
        throw new C1759d(str + " at line " + m8118v() + " column " + m8119w() + " path " + m8139q());
    }

    /* renamed from: b */
    private String m8108b(char c) {
        char[] cArr = this.f5137d;
        StringBuilder stringBuilder = new StringBuilder();
        do {
            int i = this.f5138e;
            int i2 = this.f5139f;
            int i3 = i;
            while (i3 < i2) {
                int i4 = i3 + 1;
                char c2 = cArr[i3];
                if (c2 == c) {
                    this.f5138e = i4;
                    stringBuilder.append(cArr, i, (i4 - i) - 1);
                    return stringBuilder.toString();
                }
                if (c2 == '\\') {
                    this.f5138e = i4;
                    stringBuilder.append(cArr, i, (i4 - i) - 1);
                    stringBuilder.append(m8122z());
                    i = this.f5138e;
                    i2 = this.f5139f;
                    i4 = i;
                } else if (c2 == '\n') {
                    this.f5140g++;
                    this.f5141h = i4;
                }
                i3 = i4;
            }
            stringBuilder.append(cArr, i, i3 - i);
            this.f5138e = i3;
        } while (m8109b(1));
        throw m8107b("Unterminated string");
    }

    /* renamed from: b */
    private boolean m8109b(int i) {
        Object obj = this.f5137d;
        this.f5141h -= this.f5138e;
        if (this.f5139f != this.f5138e) {
            this.f5139f -= this.f5138e;
            System.arraycopy(obj, this.f5138e, obj, 0, this.f5139f);
        } else {
            this.f5139f = 0;
        }
        this.f5138e = 0;
        do {
            int read = this.f5135b.read(obj, this.f5139f, obj.length - this.f5139f);
            if (read == -1) {
                return false;
            }
            this.f5139f = read + this.f5139f;
            if (this.f5140g == 0 && this.f5141h == 0 && this.f5139f > 0 && obj[0] == '﻿') {
                this.f5138e++;
                this.f5141h++;
                i++;
            }
        } while (this.f5139f < i);
        return true;
    }

    /* renamed from: c */
    private void m8111c(char c) {
        char[] cArr = this.f5137d;
        do {
            int i = this.f5138e;
            int i2 = this.f5139f;
            int i3 = i;
            while (i3 < i2) {
                i = i3 + 1;
                char c2 = cArr[i3];
                if (c2 == c) {
                    this.f5138e = i;
                    return;
                }
                if (c2 == '\\') {
                    this.f5138e = i;
                    m8122z();
                    i = this.f5138e;
                    i2 = this.f5139f;
                } else if (c2 == '\n') {
                    this.f5140g++;
                    this.f5141h = i;
                }
                i3 = i;
            }
            this.f5138e = i3;
        } while (m8109b(1));
        throw m8107b("Unterminated string");
    }

    /* renamed from: o */
    private int mo1271o() {
        int b;
        int i = this.f5146m[this.f5147n - 1];
        if (i == 1) {
            this.f5146m[this.f5147n - 1] = 2;
        } else if (i == 2) {
            switch (m8106b(true)) {
                case 44:
                    break;
                case 59:
                    m8120x();
                    break;
                case 93:
                    this.f5142i = 4;
                    return 4;
                default:
                    throw m8107b("Unterminated array");
            }
        } else if (i == 3 || i == 5) {
            this.f5146m[this.f5147n - 1] = 4;
            if (i == 5) {
                switch (m8106b(true)) {
                    case 44:
                        break;
                    case 59:
                        m8120x();
                        break;
                    case 125:
                        this.f5142i = 2;
                        return 2;
                    default:
                        throw m8107b("Unterminated object");
                }
            }
            b = m8106b(true);
            switch (b) {
                case 34:
                    this.f5142i = 13;
                    return 13;
                case 39:
                    m8120x();
                    this.f5142i = 12;
                    return 12;
                case 125:
                    if (i != 5) {
                        this.f5142i = 2;
                        return 2;
                    }
                    throw m8107b("Expected name");
                default:
                    m8120x();
                    this.f5138e--;
                    if (m8103a((char) b)) {
                        this.f5142i = 14;
                        return 14;
                    }
                    throw m8107b("Expected name");
            }
        } else if (i == 4) {
            this.f5146m[this.f5147n - 1] = 5;
            switch (m8106b(true)) {
                case 58:
                    break;
                case 61:
                    m8120x();
                    if ((this.f5138e < this.f5139f || m8109b(1)) && this.f5137d[this.f5138e] == '>') {
                        this.f5138e++;
                        break;
                    }
                default:
                    throw m8107b("Expected ':'");
            }
        } else if (i == 6) {
            if (this.f5136c) {
                m8099A();
            }
            this.f5146m[this.f5147n - 1] = 7;
        } else if (i == 7) {
            if (m8106b(false) == -1) {
                this.f5142i = 17;
                return 17;
            }
            m8120x();
            this.f5138e--;
        } else if (i == 8) {
            throw new IllegalStateException("JsonReader is closed");
        }
        switch (m8106b(true)) {
            case 34:
                if (this.f5147n == 1) {
                    m8120x();
                }
                this.f5142i = 9;
                return 9;
            case 39:
                m8120x();
                this.f5142i = 8;
                return 8;
            case 44:
            case 59:
                break;
            case 91:
                this.f5142i = 3;
                return 3;
            case 93:
                if (i == 1) {
                    this.f5142i = 4;
                    return 4;
                }
                break;
            case 123:
                this.f5142i = 1;
                return 1;
            default:
                this.f5138e--;
                if (this.f5147n == 1) {
                    m8120x();
                }
                b = m8114r();
                if (b != 0) {
                    return b;
                }
                b = m8115s();
                if (b != 0) {
                    return b;
                }
                if (m8103a(this.f5137d[this.f5138e])) {
                    m8120x();
                    this.f5142i = 10;
                    return 10;
                }
                throw m8107b("Expected value");
        }
        if (i == 1 || i == 2) {
            m8120x();
            this.f5138e--;
            this.f5142i = 7;
            return 7;
        }
        throw m8107b("Unexpected value");
    }

    /* renamed from: r */
    private int m8114r() {
        String str;
        int i;
        char c = this.f5137d[this.f5138e];
        String str2;
        if (c == 't' || c == 'T') {
            str = "true";
            str2 = "TRUE";
            i = 5;
        } else if (c == 'f' || c == 'F') {
            str = "false";
            str2 = "FALSE";
            i = 6;
        } else if (c != 'n' && c != 'N') {
            return 0;
        } else {
            str = "null";
            str2 = "NULL";
            i = 7;
        }
        int length = str.length();
        int i2 = 1;
        while (i2 < length) {
            if (this.f5138e + i2 >= this.f5139f && !m8109b(i2 + 1)) {
                return 0;
            }
            char c2 = this.f5137d[this.f5138e + i2];
            if (c2 != str.charAt(i2) && c2 != r1.charAt(i2)) {
                return 0;
            }
            i2++;
        }
        if ((this.f5138e + length < this.f5139f || m8109b(length + 1)) && m8103a(this.f5137d[this.f5138e + length])) {
            return 0;
        }
        this.f5138e += length;
        this.f5142i = i;
        return i;
    }

    /* renamed from: s */
    private int m8115s() {
        char[] cArr = this.f5137d;
        int i = this.f5138e;
        long j = 0;
        Object obj = null;
        int i2 = 1;
        int i3 = 0;
        int i4 = 0;
        int i5 = this.f5139f;
        int i6 = i;
        while (true) {
            Object obj2;
            if (i6 + i4 == i5) {
                if (i4 == cArr.length) {
                    return 0;
                }
                if (m8109b(i4 + 1)) {
                    i6 = this.f5138e;
                    i5 = this.f5139f;
                } else if (i3 != 2 && i2 != 0 && (j != Long.MIN_VALUE || obj != null)) {
                    if (obj == null) {
                        j = -j;
                    }
                    this.f5143j = j;
                    this.f5138e += i4;
                    this.f5142i = 15;
                    return 15;
                } else if (i3 == 2 && i3 != 4 && i3 != 7) {
                    return 0;
                } else {
                    this.f5144k = i4;
                    this.f5142i = 16;
                    return 16;
                }
            }
            char c = cArr[i6 + i4];
            int i7;
            switch (c) {
                case '+':
                    if (i3 != 5) {
                        return 0;
                    }
                    i = 6;
                    i3 = i2;
                    obj2 = obj;
                    continue;
                case '-':
                    if (i3 == 0) {
                        i = 1;
                        i7 = i2;
                        obj2 = 1;
                        i3 = i7;
                        continue;
                    } else if (i3 == 5) {
                        i = 6;
                        i3 = i2;
                        obj2 = obj;
                        break;
                    } else {
                        return 0;
                    }
                case '.':
                    if (i3 != 2) {
                        return 0;
                    }
                    i = 3;
                    i3 = i2;
                    obj2 = obj;
                    continue;
                case 'E':
                case 'e':
                    if (i3 != 2 && i3 != 4) {
                        return 0;
                    }
                    i = 5;
                    i3 = i2;
                    obj2 = obj;
                    continue;
                default:
                    if (c >= '0' && c <= '9') {
                        if (i3 != 1 && i3 != 0) {
                            if (i3 != 2) {
                                if (i3 != 3) {
                                    if (i3 != 5 && i3 != 6) {
                                        i = i3;
                                        i3 = i2;
                                        obj2 = obj;
                                        break;
                                    }
                                    i = 7;
                                    i3 = i2;
                                    obj2 = obj;
                                    break;
                                }
                                i = 4;
                                i3 = i2;
                                obj2 = obj;
                                break;
                            } else if (j != 0) {
                                long j2 = (10 * j) - ((long) (c - 48));
                                i = (j > -922337203685477580L || (j == -922337203685477580L && j2 < j)) ? 1 : 0;
                                i &= i2;
                                obj2 = obj;
                                j = j2;
                                i7 = i3;
                                i3 = i;
                                i = i7;
                                break;
                            } else {
                                return 0;
                            }
                        }
                        j = (long) (-(c - 48));
                        i = 2;
                        i3 = i2;
                        obj2 = obj;
                        continue;
                    } else if (m8103a(c)) {
                        return 0;
                    }
                    break;
            }
            if (i3 != 2) {
            }
            if (i3 == 2) {
            }
            this.f5144k = i4;
            this.f5142i = 16;
            return 16;
            i4++;
            obj = obj2;
            i2 = i3;
            i3 = i;
        }
    }

    /* renamed from: t */
    private String m8116t() {
        StringBuilder stringBuilder = null;
        int i = 0;
        while (true) {
            String str;
            if (this.f5138e + i < this.f5139f) {
                switch (this.f5137d[this.f5138e + i]) {
                    case '\t':
                    case '\n':
                    case '\f':
                    case '\r':
                    case ' ':
                    case ',':
                    case ':':
                    case '[':
                    case ']':
                    case '{':
                    case '}':
                        break;
                    case '#':
                    case '/':
                    case ';':
                    case '=':
                    case '\\':
                        m8120x();
                        break;
                    default:
                        i++;
                        continue;
                }
            } else if (i >= this.f5137d.length) {
                if (stringBuilder == null) {
                    stringBuilder = new StringBuilder();
                }
                stringBuilder.append(this.f5137d, this.f5138e, i);
                this.f5138e = i + this.f5138e;
                i = !m8109b(1) ? 0 : 0;
            } else if (m8109b(i + 1)) {
            }
            if (stringBuilder == null) {
                str = new String(this.f5137d, this.f5138e, i);
            } else {
                stringBuilder.append(this.f5137d, this.f5138e, i);
                str = stringBuilder.toString();
            }
            this.f5138e = i + this.f5138e;
            return str;
        }
    }

    /* renamed from: u */
    private void m8117u() {
        do {
            int i = 0;
            while (this.f5138e + i < this.f5139f) {
                switch (this.f5137d[this.f5138e + i]) {
                    case '\t':
                    case '\n':
                    case '\f':
                    case '\r':
                    case ' ':
                    case ',':
                    case ':':
                    case '[':
                    case ']':
                    case '{':
                    case '}':
                        break;
                    case '#':
                    case '/':
                    case ';':
                    case '=':
                    case '\\':
                        m8120x();
                        break;
                    default:
                        i++;
                }
                this.f5138e = i + this.f5138e;
                return;
            }
            this.f5138e = i + this.f5138e;
        } while (m8109b(1));
    }

    /* renamed from: v */
    private int m8118v() {
        return this.f5140g + 1;
    }

    /* renamed from: w */
    private int m8119w() {
        return (this.f5138e - this.f5141h) + 1;
    }

    /* renamed from: x */
    private void m8120x() {
        if (!this.f5136c) {
            throw m8107b("Use JsonReader.setLenient(true) to accept malformed JSON");
        }
    }

    /* renamed from: y */
    private void m8121y() {
        char c;
        do {
            if (this.f5138e < this.f5139f || m8109b(1)) {
                char[] cArr = this.f5137d;
                int i = this.f5138e;
                this.f5138e = i + 1;
                c = cArr[i];
                if (c == '\n') {
                    this.f5140g++;
                    this.f5141h = this.f5138e;
                    return;
                }
            } else {
                return;
            }
        } while (c != '\r');
    }

    /* renamed from: z */
    private char m8122z() {
        if (this.f5138e != this.f5139f || m8109b(1)) {
            char[] cArr = this.f5137d;
            int i = this.f5138e;
            this.f5138e = i + 1;
            char c = cArr[i];
            switch (c) {
                case '\n':
                    this.f5140g++;
                    this.f5141h = this.f5138e;
                    return c;
                case 'b':
                    return '\b';
                case 'f':
                    return '\f';
                case 'n':
                    return '\n';
                case 'r':
                    return '\r';
                case 't':
                    return '\t';
                case 'u':
                    if (this.f5138e + 4 <= this.f5139f || m8109b(4)) {
                        int i2 = this.f5138e;
                        int i3 = i2 + 4;
                        int i4 = i2;
                        c = '\u0000';
                        for (i = i4; i < i3; i++) {
                            char c2 = this.f5137d[i];
                            c = (char) (c << 4);
                            if (c2 >= '0' && c2 <= '9') {
                                c = (char) (c + (c2 - 48));
                            } else if (c2 >= 'a' && c2 <= 'f') {
                                c = (char) (c + ((c2 - 97) + 10));
                            } else if (c2 < 'A' || c2 > 'F') {
                                throw new NumberFormatException("\\u" + new String(this.f5137d, this.f5138e, 4));
                            } else {
                                c = (char) (c + ((c2 - 65) + 10));
                            }
                        }
                        this.f5138e += 4;
                        return c;
                    }
                    throw m8107b("Unterminated escape sequence");
                default:
                    return c;
            }
        }
        throw m8107b("Unterminated escape sequence");
    }

    /* renamed from: a */
    public void mo1256a() {
        int i = this.f5142i;
        if (i == 0) {
            i = mo1271o();
        }
        if (i == 3) {
            m8102a(1);
            this.f5149p[this.f5147n - 1] = 0;
            this.f5142i = 0;
            return;
        }
        throw new IllegalStateException("Expected BEGIN_ARRAY but was " + mo1262f() + " at line " + m8118v() + " column " + m8119w() + " path " + m8139q());
    }

    /* renamed from: a */
    public final void m8124a(boolean z) {
        this.f5136c = z;
    }

    /* renamed from: b */
    public void mo1257b() {
        int i = this.f5142i;
        if (i == 0) {
            i = mo1271o();
        }
        if (i == 4) {
            this.f5147n--;
            this.f5142i = 0;
            return;
        }
        throw new IllegalStateException("Expected END_ARRAY but was " + mo1262f() + " at line " + m8118v() + " column " + m8119w() + " path " + m8139q());
    }

    /* renamed from: c */
    public void mo1258c() {
        int i = this.f5142i;
        if (i == 0) {
            i = mo1271o();
        }
        if (i == 1) {
            m8102a(3);
            this.f5142i = 0;
            return;
        }
        throw new IllegalStateException("Expected BEGIN_OBJECT but was " + mo1262f() + " at line " + m8118v() + " column " + m8119w() + " path " + m8139q());
    }

    public void close() {
        this.f5142i = 0;
        this.f5146m[0] = 8;
        this.f5147n = 1;
        this.f5135b.close();
    }

    /* renamed from: d */
    public void mo1260d() {
        int i = this.f5142i;
        if (i == 0) {
            i = mo1271o();
        }
        if (i == 2) {
            this.f5147n--;
            this.f5148o[this.f5147n] = null;
            this.f5142i = 0;
            return;
        }
        throw new IllegalStateException("Expected END_OBJECT but was " + mo1262f() + " at line " + m8118v() + " column " + m8119w() + " path " + m8139q());
    }

    /* renamed from: e */
    public boolean mo1261e() {
        int i = this.f5142i;
        if (i == 0) {
            i = mo1271o();
        }
        return (i == 2 || i == 4) ? false : true;
    }

    /* renamed from: f */
    public C1758b mo1262f() {
        int i = this.f5142i;
        if (i == 0) {
            i = mo1271o();
        }
        switch (i) {
            case 1:
                return C1758b.BEGIN_OBJECT;
            case 2:
                return C1758b.END_OBJECT;
            case 3:
                return C1758b.BEGIN_ARRAY;
            case 4:
                return C1758b.END_ARRAY;
            case 5:
            case 6:
                return C1758b.BOOLEAN;
            case 7:
                return C1758b.NULL;
            case 8:
            case 9:
            case 10:
            case 11:
                return C1758b.STRING;
            case 12:
            case 13:
            case 14:
                return C1758b.NAME;
            case 15:
            case 16:
                return C1758b.NUMBER;
            case 17:
                return C1758b.END_DOCUMENT;
            default:
                throw new AssertionError();
        }
    }

    /* renamed from: g */
    public String mo1263g() {
        String t;
        int i = this.f5142i;
        if (i == 0) {
            i = mo1271o();
        }
        if (i == 14) {
            t = m8116t();
        } else if (i == 12) {
            t = m8108b('\'');
        } else if (i == 13) {
            t = m8108b('\"');
        } else {
            throw new IllegalStateException("Expected a name but was " + mo1262f() + " at line " + m8118v() + " column " + m8119w() + " path " + m8139q());
        }
        this.f5142i = 0;
        this.f5148o[this.f5147n - 1] = t;
        return t;
    }

    /* renamed from: h */
    public String mo1264h() {
        String t;
        int i = this.f5142i;
        if (i == 0) {
            i = mo1271o();
        }
        if (i == 10) {
            t = m8116t();
        } else if (i == 8) {
            t = m8108b('\'');
        } else if (i == 9) {
            t = m8108b('\"');
        } else if (i == 11) {
            t = this.f5145l;
            this.f5145l = null;
        } else if (i == 15) {
            t = Long.toString(this.f5143j);
        } else if (i == 16) {
            t = new String(this.f5137d, this.f5138e, this.f5144k);
            this.f5138e += this.f5144k;
        } else {
            throw new IllegalStateException("Expected a string but was " + mo1262f() + " at line " + m8118v() + " column " + m8119w() + " path " + m8139q());
        }
        this.f5142i = 0;
        int[] iArr = this.f5149p;
        int i2 = this.f5147n - 1;
        iArr[i2] = iArr[i2] + 1;
        return t;
    }

    /* renamed from: i */
    public boolean mo1265i() {
        int i = this.f5142i;
        if (i == 0) {
            i = mo1271o();
        }
        if (i == 5) {
            this.f5142i = 0;
            int[] iArr = this.f5149p;
            i = this.f5147n - 1;
            iArr[i] = iArr[i] + 1;
            return true;
        } else if (i == 6) {
            this.f5142i = 0;
            int[] iArr2 = this.f5149p;
            int i2 = this.f5147n - 1;
            iArr2[i2] = iArr2[i2] + 1;
            return false;
        } else {
            throw new IllegalStateException("Expected a boolean but was " + mo1262f() + " at line " + m8118v() + " column " + m8119w() + " path " + m8139q());
        }
    }

    /* renamed from: j */
    public void mo1266j() {
        int i = this.f5142i;
        if (i == 0) {
            i = mo1271o();
        }
        if (i == 7) {
            this.f5142i = 0;
            int[] iArr = this.f5149p;
            int i2 = this.f5147n - 1;
            iArr[i2] = iArr[i2] + 1;
            return;
        }
        throw new IllegalStateException("Expected null but was " + mo1262f() + " at line " + m8118v() + " column " + m8119w() + " path " + m8139q());
    }

    /* renamed from: k */
    public double mo1267k() {
        int i = this.f5142i;
        if (i == 0) {
            i = mo1271o();
        }
        if (i == 15) {
            this.f5142i = 0;
            int[] iArr = this.f5149p;
            int i2 = this.f5147n - 1;
            iArr[i2] = iArr[i2] + 1;
            return (double) this.f5143j;
        }
        if (i == 16) {
            this.f5145l = new String(this.f5137d, this.f5138e, this.f5144k);
            this.f5138e += this.f5144k;
        } else if (i == 8 || i == 9) {
            this.f5145l = m8108b(i == 8 ? '\'' : '\"');
        } else if (i == 10) {
            this.f5145l = m8116t();
        } else if (i != 11) {
            throw new IllegalStateException("Expected a double but was " + mo1262f() + " at line " + m8118v() + " column " + m8119w() + " path " + m8139q());
        }
        this.f5142i = 11;
        double parseDouble = Double.parseDouble(this.f5145l);
        if (this.f5136c || !(Double.isNaN(parseDouble) || Double.isInfinite(parseDouble))) {
            this.f5145l = null;
            this.f5142i = 0;
            int[] iArr2 = this.f5149p;
            int i3 = this.f5147n - 1;
            iArr2[i3] = iArr2[i3] + 1;
            return parseDouble;
        }
        throw new C1759d("JSON forbids NaN and infinities: " + parseDouble + " at line " + m8118v() + " column " + m8119w() + " path " + m8139q());
    }

    /* renamed from: l */
    public long mo1268l() {
        int i = this.f5142i;
        if (i == 0) {
            i = mo1271o();
        }
        if (i == 15) {
            this.f5142i = 0;
            int[] iArr = this.f5149p;
            int i2 = this.f5147n - 1;
            iArr[i2] = iArr[i2] + 1;
            return this.f5143j;
        }
        long parseLong;
        if (i == 16) {
            this.f5145l = new String(this.f5137d, this.f5138e, this.f5144k);
            this.f5138e += this.f5144k;
        } else if (i == 8 || i == 9) {
            this.f5145l = m8108b(i == 8 ? '\'' : '\"');
            try {
                parseLong = Long.parseLong(this.f5145l);
                this.f5142i = 0;
                int[] iArr2 = this.f5149p;
                int i3 = this.f5147n - 1;
                iArr2[i3] = iArr2[i3] + 1;
                return parseLong;
            } catch (NumberFormatException e) {
            }
        } else {
            throw new IllegalStateException("Expected a long but was " + mo1262f() + " at line " + m8118v() + " column " + m8119w() + " path " + m8139q());
        }
        this.f5142i = 11;
        double parseDouble = Double.parseDouble(this.f5145l);
        parseLong = (long) parseDouble;
        if (((double) parseLong) != parseDouble) {
            throw new NumberFormatException("Expected a long but was " + this.f5145l + " at line " + m8118v() + " column " + m8119w() + " path " + m8139q());
        }
        this.f5145l = null;
        this.f5142i = 0;
        iArr2 = this.f5149p;
        i3 = this.f5147n - 1;
        iArr2[i3] = iArr2[i3] + 1;
        return parseLong;
    }

    /* renamed from: m */
    public int mo1269m() {
        int i = this.f5142i;
        if (i == 0) {
            i = mo1271o();
        }
        int[] iArr;
        int i2;
        if (i == 15) {
            i = (int) this.f5143j;
            if (this.f5143j != ((long) i)) {
                throw new NumberFormatException("Expected an int but was " + this.f5143j + " at line " + m8118v() + " column " + m8119w() + " path " + m8139q());
            }
            this.f5142i = 0;
            iArr = this.f5149p;
            i2 = this.f5147n - 1;
            iArr[i2] = iArr[i2] + 1;
        } else {
            if (i == 16) {
                this.f5145l = new String(this.f5137d, this.f5138e, this.f5144k);
                this.f5138e += this.f5144k;
            } else if (i == 8 || i == 9) {
                this.f5145l = m8108b(i == 8 ? '\'' : '\"');
                try {
                    i = Integer.parseInt(this.f5145l);
                    this.f5142i = 0;
                    iArr = this.f5149p;
                    i2 = this.f5147n - 1;
                    iArr[i2] = iArr[i2] + 1;
                } catch (NumberFormatException e) {
                }
            } else {
                throw new IllegalStateException("Expected an int but was " + mo1262f() + " at line " + m8118v() + " column " + m8119w() + " path " + m8139q());
            }
            this.f5142i = 11;
            double parseDouble = Double.parseDouble(this.f5145l);
            i = (int) parseDouble;
            if (((double) i) != parseDouble) {
                throw new NumberFormatException("Expected an int but was " + this.f5145l + " at line " + m8118v() + " column " + m8119w() + " path " + m8139q());
            }
            this.f5145l = null;
            this.f5142i = 0;
            iArr = this.f5149p;
            i2 = this.f5147n - 1;
            iArr[i2] = iArr[i2] + 1;
        }
        return i;
    }

    /* renamed from: n */
    public void mo1270n() {
        int i = 0;
        do {
            int i2 = this.f5142i;
            if (i2 == 0) {
                i2 = mo1271o();
            }
            if (i2 == 3) {
                m8102a(1);
                i++;
            } else if (i2 == 1) {
                m8102a(3);
                i++;
            } else if (i2 == 4) {
                this.f5147n--;
                i--;
            } else if (i2 == 2) {
                this.f5147n--;
                i--;
            } else if (i2 == 14 || i2 == 10) {
                m8117u();
            } else if (i2 == 8 || i2 == 12) {
                m8111c('\'');
            } else if (i2 == 9 || i2 == 13) {
                m8111c('\"');
            } else if (i2 == 16) {
                this.f5138e += this.f5144k;
            }
            this.f5142i = 0;
        } while (i != 0);
        int[] iArr = this.f5149p;
        int i3 = this.f5147n - 1;
        iArr[i3] = iArr[i3] + 1;
        this.f5148o[this.f5147n - 1] = "null";
    }

    /* renamed from: p */
    public final boolean m8138p() {
        return this.f5136c;
    }

    /* renamed from: q */
    public String m8139q() {
        StringBuilder append = new StringBuilder().append('$');
        int i = this.f5147n;
        for (int i2 = 0; i2 < i; i2++) {
            switch (this.f5146m[i2]) {
                case 1:
                case 2:
                    append.append('[').append(this.f5149p[i2]).append(']');
                    break;
                case 3:
                case 4:
                case 5:
                    append.append('.');
                    if (this.f5148o[i2] == null) {
                        break;
                    }
                    append.append(this.f5148o[i2]);
                    break;
                default:
                    break;
            }
        }
        return append.toString();
    }

    public String toString() {
        return getClass().getSimpleName() + " at line " + m8118v() + " column " + m8119w();
    }
}
