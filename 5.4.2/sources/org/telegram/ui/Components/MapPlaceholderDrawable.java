package org.telegram.ui.Components;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import org.telegram.messenger.AndroidUtilities;

public class MapPlaceholderDrawable extends Drawable {
    private Paint linePaint;
    private Paint paint = new Paint();

    public MapPlaceholderDrawable() {
        this.paint.setColor(-2172970);
        this.linePaint = new Paint();
        this.linePaint.setColor(-3752002);
        this.linePaint.setStrokeWidth((float) AndroidUtilities.dp(1.0f));
    }

    public void draw(Canvas canvas) {
        int i = 0;
        canvas.drawRect(getBounds(), this.paint);
        int dp = AndroidUtilities.dp(9.0f);
        int width = getBounds().width() / dp;
        int height = getBounds().height() / dp;
        int i2 = getBounds().left;
        int i3 = getBounds().top;
        for (int i4 = 0; i4 < width; i4++) {
            canvas.drawLine((float) (((i4 + 1) * dp) + i2), (float) i3, (float) (((i4 + 1) * dp) + i2), (float) (getBounds().height() + i3), this.linePaint);
        }
        while (i < height) {
            canvas.drawLine((float) i2, (float) (((i + 1) * dp) + i3), (float) (getBounds().width() + i2), (float) (((i + 1) * dp) + i3), this.linePaint);
            i++;
        }
    }

    public int getIntrinsicHeight() {
        return 0;
    }

    public int getIntrinsicWidth() {
        return 0;
    }

    public int getOpacity() {
        return 0;
    }

    public void setAlpha(int i) {
    }

    public void setColorFilter(ColorFilter colorFilter) {
    }
}
