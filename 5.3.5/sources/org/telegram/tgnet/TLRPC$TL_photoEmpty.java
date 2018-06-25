package org.telegram.tgnet;

public class TLRPC$TL_photoEmpty extends TLRPC$Photo {
    public static int constructor = 590459437;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.id = stream.readInt64(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt64(this.id);
    }
}
