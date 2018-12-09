package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.Document;
import org.telegram.tgnet.TLRPC.PageBlock;
import org.telegram.tgnet.TLRPC.Photo;

public class TLRPC$TL_pageFull_layer67 extends TLRPC$TL_pageFull {
    public static int constructor = -677274263;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int i = 0;
        int i2;
        if (abstractSerializedData.readInt32(z) == 481674261) {
            int readInt32 = abstractSerializedData.readInt32(z);
            i2 = 0;
            while (i2 < readInt32) {
                PageBlock TLdeserialize = PageBlock.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                if (TLdeserialize != null) {
                    this.blocks.add(TLdeserialize);
                    i2++;
                } else {
                    return;
                }
            }
            if (abstractSerializedData.readInt32(z) == 481674261) {
                readInt32 = abstractSerializedData.readInt32(z);
                i2 = 0;
                while (i2 < readInt32) {
                    Photo TLdeserialize2 = Photo.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                    if (TLdeserialize2 != null) {
                        this.photos.add(TLdeserialize2);
                        i2++;
                    } else {
                        return;
                    }
                }
                if (abstractSerializedData.readInt32(z) == 481674261) {
                    i2 = abstractSerializedData.readInt32(z);
                    while (i < i2) {
                        Document TLdeserialize3 = Document.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                        if (TLdeserialize3 != null) {
                            this.documents.add(TLdeserialize3);
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
        } else if (z) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(i2)}));
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        int i;
        int i2 = 0;
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(481674261);
        int size = this.blocks.size();
        abstractSerializedData.writeInt32(size);
        for (i = 0; i < size; i++) {
            ((PageBlock) this.blocks.get(i)).serializeToStream(abstractSerializedData);
        }
        abstractSerializedData.writeInt32(481674261);
        size = this.photos.size();
        abstractSerializedData.writeInt32(size);
        for (i = 0; i < size; i++) {
            ((Photo) this.photos.get(i)).serializeToStream(abstractSerializedData);
        }
        abstractSerializedData.writeInt32(481674261);
        i = this.documents.size();
        abstractSerializedData.writeInt32(i);
        while (i2 < i) {
            ((Document) this.documents.get(i2)).serializeToStream(abstractSerializedData);
            i2++;
        }
    }
}
