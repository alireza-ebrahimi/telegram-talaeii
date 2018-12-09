package org.telegram.ui.Components;

import android.text.TextPaint;
import org.telegram.ui.ActionBar.Theme;

public class URLSpanUserMention extends URLSpanNoUnderline {
    private boolean isOut;

    public URLSpanUserMention(String str, boolean z) {
        super(str);
        this.isOut = z;
    }

    public void updateDrawState(TextPaint textPaint) {
        super.updateDrawState(textPaint);
        if (this.isOut) {
            textPaint.setColor(Theme.getColor(Theme.key_chat_messageLinkOut));
        } else {
            textPaint.setColor(Theme.getColor(Theme.key_chat_messageLinkIn));
        }
        textPaint.setUnderlineText(false);
    }
}
