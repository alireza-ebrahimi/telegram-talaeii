package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.DocumentAttribute;

public class TLRPC$TL_documentAttributeImageSize extends DocumentAttribute {
    public static int constructor = 1815593308;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.w = abstractSerializedData.readInt32(z);
        this.h = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.w);
        abstractSerializedData.writeInt32(this.h);
    }
}
