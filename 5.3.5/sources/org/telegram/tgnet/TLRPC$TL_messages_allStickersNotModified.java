package org.telegram.tgnet;

public class TLRPC$TL_messages_allStickersNotModified extends TLRPC$messages_AllStickers {
    public static int constructor = -395967805;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
