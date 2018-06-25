package org.telegram.tgnet;

public class TLRPC$TL_message_secret_layer72 extends TLRPC$TL_message {
    public static int constructor = 1431655929;

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
        this.ttl = stream.readInt32(exception);
        this.from_id = stream.readInt32(exception);
        this.to_id = TLRPC$Peer.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.date = stream.readInt32(exception);
        this.message = stream.readString(exception);
        this.media = TLRPC$MessageMedia.TLdeserialize(stream, stream.readInt32(exception), exception);
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
            if ((this.flags & 2048) != 0) {
                this.via_bot_name = stream.readString(exception);
            }
            if ((this.flags & 8) != 0) {
                this.reply_to_random_id = stream.readInt64(exception);
            }
            if (this.id < 0 || !(this.media == null || (this.media instanceof TLRPC$TL_messageMediaEmpty) || (this.media instanceof TLRPC$TL_messageMediaWebPage) || this.message == null || this.message.length() == 0 || !this.message.startsWith("-1"))) {
                this.attachPath = stream.readString(exception);
            }
        } else if (exception) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(magic)}));
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
        stream.writeInt32(this.ttl);
        stream.writeInt32(this.from_id);
        this.to_id.serializeToStream(stream);
        stream.writeInt32(this.date);
        stream.writeString(this.message);
        this.media.serializeToStream(stream);
        stream.writeInt32(481674261);
        int count = this.entities.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            ((TLRPC$MessageEntity) this.entities.get(a)).serializeToStream(stream);
        }
        if ((this.flags & 2048) != 0) {
            stream.writeString(this.via_bot_name);
        }
        if ((this.flags & 8) != 0) {
            stream.writeInt64(this.reply_to_random_id);
        }
        stream.writeString(this.attachPath);
    }
}
