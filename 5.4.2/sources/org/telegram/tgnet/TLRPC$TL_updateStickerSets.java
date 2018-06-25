package org.telegram.tgnet;

public class TLRPC$TL_updateStickerSets extends TLRPC$Update {
    public static int constructor = 1135492588;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
