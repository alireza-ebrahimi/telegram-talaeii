package org.telegram.customization.p153c;

import android.app.Activity;
import android.content.Context;
import java.util.ArrayList;
import java.util.List;
import org.telegram.customization.dynamicadapter.data.SlsBaseMessage;
import org.telegram.customization.dynamicadapter.data.SlsTag;

/* renamed from: org.telegram.customization.c.b */
public class C2668b {
    /* renamed from: a */
    SlsTag f8898a;
    /* renamed from: b */
    private List<SlsBaseMessage> f8899b;
    /* renamed from: c */
    private boolean f8900c = false;
    /* renamed from: d */
    private String f8901d;
    /* renamed from: e */
    private int f8902e;
    /* renamed from: f */
    private long f8903f;
    /* renamed from: g */
    private long f8904g;
    /* renamed from: h */
    private boolean f8905h = false;

    /* renamed from: a */
    public SlsBaseMessage m12512a(int i) {
        return i >= m12528e() ? null : (SlsBaseMessage) m12520b().get(i);
    }

    /* renamed from: a */
    public SlsTag m12513a() {
        if (this.f8898a == null) {
            this.f8898a = new SlsTag();
            this.f8898a.setId(0);
            this.f8898a.setTitle(TtmlNode.ANONYMOUS_REGION_ID);
        }
        return this.f8898a;
    }

    /* renamed from: a */
    public void m12514a(long j) {
        this.f8903f = j;
    }

    /* renamed from: a */
    public synchronized void m12515a(Context context, final SlsBaseMessage slsBaseMessage, final C2516a c2516a, boolean z) {
        if (m12519a(context, slsBaseMessage) && c2516a != null) {
            if (context != null) {
                try {
                    if (context instanceof Activity) {
                        ((Activity) context).runOnUiThread(new Runnable(this) {
                            /* renamed from: c */
                            final /* synthetic */ C2668b f8897c;

                            public void run() {
                                this.f8897c.m12520b().add(slsBaseMessage);
                                c2516a.mo3423a();
                            }
                        });
                    }
                } catch (Exception e) {
                }
            }
            m12520b().add(slsBaseMessage);
            c2516a.mo3423a();
        }
    }

    /* renamed from: a */
    public void m12516a(String str) {
        m12513a().setColor(str);
    }

    /* renamed from: a */
    public void m12517a(C2516a c2516a) {
        m12520b().clear();
        m12518a(false);
        m12516a("D81B60");
        if (c2516a != null) {
            c2516a.mo3423a();
        }
    }

    /* renamed from: a */
    public void m12518a(boolean z) {
        this.f8900c = z;
    }

    /* renamed from: a */
    public boolean m12519a(Context context, SlsBaseMessage slsBaseMessage) {
        List<SlsBaseMessage> arrayList = new ArrayList(m12520b());
        if (arrayList != null && arrayList.size() > 0) {
            Object obj;
            for (SlsBaseMessage row : arrayList) {
                try {
                    if (row.getRow() == slsBaseMessage.getRow()) {
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
    public List<SlsBaseMessage> m12520b() {
        if (this.f8899b == null) {
            this.f8899b = new ArrayList();
        }
        return this.f8899b;
    }

    /* renamed from: b */
    public void m12521b(int i) {
        m12513a().setId((long) i);
    }

    /* renamed from: b */
    public void m12522b(long j) {
        this.f8904g = j;
    }

    /* renamed from: b */
    public void m12523b(String str) {
        this.f8901d = str;
    }

    /* renamed from: b */
    public void m12524b(boolean z) {
        this.f8905h = z;
    }

    /* renamed from: c */
    public long m12525c() {
        return m12527d() == null ? 0 : (long) m12527d().getRow();
    }

    /* renamed from: c */
    public void m12526c(int i) {
        this.f8902e = i;
    }

    /* renamed from: d */
    public SlsBaseMessage m12527d() {
        return m12528e() == 0 ? null : m12512a(m12528e() - 1);
    }

    /* renamed from: e */
    public int m12528e() {
        return m12520b().size();
    }

    /* renamed from: f */
    public boolean m12529f() {
        return this.f8900c;
    }

    /* renamed from: g */
    public String m12530g() {
        return this.f8901d;
    }

    /* renamed from: h */
    public int m12531h() {
        return this.f8902e;
    }

    /* renamed from: i */
    public long m12532i() {
        return this.f8903f;
    }

    /* renamed from: j */
    public long m12533j() {
        return this.f8904g;
    }

    /* renamed from: k */
    public boolean m12534k() {
        return this.f8905h;
    }
}
