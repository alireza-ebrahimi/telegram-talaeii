package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.MessageEntity;
import org.telegram.tgnet.TLRPC.MessageMedia;

public class TLRPC$TL_updateServiceNotification extends TLRPC$Update {
    public static int constructor = -337352679;
    public String message;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int i = 0;
        this.flags = abstractSerializedData.readInt32(z);
        this.popup = (this.flags & 1) != 0;
        if ((this.flags & 2) != 0) {
            this.inbox_date = abstractSerializedData.readInt32(z);
        }
        this.type = abstractSerializedData.readString(z);
        this.message = abstractSerializedData.readString(z);
        this.media = MessageMedia.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        int readInt32;
        if (abstractSerializedData.readInt32(z) == 481674261) {
            readInt32 = abstractSerializedData.readInt32(z);
            while (i < readInt32) {
                MessageEntity TLdeserialize = MessageEntity.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                if (TLdeserialize != null) {
                    this.entities.add(TLdeserialize);
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
        this.flags = this.popup ? this.flags | 1 : this.flags & -2;
        abstractSerializedData.writeInt32(this.flags);
        if ((this.flags & 2) != 0) {
            abstractSerializedData.writeInt32(this.inbox_date);
        }
        abstractSerializedData.writeString(this.type);
        abstractSerializedData.writeString(this.message);
        this.media.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(481674261);
        int size = this.entities.size();
        abstractSerializedData.writeInt32(size);
        for (int i = 0; i < size; i++) {
            ((MessageEntity) this.entities.get(i)).serializeToStream(abstractSerializedData);
        }
    }
}
