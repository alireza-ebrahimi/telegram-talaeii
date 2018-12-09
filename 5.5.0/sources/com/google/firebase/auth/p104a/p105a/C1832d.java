package com.google.firebase.auth.p104a.p105a;

import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.Map;

@VisibleForTesting
/* renamed from: com.google.firebase.auth.a.a.d */
final class C1832d implements C1831g {
    /* renamed from: a */
    private final int f5469a;
    /* renamed from: b */
    private final int f5470b;
    /* renamed from: c */
    private final Map<String, Integer> f5471c;
    /* renamed from: d */
    private final boolean f5472d = true;

    public C1832d(int i, int i2, Map<String, Integer> map, boolean z) {
        this.f5469a = i;
        this.f5470b = i2;
        this.f5471c = (Map) Preconditions.checkNotNull(map);
    }

    /* renamed from: a */
    public final boolean mo3013a(C1834f c1834f) {
        if (!this.f5472d) {
            return true;
        }
        if (this.f5470b <= this.f5469a) {
            return false;
        }
        Integer num = (Integer) this.f5471c.get(c1834f.mo3017a());
        return num == null ? false : num.intValue() > this.f5469a && this.f5470b >= num.intValue();
    }
}
