package org.telegram.tgnet;

public class TLRPC$TL_topPeerCategoryCorrespondents extends TLRPC$TopPeerCategory {
    public static int constructor = 104314861;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
