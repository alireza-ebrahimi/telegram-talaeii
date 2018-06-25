package org.telegram.customization.Internet;

import com.android.volley.VolleyError;

class HandleRequest$2 extends Thread {
    final /* synthetic */ HandleRequest this$0;
    final /* synthetic */ VolleyError val$volleyError;

    HandleRequest$2(HandleRequest this$0, VolleyError volleyError) {
        this.this$0 = this$0;
        this.val$volleyError = volleyError;
    }

    public void run() {
        super.run();
        this.this$0.onErrorResponseInternal(this.val$volleyError);
    }
}
