package org.telegram.tgnet;

import java.util.ArrayList;

public class TLRPC$TL_stickerPack extends TLObject {
    public static int constructor = 313694676;
    public ArrayList<Long> documents = new ArrayList();
    public String emoticon;

    public static TLRPC$TL_stickerPack TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_stickerPack tLRPC$TL_stickerPack = new TLRPC$TL_stickerPack();
            tLRPC$TL_stickerPack.readParams(abstractSerializedData, z);
            return tLRPC$TL_stickerPack;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_stickerPack", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int i = 0;
        this.emoticon = abstractSerializedData.readString(z);
        int readInt32;
        if (abstractSerializedData.readInt32(z) == 481674261) {
            readInt32 = abstractSerializedData.readInt32(z);
            while (i < readInt32) {
                this.documents.add(Long.valueOf(abstractSerializedData.readInt64(z)));
                i++;
            }
        } else if (z) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(readInt32)}));
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.emoticon);
        abstractSerializedData.writeInt32(481674261);
        int size = this.documents.size();
        abstractSerializedData.writeInt32(size);
        for (int i = 0; i < size; i++) {
            abstractSerializedData.writeInt64(((Long) this.documents.get(i)).longValue());
        }
    }
}
