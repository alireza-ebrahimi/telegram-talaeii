package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.User;

public abstract class TLRPC$contacts_Contacts extends TLObject {
    public ArrayList<TLRPC$TL_contact> contacts = new ArrayList();
    public int saved_count;
    public ArrayList<User> users = new ArrayList();

    public static TLRPC$contacts_Contacts TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$contacts_Contacts result = null;
        switch (constructor) {
            case -1219778094:
                result = new TLRPC$TL_contacts_contactsNotModified();
                break;
            case -353862078:
                result = new TLRPC$TL_contacts_contacts();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in contacts_Contacts", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
