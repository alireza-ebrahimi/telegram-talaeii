package com.google.firebase.auth;

import android.support.annotation.Keep;
import android.util.Log;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.internal.firebase_auth.zzao;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.C1897b;
import com.google.firebase.C1897b.C1863c;
import com.google.firebase.auth.internal.C1823b;
import com.google.firebase.auth.internal.C1860a;
import com.google.firebase.auth.internal.C1862d;
import com.google.firebase.auth.internal.C1864e;
import com.google.firebase.auth.internal.C1866g;
import com.google.firebase.auth.internal.C1872l;
import com.google.firebase.auth.internal.C1879t;
import com.google.firebase.auth.p104a.p105a.C1835h;
import com.google.firebase.auth.p104a.p105a.C1842m;
import com.google.firebase.auth.p104a.p105a.C1845s;
import com.google.firebase.auth.p104a.p105a.C1848v;
import com.google.firebase.p107c.C1899b;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class FirebaseAuth implements C1823b {
    /* renamed from: a */
    private C1897b f5438a;
    /* renamed from: b */
    private final List<C1821b> f5439b;
    /* renamed from: c */
    private final List<C1860a> f5440c;
    /* renamed from: d */
    private List<C1820a> f5441d;
    /* renamed from: e */
    private C1835h f5442e;
    /* renamed from: f */
    private FirebaseUser f5443f;
    /* renamed from: g */
    private C1872l f5444g;
    /* renamed from: h */
    private final Object f5445h;
    /* renamed from: i */
    private C1862d f5446i;
    /* renamed from: j */
    private C1864e f5447j;
    /* renamed from: k */
    private C1866g f5448k;

    /* renamed from: com.google.firebase.auth.FirebaseAuth$a */
    public interface C1820a {
        /* renamed from: a */
        void m8491a(FirebaseAuth firebaseAuth);
    }

    /* renamed from: com.google.firebase.auth.FirebaseAuth$b */
    public interface C1821b {
        /* renamed from: a */
        void m8492a(FirebaseAuth firebaseAuth);
    }

    public FirebaseAuth(C1897b c1897b) {
        this(c1897b, C1845s.m8599a(c1897b.m8690a(), new C1848v(c1897b.m8696c().m8752a()).m8602a()), new C1862d(c1897b.m8690a(), c1897b.m8698f()));
    }

    @VisibleForTesting
    private FirebaseAuth(C1897b c1897b, C1835h c1835h, C1862d c1862d) {
        this.f5445h = new Object();
        this.f5438a = (C1897b) Preconditions.checkNotNull(c1897b);
        this.f5442e = (C1835h) Preconditions.checkNotNull(c1835h);
        this.f5446i = (C1862d) Preconditions.checkNotNull(c1862d);
        this.f5444g = new C1872l();
        this.f5439b = new CopyOnWriteArrayList();
        this.f5440c = new CopyOnWriteArrayList();
        this.f5441d = new CopyOnWriteArrayList();
        this.f5448k = C1866g.m8623a();
        this.f5443f = this.f5446i.m8610a();
        if (this.f5443f != null) {
            zzao b = this.f5446i.m8614b(this.f5443f);
            if (b != null) {
                m8504a(this.f5443f, b, false);
            }
        }
    }

    /* renamed from: a */
    private final void m8494a(FirebaseUser firebaseUser) {
        if (firebaseUser != null) {
            String a = firebaseUser.mo3028a();
            Log.d("FirebaseAuth", new StringBuilder(String.valueOf(a).length() + 45).append("Notifying id token listeners about user ( ").append(a).append(" ).").toString());
        } else {
            Log.d("FirebaseAuth", "Notifying id token listeners about a sign-out event.");
        }
        this.f5448k.execute(new C1886p(this, new C1899b(firebaseUser != null ? firebaseUser.mo3037i() : null)));
    }

    @VisibleForTesting
    /* renamed from: a */
    private final synchronized void m8495a(C1864e c1864e) {
        this.f5447j = c1864e;
        this.f5438a.m8692a((C1863c) c1864e);
    }

    /* renamed from: b */
    private final void m8497b(FirebaseUser firebaseUser) {
        if (firebaseUser != null) {
            String a = firebaseUser.mo3028a();
            Log.d("FirebaseAuth", new StringBuilder(String.valueOf(a).length() + 47).append("Notifying auth state listeners about user ( ").append(a).append(" ).").toString());
        } else {
            Log.d("FirebaseAuth", "Notifying auth state listeners about a sign-out event.");
        }
        this.f5448k.execute(new C1887q(this));
    }

    @VisibleForTesting
    /* renamed from: d */
    private final synchronized C1864e m8499d() {
        if (this.f5447j == null) {
            m8495a(new C1864e(this.f5438a));
        }
        return this.f5447j;
    }

    @Keep
    public static FirebaseAuth getInstance() {
        return (FirebaseAuth) C1897b.m8684d().m8691a(FirebaseAuth.class);
    }

    @Keep
    public static FirebaseAuth getInstance(C1897b c1897b) {
        return (FirebaseAuth) c1897b.m8691a(FirebaseAuth.class);
    }

    /* renamed from: a */
    public final Task<C1881j> m8501a(FirebaseUser firebaseUser, boolean z) {
        if (firebaseUser == null) {
            return Tasks.forException(C1842m.m8593a(new Status(17495)));
        }
        zzao g = this.f5443f.mo3035g();
        return (!g.isValid() || z) ? this.f5442e.m8570a(this.f5438a, firebaseUser, g.zzap(), new C1888r(this)) : Tasks.forResult(C1879t.m8633a(g.zzaw()));
    }

    /* renamed from: a */
    public Task<C1881j> m8502a(boolean z) {
        return m8501a(this.f5443f, z);
    }

    /* renamed from: a */
    public FirebaseUser m8503a() {
        return this.f5443f;
    }

    /* renamed from: a */
    public final void m8504a(FirebaseUser firebaseUser, zzao zzao, boolean z) {
        Object obj;
        Object obj2 = 1;
        Preconditions.checkNotNull(firebaseUser);
        Preconditions.checkNotNull(zzao);
        if (this.f5443f == null) {
            obj = 1;
        } else {
            obj = !this.f5443f.mo3035g().zzaw().equals(zzao.zzaw()) ? 1 : null;
            boolean equals = this.f5443f.mo3028a().equals(firebaseUser.mo3028a());
            if (equals && obj == null) {
                obj = null;
            } else {
                int i = 1;
            }
            if (equals) {
                obj2 = null;
            }
        }
        Preconditions.checkNotNull(firebaseUser);
        if (this.f5443f == null) {
            this.f5443f = firebaseUser;
        } else {
            this.f5443f.mo3027a(firebaseUser.mo3032d());
            if (!firebaseUser.mo3030b()) {
                this.f5443f.mo3033e();
            }
        }
        if (z) {
            this.f5446i.m8611a(this.f5443f);
        }
        if (obj != null) {
            if (this.f5443f != null) {
                this.f5443f.mo3029a(zzao);
            }
            m8494a(this.f5443f);
        }
        if (obj2 != null) {
            m8497b(this.f5443f);
        }
        if (z) {
            this.f5446i.m8612a(firebaseUser, zzao);
        }
        m8499d().m8622a(this.f5443f.mo3035g());
    }

    /* renamed from: b */
    public final void m8505b() {
        if (this.f5443f != null) {
            C1862d c1862d = this.f5446i;
            Preconditions.checkNotNull(this.f5443f);
            c1862d.m8613a(String.format("com.google.firebase.auth.GET_TOKEN_RESPONSE.%s", new Object[]{r1.mo3028a()}));
            this.f5443f = null;
        }
        this.f5446i.m8613a("com.google.firebase.auth.FIREBASE_USER");
        m8494a(null);
        m8497b(null);
    }

    /* renamed from: c */
    public void m8506c() {
        m8505b();
        if (this.f5447j != null) {
            this.f5447j.m8620a();
        }
    }
}
