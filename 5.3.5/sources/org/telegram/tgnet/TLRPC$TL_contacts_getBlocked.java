package org.telegram.tgnet;

public class TLRPC$TL_contacts_getBlocked extends TLObject {
    public static int constructor = -176409329;
    public int limit;
    public int offset;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$contacts_Blocked.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.offset);
        stream.writeInt32(this.limit);
    }
}
