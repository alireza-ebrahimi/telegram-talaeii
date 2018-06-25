package org.telegram.ui.Components;

import android.content.Context;
import android.text.Layout;
import android.widget.TextView;

public class CorrectlyMeasuringTextView extends TextView {
    public CorrectlyMeasuringTextView(Context context) {
        super(context);
    }

    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        try {
            Layout layout = getLayout();
            if (layout.getLineCount() > 1) {
                int i3 = 0;
                for (int lineCount = layout.getLineCount() - 1; lineCount >= 0; lineCount--) {
                    i3 = Math.max(i3, Math.round(layout.getPaint().measureText(getText(), layout.getLineStart(lineCount), layout.getLineEnd(lineCount))));
                }
                super.onMeasure(Math.min((getPaddingLeft() + i3) + getPaddingRight(), getMeasuredWidth()) | 1073741824, getMeasuredHeight() | 1073741824);
            }
        } catch (Exception e) {
        }
    }
}
