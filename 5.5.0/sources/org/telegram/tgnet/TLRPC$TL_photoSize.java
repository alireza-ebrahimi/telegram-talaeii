package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.FileLocation;
import org.telegram.tgnet.TLRPC.PhotoSize;

public class TLRPC$TL_photoSize extends PhotoSize {
    public static int constructor = 2009052699;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.type = abstractSerializedData.readString(z);
        this.location = FileLocation.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.w = abstractSerializedData.readInt32(z);
        this.h = abstractSerializedData.readInt32(z);
        this.size = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.type);
        this.location.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(this.w);
        abstractSerializedData.writeInt32(this.h);
        abstractSerializedData.writeInt32(this.size);
    }
}
