package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.StickerSetCovered;

public class TLRPC$TL_messages_featuredStickers extends TLRPC$messages_FeaturedStickers {
    public static int constructor = -123893531;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int i = 0;
        this.hash = abstractSerializedData.readInt32(z);
        int i2;
        if (abstractSerializedData.readInt32(z) == 481674261) {
            int readInt32 = abstractSerializedData.readInt32(z);
            i2 = 0;
            while (i2 < readInt32) {
                StickerSetCovered TLdeserialize = StickerSetCovered.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                if (TLdeserialize != null) {
                    this.sets.add(TLdeserialize);
                    i2++;
                } else {
                    return;
                }
            }
            if (abstractSerializedData.readInt32(z) == 481674261) {
                i2 = abstractSerializedData.readInt32(z);
                while (i < i2) {
                    this.unread.add(Long.valueOf(abstractSerializedData.readInt64(z)));
                    i++;
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
        int size = this.sets.size();
        abstractSerializedData.writeInt32(size);
        for (i = 0; i < size; i++) {
            ((StickerSetCovered) this.sets.get(i)).serializeToStream(abstractSerializedData);
        }
        abstractSerializedData.writeInt32(481674261);
        i = this.unread.size();
        abstractSerializedData.writeInt32(i);
        while (i2 < i) {
            abstractSerializedData.writeInt64(((Long) this.unread.get(i2)).longValue());
            i2++;
        }
    }
}
