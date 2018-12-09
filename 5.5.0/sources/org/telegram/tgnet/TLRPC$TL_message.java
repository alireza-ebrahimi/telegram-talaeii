package org.telegram.tgnet;

import java.util.HashMap;
import java.util.Map.Entry;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.tgnet.TLRPC.Message;
import org.telegram.tgnet.TLRPC.MessageEntity;
import org.telegram.tgnet.TLRPC.MessageFwdHeader;
import org.telegram.tgnet.TLRPC.MessageMedia;
import org.telegram.tgnet.TLRPC.Peer;
import org.telegram.tgnet.TLRPC.ReplyMarkup;

public class TLRPC$TL_message extends Message {
    public static int constructor = 1157215293;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int i;
        this.flags = abstractSerializedData.readInt32(z);
        this.out = (this.flags & 2) != 0;
        this.mentioned = (this.flags & 16) != 0;
        this.media_unread = (this.flags & 32) != 0;
        this.silent = (this.flags & MessagesController.UPDATE_MASK_CHANNEL) != 0;
        this.post = (this.flags & MessagesController.UPDATE_MASK_CHAT_ADMINS) != 0;
        this.id = abstractSerializedData.readInt32(z);
        if ((this.flags & 256) != 0) {
            this.from_id = abstractSerializedData.readInt32(z);
        }
        this.to_id = Peer.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        if ((this.flags & 4) != 0) {
            this.fwd_from = MessageFwdHeader.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }
        if ((this.flags & 2048) != 0) {
            this.via_bot_id = abstractSerializedData.readInt32(z);
        }
        if ((this.flags & 8) != 0) {
            this.reply_to_msg_id = abstractSerializedData.readInt32(z);
        }
        this.date = abstractSerializedData.readInt32(z);
        this.message = abstractSerializedData.readString(z);
        if ((this.flags & 512) != 0) {
            this.media = MessageMedia.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            if (this.media != null) {
                this.ttl = this.media.ttl_seconds;
            }
        }
        if ((this.flags & 64) != 0) {
            this.reply_markup = ReplyMarkup.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }
        if ((this.flags & 128) != 0) {
            if (abstractSerializedData.readInt32(z) == 481674261) {
                int readInt32 = abstractSerializedData.readInt32(z);
                i = 0;
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
                throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(i)}));
            } else {
                return;
            }
        }
        if ((this.flags & 1024) != 0) {
            this.views = abstractSerializedData.readInt32(z);
        }
        if ((this.flags & TLRPC.MESSAGE_FLAG_EDITED) != 0) {
            this.edit_date = abstractSerializedData.readInt32(z);
        }
        if ((this.flags & C3446C.DEFAULT_BUFFER_SEGMENT_SIZE) != 0) {
            this.post_author = abstractSerializedData.readString(z);
        }
        if ((this.flags & 131072) != 0) {
            this.grouped_id = abstractSerializedData.readInt64(z);
        }
        if (this.id < 0 || !(this.media == null || (this.media instanceof TLRPC$TL_messageMediaEmpty) || (this.media instanceof TLRPC$TL_messageMediaWebPage) || this.message == null || this.message.length() == 0 || !this.message.startsWith("-1"))) {
            this.attachPath = abstractSerializedData.readString(z);
            if (this.id < 0 && this.attachPath.startsWith("||")) {
                String[] split = this.attachPath.split("\\|\\|");
                if (split.length > 0) {
                    this.params = new HashMap();
                    for (i = 1; i < split.length - 1; i++) {
                        String[] split2 = split[i].split("\\|=\\|");
                        if (split2.length == 2) {
                            this.params.put(split2[0], split2[1]);
                        }
                    }
                    this.attachPath = split[split.length - 1];
                }
            }
        }
        if ((this.flags & 4) != 0 && this.id < 0) {
            this.fwd_msg_id = abstractSerializedData.readInt32(z);
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.flags = this.out ? this.flags | 2 : this.flags & -3;
        this.flags = this.mentioned ? this.flags | 16 : this.flags & -17;
        this.flags = this.media_unread ? this.flags | 32 : this.flags & -33;
        this.flags = this.silent ? this.flags | MessagesController.UPDATE_MASK_CHANNEL : this.flags & -8193;
        this.flags = this.post ? this.flags | MessagesController.UPDATE_MASK_CHAT_ADMINS : this.flags & -16385;
        abstractSerializedData.writeInt32(this.flags);
        abstractSerializedData.writeInt32(this.id);
        if ((this.flags & 256) != 0) {
            abstractSerializedData.writeInt32(this.from_id);
        }
        this.to_id.serializeToStream(abstractSerializedData);
        if ((this.flags & 4) != 0) {
            this.fwd_from.serializeToStream(abstractSerializedData);
        }
        if ((this.flags & 2048) != 0) {
            abstractSerializedData.writeInt32(this.via_bot_id);
        }
        if ((this.flags & 8) != 0) {
            abstractSerializedData.writeInt32(this.reply_to_msg_id);
        }
        abstractSerializedData.writeInt32(this.date);
        abstractSerializedData.writeString(this.message);
        if ((this.flags & 512) != 0) {
            this.media.serializeToStream(abstractSerializedData);
        }
        if ((this.flags & 64) != 0) {
            this.reply_markup.serializeToStream(abstractSerializedData);
        }
        if ((this.flags & 128) != 0) {
            abstractSerializedData.writeInt32(481674261);
            int size = this.entities.size();
            abstractSerializedData.writeInt32(size);
            for (int i = 0; i < size; i++) {
                ((MessageEntity) this.entities.get(i)).serializeToStream(abstractSerializedData);
            }
        }
        if ((this.flags & 1024) != 0) {
            abstractSerializedData.writeInt32(this.views);
        }
        if ((this.flags & TLRPC.MESSAGE_FLAG_EDITED) != 0) {
            abstractSerializedData.writeInt32(this.edit_date);
        }
        if ((this.flags & C3446C.DEFAULT_BUFFER_SEGMENT_SIZE) != 0) {
            abstractSerializedData.writeString(this.post_author);
        }
        if ((this.flags & 131072) != 0) {
            abstractSerializedData.writeInt64(this.grouped_id);
        }
        String str = this.attachPath;
        if (this.id < 0 && this.params != null && this.params.size() > 0) {
            String str2 = str;
            for (Entry entry : this.params.entrySet()) {
                str2 = ((String) entry.getKey()) + "|=|" + ((String) entry.getValue()) + "||" + str2;
            }
            str = "||" + str2;
        }
        abstractSerializedData.writeString(str);
        if ((this.flags & 4) != 0 && this.id < 0) {
            abstractSerializedData.writeInt32(this.fwd_msg_id);
        }
    }
}
