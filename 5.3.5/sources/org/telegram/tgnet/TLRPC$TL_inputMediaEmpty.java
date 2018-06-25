package org.telegram.tgnet;

public class TLRPC$TL_inputMediaEmpty extends TLRPC$InputMedia {
    public static int constructor = -1771768449;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
