package org.telegram.ui.Components;

import android.content.Context;
import android.text.Layout;
import android.widget.TextView;

public class CorrectlyMeasuringTextView extends TextView {
    public CorrectlyMeasuringTextView(Context context) {
        super(context);
    }

    public void onMeasure(int wms, int hms) {
        super.onMeasure(wms, hms);
        try {
            Layout l = getLayout();
            if (l.getLineCount() > 1) {
                int maxw = 0;
                for (int i = l.getLineCount() - 1; i >= 0; i--) {
                    maxw = Math.max(maxw, Math.round(l.getPaint().measureText(getText(), l.getLineStart(i), l.getLineEnd(i))));
                }
                super.onMeasure(Math.min((getPaddingLeft() + maxw) + getPaddingRight(), getMeasuredWidth()) | 1073741824, getMeasuredHeight() | 1073741824);
            }
        } catch (Exception e) {
        }
    }
}
