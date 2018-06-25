package android.support.v4.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.p022f.C0463k;
import android.view.LayoutInflater;
import android.view.View;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* renamed from: android.support.v4.app.w */
public abstract class C0350w<E> extends C0225u {
    /* renamed from: a */
    private final Activity f1065a;
    /* renamed from: b */
    final Context f1066b;
    /* renamed from: c */
    final int f1067c;
    /* renamed from: d */
    final C0366y f1068d;
    /* renamed from: e */
    private final Handler f1069e;
    /* renamed from: f */
    private C0463k<String, ae> f1070f;
    /* renamed from: g */
    private boolean f1071g;
    /* renamed from: h */
    private af f1072h;
    /* renamed from: i */
    private boolean f1073i;
    /* renamed from: j */
    private boolean f1074j;

    C0350w(Activity activity, Context context, Handler handler, int i) {
        this.f1068d = new C0366y();
        this.f1065a = activity;
        this.f1066b = context;
        this.f1069e = handler;
        this.f1067c = i;
    }

    C0350w(C0353t c0353t) {
        this(c0353t, c0353t, c0353t.f1079c, 0);
    }

    /* renamed from: a */
    af m1490a(String str, boolean z, boolean z2) {
        if (this.f1070f == null) {
            this.f1070f = new C0463k();
        }
        af afVar = (af) this.f1070f.get(str);
        if (afVar == null && z2) {
            afVar = new af(str, this, z);
            this.f1070f.put(str, afVar);
            return afVar;
        } else if (!z || afVar == null || afVar.f842e) {
            return afVar;
        } else {
            afVar.m1168b();
            return afVar;
        }
    }

    /* renamed from: a */
    public View mo199a(int i) {
        return null;
    }

    /* renamed from: a */
    public void mo259a(Fragment fragment, Intent intent, int i, Bundle bundle) {
        if (i != -1) {
            throw new IllegalStateException("Starting activity with a requestCode requires a FragmentActivity host");
        }
        this.f1066b.startActivity(intent);
    }

    /* renamed from: a */
    public void mo260a(Fragment fragment, IntentSender intentSender, int i, Intent intent, int i2, int i3, int i4, Bundle bundle) {
        if (i != -1) {
            throw new IllegalStateException("Starting intent sender with a requestCode requires a FragmentActivity host");
        }
        C0236a.m1079a(this.f1065a, intentSender, i, intent, i2, i3, i4, bundle);
    }

    /* renamed from: a */
    public void mo261a(Fragment fragment, String[] strArr, int i) {
    }

    /* renamed from: a */
    void m1495a(C0463k<String, ae> c0463k) {
        if (c0463k != null) {
            int size = c0463k.size();
            for (int i = 0; i < size; i++) {
                ((af) c0463k.m1986c(i)).m1165a(this);
            }
        }
        this.f1070f = c0463k;
    }

    /* renamed from: a */
    public void mo262a(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    }

    /* renamed from: a */
    void m1497a(boolean z) {
        this.f1071g = z;
        if (this.f1072h != null && this.f1074j) {
            this.f1074j = false;
            if (z) {
                this.f1072h.m1170d();
            } else {
                this.f1072h.m1169c();
            }
        }
    }

    /* renamed from: a */
    public boolean mo200a() {
        return true;
    }

    /* renamed from: a */
    public boolean mo263a(Fragment fragment) {
        return true;
    }

    /* renamed from: a */
    public boolean mo264a(String str) {
        return false;
    }

    /* renamed from: b */
    public LayoutInflater mo265b() {
        return (LayoutInflater) this.f1066b.getSystemService("layout_inflater");
    }

    /* renamed from: b */
    void mo266b(Fragment fragment) {
    }

    /* renamed from: b */
    void m1503b(String str) {
        if (this.f1070f != null) {
            af afVar = (af) this.f1070f.get(str);
            if (afVar != null && !afVar.f843f) {
                afVar.m1174h();
                this.f1070f.remove(str);
            }
        }
    }

    /* renamed from: b */
    void m1504b(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.print(str);
        printWriter.print("mLoadersStarted=");
        printWriter.println(this.f1074j);
        if (this.f1072h != null) {
            printWriter.print(str);
            printWriter.print("Loader Manager ");
            printWriter.print(Integer.toHexString(System.identityHashCode(this.f1072h)));
            printWriter.println(":");
            this.f1072h.m1166a(str + "  ", fileDescriptor, printWriter, strArr);
        }
    }

    /* renamed from: d */
    public void mo267d() {
    }

    /* renamed from: e */
    public boolean mo268e() {
        return true;
    }

    /* renamed from: f */
    public int mo269f() {
        return this.f1067c;
    }

    /* renamed from: g */
    public abstract E mo270g();

    /* renamed from: h */
    Activity m1509h() {
        return this.f1065a;
    }

    /* renamed from: i */
    Context m1510i() {
        return this.f1066b;
    }

    /* renamed from: j */
    Handler m1511j() {
        return this.f1069e;
    }

    /* renamed from: k */
    C0366y m1512k() {
        return this.f1068d;
    }

    /* renamed from: l */
    boolean m1513l() {
        return this.f1071g;
    }

    /* renamed from: m */
    void m1514m() {
        if (!this.f1074j) {
            this.f1074j = true;
            if (this.f1072h != null) {
                this.f1072h.m1168b();
            } else if (!this.f1073i) {
                this.f1072h = m1490a("(root)", this.f1074j, false);
                if (!(this.f1072h == null || this.f1072h.f842e)) {
                    this.f1072h.m1168b();
                }
            }
            this.f1073i = true;
        }
    }

    /* renamed from: n */
    void m1515n() {
        if (this.f1072h != null) {
            this.f1072h.m1174h();
        }
    }

    /* renamed from: o */
    void m1516o() {
        if (this.f1070f != null) {
            int size = this.f1070f.size();
            af[] afVarArr = new af[size];
            for (int i = size - 1; i >= 0; i--) {
                afVarArr[i] = (af) this.f1070f.m1986c(i);
            }
            for (int i2 = 0; i2 < size; i2++) {
                af afVar = afVarArr[i2];
                afVar.m1171e();
                afVar.m1173g();
            }
        }
    }

    /* renamed from: p */
    C0463k<String, ae> m1517p() {
        int i;
        int i2 = 0;
        if (this.f1070f != null) {
            int size = this.f1070f.size();
            af[] afVarArr = new af[size];
            for (int i3 = size - 1; i3 >= 0; i3--) {
                afVarArr[i3] = (af) this.f1070f.m1986c(i3);
            }
            boolean l = m1513l();
            i = 0;
            while (i2 < size) {
                af afVar = afVarArr[i2];
                if (!afVar.f843f && l) {
                    if (!afVar.f842e) {
                        afVar.m1168b();
                    }
                    afVar.m1170d();
                }
                if (afVar.f843f) {
                    i = 1;
                } else {
                    afVar.m1174h();
                    this.f1070f.remove(afVar.f841d);
                }
                i2++;
            }
        } else {
            i = 0;
        }
        return i != 0 ? this.f1070f : null;
    }
}
