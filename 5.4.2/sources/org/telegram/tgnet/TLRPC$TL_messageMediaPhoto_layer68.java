package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.Photo;

public class TLRPC$TL_messageMediaPhoto_layer68 extends TLRPC$TL_messageMediaPhoto {
    public static int constructor = 1032643901;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.photo = Photo.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.caption = abstractSerializedData.readString(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.photo.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeString(this.caption);
    }
}
