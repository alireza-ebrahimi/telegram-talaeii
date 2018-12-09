package com.p077f.p078a.p079a.p080a.p081a;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import com.p077f.p078a.p079a.p080a.C1526a;
import com.p077f.p078a.p079a.p080a.p083b.C1533a;
import com.p077f.p078a.p095c.C1601b;
import com.p077f.p078a.p095c.C1601b.C1596a;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import org.telegram.tgnet.TLRPC;

/* renamed from: com.f.a.a.a.a.a */
public abstract class C1531a implements C1526a {
    /* renamed from: a */
    public static final CompressFormat f4660a = CompressFormat.PNG;
    /* renamed from: b */
    protected final File f4661b;
    /* renamed from: c */
    protected final File f4662c;
    /* renamed from: d */
    protected final C1533a f4663d;
    /* renamed from: e */
    protected int f4664e = TLRPC.MESSAGE_FLAG_EDITED;
    /* renamed from: f */
    protected CompressFormat f4665f = f4660a;
    /* renamed from: g */
    protected int f4666g = 100;

    public C1531a(File file, File file2, C1533a c1533a) {
        if (file == null) {
            throw new IllegalArgumentException("cacheDir argument must be not null");
        } else if (c1533a == null) {
            throw new IllegalArgumentException("fileNameGenerator argument must be not null");
        } else {
            this.f4661b = file;
            this.f4662c = file2;
            this.f4663d = c1533a;
        }
    }

    /* renamed from: a */
    public File mo1213a(String str) {
        return m7630b(str);
    }

    /* renamed from: a */
    public boolean mo1214a(String str, Bitmap bitmap) {
        File b = m7630b(str);
        File file = new File(b.getAbsolutePath() + ".tmp");
        Closeable bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file), this.f4664e);
        try {
            boolean compress = bitmap.compress(this.f4665f, this.f4666g, bufferedOutputStream);
            C1601b.m7930a(bufferedOutputStream);
            if (compress && !file.renameTo(b)) {
                compress = false;
            }
            if (!compress) {
                file.delete();
            }
            bitmap.recycle();
            return compress;
        } catch (Throwable th) {
            C1601b.m7930a(bufferedOutputStream);
            file.delete();
        }
    }

    /* renamed from: a */
    public boolean mo1215a(String str, InputStream inputStream, C1596a c1596a) {
        Closeable bufferedOutputStream;
        Throwable th;
        File b = m7630b(str);
        File file = new File(b.getAbsolutePath() + ".tmp");
        boolean a;
        try {
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file), this.f4664e);
            a = C1601b.m7933a(inputStream, bufferedOutputStream, c1596a, this.f4664e);
            try {
                C1601b.m7930a(bufferedOutputStream);
                if (a && !file.renameTo(b)) {
                    a = false;
                }
                if (!a) {
                    file.delete();
                }
                return a;
            } catch (Throwable th2) {
                th = th2;
                a = false;
                if (!a) {
                    file.delete();
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            a = false;
            if (a && !file.renameTo(b)) {
                a = false;
            }
            if (a) {
                file.delete();
            }
            throw th;
        }
    }

    /* renamed from: b */
    protected File m7630b(String str) {
        String a = this.f4663d.mo1216a(str);
        File file = this.f4661b;
        if (!(this.f4661b.exists() || this.f4661b.mkdirs() || this.f4662c == null || (!this.f4662c.exists() && !this.f4662c.mkdirs()))) {
            file = this.f4662c;
        }
        return new File(file, a);
    }
}
