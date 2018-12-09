package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.PageBlock;
import org.telegram.tgnet.TLRPC.RichText;

public class TLRPC$TL_pageBlockEmbedPost extends PageBlock {
    public static int constructor = 690781161;
    public String author;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int i = 0;
        this.url = abstractSerializedData.readString(z);
        this.webpage_id = abstractSerializedData.readInt64(z);
        this.author_photo_id = abstractSerializedData.readInt64(z);
        this.author = abstractSerializedData.readString(z);
        this.date = abstractSerializedData.readInt32(z);
        int readInt32;
        if (abstractSerializedData.readInt32(z) == 481674261) {
            readInt32 = abstractSerializedData.readInt32(z);
            while (i < readInt32) {
                PageBlock TLdeserialize = PageBlock.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                if (TLdeserialize != null) {
                    this.blocks.add(TLdeserialize);
                    i++;
                } else {
                    return;
                }
            }
            this.caption = RichText.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        } else if (z) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(readInt32)}));
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.url);
        abstractSerializedData.writeInt64(this.webpage_id);
        abstractSerializedData.writeInt64(this.author_photo_id);
        abstractSerializedData.writeString(this.author);
        abstractSerializedData.writeInt32(this.date);
        abstractSerializedData.writeInt32(481674261);
        int size = this.blocks.size();
        abstractSerializedData.writeInt32(size);
        for (int i = 0; i < size; i++) {
            ((PageBlock) this.blocks.get(i)).serializeToStream(abstractSerializedData);
        }
        this.caption.serializeToStream(abstractSerializedData);
    }
}
