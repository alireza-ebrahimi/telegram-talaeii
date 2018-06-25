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

    public URLSpanMono(CharSequence message, int start, int end, boolean out) {
        this.currentMessage = message;
        this.currentStart = start;
        this.currentEnd = end;
    }

    public void copyToClipboard() {
        AndroidUtilities.addToClipboard(this.currentMessage.subSequence(this.currentStart, this.currentEnd).toString());
    }

    public void updateMeasureState(TextPaint p) {
        p.setTypeface(Typeface.MONOSPACE);
        p.setTextSize((float) AndroidUtilities.dp((float) (MessagesController.getInstance().fontSize - 1)));
        p.setFlags(p.getFlags() | 128);
    }

    public void updateDrawState(TextPaint ds) {
        ds.setTextSize((float) AndroidUtilities.dp((float) (MessagesController.getInstance().fontSize - 1)));
        ds.setTypeface(Typeface.MONOSPACE);
        ds.setUnderlineText(false);
        if (this.isOut) {
            ds.setColor(Theme.getColor(Theme.key_chat_messageTextOut));
        } else {
            ds.setColor(Theme.getColor(Theme.key_chat_messageTextIn));
        }
    }
}
