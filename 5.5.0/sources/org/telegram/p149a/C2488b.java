package org.telegram.p149a;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.FileLog;

/* renamed from: org.telegram.a.b */
public class C2488b {
    /* renamed from: j */
    private static volatile C2488b f8313j = null;
    /* renamed from: a */
    public byte[] f8314a;
    /* renamed from: b */
    public ByteBuffer f8315b;
    /* renamed from: c */
    public String f8316c;
    /* renamed from: d */
    public String f8317d;
    /* renamed from: e */
    public HashMap<String, Integer> f8318e;
    /* renamed from: f */
    public HashMap<String, ArrayList<String>> f8319f;
    /* renamed from: g */
    public HashMap<String, C2487a> f8320g;
    /* renamed from: h */
    public HashMap<String, String> f8321h;
    /* renamed from: i */
    private boolean f8322i = false;

    public C2488b() {
        m12195c(null);
    }

    /* renamed from: a */
    public static String m12187a(String str) {
        StringBuilder stringBuilder = new StringBuilder(str);
        String str2 = "0123456789+*#";
        for (int length = stringBuilder.length() - 1; length >= 0; length--) {
            if (!str2.contains(stringBuilder.substring(length, length + 1))) {
                stringBuilder.deleteCharAt(length);
            }
        }
        return stringBuilder.toString();
    }

    /* renamed from: a */
    public static String m12188a(String str, boolean z) {
        if (str == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder(str);
        String str2 = "0123456789";
        if (z) {
            str2 = str2 + "+";
        }
        for (int length = stringBuilder.length() - 1; length >= 0; length--) {
            if (!str2.contains(stringBuilder.substring(length, length + 1))) {
                stringBuilder.deleteCharAt(length);
            }
        }
        return stringBuilder.toString();
    }

    /* renamed from: a */
    public static C2488b m12189a() {
        C2488b c2488b = f8313j;
        if (c2488b == null) {
            synchronized (C2488b.class) {
                c2488b = f8313j;
                if (c2488b == null) {
                    c2488b = new C2488b();
                    f8313j = c2488b;
                }
            }
        }
        return c2488b;
    }

    /* renamed from: b */
    public static String m12190b(String str) {
        return C2488b.m12188a(str, false);
    }

    /* renamed from: a */
    int m12191a(int i) {
        if (i + 4 > this.f8314a.length) {
            return 0;
        }
        this.f8315b.position(i);
        return this.f8315b.getInt();
    }

    /* renamed from: b */
    short m12192b(int i) {
        if (i + 2 > this.f8314a.length) {
            return (short) 0;
        }
        this.f8315b.position(i);
        return this.f8315b.getShort();
    }

    /* renamed from: b */
    public void m12193b() {
        int a = m12191a(0);
        int i = (a * 12) + 4;
        int i2 = 4;
        int i3 = 0;
        while (i3 < a) {
            String c = m12194c(i2);
            i2 += 4;
            String c2 = m12194c(i2);
            i2 += 4;
            int a2 = m12191a(i2) + i;
            int i4 = i2 + 4;
            if (c2.equals(this.f8316c)) {
                this.f8317d = c;
            }
            this.f8321h.put(c2, c);
            this.f8318e.put(c, Integer.valueOf(a2));
            ArrayList arrayList = (ArrayList) this.f8319f.get(c);
            if (arrayList == null) {
                arrayList = new ArrayList();
                this.f8319f.put(c, arrayList);
            }
            arrayList.add(c2);
            i3++;
            i2 = i4;
        }
        if (this.f8317d != null) {
            m12198f(this.f8317d);
        }
    }

    /* renamed from: c */
    public String m12194c(int i) {
        int i2 = i;
        while (i2 < this.f8314a.length) {
            try {
                if (this.f8314a[i2] == (byte) 0) {
                    return i == i2 - i ? TtmlNode.ANONYMOUS_REGION_ID : new String(this.f8314a, i, i2 - i);
                } else {
                    i2++;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return TtmlNode.ANONYMOUS_REGION_ID;
            }
        }
        return TtmlNode.ANONYMOUS_REGION_ID;
    }

    /* renamed from: c */
    public void m12195c(String str) {
        InputStream open;
        ByteArrayOutputStream byteArrayOutputStream;
        Throwable e;
        Exception e2;
        InputStream inputStream = null;
        try {
            open = ApplicationLoader.applicationContext.getAssets().open("PhoneFormats.dat");
            try {
                byteArrayOutputStream = new ByteArrayOutputStream();
                try {
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int read = open.read(bArr, 0, 1024);
                        if (read == -1) {
                            break;
                        }
                        byteArrayOutputStream.write(bArr, 0, read);
                    }
                    this.f8314a = byteArrayOutputStream.toByteArray();
                    this.f8315b = ByteBuffer.wrap(this.f8314a);
                    this.f8315b.order(ByteOrder.LITTLE_ENDIAN);
                    if (byteArrayOutputStream != null) {
                        try {
                            byteArrayOutputStream.close();
                        } catch (Throwable e3) {
                            FileLog.m13728e(e3);
                        }
                    }
                    if (open != null) {
                        try {
                            open.close();
                        } catch (Throwable e32) {
                            FileLog.m13728e(e32);
                        }
                    }
                    if (str == null || str.length() == 0) {
                        this.f8316c = Locale.getDefault().getCountry().toLowerCase();
                    } else {
                        this.f8316c = str;
                    }
                    this.f8318e = new HashMap(255);
                    this.f8319f = new HashMap(255);
                    this.f8320g = new HashMap(10);
                    this.f8321h = new HashMap(255);
                    m12193b();
                    this.f8322i = true;
                } catch (Exception e4) {
                    e2 = e4;
                    inputStream = open;
                    try {
                        e2.printStackTrace();
                        if (byteArrayOutputStream != null) {
                            try {
                                byteArrayOutputStream.close();
                            } catch (Throwable e322) {
                                FileLog.m13728e(e322);
                            }
                        }
                        if (inputStream == null) {
                            try {
                                inputStream.close();
                            } catch (Throwable e3222) {
                                FileLog.m13728e(e3222);
                            }
                        }
                    } catch (Throwable th) {
                        e3222 = th;
                        open = inputStream;
                        if (byteArrayOutputStream != null) {
                            try {
                                byteArrayOutputStream.close();
                            } catch (Throwable e5) {
                                FileLog.m13728e(e5);
                            }
                        }
                        if (open != null) {
                            try {
                                open.close();
                            } catch (Throwable e52) {
                                FileLog.m13728e(e52);
                            }
                        }
                        throw e3222;
                    }
                } catch (Throwable th2) {
                    e3222 = th2;
                    if (byteArrayOutputStream != null) {
                        byteArrayOutputStream.close();
                    }
                    if (open != null) {
                        open.close();
                    }
                    throw e3222;
                }
            } catch (Exception e6) {
                e2 = e6;
                byteArrayOutputStream = null;
                inputStream = open;
                e2.printStackTrace();
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
                if (inputStream == null) {
                    inputStream.close();
                }
            } catch (Throwable th3) {
                e3222 = th3;
                byteArrayOutputStream = null;
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
                if (open != null) {
                    open.close();
                }
                throw e3222;
            }
        } catch (Exception e7) {
            e2 = e7;
            byteArrayOutputStream = null;
            e2.printStackTrace();
            if (byteArrayOutputStream != null) {
                byteArrayOutputStream.close();
            }
            if (inputStream == null) {
                inputStream.close();
            }
        } catch (Throwable th4) {
            e3222 = th4;
            byteArrayOutputStream = null;
            open = null;
            if (byteArrayOutputStream != null) {
                byteArrayOutputStream.close();
            }
            if (open != null) {
                open.close();
            }
            throw e3222;
        }
    }

    /* renamed from: d */
    public C2487a m12196d(String str) {
        C2487a c2487a = null;
        int i = 0;
        while (i < 3 && i < str.length()) {
            c2487a = m12198f(str.substring(0, i + 1));
            if (c2487a != null) {
                break;
            }
            i++;
        }
        return c2487a;
    }

    /* renamed from: e */
    public String m12197e(String str) {
        if (!this.f8322i) {
            return str;
        }
        try {
            String a = C2488b.m12187a(str);
            String substring;
            if (a.startsWith("+")) {
                substring = a.substring(1);
                C2487a d = m12196d(substring);
                if (d == null) {
                    return str;
                }
                return "+" + d.m12186c(substring);
            }
            C2487a f = m12198f(this.f8317d);
            if (f == null) {
                return str;
            }
            substring = f.m12184a(a);
            if (substring == null) {
                return f.m12186c(a);
            }
            a = a.substring(substring.length());
            f = m12196d(a);
            if (f != null) {
                a = f.m12186c(a);
            }
            if (a.length() == 0) {
                return substring;
            }
            return String.format("%s %s", new Object[]{substring, a});
        } catch (Throwable e) {
            FileLog.m13728e(e);
            return str;
        }
    }

    /* renamed from: f */
    public C2487a m12198f(String str) {
        C2487a c2487a = (C2487a) this.f8320g.get(str);
        if (c2487a != null) {
            return c2487a;
        }
        Integer num = (Integer) this.f8318e.get(str);
        if (num == null) {
            return c2487a;
        }
        byte[] bArr = this.f8314a;
        int intValue = num.intValue();
        C2487a c2487a2 = new C2487a();
        c2487a2.f8309b = str;
        c2487a2.f8308a = (ArrayList) this.f8319f.get(str);
        this.f8320g.put(str, c2487a2);
        short b = m12192b(intValue);
        int i = (intValue + 2) + 2;
        short b2 = m12192b(i);
        i = (i + 2) + 2;
        short b3 = m12192b(i);
        i = (i + 2) + 2;
        ArrayList arrayList = new ArrayList(5);
        while (true) {
            String c = m12194c(i);
            if (c.length() == 0) {
                break;
            }
            arrayList.add(c);
            i += c.length() + 1;
        }
        c2487a2.f8310c = arrayList;
        i++;
        arrayList = new ArrayList(5);
        while (true) {
            c = m12194c(i);
            if (c.length() == 0) {
                break;
            }
            arrayList.add(c);
            i += c.length() + 1;
        }
        c2487a2.f8311d = arrayList;
        ArrayList arrayList2 = new ArrayList(b3);
        int i2 = intValue + b;
        for (short s = (short) 0; s < b3; s++) {
            C2490d c2490d = new C2490d();
            c2490d.f8335a = m12192b(i2);
            i = i2 + 2;
            short b4 = m12192b(i);
            i2 = i + 2;
            ArrayList arrayList3 = new ArrayList(b4);
            for (short s2 = (short) 0; s2 < b4; s2++) {
                C2489c c2489c = new C2489c();
                c2489c.f8323a = m12191a(i2);
                i2 += 4;
                c2489c.f8324b = m12191a(i2);
                i2 += 4;
                int i3 = i2 + 1;
                c2489c.f8325c = bArr[i2];
                i2 = i3 + 1;
                c2489c.f8326d = bArr[i3];
                i3 = i2 + 1;
                c2489c.f8327e = bArr[i2];
                i2 = i3 + 1;
                c2489c.f8328f = bArr[i3];
                i3 = i2 + 1;
                c2489c.f8329g = bArr[i2];
                i2 = i3 + 1;
                c2489c.f8330h = bArr[i3];
                short b5 = m12192b(i2);
                i2 += 2;
                c2489c.f8331i = m12194c(b5 + ((intValue + b) + b2));
                if (c2489c.f8331i.indexOf("[[") != -1) {
                    int indexOf = c2489c.f8331i.indexOf("]]");
                    c2489c.f8331i = String.format("%s%s", new Object[]{c2489c.f8331i.substring(0, i3), c2489c.f8331i.substring(indexOf + 2)});
                }
                arrayList3.add(c2489c);
                if (c2489c.f8332j) {
                    c2490d.f8337c = true;
                }
                if (c2489c.f8333k) {
                    c2490d.f8338d = true;
                }
            }
            c2490d.f8336b = arrayList3;
            arrayList2.add(c2490d);
        }
        c2487a2.f8312e = arrayList2;
        return c2487a2;
    }
}
