package org.telegram.ui.Components;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.ui.ActionBar.Theme;

public class RoundStatusDrawable extends StatusDrawable {
    private boolean isChat = false;
    private long lastUpdateTime = 0;
    private float progress;
    private int progressDirection = 1;
    private boolean started = false;

    private void update() {
        long j = 50;
        long currentTimeMillis = System.currentTimeMillis();
        long j2 = currentTimeMillis - this.lastUpdateTime;
        this.lastUpdateTime = currentTimeMillis;
        if (j2 <= 50) {
            j = j2;
        }
        this.progress = (((float) (j * ((long) this.progressDirection))) / 400.0f) + this.progress;
        if (this.progressDirection > 0 && this.progress >= 1.0f) {
            this.progressDirection = -1;
            this.progress = 1.0f;
        } else if (this.progressDirection < 0 && this.progress <= BitmapDescriptorFactory.HUE_RED) {
            this.progressDirection = 1;
            this.progress = BitmapDescriptorFactory.HUE_RED;
        }
        invalidateSelf();
    }

    public void draw(Canvas canvas) {
        Theme.chat_statusPaint.setAlpha(((int) (200.0f * this.progress)) + 55);
        canvas.drawCircle((float) AndroidUtilities.dp(6.0f), (float) AndroidUtilities.dp(this.isChat ? 8.0f : 9.0f), (float) AndroidUtilities.dp(4.0f), Theme.chat_statusPaint);
        if (this.started) {
            update();
        }
    }

    public int getIntrinsicHeight() {
        return AndroidUtilities.dp(10.0f);
    }

    public int getIntrinsicWidth() {
        return AndroidUtilities.dp(12.0f);
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
        this.started = false;
    }
}
