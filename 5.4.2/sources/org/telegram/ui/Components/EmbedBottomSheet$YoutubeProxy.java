package org.telegram.ui.Components;

import android.webkit.JavascriptInterface;
import org.telegram.messenger.AndroidUtilities;

class EmbedBottomSheet$YoutubeProxy {
    final /* synthetic */ EmbedBottomSheet this$0;

    private EmbedBottomSheet$YoutubeProxy(EmbedBottomSheet embedBottomSheet) {
        this.this$0 = embedBottomSheet;
    }

    @JavascriptInterface
    public void postEvent(String str, String str2) {
        AndroidUtilities.runOnUIThread(new EmbedBottomSheet$YoutubeProxy$1(this, str));
    }
}
