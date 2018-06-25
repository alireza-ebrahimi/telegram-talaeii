package org.telegram.customization.fetch;

class FetchService$1 implements Runnable {
    final /* synthetic */ FetchService this$0;

    FetchService$1(FetchService this$0) {
        this.this$0 = this$0;
    }

    public void run() {
        FetchService.access$000(this.this$0).clean();
        FetchService.access$000(this.this$0).verifyOK();
    }
}
