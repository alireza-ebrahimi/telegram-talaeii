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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

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

    protected void onDraw(Canvas canvas) {
        if (!(this.mCache == null || this.mStrokeColor == 0)) {
            if (this.mUpdateCachedBitmap) {
                int measuredWidth = (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
                int measuredHeight = getMeasuredHeight();
                CharSequence obj = getText().toString();
                this.mCanvas.setBitmap(this.mCache);
                this.mCanvas.drawColor(0, Mode.CLEAR);
                this.mPaint.setStrokeWidth(this.mStrokeWidth > BitmapDescriptorFactory.HUE_RED ? this.mStrokeWidth : (float) Math.ceil((double) (getTextSize() / 11.5f)));
                this.mPaint.setColor(this.mStrokeColor);
                this.mPaint.setTextSize(getTextSize());
                this.mPaint.setTypeface(getTypeface());
                this.mPaint.setStyle(Style.FILL_AND_STROKE);
                StaticLayout staticLayout = new StaticLayout(obj, this.mPaint, measuredWidth, Alignment.ALIGN_CENTER, 1.0f, BitmapDescriptorFactory.HUE_RED, true);
                this.mCanvas.save();
                this.mCanvas.translate((float) getPaddingLeft(), (((float) (((measuredHeight - getPaddingTop()) - getPaddingBottom()) - staticLayout.getHeight())) / 2.0f) + ((float) getPaddingTop()));
                staticLayout.draw(this.mCanvas);
                this.mCanvas.restore();
                this.mUpdateCachedBitmap = false;
            }
            canvas.drawBitmap(this.mCache, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, this.mPaint);
        }
        super.onDraw(canvas);
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (i <= 0 || i2 <= 0) {
            this.mCache = null;
            return;
        }
        this.mUpdateCachedBitmap = true;
        this.mCache = Bitmap.createBitmap(i, i2, Config.ARGB_8888);
    }

    protected void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        super.onTextChanged(charSequence, i, i2, i3);
        this.mUpdateCachedBitmap = true;
    }

    public void setStrokeColor(int i) {
        this.mStrokeColor = i;
        this.mUpdateCachedBitmap = true;
        invalidate();
    }

    public void setStrokeWidth(float f) {
        this.mStrokeWidth = f;
        this.mUpdateCachedBitmap = true;
        invalidate();
    }
}
