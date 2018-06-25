package android.support.v7.widget;

import android.support.v7.widget.RecyclerView.C0910h;
import android.support.v7.widget.RecyclerView.C0952s;
import android.view.View;

class bb {
    /* renamed from: a */
    static int m5607a(C0952s c0952s, au auVar, View view, View view2, C0910h c0910h, boolean z) {
        if (c0910h.m4615w() == 0 || c0952s.m4959e() == 0 || view == null || view2 == null) {
            return 0;
        }
        if (!z) {
            return Math.abs(c0910h.m4573d(view) - c0910h.m4573d(view2)) + 1;
        }
        return Math.min(auVar.mo953f(), auVar.mo946b(view2) - auVar.mo944a(view));
    }

    /* renamed from: a */
    static int m5608a(C0952s c0952s, au auVar, View view, View view2, C0910h c0910h, boolean z, boolean z2) {
        if (c0910h.m4615w() == 0 || c0952s.m4959e() == 0 || view == null || view2 == null) {
            return 0;
        }
        int max = z2 ? Math.max(0, (c0952s.m4959e() - Math.max(c0910h.m4573d(view), c0910h.m4573d(view2))) - 1) : Math.max(0, Math.min(c0910h.m4573d(view), c0910h.m4573d(view2)));
        if (!z) {
            return max;
        }
        return Math.round((((float) max) * (((float) Math.abs(auVar.mo946b(view2) - auVar.mo944a(view))) / ((float) (Math.abs(c0910h.m4573d(view) - c0910h.m4573d(view2)) + 1)))) + ((float) (auVar.mo947c() - auVar.mo944a(view))));
    }

    /* renamed from: b */
    static int m5609b(C0952s c0952s, au auVar, View view, View view2, C0910h c0910h, boolean z) {
        if (c0910h.m4615w() == 0 || c0952s.m4959e() == 0 || view == null || view2 == null) {
            return 0;
        }
        if (!z) {
            return c0952s.m4959e();
        }
        return (int) ((((float) (auVar.mo946b(view2) - auVar.mo944a(view))) / ((float) (Math.abs(c0910h.m4573d(view) - c0910h.m4573d(view2)) + 1))) * ((float) c0952s.m4959e()));
    }
}
