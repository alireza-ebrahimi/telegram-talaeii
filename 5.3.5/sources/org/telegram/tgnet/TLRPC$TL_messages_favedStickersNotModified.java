package org.telegram.tgnet;

public class TLRPC$TL_messages_favedStickersNotModified extends TLRPC$messages_FavedStickers {
    public static int constructor = -1634752813;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
