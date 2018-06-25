package org.telegram.ui.Components.Paint.Views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.widget.EditText;

public class EditTextOutline extends EditText {
    private Bitmap mCache;
    private final Canvas mCanvas = new Canvas();
    private final TextPaint mPaint = new TextPaint();
    private int mStrokeColor = 0;
    private float mStrokeWidth;
    private boolean mUpdateCachedBitmap = true;

    public EditTextOutline(Context context) {
        super(context);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStyle(Style.FILL_AND_STROKE);
    }

    protected void onTextChanged(CharSequence text, int start, int before, int after) {
        super.onTextChanged(text, start, before, after);
        this.mUpdateCachedBitmap = true;
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w <= 0 || h <= 0) {
            this.mCache = null;
            return;
        }
        this.mUpdateCachedBitmap = true;
        this.mCache = Bitmap.createBitmap(w, h, Config.ARGB_8888);
    }

    public void setStrokeColor(int strokeColor) {
        this.mStrokeColor = strokeColor;
        this.mUpdateCachedBitmap = true;
        invalidate();
    }

    public void setStrokeWidth(float strokeWidth) {
        this.mStrokeWidth = strokeWidth;
        this.mUpdateCachedBitmap = true;
        invalidate();
    }

    protected void onDraw(Canvas canvas) {
        if (!(this.mCache == null || this.mStrokeColor == 0)) {
            if (this.mUpdateCachedBitmap) {
                int w = (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
                int h = getMeasuredHeight();
                String text = getText().toString();
                this.mCanvas.setBitmap(this.mCache);
                this.mCanvas.drawColor(0, Mode.CLEAR);
                this.mPaint.setStrokeWidth(this.mStrokeWidth > 0.0f ? this.mStrokeWidth : (float) Math.ceil((double) (getTextSize() / 11.5f)));
                this.mPaint.setColor(this.mStrokeColor);
                this.mPaint.setTextSize(getTextSize());
                this.mPaint.setTypeface(getTypeface());
                this.mPaint.setStyle(Style.FILL_AND_STROKE);
                StaticLayout sl = new StaticLayout(text, this.mPaint, w, Alignment.ALIGN_CENTER, 1.0f, 0.0f, true);
                this.mCanvas.save();
                this.mCanvas.translate((float) getPaddingLeft(), ((float) getPaddingTop()) + (((float) (((h - getPaddingTop()) - getPaddingBottom()) - sl.getHeight())) / 2.0f));
                sl.draw(this.mCanvas);
                this.mCanvas.restore();
                this.mUpdateCachedBitmap = false;
            }
            canvas.drawBitmap(this.mCache, 0.0f, 0.0f, this.mPaint);
        }
        super.onDraw(canvas);
    }
}
