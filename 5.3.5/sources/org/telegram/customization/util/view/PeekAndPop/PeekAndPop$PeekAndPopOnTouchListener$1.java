package org.telegram.customization.util.view.PeekAndPop;

import android.view.View;
import org.telegram.customization.util.view.PeekAndPop.PeekAndPop.PeekAndPopOnTouchListener;

class PeekAndPop$PeekAndPopOnTouchListener$1 implements Runnable {
    final /* synthetic */ PeekAndPopOnTouchListener this$1;
    final /* synthetic */ View val$view;

    PeekAndPop$PeekAndPopOnTouchListener$1(PeekAndPopOnTouchListener this$1, View view) {
        this.this$1 = this$1;
        this.val$view = view;
    }

    public void run() {
        PeekAndPopOnTouchListener.access$402(this.this$1, false);
        this.this$1.this$0.pop(this.val$view, PeekAndPopOnTouchListener.access$500(this.this$1));
        PeekAndPopOnTouchListener.access$602(this.this$1, null);
    }
}
