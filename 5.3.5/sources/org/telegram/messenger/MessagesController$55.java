package org.telegram.messenger;

import java.util.HashMap;

class MessagesController$55 implements Runnable {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ HashMap val$newPrintingStrings;
    final /* synthetic */ HashMap val$newPrintingStringsTypes;

    MessagesController$55(MessagesController this$0, HashMap hashMap, HashMap hashMap2) {
        this.this$0 = this$0;
        this.val$newPrintingStrings = hashMap;
        this.val$newPrintingStringsTypes = hashMap2;
    }

    public void run() {
        this.this$0.printingStrings = this.val$newPrintingStrings;
        this.this$0.printingStringsTypes = this.val$newPrintingStringsTypes;
    }
}
