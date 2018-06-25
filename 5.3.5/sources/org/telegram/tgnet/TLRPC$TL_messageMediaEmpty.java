package org.telegram.tgnet;

public class TLRPC$TL_messageMediaEmpty extends TLRPC$MessageMedia {
    public static int constructor = 1038967584;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
