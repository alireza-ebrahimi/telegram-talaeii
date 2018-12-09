package org.telegram.tgnet;

public class TLRPC$TL_contacts_contactsNotModified extends TLRPC$contacts_Contacts {
    public static int constructor = -1219778094;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
