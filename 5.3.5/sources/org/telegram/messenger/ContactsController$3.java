package org.telegram.messenger;

class ContactsController$3 implements Runnable {
    final /* synthetic */ ContactsController this$0;

    ContactsController$3(ContactsController this$0) {
        this.this$0 = this$0;
    }

    public void run() {
        if (ContactsController.access$400(this.this$0)) {
            FileLog.e("detected contacts change");
            ContactsController.getInstance().performSyncPhoneBook(ContactsController.getInstance().getContactsCopy(ContactsController.getInstance().contactsBook), true, false, true, false, true, false);
        }
    }
}
