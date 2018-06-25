package org.telegram.customization.util.view.p158c;

import android.support.v4.view.aa;
import android.view.View;
import android.view.ViewGroup;

/* renamed from: org.telegram.customization.util.view.c.b */
public abstract class C2650b extends aa {
    /* renamed from: a */
    private final C2947a f8844a;

    public C2650b() {
        this(new C2947a());
    }

    C2650b(C2947a c2947a) {
        this.f8844a = c2947a;
        c2947a.m13611a(m12495b());
    }

    /* renamed from: a */
    public int m12493a(int i) {
        return 0;
    }

    /* renamed from: a */
    public abstract View mo3459a(int i, View view, ViewGroup viewGroup);

    /* renamed from: b */
    public int m12495b() {
        return 1;
    }

    public final void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        View view = (View) obj;
        viewGroup.removeView(view);
        int a = m12493a(i);
        if (a != -1) {
            this.f8844a.m13612a(view, i, a);
        }
    }

    public final Object instantiateItem(ViewGroup viewGroup, int i) {
        int a = m12493a(i);
        View view = null;
        if (a != -1) {
            view = this.f8844a.m13609a(i, a);
        }
        view = mo3459a(i, view, viewGroup);
        viewGroup.addView(view);
        return view;
    }

    public final boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    public void notifyDataSetChanged() {
        this.f8844a.m13610a();
        super.notifyDataSetChanged();
    }
}
