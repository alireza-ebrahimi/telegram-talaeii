package org.telegram.messenger;

class LocaleController$1 implements Runnable {
    final /* synthetic */ LocaleController this$0;

    LocaleController$1(LocaleController this$0) {
        this.this$0 = this$0;
    }

    public void run() {
        this.this$0.loadRemoteLanguages();
    }
}
