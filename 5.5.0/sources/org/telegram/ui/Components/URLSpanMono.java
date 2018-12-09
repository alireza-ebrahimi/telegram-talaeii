package org.telegram.ui.Components;

import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.MessagesController;
import org.telegram.ui.ActionBar.Theme;

public class URLSpanMono extends MetricAffectingSpan {
    private int currentEnd;
    private CharSequence currentMessage;
    private int currentStart;
    private boolean isOut;

    public URLSpanMono(CharSequence charSequence, int i, int i2, boolean z) {
        this.currentMessage = charSequence;
        this.currentStart = i;
        this.currentEnd = i2;
    }

    public void copyToClipboard() {
        AndroidUtilities.addToClipboard(this.currentMessage.subSequence(this.currentStart, this.currentEnd).toString());
    }

    public void updateDrawState(TextPaint textPaint) {
        textPaint.setTextSize((float) AndroidUtilities.dp((float) (MessagesController.getInstance().fontSize - 1)));
        textPaint.setTypeface(Typeface.MONOSPACE);
        textPaint.setUnderlineText(false);
        if (this.isOut) {
            textPaint.setColor(Theme.getColor(Theme.key_chat_messageTextOut));
        } else {
            textPaint.setColor(Theme.getColor(Theme.key_chat_messageTextIn));
        }
    }

    public void updateMeasureState(TextPaint textPaint) {
        textPaint.setTypeface(Typeface.MONOSPACE);
        textPaint.setTextSize((float) AndroidUtilities.dp((float) (MessagesController.getInstance().fontSize - 1)));
        textPaint.setFlags(textPaint.getFlags() | 128);
    }
}
