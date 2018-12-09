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

    public HeightImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public HeightImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    protected void onMeasure(int i, int i2) {
        Drawable drawable = getDrawable();
        if (drawable != null) {
            int size = MeasureSpec.getSize(i2);
            setMeasuredDimension((int) Math.ceil((double) ((((float) size) * ((float) drawable.getIntrinsicWidth())) / ((float) drawable.getIntrinsicHeight()))), size);
            return;
        }
        super.onMeasure(i, i2);
    }
}
