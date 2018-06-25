package org.telegram.ui.Components;

import android.text.TextPaint;
import org.telegram.ui.ActionBar.Theme;

public class URLSpanBotCommand extends URLSpanNoUnderline {
    public static boolean enabled = true;
    public boolean isOut;

    public URLSpanBotCommand(String url, boolean isOutOwner) {
        super(url);
        this.isOut = isOutOwner;
    }

    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        if (this.isOut) {
            ds.setColor(Theme.getColor(enabled ? Theme.key_chat_messageLinkOut : Theme.key_chat_messageTextOut));
        } else {
            ds.setColor(Theme.getColor(enabled ? Theme.key_chat_messageLinkIn : Theme.key_chat_messageTextIn));
        }
        ds.setUnderlineText(false);
    }
}
