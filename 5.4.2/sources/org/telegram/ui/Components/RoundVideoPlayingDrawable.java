package org.telegram.ui.Components;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.ui.ActionBar.Theme;

public class RoundVideoPlayingDrawable extends Drawable {
    private long lastUpdateTime = 0;
    private Paint paint = new Paint(1);
    private View parentView;
    private float progress1 = 0.47f;
    private int progress1Direction = 1;
    private float progress2 = BitmapDescriptorFactory.HUE_RED;
    private int progress2Direction = 1;
    private float progress3 = 0.32f;
    private int progress3Direction = 1;
    private boolean started = false;

    public RoundVideoPlayingDrawable(View view) {
        this.parentView = view;
    }

    private void update() {
        long j = 50;
        long currentTimeMillis = System.currentTimeMillis();
        long j2 = currentTimeMillis - this.lastUpdateTime;
        this.lastUpdateTime = currentTimeMillis;
        if (j2 <= 50) {
            j = j2;
        }
        this.progress1 += (((float) j) / 300.0f) * ((float) this.progress1Direction);
        if (this.progress1 > 1.0f) {
            this.progress1Direction = -1;
            this.progress1 = 1.0f;
        } else if (this.progress1 < BitmapDescriptorFactory.HUE_RED) {
            this.progress1Direction = 1;
            this.progress1 = BitmapDescriptorFactory.HUE_RED;
        }
        this.progress2 += (((float) j) / 310.0f) * ((float) this.progress2Direction);
        if (this.progress2 > 1.0f) {
            this.progress2Direction = -1;
            this.progress2 = 1.0f;
        } else if (this.progress2 < BitmapDescriptorFactory.HUE_RED) {
            this.progress2Direction = 1;
            this.progress2 = BitmapDescriptorFactory.HUE_RED;
        }
        this.progress3 = ((((float) j) / 320.0f) * ((float) this.progress3Direction)) + this.progress3;
        if (this.progress3 > 1.0f) {
            this.progress3Direction = -1;
            this.progress3 = 1.0f;
        } else if (this.progress3 < BitmapDescriptorFactory.HUE_RED) {
            this.progress3Direction = 1;
            this.progress3 = BitmapDescriptorFactory.HUE_RED;
        }
        this.parentView.invalidate();
    }

    public void draw(Canvas canvas) {
        this.paint.setColor(Theme.getColor(Theme.key_chat_mediaTimeText));
        int i = getBounds().left;
        int i2 = getBounds().top;
        for (int i3 = 0; i3 < 3; i3++) {
            canvas.drawRect((float) (AndroidUtilities.dp(2.0f) + i), (float) (AndroidUtilities.dp((this.progress1 * 7.0f) + 2.0f) + i2), (float) (AndroidUtilities.dp(4.0f) + i), (float) (AndroidUtilities.dp(10.0f) + i2), this.paint);
            canvas.drawRect((float) (AndroidUtilities.dp(5.0f) + i), (float) (AndroidUtilities.dp((this.progress2 * 7.0f) + 2.0f) + i2), (float) (AndroidUtilities.dp(7.0f) + i), (float) (AndroidUtilities.dp(10.0f) + i2), this.paint);
            canvas.drawRect((float) (AndroidUtilities.dp(8.0f) + i), (float) (AndroidUtilities.dp((this.progress3 * 7.0f) + 2.0f) + i2), (float) (AndroidUtilities.dp(10.0f) + i), (float) (AndroidUtilities.dp(10.0f) + i2), this.paint);
        }
        if (this.started) {
            update();
        }
    }

    public int getIntrinsicHeight() {
        return AndroidUtilities.dp(12.0f);
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
}
