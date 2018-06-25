package org.telegram.messenger;

import java.util.HashMap;

class ContactsController$5 implements Runnable {
    final /* synthetic */ ContactsController this$0;
    final /* synthetic */ boolean val$cancel;
    final /* synthetic */ HashMap val$contacts;
    final /* synthetic */ boolean val$first;
    final /* synthetic */ boolean val$schedule;

    ContactsController$5(ContactsController this$0, HashMap hashMap, boolean z, boolean z2, boolean z3) {
        this.this$0 = this$0;
        this.val$contacts = hashMap;
        this.val$first = z;
        this.val$schedule = z2;
        this.val$cancel = z3;
    }

    public void run() {
        ContactsController.getInstance().performSyncPhoneBook(this.val$contacts, true, this.val$first, this.val$schedule, false, false, this.val$cancel);
    }
}
