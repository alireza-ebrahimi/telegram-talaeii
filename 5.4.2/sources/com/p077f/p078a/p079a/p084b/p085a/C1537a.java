package com.p077f.p078a.p079a.p084b.p085a;

import android.graphics.Bitmap;
import com.p077f.p078a.p079a.p084b.C1536a;
import java.util.Collection;
import java.util.Comparator;

/* renamed from: com.f.a.a.b.a.a */
public class C1537a implements C1536a {
    /* renamed from: a */
    private final C1536a f4667a;
    /* renamed from: b */
    private final Comparator<String> f4668b;

    public C1537a(C1536a c1536a, Comparator<String> comparator) {
        this.f4667a = c1536a;
        this.f4668b = comparator;
    }

    /* renamed from: a */
    public Bitmap mo1217a(String str) {
        return this.f4667a.mo1217a(str);
    }

    /* renamed from: a */
    public Collection<String> mo1218a() {
        return this.f4667a.mo1218a();
    }

    /* renamed from: a */
    public boolean mo1219a(String str, Bitmap bitmap) {
        synchronized (this.f4667a) {
            for (String str2 : this.f4667a.mo1218a()) {
                if (this.f4668b.compare(str, str2) == 0) {
                    break;
                }
            }
            String str22 = null;
            if (str22 != null) {
                this.f4667a.mo1220b(str22);
            }
        }
        return this.f4667a.mo1219a(str, bitmap);
    }

    /* renamed from: b */
    public Bitmap mo1220b(String str) {
        return this.f4667a.mo1220b(str);
    }
}
