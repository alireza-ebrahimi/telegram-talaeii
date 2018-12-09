package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.MessageEntity;
import org.telegram.tgnet.TLRPC.MessageFwdHeader;
import org.telegram.tgnet.TLRPC.MessageMedia;
import org.telegram.tgnet.TLRPC.Peer;
import org.telegram.tgnet.TLRPC.ReplyMarkup;

public class TLRPC$TL_message_old7 extends TLRPC$TL_message {
    public static int constructor = 1537633299;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int i = 0;
        this.flags = abstractSerializedData.readInt32(z);
        this.unread = (this.flags & 1) != 0;
        this.out = (this.flags & 2) != 0;
        this.mentioned = (this.flags & 16) != 0;
        this.media_unread = (this.flags & 32) != 0;
        this.id = abstractSerializedData.readInt32(z);
        if ((this.flags & 256) != 0) {
            this.from_id = abstractSerializedData.readInt32(z);
        }
        this.to_id = Peer.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        if (this.from_id == 0) {
            if (this.to_id.user_id != 0) {
                this.from_id = this.to_id.user_id;
            } else {
                this.from_id = -this.to_id.channel_id;
            }
        }
        if ((this.flags & 4) != 0) {
            this.fwd_from = new TLRPC$TL_messageFwdHeader();
            Peer TLdeserialize = Peer.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            MessageFwdHeader messageFwdHeader;
            if (TLdeserialize instanceof TLRPC$TL_peerChannel) {
                this.fwd_from.channel_id = TLdeserialize.channel_id;
                messageFwdHeader = this.fwd_from;
                messageFwdHeader.flags |= 2;
            } else if (TLdeserialize instanceof TLRPC$TL_peerUser) {
                this.fwd_from.from_id = TLdeserialize.user_id;
                messageFwdHeader = this.fwd_from;
                messageFwdHeader.flags |= 1;
            }
            this.fwd_from.date = abstractSerializedData.readInt32(z);
        }
        if ((this.flags & 8) != 0) {
            this.reply_to_msg_id = abstractSerializedData.readInt32(z);
        }
        this.date = abstractSerializedData.readInt32(z);
        this.message = abstractSerializedData.readString(z);
        if ((this.flags & 512) != 0) {
            this.media = MessageMedia.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        } else {
            this.media = new TLRPC$TL_messageMediaEmpty();
        }
        if ((this.flags & 64) != 0) {
            this.reply_markup = ReplyMarkup.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }
        if ((this.flags & 128) != 0) {
            int readInt32;
            if (abstractSerializedData.readInt32(z) == 481674261) {
                readInt32 = abstractSerializedData.readInt32(z);
                while (i < readInt32) {
                    MessageEntity TLdeserialize2 = MessageEntity.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                    if (TLdeserialize2 != null) {
                        this.entities.add(TLdeserialize2);
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
        if ((this.flags & 1024) != 0) {
            this.views = abstractSerializedData.readInt32(z);
        }
        if (this.id < 0 || !(this.media == null || (this.media instanceof TLRPC$TL_messageMediaEmpty) || (this.media instanceof TLRPC$TL_messageMediaWebPage) || this.message == null || this.message.length() == 0 || !this.message.startsWith("-1"))) {
            this.attachPath = abstractSerializedData.readString(z);
        }
        if ((this.flags & 4) != 0 && this.id < 0) {
            this.fwd_msg_id = abstractSerializedData.readInt32(z);
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
        if ((this.flags & 256) != 0) {
            abstractSerializedData.writeInt32(this.from_id);
        }
        this.to_id.serializeToStream(abstractSerializedData);
        if ((this.flags & 4) != 0) {
            if (this.fwd_from.from_id != 0) {
                TLRPC$TL_peerUser tLRPC$TL_peerUser = new TLRPC$TL_peerUser();
                tLRPC$TL_peerUser.user_id = this.fwd_from.from_id;
                tLRPC$TL_peerUser.serializeToStream(abstractSerializedData);
            } else {
                TLRPC$TL_peerChannel tLRPC$TL_peerChannel = new TLRPC$TL_peerChannel();
                tLRPC$TL_peerChannel.channel_id = this.fwd_from.channel_id;
                tLRPC$TL_peerChannel.serializeToStream(abstractSerializedData);
            }
            abstractSerializedData.writeInt32(this.fwd_from.date);
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
        abstractSerializedData.writeString(this.attachPath);
        if ((this.flags & 4) != 0 && this.id < 0) {
            abstractSerializedData.writeInt32(this.fwd_msg_id);
        }
    }
}
