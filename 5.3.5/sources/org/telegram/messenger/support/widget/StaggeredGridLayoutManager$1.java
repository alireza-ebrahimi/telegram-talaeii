package org.telegram.messenger.support.widget;

class StaggeredGridLayoutManager$1 implements Runnable {
    final /* synthetic */ StaggeredGridLayoutManager this$0;

    StaggeredGridLayoutManager$1(StaggeredGridLayoutManager this$0) {
        this.this$0 = this$0;
    }

    public void run() {
        this.this$0.checkForGaps();
    }
}
