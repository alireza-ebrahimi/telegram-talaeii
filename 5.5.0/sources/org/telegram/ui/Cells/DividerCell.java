package org.telegram.ui.Cells;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import android.view.View.MeasureSpec;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.ui.ActionBar.Theme;

public class DividerCell extends View {
    public DividerCell(Context context) {
        super(context);
    }

    protected void onDraw(Canvas canvas) {
        canvas.drawLine((float) getPaddingLeft(), (float) AndroidUtilities.dp(8.0f), (float) (getWidth() - getPaddingRight()), (float) AndroidUtilities.dp(8.0f), Theme.dividerPaint);
    }

    protected void onMeasure(int i, int i2) {
        setMeasuredDimension(MeasureSpec.getSize(i), AndroidUtilities.dp(16.0f) + 1);
    }
}
