package org.telegram.tgnet;

public class TLRPC$TL_message_old7 extends TLRPC$TL_message {
    public static int constructor = 1537633299;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        boolean z;
        this.flags = stream.readInt32(exception);
        if ((this.flags & 1) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.unread = z;
        if ((this.flags & 2) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.out = z;
        if ((this.flags & 16) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.mentioned = z;
        if ((this.flags & 32) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.media_unread = z;
        this.id = stream.readInt32(exception);
        if ((this.flags & 256) != 0) {
            this.from_id = stream.readInt32(exception);
        }
        this.to_id = TLRPC$Peer.TLdeserialize(stream, stream.readInt32(exception), exception);
        if (this.from_id == 0) {
            if (this.to_id.user_id != 0) {
                this.from_id = this.to_id.user_id;
            } else {
                this.from_id = -this.to_id.channel_id;
            }
        }
        if ((this.flags & 4) != 0) {
            this.fwd_from = new TLRPC$TL_messageFwdHeader();
            TLRPC$Peer peer = TLRPC$Peer.TLdeserialize(stream, stream.readInt32(exception), exception);
            TLRPC$MessageFwdHeader tLRPC$MessageFwdHeader;
            if (peer instanceof TLRPC$TL_peerChannel) {
                this.fwd_from.channel_id = peer.channel_id;
                tLRPC$MessageFwdHeader = this.fwd_from;
                tLRPC$MessageFwdHeader.flags |= 2;
            } else if (peer instanceof TLRPC$TL_peerUser) {
                this.fwd_from.from_id = peer.user_id;
                tLRPC$MessageFwdHeader = this.fwd_from;
                tLRPC$MessageFwdHeader.flags |= 1;
            }
            this.fwd_from.date = stream.readInt32(exception);
        }
        if ((this.flags & 8) != 0) {
            this.reply_to_msg_id = stream.readInt32(exception);
        }
        this.date = stream.readInt32(exception);
        this.message = stream.readString(exception);
        if ((this.flags & 512) != 0) {
            this.media = TLRPC$MessageMedia.TLdeserialize(stream, stream.readInt32(exception), exception);
        } else {
            this.media = new TLRPC$TL_messageMediaEmpty();
        }
        if ((this.flags & 64) != 0) {
            this.reply_markup = TLRPC$ReplyMarkup.TLdeserialize(stream, stream.readInt32(exception), exception);
        }
        if ((this.flags & 128) != 0) {
            if (stream.readInt32(exception) == 481674261) {
                int count = stream.readInt32(exception);
                int a = 0;
                while (a < count) {
                    TLRPC$MessageEntity object = TLRPC$MessageEntity.TLdeserialize(stream, stream.readInt32(exception), exception);
                    if (object != null) {
                        this.entities.add(object);
                        a++;
                    } else {
                        return;
                    }
                }
            } else if (exception) {
                throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(magic)}));
            } else {
                return;
            }
        }
        if ((this.flags & 1024) != 0) {
            this.views = stream.readInt32(exception);
        }
        if (this.id < 0 || !(this.media == null || (this.media instanceof TLRPC$TL_messageMediaEmpty) || (this.media instanceof TLRPC$TL_messageMediaWebPage) || this.message == null || this.message.length() == 0 || !this.message.startsWith("-1"))) {
            this.attachPath = stream.readString(exception);
        }
        if ((this.flags & 4) != 0 && this.id < 0) {
            this.fwd_msg_id = stream.readInt32(exception);
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        int i;
        stream.writeInt32(constructor);
        this.flags = this.unread ? this.flags | 1 : this.flags & -2;
        this.flags = this.out ? this.flags | 2 : this.flags & -3;
        this.flags = this.mentioned ? this.flags | 16 : this.flags & -17;
        if (this.media_unread) {
            i = this.flags | 32;
        } else {
            i = this.flags & -33;
        }
        this.flags = i;
        stream.writeInt32(this.flags);
        stream.writeInt32(this.id);
        if ((this.flags & 256) != 0) {
            stream.writeInt32(this.from_id);
        }
        this.to_id.serializeToStream(stream);
        if ((this.flags & 4) != 0) {
            if (this.fwd_from.from_id != 0) {
                TLRPC$TL_peerUser peer = new TLRPC$TL_peerUser();
                peer.user_id = this.fwd_from.from_id;
                peer.serializeToStream(stream);
            } else {
                TLRPC$TL_peerChannel peer2 = new TLRPC$TL_peerChannel();
                peer2.channel_id = this.fwd_from.channel_id;
                peer2.serializeToStream(stream);
            }
            stream.writeInt32(this.fwd_from.date);
        }
        if ((this.flags & 8) != 0) {
            stream.writeInt32(this.reply_to_msg_id);
        }
        stream.writeInt32(this.date);
        stream.writeString(this.message);
        if ((this.flags & 512) != 0) {
            this.media.serializeToStream(stream);
        }
        if ((this.flags & 64) != 0) {
            this.reply_markup.serializeToStream(stream);
        }
        if ((this.flags & 128) != 0) {
            stream.writeInt32(481674261);
            int count = this.entities.size();
            stream.writeInt32(count);
            for (int a = 0; a < count; a++) {
                ((TLRPC$MessageEntity) this.entities.get(a)).serializeToStream(stream);
            }
        }
        if ((this.flags & 1024) != 0) {
            stream.writeInt32(this.views);
        }
        stream.writeString(this.attachPath);
        if ((this.flags & 4) != 0 && this.id < 0) {
            stream.writeInt32(this.fwd_msg_id);
        }
    }
}
