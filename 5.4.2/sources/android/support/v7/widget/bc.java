package android.support.v7.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v7.app.C0765a.C0764c;
import android.support.v7.p025a.C0748a.C0738a;
import android.support.v7.view.C0842a;
import android.support.v7.widget.ao.C0899a;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class bc extends HorizontalScrollView implements OnItemSelectedListener {
    /* renamed from: j */
    private static final Interpolator f2995j = new DecelerateInterpolator();
    /* renamed from: a */
    Runnable f2996a;
    /* renamed from: b */
    ao f2997b;
    /* renamed from: c */
    int f2998c;
    /* renamed from: d */
    int f2999d;
    /* renamed from: e */
    private C1037b f3000e;
    /* renamed from: f */
    private Spinner f3001f;
    /* renamed from: g */
    private boolean f3002g;
    /* renamed from: h */
    private int f3003h;
    /* renamed from: i */
    private int f3004i;

    /* renamed from: android.support.v7.widget.bc$a */
    private class C1036a extends BaseAdapter {
        /* renamed from: a */
        final /* synthetic */ bc f2987a;

        C1036a(bc bcVar) {
            this.f2987a = bcVar;
        }

        public int getCount() {
            return this.f2987a.f2997b.getChildCount();
        }

        public Object getItem(int i) {
            return ((C1038c) this.f2987a.f2997b.getChildAt(i)).m5612b();
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                return this.f2987a.m5617a((C0764c) getItem(i), true);
            }
            ((C1038c) view).m5611a((C0764c) getItem(i));
            return view;
        }
    }

    /* renamed from: android.support.v7.widget.bc$b */
    private class C1037b implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ bc f2988a;

        C1037b(bc bcVar) {
            this.f2988a = bcVar;
        }

        public void onClick(View view) {
            ((C1038c) view).m5612b().m3609d();
            int childCount = this.f2988a.f2997b.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = this.f2988a.f2997b.getChildAt(i);
                childAt.setSelected(childAt == view);
            }
        }
    }

    /* renamed from: android.support.v7.widget.bc$c */
    private class C1038c extends ao implements OnLongClickListener {
        /* renamed from: a */
        final /* synthetic */ bc f2989a;
        /* renamed from: b */
        private final int[] f2990b = new int[]{16842964};
        /* renamed from: c */
        private C0764c f2991c;
        /* renamed from: d */
        private TextView f2992d;
        /* renamed from: e */
        private ImageView f2993e;
        /* renamed from: f */
        private View f2994f;

        public C1038c(bc bcVar, Context context, C0764c c0764c, boolean z) {
            this.f2989a = bcVar;
            super(context, null, C0738a.actionBarTabStyle);
            this.f2991c = c0764c;
            bk a = bk.m5654a(context, null, this.f2990b, C0738a.actionBarTabStyle, 0);
            if (a.m5671g(0)) {
                setBackgroundDrawable(a.m5657a(0));
            }
            a.m5658a();
            if (z) {
                setGravity(8388627);
            }
            m5610a();
        }

        /* renamed from: a */
        public void m5610a() {
            C0764c c0764c = this.f2991c;
            View c = c0764c.m3608c();
            if (c != null) {
                C1038c parent = c.getParent();
                if (parent != this) {
                    if (parent != null) {
                        parent.removeView(c);
                    }
                    addView(c);
                }
                this.f2994f = c;
                if (this.f2992d != null) {
                    this.f2992d.setVisibility(8);
                }
                if (this.f2993e != null) {
                    this.f2993e.setVisibility(8);
                    this.f2993e.setImageDrawable(null);
                    return;
                }
                return;
            }
            if (this.f2994f != null) {
                removeView(this.f2994f);
                this.f2994f = null;
            }
            Drawable a = c0764c.m3606a();
            CharSequence b = c0764c.m3607b();
            if (a != null) {
                if (this.f2993e == null) {
                    View appCompatImageView = new AppCompatImageView(getContext());
                    LayoutParams c0899a = new C0899a(-2, -2);
                    c0899a.f2338h = 16;
                    appCompatImageView.setLayoutParams(c0899a);
                    addView(appCompatImageView, 0);
                    this.f2993e = appCompatImageView;
                }
                this.f2993e.setImageDrawable(a);
                this.f2993e.setVisibility(0);
            } else if (this.f2993e != null) {
                this.f2993e.setVisibility(8);
                this.f2993e.setImageDrawable(null);
            }
            boolean z = !TextUtils.isEmpty(b);
            if (z) {
                if (this.f2992d == null) {
                    appCompatImageView = new C0855y(getContext(), null, C0738a.actionBarTabTextStyle);
                    appCompatImageView.setEllipsize(TruncateAt.END);
                    c0899a = new C0899a(-2, -2);
                    c0899a.f2338h = 16;
                    appCompatImageView.setLayoutParams(c0899a);
                    addView(appCompatImageView);
                    this.f2992d = appCompatImageView;
                }
                this.f2992d.setText(b);
                this.f2992d.setVisibility(0);
            } else if (this.f2992d != null) {
                this.f2992d.setVisibility(8);
                this.f2992d.setText(null);
            }
            if (this.f2993e != null) {
                this.f2993e.setContentDescription(c0764c.m3610e());
            }
            if (z || TextUtils.isEmpty(c0764c.m3610e())) {
                setOnLongClickListener(null);
                setLongClickable(false);
                return;
            }
            setOnLongClickListener(this);
        }

        /* renamed from: a */
        public void m5611a(C0764c c0764c) {
            this.f2991c = c0764c;
            m5610a();
        }

        /* renamed from: b */
        public C0764c m5612b() {
            return this.f2991c;
        }

        public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(accessibilityEvent);
            accessibilityEvent.setClassName(C0764c.class.getName());
        }

        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
            if (VERSION.SDK_INT >= 14) {
                accessibilityNodeInfo.setClassName(C0764c.class.getName());
            }
        }

        public boolean onLongClick(View view) {
            int[] iArr = new int[2];
            getLocationOnScreen(iArr);
            Context context = getContext();
            int width = getWidth();
            int height = getHeight();
            int i = context.getResources().getDisplayMetrics().widthPixels;
            Toast makeText = Toast.makeText(context, this.f2991c.m3610e(), 0);
            makeText.setGravity(49, (iArr[0] + (width / 2)) - (i / 2), height);
            makeText.show();
            return true;
        }

        public void onMeasure(int i, int i2) {
            super.onMeasure(i, i2);
            if (this.f2989a.f2998c > 0 && getMeasuredWidth() > this.f2989a.f2998c) {
                super.onMeasure(MeasureSpec.makeMeasureSpec(this.f2989a.f2998c, 1073741824), i2);
            }
        }

        public void setSelected(boolean z) {
            Object obj = isSelected() != z ? 1 : null;
            super.setSelected(z);
            if (obj != null && z) {
                sendAccessibilityEvent(4);
            }
        }
    }

    /* renamed from: a */
    private boolean m5613a() {
        return this.f3001f != null && this.f3001f.getParent() == this;
    }

    /* renamed from: b */
    private void m5614b() {
        if (!m5613a()) {
            if (this.f3001f == null) {
                this.f3001f = m5616d();
            }
            removeView(this.f2997b);
            addView(this.f3001f, new LayoutParams(-2, -1));
            if (this.f3001f.getAdapter() == null) {
                this.f3001f.setAdapter(new C1036a(this));
            }
            if (this.f2996a != null) {
                removeCallbacks(this.f2996a);
                this.f2996a = null;
            }
            this.f3001f.setSelection(this.f3004i);
        }
    }

    /* renamed from: c */
    private boolean m5615c() {
        if (m5613a()) {
            removeView(this.f3001f);
            addView(this.f2997b, new LayoutParams(-2, -1));
            setTabSelected(this.f3001f.getSelectedItemPosition());
        }
        return false;
    }

    /* renamed from: d */
    private Spinner m5616d() {
        Spinner c1085v = new C1085v(getContext(), null, C0738a.actionDropDownStyle);
        c1085v.setLayoutParams(new C0899a(-2, -1));
        c1085v.setOnItemSelectedListener(this);
        return c1085v;
    }

    /* renamed from: a */
    C1038c m5617a(C0764c c0764c, boolean z) {
        C1038c c1038c = new C1038c(this, getContext(), c0764c, z);
        if (z) {
            c1038c.setBackgroundDrawable(null);
            c1038c.setLayoutParams(new AbsListView.LayoutParams(-1, this.f3003h));
        } else {
            c1038c.setFocusable(true);
            if (this.f3000e == null) {
                this.f3000e = new C1037b(this);
            }
            c1038c.setOnClickListener(this.f3000e);
        }
        return c1038c;
    }

    /* renamed from: a */
    public void m5618a(int i) {
        final View childAt = this.f2997b.getChildAt(i);
        if (this.f2996a != null) {
            removeCallbacks(this.f2996a);
        }
        this.f2996a = new Runnable(this) {
            /* renamed from: b */
            final /* synthetic */ bc f2986b;

            public void run() {
                this.f2986b.smoothScrollTo(childAt.getLeft() - ((this.f2986b.getWidth() - childAt.getWidth()) / 2), 0);
                this.f2986b.f2996a = null;
            }
        };
        post(this.f2996a);
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.f2996a != null) {
            post(this.f2996a);
        }
    }

    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        C0842a a = C0842a.m4013a(getContext());
        setContentHeight(a.m4018e());
        this.f2999d = a.m4020g();
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.f2996a != null) {
            removeCallbacks(this.f2996a);
        }
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
        ((C1038c) view).m5612b().m3609d();
    }

    public void onMeasure(int i, int i2) {
        int i3 = 1;
        int mode = MeasureSpec.getMode(i);
        boolean z = mode == 1073741824;
        setFillViewport(z);
        int childCount = this.f2997b.getChildCount();
        if (childCount <= 1 || !(mode == 1073741824 || mode == Integer.MIN_VALUE)) {
            this.f2998c = -1;
        } else {
            if (childCount > 2) {
                this.f2998c = (int) (((float) MeasureSpec.getSize(i)) * 0.4f);
            } else {
                this.f2998c = MeasureSpec.getSize(i) / 2;
            }
            this.f2998c = Math.min(this.f2998c, this.f2999d);
        }
        mode = MeasureSpec.makeMeasureSpec(this.f3003h, 1073741824);
        if (z || !this.f3002g) {
            i3 = 0;
        }
        if (i3 != 0) {
            this.f2997b.measure(0, mode);
            if (this.f2997b.getMeasuredWidth() > MeasureSpec.getSize(i)) {
                m5614b();
            } else {
                m5615c();
            }
        } else {
            m5615c();
        }
        i3 = getMeasuredWidth();
        super.onMeasure(i, mode);
        int measuredWidth = getMeasuredWidth();
        if (z && i3 != measuredWidth) {
            setTabSelected(this.f3004i);
        }
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void setAllowCollapse(boolean z) {
        this.f3002g = z;
    }

    public void setContentHeight(int i) {
        this.f3003h = i;
        requestLayout();
    }

    public void setTabSelected(int i) {
        this.f3004i = i;
        int childCount = this.f2997b.getChildCount();
        int i2 = 0;
        while (i2 < childCount) {
            View childAt = this.f2997b.getChildAt(i2);
            boolean z = i2 == i;
            childAt.setSelected(z);
            if (z) {
                m5618a(i);
            }
            i2++;
        }
        if (this.f3001f != null && i >= 0) {
            this.f3001f.setSelection(i);
        }
    }
}
