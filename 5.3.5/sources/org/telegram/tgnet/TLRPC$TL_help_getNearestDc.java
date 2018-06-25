package org.telegram.tgnet;

public class TLRPC$TL_help_getNearestDc extends TLObject {
    public static int constructor = 531836966;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_nearestDc.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
