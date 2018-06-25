package org.telegram.customization.dynamicadapter.viewholder;

import org.telegram.customization.dynamicadapter.data.SlsTag;
import org.telegram.messenger.MessagesController;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.LaunchActivity;
import utils.view.collectionpicker.Item;
import utils.view.collectionpicker.OnItemClickListener;

class SlsTagCollectionHolder$2 implements OnItemClickListener {
    final /* synthetic */ SlsTagCollectionHolder this$0;

    SlsTagCollectionHolder$2(SlsTagCollectionHolder this$0) {
        this.this$0 = this$0;
    }

    public void onClick(Item item, int position) {
        if (this.this$0.tags != null && this.this$0.tags.size() > position) {
            if (((SlsTag) this.this$0.tags.get(position)).isChannel()) {
                MessagesController.openByUserName(((SlsTag) this.this$0.tags.get(position)).getUsername(), (BaseFragment) LaunchActivity.mainFragmentsStack.get(LaunchActivity.mainFragmentsStack.size() - 1), 1);
            } else {
                SlsTagCollectionHolder.access$100(this.this$0, ((SlsTag) this.this$0.tags.get(position)).getId());
            }
        }
    }
}
