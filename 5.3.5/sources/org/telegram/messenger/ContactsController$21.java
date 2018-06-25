package org.telegram.messenger;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Iterator;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_contact;
import org.telegram.tgnet.TLRPC$TL_contacts_importedContacts;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC.User;

class ContactsController$21 implements RequestDelegate {
    final /* synthetic */ ContactsController this$0;

    ContactsController$21(ContactsController this$0) {
        this.this$0 = this$0;
    }

    public void run(TLObject response, TLRPC$TL_error error) {
        if (error == null) {
            final TLRPC$TL_contacts_importedContacts res = (TLRPC$TL_contacts_importedContacts) response;
            MessagesStorage.getInstance().putUsersAndChats(res.users, null, true, true);
            for (int a = 0; a < res.users.size(); a++) {
                final User u = (User) res.users.get(a);
                Utilities.phoneBookQueue.postRunnable(new Runnable() {
                    public void run() {
                        ContactsController$21.this.this$0.addContactToPhoneBook(u, true);
                    }
                });
                TLRPC$TL_contact newContact = new TLRPC$TL_contact();
                newContact.user_id = u.id;
                ArrayList<TLRPC$TL_contact> arrayList = new ArrayList();
                arrayList.add(newContact);
                MessagesStorage.getInstance().putContacts(arrayList, false);
                if (!TextUtils.isEmpty(u.phone)) {
                    CharSequence name = ContactsController.formatName(u.first_name, u.last_name);
                    MessagesStorage.getInstance().applyPhoneBookUpdates(u.phone, "");
                    ContactsController$Contact contact = (ContactsController$Contact) this.this$0.contactsBookSPhones.get(u.phone);
                    if (contact != null) {
                        int index = contact.shortPhones.indexOf(u.phone);
                        if (index != -1) {
                            contact.phoneDeleted.set(index, Integer.valueOf(0));
                        }
                    }
                }
            }
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    Iterator it = res.users.iterator();
                    while (it.hasNext()) {
                        User u = (User) it.next();
                        MessagesController.getInstance().putUser(u, false);
                        if (ContactsController$21.this.this$0.contactsDict.get(Integer.valueOf(u.id)) == null) {
                            TLRPC$TL_contact newContact = new TLRPC$TL_contact();
                            newContact.user_id = u.id;
                            ContactsController$21.this.this$0.contacts.add(newContact);
                            ContactsController$21.this.this$0.contactsDict.put(Integer.valueOf(newContact.user_id), newContact);
                        }
                    }
                    ContactsController.access$1900(ContactsController$21.this.this$0, true);
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.contactsDidLoaded, new Object[0]);
                }
            });
        }
    }
}
