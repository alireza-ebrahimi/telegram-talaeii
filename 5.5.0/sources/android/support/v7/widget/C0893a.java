package android.support.v7.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.support.v4.view.C0659t;
import android.support.v4.view.ah;
import android.support.v4.view.ax;
import android.support.v4.view.bb;
import android.support.v7.p025a.C0748a.C0738a;
import android.support.v7.p025a.C0748a.C0747j;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/* renamed from: android.support.v7.widget.a */
abstract class C0893a extends ViewGroup {
    /* renamed from: a */
    protected final C0977a f2286a;
    /* renamed from: b */
    protected final Context f2287b;
    /* renamed from: c */
    protected ActionMenuView f2288c;
    /* renamed from: d */
    protected C1052d f2289d;
    /* renamed from: e */
    protected int f2290e;
    /* renamed from: f */
    protected ax f2291f;
    /* renamed from: g */
    private boolean f2292g;
    /* renamed from: h */
    private boolean f2293h;

    /* renamed from: android.support.v7.widget.a$a */
    protected class C0977a implements bb {
        /* renamed from: a */
        int f2719a;
        /* renamed from: b */
        final /* synthetic */ C0893a f2720b;
        /* renamed from: c */
        private boolean f2721c = false;

        protected C0977a(C0893a c0893a) {
            this.f2720b = c0893a;
        }

        /* renamed from: a */
        public C0977a m5207a(ax axVar, int i) {
            this.f2720b.f2291f = axVar;
            this.f2719a = i;
            return this;
        }

        public void onAnimationCancel(View view) {
            this.f2721c = true;
        }

        public void onAnimationEnd(View view) {
            if (!this.f2721c) {
                this.f2720b.f2291f = null;
                super.setVisibility(this.f2719a);
            }
        }

        public void onAnimationStart(View view) {
            super.setVisibility(0);
            this.f2721c = false;
        }
    }

    C0893a(Context context) {
        this(context, null);
    }

    C0893a(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    C0893a(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f2286a = new C0977a(this);
        TypedValue typedValue = new TypedValue();
        if (!context.getTheme().resolveAttribute(C0738a.actionBarPopupTheme, typedValue, true) || typedValue.resourceId == 0) {
            this.f2287b = context;
        } else {
            this.f2287b = new ContextThemeWrapper(context, typedValue.resourceId);
        }
    }

    /* renamed from: a */
    protected static int m4357a(int i, int i2, boolean z) {
        return z ? i - i2 : i + i2;
    }

    /* renamed from: a */
    protected int m4360a(View view, int i, int i2, int i3) {
        view.measure(MeasureSpec.makeMeasureSpec(i, Integer.MIN_VALUE), i2);
        return Math.max(0, (i - view.getMeasuredWidth()) - i3);
    }

    /* renamed from: a */
    protected int m4361a(View view, int i, int i2, int i3, boolean z) {
        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();
        int i4 = ((i3 - measuredHeight) / 2) + i2;
        if (z) {
            view.layout(i - measuredWidth, i4, i, measuredHeight + i4);
        } else {
            view.layout(i, i4, i + measuredWidth, measuredHeight + i4);
        }
        return z ? -measuredWidth : measuredWidth;
    }

    /* renamed from: a */
    public ax mo765a(int i, long j) {
        if (this.f2291f != null) {
            this.f2291f.m3027b();
        }
        if (i == 0) {
            if (getVisibility() != 0) {
                ah.m2800c((View) this, (float) BitmapDescriptorFactory.HUE_RED);
            }
            ax a = ah.m2827q(this).m3020a(1.0f);
            a.m3021a(j);
            a.m3022a(this.f2286a.m5207a(a, i));
            return a;
        }
        a = ah.m2827q(this).m3020a((float) BitmapDescriptorFactory.HUE_RED);
        a.m3021a(j);
        a.m3022a(this.f2286a.m5207a(a, i));
        return a;
    }

    /* renamed from: a */
    public boolean mo766a() {
        return this.f2289d != null ? this.f2289d.m5783d() : false;
    }

    public int getAnimatedVisibility() {
        return this.f2291f != null ? this.f2286a.f2719a : getVisibility();
    }

    public int getContentHeight() {
        return this.f2290e;
    }

    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(null, C0747j.ActionBar, C0738a.actionBarStyle, 0);
        setContentHeight(obtainStyledAttributes.getLayoutDimension(C0747j.ActionBar_height, 0));
        obtainStyledAttributes.recycle();
        if (this.f2289d != null) {
            this.f2289d.m5769a(configuration);
        }
    }

    public boolean onHoverEvent(MotionEvent motionEvent) {
        int a = C0659t.m3205a(motionEvent);
        if (a == 9) {
            this.f2293h = false;
        }
        if (!this.f2293h) {
            boolean onHoverEvent = super.onHoverEvent(motionEvent);
            if (a == 9 && !onHoverEvent) {
                this.f2293h = true;
            }
        }
        if (a == 10 || a == 3) {
            this.f2293h = false;
        }
        return true;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int a = C0659t.m3205a(motionEvent);
        if (a == 0) {
            this.f2292g = false;
        }
        if (!this.f2292g) {
            boolean onTouchEvent = super.onTouchEvent(motionEvent);
            if (a == 0 && !onTouchEvent) {
                this.f2292g = true;
            }
        }
        if (a == 1 || a == 3) {
            this.f2292g = false;
        }
        return true;
    }

    public void setContentHeight(int i) {
        this.f2290e = i;
        requestLayout();
    }

    public void setVisibility(int i) {
        if (i != getVisibility()) {
            if (this.f2291f != null) {
                this.f2291f.m3027b();
            }
            super.setVisibility(i);
        }
    }
}
