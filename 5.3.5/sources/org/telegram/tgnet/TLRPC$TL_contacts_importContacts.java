package org.telegram.tgnet;

import java.util.ArrayList;

public class TLRPC$TL_contacts_importContacts extends TLObject {
    public static int constructor = 746589157;
    public ArrayList<TLRPC$TL_inputPhoneContact> contacts = new ArrayList();

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_contacts_importedContacts.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(481674261);
        int count = this.contacts.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            ((TLRPC$TL_inputPhoneContact) this.contacts.get(a)).serializeToStream(stream);
        }
    }
}
