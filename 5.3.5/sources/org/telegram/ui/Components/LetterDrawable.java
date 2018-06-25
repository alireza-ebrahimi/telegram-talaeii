package org.telegram.ui.Components;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLog;
import org.telegram.ui.ActionBar.Theme;

public class LetterDrawable extends Drawable {
    private static TextPaint namePaint;
    public static Paint paint = new Paint();
    private StringBuilder stringBuilder = new StringBuilder(5);
    private float textHeight;
    private StaticLayout textLayout;
    private float textLeft;
    private float textWidth;

    public LetterDrawable() {
        if (namePaint == null) {
            paint.setColor(Theme.getColor(Theme.key_sharedMedia_linkPlaceholder));
            namePaint = new TextPaint(1);
            namePaint.setColor(Theme.getColor(Theme.key_sharedMedia_linkPlaceholderText));
        }
        namePaint.setTextSize((float) AndroidUtilities.dp(28.0f));
    }

    public void setBackgroundColor(int value) {
        paint.setColor(value);
    }

    public void setColor(int value) {
        namePaint.setColor(value);
    }

    public void setTitle(String title) {
        this.stringBuilder.setLength(0);
        if (title != null && title.length() > 0) {
            this.stringBuilder.append(title.substring(0, 1));
        }
        if (this.stringBuilder.length() > 0) {
            try {
                this.textLayout = new StaticLayout(this.stringBuilder.toString().toUpperCase(), namePaint, AndroidUtilities.dp(100.0f), Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
                if (this.textLayout.getLineCount() > 0) {
                    this.textLeft = this.textLayout.getLineLeft(0);
                    this.textWidth = this.textLayout.getLineWidth(0);
                    this.textHeight = (float) this.textLayout.getLineBottom(0);
                    return;
                }
                return;
            } catch (Exception e) {
                FileLog.e(e);
                return;
            }
        }
        this.textLayout = null;
    }

    public void draw(Canvas canvas) {
        Rect bounds = getBounds();
        if (bounds != null) {
            int size = bounds.width();
            canvas.save();
            canvas.drawRect((float) bounds.left, (float) bounds.top, (float) bounds.right, (float) bounds.bottom, paint);
            if (this.textLayout != null) {
                canvas.translate((((float) bounds.left) + ((((float) size) - this.textWidth) / 2.0f)) - this.textLeft, ((float) bounds.top) + ((((float) size) - this.textHeight) / 2.0f));
                this.textLayout.draw(canvas);
            }
            canvas.restore();
        }
    }

    public void setAlpha(int alpha) {
    }

    public void setColorFilter(ColorFilter cf) {
    }

    public int getOpacity() {
        return -2;
    }

    public int getIntrinsicWidth() {
        return 0;
    }

    public int getIntrinsicHeight() {
        return 0;
    }
}
