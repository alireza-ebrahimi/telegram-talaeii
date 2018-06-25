package org.telegram.tgnet;

public class TLRPC$TL_updateUserPhoto extends TLRPC$Update {
    public static int constructor = -1791935732;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.user_id = stream.readInt32(exception);
        this.date = stream.readInt32(exception);
        this.photo = TLRPC$UserProfilePhoto.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.previous = stream.readBool(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.user_id);
        stream.writeInt32(this.date);
        this.photo.serializeToStream(stream);
        stream.writeBool(this.previous);
    }
}
