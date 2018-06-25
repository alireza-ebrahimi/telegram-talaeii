package utils.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.RelativeLayout;
import org.telegram.messenger.C0906R;

public class WidthRelativeLayout extends RelativeLayout {
    int horizontalN = 1;
    int verticalN = 1;

    public WidthRelativeLayout(Context context) {
        super(context);
    }

    public WidthRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        handleAttributes(context, attrs);
    }

    public WidthRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        handleAttributes(context, attrs);
    }

    public int getHorizontalN() {
        return this.horizontalN;
    }

    public void setHorizontalN(int horizontalN) {
        this.horizontalN = horizontalN;
    }

    public int getVerticalN() {
        return this.verticalN;
    }

    public void setVerticalN(int verticalN) {
        this.verticalN = verticalN;
    }

    void handleAttributes(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, C0906R.styleable.WidthRelativeLayout);
        int N = a.getIndexCount();
        for (int i = 0; i < N; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case 0:
                    this.horizontalN = a.getInteger(attr, 1);
                    break;
                case 1:
                    this.verticalN = a.getInteger(attr, 1);
                    break;
                default:
                    break;
            }
        }
        a.recycle();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(width, (int) Math.ceil((double) ((((float) width) * ((float) this.verticalN)) / ((float) this.horizontalN))));
    }
}
