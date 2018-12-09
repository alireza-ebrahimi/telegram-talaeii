package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.Document;

public class TLRPC$TL_messages_stickers extends TLRPC$messages_Stickers {
    public static int constructor = -1970352846;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int i = 0;
        this.hash = abstractSerializedData.readString(z);
        int readInt32;
        if (abstractSerializedData.readInt32(z) == 481674261) {
            readInt32 = abstractSerializedData.readInt32(z);
            while (i < readInt32) {
                Document TLdeserialize = Document.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                if (TLdeserialize != null) {
                    this.stickers.add(TLdeserialize);
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
        abstractSerializedData.writeString(this.hash);
        abstractSerializedData.writeInt32(481674261);
        int size = this.stickers.size();
        abstractSerializedData.writeInt32(size);
        for (int i = 0; i < size; i++) {
            ((Document) this.stickers.get(i)).serializeToStream(abstractSerializedData);
        }
    }
}
