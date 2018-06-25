package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.FileLocation;

public class TLRPC$TL_userProfilePhoto_old extends TLRPC$TL_userProfilePhoto {
    public static int constructor = -1727196013;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.photo_small = FileLocation.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.photo_big = FileLocation.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.photo_small.serializeToStream(abstractSerializedData);
        this.photo_big.serializeToStream(abstractSerializedData);
    }
}
