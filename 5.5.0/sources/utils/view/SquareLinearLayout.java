package utils.view;

import android.view.View.MeasureSpec;
import android.widget.RelativeLayout;

public class SquareLinearLayout extends RelativeLayout {
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i);
        int size = MeasureSpec.getSize(i);
        setMeasuredDimension(size, size);
    }
}
