package org.telegram.tgnet;

public class TLRPC$TL_topPeerCategoryBotsInline extends TLRPC$TopPeerCategory {
    public static int constructor = 344356834;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
