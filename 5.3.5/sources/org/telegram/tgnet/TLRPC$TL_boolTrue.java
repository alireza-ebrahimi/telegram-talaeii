package org.telegram.tgnet;

public class TLRPC$TL_boolTrue extends TLRPC$Bool {
    public static int constructor = -1720552011;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
