package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.FileLocation;

public class TLRPC$TL_userProfilePhoto extends TLRPC$UserProfilePhoto {
    public static int constructor = -715532088;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.photo_id = abstractSerializedData.readInt64(z);
        this.photo_small = FileLocation.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.photo_big = FileLocation.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt64(this.photo_id);
        this.photo_small.serializeToStream(abstractSerializedData);
        this.photo_big.serializeToStream(abstractSerializedData);
    }
}
