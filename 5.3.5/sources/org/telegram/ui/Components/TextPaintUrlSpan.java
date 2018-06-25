package org.telegram.ui.Components;

import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

public class TextPaintUrlSpan extends MetricAffectingSpan {
    private int color;
    private String currentUrl;
    private TextPaint textPaint;
    private int textSize;

    public TextPaintUrlSpan(TextPaint paint, String url) {
        this.textPaint = paint;
        this.currentUrl = url;
    }

    public String getUrl() {
        return this.currentUrl;
    }

    public void updateMeasureState(TextPaint p) {
        if (this.textPaint != null) {
            p.setColor(this.textPaint.getColor());
            p.setTypeface(this.textPaint.getTypeface());
            p.setFlags(this.textPaint.getFlags());
        }
    }

    public void updateDrawState(TextPaint p) {
        if (this.textPaint != null) {
            p.setColor(this.textPaint.getColor());
            p.setTypeface(this.textPaint.getTypeface());
            p.setFlags(this.textPaint.getFlags());
        }
    }
}
