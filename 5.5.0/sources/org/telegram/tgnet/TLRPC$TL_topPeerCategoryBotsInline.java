package org.telegram.tgnet;

public class TLRPC$TL_topPeerCategoryBotsInline extends TLRPC$TopPeerCategory {
    public static int constructor = 344356834;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
