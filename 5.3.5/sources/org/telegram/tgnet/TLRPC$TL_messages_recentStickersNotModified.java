package org.telegram.tgnet;

public class TLRPC$TL_messages_recentStickersNotModified extends TLRPC$messages_RecentStickers {
    public static int constructor = 186120336;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
