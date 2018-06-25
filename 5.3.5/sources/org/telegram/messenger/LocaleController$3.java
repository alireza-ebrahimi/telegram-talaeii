package org.telegram.messenger;

class LocaleController$3 implements Runnable {
    final /* synthetic */ LocaleController this$0;

    LocaleController$3(LocaleController this$0) {
        this.this$0 = this$0;
    }

    public void run() {
        this.this$0.reloadCurrentRemoteLocale();
    }
}
