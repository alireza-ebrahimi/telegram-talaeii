package org.telegram.customization.Internet;

class HandleRequest$5 extends Thread {
    final /* synthetic */ HandleRequest this$0;
    final /* synthetic */ HandleRequest$HandleInterface val$hi;

    HandleRequest$5(HandleRequest this$0, HandleRequest$HandleInterface handleRequest$HandleInterface) {
        this.this$0 = this$0;
        this.val$hi = handleRequest$HandleInterface;
    }

    public void run() {
        super.run();
        HandleRequest.access$200(this.this$0, this.val$hi);
    }
}
