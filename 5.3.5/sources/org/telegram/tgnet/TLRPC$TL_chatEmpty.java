package org.telegram.tgnet;

public class TLRPC$TL_chatEmpty extends TLRPC$Chat {
    public static int constructor = -1683826688;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.id = stream.readInt32(exception);
        this.title = "DELETED";
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.id);
    }
}
