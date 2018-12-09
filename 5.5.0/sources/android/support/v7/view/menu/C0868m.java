package android.support.v7.view.menu;

import android.content.Context;
import android.graphics.Rect;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.PopupWindow.OnDismissListener;

/* renamed from: android.support.v7.view.menu.m */
abstract class C0868m implements C0859o, C0867s, OnItemClickListener {
    /* renamed from: a */
    private Rect f2131a;

    C0868m() {
    }

    /* renamed from: a */
    protected static int m4149a(ListAdapter listAdapter, ViewGroup viewGroup, Context context, int i) {
        int makeMeasureSpec = MeasureSpec.makeMeasureSpec(0, 0);
        int makeMeasureSpec2 = MeasureSpec.makeMeasureSpec(0, 0);
        int count = listAdapter.getCount();
        int i2 = 0;
        int i3 = 0;
        View view = null;
        int i4 = 0;
        ViewGroup viewGroup2 = viewGroup;
        while (i2 < count) {
            int itemViewType = listAdapter.getItemViewType(i2);
            if (itemViewType != i3) {
                i3 = itemViewType;
                view = null;
            }
            ViewGroup frameLayout = viewGroup2 == null ? new FrameLayout(context) : viewGroup2;
            view = listAdapter.getView(i2, view, frameLayout);
            view.measure(makeMeasureSpec, makeMeasureSpec2);
            int measuredWidth = view.getMeasuredWidth();
            if (measuredWidth >= i) {
                return i;
            }
            if (measuredWidth <= i4) {
                measuredWidth = i4;
            }
            i2++;
            i4 = measuredWidth;
            viewGroup2 = frameLayout;
        }
        return i4;
    }

    /* renamed from: a */
    protected static C0872g m4150a(ListAdapter listAdapter) {
        return listAdapter instanceof HeaderViewListAdapter ? (C0872g) ((HeaderViewListAdapter) listAdapter).getWrappedAdapter() : (C0872g) listAdapter;
    }

    /* renamed from: b */
    protected static boolean m4151b(C0873h c0873h) {
        int size = c0873h.size();
        for (int i = 0; i < size; i++) {
            MenuItem item = c0873h.getItem(i);
            if (item.isVisible() && item.getIcon() != null) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: a */
    public abstract void mo730a(int i);

    /* renamed from: a */
    public void mo719a(Context context, C0873h c0873h) {
    }

    /* renamed from: a */
    public void m4154a(Rect rect) {
        this.f2131a = rect;
    }

    /* renamed from: a */
    public abstract void mo731a(C0873h c0873h);

    /* renamed from: a */
    public abstract void mo732a(View view);

    /* renamed from: a */
    public abstract void mo733a(OnDismissListener onDismissListener);

    /* renamed from: a */
    public abstract void mo734a(boolean z);

    /* renamed from: a */
    public boolean mo722a(C0873h c0873h, C0876j c0876j) {
        return false;
    }

    /* renamed from: b */
    public abstract void mo735b(int i);

    /* renamed from: b */
    public boolean mo726b(C0873h c0873h, C0876j c0876j) {
        return false;
    }

    /* renamed from: c */
    public abstract void mo737c(int i);

    /* renamed from: c */
    public abstract void mo738c(boolean z);

    /* renamed from: f */
    protected boolean mo741f() {
        return true;
    }

    /* renamed from: g */
    public Rect m4165g() {
        return this.f2131a;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        ListAdapter listAdapter = (ListAdapter) adapterView.getAdapter();
        C0868m.m4150a(listAdapter).f2169b.m4233a((MenuItem) listAdapter.getItem(i), (C0859o) this, mo741f() ? 0 : 4);
    }
}
