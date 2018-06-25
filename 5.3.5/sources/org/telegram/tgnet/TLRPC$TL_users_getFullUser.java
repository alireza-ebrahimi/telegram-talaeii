package org.telegram.tgnet;

public class TLRPC$TL_users_getFullUser extends TLObject {
    public static int constructor = -902781519;
    public TLRPC$InputUser id;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_userFull.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.id.serializeToStream(stream);
    }
}
