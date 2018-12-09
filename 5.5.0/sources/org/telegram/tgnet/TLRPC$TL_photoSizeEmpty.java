package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.PhotoSize;

public class TLRPC$TL_photoSizeEmpty extends PhotoSize {
    public static int constructor = 236446268;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.type = abstractSerializedData.readString(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.type);
    }
}
