package org.telegram.tgnet;

public class TLRPC$TL_contacts_getContacts extends TLObject {
    public static int constructor = -1071414113;
    public int hash;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$contacts_Contacts.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.hash);
    }
}
