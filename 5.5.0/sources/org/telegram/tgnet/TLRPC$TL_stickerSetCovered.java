package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.Document;
import org.telegram.tgnet.TLRPC.StickerSet;
import org.telegram.tgnet.TLRPC.StickerSetCovered;

public class TLRPC$TL_stickerSetCovered extends StickerSetCovered {
    public static int constructor = 1678812626;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.set = StickerSet.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.cover = Document.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.set.serializeToStream(abstractSerializedData);
        this.cover.serializeToStream(abstractSerializedData);
    }
}
