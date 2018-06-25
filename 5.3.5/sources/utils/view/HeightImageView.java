package utils.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.ImageView;

public class HeightImageView extends ImageView {
    public HeightImageView(Context context) {
        super(context);
    }

    public HeightImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeightImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable d = getDrawable();
        if (d != null) {
            int height = MeasureSpec.getSize(heightMeasureSpec);
            setMeasuredDimension((int) Math.ceil((double) ((((float) height) * ((float) d.getIntrinsicWidth())) / ((float) d.getIntrinsicHeight()))), height);
            return;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
