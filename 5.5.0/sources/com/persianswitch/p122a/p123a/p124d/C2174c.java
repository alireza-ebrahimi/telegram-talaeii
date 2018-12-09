package com.persianswitch.p122a.p123a.p124d;

import javax.security.auth.x500.X500Principal;
import org.telegram.messenger.exoplayer2.extractor.ts.PsExtractor;

/* renamed from: com.persianswitch.a.a.d.c */
final class C2174c {
    /* renamed from: a */
    private final String f6606a;
    /* renamed from: b */
    private final int f6607b = this.f6606a.length();
    /* renamed from: c */
    private int f6608c;
    /* renamed from: d */
    private int f6609d;
    /* renamed from: e */
    private int f6610e;
    /* renamed from: f */
    private int f6611f;
    /* renamed from: g */
    private char[] f6612g;

    public C2174c(X500Principal x500Principal) {
        this.f6606a = x500Principal.getName("RFC2253");
    }

    /* renamed from: a */
    private int m9834a(int i) {
        if (i + 1 >= this.f6607b) {
            throw new IllegalStateException("Malformed DN: " + this.f6606a);
        }
        int i2;
        int i3;
        char c = this.f6612g[i];
        if (c >= '0' && c <= '9') {
            i2 = c - 48;
        } else if (c >= 'a' && c <= 'f') {
            i2 = c - 87;
        } else if (c < 'A' || c > 'F') {
            throw new IllegalStateException("Malformed DN: " + this.f6606a);
        } else {
            i2 = c - 55;
        }
        char c2 = this.f6612g[i + 1];
        if (c2 >= '0' && c2 <= '9') {
            i3 = c2 - 48;
        } else if (c2 >= 'a' && c2 <= 'f') {
            i3 = c2 - 87;
        } else if (c2 < 'A' || c2 > 'F') {
            throw new IllegalStateException("Malformed DN: " + this.f6606a);
        } else {
            i3 = c2 - 55;
        }
        return (i2 << 4) + i3;
    }

    /* renamed from: a */
    private String m9835a() {
        while (this.f6608c < this.f6607b && this.f6612g[this.f6608c] == ' ') {
            this.f6608c++;
        }
        if (this.f6608c == this.f6607b) {
            return null;
        }
        this.f6609d = this.f6608c;
        this.f6608c++;
        while (this.f6608c < this.f6607b && this.f6612g[this.f6608c] != '=' && this.f6612g[this.f6608c] != ' ') {
            this.f6608c++;
        }
        if (this.f6608c >= this.f6607b) {
            throw new IllegalStateException("Unexpected end of DN: " + this.f6606a);
        }
        this.f6610e = this.f6608c;
        if (this.f6612g[this.f6608c] == ' ') {
            while (this.f6608c < this.f6607b && this.f6612g[this.f6608c] != '=' && this.f6612g[this.f6608c] == ' ') {
                this.f6608c++;
            }
            if (this.f6612g[this.f6608c] != '=' || this.f6608c == this.f6607b) {
                throw new IllegalStateException("Unexpected end of DN: " + this.f6606a);
            }
        }
        this.f6608c++;
        while (this.f6608c < this.f6607b && this.f6612g[this.f6608c] == ' ') {
            this.f6608c++;
        }
        if (this.f6610e - this.f6609d > 4 && this.f6612g[this.f6609d + 3] == '.' && ((this.f6612g[this.f6609d] == 'O' || this.f6612g[this.f6609d] == 'o') && ((this.f6612g[this.f6609d + 1] == 'I' || this.f6612g[this.f6609d + 1] == 'i') && (this.f6612g[this.f6609d + 2] == 'D' || this.f6612g[this.f6609d + 2] == 'd')))) {
            this.f6609d += 4;
        }
        return new String(this.f6612g, this.f6609d, this.f6610e - this.f6609d);
    }

    /* renamed from: b */
    private String m9836b() {
        this.f6608c++;
        this.f6609d = this.f6608c;
        this.f6610e = this.f6609d;
        while (this.f6608c != this.f6607b) {
            if (this.f6612g[this.f6608c] == '\"') {
                this.f6608c++;
                while (this.f6608c < this.f6607b && this.f6612g[this.f6608c] == ' ') {
                    this.f6608c++;
                }
                return new String(this.f6612g, this.f6609d, this.f6610e - this.f6609d);
            }
            if (this.f6612g[this.f6608c] == '\\') {
                this.f6612g[this.f6610e] = m9839e();
            } else {
                this.f6612g[this.f6610e] = this.f6612g[this.f6608c];
            }
            this.f6608c++;
            this.f6610e++;
        }
        throw new IllegalStateException("Unexpected end of DN: " + this.f6606a);
    }

    /* renamed from: c */
    private String m9837c() {
        if (this.f6608c + 4 >= this.f6607b) {
            throw new IllegalStateException("Unexpected end of DN: " + this.f6606a);
        }
        int i;
        this.f6609d = this.f6608c;
        this.f6608c++;
        while (this.f6608c != this.f6607b && this.f6612g[this.f6608c] != '+' && this.f6612g[this.f6608c] != ',' && this.f6612g[this.f6608c] != ';') {
            int i2;
            if (this.f6612g[this.f6608c] == ' ') {
                this.f6610e = this.f6608c;
                this.f6608c++;
                while (this.f6608c < this.f6607b && this.f6612g[this.f6608c] == ' ') {
                    this.f6608c++;
                }
                i = this.f6610e - this.f6609d;
                if (i >= 5 || (i & 1) == 0) {
                    throw new IllegalStateException("Unexpected end of DN: " + this.f6606a);
                }
                byte[] bArr = new byte[(i / 2)];
                int i3 = this.f6609d + 1;
                for (i2 = 0; i2 < bArr.length; i2++) {
                    bArr[i2] = (byte) m9834a(i3);
                    i3 += 2;
                }
                return new String(this.f6612g, this.f6609d, i);
            }
            if (this.f6612g[this.f6608c] >= 'A' && this.f6612g[this.f6608c] <= 'F') {
                char[] cArr = this.f6612g;
                i2 = this.f6608c;
                cArr[i2] = (char) (cArr[i2] + 32);
            }
            this.f6608c++;
        }
        this.f6610e = this.f6608c;
        i = this.f6610e - this.f6609d;
        if (i >= 5) {
        }
        throw new IllegalStateException("Unexpected end of DN: " + this.f6606a);
    }

    /* renamed from: d */
    private String m9838d() {
        this.f6609d = this.f6608c;
        this.f6610e = this.f6608c;
        while (this.f6608c < this.f6607b) {
            char[] cArr;
            int i;
            switch (this.f6612g[this.f6608c]) {
                case ' ':
                    this.f6611f = this.f6610e;
                    this.f6608c++;
                    cArr = this.f6612g;
                    i = this.f6610e;
                    this.f6610e = i + 1;
                    cArr[i] = ' ';
                    while (this.f6608c < this.f6607b && this.f6612g[this.f6608c] == ' ') {
                        cArr = this.f6612g;
                        i = this.f6610e;
                        this.f6610e = i + 1;
                        cArr[i] = ' ';
                        this.f6608c++;
                    }
                    if (this.f6608c != this.f6607b && this.f6612g[this.f6608c] != ',' && this.f6612g[this.f6608c] != '+' && this.f6612g[this.f6608c] != ';') {
                        break;
                    }
                    return new String(this.f6612g, this.f6609d, this.f6611f - this.f6609d);
                    break;
                case '+':
                case ',':
                case ';':
                    return new String(this.f6612g, this.f6609d, this.f6610e - this.f6609d);
                case '\\':
                    cArr = this.f6612g;
                    i = this.f6610e;
                    this.f6610e = i + 1;
                    cArr[i] = m9839e();
                    this.f6608c++;
                    break;
                default:
                    cArr = this.f6612g;
                    i = this.f6610e;
                    this.f6610e = i + 1;
                    cArr[i] = this.f6612g[this.f6608c];
                    this.f6608c++;
                    break;
            }
        }
        return new String(this.f6612g, this.f6609d, this.f6610e - this.f6609d);
    }

    /* renamed from: e */
    private char m9839e() {
        this.f6608c++;
        if (this.f6608c == this.f6607b) {
            throw new IllegalStateException("Unexpected end of DN: " + this.f6606a);
        }
        switch (this.f6612g[this.f6608c]) {
            case ' ':
            case '\"':
            case '#':
            case '%':
            case '*':
            case '+':
            case ',':
            case ';':
            case '<':
            case '=':
            case '>':
            case '\\':
            case '_':
                return this.f6612g[this.f6608c];
            default:
                return m9840f();
        }
    }

    /* renamed from: f */
    private char m9840f() {
        int a = m9834a(this.f6608c);
        this.f6608c++;
        if (a < 128) {
            return (char) a;
        }
        if (a < PsExtractor.AUDIO_STREAM || a > 247) {
            return '?';
        }
        int i;
        if (a <= 223) {
            i = 1;
            a &= 31;
        } else if (a <= 239) {
            i = 2;
            a &= 15;
        } else {
            i = 3;
            a &= 7;
        }
        int i2 = a;
        for (a = 0; a < i; a++) {
            this.f6608c++;
            if (this.f6608c == this.f6607b || this.f6612g[this.f6608c] != '\\') {
                return '?';
            }
            this.f6608c++;
            int a2 = m9834a(this.f6608c);
            this.f6608c++;
            if ((a2 & PsExtractor.AUDIO_STREAM) != 128) {
                return '?';
            }
            i2 = (i2 << 6) + (a2 & 63);
        }
        return (char) i2;
    }

    /* renamed from: a */
    public String m9841a(String str) {
        this.f6608c = 0;
        this.f6609d = 0;
        this.f6610e = 0;
        this.f6611f = 0;
        this.f6612g = this.f6606a.toCharArray();
        String a = m9835a();
        if (a == null) {
            return null;
        }
        do {
            String str2 = TtmlNode.ANONYMOUS_REGION_ID;
            if (this.f6608c == this.f6607b) {
                return null;
            }
            switch (this.f6612g[this.f6608c]) {
                case '\"':
                    str2 = m9836b();
                    break;
                case '#':
                    str2 = m9837c();
                    break;
                case '+':
                case ',':
                case ';':
                    break;
                default:
                    str2 = m9838d();
                    break;
            }
            if (str.equalsIgnoreCase(a)) {
                return str2;
            }
            if (this.f6608c >= this.f6607b) {
                return null;
            }
            if (this.f6612g[this.f6608c] == ',' || this.f6612g[this.f6608c] == ';' || this.f6612g[this.f6608c] == '+') {
                this.f6608c++;
                a = m9835a();
            } else {
                throw new IllegalStateException("Malformed DN: " + this.f6606a);
            }
        } while (a != null);
        throw new IllegalStateException("Malformed DN: " + this.f6606a);
    }
}
