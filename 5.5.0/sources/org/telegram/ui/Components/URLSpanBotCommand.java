package org.telegram.ui.Components;

import android.text.TextPaint;
import org.telegram.ui.ActionBar.Theme;

public class URLSpanBotCommand extends URLSpanNoUnderline {
    public static boolean enabled = true;
    public boolean isOut;

    public URLSpanBotCommand(String str, boolean z) {
        super(str);
        this.isOut = z;
    }

    public void updateDrawState(TextPaint textPaint) {
        super.updateDrawState(textPaint);
        if (this.isOut) {
            textPaint.setColor(Theme.getColor(enabled ? Theme.key_chat_messageLinkOut : Theme.key_chat_messageTextOut));
        } else {
            textPaint.setColor(Theme.getColor(enabled ? Theme.key_chat_messageLinkIn : Theme.key_chat_messageTextIn));
        }
        textPaint.setUnderlineText(false);
    }
}
