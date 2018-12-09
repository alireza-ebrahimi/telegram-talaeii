package org.telegram.ui.Cells;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.ui.Components.LayoutHelper;

public class TextColorThemeCell extends FrameLayout {
    private static Paint colorPaint;
    private float alpha = 1.0f;
    private int currentColor;
    private boolean needDivider;
    private TextView textView;

    public TextColorThemeCell(Context context) {
        int i = 5;
        super(context);
        if (colorPaint == null) {
            colorPaint = new Paint(1);
        }
        this.textView = new TextView(context);
        this.textView.setTextColor(-14606047);
        this.textView.setTextSize(1, 16.0f);
        this.textView.setLines(1);
        this.textView.setMaxLines(1);
        this.textView.setSingleLine(true);
        this.textView.setGravity((LocaleController.isRTL ? 5 : 3) | 16);
        this.textView.setPadding(0, 0, 0, AndroidUtilities.dp(3.0f));
        View view = this.textView;
        if (!LocaleController.isRTL) {
            i = 3;
        }
        addView(view, LayoutHelper.createFrame(-1, -1.0f, i | 48, (float) (LocaleController.isRTL ? 17 : 53), BitmapDescriptorFactory.HUE_RED, (float) (LocaleController.isRTL ? 53 : 17), BitmapDescriptorFactory.HUE_RED));
    }

    public float getAlpha() {
        return this.alpha;
    }

    protected void onDraw(Canvas canvas) {
        if (this.currentColor != 0) {
            colorPaint.setColor(this.currentColor);
            colorPaint.setAlpha((int) (255.0f * this.alpha));
            canvas.drawCircle(!LocaleController.isRTL ? (float) AndroidUtilities.dp(28.0f) : (float) (getMeasuredWidth() - AndroidUtilities.dp(28.0f)), (float) (getMeasuredHeight() / 2), (float) AndroidUtilities.dp(10.0f), colorPaint);
        }
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(i), 1073741824), MeasureSpec.makeMeasureSpec((this.needDivider ? 1 : 0) + AndroidUtilities.dp(48.0f), 1073741824));
    }

    public void setAlpha(float f) {
        this.alpha = f;
        invalidate();
    }

    public void setTextAndColor(String str, int i) {
        this.textView.setText(str);
        this.currentColor = i;
        boolean z = !this.needDivider && this.currentColor == 0;
        setWillNotDraw(z);
        invalidate();
    }
}
