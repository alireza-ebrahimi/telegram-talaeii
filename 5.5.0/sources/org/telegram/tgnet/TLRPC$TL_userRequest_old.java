package org.telegram.tgnet;

public class TLRPC$TL_userRequest_old extends TLRPC$TL_userRequest_old2 {
    public static int constructor = 585682608;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.id = abstractSerializedData.readInt32(z);
        this.first_name = abstractSerializedData.readString(z);
        this.last_name = abstractSerializedData.readString(z);
        this.access_hash = abstractSerializedData.readInt64(z);
        this.phone = abstractSerializedData.readString(z);
        this.photo = TLRPC$UserProfilePhoto.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.status = TLRPC$UserStatus.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.id);
        abstractSerializedData.writeString(this.first_name);
        abstractSerializedData.writeString(this.last_name);
        abstractSerializedData.writeInt64(this.access_hash);
        abstractSerializedData.writeString(this.phone);
        this.photo.serializeToStream(abstractSerializedData);
        this.status.serializeToStream(abstractSerializedData);
    }
}
