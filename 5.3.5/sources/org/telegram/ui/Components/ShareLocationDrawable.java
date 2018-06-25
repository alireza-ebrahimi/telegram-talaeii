package org.telegram.ui.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;

public class ShareLocationDrawable extends Drawable {
    private Drawable drawable;
    private Drawable drawableLeft;
    private Drawable drawableRight;
    private boolean isSmall;
    private long lastUpdateTime = 0;
    private float[] progress = new float[]{0.0f, -0.5f};

    public ShareLocationDrawable(Context context, boolean small) {
        this.isSmall = small;
        if (small) {
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
        long newTime = System.currentTimeMillis();
        long dt = newTime - this.lastUpdateTime;
        this.lastUpdateTime = newTime;
        if (dt > 16) {
            dt = 16;
        }
        for (int a = 0; a < 2; a++) {
            if (this.progress[a] >= 1.0f) {
                this.progress[a] = 0.0f;
            }
            float[] fArr = this.progress;
            fArr[a] = fArr[a] + (((float) dt) / 1300.0f);
            if (this.progress[a] > 1.0f) {
                this.progress[a] = 1.0f;
            }
        }
        invalidateSelf();
    }

    public void draw(Canvas canvas) {
        int size = AndroidUtilities.dp(this.isSmall ? 30.0f : 120.0f);
        int y = getBounds().top + ((getIntrinsicHeight() - size) / 2);
        int x = getBounds().left + ((getIntrinsicWidth() - size) / 2);
        this.drawable.setBounds(x, y, this.drawable.getIntrinsicWidth() + x, this.drawable.getIntrinsicHeight() + y);
        this.drawable.draw(canvas);
        for (int a = 0; a < 2; a++) {
            if (this.progress[a] >= 0.0f) {
                float alpha;
                float scale = 0.5f + (0.5f * this.progress[a]);
                int w = AndroidUtilities.dp((this.isSmall ? 2.5f : 5.0f) * scale);
                int h = AndroidUtilities.dp((this.isSmall ? 6.5f : 18.0f) * scale);
                int tx = AndroidUtilities.dp((this.isSmall ? 6.0f : 15.0f) * this.progress[a]);
                if (this.progress[a] < 0.5f) {
                    alpha = this.progress[a] / 0.5f;
                } else {
                    alpha = 1.0f - ((this.progress[a] - 0.5f) / 0.5f);
                }
                int cx = (AndroidUtilities.dp(this.isSmall ? 7.0f : 42.0f) + x) - tx;
                int cy = (y + (this.drawable.getIntrinsicHeight() / 2)) - (this.isSmall ? 0 : AndroidUtilities.dp(7.0f));
                this.drawableLeft.setAlpha((int) (255.0f * alpha));
                this.drawableLeft.setBounds(cx - w, cy - h, cx + w, cy + h);
                this.drawableLeft.draw(canvas);
                cx = ((x + this.drawable.getIntrinsicWidth()) - AndroidUtilities.dp(this.isSmall ? 7.0f : 42.0f)) + tx;
                this.drawableRight.setAlpha((int) (255.0f * alpha));
                this.drawableRight.setBounds(cx - w, cy - h, cx + w, cy + h);
                this.drawableRight.draw(canvas);
            }
        }
        update();
    }

    public void setAlpha(int alpha) {
    }

    public void setColorFilter(ColorFilter cf) {
        this.drawable.setColorFilter(cf);
        this.drawableLeft.setColorFilter(cf);
        this.drawableRight.setColorFilter(cf);
    }

    public int getOpacity() {
        return 0;
    }

    public int getIntrinsicWidth() {
        return AndroidUtilities.dp(this.isSmall ? 40.0f : 120.0f);
    }

    public int getIntrinsicHeight() {
        return AndroidUtilities.dp(this.isSmall ? 40.0f : 180.0f);
    }
}
