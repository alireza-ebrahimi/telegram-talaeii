package org.telegram.tgnet;

public class TLRPC$TL_updateReadFeaturedStickers extends TLRPC$Update {
    public static int constructor = 1461528386;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
