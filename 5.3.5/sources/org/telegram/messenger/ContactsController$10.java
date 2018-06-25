package org.telegram.messenger;

import java.util.Comparator;
import org.telegram.tgnet.TLRPC$TL_contact;

class ContactsController$10 implements Comparator<TLRPC$TL_contact> {
    final /* synthetic */ ContactsController this$0;

    ContactsController$10(ContactsController this$0) {
        this.this$0 = this$0;
    }

    public int compare(TLRPC$TL_contact tl_contact, TLRPC$TL_contact tl_contact2) {
        if (tl_contact.user_id > tl_contact2.user_id) {
            return 1;
        }
        if (tl_contact.user_id < tl_contact2.user_id) {
            return -1;
        }
        return 0;
    }
}
