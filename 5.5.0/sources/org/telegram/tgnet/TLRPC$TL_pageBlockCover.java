package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.PageBlock;

public class TLRPC$TL_pageBlockCover extends PageBlock {
    public static int constructor = 972174080;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.cover = PageBlock.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.cover.serializeToStream(abstractSerializedData);
    }
}
