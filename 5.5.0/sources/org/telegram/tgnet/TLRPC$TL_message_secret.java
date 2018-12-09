package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.MessageEntity;
import org.telegram.tgnet.TLRPC.MessageMedia;
import org.telegram.tgnet.TLRPC.Peer;

public class TLRPC$TL_message_secret extends TLRPC$TL_message {
    public static int constructor = 1431655930;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int i = 0;
        this.flags = abstractSerializedData.readInt32(z);
        this.unread = (this.flags & 1) != 0;
        this.out = (this.flags & 2) != 0;
        this.mentioned = (this.flags & 16) != 0;
        this.media_unread = (this.flags & 32) != 0;
        this.id = abstractSerializedData.readInt32(z);
        this.ttl = abstractSerializedData.readInt32(z);
        this.from_id = abstractSerializedData.readInt32(z);
        this.to_id = Peer.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.date = abstractSerializedData.readInt32(z);
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
            if ((this.flags & 2048) != 0) {
                this.via_bot_name = abstractSerializedData.readString(z);
            }
            if ((this.flags & 8) != 0) {
                this.reply_to_random_id = abstractSerializedData.readInt64(z);
            }
            if ((this.flags & 131072) != 0) {
                this.grouped_id = abstractSerializedData.readInt64(z);
            }
            if (this.id < 0 || !(this.media == null || (this.media instanceof TLRPC$TL_messageMediaEmpty) || (this.media instanceof TLRPC$TL_messageMediaWebPage) || this.message == null || this.message.length() == 0 || !this.message.startsWith("-1"))) {
                this.attachPath = abstractSerializedData.readString(z);
            }
        } else if (z) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(readInt32)}));
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.flags = this.unread ? this.flags | 1 : this.flags & -2;
        this.flags = this.out ? this.flags | 2 : this.flags & -3;
        this.flags = this.mentioned ? this.flags | 16 : this.flags & -17;
        this.flags = this.media_unread ? this.flags | 32 : this.flags & -33;
        abstractSerializedData.writeInt32(this.flags);
        abstractSerializedData.writeInt32(this.id);
        abstractSerializedData.writeInt32(this.ttl);
        abstractSerializedData.writeInt32(this.from_id);
        this.to_id.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(this.date);
        abstractSerializedData.writeString(this.message);
        this.media.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(481674261);
        int size = this.entities.size();
        abstractSerializedData.writeInt32(size);
        for (int i = 0; i < size; i++) {
            ((MessageEntity) this.entities.get(i)).serializeToStream(abstractSerializedData);
        }
        if ((this.flags & 2048) != 0) {
            abstractSerializedData.writeString(this.via_bot_name);
        }
        if ((this.flags & 8) != 0) {
            abstractSerializedData.writeInt64(this.reply_to_random_id);
        }
        if ((this.flags & 131072) != 0) {
            abstractSerializedData.writeInt64(this.grouped_id);
        }
        abstractSerializedData.writeString(this.attachPath);
    }
}
