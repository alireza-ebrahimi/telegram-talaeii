package org.telegram.customization.dynamicadapter.viewholder;

import android.view.View;
import android.view.View.OnClickListener;

class SlsTagCollectionHolder$1 implements OnClickListener {
    final /* synthetic */ SlsTagCollectionHolder this$0;

    SlsTagCollectionHolder$1(SlsTagCollectionHolder this$0) {
        this.this$0 = this$0;
    }

    public void onClick(View v) {
        this.this$0.setCollapse(!this.this$0.isCollapse());
        SlsTagCollectionHolder.access$000(this.this$0);
    }
}
