package android.support.v7.view.menu;

import android.content.Context;
import android.support.v7.view.menu.C0873h.C0857b;
import android.support.v7.widget.bk;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public final class ExpandedMenuView extends ListView implements C0857b, C0079p, OnItemClickListener {
    /* renamed from: a */
    private static final int[] f2076a = new int[]{16842964, 16843049};
    /* renamed from: b */
    private C0873h f2077b;
    /* renamed from: c */
    private int f2078c;

    public ExpandedMenuView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842868);
    }

    public ExpandedMenuView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet);
        setOnItemClickListener(this);
        bk a = bk.m5654a(context, attributeSet, f2076a, i, 0);
        if (a.m5671g(0)) {
            setBackgroundDrawable(a.m5657a(0));
        }
        if (a.m5671g(1)) {
            setDivider(a.m5657a(1));
        }
        a.m5658a();
    }

    /* renamed from: a */
    public void mo54a(C0873h c0873h) {
        this.f2077b = c0873h;
    }

    /* renamed from: a */
    public boolean mo707a(C0876j c0876j) {
        return this.f2077b.m4232a((MenuItem) c0876j, 0);
    }

    public int getWindowAnimations() {
        return this.f2078c;
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setChildrenDrawingCacheEnabled(false);
    }

    public void onItemClick(AdapterView adapterView, View view, int i, long j) {
        mo707a((C0876j) getAdapter().getItem(i));
    }
}
