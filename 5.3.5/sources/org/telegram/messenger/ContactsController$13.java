package org.telegram.messenger;

import java.util.Comparator;

class ContactsController$13 implements Comparator<ContactsController$Contact> {
    final /* synthetic */ ContactsController this$0;

    ContactsController$13(ContactsController this$0) {
        this.this$0 = this$0;
    }

    public int compare(ContactsController$Contact contact, ContactsController$Contact contact2) {
        String toComapre1 = contact.first_name;
        if (toComapre1.length() == 0) {
            toComapre1 = contact.last_name;
        }
        String toComapre2 = contact2.first_name;
        if (toComapre2.length() == 0) {
            toComapre2 = contact2.last_name;
        }
        return toComapre1.compareTo(toComapre2);
    }
}
