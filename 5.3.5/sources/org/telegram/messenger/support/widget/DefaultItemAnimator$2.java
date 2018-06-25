package org.telegram.messenger.support.widget;

import java.util.ArrayList;
import java.util.Iterator;

class DefaultItemAnimator$2 implements Runnable {
    final /* synthetic */ DefaultItemAnimator this$0;
    final /* synthetic */ ArrayList val$changes;

    DefaultItemAnimator$2(DefaultItemAnimator this$0, ArrayList arrayList) {
        this.this$0 = this$0;
        this.val$changes = arrayList;
    }

    public void run() {
        Iterator it = this.val$changes.iterator();
        while (it.hasNext()) {
            this.this$0.animateChangeImpl((DefaultItemAnimator$ChangeInfo) it.next());
        }
        this.val$changes.clear();
        this.this$0.mChangesList.remove(this.val$changes);
    }
}
