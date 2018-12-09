package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.Document;

public class TLRPC$TL_messages_favedStickers extends TLRPC$messages_FavedStickers {
    public static int constructor = -209768682;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int i = 0;
        this.hash = abstractSerializedData.readInt32(z);
        int i2;
        if (abstractSerializedData.readInt32(z) == 481674261) {
            int readInt32 = abstractSerializedData.readInt32(z);
            i2 = 0;
            while (i2 < readInt32) {
                TLRPC$TL_stickerPack TLdeserialize = TLRPC$TL_stickerPack.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                if (TLdeserialize != null) {
                    this.packs.add(TLdeserialize);
                    i2++;
                } else {
                    return;
                }
            }
            if (abstractSerializedData.readInt32(z) == 481674261) {
                i2 = abstractSerializedData.readInt32(z);
                while (i < i2) {
                    Document TLdeserialize2 = Document.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                    if (TLdeserialize2 != null) {
                        this.stickers.add(TLdeserialize2);
                        i++;
                    } else {
                        return;
                    }
                }
            } else if (z) {
                throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(i2)}));
            }
        } else if (z) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(i2)}));
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        int i;
        int i2 = 0;
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.hash);
        abstractSerializedData.writeInt32(481674261);
        int size = this.packs.size();
        abstractSerializedData.writeInt32(size);
        for (i = 0; i < size; i++) {
            ((TLRPC$TL_stickerPack) this.packs.get(i)).serializeToStream(abstractSerializedData);
        }
        abstractSerializedData.writeInt32(481674261);
        i = this.stickers.size();
        abstractSerializedData.writeInt32(i);
        while (i2 < i) {
            ((Document) this.stickers.get(i2)).serializeToStream(abstractSerializedData);
            i2++;
        }
    }
}
