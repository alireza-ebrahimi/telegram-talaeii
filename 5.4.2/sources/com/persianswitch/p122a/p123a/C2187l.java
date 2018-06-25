package com.persianswitch.p122a.p123a;

import com.persianswitch.p122a.C2221r;
import com.persianswitch.p126b.C2096s;
import com.persianswitch.p126b.C2244c;
import com.persianswitch.p126b.C2245f;
import java.io.Closeable;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.lang.reflect.Array;
import java.net.IDN;
import java.net.Socket;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import org.telegram.messenger.exoplayer2.C3446C;

/* renamed from: com.persianswitch.a.a.l */
public final class C2187l {
    /* renamed from: a */
    public static final byte[] f6634a = new byte[0];
    /* renamed from: b */
    public static final String[] f6635b = new String[0];
    /* renamed from: c */
    public static final Charset f6636c = Charset.forName(C3446C.UTF8_NAME);
    /* renamed from: d */
    public static final TimeZone f6637d = TimeZone.getTimeZone("GMT");
    /* renamed from: e */
    private static final Pattern f6638e = Pattern.compile("([0-9a-fA-F]*:[0-9a-fA-F:.]*)|([\\d.]+)");

    /* renamed from: a */
    public static int m9886a(String str, int i, int i2) {
        int i3 = i;
        while (i3 < i2) {
            switch (str.charAt(i3)) {
                case '\t':
                case '\n':
                case '\f':
                case '\r':
                case ' ':
                    i3++;
                default:
                    return i3;
            }
        }
        return i2;
    }

    /* renamed from: a */
    public static int m9887a(String str, int i, int i2, char c) {
        for (int i3 = i; i3 < i2; i3++) {
            if (str.charAt(i3) == c) {
                return i3;
            }
        }
        return i2;
    }

    /* renamed from: a */
    public static int m9888a(String str, int i, int i2, String str2) {
        for (int i3 = i; i3 < i2; i3++) {
            if (str2.indexOf(str.charAt(i3)) != -1) {
                return i3;
            }
        }
        return i2;
    }

    /* renamed from: a */
    public static C2245f m9889a(C2245f c2245f) {
        try {
            return C2245f.m10319a(MessageDigest.getInstance("SHA-1").digest(c2245f.mo3218f()));
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        }
    }

    /* renamed from: a */
    public static String m9890a(C2221r c2221r, boolean z) {
        String f = c2221r.m10075f().contains(":") ? "[" + c2221r.m10075f() + "]" : c2221r.m10075f();
        return (z || c2221r.m10076g() != C2221r.m10052a(c2221r.m10069b())) ? f + ":" + c2221r.m10076g() : f;
    }

    /* renamed from: a */
    public static String m9891a(String str) {
        try {
            String toLowerCase = IDN.toASCII(str).toLowerCase(Locale.US);
            return (toLowerCase.isEmpty() || C2187l.m9912c(toLowerCase)) ? null : toLowerCase;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /* renamed from: a */
    public static String m9892a(String str, Object... objArr) {
        return String.format(Locale.US, str, objArr);
    }

    /* renamed from: a */
    public static <T> List<T> m9893a(List<T> list) {
        return Collections.unmodifiableList(new ArrayList(list));
    }

    /* renamed from: a */
    public static <T> List<T> m9894a(T... tArr) {
        return Collections.unmodifiableList(Arrays.asList((Object[]) tArr.clone()));
    }

    /* renamed from: a */
    private static <T> List<T> m9895a(T[] tArr, T[] tArr2) {
        List<T> arrayList = new ArrayList();
        for (Object obj : tArr) {
            for (Object obj2 : tArr2) {
                if (obj.equals(obj2)) {
                    arrayList.add(obj2);
                    break;
                }
            }
        }
        return arrayList;
    }

    /* renamed from: a */
    public static ThreadFactory m9896a(final String str, final boolean z) {
        return new ThreadFactory() {
            public Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable, str);
                thread.setDaemon(z);
                return thread;
            }
        };
    }

    /* renamed from: a */
    public static void m9897a(long j, long j2, long j3) {
        if ((j2 | j3) < 0 || j2 > j || j - j2 < j3) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    /* renamed from: a */
    public static void m9898a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e2) {
            }
        }
    }

    /* renamed from: a */
    public static void m9899a(Closeable closeable, Closeable closeable2) {
        Object obj = null;
        try {
            closeable.close();
        } catch (Throwable th) {
            obj = th;
        }
        try {
            closeable2.close();
        } catch (Throwable th2) {
            if (obj == null) {
                Throwable th3 = th2;
            }
        }
        if (obj != null) {
            if (obj instanceof IOException) {
                throw ((IOException) obj);
            } else if (obj instanceof RuntimeException) {
                throw ((RuntimeException) obj);
            } else if (obj instanceof Error) {
                throw ((Error) obj);
            } else {
                throw new AssertionError(obj);
            }
        }
    }

    /* renamed from: a */
    public static void m9900a(Socket socket) {
        if (socket != null) {
            try {
                socket.close();
            } catch (AssertionError e) {
                if (!C2187l.m9902a(e)) {
                    throw e;
                }
            } catch (RuntimeException e2) {
                throw e2;
            } catch (Exception e3) {
            }
        }
    }

    /* renamed from: a */
    public static boolean m9901a(C2096s c2096s, int i, TimeUnit timeUnit) {
        try {
            return C2187l.m9908b(c2096s, i, timeUnit);
        } catch (IOException e) {
            return false;
        }
    }

    /* renamed from: a */
    public static boolean m9902a(AssertionError assertionError) {
        return (assertionError.getCause() == null || assertionError.getMessage() == null || !assertionError.getMessage().contains("getsockname failed")) ? false : true;
    }

    /* renamed from: a */
    public static boolean m9903a(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    /* renamed from: a */
    public static boolean m9904a(String[] strArr, String str) {
        return Arrays.asList(strArr).contains(str);
    }

    /* renamed from: a */
    public static <T> T[] m9905a(Class<T> cls, T[] tArr, T[] tArr2) {
        List a = C2187l.m9895a((Object[]) tArr, (Object[]) tArr2);
        return a.toArray((Object[]) Array.newInstance(cls, a.size()));
    }

    /* renamed from: b */
    public static int m9906b(String str, int i, int i2) {
        int i3 = i2 - 1;
        while (i3 >= i) {
            switch (str.charAt(i3)) {
                case '\t':
                case '\n':
                case '\f':
                case '\r':
                case ' ':
                    i3--;
                default:
                    return i3 + 1;
            }
        }
        return i;
    }

    /* renamed from: b */
    public static C2245f m9907b(C2245f c2245f) {
        try {
            return C2245f.m10319a(MessageDigest.getInstance("SHA-256").digest(c2245f.mo3218f()));
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        }
    }

    /* renamed from: b */
    public static boolean m9908b(C2096s c2096s, int i, TimeUnit timeUnit) {
        long nanoTime = System.nanoTime();
        long d = c2096s.mo3106a().f_() ? c2096s.mo3106a().mo3201d() - nanoTime : Long.MAX_VALUE;
        c2096s.mo3106a().mo3199a(Math.min(d, timeUnit.toNanos((long) i)) + nanoTime);
        try {
            C2244c c2244c = new C2244c();
            while (c2096s.mo3105a(c2244c, 8192) != -1) {
                c2244c.m10313r();
            }
            if (d == Long.MAX_VALUE) {
                c2096s.mo3106a().mo3203f();
            } else {
                c2096s.mo3106a().mo3199a(d + nanoTime);
            }
            return true;
        } catch (InterruptedIOException e) {
            if (d == Long.MAX_VALUE) {
                c2096s.mo3106a().mo3203f();
            } else {
                c2096s.mo3106a().mo3199a(d + nanoTime);
            }
            return false;
        } catch (Throwable th) {
            if (d == Long.MAX_VALUE) {
                c2096s.mo3106a().mo3203f();
            } else {
                c2096s.mo3106a().mo3199a(d + nanoTime);
            }
            throw th;
        }
    }

    /* renamed from: b */
    public static boolean m9909b(String str) {
        return f6638e.matcher(str).matches();
    }

    /* renamed from: b */
    public static String[] m9910b(String[] strArr, String str) {
        Object obj = new String[(strArr.length + 1)];
        System.arraycopy(strArr, 0, obj, 0, strArr.length);
        obj[obj.length - 1] = str;
        return obj;
    }

    /* renamed from: c */
    public static String m9911c(String str, int i, int i2) {
        int a = C2187l.m9886a(str, i, i2);
        return str.substring(a, C2187l.m9906b(str, a, i2));
    }

    /* renamed from: c */
    private static boolean m9912c(String str) {
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (charAt <= '\u001f' || charAt >= '') {
                return true;
            }
            if (" #%/:?@[\\]".indexOf(charAt) != -1) {
                return true;
            }
        }
        return false;
    }
}
