package org.telegram.customization.dynamicadapter.viewholder;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import org.telegram.customization.util.Constants;

class SlsSearchImgClickableHolder$2 implements Runnable {
    final /* synthetic */ SlsSearchImgClickableHolder this$0;
    final /* synthetic */ long val$mediaType;
    final /* synthetic */ String val$searchStr;

    SlsSearchImgClickableHolder$2(SlsSearchImgClickableHolder this$0, String str, long j) {
        this.this$0 = this$0;
        this.val$searchStr = str;
        this.val$mediaType = j;
    }

    public void run() {
        Intent intent = new Intent(Constants.ACTION_SEARCH);
        intent.putExtra(utils.view.Constants.EXTRA_SCROLL_TO_TOP, true);
        intent.putExtra(Constants.EXTRA_SEARCH_STRING, this.val$searchStr);
        intent.putExtra(Constants.EXTRA_MEDIA_TYPE, this.val$mediaType);
        LocalBroadcastManager.getInstance(this.this$0.getActivity()).sendBroadcast(intent);
    }
}
