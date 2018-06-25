package org.telegram.messenger;

class ContactsController$7 implements Runnable {
    final /* synthetic */ ContactsController this$0;

    ContactsController$7(ContactsController this$0) {
        this.this$0 = this$0;
    }

    public void run() {
        if (!this.this$0.contacts.isEmpty() || this.this$0.contactsLoaded) {
            synchronized (ContactsController.access$500()) {
                ContactsController.access$602(this.this$0, false);
            }
            return;
        }
        this.this$0.loadContacts(true, 0);
    }
}
