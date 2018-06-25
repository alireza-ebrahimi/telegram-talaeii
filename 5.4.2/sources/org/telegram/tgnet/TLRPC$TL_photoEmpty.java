package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.Photo;

public class TLRPC$TL_photoEmpty extends Photo {
    public static int constructor = 590459437;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.id = abstractSerializedData.readInt64(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt64(this.id);
    }
}
