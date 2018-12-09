package org.telegram.news.p175c;

import android.app.Activity;
import android.content.Context;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.telegram.customization.dynamicadapter.data.SlsTag;
import org.telegram.news.p177b.C3744b;
import org.telegram.news.p177b.C3745c;

/* renamed from: org.telegram.news.c.c */
public class C3751c {
    /* renamed from: a */
    C3745c f10013a;
    /* renamed from: b */
    private ArrayList<C3744b> f10014b;
    /* renamed from: c */
    private boolean f10015c = false;
    /* renamed from: d */
    private boolean f10016d = false;
    /* renamed from: e */
    private boolean f10017e = false;
    /* renamed from: f */
    private boolean f10018f = false;

    /* renamed from: a */
    public C3744b m13830a(int i) {
        return i >= m13843e() ? null : (C3744b) m13839b().get(i);
    }

    /* renamed from: a */
    public C3744b m13831a(C3744b c3744b) {
        if (c3744b != null && c3744b.m13791j() > System.currentTimeMillis() / 1000) {
            c3744b.m13762a(System.currentTimeMillis() / 1000);
        }
        return c3744b;
    }

    /* renamed from: a */
    public C3745c m13832a() {
        if (this.f10013a == null) {
            this.f10013a = new C3745c();
            this.f10013a.m13809b(SlsTag.DEFAULT_TAG_COLOR);
            this.f10013a.m13807a(0);
            this.f10013a.m13808a(TtmlNode.ANONYMOUS_REGION_ID);
        }
        return this.f10013a;
    }

    /* renamed from: a */
    public void m13833a(Context context, ArrayList<C3744b> arrayList, C3727a c3727a) {
        if (arrayList != null) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                m13834a(context, (C3744b) it.next(), c3727a);
            }
        }
    }

    /* renamed from: a */
    public synchronized void m13834a(Context context, final C3744b c3744b, final C3727a c3727a) {
        if (m13838a(context, c3744b)) {
            c3744b.m13762a(m13831a(c3744b).m13791j());
            c3744b.m13792j(TtmlNode.ANONYMOUS_REGION_ID);
            c3744b.m13786g(TtmlNode.ANONYMOUS_REGION_ID);
            if (c3727a != null) {
                try {
                    if (context instanceof Activity) {
                        ((Activity) context).runOnUiThread(new Runnable(this) {
                            /* renamed from: c */
                            final /* synthetic */ C3751c f10012c;

                            public void run() {
                                this.f10012c.m13839b().add(c3744b);
                                c3727a.mo4194a();
                            }
                        });
                    } else {
                        c3727a.mo4194a();
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    /* renamed from: a */
    public void m13835a(String str) {
        m13832a().m13809b(str);
    }

    /* renamed from: a */
    public void m13836a(C3727a c3727a) {
        m13839b().clear();
        m13837a(false);
        m13835a("D81B60");
        if (c3727a != null) {
            c3727a.mo4194a();
        }
    }

    /* renamed from: a */
    public void m13837a(boolean z) {
        this.f10015c = z;
    }

    /* renamed from: a */
    public boolean m13838a(Context context, C3744b c3744b) {
        List<C3744b> arrayList = new ArrayList(m13839b());
        if (arrayList != null && arrayList.size() > 0) {
            Object obj;
            for (C3744b h : arrayList) {
                try {
                    if (h.m13787h().equals(c3744b.m13787h())) {
                        obj = 1;
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
            obj = null;
            if (obj != null) {
                return false;
            }
        }
        return true;
    }

    /* renamed from: b */
    public ArrayList<C3744b> m13839b() {
        if (this.f10014b == null) {
            this.f10014b = new ArrayList();
        }
        return this.f10014b;
    }

    /* renamed from: b */
    public void m13840b(int i) {
        m13832a().m13807a(i);
    }

    /* renamed from: c */
    public String m13841c() {
        return m13842d() == null ? "0" : m13842d().m13787h();
    }

    /* renamed from: d */
    public C3744b m13842d() {
        return m13843e() == 0 ? null : m13830a(m13843e() - 1);
    }

    /* renamed from: e */
    public int m13843e() {
        return m13839b().size();
    }

    /* renamed from: f */
    public boolean m13844f() {
        return this.f10015c;
    }
}
