package tellh.com.stickyheaderview_rv.p179a;

import android.support.v4.p022f.C0482l;

/* renamed from: tellh.com.stickyheaderview_rv.a.a */
public abstract class C5304a implements C5302c, C5303d {
    private C5305b viewBinder;

    public final C5305b provideViewBinder(C5306e c5306e, C0482l<? extends C5305b> c0482l, int i) {
        if (this.viewBinder == null) {
            this.viewBinder = (C5305b) c0482l.a(getItemLayoutId(c5306e));
        }
        return this.viewBinder;
    }

    public boolean shouldSticky() {
        return false;
    }
}
