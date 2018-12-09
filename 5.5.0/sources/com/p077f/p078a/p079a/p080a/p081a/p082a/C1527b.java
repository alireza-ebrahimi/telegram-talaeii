package com.p077f.p078a.p079a.p080a.p081a.p082a;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import com.p077f.p078a.p079a.p080a.C1526a;
import com.p077f.p078a.p079a.p080a.p081a.p082a.C1525a.C1522a;
import com.p077f.p078a.p079a.p080a.p081a.p082a.C1525a.C1524c;
import com.p077f.p078a.p079a.p080a.p083b.C1533a;
import com.p077f.p078a.p095c.C1601b;
import com.p077f.p078a.p095c.C1601b.C1596a;
import com.p077f.p078a.p095c.C1602c;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.telegram.tgnet.TLRPC;

/* renamed from: com.f.a.a.a.a.a.b */
public class C1527b implements C1526a {
    /* renamed from: a */
    public static final CompressFormat f4645a = CompressFormat.PNG;
    /* renamed from: b */
    protected C1525a f4646b;
    /* renamed from: c */
    protected final C1533a f4647c;
    /* renamed from: d */
    protected int f4648d = TLRPC.MESSAGE_FLAG_EDITED;
    /* renamed from: e */
    protected CompressFormat f4649e = f4645a;
    /* renamed from: f */
    protected int f4650f = 100;
    /* renamed from: g */
    private File f4651g;

    public C1527b(File file, File file2, C1533a c1533a, long j, int i) {
        if (file == null) {
            throw new IllegalArgumentException("cacheDir argument must be not null");
        } else if (j < 0) {
            throw new IllegalArgumentException("cacheMaxSize argument must be positive number");
        } else if (i < 0) {
            throw new IllegalArgumentException("cacheMaxFileCount argument must be positive number");
        } else if (c1533a == null) {
            throw new IllegalArgumentException("fileNameGenerator argument must be not null");
        } else {
            long j2 = j == 0 ? Long.MAX_VALUE : j;
            int i2 = i == 0 ? Integer.MAX_VALUE : i;
            this.f4651g = file2;
            this.f4647c = c1533a;
            m7617a(file, file2, j2, i2);
        }
    }

    /* renamed from: a */
    private void m7617a(File file, File file2, long j, int i) {
        try {
            this.f4646b = C1525a.m7588a(file, 1, 1, j, i);
        } catch (Throwable e) {
            C1602c.m7937a(e);
            if (file2 != null) {
                m7617a(file2, null, j, i);
            }
            if (this.f4646b == null) {
                throw e;
            }
        }
    }

    /* renamed from: b */
    private String m7618b(String str) {
        return this.f4647c.mo1216a(str);
    }

    /* renamed from: a */
    public File mo1213a(String str) {
        C1524c a;
        Throwable e;
        Throwable th;
        File file = null;
        try {
            a = this.f4646b.m7610a(m7618b(str));
            if (a != null) {
                try {
                    file = a.m7585a(0);
                } catch (IOException e2) {
                    e = e2;
                    try {
                        C1602c.m7937a(e);
                        if (a != null) {
                            a.close();
                        }
                        return file;
                    } catch (Throwable th2) {
                        th = th2;
                        if (a != null) {
                            a.close();
                        }
                        throw th;
                    }
                }
            }
            if (a != null) {
                a.close();
            }
        } catch (IOException e3) {
            e = e3;
            a = file;
            C1602c.m7937a(e);
            if (a != null) {
                a.close();
            }
            return file;
        } catch (Throwable e4) {
            a = file;
            th = e4;
            if (a != null) {
                a.close();
            }
            throw th;
        }
        return file;
    }

    /* renamed from: a */
    public boolean mo1214a(String str, Bitmap bitmap) {
        boolean z = false;
        C1522a b = this.f4646b.m7612b(m7618b(str));
        if (b != null) {
            Closeable bufferedOutputStream = new BufferedOutputStream(b.m7568a(0), this.f4648d);
            try {
                z = bitmap.compress(this.f4649e, this.f4650f, bufferedOutputStream);
                if (z) {
                    b.m7569a();
                } else {
                    b.m7570b();
                }
            } finally {
                C1601b.m7930a(bufferedOutputStream);
            }
        }
        return z;
    }

    /* renamed from: a */
    public boolean mo1215a(String str, InputStream inputStream, C1596a c1596a) {
        boolean z = false;
        C1522a b = this.f4646b.m7612b(m7618b(str));
        if (b != null) {
            Closeable bufferedOutputStream = new BufferedOutputStream(b.m7568a(0), this.f4648d);
            try {
                z = C1601b.m7933a(inputStream, bufferedOutputStream, c1596a, this.f4648d);
                C1601b.m7930a(bufferedOutputStream);
                if (z) {
                    b.m7569a();
                } else {
                    b.m7570b();
                }
            } catch (Throwable th) {
                C1601b.m7930a(bufferedOutputStream);
                b.m7570b();
            }
        }
        return z;
    }
}
