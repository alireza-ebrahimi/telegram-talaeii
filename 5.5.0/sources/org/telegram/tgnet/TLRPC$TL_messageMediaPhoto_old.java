package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.Photo;

public class TLRPC$TL_messageMediaPhoto_old extends TLRPC$TL_messageMediaPhoto {
    public static int constructor = -926655958;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.photo = Photo.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.photo.serializeToStream(abstractSerializedData);
    }
}
