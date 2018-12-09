package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.DraftMessage;
import org.telegram.tgnet.TLRPC.MessageEntity;

public class TLRPC$TL_draftMessage extends DraftMessage {
    public static int constructor = -40996577;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int i = 0;
        this.flags = abstractSerializedData.readInt32(z);
        this.no_webpage = (this.flags & 2) != 0;
        if ((this.flags & 1) != 0) {
            this.reply_to_msg_id = abstractSerializedData.readInt32(z);
        }
        this.message = abstractSerializedData.readString(z);
        if ((this.flags & 8) != 0) {
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
            } else {
                return;
            }
        }
        this.date = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.flags = this.no_webpage ? this.flags | 2 : this.flags & -3;
        abstractSerializedData.writeInt32(this.flags);
        if ((this.flags & 1) != 0) {
            abstractSerializedData.writeInt32(this.reply_to_msg_id);
        }
        abstractSerializedData.writeString(this.message);
        if ((this.flags & 8) != 0) {
            abstractSerializedData.writeInt32(481674261);
            int size = this.entities.size();
            abstractSerializedData.writeInt32(size);
            for (int i = 0; i < size; i++) {
                ((MessageEntity) this.entities.get(i)).serializeToStream(abstractSerializedData);
            }
        }
        abstractSerializedData.writeInt32(this.date);
    }
}
