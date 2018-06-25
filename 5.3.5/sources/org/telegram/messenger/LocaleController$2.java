package org.telegram.messenger;

class LocaleController$2 implements Runnable {
    final /* synthetic */ LocaleController this$0;
    final /* synthetic */ LocaleController$LocaleInfo val$localeInfo;

    LocaleController$2(LocaleController this$0, LocaleController$LocaleInfo localeController$LocaleInfo) {
        this.this$0 = this$0;
        this.val$localeInfo = localeController$LocaleInfo;
    }

    public void run() {
        LocaleController.access$100(this.this$0, this.val$localeInfo, null, true);
    }
}
