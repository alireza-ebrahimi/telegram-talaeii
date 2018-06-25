package org.telegram.tgnet;

public class TLRPC$TL_videoEmpty_layer45 extends TLRPC$Video {
    public static int constructor = -1056548696;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.id = stream.readInt64(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt64(this.id);
    }
}
