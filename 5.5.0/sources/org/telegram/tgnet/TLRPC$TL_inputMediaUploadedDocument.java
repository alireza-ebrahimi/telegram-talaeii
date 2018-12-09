package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.DocumentAttribute;
import org.telegram.tgnet.TLRPC.InputDocument;
import org.telegram.tgnet.TLRPC.InputFile;
import org.telegram.tgnet.TLRPC.InputMedia;

public class TLRPC$TL_inputMediaUploadedDocument extends InputMedia {
    public static int constructor = -476700163;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int i = 0;
        this.flags = abstractSerializedData.readInt32(z);
        this.nosound_video = (this.flags & 8) != 0;
        this.file = InputFile.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        if ((this.flags & 4) != 0) {
            this.thumb = InputFile.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }
        this.mime_type = abstractSerializedData.readString(z);
        int i2;
        if (abstractSerializedData.readInt32(z) == 481674261) {
            int readInt32 = abstractSerializedData.readInt32(z);
            i2 = 0;
            while (i2 < readInt32) {
                DocumentAttribute TLdeserialize = DocumentAttribute.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                if (TLdeserialize != null) {
                    this.attributes.add(TLdeserialize);
                    i2++;
                } else {
                    return;
                }
            }
            this.caption = abstractSerializedData.readString(z);
            if ((this.flags & 1) != 0) {
                if (abstractSerializedData.readInt32(z) == 481674261) {
                    i2 = abstractSerializedData.readInt32(z);
                    while (i < i2) {
                        InputDocument TLdeserialize2 = InputDocument.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                        if (TLdeserialize2 != null) {
                            this.stickers.add(TLdeserialize2);
                            i++;
                        } else {
                            return;
                        }
                    }
                } else if (z) {
                    throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(i2)}));
                } else {
                    return;
                }
            }
            if ((this.flags & 2) != 0) {
                this.ttl_seconds = abstractSerializedData.readInt32(z);
            }
        } else if (z) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(i2)}));
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        int i;
        int i2 = 0;
        abstractSerializedData.writeInt32(constructor);
        this.flags = this.nosound_video ? this.flags | 8 : this.flags & -9;
        abstractSerializedData.writeInt32(this.flags);
        this.file.serializeToStream(abstractSerializedData);
        if ((this.flags & 4) != 0) {
            this.thumb.serializeToStream(abstractSerializedData);
        }
        abstractSerializedData.writeString(this.mime_type);
        abstractSerializedData.writeInt32(481674261);
        int size = this.attributes.size();
        abstractSerializedData.writeInt32(size);
        for (i = 0; i < size; i++) {
            ((DocumentAttribute) this.attributes.get(i)).serializeToStream(abstractSerializedData);
        }
        abstractSerializedData.writeString(this.caption);
        if ((this.flags & 1) != 0) {
            abstractSerializedData.writeInt32(481674261);
            i = this.stickers.size();
            abstractSerializedData.writeInt32(i);
            while (i2 < i) {
                ((InputDocument) this.stickers.get(i2)).serializeToStream(abstractSerializedData);
                i2++;
            }
        }
        if ((this.flags & 2) != 0) {
            abstractSerializedData.writeInt32(this.ttl_seconds);
        }
    }
}
