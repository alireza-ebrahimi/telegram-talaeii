package org.telegram.tgnet;

import java.util.ArrayList;

public class TLRPC$TL_contacts_importContacts extends TLObject {
    public static int constructor = 746589157;
    public ArrayList<TLRPC$TL_inputPhoneContact> contacts = new ArrayList();

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$TL_contacts_importedContacts.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(481674261);
        int size = this.contacts.size();
        abstractSerializedData.writeInt32(size);
        for (int i = 0; i < size; i++) {
            ((TLRPC$TL_inputPhoneContact) this.contacts.get(i)).serializeToStream(abstractSerializedData);
        }
    }
}
