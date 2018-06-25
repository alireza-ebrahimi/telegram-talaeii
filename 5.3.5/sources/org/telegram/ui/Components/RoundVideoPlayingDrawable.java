package org.telegram.ui.Components;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.View;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.ui.ActionBar.Theme;

public class RoundVideoPlayingDrawable extends Drawable {
    private long lastUpdateTime = 0;
    private Paint paint = new Paint(1);
    private View parentView;
    private float progress1 = 0.47f;
    private int progress1Direction = 1;
    private float progress2 = 0.0f;
    private int progress2Direction = 1;
    private float progress3 = 0.32f;
    private int progress3Direction = 1;
    private boolean started = false;

    public RoundVideoPlayingDrawable(View view) {
        this.parentView = view;
    }

    private void update() {
        long newTime = System.currentTimeMillis();
        long dt = newTime - this.lastUpdateTime;
        this.lastUpdateTime = newTime;
        if (dt > 50) {
            dt = 50;
        }
        this.progress1 += (((float) dt) / 300.0f) * ((float) this.progress1Direction);
        if (this.progress1 > 1.0f) {
            this.progress1Direction = -1;
            this.progress1 = 1.0f;
        } else if (this.progress1 < 0.0f) {
            this.progress1Direction = 1;
            this.progress1 = 0.0f;
        }
        this.progress2 += (((float) dt) / 310.0f) * ((float) this.progress2Direction);
        if (this.progress2 > 1.0f) {
            this.progress2Direction = -1;
            this.progress2 = 1.0f;
        } else if (this.progress2 < 0.0f) {
            this.progress2Direction = 1;
            this.progress2 = 0.0f;
        }
        this.progress3 += (((float) dt) / 320.0f) * ((float) this.progress3Direction);
        if (this.progress3 > 1.0f) {
            this.progress3Direction = -1;
            this.progress3 = 1.0f;
        } else if (this.progress3 < 0.0f) {
            this.progress3Direction = 1;
            this.progress3 = 0.0f;
        }
        this.parentView.invalidate();
    }

    public void start() {
        if (!this.started) {
            this.lastUpdateTime = System.currentTimeMillis();
            this.started = true;
            this.parentView.invalidate();
        }
    }

    public void stop() {
        if (this.started) {
            this.started = false;
        }
    }

    public void draw(Canvas canvas) {
        this.paint.setColor(Theme.getColor(Theme.key_chat_mediaTimeText));
        int x = getBounds().left;
        int y = getBounds().top;
        for (int a = 0; a < 3; a++) {
            canvas.drawRect((float) (AndroidUtilities.dp(2.0f) + x), (float) (AndroidUtilities.dp((this.progress1 * 7.0f) + 2.0f) + y), (float) (AndroidUtilities.dp(4.0f) + x), (float) (AndroidUtilities.dp(10.0f) + y), this.paint);
            canvas.drawRect((float) (AndroidUtilities.dp(5.0f) + x), (float) (AndroidUtilities.dp((this.progress2 * 7.0f) + 2.0f) + y), (float) (AndroidUtilities.dp(7.0f) + x), (float) (AndroidUtilities.dp(10.0f) + y), this.paint);
            canvas.drawRect((float) (AndroidUtilities.dp(8.0f) + x), (float) (AndroidUtilities.dp((this.progress3 * 7.0f) + 2.0f) + y), (float) (AndroidUtilities.dp(10.0f) + x), (float) (AndroidUtilities.dp(10.0f) + y), this.paint);
        }
        if (this.started) {
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
        return AndroidUtilities.dp(12.0f);
    }

    public int getIntrinsicHeight() {
        return AndroidUtilities.dp(12.0f);
    }
}
