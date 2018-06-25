package p033b.p034a.p035a.p036a.p037a.p042d;

import android.content.Context;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import p033b.p034a.p035a.p036a.p037a.p039b.C1110i;
import p033b.p034a.p035a.p036a.p037a.p039b.C1129r;

/* renamed from: b.a.a.a.a.d.h */
public class C1172h implements C1168c {
    /* renamed from: a */
    private final Context f3396a;
    /* renamed from: b */
    private final File f3397b;
    /* renamed from: c */
    private final String f3398c;
    /* renamed from: d */
    private final File f3399d;
    /* renamed from: e */
    private C1129r f3400e = new C1129r(this.f3399d);
    /* renamed from: f */
    private File f3401f;

    public C1172h(Context context, File file, String str, String str2) {
        this.f3396a = context;
        this.f3397b = file;
        this.f3398c = str2;
        this.f3399d = new File(this.f3397b, str);
        m6211e();
    }

    /* renamed from: a */
    private void m6210a(File file, File file2) {
        Throwable th;
        Closeable closeable = null;
        Closeable fileInputStream;
        try {
            fileInputStream = new FileInputStream(file);
            try {
                closeable = mo1043a(file2);
                C1110i.m6013a((InputStream) fileInputStream, (OutputStream) closeable, new byte[1024]);
                C1110i.m6011a(fileInputStream, "Failed to close file input stream");
                C1110i.m6011a(closeable, "Failed to close output stream");
                file.delete();
            } catch (Throwable th2) {
                th = th2;
                C1110i.m6011a(fileInputStream, "Failed to close file input stream");
                C1110i.m6011a(closeable, "Failed to close output stream");
                file.delete();
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            fileInputStream = null;
            C1110i.m6011a(fileInputStream, "Failed to close file input stream");
            C1110i.m6011a(closeable, "Failed to close output stream");
            file.delete();
            throw th;
        }
    }

    /* renamed from: e */
    private void m6211e() {
        this.f3401f = new File(this.f3397b, this.f3398c);
        if (!this.f3401f.exists()) {
            this.f3401f.mkdirs();
        }
    }

    /* renamed from: a */
    public int mo1034a() {
        return this.f3400e.m6097a();
    }

    /* renamed from: a */
    public OutputStream mo1043a(File file) {
        return new FileOutputStream(file);
    }

    /* renamed from: a */
    public List<File> mo1035a(int i) {
        List<File> arrayList = new ArrayList();
        for (Object add : this.f3401f.listFiles()) {
            arrayList.add(add);
            if (arrayList.size() >= i) {
                break;
            }
        }
        return arrayList;
    }

    /* renamed from: a */
    public void mo1036a(String str) {
        this.f3400e.close();
        m6210a(this.f3399d, new File(this.f3401f, str));
        this.f3400e = new C1129r(this.f3399d);
    }

    /* renamed from: a */
    public void mo1037a(List<File> list) {
        for (File file : list) {
            C1110i.m6008a(this.f3396a, String.format("deleting sent analytics file %s", new Object[]{file.getName()}));
            file.delete();
        }
    }

    /* renamed from: a */
    public void mo1038a(byte[] bArr) {
        this.f3400e.m6099a(bArr);
    }

    /* renamed from: a */
    public boolean mo1039a(int i, int i2) {
        return this.f3400e.m6101a(i, i2);
    }

    /* renamed from: b */
    public boolean mo1040b() {
        return this.f3400e.m6102b();
    }

    /* renamed from: c */
    public List<File> mo1041c() {
        return Arrays.asList(this.f3401f.listFiles());
    }

    /* renamed from: d */
    public void mo1042d() {
        try {
            this.f3400e.close();
        } catch (IOException e) {
        }
        this.f3399d.delete();
    }
}
