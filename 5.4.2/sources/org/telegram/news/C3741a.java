package org.telegram.news;

import android.graphics.Bitmap;
import android.view.View;
import com.p077f.p078a.p086b.C1570c;
import com.p077f.p078a.p086b.C1575d;
import com.p077f.p078a.p086b.p093f.C1586a;
import com.p077f.p078a.p086b.p093f.C1588c;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* renamed from: org.telegram.news.a */
public class C3741a {
    /* renamed from: a */
    public static C3741a f9956a;
    /* renamed from: b */
    public C1575d f9957b = C1575d.m7807a();
    /* renamed from: c */
    public Map<String, C3729a> f9958c = new HashMap();

    /* renamed from: org.telegram.news.a$a */
    class C3729a {
        /* renamed from: a */
        String f9913a;
        /* renamed from: b */
        ArrayList<C1586a> f9914b = new ArrayList();
        /* renamed from: c */
        final /* synthetic */ C3741a f9915c;

        C3729a(C3741a c3741a, String str) {
            this.f9915c = c3741a;
            this.f9913a = str;
        }

        /* renamed from: a */
        void m13735a(C1586a c1586a) {
            this.f9914b.add(c1586a);
        }
    }

    /* renamed from: a */
    public static C3741a m13750a() {
        if (f9956a == null) {
            f9956a = new C3741a();
        }
        return f9956a;
    }

    /* renamed from: a */
    public void m13751a(String str, Bitmap bitmap) {
        ArrayList arrayList = ((C3729a) this.f9958c.remove(str)).f9914b;
        for (int i = 0; i < arrayList.size(); i++) {
            ((C1586a) arrayList.get(i)).onLoadingComplete(TtmlNode.ANONYMOUS_REGION_ID, null, bitmap);
        }
        arrayList.clear();
    }

    /* renamed from: a */
    public void m13752a(final String str, C1570c c1570c, C1586a c1586a) {
        C3729a c3729a = (C3729a) this.f9958c.get(str);
        if (c3729a == null) {
            c3729a = new C3729a(this, str);
            c3729a.m13735a(c1586a);
            this.f9958c.put(str, c3729a);
            this.f9957b.m7815a(str, c1570c, new C1588c(this) {
                /* renamed from: b */
                final /* synthetic */ C3741a f9912b;

                public void onLoadingComplete(String str, View view, Bitmap bitmap) {
                    this.f9912b.m13751a(str, bitmap);
                }
            });
            return;
        }
        c3729a.m13735a(c1586a);
    }
}
