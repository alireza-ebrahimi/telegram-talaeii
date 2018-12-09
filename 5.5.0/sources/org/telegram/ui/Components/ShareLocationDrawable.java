package org.telegram.ui.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;

public class ShareLocationDrawable extends Drawable {
    private Drawable drawable;
    private Drawable drawableLeft;
    private Drawable drawableRight;
    private boolean isSmall;
    private long lastUpdateTime = 0;
    private float[] progress = new float[]{BitmapDescriptorFactory.HUE_RED, -0.5f};

    public ShareLocationDrawable(Context context, boolean z) {
        this.isSmall = z;
        if (z) {
            this.drawable = context.getResources().getDrawable(R.drawable.smallanimationpin);
            this.drawableLeft = context.getResources().getDrawable(R.drawable.smallanimationpinleft);
            this.drawableRight = context.getResources().getDrawable(R.drawable.smallanimationpinright);
            return;
        }
        this.drawable = context.getResources().getDrawable(R.drawable.animationpin);
        this.drawableLeft = context.getResources().getDrawable(R.drawable.animationpinleft);
        this.drawableRight = context.getResources().getDrawable(R.drawable.animationpinright);
    }

    private void update() {
        long j = 16;
        long currentTimeMillis = System.currentTimeMillis();
        long j2 = currentTimeMillis - this.lastUpdateTime;
        this.lastUpdateTime = currentTimeMillis;
        if (j2 <= 16) {
            j = j2;
        }
        for (int i = 0; i < 2; i++) {
            if (this.progress[i] >= 1.0f) {
                this.progress[i] = BitmapDescriptorFactory.HUE_RED;
            }
            float[] fArr = this.progress;
            fArr[i] = fArr[i] + (((float) j) / 1300.0f);
            if (this.progress[i] > 1.0f) {
                this.progress[i] = 1.0f;
            }
        }
        invalidateSelf();
    }

    public void draw(Canvas canvas) {
        int dp = AndroidUtilities.dp(this.isSmall ? 30.0f : 120.0f);
        int intrinsicHeight = getBounds().top + ((getIntrinsicHeight() - dp) / 2);
        int intrinsicWidth = getBounds().left + ((getIntrinsicWidth() - dp) / 2);
        this.drawable.setBounds(intrinsicWidth, intrinsicHeight, this.drawable.getIntrinsicWidth() + intrinsicWidth, this.drawable.getIntrinsicHeight() + intrinsicHeight);
        this.drawable.draw(canvas);
        for (int i = 0; i < 2; i++) {
            if (this.progress[i] >= BitmapDescriptorFactory.HUE_RED) {
                float f = (0.5f * this.progress[i]) + 0.5f;
                int dp2 = AndroidUtilities.dp((this.isSmall ? 2.5f : 5.0f) * f);
                int dp3 = AndroidUtilities.dp((this.isSmall ? 6.5f : 18.0f) * f);
                int dp4 = AndroidUtilities.dp((this.isSmall ? 6.0f : 15.0f) * this.progress[i]);
                float f2 = this.progress[i] < 0.5f ? this.progress[i] / 0.5f : 1.0f - ((this.progress[i] - 0.5f) / 0.5f);
                int dp5 = (AndroidUtilities.dp(this.isSmall ? 7.0f : 42.0f) + intrinsicWidth) - dp4;
                int intrinsicHeight2 = (intrinsicHeight + (this.drawable.getIntrinsicHeight() / 2)) - (this.isSmall ? 0 : AndroidUtilities.dp(7.0f));
                this.drawableLeft.setAlpha((int) (255.0f * f2));
                this.drawableLeft.setBounds(dp5 - dp2, intrinsicHeight2 - dp3, dp5 + dp2, intrinsicHeight2 + dp3);
                this.drawableLeft.draw(canvas);
                int intrinsicWidth2 = ((intrinsicWidth + this.drawable.getIntrinsicWidth()) - AndroidUtilities.dp(this.isSmall ? 7.0f : 42.0f)) + dp4;
                this.drawableRight.setAlpha((int) (f2 * 255.0f));
                this.drawableRight.setBounds(intrinsicWidth2 - dp2, intrinsicHeight2 - dp3, intrinsicWidth2 + dp2, intrinsicHeight2 + dp3);
                this.drawableRight.draw(canvas);
            }
        }
        update();
    }

    public int getIntrinsicHeight() {
        return AndroidUtilities.dp(this.isSmall ? 40.0f : 180.0f);
    }

    public int getIntrinsicWidth() {
        return AndroidUtilities.dp(this.isSmall ? 40.0f : 120.0f);
    }

    public int getOpacity() {
        return 0;
    }

    public void setAlpha(int i) {
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.drawable.setColorFilter(colorFilter);
        this.drawableLeft.setColorFilter(colorFilter);
        this.drawableRight.setColorFilter(colorFilter);
    }
}
