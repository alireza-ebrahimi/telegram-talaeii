package org.telegram.tgnet;

public class TLRPC$TL_userRequest_old extends TLRPC$TL_userRequest_old2 {
    public static int constructor = 585682608;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.id = stream.readInt32(exception);
        this.first_name = stream.readString(exception);
        this.last_name = stream.readString(exception);
        this.access_hash = stream.readInt64(exception);
        this.phone = stream.readString(exception);
        this.photo = TLRPC$UserProfilePhoto.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.status = TLRPC$UserStatus.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.id);
        stream.writeString(this.first_name);
        stream.writeString(this.last_name);
        stream.writeInt64(this.access_hash);
        stream.writeString(this.phone);
        this.photo.serializeToStream(stream);
        this.status.serializeToStream(stream);
    }
}
