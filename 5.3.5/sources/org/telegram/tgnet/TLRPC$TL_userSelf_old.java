package org.telegram.tgnet;

public class TLRPC$TL_userSelf_old extends TLRPC$TL_userSelf_old3 {
    public static int constructor = 1912944108;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.id = stream.readInt32(exception);
        this.first_name = stream.readString(exception);
        this.last_name = stream.readString(exception);
        this.phone = stream.readString(exception);
        this.photo = TLRPC$UserProfilePhoto.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.status = TLRPC$UserStatus.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.inactive = stream.readBool(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.id);
        stream.writeString(this.first_name);
        stream.writeString(this.last_name);
        stream.writeString(this.phone);
        this.photo.serializeToStream(stream);
        this.status.serializeToStream(stream);
        stream.writeBool(this.inactive);
    }
}
