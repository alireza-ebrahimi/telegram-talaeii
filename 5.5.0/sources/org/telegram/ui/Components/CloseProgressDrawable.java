package org.telegram.ui.Components;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.drawable.Drawable;
import android.view.animation.DecelerateInterpolator;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;

public class CloseProgressDrawable extends Drawable {
    private int currentAnimationTime;
    private int currentSegment;
    private DecelerateInterpolator interpolator = new DecelerateInterpolator();
    private long lastFrameTime;
    private Paint paint = new Paint(1);

    public CloseProgressDrawable() {
        this.paint.setColor(-9079435);
        this.paint.setStrokeWidth((float) AndroidUtilities.dp(2.0f));
        this.paint.setStrokeCap(Cap.ROUND);
    }

    public void draw(Canvas canvas) {
        long currentTimeMillis = System.currentTimeMillis();
        if (this.lastFrameTime != 0) {
            this.currentAnimationTime = (int) ((currentTimeMillis - this.lastFrameTime) + ((long) this.currentAnimationTime));
            if (this.currentAnimationTime > Callback.DEFAULT_DRAG_ANIMATION_DURATION) {
                this.currentAnimationTime = 0;
                this.currentSegment++;
                if (this.currentSegment == 4) {
                    this.currentSegment -= 4;
                }
            }
        }
        canvas.save();
        canvas.translate((float) (getIntrinsicWidth() / 2), (float) (getIntrinsicHeight() / 2));
        canvas.rotate(45.0f);
        this.paint.setAlpha(255 - ((this.currentSegment % 4) * 40));
        canvas.drawLine((float) (-AndroidUtilities.dp(8.0f)), BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, this.paint);
        this.paint.setAlpha(255 - (((this.currentSegment + 1) % 4) * 40));
        canvas.drawLine(BitmapDescriptorFactory.HUE_RED, (float) (-AndroidUtilities.dp(8.0f)), BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, this.paint);
        this.paint.setAlpha(255 - (((this.currentSegment + 2) % 4) * 40));
        canvas.drawLine(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, (float) AndroidUtilities.dp(8.0f), BitmapDescriptorFactory.HUE_RED, this.paint);
        this.paint.setAlpha(255 - (((this.currentSegment + 3) % 4) * 40));
        canvas.drawLine(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, (float) AndroidUtilities.dp(8.0f), this.paint);
        canvas.restore();
        this.lastFrameTime = currentTimeMillis;
        invalidateSelf();
    }

    public int getIntrinsicHeight() {
        return AndroidUtilities.dp(24.0f);
    }

    public int getIntrinsicWidth() {
        return AndroidUtilities.dp(24.0f);
    }

    public int getOpacity() {
        return -2;
    }

    public void setAlpha(int i) {
    }

    public void setColorFilter(ColorFilter colorFilter) {
    }
}
