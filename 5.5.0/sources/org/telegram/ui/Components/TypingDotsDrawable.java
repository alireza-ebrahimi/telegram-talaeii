package org.telegram.ui.Components;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.view.animation.DecelerateInterpolator;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.NotificationCenter;
import org.telegram.ui.ActionBar.Theme;

public class TypingDotsDrawable extends StatusDrawable {
    private DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator();
    private float[] elapsedTimes = new float[]{BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED};
    private boolean isChat = false;
    private long lastUpdateTime = 0;
    private float[] scales = new float[3];
    private float[] startTimes = new float[]{BitmapDescriptorFactory.HUE_RED, 150.0f, 300.0f};
    private boolean started = false;

    /* renamed from: org.telegram.ui.Components.TypingDotsDrawable$1 */
    class C46381 implements Runnable {
        C46381() {
        }

        public void run() {
            TypingDotsDrawable.this.checkUpdate();
        }
    }

    private void checkUpdate() {
        if (!this.started) {
            return;
        }
        if (NotificationCenter.getInstance().isAnimationInProgress()) {
            AndroidUtilities.runOnUIThread(new C46381(), 100);
        } else {
            update();
        }
    }

    private void update() {
        long j = 50;
        long currentTimeMillis = System.currentTimeMillis();
        long j2 = currentTimeMillis - this.lastUpdateTime;
        this.lastUpdateTime = currentTimeMillis;
        if (j2 <= 50) {
            j = j2;
        }
        for (int i = 0; i < 3; i++) {
            float[] fArr = this.elapsedTimes;
            fArr[i] = fArr[i] + ((float) j);
            float f = this.elapsedTimes[i] - this.startTimes[i];
            if (f <= BitmapDescriptorFactory.HUE_RED) {
                this.scales[i] = 1.33f;
            } else if (f <= 320.0f) {
                this.scales[i] = this.decelerateInterpolator.getInterpolation(f / 320.0f) + 1.33f;
            } else if (f <= 640.0f) {
                this.scales[i] = (1.0f - this.decelerateInterpolator.getInterpolation((f - 320.0f) / 320.0f)) + 1.33f;
            } else if (f >= 800.0f) {
                this.elapsedTimes[i] = BitmapDescriptorFactory.HUE_RED;
                this.startTimes[i] = BitmapDescriptorFactory.HUE_RED;
                this.scales[i] = 1.33f;
            } else {
                this.scales[i] = 1.33f;
            }
        }
        invalidateSelf();
    }

    public void draw(Canvas canvas) {
        int dp = this.isChat ? AndroidUtilities.dp(8.5f) + getBounds().top : AndroidUtilities.dp(9.3f) + getBounds().top;
        Theme.chat_statusPaint.setAlpha(255);
        canvas.drawCircle((float) AndroidUtilities.dp(3.0f), (float) dp, this.scales[0] * AndroidUtilities.density, Theme.chat_statusPaint);
        canvas.drawCircle((float) AndroidUtilities.dp(9.0f), (float) dp, this.scales[1] * AndroidUtilities.density, Theme.chat_statusPaint);
        canvas.drawCircle((float) AndroidUtilities.dp(15.0f), (float) dp, this.scales[2] * AndroidUtilities.density, Theme.chat_statusPaint);
        checkUpdate();
    }

    public int getIntrinsicHeight() {
        return AndroidUtilities.dp(18.0f);
    }

    public int getIntrinsicWidth() {
        return AndroidUtilities.dp(18.0f);
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
        for (int i = 0; i < 3; i++) {
            this.elapsedTimes[i] = BitmapDescriptorFactory.HUE_RED;
            this.scales[i] = 1.33f;
        }
        this.startTimes[0] = BitmapDescriptorFactory.HUE_RED;
        this.startTimes[1] = 150.0f;
        this.startTimes[2] = 300.0f;
        this.started = false;
    }
}
