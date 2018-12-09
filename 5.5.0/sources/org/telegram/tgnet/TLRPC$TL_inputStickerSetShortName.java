package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputStickerSet;

public class TLRPC$TL_inputStickerSetShortName extends InputStickerSet {
    public static int constructor = -2044933984;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.short_name = abstractSerializedData.readString(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.short_name);
    }
}
