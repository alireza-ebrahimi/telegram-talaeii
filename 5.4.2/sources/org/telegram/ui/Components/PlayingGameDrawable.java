package org.telegram.ui.Components;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.animation.DecelerateInterpolator;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
    class C45611 implements Runnable {
        C45611() {
        }

        public void run() {
            PlayingGameDrawable.this.checkUpdate();
        }
    }

    private void checkUpdate() {
        if (!this.started) {
            return;
        }
        if (NotificationCenter.getInstance().isAnimationInProgress()) {
            AndroidUtilities.runOnUIThread(new C45611(), 100);
        } else {
            update();
        }
    }

    private void update() {
        long j = 16;
        long currentTimeMillis = System.currentTimeMillis();
        long j2 = currentTimeMillis - this.lastUpdateTime;
        this.lastUpdateTime = currentTimeMillis;
        if (j2 <= 16) {
            j = j2;
        }
        if (this.progress >= 1.0f) {
            this.progress = BitmapDescriptorFactory.HUE_RED;
        }
        this.progress = (((float) j) / 300.0f) + this.progress;
        if (this.progress > 1.0f) {
            this.progress = 1.0f;
        }
        invalidateSelf();
    }

    public void draw(Canvas canvas) {
        int dp = AndroidUtilities.dp(10.0f);
        int intrinsicHeight = getBounds().top + ((getIntrinsicHeight() - dp) / 2);
        int dp2 = this.isChat ? intrinsicHeight : intrinsicHeight + AndroidUtilities.dp(1.0f);
        this.paint.setColor(Theme.getColor(Theme.key_actionBarDefaultSubtitle));
        this.rect.set(BitmapDescriptorFactory.HUE_RED, (float) dp2, (float) dp, (float) (dp2 + dp));
        intrinsicHeight = this.progress < 0.5f ? (int) ((1.0f - (this.progress / 0.5f)) * 35.0f) : (int) (((this.progress - 0.5f) * 35.0f) / 0.5f);
        for (int i = 0; i < 3; i++) {
            float dp3 = ((float) ((AndroidUtilities.dp(5.0f) * i) + AndroidUtilities.dp(9.2f))) - (((float) AndroidUtilities.dp(5.0f)) * this.progress);
            if (i == 2) {
                this.paint.setAlpha(Math.min(255, (int) ((255.0f * this.progress) / 0.5f)));
            } else if (i != 0) {
                this.paint.setAlpha(255);
            } else if (this.progress > 0.5f) {
                this.paint.setAlpha((int) (255.0f * (1.0f - ((this.progress - 0.5f) / 0.5f))));
            } else {
                this.paint.setAlpha(255);
            }
            canvas.drawCircle(dp3, (float) ((dp / 2) + dp2), (float) AndroidUtilities.dp(1.2f), this.paint);
        }
        this.paint.setAlpha(255);
        canvas.drawArc(this.rect, (float) intrinsicHeight, (float) (360 - (intrinsicHeight * 2)), true, this.paint);
        this.paint.setColor(Theme.getColor(Theme.key_actionBarDefault));
        canvas.drawCircle((float) AndroidUtilities.dp(4.0f), (float) (((dp / 2) + dp2) - AndroidUtilities.dp(2.0f)), (float) AndroidUtilities.dp(1.0f), this.paint);
        checkUpdate();
    }

    public int getIntrinsicHeight() {
        return AndroidUtilities.dp(18.0f);
    }

    public int getIntrinsicWidth() {
        return AndroidUtilities.dp(20.0f);
    }

    public int getOpacity() {
        return 0;
    }

    public void setAlpha(int i) {
    }

    public void setColorFilter(ColorFilter colorFilter) {
    }

    public void setIsChat(boolean z) {
        this.isChat = z;
    }

    public void start() {
        this.lastUpdateTime = System.currentTimeMillis();
        this.started = true;
        invalidateSelf();
    }

    public void stop() {
        this.progress = BitmapDescriptorFactory.HUE_RED;
        this.started = false;
    }
}
