package org.telegram.ui.Components;

import android.text.TextPaint;
import org.telegram.ui.ActionBar.Theme;

public class URLSpanUserMention extends URLSpanNoUnderline {
    private boolean isOut;

    public URLSpanUserMention(String url, boolean isOutOwner) {
        super(url);
        this.isOut = isOutOwner;
    }

    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        if (this.isOut) {
            ds.setColor(Theme.getColor(Theme.key_chat_messageLinkOut));
        } else {
            ds.setColor(Theme.getColor(Theme.key_chat_messageLinkIn));
        }
        ds.setUnderlineText(false);
    }
}
