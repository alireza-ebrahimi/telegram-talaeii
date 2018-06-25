package android.support.v7.widget;

import android.content.Context;
import android.os.Build.VERSION;
import android.support.v4.view.ah;
import android.support.v4.view.ax;
import android.support.v7.p025a.C0748a.C0738a;
import android.support.v7.p025a.C0748a.C0743f;
import android.support.v7.p025a.C0748a.C0744g;
import android.support.v7.p025a.C0748a.C0747j;
import android.support.v7.view.C0814b;
import android.support.v7.view.menu.C0873h;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.accessibility.AccessibilityEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActionBarContextView extends C0893a {
    /* renamed from: g */
    private CharSequence f2294g;
    /* renamed from: h */
    private CharSequence f2295h;
    /* renamed from: i */
    private View f2296i;
    /* renamed from: j */
    private View f2297j;
    /* renamed from: k */
    private LinearLayout f2298k;
    /* renamed from: l */
    private TextView f2299l;
    /* renamed from: m */
    private TextView f2300m;
    /* renamed from: n */
    private int f2301n;
    /* renamed from: o */
    private int f2302o;
    /* renamed from: p */
    private boolean f2303p;
    /* renamed from: q */
    private int f2304q;

    public ActionBarContextView(Context context) {
        this(context, null);
    }

    public ActionBarContextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, C0738a.actionModeStyle);
    }

    public ActionBarContextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        bk a = bk.m5654a(context, attributeSet, C0747j.ActionMode, i, 0);
        ah.m2781a((View) this, a.m5657a(C0747j.ActionMode_background));
        this.f2301n = a.m5670g(C0747j.ActionMode_titleTextStyle, 0);
        this.f2302o = a.m5670g(C0747j.ActionMode_subtitleTextStyle, 0);
        this.e = a.m5668f(C0747j.ActionMode_height, 0);
        this.f2304q = a.m5670g(C0747j.ActionMode_closeItemLayout, C0744g.abc_action_mode_close_item_material);
        a.m5658a();
    }

    /* renamed from: e */
    private void m4364e() {
        int i = 8;
        Object obj = 1;
        if (this.f2298k == null) {
            LayoutInflater.from(getContext()).inflate(C0744g.abc_action_bar_title_item, this);
            this.f2298k = (LinearLayout) getChildAt(getChildCount() - 1);
            this.f2299l = (TextView) this.f2298k.findViewById(C0743f.action_bar_title);
            this.f2300m = (TextView) this.f2298k.findViewById(C0743f.action_bar_subtitle);
            if (this.f2301n != 0) {
                this.f2299l.setTextAppearance(getContext(), this.f2301n);
            }
            if (this.f2302o != 0) {
                this.f2300m.setTextAppearance(getContext(), this.f2302o);
            }
        }
        this.f2299l.setText(this.f2294g);
        this.f2300m.setText(this.f2295h);
        Object obj2 = !TextUtils.isEmpty(this.f2294g) ? 1 : null;
        if (TextUtils.isEmpty(this.f2295h)) {
            obj = null;
        }
        this.f2300m.setVisibility(obj != null ? 0 : 8);
        LinearLayout linearLayout = this.f2298k;
        if (!(obj2 == null && obj == null)) {
            i = 0;
        }
        linearLayout.setVisibility(i);
        if (this.f2298k.getParent() == null) {
            addView(this.f2298k);
        }
    }

    /* renamed from: a */
    public /* bridge */ /* synthetic */ ax mo765a(int i, long j) {
        return super.mo765a(i, j);
    }

    /* renamed from: a */
    public void m4366a(final C0814b c0814b) {
        if (this.f2296i == null) {
            this.f2296i = LayoutInflater.from(getContext()).inflate(this.f2304q, this, false);
            addView(this.f2296i);
        } else if (this.f2296i.getParent() == null) {
            addView(this.f2296i);
        }
        this.f2296i.findViewById(C0743f.action_mode_close_button).setOnClickListener(new OnClickListener(this) {
            /* renamed from: b */
            final /* synthetic */ ActionBarContextView f2285b;

            public void onClick(View view) {
                c0814b.mo687c();
            }
        });
        C0873h c0873h = (C0873h) c0814b.mo684b();
        if (this.d != null) {
            this.d.m5785f();
        }
        this.d = new C1052d(getContext());
        this.d.m5781c(true);
        LayoutParams layoutParams = new LayoutParams(-2, -1);
        c0873h.m4227a(this.d, this.b);
        this.c = (ActionMenuView) this.d.mo998a((ViewGroup) this);
        ah.m2781a(this.c, null);
        addView(this.c, layoutParams);
    }

    /* renamed from: a */
    public boolean mo766a() {
        return this.d != null ? this.d.m5783d() : false;
    }

    /* renamed from: b */
    public void m4368b() {
        if (this.f2296i == null) {
            m4369c();
        }
    }

    /* renamed from: c */
    public void m4369c() {
        removeAllViews();
        this.f2297j = null;
        this.c = null;
    }

    /* renamed from: d */
    public boolean m4370d() {
        return this.f2303p;
    }

    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(-1, -2);
    }

    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new MarginLayoutParams(getContext(), attributeSet);
    }

    public /* bridge */ /* synthetic */ int getAnimatedVisibility() {
        return super.getAnimatedVisibility();
    }

    public /* bridge */ /* synthetic */ int getContentHeight() {
        return super.getContentHeight();
    }

    public CharSequence getSubtitle() {
        return this.f2295h;
    }

    public CharSequence getTitle() {
        return this.f2294g;
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.d != null) {
            this.d.m5784e();
            this.d.m5786g();
        }
    }

    public /* bridge */ /* synthetic */ boolean onHoverEvent(MotionEvent motionEvent) {
        return super.onHoverEvent(motionEvent);
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if (VERSION.SDK_INT < 14) {
            return;
        }
        if (accessibilityEvent.getEventType() == 32) {
            accessibilityEvent.setSource(this);
            accessibilityEvent.setClassName(getClass().getName());
            accessibilityEvent.setPackageName(getContext().getPackageName());
            accessibilityEvent.setContentDescription(this.f2294g);
            return;
        }
        super.onInitializeAccessibilityEvent(accessibilityEvent);
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        boolean a = bp.m5747a(this);
        int paddingRight = a ? (i3 - i) - getPaddingRight() : getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingTop2 = ((i4 - i2) - getPaddingTop()) - getPaddingBottom();
        if (this.f2296i == null || this.f2296i.getVisibility() == 8) {
            i5 = paddingRight;
        } else {
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams) this.f2296i.getLayoutParams();
            i5 = a ? marginLayoutParams.rightMargin : marginLayoutParams.leftMargin;
            int i6 = a ? marginLayoutParams.leftMargin : marginLayoutParams.rightMargin;
            i5 = C0893a.m4357a(paddingRight, i5, a);
            i5 = C0893a.m4357a(m4361a(this.f2296i, i5, paddingTop, paddingTop2, a) + i5, i6, a);
        }
        if (!(this.f2298k == null || this.f2297j != null || this.f2298k.getVisibility() == 8)) {
            i5 += m4361a(this.f2298k, i5, paddingTop, paddingTop2, a);
        }
        if (this.f2297j != null) {
            int a2 = m4361a(this.f2297j, i5, paddingTop, paddingTop2, a) + i5;
        }
        i5 = a ? getPaddingLeft() : (i3 - i) - getPaddingRight();
        if (this.c != null) {
            a2 = m4361a(this.c, i5, paddingTop, paddingTop2, !a) + i5;
        }
    }

    protected void onMeasure(int i, int i2) {
        int i3 = 1073741824;
        int i4 = 0;
        if (MeasureSpec.getMode(i) != 1073741824) {
            throw new IllegalStateException(getClass().getSimpleName() + " can only be used " + "with android:layout_width=\"match_parent\" (or fill_parent)");
        } else if (MeasureSpec.getMode(i2) == 0) {
            throw new IllegalStateException(getClass().getSimpleName() + " can only be used " + "with android:layout_height=\"wrap_content\"");
        } else {
            int a;
            int size = MeasureSpec.getSize(i);
            int size2 = this.e > 0 ? this.e : MeasureSpec.getSize(i2);
            int paddingTop = getPaddingTop() + getPaddingBottom();
            int paddingLeft = (size - getPaddingLeft()) - getPaddingRight();
            int i5 = size2 - paddingTop;
            int makeMeasureSpec = MeasureSpec.makeMeasureSpec(i5, Integer.MIN_VALUE);
            if (this.f2296i != null) {
                a = m4360a(this.f2296i, paddingLeft, makeMeasureSpec, 0);
                MarginLayoutParams marginLayoutParams = (MarginLayoutParams) this.f2296i.getLayoutParams();
                paddingLeft = a - (marginLayoutParams.rightMargin + marginLayoutParams.leftMargin);
            }
            if (this.c != null && this.c.getParent() == this) {
                paddingLeft = m4360a(this.c, paddingLeft, makeMeasureSpec, 0);
            }
            if (this.f2298k != null && this.f2297j == null) {
                if (this.f2303p) {
                    this.f2298k.measure(MeasureSpec.makeMeasureSpec(0, 0), makeMeasureSpec);
                    a = this.f2298k.getMeasuredWidth();
                    makeMeasureSpec = a <= paddingLeft ? 1 : 0;
                    if (makeMeasureSpec != 0) {
                        paddingLeft -= a;
                    }
                    this.f2298k.setVisibility(makeMeasureSpec != 0 ? 0 : 8);
                } else {
                    paddingLeft = m4360a(this.f2298k, paddingLeft, makeMeasureSpec, 0);
                }
            }
            if (this.f2297j != null) {
                LayoutParams layoutParams = this.f2297j.getLayoutParams();
                makeMeasureSpec = layoutParams.width != -2 ? 1073741824 : Integer.MIN_VALUE;
                if (layoutParams.width >= 0) {
                    paddingLeft = Math.min(layoutParams.width, paddingLeft);
                }
                if (layoutParams.height == -2) {
                    i3 = Integer.MIN_VALUE;
                }
                this.f2297j.measure(MeasureSpec.makeMeasureSpec(paddingLeft, makeMeasureSpec), MeasureSpec.makeMeasureSpec(layoutParams.height >= 0 ? Math.min(layoutParams.height, i5) : i5, i3));
            }
            if (this.e <= 0) {
                makeMeasureSpec = getChildCount();
                size2 = 0;
                while (i4 < makeMeasureSpec) {
                    paddingLeft = getChildAt(i4).getMeasuredHeight() + paddingTop;
                    if (paddingLeft <= size2) {
                        paddingLeft = size2;
                    }
                    i4++;
                    size2 = paddingLeft;
                }
                setMeasuredDimension(size, size2);
                return;
            }
            setMeasuredDimension(size, size2);
        }
    }

    public /* bridge */ /* synthetic */ boolean onTouchEvent(MotionEvent motionEvent) {
        return super.onTouchEvent(motionEvent);
    }

    public void setContentHeight(int i) {
        this.e = i;
    }

    public void setCustomView(View view) {
        if (this.f2297j != null) {
            removeView(this.f2297j);
        }
        this.f2297j = view;
        if (!(view == null || this.f2298k == null)) {
            removeView(this.f2298k);
            this.f2298k = null;
        }
        if (view != null) {
            addView(view);
        }
        requestLayout();
    }

    public void setSubtitle(CharSequence charSequence) {
        this.f2295h = charSequence;
        m4364e();
    }

    public void setTitle(CharSequence charSequence) {
        this.f2294g = charSequence;
        m4364e();
    }

    public void setTitleOptional(boolean z) {
        if (z != this.f2303p) {
            requestLayout();
        }
        this.f2303p = z;
    }

    public /* bridge */ /* synthetic */ void setVisibility(int i) {
        super.setVisibility(i);
    }

    public boolean shouldDelayChildPressedState() {
        return false;
    }
}
