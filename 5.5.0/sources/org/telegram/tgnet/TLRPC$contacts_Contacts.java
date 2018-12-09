package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.User;

public abstract class TLRPC$contacts_Contacts extends TLObject {
    public ArrayList<TLRPC$TL_contact> contacts = new ArrayList();
    public int saved_count;
    public ArrayList<User> users = new ArrayList();

    public static TLRPC$contacts_Contacts TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        TLRPC$contacts_Contacts tLRPC$contacts_Contacts = null;
        switch (i) {
            case -1219778094:
                tLRPC$contacts_Contacts = new TLRPC$TL_contacts_contactsNotModified();
                break;
            case -353862078:
                tLRPC$contacts_Contacts = new TLRPC$TL_contacts_contacts();
                break;
        }
        if (tLRPC$contacts_Contacts == null && z) {
            throw new RuntimeException(String.format("can't parse magic %x in contacts_Contacts", new Object[]{Integer.valueOf(i)}));
        }
        if (tLRPC$contacts_Contacts != null) {
            tLRPC$contacts_Contacts.readParams(abstractSerializedData, z);
        }
        return tLRPC$contacts_Contacts;
    }
}
