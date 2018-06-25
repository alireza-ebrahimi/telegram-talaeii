package com.p077f.p078a.p079a.p080a.p081a.p082a;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/* renamed from: com.f.a.a.a.a.a.a */
final class C1525a implements Closeable {
    /* renamed from: a */
    static final Pattern f4627a = Pattern.compile("[a-z0-9_-]{1,64}");
    /* renamed from: r */
    private static final OutputStream f4628r = new C15202();
    /* renamed from: b */
    final ThreadPoolExecutor f4629b = new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue());
    /* renamed from: c */
    private final File f4630c;
    /* renamed from: d */
    private final File f4631d;
    /* renamed from: e */
    private final File f4632e;
    /* renamed from: f */
    private final File f4633f;
    /* renamed from: g */
    private final int f4634g;
    /* renamed from: h */
    private long f4635h;
    /* renamed from: i */
    private int f4636i;
    /* renamed from: j */
    private final int f4637j;
    /* renamed from: k */
    private long f4638k = 0;
    /* renamed from: l */
    private int f4639l = 0;
    /* renamed from: m */
    private Writer f4640m;
    /* renamed from: n */
    private final LinkedHashMap<String, C1523b> f4641n = new LinkedHashMap(0, 0.75f, true);
    /* renamed from: o */
    private int f4642o;
    /* renamed from: p */
    private long f4643p = 0;
    /* renamed from: q */
    private final Callable<Void> f4644q = new C15191(this);

    /* renamed from: com.f.a.a.a.a.a.a$1 */
    class C15191 implements Callable<Void> {
        /* renamed from: a */
        final /* synthetic */ C1525a f4608a;

        C15191(C1525a c1525a) {
            this.f4608a = c1525a;
        }

        /* renamed from: a */
        public Void m7564a() {
            synchronized (this.f4608a) {
                if (this.f4608a.f4640m == null) {
                } else {
                    this.f4608a.m7608h();
                    this.f4608a.m7609i();
                    if (this.f4608a.m7605f()) {
                        this.f4608a.m7601e();
                        this.f4608a.f4642o = 0;
                    }
                }
            }
            return null;
        }

        public /* synthetic */ Object call() {
            return m7564a();
        }
    }

    /* renamed from: com.f.a.a.a.a.a.a$2 */
    static class C15202 extends OutputStream {
        C15202() {
        }

        public void write(int i) {
        }
    }

    /* renamed from: com.f.a.a.a.a.a.a$a */
    public final class C1522a {
        /* renamed from: a */
        final /* synthetic */ C1525a f4610a;
        /* renamed from: b */
        private final C1523b f4611b;
        /* renamed from: c */
        private final boolean[] f4612c;
        /* renamed from: d */
        private boolean f4613d;
        /* renamed from: e */
        private boolean f4614e;

        /* renamed from: com.f.a.a.a.a.a.a$a$a */
        private class C1521a extends FilterOutputStream {
            /* renamed from: a */
            final /* synthetic */ C1522a f4609a;

            private C1521a(C1522a c1522a, OutputStream outputStream) {
                this.f4609a = c1522a;
                super(outputStream);
            }

            public void close() {
                try {
                    this.out.close();
                } catch (IOException e) {
                    this.f4609a.f4613d = true;
                }
            }

            public void flush() {
                try {
                    this.out.flush();
                } catch (IOException e) {
                    this.f4609a.f4613d = true;
                }
            }

            public void write(int i) {
                try {
                    this.out.write(i);
                } catch (IOException e) {
                    this.f4609a.f4613d = true;
                }
            }

            public void write(byte[] bArr, int i, int i2) {
                try {
                    this.out.write(bArr, i, i2);
                } catch (IOException e) {
                    this.f4609a.f4613d = true;
                }
            }
        }

        private C1522a(C1525a c1525a, C1523b c1523b) {
            this.f4610a = c1525a;
            this.f4611b = c1523b;
            this.f4612c = c1523b.f4618d ? null : new boolean[c1525a.f4637j];
        }

        /* renamed from: a */
        public OutputStream m7568a(int i) {
            OutputStream b;
            synchronized (this.f4610a) {
                File b2;
                OutputStream fileOutputStream;
                if (this.f4611b.f4619e != this) {
                    throw new IllegalStateException();
                }
                if (!this.f4611b.f4618d) {
                    this.f4612c[i] = true;
                }
                b2 = this.f4611b.m7584b(i);
                try {
                    fileOutputStream = new FileOutputStream(b2);
                } catch (FileNotFoundException e) {
                    this.f4610a.f4630c.mkdirs();
                    try {
                        fileOutputStream = new FileOutputStream(b2);
                    } catch (FileNotFoundException e2) {
                        b = C1525a.f4628r;
                    }
                }
                b = new C1521a(fileOutputStream);
            }
            return b;
        }

        /* renamed from: a */
        public void m7569a() {
            if (this.f4613d) {
                this.f4610a.m7590a(this, false);
                this.f4610a.m7613c(this.f4611b.f4616b);
            } else {
                this.f4610a.m7590a(this, true);
            }
            this.f4614e = true;
        }

        /* renamed from: b */
        public void m7570b() {
            this.f4610a.m7590a(this, false);
        }
    }

    /* renamed from: com.f.a.a.a.a.a.a$b */
    private final class C1523b {
        /* renamed from: a */
        final /* synthetic */ C1525a f4615a;
        /* renamed from: b */
        private final String f4616b;
        /* renamed from: c */
        private final long[] f4617c;
        /* renamed from: d */
        private boolean f4618d;
        /* renamed from: e */
        private C1522a f4619e;
        /* renamed from: f */
        private long f4620f;

        private C1523b(C1525a c1525a, String str) {
            this.f4615a = c1525a;
            this.f4616b = str;
            this.f4617c = new long[c1525a.f4637j];
        }

        /* renamed from: a */
        private void m7575a(String[] strArr) {
            if (strArr.length != this.f4615a.f4637j) {
                throw m7577b(strArr);
            }
            int i = 0;
            while (i < strArr.length) {
                try {
                    this.f4617c[i] = Long.parseLong(strArr[i]);
                    i++;
                } catch (NumberFormatException e) {
                    throw m7577b(strArr);
                }
            }
        }

        /* renamed from: b */
        private IOException m7577b(String[] strArr) {
            throw new IOException("unexpected journal line: " + Arrays.toString(strArr));
        }

        /* renamed from: a */
        public File m7582a(int i) {
            return new File(this.f4615a.f4630c, this.f4616b + TtmlNode.ANONYMOUS_REGION_ID + i);
        }

        /* renamed from: a */
        public String m7583a() {
            StringBuilder stringBuilder = new StringBuilder();
            for (long append : this.f4617c) {
                stringBuilder.append(' ').append(append);
            }
            return stringBuilder.toString();
        }

        /* renamed from: b */
        public File m7584b(int i) {
            return new File(this.f4615a.f4630c, this.f4616b + TtmlNode.ANONYMOUS_REGION_ID + i + ".tmp");
        }
    }

    /* renamed from: com.f.a.a.a.a.a.a$c */
    public final class C1524c implements Closeable {
        /* renamed from: a */
        final /* synthetic */ C1525a f4621a;
        /* renamed from: b */
        private final String f4622b;
        /* renamed from: c */
        private final long f4623c;
        /* renamed from: d */
        private File[] f4624d;
        /* renamed from: e */
        private final InputStream[] f4625e;
        /* renamed from: f */
        private final long[] f4626f;

        private C1524c(C1525a c1525a, String str, long j, File[] fileArr, InputStream[] inputStreamArr, long[] jArr) {
            this.f4621a = c1525a;
            this.f4622b = str;
            this.f4623c = j;
            this.f4624d = fileArr;
            this.f4625e = inputStreamArr;
            this.f4626f = jArr;
        }

        /* renamed from: a */
        public File m7585a(int i) {
            return this.f4624d[i];
        }

        public void close() {
            for (Closeable a : this.f4625e) {
                C1530d.m7625a(a);
            }
        }
    }

    private C1525a(File file, int i, int i2, long j, int i3) {
        this.f4630c = file;
        this.f4634g = i;
        this.f4631d = new File(file, "journal");
        this.f4632e = new File(file, "journal.tmp");
        this.f4633f = new File(file, "journal.bkp");
        this.f4637j = i2;
        this.f4635h = j;
        this.f4636i = i3;
    }

    /* renamed from: a */
    private synchronized C1522a m7587a(String str, long j) {
        C1522a c1522a;
        m7607g();
        m7603e(str);
        C1523b c1523b = (C1523b) this.f4641n.get(str);
        if (j == -1 || (c1523b != null && c1523b.f4620f == j)) {
            C1523b c1523b2;
            if (c1523b == null) {
                c1523b = new C1523b(str);
                this.f4641n.put(str, c1523b);
                c1523b2 = c1523b;
            } else if (c1523b.f4619e != null) {
                c1522a = null;
            } else {
                c1523b2 = c1523b;
            }
            c1522a = new C1522a(c1523b2);
            c1523b2.f4619e = c1522a;
            this.f4640m.write("DIRTY " + str + '\n');
            this.f4640m.flush();
        } else {
            c1522a = null;
        }
        return c1522a;
    }

    /* renamed from: a */
    public static C1525a m7588a(File file, int i, int i2, long j, int i3) {
        if (j <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        } else if (i3 <= 0) {
            throw new IllegalArgumentException("maxFileCount <= 0");
        } else if (i2 <= 0) {
            throw new IllegalArgumentException("valueCount <= 0");
        } else {
            File file2 = new File(file, "journal.bkp");
            if (file2.exists()) {
                File file3 = new File(file, "journal");
                if (file3.exists()) {
                    file2.delete();
                } else {
                    C1525a.m7593a(file2, file3, false);
                }
            }
            C1525a c1525a = new C1525a(file, i, i2, j, i3);
            if (c1525a.f4631d.exists()) {
                try {
                    c1525a.m7596c();
                    c1525a.m7598d();
                    c1525a.f4640m = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(c1525a.f4631d, true), C1530d.f4658a));
                    return c1525a;
                } catch (IOException e) {
                    System.out.println("DiskLruCache " + file + " is corrupt: " + e.getMessage() + ", removing");
                    c1525a.m7611a();
                }
            }
            file.mkdirs();
            c1525a = new C1525a(file, i, i2, j, i3);
            c1525a.m7601e();
            return c1525a;
        }
    }

    /* renamed from: a */
    private synchronized void m7590a(C1522a c1522a, boolean z) {
        int i = 0;
        synchronized (this) {
            C1523b a = c1522a.f4611b;
            if (a.f4619e != c1522a) {
                throw new IllegalStateException();
            }
            if (z) {
                if (!a.f4618d) {
                    int i2 = 0;
                    while (i2 < this.f4637j) {
                        if (!c1522a.f4612c[i2]) {
                            c1522a.m7570b();
                            throw new IllegalStateException("Newly created entry didn't create value for index " + i2);
                        } else if (!a.m7584b(i2).exists()) {
                            c1522a.m7570b();
                            break;
                        } else {
                            i2++;
                        }
                    }
                }
            }
            while (i < this.f4637j) {
                File b = a.m7584b(i);
                if (!z) {
                    C1525a.m7592a(b);
                } else if (b.exists()) {
                    File a2 = a.m7582a(i);
                    b.renameTo(a2);
                    long j = a.f4617c[i];
                    long length = a2.length();
                    a.f4617c[i] = length;
                    this.f4638k = (this.f4638k - j) + length;
                    this.f4639l++;
                }
                i++;
            }
            this.f4642o++;
            a.f4619e = null;
            if ((a.f4618d | z) != 0) {
                a.f4618d = true;
                this.f4640m.write("CLEAN " + a.f4616b + a.m7583a() + '\n');
                if (z) {
                    long j2 = this.f4643p;
                    this.f4643p = 1 + j2;
                    a.f4620f = j2;
                }
            } else {
                this.f4641n.remove(a.f4616b);
                this.f4640m.write("REMOVE " + a.f4616b + '\n');
            }
            this.f4640m.flush();
            if (this.f4638k > this.f4635h || this.f4639l > this.f4636i || m7605f()) {
                this.f4629b.submit(this.f4644q);
            }
        }
    }

    /* renamed from: a */
    private static void m7592a(File file) {
        if (file.exists() && !file.delete()) {
            throw new IOException();
        }
    }

    /* renamed from: a */
    private static void m7593a(File file, File file2, boolean z) {
        if (z) {
            C1525a.m7592a(file2);
        }
        if (!file.renameTo(file2)) {
            throw new IOException();
        }
    }

    /* renamed from: c */
    private void m7596c() {
        Closeable c1529c = new C1529c(new FileInputStream(this.f4631d), C1530d.f4658a);
        int i;
        try {
            String a = c1529c.m7624a();
            String a2 = c1529c.m7624a();
            String a3 = c1529c.m7624a();
            String a4 = c1529c.m7624a();
            String a5 = c1529c.m7624a();
            if ("libcore.io.DiskLruCache".equals(a) && "1".equals(a2) && Integer.toString(this.f4634g).equals(a3) && Integer.toString(this.f4637j).equals(a4) && TtmlNode.ANONYMOUS_REGION_ID.equals(a5)) {
                i = 0;
                while (true) {
                    m7599d(c1529c.m7624a());
                    i++;
                }
            } else {
                throw new IOException("unexpected journal header: [" + a + ", " + a2 + ", " + a4 + ", " + a5 + "]");
            }
        } catch (EOFException e) {
            this.f4642o = i - this.f4641n.size();
            C1530d.m7625a(c1529c);
        } catch (Throwable th) {
            C1530d.m7625a(c1529c);
        }
    }

    /* renamed from: d */
    private void m7598d() {
        C1525a.m7592a(this.f4632e);
        Iterator it = this.f4641n.values().iterator();
        while (it.hasNext()) {
            C1523b c1523b = (C1523b) it.next();
            int i;
            if (c1523b.f4619e == null) {
                for (i = 0; i < this.f4637j; i++) {
                    this.f4638k += c1523b.f4617c[i];
                    this.f4639l++;
                }
            } else {
                c1523b.f4619e = null;
                for (i = 0; i < this.f4637j; i++) {
                    C1525a.m7592a(c1523b.m7582a(i));
                    C1525a.m7592a(c1523b.m7584b(i));
                }
                it.remove();
            }
        }
    }

    /* renamed from: d */
    private void m7599d(String str) {
        int indexOf = str.indexOf(32);
        if (indexOf == -1) {
            throw new IOException("unexpected journal line: " + str);
        }
        String str2;
        int i = indexOf + 1;
        int indexOf2 = str.indexOf(32, i);
        if (indexOf2 == -1) {
            String substring = str.substring(i);
            if (indexOf == "REMOVE".length() && str.startsWith("REMOVE")) {
                this.f4641n.remove(substring);
                return;
            }
            str2 = substring;
        } else {
            str2 = str.substring(i, indexOf2);
        }
        C1523b c1523b = (C1523b) this.f4641n.get(str2);
        if (c1523b == null) {
            c1523b = new C1523b(str2);
            this.f4641n.put(str2, c1523b);
        }
        if (indexOf2 != -1 && indexOf == "CLEAN".length() && str.startsWith("CLEAN")) {
            String[] split = str.substring(indexOf2 + 1).split(" ");
            c1523b.f4618d = true;
            c1523b.f4619e = null;
            c1523b.m7575a(split);
        } else if (indexOf2 == -1 && indexOf == "DIRTY".length() && str.startsWith("DIRTY")) {
            c1523b.f4619e = new C1522a(c1523b);
        } else if (indexOf2 != -1 || indexOf != "READ".length() || !str.startsWith("READ")) {
            throw new IOException("unexpected journal line: " + str);
        }
    }

    /* renamed from: e */
    private synchronized void m7601e() {
        if (this.f4640m != null) {
            this.f4640m.close();
        }
        Writer bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.f4632e), C1530d.f4658a));
        try {
            bufferedWriter.write("libcore.io.DiskLruCache");
            bufferedWriter.write("\n");
            bufferedWriter.write("1");
            bufferedWriter.write("\n");
            bufferedWriter.write(Integer.toString(this.f4634g));
            bufferedWriter.write("\n");
            bufferedWriter.write(Integer.toString(this.f4637j));
            bufferedWriter.write("\n");
            bufferedWriter.write("\n");
            for (C1523b c1523b : this.f4641n.values()) {
                if (c1523b.f4619e != null) {
                    bufferedWriter.write("DIRTY " + c1523b.f4616b + '\n');
                } else {
                    bufferedWriter.write("CLEAN " + c1523b.f4616b + c1523b.m7583a() + '\n');
                }
            }
            bufferedWriter.close();
            if (this.f4631d.exists()) {
                C1525a.m7593a(this.f4631d, this.f4633f, true);
            }
            C1525a.m7593a(this.f4632e, this.f4631d, false);
            this.f4633f.delete();
            this.f4640m = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.f4631d, true), C1530d.f4658a));
        } catch (Throwable th) {
            bufferedWriter.close();
        }
    }

    /* renamed from: e */
    private void m7603e(String str) {
        if (!f4627a.matcher(str).matches()) {
            throw new IllegalArgumentException("keys must match regex [a-z0-9_-]{1,64}: \"" + str + "\"");
        }
    }

    /* renamed from: f */
    private boolean m7605f() {
        return this.f4642o >= 2000 && this.f4642o >= this.f4641n.size();
    }

    /* renamed from: g */
    private void m7607g() {
        if (this.f4640m == null) {
            throw new IllegalStateException("cache is closed");
        }
    }

    /* renamed from: h */
    private void m7608h() {
        while (this.f4638k > this.f4635h) {
            m7613c((String) ((Entry) this.f4641n.entrySet().iterator().next()).getKey());
        }
    }

    /* renamed from: i */
    private void m7609i() {
        while (this.f4639l > this.f4636i) {
            m7613c((String) ((Entry) this.f4641n.entrySet().iterator().next()).getKey());
        }
    }

    /* renamed from: a */
    public synchronized C1524c m7610a(String str) {
        int i;
        C1524c c1524c = null;
        synchronized (this) {
            m7607g();
            m7603e(str);
            C1523b c1523b = (C1523b) this.f4641n.get(str);
            if (c1523b != null) {
                if (c1523b.f4618d) {
                    File[] fileArr = new File[this.f4637j];
                    r7 = new InputStream[this.f4637j];
                    int i2 = 0;
                    while (i2 < this.f4637j) {
                        try {
                            File a = c1523b.m7582a(i2);
                            fileArr[i2] = a;
                            r7[i2] = new FileInputStream(a);
                            i2++;
                        } catch (FileNotFoundException e) {
                            i = 0;
                            while (i < this.f4637j && r7[i] != null) {
                                InputStream[] inputStreamArr;
                                C1530d.m7625a(inputStreamArr[i]);
                                i++;
                            }
                        }
                    }
                    this.f4642o++;
                    this.f4640m.append("READ " + str + '\n');
                    if (m7605f()) {
                        this.f4629b.submit(this.f4644q);
                    }
                    c1524c = new C1524c(str, c1523b.f4620f, fileArr, inputStreamArr, c1523b.f4617c);
                }
            }
        }
        return c1524c;
    }

    /* renamed from: a */
    public void m7611a() {
        close();
        C1530d.m7626a(this.f4630c);
    }

    /* renamed from: b */
    public C1522a m7612b(String str) {
        return m7587a(str, -1);
    }

    /* renamed from: c */
    public synchronized boolean m7613c(String str) {
        boolean z;
        int i = 0;
        synchronized (this) {
            m7607g();
            m7603e(str);
            C1523b c1523b = (C1523b) this.f4641n.get(str);
            if (c1523b == null || c1523b.f4619e != null) {
                z = false;
            } else {
                while (i < this.f4637j) {
                    File a = c1523b.m7582a(i);
                    if (!a.exists() || a.delete()) {
                        this.f4638k -= c1523b.f4617c[i];
                        this.f4639l--;
                        c1523b.f4617c[i] = 0;
                        i++;
                    } else {
                        throw new IOException("failed to delete " + a);
                    }
                }
                this.f4642o++;
                this.f4640m.append("REMOVE " + str + '\n');
                this.f4641n.remove(str);
                if (m7605f()) {
                    this.f4629b.submit(this.f4644q);
                }
                z = true;
            }
        }
        return z;
    }

    public synchronized void close() {
        if (this.f4640m != null) {
            Iterator it = new ArrayList(this.f4641n.values()).iterator();
            while (it.hasNext()) {
                C1523b c1523b = (C1523b) it.next();
                if (c1523b.f4619e != null) {
                    c1523b.f4619e.m7570b();
                }
            }
            m7608h();
            m7609i();
            this.f4640m.close();
            this.f4640m = null;
        }
    }
}
