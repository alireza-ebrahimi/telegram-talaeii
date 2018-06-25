package org.telegram.tgnet;

public class TLRPC$TL_documentEmpty extends TLRPC$Document {
    public static int constructor = 922273905;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.id = stream.readInt64(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt64(this.id);
    }
}
