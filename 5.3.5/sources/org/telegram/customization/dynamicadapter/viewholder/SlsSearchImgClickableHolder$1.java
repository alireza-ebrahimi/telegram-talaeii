package org.telegram.customization.dynamicadapter.viewholder;

import android.view.View;
import android.view.View.OnClickListener;
import org.telegram.customization.dynamicadapter.data.ObjBase;

class SlsSearchImgClickableHolder$1 implements OnClickListener {
    final /* synthetic */ SlsSearchImgClickableHolder this$0;
    final /* synthetic */ ObjBase val$obj;

    SlsSearchImgClickableHolder$1(SlsSearchImgClickableHolder this$0, ObjBase objBase) {
        this.this$0 = this$0;
        this.val$obj = objBase;
    }

    public void onClick(View v) {
        this.this$0.itemClicked(this.val$obj);
    }
}
