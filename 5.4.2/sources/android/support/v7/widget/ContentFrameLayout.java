package android.support.v7.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.ah;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;

public class ContentFrameLayout extends FrameLayout {
    /* renamed from: a */
    private TypedValue f1821a;
    /* renamed from: b */
    private TypedValue f1822b;
    /* renamed from: c */
    private TypedValue f1823c;
    /* renamed from: d */
    private TypedValue f1824d;
    /* renamed from: e */
    private TypedValue f1825e;
    /* renamed from: f */
    private TypedValue f1826f;
    /* renamed from: g */
    private final Rect f1827g;
    /* renamed from: h */
    private C0789a f1828h;

    /* renamed from: android.support.v7.widget.ContentFrameLayout$a */
    public interface C0789a {
        /* renamed from: a */
        void mo655a();

        /* renamed from: b */
        void mo656b();
    }

    public ContentFrameLayout(Context context) {
        this(context, null);
    }

    public ContentFrameLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ContentFrameLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f1827g = new Rect();
    }

    /* renamed from: a */
    public void m3805a(int i, int i2, int i3, int i4) {
        this.f1827g.set(i, i2, i3, i4);
        if (ah.m2767G(this)) {
            requestLayout();
        }
    }

    /* renamed from: a */
    public void m3806a(Rect rect) {
        fitSystemWindows(rect);
    }

    public TypedValue getFixedHeightMajor() {
        if (this.f1825e == null) {
            this.f1825e = new TypedValue();
        }
        return this.f1825e;
    }

    public TypedValue getFixedHeightMinor() {
        if (this.f1826f == null) {
            this.f1826f = new TypedValue();
        }
        return this.f1826f;
    }

    public TypedValue getFixedWidthMajor() {
        if (this.f1823c == null) {
            this.f1823c = new TypedValue();
        }
        return this.f1823c;
    }

    public TypedValue getFixedWidthMinor() {
        if (this.f1824d == null) {
            this.f1824d = new TypedValue();
        }
        return this.f1824d;
    }

    public TypedValue getMinWidthMajor() {
        if (this.f1821a == null) {
            this.f1821a = new TypedValue();
        }
        return this.f1821a;
    }

    public TypedValue getMinWidthMinor() {
        if (this.f1822b == null) {
            this.f1822b = new TypedValue();
        }
        return this.f1822b;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.f1828h != null) {
            this.f1828h.mo655a();
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.f1828h != null) {
            this.f1828h.mo656b();
        }
    }

    protected void onMeasure(int i, int i2) {
        TypedValue typedValue;
        int dimension;
        Object obj;
        TypedValue typedValue2;
        int dimension2;
        Object obj2 = null;
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        Object obj3 = displayMetrics.widthPixels < displayMetrics.heightPixels ? 1 : null;
        int mode = MeasureSpec.getMode(i);
        int mode2 = MeasureSpec.getMode(i2);
        if (mode == Integer.MIN_VALUE) {
            typedValue = obj3 != null ? this.f1824d : this.f1823c;
            if (!(typedValue == null || typedValue.type == 0)) {
                dimension = typedValue.type == 5 ? (int) typedValue.getDimension(displayMetrics) : typedValue.type == 6 ? (int) typedValue.getFraction((float) displayMetrics.widthPixels, (float) displayMetrics.widthPixels) : 0;
                if (dimension > 0) {
                    i = MeasureSpec.makeMeasureSpec(Math.min(dimension - (this.f1827g.left + this.f1827g.right), MeasureSpec.getSize(i)), 1073741824);
                    obj = 1;
                    if (mode2 == Integer.MIN_VALUE) {
                        typedValue = obj3 == null ? this.f1825e : this.f1826f;
                        if (!(typedValue == null || typedValue.type == 0)) {
                            dimension = typedValue.type != 5 ? (int) typedValue.getDimension(displayMetrics) : typedValue.type != 6 ? (int) typedValue.getFraction((float) displayMetrics.heightPixels, (float) displayMetrics.heightPixels) : 0;
                            if (dimension > 0) {
                                i2 = MeasureSpec.makeMeasureSpec(Math.min(dimension - (this.f1827g.top + this.f1827g.bottom), MeasureSpec.getSize(i2)), 1073741824);
                            }
                        }
                    }
                    super.onMeasure(i, i2);
                    mode2 = getMeasuredWidth();
                    dimension = MeasureSpec.makeMeasureSpec(mode2, 1073741824);
                    if (obj == null && mode == Integer.MIN_VALUE) {
                        typedValue2 = obj3 == null ? this.f1822b : this.f1821a;
                        if (!(typedValue2 == null || typedValue2.type == 0)) {
                            dimension2 = typedValue2.type != 5 ? (int) typedValue2.getDimension(displayMetrics) : typedValue2.type != 6 ? (int) typedValue2.getFraction((float) displayMetrics.widthPixels, (float) displayMetrics.widthPixels) : 0;
                            if (dimension2 > 0) {
                                dimension2 -= this.f1827g.left + this.f1827g.right;
                            }
                            if (mode2 < dimension2) {
                                dimension2 = MeasureSpec.makeMeasureSpec(dimension2, 1073741824);
                                obj2 = 1;
                                if (obj2 == null) {
                                    super.onMeasure(dimension2, i2);
                                }
                            }
                        }
                    }
                    dimension2 = dimension;
                    if (obj2 == null) {
                        super.onMeasure(dimension2, i2);
                    }
                }
            }
        }
        obj = null;
        if (mode2 == Integer.MIN_VALUE) {
            if (obj3 == null) {
            }
            if (typedValue.type != 5) {
                if (typedValue.type != 6) {
                }
            }
            if (dimension > 0) {
                i2 = MeasureSpec.makeMeasureSpec(Math.min(dimension - (this.f1827g.top + this.f1827g.bottom), MeasureSpec.getSize(i2)), 1073741824);
            }
        }
        super.onMeasure(i, i2);
        mode2 = getMeasuredWidth();
        dimension = MeasureSpec.makeMeasureSpec(mode2, 1073741824);
        if (obj3 == null) {
        }
        if (typedValue2.type != 5) {
            if (typedValue2.type != 6) {
            }
        }
        if (dimension2 > 0) {
            dimension2 -= this.f1827g.left + this.f1827g.right;
        }
        if (mode2 < dimension2) {
            dimension2 = MeasureSpec.makeMeasureSpec(dimension2, 1073741824);
            obj2 = 1;
            if (obj2 == null) {
                super.onMeasure(dimension2, i2);
            }
        }
        dimension2 = dimension;
        if (obj2 == null) {
            super.onMeasure(dimension2, i2);
        }
    }

    public void setAttachListener(C0789a c0789a) {
        this.f1828h = c0789a;
    }
}
