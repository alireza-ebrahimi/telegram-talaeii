package org.telegram.ui.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLog;
import org.telegram.ui.ActionBar.Theme;

public class TimerDrawable extends Drawable {
    private Paint linePaint = new Paint(1);
    private Paint paint = new Paint(1);
    private int time = 0;
    private int timeHeight = 0;
    private StaticLayout timeLayout;
    private TextPaint timePaint = new TextPaint(1);
    private float timeWidth = 0.0f;

    public TimerDrawable(Context context) {
        this.timePaint.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.timePaint.setTextSize((float) AndroidUtilities.dp(11.0f));
        this.linePaint.setStrokeWidth((float) AndroidUtilities.dp(1.0f));
        this.linePaint.setStyle(Style.STROKE);
    }

    public void setTime(int value) {
        String timeString;
        this.time = value;
        if (this.time >= 1 && this.time < 60) {
            timeString = "" + value;
            if (timeString.length() < 2) {
                timeString = timeString + "s";
            }
        } else if (this.time >= 60 && this.time < 3600) {
            timeString = "" + (value / 60);
            if (timeString.length() < 2) {
                timeString = timeString + "m";
            }
        } else if (this.time >= 3600 && this.time < 86400) {
            timeString = "" + ((value / 60) / 60);
            if (timeString.length() < 2) {
                timeString = timeString + "h";
            }
        } else if (this.time < 86400 || this.time >= 604800) {
            timeString = "" + ((((value / 60) / 60) / 24) / 7);
            if (timeString.length() < 2) {
                timeString = timeString + "w";
            } else if (timeString.length() > 2) {
                timeString = "c";
            }
        } else {
            timeString = "" + (((value / 60) / 60) / 24);
            if (timeString.length() < 2) {
                timeString = timeString + "d";
            }
        }
        this.timeWidth = this.timePaint.measureText(timeString);
        try {
            this.timeLayout = new StaticLayout(timeString, this.timePaint, (int) Math.ceil((double) this.timeWidth), Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
            this.timeHeight = this.timeLayout.getHeight();
        } catch (Exception e) {
            this.timeLayout = null;
            FileLog.e(e);
        }
        invalidateSelf();
    }

    public void draw(Canvas canvas) {
        int width = getIntrinsicWidth();
        int height = getIntrinsicHeight();
        if (this.time == 0) {
            this.paint.setColor(Theme.getColor(Theme.key_chat_secretTimerBackground));
            this.linePaint.setColor(Theme.getColor(Theme.key_chat_secretTimerText));
            canvas.drawCircle(AndroidUtilities.dpf2(9.0f), AndroidUtilities.dpf2(9.0f), AndroidUtilities.dpf2(7.5f), this.paint);
            canvas.drawCircle(AndroidUtilities.dpf2(9.0f), AndroidUtilities.dpf2(9.0f), AndroidUtilities.dpf2(8.0f), this.linePaint);
            this.paint.setColor(Theme.getColor(Theme.key_chat_secretTimerText));
            canvas.drawLine((float) AndroidUtilities.dp(9.0f), (float) AndroidUtilities.dp(9.0f), (float) AndroidUtilities.dp(13.0f), (float) AndroidUtilities.dp(9.0f), this.linePaint);
            canvas.drawLine((float) AndroidUtilities.dp(9.0f), (float) AndroidUtilities.dp(5.0f), (float) AndroidUtilities.dp(9.0f), (float) AndroidUtilities.dp(9.5f), this.linePaint);
            canvas.drawRect(AndroidUtilities.dpf2(7.0f), AndroidUtilities.dpf2(0.0f), AndroidUtilities.dpf2(11.0f), AndroidUtilities.dpf2(1.5f), this.paint);
        } else {
            this.paint.setColor(Theme.getColor(Theme.key_chat_secretTimerBackground));
            this.timePaint.setColor(Theme.getColor(Theme.key_chat_secretTimerText));
            canvas.drawCircle((float) AndroidUtilities.dp(9.5f), (float) AndroidUtilities.dp(9.5f), (float) AndroidUtilities.dp(9.5f), this.paint);
        }
        if (this.time != 0 && this.timeLayout != null) {
            int xOffxet = 0;
            if (AndroidUtilities.density == 3.0f) {
                xOffxet = -1;
            }
            canvas.translate((float) (((int) (((double) (width / 2)) - Math.ceil((double) (this.timeWidth / 2.0f)))) + xOffxet), (float) ((height - this.timeHeight) / 2));
            this.timeLayout.draw(canvas);
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
        return AndroidUtilities.dp(19.0f);
    }

    public int getIntrinsicHeight() {
        return AndroidUtilities.dp(19.0f);
    }
}
