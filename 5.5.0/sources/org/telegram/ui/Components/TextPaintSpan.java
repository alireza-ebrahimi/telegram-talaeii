package org.telegram.ui.Components;

import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

public class TextPaintSpan extends MetricAffectingSpan {
    private int color;
    private TextPaint textPaint;
    private int textSize;

    public TextPaintSpan(TextPaint textPaint) {
        this.textPaint = textPaint;
    }

    public void updateDrawState(TextPaint textPaint) {
        textPaint.setColor(this.textPaint.getColor());
        textPaint.setTypeface(this.textPaint.getTypeface());
        textPaint.setFlags(this.textPaint.getFlags());
    }

    public void updateMeasureState(TextPaint textPaint) {
        textPaint.setColor(this.textPaint.getColor());
        textPaint.setTypeface(this.textPaint.getTypeface());
        textPaint.setFlags(this.textPaint.getFlags());
    }
}
