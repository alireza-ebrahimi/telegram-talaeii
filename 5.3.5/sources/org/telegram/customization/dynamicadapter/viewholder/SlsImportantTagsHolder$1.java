package org.telegram.customization.dynamicadapter.viewholder;

import android.view.View;
import android.view.View.OnClickListener;
import java.util.ArrayList;
import org.telegram.customization.dynamicadapter.data.SlsTag;

class SlsImportantTagsHolder$1 implements OnClickListener {
    final /* synthetic */ SlsImportantTagsHolder this$0;
    final /* synthetic */ ArrayList val$tags;

    SlsImportantTagsHolder$1(SlsImportantTagsHolder this$0, ArrayList arrayList) {
        this.this$0 = this$0;
        this.val$tags = arrayList;
    }

    public void onClick(View v) {
        SlsImportantTagsHolder.access$000(this.this$0, ((SlsTag) this.val$tags.get(0)).getId());
    }
}
