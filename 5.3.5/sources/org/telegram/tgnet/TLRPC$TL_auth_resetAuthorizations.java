package org.telegram.tgnet;

public class TLRPC$TL_auth_resetAuthorizations extends TLObject {
    public static int constructor = -1616179942;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Bool.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
