package org.telegram.messenger;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC$TL_contact;

class ContactsController$19 implements Runnable {
    final /* synthetic */ ContactsController this$0;
    final /* synthetic */ ArrayList val$contactsToDelete;
    final /* synthetic */ ArrayList val$newContacts;

    ContactsController$19(ContactsController this$0, ArrayList arrayList, ArrayList arrayList2) {
        this.this$0 = this$0;
        this.val$newContacts = arrayList;
        this.val$contactsToDelete = arrayList2;
    }

    public void run() {
        int a;
        boolean z = true;
        for (a = 0; a < this.val$newContacts.size(); a++) {
            TLRPC$TL_contact contact = (TLRPC$TL_contact) this.val$newContacts.get(a);
            if (this.this$0.contactsDict.get(Integer.valueOf(contact.user_id)) == null) {
                this.this$0.contacts.add(contact);
                this.this$0.contactsDict.put(Integer.valueOf(contact.user_id), contact);
            }
        }
        for (a = 0; a < this.val$contactsToDelete.size(); a++) {
            Integer uid = (Integer) this.val$contactsToDelete.get(a);
            contact = (TLRPC$TL_contact) this.this$0.contactsDict.get(uid);
            if (contact != null) {
                this.this$0.contacts.remove(contact);
                this.this$0.contactsDict.remove(uid);
            }
        }
        if (!this.val$newContacts.isEmpty()) {
            this.this$0.updateUnregisteredContacts(this.this$0.contacts);
            ContactsController.access$1400(this.this$0);
        }
        this.this$0.performSyncPhoneBook(this.this$0.getContactsCopy(this.this$0.contactsBook), false, false, false, false, true, false);
        ContactsController contactsController = this.this$0;
        if (this.val$newContacts.isEmpty()) {
            z = false;
        }
        ContactsController.access$1900(contactsController, z);
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.contactsDidLoaded, new Object[0]);
    }
}
