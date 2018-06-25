package android.support.v4.content;

import android.support.v4.p022f.C0468d;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* renamed from: android.support.v4.content.k */
public class C0420k<D> {
    /* renamed from: a */
    int f1165a;
    /* renamed from: b */
    C0253b<D> f1166b;
    /* renamed from: c */
    C0252a<D> f1167c;
    /* renamed from: d */
    boolean f1168d;
    /* renamed from: e */
    boolean f1169e;
    /* renamed from: f */
    boolean f1170f;
    /* renamed from: g */
    boolean f1171g;
    /* renamed from: h */
    boolean f1172h;

    /* renamed from: android.support.v4.content.k$a */
    public interface C0252a<D> {
    }

    /* renamed from: android.support.v4.content.k$b */
    public interface C0253b<D> {
    }

    /* renamed from: a */
    public String m1887a(D d) {
        StringBuilder stringBuilder = new StringBuilder(64);
        C0468d.m2014a(d, stringBuilder);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    /* renamed from: a */
    public final void m1888a() {
        this.f1168d = true;
        this.f1170f = false;
        this.f1169e = false;
        m1893b();
    }

    /* renamed from: a */
    public void m1889a(int i, C0253b<D> c0253b) {
        if (this.f1166b != null) {
            throw new IllegalStateException("There is already a listener registered");
        }
        this.f1166b = c0253b;
        this.f1165a = i;
    }

    /* renamed from: a */
    public void m1890a(C0252a<D> c0252a) {
        if (this.f1167c != null) {
            throw new IllegalStateException("There is already a listener registered");
        }
        this.f1167c = c0252a;
    }

    /* renamed from: a */
    public void m1891a(C0253b<D> c0253b) {
        if (this.f1166b == null) {
            throw new IllegalStateException("No listener register");
        } else if (this.f1166b != c0253b) {
            throw new IllegalArgumentException("Attempting to unregister the wrong listener");
        } else {
            this.f1166b = null;
        }
    }

    /* renamed from: a */
    public void m1892a(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.print(str);
        printWriter.print("mId=");
        printWriter.print(this.f1165a);
        printWriter.print(" mListener=");
        printWriter.println(this.f1166b);
        if (this.f1168d || this.f1171g || this.f1172h) {
            printWriter.print(str);
            printWriter.print("mStarted=");
            printWriter.print(this.f1168d);
            printWriter.print(" mContentChanged=");
            printWriter.print(this.f1171g);
            printWriter.print(" mProcessingChange=");
            printWriter.println(this.f1172h);
        }
        if (this.f1169e || this.f1170f) {
            printWriter.print(str);
            printWriter.print("mAbandoned=");
            printWriter.print(this.f1169e);
            printWriter.print(" mReset=");
            printWriter.println(this.f1170f);
        }
    }

    /* renamed from: b */
    protected void m1893b() {
    }

    /* renamed from: b */
    public void m1894b(C0252a<D> c0252a) {
        if (this.f1167c == null) {
            throw new IllegalStateException("No listener register");
        } else if (this.f1167c != c0252a) {
            throw new IllegalArgumentException("Attempting to unregister the wrong listener");
        } else {
            this.f1167c = null;
        }
    }

    /* renamed from: c */
    public void m1895c() {
        this.f1168d = false;
        m1896d();
    }

    /* renamed from: d */
    protected void m1896d() {
    }

    /* renamed from: e */
    public void m1897e() {
        m1898f();
        this.f1170f = true;
        this.f1168d = false;
        this.f1169e = false;
        this.f1171g = false;
        this.f1172h = false;
    }

    /* renamed from: f */
    protected void m1898f() {
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(64);
        C0468d.m2014a(this, stringBuilder);
        stringBuilder.append(" id=");
        stringBuilder.append(this.f1165a);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
