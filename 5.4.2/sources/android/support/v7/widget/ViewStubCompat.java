package android.support.v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.v7.p025a.C0748a.C0747j;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import java.lang.ref.WeakReference;

public final class ViewStubCompat extends View {
    /* renamed from: a */
    private int f2714a;
    /* renamed from: b */
    private int f2715b;
    /* renamed from: c */
    private WeakReference<View> f2716c;
    /* renamed from: d */
    private LayoutInflater f2717d;
    /* renamed from: e */
    private C0976a f2718e;

    /* renamed from: android.support.v7.widget.ViewStubCompat$a */
    public interface C0976a {
        /* renamed from: a */
        void m5205a(ViewStubCompat viewStubCompat, View view);
    }

    public ViewStubCompat(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ViewStubCompat(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f2714a = 0;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C0747j.ViewStubCompat, i, 0);
        this.f2715b = obtainStyledAttributes.getResourceId(C0747j.ViewStubCompat_android_inflatedId, -1);
        this.f2714a = obtainStyledAttributes.getResourceId(C0747j.ViewStubCompat_android_layout, 0);
        setId(obtainStyledAttributes.getResourceId(C0747j.ViewStubCompat_android_id, -1));
        obtainStyledAttributes.recycle();
        setVisibility(8);
        setWillNotDraw(true);
    }

    /* renamed from: a */
    public View m5206a() {
        ViewParent parent = getParent();
        if (parent == null || !(parent instanceof ViewGroup)) {
            throw new IllegalStateException("ViewStub must have a non-null ViewGroup viewParent");
        } else if (this.f2714a != 0) {
            ViewGroup viewGroup = (ViewGroup) parent;
            View inflate = (this.f2717d != null ? this.f2717d : LayoutInflater.from(getContext())).inflate(this.f2714a, viewGroup, false);
            if (this.f2715b != -1) {
                inflate.setId(this.f2715b);
            }
            int indexOfChild = viewGroup.indexOfChild(this);
            viewGroup.removeViewInLayout(this);
            LayoutParams layoutParams = getLayoutParams();
            if (layoutParams != null) {
                viewGroup.addView(inflate, indexOfChild, layoutParams);
            } else {
                viewGroup.addView(inflate, indexOfChild);
            }
            this.f2716c = new WeakReference(inflate);
            if (this.f2718e != null) {
                this.f2718e.m5205a(this, inflate);
            }
            return inflate;
        } else {
            throw new IllegalArgumentException("ViewStub must have a valid layoutResource");
        }
    }

    protected void dispatchDraw(Canvas canvas) {
    }

    public void draw(Canvas canvas) {
    }

    public int getInflatedId() {
        return this.f2715b;
    }

    public LayoutInflater getLayoutInflater() {
        return this.f2717d;
    }

    public int getLayoutResource() {
        return this.f2714a;
    }

    protected void onMeasure(int i, int i2) {
        setMeasuredDimension(0, 0);
    }

    public void setInflatedId(int i) {
        this.f2715b = i;
    }

    public void setLayoutInflater(LayoutInflater layoutInflater) {
        this.f2717d = layoutInflater;
    }

    public void setLayoutResource(int i) {
        this.f2714a = i;
    }

    public void setOnInflateListener(C0976a c0976a) {
        this.f2718e = c0976a;
    }

    public void setVisibility(int i) {
        if (this.f2716c != null) {
            View view = (View) this.f2716c.get();
            if (view != null) {
                view.setVisibility(i);
                return;
            }
            throw new IllegalStateException("setVisibility called on un-referenced view");
        }
        super.setVisibility(i);
        if (i == 0 || i == 4) {
            m5206a();
        }
    }
}
