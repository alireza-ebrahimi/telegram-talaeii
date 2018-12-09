package utils.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import utils.view.SlidingTabLayout.TabColorizer;

class SlidingTabStrip extends LinearLayout {
    /* renamed from: a */
    private final int f10303a;
    /* renamed from: b */
    private final Paint f10304b;
    /* renamed from: c */
    private final int f10305c;
    /* renamed from: d */
    private final Paint f10306d;
    /* renamed from: e */
    private final int f10307e;
    /* renamed from: f */
    private final SimpleTabColorizer f10308f;
    /* renamed from: g */
    private int f10309g;
    /* renamed from: h */
    private float f10310h;
    /* renamed from: i */
    private TabColorizer f10311i;

    private static class SimpleTabColorizer implements TabColorizer {
        /* renamed from: a */
        private int[] f10302a;

        private SimpleTabColorizer() {
        }

        /* renamed from: a */
        void m14188a(int... iArr) {
            this.f10302a = iArr;
        }

        public final int getIndicatorColor(int i) {
            return this.f10302a[i % this.f10302a.length];
        }
    }

    SlidingTabStrip(Context context) {
        this(context, null);
    }

    SlidingTabStrip(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setWillNotDraw(false);
        float f = getResources().getDisplayMetrics().density;
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(16842800, typedValue, true);
        this.f10307e = m14189a(typedValue.data, (byte) 38);
        this.f10308f = new SimpleTabColorizer();
        this.f10308f.m14188a(-13388315);
        this.f10303a = (int) (BitmapDescriptorFactory.HUE_RED * f);
        this.f10304b = new Paint();
        this.f10304b.setColor(this.f10307e);
        this.f10305c = (int) (f * 3.0f);
        this.f10306d = new Paint();
    }

    /* renamed from: a */
    private static int m14189a(int i, byte b) {
        return Color.argb(b, Color.red(i), Color.green(i), Color.blue(i));
    }

    /* renamed from: a */
    private static int m14190a(int i, int i2, float f) {
        float f2 = 1.0f - f;
        return Color.rgb((int) ((((float) Color.red(i)) * f) + (((float) Color.red(i2)) * f2)), (int) ((((float) Color.green(i)) * f) + (((float) Color.green(i2)) * f2)), (int) ((f2 * ((float) Color.blue(i2))) + (((float) Color.blue(i)) * f)));
    }

    /* renamed from: a */
    void m14191a(int i, float f) {
        this.f10309g = i;
        this.f10310h = f;
        invalidate();
    }

    /* renamed from: a */
    void m14192a(TabColorizer tabColorizer) {
        this.f10311i = tabColorizer;
        invalidate();
    }

    /* renamed from: a */
    void m14193a(int... iArr) {
        this.f10311i = null;
        this.f10308f.m14188a(iArr);
        invalidate();
    }

    protected void onDraw(Canvas canvas) {
        int height = getHeight();
        int childCount = getChildCount();
        if (this.f10311i != null) {
            TabColorizer tabColorizer = this.f10311i;
        } else {
            Object obj = this.f10308f;
        }
        if (childCount > 0) {
            int i;
            View childAt = getChildAt(this.f10309g);
            int left = childAt.getLeft();
            childCount = childAt.getRight();
            int indicatorColor = tabColorizer.getIndicatorColor(this.f10309g);
            if (this.f10310h <= BitmapDescriptorFactory.HUE_RED || this.f10309g >= getChildCount() - 1) {
                i = childCount;
                childCount = left;
            } else {
                i = tabColorizer.getIndicatorColor(this.f10309g + 1);
                if (indicatorColor != i) {
                    indicatorColor = m14190a(i, indicatorColor, this.f10310h);
                }
                View childAt2 = getChildAt(this.f10309g + 1);
                left = (int) ((((float) left) * (1.0f - this.f10310h)) + (this.f10310h * ((float) childAt2.getLeft())));
                i = (int) ((((float) childCount) * (1.0f - this.f10310h)) + (((float) childAt2.getRight()) * this.f10310h));
                childCount = left;
            }
            this.f10306d.setColor(indicatorColor);
            canvas.drawRect((float) childCount, (float) (height - this.f10305c), (float) i, (float) height, this.f10306d);
        }
        canvas.drawRect(BitmapDescriptorFactory.HUE_RED, (float) (height - this.f10303a), (float) getWidth(), (float) height, this.f10304b);
    }
}
