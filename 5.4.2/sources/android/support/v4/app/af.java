package android.support.v4.app;

import android.os.Bundle;
import android.support.v4.app.ae.C0251a;
import android.support.v4.content.C0420k;
import android.support.v4.content.C0420k.C0252a;
import android.support.v4.content.C0420k.C0253b;
import android.support.v4.p022f.C0468d;
import android.support.v4.p022f.C0482l;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Modifier;

class af extends ae {
    /* renamed from: a */
    static boolean f838a = false;
    /* renamed from: b */
    final C0482l<C0254a> f839b = new C0482l();
    /* renamed from: c */
    final C0482l<C0254a> f840c = new C0482l();
    /* renamed from: d */
    final String f841d;
    /* renamed from: e */
    boolean f842e;
    /* renamed from: f */
    boolean f843f;
    /* renamed from: g */
    C0350w f844g;

    /* renamed from: android.support.v4.app.af$a */
    final class C0254a implements C0252a<Object>, C0253b<Object> {
        /* renamed from: a */
        final int f823a;
        /* renamed from: b */
        final Bundle f824b;
        /* renamed from: c */
        C0251a<Object> f825c;
        /* renamed from: d */
        C0420k<Object> f826d;
        /* renamed from: e */
        boolean f827e;
        /* renamed from: f */
        boolean f828f;
        /* renamed from: g */
        Object f829g;
        /* renamed from: h */
        boolean f830h;
        /* renamed from: i */
        boolean f831i;
        /* renamed from: j */
        boolean f832j;
        /* renamed from: k */
        boolean f833k;
        /* renamed from: l */
        boolean f834l;
        /* renamed from: m */
        boolean f835m;
        /* renamed from: n */
        C0254a f836n;
        /* renamed from: o */
        final /* synthetic */ af f837o;

        /* renamed from: a */
        void m1157a() {
            if (this.f831i && this.f832j) {
                this.f830h = true;
            } else if (!this.f830h) {
                this.f830h = true;
                if (af.f838a) {
                    Log.v("LoaderManager", "  Starting: " + this);
                }
                if (this.f826d == null && this.f825c != null) {
                    this.f826d = this.f825c.m1153a(this.f823a, this.f824b);
                }
                if (this.f826d == null) {
                    return;
                }
                if (!this.f826d.getClass().isMemberClass() || Modifier.isStatic(this.f826d.getClass().getModifiers())) {
                    if (!this.f835m) {
                        this.f826d.m1889a(this.f823a, this);
                        this.f826d.m1890a((C0252a) this);
                        this.f835m = true;
                    }
                    this.f826d.m1888a();
                    return;
                }
                throw new IllegalArgumentException("Object returned from onCreateLoader must not be a non-static inner member class: " + this.f826d);
            }
        }

        /* renamed from: a */
        void m1158a(C0420k<Object> c0420k, Object obj) {
            String str;
            if (this.f825c != null) {
                if (this.f837o.f844g != null) {
                    String str2 = this.f837o.f844g.f1068d.f1134u;
                    this.f837o.f844g.f1068d.f1134u = "onLoadFinished";
                    str = str2;
                } else {
                    str = null;
                }
                try {
                    if (af.f838a) {
                        Log.v("LoaderManager", "  onLoadFinished in " + c0420k + ": " + c0420k.m1887a(obj));
                    }
                    this.f825c.m1155a((C0420k) c0420k, obj);
                    this.f828f = true;
                } finally {
                    if (this.f837o.f844g != null) {
                        this.f837o.f844g.f1068d.f1134u = str;
                    }
                }
            }
        }

        /* renamed from: a */
        public void m1159a(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            printWriter.print(str);
            printWriter.print("mId=");
            printWriter.print(this.f823a);
            printWriter.print(" mArgs=");
            printWriter.println(this.f824b);
            printWriter.print(str);
            printWriter.print("mCallbacks=");
            printWriter.println(this.f825c);
            printWriter.print(str);
            printWriter.print("mLoader=");
            printWriter.println(this.f826d);
            if (this.f826d != null) {
                this.f826d.m1892a(str + "  ", fileDescriptor, printWriter, strArr);
            }
            if (this.f827e || this.f828f) {
                printWriter.print(str);
                printWriter.print("mHaveData=");
                printWriter.print(this.f827e);
                printWriter.print("  mDeliveredData=");
                printWriter.println(this.f828f);
                printWriter.print(str);
                printWriter.print("mData=");
                printWriter.println(this.f829g);
            }
            printWriter.print(str);
            printWriter.print("mStarted=");
            printWriter.print(this.f830h);
            printWriter.print(" mReportNextStart=");
            printWriter.print(this.f833k);
            printWriter.print(" mDestroyed=");
            printWriter.println(this.f834l);
            printWriter.print(str);
            printWriter.print("mRetaining=");
            printWriter.print(this.f831i);
            printWriter.print(" mRetainingStarted=");
            printWriter.print(this.f832j);
            printWriter.print(" mListenerRegistered=");
            printWriter.println(this.f835m);
            if (this.f836n != null) {
                printWriter.print(str);
                printWriter.println("Pending Loader ");
                printWriter.print(this.f836n);
                printWriter.println(":");
                this.f836n.m1159a(str + "  ", fileDescriptor, printWriter, strArr);
            }
        }

        /* renamed from: b */
        void m1160b() {
            if (af.f838a) {
                Log.v("LoaderManager", "  Retaining: " + this);
            }
            this.f831i = true;
            this.f832j = this.f830h;
            this.f830h = false;
            this.f825c = null;
        }

        /* renamed from: c */
        void m1161c() {
            if (this.f831i) {
                if (af.f838a) {
                    Log.v("LoaderManager", "  Finished Retaining: " + this);
                }
                this.f831i = false;
                if (!(this.f830h == this.f832j || this.f830h)) {
                    m1163e();
                }
            }
            if (this.f830h && this.f827e && !this.f833k) {
                m1158a(this.f826d, this.f829g);
            }
        }

        /* renamed from: d */
        void m1162d() {
            if (this.f830h && this.f833k) {
                this.f833k = false;
                if (this.f827e && !this.f831i) {
                    m1158a(this.f826d, this.f829g);
                }
            }
        }

        /* renamed from: e */
        void m1163e() {
            if (af.f838a) {
                Log.v("LoaderManager", "  Stopping: " + this);
            }
            this.f830h = false;
            if (!this.f831i && this.f826d != null && this.f835m) {
                this.f835m = false;
                this.f826d.m1891a((C0253b) this);
                this.f826d.m1894b(this);
                this.f826d.m1895c();
            }
        }

        /* renamed from: f */
        void m1164f() {
            String str;
            C0251a c0251a = null;
            if (af.f838a) {
                Log.v("LoaderManager", "  Destroying: " + this);
            }
            this.f834l = true;
            boolean z = this.f828f;
            this.f828f = false;
            if (this.f825c != null && this.f826d != null && this.f827e && z) {
                if (af.f838a) {
                    Log.v("LoaderManager", "  Resetting: " + this);
                }
                if (this.f837o.f844g != null) {
                    String str2 = this.f837o.f844g.f1068d.f1134u;
                    this.f837o.f844g.f1068d.f1134u = "onLoaderReset";
                    str = str2;
                } else {
                    str = null;
                }
                try {
                    this.f825c.m1154a(this.f826d);
                } finally {
                    c0251a = this.f837o.f844g;
                    if (c0251a != null) {
                        c0251a = this.f837o.f844g.f1068d;
                        c0251a.f1134u = str;
                    }
                }
            }
            this.f825c = c0251a;
            this.f829g = c0251a;
            this.f827e = false;
            if (this.f826d != null) {
                if (this.f835m) {
                    this.f835m = false;
                    this.f826d.m1891a((C0253b) this);
                    this.f826d.m1894b(this);
                }
                this.f826d.m1897e();
            }
            if (this.f836n != null) {
                this.f836n.m1164f();
            }
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(64);
            stringBuilder.append("LoaderInfo{");
            stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
            stringBuilder.append(" #");
            stringBuilder.append(this.f823a);
            stringBuilder.append(" : ");
            C0468d.m2014a(this.f826d, stringBuilder);
            stringBuilder.append("}}");
            return stringBuilder.toString();
        }
    }

    af(String str, C0350w c0350w, boolean z) {
        this.f841d = str;
        this.f844g = c0350w;
        this.f842e = z;
    }

    /* renamed from: a */
    void m1165a(C0350w c0350w) {
        this.f844g = c0350w;
    }

    /* renamed from: a */
    public void m1166a(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        int i = 0;
        if (this.f839b.m2042b() > 0) {
            printWriter.print(str);
            printWriter.println("Active Loaders:");
            String str2 = str + "    ";
            for (int i2 = 0; i2 < this.f839b.m2042b(); i2++) {
                C0254a c0254a = (C0254a) this.f839b.m2049e(i2);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(this.f839b.m2048d(i2));
                printWriter.print(": ");
                printWriter.println(c0254a.toString());
                c0254a.m1159a(str2, fileDescriptor, printWriter, strArr);
            }
        }
        if (this.f840c.m2042b() > 0) {
            printWriter.print(str);
            printWriter.println("Inactive Loaders:");
            String str3 = str + "    ";
            while (i < this.f840c.m2042b()) {
                c0254a = (C0254a) this.f840c.m2049e(i);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(this.f840c.m2048d(i));
                printWriter.print(": ");
                printWriter.println(c0254a.toString());
                c0254a.m1159a(str3, fileDescriptor, printWriter, strArr);
                i++;
            }
        }
    }

    /* renamed from: a */
    public boolean mo204a() {
        int b = this.f839b.m2042b();
        boolean z = false;
        for (int i = 0; i < b; i++) {
            C0254a c0254a = (C0254a) this.f839b.m2049e(i);
            int i2 = (!c0254a.f830h || c0254a.f828f) ? 0 : 1;
            z |= i2;
        }
        return z;
    }

    /* renamed from: b */
    void m1168b() {
        if (f838a) {
            Log.v("LoaderManager", "Starting in " + this);
        }
        if (this.f842e) {
            Throwable runtimeException = new RuntimeException("here");
            runtimeException.fillInStackTrace();
            Log.w("LoaderManager", "Called doStart when already started: " + this, runtimeException);
            return;
        }
        this.f842e = true;
        for (int b = this.f839b.m2042b() - 1; b >= 0; b--) {
            ((C0254a) this.f839b.m2049e(b)).m1157a();
        }
    }

    /* renamed from: c */
    void m1169c() {
        if (f838a) {
            Log.v("LoaderManager", "Stopping in " + this);
        }
        if (this.f842e) {
            for (int b = this.f839b.m2042b() - 1; b >= 0; b--) {
                ((C0254a) this.f839b.m2049e(b)).m1163e();
            }
            this.f842e = false;
            return;
        }
        Throwable runtimeException = new RuntimeException("here");
        runtimeException.fillInStackTrace();
        Log.w("LoaderManager", "Called doStop when not started: " + this, runtimeException);
    }

    /* renamed from: d */
    void m1170d() {
        if (f838a) {
            Log.v("LoaderManager", "Retaining in " + this);
        }
        if (this.f842e) {
            this.f843f = true;
            this.f842e = false;
            for (int b = this.f839b.m2042b() - 1; b >= 0; b--) {
                ((C0254a) this.f839b.m2049e(b)).m1160b();
            }
            return;
        }
        Throwable runtimeException = new RuntimeException("here");
        runtimeException.fillInStackTrace();
        Log.w("LoaderManager", "Called doRetain when not started: " + this, runtimeException);
    }

    /* renamed from: e */
    void m1171e() {
        if (this.f843f) {
            if (f838a) {
                Log.v("LoaderManager", "Finished Retaining in " + this);
            }
            this.f843f = false;
            for (int b = this.f839b.m2042b() - 1; b >= 0; b--) {
                ((C0254a) this.f839b.m2049e(b)).m1161c();
            }
        }
    }

    /* renamed from: f */
    void m1172f() {
        for (int b = this.f839b.m2042b() - 1; b >= 0; b--) {
            ((C0254a) this.f839b.m2049e(b)).f833k = true;
        }
    }

    /* renamed from: g */
    void m1173g() {
        for (int b = this.f839b.m2042b() - 1; b >= 0; b--) {
            ((C0254a) this.f839b.m2049e(b)).m1162d();
        }
    }

    /* renamed from: h */
    void m1174h() {
        int b;
        if (!this.f843f) {
            if (f838a) {
                Log.v("LoaderManager", "Destroying Active in " + this);
            }
            for (b = this.f839b.m2042b() - 1; b >= 0; b--) {
                ((C0254a) this.f839b.m2049e(b)).m1164f();
            }
            this.f839b.m2045c();
        }
        if (f838a) {
            Log.v("LoaderManager", "Destroying Inactive in " + this);
        }
        for (b = this.f840c.m2042b() - 1; b >= 0; b--) {
            ((C0254a) this.f840c.m2049e(b)).m1164f();
        }
        this.f840c.m2045c();
        this.f844g = null;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append("LoaderManager{");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringBuilder.append(" in ");
        C0468d.m2014a(this.f844g, stringBuilder);
        stringBuilder.append("}}");
        return stringBuilder.toString();
    }
}
