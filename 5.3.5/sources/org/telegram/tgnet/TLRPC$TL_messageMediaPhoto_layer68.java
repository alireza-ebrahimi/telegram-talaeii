package org.telegram.tgnet;

public class TLRPC$TL_messageMediaPhoto_layer68 extends TLRPC$TL_messageMediaPhoto {
    public static int constructor = 1032643901;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.photo = TLRPC$Photo.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.caption = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.photo.serializeToStream(stream);
        stream.writeString(this.caption);
    }
}
