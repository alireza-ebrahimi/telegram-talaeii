package org.telegram.tgnet;

public class TLRPC$TL_userProfilePhoto extends TLRPC$UserProfilePhoto {
    public static int constructor = -715532088;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.photo_id = stream.readInt64(exception);
        this.photo_small = TLRPC$FileLocation.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.photo_big = TLRPC$FileLocation.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt64(this.photo_id);
        this.photo_small.serializeToStream(stream);
        this.photo_big.serializeToStream(stream);
    }
}
