package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.Document;
import org.telegram.tgnet.TLRPC.StickerSet;
import org.telegram.tgnet.TLRPC.StickerSetCovered;

public class TLRPC$TL_stickerSetMultiCovered extends StickerSetCovered {
    public static int constructor = 872932635;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int i = 0;
        this.set = StickerSet.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        int readInt32;
        if (abstractSerializedData.readInt32(z) == 481674261) {
            readInt32 = abstractSerializedData.readInt32(z);
            while (i < readInt32) {
                Document TLdeserialize = Document.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                if (TLdeserialize != null) {
                    this.covers.add(TLdeserialize);
                    i++;
                } else {
                    return;
                }
            }
        } else if (z) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(readInt32)}));
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.set.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(481674261);
        int size = this.covers.size();
        abstractSerializedData.writeInt32(size);
        for (int i = 0; i < size; i++) {
            ((Document) this.covers.get(i)).serializeToStream(abstractSerializedData);
        }
    }
}
