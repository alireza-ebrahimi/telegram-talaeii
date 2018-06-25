package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputStickerSet;

public class TLRPC$TL_inputStickerSetEmpty extends InputStickerSet {
    public static int constructor = -4838507;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
