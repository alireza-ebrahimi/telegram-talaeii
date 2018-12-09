package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputDocument;
import org.telegram.tgnet.TLRPC.InputFile;
import org.telegram.tgnet.TLRPC.InputMedia;

public class TLRPC$TL_inputMediaUploadedPhoto extends InputMedia {
    public static int constructor = 792191537;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int i = 0;
        this.flags = abstractSerializedData.readInt32(z);
        this.file = InputFile.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.caption = abstractSerializedData.readString(z);
        if ((this.flags & 1) != 0) {
            int readInt32;
            if (abstractSerializedData.readInt32(z) == 481674261) {
                readInt32 = abstractSerializedData.readInt32(z);
                while (i < readInt32) {
                    InputDocument TLdeserialize = InputDocument.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                    if (TLdeserialize != null) {
                        this.stickers.add(TLdeserialize);
                        i++;
                    } else {
                        return;
                    }
                }
            } else if (z) {
                throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(readInt32)}));
            } else {
                return;
            }
        }
        if ((this.flags & 2) != 0) {
            this.ttl_seconds = abstractSerializedData.readInt32(z);
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.flags);
        this.file.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeString(this.caption);
        if ((this.flags & 1) != 0) {
            abstractSerializedData.writeInt32(481674261);
            int size = this.stickers.size();
            abstractSerializedData.writeInt32(size);
            for (int i = 0; i < size; i++) {
                ((InputDocument) this.stickers.get(i)).serializeToStream(abstractSerializedData);
            }
        }
        if ((this.flags & 2) != 0) {
            abstractSerializedData.writeInt32(this.ttl_seconds);
        }
    }
}
