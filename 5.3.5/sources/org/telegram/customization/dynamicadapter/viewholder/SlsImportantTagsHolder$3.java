package org.telegram.customization.dynamicadapter.viewholder;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import org.telegram.customization.util.Constants;

class SlsImportantTagsHolder$3 implements Runnable {
    final /* synthetic */ SlsImportantTagsHolder this$0;

    SlsImportantTagsHolder$3(SlsImportantTagsHolder this$0) {
        this.this$0 = this$0;
    }

    public void run() {
        Intent intent = new Intent(Constants.ACTION_SET_MEDIA_TYPE);
        intent.putExtra(Constants.EXTRA_MEDIA_TYPE, 8);
        intent.putExtra(Constants.EXTRA_MEDIA_TYPE_IN_HOT, true);
        LocalBroadcastManager.getInstance(this.this$0.getActivity()).sendBroadcast(intent);
    }
}
