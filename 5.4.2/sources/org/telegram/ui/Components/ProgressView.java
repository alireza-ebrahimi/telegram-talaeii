package org.telegram.ui.Components;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.telegram.messenger.AndroidUtilities;

public class ProgressView {
    public float currentProgress = BitmapDescriptorFactory.HUE_RED;
    public int height;
    private Paint innerPaint = new Paint();
    private Paint outerPaint = new Paint();
    public float progressHeight = ((float) AndroidUtilities.dp(2.0f));
    public int width;

    public void draw(Canvas canvas) {
        Canvas canvas2 = canvas;
        canvas2.drawRect(BitmapDescriptorFactory.HUE_RED, ((float) (this.height / 2)) - (this.progressHeight / 2.0f), (float) this.width, (this.progressHeight / 2.0f) + ((float) (this.height / 2)), this.innerPaint);
        canvas2 = canvas;
        canvas2.drawRect(BitmapDescriptorFactory.HUE_RED, ((float) (this.height / 2)) - (this.progressHeight / 2.0f), this.currentProgress * ((float) this.width), (this.progressHeight / 2.0f) + ((float) (this.height / 2)), this.outerPaint);
    }

    public void setProgress(float f) {
        this.currentProgress = f;
        if (this.currentProgress < BitmapDescriptorFactory.HUE_RED) {
            this.currentProgress = BitmapDescriptorFactory.HUE_RED;
        } else if (this.currentProgress > 1.0f) {
            this.currentProgress = 1.0f;
        }
    }

    public void setProgressColors(int i, int i2) {
        this.innerPaint.setColor(i);
        this.outerPaint.setColor(i2);
    }
}
