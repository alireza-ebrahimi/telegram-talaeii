package org.telegram.ui.Components;

import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;
import org.telegram.messenger.AndroidUtilities;

public class TypefaceSpan extends MetricAffectingSpan {
    private int color;
    private int textSize;
    private Typeface typeface;

    public TypefaceSpan(Typeface typeface) {
        this.typeface = typeface;
    }

    public TypefaceSpan(Typeface typeface, int i) {
        this.typeface = typeface;
        this.textSize = i;
    }

    public TypefaceSpan(Typeface typeface, int i, int i2) {
        this.typeface = typeface;
        this.textSize = i;
        this.color = i2;
    }

    public Typeface getTypeface() {
        return this.typeface;
    }

    public boolean isBold() {
        return this.typeface == AndroidUtilities.getTypeface("fonts/rmedium.ttf");
    }

    public boolean isItalic() {
        return this.typeface == AndroidUtilities.getTypeface("fonts/ritalic.ttf");
    }

    public void updateDrawState(TextPaint textPaint) {
        if (this.typeface != null) {
            textPaint.setTypeface(this.typeface);
        }
        if (this.textSize != 0) {
            textPaint.setTextSize((float) this.textSize);
        }
        if (this.color != 0) {
            textPaint.setColor(this.color);
        }
        textPaint.setFlags(textPaint.getFlags() | 128);
    }

    public void updateMeasureState(TextPaint textPaint) {
        if (this.typeface != null) {
            textPaint.setTypeface(this.typeface);
        }
        if (this.textSize != 0) {
            textPaint.setTextSize((float) this.textSize);
        }
        textPaint.setFlags(textPaint.getFlags() | 128);
    }
}
