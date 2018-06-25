package android.support.v7.widget;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.support.v4.view.C0616d;
import android.support.v4.view.ah;
import android.support.v7.p025a.C0748a.C0743f;
import android.support.v7.p025a.C0748a.C0744g;
import android.support.v7.p025a.C0748a.C0745h;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

public class ActivityChooserView extends ViewGroup {
    /* renamed from: a */
    final C0903a f2366a;
    /* renamed from: b */
    final FrameLayout f2367b;
    /* renamed from: c */
    final FrameLayout f2368c;
    /* renamed from: d */
    C0616d f2369d;
    /* renamed from: e */
    final DataSetObserver f2370e;
    /* renamed from: f */
    OnDismissListener f2371f;
    /* renamed from: g */
    boolean f2372g;
    /* renamed from: h */
    int f2373h;
    /* renamed from: i */
    private final C0904b f2374i;
    /* renamed from: j */
    private final ao f2375j;
    /* renamed from: k */
    private final ImageView f2376k;
    /* renamed from: l */
    private final int f2377l;
    /* renamed from: m */
    private final OnGlobalLayoutListener f2378m;
    /* renamed from: n */
    private ap f2379n;
    /* renamed from: o */
    private boolean f2380o;
    /* renamed from: p */
    private int f2381p;

    public static class InnerLayout extends ao {
        /* renamed from: a */
        private static final int[] f2358a = new int[]{16842964};

        public InnerLayout(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            bk a = bk.m5653a(context, attributeSet, f2358a);
            setBackgroundDrawable(a.m5657a(0));
            a.m5658a();
        }
    }

    /* renamed from: android.support.v7.widget.ActivityChooserView$a */
    private class C0903a extends BaseAdapter {
        /* renamed from: a */
        final /* synthetic */ ActivityChooserView f2359a;
        /* renamed from: b */
        private C1058e f2360b;
        /* renamed from: c */
        private int f2361c;
        /* renamed from: d */
        private boolean f2362d;
        /* renamed from: e */
        private boolean f2363e;
        /* renamed from: f */
        private boolean f2364f;

        /* renamed from: a */
        public int m4427a() {
            int i = 0;
            int i2 = this.f2361c;
            this.f2361c = Integer.MAX_VALUE;
            int makeMeasureSpec = MeasureSpec.makeMeasureSpec(0, 0);
            int makeMeasureSpec2 = MeasureSpec.makeMeasureSpec(0, 0);
            int count = getCount();
            View view = null;
            int i3 = 0;
            while (i < count) {
                view = getView(i, view, null);
                view.measure(makeMeasureSpec, makeMeasureSpec2);
                i3 = Math.max(i3, view.getMeasuredWidth());
                i++;
            }
            this.f2361c = i2;
            return i3;
        }

        /* renamed from: a */
        public void m4428a(int i) {
            if (this.f2361c != i) {
                this.f2361c = i;
                notifyDataSetChanged();
            }
        }

        /* renamed from: a */
        public void m4429a(C1058e c1058e) {
            C1058e d = this.f2359a.f2366a.m4434d();
            if (d != null && this.f2359a.isShown()) {
                d.unregisterObserver(this.f2359a.f2370e);
            }
            this.f2360b = c1058e;
            if (c1058e != null && this.f2359a.isShown()) {
                c1058e.registerObserver(this.f2359a.f2370e);
            }
            notifyDataSetChanged();
        }

        /* renamed from: a */
        public void m4430a(boolean z) {
            if (this.f2364f != z) {
                this.f2364f = z;
                notifyDataSetChanged();
            }
        }

        /* renamed from: a */
        public void m4431a(boolean z, boolean z2) {
            if (this.f2362d != z || this.f2363e != z2) {
                this.f2362d = z;
                this.f2363e = z2;
                notifyDataSetChanged();
            }
        }

        /* renamed from: b */
        public ResolveInfo m4432b() {
            return this.f2360b.m5805b();
        }

        /* renamed from: c */
        public int m4433c() {
            return this.f2360b.m5801a();
        }

        /* renamed from: d */
        public C1058e m4434d() {
            return this.f2360b;
        }

        /* renamed from: e */
        public boolean m4435e() {
            return this.f2362d;
        }

        public int getCount() {
            int a = this.f2360b.m5801a();
            if (!(this.f2362d || this.f2360b.m5805b() == null)) {
                a--;
            }
            a = Math.min(a, this.f2361c);
            return this.f2364f ? a + 1 : a;
        }

        public Object getItem(int i) {
            switch (getItemViewType(i)) {
                case 0:
                    if (!(this.f2362d || this.f2360b.m5805b() == null)) {
                        i++;
                    }
                    return this.f2360b.m5803a(i);
                case 1:
                    return null;
                default:
                    throw new IllegalArgumentException();
            }
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public int getItemViewType(int i) {
            return (this.f2364f && i == getCount() - 1) ? 1 : 0;
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            switch (getItemViewType(i)) {
                case 0:
                    if (view == null || view.getId() != C0743f.list_item) {
                        view = LayoutInflater.from(this.f2359a.getContext()).inflate(C0744g.abc_activity_chooser_view_list_item, viewGroup, false);
                    }
                    PackageManager packageManager = this.f2359a.getContext().getPackageManager();
                    ResolveInfo resolveInfo = (ResolveInfo) getItem(i);
                    ((ImageView) view.findViewById(C0743f.icon)).setImageDrawable(resolveInfo.loadIcon(packageManager));
                    ((TextView) view.findViewById(C0743f.title)).setText(resolveInfo.loadLabel(packageManager));
                    if (this.f2362d && i == 0 && this.f2363e) {
                        ah.m2802c(view, true);
                        return view;
                    }
                    ah.m2802c(view, false);
                    return view;
                case 1:
                    if (view != null && view.getId() == 1) {
                        return view;
                    }
                    view = LayoutInflater.from(this.f2359a.getContext()).inflate(C0744g.abc_activity_chooser_view_list_item, viewGroup, false);
                    view.setId(1);
                    ((TextView) view.findViewById(C0743f.title)).setText(this.f2359a.getContext().getString(C0745h.abc_activity_chooser_view_see_all));
                    return view;
                default:
                    throw new IllegalArgumentException();
            }
        }

        public int getViewTypeCount() {
            return 3;
        }
    }

    /* renamed from: android.support.v7.widget.ActivityChooserView$b */
    private class C0904b implements OnClickListener, OnLongClickListener, OnItemClickListener, OnDismissListener {
        /* renamed from: a */
        final /* synthetic */ ActivityChooserView f2365a;

        /* renamed from: a */
        private void m4436a() {
            if (this.f2365a.f2371f != null) {
                this.f2365a.f2371f.onDismiss();
            }
        }

        public void onClick(View view) {
            if (view == this.f2365a.f2368c) {
                this.f2365a.m4439b();
                Intent b = this.f2365a.f2366a.m4434d().m5804b(this.f2365a.f2366a.m4434d().m5802a(this.f2365a.f2366a.m4432b()));
                if (b != null) {
                    b.addFlags(524288);
                    this.f2365a.getContext().startActivity(b);
                }
            } else if (view == this.f2365a.f2367b) {
                this.f2365a.f2372g = false;
                this.f2365a.m4437a(this.f2365a.f2373h);
            } else {
                throw new IllegalArgumentException();
            }
        }

        public void onDismiss() {
            m4436a();
            if (this.f2365a.f2369d != null) {
                this.f2365a.f2369d.m3098a(false);
            }
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            switch (((C0903a) adapterView.getAdapter()).getItemViewType(i)) {
                case 0:
                    this.f2365a.m4439b();
                    if (!this.f2365a.f2372g) {
                        if (!this.f2365a.f2366a.m4435e()) {
                            i++;
                        }
                        Intent b = this.f2365a.f2366a.m4434d().m5804b(i);
                        if (b != null) {
                            b.addFlags(524288);
                            this.f2365a.getContext().startActivity(b);
                            return;
                        }
                        return;
                    } else if (i > 0) {
                        this.f2365a.f2366a.m4434d().m5806c(i);
                        return;
                    } else {
                        return;
                    }
                case 1:
                    this.f2365a.m4437a(Integer.MAX_VALUE);
                    return;
                default:
                    throw new IllegalArgumentException();
            }
        }

        public boolean onLongClick(View view) {
            if (view == this.f2365a.f2368c) {
                if (this.f2365a.f2366a.getCount() > 0) {
                    this.f2365a.f2372g = true;
                    this.f2365a.m4437a(this.f2365a.f2373h);
                }
                return true;
            }
            throw new IllegalArgumentException();
        }
    }

    /* renamed from: a */
    void m4437a(int i) {
        if (this.f2366a.m4434d() == null) {
            throw new IllegalStateException("No data model. Did you call #setDataModel?");
        }
        getViewTreeObserver().addOnGlobalLayoutListener(this.f2378m);
        boolean z = this.f2368c.getVisibility() == 0;
        int c = this.f2366a.m4433c();
        int i2 = z ? 1 : 0;
        if (i == Integer.MAX_VALUE || c <= i2 + i) {
            this.f2366a.m4430a(false);
            this.f2366a.m4428a(i);
        } else {
            this.f2366a.m4430a(true);
            this.f2366a.m4428a(i - 1);
        }
        ap listPopupWindow = getListPopupWindow();
        if (!listPopupWindow.mo739d()) {
            if (this.f2372g || !z) {
                this.f2366a.m4431a(true, z);
            } else {
                this.f2366a.m4431a(false, false);
            }
            listPopupWindow.m5488g(Math.min(this.f2366a.m4427a(), this.f2377l));
            listPopupWindow.mo729a();
            if (this.f2369d != null) {
                this.f2369d.m3098a(true);
            }
            listPopupWindow.mo740e().setContentDescription(getContext().getString(C0745h.abc_activitychooserview_choose_application));
        }
    }

    /* renamed from: a */
    public boolean m4438a() {
        if (m4440c() || !this.f2380o) {
            return false;
        }
        this.f2372g = false;
        m4437a(this.f2373h);
        return true;
    }

    /* renamed from: b */
    public boolean m4439b() {
        if (m4440c()) {
            getListPopupWindow().mo736c();
            ViewTreeObserver viewTreeObserver = getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.removeGlobalOnLayoutListener(this.f2378m);
            }
        }
        return true;
    }

    /* renamed from: c */
    public boolean m4440c() {
        return getListPopupWindow().mo739d();
    }

    public C1058e getDataModel() {
        return this.f2366a.m4434d();
    }

    ap getListPopupWindow() {
        if (this.f2379n == null) {
            this.f2379n = new ap(getContext());
            this.f2379n.mo1012a(this.f2366a);
            this.f2379n.m5480b((View) this);
            this.f2379n.m5478a(true);
            this.f2379n.m5475a(this.f2374i);
            this.f2379n.m5477a(this.f2374i);
        }
        return this.f2379n;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        C1058e d = this.f2366a.m4434d();
        if (d != null) {
            d.registerObserver(this.f2370e);
        }
        this.f2380o = true;
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        C1058e d = this.f2366a.m4434d();
        if (d != null) {
            d.unregisterObserver(this.f2370e);
        }
        ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.removeGlobalOnLayoutListener(this.f2378m);
        }
        if (m4440c()) {
            m4439b();
        }
        this.f2380o = false;
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        this.f2375j.layout(0, 0, i3 - i, i4 - i2);
        if (!m4440c()) {
            m4439b();
        }
    }

    protected void onMeasure(int i, int i2) {
        View view = this.f2375j;
        if (this.f2368c.getVisibility() != 0) {
            i2 = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(i2), 1073741824);
        }
        measureChild(view, i, i2);
        setMeasuredDimension(view.getMeasuredWidth(), view.getMeasuredHeight());
    }

    public void setActivityChooserModel(C1058e c1058e) {
        this.f2366a.m4429a(c1058e);
        if (m4440c()) {
            m4439b();
            m4438a();
        }
    }

    public void setDefaultActionButtonContentDescription(int i) {
        this.f2381p = i;
    }

    public void setExpandActivityOverflowButtonContentDescription(int i) {
        this.f2376k.setContentDescription(getContext().getString(i));
    }

    public void setExpandActivityOverflowButtonDrawable(Drawable drawable) {
        this.f2376k.setImageDrawable(drawable);
    }

    public void setInitialActivityCount(int i) {
        this.f2373h = i;
    }

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.f2371f = onDismissListener;
    }

    public void setProvider(C0616d c0616d) {
        this.f2369d = c0616d;
    }
}
