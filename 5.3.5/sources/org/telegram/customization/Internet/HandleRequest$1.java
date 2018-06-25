package org.telegram.customization.Internet;

class HandleRequest$1 implements Runnable {
    final /* synthetic */ HandleRequest this$0;
    final /* synthetic */ int val$StatusCode;
    final /* synthetic */ Object val$object;

    HandleRequest$1(HandleRequest this$0, Object obj, int i) {
        this.this$0 = this$0;
        this.val$object = obj;
        this.val$StatusCode = i;
    }

    public void run() {
        HandleRequest.access$000(this.this$0).onResult(this.val$object, this.val$StatusCode);
    }
}
