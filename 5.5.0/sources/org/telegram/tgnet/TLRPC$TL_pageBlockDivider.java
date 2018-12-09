package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.PageBlock;

public class TLRPC$TL_pageBlockDivider extends PageBlock {
    public static int constructor = -618614392;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
