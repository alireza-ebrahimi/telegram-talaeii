package org.telegram.tgnet;

public class TLRPC$TL_topPeerCategoryChannels extends TLRPC$TopPeerCategory {
    public static int constructor = 371037736;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
