package org.telegram.ui.Components;

import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

public class TextPaintUrlSpan extends MetricAffectingSpan {
    private int color;
    private String currentUrl;
    private TextPaint textPaint;
    private int textSize;

    public TextPaintUrlSpan(TextPaint textPaint, String str) {
        this.textPaint = textPaint;
        this.currentUrl = str;
    }

    public String getUrl() {
        return this.currentUrl;
    }

    public void updateDrawState(TextPaint textPaint) {
        if (this.textPaint != null) {
            textPaint.setColor(this.textPaint.getColor());
            textPaint.setTypeface(this.textPaint.getTypeface());
            textPaint.setFlags(this.textPaint.getFlags());
        }
    }

    public void updateMeasureState(TextPaint textPaint) {
        if (this.textPaint != null) {
            textPaint.setColor(this.textPaint.getColor());
            textPaint.setTypeface(this.textPaint.getTypeface());
            textPaint.setFlags(this.textPaint.getFlags());
        }
    }
}
