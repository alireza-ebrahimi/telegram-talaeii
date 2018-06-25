package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.PageBlock;

public class TLRPC$TL_pageBlockAnchor extends PageBlock {
    public static int constructor = -837994576;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.name = abstractSerializedData.readString(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.name);
    }
}
