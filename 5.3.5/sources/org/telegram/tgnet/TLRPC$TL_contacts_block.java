package org.telegram.tgnet;

public class TLRPC$TL_contacts_block extends TLObject {
    public static int constructor = 858475004;
    public TLRPC$InputUser id;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Bool.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.id.serializeToStream(stream);
    }
}
