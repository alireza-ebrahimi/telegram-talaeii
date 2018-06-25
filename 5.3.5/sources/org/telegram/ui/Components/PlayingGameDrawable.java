package org.telegram.ui.Components;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.animation.DecelerateInterpolator;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.NotificationCenter;
import org.telegram.ui.ActionBar.Theme;

public class PlayingGameDrawable extends StatusDrawable {
    private DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator();
    private boolean isChat = false;
    private long lastUpdateTime = 0;
    private Paint paint = new Paint(1);
    private float progress;
    private RectF rect = new RectF();
    private boolean started = false;

    /* renamed from: org.telegram.ui.Components.PlayingGameDrawable$1 */
    class C27231 implements Runnable {
        C27231() {
        }

        public void run() {
            PlayingGameDrawable.this.checkUpdate();
        }
    }

    public void setIsChat(boolean value) {
        this.isChat = value;
    }

    private void update() {
        long newTime = System.currentTimeMillis();
        long dt = newTime - this.lastUpdateTime;
        this.lastUpdateTime = newTime;
        if (dt > 16) {
            dt = 16;
        }
        if (this.progress >= 1.0f) {
            this.progress = 0.0f;
        }
        this.progress += ((float) dt) / 300.0f;
        if (this.progress > 1.0f) {
            this.progress = 1.0f;
        }
        invalidateSelf();
    }

    public void start() {
        this.lastUpdateTime = System.currentTimeMillis();
        this.started = true;
        invalidateSelf();
    }

    public void stop() {
        this.progress = 0.0f;
        this.started = false;
    }

    public void draw(Canvas canvas) {
        int rad;
        int size = AndroidUtilities.dp(10.0f);
        int y = getBounds().top + ((getIntrinsicHeight() - size) / 2);
        if (!this.isChat) {
            y += AndroidUtilities.dp(1.0f);
        }
        this.paint.setColor(Theme.getColor(Theme.key_actionBarDefaultSubtitle));
        this.rect.set(0.0f, (float) y, (float) size, (float) (y + size));
        if (this.progress < 0.5f) {
            rad = (int) (35.0f * (1.0f - (this.progress / 0.5f)));
        } else {
            rad = (int) ((35.0f * (this.progress - 0.5f)) / 0.5f);
        }
        for (int a = 0; a < 3; a++) {
            float x = ((float) ((AndroidUtilities.dp(5.0f) * a) + AndroidUtilities.dp(9.2f))) - (((float) AndroidUtilities.dp(5.0f)) * this.progress);
            if (a == 2) {
                this.paint.setAlpha(Math.min(255, (int) ((255.0f * this.progress) / 0.5f)));
            } else if (a != 0) {
                this.paint.setAlpha(255);
            } else if (this.progress > 0.5f) {
                this.paint.setAlpha((int) (255.0f * (1.0f - ((this.progress - 0.5f) / 0.5f))));
            } else {
                this.paint.setAlpha(255);
            }
            canvas.drawCircle(x, (float) ((size / 2) + y), (float) AndroidUtilities.dp(1.2f), this.paint);
        }
        this.paint.setAlpha(255);
        canvas.drawArc(this.rect, (float) rad, (float) (360 - (rad * 2)), true, this.paint);
        this.paint.setColor(Theme.getColor(Theme.key_actionBarDefault));
        canvas.drawCircle((float) AndroidUtilities.dp(4.0f), (float) (((size / 2) + y) - AndroidUtilities.dp(2.0f)), (float) AndroidUtilities.dp(1.0f), this.paint);
        checkUpdate();
    }

    private void checkUpdate() {
        if (!this.started) {
            return;
        }
        if (NotificationCenter.getInstance().isAnimationInProgress()) {
            AndroidUtilities.runOnUIThread(new C27231(), 100);
        } else {
            update();
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
        return AndroidUtilities.dp(20.0f);
    }

    public int getIntrinsicHeight() {
        return AndroidUtilities.dp(18.0f);
    }
}
