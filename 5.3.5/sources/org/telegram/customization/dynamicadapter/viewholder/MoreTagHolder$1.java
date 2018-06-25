package org.telegram.customization.dynamicadapter.viewholder;

import android.view.View;
import android.view.View.OnClickListener;
import org.telegram.customization.dynamicadapter.data.More;
import org.telegram.customization.dynamicadapter.data.ObjBase;

class MoreTagHolder$1 implements OnClickListener {
    final /* synthetic */ MoreTagHolder this$0;
    final /* synthetic */ ObjBase val$obj;

    MoreTagHolder$1(MoreTagHolder this$0, ObjBase objBase) {
        this.this$0 = this$0;
        this.val$obj = objBase;
    }

    public void onClick(View v) {
        if (((More) this.val$obj).getTagCount() == 9999) {
            MoreTagHolder.access$000(this.this$0);
        } else {
            this.this$0.itemClicked(this.val$obj);
        }
    }
}
