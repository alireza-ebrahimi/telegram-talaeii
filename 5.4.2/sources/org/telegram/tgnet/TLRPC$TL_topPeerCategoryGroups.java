package org.telegram.tgnet;

public class TLRPC$TL_topPeerCategoryGroups extends TLRPC$TopPeerCategory {
    public static int constructor = -1122524854;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
