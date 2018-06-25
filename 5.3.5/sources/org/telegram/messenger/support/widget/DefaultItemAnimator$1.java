package org.telegram.messenger.support.widget;

import java.util.ArrayList;
import java.util.Iterator;

class DefaultItemAnimator$1 implements Runnable {
    final /* synthetic */ DefaultItemAnimator this$0;
    final /* synthetic */ ArrayList val$moves;

    DefaultItemAnimator$1(DefaultItemAnimator this$0, ArrayList arrayList) {
        this.this$0 = this$0;
        this.val$moves = arrayList;
    }

    public void run() {
        Iterator it = this.val$moves.iterator();
        while (it.hasNext()) {
            DefaultItemAnimator$MoveInfo moveInfo = (DefaultItemAnimator$MoveInfo) it.next();
            this.this$0.animateMoveImpl(moveInfo.holder, moveInfo.fromX, moveInfo.fromY, moveInfo.toX, moveInfo.toY);
        }
        this.val$moves.clear();
        this.this$0.mMovesList.remove(this.val$moves);
    }
}
