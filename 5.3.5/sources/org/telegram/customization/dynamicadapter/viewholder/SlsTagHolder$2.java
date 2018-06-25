package org.telegram.customization.dynamicadapter.viewholder;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import org.telegram.customization.util.Constants;

class SlsTagHolder$2 implements Runnable {
    final /* synthetic */ SlsTagHolder this$0;
    final /* synthetic */ long val$id;
    final /* synthetic */ String val$showName;

    SlsTagHolder$2(SlsTagHolder this$0, long j, String str) {
        this.this$0 = this$0;
        this.val$id = j;
        this.val$showName = str;
    }

    public void run() {
        Intent intent = new Intent(Constants.ACTION_SET_TAG_ID);
        intent.putExtra("EXTRA_TAG_ID", this.val$id);
        intent.putExtra(Constants.EXTRA_TAG_NAME, this.val$showName);
        intent.putExtra(utils.view.Constants.EXTRA_POOL_ID, this.this$0.getExtraData().getPoolId());
        LocalBroadcastManager.getInstance(this.this$0.getActivity()).sendBroadcast(intent);
    }
}
