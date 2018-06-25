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
        int a;
        canvas.drawRect(getBounds(), this.paint);
        int gap = AndroidUtilities.dp(9.0f);
        int xcount = getBounds().width() / gap;
        int ycount = getBounds().height() / gap;
        int x = getBounds().left;
        int y = getBounds().top;
        for (a = 0; a < xcount; a++) {
            canvas.drawLine((float) (((a + 1) * gap) + x), (float) y, (float) (((a + 1) * gap) + x), (float) (getBounds().height() + y), this.linePaint);
        }
        for (a = 0; a < ycount; a++) {
            canvas.drawLine((float) x, (float) (((a + 1) * gap) + y), (float) (getBounds().width() + x), (float) (((a + 1) * gap) + y), this.linePaint);
        }
    }

    public void setAlpha(int alpha) {
    }

    public void setColorFilter(ColorFilter cf) {
    }

    public int getOpacity() {
        return 0;
    }

    public int getIntrinsicWidth() {
        return 0;
    }

    public int getIntrinsicHeight() {
        return 0;
    }
}
