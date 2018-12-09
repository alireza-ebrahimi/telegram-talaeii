package p033b.p034a.p035a.p036a;

import android.util.Log;

/* renamed from: b.a.a.a.b */
public class C1225b implements C1224l {
    /* renamed from: a */
    private int f3544a;

    public C1225b() {
        this.f3544a = 4;
    }

    public C1225b(int i) {
        this.f3544a = i;
    }

    /* renamed from: a */
    public void mo1061a(int i, String str, String str2) {
        m6382a(i, str, str2, false);
    }

    /* renamed from: a */
    public void m6382a(int i, String str, String str2, boolean z) {
        if (z || mo1064a(str, i)) {
            Log.println(i, str, str2);
        }
    }

    /* renamed from: a */
    public void mo1062a(String str, String str2) {
        mo1063a(str, str2, null);
    }

    /* renamed from: a */
    public void mo1063a(String str, String str2, Throwable th) {
        if (mo1064a(str, 3)) {
            Log.d(str, str2, th);
        }
    }

    /* renamed from: a */
    public boolean mo1064a(String str, int i) {
        return this.f3544a <= i;
    }

    /* renamed from: b */
    public void mo1065b(String str, String str2) {
        m6387b(str, str2, null);
    }

    /* renamed from: b */
    public void m6387b(String str, String str2, Throwable th) {
        if (mo1064a(str, 2)) {
            Log.v(str, str2, th);
        }
    }

    /* renamed from: c */
    public void mo1066c(String str, String str2) {
        m6389c(str, str2, null);
    }

    /* renamed from: c */
    public void m6389c(String str, String str2, Throwable th) {
        if (mo1064a(str, 4)) {
            Log.i(str, str2, th);
        }
    }

    /* renamed from: d */
    public void mo1067d(String str, String str2) {
        mo1068d(str, str2, null);
    }

    /* renamed from: d */
    public void mo1068d(String str, String str2, Throwable th) {
        if (mo1064a(str, 5)) {
            Log.w(str, str2, th);
        }
    }

    /* renamed from: e */
    public void mo1069e(String str, String str2) {
        mo1070e(str, str2, null);
    }

    /* renamed from: e */
    public void mo1070e(String str, String str2, Throwable th) {
        if (mo1064a(str, 6)) {
            Log.e(str, str2, th);
        }
    }
}
