package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.PhotoSize;

public class TLRPC$TL_wallPaper extends TLRPC$WallPaper {
    public static int constructor = -860866985;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int i = 0;
        this.id = abstractSerializedData.readInt32(z);
        this.title = abstractSerializedData.readString(z);
        int readInt32;
        if (abstractSerializedData.readInt32(z) == 481674261) {
            readInt32 = abstractSerializedData.readInt32(z);
            while (i < readInt32) {
                PhotoSize TLdeserialize = PhotoSize.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                if (TLdeserialize != null) {
                    this.sizes.add(TLdeserialize);
                    i++;
                } else {
                    return;
                }
            }
            this.color = abstractSerializedData.readInt32(z);
        } else if (z) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(readInt32)}));
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.id);
        abstractSerializedData.writeString(this.title);
        abstractSerializedData.writeInt32(481674261);
        int size = this.sizes.size();
        abstractSerializedData.writeInt32(size);
        for (int i = 0; i < size; i++) {
            ((PhotoSize) this.sizes.get(i)).serializeToStream(abstractSerializedData);
        }
        abstractSerializedData.writeInt32(this.color);
    }
}
