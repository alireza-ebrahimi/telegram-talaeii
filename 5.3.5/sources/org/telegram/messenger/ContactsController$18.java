package org.telegram.messenger;

class ContactsController$18 implements Runnable {
    final /* synthetic */ ContactsController this$0;

    ContactsController$18(ContactsController this$0) {
        this.this$0 = this$0;
    }

    public void run() {
        this.this$0.loadContacts(false, 0);
    }
}
