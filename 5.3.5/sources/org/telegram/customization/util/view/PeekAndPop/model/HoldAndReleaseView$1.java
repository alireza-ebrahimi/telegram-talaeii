package org.telegram.customization.util.view.PeekAndPop.model;

import java.util.TimerTask;
import org.telegram.customization.util.view.PeekAndPop.PeekAndPop;

class HoldAndReleaseView$1 extends TimerTask {
    final /* synthetic */ HoldAndReleaseView this$0;
    final /* synthetic */ PeekAndPop val$peekAndPop;
    final /* synthetic */ int val$position;

    HoldAndReleaseView$1(HoldAndReleaseView this$0, PeekAndPop peekAndPop, int i) {
        this.this$0 = this$0;
        this.val$peekAndPop = peekAndPop;
        this.val$position = i;
    }

    public void run() {
        this.val$peekAndPop.setCurrentHoldAndReleaseView(this.this$0);
        this.val$peekAndPop.triggerOnHoldEvent(HoldAndReleaseView.access$000(this.this$0), this.val$position);
    }
}
