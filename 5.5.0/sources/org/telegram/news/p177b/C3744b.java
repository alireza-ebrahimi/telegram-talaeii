package org.telegram.news.p177b;

import android.text.TextUtils;
import android.widget.TextView;
import com.google.p098a.p099a.C1662c;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import org.ocpsoft.prettytime.C2473c;

/* renamed from: org.telegram.news.b.b */
public class C3744b {
    /* renamed from: A */
    private ArrayList<C3744b> f9966A;
    /* renamed from: a */
    long f9967a;
    /* renamed from: b */
    String f9968b;
    /* renamed from: c */
    String f9969c;
    /* renamed from: d */
    long f9970d;
    @C1662c(a = "irCreationDate")
    /* renamed from: e */
    String f9971e;
    /* renamed from: f */
    String f9972f;
    /* renamed from: g */
    String f9973g;
    /* renamed from: h */
    int f9974h;
    /* renamed from: i */
    int f9975i;
    /* renamed from: j */
    ArrayList<String> f9976j;
    /* renamed from: k */
    C3743a[] f9977k;
    /* renamed from: l */
    C3743a[] f9978l;
    /* renamed from: m */
    String f9979m;
    /* renamed from: n */
    public boolean f9980n = false;
    /* renamed from: o */
    int f9981o;
    /* renamed from: p */
    private String f9982p;
    /* renamed from: q */
    private String f9983q;
    /* renamed from: r */
    private String f9984r;
    /* renamed from: s */
    private int f9985s;
    /* renamed from: t */
    private String f9986t;
    /* renamed from: u */
    private String f9987u;
    /* renamed from: v */
    private boolean f9988v = false;
    /* renamed from: w */
    private String f9989w;
    /* renamed from: x */
    private int f9990x;
    /* renamed from: y */
    private ArrayList<C3745c> f9991y;
    /* renamed from: z */
    private C3746d f9992z;

    /* renamed from: a */
    public static void m13759a(TextView textView, C3744b c3744b) {
        if (TextUtils.isEmpty(c3744b.m13806w())) {
            try {
                textView.setText(new C2473c(new Locale("FA")).m12075b(new Date((long) (Double.valueOf((double) c3744b.m13791j()).doubleValue() * 1000.0d))));
                return;
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        textView.setText(c3744b.m13806w() + TtmlNode.ANONYMOUS_REGION_ID);
    }

    /* renamed from: a */
    public String m13760a() {
        return this.f9979m;
    }

    /* renamed from: a */
    public void m13761a(int i) {
        this.f9981o = i;
    }

    /* renamed from: a */
    public void m13762a(long j) {
        this.f9970d = j;
    }

    /* renamed from: a */
    public void m13763a(String str) {
        this.f9979m = str;
    }

    /* renamed from: a */
    public void m13764a(ArrayList<C3744b> arrayList) {
        this.f9966A = arrayList;
    }

    /* renamed from: a */
    public void m13765a(C3746d c3746d) {
        this.f9992z = c3746d;
    }

    /* renamed from: a */
    public void m13766a(C3743a[] c3743aArr) {
        this.f9978l = c3743aArr;
    }

    /* renamed from: b */
    public int m13767b() {
        return this.f9981o;
    }

    /* renamed from: b */
    public void m13768b(int i) {
        this.f9975i = i;
    }

    /* renamed from: b */
    public void m13769b(long j) {
        this.f9967a = j;
    }

    /* renamed from: b */
    public void m13770b(String str) {
        this.f9973g = str;
    }

    /* renamed from: b */
    public void m13771b(ArrayList<String> arrayList) {
        this.f9976j = arrayList;
    }

    /* renamed from: b */
    public void m13772b(C3743a[] c3743aArr) {
        this.f9977k = c3743aArr;
    }

    /* renamed from: c */
    public ArrayList<C3744b> m13773c() {
        return this.f9966A;
    }

    /* renamed from: c */
    public void m13774c(int i) {
        this.f9974h = i;
    }

    /* renamed from: c */
    public void m13775c(String str) {
        this.f9968b = str;
    }

    /* renamed from: c */
    public void m13776c(ArrayList<C3745c> arrayList) {
        this.f9991y = arrayList;
    }

    /* renamed from: d */
    public void m13777d(int i) {
        this.f9985s = i;
    }

    /* renamed from: d */
    public void m13778d(String str) {
        this.f9969c = str;
    }

    /* renamed from: d */
    public C3743a[] m13779d() {
        if (this.f9978l == null) {
            this.f9978l = new C3743a[0];
        }
        return this.f9978l;
    }

    /* renamed from: e */
    public void m13780e(int i) {
        this.f9990x = i;
    }

    /* renamed from: e */
    public void m13781e(String str) {
        this.f9972f = str;
    }

    /* renamed from: e */
    public C3743a[] m13782e() {
        if (this.f9977k == null) {
            this.f9977k = new C3743a[0];
        }
        return this.f9977k;
    }

    /* renamed from: f */
    public int m13783f() {
        if (this.f9975i == 0 && m13782e().length > 0) {
            for (C3743a a : m13782e()) {
                if (a.m13753a() == 3) {
                    this.f9975i++;
                }
            }
        }
        return this.f9975i;
    }

    /* renamed from: f */
    public void m13784f(String str) {
        this.f9986t = str;
    }

    /* renamed from: g */
    public String m13785g() {
        return this.f9973g;
    }

    /* renamed from: g */
    public void m13786g(String str) {
        this.f9983q = str;
    }

    /* renamed from: h */
    public String m13787h() {
        return this.f9968b;
    }

    /* renamed from: h */
    public void m13788h(String str) {
        this.f9984r = str;
    }

    /* renamed from: i */
    public String m13789i() {
        return this.f9969c;
    }

    /* renamed from: i */
    public void m13790i(String str) {
        this.f9982p = str;
    }

    /* renamed from: j */
    public long m13791j() {
        return this.f9970d;
    }

    /* renamed from: j */
    public void m13792j(String str) {
        this.f9987u = str;
    }

    /* renamed from: k */
    public String m13793k() {
        return this.f9972f;
    }

    /* renamed from: k */
    public void m13794k(String str) {
        this.f9989w = str;
    }

    /* renamed from: l */
    public String m13795l() {
        return this.f9986t;
    }

    /* renamed from: m */
    public ArrayList<String> m13796m() {
        if (this.f9976j == null) {
            this.f9976j = new ArrayList();
            if (this.f9976j.size() == 0 && m13782e().length > 0) {
                for (C3743a c3743a : m13782e()) {
                    if (c3743a.m13753a() == 2) {
                        Collections.addAll(this.f9976j, c3743a.m13754b());
                    }
                }
            }
        }
        return this.f9976j;
    }

    /* renamed from: n */
    public int m13797n() {
        if (this.f9974h == 0 && m13782e().length > 0) {
            for (C3743a c3743a : m13782e()) {
                if (c3743a.m13753a() == 2) {
                    this.f9974h = c3743a.m13754b().length + this.f9974h;
                }
            }
        }
        return this.f9974h;
    }

    /* renamed from: o */
    public String m13798o() {
        return this.f9983q;
    }

    /* renamed from: p */
    public String m13799p() {
        return this.f9984r;
    }

    /* renamed from: q */
    public String m13800q() {
        return this.f9982p;
    }

    /* renamed from: r */
    public ArrayList<C3745c> m13801r() {
        if (this.f9991y == null) {
            this.f9991y = new ArrayList();
        }
        return this.f9991y;
    }

    /* renamed from: s */
    public String m13802s() {
        return this.f9987u;
    }

    /* renamed from: t */
    public int m13803t() {
        return this.f9985s;
    }

    /* renamed from: u */
    public C3746d m13804u() {
        return this.f9992z;
    }

    /* renamed from: v */
    public int m13805v() {
        return this.f9990x;
    }

    /* renamed from: w */
    public String m13806w() {
        return this.f9971e;
    }
}
