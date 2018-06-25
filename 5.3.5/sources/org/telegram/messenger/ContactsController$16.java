package org.telegram.messenger;

import java.util.ArrayList;

class ContactsController$16 implements Runnable {
    final /* synthetic */ ContactsController this$0;
    final /* synthetic */ ArrayList val$contactsArray;

    ContactsController$16(ContactsController this$0, ArrayList arrayList) {
        this.this$0 = this$0;
        this.val$contactsArray = arrayList;
    }

    public void run() {
        ContactsController.access$1700(this.this$0, this.val$contactsArray);
    }
}
