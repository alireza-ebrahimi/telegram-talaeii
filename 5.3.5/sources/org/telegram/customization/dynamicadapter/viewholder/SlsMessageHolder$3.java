package org.telegram.customization.dynamicadapter.viewholder;

import android.view.View;
import android.view.View.OnClickListener;
import org.telegram.customization.dynamicadapter.data.ObjBase;
import org.telegram.customization.dynamicadapter.data.SlsBaseMessage;

class SlsMessageHolder$3 implements OnClickListener {
    final /* synthetic */ SlsMessageHolder this$0;
    final /* synthetic */ ObjBase val$obj;

    SlsMessageHolder$3(SlsMessageHolder this$0, ObjBase objBase) {
        this.this$0 = this$0;
        this.val$obj = objBase;
    }

    public void onClick(View v) {
        if (SlsBaseMessage.isMediaAvailable(this.val$obj)) {
            SlsMessageHolder.access$200(this.this$0);
        } else {
            this.this$0.itemClicked(this.val$obj);
        }
    }
}
