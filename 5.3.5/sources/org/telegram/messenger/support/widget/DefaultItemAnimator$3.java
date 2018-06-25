package org.telegram.messenger.support.widget;

import java.util.ArrayList;
import java.util.Iterator;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;

class DefaultItemAnimator$3 implements Runnable {
    final /* synthetic */ DefaultItemAnimator this$0;
    final /* synthetic */ ArrayList val$additions;

    DefaultItemAnimator$3(DefaultItemAnimator this$0, ArrayList arrayList) {
        this.this$0 = this$0;
        this.val$additions = arrayList;
    }

    public void run() {
        Iterator it = this.val$additions.iterator();
        while (it.hasNext()) {
            this.this$0.animateAddImpl((ViewHolder) it.next());
        }
        this.val$additions.clear();
        this.this$0.mAdditionsList.remove(this.val$additions);
    }
}
