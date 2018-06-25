package org.telegram.messenger;

import java.util.HashMap;

class ContactsController$4 implements Runnable {
    final /* synthetic */ ContactsController this$0;

    ContactsController$4(ContactsController this$0) {
        this.this$0 = this$0;
    }

    public void run() {
        ContactsController.getInstance().performSyncPhoneBook(new HashMap(), true, true, true, true, false, false);
    }
}
