package org.telegram.tgnet;

public class TLRPC$TL_photoCachedSize extends TLRPC$PhotoSize {
    public static int constructor = -374917894;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.type = stream.readString(exception);
        this.location = TLRPC$FileLocation.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.w = stream.readInt32(exception);
        this.h = stream.readInt32(exception);
        this.bytes = stream.readByteArray(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.type);
        this.location.serializeToStream(stream);
        stream.writeInt32(this.w);
        stream.writeInt32(this.h);
        stream.writeByteArray(this.bytes);
    }
}
