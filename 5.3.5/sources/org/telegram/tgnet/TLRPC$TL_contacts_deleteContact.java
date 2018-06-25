package org.telegram.tgnet;

public class TLRPC$TL_contacts_deleteContact extends TLObject {
    public static int constructor = -1902823612;
    public TLRPC$InputUser id;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_contacts_link.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.id.serializeToStream(stream);
    }
}
