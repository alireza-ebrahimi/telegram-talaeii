package org.telegram.tgnet;

import org.telegram.messenger.MessagesController;
import org.telegram.tgnet.TLRPC.MessageEntity;
import org.telegram.tgnet.TLRPC.MessageFwdHeader;

public class TLRPC$TL_updateShortMessage extends TLRPC$Updates {
    public static int constructor = -1857044719;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int i = 0;
        this.flags = abstractSerializedData.readInt32(z);
        this.out = (this.flags & 2) != 0;
        this.mentioned = (this.flags & 16) != 0;
        this.media_unread = (this.flags & 32) != 0;
        this.silent = (this.flags & MessagesController.UPDATE_MASK_CHANNEL) != 0;
        this.id = abstractSerializedData.readInt32(z);
        this.user_id = abstractSerializedData.readInt32(z);
        this.message = abstractSerializedData.readString(z);
        this.pts = abstractSerializedData.readInt32(z);
        this.pts_count = abstractSerializedData.readInt32(z);
        this.date = abstractSerializedData.readInt32(z);
        if ((this.flags & 4) != 0) {
            this.fwd_from = MessageFwdHeader.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }
        if ((this.flags & 2048) != 0) {
            this.via_bot_id = abstractSerializedData.readInt32(z);
        }
        if ((this.flags & 8) != 0) {
            this.reply_to_msg_id = abstractSerializedData.readInt32(z);
        }
        if ((this.flags & 128) != 0) {
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
    }
}
