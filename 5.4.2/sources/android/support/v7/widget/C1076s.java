package android.support.v7.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.ah;
import android.support.v7.p025a.C0748a.C0738a;
import android.util.AttributeSet;
import android.widget.RatingBar;

/* renamed from: android.support.v7.widget.s */
public class C1076s extends RatingBar {
    /* renamed from: a */
    private C1074q f3193a;

    public C1076s(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, C0738a.ratingBarStyle);
    }

    public C1076s(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f3193a = new C1074q(this);
        this.f3193a.mo1010a(attributeSet, i);
    }

    protected synchronized void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        Bitmap a = this.f3193a.m5897a();
        if (a != null) {
            setMeasuredDimension(ah.m2773a(a.getWidth() * getNumStars(), i, 0), getMeasuredHeight());
        }
    }
}
