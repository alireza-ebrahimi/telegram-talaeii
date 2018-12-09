package p033b.p034a.p035a.p036a.p037a.p043e;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URI;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.zip.GZIPInputStream;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;
import org.telegram.ui.ChatActivity;

/* renamed from: b.a.a.a.a.e.d */
public class C1187d {
    /* renamed from: b */
    private static final String[] f3421b = new String[0];
    /* renamed from: c */
    private static C1183b f3422c = C1183b.f3419a;
    /* renamed from: a */
    public final URL f3423a;
    /* renamed from: d */
    private HttpURLConnection f3424d = null;
    /* renamed from: e */
    private final String f3425e;
    /* renamed from: f */
    private C1186e f3426f;
    /* renamed from: g */
    private boolean f3427g;
    /* renamed from: h */
    private boolean f3428h = true;
    /* renamed from: i */
    private boolean f3429i = false;
    /* renamed from: j */
    private int f3430j = MessagesController.UPDATE_MASK_CHANNEL;
    /* renamed from: k */
    private String f3431k;
    /* renamed from: l */
    private int f3432l;

    /* renamed from: b.a.a.a.a.e.d$d */
    protected static abstract class C1180d<V> implements Callable<V> {
        protected C1180d() {
        }

        /* renamed from: b */
        protected abstract V mo1047b();

        /* renamed from: c */
        protected abstract void mo1046c();

        public V call() {
            Throwable th;
            Object obj = 1;
            try {
                V b = mo1047b();
                try {
                    mo1046c();
                    return b;
                } catch (IOException e) {
                    throw new C1185c(e);
                }
            } catch (C1185c e2) {
                throw e2;
            } catch (IOException e3) {
                throw new C1185c(e3);
            } catch (Throwable th2) {
                th = th2;
            }
            try {
                mo1046c();
            } catch (IOException e4) {
                if (obj == null) {
                    throw new C1185c(e4);
                }
            }
            throw th;
        }
    }

    /* renamed from: b.a.a.a.a.e.d$a */
    protected static abstract class C1181a<V> extends C1180d<V> {
        /* renamed from: a */
        private final Closeable f3414a;
        /* renamed from: b */
        private final boolean f3415b;

        protected C1181a(Closeable closeable, boolean z) {
            this.f3414a = closeable;
            this.f3415b = z;
        }

        /* renamed from: c */
        protected void mo1046c() {
            if (this.f3414a instanceof Flushable) {
                ((Flushable) this.f3414a).flush();
            }
            if (this.f3415b) {
                try {
                    this.f3414a.close();
                    return;
                } catch (IOException e) {
                    return;
                }
            }
            this.f3414a.close();
        }
    }

    /* renamed from: b.a.a.a.a.e.d$b */
    public interface C1183b {
        /* renamed from: a */
        public static final C1183b f3419a = new C11841();

        /* renamed from: b.a.a.a.a.e.d$b$1 */
        static class C11841 implements C1183b {
            C11841() {
            }

            /* renamed from: a */
            public HttpURLConnection mo1048a(URL url) {
                return (HttpURLConnection) url.openConnection();
            }

            /* renamed from: a */
            public HttpURLConnection mo1049a(URL url, Proxy proxy) {
                return (HttpURLConnection) url.openConnection(proxy);
            }
        }

        /* renamed from: a */
        HttpURLConnection mo1048a(URL url);

        /* renamed from: a */
        HttpURLConnection mo1049a(URL url, Proxy proxy);
    }

    /* renamed from: b.a.a.a.a.e.d$c */
    public static class C1185c extends RuntimeException {
        protected C1185c(IOException iOException) {
            super(iOException);
        }

        /* renamed from: a */
        public IOException m6242a() {
            return (IOException) super.getCause();
        }

        public /* synthetic */ Throwable getCause() {
            return m6242a();
        }
    }

    /* renamed from: b.a.a.a.a.e.d$e */
    public static class C1186e extends BufferedOutputStream {
        /* renamed from: a */
        private final CharsetEncoder f3420a;

        public C1186e(OutputStream outputStream, String str, int i) {
            super(outputStream, i);
            this.f3420a = Charset.forName(C1187d.m6256f(str)).newEncoder();
        }

        /* renamed from: a */
        public C1186e m6243a(String str) {
            ByteBuffer encode = this.f3420a.encode(CharBuffer.wrap(str));
            super.write(encode.array(), 0, encode.limit());
            return this;
        }
    }

    public C1187d(CharSequence charSequence, String str) {
        try {
            this.f3423a = new URL(charSequence.toString());
            this.f3425e = str;
        } catch (IOException e) {
            throw new C1185c(e);
        }
    }

    /* renamed from: a */
    public static C1187d m6245a(CharSequence charSequence, Map<?, ?> map, boolean z) {
        CharSequence a = C1187d.m6247a(charSequence, (Map) map);
        if (z) {
            a = C1187d.m6246a(a);
        }
        return C1187d.m6249b(a);
    }

    /* renamed from: a */
    public static String m6246a(CharSequence charSequence) {
        try {
            URL url = new URL(charSequence.toString());
            String host = url.getHost();
            int port = url.getPort();
            if (port != -1) {
                host = host + ':' + Integer.toString(port);
            }
            try {
                String toASCIIString = new URI(url.getProtocol(), host, url.getPath(), url.getQuery(), null).toASCIIString();
                int indexOf = toASCIIString.indexOf(63);
                if (indexOf > 0 && indexOf + 1 < toASCIIString.length()) {
                    toASCIIString = toASCIIString.substring(0, indexOf + 1) + toASCIIString.substring(indexOf + 1).replace("+", "%2B");
                }
                return toASCIIString;
            } catch (Throwable e) {
                IOException iOException = new IOException("Parsing URI failed");
                iOException.initCause(e);
                throw new C1185c(iOException);
            }
        } catch (IOException e2) {
            throw new C1185c(e2);
        }
    }

    /* renamed from: a */
    public static String m6247a(CharSequence charSequence, Map<?, ?> map) {
        String charSequence2 = charSequence.toString();
        if (map == null || map.isEmpty()) {
            return charSequence2;
        }
        StringBuilder stringBuilder = new StringBuilder(charSequence2);
        C1187d.m6248a(charSequence2, stringBuilder);
        C1187d.m6251b(charSequence2, stringBuilder);
        Iterator it = map.entrySet().iterator();
        Entry entry = (Entry) it.next();
        stringBuilder.append(entry.getKey().toString());
        stringBuilder.append('=');
        Object value = entry.getValue();
        if (value != null) {
            stringBuilder.append(value);
        }
        while (it.hasNext()) {
            stringBuilder.append('&');
            entry = (Entry) it.next();
            stringBuilder.append(entry.getKey().toString());
            stringBuilder.append('=');
            value = entry.getValue();
            if (value != null) {
                stringBuilder.append(value);
            }
        }
        return stringBuilder.toString();
    }

    /* renamed from: a */
    private static StringBuilder m6248a(String str, StringBuilder stringBuilder) {
        if (str.indexOf(58) + 2 == str.lastIndexOf(47)) {
            stringBuilder.append('/');
        }
        return stringBuilder;
    }

    /* renamed from: b */
    public static C1187d m6249b(CharSequence charSequence) {
        return new C1187d(charSequence, "GET");
    }

    /* renamed from: b */
    public static C1187d m6250b(CharSequence charSequence, Map<?, ?> map, boolean z) {
        CharSequence a = C1187d.m6247a(charSequence, (Map) map);
        if (z) {
            a = C1187d.m6246a(a);
        }
        return C1187d.m6252c(a);
    }

    /* renamed from: b */
    private static StringBuilder m6251b(String str, StringBuilder stringBuilder) {
        int indexOf = str.indexOf(63);
        int length = stringBuilder.length() - 1;
        if (indexOf == -1) {
            stringBuilder.append('?');
        } else if (indexOf < length && str.charAt(length) != '&') {
            stringBuilder.append('&');
        }
        return stringBuilder;
    }

    /* renamed from: c */
    public static C1187d m6252c(CharSequence charSequence) {
        return new C1187d(charSequence, "POST");
    }

    /* renamed from: d */
    public static C1187d m6253d(CharSequence charSequence) {
        return new C1187d(charSequence, "PUT");
    }

    /* renamed from: e */
    public static C1187d m6254e(CharSequence charSequence) {
        return new C1187d(charSequence, "DELETE");
    }

    /* renamed from: f */
    private static String m6256f(String str) {
        return (str == null || str.length() <= 0) ? C3446C.UTF8_NAME : str;
    }

    /* renamed from: q */
    private Proxy m6257q() {
        return new Proxy(Type.HTTP, new InetSocketAddress(this.f3431k, this.f3432l));
    }

    /* renamed from: r */
    private HttpURLConnection m6258r() {
        try {
            HttpURLConnection a = this.f3431k != null ? f3422c.mo1049a(this.f3423a, m6257q()) : f3422c.mo1048a(this.f3423a);
            a.setRequestMethod(this.f3425e);
            return a;
        } catch (IOException e) {
            throw new C1185c(e);
        }
    }

    /* renamed from: a */
    public int m6259a(String str, int i) {
        m6293l();
        return m6272a().getHeaderFieldInt(str, i);
    }

    /* renamed from: a */
    public C1187d m6260a(int i) {
        m6272a().setConnectTimeout(i);
        return this;
    }

    /* renamed from: a */
    protected C1187d m6261a(InputStream inputStream, OutputStream outputStream) {
        final InputStream inputStream2 = inputStream;
        final OutputStream outputStream2 = outputStream;
        return (C1187d) new C1181a<C1187d>(this, inputStream, this.f3428h) {
            /* renamed from: c */
            final /* synthetic */ C1187d f3418c;

            /* renamed from: a */
            public C1187d m6236a() {
                byte[] bArr = new byte[this.f3418c.f3430j];
                while (true) {
                    int read = inputStream2.read(bArr);
                    if (read == -1) {
                        return this.f3418c;
                    }
                    outputStream2.write(bArr, 0, read);
                }
            }

            /* renamed from: b */
            public /* synthetic */ Object mo1047b() {
                return m6236a();
            }
        }.call();
    }

    /* renamed from: a */
    public C1187d m6262a(String str, Number number) {
        return m6264a(str, null, number);
    }

    /* renamed from: a */
    public C1187d m6263a(String str, String str2) {
        m6272a().setRequestProperty(str, str2);
        return this;
    }

    /* renamed from: a */
    public C1187d m6264a(String str, String str2, Number number) {
        return m6274b(str, str2, number != null ? number.toString() : null);
    }

    /* renamed from: a */
    protected C1187d m6265a(String str, String str2, String str3) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("form-data; name=\"").append(str);
        if (str2 != null) {
            stringBuilder.append("\"; filename=\"").append(str2);
        }
        stringBuilder.append('\"');
        m6286f("Content-Disposition", stringBuilder.toString());
        if (str3 != null) {
            m6286f("Content-Type", str3);
        }
        return m6285f((CharSequence) "\r\n");
    }

    /* renamed from: a */
    public C1187d m6266a(String str, String str2, String str3, File file) {
        InputStream bufferedInputStream;
        IOException e;
        Throwable th;
        try {
            bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            try {
                C1187d a = m6267a(str, str2, str3, bufferedInputStream);
                if (bufferedInputStream != null) {
                    try {
                        bufferedInputStream.close();
                    } catch (IOException e2) {
                    }
                }
                return a;
            } catch (IOException e3) {
                e = e3;
                try {
                    throw new C1185c(e);
                } catch (Throwable th2) {
                    th = th2;
                    if (bufferedInputStream != null) {
                        try {
                            bufferedInputStream.close();
                        } catch (IOException e4) {
                        }
                    }
                    throw th;
                }
            }
        } catch (IOException e5) {
            e = e5;
            bufferedInputStream = null;
            throw new C1185c(e);
        } catch (Throwable th3) {
            th = th3;
            bufferedInputStream = null;
            if (bufferedInputStream != null) {
                bufferedInputStream.close();
            }
            throw th;
        }
    }

    /* renamed from: a */
    public C1187d m6267a(String str, String str2, String str3, InputStream inputStream) {
        try {
            m6295n();
            m6265a(str, str2, str3);
            m6261a(inputStream, this.f3426f);
            return this;
        } catch (IOException e) {
            throw new C1185c(e);
        }
    }

    /* renamed from: a */
    public C1187d m6268a(String str, String str2, String str3, String str4) {
        try {
            m6295n();
            m6265a(str, str2, str3);
            this.f3426f.m6243a(str4);
            return this;
        } catch (IOException e) {
            throw new C1185c(e);
        }
    }

    /* renamed from: a */
    public C1187d m6269a(Entry<String, String> entry) {
        return m6263a((String) entry.getKey(), (String) entry.getValue());
    }

    /* renamed from: a */
    public C1187d m6270a(boolean z) {
        m6272a().setUseCaches(z);
        return this;
    }

    /* renamed from: a */
    public String m6271a(String str) {
        OutputStream d = m6282d();
        try {
            m6261a(m6287f(), d);
            return d.toString(C1187d.m6256f(str));
        } catch (IOException e) {
            throw new C1185c(e);
        }
    }

    /* renamed from: a */
    public HttpURLConnection m6272a() {
        if (this.f3424d == null) {
            this.f3424d = m6258r();
        }
        return this.f3424d;
    }

    /* renamed from: b */
    public int m6273b() {
        try {
            m6292k();
            return m6272a().getResponseCode();
        } catch (IOException e) {
            throw new C1185c(e);
        }
    }

    /* renamed from: b */
    public C1187d m6274b(String str, String str2, String str3) {
        return m6268a(str, str2, null, str3);
    }

    /* renamed from: b */
    public String m6275b(String str) {
        m6293l();
        return m6272a().getHeaderField(str);
    }

    /* renamed from: b */
    public String m6276b(String str, String str2) {
        return m6278c(m6275b(str), str2);
    }

    /* renamed from: c */
    public int m6277c(String str) {
        return m6259a(str, -1);
    }

    /* renamed from: c */
    protected String m6278c(String str, String str2) {
        if (str == null || str.length() == 0) {
            return null;
        }
        int length = str.length();
        int indexOf = str.indexOf(59) + 1;
        if (indexOf == 0 || indexOf == length) {
            return null;
        }
        int indexOf2 = str.indexOf(59, indexOf);
        if (indexOf2 == -1) {
            indexOf2 = indexOf;
            indexOf = length;
        } else {
            int i = indexOf2;
            indexOf2 = indexOf;
            indexOf = i;
        }
        while (indexOf2 < indexOf) {
            int indexOf3 = str.indexOf(61, indexOf2);
            if (indexOf3 != -1 && indexOf3 < indexOf && str2.equals(str.substring(indexOf2, indexOf3).trim())) {
                String trim = str.substring(indexOf3 + 1, indexOf).trim();
                indexOf3 = trim.length();
                if (indexOf3 != 0) {
                    return (indexOf3 > 2 && '\"' == trim.charAt(0) && '\"' == trim.charAt(indexOf3 - 1)) ? trim.substring(1, indexOf3 - 1) : trim;
                }
            }
            indexOf++;
            indexOf2 = str.indexOf(59, indexOf);
            if (indexOf2 == -1) {
                indexOf2 = length;
            }
            i = indexOf2;
            indexOf2 = indexOf;
            indexOf = i;
        }
        return null;
    }

    /* renamed from: c */
    public boolean m6279c() {
        return Callback.DEFAULT_DRAG_ANIMATION_DURATION == m6273b();
    }

    /* renamed from: d */
    public C1187d m6280d(String str) {
        return m6281d(str, null);
    }

    /* renamed from: d */
    public C1187d m6281d(String str, String str2) {
        if (str2 == null || str2.length() <= 0) {
            return m6263a("Content-Type", str);
        }
        String str3 = "; charset=";
        return m6263a("Content-Type", str + "; charset=" + str2);
    }

    /* renamed from: d */
    protected ByteArrayOutputStream m6282d() {
        int j = m6291j();
        return j > 0 ? new ByteArrayOutputStream(j) : new ByteArrayOutputStream();
    }

    /* renamed from: e */
    public C1187d m6283e(String str, String str2) {
        return m6274b(str, null, str2);
    }

    /* renamed from: e */
    public String m6284e() {
        return m6271a(m6289h());
    }

    /* renamed from: f */
    public C1187d m6285f(CharSequence charSequence) {
        try {
            m6294m();
            this.f3426f.m6243a(charSequence.toString());
            return this;
        } catch (IOException e) {
            throw new C1185c(e);
        }
    }

    /* renamed from: f */
    public C1187d m6286f(String str, String str2) {
        return m6285f((CharSequence) str).m6285f((CharSequence) ": ").m6285f((CharSequence) str2).m6285f((CharSequence) "\r\n");
    }

    /* renamed from: f */
    public BufferedInputStream m6287f() {
        return new BufferedInputStream(m6288g(), this.f3430j);
    }

    /* renamed from: g */
    public InputStream m6288g() {
        if (m6273b() < ChatActivity.scheduleDownloads) {
            try {
                InputStream inputStream = m6272a().getInputStream();
            } catch (IOException e) {
                throw new C1185c(e);
            }
        }
        inputStream = m6272a().getErrorStream();
        if (inputStream == null) {
            try {
                inputStream = m6272a().getInputStream();
            } catch (IOException e2) {
                throw new C1185c(e2);
            }
        }
        if (!this.f3429i || !"gzip".equals(m6290i())) {
            return inputStream;
        }
        try {
            return new GZIPInputStream(inputStream);
        } catch (IOException e22) {
            throw new C1185c(e22);
        }
    }

    /* renamed from: h */
    public String m6289h() {
        return m6276b("Content-Type", "charset");
    }

    /* renamed from: i */
    public String m6290i() {
        return m6275b("Content-Encoding");
    }

    /* renamed from: j */
    public int m6291j() {
        return m6277c("Content-Length");
    }

    /* renamed from: k */
    protected C1187d m6292k() {
        if (this.f3426f != null) {
            if (this.f3427g) {
                this.f3426f.m6243a("\r\n--00content0boundary00--\r\n");
            }
            if (this.f3428h) {
                try {
                    this.f3426f.close();
                } catch (IOException e) {
                }
            } else {
                this.f3426f.close();
            }
            this.f3426f = null;
        }
        return this;
    }

    /* renamed from: l */
    protected C1187d m6293l() {
        try {
            return m6292k();
        } catch (IOException e) {
            throw new C1185c(e);
        }
    }

    /* renamed from: m */
    protected C1187d m6294m() {
        if (this.f3426f == null) {
            m6272a().setDoOutput(true);
            this.f3426f = new C1186e(m6272a().getOutputStream(), m6278c(m6272a().getRequestProperty("Content-Type"), "charset"), this.f3430j);
        }
        return this;
    }

    /* renamed from: n */
    protected C1187d m6295n() {
        if (this.f3427g) {
            this.f3426f.m6243a("\r\n--00content0boundary00\r\n");
        } else {
            this.f3427g = true;
            m6280d("multipart/form-data; boundary=00content0boundary00").m6294m();
            this.f3426f.m6243a("--00content0boundary00\r\n");
        }
        return this;
    }

    /* renamed from: o */
    public URL m6296o() {
        return m6272a().getURL();
    }

    /* renamed from: p */
    public String m6297p() {
        return m6272a().getRequestMethod();
    }

    public String toString() {
        return m6297p() + ' ' + m6296o();
    }
}
