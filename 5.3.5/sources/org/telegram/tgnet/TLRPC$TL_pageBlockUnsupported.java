package org.telegram.tgnet;

public class TLRPC$TL_pageBlockUnsupported extends TLRPC$PageBlock {
    public static int constructor = 324435594;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
