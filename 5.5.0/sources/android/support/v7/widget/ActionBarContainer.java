package android.support.v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.view.ah;
import android.support.v7.p025a.C0748a.C0743f;
import android.support.v7.p025a.C0748a.C0747j;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

public class ActionBarContainer extends FrameLayout {
    /* renamed from: a */
    Drawable f2274a;
    /* renamed from: b */
    Drawable f2275b;
    /* renamed from: c */
    Drawable f2276c;
    /* renamed from: d */
    boolean f2277d;
    /* renamed from: e */
    boolean f2278e;
    /* renamed from: f */
    private boolean f2279f;
    /* renamed from: g */
    private View f2280g;
    /* renamed from: h */
    private View f2281h;
    /* renamed from: i */
    private View f2282i;
    /* renamed from: j */
    private int f2283j;

    public ActionBarContainer(Context context) {
        this(context, null);
    }

    public ActionBarContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        ah.m2781a((View) this, VERSION.SDK_INT >= 21 ? new C1044c(this) : new C1034b(this));
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C0747j.ActionBar);
        this.f2274a = obtainStyledAttributes.getDrawable(C0747j.ActionBar_background);
        this.f2275b = obtainStyledAttributes.getDrawable(C0747j.ActionBar_backgroundStacked);
        this.f2283j = obtainStyledAttributes.getDimensionPixelSize(C0747j.ActionBar_height, -1);
        if (getId() == C0743f.split_action_bar) {
            this.f2277d = true;
            this.f2276c = obtainStyledAttributes.getDrawable(C0747j.ActionBar_backgroundSplit);
        }
        obtainStyledAttributes.recycle();
        boolean z = this.f2277d ? this.f2276c == null : this.f2274a == null && this.f2275b == null;
        setWillNotDraw(z);
    }

    /* renamed from: a */
    private boolean m4355a(View view) {
        return view == null || view.getVisibility() == 8 || view.getMeasuredHeight() == 0;
    }

    /* renamed from: b */
    private int m4356b(View view) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        return layoutParams.bottomMargin + (view.getMeasuredHeight() + layoutParams.topMargin);
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.f2274a != null && this.f2274a.isStateful()) {
            this.f2274a.setState(getDrawableState());
        }
        if (this.f2275b != null && this.f2275b.isStateful()) {
            this.f2275b.setState(getDrawableState());
        }
        if (this.f2276c != null && this.f2276c.isStateful()) {
            this.f2276c.setState(getDrawableState());
        }
    }

    public View getTabContainer() {
        return this.f2280g;
    }

    public void jumpDrawablesToCurrentState() {
        if (VERSION.SDK_INT >= 11) {
            super.jumpDrawablesToCurrentState();
            if (this.f2274a != null) {
                this.f2274a.jumpToCurrentState();
            }
            if (this.f2275b != null) {
                this.f2275b.jumpToCurrentState();
            }
            if (this.f2276c != null) {
                this.f2276c.jumpToCurrentState();
            }
        }
    }

    public void onFinishInflate() {
        super.onFinishInflate();
        this.f2281h = findViewById(C0743f.action_bar);
        this.f2282i = findViewById(C0743f.action_context_bar);
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return this.f2279f || super.onInterceptTouchEvent(motionEvent);
    }

    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5 = 1;
        super.onLayout(z, i, i2, i3, i4);
        View view = this.f2280g;
        boolean z2 = (view == null || view.getVisibility() == 8) ? false : true;
        if (!(view == null || view.getVisibility() == 8)) {
            int measuredHeight = getMeasuredHeight();
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            view.layout(i, (measuredHeight - view.getMeasuredHeight()) - layoutParams.bottomMargin, i3, measuredHeight - layoutParams.bottomMargin);
        }
        if (!this.f2277d) {
            int i6;
            if (this.f2274a != null) {
                if (this.f2281h.getVisibility() == 0) {
                    this.f2274a.setBounds(this.f2281h.getLeft(), this.f2281h.getTop(), this.f2281h.getRight(), this.f2281h.getBottom());
                } else if (this.f2282i == null || this.f2282i.getVisibility() != 0) {
                    this.f2274a.setBounds(0, 0, 0, 0);
                } else {
                    this.f2274a.setBounds(this.f2282i.getLeft(), this.f2282i.getTop(), this.f2282i.getRight(), this.f2282i.getBottom());
                }
                i6 = 1;
            } else {
                i6 = 0;
            }
            this.f2278e = z2;
            if (!z2 || this.f2275b == null) {
                i5 = i6;
            } else {
                this.f2275b.setBounds(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
            }
        } else if (this.f2276c != null) {
            this.f2276c.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
        } else {
            i5 = 0;
        }
        if (i5 != 0) {
            invalidate();
        }
    }

    public void onMeasure(int i, int i2) {
        if (this.f2281h == null && MeasureSpec.getMode(i2) == Integer.MIN_VALUE && this.f2283j >= 0) {
            i2 = MeasureSpec.makeMeasureSpec(Math.min(this.f2283j, MeasureSpec.getSize(i2)), Integer.MIN_VALUE);
        }
        super.onMeasure(i, i2);
        if (this.f2281h != null) {
            int mode = MeasureSpec.getMode(i2);
            if (this.f2280g != null && this.f2280g.getVisibility() != 8 && mode != 1073741824) {
                int b = !m4355a(this.f2281h) ? m4356b(this.f2281h) : !m4355a(this.f2282i) ? m4356b(this.f2282i) : 0;
                setMeasuredDimension(getMeasuredWidth(), Math.min(b + m4356b(this.f2280g), mode == Integer.MIN_VALUE ? MeasureSpec.getSize(i2) : Integer.MAX_VALUE));
            }
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        return true;
    }

    public void setPrimaryBackground(Drawable drawable) {
        boolean z = true;
        if (this.f2274a != null) {
            this.f2274a.setCallback(null);
            unscheduleDrawable(this.f2274a);
        }
        this.f2274a = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
            if (this.f2281h != null) {
                this.f2274a.setBounds(this.f2281h.getLeft(), this.f2281h.getTop(), this.f2281h.getRight(), this.f2281h.getBottom());
            }
        }
        if (this.f2277d) {
            if (this.f2276c != null) {
                z = false;
            }
        } else if (!(this.f2274a == null && this.f2275b == null)) {
            z = false;
        }
        setWillNotDraw(z);
        invalidate();
    }

    public void setSplitBackground(Drawable drawable) {
        boolean z = true;
        if (this.f2276c != null) {
            this.f2276c.setCallback(null);
            unscheduleDrawable(this.f2276c);
        }
        this.f2276c = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
            if (this.f2277d && this.f2276c != null) {
                this.f2276c.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
            }
        }
        if (this.f2277d) {
            if (this.f2276c != null) {
                z = false;
            }
        } else if (!(this.f2274a == null && this.f2275b == null)) {
            z = false;
        }
        setWillNotDraw(z);
        invalidate();
    }

    public void setStackedBackground(Drawable drawable) {
        boolean z = true;
        if (this.f2275b != null) {
            this.f2275b.setCallback(null);
            unscheduleDrawable(this.f2275b);
        }
        this.f2275b = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
            if (this.f2278e && this.f2275b != null) {
                this.f2275b.setBounds(this.f2280g.getLeft(), this.f2280g.getTop(), this.f2280g.getRight(), this.f2280g.getBottom());
            }
        }
        if (this.f2277d) {
            if (this.f2276c != null) {
                z = false;
            }
        } else if (!(this.f2274a == null && this.f2275b == null)) {
            z = false;
        }
        setWillNotDraw(z);
        invalidate();
    }

    public void setTabContainer(bc bcVar) {
        if (this.f2280g != null) {
            removeView(this.f2280g);
        }
        this.f2280g = bcVar;
        if (bcVar != null) {
            addView(bcVar);
            ViewGroup.LayoutParams layoutParams = bcVar.getLayoutParams();
            layoutParams.width = -1;
            layoutParams.height = -2;
            bcVar.setAllowCollapse(false);
        }
    }

    public void setTransitioning(boolean z) {
        this.f2279f = z;
        setDescendantFocusability(z ? 393216 : 262144);
    }

    public void setVisibility(int i) {
        super.setVisibility(i);
        boolean z = i == 0;
        if (this.f2274a != null) {
            this.f2274a.setVisible(z, false);
        }
        if (this.f2275b != null) {
            this.f2275b.setVisible(z, false);
        }
        if (this.f2276c != null) {
            this.f2276c.setVisible(z, false);
        }
    }

    public ActionMode startActionModeForChild(View view, Callback callback) {
        return null;
    }

    public ActionMode startActionModeForChild(View view, Callback callback, int i) {
        return i != 0 ? super.startActionModeForChild(view, callback, i) : null;
    }

    protected boolean verifyDrawable(Drawable drawable) {
        return (drawable == this.f2274a && !this.f2277d) || ((drawable == this.f2275b && this.f2278e) || ((drawable == this.f2276c && this.f2277d) || super.verifyDrawable(drawable)));
    }
}
