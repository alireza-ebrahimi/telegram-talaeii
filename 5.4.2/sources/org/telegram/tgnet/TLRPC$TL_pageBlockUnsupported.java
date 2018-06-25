package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.PageBlock;

public class TLRPC$TL_pageBlockUnsupported extends PageBlock {
    public static int constructor = 324435594;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
