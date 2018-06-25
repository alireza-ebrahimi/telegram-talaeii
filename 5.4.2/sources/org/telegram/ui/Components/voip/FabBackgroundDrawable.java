package org.telegram.ui.Components.voip;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.telegram.messenger.AndroidUtilities;

public class FabBackgroundDrawable extends Drawable {
    private Paint bgPaint = new Paint(1);
    private Bitmap shadowBitmap;
    private Paint shadowPaint = new Paint();

    public FabBackgroundDrawable() {
        this.shadowPaint.setColor(1275068416);
    }

    public void draw(Canvas canvas) {
        if (this.shadowBitmap == null) {
            onBoundsChange(getBounds());
        }
        int min = Math.min(getBounds().width(), getBounds().height());
        if (this.shadowBitmap != null) {
            canvas.drawBitmap(this.shadowBitmap, (float) (getBounds().centerX() - (this.shadowBitmap.getWidth() / 2)), (float) (getBounds().centerY() - (this.shadowBitmap.getHeight() / 2)), this.shadowPaint);
        }
        canvas.drawCircle((float) (min / 2), (float) (min / 2), (float) ((min / 2) - AndroidUtilities.dp(4.0f)), this.bgPaint);
    }

    public int getOpacity() {
        return 0;
    }

    public boolean getPadding(Rect rect) {
        int dp = AndroidUtilities.dp(4.0f);
        rect.set(dp, dp, dp, dp);
        return true;
    }

    protected void onBoundsChange(Rect rect) {
        int min = Math.min(rect.width(), rect.height());
        if (min <= 0) {
            this.shadowBitmap = null;
            return;
        }
        this.shadowBitmap = Bitmap.createBitmap(min, min, Config.ALPHA_8);
        Canvas canvas = new Canvas(this.shadowBitmap);
        Paint paint = new Paint(1);
        paint.setShadowLayer((float) AndroidUtilities.dp(3.33333f), BitmapDescriptorFactory.HUE_RED, (float) AndroidUtilities.dp(0.666f), -1);
        canvas.drawCircle((float) (min / 2), (float) (min / 2), (float) ((min / 2) - AndroidUtilities.dp(4.0f)), paint);
    }

    public void setAlpha(int i) {
    }

    public void setColor(int i) {
        this.bgPaint.setColor(i);
        invalidateSelf();
    }

    public void setColorFilter(ColorFilter colorFilter) {
    }
}
