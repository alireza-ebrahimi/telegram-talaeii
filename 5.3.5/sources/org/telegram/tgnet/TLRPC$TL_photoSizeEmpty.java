package org.telegram.tgnet;

public class TLRPC$TL_photoSizeEmpty extends TLRPC$PhotoSize {
    public static int constructor = 236446268;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.type = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.type);
    }
}
