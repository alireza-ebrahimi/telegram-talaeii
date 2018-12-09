package org.telegram.ui.Components;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.RectF;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.ui.ActionBar.Theme;

public class RecordStatusDrawable extends StatusDrawable {
    private boolean isChat = false;
    private long lastUpdateTime = 0;
    private float progress;
    private RectF rect = new RectF();
    private boolean started = false;

    private void update() {
        long j = 50;
        long currentTimeMillis = System.currentTimeMillis();
        long j2 = currentTimeMillis - this.lastUpdateTime;
        this.lastUpdateTime = currentTimeMillis;
        if (j2 <= 50) {
            j = j2;
        }
        this.progress = (((float) j) / 300.0f) + this.progress;
        while (this.progress > 1.0f) {
            this.progress -= 1.0f;
        }
        invalidateSelf();
    }

    public void draw(Canvas canvas) {
        canvas.save();
        canvas.translate(BitmapDescriptorFactory.HUE_RED, (float) (AndroidUtilities.dp(this.isChat ? 1.0f : 2.0f) + (getIntrinsicHeight() / 2)));
        for (int i = 0; i < 4; i++) {
            if (i == 0) {
                Theme.chat_statusRecordPaint.setAlpha((int) (this.progress * 255.0f));
            } else if (i == 3) {
                Theme.chat_statusRecordPaint.setAlpha((int) ((1.0f - this.progress) * 255.0f));
            } else {
                Theme.chat_statusRecordPaint.setAlpha(255);
            }
            float dp = ((float) (AndroidUtilities.dp(4.0f) * i)) + (((float) AndroidUtilities.dp(4.0f)) * this.progress);
            this.rect.set(-dp, -dp, dp, dp);
            canvas.drawArc(this.rect, -15.0f, 30.0f, false, Theme.chat_statusRecordPaint);
        }
        canvas.restore();
        if (this.started) {
            update();
        }
    }

    public int getIntrinsicHeight() {
        return AndroidUtilities.dp(14.0f);
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
        this.started = false;
    }
}
