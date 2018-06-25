package org.telegram.customization.util.view.PeekAndPop.model;

import java.util.TimerTask;
import org.telegram.customization.util.view.PeekAndPop.PeekAndPop;

class LongHoldView$1 extends TimerTask {
    final /* synthetic */ LongHoldView this$0;
    final /* synthetic */ long val$duration;
    final /* synthetic */ PeekAndPop val$peekAndPop;
    final /* synthetic */ int val$position;

    LongHoldView$1(LongHoldView this$0, PeekAndPop peekAndPop, int i, long j) {
        this.this$0 = this$0;
        this.val$peekAndPop = peekAndPop;
        this.val$position = i;
        this.val$duration = j;
    }

    public void run() {
        this.val$peekAndPop.sendOnLongHoldEvent(LongHoldView.access$000(this.this$0), this.val$position);
        if (LongHoldView.access$100(this.this$0)) {
            this.this$0.startLongHoldViewTimer(this.val$peekAndPop, this.val$position, this.val$duration);
        }
    }
}
