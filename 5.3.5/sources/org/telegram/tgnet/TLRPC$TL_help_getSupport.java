package org.telegram.tgnet;

public class TLRPC$TL_help_getSupport extends TLObject {
    public static int constructor = -1663104819;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_help_support.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
