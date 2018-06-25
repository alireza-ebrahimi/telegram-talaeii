package org.telegram.customization.util.view.PeekAndPop;

import android.view.View;
import java.util.TimerTask;
import org.telegram.customization.util.view.PeekAndPop.PeekAndPop.PeekAndPopOnTouchListener;

class PeekAndPop$PeekAndPopOnTouchListener$2 extends TimerTask {
    final /* synthetic */ PeekAndPopOnTouchListener this$1;
    final /* synthetic */ View val$view;

    /* renamed from: org.telegram.customization.util.view.PeekAndPop.PeekAndPop$PeekAndPopOnTouchListener$2$1 */
    class C12491 implements Runnable {
        C12491() {
        }

        public void run() {
            if (PeekAndPopOnTouchListener.access$400(PeekAndPop$PeekAndPopOnTouchListener$2.this.this$1)) {
                PeekAndPop$PeekAndPopOnTouchListener$2.this.this$1.this$0.peek(PeekAndPop$PeekAndPopOnTouchListener$2.this.val$view, PeekAndPopOnTouchListener.access$500(PeekAndPop$PeekAndPopOnTouchListener$2.this.this$1));
                PeekAndPopOnTouchListener.access$602(PeekAndPop$PeekAndPopOnTouchListener$2.this.this$1, null);
            }
        }
    }

    PeekAndPop$PeekAndPopOnTouchListener$2(PeekAndPopOnTouchListener this$1, View view) {
        this.this$1 = this$1;
        this.val$view = view;
    }

    public void run() {
        PeekAndPopOnTouchListener.access$402(this.this$1, true);
        PeekAndPopOnTouchListener.access$602(this.this$1, new C12491());
        this.this$1.this$0.builder.activity.runOnUiThread(PeekAndPopOnTouchListener.access$600(this.this$1));
    }
}
