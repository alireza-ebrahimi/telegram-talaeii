package org.telegram.tgnet;

public class TLRPC$TL_payments_getSavedInfo extends TLObject {
    public static int constructor = 578650699;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_payments_savedInfo.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
