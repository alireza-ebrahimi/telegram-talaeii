package org.telegram.tgnet;

public class TLRPC$TL_pageBlockDivider extends TLRPC$PageBlock {
    public static int constructor = -618614392;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
