package utils.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.RelativeLayout;
import org.telegram.messenger.C3336R;

public class WidthRelativeLayout extends RelativeLayout {
    /* renamed from: a */
    int f10340a = 1;
    /* renamed from: b */
    int f10341b = 1;

    public WidthRelativeLayout(Context context) {
        super(context);
    }

    public WidthRelativeLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        m14206a(context, attributeSet);
    }

    public WidthRelativeLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        m14206a(context, attributeSet);
    }

    /* renamed from: a */
    void m14206a(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C3336R.styleable.WidthRelativeLayout);
        int indexCount = obtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = obtainStyledAttributes.getIndex(i);
            switch (index) {
                case 0:
                    this.f10340a = obtainStyledAttributes.getInteger(index, 1);
                    break;
                case 1:
                    this.f10341b = obtainStyledAttributes.getInteger(index, 1);
                    break;
                default:
                    break;
            }
        }
        obtainStyledAttributes.recycle();
    }

    public int getHorizontalN() {
        return this.f10340a;
    }

    public int getVerticalN() {
        return this.f10341b;
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i);
        int size = MeasureSpec.getSize(i);
        setMeasuredDimension(size, (int) Math.ceil((double) ((((float) size) * ((float) this.f10341b)) / ((float) this.f10340a))));
    }

    public void setHorizontalN(int i) {
        this.f10340a = i;
    }

    public void setVerticalN(int i) {
        this.f10341b = i;
    }
}
