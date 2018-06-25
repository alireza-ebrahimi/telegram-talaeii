package org.ocpsoft.prettytime.p147b;

import java.util.Locale;
import java.util.ResourceBundle;
import org.ocpsoft.prettytime.C2451d;
import org.ocpsoft.prettytime.C2453a;
import org.ocpsoft.prettytime.C2455b;
import org.ocpsoft.prettytime.p146a.C2452a;

/* renamed from: org.ocpsoft.prettytime.b.b */
public class C2456b extends C2452a implements C2455b<C2456b>, C2451d {
    /* renamed from: a */
    private ResourceBundle f8213a;
    /* renamed from: b */
    private final C2458c f8214b;
    /* renamed from: c */
    private C2451d f8215c;

    public C2456b(C2458c c2458c) {
        this.f8214b = c2458c;
    }

    /* renamed from: a */
    public /* synthetic */ Object mo3403a(Locale locale) {
        return m12041b(locale);
    }

    /* renamed from: a */
    public String mo3396a(C2453a c2453a) {
        return this.f8215c == null ? super.mo3396a(c2453a) : this.f8215c.mo3396a(c2453a);
    }

    /* renamed from: a */
    public String mo3397a(C2453a c2453a, String str) {
        return this.f8215c == null ? super.mo3397a(c2453a, str) : this.f8215c.mo3397a(c2453a, str);
    }

    /* renamed from: b */
    public C2456b m12041b(Locale locale) {
        this.f8213a = ResourceBundle.getBundle(this.f8214b.m12049d(), locale);
        if (this.f8213a instanceof C2459d) {
            C2451d a = ((C2459d) this.f8213a).mo3408a(this.f8214b);
            if (a != null) {
                this.f8215c = a;
            }
        } else {
            this.f8215c = null;
        }
        if (this.f8215c == null) {
            m12011a(this.f8213a.getString(this.f8214b.mo3406c() + "Pattern"));
            m12013b(this.f8213a.getString(this.f8214b.mo3406c() + "FuturePrefix"));
            m12014c(this.f8213a.getString(this.f8214b.mo3406c() + "FutureSuffix"));
            m12015d(this.f8213a.getString(this.f8214b.mo3406c() + "PastPrefix"));
            m12016e(this.f8213a.getString(this.f8214b.mo3406c() + "PastSuffix"));
            m12017f(this.f8213a.getString(this.f8214b.mo3406c() + "SingularName"));
            m12018g(this.f8213a.getString(this.f8214b.mo3406c() + "PluralName"));
            try {
                mo3411i(this.f8213a.getString(this.f8214b.mo3406c() + "FuturePluralName"));
            } catch (Exception e) {
            }
            try {
                m12019h(this.f8213a.getString(this.f8214b.mo3406c() + "FutureSingularName"));
            } catch (Exception e2) {
            }
            try {
                mo3412k(this.f8213a.getString(this.f8214b.mo3406c() + "PastPluralName"));
            } catch (Exception e3) {
            }
            try {
                m12021j(this.f8213a.getString(this.f8214b.mo3406c() + "PastSingularName"));
            } catch (Exception e4) {
            }
        }
        return this;
    }
}
