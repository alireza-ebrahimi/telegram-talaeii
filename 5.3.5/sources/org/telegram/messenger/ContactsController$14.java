package org.telegram.messenger;

import java.util.Comparator;
import org.telegram.tgnet.TLRPC$TL_contact;

class ContactsController$14 implements Comparator<TLRPC$TL_contact> {
    final /* synthetic */ ContactsController this$0;

    ContactsController$14(ContactsController this$0) {
        this.this$0 = this$0;
    }

    public int compare(TLRPC$TL_contact tl_contact, TLRPC$TL_contact tl_contact2) {
        return UserObject.getFirstName(MessagesController.getInstance().getUser(Integer.valueOf(tl_contact.user_id))).compareTo(UserObject.getFirstName(MessagesController.getInstance().getUser(Integer.valueOf(tl_contact2.user_id))));
    }
}
