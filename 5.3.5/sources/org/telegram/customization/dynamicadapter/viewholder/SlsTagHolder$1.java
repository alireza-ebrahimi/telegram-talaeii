package org.telegram.customization.dynamicadapter.viewholder;

import android.view.View;
import android.view.View.OnClickListener;
import org.telegram.customization.dynamicadapter.data.ObjBase;

class SlsTagHolder$1 implements OnClickListener {
    final /* synthetic */ SlsTagHolder this$0;
    final /* synthetic */ ObjBase val$obj;

    SlsTagHolder$1(SlsTagHolder this$0, ObjBase objBase) {
        this.this$0 = this$0;
        this.val$obj = objBase;
    }

    public void onClick(View v) {
        this.this$0.itemClicked(this.val$obj);
    }
}
