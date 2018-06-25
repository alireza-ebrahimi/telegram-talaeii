package org.telegram.customization.Internet;

class HandleRequest$3 extends Thread {
    final /* synthetic */ HandleRequest this$0;
    final /* synthetic */ String val$response;

    HandleRequest$3(HandleRequest this$0, String str) {
        this.this$0 = this$0;
        this.val$response = str;
    }

    public void run() {
        super.run();
        this.this$0.onResponseInternal(this.val$response);
    }
}
