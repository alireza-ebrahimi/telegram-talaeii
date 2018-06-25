package org.telegram.tgnet;

public class TLRPC$TL_phone_getCallConfig extends TLObject {
    public static int constructor = 1430593449;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_dataJSON.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
