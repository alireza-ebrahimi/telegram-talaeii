package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputStickerSet;

public class TLRPC$TL_documentAttributeSticker_layer55 extends TLRPC$TL_documentAttributeSticker {
    public static int constructor = 978674434;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.alt = abstractSerializedData.readString(z);
        this.stickerset = InputStickerSet.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.alt);
        this.stickerset.serializeToStream(abstractSerializedData);
    }
}
