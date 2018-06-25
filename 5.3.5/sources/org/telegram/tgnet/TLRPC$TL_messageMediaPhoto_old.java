package org.telegram.tgnet;

public class TLRPC$TL_messageMediaPhoto_old extends TLRPC$TL_messageMediaPhoto {
    public static int constructor = -926655958;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.photo = TLRPC$Photo.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.photo.serializeToStream(stream);
    }
}
