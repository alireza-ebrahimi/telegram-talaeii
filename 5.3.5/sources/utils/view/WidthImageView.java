package utils.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.ImageView;

public class WidthImageView extends ImageView {
    public WidthImageView(Context context) {
        super(context);
    }

    public WidthImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WidthImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable d = getDrawable();
        if (d != null) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            setMeasuredDimension(width, (int) Math.ceil((double) ((((float) width) * ((float) d.getIntrinsicHeight())) / ((float) d.getIntrinsicWidth()))));
            return;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
