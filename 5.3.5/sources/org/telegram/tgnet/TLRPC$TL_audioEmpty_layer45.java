package org.telegram.tgnet;

public class TLRPC$TL_audioEmpty_layer45 extends TLRPC$Audio {
    public static int constructor = 1483311320;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.id = stream.readInt64(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt64(this.id);
    }
}
