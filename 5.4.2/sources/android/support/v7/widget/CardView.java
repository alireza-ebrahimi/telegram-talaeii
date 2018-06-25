package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v7.p026b.C0822a.C0818a;
import android.support.v7.p026b.C0822a.C0820c;
import android.support.v7.p026b.C0822a.C0821d;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class CardView extends FrameLayout {
    /* renamed from: e */
    private static final int[] f2390e = new int[]{16842801};
    /* renamed from: f */
    private static final ac f2391f;
    /* renamed from: a */
    int f2392a;
    /* renamed from: b */
    int f2393b;
    /* renamed from: c */
    final Rect f2394c = new Rect();
    /* renamed from: d */
    final Rect f2395d = new Rect();
    /* renamed from: g */
    private boolean f2396g;
    /* renamed from: h */
    private boolean f2397h;
    /* renamed from: i */
    private final aa f2398i = new C09051(this);

    /* renamed from: android.support.v7.widget.CardView$1 */
    class C09051 implements aa {
        /* renamed from: a */
        final /* synthetic */ CardView f2388a;
        /* renamed from: b */
        private Drawable f2389b;

        C09051(CardView cardView) {
            this.f2388a = cardView;
        }

        /* renamed from: a */
        public void mo792a(int i, int i2) {
            if (i > this.f2388a.f2392a) {
                super.setMinimumWidth(i);
            }
            if (i2 > this.f2388a.f2393b) {
                super.setMinimumHeight(i2);
            }
        }

        /* renamed from: a */
        public void mo793a(int i, int i2, int i3, int i4) {
            this.f2388a.f2395d.set(i, i2, i3, i4);
            super.setPadding(this.f2388a.f2394c.left + i, this.f2388a.f2394c.top + i2, this.f2388a.f2394c.right + i3, this.f2388a.f2394c.bottom + i4);
        }

        /* renamed from: a */
        public void mo794a(Drawable drawable) {
            this.f2389b = drawable;
            this.f2388a.setBackgroundDrawable(drawable);
        }

        /* renamed from: a */
        public boolean mo795a() {
            return this.f2388a.getUseCompatPadding();
        }

        /* renamed from: b */
        public boolean mo796b() {
            return this.f2388a.getPreventCornerOverlap();
        }

        /* renamed from: c */
        public Drawable mo797c() {
            return this.f2389b;
        }

        /* renamed from: d */
        public View mo798d() {
            return this.f2388a;
        }
    }

    static {
        if (VERSION.SDK_INT >= 21) {
            f2391f = new C1088z();
        } else if (VERSION.SDK_INT >= 17) {
            f2391f = new ad();
        } else {
            f2391f = new ab();
        }
        f2391f.mo904a();
    }

    public CardView(Context context) {
        super(context);
        m4461a(context, null, 0);
    }

    public CardView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        m4461a(context, attributeSet, 0);
    }

    public CardView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        m4461a(context, attributeSet, i);
    }

    /* renamed from: a */
    private void m4461a(Context context, AttributeSet attributeSet, int i) {
        ColorStateList colorStateList;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C0821d.CardView, i, C0820c.CardView);
        if (obtainStyledAttributes.hasValue(C0821d.CardView_cardBackgroundColor)) {
            colorStateList = obtainStyledAttributes.getColorStateList(C0821d.CardView_cardBackgroundColor);
        } else {
            TypedArray obtainStyledAttributes2 = getContext().obtainStyledAttributes(f2390e);
            int color = obtainStyledAttributes2.getColor(0, 0);
            obtainStyledAttributes2.recycle();
            float[] fArr = new float[3];
            Color.colorToHSV(color, fArr);
            colorStateList = ColorStateList.valueOf(fArr[2] > 0.5f ? getResources().getColor(C0818a.cardview_light_background) : getResources().getColor(C0818a.cardview_dark_background));
        }
        float dimension = obtainStyledAttributes.getDimension(C0821d.CardView_cardCornerRadius, BitmapDescriptorFactory.HUE_RED);
        float dimension2 = obtainStyledAttributes.getDimension(C0821d.CardView_cardElevation, BitmapDescriptorFactory.HUE_RED);
        float dimension3 = obtainStyledAttributes.getDimension(C0821d.CardView_cardMaxElevation, BitmapDescriptorFactory.HUE_RED);
        this.f2396g = obtainStyledAttributes.getBoolean(C0821d.CardView_cardUseCompatPadding, false);
        this.f2397h = obtainStyledAttributes.getBoolean(C0821d.CardView_cardPreventCornerOverlap, true);
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(C0821d.CardView_contentPadding, 0);
        this.f2394c.left = obtainStyledAttributes.getDimensionPixelSize(C0821d.CardView_contentPaddingLeft, dimensionPixelSize);
        this.f2394c.top = obtainStyledAttributes.getDimensionPixelSize(C0821d.CardView_contentPaddingTop, dimensionPixelSize);
        this.f2394c.right = obtainStyledAttributes.getDimensionPixelSize(C0821d.CardView_contentPaddingRight, dimensionPixelSize);
        this.f2394c.bottom = obtainStyledAttributes.getDimensionPixelSize(C0821d.CardView_contentPaddingBottom, dimensionPixelSize);
        if (dimension2 > dimension3) {
            dimension3 = dimension2;
        }
        this.f2392a = obtainStyledAttributes.getDimensionPixelSize(C0821d.CardView_android_minWidth, 0);
        this.f2393b = obtainStyledAttributes.getDimensionPixelSize(C0821d.CardView_android_minHeight, 0);
        obtainStyledAttributes.recycle();
        f2391f.mo906a(this.f2398i, context, colorStateList, dimension, dimension2, dimension3);
    }

    public ColorStateList getCardBackgroundColor() {
        return f2391f.mo916i(this.f2398i);
    }

    public float getCardElevation() {
        return f2391f.mo913e(this.f2398i);
    }

    public int getContentPaddingBottom() {
        return this.f2394c.bottom;
    }

    public int getContentPaddingLeft() {
        return this.f2394c.left;
    }

    public int getContentPaddingRight() {
        return this.f2394c.right;
    }

    public int getContentPaddingTop() {
        return this.f2394c.top;
    }

    public float getMaxCardElevation() {
        return f2391f.mo903a(this.f2398i);
    }

    public boolean getPreventCornerOverlap() {
        return this.f2397h;
    }

    public float getRadius() {
        return f2391f.mo912d(this.f2398i);
    }

    public boolean getUseCompatPadding() {
        return this.f2396g;
    }

    protected void onMeasure(int i, int i2) {
        if (f2391f instanceof C1088z) {
            super.onMeasure(i, i2);
            return;
        }
        int mode = MeasureSpec.getMode(i);
        switch (mode) {
            case Integer.MIN_VALUE:
            case 1073741824:
                i = MeasureSpec.makeMeasureSpec(Math.max((int) Math.ceil((double) f2391f.mo908b(this.f2398i)), MeasureSpec.getSize(i)), mode);
                break;
        }
        mode = MeasureSpec.getMode(i2);
        switch (mode) {
            case Integer.MIN_VALUE:
            case 1073741824:
                i2 = MeasureSpec.makeMeasureSpec(Math.max((int) Math.ceil((double) f2391f.mo910c(this.f2398i)), MeasureSpec.getSize(i2)), mode);
                break;
        }
        super.onMeasure(i, i2);
    }

    public void setCardBackgroundColor(int i) {
        f2391f.mo907a(this.f2398i, ColorStateList.valueOf(i));
    }

    public void setCardBackgroundColor(ColorStateList colorStateList) {
        f2391f.mo907a(this.f2398i, colorStateList);
    }

    public void setCardElevation(float f) {
        f2391f.mo911c(this.f2398i, f);
    }

    public void setMaxCardElevation(float f) {
        f2391f.mo909b(this.f2398i, f);
    }

    public void setMinimumHeight(int i) {
        this.f2393b = i;
        super.setMinimumHeight(i);
    }

    public void setMinimumWidth(int i) {
        this.f2392a = i;
        super.setMinimumWidth(i);
    }

    public void setPadding(int i, int i2, int i3, int i4) {
    }

    public void setPaddingRelative(int i, int i2, int i3, int i4) {
    }

    public void setPreventCornerOverlap(boolean z) {
        if (z != this.f2397h) {
            this.f2397h = z;
            f2391f.mo915h(this.f2398i);
        }
    }

    public void setRadius(float f) {
        f2391f.mo905a(this.f2398i, f);
    }

    public void setUseCompatPadding(boolean z) {
        if (this.f2396g != z) {
            this.f2396g = z;
            f2391f.mo914g(this.f2398i);
        }
    }
}
