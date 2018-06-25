package org.telegram.tgnet;

public class TLRPC$TL_topPeerCategoryBotsPM extends TLRPC$TopPeerCategory {
    public static int constructor = -1419371685;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
