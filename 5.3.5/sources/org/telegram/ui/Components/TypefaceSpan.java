package org.telegram.ui.Components;

import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;
import org.telegram.messenger.AndroidUtilities;

public class TypefaceSpan extends MetricAffectingSpan {
    private int color;
    private int textSize;
    private Typeface typeface;

    public TypefaceSpan(Typeface tf) {
        this.typeface = tf;
    }

    public TypefaceSpan(Typeface tf, int size) {
        this.typeface = tf;
        this.textSize = size;
    }

    public TypefaceSpan(Typeface tf, int size, int textColor) {
        this.typeface = tf;
        this.textSize = size;
        this.color = textColor;
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

    public void updateMeasureState(TextPaint p) {
        if (this.typeface != null) {
            p.setTypeface(this.typeface);
        }
        if (this.textSize != 0) {
            p.setTextSize((float) this.textSize);
        }
        p.setFlags(p.getFlags() | 128);
    }

    public void updateDrawState(TextPaint tp) {
        if (this.typeface != null) {
            tp.setTypeface(this.typeface);
        }
        if (this.textSize != 0) {
            tp.setTextSize((float) this.textSize);
        }
        if (this.color != 0) {
            tp.setColor(this.color);
        }
        tp.setFlags(tp.getFlags() | 128);
    }
}
