package org.telegram.tgnet;

public class TLRPC$TL_account_getAuthorizations extends TLObject {
    public static int constructor = -484392616;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_account_authorizations.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
