package android.support.design.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.C0073a.C0065d;
import android.support.design.C0073a.C0067f;
import android.support.design.C0073a.C0072k;
import android.support.design.widget.C0114b.C0080c;
import android.support.v4.view.ah;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class SnackbarContentLayout extends LinearLayout implements C0080c {
    /* renamed from: a */
    private TextView f208a;
    /* renamed from: b */
    private Button f209b;
    /* renamed from: c */
    private int f210c;
    /* renamed from: d */
    private int f211d;

    public SnackbarContentLayout(Context context) {
        this(context, null);
    }

    public SnackbarContentLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C0072k.SnackbarLayout);
        this.f210c = obtainStyledAttributes.getDimensionPixelSize(C0072k.SnackbarLayout_android_maxWidth, -1);
        this.f211d = obtainStyledAttributes.getDimensionPixelSize(C0072k.SnackbarLayout_maxActionInlineWidth, -1);
        obtainStyledAttributes.recycle();
    }

    /* renamed from: a */
    private static void m310a(View view, int i, int i2) {
        if (ah.m2762B(view)) {
            ah.m2777a(view, ah.m2818j(view), i, ah.m2820k(view), i2);
        } else {
            view.setPadding(view.getPaddingLeft(), i, view.getPaddingRight(), i2);
        }
    }

    /* renamed from: a */
    private boolean m311a(int i, int i2, int i3) {
        boolean z = false;
        if (i != getOrientation()) {
            setOrientation(i);
            z = true;
        }
        if (this.f208a.getPaddingTop() == i2 && this.f208a.getPaddingBottom() == i3) {
            return z;
        }
        m310a(this.f208a, i2, i3);
        return true;
    }

    /* renamed from: a */
    public void mo55a(int i, int i2) {
        ah.m2800c(this.f208a, (float) BitmapDescriptorFactory.HUE_RED);
        ah.m2827q(this.f208a).m3020a(1.0f).m3021a((long) i2).m3026b((long) i).m3029c();
        if (this.f209b.getVisibility() == 0) {
            ah.m2800c(this.f209b, (float) BitmapDescriptorFactory.HUE_RED);
            ah.m2827q(this.f209b).m3020a(1.0f).m3021a((long) i2).m3026b((long) i).m3029c();
        }
    }

    /* renamed from: b */
    public void mo56b(int i, int i2) {
        ah.m2800c(this.f208a, 1.0f);
        ah.m2827q(this.f208a).m3020a((float) BitmapDescriptorFactory.HUE_RED).m3021a((long) i2).m3026b((long) i).m3029c();
        if (this.f209b.getVisibility() == 0) {
            ah.m2800c(this.f209b, 1.0f);
            ah.m2827q(this.f209b).m3020a((float) BitmapDescriptorFactory.HUE_RED).m3021a((long) i2).m3026b((long) i).m3029c();
        }
    }

    public Button getActionView() {
        return this.f209b;
    }

    public TextView getMessageView() {
        return this.f208a;
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.f208a = (TextView) findViewById(C0067f.snackbar_text);
        this.f209b = (Button) findViewById(C0067f.snackbar_action);
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.f210c > 0 && getMeasuredWidth() > this.f210c) {
            i = MeasureSpec.makeMeasureSpec(this.f210c, 1073741824);
            super.onMeasure(i, i2);
        }
        int dimensionPixelSize = getResources().getDimensionPixelSize(C0065d.design_snackbar_padding_vertical_2lines);
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(C0065d.design_snackbar_padding_vertical);
        int i3 = this.f208a.getLayout().getLineCount() > 1 ? 1 : 0;
        if (i3 == 0 || this.f211d <= 0 || this.f209b.getMeasuredWidth() <= this.f211d) {
            if (i3 == 0) {
                dimensionPixelSize = dimensionPixelSize2;
            }
            if (m311a(0, dimensionPixelSize, dimensionPixelSize)) {
                dimensionPixelSize = 1;
            }
            dimensionPixelSize = 0;
        } else {
            if (m311a(1, dimensionPixelSize, dimensionPixelSize - dimensionPixelSize2)) {
                dimensionPixelSize = 1;
            }
            dimensionPixelSize = 0;
        }
        if (dimensionPixelSize != 0) {
            super.onMeasure(i, i2);
        }
    }
}
